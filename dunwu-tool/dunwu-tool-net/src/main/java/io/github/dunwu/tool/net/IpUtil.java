package io.github.dunwu.tool.net;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.io.AnsiColorUtil;
import io.github.dunwu.tool.net.ip.Searcher;
import io.github.dunwu.tool.util.ValidatorUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ip 工具类 (数据来自 IPIP)
 * <p>
 * 先将字符串传换为 byte[] 再用 InetAddress.getByAddress(byte[])，避免了 InetAddress.getByName(ip) 可能引起的 DNS 访问.
 * <p>
 * 根据 IP 获取地域相关方法需要加载 17monipdb.dat（来自于 IPIP.net 的数据文件），可以考虑缓存。
 * <p>
 * InetAddress 与 String 的转换其实消耗不小，如果是有限的地址，建议进行缓存。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-11
 */
public class IpUtil {

    private static final String IP_FILE = "db/ip2region.xdb";
    private static final Searcher searcher = getSearcher();

    private IpUtil() { }

    // IP 校验
    // -------------------------------------------------------------------------------------------------

    /**
     * 判断传入的字符串是否为有效 ip 地址（ipv4/ipv6都支持）
     *
     * @param ipStr ip 地址
     * @return true / false
     */
    public static boolean isValidIp(final String ipStr) {
        return ValidatorUtil.isIpv4(ipStr) || ValidatorUtil.isIpv6(ipStr);
    }

    /**
     * 判断传入的字符串是否为有效 ipv4 地址
     *
     * @param ipv4Str ipv4 地址
     * @return true / false
     */
    public static boolean isValidIpv4(final String ipv4Str) {
        return ValidatorUtil.isIpv4(ipv4Str);
    }

    /**
     * 判断传入的字符串是否为有效 ipv6 地址
     *
     * @param ipv6Str ipv6 地址
     * @return true / false
     */
    public static boolean isValidIpv6(final String ipv6Str) {
        return ValidatorUtil.isIpv6(ipv6Str);
    }

    // Ip 地址转化
    // -------------------------------------------------------------------------------------------------

    /**
     * Ipv4 String 转换为 byte[]，失败则返回 null
     */
    public static byte[] ipv4StrToBytes(final String ipv4Str) {
        if (StrUtil.isBlank(ipv4Str)) {
            return null;
        }

        List<String> list = StrUtil.split(ipv4Str, '.', 4);
        if (list == null || list.size() != 4) {
            return null;
        }

        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            int value = Integer.parseInt(list.get(i));
            if (value > 255) {
                return null;
            }
            bytes[i] = (byte) value;
        }
        return bytes;
    }

    /**
     * Ipv4 String 转换到 int
     */
    public static int ipv4StrToInt(final String ipv4Str) {
        byte[] bytes = ipv4StrToBytes(ipv4Str);
        if (ArrayUtil.isEmpty(bytes)) {
            return 0;
        } else {
            return Convert.bytesToInt(bytes);
        }
    }

    /**
     * Ipv4 String 转换到 long
     */
    public static long ipv4StrToLong(final String ipv4Str) {
        return ipv4StrToInt(ipv4Str);
    }

    /**
     * int转换到IPV4 String, from Netty NetUtil
     */
    public static String intToIpv4Str(final int i) {
        return new StringBuilder(15).append((i >> 24) & 0xff).append('.')
                                    .append(i >> 16 & 0xff).append('.').append((i >> 8) & 0xff).append('.')
                                    .append(i & 0xff).toString();
    }

    public static String ipv4ToIpv6(final String ipv4Str) throws UnknownHostException {
        if (!isValidIpv4(ipv4Str)) {
            throw new IllegalArgumentException(ipv4Str + " is invalid ipv4 address");
        }

        byte[] ipv4Bytes = ipv4StrToBytes(ipv4Str);
        if (ArrayUtil.isEmpty(ipv4Bytes)) {
            return null;
        }

        byte[] ipv6Bytes = new byte[16];
        ipv6Bytes[12] = ipv4Bytes[0];
        ipv6Bytes[13] = ipv4Bytes[1];
        ipv6Bytes[14] = ipv4Bytes[2];
        ipv6Bytes[15] = ipv4Bytes[3];
        InetAddress address = Inet6Address.getByAddress(ipv6Bytes);
        return address.getHostAddress();
    }

    /**
     * 从 InetAddress 转换为 String（可以是 ipv4 或 ipv6）。
     */
    public static String getStrFromInetAddress(final InetAddress address) {
        return address.getHostAddress();
    }

    // getRegion
    // -------------------------------------------------------------------------------------------------

    private static String getIpDbFilePath() {
        URL url = IpUtil.class.getClassLoader().getResource(IP_FILE);
        if (url == null) {
            AnsiColorUtil.BOLD_RED.printf("IP地址库文件路径 %s 不存在！\n", IP_FILE);
            return null;
        }

        String dbPath = url.getPath();
        File file = new File(dbPath);
        if (!file.exists()) {
            AnsiColorUtil.BOLD_RED.printf("IP地址库文件 %s 不存在！\n", IP_FILE);
            String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
            dbPath = tmpDir + File.separator + "ip2region.db";
            AnsiColorUtil.BOLD_RED.printf("IP地址库临时文件路径：%s\n", dbPath);
            file = new File(dbPath);
            if (!file.exists() || (System.currentTimeMillis() - file.lastModified() > 86400000L)) {
                InputStream input = null;
                try {
                    input = IpUtil.class.getClassLoader().getResourceAsStream(IP_FILE);
                    IoUtil.copy(input, new FileOutputStream(file));
                } catch (IOException exception) {
                    exception.printStackTrace();
                } finally {
                    IoUtil.close(input);
                }
            }
        }
        return dbPath;
    }

    private static Searcher getSearcher() {
        String dbPath = getIpDbFilePath();

        // 1、从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用。
        byte[] vIndex;
        try {
            vIndex = Searcher.loadVectorIndexFromFile(dbPath);
        } catch (Exception e) {
            AnsiColorUtil.BOLD_RED.printf("failed to load vector index from `%s`: %s\n", dbPath, e);
            return null;
        }
        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithVectorIndex(dbPath, vIndex);
        } catch (Exception e) {
            System.out.printf("failed to create vectorIndex cached searcher with `%s`: %s\n", dbPath, e);
        }
        return searcher;
    }

    /**
     * 返回 IP 地址所属地（返回能得到的所有级别行政单位+ISP）
     *
     * @param ip IP 地址
     * @return 所属地的名称数组
     */
    public static String getRegion(String ip) {
        if (searcher == null) {
            AnsiColorUtil.BOLD_RED.println("IP地址库查询器未成功加载！");
            return null;
        }

        String result = null;
        try {
            result = searcher.search(ip);
        } catch (Exception e) {
            AnsiColorUtil.BOLD_RED.printf("查询 (%s) 失败！%s\n", ip, e);
        }

        if (StrUtil.isEmpty(result)) {
            return null;
        }

        List<String> regions = StrUtil.split(result, '|');
        if (CollectionUtil.isEmpty(regions)) {
            return null;
        }

        regions = regions.stream().filter(i -> !i.equals("0")).collect(Collectors.toList());
        return CollectionUtil.join(regions, "|");
    }

    /**
     * 获取当前机器的IP
     *
     * @return /
     */
    public static String getLocalIp() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                interfaces.hasMoreElements(); ) {
                NetworkInterface anInterface = interfaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<InetAddress> inetAddresses = anInterface.getInetAddresses();
                    inetAddresses.hasMoreElements(); ) {
                    InetAddress inetAddr = inetAddresses.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr.getHostAddress();
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress.getHostAddress();
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                return "";
            }
            return jdkSuppliedAddress.getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

}

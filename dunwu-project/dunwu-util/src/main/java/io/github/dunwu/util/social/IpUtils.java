package io.github.dunwu.util.social;

import com.google.common.net.InetAddresses;
import io.github.dunwu.util.base.ByteExtUtils;
import io.github.dunwu.util.base.NumberExtUtils;
import io.github.dunwu.util.collection.ArrayExtUtils;
import io.github.dunwu.util.io.ResourceUtil;
import io.github.dunwu.util.social.bean.City;
import io.github.dunwu.util.social.bean.County;
import io.github.dunwu.util.social.bean.Province;
import io.github.dunwu.util.text.RegexUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

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
public class IpUtils {

	private static final String IP_DB_FILE = "db/17monipdb.dat";

	private static final String REGION_CHINA = "中国";

	private static final int IP_DB_INDEX_LENGTH = 256;

	private static final int[] IP_INDEXES = new int[IP_DB_INDEX_LENGTH];

	private static int offset;

	private static ByteBuffer dataBuffer;

	private static ByteBuffer indexBuffer;

	private static ReentrantLock lock = new ReentrantLock();

	static {
		init();
	}

	private IpUtils() {}

	// isValid
	// -------------------------------------------------------------------------------------------------

	/**
	 * 判断传入的字符串是否为有效 ip 地址（ipv4/ipv6都支持）
	 *
	 * @param ipStr ip 地址
	 * @return true / false
	 */
	public static boolean isValidIp(final String ipStr) {
		return RegexUtils.isIpv4(ipStr) || RegexUtils.isIpv6(ipStr);
	}

	/**
	 * 判断传入的字符串是否为有效 ipv4 地址
	 *
	 * @param ipv4Str ipv4 地址
	 * @return true / false
	 */
	public static boolean isValidIpv4(final String ipv4Str) {
		return RegexUtils.isIpv4(ipv4Str);
	}

	/**
	 * 判断传入的字符串是否为有效 ipv6 地址
	 *
	 * @param ipv6Str ipv6 地址
	 * @return true / false
	 */
	public static boolean isValidIpv6(final String ipv6Str) {
		return RegexUtils.isIpv6(ipv6Str);
	}

	// Inet4Address
	// -------------------------------------------------------------------------------------------------

	/**
	 * 从 int 转换为 Inet4Address (仅支持IPV4)
	 */
	public static Inet4Address getInet4AddressFromInt(final int address) {
		return InetAddresses.fromInteger(address);
	}

	/**
	 * 从 String 转换为 Inet4Address (仅支持 ipv4)，失败则返回 null
	 */
	public static Inet4Address getInet4AddressFromStr(final String address) {
		byte[] bytes = ipv4StrToBytes(address);
		if (bytes == null) {
			return null;
		}

		try {
			return (Inet4Address) Inet4Address.getByAddress(bytes);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从 String 转换为 InetAddress（可以是 ipv4 或 ipv6）。
	 */
	public static InetAddress getInetAddressFromStr(final String address) {
		return InetAddresses.forString(address);
	}

	/**
	 * 从 InetAddress 转换为 int（可以是 ipv4 或 ipv6）。
	 */
	public static int getIntFromInetAddress(final InetAddress address) {
		return InetAddresses.coerceToInteger(address);
	}

	/**
	 * 从 InetAddress 转换为 String（可以是 ipv4 或 ipv6）。
	 */
	public static String getStrFromInetAddress(final InetAddress address) {
		return InetAddresses.toAddrString(address);
	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * Ipv4 String 转换为 byte[]，失败则返回 null
	 */
	public static byte[] ipv4StrToBytes(final String ipv4Str) {
		if (StringUtils.isBlank(ipv4Str)) {
			return null;
		}

		String[] arrays = StringUtils.split(ipv4Str, ".", 4);
		if (arrays == null || arrays.length != 4) {
			return null;
		}

		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			int value = Integer.parseInt(arrays[i]);
			if (value > 255) {
				return null;
			}
			bytes[i] = (byte) value;
		}
		return bytes;
	}

	/**
	 * Ipv4 String 转换到 long
	 */
	public static long ipv4StrToLong(final String ipv4Str) {
		return ipv4StrToInt(ipv4Str);
	}

	/**
	 * Ipv4 String 转换到 int
	 */
	public static int ipv4StrToInt(final String ipv4Str) {
		byte[] bytes = ipv4StrToBytes(ipv4Str);
		if (ArrayUtils.isEmpty(bytes)) {
			return 0;
		} else {
			return NumberExtUtils.parseInt(bytes);
		}
	}

	/**
	 * int转换到IPV4 String, from Netty NetUtil
	 */
	public static String intToIpv4Str(final int i) {
		return new StringBuilder(15).append((i >> 24) & 0xff).append('.')
			.append(i >> 16 & 0xff).append('.').append((i >> 8) & 0xff).append('.')
			.append(i & 0xff).toString();
	}

	// getRegion
	// -------------------------------------------------------------------------------------------------

	/**
	 * 返回 IP 地址所属地的编码（返回能得到的最小行政单位）
	 *
	 * @param ip IP 地址
	 * @return 所属地的编码
	 */
	public static String getRegionCode(final String ip) {
		String[] regionNames = getFullRegionName(ip);
		if (REGION_CHINA.equals(regionNames[0])) {
			if (StringUtils.isBlank(ArrayExtUtils.indexOf(regionNames, 1))) {
				return null;
			}

			Province province = RegionUtils.getProvinceByName(regionNames[1]);
			if (province == null) {
				return null;
			}

			if (StringUtils.isBlank(ArrayExtUtils.indexOf(regionNames, 2))) {
				return province.getCode();
			}
			City city = RegionUtils.getCityByName(regionNames[2]);
			if (city == null) {
				// 如果省级行政单位名和市级行政单位名同名，视其为直辖市
				// 对于直辖市，第一个区号名为市辖区
				if (regionNames[1].equals(regionNames[2])) {
					city = RegionUtils.getCityByName("市辖区");
					return city != null ? city.getCode() : null;
				}
				return null;
			}

			return city.getCode();
		} else {
			return null;
		}
	}

	private static String[] toStandardRegionNames(final String[] regionNames) {
		if (ArrayUtils.isEmpty(regionNames)) {
			return null;
		}

		// 国家级行政单位为空，直接返回 null
		String country = ArrayExtUtils.indexOf(regionNames, 0);
		if (StringUtils.isBlank(country)) {
			return null;
		}

		// 判断是否为国内行政单位
		if (REGION_CHINA.equals(country)) {

			List<String> result = new ArrayList<>();
			result.add(country);

			// 国内地名需查询省市区
			// 省级行政单位为空，直接返回 null
			String provinceStr = ArrayExtUtils.indexOf(regionNames, 1);
			if (StringUtils.isBlank(provinceStr)) {
				return result.toArray(new String[0]);
			}
			// 查询省级行政单位标准名称
			Province province = RegionUtils.getProvinceByName(provinceStr);
			if (province == null) {
				return result.toArray(new String[0]);
			}
			result.add(province.getName());

			// 市级行政单位为空，直接返回 null
			String cityStr = ArrayExtUtils.indexOf(regionNames, 2);
			if (StringUtils.isBlank(cityStr)) {
				return result.toArray(new String[0]);
			}
			// 正常情况下，省级行政单位不应该和市级行政单位同名
			// 对于直辖市来说，如果省级行政单位和市级行政单位同名，将其视为市辖区
			if (provinceStr.equals(cityStr)) {
				cityStr = "市辖区";
			}
			City city = RegionUtils.getCityByName(cityStr);
			if (city == null) {
				return result.toArray(new String[0]);
			}
			result.add(city.getName());

			// 区/县级行政单位为空，直接返回 null
			String countyStr = ArrayExtUtils.indexOf(regionNames, 3);
			if (StringUtils.isBlank(countyStr)) {
				return result.toArray(new String[0]);
			}
			County county = RegionUtils.getCountyByName(city, countyStr);
			if (county == null) {
				return result.toArray(new String[0]);
			}
			result.add(county.getName());

			return result.toArray(new String[0]);
		} else {
			// 国外地名无需查询省市区
			// 数组过滤空字符串
			String[] temp = ArrayExtUtils.trim(regionNames);
			// 地名去重
			return getDeduplicateArray(temp);
		}
	}

	public static String[] getDeduplicateArray(final String[] array) {
		Set<String> set = new TreeSet<>(Arrays.asList(array));
		return set.toArray(new String[0]);
	}

	/**
	 * 返回 IP 地址所属地（返回能得到的所有级别行政单位）
	 *
	 * @param ip IP 地址
	 * @return 所属地的名称数组
	 */
	public static String[] getFullRegionName(final String ip) {
		if (StringUtils.isBlank(ip)) {
			throw new IllegalArgumentException(ip + " must not be null");
		}

		if (!isValidIpv4(ip)) {
			throw new IllegalArgumentException(ip + " is not valid ipv4 address");
		}

		int ipPrefixValue = new Integer(ip.substring(0, ip.indexOf(".")));
		long ip2longValue = ipv4StrToInt(ip);
		int start = IP_INDEXES[ipPrefixValue];
		int maxCompLen = offset - 1028;
		long indexOffset = -1;
		int indexLength = -1;
		byte b = 0;
		for (start = start * 8 + 1024; start < maxCompLen; start += 8) {
			if (indexBuffer.getInt(start) >= ip2longValue) {
				indexOffset =
					ByteExtUtils.bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5),
						indexBuffer.get(start + 4));
				indexLength = 0xFF & indexBuffer.get(start + 7);
				break;
			}
		}

		byte[] bytes;
		lock.lock();
		try {
			dataBuffer.position(offset + (int) indexOffset - 1024);
			bytes = new byte[indexLength];
			dataBuffer.get(bytes, 0, indexLength);
		} finally {
			lock.unlock();
		}

		String temp = new String(bytes, StandardCharsets.UTF_8);
		String[] regions = temp.split("\t");

		return toStandardRegionNames(regions);
	}

	/**
	 * 返回 IP 地址所属地的名称（返回能得到的最小行政单位）
	 *
	 * @param ip IP 地址
	 * @return 所属地的名称
	 */
	public static String getRegionName(final String ip) {
		String[] regionNames = getFullRegionName(ip);
		if (ArrayUtils.isEmpty(regionNames)) {
			return null;
		}

		int end = regionNames.length - 1;
		for (int i = end; i >= 0; i--) {
			if (StringUtils.isNotBlank(regionNames[i])) {
				return regionNames[i];
			}
		}
		return null;
	}

	public static String ipv4ToIpv6(final String ipv4Str) throws UnknownHostException {
		if (!isValidIpv4(ipv4Str)) {
			throw new IllegalArgumentException(ipv4Str + " is invalid ipv4 address");
		}

		byte[] ipv4Bytes = ipv4StrToBytes(ipv4Str);
		if (ArrayUtils.isEmpty(ipv4Bytes)) {
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

	private static void init() {

		InputStream fis = null;
		lock.lock();
		try {
			fis = IpUtils.class.getClassLoader().getResourceAsStream(IP_DB_FILE);
			if (fis == null) {
				System.out.printf("加载 IP 数据库文件 %s 失败\n", IP_DB_FILE);
				return;
			}
			dataBuffer = ByteBuffer.allocate(fis.available());
			byte[] chunk = new byte[4096];
			while (fis.available() > 0) {
				int len = fis.read(chunk);
				dataBuffer.put(chunk, 0, len);
			}
			dataBuffer.position(0);
			int indexLength = dataBuffer.getInt();
			byte[] indexBytes = new byte[indexLength];
			dataBuffer.get(indexBytes, 0, indexLength - 4);
			indexBuffer = ByteBuffer.wrap(indexBytes);
			indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
			offset = indexLength;

			int loop = 0;
			while (loop++ < IP_DB_INDEX_LENGTH) {
				IP_INDEXES[loop - 1] = indexBuffer.getInt();
			}
			indexBuffer.order(ByteOrder.BIG_ENDIAN);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}
	}

}

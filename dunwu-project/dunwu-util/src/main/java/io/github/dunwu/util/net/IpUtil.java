package io.github.dunwu.util.net;

import com.google.common.net.InetAddresses;
import io.github.dunwu.util.base.NumberExtUtils;
import io.github.dunwu.util.text.RegexUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * InetAddress工具类，基于Guava的InetAddresses. 主要包含int, String/IPV4String,
 * InetAdress/Inet4Address之间的互相转换
 * 先将字符串传换为byte[]再用InetAddress.getByAddress(byte[])，避免了InetAddress.getByName(ip)可能引起的DNS访问.
 * InetAddress与String的转换其实消耗不小，如果是有限的地址，建议进行缓存.
 */
public class IpUtil {

	public static final int MAX_IP_NUM = 4;

	public static boolean isValidIp(String address) {
		return RegexUtil.isIpv4(address) || RegexUtil.isIpv6(address);
	}

	public static boolean isValidIpv4(String address) {
		return RegexUtil.isIpv4(address);
	}

	public static boolean isValidIpv6(String address) {
		return RegexUtil.isIpv6(address);
	}

	/**
	 * 从InetAddress转化到int, 传输和存储时, 用int代表InetAddress是最小的开销.
	 * InetAddress可以是IPV4或IPV6，都会转成IPV4.
	 *
	 * @see com.google.common.net.InetAddresses#coerceToInteger(InetAddress)
	 */
	public static int toInt(InetAddress address) {
		return InetAddresses.coerceToInteger(address);
	}

	/**
	 * InetAddress转换为String. InetAddress可以是IPV4或IPV6. 其中IPV4直接调用getHostAddress()
	 *
	 * @see com.google.common.net.InetAddresses#toAddrString(InetAddress)
	 */
	public static String toIpString(InetAddress address) {
		return InetAddresses.toAddrString(address);
	}

	/**
	 * 从int转换为Inet4Address(仅支持IPV4)
	 */
	public static Inet4Address fromInt(int address) {
		return InetAddresses.fromInteger(address);
	}

	/**
	 * 从String转换为InetAddress. IpString可以是ipv4 或 ipv6 string, 但不可以是域名.
	 * 先字符串传换为byte[]再调getByAddress(byte[]) ，避免了调用getByName(ip)可能引起的DNS访问.
	 */
	public static InetAddress fromIpString(String address) {
		return InetAddresses.forString(address);
	}

	/**
	 * 从IPv4String转换为InetAddress. IpString如果确定ipv4, 使用本方法减少字符分析消耗 .
	 * 先字符串传换为byte[]再调getByAddress(byte[]) ，避免了调用getByName(ip)可能引起的DNS访问.
	 */
	public static Inet4Address fromIpv4String(String address) {
		byte[] bytes = ip4StringToBytes(address);
		if (bytes == null) {
			return null;
		}
		else {
			try {
				return (Inet4Address) Inet4Address.getByAddress(bytes);
			}
			catch (UnknownHostException e) {
				throw new AssertionError(e);
			}
		}
	}

	/**
	 * Ipv4 String 转换到byte[]
	 */
	private static byte[] ip4StringToBytes(String ipv4Str) {
		if (ipv4Str == null) {
			return null;
		}

		String[] strs = StringUtils.split(ipv4Str, ".", 4);
		if (strs.length != MAX_IP_NUM) {
			return null;
		}

		byte[] byteAddress = new byte[4];
		for (int i = 0; i < MAX_IP_NUM; i++) {
			int tempInt = Integer.parseInt(strs[i]);
			if (tempInt > 255) {
				return null;
			}
			byteAddress[i] = (byte) tempInt;
		}
		return byteAddress;
	}

	/**
	 * int转换到IPV4 String, from Netty NetUtil
	 */
	public static String intToIpv4String(int i) {
		return new StringBuilder(15).append((i >> 24) & 0xff).append('.')
				.append(i >> 16 & 0xff).append('.').append((i >> 8) & 0xff).append('.')
				.append(i & 0xff).toString();
	}

	/**
	 * Ipv4 String 转换到int
	 */
	public static int ipv4StringToInt(String ipv4Str) {
		byte[] byteAddress = ip4StringToBytes(ipv4Str);
		if (byteAddress == null) {
			return 0;
		}
		else {
			return NumberExtUtils.parseInt(byteAddress);
		}
	}

}

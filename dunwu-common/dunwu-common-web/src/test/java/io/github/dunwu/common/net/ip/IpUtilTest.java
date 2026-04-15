package io.github.dunwu.common.net.ip;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link IpUtil} 单元测试
 */
@DisplayName("IpUtil 工具类测试")
class IpUtilTest {

    @Nested
    @DisplayName("isValidIp 测试")
    class IsValidIpTest {

        @Test
        @DisplayName("isValidIp - 有效 IPv4 应返回 true")
        void isValidIp_validIpv4() {
            assertThat(IpUtil.isValidIp("192.168.1.1")).isTrue();
            assertThat(IpUtil.isValidIp("127.0.0.1")).isTrue();
        }

        @Test
        @DisplayName("isValidIp - 有效 IPv6 应返回 true")
        void isValidIp_validIpv6() {
            assertThat(IpUtil.isValidIp("::1")).isTrue();
            assertThat(IpUtil.isValidIp("2001:0db8:85a3:0000:0000:8a2e:0370:7334")).isTrue();
        }

        @Test
        @DisplayName("isValidIp - 无效 IP 应返回 false")
        void isValidIp_invalid() {
            assertThat(IpUtil.isValidIp("")).isFalse();
            assertThat(IpUtil.isValidIp(null)).isFalse();
            assertThat(IpUtil.isValidIp("999.999.999.999")).isFalse();
            assertThat(IpUtil.isValidIp("abc")).isFalse();
        }

    }

    @Nested
    @DisplayName("isValidIpv4 测试")
    class IsValidIpv4Test {

        @Test
        @DisplayName("isValidIpv4 - 有效 IPv4 应返回 true")
        void isValidIpv4_valid() {
            assertThat(IpUtil.isValidIpv4("10.0.0.1")).isTrue();
            assertThat(IpUtil.isValidIpv4("255.255.255.255")).isTrue();
        }

        @Test
        @DisplayName("isValidIpv4 - 无效 IPv4 应返回 false")
        void isValidIpv4_invalid() {
            assertThat(IpUtil.isValidIpv4("256.1.1.1")).isFalse();
            assertThat(IpUtil.isValidIpv4("1.1.1")).isFalse();
            assertThat(IpUtil.isValidIpv4(null)).isFalse();
        }

    }

    @Nested
    @DisplayName("isValidIpv6 测试")
    class IsValidIpv6Test {

        @Test
        @DisplayName("isValidIpv6 - 有效 IPv6 应返回 true")
        void isValidIpv6_valid() {
            assertThat(IpUtil.isValidIpv6("::")).isTrue();
            assertThat(IpUtil.isValidIpv6("fe80::1")).isTrue();
        }

        @Test
        @DisplayName("isValidIpv6 - 无效 IPv6 应返回 false")
        void isValidIpv6_invalid() {
            assertThat(IpUtil.isValidIpv6("192.168.1.1")).isFalse();
            assertThat(IpUtil.isValidIpv6(null)).isFalse();
        }

    }

    @Nested
    @DisplayName("ipv4StrToBytes 测试")
    class Ipv4StrToBytesTest {

        @Test
        @DisplayName("ipv4StrToBytes - 有效 IPv4 应返回 4 字节数组")
        void ipv4StrToBytes_valid() {
            byte[] bytes = IpUtil.ipv4StrToBytes("192.168.1.100");
            assertThat(bytes).isNotNull();
            assertThat(bytes).hasSize(4);
            assertThat(bytes[0]).isEqualTo((byte)192);
            assertThat(bytes[1]).isEqualTo((byte)168);
            assertThat(bytes[2]).isEqualTo((byte)1);
            assertThat(bytes[3]).isEqualTo((byte)100);
        }

        @Test
        @DisplayName("ipv4StrToBytes - 无效输入应返回 null")
        void ipv4StrToBytes_invalid() {
            assertThat(IpUtil.ipv4StrToBytes(null)).isNull();
            assertThat(IpUtil.ipv4StrToBytes("")).isNull();
            assertThat(IpUtil.ipv4StrToBytes("1.2.3")).isNull();
            assertThat(IpUtil.ipv4StrToBytes("256.1.1.1")).isNull();
        }

    }

    @Nested
    @DisplayName("ipv4StrToInt 测试")
    class Ipv4StrToIntTest {

        @Test
        @DisplayName("ipv4StrToInt - 有效 IPv4 应返回 int")
        void ipv4StrToInt_valid() {
            int result = IpUtil.ipv4StrToInt("127.0.0.1");
            assertThat(result).isNotZero();
        }

        @Test
        @DisplayName("ipv4StrToInt - 无效输入应返回 0")
        void ipv4StrToInt_invalid() {
            assertThat(IpUtil.ipv4StrToInt("")).isZero();
            assertThat(IpUtil.ipv4StrToInt(null)).isZero();
        }

    }

    @Nested
    @DisplayName("ipv4StrToLong 测试")
    class Ipv4StrToLongTest {

        @Test
        @DisplayName("ipv4StrToLong - 有效 IPv4 应返回 long")
        void ipv4StrToLong_valid() {
            long result = IpUtil.ipv4StrToLong("10.0.0.1");
            assertThat(result).isNotZero();
        }

        @Test
        @DisplayName("ipv4StrToLong - 无效输入应返回 0")
        void ipv4StrToLong_invalid() {
            assertThat(IpUtil.ipv4StrToLong("")).isZero();
        }

    }

    @Nested
    @DisplayName("intToIpv4Str 测试")
    class IntToIpv4StrTest {

        @Test
        @DisplayName("intToIpv4Str - 应正确转换 int 到 IPv4 字符串")
        void intToIpv4Str() {
            String result = IpUtil.intToIpv4Str(0x7F000001);
            assertThat(result).isEqualTo("127.0.0.1");
        }

        @Test
        @DisplayName("intToIpv4Str - 0 应返回 0.0.0.0")
        void intToIpv4Str_zero() {
            assertThat(IpUtil.intToIpv4Str(0)).isEqualTo("0.0.0.0");
        }

    }

    @Nested
    @DisplayName("ipv4ToIpv6 测试")
    class Ipv4ToIpv6Test {

        @Test
        @DisplayName("ipv4ToIpv6 - 有效 IPv4 应返回 IPv6 格式字符串")
        void ipv4ToIpv6_valid() throws UnknownHostException {
            String result = IpUtil.ipv4ToIpv6("192.168.1.1");
            assertThat(result).isNotNull();
            assertThat(result).contains(":");
        }

        @Test
        @DisplayName("ipv4ToIpv6 - 无效 IPv4 应抛出 IllegalArgumentException")
        void ipv4ToIpv6_invalid() {
            assertThatThrownBy(() -> IpUtil.ipv4ToIpv6("invalid")).isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("getStrFromInetAddress 测试")
    class GetStrFromInetAddressTest {

        @Test
        @DisplayName("getStrFromInetAddress - 应返回 IP 字符串")
        void getStrFromInetAddress() throws UnknownHostException {
            InetAddress address = InetAddress.getByName("127.0.0.1");
            String result = IpUtil.getStrFromInetAddress(address);
            assertThat(result).isEqualTo("127.0.0.1");
        }

    }

    @Nested
    @DisplayName("getRegion 测试")
    class GetRegionTest {

        @Test
        @DisplayName("getRegion - 查询有效 IP 应返回地域信息或 null(取决于数据库)")
        void getRegion() {
            // 该方法依赖外部数据文件，测试不抛异常即可
            String result = IpUtil.getRegion("114.114.114.114");
            // 结果可能为 null（如果数据文件未加载）
            assertThat(result).isNotNull();
        }

    }

    @Nested
    @DisplayName("getLocalIp 测试")
    class GetLocalIpTest {

        @Test
        @DisplayName("getLocalIp - 应返回本机 IP 字符串")
        void getLocalIp() {
            String localIp = IpUtil.getLocalIp();
            assertThat(localIp).isNotNull();
        }

    }

}

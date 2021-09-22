package io.github.dunwu.tool.net;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.io.AnsiColorUtil;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

class IpUtilsTest {

    @Test
    void isValidIp() {
        assertThat(IpUtil.isValidIpv4("127.0.0.1")).isTrue();
        assertThat(IpUtil.isValidIpv4("192.168.0.1")).isTrue();
        assertThat(IpUtil.isValidIpv4("192.168.0.2")).isTrue();
        assertThat(IpUtil.isValidIpv4("")).isFalse();
        assertThat(IpUtil.isValidIpv4("256.168.0.1")).isFalse();

        assertThat(IpUtil.isValidIpv6("2001:0db8:85a3:0000:0000:8a2e:0370:7334")).isTrue();
        assertThat(IpUtil.isValidIpv6("2001:db8:85a3:0:0:8A2E:0370:7334")).isTrue();
        assertThat(IpUtil.isValidIpv6("0:0:0:0:0:0:0:0")).isTrue();
        assertThat(IpUtil.isValidIpv6("02001:0db8:85a3:0000:0000:8a2e:0370:7334")).isFalse();

        assertThat(IpUtil.isValidIp("192.168.0.1")).isTrue();
        assertThat(IpUtil.isValidIp("192.168.0.2")).isTrue();
        assertThat(IpUtil.isValidIp("")).isFalse();
        assertThat(IpUtil.isValidIp("256.168.0.1")).isFalse();
        assertThat(IpUtil.isValidIp("2001:0db8:85a3:0000:0000:8a2e:0370:7334")).isTrue();
        assertThat(IpUtil.isValidIp("2001:db8:85a3:0:0:8A2E:0370:7334")).isTrue();
        assertThat(IpUtil.isValidIp("0:0:0:0:0:0:0:0")).isTrue();
        assertThat(IpUtil.isValidIp("02001:0db8:85a3:0000:0000:8a2e:0370:7334")).isFalse();
    }

    @Test
    void ipv4StringToInt() {

        assertThat(IpUtil.ipv4StrToInt("192.168.0.1")).isEqualTo(-1062731775);
        assertThat(IpUtil.ipv4StrToInt("192.168.0.2")).isEqualTo(-1062731774);

        assertThat(IpUtil.intToIpv4Str(-1062731775)).isEqualTo("192.168.0.1");
        assertThat(IpUtil.intToIpv4Str(-1062731774)).isEqualTo("192.168.0.2");
    }

    @Test
    void getRegion() {
        String[] ips = { "117.136.45.132", "218.138.160.46", "183.206.167.143", "123.138.216.117", "180.98.66.165",
            "183.206.167.17", "72.14.229.153", "202.21.96.90", "210.51.244.183", "117.136.83.118", "210.51.244.185",
            "161.64.208.3", "165.204.55.251", "123.178.83.77", "116.210.20.41", "222.140.115.52", "223.199.99.173",
            "153.0.70.164", "36.101.184.141", "219.138.245.230", "61.186.59.220", "116.209.68.12", "61.186.61.166",
            "120.213.7.1", "222.80.175.22", "223.199.110.207", "36.101.185.17", "116.210.53.67", "115.49.182.136",
            "59.47.53.213", "222.214.220.148", "36.101.71.90", "218.84.166.132", "222.81.14.94", "43.224.213.66",
            "221.205.169.151", "1.0.2.0", "218.93.179.246", "2.0.0.1", "118.28.8.8", "8.8.8.8",
            "61.155.110.90",
            "27.225.89.137", "117.136.38.186", "183.206.169.85", "114.221.80.160", "121.229.26.184" };

        long time = 0L;
        for (String ip : ips) {
            String[] regionNames = IpUtil.getFullRegionName(ip);
            long beginTime = System.nanoTime();
            AnsiColorUtil.BLUE.println(ip + " 所属完整行政单位：" + StrUtil.join(",", regionNames));
            AnsiColorUtil.BLUE.println(ip + " 最小行政单位：" + StrUtil.join(IpUtil.getRegionName(ip)));
            long endTime = System.nanoTime();
            long value = (endTime - beginTime) / 1000000;
            time += value;
            System.out.println("耗时：" + value);
            String city;
            city = IpUtil.getRegionCode(ip);
            AnsiColorUtil.YELLOW.println("IP:" + ip + " City :" + city);
        }

        System.out.println("平均耗时：" + (time / ips.length));
    }

    @Test
    void test() throws UnknownHostException {
        String ipv6Str = IpUtil.ipv4ToIpv6("127.0.0.1");
        assertThat(ipv6Str).isEqualTo("0:0:0:0:0:0:7f00:1");
        System.out.printf("127.0.0.1 对应的 IPv6 地址为：%s\n", ipv6Str);

        String ipv6Str2 = IpUtil.ipv4ToIpv6("36.101.185.17");
        assertThat(ipv6Str2).isEqualTo("0:0:0:0:0:0:2465:b911");
        System.out.printf("36.101.185.17 对应的 IPv6 地址为：%s\n", ipv6Str2);
    }

}

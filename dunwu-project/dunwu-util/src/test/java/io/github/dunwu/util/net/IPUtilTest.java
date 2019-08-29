package io.github.dunwu.util.net;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class IPUtilTest {

    @Test
    public void stringAndInt() {

        assertThat(IpUtil.ipv4StringToInt("192.168.0.1")).isEqualTo(-1062731775);
        assertThat(IpUtil.ipv4StringToInt("192.168.0.2")).isEqualTo(-1062731774);

        assertThat(IpUtil.intToIpv4String(-1062731775)).isEqualTo("192.168.0.1");
        assertThat(IpUtil.intToIpv4String(-1062731774)).isEqualTo("192.168.0.2");
    }

    @Test
    public void inetAddress() {

        assertThat(IpUtil.fromInt(-1062731775).getHostAddress()).isEqualTo("192.168.0.1");
        assertThat(IpUtil.fromInt(-1062731774).getHostAddress()).isEqualTo("192.168.0.2");

        assertThat(IpUtil.fromIpString("192.168.0.1").getHostAddress()).isEqualTo("192.168.0.1");
        assertThat(IpUtil.fromIpString("192.168.0.2").getHostAddress()).isEqualTo("192.168.0.2");
        assertThat(IpUtil.fromIpv4String("192.168.0.1").getHostAddress()).isEqualTo("192.168.0.1");
        assertThat(IpUtil.fromIpv4String("192.168.0.2").getHostAddress()).isEqualTo("192.168.0.2");

        assertThat(IpUtil.toInt(IpUtil.fromIpString("192.168.0.1"))).isEqualTo(-1062731775);
    }
}
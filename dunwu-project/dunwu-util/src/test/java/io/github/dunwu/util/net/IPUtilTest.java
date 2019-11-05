package io.github.dunwu.util.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IPUtilTest {

	@Test
	public void isValidIp() {
		Assertions.assertTrue(IpUtil.isValidIpv4("192.168.0.1"));
		Assertions.assertTrue(IpUtil.isValidIpv4("192.168.0.2"));
		Assertions.assertFalse(IpUtil.isValidIpv4(""));
		Assertions.assertFalse(IpUtil.isValidIpv4("256.168.0.1"));

		Assertions.assertTrue(
				IpUtil.isValidIpv6("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
		Assertions.assertTrue(IpUtil.isValidIpv6("2001:db8:85a3:0:0:8A2E:0370:7334"));
		Assertions.assertTrue(IpUtil.isValidIpv6("0:0:0:0:0:0:0:0"));
		Assertions.assertFalse(
				IpUtil.isValidIpv6("02001:0db8:85a3:0000:0000:8a2e:0370:7334"));
	}

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

		assertThat(IpUtil.fromIpString("192.168.0.1").getHostAddress())
				.isEqualTo("192.168.0.1");
		assertThat(IpUtil.fromIpString("192.168.0.2").getHostAddress())
				.isEqualTo("192.168.0.2");
		assertThat(IpUtil.fromIpv4String("192.168.0.1").getHostAddress())
				.isEqualTo("192.168.0.1");
		assertThat(IpUtil.fromIpv4String("192.168.0.2").getHostAddress())
				.isEqualTo("192.168.0.2");

		assertThat(IpUtil.toInt(IpUtil.fromIpString("192.168.0.1")))
				.isEqualTo(-1062731775);
	}

}

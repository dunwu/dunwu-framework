package io.github.dunwu.tool.net;

import io.github.dunwu.tool.util.ValidatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

/**
 * NetUtil单元测试
 *
 * @author Looly
 */
public class NetUtilTest {

    @Test
    @Disabled
    public void getLocalhostStrTest() {
        String localhost = NetUtil.getLocalhostStr();
        Assertions.assertNotNull(localhost);
    }

    @Test
    @Disabled
    public void getLocalhostTest() {
        InetAddress localhost = NetUtil.getLocalhost();
        Assertions.assertNotNull(localhost);
    }

    @Test
    @Disabled
    public void getLocalMacAddressTest() {
        String macAddress = NetUtil.getLocalMacAddress();
        Assertions.assertNotNull(macAddress);

        // 验证MAC地址正确
        Assertions.assertTrue(ValidatorUtil.isMac(macAddress));
    }

    @Test
    public void longToIpTest() {
        String ipv4 = NetUtil.longToIpv4(2130706433L);
        Assertions.assertEquals("127.0.0.1", ipv4);
    }

    @Test
    public void ipToLongTest() {
        long ipLong = NetUtil.ipv4ToLong("127.0.0.1");
        Assertions.assertEquals(2130706433L, ipLong);
    }

}

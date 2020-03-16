package io.github.dunwu.util.net;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class NetUtilsTest {

    @Test
    public void localhost() {
        assertThat(NetUtils.getLocalHost()).isNotEqualTo("127.0.0.1");
        assertThat(NetUtils.getLocalAddress().getHostAddress()).isNotEqualTo("127.0.0.1");
    }

    @Test
    public void portDetect() throws IOException {
        int port = NetUtils.findRandomAvailablePort(20000, 20100);
        assertThat(port).isBetween(20000, 20100);
        System.out.println("random port:" + port);

        assertThat(NetUtils.isPortAvailable(port)).isTrue();

        int port2 = NetUtils.findAvailablePortFrom(port);
        assertThat(port2).isEqualTo(port);

        int port3 = NetUtils.findRandomAvailablePort();

        assertThat(port3).isBetween(NetUtils.PORT_RANGE_MIN, NetUtils.PORT_RANGE_MAX);
        System.out.println("random port:" + port3);

        // 尝试占住一个端口
        ServerSocket serverSocket = null;
        try {
            serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1,
                InetAddress.getByName("localhost"));

            assertThat(NetUtils.isPortAvailable(port)).isFalse();

            int port4 = NetUtils.findAvailablePortFrom(port);
            assertThat(port4).isEqualTo(port + 1);

            try {
                int port5 = NetUtils.findRandomAvailablePort(port, port);
                fail("should fail before");
            } catch (Throwable t) {
                assertThat(t).isInstanceOf(IllegalStateException.class);
            }
        } finally {
            IOUtil.close(serverSocket);
        }
    }

}

package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.support.BaseLockProviderIntegrationTest;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ZookeeperLockProviderIntegrationTest extends BaseLockProviderIntegrationTest {

    private TestingServer zkTestServer;

    private CuratorFramework client;

    private ZookeeperLockProvider zookeeperLockProvider;

    @Override
    protected LockProvider getLockProvider() {
        return zookeeperLockProvider;
    }

    @Override
    protected void assertUnlocked(String lockName) {
        try {
            assertThat(zookeeperLockProvider.isLocked(getNodePath(lockName))).isFalse();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void assertLocked(String lockName) {
        try {
            assertThat(zookeeperLockProvider.isLocked(getNodePath(lockName))).isTrue();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String getNodePath(String lockName) {
        return zookeeperLockProvider.getNodePath(lockName);
    }

    @BeforeEach
    public void startZookeeper() throws Exception {
        zkTestServer = new TestingServer();
        client = newClient();
        zookeeperLockProvider = new ZookeeperLockProvider(client);
    }

    private CuratorFramework newClient() {
        CuratorFramework client =
            CuratorFrameworkFactory.builder().namespace("MyApp").retryPolicy(new RetryOneTime(2000))
                .connectString(zkTestServer.getConnectString()).build();
        client.start();
        return client;
    }

    @AfterEach
    public void stopZookeeper() throws IOException {
        client.close();
        zkTestServer.stop();
    }

    @Test
    public void shouldNotOverwriteLockCreatedByPreviousVersion() throws Exception {
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(getNodePath(LOCK_NAME1));

        Optional<DistributedLock> lock1 = zookeeperLockProvider.lock(lockConfig(LOCK_NAME1));
        assertThat(lock1).isEmpty();

        client.delete().forPath(getNodePath(LOCK_NAME1));
        Optional<DistributedLock> lock2 = zookeeperLockProvider.lock(lockConfig(LOCK_NAME1));
        assertThat(lock2).isNotEmpty();
    }

}

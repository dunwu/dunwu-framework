/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.github.dunwu.dlock.provider;

import io.github.dunwu.dlock.core.DistributedLock;
import io.github.dunwu.dlock.core.LockProvider;
import io.github.dunwu.dlock.test.support.AbstractLockProviderIntegrationTest;
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

public class ZookeeperLockProviderIntegrationTest extends AbstractLockProviderIntegrationTest {

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
        CuratorFramework client = CuratorFrameworkFactory.builder().namespace("MyApp")
            .retryPolicy(new RetryOneTime(2000))
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

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
package io.github.dunwu.dlock.test.support;

import io.github.dunwu.dlock.core.DistributedLock;
import io.github.dunwu.dlock.core.LockConfiguration;
import io.github.dunwu.dlock.core.LockProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Increments counter from several threads coordinating using lock provided under test.
 */
public class FuzzTester {

    public static final int SHORT_ITERATION = 10;

    private static final int THREADS = 8;

    private static final int ITERATIONS = 100;

    private final LockProvider lockProvider;

    private final LockConfiguration config = new LockConfiguration("lock", Instant.now().plus(5, ChronoUnit.MINUTES));

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int counter;

    public FuzzTester(LockProvider lockProvider) {
        this.lockProvider = lockProvider;
    }

    public void doFuzzTest() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        int[] iterations = range(0, THREADS).map(i -> ITERATIONS).toArray();
        iterations[0] = SHORT_ITERATION; // short task to simulate MySql issues

        List<Callable<Void>> tasks =
            range(0, THREADS).mapToObj(i -> (Callable<Void>) () -> this.task(iterations[i])).collect(toList());
        waitForIt(executor.invokeAll(tasks));

        assertThat(counter).isEqualTo((THREADS - 1) * ITERATIONS + SHORT_ITERATION);
    }

    private void waitForIt(List<Future<Void>> futures) throws InterruptedException, ExecutionException {
        for (Future<Void> f : futures) {
            f.get();
        }
    }

    protected Void task(int iterations) {
        try {
            for (int i = 0; i < iterations; ) {
                Optional<DistributedLock> lock = lockProvider.lock(config);
                if (lock.isPresent()) {
                    int n = counter;
                    if (shouldLog()) logger.debug("action=getLock value={} i={}", n, i);
                    sleep();
                    if (shouldLog()) logger.debug("action=setCounter value={} i={}", n + 1, i);
                    counter = n + 1;
                    i++;
                    lock.get().unlock();
                }
            }
            logger.debug("action=finished");
            return null;
        } catch (RuntimeException e) {
            logger.error("Unexpected exception", e);
            throw e;
        }
    }

    protected boolean shouldLog() {
        return false;
    }

    private void sleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

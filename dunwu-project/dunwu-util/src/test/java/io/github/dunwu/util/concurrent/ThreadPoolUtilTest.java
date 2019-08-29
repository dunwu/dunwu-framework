package io.github.dunwu.util.concurrent;

import io.github.dunwu.test.log.LogbackListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ThreadPoolUtilTest {

    @Test
    void buildThreadFactory() {
        Runnable runnable = () -> {
            System.out.println("Thread name" + Thread.currentThread()
                                                     .getName());
        };

        // 测试name格式
        ThreadFactory threadFactory = ThreadPoolUtil.buildThreadFactory("thread1");
        Thread thread = threadFactory.newThread(runnable);
        assertThat(thread.getName()).isEqualTo("thread1-0");
        assertThat(thread.isDaemon()).isFalse();

        // 测试daemon属性设置
        threadFactory = ThreadPoolUtil.buildThreadFactory("thread2", true);
        Thread thread2 = threadFactory.newThread(runnable);
        assertThat(thread2.getName()).isEqualTo("thread2-0");
        assertThat(thread2.isDaemon()).isTrue();
    }

    @Test
    void gracefulShutdown() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger("test");
        LogbackListAppender appender = new LogbackListAppender();
        appender.addToLogger("test");

        // time enough to shutdown
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable task = new Task(logger, 200, 0);
        executorService.execute(task);
        ThreadPoolUtil.gracefulShutdown(executorService, 1000, TimeUnit.MILLISECONDS);
        assertThat(executorService.isTerminated()).isTrue();
        assertThat(appender.getFirstLog()).isNull();

        // time not enough to shutdown,call shutdownNow
        appender.clearLogs();
        executorService = Executors.newSingleThreadExecutor();
        task = new Task(logger, 1000, 0);
        executorService.execute(task);
        ThreadPoolUtil.gracefulShutdown(executorService, 500, TimeUnit.MILLISECONDS);
        assertThat(executorService.isTerminated()).isTrue();
        assertThat(appender.getFirstLog()
                           .getMessage()).isEqualTo("InterruptedException");

        // self thread interrupt while calling gracefulShutdown
        appender.clearLogs();

        final ExecutorService self = Executors.newSingleThreadExecutor();
        task = new Task(logger, 100000, 0);
        self.execute(task);

        final CountDownLatch lock = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            lock.countDown();
            ThreadPoolUtil.gracefulShutdown(self, 200000, TimeUnit.MILLISECONDS);
        });
        thread.start();
        lock.await();
        thread.interrupt();
        ThreadUtil.sleep(500);
        assertThat(appender.getFirstLog()
                           .getMessage()).isEqualTo("InterruptedException");

        ThreadPoolUtil.gracefulShutdown(null, 1000);
        ThreadPoolUtil.gracefulShutdown(null, 1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void wrapException() {
        ScheduledThreadPoolExecutor executor = ThreadPoolUtil.scheduledPool()
                                                             .build();
        ExceptionTask task = new ExceptionTask();
        executor.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);

        ThreadUtil.sleep(500);

        // 线程第一次跑就被中断
        assertThat(task.counter.get()).isEqualTo(1);
        ThreadPoolUtil.gracefulShutdown(executor, 1000);

        ////////
        executor = ThreadPoolUtil.scheduledPool()
                                 .build();
        ExceptionTask newTask = new ExceptionTask();
        Runnable wrapTask = ThreadUtil.safeRunnable(newTask);
        executor.scheduleAtFixedRate(wrapTask, 0, 100, TimeUnit.MILLISECONDS);

        ThreadUtil.sleep(500);
        assertThat(newTask.counter.get()).isGreaterThan(2);
        System.out.println("-------actual run:" + task.counter.get());
        ThreadPoolUtil.gracefulShutdown(executor, 1000);

    }

    @Test
    public void fixPool() {
        ThreadPoolExecutor singlePool = ThreadPoolUtil.fixedPool()
                                                      .build();
        assertThat(singlePool.getCorePoolSize()).isEqualTo(1);
        assertThat(singlePool.getMaximumPoolSize()).isEqualTo(1);
        assertThat(singlePool.getQueue()).isInstanceOf(LinkedBlockingQueue.class);
        singlePool.shutdown();

        ThreadPoolExecutor fixPoolWithUnlimitQueue = ThreadPoolUtil.fixedPool()
                                                                   .setPoolSize(10)
                                                                   .build();
        assertThat(fixPoolWithUnlimitQueue.getCorePoolSize()).isEqualTo(10);
        assertThat(fixPoolWithUnlimitQueue.getMaximumPoolSize()).isEqualTo(10);
        fixPoolWithUnlimitQueue.shutdown();

        ThreadPoolExecutor fixPoolWithlimitQueue = ThreadPoolUtil.fixedPool()
                                                                 .setPoolSize(10)
                                                                 .setQueueSize(100)
                                                                 .setThreadFactory(
                                                                     ThreadPoolUtil.buildThreadFactory("kaka"))
                                                                 .build();

        assertThat(fixPoolWithlimitQueue.getQueue()).isInstanceOf(ArrayBlockingQueue.class);
        Thread thread = fixPoolWithlimitQueue.getThreadFactory()
                                             .newThread(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                 }
                                             });
        assertThat(thread.getName()).startsWith("kaka");

        fixPoolWithlimitQueue.shutdown();

        ThreadPoolExecutor fixPoolWithNamePrefix = ThreadPoolUtil.fixedPool()
                                                                 .setPoolSize(10)
                                                                 .setThreadNamePrefix("fixPool")
                                                                 .build();
        Thread thread2 = fixPoolWithNamePrefix.getThreadFactory()
                                              .newThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                  }
                                              });
        assertThat(thread2.getName()).startsWith("fixPool");
        assertThat(thread2.isDaemon()).isFalse();
        fixPoolWithNamePrefix.shutdown();

        ThreadPoolExecutor fixPoolWithNamePrefixAndDaemon = ThreadPoolUtil.fixedPool()
                                                                          .setPoolSize(10)
                                                                          .setThreadNamePrefix("fixPoolDaemon")
                                                                          .setDaemon(true)
                                                                          .build();
        Thread thread3 = fixPoolWithNamePrefixAndDaemon.getThreadFactory()
                                                       .newThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                           }
                                                       });
        assertThat(thread3.getName()).startsWith("fixPoolDaemon");
        assertThat(thread3.isDaemon()).isTrue();
        fixPoolWithNamePrefixAndDaemon.shutdown();
    }

    @Test
    public void cachedPool() {
        ThreadPoolExecutor singlePool = ThreadPoolUtil.cachedPool()
                                                      .build();
        assertThat(singlePool.getCorePoolSize()).isEqualTo(0);
        assertThat(singlePool.getMaximumPoolSize()).isEqualTo(Integer.MAX_VALUE);
        assertThat(singlePool.getKeepAliveTime(TimeUnit.SECONDS)).isEqualTo(10);
        assertThat(singlePool.getQueue()).isInstanceOf(SynchronousQueue.class);
        singlePool.shutdown();

        ThreadPoolExecutor sizeablePool = ThreadPoolUtil.cachedPool()
                                                        .setMinSize(10)
                                                        .setMaxSize(100)
                                                        .setKeepAliveSecs(20)
                                                        .build();
        assertThat(sizeablePool.getCorePoolSize()).isEqualTo(10);
        assertThat(sizeablePool.getMaximumPoolSize()).isEqualTo(100);
        assertThat(sizeablePool.getKeepAliveTime(TimeUnit.SECONDS)).isEqualTo(20);
        sizeablePool.shutdown();

        ThreadPoolExecutor fixPoolWithNamePrefix = ThreadPoolUtil.cachedPool()
                                                                 .setThreadNamePrefix("cachedPool")
                                                                 .build();
        Thread thread = fixPoolWithNamePrefix.getThreadFactory()
                                             .newThread(new Runnable() {

                                                 @Override
                                                 public void run() {
                                                 }
                                             });
        assertThat(thread.getName()).startsWith("cachedPool");
        fixPoolWithNamePrefix.shutdown();
    }

    @Test
    public void scheduledPool() {
        ScheduledThreadPoolExecutor singlePool = ThreadPoolUtil.scheduledPool()
                                                               .build();
        assertThat(singlePool.getCorePoolSize()).isEqualTo(1);
        assertThat(singlePool.getMaximumPoolSize()).isEqualTo(Integer.MAX_VALUE);
        singlePool.shutdown();

        ScheduledThreadPoolExecutor sizeablePool = ThreadPoolUtil.scheduledPool()
                                                                 .setPoolSize(2)
                                                                 .build();
        assertThat(sizeablePool.getCorePoolSize()).isEqualTo(2);
        assertThat(sizeablePool.getMaximumPoolSize()).isEqualTo(Integer.MAX_VALUE);
        sizeablePool.shutdown();

        ThreadPoolExecutor fixPoolWithNamePrefix = ThreadPoolUtil.scheduledPool()
                                                                 .setThreadNamePrefix("scheduledPool")
                                                                 .build();
        Thread thread = fixPoolWithNamePrefix.getThreadFactory()
                                             .newThread(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                 }
                                             });
        assertThat(thread.getName()).startsWith("scheduledPool");
        fixPoolWithNamePrefix.shutdown();
    }

    @Test
    public void quequablePool() {
        ThreadPoolExecutor singlePool = ThreadPoolUtil.queuableCachedPool()
                                                      .build();
        assertThat(singlePool.getCorePoolSize()).isEqualTo(0);
        assertThat(singlePool.getMaximumPoolSize()).isEqualTo(Integer.MAX_VALUE);
        assertThat(singlePool.getKeepAliveTime(TimeUnit.SECONDS)).isEqualTo(10);
        assertThat(singlePool.getQueue()).isInstanceOf(ThreadPoolUtil.QueuableCachedThreadPool.ControllableQueue.class);
        singlePool.shutdown();

        ThreadPoolExecutor sizeablePool = ThreadPoolUtil.queuableCachedPool()
                                                        .setMinSize(10)
                                                        .setMaxSize(100)
                                                        .setKeepAliveSecs(20)
                                                        .build();
        assertThat(sizeablePool.getCorePoolSize()).isEqualTo(10);
        assertThat(sizeablePool.getMaximumPoolSize()).isEqualTo(100);
        assertThat(sizeablePool.getKeepAliveTime(TimeUnit.SECONDS)).isEqualTo(20);
        sizeablePool.shutdown();

        ThreadPoolExecutor fixPoolWithNamePrefix = ThreadPoolUtil.queuableCachedPool()
                                                                 .setThreadNamePrefix("queuableCachedPool")
                                                                 .build();
        Thread thread = fixPoolWithNamePrefix.getThreadFactory()
                                             .newThread(new Runnable() {

                                                 @Override
                                                 public void run() {
                                                 }
                                             });
        assertThat(thread.getName()).startsWith("queuableCachedPool");
        fixPoolWithNamePrefix.shutdown();
    }

    public static class LongRunTask implements Runnable {
        @Override
        public void run() {
            ThreadUtil.sleep(5, TimeUnit.SECONDS);
        }
    }

    @Test
    public void test() {
        ThreadPoolUtil.QueuableCachedThreadPool threadPool = null;
        try {
            threadPool = ThreadPoolUtil.queuableCachedPool()
                                       .setMinSize(0)
                                       .setMaxSize(10)
                                       .setQueueSize(10)
                                       .build();
            // 线程满
            for (int i = 0; i < 10; i++) {
                threadPool.submit(new LongRunTask());
            }

            assertThat(threadPool.getActiveCount()).isEqualTo(10);
            assertThat(threadPool.getQueue()
                                 .size()).isEqualTo(0);

            // queue 满
            for (int i = 0; i < 10; i++) {
                threadPool.submit(new LongRunTask());
            }
            assertThat(threadPool.getActiveCount()).isEqualTo(10);
            assertThat(threadPool.getQueue()
                                 .size()).isEqualTo(10);

            // 爆
            try {
                threadPool.submit(new LongRunTask());
                fail("should fail before");
            } catch (Throwable t) {
                assertThat(t).isInstanceOf(RejectedExecutionException.class);
            }

        } finally {
            ThreadPoolUtil.gracefulShutdown(threadPool, 1000);
        }
    }


    static class ExceptionTask implements Runnable {
        public AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void run() {
            counter.incrementAndGet();
            throw new RuntimeException("fail");
        }

    }


    static class Task implements Runnable {
        private final Logger logger;

        private int runTime = 0;

        private final int sleepTime;

        Task(Logger logger, int sleepTime, int runTime) {
            this.logger = logger;
            this.sleepTime = sleepTime;
            this.runTime = runTime;
        }

        @Override
        public void run() {
            System.out.println("start task");
            if (runTime > 0) {
                long start = System.currentTimeMillis();
                while ((System.currentTimeMillis() - start) < runTime) {
                }
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                logger.warn("InterruptedException");
            }
        }
    }
}

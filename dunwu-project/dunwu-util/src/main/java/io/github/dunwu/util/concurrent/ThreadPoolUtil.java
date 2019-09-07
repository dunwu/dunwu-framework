package io.github.dunwu.util.concurrent;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.dunwu.util.base.annotation.NotNull;
import io.github.dunwu.util.base.annotation.Nullable;
import org.apache.commons.lang3.Validate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具集
 * <p>
 * 1. 优雅关闭线程池(via guava)
 * <p>
 * 2. 创建可自定义线程名的ThreadFactory(via guava)
 * <p>
 * 3. 防止第三方Runnable未捕捉异常导致线程跑飞
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 */
public class ThreadPoolUtil {

	/**
	 * 按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法. 先使用shutdown,
	 * 停止接收新任务并尝试完成所有已存在任务.
	 * <p>
	 * 如果1/2超时时间后, 则调用shutdownNow,取消在workQueue中Pending的任务,并中断所有阻塞函数.
	 * <p>
	 * 如果1/2超时仍然超時，則強制退出. 另对在shutdown时线程本身被调用中断做了处理. 返回线程最后是否被中断.
	 * <p>
	 * 使用了Guava的工具类
	 *
	 * @see MoreExecutors#shutdownAndAwaitTermination(java.util.concurrent.ExecutorService,
	 * long, java.util.concurrent.TimeUnit)
	 */
	public static boolean gracefulShutdown(@Nullable ExecutorService threadPool,
			int shutdownTimeoutMills) {
		return threadPool == null || MoreExecutors.shutdownAndAwaitTermination(threadPool,
				shutdownTimeoutMills, TimeUnit.MILLISECONDS);
	}

	/**
	 * @see ThreadPoolUtil#gracefulShutdown
	 */
	public static boolean gracefulShutdown(@Nullable ExecutorService threadPool,
			int shutdownTimeout, TimeUnit timeUnit) {
		return threadPool == null || MoreExecutors.shutdownAndAwaitTermination(threadPool,
				shutdownTimeout, timeUnit);
	}

	/**
	 * 创建ThreadFactory，使得创建的线程有自己的名字而不是默认的"pool-x-thread-y" 使用了Guava的工具类
	 *
	 * @see ThreadFactoryBuilder#build()
	 */
	public static ThreadFactory buildThreadFactory(@NotNull String threadNamePrefix) {
		return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
	}

	/**
	 * 可设定是否daemon, daemon线程在主线程已执行完毕时, 不会阻塞应用不退出, 而非daemon线程则会阻塞.
	 *
	 * @see ThreadFactoryBuilder
	 */
	public static ThreadFactory buildThreadFactory(@NotNull String threadNamePrefix,
			@NotNull boolean daemon) {
		return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d")
				.setDaemon(daemon).build();
	}

	private static RejectedExecutionHandler defaultRejectHandler = new ThreadPoolExecutor.AbortPolicy();

	/**
	 * @see FixedThreadPoolBuilder
	 */
	public static FixedThreadPoolBuilder fixedPool() {
		return new FixedThreadPoolBuilder();
	}

	/**
	 * @see CachedThreadPoolBuilder
	 */
	public static CachedThreadPoolBuilder cachedPool() {
		return new CachedThreadPoolBuilder();
	}

	/**
	 * @see ScheduledThreadPoolBuilder
	 */
	public static ScheduledThreadPoolBuilder scheduledPool() {
		return new ScheduledThreadPoolBuilder();
	}

	/**
	 * @see QueuableCachedThreadPoolBuilder
	 */
	public static QueuableCachedThreadPoolBuilder queuableCachedPool() {
		return new QueuableCachedThreadPoolBuilder();
	}

	/**
	 * 创建FixedThreadPool.建议必须设置queueSize保证有界。 1. 任务提交时, 如果线程数还没达到poolSize即创建新线程并绑定任务
	 * (即poolSize次提交后线程总数必达到poolSize，不会重用之前的线程) poolSize默认为1，即singleThreadPool. 2.
	 * 第poolSize次任务提交后, 新增任务放入Queue中, Pool中的所有线程从Queue中take任务执行.
	 * Queue默认为无限长的LinkedBlockingQueue, 但建议设置queueSize换成有界的队列. 如果使用有界队列,
	 * 当队列满了之后,会调用RejectHandler进行处理, 默认为AbortPolicy，抛出RejectedExecutionException异常.
	 * 其他可选的Policy包括静默放弃当前任务(Discard)，放弃Queue里最老的任务(DisacardOldest)
	 * ，或由主线程来直接执行(CallerRuns). 3. 因为线程全部为core线程，所以不会在空闲时回收.
	 */
	public static class FixedThreadPoolBuilder {

		private int poolSize = 1;

		private int queueSize = -1;

		private ThreadFactory threadFactory;

		private String threadNamePrefix;

		private Boolean daemon;

		private RejectedExecutionHandler rejectHandler;

		/**
		 * Pool大小，默认为1，即singleThreadPool
		 */
		public FixedThreadPoolBuilder setPoolSize(int poolSize) {
			Validate.isTrue(poolSize >= 1);
			this.poolSize = poolSize;
			return this;
		}

		/**
		 * 不设置时默认为-1, 使用无限长的LinkedBlockingQueue. 为正数时使用ArrayBlockingQueue.
		 */
		public FixedThreadPoolBuilder setQueueSize(int queueSize) {
			this.queueSize = queueSize;
			return this;
		}

		/**
		 * 与threadNamePrefix互斥, 优先使用ThreadFactory
		 */
		public FixedThreadPoolBuilder setThreadFactory(ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
			return this;
		}

		/**
		 * 与ThreadFactory互斥, 优先使用ThreadFactory
		 */
		public FixedThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;
			return this;
		}

		/**
		 * 与threadFactory互斥, 优先使用ThreadFactory 默认为NULL，不进行设置，使用JDK的默认值.
		 */
		public FixedThreadPoolBuilder setDaemon(Boolean daemon) {
			this.daemon = daemon;
			return this;
		}

		public FixedThreadPoolBuilder setRejectHanlder(
				RejectedExecutionHandler rejectHandler) {
			this.rejectHandler = rejectHandler;
			return this;
		}

		public ThreadPoolExecutor build() {
			BlockingQueue<Runnable> queue = null;
			if (queueSize < 1) {
				queue = new LinkedBlockingQueue<Runnable>();
			}
			else {
				queue = new ArrayBlockingQueue<Runnable>(queueSize);
			}

			threadFactory = createThreadFactory(threadFactory, threadNamePrefix, daemon);

			if (rejectHandler == null) {
				rejectHandler = defaultRejectHandler;
			}

			return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS,
					queue, threadFactory, rejectHandler);
		}

	}

	/**
	 * 创建CachedThreadPool, maxSize建议设置 1. 任务提交时,
	 * 如果线程数还没达到minSize即创建新线程并绑定任务(即minSize次提交后线程总数必达到minSize, 不会重用之前的线程) minSize默认为0,
	 * 可设置保证有基本的线程处理请求不被回收. 2. 第minSize次任务提交后,
	 * 新增任务提交进SynchronousQueue后，如果没有空闲线程立刻处理，则会创建新的线程, 直到总线程数达到上限. maxSize默认为Integer.Max,
	 * 可以进行设置. 如果设置了maxSize, 当总线程数达到上限, 会调用RejectHandler进行处理, 默认为AbortPolicy,
	 * 抛出RejectedExecutionException异常.
	 * 其他可选的Policy包括静默放弃当前任务(Discard)，或由主线程来直接执行(CallerRuns). 3. minSize以上, maxSize以下的线程,
	 * 如果在keepAliveTime中都poll不到任务执行将会被结束掉, keeAliveTimeJDK默认为10秒.
	 * JDK默认值60秒太高，如高达1000线程时，要低于16QPS时才会开始回收线程, 因此改为默认10秒.
	 */
	public static class CachedThreadPoolBuilder {

		private int minSize = 0;

		private int maxSize = Integer.MAX_VALUE;

		private int keepAliveSecs = 10;

		private ThreadFactory threadFactory;

		private String threadNamePrefix;

		private Boolean daemon;

		private RejectedExecutionHandler rejectHandler;

		public CachedThreadPoolBuilder setMinSize(int minSize) {
			this.minSize = minSize;
			return this;
		}

		/**
		 * Max默认Integer.MAX_VALUE的，建议设置
		 */
		public CachedThreadPoolBuilder setMaxSize(int maxSize) {
			this.maxSize = maxSize;
			return this;
		}

		/**
		 * JDK默认值60秒太高，如高达1000线程时，要低于16QPS时才会开始回收线程, 因此改为默认10秒.
		 */
		public CachedThreadPoolBuilder setKeepAliveSecs(int keepAliveSecs) {
			this.keepAliveSecs = keepAliveSecs;
			return this;
		}

		/**
		 * 与threadNamePrefix互斥, 优先使用ThreadFactory
		 */
		public CachedThreadPoolBuilder setThreadFactory(ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
			return this;
		}

		/**
		 * 与threadFactory互斥, 优先使用ThreadFactory
		 */
		public CachedThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;
			return this;
		}

		/**
		 * 与threadFactory互斥, 优先使用ThreadFactory 默认为NULL，不进行设置，使用JDK的默认值.
		 */
		public CachedThreadPoolBuilder setDaemon(Boolean daemon) {
			this.daemon = daemon;
			return this;
		}

		public CachedThreadPoolBuilder setRejectHanlder(
				RejectedExecutionHandler rejectHandler) {
			this.rejectHandler = rejectHandler;
			return this;
		}

		public ThreadPoolExecutor build() {

			threadFactory = createThreadFactory(threadFactory, threadNamePrefix, daemon);

			if (rejectHandler == null) {
				rejectHandler = defaultRejectHandler;
			}

			return new ThreadPoolExecutor(minSize, maxSize, keepAliveSecs,
					TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory,
					rejectHandler);
		}

	}

	/*
	 * 创建ScheduledPool.
	 */
	public static class ScheduledThreadPoolBuilder {

		private int poolSize = 1;

		private ThreadFactory threadFactory;

		private String threadNamePrefix;

		/**
		 * 默认为1
		 */
		public ScheduledThreadPoolBuilder setPoolSize(int poolSize) {
			this.poolSize = poolSize;
			return this;
		}

		/**
		 * 与threadNamePrefix互斥, 优先使用ThreadFactory
		 */
		public ScheduledThreadPoolBuilder setThreadFactory(ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
			return this;
		}

		public ScheduledThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;
			return this;
		}

		public ScheduledThreadPoolExecutor build() {
			threadFactory = createThreadFactory(threadFactory, threadNamePrefix,
					Boolean.TRUE);
			return new ScheduledThreadPoolExecutor(poolSize, threadFactory);
		}

	}

	/**
	 * 从Tomcat移植过来的可扩展可用Queue缓存任务的ThreadPool
	 *
	 * @see QueuableCachedThreadPool
	 */
	public static class QueuableCachedThreadPoolBuilder {

		private int minSize = 0;

		private int maxSize = Integer.MAX_VALUE;

		private int keepAliveSecs = 10;

		private int queueSize = 100;

		private ThreadFactory threadFactory;

		private String threadNamePrefix;

		private Boolean daemon;

		private RejectedExecutionHandler rejectHandler;

		public QueuableCachedThreadPoolBuilder setMinSize(int minSize) {
			this.minSize = minSize;
			return this;
		}

		public QueuableCachedThreadPoolBuilder setMaxSize(int maxSize) {
			this.maxSize = maxSize;
			return this;
		}

		/**
		 * LinkedQueue长度, 默认100
		 */
		public QueuableCachedThreadPoolBuilder setQueueSize(int queueSize) {
			this.queueSize = queueSize;
			return this;
		}

		public QueuableCachedThreadPoolBuilder setKeepAliveSecs(int keepAliveSecs) {
			this.keepAliveSecs = keepAliveSecs;
			return this;
		}

		/**
		 * 与threadNamePrefix互斥, 优先使用ThreadFactory
		 */
		public QueuableCachedThreadPoolBuilder setThreadFactory(
				ThreadFactory threadFactory) {
			this.threadFactory = threadFactory;
			return this;
		}

		/**
		 * 与threadFactory互斥, 优先使用ThreadFactory
		 */
		public QueuableCachedThreadPoolBuilder setThreadNamePrefix(
				String threadNamePrefix) {
			this.threadNamePrefix = threadNamePrefix;
			return this;
		}

		/**
		 * 与threadFactory互斥, 优先使用ThreadFactory 默认为NULL，不进行设置，使用JDK的默认值.
		 */
		public QueuableCachedThreadPoolBuilder setDaemon(Boolean daemon) {
			this.daemon = daemon;
			return this;
		}

		public QueuableCachedThreadPoolBuilder setRejectHanlder(
				RejectedExecutionHandler rejectHandler) {
			this.rejectHandler = rejectHandler;
			return this;
		}

		public QueuableCachedThreadPool build() {

			threadFactory = createThreadFactory(threadFactory, threadNamePrefix, daemon);

			if (rejectHandler == null) {
				rejectHandler = defaultRejectHandler;
			}

			return new QueuableCachedThreadPool(minSize, maxSize, keepAliveSecs,
					TimeUnit.SECONDS,
					new QueuableCachedThreadPool.ControllableQueue(queueSize),
					threadFactory, rejectHandler);
		}

	}

	/**
	 * From Tomcat 8.5.6, 传统的FixedThreadPool有Queue但线程数量不变，而CachedThreadPool线程数可变但没有Queue
	 * Tomcat的线程池，通过控制TaskQueue，线程数，但线程数到达最大时会进入Queue中. 代码从Tomcat复制，主要修改包括： 1.
	 * 删除定期重启线程避免内存泄漏的功能， 2. TaskQueue中可能3次有锁的读取线程数量，改为只读取1次，这把锁也是这个实现里的唯一遗憾了。
	 * https://github
	 * .com/apache/tomcat/blob/trunk/java/org/apache/tomcat/util/threads/ThreadPoolExecutor.java
	 */
	public static class QueuableCachedThreadPool
			extends java.util.concurrent.ThreadPoolExecutor {

		/**
		 * The number of tasks submitted but not yet finished. This includes tasks in the
		 * queue and tasks that have been handed to a worker thread but the latter did not
		 * start executing the task yet. This number is always greater or equal to
		 * {@link #getActiveCount()}.
		 */
		private final AtomicInteger submittedCount = new AtomicInteger(0);

		public QueuableCachedThreadPool(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				QueuableCachedThreadPool.ControllableQueue workQueue,
				ThreadFactory threadFactory, RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
					threadFactory, handler);
			workQueue.setParent(this);
			prestartAllCoreThreads(); // NOSOANR
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			submittedCount.decrementAndGet();
		}

		public int getSubmittedCount() {
			return submittedCount.get();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void execute(Runnable command) {
			execute(command, 0, TimeUnit.MILLISECONDS);
		}

		/**
		 * Executes the given command at some time in the future. The command may execute
		 * in a new thread, in a pooled thread, or in the calling thread, at the
		 * discretion of the <tt>Executor</tt> implementation. If no threads are
		 * available, it will be added to the work queue. If the work queue is full, the
		 * system will wait for the specified time and it throw a
		 * RejectedExecutionException if the queue is still full after that.
		 * @param command the runnable task
		 * @param timeout A timeout for the completion of the task
		 * @param unit The timeout time unit
		 * @throws RejectedExecutionException if this task cannot be accepted for
		 * execution - the queue is full
		 * @throws NullPointerException if command or unit is null
		 */
		public void execute(Runnable command, long timeout, TimeUnit unit) {
			submittedCount.incrementAndGet();
			try {
				super.execute(command);
			}
			catch (RejectedExecutionException rx) { // NOSONAR
				// not to re-throw this exception because this is only used to find out
				// whether the pool is full, not
				// for a
				// exception purpose
				final QueuableCachedThreadPool.ControllableQueue queue = (QueuableCachedThreadPool.ControllableQueue) super.getQueue();
				try {
					if (!queue.force(command, timeout, unit)) {
						submittedCount.decrementAndGet();
						throw new RejectedExecutionException("Queue capacity is full.");
					}
				}
				catch (InterruptedException x) {
					submittedCount.decrementAndGet();
					throw new RejectedExecutionException(x);
				}
			}
		}

		/**
		 * https://github.com/apache/tomcat/blob/trunk/java/org/apache/tomcat/util/threads/TaskQueue.java
		 */
		protected static class ControllableQueue extends LinkedBlockingQueue<Runnable> {

			private static final long serialVersionUID = 5044057462066661171L;

			private transient volatile QueuableCachedThreadPool parent = null;

			public ControllableQueue(int capacity) {
				super(capacity);
			}

			public void setParent(QueuableCachedThreadPool tp) {
				parent = tp;
			}

			public boolean force(Runnable o) {
				if (parent.isShutdown()) {
					throw new RejectedExecutionException(
							"Executor not running, can't force a command into the queue");
				}
				return super.offer(o); // forces the item onto the queue, to be used if
										// the task is rejected
			}

			public boolean force(Runnable o, long timeout, TimeUnit unit)
					throws InterruptedException {
				if (parent.isShutdown()) {
					throw new RejectedExecutionException(
							"Executor not running, can't force a command into the queue");
				}
				return super.offer(o, timeout, unit); // forces the item onto the queue,
														// to be used if the task is
														// rejected
			}

			@Override
			public boolean offer(Runnable o) {
				// springside: threadPool.getPoolSize() 是个有锁的操作，所以尽量减少

				int currentPoolSize = parent.getPoolSize();

				// we are maxed out on threads, simply queue the object
				if (currentPoolSize >= parent.getMaximumPoolSize()) {
					return super.offer(o);
				}
				// we have idle threads, just add it to the queue
				if (parent.getSubmittedCount() < currentPoolSize) {
					return super.offer(o);
				}
				// if we have less threads than maximum force creation of a new thread
				if (currentPoolSize < parent.getMaximumPoolSize()) {
					return false;
				}
				// if we reached here, we need to add it to the queue
				return super.offer(o);
			}

		}

	}

	/**
	 * 优先使用threadFactory，否则如果threadNamePrefix不为空则使用自建ThreadFactory，否则使用defaultThreadFactory
	 */
	private static ThreadFactory createThreadFactory(ThreadFactory threadFactory,
			String threadNamePrefix, Boolean daemon) {
		if (threadFactory != null) {
			return threadFactory;
		}

		if (threadNamePrefix != null) {
			if (daemon != null) {
				return ThreadPoolUtil.buildThreadFactory(threadNamePrefix, daemon);
			}
			else {
				return ThreadPoolUtil.buildThreadFactory(threadNamePrefix);
			}
		}

		return Executors.defaultThreadFactory();
	}

}

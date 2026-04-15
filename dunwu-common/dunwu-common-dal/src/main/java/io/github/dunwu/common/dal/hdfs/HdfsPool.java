package io.github.dunwu.common.dal.hdfs;

import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;

/**
 * HDFS 连接池
 * <p>
 * 基于 Apache Commons Pool2 的 {@link GenericObjectPool} 实现，用于管理 {@link FileSystem} 对象的生命周期。
 * 需要配合 {@link HdfsFactory} 使用。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
public class HdfsPool extends GenericObjectPool<FileSystem> {

    /**
     * 构造 HDFS 连接池
     *
     * @param factory HDFS 对象工厂
     */
    public HdfsPool(final HdfsFactory factory) {
        super(factory);
    }

    /**
     * 构造 HDFS 连接池
     *
     * @param factory HDFS 对象工厂
     * @param config  连接池配置
     */
    public HdfsPool(final HdfsFactory factory, final GenericObjectPoolConfig<FileSystem> config) {
        super(factory, config);
    }

    /**
     * 构造 HDFS 连接池
     *
     * @param factory        HDFS 对象工厂
     * @param config         连接池配置
     * @param abandonedConfig 废弃对象处理配置
     */
    public HdfsPool(final HdfsFactory factory, final GenericObjectPoolConfig<FileSystem> config,
        final AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

}

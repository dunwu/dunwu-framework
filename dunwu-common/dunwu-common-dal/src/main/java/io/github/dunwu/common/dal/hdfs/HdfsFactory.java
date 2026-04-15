package io.github.dunwu.common.dal.hdfs;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HDFS 对象工厂类
 * <p>
 * 配合 {@link HdfsPool} 使用，负责创建、验证和销毁 {@link FileSystem} 对象。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
public class HdfsFactory extends BasePooledObjectFactory<FileSystem> {

    /**
     * HDFS 配置信息
     */
    private final HdfsConfig hdfsConfig;

    /**
     * Hadoop 配置对象
     */
    private final Configuration configuration;

    /**
     * 构造 HDFS 工厂
     *
     * @param hdfsConfig HDFS 连接配置
     */
    public HdfsFactory(HdfsConfig hdfsConfig) {
        this.hdfsConfig = hdfsConfig;
        this.configuration = new Configuration();
        // this.configuration.set("fs.hdfs.impl.disable.cache", "true");
        this.configuration.set(HdfsConfig.HDFS_DEFAULT_URL_KEY, hdfsConfig.getUrl());
    }

    /**
     * 创建新的 {@link FileSystem} 实例
     *
     * @return 新创建的 FileSystem 对象
     * @throws IOException        如果连接 HDFS 失败
     * @throws URISyntaxException 如果 URI 格式不正确
     */
    @Override
    public FileSystem create() throws IOException, URISyntaxException, InterruptedException {
        return FileSystem.get(new URI(this.hdfsConfig.getUrl()), configuration, this.hdfsConfig.getUser());
    }

    /**
     * 将对象包装为池化对象
     *
     * @param obj 需要包装的 FileSystem 对象
     * @return 包装后的 {@link PooledObject}
     */
    @Override
    public PooledObject<FileSystem> wrap(FileSystem obj) {
        return new DefaultPooledObject<>(obj);
    }

    /**
     * 验证池化对象是否有效
     *
     * @param pooledObject 待验证的池化对象
     * @return 如果对象有效返回 true，否则返回 false
     */
    @Override
    public boolean validateObject(final PooledObject<FileSystem> pooledObject) {
        if (pooledObject == null) {
            return false;
        }

        FileSystem obj = pooledObject.getObject();
        return obj != null;
    }

    /**
     * 销毁池化对象，关闭 FileSystem 连接
     *
     * @param pooledObject 待销毁的池化对象
     * @throws IOException 如果关闭连接时发生错误
     */
    @Override
    public void destroyObject(PooledObject<FileSystem> pooledObject) throws IOException {
        if (pooledObject == null) {
            return;
        }

        FileSystem fileSystem = pooledObject.getObject();
        try {
            fileSystem.close();
        } finally {
            fileSystem = null;
        }
    }

    /**
     * 获取 HDFS 配置信息
     *
     * @return HDFS 配置对象
     */
    public HdfsConfig getHdfsConfig() {
        return hdfsConfig;
    }

    /**
     * 获取 Hadoop 配置对象
     *
     * @return Hadoop Configuration 对象
     */
    public Configuration getConfiguration() {
        return configuration;
    }

}

package io.github.dunwu.data.hdfs;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HDFS 工厂类，需要配合 {@link HdfsPool} 使用
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
public class HdfsFactory extends BasePooledObjectFactory<FileSystem> {

    private final HdfsConfig hdfsConfig;
    private final Configuration configuration;

    public HdfsFactory(HdfsConfig hdfsConfig) {
        this.hdfsConfig = hdfsConfig;
        this.configuration = new Configuration();
        // this.configuration.set("fs.hdfs.impl.disable.cache", "true");
        this.configuration.set(HdfsConfig.HDFS_DEFAULT_URL_KEY, hdfsConfig.getUrl());
    }

    @Override
    public FileSystem create() throws InterruptedException, IOException, URISyntaxException {
        return FileSystem.get(new URI(this.hdfsConfig.getUrl()), configuration, this.hdfsConfig.getUser());
    }

    @Override
    public PooledObject<FileSystem> wrap(FileSystem obj) {
        return new DefaultPooledObject<>(obj);
    }

    @Override
    public boolean validateObject(final PooledObject<FileSystem> pooledObject) {
        if (pooledObject == null) {
            return false;
        }

        FileSystem obj = pooledObject.getObject();
        return obj != null;
    }

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

    public HdfsConfig getHdfsConfig() {
        return hdfsConfig;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}

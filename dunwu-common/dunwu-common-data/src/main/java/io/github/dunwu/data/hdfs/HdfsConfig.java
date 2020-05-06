package io.github.dunwu.data.hdfs;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;

import java.io.Serializable;

/**
 * Hdfs 配置选项
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
@Data
@ToString
@Accessors(chain = true)
public class HdfsConfig implements Serializable {

    public static final String HDFS_DEFAULT_URL_KEY = "fs.defaultFS";

    public static final String HDFS_DEFAULT_URL = "hdfs://localhost:9000";

    public static final String HDFS_DEFAULT_USER_NAME = "hdfs";

    private static final long serialVersionUID = -5434086838792181903L;

    protected final Pool pool = new Pool();

    protected String url = HDFS_DEFAULT_URL;

    protected String user = HDFS_DEFAULT_USER_NAME;

    public static class Pool extends GenericObjectPoolConfig<FileSystem> {}

}

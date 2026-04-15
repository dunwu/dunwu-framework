package io.github.dunwu.common.dal.hdfs;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;

import java.io.Serializable;

/**
 * HDFS 连接池配置类
 * <p>
 * 包含 HDFS 连接 URL、用户名以及连接池的相关配置。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
@Data
@ToString
@Accessors(chain = true)
public class HdfsConfig implements Serializable {

    /**
     * HDFS 默认文件系统配置键
     */
    public static final String HDFS_DEFAULT_URL_KEY = "fs.defaultFS";

    /**
     * HDFS 默认地址
     */
    public static final String HDFS_DEFAULT_URL = "hdfs://localhost:9000";

    /**
     * HDFS 默认用户名
     */
    public static final String HDFS_DEFAULT_USER_NAME = "hdfs";

    private static final long serialVersionUID = -5434086838792181903L;

    /**
     * 连接池配置对象
     */
    protected final Pool pool = new Pool();

    /**
     * HDFS 连接地址
     */
    protected String url = HDFS_DEFAULT_URL;

    /**
     * HDFS 操作用户名
     */
    protected String user = HDFS_DEFAULT_USER_NAME;

    /**
     * HDFS 连接池配置
     * <p>
     * 继承自 {@link GenericObjectPoolConfig}，用于配置连接池的大小、超时时间等参数。
     */
    public static class Pool extends GenericObjectPoolConfig<FileSystem> {
    }

}

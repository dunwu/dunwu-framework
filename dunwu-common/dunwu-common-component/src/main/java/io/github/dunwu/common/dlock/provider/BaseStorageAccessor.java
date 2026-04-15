package io.github.dunwu.common.dlock.provider;

import io.github.dunwu.common.dlock.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储访问器抽象基类，提供日志和主机名获取等通用能力
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public abstract class BaseStorageAccessor implements StorageAccessor {

    /** 日志记录器 */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取当前主机名
     *
     * @return 主机名
     */
    protected String getHostname() {
        return Utils.getHostname();
    }

}

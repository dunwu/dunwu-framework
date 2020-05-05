package io.github.dunwu.filesystem.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件服务配置类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/tobato/FastDFS_Client">FastDFS_Client</a>
 * @since 2019-07-24
 */
@Configuration
@EnableConfigurationProperties({ FileSystemProperties.class })
public class FileSystemAutoConfiguration {

}

// package io.github.dunwu.autoconfigure.hdfs;
//
// import io.github.dunwu.tool.data.hdfs.HdfsConfig;
// import io.github.dunwu.tool.data.hdfs.HdfsFactory;
// import io.github.dunwu.tool.data.hdfs.HdfsPool;
// import io.github.dunwu.tool.data.hdfs.HdfsUtil;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// /**
//  * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
//  * @since 2020-02-27
//  */
// @Configuration
// @ConditionalOnClass({ HdfsConfig.class, HdfsFactory.class, HdfsPool.class })
// @ConditionalOnProperty(value = "dunwu.hdfs.enabled", havingValue = "true")
// @EnableConfigurationProperties(HdfsProperties.class)
// public class DunwuHdfsConfiguration {
//
//     @Bean
//     public HdfsFactory hdfsFactory(HdfsProperties properties) {
//         return new HdfsFactory(properties);
//     }
//
//     @Bean
//     public HdfsPool hdfsPool(HdfsFactory hdfsFactory, HdfsProperties properties) {
//         return new HdfsPool(hdfsFactory, properties.getPool());
//     }
//
//     @Bean
//     public HdfsUtil hdfsUtil(HdfsPool hdfsPool) {
//         return new HdfsUtil(hdfsPool);
//     }
//
// }

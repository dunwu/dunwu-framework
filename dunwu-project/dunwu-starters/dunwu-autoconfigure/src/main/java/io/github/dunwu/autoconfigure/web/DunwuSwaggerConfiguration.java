package io.github.dunwu.autoconfigure.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Configuration
@ConditionalOnClass({ ApiInfo.class, Docket.class })
@ConditionalOnProperty(name = "dunwu.swagger.enabled", havingValue = "true")
@EnableSwagger2
@EnableConfigurationProperties({ DunwuSwaggerProperties.class })
public class DunwuSwaggerConfiguration {

    private final DunwuSwaggerProperties properties;

    public DunwuSwaggerConfiguration(DunwuSwaggerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo(properties));
    }

    private ApiInfo apiInfo(DunwuSwaggerProperties swagger) {
        return new ApiInfo(
            swagger.getTitle(),
            swagger.getDescription(),
            swagger.getVersion(),
            null,
            new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()),
            swagger.getLicense(), swagger.getLicenseUrl(), Collections.emptyList());
    }

}

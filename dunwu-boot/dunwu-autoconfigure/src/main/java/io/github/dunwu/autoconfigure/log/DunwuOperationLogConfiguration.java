package io.github.dunwu.autoconfigure.log;

import io.github.dunwu.tool.web.log.aspect.OperationLogAspect;
import io.github.dunwu.tool.web.log.service.FunctionService;
import io.github.dunwu.tool.web.log.service.LogRecordService;
import io.github.dunwu.tool.web.log.service.ParseFunction;
import io.github.dunwu.tool.web.log.service.impl.DefaultFunctionServiceImpl;
import io.github.dunwu.tool.web.log.service.impl.DefaultLogRecordServiceImpl;
import io.github.dunwu.tool.web.log.service.impl.DefaultParseFunction;
import io.github.dunwu.tool.web.log.support.ParseFunctionFactory;
import io.github.dunwu.tool.web.log.support.SpElValueParser;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * 操作日志配置类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Slf4j
@ConditionalOnClass({ OperationLogAspect.class })
@ConditionalOnProperty(value = "dunwu.operation.log.enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties(DunwuOperationLogProperties.class)
public class DunwuOperationLogConfiguration {

    @Bean
    public OperationLogAspect operationLogAspect(LogRecordService logRecordService, SecurityService securityService,
        SpElValueParser spElValueParser, DunwuOperationLogProperties properties) {
        return new OperationLogAspect(properties.getAppName(), logRecordService, securityService, spElValueParser);
    }

    @Bean
    @ConditionalOnMissingBean(LogRecordService.class)
    @Role(BeanDefinition.ROLE_APPLICATION)
    public LogRecordService logRecordService() {
        return new DefaultLogRecordServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ParseFunction.class)
    public ParseFunction parseFunction() {
        return new DefaultParseFunction();
    }

    @Bean
    public ParseFunctionFactory parseFunctionFactory(List<ParseFunction> parseFunctions) {
        return new ParseFunctionFactory(parseFunctions);
    }

    @Bean
    @ConditionalOnMissingBean(FunctionService.class)
    public FunctionService functionService(ParseFunctionFactory parseFunctionFactory) {
        return new DefaultFunctionServiceImpl(parseFunctionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(SpElValueParser.class)
    public SpElValueParser spElValueParser(FunctionService functionService) {
        return new SpElValueParser(functionService);
    }

}

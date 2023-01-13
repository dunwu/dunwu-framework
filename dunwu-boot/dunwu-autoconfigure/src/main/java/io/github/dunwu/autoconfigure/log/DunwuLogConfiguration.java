package io.github.dunwu.autoconfigure.log;

import io.github.dunwu.tool.web.log.aop.DataLockLogAop;
import io.github.dunwu.tool.web.log.aop.OperationLogAop;
import io.github.dunwu.tool.web.log.service.DataLockLogService;
import io.github.dunwu.tool.web.log.service.FunctionService;
import io.github.dunwu.tool.web.log.service.OperationLogService;
import io.github.dunwu.tool.web.log.service.ParseFunction;
import io.github.dunwu.tool.web.log.service.TableColumnConfigService;
import io.github.dunwu.tool.web.log.service.impl.DefaultDataLockLogServiceImpl;
import io.github.dunwu.tool.web.log.service.impl.DefaultFunctionServiceImpl;
import io.github.dunwu.tool.web.log.service.impl.DefaultOperationLogServiceImpl;
import io.github.dunwu.tool.web.log.service.impl.DefaultParseFunction;
import io.github.dunwu.tool.web.log.service.impl.DefaultTableColumnConfigServiceImpl;
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
import org.springframework.context.annotation.Role;

import java.util.List;

/**
 * 日志配置类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Slf4j
@ConditionalOnClass({ OperationLogAop.class, DataLockLogAop.class })
@EnableConfigurationProperties(DunwuLogProperties.class)
public abstract class DunwuLogConfiguration {

    @Bean
    @ConditionalOnProperty(value = "dunwu.log.operation.enabled", havingValue = "true")
    public OperationLogAop operationLogAspect(OperationLogService operationLogService, SecurityService securityService,
        SpElValueParser spElValueParser, DunwuLogProperties properties) {
        return new OperationLogAop(properties.getAppName(), operationLogService, securityService, spElValueParser);
    }

    @Bean
    @ConditionalOnMissingBean(OperationLogService.class)
    @Role(BeanDefinition.ROLE_APPLICATION)
    public OperationLogService logRecordService() {
        return new DefaultOperationLogServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(DataLockLogService.class)
    public DataLockLogService dataLockLogService() {
        return new DefaultDataLockLogServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(TableColumnConfigService.class)
    public TableColumnConfigService tableColumnConfigService() {
        return new DefaultTableColumnConfigServiceImpl();
    }

    @Bean
    @ConditionalOnProperty(value = "dunwu.log.lock.enabled", havingValue = "true")
    public DataLockLogAop dataLockLogAop(DataLockLogService dataLockLogService,
        TableColumnConfigService tableColumnConfigService, SecurityService securityService,
        SpElValueParser spElValueParser) {
        return new DataLockLogAop(dataLockLogService, tableColumnConfigService, securityService, spElValueParser);
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

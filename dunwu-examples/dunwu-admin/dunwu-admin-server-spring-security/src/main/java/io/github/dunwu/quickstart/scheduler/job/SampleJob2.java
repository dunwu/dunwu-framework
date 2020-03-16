package io.github.dunwu.quickstart.scheduler.job;

import com.alibaba.fastjson.JSON;
import io.github.dunwu.common.annotation.JobHandler;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.ResultUtils;
import io.github.dunwu.quickstart.scheduler.handler.IJobHandler;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-04
 */
@JobHandler("sampleJob2")
public class SampleJob2 implements IJobHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResult execute(String params) {
        Person person = JSON.parseObject(params, Person.class);
        log.info("person = {}", person);
        return ResultUtils.successBaseResult();
    }

    @Data
    @ToString
    private static class Person {

        private String name;

        private Integer age;

        private String sex;

    }

}

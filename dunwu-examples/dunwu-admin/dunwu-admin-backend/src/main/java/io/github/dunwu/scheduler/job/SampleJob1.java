package io.github.dunwu.scheduler.job;

import io.github.dunwu.scheduler.handler.IJobHandler;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-04
 */
@JobHandler("sampleJob1")
public class SampleJob1 implements IJobHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseResult execute(String params) {
        log.info("params = {}", params);
        return BaseResult.success();
    }

}

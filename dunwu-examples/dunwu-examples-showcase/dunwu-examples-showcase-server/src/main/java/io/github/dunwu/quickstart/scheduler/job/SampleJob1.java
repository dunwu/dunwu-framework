package io.github.dunwu.quickstart.scheduler.job;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.annotation.JobHandler;
import io.github.dunwu.quickstart.scheduler.handler.IJobHandler;
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
        return ResultUtil.successBaseResult();
    }
}

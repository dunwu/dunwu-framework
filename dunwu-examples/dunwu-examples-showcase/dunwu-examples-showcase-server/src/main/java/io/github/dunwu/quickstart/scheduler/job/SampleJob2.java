package io.github.dunwu.quickstart.scheduler.job;

import io.github.dunwu.annotation.JobHandler;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.scheduler.handler.IJobHandler;
import io.github.dunwu.util.parser.JsonMapper;
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
		JsonMapper jsonMapper = JsonMapper.nonNullMapper();
		Person person = jsonMapper.fromJson(params, Person.class);
		log.info("person = {}", person.toString());
		return ResultUtil.successBaseResult();
	}

	@Data
	@ToString
	static class Person {

		private String name;

		private Integer age;

		private String sex;

	}

}

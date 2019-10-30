package io.github.dunwu.quickstart.scheduler.handler;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.quickstart.scheduler.constant.SchedulerConstant;
import io.github.dunwu.web.util.SpringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ExecuteMethodJobHandler extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void executeInternal(JobExecutionContext context) {

		JobDataMap jobDataMap = context.getMergedJobDataMap();
		String beanName = (String) jobDataMap.get(SchedulerConstant.BEAN_NAME);
		String beanType = (String) jobDataMap.get(SchedulerConstant.BEAN_TYPE);
		String methodName = (String) jobDataMap.get(SchedulerConstant.METHOD_NAME);
		String methodParams = (String) jobDataMap.get(SchedulerConstant.METHOD_PARAMS);

		try {
			Object bean;
			Class<?> clazz;
			if (StringUtils.isNotBlank(beanName)) {
				bean = SpringUtil.getBean(beanName);
				clazz = bean.getClass();
			} else {
				clazz = Class.forName(beanType);
				bean = SpringUtil.getBean(clazz);
			}
			Method method = clazz.getMethod(methodName, String.class);
			if (method == null) {
				log.error("class = {} 中未找到 {} 方法", clazz.getName(), methodName);
			}

			JobDetail jobDetail = context.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			BaseResult baseResult = (BaseResult) method.invoke(bean, methodParams);
			if (baseResult != null && baseResult.getSuccess()) {
				log.info("jobGroup = {}, jobName = {} execute success.",
					jobKey.getGroup(), jobKey.getName());
			} else {
				log.info("jobGroup = {}, jobName = {} execute failed.", jobKey.getGroup(),
					jobKey.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package io.github.dunwu.quickstart.scheduler.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
public enum TriggerTypeEnum {

	SIMPLE(0), CRON(1);

	@EnumValue
	@JsonValue
	private Integer key;

	TriggerTypeEnum(Integer key) {
		this.key = key;
	}

	public Integer getKey() {
		return this.key;
	}
}

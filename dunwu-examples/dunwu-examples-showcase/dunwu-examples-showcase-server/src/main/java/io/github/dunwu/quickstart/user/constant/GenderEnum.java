package io.github.dunwu.quickstart.user.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.github.dunwu.constant.IIntegerStringEnum;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
public enum GenderEnum implements IIntegerStringEnum {

	MALE(1, "男"), FEMALE(2, "女");

	GenderEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 标记数据库存的值是code
	 */
	@EnumValue
	private final int key;

	private final String value;

	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}

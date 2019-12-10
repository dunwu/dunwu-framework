package io.github.dunwu.bean;

public enum GenderEnum implements IIntegerStringEnum {

	MALE(0, "男"),
	FEMALE(1, "女");

	/**
	 * 标记数据库存的值是code
	 */
	private final int key;

	private final String value;

	GenderEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public String getValue() {
		return this.value;
	}
}

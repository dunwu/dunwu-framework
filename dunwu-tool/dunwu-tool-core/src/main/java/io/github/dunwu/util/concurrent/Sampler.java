package io.github.dunwu.util.concurrent;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;

/**
 * 采样器 移植 Twitter Common, 优化使用ThreadLocalRandom https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/util/Sampler.java
 */
public class Sampler {

	private static final Double ALWAYS = Double.valueOf(100);

	private static final Double NEVER = Double.valueOf(0);

	private double threshold;

	protected Sampler() {
	}

	/**
	 * @param selectPercent 采样率，在0-100 之间，可以有小数位
	 */
	protected Sampler(double selectPercent) {
		Validate.isTrue((selectPercent >= 0) && (selectPercent <= 100),
			"Invalid selectPercent value: " + selectPercent);

		this.threshold = selectPercent / 100;
	}

	/**
	 * 优化的创建函数，如果为0或100时，返回更直接的采样器
	 */
	public static Sampler create(Double selectPercent) {
		if (ALWAYS.equals(selectPercent)) {
			return new AlwaysSampler();
		} else if (NEVER.equals(selectPercent)) {
			return new NeverSampler();
		} else {
			return new Sampler(selectPercent);
		}
	}

	/**
	 * 判断当前请求是否命中采样
	 */
	public boolean select() {
		return RandomUtils.nextDouble() < threshold;
	}

	/**
	 * 采样率为100时，总是返回true
	 */
	public static class AlwaysSampler extends Sampler {

		@Override
		public boolean select() {
			return true;
		}

	}

	/**
	 * 采样率为0时，总是返回false
	 */
	public static class NeverSampler extends Sampler {

		@Override
		public boolean select() {
			return false;
		}

	}

}

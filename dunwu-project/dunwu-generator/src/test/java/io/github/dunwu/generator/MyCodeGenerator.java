package io.github.dunwu.generator;

/**
 * 代码生成器
 * <p>
 * （1）如果使用默认代码生成器，可以在 conf/mybatis.properties 中配置 ParamKeyEnum 的 key 来控制代码生成
 * <p>
 * （2）如果需要定制，可以覆写 DefaultCodeGenerator 中的方法来订制更细粒度的配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see DefaultCodeGenerator
 * @see CodeGeneratorProperties.ParamKeyEnum
 * @since 2019-07-19
 */
public class MyCodeGenerator extends DefaultCodeGenerator {

	public static void main(String[] args) {
		new MyCodeGenerator().generate();
	}

}

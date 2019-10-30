package io.github.dunwu.util.io;

import io.github.dunwu.util.io.constant.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AnsiSystem 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/10/30
 */
class AnsiSystemTest {

	@Test
	@DisplayName("遍历所有默认支持的 ANSI 彩色并打印")
	void printAllColors() {
		for (Color bgColor : Color.values()) {
			for (Color color : Color.values()) {
				String message = "字体颜色：" + color.getDesc() + "，背景颜色：" + bgColor.getDesc();
				AnsiSystem.AnsiConfig config = new AnsiSystem.AnsiConfig();
				config.setColor(color);
				config.setBgColor(bgColor);
				AnsiSystem system = new AnsiSystem(config);
				system.println(message);
			}
		}
	}

	@Test
	@DisplayName("特殊显示字体")
	void testSgr() {
		AnsiSystem.AnsiConfig config = new AnsiSystem.AnsiConfig();
		config.setBold(true);
		config.setItalic(true);
		config.setUnderline(true);
		config.setColor(Color.BLACK);
		config.setBgColor(Color.BLUE);
		AnsiSystem system = new AnsiSystem(config);
		system.println("特殊显示字体");
	}

	@Test
	@DisplayName("预置控制输出形式")
	void test() {
		AnsiSystem.RED.println("HELLO WORLD");
		AnsiSystem.GREEN.println("HELLO WORLD");
		AnsiSystem.YELLOW.println("HELLO WORLD");
		AnsiSystem.BLUE.println("HELLO WORLD");
		AnsiSystem.MAGENTA.println("HELLO WORLD");
		AnsiSystem.CYAN.println("HELLO WORLD");
		AnsiSystem.WHITE.println("HELLO WORLD");
	}

}

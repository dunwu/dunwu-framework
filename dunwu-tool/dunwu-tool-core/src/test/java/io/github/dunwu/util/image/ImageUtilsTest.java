package io.github.dunwu.util.image;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see ImageUtils
 * @since 2019-04-13
 */
@DisplayName("图片工具测试")
class ImageUtilsTest {

	static final String[] INPUT_FILE = { "D:\\Temp\\logo.png", "D:\\Temp\\logo2.png" };

	static final String[] OUTPUT_FILE = { "D:\\Temp\\new-logo.png",
		"D:\\Temp\\new-logo2.png" };

	@Test
	@Disabled("展示如何输出图片")
	void toFile() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setWidth(50).setHeight(50);
		try {
			ImageUtils.toFile(INPUT_FILE[0], OUTPUT_FILE[0], params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Disabled("展示如何输出图片")
	void toFile2() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setWidth(50).setHeight(50);
		try {
			ImageUtils.toFile(new File(INPUT_FILE[0]), new File(OUTPUT_FILE[0]), params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Disabled("展示如何输出图片")
	void toFile3() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setWidth(50).setHeight(50);
		try {
			InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
			ImageUtils.toFile(is, new File(OUTPUT_FILE[0]), params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Disabled("展示如何输出图片")
	void toFile4() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setWidth(50).setHeight(50);
		try {
			BufferedImage input = ImageIO.read(new File(INPUT_FILE[0]));
			ImageUtils.toFile(input, new File(OUTPUT_FILE[0]), params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Disabled("展示如何输出图片")
	void toFiles() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setWidth(50).setHeight(50);

		List<File> input = new ArrayList<>();
		for (String item : INPUT_FILE) {
			input.add(new File(item));
		}

		List<File> output = new ArrayList<>();
		for (String item : OUTPUT_FILE) {
			output.add(new File(item));
		}

		try {
			ImageUtils.toFiles(input, output, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void toOutputStream() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setRotate(90.0);
		try {
			OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
			ImageUtils.toOutputStream(INPUT_FILE[0], os, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void toOutputStream2() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setRotate(90.0);
		try {
			OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
			ImageUtils.toOutputStream(new File(INPUT_FILE[0]), os, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void toOutputStream3() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setRotate(90.0);
		try {
			InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
			OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
			ImageUtils.toOutputStream(is, os, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void toOutputStream4() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setRotate(90.0);
		try {
			InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
			OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
			BufferedImage image = ImageIO.read(is);
			ImageUtils.toOutputStream(image, os, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void toOutputStreams() {
		ImageUtils.ImageParam params = new ImageUtils.ImageParam();
		params.setRotate(90.0);
		try {
			List<InputStream> input = new ArrayList<>();
			for (String item : INPUT_FILE) {
				InputStream is = new FileInputStream(item);
				input.add(is);
			}

			List<OutputStream> output = new ArrayList<>();
			for (String item : INPUT_FILE) {
				OutputStream os = new FileOutputStream(item);
				output.add(os);
			}

			ImageUtils.toOutputStreams(input, output, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

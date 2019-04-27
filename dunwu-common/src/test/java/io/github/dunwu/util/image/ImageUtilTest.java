package io.github.dunwu.util.image;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see ImageUtil
 * @since 2019-04-13
 */
@DisplayName("图片工具测试")
class ImageUtilTest {

    static final String[] INPUT_FILE = {"D:\\Temp\\logo.png", "D:\\Temp\\logo2.png"};
    static final String[] OUTPUT_FILE = {"D:\\Temp\\new-logo.png", "D:\\Temp\\new-logo2.png"};

    @Test
    @Disabled("展示如何输出图片")
    void toFile() {
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setWidth(50).setHeight(50);
        try {
            ImageUtil.toFile(INPUT_FILE[0], OUTPUT_FILE[0], params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled("展示如何输出图片")
    void toFile2() {
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setWidth(50).setHeight(50);
        try {
            ImageUtil.toFile(new File(INPUT_FILE[0]), new File(OUTPUT_FILE[0]), params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled("展示如何输出图片")
    void toFile3() {
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setWidth(50).setHeight(50);
        try {
            InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
            ImageUtil.toFile(is, new File(OUTPUT_FILE[0]), params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled("展示如何输出图片")
    void toFile4() {
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setWidth(50).setHeight(50);
        try {
            BufferedImage input = ImageIO.read(new File(INPUT_FILE[0]));
            ImageUtil.toFile(input, new File(OUTPUT_FILE[0]), params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled("展示如何输出图片")
    void toFiles() {
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
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
            ImageUtil.toFiles(input, output, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream(){
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setRotate(90.0);
        try {
            OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
            ImageUtil.toOutputStream(INPUT_FILE[0], os, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream2(){
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setRotate(90.0);
        try {
            OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
            ImageUtil.toOutputStream(new File(INPUT_FILE[0]), os, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream3(){
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setRotate(90.0);
        try {
            InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
            OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
            ImageUtil.toOutputStream(is, os, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream4(){
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
        params.setRotate(90.0);
        try {
            InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
            OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
            BufferedImage image = ImageIO.read(is);
            ImageUtil.toOutputStream(image, os, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStreams(){
        ImageUtil.ImageParam params = new ImageUtil.ImageParam();
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

            ImageUtil.toOutputStreams(input, output, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

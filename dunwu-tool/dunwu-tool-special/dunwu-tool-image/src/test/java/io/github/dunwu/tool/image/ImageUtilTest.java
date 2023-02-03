package io.github.dunwu.tool.image;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see ImageUtil
 * @since 2019-04-13
 */
@DisplayName("图片工具测试")
class ImageUtilTest {

    static final String[] INPUT_FILE = { "images/logo.png", "images/logo2.png" };

    static final String[] OUTPUT_FILE = { "images/logo-new.png", "images/logo2-new.png" };

    public String getAbsolutePath(String path) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL oldUrl = loader.getResource(path);
        if (oldUrl == null) {
            return null;
        }
        return oldUrl.getPath();
    }

    @Test
    @DisplayName("裁剪图片尺寸，并生成新文件")
    void toFile() {
        ImageProperties properties = ImageProperties.builder().width(300).height(300).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getAbsolutePath(OUTPUT_FILE[0]);
            ImageUtil.toFile(oldPath, newPath, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("裁剪图片尺寸，并生成新文件")
    void toFile2() {
        ImageProperties properties = ImageProperties.builder().width(50).height(50).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getAbsolutePath(OUTPUT_FILE[0]);
            ImageUtil.toFile(new File(oldPath), new File(newPath), properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("裁剪图片尺寸，并生成新文件")
    void toFile3() {
        ImageProperties properties = ImageProperties.builder().width(50).height(50).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getAbsolutePath(OUTPUT_FILE[0]);
            BufferedInputStream is = FileUtil.getInputStream(oldPath);
            ImageUtil.toFile(is, new File(newPath), properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("裁剪图片尺寸，并生成新文件")
    void toFile4() {
        ImageProperties properties = ImageProperties.builder().width(50).height(50).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getAbsolutePath(OUTPUT_FILE[0]);
            BufferedImage input = ImageIO.read(new File(oldPath));
            ImageUtil.toFile(input, new File(newPath), properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream() {
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getAbsolutePath(OUTPUT_FILE[0]);
            OutputStream os = new FileOutputStream(new File(newPath));
            ImageUtil.toOutputStream(oldPath, os, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream2() {
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getAbsolutePath(OUTPUT_FILE[0]);
            OutputStream os = new FileOutputStream(new File(newPath));
            ImageUtil.toOutputStream(new File(oldPath), os, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream3() {
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
            OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
            ImageUtil.toOutputStream(is, os, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStream4() {
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            InputStream is = new FileInputStream(new File(INPUT_FILE[0]));
            OutputStream os = new FileOutputStream(new File(OUTPUT_FILE[0]));
            BufferedImage image = ImageIO.read(is);
            ImageUtil.toOutputStream(image, os, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toOutputStreams() {
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
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

            ImageUtil.toOutputStreams(input, output, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String oldPath = getAbsolutePath(INPUT_FILE[0]);
        String newPath = getAbsolutePath(OUTPUT_FILE[0]);

        try {
            BufferedImage image = ImageIO.read(FileUtil.getInputStream(oldPath));
            image.getWidth();
            ImageProperties.Region source = new ImageProperties.Region(83, 0, 106, 106);
            ImageProperties properties =
                ImageProperties.builder().source(source).width(106).height(106).keepAspectRatio(true).build();
            ImageUtil.toFile(oldPath, newPath, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

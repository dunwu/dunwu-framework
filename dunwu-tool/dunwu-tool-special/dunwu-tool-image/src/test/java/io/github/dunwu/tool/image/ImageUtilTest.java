package io.github.dunwu.tool.image;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.io.FileUtil;
import org.assertj.core.api.Assertions;
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
import javax.imageio.ImageIO;

/**
 * ImageUtil 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see ImageUtil
 * @since 2019-04-13
 */
@DisplayName("图片工具测试")
class ImageUtilTest {

    static final String[] INPUT_FILE = { "images/logo.png", "images/logo2.png" };

    public String getAbsolutePath(String path) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        URL oldUrl = loader.getResource(path);
        if (oldUrl == null) {
            return null;
        }
        return oldUrl.getPath();
    }

    public String getNewPath(String oldPath) {
        if (StrUtil.isBlank(oldPath)) {
            return null;
        }
        File file = new File(oldPath);
        if (file.exists()) {
            String dir = file.getParent();
            return dir + File.separator + IdUtil.fastSimpleUUID() + FileUtil.getExtension(oldPath, true);
        }
        return null;
    }

    @Test
    void toFile() {
        File newFile = null;
        ImageProperties properties = ImageProperties.builder().width(300).height(300).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            newFile = ImageUtil.toFile(oldPath, newPath, properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toFile2() {
        File newFile = null;
        ImageProperties properties = ImageProperties.builder().width(50).height(50).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            newFile = ImageUtil.toFile(new File(oldPath), new File(newPath), properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toFile3() {
        File newFile = null;
        BufferedInputStream is = null;
        ImageProperties properties = ImageProperties.builder().width(50).height(50).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            is = FileUtil.getInputStream(oldPath);
            newFile = ImageUtil.toFile(is, new File(newPath), properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(is);
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toFile4() {
        File newFile = null;
        ImageProperties properties = ImageProperties.builder().width(50).height(50).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            BufferedImage bufferedImage = ImageIO.read(new File(oldPath));
            newFile = ImageUtil.toFile(bufferedImage, new File(newPath), properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toFile5() {
        File newFile = null;
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            BufferedImage image = ImageIO.read(FileUtil.getInputStream(oldPath));
            image.getWidth();
            ImageProperties.Region source = new ImageProperties.Region(83, 0, 106, 106);
            ImageProperties properties =
                ImageProperties.builder().source(source).width(106).height(106).keepAspectRatio(true).build();
            newFile = ImageUtil.toFile(oldPath, newPath, properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toOutputStream() {
        File newFile = null;
        OutputStream os = null;
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            newFile = new File(newPath);
            os = new FileOutputStream(newFile);
            ImageUtil.toOutputStream(oldPath, os, properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(os);
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toOutputStream2() {
        File newFile = null;
        OutputStream os = null;
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            newFile = new File(newPath);
            os = new FileOutputStream(newFile);
            ImageUtil.toOutputStream(new File(oldPath), os, properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(os);
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toOutputStream3() {
        File newFile = null;
        InputStream is = null;
        OutputStream os = null;
        ImageProperties properties = ImageProperties.builder().rotate(90.0).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            newFile = new File(newPath);
            is = new FileInputStream(new File(oldPath));
            os = new FileOutputStream(newFile);
            ImageUtil.toOutputStream(is, os, properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(os);
            IoUtil.close(is);
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    void toOutputStream4() {
        File newFile = null;
        InputStream is = null;
        OutputStream os = null;
        ImageProperties properties = ImageProperties.builder().rotate(90.0).format(ImageType.jpeg).build();
        try {
            String oldPath = getAbsolutePath(INPUT_FILE[0]);
            String newPath = getNewPath(oldPath);
            newFile = new File(newPath);
            is = new FileInputStream(new File(oldPath));
            os = new FileOutputStream(newFile);
            BufferedImage image = ImageIO.read(is);
            ImageUtil.toOutputStream(image, os, properties);
            Assertions.assertThat(newFile).isNotNull();
            Assertions.assertThat(newFile.exists()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(os);
            IoUtil.close(is);
            if (newFile != null) {
                FileUtil.del(newFile);
            }
        }
    }

    @Test
    public void getImageInfo() throws IOException {

        String url2 = "https://image.tingmall.com/station/000/384-JPG-600X600-STATION.jpg";
        ImageInfo imageInfo2 = ImageUtil.getImageInfo(url2);
        System.out.println(JSONUtil.toJsonPrettyStr(imageInfo2));

        String url3 = "https://p6.itc.cn/q_70/images03/20201111/85aacdd1091747319084f04f9e744646.gif";
        ImageInfo imageInfo3 = ImageUtil.getImageInfo(url3);
        System.out.println(JSONUtil.toJsonPrettyStr(imageInfo3));
    }

}

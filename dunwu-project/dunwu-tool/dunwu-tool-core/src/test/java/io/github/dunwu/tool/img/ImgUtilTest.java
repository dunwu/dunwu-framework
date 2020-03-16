package io.github.dunwu.tool.img;

import io.github.dunwu.tool.io.FileUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImgUtilTest {

    @Test
    @Disabled
    public void scaleTest() {
        ImgUtil.scale(FileUtil.file("e:/pic/test.jpg"), FileUtil.file("e:/pic/test_result.jpg"), 0.8f);
    }

    @Test
    @Disabled
    public void scalePngTest() {
        ImgUtil.scale(FileUtil.file("f:/test/test.png"), FileUtil.file("f:/test/test_result.png"), 0.5f);
    }

    @Test
    @Disabled
    public void scaleByWidthAndHeightTest() {
        ImgUtil.scale(FileUtil.file("f:/test/aaa.jpg"), FileUtil.file("f:/test/aaa_result.jpg"), 100, 400, Color.BLUE);
    }

    @Test
    @Disabled
    public void cutTest() {
        ImgUtil.cut(FileUtil.file("d:/face.jpg"), FileUtil.file("d:/face_result.jpg"),
            new Rectangle(200, 200, 100, 100));
    }

    @Test
    @Disabled
    public void rotateTest() throws IOException {
        Image image = ImgUtil.rotate(ImageIO.read(FileUtil.file("e:/pic/366466.jpg")), 180);
        ImgUtil.write(image, FileUtil.file("e:/pic/result.png"));
    }

    @Test
    @Disabled
    public void flipTest() throws IOException {
        ImgUtil.flip(FileUtil.file("d:/logo.png"), FileUtil.file("d:/result.png"));
    }

    @Test
    @Disabled
    public void pressImgTest() {
        ImgUtil.pressImage(FileUtil.file("d:/picTest/1.jpg"), FileUtil.file("d:/picTest/dest.jpg"),
            ImgUtil.read(FileUtil.file("d:/picTest/1432613.jpg")), 0, 0, 0.1f);
    }

    @Test
    @Disabled
    public void pressTextTest() {
        ImgUtil.pressText(//
            FileUtil.file("e:/pic/face.jpg"), //
            FileUtil.file("e:/pic/test2_result.png"), //
            "版权所有", Color.WHITE, //
            new Font("黑体", Font.BOLD, 100), //
            0, //
            0, //
            0.8f);
    }

    @Test
    @Disabled
    public void sliceByRowsAndColsTest() {
        ImgUtil.sliceByRowsAndCols(FileUtil.file("e:/pic/1.png"), FileUtil.file("e:/pic/dest"), 10, 10);
    }

    @Test
    @Disabled
    public void convertTest() {
        ImgUtil.convert(FileUtil.file("e:/test2.png"), FileUtil.file("e:/test2Convert.jpg"));
    }

    @Test
    @Disabled
    public void writeTest() {
        ImgUtil.write(ImgUtil.read("e:/test2.png"), FileUtil.file("e:/test2Write.jpg"));
    }

    @Test
    @Disabled
    public void compressTest() {
        ImgUtil.compress(FileUtil.file("e:/pic/1111.png"), FileUtil.file("e:/pic/1111_target.jpg"), 0.8f);
    }

    @Test
    @Disabled
    public void copyTest() {
        BufferedImage image = ImgUtil.copyImage(ImgUtil.read("f:/pic/test.png"), BufferedImage.TYPE_INT_RGB);
        ImgUtil.write(image, FileUtil.file("f:/pic/test_dest.jpg"));
    }

}

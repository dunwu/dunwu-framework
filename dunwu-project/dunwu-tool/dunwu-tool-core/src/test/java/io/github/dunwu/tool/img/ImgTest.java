package io.github.dunwu.tool.img;

import io.github.dunwu.tool.io.FileUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ImgTest {

    @Test
    @Disabled
    public void cutTest1() {
        Img.from(FileUtil.file("e:/pic/face.jpg")).cut(0, 0, 200).write(FileUtil.file("e:/pic/face_radis.png"));
    }

    @Test
    @Disabled
    public void compressTest() {
        Img.from(FileUtil.file("f:/test/4347273249269e3fb272341acc42d4e.jpg"))
            .setQuality(0.8)
            .write(FileUtil.file("f:/test/test_dest.jpg"));
    }

    @Test
    @Disabled
    public void roundTest() {
        Img.from(FileUtil.file("e:/pic/face.jpg")).round(0.5).write(FileUtil.file("e:/pic/face_round.png"));
    }

}

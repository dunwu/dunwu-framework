package io.github.dunwu.tool.util;

import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * {@link ZipUtil}单元测试
 *
 * @author Looly
 */
public class ZipUtilTest {

    @Test
    @Disabled
    public void zipDirTest() {
        ZipUtil.zip(new File("e:/picTest/picSubTest"));
    }

    @Test
    @Disabled
    public void unzipTest() {
        File unzip = ZipUtil.unzip("f:/test/apache-maven-3.6.2.zip", "f:\\test");
        Console.log(unzip);
    }

    @Test
    @Disabled
    public void unzipTest2() {
        File unzip = ZipUtil.unzip("f:/test/各种资源.zip", "f:/test/各种资源", CharsetUtil.CHARSET_GBK);
        Console.log(unzip);
    }

    @Test
    @Disabled
    public void unzipFromStreamTest() {
        File unzip = ZipUtil.unzip(FileUtil.getInputStream("e:/test/antlr.zip"), FileUtil.file("e:/test/"),
            CharsetUtil.CHARSET_UTF_8);
        Console.log(unzip);
    }

    @Test
    @Disabled
    public void unzipChineseTest() {
        ZipUtil.unzip("d:/测试.zip");
    }

    @Test
    @Disabled
    public void unzipFileBytesTest() {
        byte[] fileBytes = ZipUtil.unzipFileBytes(FileUtil.file("e:/02 电力相关设备及服务2-241-.zip"), CharsetUtil.CHARSET_GBK,
            "images/CE-EP-HY-MH01-ES-0001.jpg");
        Assertions.assertNotNull(fileBytes);
    }

    @Test
    public void gzipTest() {
        String data = "我是一个需要压缩的很长很长的字符串";
        byte[] bytes = StringUtil.utf8Bytes(data);
        byte[] gzip = ZipUtil.gzip(bytes);

        //保证gzip长度正常
        Assertions.assertEquals(68, gzip.length);

        byte[] unGzip = ZipUtil.unGzip(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringUtil.utf8Str(unGzip));
    }

    @Test
    public void zlibTest() {
        String data = "我是一个需要压缩的很长很长的字符串";
        byte[] bytes = StringUtil.utf8Bytes(data);
        byte[] gzip = ZipUtil.zlib(bytes, 0);

        //保证zlib长度正常
        Assertions.assertEquals(62, gzip.length);
        byte[] unGzip = ZipUtil.unZlib(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringUtil.utf8Str(unGzip));

        gzip = ZipUtil.zlib(bytes, 9);
        //保证zlib长度正常
        Assertions.assertEquals(56, gzip.length);
        byte[] unGzip2 = ZipUtil.unZlib(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringUtil.utf8Str(unGzip2));
    }

}

package io.github.dunwu.tool.io;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileReader;

import java.io.File;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 读取文件所有数据<br> 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param filePath 文件路径
     * @return 字节码
     * @throws IORuntimeException IO异常
     * @since 3.2.0
     */
    public static byte[] readBytes(String filePath) throws IORuntimeException {
        return readBytes(file(filePath));
    }

    /**
     * 读取文件所有数据<br> 文件的长度不能超过Integer.MAX_VALUE
     *
     * @param file 文件
     * @return 字节码
     * @throws IORuntimeException IO异常
     */
    public static byte[] readBytes(File file) throws IORuntimeException {
        return FileReader.create(file).readBytes();
    }

}

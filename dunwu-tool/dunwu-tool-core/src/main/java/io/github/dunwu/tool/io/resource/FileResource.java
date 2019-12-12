package io.github.dunwu.tool.io.resource;

import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.util.StringUtil;
import io.github.dunwu.tool.util.URLUtil;

import java.io.File;
import java.nio.file.Path;

/**
 * 文件资源访问对象
 *
 * @author looly
 */
public class FileResource extends UrlResource {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------- Constructor start

    /**
     * 构造
     *
     * @param path 文件
     * @since 4.4.1
     */
    public FileResource(Path path) {
        this(path.toFile());
    }

    /**
     * 构造
     *
     * @param file 文件
     */
    public FileResource(File file) {
        this(file, file.getName());
    }

    /**
     * 构造
     *
     * @param file     文件
     * @param fileName 文件名，如果为null获取文件本身的文件名
     */
    public FileResource(File file, String fileName) {
        super(URLUtil.getURL(file), StringUtil.isBlank(fileName) ? file.getName() : fileName);
    }

    /**
     * 构造
     *
     * @param path 文件绝对路径或相对ClassPath路径，但是这个路径不能指向一个jar包中的文件
     */
    public FileResource(String path) {
        this(FileUtil.file(path));
    }
    // ----------------------------------------------------------------------- Constructor end
}

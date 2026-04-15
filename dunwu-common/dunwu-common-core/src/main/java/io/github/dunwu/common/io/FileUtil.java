package io.github.dunwu.common.io;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.constant.ResultCode;
import io.github.dunwu.common.exception.DefaultException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件工具类，扩展 Hutool 的 {@link cn.hutool.core.io.FileUtil}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 系统临时目录
     */
    public static final String SYS_TEMP_DIR = System.getProperty("java.io.tmpdir") + File.separator;
    /**
     * 定义GB的计算常量
     */
    public static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    public static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    public static final int KB = 1024;
    /**
     * 格式化小数
     */
    public static final DecimalFormat DF = new DecimalFormat("0.00");

    public static final String TEXT_SPLIT_CHAR = ",";

    /**
     * 获取文件的扩展类型
     *
     * @param filename 文件名
     * @return 返回文件类型，形式如：jpg、txt、png
     */
    public static String getExtension(String filename) {
        return getExtension(filename, false);
    }

    /**
     * 获取文件的扩展类型
     *
     * @param filename 文件名
     * @param withDot  是否包含 .
     * @return 返回文件类型，形式如：.jpg、.txt、.png
     */
    public static String getExtension(String filename, boolean withDot) {
        if (StrUtil.isNotBlank(filename)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                if (withDot) {
                    return filename.substring(dot);
                } else {
                    return filename.substring(dot + 1);
                }
            }
        }
        return filename;
    }

    /**
     * 获取网络资源文件后缀
     *
     * @param url 网络资源地址
     * @return 返回文件类型，形式如：jpg、txt、png
     */
    public static String getExtensionFromUrl(String url) throws DefaultException {
        return getExtensionFromUrl(url, false);
    }

    /**
     * 获取网络资源文件后缀
     *
     * @param url     网络资源地址
     * @param withDot 是否包含 .
     * @return 返回文件类型，形式如：.jpg、.txt、.png
     */
    public static String getExtensionFromUrl(String url, boolean withDot) throws DefaultException {

        if (StrUtil.isBlank(url)) {
            throw new DefaultException(ResultCode.PARAM_ERROR, "url 不能为空！");
        }

        String realUrl;
        int end = url.lastIndexOf("?");
        if (end != -1) {
            realUrl = url.substring(0, end);
        } else {
            realUrl = url;
        }

        return getExtension(realUrl, withDot);
    }

    /**
     * 文件大小格式化转换
     *
     * @param size 文件大小（字节）
     * @return 格式化后的文件大小字符串
     */
    public static String getFormatSize(long size) {
        String resultSize;
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float)GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float)MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float)KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

    /**
     * 根据 InputStream 获取 File 对象
     *
     * @param is   InputStream 对象
     * @param name 文件名
     * @return 文件对象
     * @throws FileNotFoundException 文件未找到异常
     */
    static File toFile(InputStream is, String name) throws FileNotFoundException {
        File file = new File(SYS_TEMP_DIR + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        IoUtil.copy(is, os);
        IoUtil.close(os);
        IoUtil.close(is);
        return file;
    }

    /**
     * 获取指定路径下所有特定类型的文件
     *
     * @param path        根目录
     * @param ext         文件类型后缀名
     * @param ignoredDirs 忽略的目录
     * @return 子文件列表
     */
    public static List<File> getFiles(String path, String ext, String... ignoredDirs) {
        List<File> files = new ArrayList<>();
        getChildrenFiles(path, ignoredDirs, ext, files);
        if (CollectionUtil.isNotEmpty(files)) {
            return files.stream().distinct().collect(Collectors.toList());
        }
        return files;
    }

    private static void getChildrenFiles(String path, String[] blackList, String ext, List<File> resultFiles) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (ArrayUtil.isNotEmpty(files)) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        if (StrUtil.containsAny(f.getName(), blackList)) {
                            continue;
                        }
                        getChildrenFiles(f.getAbsolutePath(), blackList, ext, resultFiles);
                    } else {
                        String extensionName = getExtension(f.getName());
                        if (ext.equalsIgnoreCase(extensionName)) {
                            resultFiles.add(f);
                        }
                    }
                }
            }
        } else {
            String extensionName = getExtension(file.getName());
            if (ext.equalsIgnoreCase(extensionName)) {
                resultFiles.add(file);
            }
        }
    }

    /**
     * 将二进制文件转换为文本文件
     *
     * @param srcBinaryFile  源二进制文件路径
     * @param targetTextFile 目标文本文件路径
     */
    public static void binaryToText(String srcBinaryFile, String targetTextFile) {

        if (!FileUtil.exist(srcBinaryFile)) {
            System.err.println("待转换的二进制文件不存在！");
            return;
        }

        BufferedInputStream input = null;
        try {
            input = FileUtil.getInputStream(srcBinaryFile);

            StringBuilder builder = new StringBuilder();
            int i = 0;
            while ((i = input.read()) != -1) {
                builder.append(i).append(TEXT_SPLIT_CHAR);
            }
            String encoder = builder.toString();
            if (StrUtil.isNotBlank(encoder)) {
                FileUtil.writeUtf8String(encoder, targetTextFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(input);
        }
    }

    /**
     * 将文本文件转换为二进制文件
     *
     * @param srcTextFile      源文本文件路径
     * @param targetBinaryFile 目标二进制文件路径
     */
    public static void textToBinary(String srcTextFile, String targetBinaryFile) {

        if (!FileUtil.exist(srcTextFile)) {
            System.err.println("待转换的文本文件不存在！");
            return;
        }

        BufferedReader reader = null;
        BufferedOutputStream output = null;

        try {
            reader = FileUtil.getReader(srcTextFile, StandardCharsets.UTF_8);
            output = FileUtil.getOutputStream(targetBinaryFile);

            String[] lines = reader.readLine().split(TEXT_SPLIT_CHAR);
            for (int j = 0; j < lines.length; j++) {
                output.write(Integer.parseInt(lines[j]));
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(output);
            IoUtil.close(reader);
        }
    }

    /**
     * 获取合法的文件路径，校验文件后缀是否在允许范围内
     *
     * @param dirPath  目录路径
     * @param fileName 文件名
     * @return 合法的文件路径
     * @throws IOException            IO 异常
     * @throws IllegalAccessException 文件后缀不合法时抛出
     */
    public static String getValidPath(String dirPath, String fileName) throws IOException, IllegalAccessException {

        StringBuilder sb = new StringBuilder();

        if (StrUtil.isNotBlank(dirPath)) {
            sb.append(dirPath);
        }
        if (StrUtil.isNotBlank(fileName)) {
            sb.append(fileName);
        }

        String path = sb.toString();
        if (path.contains("..")) {
            path = path.replaceAll("..", "");
        }
        try {
            path = new File(path).getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        String suffix = getExtensionFromUrl(path);
        if (StrUtil.isNotBlank(suffix)) {
            suffix = suffix.toLowerCase();
        }
        return path;
    }

}

package io.github.dunwu.tool.io;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    private static final Set<String> ALLOW_TYPES =
        Stream.of("bmp,jpg,png,gif,jpeg").collect(Collectors.toSet());

    /**
     * 系统临时目录
     * <br>
     * windows 包含路径分割符，但Linux 不包含, 在windows \\==\ 前提下， 为安全起见 同意拼装 路径分割符，
     * <pre>
     *       java.io.tmpdir
     *       windows : C:\Users/xxx\AppData\Local\Temp\
     *       linux: /temp
     * </pre>
     */
    public static final String SYS_TEM_DIR = System.getProperty("java.io.tmpdir") + File.separator;
    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;
    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

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
        if ((filename != null) && (filename.length() > 0)) {
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
            throw new DefaultException(ResultStatus.PARAMS_ERROR, "url 不能为空！");
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
     */
    public static String getFormatSize(long size) {
        String resultSize;
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
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
     * @return /
     */
    static File toFile(InputStream is, String name) throws FileNotFoundException {
        File file = new File(SYS_TEM_DIR + name);
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
     * 下载文件
     *
     * @param url      网络资源 URL
     * @param dirPath  下载存放的本地目录
     * @param fileName 下载的文件名
     * @return /
     */
    public static File download(String url, String dirPath, String fileName) throws IOException {

        String path = null;
        try {
            path = FileUtil.getValidPath(dirPath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean isOk = dir.mkdirs();
            if (!isOk) {
                throw new IOException("dir " + dirPath + " is not exists and try to create it failed.");
            }
        }

        File file = new File(path);
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL httpUrl = new URL(url);
            inputStream = httpUrl.openStream();
            fos = new FileOutputStream(file);
            IoUtil.copy(inputStream, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(fos);
            IoUtil.close(inputStream);
        }
        return file;
    }

    public static File download(String url) throws IOException {
        String extension = getExtensionFromUrl(url, true);
        String filename = IdUtil.fastSimpleUUID() + extension;
        return download(url, SYS_TEM_DIR, filename);
    }

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
        if (!ALLOW_TYPES.contains(suffix)) {
            throw new IllegalAccessException("文件后缀不合法");
        }
        return path;
    }

    public static void main(String[] args) throws Exception {

        for (int i = 1; i <= 4; i++) {
            FileUtil.binaryToText(StrUtil.format("F:\\Output\\backup.part0{}.rar", i),
                StrUtil.format("F:\\Output\\temp{}.txt", i));
        }
        FileUtil.binaryToText("F:\\Output\\Output.rar", "F:\\Output\\Output.txt");
        FileUtil.textToBinary("D:\\Output\\note.txt", "D:\\Output\\note.rar");
    }

}

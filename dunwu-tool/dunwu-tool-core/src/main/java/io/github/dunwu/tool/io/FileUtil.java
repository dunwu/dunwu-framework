package io.github.dunwu.tool.io;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 文件分隔符
     */
    public static final String FILE_SEPARATOR = SystemUtil.get(SystemUtil.FILE_SEPARATOR);
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

    public static final String IMAGE = "图片";
    public static final String TXT = "文档";
    public static final String MUSIC = "音乐";
    public static final String VIDEO = "视频";
    public static final String OTHER = "其他";

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

    /**
     * 获取文件扩展名，不带 .
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 文件大小转换
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
     * inputStream 转 File
     */
    static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(SYS_TEM_DIR + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * 获取指定路径下所有特定类型的文件
     *
     * @param path        根目录
     * @param ignoredDirs 忽略的目录
     * @param ext         文件类型后缀名
     * @return 子文件列表
     */
    public static List<File> getAllExtFiles(String path, String[] ignoredDirs, String ext) {
        List<File> files = new ArrayList<>();
        getChildrenFiles(path, ignoredDirs, ext, files);
        if (CollectionUtil.isNotEmpty(files)) {
            return files.stream().distinct().collect(Collectors.toList());
        }

        return files;
    }

    static void getChildrenFiles(String path, String[] blackList, String ext, List<File> resultFiles) {
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
                        String extensionName = getExtensionName(f.getName());
                        if (ext.equalsIgnoreCase(extensionName)) {
                            resultFiles.add(f);
                        }
                    }
                }
            }
        } else {
            String extensionName = getExtensionName(file.getName());
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

}

package io.github.dunwu.common.dal.hdfs;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import io.github.dunwu.common.io.FileUtil;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * HDFS 客户端工具类
 * <p>
 * 封装了 HDFS 常用的文件操作，如上传、下载、删除、读取、重命名等。
 * 所有操作均通过 {@link HdfsPool} 获取连接，使用完毕后自动归还。
 *
 * @author Zhang Peng
 * @since 2019-03-01
 */
public class HdfsUtil {

    /**
     * 日志记录器
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认缓冲区大小 (64MB)
     */
    private final int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 64;

    /**
     * HDFS 连接池实例
     */
    private HdfsPool hdfsPool;

    /**
     * 构造 HDFS 工具类实例
     *
     * @param hdfsPool HDFS 连接池
     */
    public HdfsUtil(HdfsPool hdfsPool) {
        this.hdfsPool = hdfsPool;
    }

    // ------------------------------------------------------
    // 文件操作
    // ------------------------------------------------------

    /**
     * 上传本地文件到 HDFS
     *
     * @param sourcePath 本地源文件路径
     * @param targetPath HDFS 目标路径
     * @throws Exception 如果上传失败
     */
    public void uploadFile(@NotBlank String sourcePath, @NotBlank String targetPath) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 调用文件系统的文件复制方法，第一个参数为是否删除原文件（true为删除），默认为 false
            fileSystem.copyFromLocalFile(false, new Path(sourcePath), new Path(targetPath));
        } catch (Exception e) {
            log.error("upload failed", e);
            throw e;
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 上传本地文件到 HDFS
     * <p>
     * 如果 {@link File} 存在，读取文件内容并上传到 targetPath。
     *
     * @param file       本地源文件对象
     * @param targetPath HDFS 目标路径
     * @throws Exception 如果文件不存在或上传失败
     */
    public void uploadFile(@NotNull File file, @NotBlank String targetPath) throws Exception {

        if (file == null || !file.exists()) {
            throw new IOException("file not exists");
        }

        // 从文件中读取二进制数据
        byte[] bytes = FileUtil.readBytes(file);

        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            outputStream = fileSystem.create(new Path(targetPath));
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
            IoUtil.close(outputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 通过输入流上传文件到 HDFS
     *
     * @param inputStream 输入流
     * @param targetPath  HDFS 目标路径
     * @throws Exception 如果上传失败
     */
    public void uploadFile(@NotNull InputStream inputStream, @NotBlank String targetPath) throws Exception {
        // 从输入流中读取二进制数据
        byte[] bytes = IoUtil.readBytes(inputStream);

        FileSystem fileSystem = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            outputStream = fileSystem.create(new Path(targetPath));
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
            IoUtil.close(outputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 上传大文件到 HDFS（带进度回调）
     *
     * @param sourcePath 本地源文件路径
     * @param targetPath HDFS 目标路径
     * @throws Exception 如果文件不存在或上传失败
     */
    public void uploadBigFile(@NotBlank String sourcePath, @NotBlank String targetPath) throws Exception {

        File file = new File(sourcePath);
        if (file.exists()) {
            throw new IOException("file not exists");
        }
        final float fileSize = file.length();

        FileSystem fileSystem = null;
        InputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = fileSystem.create(new Path(targetPath),
                new Progressable() {
                    long cnt = 0;

                    @Override
                    public void progress() {
                        cnt++;
                        // progress 方法每上传大约 64KB 的数据后就会被调用一次
                        System.out.println("上传进度：" + (cnt * 64 * 1024 / fileSize) * 100 + " %");
                    }
                });

            IoUtil.copyByNIO(inputStream, outputStream, 4096, new StreamProgress() {
                @Override
                public void start() {
                    log.info("copy start");
                }

                @Override
                public void progress(long total, long progressSize) {

                }

                @Override
                public void finish() {
                    log.info("copy success");
                }
            });
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 从 HDFS 下载文件到本地
     *
     * @param sourcePath HDFS 源文件路径
     * @param targetPath 本地目标路径
     * @throws Exception 如果下载失败
     */
    public void downloadFile(@NotBlank String sourcePath, @NotBlank String targetPath) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 调用文件系统的文件复制方法，第一个参数为是否删除原文件（true为删除），默认为 false
            fileSystem.copyToLocalFile(false, new Path(sourcePath), new Path(targetPath));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 在 HDFS 中复制文件
     *
     * @param sourcePath HDFS 源文件路径
     * @param targetPath HDFS 目标文件路径
     * @param buffSize   缓冲区大小
     * @throws Exception 如果复制失败
     */
    public void copyFile(@NotBlank String sourcePath, @NotBlank String targetPath, int buffSize) throws Exception {
        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            inputStream = fileSystem.open(new Path(sourcePath));
            outputStream = fileSystem.create(new Path(targetPath));
            if (buffSize <= 0) {
                buffSize = DEFAULT_BUFFER_SIZE;
            }
            IOUtils.copyBytes(inputStream, outputStream, buffSize, false);
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 标记文件在退出时删除
     *
     * @param path HDFS 文件路径
     * @return 如果标记成功返回 true，否则返回 false
     * @throws Exception 如果操作失败
     */
    public boolean deleteOnExit(@NotBlank String path) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.deleteOnExit(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 读取 HDFS 中的文本文件内容
     *
     * @param path HDFS 文件路径
     * @return 文件内容字符串
     * @throws Exception 如果文件不存在或读取失败
     */
    public String readFile(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        // 目标路径
        Path sourcePath = new Path(path);
        BufferedReader reader = null;
        FSDataInputStream inputStream = null;
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            inputStream = fileSystem.open(sourcePath);
            // 防止中文乱码
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt);
            }
            return sb.toString();
        } finally {
            IoUtil.close(reader);
            IoUtil.close(inputStream);
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 获取文件或目录的内容摘要信息
     *
     * @param path HDFS 路径
     * @return 内容摘要 {@link ContentSummary}
     * @throws Exception 如果路径不存在或获取失败
     */
    public ContentSummary getContentSummary(String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.getContentSummary(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 获取文件在 HDFS 集群中的块位置信息
     *
     * @param path HDFS 文件路径
     * @return 块位置数组
     * @throws Exception 如果文件不存在或获取失败
     */
    public BlockLocation[] getFileBlockLocations(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            FileStatus fileStatus = fileSystem.getFileStatus(new Path(path));
            return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 重命名 HDFS 中的文件或目录
     *
     * @param oldName 旧路径名
     * @param newName 新路径名
     * @return 如果重命名成功返回 true，否则返回 false
     * @throws Exception 如果操作失败
     */
    public boolean rename(@NotBlank String oldName, @NotBlank String newName) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.rename(new Path(oldName), new Path(newName));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 在 HDFS 中创建目录
     *
     * @param path 目录路径
     * @return 如果创建成功或目录已存在返回 true，否则返回 false
     * @throws Exception 如果创建失败
     */
    public boolean mkdirs(@NotBlank String path) throws Exception {
        if (exists(path)) {
            return true;
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.mkdirs(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 在 HDFS 中创建目录并设置权限
     *
     * @param path       目录路径
     * @param permission 访问权限
     * @return 如果创建成功或目录已存在返回 true，否则返回 false
     * @throws Exception 如果创建失败
     */
    public boolean mkdirsWithPermission(@NotBlank String path, FsPermission permission) throws Exception {
        if (exists(path)) {
            return true;
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            fileSystem.mkdirs(new Path(path), permission);
            return fileSystem.mkdirs(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 读取 HDFS 目录下的文件状态列表
     *
     * @param path HDFS 目录路径
     * @return 文件状态数组 {@link FileStatus[]}
     * @throws Exception 如果目录不存在或读取失败
     */
    public FileStatus[] listStatus(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 强制递归删除，使得无论文件或文件夹都会被彻底删除
            return fileSystem.listStatus(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 递归读取 HDFS 目录下的所有文件列表
     *
     * @param path HDFS 目录路径
     * @return 文件信息列表 {@link LocatedFileStatus}
     * @throws Exception 如果目录不存在或读取失败
     */
    public List<LocatedFileStatus> listFiles(@NotBlank String path) throws Exception {
        if (!exists(path)) {
            throw new IOException(path + " not exists in hdfs");
        }

        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 递归找到所有文件
            RemoteIterator<LocatedFileStatus> filesList = fileSystem.listFiles(new Path(path), true);
            List<LocatedFileStatus> returnList = new ArrayList<>();
            while (filesList.hasNext()) {
                LocatedFileStatus next = filesList.next();
                returnList.add(next);
            }
            return returnList;
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 删除 HDFS 中的文件或目录
     *
     * @param path HDFS 路径
     * @return 如果删除成功返回 true，否则返回 false
     * @throws Exception 如果删除失败
     */
    public boolean delete(@NotBlank String path) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            // 强制递归删除，使得无论文件或文件夹都会被彻底删除
            return fileSystem.delete(new Path(path), true);
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

    /**
     * 判断 HDFS 中的文件或目录是否存在
     *
     * @param path HDFS 路径
     * @return 如果存在返回 true，否则返回 false
     * @throws Exception 如果检查失败
     */
    public boolean exists(@NotBlank String path) throws Exception {
        FileSystem fileSystem = null;
        try {
            fileSystem = this.hdfsPool.borrowObject();
            return fileSystem.exists(new Path(path));
        } finally {
            if (fileSystem != null) { this.hdfsPool.returnObject(fileSystem); }
        }
    }

}

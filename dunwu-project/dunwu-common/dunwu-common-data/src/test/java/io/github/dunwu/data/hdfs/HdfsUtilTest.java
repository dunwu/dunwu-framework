package io.github.dunwu.data.hdfs;

import io.github.dunwu.tool.util.ClassUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-21
 */
public class HdfsUtilTest {

    public static final String TEMP_DIR = "/user/dunwu";
    public static final String TEMP_DIR2 = "/user/dunwu2";
    private static HdfsConfig config;
    private static HdfsUtil hdfsUtil;
    private static HdfsFactory hdfsFactory;
    private static HdfsPool hdfsPool;

    @BeforeAll
    public static void create() throws Exception {
        config = new HdfsConfig().setUrl("hdfs://tdh60dev01:8020").setUser("hadoop");
        GenericObjectPoolConfig<FileSystem> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxWaitMillis(3000);
        hdfsFactory = new HdfsFactory(config);
        hdfsPool = new HdfsPool(hdfsFactory, poolConfig);
        hdfsUtil = new HdfsUtil(hdfsPool);
        // hdfsUtil.delete(TEMP_DIR);
        hdfsUtil.mkdirs(TEMP_DIR);
    }

    @AfterAll
    public static void destroy() throws Exception {
        hdfsUtil.delete(TEMP_DIR);
    }

    @Nested
    @DisplayName("文件操作测试")
    class FileOperTest {

        static final String SOURCE_PATH = TEMP_DIR + "/source.txt";
        static final String SOURCE_PATH2 = TEMP_DIR + "/source2.txt";
        static final String TARGET_PATH = TEMP_DIR + "/target.txt";

        @Test
        public void uploadAndDownloadTest() throws Exception {
            // 上传测试
            final String classPath = ClassUtil.getClassPath();
            File file = new File(classPath + "/source.txt");
            Assertions.assertTrue(file.exists(), "测试文件不存在");
            hdfsUtil.uploadFile(file, SOURCE_PATH);
            Assertions.assertTrue(hdfsUtil.exists(SOURCE_PATH));

            // 下载测试
            hdfsUtil.downloadFile(SOURCE_PATH, classPath + "/target.txt");
            File file2 = new File(classPath + "/target.txt");
            Assertions.assertTrue(file2.exists(), "下载文件失败");
        }

        @Test
        public void uploadAndDownloadTest2() throws Exception {
            // 上传测试
            final String classPath = ClassUtil.getClassPath();
            hdfsUtil.uploadFile(classPath + "/source.txt", SOURCE_PATH2);
            Assertions.assertTrue(hdfsUtil.exists(SOURCE_PATH2));

            // 下载测试
            hdfsUtil.downloadFile(SOURCE_PATH2, classPath + "/target2.txt");
            File file = new File(classPath + "/target2.txt");
            Assertions.assertTrue(file.exists(), "下载文件失败");
        }

        @Test
        public void renameTest() throws Exception {
            if (!hdfsUtil.exists(SOURCE_PATH)) {
                return;
            }

            Assertions.assertTrue(hdfsUtil.rename(SOURCE_PATH, TARGET_PATH));
            Assertions.assertFalse(hdfsUtil.exists(SOURCE_PATH));
            Assertions.assertTrue(hdfsUtil.exists(TARGET_PATH));

            Assertions.assertTrue(hdfsUtil.rename(TARGET_PATH, SOURCE_PATH));
            Assertions.assertTrue(hdfsUtil.exists(SOURCE_PATH));
            Assertions.assertFalse(hdfsUtil.exists(TARGET_PATH));
        }

        @Test
        @Order(2)
        public void readFileTest() throws Exception {
            String content = hdfsUtil.readFile(SOURCE_PATH);
            System.out.println(content);
        }

    }

    @Nested
    @DisplayName("文件夹操作测试")
    class DirOperTest {

        @Test
        @DisplayName("创建、删除文件夹操作测试")
        public void mkdirTest() throws Exception {
            Assertions.assertTrue(hdfsUtil.mkdirs(TEMP_DIR2));
            Assertions.assertTrue(hdfsUtil.exists(TEMP_DIR2));
            Assertions.assertTrue(hdfsUtil.delete(TEMP_DIR2));
            Assertions.assertFalse(hdfsUtil.exists(TEMP_DIR2));
        }

        @Test
        @DisplayName("获取目录信息")
        public void listStatusTest() throws Exception {
            FileStatus[] fileStatuses = hdfsUtil.listStatus(TEMP_DIR);
            for (FileStatus status : fileStatuses) {
                System.out.println(status.toString());
            }
        }

        @Test
        @DisplayName("查看目录下的文件信息")
        public void listFilesTest() throws Exception {
            List<LocatedFileStatus> locatedFileStatuses = hdfsUtil.listFiles(TEMP_DIR);
            locatedFileStatuses.forEach(System.out::println);
        }

    }

}

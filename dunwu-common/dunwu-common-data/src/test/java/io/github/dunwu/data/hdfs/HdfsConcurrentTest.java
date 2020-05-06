package io.github.dunwu.data.hdfs;

import cn.hutool.core.util.ClassUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.hadoop.fs.FileSystem;

import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-22
 */
public class HdfsConcurrentTest {

    public static final String TEMP_DIR = "/user/dunwu";
    private static HdfsConfig config;
    private static HdfsUtil hdfsUtil;
    private static HdfsFactory hdfsFactory;
    private static HdfsPool hdfsPool;

    public static void main(String[] args) throws Exception {
        config = new HdfsConfig().setUrl("hdfs://tdh60dev01:8020").setUser("hadoop");
        hdfsFactory = new HdfsFactory(config);

        GenericObjectPoolConfig<FileSystem> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxIdle(100);
        poolConfig.setMinIdle(10);
        poolConfig.setMaxWaitMillis(3000);
        hdfsPool = new HdfsPool(hdfsFactory, poolConfig);
        hdfsUtil = new HdfsUtil(hdfsPool);
        hdfsUtil.delete(TEMP_DIR);
        hdfsUtil.mkdirs(TEMP_DIR);

        CountDownLatch latch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            String name = "thread" + i;
            new UploadThread(name, latch).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class UploadThread extends Thread {

        private String name;
        private CountDownLatch latch;
        final String classPath = ClassUtil.getClassPath();

        UploadThread(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                String targetPath = TEMP_DIR + "/" + name + ".txt";
                hdfsUtil.uploadFile(classPath + "/source.txt", targetPath);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }

    }

}

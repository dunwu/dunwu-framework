package io.github.dunwu.tool.io;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-05-01
 */
public class FileUtilTests {

    String rootDir = "E:\\照片\\";

    @Test
    public void test() {
        File dir = new File(rootDir);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }

            String fullFileName = FileUtil.getName(file);
            String filename = StrUtil.subBefore(fullFileName, ".jpg", true);
            if (!StrUtil.startWith(filename, "IMG_")) {
                continue;
            }

            List<String> groups = StrUtil.split(filename, "_");

            String month = groups.get(1).substring(0, 6);
            String newFileName = rootDir + month + "\\" + fullFileName;
            FileUtil.move(file, new File(newFileName), false);
            // System.out.println(newFileName);
        }
    }

    @Test
    public void test2() {
        // for (int i = 1; i <= 4; i++) {
        //     FileUtil.textToBinary(StrUtil.format("D:\\Temp\\temp{}.txt", i),
        //         StrUtil.format("D:\\Temp\\backup.part0{}.rar", i));
        // }
        // FileUtil.binaryToText("D:\\Workspace\\知识图谱.xmind", "D:\\Workspace\\知识图谱.txt");
        FileUtil.textToBinary("D:\\Temp\\Output.txt", "D:\\Temp\\Output.rar");
    }

}

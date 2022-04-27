package io.github.dunwu.tool.parser.git;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.util.RegexUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Git 工具
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-11
 */
public class GitUtil {

    /**
     * 通过调用 Git 命令行工具，获取某 git 仓库下指定文件的第一次创建时间
     *
     * @param gitProjectPath git 项目目录
     * @param filename git 项目中文件的相对路径
     * @return
     */
    public static String getFileFirstGitPushTime(String gitProjectPath, String filename) {
        Process process;
        try {
            Runtime.getRuntime().exec("git config log.date iso8601");
            process = Runtime.getRuntime().exec("git blame " + filename, null, new File(gitProjectPath));
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            if (CollectionUtil.isEmpty(lines)) {
                return null;
            }

            String regex = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
            String lastLine = lines.get(lines.size() - 1);
            return RegexUtil.getFirst(lastLine, regex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

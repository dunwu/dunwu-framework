package io.github.dunwu.tool.parser.md;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.parser.git.GitUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

/**
 * Markdown 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-12
 */
public class MarkdownUtil {

    private static final String separator = System.getProperty("line.separator");

    public static FrontMatter getFrontMatterFromFile(File file) {
        List<String> lines = FileUtil.readUtf8Lines(file);
        return getFrontMatterFromLines(lines);
    }

    public static FrontMatter getFrontMatterFromLines(List<String> lines) {
        if (CollectionUtil.isEmpty(lines)) {
            return null;
        }

        String firstLine = lines.get(0);
        if ("---".equals(firstLine)) {
            // 找到第二行 ---，即 front matter 的边界
            for (int i = 1; i < lines.size(); i++) {
                if ("---".equals(lines.get(i))) {
                    List<String> contentLines = lines.subList(1, i);
                    String content = String.join(separator, contentLines);
                    return new FrontMatter(0, i, content, contentLines);
                }
            }
        }
        return null;
    }

    public static void formatDate(String path, String filename, MarkdownUtil.HexoFrontMatter hexoFrontMatter) {
        DateTime newDate;
        String firstGitTime = GitUtil.getFileFirstGitPushTime(path, filename);
        if (StrUtil.isNotBlank(firstGitTime)) {
            newDate = DateUtil.parse(firstGitTime, DatePattern.NORM_DATETIME_PATTERN);
        } else {
            newDate = new DateTime();
            System.err.println(StrUtil.format("【{}】未获得第一次git提交时间", filename));
        }

        DateTime oldDate = null;
        if (hexoFrontMatter.getDate() != null) {
            try {
                oldDate = DateUtil.parse(hexoFrontMatter.getDate(), DatePattern.NORM_DATETIME_PATTERN);
            } catch (DateException e) {
                try {
                    oldDate = DateUtil.parse(hexoFrontMatter.getDate(), DatePattern.NORM_DATETIME_MINUTE_PATTERN);
                } catch (DateException e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            oldDate = new DateTime();
        }

        String dateStr;
        if (oldDate != null && newDate.after(oldDate)) {
            dateStr = DateUtil.format(oldDate, DatePattern.NORM_DATETIME_PATTERN);
        } else {
            dateStr = firstGitTime;
        }

        hexoFrontMatter.setDate(dateStr);
        // System.out.println(StrUtil.format("【{}】{}", filename, dateStr));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FrontMatter {

        private int startLine;
        private int endLine;
        private String content;
        private List<String> contentLines;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HexoFrontMatter {

        private String title;
        private String date;
        private List<String> categories;
        private List<String> tags;
        private String permalink;
        private String abbrlink;
        private String hidden;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("---").append(separator);
            if (StrUtil.isNotBlank(title)) {
                sb.append("title: ").append(title).append(separator);
            }
            if (StrUtil.isNotBlank(date)) {
                sb.append("date: ").append(date).append(separator);
            }
            if (CollectionUtil.isNotEmpty(categories)) {
                sb.append("categories:").append(separator);
                for (String category : categories) {
                    sb.append("  - ").append(category).append(separator);
                }
            }
            if (CollectionUtil.isNotEmpty(tags)) {
                sb.append("tags:").append(separator);
                for (String tag : tags) {
                    sb.append("  - ").append(tag).append(separator);
                }
            }
            if (StrUtil.isNotBlank(permalink)) {
                sb.append("permalink: ").append(permalink).append(separator);
            }
            if (StrUtil.isNotBlank(hidden)) {
                sb.append("hidden: ").append(hidden).append(separator);
            }
            sb.append("---").append(separator);
            return sb.toString();
        }

        public List<String> toLines() {
            String content = this.toString();
            String[] array = content.split(separator);
            return CollectionUtil.toList(array);
        }

    }

}

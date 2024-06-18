package io.github.dunwu.tool.parser.md;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.parser.git.GitUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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

    public static void formatDate(String path, String filename, HexoFrontMatter hexoFrontMatter) {
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

    public static String createPermalink(String path) {
        int hash = HashUtil.murmur32(path.getBytes());
        return "/pages/" + StrUtil.padPre(HexUtil.toHex(hash), 8, "0") + "/";
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

        private static Field[] fields =
            ReflectUtil.getFields(HexoFrontMatter.class, field -> !Modifier.isStatic(field.getModifiers()));

        private String home;

        private String layout;

        private String icon;

        private String title;

        private String heroFullScreen;

        private String heroImage;

        private String heroText;

        private String tagline;

        private String bgImage;

        private String cover;

        private String date;

        private String order;

        private List<String> categories;

        private List<String> tags;

        private String permalink;

        private String hidden;

        private String index;

        private HexoDirFrontMatter dir;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("---").append(separator);

            for (Field f : fields) {
                if (f.getName().equals("dir")) {
                    HexoDirFrontMatter dir = (HexoDirFrontMatter) ReflectUtil.getFieldValue(this, f);
                    if (dir != null) {
                        sb.append("dir:").append(separator)
                          .append("  order: ").append(dir.getOrder()).append(separator)
                          .append("  link: ").append(dir.getLink()).append(separator);
                    }
                    continue;
                }

                if (f.getGenericType() == String.class) {
                    String value = (String) ReflectUtil.getFieldValue(this, f);
                    if (StrUtil.isNotBlank(value)) {
                        sb.append(f.getName() + ": ").append(value).append(separator);
                    }
                } else {
                    List<String> list = (List<String>) ReflectUtil.getFieldValue(this, f);
                    if (CollectionUtil.isNotEmpty(list)) {
                        sb.append(f.getName() + ":").append(separator);
                        for (String value : list) {
                            sb.append("  - ").append(value).append(separator);
                        }
                    }
                }
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HexoDirFrontMatter {

        private String order;
        private String link;

    }

}

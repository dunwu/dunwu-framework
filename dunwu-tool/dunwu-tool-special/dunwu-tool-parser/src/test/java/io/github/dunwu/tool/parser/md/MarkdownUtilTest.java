package io.github.dunwu.tool.parser.md;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriteConfig;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.io.AnsiColorUtil;
import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.parser.yaml.YamlUtil;
import io.github.dunwu.tool.util.RegexUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * FileUtil 测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-11
 */
public class MarkdownUtilTest {

    public static final String MARKDOWN_EXT = "md";

    // 忽略的目录
    public static final String[] IGNORED_DIR_LIST = { ".git", ".idea", ".temp", "node_modules", "assets", ".github",
        "scripts", "settings", "scaffolds", "@pages" };

    public static final String PERM_LINK_OUT_FILE = "d://diff_perm_link_out.csv";
    public static final String PERM_LINK_IN_FILE = "d://diff_perm_link_in.csv";
    public static CsvWriteConfig CSV_WRITE_CONFIG = new CsvWriteConfig();
    public static CsvWriter CSV_WRITER;
    private static List<UrlPair> URL_PAIRS = new LinkedList<>();
    private static Map<String, String> URL_PAIR_MAP;

    static {
        CSV_WRITE_CONFIG.setFieldSeparator(',');
        CSV_WRITER = CsvUtil.getWriter(new File(PERM_LINK_OUT_FILE), StandardCharsets.UTF_8, true, CSV_WRITE_CONFIG);

        CsvReadConfig config = new CsvReadConfig();
        config.setContainsHeader(true);
        CsvReader CSV_READER = new CsvReader(config);

        if (FileUtil.exist(PERM_LINK_IN_FILE)) {
            CsvData data = CSV_READER.read(new File(PERM_LINK_IN_FILE));
            URL_PAIR_MAP = new HashMap<>(data.getRowCount());
            for (CsvRow row : data) {
                UrlPair urlPair = row.toBean(UrlPair.class);
                URL_PAIR_MAP.put(urlPair.getOldUrl(), urlPair.getNewUrl());
            }
        } else {
            URL_PAIR_MAP = new HashMap<>(0);
        }
    }

    private static final boolean IS_BLOG = false;

    private static final int SPAN = 20;

    private static final ExecutorService EXECUTOR = ExecutorBuilder.create()
                                                                   .setCorePoolSize(5)
                                                                   .setMaxPoolSize(20)
                                                                   .setWorkQueue(new LinkedBlockingQueue<>(100))
                                                                   .setThreadFactory(ThreadUtil.newNamedThreadFactory(
                                                                       "Markdown 文件处理线程", false))
                                                                   .build();

    @ParameterizedTest
    @DisplayName("格式化Markdown文档")
    @ValueSource(strings = { "D:\\Codes\\zp\\ztutorial\\zpcs\\waterdrop\\" })
    public void test(String projectPath) {
        String docsDir = projectPath + "docs";
        List<File> files = FileUtil.getFiles(docsDir, MARKDOWN_EXT, IGNORED_DIR_LIST);
        if (CollectionUtil.isEmpty(files)) {
            return;
        }

        int threadNum = 1;
        int size = files.size();
        if (size > SPAN) {
            threadNum = size / SPAN;
            if (size % SPAN != 0) {
                threadNum++;
            }

            CountDownLatch latch = new CountDownLatch(threadNum);
            for (int i = 0; i < threadNum; i++) {
                int start = i * SPAN;
                Task task = new Task(start, size, projectPath, latch, files);
                EXECUTOR.execute(task);
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            for (File f : files) {
                resolveMarkdownFile(projectPath, f);
            }
        }
        CSV_WRITER.writeBeans(URL_PAIRS);
        CSV_WRITER.close();
    }

    @Test
    @DisplayName("清理markdown文件中未使用的图片")
    public void test2() {

        String imageProjDir = "D:\\Codes\\zp\\images\\";
        Set<File> realImages = new LinkedHashSet<>();
        realImages.addAll(FileUtil.loopFiles(imageProjDir + "common"));
        realImages.addAll(FileUtil.loopFiles(imageProjDir + "cs"));
        realImages.addAll(FileUtil.loopFiles(imageProjDir + "snap"));
        System.out.println("实际文件数：" + realImages.size());

        Set<File> images = new LinkedHashSet<>();
        String root = "D:\\Codes\\zp\\ztutorial";
        List<File> files = FileUtil.getFiles(root, "md", ".idea", ".git", "assets", "node_modules");
        for (File file : files) {
            List<String> lines = FileUtil.readUtf8Lines(file);
            for (String line : lines) {
                if (line.contains("https://raw.githubusercontent.com/dunwu/images/")) {
                    String url = RegexUtil.getFirst(line, "https://raw.githubusercontent.com/dunwu/image[\\S]*");
                    if (url.contains("\"")) {
                        url = StrUtil.sub(url, 0, url.indexOf("\""));
                    } else if (url.contains(")")) {
                        url = StrUtil.sub(url, 0, url.indexOf(")"));
                    }

                    String imageFile = null;
                    if (url.contains("https://raw.githubusercontent.com/dunwu/images/master/")) {
                        imageFile =
                            StrUtil.subAfter(url, "https://raw.githubusercontent.com/dunwu/images/master/", false);
                    }
                    if (imageFile == null) {
                        continue;
                    }
                    imageFile = imageProjDir + imageFile;
                    File image = new File(imageFile);
                    images.add(image);
                }
            }
        }
        System.out.println("引用文件数：" + images.size());

        for (File file : realImages) {
            if (images.contains(file)) {
                // System.out.println("存在文件：" + file.getAbsolutePath());
            } else {
                // System.out.println("不存在文件：" + file.getAbsolutePath());
                FileUtil.del(file);
            }
        }
    }

    @Test
    public void test3() {
        List<File> realImages = new LinkedList<>();
        realImages.addAll(FileUtil.loopFiles("D:\\Codes\\zp\\images\\common"));
        realImages.addAll(FileUtil.loopFiles("D:\\Codes\\zp\\images\\cs"));
        realImages.addAll(FileUtil.loopFiles("D:\\Codes\\zp\\images\\snap"));
        System.out.println("实际文件数：" + realImages.size());
        realImages.forEach(System.out::println);
    }

    private static void resolveMarkdownFile(String projectPath, File f) {
        String filename = f.getAbsolutePath().replace(projectPath, "");
        List<String> lines = FileUtil.readUtf8Lines(f);
        if (CollectionUtil.isEmpty(lines)) {
            return;
        }

        MarkdownUtil.HexoFrontMatter hexoFrontMatter;
        MarkdownUtil.FrontMatter frontMatter = MarkdownUtil.getFrontMatterFromLines(lines);
        if (frontMatter == null) {
            System.err.println(filename + " 文件的 front matter 解析失败！");
            frontMatter = new MarkdownUtil.FrontMatter();
            hexoFrontMatter = new MarkdownUtil.HexoFrontMatter();
        } else {
            try {
                // Yaml 解析
                hexoFrontMatter = YamlUtil.parse(frontMatter.getContent(), MarkdownUtil.HexoFrontMatter.class);
            } catch (Exception e) {
                System.err.println(filename + " 解析失败！");
                e.printStackTrace();
                return;
            }
        }

        if (filename.endsWith("README.md")) {
            hexoFrontMatter.setHidden("true");
            hexoFrontMatter.setIndex("false");
        } else {
            if (StrUtil.isBlank(hexoFrontMatter.getOrder())) {
                String str = FileUtil.getName(filename);
                String order = RegexUtil.getFirst(str, "\\d{2}");
                if (StrUtil.isNotBlank(order)) {
                    hexoFrontMatter.setOrder(order);
                }
            }
        }

        // 格式化时间
        MarkdownUtil.formatDate(projectPath, filename, hexoFrontMatter);

        // 根据实际文件路径刷新目录
        String relativePath = filename.replace("docs\\", "");
        String[] paths = relativePath.split("\\\\");
        if (ArrayUtil.isNotEmpty(paths)) {
            hexoFrontMatter.setCategories(new ArrayList<>());
            for (int i = 0; i < paths.length - 1; i++) {
                String dir = RegexUtil.replaceFirst(paths[i], "^[0-9]{2}\\.", "");
                hexoFrontMatter.getCategories().add(dir);
            }
        }

        // 根据是否为 hexo 博客，选择删除 permalink 或 abbrlink 属性
        if (IS_BLOG) {
            hexoFrontMatter.setPermalink(null);
        } else {
            String oldUrl = hexoFrontMatter.getPermalink();
            String newUrl = MarkdownUtil.createPermalink(relativePath);
            if (!newUrl.equals(oldUrl)) {
                String msg = StrUtil.format("path: {}, oldUrl: {}, newUrl: {}", relativePath, oldUrl, newUrl);
                AnsiColorUtil.BOLD_CYAN.println(msg);
                if (StrUtil.isNotBlank(oldUrl)) {
                    URL_PAIRS.add(new UrlPair(oldUrl, newUrl));
                }
                hexoFrontMatter.setPermalink(newUrl);
            }
        }

        List<String> textLines;
        if (frontMatter.getEndLine() != 0) {
            textLines = lines.subList(frontMatter.getEndLine() + 1, lines.size());
        } else {
            textLines = lines;
        }

        List<String> finalLines = new ArrayList<>(hexoFrontMatter.toLines());
        for (String line : textLines) {
            if (line.contains("https://dunwu.github.io/waterdrop/pages/")) {
                String fullPath = RegexUtil.getFirst(line, "https://dunwu.github.io/waterdrop/pages/[\\w//]*");
                String oldUrl = fullPath.replace("https://dunwu.github.io/waterdrop", "");
                if (StrUtil.isNotBlank(oldUrl) && oldUrl.length() < 16) {
                    System.out.println("line: " + line);
                }
                // System.out.println("old url: " + oldUrl);
                if (URL_PAIR_MAP.containsKey(oldUrl)) {
                    String newUrl = URL_PAIR_MAP.get(oldUrl);
                    // System.out.println("new url: " + newUrl);
                    line = line.replace(oldUrl, newUrl);
                    // System.out.println("line: " + line);
                }
            }
            finalLines.add(line);
        }

        // int last = finalLines.size() - 1;
        // String lastLine = finalLines.get(last);
        FileUtil.writeUtf8Lines(finalLines, f);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Task implements Runnable {

        private int start;

        private int size;

        private String projectPath;

        private CountDownLatch latch;

        private List<File> files;

        @Override
        public void run() {
            int end = start + SPAN;
            // System.out.printf("处理范围：%d - %d\n", start, end);
            while (start < size && start < end) {
                resolveMarkdownFile(projectPath, files.get(start));
                // System.out.printf("处理了第 %d 个文件\n", start);
                start++;
            }

            latch.countDown();
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UrlPair {

        private String oldUrl;
        private String newUrl;

    }

}

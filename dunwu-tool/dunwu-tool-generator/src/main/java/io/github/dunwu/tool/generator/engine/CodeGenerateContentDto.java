package io.github.dunwu.tool.generator.engine;

/**
 * 模板内容
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-30
 */
public class CodeGenerateContentDto {

    private final String name;
    private final String content;

    public CodeGenerateContentDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

}

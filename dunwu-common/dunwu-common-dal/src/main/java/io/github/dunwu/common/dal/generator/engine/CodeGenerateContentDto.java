package io.github.dunwu.common.dal.generator.engine;

/**
 * 代码生成内容 DTO
 * <p>
 * 用于封装预览或生成代码时的文件名称和文件内容。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-30
 */
public class CodeGenerateContentDto {

    /**
     * 文件名称或类型标识
     */
    private final String name;

    /**
     * 文件内容字符串
     */
    private final String content;

    /**
     * 构造代码生成内容对象
     *
     * @param name    文件名称或类型标识
     * @param content 文件内容字符串
     */
    public CodeGenerateContentDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    /**
     * 获取文件名称或类型标识
     *
     * @return 文件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取文件内容字符串
     *
     * @return 文件内容
     */
    public String getContent() {
        return content;
    }

}

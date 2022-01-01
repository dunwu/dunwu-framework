package io.github.dunwu.tool.web.log.constant;

/**
 * 操作类型枚举
 *
 * @author peng.zhang
 * @date 2021-12-29
 */
public enum OperationType {

    /** 添加 */
    ADD("添加"),
    /** 更新 */
    EDIT("更新"),
    /** 删除 */
    DEL("删除"),
    /** 保存 */
    SAVE("保存"),
    /** 批量添加 */
    BATCH_ADD("批量添加"),
    /** 批量更新 */
    BATCH_EDIT("批量更新"),
    /** 批量删除 */
    BATCH_DEL("批量删除"),
    /** 批量保存 */
    BATCH_SAVE("批量保存"),
    /** 根据ID导出 */
    EXPORT_LIST("根据ID导出"),
    /** 分页查询导出 */
    EXPORT_PAGE("分页查询导出"),
    /** 其他 */
    OTHER("其他");

    private final String name;

    public String getName() {
        return name;
    }

    OperationType(String name) {
        this.name = name;
    }
}

package io.github.dunwu.common.dal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库表列信息实体
 * <p>
 * 用于封装数据库表的列（字段）元数据信息，如列名、类型、注释等。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableColumnInfo {

    /**
     * Schema 名称
     */
    @JsonProperty("schemaName")
    private String tableSchema;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段备注/注释
     */
    private String columnComment;

    /**
     * 字段数据类型
     */
    private String dataType;

}

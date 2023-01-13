package io.github.dunwu.tool.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableColumnInfo {

    /** Schema 名称 */
    @JsonProperty("schemaName")
    private String tableSchema;

    /** 表名称 */
    private String tableName;

    /** 字段名称 */
    private String columnName;

    /** 字段备注 */
    private String columnComment;

    /** 字段数据类型 */
    private String dataType;

}

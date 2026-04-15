package io.github.dunwu.common.dal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 数据库表信息实体类。
 * <p>
 * 用于封装数据库表的元数据信息，如表名、存储引擎、字符集、注释及创建时间等。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {

    /**
     * 数据库 Schema 名称（模式名）
     */
    @JsonProperty("schemaName")
    private String tableSchema;

    /**
     * 数据库表名称
     */
    private String tableName;

    /**
     * 数据库存储引擎类型
     * <p>
     * 例如：{@code InnoDB}, {@code MyISAM}
     */
    private String engine;

    /**
     * 表字符集排序规则
     * <p>
     * 例如：{@code utf8mb4_general_ci}
     */
    @JsonProperty("coding")
    private String tableCollation;

    /**
     * 表注释或描述信息
     */
    @JsonProperty("comment")
    private String tableComment;

    /**
     * 表创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}

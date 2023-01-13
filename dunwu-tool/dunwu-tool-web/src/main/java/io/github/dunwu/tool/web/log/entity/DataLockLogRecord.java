package io.github.dunwu.tool.web.log.entity;

import io.github.dunwu.tool.web.log.annotation.DataLockLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 数据锁定日志注解 {@link DataLockLog} 对应的数据信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Data
@Builder
public class DataLockLogRecord {

    /** ID */
    private Long id;

    /** 锁定的库名 */
    private String schemaName;

    /** 锁定的表名 */
    private String tableName;

    /** 锁定的表名 */
    private String columnName;

    /** 行ID */
    private String rowId;

    /** 单元值 */
    private String cellValue;

    /** 操作类型 */
    private OperationType operation;

    /** 操作者ID */
    private Long operatorId;

    /** 操作者用户名 */
    private String operatorName;

    /** 日志记录时间 */
    private Date createTime;

}

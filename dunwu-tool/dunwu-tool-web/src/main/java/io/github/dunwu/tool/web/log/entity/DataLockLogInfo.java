package io.github.dunwu.tool.web.log.entity;

import io.github.dunwu.tool.web.log.annotation.DataLockLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
import lombok.Builder;
import lombok.Data;

/**
 * 数据锁定日志注解 {@link DataLockLog} 对应的数据信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Data
@Builder
public class DataLockLogInfo {

    /**
     * 锁定的表所属 schema
     */
    private String schemaName;

    /**
     * 锁定的表名
     */
    private String tableName;

    /**
     * 主键关键字名
     */
    private String id;

    /**
     * 实体
     */
    private String entity;

    /**
     * 操作类型
     */
    private OperationType operation;

    /**
     * 根据 ID 批量查询数据的方法名，例如：io.github.dunwu.tool.data.mybatis.IDao#listByIds
     */
    private String queryMethod;

}

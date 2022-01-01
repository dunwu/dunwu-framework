package io.github.dunwu.tool.web.log.entity;

import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
import lombok.Builder;
import lombok.Data;

/**
 * 操作日志注解 {@link OperationLog} 对应的数据信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Data
@Builder
public class OperationLogInfo {

    /** 业务 NO */
    private String bizNo;

    /** 业务类型 */
    private String bizType;

    /** 操作类型 */
    private OperationType operation;

    /** 成功信息 SpEl 模板 */
    private String success;

    /** 失败信息 SpEl 模板 */
    private String fail;

    /** 详情信息 SpEl 模板 */
    private String detail;

    /** 操作者 SpEl 模板 */
    private String operator;

    /** 日志记录条件 SpEl 模板 */
    private String condition;

}

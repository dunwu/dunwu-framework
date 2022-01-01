package io.github.dunwu.tool.web.log.entity;

import io.github.dunwu.tool.web.log.constant.OperationType;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 操作日志实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LogRecord {

    /** ID */
    private Long id;

    /** 应用名 */
    private String appName;

    /** 业务编码 */
    private String bizNo;

    /** 业务类型 */
    private String bizType;

    /** 是否成功 */
    private boolean success;

    /** 基本信息 */
    private String message;

    /** 详情信息 */
    private String detail;

    /** 异常信息 */
    private String exception;

    /** 操作的类名 */
    private String className;

    /** 操作的方法名 */
    private String methodName;

    /** 被调用方法的参数 */
    private String params;

    /** 操作类型 */
    private OperationType operation;

    /** 操作者ID */
    private Long operatorId;

    /** 操作者用户名 */
    private String operatorName;

    /** 服务端IP地址 */
    private String serverIp;

    /** 客户端IP地址 */
    private String clientIp;

    /** 客户端地理位置 */
    private String clientLocation;

    /** 客户端设备 */
    private String clientDevice;

    /** HTTP请求的耗时 */
    private Long requestTime;

    /** 日志记录时间 */
    private Date createTime;

}

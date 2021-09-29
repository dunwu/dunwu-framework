package io.github.dunwu.tool.web.log;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志记录 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
public class AppLogInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 日志级别 */
    private String level;

    /** 业务类型 */
    private String bizType;

    /** 日志消息 */
    private String message;

    /** 异常信息，只有日志级别为ERROR时才有值 */
    private String exceptionMessage;

    /** 操作的类名 */
    private String className;

    /** 操作的方法名 */
    private String methodName;

    /** 被调用方法的参数 */
    private String params;

    /** 操作类型 */
    private String operateType;

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
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public AppLogInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public AppLogInfo setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getBizType() {
        return bizType;
    }

    public AppLogInfo setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AppLogInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public AppLogInfo setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public AppLogInfo setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public AppLogInfo setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getParams() {
        return params;
    }

    public AppLogInfo setParams(String params) {
        this.params = params;
        return this;
    }

    public String getOperateType() {
        return operateType;
    }

    public AppLogInfo setOperateType(String operateType) {
        this.operateType = operateType;
        return this;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public AppLogInfo setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
        return this;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public AppLogInfo setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public AppLogInfo setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public AppLogInfo setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public AppLogInfo setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
        return this;
    }

    public String getClientDevice() {
        return clientDevice;
    }

    public AppLogInfo setClientDevice(String clientDevice) {
        this.clientDevice = clientDevice;
        return this;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public AppLogInfo setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public AppLogInfo setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

}

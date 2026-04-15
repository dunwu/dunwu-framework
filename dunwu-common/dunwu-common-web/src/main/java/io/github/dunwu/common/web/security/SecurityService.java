package io.github.dunwu.common.web.security;

import java.util.List;

/**
 * 安全服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-26
 */
public interface SecurityService {

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    Long getCurrentUserId();

    /**
     * 获取当前用户名称
     *
     * @return 当前用户名称
     */
    String getCurrentUsername();

    /**
     * 获取数据权限级别
     *
     * @return 级别
     */
    String getDataScopeType();

    /**
     * 获取当前用户的数据权限 ID 列表
     *
     * @return 数据权限 ID 列表
     */
    List<Long> getCurrentUserDataScope();

}

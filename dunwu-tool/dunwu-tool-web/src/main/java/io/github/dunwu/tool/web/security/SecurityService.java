package io.github.dunwu.tool.web.security;

import java.util.List;

/**
 * 安全服务
 *
 * @author peng.zhang
 * @date 2021-09-26
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
     */
    String getCurrentUsername();

    /**
     * 获取数据权限级别
     *
     * @return 级别
     */
    String getDataScopeType();

    /**
     * 获取当前用户的数据权限
     *
     * @return /
     */
    List<Long> getCurrentUserDataScope();

}

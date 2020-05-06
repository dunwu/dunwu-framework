package io.github.dunwu.data.core.constant;

/**
 * 常量
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
public class DunwuConstant {

    /**
     * 注册用户角色ID
     */
    public static final Long REGISTER_ROLE_ID = 2L;

    /**
     * 排序规则：降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 排序规则：升序
     */
    public static final String ORDER_ASC = "asc";

    /**
     * 前端页面路径前缀
     */
    public static final String VIEW_PREFIX = "febs/views/";

    /**
     * 验证码 Session Key
     */
    public static final String CODE_PREFIX = "dunwu_captcha_";

    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    public static final String[] VALID_FILE_TYPE = { "xlsx", "zip" };

    /**
     * 异步线程池名称
     */
    public static final String ASYNC_POOL = "dunwuAsyncThreadPool";

    /**
     * 开发环境
     */
    public static final String DEVELOP = "dev";

    /**
     * Windows 操作系统
     */
    public static final String SYSTEM_WINDOWS = "windows";

}

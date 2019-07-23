package io.github.dunwu.hehe.entity;

import java.time.LocalDateTime;
import io.github.dunwu.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登录信息表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LoginInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 上一次登录IP
     */
    private String lastLoginIp;

    /**
     * 上一次上线时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 上一次离线时间
     */
    private LocalDateTime lastLogoutTime;


}

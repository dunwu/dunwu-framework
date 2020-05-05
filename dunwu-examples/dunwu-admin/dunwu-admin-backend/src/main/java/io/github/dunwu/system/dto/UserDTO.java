package io.github.dunwu.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.dunwu.constant.DataStatus;
import io.github.dunwu.constant.GenderEnum;
import io.github.dunwu.tool.util.RegexUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 实现 Spring Security 框架的 {@link UserDetails}，管理用户身份信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Valid
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "UserDTO", description = "用户信息 DTO")
public class UserDTO implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    /** Spring Security 要求角色信息必须加这个前缀 */
    public static final String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    @ApiModelProperty(value = "ID", example = "0")
    private Integer id;

    @NotNull
    @Length(min = 6, max = 10)
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotNull
    @ApiModelProperty(value = "密码")
    private String password;

    @Range(min = 1, max = 2)
    @ApiModelProperty(value = "性别", example = "MALE")
    private GenderEnum sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Pattern(regexp = RegexUtil.MOBILE, message = "不是合法手机号")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private DataStatus status;

    @ApiModelProperty(value = "用户拥有的角色")
    private Collection<RoleDTO> roles;

    private String token;

    public UserDTO() {
        this.roles = Collections.emptyList();
    }

    /**
     * 获取账户角色信息（Spring Security 会调用此方法获取授权信息）
     *
     * @return String
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDTO role : roles) {
            authorities.add(new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + role.getCode()));
        }
        return authorities;
    }

    /**
     * 获取账户密码（Spring Security 会调用此方法获取密码）
     *
     * @return String
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 获取账户名（Spring Security 会调用此方法获取账户名）
     *
     * @return String
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 帐户是否过期（Spring Security 会调用此方法判断帐户是否过期）
     *
     * @return true / false
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 帐户是否被冻结（Spring Security 会调用此方法判断帐户是否被冻结）
     *
     * @return true / false
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 认证凭证是否过期（Spring Security 会调用此方法判断认证凭证是否被过期）
     * <p>
     * 一般有的密码要求性高的系统会使用到，比如每隔一段时间就要求用户重置密码
     *
     * @return true / false
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否有效（Spring Security 会调用此方法判断账号是否有效）
     *
     * @return true / false
     */
    @Override
    public boolean isEnabled() {
        return DataStatus.VALID.equals(this.status);
    }

}

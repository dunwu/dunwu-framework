package io.github.dunwu.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.admin.exception.AccountException;
import io.github.dunwu.admin.system.dto.RoleDTO;
import io.github.dunwu.admin.system.dto.UserDTO;
import io.github.dunwu.admin.system.entity.Role;
import io.github.dunwu.admin.system.entity.User;
import io.github.dunwu.admin.system.entity.UserRole;
import io.github.dunwu.admin.system.mapper.RoleMapper;
import io.github.dunwu.admin.system.mapper.UserMapper;
import io.github.dunwu.admin.system.mapper.UserRoleMapper;
import io.github.dunwu.admin.system.service.UserManager;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.annotation.Manager;
import io.github.dunwu.common.constant.ResultStatus;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
@Slf4j
@Manager("userDetailsService")
@RequiredArgsConstructor
public class UserManagerImpl extends UserServiceImpl implements UserManager {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;

    /**
     * 根据用户名获取用户完整信息（包含用户相关的角色、权限信息）
     * <p>
     * Spring Security 会根据此方法获取用户信息
     *
     * @param username 用户名
     * @return {@link UserDTO}
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
            .nested(i -> i.eq(User::getUsername, username)));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户名 %s 不存在", username));
        }

        return fullLoadUserInfoByKey(user);
    }

    /**
     * 根据用户名获取用户完整信息（包含用户相关的角色、权限信息）
     * <p>
     * Spring Security 会根据此方法获取用户信息
     *
     * @param key 找关键字：username/email/mobile（这些关键字在用户表中必须保证是惟一的）
     * @return {@link UserDTO}
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDTO loadUserByUniqueKey(String key) throws AccountException {

        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key 不能为空");
        }

        User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
            .nested(i -> i.eq(User::getUsername, key)
                .or().eq(User::getEmail, key)
                .or().eq(User::getMobile, key)));

        return fullLoadUserInfoByKey(user);
    }

    /**
     * 根据关键字快速查找用户信息（不含用户相关的角色、权限信息）
     *
     * @param key 查找关键字：username/email/mobile（这些关键字在用户表中必须保证是惟一的）
     * @return 返回用户信息 DTO {@link UserDTO}。注意：其中的 roles 为空
     */
    @Override
    public UserDTO fastLoadUserInfoByKey(String key) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda()
            .nested(i -> i.eq(User::getUsername, key)
                .or().eq(User::getEmail, key)
                .or().eq(User::getMobile, key)));
        if (user == null) {
            return null;
        } else {
            return BeanUtil.toBean(user, UserDTO.class);
        }
    }

    /**
     * 查询用户完整信息，包含用户关联的角色、权限信息
     *
     * @param user 要查询的用户实体
     * @return {@link UserDTO}
     * @throws AccountException
     */
    public UserDTO fullLoadUserInfoByKey(User user) throws AccountException {
        if (user == null) {
            throw new AccountException("账户不存在");
        }

        // 填充 UserDTO 基本信息
        UserDTO userDTO = BeanUtil.toBean(user, UserDTO.class);

        // 查询用户角色关联信息
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<>(userRole));
        if (CollectionUtil.isEmpty(userRoleList)) {
            throw new AccountException("账户没有绑定角色");
        }

        // 查询相关角色信息
        List<RoleDTO> roles = new ArrayList<>();
        for (UserRole item : userRoleList) {
            Role role = roleMapper.selectById(item.getRoleId());
            if (role != null) {
                RoleDTO roleDTO = BeanUtil.toBean(role, RoleDTO.class);
                roles.add(roleDTO);
            }
        }

        userDTO.setRoles(roles);
        return userDTO;
    }

    @Override
    public BaseResult register(UserDTO userDTO) {
        DataResult<Boolean> dataResult = isUserExists(userDTO);
        if (dataResult.getData()) {
            return BaseResult.fail(ResultStatus.DATA_ERROR.getCode(), "用户已存在");
        }

        User user = BeanUtil.toBean(userDTO, User.class);
        if (userMapper.insert(user) > 0) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(2); // 默认注册用户的角色为 2（user）
            userRoleMapper.insert(userRole);
            return BaseResult.success();
        } else {
            return BaseResult.fail(ResultStatus.DATA_ERROR);
        }
    }

    @Override
    public DataResult<Boolean> isUserExists(UserDTO userDTO) {
        try {
            if (StringUtils.isNotBlank(userDTO.getUsername())) {
                loadUserByUniqueKey("username");
            }

            if (StringUtils.isNotBlank(userDTO.getEmail())) {
                loadUserByUniqueKey("email");
            }
            return DataResult.success(false);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return DataResult.success(true);
        }
    }

}

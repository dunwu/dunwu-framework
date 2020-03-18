package io.github.dunwu.quickstart.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.annotation.Manager;
import io.github.dunwu.common.constant.AppResulstStatus;
import io.github.dunwu.quickstart.user.dto.RoleDTO;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.github.dunwu.quickstart.user.entity.Role;
import io.github.dunwu.quickstart.user.entity.User;
import io.github.dunwu.quickstart.user.entity.UserRole;
import io.github.dunwu.quickstart.user.mapper.RoleMapper;
import io.github.dunwu.quickstart.user.mapper.UserMapper;
import io.github.dunwu.quickstart.user.mapper.UserRoleMapper;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
@Slf4j
@Manager
@AllArgsConstructor
public class UserManagerImpl implements UserManager {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    @Override
    public UserDTO getByUsername(String username) {
        User query = new User();
        query.setUsername(username);
        User user = userMapper.selectOne(new QueryWrapper<>(query));
        UserDTO userDTO = BeanUtil.toBean(user, UserDTO.class);

        // 查询用户角色列表
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        List<UserRole> userRoleList = userRoleMapper
            .selectList(new QueryWrapper<>(userRole));
        if (CollectionUtil.isEmpty(userRoleList)) {
            return userDTO;
        }

        // 查询角色列表
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
    @Transactional(rollbackFor = Exception.class)
    public BaseResult register(UserDTO userDTO) {
        DataResult<Boolean> dataResult = isUserExists(userDTO);
        if (dataResult.getData()) {
            return BaseResult.fail(AppResulstStatus.ERROR_DB.getCode(), "用户已存在");
        }

        User user = BeanUtil.toBean(userDTO, User.class);
        if (userMapper.insert(user) > 0) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(2); // 默认注册用户的角色为 2（user）
            userRoleMapper.insert(userRole);
            return BaseResult.success();
        } else {
            return BaseResult.fail(AppResulstStatus.ERROR_DB);
        }
    }

    @Override
    public DataResult<Boolean> isUserExists(UserDTO userDTO) {
        try {
            if (StringUtils.isNotBlank(userDTO.getUsername())) {
                checkUserExists("username", userDTO.getUsername());
            }

            if (StringUtils.isNotBlank(userDTO.getEmail())) {
                checkUserExists("email", userDTO.getEmail());
            }
            return DataResult.success(false);
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return DataResult.success(true);
        }
    }

    public void checkUserExists(String key, String value) throws IllegalArgumentException {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("信息为空");
        }

        if ("username".equalsIgnoreCase(key) || "email".equalsIgnoreCase(key)) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(key, value);
            Integer count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new IllegalArgumentException(value + " 已存在");
            }
        }
    }

}

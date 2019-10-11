package io.github.dunwu.quickstart.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.annotation.Manager;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.user.dto.RoleDTO;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.github.dunwu.quickstart.user.entity.Role;
import io.github.dunwu.quickstart.user.entity.User;
import io.github.dunwu.quickstart.user.entity.UserRole;
import io.github.dunwu.quickstart.user.mapper.RoleMapper;
import io.github.dunwu.quickstart.user.mapper.UserMapper;
import io.github.dunwu.quickstart.user.mapper.UserRoleMapper;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.util.mapper.BeanMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
@Slf4j
@Manager
@AllArgsConstructor
public class UserManagerImpl implements UserManager {

	private final ObjectMapper objectMapper;

	private final UserMapper userMapper;

	private final RoleMapper roleMapper;

	private final UserRoleMapper userRoleMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DataResult<Map<String, String>> register(UserDTO userDTO) {
		if (userDTO == null) {
			return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER.getCode(), AppCode.ERROR_PARAMETER.getTemplate(),
					"userDTO", "null");
		}

		User user = BeanMapper.map(userDTO, User.class);
		if (userMapper.insert(user) > 0) {
			return ResultUtil.failDataResult(AppCode.ERROR_DB);
		}

		Map<String, String> map = new HashMap<>(1);
		map.put("currentAuthority", "user");
		return ResultUtil.successDataResult(map);
	}

	@Override
	public UserDTO getByUsername(String username) {
		User query = new User();
		query.setUsername(username);
		User user = userMapper.selectOne(new QueryWrapper<>(query));
		UserDTO userDTO = BeanMapper.map(user, UserDTO.class);

		// 查询用户角色列表
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<>(userRole));
		if (CollectionUtils.isEmpty(userRoleList)) {
			return userDTO;
		}

		// 查询角色列表
		List<RoleDTO> roles = new ArrayList<>();
		for (UserRole item : userRoleList) {
			Role role = roleMapper.selectById(item.getRoleId());
			if (role != null) {
				RoleDTO roleDTO = BeanMapper.map(role, RoleDTO.class);
				roles.add(roleDTO);
			}
		}
		userDTO.setRoles(roles);

		return userDTO;
	}

}

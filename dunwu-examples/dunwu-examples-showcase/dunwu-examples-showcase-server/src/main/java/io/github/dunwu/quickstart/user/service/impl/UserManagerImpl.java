package io.github.dunwu.quickstart.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dunwu.annotation.Manager;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.user.dto.LoginDTO;
import io.github.dunwu.quickstart.user.dto.UserDTO;
import io.github.dunwu.quickstart.user.entity.Login;
import io.github.dunwu.quickstart.user.entity.User;
import io.github.dunwu.quickstart.user.mapper.LoginMapper;
import io.github.dunwu.quickstart.user.mapper.UserMapper;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.util.mapper.BeanMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

	private final LoginMapper loginMapper;

	private final UserMapper userMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DataResult<Map<String, String>> register(LoginDTO loginDTO) {
		if (loginDTO == null) {
			return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER.getCode(),
					AppCode.ERROR_PARAMETER.getTemplate(), "userDTO", "null");
		}

		Login login = BeanMapper.map(loginDTO, Login.class);
		if (loginMapper.insert(login) > 0) {
			return ResultUtil.failDataResult(AppCode.ERROR_DB);
		}

		User user = BeanMapper.map(loginDTO, User.class);
		if (userMapper.insert(user) > 0) {
			return ResultUtil.failDataResult(AppCode.ERROR_DB);
		}

		Map<String, String> map = new HashMap<>(1);
		map.put("currentAuthority", "user");
		return ResultUtil.successDataResult(map);
	}

	@Override
	public DataResult<UserDTO> login(HttpSession session, Map<String, String> map) {
		String nickname = map.get("nickname");
		String password = map.get("password");

		Login loginInfoQuery = new Login();
		loginInfoQuery.setNickname(nickname);
		Login login = loginMapper.selectOne(new QueryWrapper<>(loginInfoQuery));
		if (login == null) {
			return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
		}

		if (password.equals(login.getPassword())) {
			String sessionId = session.getId();
			User userInfoQuery = new User();
			userInfoQuery.setNickname(login.getNickname());
			User user = userMapper.selectOne(new QueryWrapper<>(userInfoQuery));
			if (user == null) {
				return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
			}

			UserDTO userDTO = BeanMapper.map(user, UserDTO.class);
			ArrayList<String> roles = new ArrayList<>();
			roles.add("admin");
			userDTO.setRoles(roles);
			userDTO.setCurrentAuthority("admin");
			userDTO.setToken(sessionId);

			try {
				session.setAttribute(sessionId, objectMapper.writeValueAsString(userDTO));
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return ResultUtil.successDataResult(userDTO);
		}
		return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
	}

	@Override
	public DataResult<UserDTO> getCurrentUserInfo(HttpSession session) {
		String value = (String) session.getAttribute(session.getId());
		if (value == null) {
			return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
		}
		UserDTO userDTO = null;
		try {
			userDTO = objectMapper.readValue(value, UserDTO.class);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ResultUtil.successDataResult(userDTO);
	}

}

package io.github.dunwu.quickstart.user.service;

import io.github.dunwu.core.DataResult;
import io.github.dunwu.quickstart.user.dto.LoginDTO;
import io.github.dunwu.quickstart.user.dto.UserDTO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
public interface UserManager {

	DataResult<Map<String, String>> register(LoginDTO registerUserDTO);

	DataResult<UserDTO> login(HttpSession session, Map<String, String> map);

	DataResult<UserDTO> getCurrentUserInfo(HttpSession session);

}

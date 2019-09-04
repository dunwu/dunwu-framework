package io.github.dunwu.quickstart.user.service;

import io.github.dunwu.core.DataResult;
import io.github.dunwu.quickstart.user.dto.LoginInfoDTO;
import io.github.dunwu.quickstart.user.dto.UserInfoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
public interface UserManager {

    DataResult<Map<String, String>> register(LoginInfoDTO registerUserDTO);

    DataResult<UserInfoDTO> login(HttpSession session, Map<String, String> map);

    DataResult<UserInfoDTO> getCurrentUserInfo(HttpSession session);
}

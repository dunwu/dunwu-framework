package io.github.dunwu.quickstart.user.service;

import io.github.dunwu.core.DataResult;
import io.github.dunwu.quickstart.user.dto.UserDTO;

import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
public interface UserManager {

	UserDTO getByUsername(String username);

	DataResult<Map<String, String>> register(UserDTO userDTO);

}

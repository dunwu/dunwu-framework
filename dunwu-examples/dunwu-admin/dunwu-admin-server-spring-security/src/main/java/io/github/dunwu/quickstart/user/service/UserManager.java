package io.github.dunwu.quickstart.user.service;

import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.quickstart.user.dto.UserDTO;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
public interface UserManager {

    UserDTO getByUsername(String username);

    BaseResult register(UserDTO userDTO);

    DataResult<Boolean> isUserExists(UserDTO userDTO);

}

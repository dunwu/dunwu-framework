package io.github.dunwu.admin.system.service;

import io.github.dunwu.admin.exception.AccountException;
import io.github.dunwu.admin.system.dto.UserDTO;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataResult;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
public interface UserManager extends UserService, UserDetailsService {

    UserDTO loadUserByUniqueKey(String key) throws AccountException;

    @Transactional(rollbackFor = Exception.class)
    BaseResult register(UserDTO userDTO);

    DataResult<Boolean> isUserExists(UserDTO userDTO);

}

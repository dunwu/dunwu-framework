package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.annotation.Manager;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.user.dto.LoginInfoDTO;
import io.github.dunwu.quickstart.user.dto.UserInfoDTO;
import io.github.dunwu.quickstart.user.entity.LoginInfo;
import io.github.dunwu.quickstart.user.entity.UserInfo;
import io.github.dunwu.quickstart.user.mapper.dao.LoginInfoDao;
import io.github.dunwu.quickstart.user.mapper.dao.UserInfoDao;
import io.github.dunwu.quickstart.user.service.UserManager;
import io.github.dunwu.util.mapper.BeanMapper;
import io.github.dunwu.web.util.CookieUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static io.github.dunwu.quickstart.user.controller.UserController.TOKEN_KEY;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
@Slf4j
@Manager
@AllArgsConstructor
public class UserManagerImpl implements UserManager {

    private final LoginInfoDao loginInfoDao;
    private final UserInfoDao userInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataResult<Map<String, String>> register(LoginInfoDTO loginInfoDTO) {
        if (loginInfoDTO == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER.getCode(), AppCode.ERROR_PARAMETER.getTemplate(),
                                             "userInfoDTO", "null");
        }

        LoginInfo loginInfo = BeanMapper.map(loginInfoDTO, LoginInfo.class);
        if (!loginInfoDao.save(loginInfo)) {
            return ResultUtil.failDataResult(AppCode.ERROR_DB);
        }

        UserInfo userInfo = BeanMapper.map(loginInfoDTO, UserInfo.class);
        if (!userInfoDao.save(userInfo)) {
            return ResultUtil.failDataResult(AppCode.ERROR_DB);
        }

        Map<String, String> map = new HashMap<>(1);
        map.put("currentAuthority", "user");
        return ResultUtil.successDataResult(map);
    }

    @Override
    public DataResult<UserInfoDTO> login(HttpServletRequest request, HttpServletResponse response,
                                         Map<String, String> map) {
        String nickname = map.get("nickname");
        String password = map.get("password");

        LoginInfo loginInfoQuery = new LoginInfo();
        loginInfoQuery.setNickname(nickname);
        LoginInfo loginInfo = loginInfoDao.getOne(loginInfoQuery);
        if (loginInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
        }

        if (password.equals(loginInfo.getPassword())) {

            String sessionId = request.getSession(true)
                                      .getId();
            request.getSession()
                   .setAttribute(TOKEN_KEY, sessionId);
            log.info("sessionId = {}", sessionId);
            CookieUtil.addCookie(request, response, "SESSION", sessionId);

            UserInfo userInfoQuery = new UserInfo();
            userInfoQuery.setNickname(loginInfo.getNickname());
            UserInfo userInfo = userInfoDao.getOne(userInfoQuery);
            if (userInfo == null) {
                return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
            }

            UserInfoDTO userInfoDTO = BeanMapper.map(userInfo, UserInfoDTO.class);
            userInfoDTO.setRoles("admin");
            userInfoDTO.setCurrentAuthority("admin");
            userInfoDTO.setToken(sessionId);
            return ResultUtil.successDataResult(userInfoDTO);
        }
        return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
    }
}

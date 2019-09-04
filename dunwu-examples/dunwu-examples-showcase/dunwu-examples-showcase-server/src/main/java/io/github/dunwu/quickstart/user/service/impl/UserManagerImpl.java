package io.github.dunwu.quickstart.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public DataResult<UserInfoDTO> login(HttpSession session, Map<String, String> map) {
        String nickname = map.get("nickname");
        String password = map.get("password");

        LoginInfo loginInfoQuery = new LoginInfo();
        loginInfoQuery.setNickname(nickname);
        LoginInfo loginInfo = loginInfoDao.getOne(loginInfoQuery);
        if (loginInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
        }

        if (password.equals(loginInfo.getPassword())) {
            String sessionId = session.getId();
            UserInfo userInfoQuery = new UserInfo();
            userInfoQuery.setNickname(loginInfo.getNickname());
            UserInfo userInfo = userInfoDao.getOne(userInfoQuery);
            if (userInfo == null) {
                return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
            }

            UserInfoDTO userInfoDTO = BeanMapper.map(userInfo, UserInfoDTO.class);
            ArrayList<String> roles = new ArrayList<>();
            roles.add("admin");
            userInfoDTO.setRoles(roles);
            userInfoDTO.setCurrentAuthority("admin");
            userInfoDTO.setToken(sessionId);

            try {
                session.setAttribute(sessionId, objectMapper.writeValueAsString(userInfoDTO));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return ResultUtil.successDataResult(userInfoDTO);
        }
        return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
    }

    @Override
    public DataResult<UserInfoDTO> getCurrentUserInfo(HttpSession session) {
        String value = (String) session.getAttribute(session.getId());
        if (value == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_AUTH);
        }
        UserInfoDTO userInfoDTO = null;
        try {
            userInfoDTO = objectMapper.readValue(value, UserInfoDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.successDataResult(userInfoDTO);
    }
}

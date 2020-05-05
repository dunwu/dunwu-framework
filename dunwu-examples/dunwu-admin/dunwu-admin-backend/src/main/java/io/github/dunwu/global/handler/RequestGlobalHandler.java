package io.github.dunwu.global.handler;

import cn.hutool.core.util.StrUtil;
import com.google.common.net.HttpHeaders;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.GlobalException;
import io.github.dunwu.data.core.constant.ResultStatus;
import io.github.dunwu.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

/**
 * 系统全局异常统一处理接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://blog.csdn.net/hao_kkkkk/article/details/80538955">Springboot项目全局异常统一处理</a>
 * @since 2019-09-11
 */
@ControllerAdvice
public class RequestGlobalHandler {

    public static final String APP_VERSION = "0.5.1";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("appVersion", APP_VERSION);
    }

    /**
     * 统一处理请求参数校验异常(普通传参)
     *
     * @param e ConstraintViolationException
     * @return {@link BaseResult}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ConstraintViolationException.class })
    public BaseResult handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StrUtil.split(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return BaseResult.fail(ResultStatus.HTTP_BAD_REQUEST.getCode(), message.toString());
    }

    /**
     * 处理参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return {@link BaseResult}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    private BaseResult handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        StringBuilder sb = new StringBuilder();
        sb.append("参数错误：\n");
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sb.append(((FieldError) error).getField() + " ");
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }
        return BaseResult.fail(ResultStatus.SYSTEM_ERROR_PARAM.getCode(), sb.toString());
    }

    // ------------------------------------------------------------------------------
    // 认证、授权异常
    // ------------------------------------------------------------------------------

    /**
     * 处理认证异常
     *
     * @param e AuthenticationException
     * @return {@link BaseResult}
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public BaseResult handleAuthenticationException(final AuthenticationException e) {
        log.error("Exception: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理未授权异常（登录状态下，无权限会触发）
     *
     * @param e AccessDeniedException
     * @return {@link BaseResult}
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = AccessDeniedException.class)
    public BaseResult handleAccessDeniedException(final AccessDeniedException e) {
        log.error("Exception: {}, message: {}", e.getClass().getCanonicalName(), e.getLocalizedMessage());
        return BaseResult.fail(ResultStatus.HTTP_UNAUTHORIZED.getCode(), e.getLocalizedMessage());
    }

    /**
     * 处理<code>未处理异常</code>
     *
     * @param e MethodArgumentNotValidException
     * @return {@link BaseResult} / {@link ModelAndView}
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handler(HttpServletRequest request, Exception e) throws Exception {

        log.error("捕获未处理异常", e);

        BaseResult baseResult;
        if (e instanceof GlobalException) {
            baseResult = BaseResult.fail(ResultStatus.SYSTEM_ERROR);
        } else {
            baseResult = BaseResult.fail(ResultStatus.SYSTEM_ERROR.getCode(), e.getMessage());
        }

        WebConstant.ResponseType responseType = getResponseMode(request);
        if (responseType == WebConstant.ResponseType.HTTP_REPONSE) {
            return baseResult;
        } else {
            Map<String, Object> map = new HashMap<>(4);
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("requestURL", request.getRequestURL());
            map.put("message", e.getMessage());
            map.put("stackTrace", e.getStackTrace());
            return new ModelAndView("error", map);
        }
    }

    private WebConstant.ResponseType getResponseMode(HttpServletRequest request) {
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        String xRequestedWith = request.getHeader(HttpHeaders.X_REQUESTED_WITH);

        if (StrUtil.isNotBlank(contentType)
            && contentType.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        if (StrUtil.isNotBlank(accept)
            && accept.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        if (StrUtil.isNotBlank(xRequestedWith)
            && HttpHeaders.X_REQUESTED_WITH.equalsIgnoreCase(xRequestedWith)) {
            return WebConstant.ResponseType.HTTP_REPONSE;
        }

        return WebConstant.ResponseType.MODEL_AND_VIEW;
    }

}

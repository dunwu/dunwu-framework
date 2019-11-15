package io.github.dunwu.quickstart.global.handler;

import com.google.common.net.HttpHeaders;
import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.AppException;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtils;
import io.github.dunwu.web.constant.WebConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统全局异常统一处理接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href= "https://blog.csdn.net/hao_kkkkk/article/details/80538955">Springboot项目全局异常统一处理</a>
 * @since 2019-09-11
 */
@ControllerAdvice
public class RequestGlobalHandler {

	public static final String APP_VERSION = "0.4.9-SNAPSHOT";

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
	 * 处理<code>未处理异常</code>
	 *
	 * @param e MethodArgumentNotValidException
	 * @return {@link BaseResult} / {@link ModelAndView}
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Object handler(HttpServletRequest request, Exception e) {

		log.error("捕获未处理异常", e);

		BaseResult baseResult;
		if (e instanceof MethodArgumentNotValidException) {
			baseResult = resolveMethodArgumentNotValidException(
				(MethodArgumentNotValidException) e);
		} else if (e instanceof AppException) {
			baseResult = ResultUtils.failBaseResult(AppCode.ERROR_SYSTEM);
		} else {
			baseResult = ResultUtils.failBaseResult(AppCode.ERROR_SYSTEM.getCode(), e.getMessage());
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

	/**
	 * 处理参数校验异常
	 *
	 * @param e MethodArgumentNotValidException
	 * @return {@link BaseResult}
	 */
	private BaseResult resolveMethodArgumentNotValidException(
		final MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder();
		sb.append("出现参数错误：\n");
		for (ObjectError error : e.getBindingResult().getAllErrors()) {
			sb.append(((FieldError) error).getField() + " ");
			sb.append(error.getDefaultMessage());
			sb.append("\n");
		}
		return ResultUtils.failBaseResult(AppCode.ERROR_PARAMETER.getCode(),
			sb.toString());
	}

	private WebConstant.ResponseType getResponseMode(HttpServletRequest request) {
		String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
		String accept = request.getHeader(HttpHeaders.ACCEPT);
		String xRequestedWith = request.getHeader(HttpHeaders.X_REQUESTED_WITH);

		if (StringUtils.isNotBlank(contentType)
			&& contentType.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
			return WebConstant.ResponseType.HTTP_REPONSE;
		}

		if (StringUtils.isNotBlank(accept)
			&& accept.contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
			return WebConstant.ResponseType.HTTP_REPONSE;
		}

		if (StringUtils.isNotBlank(xRequestedWith)
			&& HttpHeaders.X_REQUESTED_WITH.equalsIgnoreCase(xRequestedWith)) {
			return WebConstant.ResponseType.HTTP_REPONSE;
		}

		return WebConstant.ResponseType.MODEL_AND_VIEW;
	}

}

package io.github.dunwu.web.resolver;

import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.AppException;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.util.mapper.JsonMapper;
import io.github.dunwu.web.constant.WebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * @author xiaolongzuo
 */
public class JsonHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {

	public static final Logger log = LoggerFactory
			.getLogger(JsonHandlerExceptionResolver.class);

	private static final String KEY = "exception";

	private View errorView = new ErrorView();

	private MessageSource messageSource;

	public JsonHandlerExceptionResolver(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		ExceptionMetadata exceptionMetadata = logException(handler, request, exception);
		BaseResult result = buildJsonResponse(request, exception,
				exceptionMetadata.getErrorId());
		ModelAndView view = new ModelAndView(errorView);
		view.addObject(KEY, result);
		return view;
	}

	private BaseResult buildJsonResponse(HttpServletRequest request, Throwable throwable,
			String errorId) {
		while (throwable.getCause() != null) {
			throwable = throwable.getCause();
		}
		String code;
		Object[] args = new Object[0];
		Object data = null;
		if (throwable instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) throwable;
			code = "YA-400";
			data = new ArrayList<>();
			for (ConstraintViolation<?> constraintViolation : constraintViolationException
					.getConstraintViolations()) {
				Map<String, String> map = new HashMap<>(2);
				map.put("message", constraintViolation.getMessage());
				map.put("propertyPath", constraintViolation.getPropertyPath().toString());
				((List) data).add(map);
			}
		}
		else if (throwable instanceof AppException) {
			AppException exception = (AppException) throwable;
			code = exception.getResult().getCode();
		}
		else {
			code = AppCode.FAIL.getCode();
		}
		Locale locale = (Locale) request.getSession()
				.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		String message;
		try {
			message = messageSource.getMessage(code, args, locale);
		}
		catch (NoSuchMessageException e) {
			if (throwable instanceof AppException && throwable.getMessage() != null) {
				message = throwable.getMessage();
			}
			else {
				throw e;
			}
		}
		return ResultUtil.failBaseResult(code, message);
	}

	private ExceptionMetadata logException(Object handler, HttpServletRequest request,
			Exception exception) {
		ExceptionMetadata exceptionMetadata = new ExceptionMetadata();
		try {
			buildExceptionMetadata(exceptionMetadata, handler, request);
			if (exceptionMetadata.isHandlerMethod()) {
				Logger logger = LoggerFactory.getLogger(exceptionMetadata.getBeanType());
				logger.error("Error id is :" + exceptionMetadata.getErrorId());
				logger.error("RequestMapping is:");
				logger.error(exceptionMetadata.getRequestMapping());
				logger.error("HandlerMethod is:");
				logger.error(exceptionMetadata.getMethodName());
				logger.error("ParameterMap is:");
				logger.error(exceptionMetadata.getParameterMap(), exception);
			}
			else {
				log.error(handler + " execute failed, error id is:"
						+ exceptionMetadata.getErrorId(), exception);
			}
		}
		catch (Exception e) {
			log.error(handler + " execute failed.", exception);
		}
		return exceptionMetadata;
	}

	private void buildExceptionMetadata(ExceptionMetadata exceptionMetadata,
			Object handler, HttpServletRequest request) {
		if (handler != null && HandlerMethod.class.isAssignableFrom(handler.getClass())) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Class<?> beanType = handlerMethod.getBeanType();
			String methodName = handlerMethod.getMethod().getName();
			RequestMapping controllerRequestMapping = beanType
					.getDeclaredAnnotation(RequestMapping.class);
			String classMapping = "";
			if (controllerRequestMapping != null) {
				classMapping = controllerRequestMapping.value()[0];
			}
			RequestMapping methodRequestMapping = handlerMethod
					.getMethodAnnotation(RequestMapping.class);
			String methodMapping = "";
			if (methodRequestMapping != null) {
				methodMapping = methodRequestMapping.value()[0];
			}

			if (!methodMapping.startsWith(WebConstant.PATH_SEPARATOR)) {
				methodMapping = "/" + methodMapping;
			}
			exceptionMetadata.setHandlerMethod(true);
			exceptionMetadata.setBeanType(beanType);
			exceptionMetadata.setMethodName(methodName);
			exceptionMetadata.setClassMapping(classMapping);
			exceptionMetadata.setMethodMapping(methodMapping);
			exceptionMetadata.setParameterMap(request.getParameterMap());
		}
		else {
			exceptionMetadata.setHandlerMethod(false);
		}
	}

	@Override
	public int getOrder() {
		return -10;
	}

	private static class ExceptionMetadata {

		private String errorId = UUID.randomUUID().toString();

		private String methodName;

		private String classMapping;

		private String methodMapping;

		private Class<?> beanType;

		private Map<String, String[]> parameterMap;

		private boolean isHandlerMethod;

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public void setClassMapping(String classMapping) {
			this.classMapping = classMapping;
		}

		public void setMethodMapping(String methodMapping) {
			this.methodMapping = methodMapping;
		}

		public void setBeanType(Class<?> beanType) {
			this.beanType = beanType;
		}

		public void setParameterMap(Map<String, String[]> parameterMap) {
			this.parameterMap = parameterMap;
		}

		public boolean isHandlerMethod() {
			return isHandlerMethod;
		}

		public void setHandlerMethod(boolean handlerMethod) {
			isHandlerMethod = handlerMethod;
		}

		public String getErrorId() {
			return errorId;
		}

		public String getRequestMapping() {
			return classMapping + methodMapping;
		}

		public String getMethodName() {
			return getBeanType().getSimpleName() + "." + methodName + "()";
		}

		public Class<?> getBeanType() {
			return beanType;
		}

		public String getParameterMap() {
			return JsonMapper.defaultMapper().toJson(parameterMap);
		}

	}

	private static class ErrorView implements View {

		private static final String CONTENT_TYPE = "application/json;charset=utf-8";

		@Override
		public String getContentType() {
			return CONTENT_TYPE;
		}

		@Override
		public void render(Map<String, ?> model, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			response.setContentType(CONTENT_TYPE);
			String json = JsonMapper.defaultMapper().toJson(model.get(KEY));
			response.getOutputStream().write(json.getBytes());
			response.getOutputStream().flush();
		}

	}

}

package com.bzb.atjob.config;

// import lombok.extern.slf4j.Slf4j;

import javax.validation.ValidationException;

import com.bzb.atjob.common.vo.ApiResult;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 */
@ControllerAdvice
// @Slf4j
public class ExceptionsHandler {
	/**
	 * 验证异常处理
	 */
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	public ApiResult<Object> ValidationExceptionHandler(ValidationException exception) {
		// log.error("[ValidationException]:{}", exception.getMessage());
		return ApiResult.validationFailMsg(exception.getMessage());
	}
}

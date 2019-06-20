/**
 * 
 */
package com.hottop.core.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * 
 *
 *
 */
public interface ValidateCodeProcessor {

	/**
	 * 验证码放入redis 时的前缀
	 */
	String IMAGE_CODE_KEY_PREFIX = "IMAGE_CODE_KEY_PREFIX_";

	/**
	 * 验证码放入 redis的前缀
	 */
	String SMS_CODE_KEY_PREFIX = "SMS_CODE_KEY_PREFIX_";

	/**
	 * 创建校验码
	 * 
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}

/**
 * 
 */
package com.hottop.core.security.validate.code.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.config.SmsConfig;
import com.hottop.core.security.properties.SecurityConstants;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.security.validate.code.*;
import com.hottop.core.security.validate.code.image.ImageCode;
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 *
 *
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		String type = getValidateCodeType(request).toString().toLowerCase();
		if(type.startsWith("sms")) type = "sms";
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		if (validateCode instanceof ImageCode ) {
			logger.info("保存图片验证码到session：{}", validateCode.getCode());
			sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
		}else{
			String redis_key = getRedisKey(request);
			logger.info("保存短信验证码：{},{}", BaseConfiguration.generalGson().toJson(validateCode), validateCode.getCode());
			//设置过期时间
			redisTemplate.opsForValue().set(redis_key, BaseConfiguration.generalGson().toJson(validateCode));
			redisTemplate.expire(redis_key, Long.parseLong(SmsConfig.smsCodeExpireTime), TimeUnit.SECONDS);
		}
	}

	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}

	/**
	 * 构建短信登录 放入redis时的key
	 * @param request
	 * @return
	 */
	private String getRedisKey(ServletWebRequest request) {
		String tel = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
		String redis_key = tel + ESmsCodeType.Login;
		logger.info("构建短信登录 放入redis时的key:{},手机号：{}", redis_key, tel);
		return redis_key;
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

		ValidateCodeType processorType = getValidateCodeType(request);
		String redisKey = null;
		ValidateCode code = null;
		if(processorType.name().equals(ValidateCodeType.SMSLOGIN.name())){
			redisKey = getRedisKey(request);
			code = BaseConfiguration.generalGson().fromJson((String)redisTemplate.opsForValue().get(redisKey), ValidateCode.class);
		}else if(processorType.name().equals(ValidateCodeType.IMAGE.name())){
			redisKey = getSessionKey(request);
			code = (C) sessionStrategy.getAttribute(request, getSessionKey(request));
		}

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (code == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (code.isExpried()) {
			//sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equals(code.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}

		//sessionStrategy.removeAttribute(request, sessionKey);
	}

}

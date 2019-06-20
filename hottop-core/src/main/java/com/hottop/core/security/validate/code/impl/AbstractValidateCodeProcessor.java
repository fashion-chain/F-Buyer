/**
 * 
 */
package com.hottop.core.security.validate.code.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.config.SmsConfig;
import com.hottop.core.security.properties.SecurityConstants;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.security.validate.code.*;
import com.hottop.core.security.validate.code.image.ImageCode;
import com.hottop.core.security.validate.code.sms.AliSmsCodeSender;
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import com.hottop.core.security.validate.code.sms.SmsCodeSender;
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

	private final static Logger logger = LoggerFactory.getLogger(AbstractValidateCodeProcessor.class);

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private SecurityProperties securityProperties;

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Autowired
	private AliSmsCodeSender aliSmsCodeSender;

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
		final C generate = (C) validateCodeGenerator.generate(request);
		logger.info("生成验证码：------ {} --------,验证码类型：{}", generate.getCode(), type);
		return generate;
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		if (validateCode instanceof ImageCode ) {
			String imageKey = getImageKey(request);
			redisTemplate.opsForValue().set(imageKey, BaseConfiguration.generalGson().toJson(
					new ValidateCode(validateCode.getCode(),validateCode.getExpireTime())));
			redisTemplate.expire(imageKey, securityProperties.getCode().getImage().getExpireIn(), TimeUnit.SECONDS);
			logger.info("保存图片验证码到redis,imageCode:{}, key:{}, expireTime:{}秒", validateCode.getCode(), imageKey, securityProperties.getCode().getImage().getExpireIn());
			((ImageCode) validateCode).setImage_key(imageKey);
		}else{
			String redis_key = getRedisKey(request);
			//设置过期时间
			redisTemplate.opsForValue().set(redis_key, BaseConfiguration.generalGson().toJson(validateCode));
			redisTemplate.expire(redis_key, Long.parseLong(SmsConfig.smsCodeExpireTime), TimeUnit.SECONDS);
			logger.info("保存短信验证码到redis中：smsCode:{}, key:{}, smsExpireTime", validateCode.getCode(), redis_key, SmsConfig.smsCodeExpireTime);
		}
	}

	/**
	 * 构建图片验证码 放入redis 时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getImageKey(ServletWebRequest request) {
		if (StringUtils.isNotBlank(request.getHeader("image_key"))) {
			return request.getHeader("image_key");
		}
		return IMAGE_CODE_KEY_PREFIX + UUID.randomUUID().toString().replace("-", "").toString();
	}

	/**
	 * 构建短信登录 放入redis时的key
	 * @param request
	 * @return
	 */
	public String getRedisKey(ServletWebRequest request) {
		String tel = request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
		String redis_key = aliSmsCodeSender.getRedisKey(tel, ESmsCodeType.Login);
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
		if(processorType.name().equals(ValidateCodeType.SMSLOGIN.name())){ //短信验证码
			redisKey = getRedisKey(request);
			code = BaseConfiguration.generalGson().fromJson((String)redisTemplate.opsForValue().get(redisKey), ValidateCode.class);
		}else if(processorType.name().equals(ValidateCodeType.IMAGE.name())){ //图片验证码
			redisKey = getImageKey(request);
			code = BaseConfiguration.generalGson().fromJson((String)redisTemplate.opsForValue().get(redisKey), ValidateCode.class);
		}
		logger.info("从redis数据库中获取的验证码：{}", (String)redisTemplate.opsForValue().get(redisKey));

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

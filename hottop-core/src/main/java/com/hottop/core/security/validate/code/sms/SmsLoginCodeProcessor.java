/**
 * 
 */
package com.hottop.core.security.validate.code.sms;

import com.hottop.core.security.properties.SecurityConstants;
import com.hottop.core.security.validate.code.ValidateCode;
import com.hottop.core.security.validate.code.impl.AbstractValidateCodeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 短信验证码处理器
 *
 */
@Component("smsloginValidateCodeProcessor")
public class SmsLoginCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		//
		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        logger.info("短信登录，发送短信手机号：{}", mobile);
        smsCodeSender.send(mobile, validateCode.getCode(), ESmsCodeType.Login);
	}

}

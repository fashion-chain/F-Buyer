/**
 * 
 */
package com.hottop.core.security.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see com.imooc.security.core.validate.code.sms.SmsCodeSender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String mobile, String code, ESmsCodeType smsCodeType) {
		logger.info("向手机:[{}],发送短信验证码：[{}],短信类型：[{}]", mobile, code, smsCodeType.name());
	}

}

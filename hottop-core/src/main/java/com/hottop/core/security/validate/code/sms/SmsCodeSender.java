/**
 * 
 */
package com.hottop.core.security.validate.code.sms;

/**
 *
 *
 */
public interface SmsCodeSender {
	
	void send(String mobile, String code, ESmsCodeType type);

	String getRedisKey(String mobile, ESmsCodeType type);
}

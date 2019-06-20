/**
 * 
 */
package com.hottop.core.security.properties;

import lombok.Data;

/**
 *
 *
 */
@Data
public class SmsCodeProperties {
	
	private int length = 6;
	private int expireIn = 60;
	
	private String url;

	private String currentCompanyName;
	private boolean isAliSmsOpen = false;


}

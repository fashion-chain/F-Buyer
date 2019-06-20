/**
 * 
 */
package com.hottop.core.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 *
 */
@ConfigurationProperties(prefix = "ht.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();

	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	private SocialProperties social = new SocialProperties();

	private ApiProperties api = new ApiProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}

	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties social) {
		this.social = social;
	}

	public ApiProperties getApi() {
		return api;
	}

	public void setApi(ApiProperties api) {
		this.api = api;
	}
}


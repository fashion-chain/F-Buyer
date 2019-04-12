/**
 * 
 */
package com.hottop.core.security.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 *
 *
 */
public interface ValidateCodeGenerator {

	ValidateCode generate(ServletWebRequest request);
	
}

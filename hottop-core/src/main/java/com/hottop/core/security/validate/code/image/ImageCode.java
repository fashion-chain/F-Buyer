/**
 * 
 */
package com.hottop.core.security.validate.code.image;

import com.hottop.core.security.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;



/**
 *
 *
 */
public class ImageCode extends ValidateCode {

	private String image_key;
	
	private BufferedImage image; 
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getImage_key() {
		return image_key;
	}

	public void setImage_key(String image_key) {
		this.image_key = image_key;
	}
}

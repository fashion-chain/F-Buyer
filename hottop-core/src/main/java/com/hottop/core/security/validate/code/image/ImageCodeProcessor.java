/**
 * 
 */
package com.hottop.core.security.validate.code.image;

import javax.imageio.ImageIO;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;


/**
 * 图片验证码处理器
 * 
 *
 *
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		request.getResponse().setHeader("image_key", imageCode.getImage_key());
		//ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
		HashMap<String, String> map = new HashMap<>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(imageCode.getImage(), "JPEG", baos);
		map.put("imageBase64Str", new BASE64Encoder().encode(baos.toByteArray()));
		map.put("image_key", imageCode.getImage_key());
		request.getResponse().getWriter().write(BaseConfiguration.generalGson().toJson(
				Response.ResponseBuilder.result(EResponseResult.OK).data(map).create()
		));
	}

}

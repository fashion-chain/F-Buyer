/**
 *
 */
package com.hottop.core.security.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.jwt.JwtUserDetailService;
import com.hottop.core.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 *
 */
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Lazy
    private JwtUserDetailService jwtUserDetailService;

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");
        //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            response.getWriter().write(BaseConfiguration.generalGson().toJson(
                    Response.ResponseBuilder.result(EResponseResult.ERROR_AUTHENTICATION_FAIL).create()
            ));
            return ;
        }
        //处理token过期异常
        if (exception instanceof NonceExpiredException) { //过期
            String message = exception.getMessage();
            JsonObject jsonMsg = new JsonParser().parse(message).getAsJsonObject();
            String subject = jsonMsg.get("subject").getAsString();//用户名
            String msg = jsonMsg.get("msg").getAsString();//异常消息
            if(msg.equals("refreshTokenExpire")){
                response.getWriter().write(BaseConfiguration.generalGson().toJson(
                        Response.ResponseBuilder.result(EResponseResult.JWT_RefreshToken_EXPIRE).message().create()
                ));
                return;
            }
            UserDetails userDetails = jwtUserDetailService.getUserLoginInfoByRefreshToken(subject);
            if(userDetails == null) {
                response.getWriter().write(BaseConfiguration.generalGson().toJson(
                        Response.ResponseBuilder.result(EResponseResult.JWT_RefreshToken_EXPIRE).message().create()
                ));
                return;
            }
            //Map tokenMap = jwtUserDetailService.saveUserLoginInfo(userDetails);
            if(userDetails != null) {
                response.getWriter().write(BaseConfiguration.generalGson().toJson(
                        Response.ResponseBuilder.result(EResponseResult.JWT_TOKEN_EXPIRE).message().create()
                ));
                return;
            }
        } else if (exception instanceof InsufficientAuthenticationException) {
            response.getWriter().write(BaseConfiguration.generalGson().toJson(ResponseUtil.createErrorResponse(EResponseResult.AUTHENTICATION_ERROR_tokenIllegal)));
        } else {
            response.getWriter().write(BaseConfiguration.generalGson().toJson(
                    Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(exception.getMessage()).create()
            ));
            return;
        }
        //response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write(BaseConfiguration.generalGson().toJson(
                Response.ResponseBuilder.result(EResponseResult.ERROR_AUTHENTICATION_FAIL).message().create()
        ));


    }

}

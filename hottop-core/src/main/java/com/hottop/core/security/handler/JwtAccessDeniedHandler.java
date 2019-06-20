package com.hottop.core.security.handler;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                new String(
                        BaseConfiguration.generalGson().toJson(
                        Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                                .simpleMessage("无权限访问").create()
                    ).getBytes("UTF-8")
                )
        );
    }
}

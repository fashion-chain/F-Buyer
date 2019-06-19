package com.hottop.core.security.jwt;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class JwtAuthenticationController {

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    //判断当前用户是否已经登录
    @RequestMapping(path = "/verify")
    public Response verify(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage(BaseConfiguration.getMessage("jwt.authorization.not.blank")).create();
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .simpleMessage("用户已经登录").create();
    }

}

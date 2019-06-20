package com.hottop.backstage.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.jwt.JwtUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//security controller
@RestController
@RequestMapping(path = "/jwt")
public class BackStageSecurityController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    //根据refreshToken获取新的token和刷新token
    @GetMapping(path = "/refreshToken")
    public Response getTokenMapByRefreshToken(@RequestParam("refreshToken") String refreshToken) {
        DecodedJWT decodeRefreshToken = JWT.decode(refreshToken);
        UserDetails userDetails = jwtUserDetailService.getUserLoginInfoByRefreshToken(decodeRefreshToken.getSubject());
        //token是否过期
        if(userDetails == null) {
            return Response.ResponseBuilder.result(EResponseResult.JWT_RefreshToken_EXPIRE).create();
        }
        //判断token是否合法,redis中的salt
        String salt = userDetails.getPassword();
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTVerifier verifier = JWT.require(algorithm)
                .withSubject(userDetails.getUsername())
                .build();
        try {
            verifier.verify(decodeRefreshToken.getToken());
        } catch (JWTVerificationException e) {
            logger.info("refreshToken验证签名非法");
            return Response.ResponseBuilder.result(EResponseResult.JWT_RefreshToken_EXPIRE).create();
        }
        Map map = jwtUserDetailService.saveUserLoginInfo(userDetails);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(map).create();
    }
}

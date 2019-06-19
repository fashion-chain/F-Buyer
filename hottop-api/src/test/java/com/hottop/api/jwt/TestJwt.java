package com.hottop.api.jwt;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.utils.ResponseUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJwt {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetApiValue() {
        SecurityProperties securityProperties = wac.getBean(SecurityProperties.class);
        System.out.println(securityProperties.getApi().getLoginType());

        //
        Response okResponse = ResponseUtil.createOKResponse(EResponseResult.USER_SUCCESS_REGISTER);
        String s = BaseConfiguration.generalGson().toJson(okResponse);
        System.out.println(s);
    }

    public static void main(String[] args) {
        //$2a$10$M5mA8rS8N7MCl6/PuM1AEuy3e17cJmiXIpCTP2Lb5aHVxNoi0ScO2
        System.out.println(new BCryptPasswordEncoder().encode("root1234"));

        System.out.println(Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).message("sms.error").create());
    }
}

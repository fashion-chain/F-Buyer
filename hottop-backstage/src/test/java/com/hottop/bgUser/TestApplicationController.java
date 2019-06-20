package com.hottop.bgUser;

import com.google.gson.Gson;
import com.hottop.core.model.user.Application;
import com.hottop.backstage.application.model.BgUserDto;
import com.hottop.backstage.application.service.ApplicationService;
import com.hottop.core.config.BaseConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 后台用户测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Autowired
    private ApplicationService applicationService;

    /**
     * 创建后台用户
     * @throws Exception
     */
    @Test
    public void testCreateBgUser() throws Exception {
        Application application = new Application();
        application.setTel("18330219446");
        application.setPassword("root1234");
        //com.hottop.application.setState(EBgUser.EBgUserStatus.ok.getStatus());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/com/hottop/application/register")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(BaseConfiguration.generalGson().toJson(application)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("结果：{}", mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void testUpdateBgUser() throws Exception {
        BgUserDto bgUserDto = new BgUserDto();
        bgUserDto.setTel("18330219446Update");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/bgUser/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(BaseConfiguration.generalGson().toJson(bgUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("结果：{}", mvcResult.getResponse().getContentAsString());
    }

    /**
     * 后台用户添加角色
     */
    @Test
    public void testBgUserAddRoles() throws Exception {

    }


    public static void main(String[] args) {
        createBgUser();
    }

    public static void createBgUser() {
        Application application = new Application();
        application.setTel("18330219446");
        application.setPassword("root1234");
        System.out.println(new Gson().toJson(application));
    }

}

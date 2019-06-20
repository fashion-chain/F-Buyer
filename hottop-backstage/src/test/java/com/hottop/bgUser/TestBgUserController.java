package com.hottop.bgUser;

import com.hottop.backstage.bguser.enums.EBgUser;
import com.hottop.backstage.bguser.model.BgUser;
import com.hottop.backstage.bguser.model.BgUserDto;
import com.hottop.backstage.bguser.service.BgUserService;
import com.hottop.backstage.security.service.MyUserDetailService;
import com.hottop.core.config.BaseConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Iterator;

/**
 * 后台用户测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBgUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Autowired
    private BgUserService bgUserService;

    /**
     * 创建后台用户
     * @throws Exception
     */
    @Test
    public void testCreateBgUser() throws Exception {
        BgUser bgUser = new BgUser();
        bgUser.setTel("18330219446");
        bgUser.setPassword("root1234");
        bgUser.setState(EBgUser.EBgUserStatus.OK.status);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/bgUser/register")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(BaseConfiguration.generalGson().toJson(bgUser)))
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

    @Autowired
    private MyUserDetailService myUserDetailService;

    /**
     * show UserDetail
     */
    @Test
    public void testShowBgUserDetails() {
        String tel = "18330219446";
        SocialUserDetails socialUserDetails =  myUserDetailService.loadUserByUserId(tel);
        logger.info("userId:{},userName:{},password:{}", socialUserDetails.getUserId(),
            socialUserDetails.getUsername(), socialUserDetails.getPassword());
        Iterator<? extends GrantedAuthority> iterator = socialUserDetails.getAuthorities().iterator();
        while (iterator.hasNext()) {
            logger.info(iterator.next().getAuthority());
        }
    }
}

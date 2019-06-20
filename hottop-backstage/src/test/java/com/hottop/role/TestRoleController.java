package com.hottop.role;

import com.hottop.backstage.role.dto.RoleDto;
import com.hottop.backstage.role.enums.ERoleStatus;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Role;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRoleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 添加一个role
     */
    @Test
    public void testAddRole() throws Exception {
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        role.setRemark("普通用户角色");
        role.setState(ERoleStatus.OK.status);
        //role.setState(ERoleStatus.OK.status);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/role/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(BaseConfiguration.generalGson().toJson(role)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("结果：{}", mvcResult.getResponse().getContentAsString());
        //结果：{"code":1,"data":{"state":"角色状态不合法"}}

        //结果：{"code":0,"data":{"roleName":"ROLE_USER","state":"1","remark":"普通用户角色","id":2,"createTime":"Apr 10, 2019 10:23:28 AM"}}
    }
}

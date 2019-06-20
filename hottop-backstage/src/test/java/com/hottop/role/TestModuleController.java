package com.hottop.role;

import com.google.gson.Gson;
import com.hottop.backstage.role.enums.ERoleStatus;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Module;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestModuleController {

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
        Module module = new Module();
        module.setModuleName("ROLE_USER");
        module.setRemark("普通用户角色");
        module.setState(ERoleStatus.ok.status);
        //module.setState(ERoleStatus.OK.status);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/module/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(BaseConfiguration.generalGson().toJson(module)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.info("结果：{}", mvcResult.getResponse().getContentAsString());
        //结果：{"code":1,"data":{"state":"角色状态不合法"}}

        //结果：{"code":0,"data":{"roleName":"ROLE_USER","state":"1","remark":"普通用户角色","id":2,"createTime":"Apr 10, 2019 10:23:28 AM"}}
    }

    public static void main(String[] args) {
        createRole();
    }

    public static void createRole() {
        Module module = new Module();
        module.setModuleName("ROLE_USER1");
        module.setRemark("普通用户角色1");
        List<Long> permissionIds = new ArrayList<>();
        permissionIds.add(1l);
        permissionIds.add(3l);
        permissionIds.add(4l);
        permissionIds.add(5l);
        permissionIds.add(6l);
        module.setCapabilityIds(permissionIds);
        System.out.println(new Gson().toJson(module));
    }


}

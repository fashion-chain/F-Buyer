package com.hottop.api.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.UserAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 用户地址测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserAddressController {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 测试新增 userAddress
     */
    @Test
    public void testUserAddressPost() throws Exception {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(1l);
        userAddress.setZipCode("0100056");
        userAddress.setProvince("110000");
        userAddress.setCity("110100");
        userAddress.setArea("110105");
        userAddress.setAddress("复兴国际中心");
        userAddress.setIsDefault("1");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/userAddress/")
                .content(BaseConfiguration.generalGson().toJson(userAddress))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("结果：" + mvcResult.getResponse().getContentAsString());
        //结果：{"code":1,"data":{"zipCode":"邮政编码不合法","userToken":"用户token不能为空"},"message":null,"error":null,"flag":null,"flagPre":null,"success":false}
    }

    //测试更新
    @Test
    public void testUserAddressPut() throws Exception {
        UserAddress userAddress = new UserAddress();
        userAddress.setArea("复兴国际中心-更新1次");
        userAddress.setId(1l);
        String jsonStr = BaseConfiguration.generalGson().toJson(userAddress);
        //situation1 : 不设置useToken
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/userAddress/")
                .content(jsonStr)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("结果：" + result.getResponse().getContentAsString());
    }

    //测试获得详情
    @Test
    public void testUserAddressGet() throws Exception {
        Long id = 1l;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/userAddress/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("结果：" + result.getResponse().getContentAsString());
    }

    public static void main(String[] args) {
        UserAddress userAddress = new UserAddress();
        //userAddress.setUserId(1l);
        userAddress.setZipCode("0100056");
        userAddress.setProvince("110000");
        userAddress.setCity("110100");
        userAddress.setArea("110105");
        userAddress.setAddress("复兴国际中心");
        userAddress.setIsDefault("1");
        String s = new Gson().toJson(userAddress);
        System.out.println(s);
    }

}

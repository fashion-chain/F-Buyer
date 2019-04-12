package com.hottop.api.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.User;
import com.hottop.core.response.EResponseCode;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.SmsUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 用户模块测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserController {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 测试redisTemplate get set expireTime
     */
    @Test
    public void testRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) wac.getBean("redisTemplate");
        setKeyVlaue(redisTemplate);
        String key = "18330268186" + ESmsCodeType.Login;
        getKeyValue(redisTemplate, key);
    }

    private void setKeyVlaue(RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set("name", "liu" + new Date().getTime());
    }

    private void getKeyValue(RedisTemplate redisTemplate, String key) {
        Object value = redisTemplate.opsForValue().get(key);
        System.out.println(String.format("获取的的key对应的value：%s",value));
    }

    private void setExpireTime(RedisTemplate redisTemplate, String key, Integer seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取用户登录的短信验证码
     * 如果有，返回结果
     * 如果没有，给用户发送短信，并返回结果
     */
    @Test
    public void testGetLoginSmsCode() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getLoginSmsCode")
                .param("tel", "18330219446"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String prettyJsonResult = toPrettyFormat(result.getResponse().getContentAsString());
        System.out.println(prettyJsonResult);
    }

    /**
     * 获取用户注册的短信验证码
     * 如果有，返回结果
     * 如果没有，给用户发送短信，并返回结果
     */
    @Test
    public void testGetRegisterSmsCode() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getRegisterSmsCode")
                .param("tel", "18330219446"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String prettyJsonResult = toPrettyFormat(result.getResponse().getContentAsString());
        System.out.println(prettyJsonResult);
    }

    /**
     * 判断用户电话号码是否存在
     * false表示不存在，true表示存在
     */
    @Test
    public void testTelExists() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/telExists")
                .param("tel", "18330268186"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String prettyJsonResult = toPrettyFormat(result.getResponse().getContentAsString());
        System.out.println(prettyJsonResult);
    }

    /**
     * 用户注册
     * 密码要求：字母数组组合8-16位
     * 验证码：要求跟短信发送的验证码一致
     */
    @Test
    public void testUserRegisterValidator() throws Exception {
        String tel = "18330219446";
        String password = "root1234";
        String verifyCode = "101168";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("tel", tel)
                .param("password", password)
                .param("verifyCode", verifyCode)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String prettyJsonResult = toPrettyFormat(result.getResponse().getContentAsString());
        System.out.println(prettyJsonResult);
    }

    /**
     * 测试用户 手机号-密码 登录
     */
    @Test
    public void testUserLogin() throws Exception {
        String tel = "18330219446";
        String password = "root";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/sms")
                .param("tel", tel)
                .param("password", password)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        //result1 -> 账号密码不正确
        //{"code":1,"data":"用户名或者密码不正确","message":null,"error":null,"flag":null,"flagPre":null,"success":false}

        //result2 -> 账号密码正确
        //{"code":0,"data":"c436e169dab04e509344ad483b029c30","message":null,"error":null,"flag":null,"flagPre":null,"success":true}
    }

    @Test
    public void testMessage() {
        System.out.println(BaseConfiguration.generalGson().toJson(Response.ResponseBuilder.result(EResponseResult.ERROR_SMS_SEND).message().create()));
    }

}

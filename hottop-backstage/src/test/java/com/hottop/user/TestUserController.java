package com.hottop.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hottop.backstage.config.RedisConfig;
import com.hottop.core.utils.SmsUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
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
        String key = "18330268186" + SmsUtil.ESmsCodeType.Login;
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
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getLoginSmsCode")
                .param("mobilePhone", "18330268186"))
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
}

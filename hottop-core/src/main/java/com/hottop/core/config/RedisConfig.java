package com.hottop.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.jws.soap.SOAPBinding;

/**
 * redis 配置类
 */
@Configuration
@PropertySource("classpath:business.properties")
public class RedisConfig {

    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private RedisConnectionFactory redisConnectionFactory = null;

    /**
     * 最大空闲数
     */
    @Value("${redis.maxIdle}")
    private String maxIdle;

    /**
     * 最大连接数
     */
    @Value("${redis.maxTotal}")
    private String maxTotal;

    /**
     * 超时时间
     */
    @Value("${redis.maxWaitMillis}")
    private String maxWaitMillis;

    /**
     * 主机
     */
    @Value("${redis.hostname}")
    private String hostname;

    /**
     * 端口
     */
    @Value("${redis.port}")
    private String port;

    /**
     * 密码
     */
    @Value("${redis.password}")
    private String password;

    @Value("${redis.key.default.expireTime}")
    private Long expireTime;

    //用户注册验证码前缀
    public static String Prefix_user_verifyCode_register = "user:verifyCode:register:";

    //用户登录验证码前缀
    public static String Prefix_user_verifyCode_login = "user:verifyCode:login:";

    //用户登录验证码前缀
    public static String Prefix_user_verifyCode_changePassword = "user:verifyCode:changePassword:";

    //redis connection factory初始化
    @Bean(name = "RedisConnectionFactory")
    @Primary
    public RedisConnectionFactory initRedisConnectionFactory() {
        if (this.redisConnectionFactory != null) {
            return this.redisConnectionFactory;
        }
        logger.info(String.format("\n------\n获取到的maxIdle：%s\n,maxTotal:%s\n,maxWailtMillis:%s\n," +
                "hostname:%s\n,port:%s\n,password:%s\n-------", maxIdle, maxTotal, maxWaitMillis, hostname, port, password));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        jedisPoolConfig.setMaxTotal(Integer.parseInt(maxTotal));
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
        JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig);
        RedisStandaloneConfiguration standaloneConfiguration = factory.getStandaloneConfiguration();
        standaloneConfiguration.setHostName(hostname);
        standaloneConfiguration.setPort(Integer.parseInt(port));
        standaloneConfiguration.setPassword(password);
        return factory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> initRedisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        return redisTemplate;
    }

    public Long getExpireTime() {
        return expireTime;
    }
}

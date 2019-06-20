package com.hottop.core.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Application;
import com.hottop.core.model.user.Capability;
import com.hottop.core.model.user.Module;
import com.hottop.core.repository.user.ApplicationRepository;
import com.hottop.core.repository.user.ModuleRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.utils.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class JwtUserDetailService<T> implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private T t;

    public void setT(T t) {
        this.t = t;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();  //默认使用 bcrypt， strength=10

    public JwtUserDetailService() {
    }

    private static String token_redis_prefix = "token:back:";
    private static String refreshToken_redis_prefix = "refreshToken:back:";


    public UserDetails getUserLoginInfo(String username) {
        /**
         *  从数据库或者缓存中取出jwt token生成时用的salt
         */
        String redis_key = token_redis_prefix + username;
        String salt = (String) redisTemplate.opsForValue().get(redis_key);
        long ttlTime = redisTemplate.getExpire(redis_key);
        logger.info("redis中取出token redis-key:{}, 距离过期还有【{}】秒,jwt salt：{}", token_redis_prefix + username, ttlTime, salt);
        if(StringUtils.isBlank(salt)) {
//            redis_key = refreshToken_redis_prefix + username;
//            salt = (String) redisTemplate.opsForValue().get(redis_key);
//            ttlTime = redisTemplate.getExpire(redis_key);
//            logger.info("redis中取出刷新token redis-key:{}, 距离过期还有【{}】秒,jwt salt：{}", refreshToken_redis_prefix + username, ttlTime, salt);
//            if(StringUtils.isBlank(salt)) {
//                return null;
//            }
            return null;
        }

        //UserDetails user = loadUserByUsername(username);
        //if(user == null) throw new BadCredentialsException("账号密码错误");
        //将salt放到password字段返回， userDetails
        return User.builder().username(username).password(salt).authorities(new ArrayList<SimpleGrantedAuthority>()).build();
    }

    public UserDetails getUserLoginInfoByRefreshToken(String username) {
        String redis_key = refreshToken_redis_prefix + username;
        String salt = (String) redisTemplate.opsForValue().get(redis_key);
        if(StringUtils.isBlank(salt)) {
            logger.info("redis中取出refreshToken为空，redis-key:{}", redis_key);
            return null;
        }
        logger.info("redis中取出refreshToken redis-key:{},jwt salt：{}", redis_key, salt);
        UserDetails user = loadUserByUsername(username);
        //将salt放到password字段返回
        return User.builder().username(user.getUsername()).password(salt).authorities(user.getAuthorities()).build();
    }

    public static final String ROLE_CLAIMS = "Authorities";
    //用户登录成功的时候，生产token
    public Map<String, String> saveUserLoginInfo(UserDetails user) {
        String salt = BCrypt.gensalt();
        /**
         * 将salt保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
         */
        Integer jwtTokenExpireTime = Integer.parseInt(securityProperties.getBrowser().getJwtExpireTime());
        Integer jwtRefreshTime = Integer.parseInt(securityProperties.getBrowser().getJwtRefreshTime());
        String token_redisKey = token_redis_prefix + user.getUsername();
        String refreshToken_redisKey = refreshToken_redis_prefix + user.getUsername();
        logger.info("jwt salt保存到redis中，key:{}, salt:{}, 过期时间：{} 秒",token_redisKey, salt, jwtTokenExpireTime);
        logger.info("jwt salt保存到redis中，key:{}, salt:{}, 过期时间：{} 秒", refreshToken_redisKey, salt, jwtRefreshTime);
        redisTemplate.opsForValue().set(token_redisKey, salt, jwtTokenExpireTime, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(refreshToken_redisKey, salt, jwtRefreshTime, TimeUnit.SECONDS);
        Algorithm algorithm = Algorithm.HMAC256(salt);
        Date date = new Date(System.currentTimeMillis() + jwtTokenExpireTime * 1000);  //设置1小时后过期
        Date refreshTokenDate = new Date(System.currentTimeMillis() + jwtRefreshTime * 1000);
        SimpleGrantedAuthority[] simpleGrantedAuthorities = user.getAuthorities().toArray(new SimpleGrantedAuthority[user.getAuthorities().size()]);
        String Authorities = BaseConfiguration.generalGson().toJson(simpleGrantedAuthorities);
        logger.info("Authorities：{}", Authorities);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim(ROLE_CLAIMS, Authorities)
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withClaim(ROLE_CLAIMS, Authorities)
                .withExpiresAt(refreshTokenDate)
                .withIssuedAt(new Date())
                .sign(algorithm);
        Map<String,String > result = new HashMap<>();
        result.put("token", token);
        result.put("refreshToken", refreshToken);
        return result;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        if (t instanceof Application) {
            return loadUserByUsername_bg(userName);
        } else if (t instanceof com.hottop.core.model.user.User) {
            return loadUserByUsername_api(userName);
        }
        return null;
    }

    //后台用户加载用户详情
    private UserDetails loadUserByUsername_bg(String userName) throws UsernameNotFoundException {
        Application user = null;
        if(ValidatorUtil.ValidatePhone(userName)) {
            logger.info("jwt-userDetail-使用手机号登录,手机号：{}", userName);
            user = applicationRepository.findByTel(userName);

        } else {
            //根据用户名查找用户信息
            logger.info("jwt-userDetail-使用用户名登录,登录用户名：{}", userName);
            user = applicationRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new UsernameNotFoundException("后台用户不存在");
        }
        //查询权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Module> modules = moduleRepository.findAllByAppId(user.getId());
        for (Module r : modules) {
            List<Capability> capabilities = r.getCapabilities();
            for(Capability p : capabilities) {
                authorities.add(new SimpleGrantedAuthority(p.getCapabilityName()));
            }
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    //api用户加载用户详情
    private UserDetails loadUserByUsername_api(String userName) throws UsernameNotFoundException {
        com.hottop.core.model.user.User user = null;
        if(ValidatorUtil.ValidatePhone(userName)) {
            user = userRepository.findByTel(userName);
        } else {
            //根据用户名查找用户信息
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new UsernameNotFoundException("app用户不存在");
        }
        //查询权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<Module> modules = moduleRepository.findAllByUserId(user.getId());
        for (Module r : modules) {
            authorities.add(new SimpleGrantedAuthority(r.getModuleName()));
        }
        return new User(userName, user.getPassword(), authorities);
    }

    public void createUser(String username, String password) {
        String encryptPwd = passwordEncoder.encode(password);
        /**
         * @todo 保存用户名和加密后密码到数据库
         */
    }

    public void deleteUserLoginInfo(String username) {
        /**
         * @todo 清除数据库或者缓存中登录salt
         */
        redisTemplate.delete(token_redis_prefix + username);
        redisTemplate.delete(refreshToken_redis_prefix + username);
    }

}

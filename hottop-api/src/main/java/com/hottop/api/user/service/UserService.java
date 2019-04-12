package com.hottop.api.user.service;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.model.user.User;
import com.hottop.core.model.user.enums.EUserStatus;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * user service
 * 用户服务类
 */
@Service("userService")
public class UserService extends EntityBaseService<User,Long>{

    private static Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;
    @Override
    public EntityBaseRepository<User, Long> repository() {
        return userRepository;
    }

    /**
     * 判断电话号码是否已经存在
     * @param tel
     */
    public boolean telExists(String tel) {
        User byTelExists = userRepository.findByTel(tel);
        if(byTelExists != null) return true;
        return false;
    }

    /**
     * 根据id 判断user是否存在
     * @param id
     * @return
     */
    public boolean idExists(Long id){
        Long count = userRepository.countById(id);
        if(count != null && count.longValue() >= 1) return true;
        return false;
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public User save (User user) {
        String salt = EncryptUtil.generateSalt(4);
        user.setSalt(salt);
        String password = EncryptUtil.generatePassword(user.getPassword() , salt);
        user.setPassword(password);
        user.setState(EUserStatus.ok.getStatus());
        return super.save(user);
    }

    /**
     * 判断用户手机号 和 密码是否正确
     * @param tel
     * @param password
     * @return
     */
    public User verifyTelAndPassword(String tel, String password) {
        User user_repo = userRepository.findByTel(tel);
        if(user_repo == null) {
            logger.info("数据库中没有这个手机号【{}】的用户", tel);
            return null;
        }
        password = EncryptUtil.generatePassword(password, user_repo.getSalt());
        User user = userRepository.findByTelAndPassword(tel, password);
        logger.info("根据手机号查询用户不为空，用户salt:{},查询出来的用户是否为空:{}",user_repo.getSalt(),user == null);
        return user;
    }

    /**
     * 用户登录
     * @return token
     */
    public Response login(String tel, String password) {
        String token = null;
        User user = null;
        //判断账号密码是否正确
        user = verifyTelAndPassword(tel, password);
        if(user == null) return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data("用户名或者密码不正确").create();
        try {
            //从redis中取这个tel的token
            token = (String) redisTemplate.opsForHash().get(RedisConfig.login_telToken_hash, tel);
            if(token == null) {
                logger.info("用户登录");
            }else {
                logger.info("更新用户登录的token");
                //user = (User) redisTemplate.opsForHash().get(RedisConfig.login_tokenUser_hash, token);
                redisTemplate.opsForHash().delete(RedisConfig.login_tokenUser_hash, token);
            }
            //redis库中插入token user
            token = UUID.randomUUID().toString().replace("-","");
            redisTemplate.opsForHash().put(RedisConfig.login_telToken_hash, tel, token);
            redisTemplate.opsForHash().put(RedisConfig.login_tokenUser_hash, token, BaseConfiguration.generalGson().toJson(user));
            logger.info("现在'login_userToken_hash' size : {}", redisTemplate.opsForHash().size(RedisConfig.login_telToken_hash));
        }catch (Exception e){
            logger.info("redisTemplate 写入用户登录信息出错,用户电话:{}", tel);
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data("用户登录出错").create();
        }
        return Response.ResponseBuilder.result(EResponseResult.OK).data(token).create();
    }

}

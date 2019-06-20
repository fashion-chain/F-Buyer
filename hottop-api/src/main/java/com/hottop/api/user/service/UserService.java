package com.hottop.api.user.service;

import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * user service
 * 用户服务类
 */
@Service("userService")
public class UserService extends EntityBaseService<User, Long> {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public EntityBaseRepository<User, Long> repository() {
        return userRepository;
    }

    /**
     * 判断电话号码是否已经存在
     *
     * @param tel
     * @return true表示电话号码存在
     */
    public boolean telExists(String tel) {
        User byTelExists = userRepository.findByTel(tel);
        if (byTelExists == null) return false;
        return true;
    }

    /**
     * 根据id 判断user是否存在
     *
     * @param id
     * @return
     */
    public boolean idExists(Long id) {
        Long count = userRepository.countById(id);
        if (count != null && count.longValue() >= 1) return true;
        return false;
    }

    /**
     * 判断用户手机号 和 密码是否正确
     *
     * @param password
     * @return
     */
    public User verifyNameAndPassword(String name, String password) {
        User user_repo = null;
        if(ValidatorUtil.ValidatePhone(name)){
            user_repo = userRepository.findByTel(name);
        }else {
            user_repo = userRepository.findByUsername(name);
        }
        if (user_repo == null) {
            logger.info("数据库中没有这个手机号【{}】的用户", name);
            return null;
        }
        password = bCryptPasswordEncoder.encode(password);
        if(user_repo.getPassword().equals(password)){
            logger.info("验证账号密码通过");
            return user_repo;
        }
        logger.info("账号密码不匹配");
        return null;
    }

    /**
     *  重置密码
     * @param tel
     * @param password
     * @return
     */
    @Transactional
    public Response resetPassword (String tel, String password) {
        try {
            User user_repo = userRepository.findByTel(tel);
            user_repo.setPassword(bCryptPasswordEncoder.encode(password));
            super.save(user_repo);
        }catch (Exception e) {
            LogUtil.error(e.getStackTrace());
            return ResponseUtil.createErrorResponse(EResponseResult.USER_ERROR_PASSWORD_CHANGE);
        }
        return ResponseUtil.createOKResponse(EResponseResult.USER_SUCCESS_PASSWORD_CHANGE);
    }
    /**
     * 根据用户标识获得用户
     *
     * @param identification 比如手机号，邮箱，username
     * @return
     */
    public User getUserByIdentification(String identification) {
        User user = null;
        if (ValidatorUtil.ValidatePhone(identification)) {
            user = userRepository.findByTel(identification);
        } else {
            user = userRepository.findByUsername(identification);
        }
        return user;
    }

}

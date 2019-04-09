package com.hottop.backstage.user.service;

import com.hottop.backstage.product.service.CommerceBrandService;
import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.service.EntityBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * user service
 * 用户服务类
 */
@Service
public class UserService extends EntityBaseService<User,Long>{

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

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
        List<User> byTelExists = userRepository.findByTel(tel);
        if(byTelExists != null && byTelExists.size() >= 1) return true;
        return false;
    }
}

package com.hottop.api.user.service;

import com.hottop.core.model.user.User;
import com.hottop.core.model.user.UserAddress;
import com.hottop.core.model.user.enums.EUserAddressIsDefault;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.UserAddressRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * 用户地址服务类
 */
@Service("userAddressService")
public class UserAddressService extends EntityBaseService<UserAddress, Long>{

    private static Logger logger = LoggerFactory.getLogger(UserAddressService.class);

    @Autowired
    private UserAddressRepository userAddressRepository;
    @Override
    public EntityBaseRepository<UserAddress, Long> repository() {
        return userAddressRepository;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    private void transferIfFieldsNotBlank(UserAddress source, UserAddress destination) {

        if(StringUtils.isNotBlank(source.getZipCode())){
            destination.setZipCode(source.getZipCode());
        }
        if(StringUtils.isNotBlank(source.getProvince())){
            destination.setProvince(source.getProvince());
        }
        if(StringUtils.isNotBlank(source.getCity())){
            destination.setCity(source.getCity());
        }
        if(StringUtils.isNotBlank(source.getArea())){
            destination.setArea(source.getArea());
        }
        if(StringUtils.isNotBlank(source.getAddress())){
            destination.setAddress(source.getAddress());
        }
        if(StringUtils.isNotBlank(source.getIsDefault())){
            destination.setIsDefault(source.getIsDefault());
        }
    }


    /**
     * 更新用户地址
     * @throws Exception
     */
    @Transactional
    public Response updateUserAddress(UserAddress userAddress) {
        //邮政编码是否合法
        if(StringUtils.isNotBlank(userAddress.getZipCode()) && !ValidatorUtil.ValidateZipCode(userAddress.getZipCode())) {
            return ResponseUtil.createErrorResponse(EResponseResult.UserAddress_ERROR_ZipCode);
        }
        Optional<UserAddress> userAddress_repo_optional = userAddressRepository.findById(userAddress.getId());
        if(!userAddress_repo_optional.isPresent()){
            logger.info("更新用户地址ID不存在");
            return ResponseUtil.createErrorResponse(EResponseResult.UserAddress_ERROR_UPDATE);
        }
        UserAddress userAddress_repo = userAddress_repo_optional.get();
        try {
            transferIfFieldsNotBlank(userAddress, userAddress_repo);
            UserAddress updatedUserAddress = update(userAddress_repo);
            updateIsDefaultAddress(updatedUserAddress);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(updatedUserAddress).create();
        }catch (Exception e){
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.createErrorResponse(EResponseResult.UserAddress_ERROR_UPDATE);
    }

    /**
     * 保存用户地址
     * @param userAddress
     * @return
     */
    @Transactional
    public Response saveUserAddress(UserAddress userAddress) {
        try {
            User user = getCurrentUser();
            userAddress.setUserId(user.getId());
            super.save(userAddress);
            updateIsDefaultAddress(userAddress);
            return ResponseUtil.createOKResponse(EResponseResult.UserAddress_SUCCESS_SAVE);
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.createOKResponse(EResponseResult.UserAddress_ERROR_SAVE);
    }

    private User getCurrentUser() throws Exception {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        logger.info("当前用户：{}", username);
        User user = userService.getUserByIdentification(username);
        if (user == null ) throw new Exception("spring security context 中没有当前用户信息");
        return user;
    }

    /**
     * 批量更新默认地址状态
     * @param userAddress
     */
    private void updateIsDefaultAddress(UserAddress userAddress) {
        List<UserAddress> list = userAddressRepository.findByUserIdAndIdNot(userAddress.getUserId(), userAddress.getId());
        EntityManager entityManager = super.getEntityManager();
        for(UserAddress u : list) {
            u.setIsDefault(EUserAddressIsDefault.NOT_DEFAULT.getIsDefault());
            entityManager.merge(u);
        }
        entityManager.flush();
        entityManager.clear();
    }
}

package com.hottop.api.user.service;

import com.hottop.core.model.user.User;
import com.hottop.core.model.user.UserAddress;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.UserAddressRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(StringUtils.isNotBlank(source.getUserToken())){
            destination.setUserToken(source.getUserToken());
        }
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
        //userToken
        User user = (User) redisTemplate.opsForValue().get(userAddress.getUserToken());
        if(user == null || !userService.idExists(user.getId())) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data("更新'用户'ID不存在").create();
        }
        Optional<UserAddress> userAddress_repo_optional = userAddressRepository.findById(userAddress.getId());
        if(!userAddress_repo_optional.isPresent()){
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data("更新'用户地址'ID不存在").create();
        }
        UserAddress userAddress_repo = userAddress_repo_optional.get();
        try {
            transferIfFieldsNotBlank(userAddress, userAddress_repo);
            UserAddress updatedUserAddress = update(userAddress_repo);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(updatedUserAddress).create();
        }catch (Exception e){
            LogUtil.error(e.getStackTrace());
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data("更新'用户地址'出错").create();
    }
}

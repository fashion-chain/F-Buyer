package com.hottop.api.user.controller;

import com.hottop.api.user.service.UserAddressService;
import com.hottop.api.user.service.UserService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.user.User;
import com.hottop.core.model.user.UserAddress;
import com.hottop.core.request.argument.validator.user.UserAddressValidator;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * 用户地址controller
 */
@RestController
@RequestMapping("/userAddress")
public class UserAddressController extends EntityBaseController<UserAddress> {

    private static Logger logger = LoggerFactory.getLogger(UserAddressController.class);

    @Override
    public Class<UserAddress> clazz() {
        return UserAddress.class;
    }

    @Override
    public EntityBaseService service() {
        return userAddressService;
    }

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 注册验证器
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new UserAddressValidator());
    }


    /**
     * 用户地址新增
     * @param userAddress userId使用userToken
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response addUserAddress(@Valid @RequestBody UserAddress userAddress,
                                   Errors errors) {
        HashMap<String, String> map = new HashMap<>();
        if(errors.hasErrors()){
            ValidatorUtil.errorsToMap(map, errors);
            String message = BaseConfiguration.generalGson().toJson(map);
            logger.info("用户地址保存出错：" + message);
            return ResponseUtil.createErrorResponse(EResponseResult.UserAddress_ERROR_SAVE);
        }
        return userAddressService.saveUserAddress(userAddress);
    }

    /**
     * 用户地址更新
     * @param userAddress
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public Response updateUserAddress( @RequestBody UserAddress userAddress)  {
        return userAddressService.updateUserAddress(userAddress);
    }

    /**
     * 获取地址详情
     * @param userAddressId
     * @return
     */
    @RequestMapping(path = "/{userAddressId}", method = RequestMethod.GET)
    public Response getUserAddress(@PathVariable(name = "userAddressId") Long userAddressId) throws Exception{
        return super.getOne(userAddressId);
    }

}

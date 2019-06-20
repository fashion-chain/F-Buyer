package com.hottop.api.commerce.retailSpu.controller;

import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.user.service.UserService;
import com.hottop.core.model.commerce.CommerceRetailSpu;
import com.hottop.core.model.user.User;
import com.hottop.core.product.service.CommerceRetailSpuService;
import com.hottop.core.response.Response;
import com.hottop.core.security.jwt.JwtUserDetailService;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//零售商品 controller
@RestController
@RequestMapping("/retailSpu")
public class CommerceRetailSpuController extends ApiBaseController<CommerceRetailSpu> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceRetailSpuService commerceRetailSpuService;

    @Autowired
    private UserService userService;

    @Override
    public Class<CommerceRetailSpu> clazz() {
        return CommerceRetailSpu.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceRetailSpuService;
    }

    //新增
    //根据本人 采购订单的Id 来新增
    @PostMapping(path = "/")
    public Response addRetailSpu(@RequestBody List<CommerceRetailSpu> retailSpus) {
        if (retailSpus == null || retailSpus.size() <= 0) {
            return ResponseUtil.addFailResponse(CommerceRetailSpu.class.getSimpleName());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByIdentification(username);
        logger.info("当前登录用户username:{}, user是否为空:{}", username, user == null);
        if (user == null || user.getAppId() == null){
            return ResponseUtil.addFailResponse(CommerceRetailSpu.class.getSimpleName());
        }
        return commerceRetailSpuService.addRetailSpu(retailSpus, user);
    }
}

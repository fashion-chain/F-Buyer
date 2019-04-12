package com.hottop.backstage.bguser.controller;

import com.hottop.backstage.bguser.model.BgUser;
import com.hottop.backstage.bguser.model.BgUserDto;
import com.hottop.backstage.bguser.service.BgUserService;
import com.hottop.backstage.bguser.validator.BgUserValidator;
import com.hottop.core.model.user.Role;
import com.hottop.core.response.Response;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 后台用户
 */
@RestController
@RequestMapping("/bgUser")
public class BgUserController {

    @Autowired
    private BgUserService bgUserService;

    /**
     * spring 容器中已经有这个bean了
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注册验证器
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new BgUserValidator());
    }

    /**
     * 后台用户新增
     *
     * @param bgUser
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@Valid @RequestBody BgUser bgUser, Errors errors) {
        HashMap<String, String> map = new HashMap<>();
        if (errors.hasErrors()) {
            ValidatorUtil.errorsToMap(map, errors);
            return ResponseUtil.createErrorResponse(map);
        }
        //手机号是否已存在
        if (bgUserService.telExists(bgUser.getTel())) {
            return ResponseUtil.createErrorResponse("手机号已经注册");
        }
        return bgUserService.saveBgUser(bgUser);
    }

    /**
     * 更新 后台用户
     *
     * @param bgUser
     * @return
     */
    @RequestMapping(path = "/{bgUserId}", method = RequestMethod.PUT)
    public Response update(@RequestBody BgUserDto bgUser, @PathVariable Long bgUserId) {
        if (bgUser == null) {
            return ResponseUtil.createErrorResponse("更新对象bgUser不能为空");
        }
        bgUserService.updateBgUser(bgUser, bgUserId);

        return null;
    }

    @RequestMapping(path = "/{bgUserId}/addRoles", method = RequestMethod.POST)
    public Response addRoles(@RequestBody List<Role> roles, @PathVariable Long bgUserId) {

        return null;
    }
}

package com.hottop.backstage.role.controller;

import com.hottop.backstage.role.service.RoleService;
import com.hottop.backstage.role.validator.RoleValidator;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.user.Role;
import com.hottop.core.model.user.validator.UserRegisterValidator;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController extends EntityBaseController<Role> {

    @Override
    public Class<Role> clazz() {
        return Role.class;
    }
    @Autowired
    private RoleService roleService;

    @Override
    public EntityBaseService service() {
        return roleService;
    }

    /**
     * 注册验证器
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new RoleValidator());
    }

    /**
     * 新增 role
     * @param role
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response create(@Valid @RequestBody Role role, Errors errors) throws Exception {
        if(errors.hasErrors()) {
            Map<String,String> map = new HashMap<>();
            ValidatorUtil.errorsToMap(map, errors);
            return ResponseUtil.createErrorResponse(map);
        }
        return super.create(role);
    }
}

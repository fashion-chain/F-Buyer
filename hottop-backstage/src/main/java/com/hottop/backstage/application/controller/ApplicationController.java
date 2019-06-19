package com.hottop.backstage.application.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.role.service.ModuleService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Application;
import com.hottop.backstage.application.service.ApplicationService;
import com.hottop.backstage.application.validator.ApplicationValidator;
import com.hottop.core.model.user.Module;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户
 */
@RestController
@RequestMapping("/application")
public class ApplicationController extends BackstageBaseController<Application>{

    @Override
    public Class<Application> clazz() {
        return Application.class;
    }

    @Override
    public EntityBaseService service() {
        return applicationService;
    }

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ModuleService moduleService;

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
    @Autowired
    private ApplicationValidator applicationValidator;
    @InitBinder("application")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(applicationValidator);
    }

    /**
     * 后台用户新增
     *
     * @param application
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Response register(@Valid @RequestBody Application application, Errors errors) {
        HashMap<String, String> map = new HashMap<>();
        if (errors.hasErrors()) {
            ValidatorUtil.errorsToMap(map, errors);
            return ResponseUtil.createErrorResponse(BaseConfiguration.generalGson().toJson(map));
        }
        //手机号是否已存在
        if (applicationService.telExists(application.getTel())) {
            return ResponseUtil.existResponse("application.tel.exists");
        }
        return applicationService.saveBgUser(application);
    }

    /**
     * 更新 后台用户
     *
     * @param application
     * @return
     */
    @RequestMapping(path = "/{appId}", method = RequestMethod.PUT)
    public Response update(@RequestBody Application application, @PathVariable("appId") Long bgUserId) {
        return applicationService.updateBgUser(application, bgUserId);
    }

    //用户添加角色
    @RequestMapping(path = "/addModules/{appId}", method = RequestMethod.POST)
    public Response addRoles(@RequestBody List<Long> roleIds, @PathVariable("appId") Long appId) {
        return applicationService.addRoles(roleIds, appId);
    }

    //查询用户详情
    @GetMapping(path = "/{appId}")
    @Override
    public Response getOne(@PathVariable("appId") Long id) throws Exception {
        Response result = super.getOne(id);
        Application application = (Application) result.getData();
        application.setPassword("***");
        transferState(application);
        transferRole(application);
        transferStateAddMap(application);
        return result;
    }
    //添加用户状态map
    private void transferStateAddMap(Application application) {
        Map<String, String> statusMap = applicationService.getBgUserStatusMap();
        application.setStateMap(statusMap);
    }
    //用户状态字段转换
    private void transferState(Application application) {
        Map<String, String> statusMap = applicationService.getBgUserStatusMap();
        if(statusMap.containsKey(application.getState())) {
            application.setStateShowName(statusMap.get(application.getState()));
        } else {
            application.setStateShowName(BaseConfiguration.getMessage("common.status.unknow"));
        }
    }
    //角色对象转换
    private void transferRole(Application application) {
        if(application == null || application.getModules() == null || application.getModules().size() <= 0) return;
        ArrayList<Long> roleIds = new ArrayList<>();
        for(Module r : application.getModules()) {
            moduleService.transferRoleState(r);
            moduleService.transferPermissionState(r);
            r.setCapabilities(null);
            roleIds.add(r.getId());
        }
        application.setRolesIds(roleIds);
    }

    //filter
    @RequestMapping(path = "/filter")
    @Override
    public Response filter(@Filter IFilterResolver<Application> filterResolver) throws Exception {
        Response filter = super.filter(filterResolver);
        List<Application> data = (List<Application>) filter.getData();
        for(Application application : data) {
            application.setPassword(null);
            transferState(application);
            transferRole(application);
        }
        return filter;
    }
}

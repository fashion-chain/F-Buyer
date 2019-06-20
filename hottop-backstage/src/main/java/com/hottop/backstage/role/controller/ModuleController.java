package com.hottop.backstage.role.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.role.service.ModuleService;
import com.hottop.backstage.role.support.RoleEditNode;
import com.hottop.backstage.role.validator.RoleValidator;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Module;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController extends BackstageBaseController<Module> {

    @Override
    public Class<Module> clazz() {
        return Module.class;
    }
    @Autowired
    private ModuleService moduleService;

    @Override
    public EntityBaseService service() {
        return moduleService;
    }

    /**
     * 注册验证器
     * @param webDataBinder
     */
    @InitBinder("module")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new RoleValidator());
    }

    /**
     * 新增 module
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/")
    public Response create(@Valid @RequestBody Module module, Errors errors) throws Exception {
        return moduleService.createRole(module);
    }

    /**
     * module 更新权限
     * @return
     */
    @PutMapping(path = "/{id}")
    public Response updateRolePermission(@RequestBody Module module,
                                         @PathVariable("id") Long id) {
        return moduleService.updateRole(module, id);
    }

    //查询角色详情
    @GetMapping(path = "/{id}")
    @Override
    public Response getOne(@PathVariable("id") Long id) throws Exception {
        return moduleService.getRoleDetail(id);
    }

    //删除角色
    @DeleteMapping(path = "/{id}")
    @Override
    public Response delete(@PathVariable("id") Long id) throws Exception {
        return moduleService.deleteRole(id);
    }

    //查看角色列表

    @GetMapping(path = "/filter")
    @Override
    public Response filter(@Filter IFilterResolver<Module> filterResolver) throws Exception {
        Response filter = super.filter(filterResolver);
        List<Module> data = (List<Module>) filter.getData();
        if(data == null) return filter;
        for (Module r : data) {
            moduleService.transferRoleState(r);
        }
        return filter;
    }

    //获取角色tree，但是是单层列表
    @GetMapping(path = "/tree/edit")
    public Response getRoleEditTree() {
        RoleEditNode node = moduleService.getRoleEditTree();
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(node.getChildren()).simpleMessage(BaseConfiguration.getMessage("role.tree.success")).create();
    }
}

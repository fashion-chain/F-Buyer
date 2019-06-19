package com.hottop.backstage.permission.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.permission.service.CapabilityService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Capability;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capability")
public class CapabilityController extends BackstageBaseController<Capability> {

    @Override
    public Class<Capability> clazz() {
        return Capability.class;
    }

    @Override
    public EntityBaseService service() {
        return capabilityService;
    }

    @Autowired
    private CapabilityService capabilityService;

    /**
     * 添加一个权限
     * @return
     */
    @PostMapping(path = "/")
    public Response addPermission(@RequestBody Capability capability) {
        return capabilityService.addCapability(capability);
    }

    //权限更新
    @PutMapping(path = "/{id}")
    public Response updatePermission(@RequestBody Capability capability, @PathVariable("id") Long id) {
        return capabilityService.updatePermission(capability, id);
    }

    //权限删除

    @DeleteMapping(path = "/{id}")
    @Override
    public Response delete(@PathVariable("id") Long id) throws Exception {
        return capabilityService.deletePermission(id);
    }

    /**
     * 加载用户的权限树
     * 1商品管理
     *  商品管理权限json
     * 2分类管理
     * @return
     */
    @GetMapping(path = "/tree")
    public Response getPermissionList() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return capabilityService.getPermissionsByUsername(username);
    }

    //编辑的时候查看权限树
    @GetMapping(path = "/tree/edit")
    public Response getPermissionTreeAtEdit() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return capabilityService.getPermissionsByUsernameEdit(username);
    }

    //根据权限id查询权限列表
    @GetMapping(path = "/getByIds")
    public Response getPermissionsByIds(@RequestBody List<Long> ids) {
        try {
            List<Capability> result = capabilityService.getCapabilityByIds(ids);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(result).simpleMessage(BaseConfiguration.getMessage("permission.getByIds.success"))
                    .create();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .simpleMessage(BaseConfiguration.getMessage("permission.getByIds.fail"))
                .create();
    }


}

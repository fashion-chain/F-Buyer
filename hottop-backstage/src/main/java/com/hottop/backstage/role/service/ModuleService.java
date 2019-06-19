package com.hottop.backstage.role.service;

import com.hottop.backstage.application.service.ApplicationService;
import com.hottop.backstage.permission.service.CapabilityService;
import com.hottop.backstage.role.enums.ERoleStatus;
import com.hottop.backstage.role.support.RoleEditNode;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Capability;
import com.hottop.core.model.user.Module;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.ModuleRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("moduleService")
public class ModuleService extends EntityBaseService<Module, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    public EntityBaseRepository<Module, Long> repository() {
        return moduleRepository;
    }

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CapabilityService capabilityService;

    //更新role
    @Transactional
    public Response updateRole(Module module, Long id) {
        try {
            Module module_repo = findOne(Module.class, id);
            transferIfFieldsNotBlank(module, module_repo);
            if(module.getCapabilityIds() != null) {
                List<Long> permissionIds = module.getCapabilityIds();
                List<Capability> capabilities = capabilityService.getCapabilityByIds(permissionIds);
                module_repo.setCapabilities(capabilities);
            }
            module_repo.setUpdateTime(new Date());
            update(module_repo);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .simpleMessage(
                            String.format(BaseConfiguration.getMessage("common.update.success"),
                                    BaseConfiguration.getMessage(Module.class.getSimpleName()))
                    ).create();
        }catch (Exception e){
            LogUtil.error(e.getStackTrace());
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                .simpleMessage(BaseConfiguration.getMessage("role.update.fail")).create();
    }
    private void transferIfFieldsNotBlank(Module source, Module destination) {
        if(StringUtils.isNotBlank(source.getModuleName())){
            destination.setModuleName(source.getModuleName());
        }
        if(StringUtils.isNotBlank(source.getState())){
            destination.setState(source.getState());
        }
        if(StringUtils.isNotBlank(source.getRemark())){
            destination.setRemark(source.getRemark());
        }
        if(source.getCapabilities() != null){
            destination.setCapabilities(source.getCapabilities());
        }
        if(source.getCapabilityIds() != null){
            destination.setCapabilityIds(source.getCapabilityIds());
        }
    }

    //创建role
    @Transactional
    public Response createRole(Module module) {
        try {
            module.setState(ERoleStatus.ok.getStatus());//创建角色的时候，角色状态为可用
            List<Capability> capabilities = capabilityService.getCapabilityByIds(module.getCapabilityIds());
            module.setCapabilities(capabilities);
            save(module);
            return ResponseUtil.addSuccessResponse(Module.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.addFailResponse(Module.class.getSimpleName());
    }

    //根据角色ids查询角色列表
    public List<Module> getRolesByIds(List<Long> ids) {
        return moduleRepository.findAllById(ids);
    }

    //查看角色详情
    public Response getRoleDetail(Long id) {
        try {
            Module module_repo = findOne(Module.class, id);
            transferRoleState(module_repo);
            transferPermissionState(module_repo);
            transferRolePermissionIds(module_repo);
            return ResponseUtil.detailSuccessResponse(Module.class.getSimpleName(), module_repo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询角色详情失败");
        }
        return ResponseUtil.detailFailResponse(Module.class.getSimpleName());
    }
    //角色状态转换
    public void transferRoleState(Module module) {
        if(module == null) return;
        Map<String, String> roleStatusMap = getRoleStatusMap();
        if(roleStatusMap.containsKey(module.getState())) {
            module.setStateShowName(roleStatusMap.get(module.getState()));
        } else {
            module.setStateShowName(BaseConfiguration.getMessage("common.status.unknow"));
        }
    }
    //权限状态转换
    public void transferPermissionState(Module module) {
        if(module == null) return;
        Map<String, String> statusMap = getRoleStatusMap();
        if (module.getCapabilities() != null) {
            for (Capability p : module.getCapabilities()) {
                if(statusMap.containsKey(p.getState())) {
                    p.setStateShowName(statusMap.get(p.getState()));
                } else {
                    module.setStateShowName(BaseConfiguration.getMessage("common.status.unknow"));
                }
            }
        }
    }
    //角色权限ids添加
    public void transferRolePermissionIds(Module module) {
        if(module == null) return;
        ArrayList<Long> permissionIds = new ArrayList<>();
        if(module.getCapabilities() != null) {
            for (Capability p : module.getCapabilities()) {
                permissionIds.add(p.getId());
            }
        }
        module.setCapabilityIds(permissionIds);
    }

    public static Map<String,String> statusMap;
    //获取角色状态map
    public Map<String,String> getRoleStatusMap() {
        if (statusMap == null) {
            loadStatusMap();
        }
        return statusMap;
    }
    //加载权限map
    private void loadStatusMap() {
        statusMap = new LinkedHashMap<>();
        ERoleStatus[] values = ERoleStatus.values();
        String back_properties_prefix = "common.status.";
        for(ERoleStatus status : values) {
            if(StringUtils.isNotBlank(BaseConfiguration.getMessage(back_properties_prefix + status.name()))) {
                statusMap.put(status.getStatus(), BaseConfiguration.getMessage(back_properties_prefix + status.name()));
            }
        }
    }

    //删除角色
    @Transactional
    public Response deleteRole(Long id) {
        try {
            Module module_repo = findOne(Module.class, id);
            module_repo.setState(ERoleStatus.disable.getStatus());
            module_repo.setDeleteTime(new Date());
            update(module_repo);
            return ResponseUtil.deleteSuccessResponse(Module.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除角色失败");
        }
        return ResponseUtil.deleteFailResponse(Module.class.getSimpleName());
    }

    //角色tree
    public RoleEditNode getRoleEditTree() {
        List<Module> modules = moduleRepository.findAllByState(ERoleStatus.ok.getStatus());
        RoleEditNode node = new RoleEditNode();
        getEditNode(node, 0l, modules);
        return node;
    }

    //获取编辑页面node
    private void getEditNode(RoleEditNode result, Long pid, List<Module> list) {
        if (list.size() == 0) {
            return;
        } else {
            ArrayList<RoleEditNode> children = result.getChildren();
            for (Module r : list) {
                RoleEditNode roleEditNode = new RoleEditNode();
                roleEditNode.setKey(String.valueOf(r.getId()));
                roleEditNode.setValue(String.valueOf(r.getId()));
                roleEditNode.setTitle(r.getRemark());
                children.add(roleEditNode);
            }
            result.setChildren(children);
        }
    }

}

package com.hottop.backstage.permission.service;

import com.hottop.backstage.permission.support.CapabilityEditNode;
import com.hottop.backstage.permission.support.CapabilityNode;
import com.hottop.backstage.role.enums.ERoleStatus;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Application;
import com.hottop.backstage.application.service.ApplicationService;
import com.hottop.core.model.user.Capability;
import com.hottop.core.model.user.Module;
import com.hottop.core.model.user.enums.EPermissionStatus;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.CapabilityRepository;
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
import java.util.stream.Collectors;

@Service
public class CapabilityService extends EntityBaseService<Capability, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CapabilityRepository capabilityRepository;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public EntityBaseRepository<Capability, Long> repository() {
        return capabilityRepository;
    }

    /**
     * 根据用户名加载用户接口 权限
     *
     * @return
     */
    public Response getPermissionsByUsername(String username) {
        Application user = applicationService.findByIdentification(username);
        logger.info("username:{}", username);
        if (user == null) {
            logger.info("用户不存在:{}", username);
            return ResponseUtil.notExistResponse("capability.user.notExists");
        }
        //管理员加载所有的权限，普通用户加载角色下的权限
        if(username.equals("admin")) {
            List<Capability> allCapabilities = capabilityRepository.findAll().stream().filter(x -> x.getState().equals(EPermissionStatus.ok.getStatus())).collect(Collectors.toList());
            statusToShowName(allCapabilities);
            CapabilityNode result = new CapabilityNode();
            getNode(result, 0l, allCapabilities);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(result.getChildren()).simpleMessage(BaseConfiguration.getMessage("capability.load.success")).create();
        }
        List<Module> modules = user.getModules();
        List<Capability> capabilities = roleGetPermissions(modules);
        statusToShowName(capabilities);
        //list 转 tree
        CapabilityNode result = new CapabilityNode();
        getNode(result, 0l, capabilities);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(result.getChildren()).simpleMessage(BaseConfiguration.getMessage("capability.load.success")).create();
    }
    private List<Capability> roleGetPermissions (List<Module> modules) {
        if(modules == null || modules.size() <= 0) {
            return null;
        }
        Map<String,Capability> pMap = new HashMap<>();
        for (Module module : modules) {
            if (!module.getState().equals(ERoleStatus.ok.getStatus())) continue;
            List<Capability> capabilities = module.getCapabilities();
            for (Capability p : capabilities) {
                if (p != null &&
                        p.getState().equals(EPermissionStatus.ok.getStatus()) &&
                        !pMap.keySet().contains(p.getCapabilityName())
                        ) {
                    pMap.put(p.getCapabilityName(), p);
                }
            }
        }
        Collection<Capability> values = pMap.values();
        return new ArrayList<>(values);
    }

    //编辑页面返回tree
    public Response getPermissionsByUsernameEdit(String username) {
        Application user = applicationService.findByIdentification(username);
        logger.info("username:{}", username);
        if (user == null) {
            logger.info("用户不存在:{}", username);
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("用户不存在").create();
        }
        //管理员加载所有的权限，普通用户加载角色下的权限
        if(username.equals("admin")) {
            List<Capability> allCapabilities = capabilityRepository.findAll().stream().filter(x -> x.getState().equals(EPermissionStatus.ok.getStatus())).collect(Collectors.toList());
            CapabilityEditNode result = new CapabilityEditNode();
            getEditNode(result, 0l, allCapabilities);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(result.getChildren()).simpleMessage(BaseConfiguration.getMessage("capability.load.success")).create();
        }
        List<Module> modules = user.getModules();
        List<Capability> capabilities = roleGetPermissions(modules);
        CapabilityEditNode result = new CapabilityEditNode();
        getEditNode(result, 0l, capabilities);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(result.getChildren()).simpleMessage(BaseConfiguration.getMessage("capability.load.success")).create();
    }

    //查询结果状态转中文
    private void statusToShowName(List<Capability> list) {
        Map<String, String> permissionStatusMap = getPermissionStatusMap();
        for (Capability p : list) {
            if (permissionStatusMap.containsKey(p.getState())) {
                p.setState(permissionStatusMap.get(p.getState()));
            } else {
                p.setState(BaseConfiguration.getMessage("common.status.unknow"));
            }
        }
    }

    //获取权限节点node
    private void getNode(CapabilityNode result, Long pid, List<Capability> list) {
        List<Capability> tmpList = findCategoriesByPId(list, pid);
        if (tmpList.size() == 0) {
            return;
        } else {
            ArrayList<CapabilityNode> children = result.getChildren();
            for (Capability p : tmpList) {
                CapabilityNode permissionNode = new CapabilityNode(p);
                permissionNode.setKey(p.getId());
                permissionNode.setPId(p.getPid());
                permissionNode.setName(p.getDescription());
                children.add(permissionNode);
            }
            result.setChildren(children);
            for (CapabilityNode node : children) {
                getNode(node, node.getKey(), list);
            }
        }
    }

    //获取编辑页面node
    private void getEditNode(CapabilityEditNode result, Long pid, List<Capability> list) {
        List<Capability> tmpList = findCategoriesByPId(list, pid);
        if (tmpList.size() == 0) {
            return;
        } else {
            ArrayList<CapabilityEditNode> children = result.getChildren();
            int level_index = 0;//当前level层的第几个
            for (Capability p : tmpList) {
                //String key = level + "-" + level_index;
                CapabilityEditNode permissionEditNode = new CapabilityEditNode();
                permissionEditNode.setKey(String.valueOf(p.getId()));
                permissionEditNode.setValue(String.valueOf(p.getId()));
                permissionEditNode.setTitle(p.getDescription());
                children.add(permissionEditNode);
            }
            result.setChildren(children);
            for (CapabilityEditNode node : children) {
                getEditNode(node, Long.parseLong(node.getKey()), list);
            }
        }
    }

    //根据pid 加载它下面的list
    private List<Capability> findCategoriesByPId(List<Capability> list, Long pid) {
        ArrayList<Capability> result = new ArrayList<>();
        for (Capability c : list) {
            if (c.getPid() == pid) {
                result.add(c);
            }
        }
        return result;
    }

    //权限新增
    @Transactional
    public Response addCapability(Capability capability) {
        try {
            Long pid = capability.getPid();
            if(pid != 0) {
                Capability parent_capability = findOne(Capability.class, pid);
                if(parent_capability == null) {
                    return ResponseUtil.notExistResponse("capability.parentId.notExists");
                }
            }
            capability.setState(EPermissionStatus.ok.getStatus());
            Capability save = super.save(capability);
            return ResponseUtil.addSuccessResponse(Capability.class.getSimpleName());
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(Capability.class.getSimpleName());

    }

    //权限更新
    @Transactional
    public Response updatePermission(Capability capability, Long id) {
        try {
            Capability capability_repo = findOne(Capability.class, id);
            transferIfFieldsNotBlank(capability, capability_repo);
            update(capability_repo);
            return ResponseUtil.updateSuccessResponse(Capability.class.getSimpleName());
        } catch (Exception e) {
            logger.info("更新权限表失败");
            e.printStackTrace();
        }
        return ResponseUtil.updateFailResponse(Capability.class.getSimpleName());
    }

    private void transferIfFieldsNotBlank(Capability source, Capability destination) {
        if (StringUtils.isNotBlank(source.getCapabilityName())) {
            destination.setCapabilityName(source.getCapabilityName());
        }
        if (StringUtils.isNotBlank(source.getState())) {
            destination.setState(source.getState());
        }
        if (StringUtils.isNotBlank(source.getDescription())) {
            destination.setDescription(source.getDescription());
        }
        if (StringUtils.isNotBlank(source.getUrl())) {
            destination.setUrl(source.getUrl());
        }
        if (StringUtils.isNotBlank(source.getMethod())) {
            destination.setMethod(source.getMethod());
        }
        if (source.getPid() != null) {
            destination.setPid(source.getPid());
        }

    }

    //删除权限
    @Transactional
    public Response deletePermission(Long id) {
        Capability capability_repo = findOne(Capability.class, id);
        if (capability_repo == null) {
            return ResponseUtil.notExistResponse(Capability.class.getSimpleName());
        }
        try {
            capability_repo.setState(EPermissionStatus.disable.getStatus());
            capability_repo.setDeleteTime(new Date());
            update(capability_repo);
            return ResponseUtil.deleteSuccessResponse(Capability.class.getSimpleName());
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.deleteFailResponse(Capability.class.getSimpleName());
    }

    public static Map<String, String> statusMap;

    //获取权限状态map
    public Map<String, String> getPermissionStatusMap() {
        if (statusMap == null) {
            loadStatusMap();
        }
        return statusMap;
    }

    //加载权限map
    private void loadStatusMap() {
        statusMap = new LinkedHashMap<>();
        EPermissionStatus[] values = EPermissionStatus.values();
        String back_properties_prefix = "common.status.";
        for (EPermissionStatus eSpuStatus : values) {
            if (StringUtils.isNotBlank(BaseConfiguration.getMessage(back_properties_prefix + eSpuStatus.name()))) {
                statusMap.put(eSpuStatus.getStatus(), BaseConfiguration.getMessage(back_properties_prefix + eSpuStatus.name()));
            }
        }
    }

    //根据ids查询权限
    public List<Capability> getCapabilityByIds(List<Long> ids) {
        return capabilityRepository.findAllById(ids);
    }
}

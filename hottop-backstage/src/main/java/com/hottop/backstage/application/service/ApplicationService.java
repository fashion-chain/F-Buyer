package com.hottop.backstage.application.service;

import com.hottop.backstage.application.enums.EBgUser;
import com.hottop.backstage.role.service.ModuleService;
import com.hottop.core.model.user.Application;
import com.hottop.core.model.user.Module;
import com.hottop.core.repository.user.ApplicationRepository;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户service
 */
@Service("applicationService")
public class ApplicationService extends EntityBaseService<Application, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModuleService moduleService;

    @Override
    public EntityBaseRepository<Application, Long> repository() {
        return applicationRepository;
    }

    /**
     * 判断电话号码是否已经存在
     *
     * @param tel
     */
    public boolean telExists(String tel) {
        int count = applicationRepository.countByTel(tel);
        if (count >= 1) return true;
        return false;
    }

    /**
     * 根据id判断后台用户是否存在
     *
     * @return
     */
    public boolean bgUserExistsById(Long id) {
        int count = applicationRepository.countById(id);
        if (count >= 1) return true;
        return false;
    }

    /**
     * 创建 后台用户
     *
     * @param application
     * @return
     */
    @Transactional
    public Response saveBgUser(Application application) {
        try {
            String source_password = application.getPassword();
            application.setPassword(passwordEncoder.encode(source_password));
            application.setState(EBgUser.EBgUserStatus.ok.getStatus());
            super.save(application);
            return ResponseUtil.addSuccessResponse(Application.class.getSimpleName());
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(Application.class.getSimpleName());
    }

    /**
     * 更新 后台用户
     *
     * @param application
     * @param id
     * @return
     */
    @Transactional
    public Response updateBgUser(Application application, Long id) {
        if (!bgUserExistsById(id)) {
            return ResponseUtil.notExistResponse(Application.class.getSimpleName());
        }
        try {
            Application application_repo = applicationRepository.findById(id).get();
            transferIfFieldsNotBlank(application, application_repo);
            update(application_repo);
            //处理bgUser
            application_repo.setPassword("");
            return ResponseUtil.updateSuccessResponse(Application.class.getSimpleName());
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.updateFailResponse(Application.class.getSimpleName());
    }

    /**
     * 添加角色
     * @param modules
     * @param id
     * @return
     */
    public Response updateRoles(List<Module> modules, Long id) {
        if(modules == null || modules.size() < 1) {
            return ResponseUtil.notExistResponse("application.modules");
        }
        if(!bgUserExistsById(id)) {
            return ResponseUtil.notExistResponse(Application.class.getSimpleName());
        }
        Application application = applicationRepository.findById(id).get();
        application.setModules(modules);
        return ResponseUtil.updateSuccessResponse("application.modules");
    }

    private void transferIfFieldsNotBlank(Application source, Application destination) {
        if(StringUtils.isNotBlank(source.getState())){
            destination.setState(source.getState());
        }
        if(StringUtils.isNotBlank(source.getTel())){
            destination.setTel(source.getTel());
        }
        if(StringUtils.isNotBlank(source.getEmail())){
            destination.setEmail(source.getEmail());
        }
        if(StringUtils.isNotBlank(source.getRemark())){
            destination.setRemark(source.getRemark());
        }
    }

    public Application findByIdentification(String userName) {
        Application user = null;
        if(ValidatorUtil.ValidatePhone(userName)) {
            user = applicationRepository.findByTel(userName);
        } else {
            //根据用户名查找用户信息
            user = applicationRepository.findByUsername(userName);
        }
        return user;
    }

    //后台用户配置角色
    @Transactional
    public Response addRoles(List<Long> roleIds, Long appId) {
        Application application = findOne(Application.class, appId);
        if(application == null) {
            return ResponseUtil.notExistResponse(Application.class.getSimpleName());
        }
        try {
            List<Module> modules = moduleService.getRolesByIds(roleIds);
            application.setModules(modules);
            update(application);
            return ResponseUtil.addSuccessResponse("application.modules");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.addFailResponse("application.modules");
    }

    public static Map<String,String> statusMap;
    //获取权限状态map
    public Map<String,String> getBgUserStatusMap() {
        if (statusMap == null) {
            loadStatusMap();
        }
        return statusMap;
    }
    //加载权限map
    private void loadStatusMap() {
        statusMap = new LinkedHashMap<>();
        EBgUser.EBgUserStatus[] values = EBgUser.EBgUserStatus.values();
        String back_properties_prefix = "common.status.";
        for(EBgUser.EBgUserStatus status : values) {
            if(StringUtils.isNotBlank(BaseConfiguration.getMessage(back_properties_prefix + status.name()))) {
                statusMap.put(status.getStatus(), BaseConfiguration.getMessage(back_properties_prefix + status.name()));
            }
        }
    }

    //根据用户名判断用户是否已经存在
    public boolean bgUserExistsByUsername(String username) {
        int i = applicationRepository.countByUsername(username);
        return i >= 1;
    }
}

package com.hottop.backstage.bguser.service;

import com.hottop.backstage.bguser.model.BgUser;
import com.hottop.backstage.bguser.model.BgUserDto;
import com.hottop.backstage.bguser.repository.BgUserRepository;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.user.Role;
import com.hottop.core.model.user.User;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.UserRepository;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.GenerateFunction;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 后台用户service
 */
@Service("bgUserService")
public class BgUserService extends EntityBaseService<BgUser, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BgUserRepository bgUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EntityBaseRepository<BgUser, Long> repository() {
        return bgUserRepository;
    }

    /**
     * 判断电话号码是否已经存在
     *
     * @param tel
     */
    public boolean telExists(String tel) {
        int count = bgUserRepository.countByTel(tel);
        if (count >= 1) return true;
        return false;
    }

    /**
     * 根据id判断后台用户是否存在
     *
     * @return
     */
    public boolean bgUserExistsById(Long id) {
        int count = bgUserRepository.countById(id);
        if (count >= 1) return true;
        return false;
    }

    /**
     * 创建 后台用户
     *
     * @param bgUser
     * @return
     */
    @Transactional
    public Response saveBgUser(BgUser bgUser) {
        try {
            String source_password = bgUser.getPassword();
            bgUser.setPassword(passwordEncoder.encode(source_password));
            super.save(bgUser);
            return ResponseUtil.createOKResponse();
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.createErrorResponse("创建后台用户失败");
    }

    /**
     * 更新 后台用户
     *
     * @param bgUser
     * @param id
     * @return
     */
    @Transactional
    public Response updateBgUser(BgUserDto bgUser, Long id) {
        if (!bgUserExistsById(id)) {
            return ResponseUtil.createErrorResponse("后台用户ID不存在");
        }
        try {
            BgUser bgUser_repo = bgUserRepository.findById(id).get();
            transferIfFieldsNotBlank(bgUser, bgUser_repo);
            update(bgUser_repo);
            transferIfFieldsNotBlank(bgUser_repo, bgUser);
            return ResponseUtil.createOKResponse(BaseConfiguration.generalGson().toJson(bgUser));
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.createErrorResponse("更新后台用户失败");
    }

    /**
     * 添加角色
     * @param roles
     * @param id
     * @return
     */
    public Response addRoles(List<Role> roles, Long id) {
        if(roles == null || roles.size() < 1) {
            return ResponseUtil.createErrorResponse("更新角色列表为空");
        }
        if(!bgUserExistsById(id)) {
            return ResponseUtil.createErrorResponse("后台用户ID不存在");
        }
        BgUser bgUser = bgUserRepository.findById(id).get();

        return null;
    }

    private void transferIfFieldsNotBlank(BgUserDto source, BgUser destination) {

        if (StringUtils.isNotBlank(source.getState())) {
            destination.setState(source.getState());
        }
        if (StringUtils.isNotBlank(source.getTel())) {
            destination.setTel(source.getTel());
        }
        if (StringUtils.isNotBlank(source.getEmail())) {
            destination.setEmail(source.getEmail());
        }
        if (StringUtils.isNotBlank(source.getRemark())) {
            destination.setRemark(source.getRemark());
        }
    }

    //从后台实体转化成 vo
    private void transferIfFieldsNotBlank(BgUser source, BgUserDto destination) {

        if (StringUtils.isNotBlank(source.getUsername())) {
            destination.setUsername(source.getUsername());
        }
        if (StringUtils.isNotBlank(source.getState())) {
            destination.setState(source.getState());
        }
        if (StringUtils.isNotBlank(source.getTel())) {
            destination.setTel(source.getTel());
        }
        if (StringUtils.isNotBlank(source.getEmail())) {
            destination.setEmail(source.getEmail());
        }
        if (StringUtils.isNotBlank(source.getRemark())) {
            destination.setRemark(source.getRemark());
        }
    }


    public static void main(String[] args) {
        GenerateFunction.generateTwoSampleObjectTransferFunction(BgUser.class, BgUserDto.class);
    }


}

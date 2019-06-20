package com.hottop.backstage.role.validator;

import com.hottop.backstage.role.enums.ERoleStatus;
import com.hottop.core.model.user.Module;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RoleValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Module.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target == null) {
            errors.rejectValue(null, "", "role不能为空");
        }
        Module module = (Module) target;
//        if(!verifyERoleStatus(module.getState())) {
//            errors.rejectValue("state", "", "角色状态不合法");
//        }
        if(StringUtils.isNotBlank(module.getRemark()) && module.getRemark().length() > 200) {
            errors.rejectValue("remark", "", "角色备注不能超过200字符");
        }
    }

    //判断status是否合法
    private static boolean verifyERoleStatus (String status) {
        ERoleStatus[] values = ERoleStatus.values();
        for(ERoleStatus r: values) {
            if( r.status.equals(status) ) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(verifyERoleStatus(ERoleStatus.ok.status));
    }
}

package com.hottop.core.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    public static void errorsToMap(Map map, Errors errors) {
        if (errors.hasErrors()) {
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError oe : allErrors) {
                if (oe instanceof FieldError) {
                    FieldError fieldError = (FieldError) oe;
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                } else {
                    map.put(oe.getObjectName(), oe.getDefaultMessage());
                }
            }
        }
    }

    /**
     * 验证是否是手机号
     * @param phone
     * @return true 表示手机号合法
     */
    public static boolean ValidatePhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 判断密码是否合法
     * @param password
     * @return true 表示手机号合法
     */
    public static boolean ValidatePassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        return password.matches(regex);
    }

    /**
     * 判断两次密码是否一致
     * @param password
     * @param password_second
     * @return
     */
    public static boolean ValidatePasswordTwice(String password, String password_second) {
        return password.equals(password_second);
    }

    /**
     * 判断邮政编码
     * @param zipCode
     * @return
     */
    public static boolean ValidateZipCode(String zipCode) {
        String zipCodeRegex = "^\\d{6}$";
        return zipCode.matches(zipCodeRegex);
    }


}

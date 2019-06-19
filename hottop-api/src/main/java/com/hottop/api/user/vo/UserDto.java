package com.hottop.api.user.vo;

import lombok.Data;

@Data
public class UserDto {

    private String tel;//电话号码

    private String password;//密码

    private String confirmPassword;//确认密码

    private String verifyCode;//验证码

}

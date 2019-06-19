package com.hottop.api.sms;

import com.hottop.core.security.validate.code.sms.ESmsCodeType;

public class TestSms {

    public static void main(String[] args) {
        getChinese();
    }

    public static void getChinese() {
        String msg = "abc中国";
        msg = msg.replaceAll("[A-Za-z]","");
        System.out.println(msg);
    }
}

package com.hottop.core.security.validate.code.sms;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.security.properties.SecurityProperties;
import com.hottop.core.security.properties.SmsCodeProperties;
import com.hottop.core.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("aliSmsCodeSender")
public class AliSmsCodeSender implements SmsCodeSender {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void send(String mobile, String code, ESmsCodeType type) {
        SmsCodeProperties smsCodeProperties = securityProperties.getCode().getSms();
        Map map = new HashMap<String,String>();
        map.put("code", code);
        String jsonStr = BaseConfiguration.generalGson().toJson(map);
        if(type.equals(ESmsCodeType.Login)){
            SmsUtil.send(mobile, smsCodeProperties.getCurrentCompanyName(),
                    SmsUtil.ALI_SMS_TEMPLATE_SMS_LOGIN,jsonStr
                    );
        } else if(type.equals(ESmsCodeType.Registration)){
            SmsUtil.send(mobile, smsCodeProperties.getCurrentCompanyName(),
                    SmsUtil.ALI_SMS_TEMPLATE_USER_REGISTER,
                    jsonStr);
        }
    }

}

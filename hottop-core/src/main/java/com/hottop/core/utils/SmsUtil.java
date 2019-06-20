package com.hottop.core.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
<<<<<<< HEAD
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
=======
import com.hottop.core.security.validate.code.sms.ESmsCodeType;
import com.hottop.core.security.validate.code.sms.SmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

import java.util.Random;

/**
 * Created by lq on 2019/3/15.
 * ali 短信发送测试类
 */
public class SmsUtil {

<<<<<<< HEAD
    static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
=======
    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

    /** ali 短信服务 accessKeyId 固定值 */
    private static final String accessKeyId = "LTAIXyJXzfVdLwfL";
    /** ali 短信服务 secret 固定值 */
    private static final String secret = "I9A9fQ2ilfFTMZdors79a9S7iQRI7o";

    /**
     * 发送短信的签名 - > 北京凡生易美科技有限公司
     * 类似短信标题
     */
    public static final String ALI_SMS_SIGN_FSYM = "北京凡生易美科技有限公司";

    /**
     * 发送短信的签名 - > 北京凡生易美科技有限公司
     * 类似短信标题
     */
    public static final String ALI_SMS_SIGN_HQJH = "环球集货";
    /**
     * 短信模板 用户注册
     * JSON -> name:code value 自定义
     */
    public static final String ALI_SMS_TEMPLATE_USER_REGISTER = "SMS_160595616";
    /**
     * 短信模板 短信登录
     * JSON -> name:code value 自定义
     */
    public static final String ALI_SMS_TEMPLATE_SMS_LOGIN = "SMS_160535978";
    /**
     * 短信模板 修改密码
     * JSON -> name:code value 自定义
     */
    public static final String ALI_SMS_TEMPLATE_CHANGE_PASS = "SMS_160595617";

    /**
     * 阿里发送短信
     * @param phoneNumber 电话号码
     * @param aliSmsSign 发送短信的签名，类似短信标题
     * @param aliSmsTemplate 短信模板
     * @param jsonStr_aliSmsTemplate JSON -> code value
     */
    public static boolean send(String phoneNumber , String aliSmsSign , String aliSmsTemplate ,String jsonStr_aliSmsTemplate){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", aliSmsSign);
        request.putQueryParameter("TemplateCode", aliSmsTemplate);
        request.putQueryParameter("TemplateParam", jsonStr_aliSmsTemplate);
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info("ali-短信发送结果："+response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成随机的6位数，短信验证码
     * @return
     */
    public static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }

    public static void main(String[] args) {
        //testSms();
    }

    /**
     * 测试短信发送
     */
    public void testSms() {
        String accessKeyId = "LTAIXyJXzfVdLwfL";
        String secret = "I9A9fQ2ilfFTMZdors79a9S7iQRI7o";
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18330219446");
        request.putQueryParameter("SignName", "北京凡生易美科技有限公司");
        request.putQueryParameter("TemplateCode", "SMS_160515474");
        request.putQueryParameter("TemplateParam", "{\"code\":\"123456\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

<<<<<<< HEAD
    /**
     * 发送验证码的类型
     */
    public enum ESmsCodeType {
        Login("_login"), Registration("_registration");
        private String smsCodeType;

        ESmsCodeType(String smsCodeType) {
            this.smsCodeType = smsCodeType;
        }
    }
=======


>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
}


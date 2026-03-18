package com.ming.hospital.utils;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import java.util.logging.Logger;

public class SmsUtil {
    private static final Logger logger = Logger.getLogger(SmsUtil.class.getName());
    
    private static final String ACCESS_KEY_ID = "your_access_key_id";
    private static final String ACCESS_KEY_SECRET = "your_access_key_secret";
    private static final String SIGN_NAME = "医者天下";
    private static final String TEMPLATE_CODE = "SMS_123456789";
    
    private static Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(ACCESS_KEY_ID)
                .setAccessKeySecret(ACCESS_KEY_SECRET)
                .setEndpoint("dysmsapi.aliyuncs.com");
        return new Client(config);
    }

    public static boolean sendVerifyCode(String phone, String code) {
        try {
            Client client = createClient();
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setSignName(SIGN_NAME)
                    .setTemplateCode(TEMPLATE_CODE)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            logger.info("短信发送结果: " + response.getBody().getMessage());
            return "OK".equals(response.getBody().getCode());
        } catch (Exception e) {
            logger.severe("短信发送失败: " + e.getMessage());
            return false;
        }
    }

    public static boolean sendAppointmentNotice(String phone, String doctorName, String visitTime) {
        try {
            Client client = createClient();
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setSignName(SIGN_NAME)
                    .setTemplateCode("SMS_APPOINTMENT_TEMPLATE")
                    .setTemplateParam("{\"doctorName\":\"" + doctorName + "\",\"visitTime\":\"" + visitTime + "\"}");
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            return "OK".equals(response.getBody().getCode());
        } catch (Exception e) {
            logger.severe("预约通知发送失败: " + e.getMessage());
            return false;
        }
    }
}

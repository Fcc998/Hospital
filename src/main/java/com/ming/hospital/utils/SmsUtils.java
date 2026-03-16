package com.ming.hospital.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsUtils {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    public static boolean sendAppointmentSuccess(String phone, String name, String visitTime, String orderNo) {
        try {
            String content = String.format("【医者天下】尊敬的%s，您已成功预约医生，就诊时间：%s，请按时就诊！订单号：%s",
                    name, visitTime, orderNo);
            logger.info("发送短信至 {}: {}", phone, content);
            System.out.println("===== 短信发送模拟 =====");
            System.out.println("接收号码: " + phone);
            System.out.println("短信内容: " + content);
            System.out.println("========================");
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败", e);
            return false;
        }
    }

    public static boolean sendPaySuccess(String phone, String name, double amount, String payType) {
        try {
            String type = "alipay".equals(payType) ? "支付宝" : "微信支付";
            String content = String.format("【医者天下】尊敬的%s，您已通过%s成功支付挂号费%.2f元，祝您就诊愉快！",
                    name, type, amount);
            logger.info("发送短信至 {}: {}", phone, content);
            System.out.println("===== 短信发送模拟 =====");
            System.out.println("接收号码: " + phone);
            System.out.println("短信内容: " + content);
            System.out.println("========================");
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败", e);
            return false;
        }
    }
}

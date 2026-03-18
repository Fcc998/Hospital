package com.ming.hospital.service.impl;

import com.ming.hospital.dao.AppointmentMapper;
import com.ming.hospital.dao.PaymentOrderMapper;
import com.ming.hospital.dao.ScheduleMapper;
import com.ming.hospital.pojo.Appointment;
import com.ming.hospital.pojo.PaymentOrder;
import com.ming.hospital.pojo.Schedule;
import com.ming.hospital.service.PaymentService;
import com.ming.hospital.utils.CommonUtils;
import com.ming.hospital.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Autowired
    private PaymentOrderMapper paymentOrderMapper;
    
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public PaymentOrder createOrder(Long aid, Long uid, Integer payType) {
        Appointment appointment = appointmentMapper.selectByPrimaryKey(aid);
        if (appointment == null) {
            return null;
        }
        
        PaymentOrder existOrder = paymentOrderMapper.selectByAppointmentId(aid);
        if (existOrder != null && existOrder.getPayStatus() == 1) {
            return existOrder;
        }
        
        PaymentOrder order = new PaymentOrder();
        order.setOrderId(CommonUtils.getId());
        order.setAid(aid);
        order.setUid(uid);
        order.setOrderNo(generateOrderNo());
        order.setAmount(new BigDecimal("50.00"));
        order.setPayType(payType);
        order.setPayStatus(0);
        order.setCreatetime(new Date());
        order.setUpdatetime(new Date());
        
        paymentOrderMapper.insert(order);
        return order;
    }

    @Override
    public PaymentOrder getOrderByOrderNo(String orderNo) {
        return paymentOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    @Transactional
    public boolean processPaySuccess(String orderNo) {
        PaymentOrder order = paymentOrderMapper.selectByOrderNo(orderNo);
        if (order == null || order.getPayStatus() == 1) {
            return false;
        }
        
        int result = paymentOrderMapper.updatePayStatus(order.getOrderId(), 1);
        if (result > 0) {
            Appointment appointment = appointmentMapper.selectByPrimaryKey(order.getAid());
            if (appointment != null) {
                appointment.setStatus(1);
                appointment.setPayStatus(1);
                appointment.setPayTime(new Date());
                appointment.setOrderNo(orderNo);
                appointment.setUpdatetime(new Date());
                appointmentMapper.updateByPrimaryKeySelective(appointment);
                
                if (appointment.getScheduleId() != null) {
                    scheduleMapper.decreaseRemainNum(appointment.getScheduleId());
                }
                
                if (appointment.getAphone() != null) {
                    String doctorName = "医生";
                    String visitTime = appointment.getVisittime();
                    SmsUtil.sendAppointmentNotice(appointment.getAphone(), doctorName, visitTime);
                }
                
                logger.info("支付成功，订单号: " + orderNo);
            }
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getPayInfo(String orderNo, Integer payType) {
        PaymentOrder order = paymentOrderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            return null;
        }
        
        Map<String, Object> payInfo = new HashMap<>();
        payInfo.put("orderNo", orderNo);
        payInfo.put("amount", order.getAmount());
        payInfo.put("subject", "医院挂号费用");
        payInfo.put("body", "在线医院挂号服务");
        
        if (payType == 1) {
            payInfo.put("payUrl", "/payment/alipay?orderNo=" + orderNo);
            payInfo.put("payType", "支付宝");
        } else {
            payInfo.put("payUrl", "/payment/wechat?orderNo=" + orderNo);
            payInfo.put("payType", "微信支付");
        }
        
        return payInfo;
    }
    
    private String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String random = String.valueOf((int)((Math.random() * 9 + 1) * 1000));
        return timestamp + random;
    }
}

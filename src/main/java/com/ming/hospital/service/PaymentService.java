package com.ming.hospital.service;

import com.ming.hospital.pojo.PaymentOrder;
import java.util.Map;

public interface PaymentService {
    PaymentOrder createOrder(Long aid, Long uid, Integer payType);
    PaymentOrder getOrderByOrderNo(String orderNo);
    boolean processPaySuccess(String orderNo);
    Map<String, Object> getPayInfo(String orderNo, Integer payType);
}

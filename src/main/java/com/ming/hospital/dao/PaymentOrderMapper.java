package com.ming.hospital.dao;

import com.ming.hospital.pojo.PaymentOrder;
import org.apache.ibatis.annotations.Param;

public interface PaymentOrderMapper {
    int insert(PaymentOrder record);
    
    int updatePayStatus(@Param("orderId") Long orderId, @Param("payStatus") Integer payStatus);
    
    PaymentOrder selectByPrimaryKey(@Param("orderId") Long orderId);
    
    PaymentOrder selectByOrderNo(@Param("orderNo") String orderNo);
    
    PaymentOrder selectByAppointmentId(@Param("aid") Long aid);
}

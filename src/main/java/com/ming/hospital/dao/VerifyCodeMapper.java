package com.ming.hospital.dao;

import com.ming.hospital.pojo.VerifyCode;
import org.apache.ibatis.annotations.Param;

public interface VerifyCodeMapper {
    int insert(VerifyCode record);
    
    VerifyCode selectValidCode(@Param("target") String target, @Param("code") String code, @Param("type") Integer type);
    
    int markAsUsed(@Param("vid") Long vid);
    
    int deleteExpired();
}

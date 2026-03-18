package com.ming.hospital.service;

public interface VerifyCodeService {
    boolean sendEmailCode(String email);
    boolean sendPhoneCode(String phone);
    boolean verifyCode(String target, String code, Integer type);
}

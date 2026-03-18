package com.ming.hospital.service.impl;

import com.ming.hospital.dao.VerifyCodeMapper;
import com.ming.hospital.pojo.VerifyCode;
import com.ming.hospital.service.VerifyCodeService;
import com.ming.hospital.utils.CommonUtils;
import com.ming.hospital.utils.MailUtil;
import com.ming.hospital.utils.SmsUtil;
import com.ming.hospital.utils.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    
    private static final Logger logger = Logger.getLogger(VerifyCodeServiceImpl.class.getName());
    private static final int CODE_EXPIRE_MINUTES = 5;

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Override
    public boolean sendEmailCode(String email) {
        String code = VerifyCodeUtil.generateCode6();
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setVid(CommonUtils.getId());
        verifyCode.setTarget(email);
        verifyCode.setCode(code);
        verifyCode.setType(1);
        verifyCode.setUsed(0);
        verifyCode.setCreatetime(new Date());
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, CODE_EXPIRE_MINUTES);
        verifyCode.setExpireTime(calendar.getTime());
        
        int result = verifyCodeMapper.insert(verifyCode);
        if (result > 0) {
            try {
                new MailUtil(email, "医者天下验证码", "您的验证码是：" + code + "，有效期5分钟。").start();
                logger.info("邮箱验证码发送成功: " + email);
                return true;
            } catch (Exception e) {
                logger.severe("邮箱验证码发送失败: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean sendPhoneCode(String phone) {
        String code = VerifyCodeUtil.generateCode6();
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setVid(CommonUtils.getId());
        verifyCode.setTarget(phone);
        verifyCode.setCode(code);
        verifyCode.setType(2);
        verifyCode.setUsed(0);
        verifyCode.setCreatetime(new Date());
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, CODE_EXPIRE_MINUTES);
        verifyCode.setExpireTime(calendar.getTime());
        
        int result = verifyCodeMapper.insert(verifyCode);
        if (result > 0) {
            boolean sendResult = SmsUtil.sendVerifyCode(phone, code);
            if (sendResult) {
                logger.info("手机验证码发送成功: " + phone);
            }
            return sendResult;
        }
        return false;
    }

    @Override
    public boolean verifyCode(String target, String code, Integer type) {
        VerifyCode verifyCode = verifyCodeMapper.selectValidCode(target, code, type);
        if (verifyCode != null) {
            verifyCodeMapper.markAsUsed(verifyCode.getVid());
            return true;
        }
        return false;
    }
}

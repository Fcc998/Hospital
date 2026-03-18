package com.ming.hospital.web;

import com.ming.hospital.service.VerifyCodeService;
import com.ming.hospital.utils.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/verify")
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @RequestMapping("/sendEmailCode")
    @ResponseBody
    public Map<String, Object> sendEmailCode(@RequestParam String email) {
        Map<String, Object> result = new HashMap<>();
        
        if (!VerifyCodeUtil.isValidEmail(email)) {
            result.put("success", false);
            result.put("msg", "邮箱格式不正确");
            return result;
        }
        
        boolean success = verifyCodeService.sendEmailCode(email);
        result.put("success", success);
        result.put("msg", success ? "验证码已发送" : "验证码发送失败");
        return result;
    }

    @RequestMapping("/sendPhoneCode")
    @ResponseBody
    public Map<String, Object> sendPhoneCode(@RequestParam String phone) {
        Map<String, Object> result = new HashMap<>();
        
        if (!VerifyCodeUtil.isValidPhone(phone)) {
            result.put("success", false);
            result.put("msg", "手机号格式不正确");
            return result;
        }
        
        boolean success = verifyCodeService.sendPhoneCode(phone);
        result.put("success", success);
        result.put("msg", success ? "验证码已发送" : "验证码发送失败");
        return result;
    }
}

package com.ming.hospital.web;

import com.ming.hospital.pojo.User;
import com.ming.hospital.service.UserService;
import com.ming.hospital.service.VerifyCodeService;
import com.ming.hospital.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private VerifyCodeService verifyCodeService;

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public String register(User user, String verifyCode, Integer verifyType, Model model) {
        if (verifyCode != null && verifyType != null) {
            String target = verifyType == 1 ? user.getEmail() : user.getPhone();
            if (!verifyCodeService.verifyCode(target, verifyCode, verifyType)) {
                model.addAttribute("msg", "验证码错误或已过期");
                return "error";
            }
        }
        
        Date date = new Date();
        user.setCreatetime(date);
        user.setUpdatetime(date);
        user.setUid(CommonUtils.getId());
        user.setCode(UUID.randomUUID().toString().replace("-", ""));
        user.setState(1);
        
        boolean register = userService.register(user);
        if (register) {
            return "registerSuccess";
        }
        model.addAttribute("msg", "注册失败，请重试");
        return "error";
    }

    @RequestMapping("/active/{code}")
    public String active(@PathVariable String code, Model model) {
        if (code != null) {
            boolean active = userService.active(code);
            if (active) {
                return "redirect:/";
            } else {
                model.addAttribute("msg", "激活邮箱失败！您的激活码可能超过48小时！请重新注册！");
            }
        }
        return "error";
    }

    @RequestMapping(value = "/loginByCode", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> loginByCode(String target, String verifyCode, Integer verifyType, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        
        if (!verifyCodeService.verifyCode(target, verifyCode, verifyType)) {
            result.put("success", false);
            result.put("msg", "验证码错误或已过期");
            return result;
        }
        
        User user = userService.loginByTarget(target, verifyType);
        if (user != null) {
            session.setAttribute("user", user);
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "用户不存在，请先注册");
        }
        return result;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(User user, HttpServletRequest request) {
        User fullUser = userService.login(user);
        if (fullUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", fullUser);
            return "redirect:/";
        }
        request.setAttribute("msg", "账号或密码错误");
        return "error";
    }

    @RequestMapping("/checkUserName")
    @ResponseBody
    public Map<String, Object> checkUserName(String user) {
        Map<String, Object> map = new HashMap<>();
        boolean isExist = userService.checkUserName(user);
        map.put("isExist", isExist);
        return map;
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(String email) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getByEmail(email);
        map.put("isExist", user != null);
        return map;
    }

    @RequestMapping("/checkPhone")
    @ResponseBody
    public Map<String, Object> checkPhone(String phone) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getByPhone(phone);
        map.put("isExist", user != null);
        return map;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}

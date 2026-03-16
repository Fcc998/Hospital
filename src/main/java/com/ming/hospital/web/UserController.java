package com.ming.hospital.web;

import com.ming.hospital.pojo.User;
import com.ming.hospital.service.UserService;
import com.ming.hospital.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ming on 2017/11/17.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    public String register(User user,Model model) {
        Date date = new Date();
        user.setCreatetime(date);
        user.setUpdatetime(date);
        user.setUid(CommonUtils.getId());
        user.setCode(UUID.randomUUID().toString().replace("-",""));//激活码
        boolean register = userService.register(user);
        if(register){
            return "registerSuccess";
        }
        model.addAttribute("msg","不好意思！注册失败！请重新注册，");
        return "error";
    }
    @RequestMapping("/active/{code}")
    public String active(@PathVariable String code,Model model){
        if(code != null){
            boolean active = userService.active(code);
            if(active){
                return "redirect:/";
            }else {
                model.addAttribute("msg","激活邮箱失败！您的激活码可能超过48小时！请重新注册！");
            }
        }
        return "error";
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    public String login(User user,HttpServletRequest request){
        User fullUser =  userService.login(user);
        if(fullUser != null){
            HttpSession session = request.getSession();
            session.setAttribute("user",fullUser);
            return "forward:/";
        }
        request.setAttribute("msg","账号或密码错误！请重新登陆<a href = ''>医者天下</a>");
        return "error";
    }
    @RequestMapping("/checkUserName")
    @ResponseBody
    public Map checkUserName(String user){
        Map map = new HashMap();
        boolean isExist = userService.checkUserName(user);
        map.put("isExist",isExist ? true:false);
        return map;
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/sendPhoneCode")
    @ResponseBody
    public Map sendPhoneCode(String phone, HttpSession session){
        Map map = new HashMap();
        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        session.setAttribute("phoneCode", code);
        session.setAttribute("phoneCodeTime", System.currentTimeMillis());
        logger.info("手机验证码: {} (模拟发送)", code);
        map.put("success", true);
        map.put("msg", "验证码已发送");
        return map;
    }

    @RequestMapping("/checkPhoneCode")
    @ResponseBody
    public Map checkPhoneCode(String phone, String code, HttpSession session){
        Map map = new HashMap();
        String sessionCode = (String) session.getAttribute("phoneCode");
        Long codeTime = (Long) session.getAttribute("phoneCodeTime");
        if(sessionCode == null || codeTime == null){
            map.put("success", false);
            map.put("msg", "验证码不存在");
            return map;
        }
        if(System.currentTimeMillis() - codeTime > 5 * 60 * 1000){
            map.put("success", false);
            map.put("msg", "验证码已过期");
            return map;
        }
        if(!sessionCode.equals(code)){
            map.put("success", false);
            map.put("msg", "验证码错误");
            return map;
        }
        map.put("success", true);
        map.put("msg", "验证成功");
        return map;
    }

    @RequestMapping("/loginByPhone")
    @ResponseBody
    public Map loginByPhone(String phone, String code, HttpSession session){
        Map map = new HashMap();
        Map checkResult = checkPhoneCode(phone, code, session);
        if(!(Boolean)checkResult.get("success")){
            return checkResult;
        }
        User user = userService.findByPhone(phone);
        if(user == null){
            user = new User();
            user.setUid(CommonUtils.getId());
            user.setUser(phone);
            user.setPhone(phone);
            user.setName("手机用户");
            user.setPwd(CommonUtils.BCryptEncode(phone + "_default"));
            user.setCreatetime(new Date());
            user.setUpdatetime(new Date());
            user.setState(1);
            userService.register(user);
        }
        session.setAttribute("user", user);
        map.put("success", true);
        map.put("msg", "登录成功");
        return map;
    }

    @RequestMapping("/checkPhoneExist")
    @ResponseBody
    public Map checkPhoneExist(String phone){
        Map map = new HashMap();
        boolean isExist = userService.checkPhoneExist(phone);
        map.put("isExist", isExist);
        return map;
    }


}

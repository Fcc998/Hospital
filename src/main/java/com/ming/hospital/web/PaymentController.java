package com.ming.hospital.web;

import com.ming.hospital.pojo.PaymentOrder;
import com.ming.hospital.pojo.User;
import com.ming.hospital.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/createOrder")
    @ResponseBody
    public Map<String, Object> createOrder(@RequestParam Long aid, @RequestParam Integer payType, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Map.of("success", false, "msg", "请先登录");
        }
        
        PaymentOrder order = paymentService.createOrder(aid, user.getUid(), payType);
        if (order == null) {
            return Map.of("success", false, "msg", "创建订单失败");
        }
        
        Map<String, Object> payInfo = paymentService.getPayInfo(order.getOrderNo(), payType);
        payInfo.put("success", true);
        payInfo.put("orderNo", order.getOrderNo());
        return payInfo;
    }

    @RequestMapping("/alipay")
    public String alipay(@RequestParam String orderNo, Model model) {
        PaymentOrder order = paymentService.getOrderByOrderNo(orderNo);
        if (order == null) {
            model.addAttribute("msg", "订单不存在");
            return "error";
        }
        
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("amount", order.getAmount());
        model.addAttribute("payType", "支付宝");
        return "payment/alipay";
    }

    @RequestMapping("/wechat")
    public String wechat(@RequestParam String orderNo, Model model) {
        PaymentOrder order = paymentService.getOrderByOrderNo(orderNo);
        if (order == null) {
            model.addAttribute("msg", "订单不存在");
            return "error";
        }
        
        model.addAttribute("orderNo", orderNo);
        model.addAttribute("amount", order.getAmount());
        model.addAttribute("payType", "微信支付");
        return "payment/wechat";
    }

    @RequestMapping("/notify")
    @ResponseBody
    public String payNotify(@RequestParam String orderNo) {
        boolean success = paymentService.processPaySuccess(orderNo);
        return success ? "success" : "fail";
    }

    @RequestMapping("/simulate")
    @ResponseBody
    public Map<String, Object> simulatePay(@RequestParam String orderNo) {
        boolean success = paymentService.processPaySuccess(orderNo);
        return Map.of("success", success, "msg", success ? "支付成功" : "支付失败");
    }
}

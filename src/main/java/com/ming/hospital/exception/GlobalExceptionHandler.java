package com.ming.hospital.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Map<String, Object> handleBusinessException(BusinessException e, HttpServletRequest request) {
        logger.warning("业务异常: " + e.getMessage() + " - 请求路径: " + request.getRequestURI());
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", e.getCode());
        result.put("msg", e.getMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, HttpServletRequest request) {
        logger.severe("系统异常: " + e.getMessage() + " - 请求路径: " + request.getRequestURI());
        e.printStackTrace();
        model.addAttribute("msg", "系统繁忙，请稍后再试");
        return "error";
    }
}

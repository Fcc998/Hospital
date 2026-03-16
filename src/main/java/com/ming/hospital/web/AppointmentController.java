package com.ming.hospital.web;


import com.ming.hospital.pojo.Appointment;
import com.ming.hospital.pojo.Doctor;
import com.ming.hospital.pojo.User;
import com.ming.hospital.service.AppointmentService;
import com.ming.hospital.service.DoctorService;
import com.ming.hospital.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.*;
/**
 * Created by Ming on 2017/11/26.
 */
@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping("/{did}")
    public String show(@PathVariable Long did, String dayInfo, Model model){
        Doctor doctor = doctorService.selectById(did);
        model.addAttribute("doctor",doctor);
        model.addAttribute("dayInfo",dayInfo);
        return "appointment_form";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public String commit(Appointment appointment, HttpSession session){
        appointment.setAid(CommonUtils.getId());
        appointment.setCreatetime(new Date());
        appointment.setUpdatetime(new Date());
        String resultData = "";
        User user = (User)session.getAttribute("user");

        if(user == null){
            resultData = "-2";
        }else {
            appointment.setUid(user.getUid());
            if(!appointment.getName().equals(user.getName())){
                resultData = "-1";
            }else {
                if( !appointmentService.save(appointment)  ){
                    resultData = "-3";
                }else{
                    resultData = String.valueOf(appointment.getAid());
                }
            }
        }
        return resultData;
    }

    @RequestMapping("/pay/{aid}")
    public String showPayPage(@PathVariable Long aid, Model model) {
        logger.info("Showing payment page for appointment: {}", aid);
        Appointment appointment = appointmentService.getById(aid);
        model.addAttribute("appointment", appointment);
        return "pay";
    }

    @RequestMapping("/processPay")
    @ResponseBody
    public String processPay(Long aid, String payType, HttpSession session) {
        logger.info("Processing payment for appointment: {}, payType: {}", aid, payType);
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "-1";
            }
            Appointment appointment = appointmentService.getById(aid);
            if (appointment == null) {
                return "-2";
            }
            boolean success = appointmentService.updatePayStatus(aid, 1, payType, appointment.getPayAmount());
            if (success) {
                logger.info("Payment successful for appointment: {}", aid);
                return String.valueOf(aid);
            } else {
                return "-3";
            }
        } catch (Exception e) {
            logger.error("Payment processing error", e);
            return "-4";
        }
    }

    @RequestMapping("/paySuccess/{aid}")
    public String paySuccess(@PathVariable Long aid, Model model) {
        Appointment appointment = appointmentService.getById(aid);
        model.addAttribute("appointment", appointment);
        return "pay_success";
    }
}

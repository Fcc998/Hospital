package com.ming.hospital.web;

import com.ming.hospital.pojo.Appointment;
import com.ming.hospital.pojo.Doctor;
import com.ming.hospital.pojo.Schedule;
import com.ming.hospital.pojo.User;
import com.ming.hospital.service.AppointmentService;
import com.ming.hospital.service.DoctorService;
import com.ming.hospital.service.ScheduleService;
import com.ming.hospital.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/{did}")
    public String show(@PathVariable Long did, String dayInfo, Model model) {
        Doctor doctor = doctorService.selectById(did);
        model.addAttribute("doctor", doctor);
        model.addAttribute("dayInfo", dayInfo);
        
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = calendar.getTime();
        
        List<Schedule> schedules = scheduleService.getDoctorSchedule(did, startDate, endDate);
        model.addAttribute("schedules", schedules);
        
        return "appointment_form";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public Map<String, Object> commit(Appointment appointment, HttpSession session,
                                       @RequestParam(required = false) Long scheduleId) {
        Map<String, Object> result = new HashMap<>();
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.put("success", false);
            result.put("code", -2);
            result.put("msg", "请先登录");
            return result;
        }
        
        appointment.setAid(CommonUtils.getId());
        appointment.setCreatetime(new Date());
        appointment.setUpdatetime(new Date());
        appointment.setUid(user.getUid());
        appointment.setStatus(0);
        appointment.setPayStatus(0);
        
        if (scheduleId != null) {
            Schedule schedule = scheduleService.getScheduleById(scheduleId);
            if (schedule == null || schedule.getRemainNum() <= 0) {
                result.put("success", false);
                result.put("code", -4);
                result.put("msg", "号源不足");
                return result;
            }
            appointment.setScheduleId(scheduleId);
            appointment.setAmount(schedule.getPrice());
        }
        
        if (!appointmentService.save(appointment)) {
            result.put("success", false);
            result.put("code", -3);
            result.put("msg", "预约失败");
            return result;
        }
        
        result.put("success", true);
        result.put("aid", appointment.getAid());
        result.put("msg", "预约成功，请尽快支付");
        return result;
    }

    @RequestMapping("/toPay/{aid}")
    public String toPay(@PathVariable Long aid, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("msg", "请先登录");
            return "error";
        }
        
        Appointment appointment = appointmentService.getById(aid);
        if (appointment == null || !appointment.getUid().equals(user.getUid())) {
            model.addAttribute("msg", "预约不存在");
            return "error";
        }
        
        if (appointment.getPayStatus() == 1) {
            model.addAttribute("msg", "该预约已支付");
            return "error";
        }
        
        model.addAttribute("appointment", appointment);
        return "payment/select";
    }
}

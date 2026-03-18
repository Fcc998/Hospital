package com.ming.hospital.web;

import com.ming.hospital.pojo.Schedule;
import com.ming.hospital.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/getByDoctor/{did}")
    @ResponseBody
    public Map<String, Object> getByDoctor(@PathVariable Long did) {
        Map<String, Object> result = new HashMap<>();
        
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = calendar.getTime();
        
        List<Schedule> schedules = scheduleService.getDoctorSchedule(did, startDate, endDate);
        
        result.put("success", true);
        result.put("schedules", schedules);
        return result;
    }

    @RequestMapping("/getRemainNum/{sid}")
    @ResponseBody
    public Map<String, Object> getRemainNum(@PathVariable Long sid) {
        Map<String, Object> result = new HashMap<>();
        
        Schedule schedule = scheduleService.getScheduleById(sid);
        if (schedule != null) {
            result.put("success", true);
            result.put("remainNum", schedule.getRemainNum());
            result.put("totalNum", schedule.getTotalNum());
        } else {
            result.put("success", false);
            result.put("msg", "号源不存在");
        }
        return result;
    }
}

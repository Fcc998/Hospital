package com.ming.hospital.service;

import com.ming.hospital.pojo.Schedule;
import java.util.Date;
import java.util.List;

public interface ScheduleService {
    Schedule getScheduleById(Long sid);
    Schedule getScheduleByDoctorAndDate(Long did, Date date, String timePeriod);
    List<Schedule> getDoctorSchedule(Long did, Date startDate, Date endDate);
    boolean decreaseRemainNum(Long sid);
    boolean increaseRemainNum(Long sid);
    boolean initSchedule(Long did, Date date, String timePeriod, Integer totalNum);
}

package com.ming.hospital.service.impl;

import com.ming.hospital.dao.ScheduleMapper;
import com.ming.hospital.pojo.Schedule;
import com.ming.hospital.service.ScheduleService;
import com.ming.hospital.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public Schedule getScheduleById(Long sid) {
        return scheduleMapper.selectByPrimaryKey(sid);
    }

    @Override
    public Schedule getScheduleByDoctorAndDate(Long did, Date date, String timePeriod) {
        return scheduleMapper.selectByDoctorAndDate(did, date, timePeriod);
    }

    @Override
    public List<Schedule> getDoctorSchedule(Long did, Date startDate, Date endDate) {
        return scheduleMapper.selectByDoctorId(did, startDate, endDate);
    }

    @Override
    @Transactional
    public boolean decreaseRemainNum(Long sid) {
        return scheduleMapper.decreaseRemainNum(sid) > 0;
    }

    @Override
    @Transactional
    public boolean increaseRemainNum(Long sid) {
        return scheduleMapper.increaseRemainNum(sid) > 0;
    }

    @Override
    public boolean initSchedule(Long did, Date date, String timePeriod, Integer totalNum) {
        Schedule schedule = new Schedule();
        schedule.setSid(CommonUtils.getId());
        schedule.setDid(did);
        schedule.setScheduleDate(date);
        schedule.setTimePeriod(timePeriod);
        schedule.setTotalNum(totalNum);
        schedule.setRemainNum(totalNum);
        schedule.setPrice(new BigDecimal("50.00"));
        schedule.setCreatetime(new Date());
        schedule.setUpdatetime(new Date());
        return scheduleMapper.insert(schedule) > 0;
    }
}

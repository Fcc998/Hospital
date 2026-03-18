package com.ming.hospital.dao;

import com.ming.hospital.pojo.Schedule;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

public interface ScheduleMapper {
    int insert(Schedule record);
    
    int updateRemainNum(@Param("sid") Long sid, @Param("num") Integer num);
    
    Schedule selectByPrimaryKey(@Param("sid") Long sid);
    
    Schedule selectByDoctorAndDate(@Param("did") Long did, @Param("date") Date date, @Param("timePeriod") String timePeriod);
    
    List<Schedule> selectByDoctorId(@Param("did") Long did, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    int decreaseRemainNum(@Param("sid") Long sid);
    
    int increaseRemainNum(@Param("sid") Long sid);
}

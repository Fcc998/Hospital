package com.ming.hospital.service;

import com.ming.hospital.pojo.Appointment;
import java.util.List;

public interface AppointmentService {

    Integer selectTimesFromHospital(Long hid);

    Boolean save(Appointment appointment);

    List<Appointment> listAll();
    
    Appointment getById(Long aid);
    
    List<Appointment> getByUid(Long uid);

}

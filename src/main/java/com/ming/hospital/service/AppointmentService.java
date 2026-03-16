package com.ming.hospital.service;

import com.ming.hospital.pojo.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ming on 2017/11/17.
 */

public interface AppointmentService {

    Integer selectTimesFromHospital(Long hid);

    Boolean save(Appointment appointment);

    List<Appointment> listAll();

    int countByDoctorAndTime(Long did, String visitTime);

    Appointment getById(Long aid);

    Boolean updatePayStatus(Long aid, Integer payStatus, String payType, Double payAmount);

}

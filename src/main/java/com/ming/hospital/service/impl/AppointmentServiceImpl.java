package com.ming.hospital.service.impl;

import com.ming.hospital.dao.AppointmentMapper;
import com.ming.hospital.pojo.Appointment;
import com.ming.hospital.pojo.AppointmentExample;
import com.ming.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Override
    public Integer selectTimesFromHospital(Long hid) {
        Integer integer = appointmentMapper.selectTimesFromHospital(hid);
        return integer;
    }

    @Override
    public Boolean save(Appointment appointment) {
        try {
            appointmentMapper.insert(appointment);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Appointment> listAll() {
        List<Appointment> appointments = appointmentMapper.selectByExample(new AppointmentExample());
        return appointments;
    }

    @Override
    public Appointment getById(Long aid) {
        return appointmentMapper.selectByPrimaryKey(aid);
    }

    @Override
    public List<Appointment> getByUid(Long uid) {
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andUidEqualTo(uid);
        example.setOrderByClause("createtime DESC");
        return appointmentMapper.selectByExample(example);
    }
}

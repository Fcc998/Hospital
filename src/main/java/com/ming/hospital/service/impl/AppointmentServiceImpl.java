package com.ming.hospital.service.impl;

import com.ming.hospital.dao.AppointmentMapper;
import com.ming.hospital.pojo.Appointment;
import com.ming.hospital.pojo.AppointmentExample;
import com.ming.hospital.service.AppointmentService;
import com.ming.hospital.utils.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Ming on 2017/11/17.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Override
    public Integer selectTimesFromHospital(Long hid) {
        Integer integer = appointmentMapper.selectTimesFromHospital(hid);
        return integer;
    }

    @Override
    public Boolean save(Appointment appointment) {
        try{
            appointmentMapper.insert(appointment);
            logger.info("Appointment saved successfully: {}", appointment.getAid());
            return true;
        }catch (Exception e){
            logger.error("Save appointment error", e);
            return false;
        }
    }

    @Override
    public List<Appointment> listAll() {
        List<Appointment> appointments = appointmentMapper.selectByExample(new AppointmentExample());
        return appointments;
    }

    @Override
    public int countByDoctorAndTime(Long did, String visitTime) {
        AppointmentExample example = new AppointmentExample();
        example.createCriteria().andDidEqualTo(did).andVisittimeEqualTo(visitTime);
        return (int) appointmentMapper.countByExample(example);
    }

    @Override
    public Appointment getById(Long aid) {
        return appointmentMapper.selectByPrimaryKey(aid);
    }

    @Override
    public Boolean updatePayStatus(Long aid, Integer payStatus, String payType, Double payAmount) {
        try {
            Appointment existing = appointmentMapper.selectByPrimaryKey(aid);
            if (existing == null) {
                logger.warn("Appointment not found: {}", aid);
                return false;
            }
            Appointment appointment = new Appointment();
            appointment.setAid(aid);
            appointment.setPayStatus(payStatus);
            appointment.setPayType(payType);
            appointment.setPayAmount(payAmount != null ? payAmount : 15.0);
            appointment.setPayTime(new Date());
            appointment.setUpdatetime(new Date());
            int result = appointmentMapper.updateByPrimaryKeySelective(appointment);
            if (result > 0 && payStatus == 1) {
                logger.info("Payment successful for appointment: {}, sending SMS...", aid);
                SmsUtils.sendAppointmentSuccess(existing.getAphone(), existing.getName(), existing.getVisittime(), String.valueOf(aid));
                SmsUtils.sendPaySuccess(existing.getAphone(), existing.getName(), appointment.getPayAmount(), payType);
            }
            return result > 0;
        } catch (Exception e) {
            logger.error("Update pay status error", e);
            return false;
        }
    }

}

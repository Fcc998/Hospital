package com.ming.hospital.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Schedule {
    private Long sid;
    private Long did;
    private Date scheduleDate;
    private String timePeriod;
    private Integer totalNum;
    private Integer remainNum;
    private BigDecimal price;
    private Date createtime;
    private Date updatetime;
    
    private Doctor doctor;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod == null ? null : timePeriod.trim();
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "sid=" + sid +
                ", did=" + did +
                ", scheduleDate=" + scheduleDate +
                ", timePeriod='" + timePeriod + '\'' +
                ", totalNum=" + totalNum +
                ", remainNum=" + remainNum +
                ", price=" + price +
                '}';
    }
}

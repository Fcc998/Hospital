package com.ming.hospital.pojo;

import java.util.Date;

public class VerifyCode {
    private Long vid;
    private String target;
    private String code;
    private Integer type;
    private Date expireTime;
    private Integer used;
    private Date createtime;

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "vid=" + vid +
                ", target='" + target + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", expireTime=" + expireTime +
                ", used=" + used +
                '}';
    }
}

package com.ming.hospital.pojo;

import java.util.Date;

public class Comment {
    private Long cid;
    private Long did;
    private Long uid;
    private Float score;
    private String content;
    private Date createtime;
    private String username;
    private String doctorname;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", did=" + did +
                ", uid=" + uid +
                ", score=" + score +
                ", content='" + content + '\'' +
                ", createtime=" + createtime +
                ", username='" + username + '\'' +
                ", doctorname='" + doctorname + '\'' +
                '}';
    }
}

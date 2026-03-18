package com.ming.hospital.service;

import com.ming.hospital.pojo.User;

public interface UserService {

    boolean register(User user);
    boolean active(String code);
    User login(User user);
    boolean checkUserName(String user);
    User loginByTarget(String target, Integer type);
    User getByEmail(String email);
    User getByPhone(String phone);
}

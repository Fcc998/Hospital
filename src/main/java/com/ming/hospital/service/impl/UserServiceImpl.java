package com.ming.hospital.service.impl;

import com.ming.hospital.dao.UserMapper;
import com.ming.hospital.pojo.User;
import com.ming.hospital.pojo.UserExample;
import com.ming.hospital.service.UserService;
import com.ming.hospital.utils.BCryptUtil;
import com.ming.hospital.utils.CommonUtils;
import com.ming.hospital.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        String pwd = BCryptUtil.hashPassword(user.getPwd());
        user.setPwd(pwd);
        int insert = userMapper.insert(user);
        new MailUtil(user).start();
        return insert > 0;
    }

    @Override
    public boolean active(String code) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andCodeEqualTo(code);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() > 0) {
            User user = users.get(0);
            Long createTime = user.getCreatetime().getTime();
            Long nowTime = System.currentTimeMillis();
            double day = (nowTime - createTime) / 1000.0 / 60 / 60 / 24;
            if (day < 2) {
                user.setState(1);
                user.setUpdatetime(new Date());
                int i = userMapper.updateByPrimaryKeySelective(user);
                return i > 0;
            } else {
                userMapper.deleteByPrimaryKey(user.getUid());
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserEqualTo(user.getUser());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() > 0) {
            User dbUser = users.get(0);
            if (BCryptUtil.checkPassword(user.getPwd(), dbUser.getPwd())) {
                return dbUser;
            }
        }
        return null;
    }

    @Override
    public boolean checkUserName(String user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserEqualTo(user);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() > 0;
    }

    @Override
    public User loginByTarget(String target, Integer type) {
        UserExample userExample = new UserExample();
        if (type == 1) {
            userExample.createCriteria().andEmailEqualTo(target);
        } else {
            userExample.createCriteria().andPhoneEqualTo(target);
        }
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public User getByEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public User getByPhone(String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() > 0 ? users.get(0) : null;
    }
}

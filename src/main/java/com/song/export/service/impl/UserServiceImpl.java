package com.song.export.service.impl;

import com.song.export.dao.master.UserMapper;
import com.song.export.model.bean.master.User;
import com.song.export.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.queryAll();
    }

    @Override
    public User findUserById(long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public void edit(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}

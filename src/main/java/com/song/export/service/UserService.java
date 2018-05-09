package com.song.export.service;

import com.song.export.model.bean.master.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User findUserById(long id);

    void save(User user);

    void edit(User user);

    void delete(long id);


}

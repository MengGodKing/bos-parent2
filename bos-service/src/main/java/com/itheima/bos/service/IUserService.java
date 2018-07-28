package com.itheima.bos.service;

import com.itheima.bos.domain.User;

public interface IUserService {

    public User login(User model);

    void editPassword(String id, String password);
}

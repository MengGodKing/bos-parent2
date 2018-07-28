package com.itheima.bos.service.impl;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public User login(User user) {

         User user1 = userDao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
        return user1;
    }

    @Override
    public void editPassword(String id, String password) {
        //使用MD5加密
        String pass = MD5Utils.md5(password);
        userDao.executeUpdate("user.editpassword",pass,id);
    }
}

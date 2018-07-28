package com.itheima.bos.dao.impl;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;
import com.itheima.bos.utils.MD5Utils;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class IUserDaoImpl extends IBaseDaoImpl<User> implements IUserDao {

    /**
     * 根据用户名和密码查询用户
     */
    public User findUserByUsernameAndPassword(String username, String password) {
        //使用MD5加密数据
        String mdPassword = MD5Utils.md5(password);
        String hql = "FROM User u WHERE u.username = ? AND u.password = ?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,mdPassword);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}

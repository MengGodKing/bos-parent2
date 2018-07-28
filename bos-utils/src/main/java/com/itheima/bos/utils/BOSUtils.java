package com.itheima.bos.utils;

import com.itheima.bos.domain.User;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

public class BOSUtils {
    //获取session对象
    public static HttpSession getSession(){
        return ServletActionContext.getRequest().getSession();
    }
    //获取登陆用户对象
    public static User getLoginUser(){
        return (User) getSession().getAttribute("loginUser");
    }

}

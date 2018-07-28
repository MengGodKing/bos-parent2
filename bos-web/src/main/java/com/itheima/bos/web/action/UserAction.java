package com.itheima.bos.web.action;



import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@Scope("prototype")
public class UserAction extends IBaseAction<User> {

    //属性驱动，接收页面输入的验证码
    private  String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Autowired
    private IUserService iUserService;

    public String login(){
        //从session中获取生成的验证码
        String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        //校验验证码是否正确
        if (StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
            User user = iUserService.login(model);
            if (user!=null){
                //登陆成功，将user对象放入session中，跳转到首页
                ServletActionContext.getRequest().getSession().setAttribute("loginUser",user);
                return HOME;
            }else {
                this.addActionError("用户名或密码错误!");
                return LOGIN;
            }
        }else {
            this.addActionError("输入的验证码错误!");
            return LOGIN;
        }
    }

    /**
     * 用户注销
     */
    public String logout(){
        ServletActionContext.getRequest().getSession().invalidate();
        return LOGIN;
    }

    /**
     * 修改密码
     */
    public String editPassword() throws IOException {

        User user = BOSUtils.getLoginUser();
        String flat = "0";
        try {
            iUserService.editPassword(user.getId(),model.getPassword());
            flat = "1";
        }catch (Exception e){
            e.printStackTrace();
        }
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ServletActionContext.getResponse().getWriter().write(flat);
        return NONE;
    }
}

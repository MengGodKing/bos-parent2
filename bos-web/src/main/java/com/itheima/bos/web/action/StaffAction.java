package com.itheima.bos.web.action;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class StaffAction extends IBaseAction<Staff> {


    private String ids;

    @Autowired
    private IStaffService staffService;
    public String add(){
        staffService.add(model);
        return LIST;
    }

    /**
     * 分页查询方法
     * @throws IOException
     */
    public String pageQuery() throws IOException{


        staffService.pageQuery(pageBean);

        this.java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});

        return NONE;
    }

    /**
     * 作废
     * @return
     */
    public String delete(){
        staffService.delete(ids);
        return LIST;
    }


    /**
     * 修改取派員
     * @return
     */
    public String edit(){
        Staff staff = staffService.fingById(model.getId());
        staff.setName(model.getName());
        staff.setTelephone(model.getTelephone());
        staff.setHaspda(model.getHaspda());
        staff.setStation(model.getStation());
        staff.setStandard(model.getStandard());

        staffService.update(staff);

        return LIST;
    }




    public String listajax(){

        List<Staff> list = staffService.findListNotDelete();
        java2Json(list,new String[]{"decidedzones"});
        return NONE;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}

package com.itheima.bos.web.action;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends IBaseAction<Decidedzone> {

    private  String[] subareaid;
    private String decideid;

    public void setDecideid(String decideid) {
        this.decideid = decideid;
    }

    @Autowired
    private DecidedzoneService decidedzoneService;

    public void setSubareaid(String[] subareaid) {
        this.subareaid = subareaid;
    }

    public String add(){
        decidedzoneService.save(model,subareaid);
        return LIST;
    }

    public String pageQuery(){

        decidedzoneService.pageQuery(pageBean);
        this.java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
        return NONE;
    }

    @Autowired
    private ICustomerService customerService;
    public String findListHasAssociation(){
        List<Customer> customers = customerService.findListHasAssociation(decideid);
        java2Json(customers,new String[]{});
        return NONE;
    }

    public String findListNotAssociation(){
        List<Customer> customers = customerService.findListNotAssociation();
        java2Json(customers,new String[]{});
        return NONE;
    }


    //属性驱动，接收页面提交的多个客户id
    private List<Integer> customerIds;

    /**
     * 远程调用crm服务，将客户关联到定区
     * @return
     */
    public String assigncustomerstodecidedzone(){
        customerService.assigncustomerstodecidedzone(model.getId(),customerIds);

        return LIST;
    }

    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }
}

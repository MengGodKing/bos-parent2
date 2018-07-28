package com.itheima.bos.service.impl;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IStaffServiceImpl implements IStaffService {
    @Autowired
    private IStaffDao staffDao;

    @Override
    public void add(Staff staff) {
        staffDao.save(staff);
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        staffDao.pageQuery(pageBean);
    }

    @Override
    public void delete(String ids) {
        if (StringUtils.isNotBlank(ids)){
            String[] strs = ids.split("/+");
            for (String str:strs) {
                staffDao.executeUpdate("staff.delete",str);
            }
        }
    }

    @Override
    public Staff fingById(String id) {
        return staffDao.findById(id);
    }

    @Override
    public void update(Staff staff) {
        staffDao.update(staff);
    }


    @Override
    public List<Staff> findListNotDelete() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
        detachedCriteria.add(Restrictions.eq("deltag","0"));
        List<Staff> staffList = staffDao.findByCriteria(detachedCriteria);
        return staffList;
    }
}

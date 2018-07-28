package com.itheima.bos.service;


import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

import java.util.List;

public interface IStaffService {

    public void add(Staff staff);
    public void pageQuery(PageBean pageBean);

    public void delete(String ids);

    Staff fingById(String id);

    public void update(Staff staff);

    public List<Staff> findListNotDelete();
}

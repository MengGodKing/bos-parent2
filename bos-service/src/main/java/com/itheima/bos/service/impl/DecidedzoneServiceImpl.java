package com.itheima.bos.service.impl;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubAreaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {

    @Autowired
    private DecidedzoneDao decidedzoneDao;
    @Autowired
    private SubAreaDao subAreaDao;

    @Override
    public void save(Decidedzone model, String[] subareas) {
        decidedzoneDao.save(model);
        for (String id : subareas) {
            Subarea subarea = subAreaDao.findById(id);
            //model.getSubareas().add(subarea);一方（定区）已经放弃维护外键权利，只能由多方（分区）负责维护
            subarea.setDecidedzone(model);//分区关联定区
        }
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        decidedzoneDao.pageQuery(pageBean);
    }
}

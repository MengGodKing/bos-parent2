package com.itheima.bos.service.impl;

import com.itheima.bos.dao.SubAreaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubAreaService;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
    @Autowired
    private SubAreaDao subAreaDao;

    @Override
    public void save(Subarea subarea) {
        subAreaDao.save(subarea);
    }

    @Override
    public void pageQuery(PageBean pageBean) {
        subAreaDao.pageQuery(pageBean);
    }

    @Override
    public List<Subarea> findAll() {
        return subAreaDao.findAll();
    }

    @Override
    public List<Subarea> findListNotAssociation() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
        detachedCriteria.add(Restrictions.isNull("decidedzone"));
        List<Subarea> subareas = subAreaDao.findByCriteria(detachedCriteria);
        return subareas;
    }

    @Override
    public List<Subarea> findSubareListByDecideareId(String id) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Subarea.class);
        criteria.add(Restrictions.eq("decidedzone.id",id));

        return subAreaDao.findByCriteria(criteria);
    }
}

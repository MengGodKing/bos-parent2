package com.itheima.bos.service.impl;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private IRegionDao regionDao;

    @Override
    public void saveBatch(List<Region> regionList) {
        for (Region region:regionList) {
            regionDao.saveOrUpdate(region);
        }

    }

    @Override
    public void pageQuery(PageBean pageBean) {
        regionDao.pageQuery(pageBean);
    }

    @Override
    public List<Region> findAll() {
        return (List<Region>) regionDao.findAll();
    }

    @Override
    public List<Region> findByQ(String q) {
        return regionDao.findByQ(q);
    }
}

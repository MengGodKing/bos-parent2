package com.itheima.bos.dao.impl;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import org.springframework.stereotype.Repository;
import com.itheima.bos.domain.Region;

import java.util.List;

@Repository
public class IRegionDaoImpl extends IBaseDaoImpl<Region> implements IRegionDao {
    @Override
    public List<Region> findByQ(String q) {
        String hql = "FROM Region r WHERE r.shortcode LIKE ? "
                + "	OR r.citycode LIKE ? OR r.province LIKE ? "
                + "OR r.city LIKE ? OR r.district LIKE ?";
        List<Region> list = (List<Region>) this.getHibernateTemplate().
                find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
        return null;
    }
}

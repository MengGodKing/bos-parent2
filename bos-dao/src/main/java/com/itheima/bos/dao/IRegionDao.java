package com.itheima.bos.dao;

import com.itheima.bos.domain.Region;

import java.util.List;

public interface IRegionDao extends IBaseDao<Region> {
    List<Region> findByQ(String q);

}

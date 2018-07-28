package com.itheima.bos.service;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

import java.util.List;

public interface SubAreaService {

    public void save(Subarea subarea);

    public void pageQuery(PageBean pageBean);

    public List<Subarea> findAll();

    public List<Subarea> findListNotAssociation();

    public List<Subarea> findSubareListByDecideareId(String id);
}

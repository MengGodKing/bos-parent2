package com.itheima.bos.dao;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
    public void save(T entity);
    public void delete(T entity);
    public void update(T entity);
    public void saveOrUpdate(T entity);
    public T findById(Serializable id);
    public List<T> findAll();
    public void executeUpdate(String queryName,Object...Objects);
    public void pageQuery(PageBean pageBean);

    public List<T> findByCriteria(DetachedCriteria detachedCriteria);

}

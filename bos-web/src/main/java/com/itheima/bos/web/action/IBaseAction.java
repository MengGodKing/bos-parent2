package com.itheima.bos.web.action;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class IBaseAction<T> extends ActionSupport implements ModelDriven<T> {
    public static final String HOME = "home";
    public static final String LIST = "list";


    protected PageBean pageBean = new PageBean();


    //创建离线提交查询对象
    public void setPage(int page) {
        pageBean.setCurrentPage(page);
    }

    public void setRows(int rows) {
        pageBean.setPageSize(rows);
    }

    //模型对象
    protected T model;
    public T getModel() {
        return model;
    }

    //在构造方法中动态获取实体类型，通过反射创建model对象
    public IBaseAction() {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        //获得BaseAction上声明的泛型数组
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class<T> entityClass = (Class<T>) actualTypeArguments[0];
        pageBean.setDetachedCriteria(DetachedCriteria.forClass(entityClass));
        //通过反射创建对象
        try {
            model = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     *  此方法用于向客户端回写json数据
     * @param obj 需要转换成json的对象
     * @param exclued 指明需要转换成的json对象不需要包含哪些属性
     */
    public void java2Json(Object obj,String[] exclued){
        //使用json-lib将PageBean对象转为json，通过输出流写回页面中
        //JSONObject---将单一对象转为json
        //JSONArray----将数组或者集合对象转为json
        JsonConfig jsonConfig = new JsonConfig();
        //指定哪些属性不需要转json
        jsonConfig.setExcludes(exclued);
        String json = JSONObject.fromObject(obj,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void java2Json(List o ,String[] exclueds){
        JsonConfig jsonConfig = new JsonConfig();
        //指定哪些属性不需要转json
        jsonConfig.setExcludes(exclueds);
        String json = JSONArray.fromObject(o,jsonConfig).toString();
        ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
        try {
            ServletActionContext.getResponse().getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

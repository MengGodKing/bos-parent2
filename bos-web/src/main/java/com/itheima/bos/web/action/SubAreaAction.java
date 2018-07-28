package com.itheima.bos.web.action;


import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubAreaService;
import com.itheima.bos.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
public class SubAreaAction extends IBaseAction<Subarea> {


    @Autowired
    private SubAreaService subAreaService;
    public String add(){

        subAreaService.save(model);

        return LIST;
    }



    public String pageQuery(){

        DetachedCriteria dc = pageBean.getDetachedCriteria();

        //动态添加过滤条件
        String addresskey = model.getAddresskey();

        if (StringUtils.isNotBlank(addresskey)){
            dc.add(Restrictions.like("addresskey","%"+addresskey+"%"));
        }

        Region region = model.getRegion();
        if (region!=null){
            String province = region.getProvince();
            String city = region.getCity();
            String district = region.getDistrict();
            dc.createAlias("region","r");
            if(StringUtils.isNotBlank(province)){
                //添加过滤条件，根据省份模糊查询-----多表关联查询，使用别名方式实现
                //参数一：分区对象中关联的区域对象属性名称
                //参数二：别名，可以任意
                dc.add(Restrictions.like("r.province", "%"+province+"%"));
            }
            if(StringUtils.isNotBlank(city)){
                //添加过滤条件，根据市模糊查询-----多表关联查询，使用别名方式实现
                //参数一：分区对象中关联的区域对象属性名称
                //参数二：别名，可以任意
                dc.add(Restrictions.like("r.city", "%"+city+"%"));
            }
            if(StringUtils.isNotBlank(district)){
                //添加过滤条件，根据区模糊查询-----多表关联查询，使用别名方式实现
                //参数一：分区对象中关联的区域对象属性名称
                //参数二：别名，可以任意
                dc.add(Restrictions.like("r.district", "%"+district+"%"));
            }
        }

        subAreaService.pageQuery(pageBean);
        this.java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize","decidedzone","subareas"});
        return NONE;
    }

    /**
     * 分区数据导出功能
     */
    public String exportXls() throws IOException {
        //查找数据
        List<Subarea> subareas = subAreaService.findAll();
        HSSFWorkbook workbook = getHSSFWorkbook(subareas);

        writeToClient(workbook,"分区数据.xls");

        return NONE;
    }

    /**
     * 查询不在定区内的分区
     */
    public String listAjx(){

        List<Subarea> subareas = subAreaService.findListNotAssociation();
            java2Json(subareas,new String[]{"decidedzone","region"});
        return NONE;
    }


    /**
     * 根据定区id查询所对应的关联分区立标
     */
    public String findSubareList(){
        List<Subarea> subareas = subAreaService.findSubareListByDecideareId(model.getDecidedzone().getId());
        java2Json(subareas,new String[]{"decidedzone","subareas"});
        return NONE;
    }


    /**
     * 在内存中创建一个自定义的 HSSFWorkbook
     * @param list
     * @return
     */
    private HSSFWorkbook getHSSFWorkbook(List<Subarea> list){
        //第二步：使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("分区数据");
        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("分区编号");
        headRow.createCell(1).setCellValue("开始编号");
        headRow.createCell(2).setCellValue("结束编号");
        headRow.createCell(3).setCellValue("位置信息");
        headRow.createCell(4).setCellValue("省市区");

        for (Subarea subarea : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(subarea.getId());
            dataRow.createCell(1).setCellValue(subarea.getStartnum());
            dataRow.createCell(2).setCellValue(subarea.getEndnum());
            dataRow.createCell(3).setCellValue(subarea.getPosition());
            dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
        }




        return workbook;
    }


    /**
     * 向客户端传出文件
     * @param fileName
     */
    private void writeToClient(HSSFWorkbook workbook,String fileName) throws IOException {
        ServletContext servletContext = ServletActionContext.getServletContext();
        HttpServletResponse response = ServletActionContext.getResponse();
        //获取文件类型
        String type = servletContext.getMimeType(fileName);
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType(type);

        //获取客户端类型
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        String downloadFilename = FileUtils.encodeDownloadFilename(fileName, agent);

        response.setHeader("content-disposition", "attachment;filename="+downloadFilename);

        workbook.write(outputStream);

    }
}

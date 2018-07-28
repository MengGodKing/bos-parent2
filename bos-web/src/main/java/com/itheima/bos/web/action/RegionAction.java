package com.itheima.bos.web.action;

import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class RegionAction extends IBaseAction<Region> {

    private File regionFile;
    private int page;
    private int rows;
    private String q;
    @Autowired
    private RegionService regionService;

    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setQ(String q) {
        this.q = q;
    }



    public String importXls() throws IOException {


        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
        HSSFSheet sheet = workbook.getSheetAt(0);
        List<Region> regionList = new ArrayList<Region>();
        for (Row row: sheet) {
            if(row.getRowNum()==0){
                continue;
            }
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            //包装一个区域对象
            Region region = new Region(id, province, city, district, postcode, null, null, null);

            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            String info = province + city + district;
            String[] headByString = PinYin4jUtils.getHeadByString(info);
            String shortcode = StringUtils.join(headByString);
            //城市编码---->>shijiazhuang
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");

            region.setShortcode(shortcode);
            region.setCitycode(citycode);
            regionList.add(region);
        }
        regionService.saveBatch(regionList);



        return LIST;
    }


    public String pageQuery() throws IOException {
        regionService.pageQuery(pageBean);
        this.java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize","subareas"});

        return NONE;
    }

    public String listajax() {


        List<Region> list = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(q)) {
            list = regionService.findByQ(q);
        } else {
            list = regionService.findAll();
        }
        this.java2Json(list, new String[]{"subareas"});
        return NONE;
    }
}

package com.kexin.dkt.core.service.customer;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kexin.dkt.core.dao.customer.CustBusinessCoopDao;
import com.kexin.dkt.core.entity.custcustomer.CustBusinessCoop;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.entity.vo.PageBeanVo;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 14:13
 * @desc
 **/
@Slf4j
@Service
public class CustBusinessCoopService {

    @Autowired
    private CustBusinessCoopDao custBusinessCoopDao;

    public int saveBusiness(CustBusinessCoop custBusinessCoop){
        return custBusinessCoopDao.saveBusiness(custBusinessCoop);
    }

    /*查询分页的对象数据*/
    public PageBean<CustBusinessCoop> findCoopListByType(PageBeanVo pageBeanVo) {
        PageBean<CustBusinessCoop> pageBean = new PageBean<CustBusinessCoop>();
        PageHelper.startPage(pageBeanVo.getPage(), pageBeanVo.getLimit());
        List<CustBusinessCoop> custBusinessCoopList =  custBusinessCoopDao.findCoopListByType(pageBeanVo);
        if(custBusinessCoopList !=null && custBusinessCoopList.size()>0) {
            for (int i = 0 ;i<custBusinessCoopList.size();i++) {
                CustBusinessCoop coop = (CustBusinessCoop)custBusinessCoopList.get(i);
                coop.setCoopTypeName(setCoopTypeName(coop.getCoopType()));
            }
            PageInfo pageInfo = new PageInfo(custBusinessCoopList);
            pageBean.setList(pageInfo.getList());
            pageBean.setTotalCount((int)pageInfo.getTotal());
            pageBean.setTotalPage(pageInfo.getPages());
        }else{
            pageBean.setList(new ArrayList<CustBusinessCoop>());
        }
        return pageBean;
    }

    /*设置合作类型名称*/
    public String setCoopTypeName(int coopType){
        String coopTypeName = "";
        if(coopType == 1){
            coopTypeName = "我要巡检";
        }else if (coopType == 2){
            coopTypeName = "我要实验";
        }else if (coopType == 3){
            coopTypeName = "节能技改";
        }else if (coopType == 4){
            coopTypeName = "售电合作";
        }else if (coopType == 5){
            coopTypeName = "技术支持";
        }else if (coopType == 6){
            coopTypeName = "运维合作";
        }else if (coopType == 7){
            coopTypeName = "电房托管";
        }else if (coopType == 8){
            coopTypeName = "其它";
        }else if (coopType == 9){
            coopTypeName = "我要报障";
        }
        return coopTypeName;
    }

    /*查询某个合作类型详情*/
    public CustBusinessCoop findCoopById(CustBusinessCoop custBusinessCoop ) {
        custBusinessCoop = custBusinessCoopDao.findCoopById(custBusinessCoop );
        custBusinessCoop.setCoopTypeName(setCoopTypeName(custBusinessCoop.getCoopType()));
        return custBusinessCoop;
    }
}

package com.kexin.dkt.core.web.app.customer;

import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.custcustomer.CustBusinessCoop;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.entity.vo.PageBeanVo;
import com.kexin.dkt.core.service.customer.CustBusinessCoopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @auther shr
 * @date 2017/11/1
 * @time 11:41
 * @desc
 **/
@Slf4j
@Controller
@RequestMapping("app/business/coop")
public class CustBusinessCoopController {

    @Autowired
    private CustBusinessCoopService custBusinessCoopService;

    @RequestMapping("save")
    @ResponseBody
    public ActionResult saveBusiness(@RequestBody CustBusinessCoop custBusinessCoop){
        ActionResult actionResult = new ActionResult();
        custBusinessCoopService.saveBusiness(custBusinessCoop);
        custBusinessCoop.setId(custBusinessCoop.getId());
        actionResult.setContent(custBusinessCoop);
        return actionResult;
    }

    /*返回所有合作类型*/
    @ResponseBody
    @RequestMapping("coopList")
    public ActionResult findCoopList(){
        ActionResult result = new ActionResult();
        try {
            List<Object> custBusinessCoopList = new ArrayList<Object>();
            for (int i = 1; i < 10; i++) {
                String coopTypeName = custBusinessCoopService.setCoopTypeName(i);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("coopType",i);
                map.put("coopTypeName",coopTypeName);
                custBusinessCoopList.add(map);
            }
            result.setContent(custBusinessCoopList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /*查询某个合作类型列表*/
    @ResponseBody
    @RequestMapping("coopListByType")
    public ActionResult findCoopListByType(@RequestBody PageBeanVo pageBeanVo ){
        ActionResult result = new ActionResult();
        try {

            PageBean<CustBusinessCoop> pageBean = custBusinessCoopService.findCoopListByType(pageBeanVo);

            result.setContent(pageBean);

        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /*查询某个合作类型详情*/
    @ResponseBody
    @RequestMapping("coopById")
    public ActionResult findCoopById(@RequestBody CustBusinessCoop custBusinessCoop ){
        ActionResult result = new ActionResult();
        try {

            custBusinessCoop = custBusinessCoopService.findCoopById(custBusinessCoop);

            result.setContent(custBusinessCoop);

        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


}

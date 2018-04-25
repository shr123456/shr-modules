package com.kexin.dkt.core.web.app.information;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.school.BasicRepository;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.entity.vo.PageBeanVo;
import com.kexin.dkt.core.service.information.BasicRepositoryService;
import javafx.beans.binding.ObjectBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by kexin on 2017/11/27.
 */
@Controller
@RequestMapping("app/home/information")
public class InformationController {

    @Autowired
    private BasicRepositoryService basicRepositoryService;

    /*获取方案信息，如无人托管/电力建设等*/
    @RequestMapping("schemeInformation")
    @ResponseBody
    public ActionResult loadSchemeinformation(){
        ActionResult result = new ActionResult();
        String repositoryType = "方案信息";
        try {
            List<Map<String,Object>> list = basicRepositoryService.loadSchemeinformation(repositoryType);

            result.setContent(list);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    /*查询政策资讯/分页查询*/
    @RequestMapping("policyinformation")
    @ResponseBody
    public ActionResult loadPolicyinformation(@RequestBody PageBeanVo pageBeanVo){
        ActionResult result = new ActionResult();
        String repositoryType = "政策资讯";
        try {
            PageBean<Map<String,Object>> pageBean = basicRepositoryService.findPolicyinformationByPage(pageBeanVo,repositoryType);
            result.setContent(pageBean);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    /*查询内容详情*/
    @RequestMapping("detail")
    @ResponseBody
    public ActionResult getInformationById(@RequestBody BasicRepository basicRepository ){
        ActionResult result = new ActionResult();
        try {
            basicRepository = basicRepositoryService.getInformationById(basicRepository.getRepositoryId());

            result.setContent(basicRepository);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }


}

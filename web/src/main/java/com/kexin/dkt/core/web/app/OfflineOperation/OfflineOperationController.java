package com.kexin.dkt.core.web.app.OfflineOperation;

import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.service.offlineOperation.OfflineOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("app/offlineOperation")
public class OfflineOperationController {

    @Autowired
    private OfflineOperationService offlineOperationService;

    /**
     * 生成离线报告
     * @return
     */
    @RequestMapping("productReport")
    public ActionResult productReport(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = offlineOperationService.productReport(paramMap);
            result.setContent(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 确认提交
     * @return
     */
    @RequestMapping("submitInfomation")
    public ActionResult submitInfomation(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            offlineOperationService.submitInfomation(paramMap);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 查询离线工单列表
     * @return
     */
    @RequestMapping("findOffLineOrderList")
    public ActionResult findOffLineOrderList(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            Object object = offlineOperationService.findOffLineOrderList(paramMap);
            result.setContent(object);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 查看工单报告
     * @return
     */
    @RequestMapping("findOrderReport")
    public ActionResult findOrderReport(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            Object object = offlineOperationService.findOrderReport(paramMap);
            result.setContent(object);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


}

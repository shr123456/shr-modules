package com.kexin.dkt.core.web.app.operation;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceOrder;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceProject;
import com.kexin.dkt.core.entity.vo.OrderVo;
import com.kexin.dkt.core.service.operation.OperationMaintenanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.OrderColumn;
import javax.persistence.criteria.Order;
import javax.swing.*;
import javax.xml.transform.Result;
import java.util.*;

/**
 * Created by kexin on 2017/11/7.
 */
@Slf4j
@Controller
@RequestMapping("app/operation")
public class OperationMaintenanceController {

    @Autowired
    private OperationMaintenanceService operationMaintenanceService;

    /*运维首页*/
    @RequestMapping("home")
    @ResponseBody
    public ActionResult loadOperationMaintenanceDetail(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            //时间/已完成次数/总共次数的map集合
            Map<String,Object> map = operationMaintenanceService.getMapData(orderVo);

            //查询合同任务的状态(执行进度)
            String option = operationMaintenanceService.getOperationMaintenanceOption(orderVo);
            map.put("option", JSON.parse(option));

            /*查询事件的进度列表，默认显示5条记录*/

            result.setContent(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    /*显示工单列表*/
    @RequestMapping("workOrderList")
    @ResponseBody
    public ActionResult loadWorkOrderList(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            /*查询订单类型*/
            List<Object> list = operationMaintenanceService.queryOrderByType(orderVo);
            if(list ==null){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("title","");
                map.put("data",null);
                list.add(map);
            }
            result.setContent(list);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /*显示工单详情*/
    @RequestMapping("orderDetail")
    @ResponseBody
    public ActionResult loadOrderDetail(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            /*查询订单详情*/
            OperationMaintenanceOrder order = operationMaintenanceService.queryOrderDetail(orderVo);
            //获取图片集合
            Map<String,Object> picMap = operationMaintenanceService.getPictureList(order);
            //获取pdf文件集合
            List<Object> pdfList = operationMaintenanceService.getpdfList(order);
            //本次项目检查数量,数据库中目前无字段
            int projectInspectCount = 57;
            //本次发现问题数量,数据库中目前无字段
            int problemCount = 0;
            map.put("order",order);
            map.put("picture",picMap);
            map.put("pdfMap",pdfList);
            map.put("projectInspectCount",projectInspectCount);
            map.put("problemCount",problemCount);
            map.put("commentStatus",order.getOrderType() == 3);
            result.setContent(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    /*保存工单评论*/
    @RequestMapping("orderComment")
    @ResponseBody
    public ActionResult saveOrderComment(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            //保存工单评论
            operationMaintenanceService.saveOrderComment(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改阅读状态
     * @param order
     * @return
     */
    @RequestMapping("readStatus")
    @ResponseBody
    public ActionResult updateReadStatus(@RequestBody  OperationMaintenanceOrder order){
        ActionResult result = new ActionResult();
        try {
            //修改阅读状态
            operationMaintenanceService.updateReadStatus(order);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询阅读状态
     * @return
     */
    @RequestMapping("allReadStatus")
    @ResponseBody
    public ActionResult findAllReadStatus(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            //查询阅读状态
            boolean status = operationMaintenanceService.findAllReadStatus(orderVo);
            result.setContent(status);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }



}

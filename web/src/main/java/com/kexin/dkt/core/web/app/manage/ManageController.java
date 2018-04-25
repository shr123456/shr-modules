package com.kexin.dkt.core.web.app.manage;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.common.util.StringUtils;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.file.SysAccessory;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceOrder;
import com.kexin.dkt.core.entity.vo.OrderVo;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.service.customer.CustomerService;
import com.kexin.dkt.core.service.operation.OperationMaintenanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Order;
import java.io.File;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("app/manage")
public class ManageController {

    @Autowired
    private OperationMaintenanceService operationMaintenanceService;

    @Autowired
    private CustomerService customerService;

    /**
     * 查询任务每月任务概要/每天任务概要
     * @return
     */
    @RequestMapping("taskDesctription")
    public ActionResult loadTaskDescription(){
        ActionResult result = new ActionResult();
        try {

            Map<String,Object> map = operationMaintenanceService.loadTaskDescription();

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
     * 获取每月的记录 传入参数：monthTime
     * @return
     */
    @RequestMapping("kalendarSheet")
    public ActionResult getKalendarSheet(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            /*获取每月的记录*/
            List<Map<String,Object>> mapList =  operationMaintenanceService.findOrderByMonth(map);
            result.setContent(mapList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询工单事件
     * @return
     */
    @RequestMapping("orderEvents")
    public ActionResult loadOrderEvents(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            /*查询工单事件*/
            Map<String,Object> map =  operationMaintenanceService.loadOrderEvents(paramMap);
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
     * 新增工单/新增userId字段
     * @return
     */
    @RequestMapping("addOrder")
    public ActionResult addOrder(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            /*新增工单*/
            operationMaintenanceService.addOrder(paramMap);

        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询工单报告列表
     * @return
     */
    @RequestMapping("findReportList")
    public ActionResult findReportList(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
           List<Map<String,Object>> reportList = operationMaintenanceService.findReportList(paramMap);
           result.setContent(reportList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询工单类型列表
     * @return
     */
    @RequestMapping("orderList")
    public ActionResult findOrderList(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
           PageBean pageBean = operationMaintenanceService.findOrderList(paramMap);
           result.setContent(pageBean);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询所有客户信息
     * @return
     */
    @RequestMapping("customerList")
    public ActionResult findCustomerList(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
           List<Map<String,Object>> customerList = customerService.findCustomerList(paramMap);
           result.setContent(customerList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询客户中的所有项目
     * @return
     */
    @RequestMapping("projectList")
    public ActionResult findProjectList(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
           List<Map<String,Object>> projectList = operationMaintenanceService.findAllProject(paramMap);
           result.setContent(projectList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 条件查询客户中的所有项目
     * @return
     */
    @RequestMapping("projectListLike")
    public ActionResult findProjectListLike(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> projectList = operationMaintenanceService.findProjectListLike(paramMap);
           result.setContent(projectList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询所有超时未完成数量
     * @return
     */
    @RequestMapping("allOverTimeTaskCount")
    public ActionResult findAllOverTimeTaskCount(@RequestBody Map<String,String> paramMap){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<>();
            int overTimeCount = operationMaintenanceService.findAllOverTimeTaskCount(paramMap);
            map.put("overTimeCount",overTimeCount);
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
     * 查询工单详情
     * @return
     */
    @RequestMapping("orderDetail")
    public ActionResult findOrderDetail(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> resultMap = operationMaintenanceService.findOrderContent(orderVo);
            OperationMaintenanceOrder order = operationMaintenanceService.queryOrderDetail(orderVo);
            Map<String,Object> orderMap = (Map<String,Object>)resultMap.get("order");
            operationMaintenanceService.findOrderDetail(orderMap,order);
            result.setContent(resultMap);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 工单轨迹
     * @return
     */
    @RequestMapping("orderLocus")
    public ActionResult orderLocus(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> locusList = operationMaintenanceService.orderLocus(paramMap);
            result.setContent(locusList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 派工
     * @return
     */
    @RequestMapping("signingWork")
    public ActionResult signingWork(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.updateGroup(paramMap);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 主管评价
     * @return
     */
    @RequestMapping("managerRemark")
    public ActionResult managerRemark(@RequestBody Map<String,Object> paramMap){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.managerRemark(paramMap);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

}

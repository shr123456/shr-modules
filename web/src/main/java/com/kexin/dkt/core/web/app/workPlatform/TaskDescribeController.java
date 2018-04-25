package com.kexin.dkt.core.web.app.workPlatform;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceOrder;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceProject;
import com.kexin.dkt.core.entity.vo.OrderVo;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.service.operation.OperationMaintenanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务概要
 * Created by kexin on 2017/12/25.
 */
@Controller
@Slf4j
@RequestMapping("app/taskDescription")
public class TaskDescribeController {

    @Autowired
    private OperationMaintenanceService operationMaintenanceService;

    /**
     * 查询任务的执行进度：今天任务/明天任务/本周任务/本月任务
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("schedule")//需传入客户id
    public ActionResult findTaskSchedule(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> list= operationMaintenanceService.findTaskSchedule(orderVo);
            result.setContent(list);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询任务列表：今天任务/明天任务/本周任务/本月任务/超时未完成
     * @param pageMap
     * @return
     */
    @ResponseBody
    @RequestMapping("taskList")//需传入客户id,tabName
    public ActionResult findTaskDescription(@RequestBody  Map<String,Object> pageMap){
        ActionResult result = new ActionResult();
        try {
            PageBean pageBean = operationMaintenanceService.findTaskList(pageMap);
            result.setContent(pageBean);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询本月超时未完成的任务数量
     * @param orderVo
     * @return
     */
    @ResponseBody
    @RequestMapping("overTimeTaskCount")//需传入客户id
    public ActionResult findOverTimeTaskCount(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();
            /*查询本月超时任务数量*/
            int count = operationMaintenanceService.findOverTimeTaskCount(orderVo);
            map.put("count",count);
            result.setContent(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询本月任务数量以及完成数量折线图
     * @param orderVo
     * @return
     */
    @ResponseBody
    @RequestMapping("taskOption")//需传入客户id
    public ActionResult loadTaskOption(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();
            /*查询本月任务数量以及完成数量折线图*/
            String option = operationMaintenanceService.loadTaskOption(orderVo);
            map.put("option",JSON.parse(option));
            result.setContent(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询某设备详情以及项目的执行情况
     * @param orderVo
     * @return
     */
    @ResponseBody
    @RequestMapping("projectDetail")//项目id
    public ActionResult findProjectDetail(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> resultMap = operationMaintenanceService.findOrderContent(orderVo);

            result.setContent(resultMap);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 开始工作
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("startWork")//项目id/需要传入用户id
    public ActionResult startWork(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            /*开始工作*/
            operationMaintenanceService.startWork(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

}

package com.kexin.dkt.core.web.app.workPlatform;

import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.equipment.ElectricalRoom;
import com.kexin.dkt.core.entity.operation.*;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.OrderVo;
import com.kexin.dkt.core.service.operation.OperationMaintenanceService;
import com.kexin.dkt.core.service.system.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 任务执行
 * Created by kexin on 2017/12/26.
 */
@Controller
@Slf4j
@RequestMapping("app/taskExecution")
public class TaskExecutionController {

    @Autowired
    private OperationMaintenanceService operationMaintenanceService;

    /**
     *查询班组分组
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("constructionGroupList")
    public ActionResult findConstructionGroup(){
        ActionResult result = new ActionResult();
        try {
            List<String> groupList = operationMaintenanceService.findConstructionGroup();
            result.setContent(groupList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询班组成员带标识
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("groupMemberList")//需传入客户id
    public ActionResult findConstructionGroupMember(@RequestBody OperationMaintenanceOrder order){
        ActionResult result = new ActionResult();
        try {
            Map<String,String> receiveMap = new HashMap<String, String>();
            receiveMap.put("groupName",order.getEngineer());
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("title",order.getEngineer());
            List<Map<String,String>> engineerList = operationMaintenanceService.findConstructionGroupMember(receiveMap);
            map.put("list",engineerList);
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
     *新增班组成员
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("addGroupMember")//需传入客户id
    public ActionResult addConstructionGroupMember(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.addConstructionGroupMember(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询电房电房列表
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("electricRoomList")//需传入客户id
    public ActionResult queryElectricRoomList(@RequestBody  OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> roomList = operationMaintenanceService.queryElectricRoomList(orderVo);
            result.setContent(roomList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *新增电房
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("addElectricRoom")//需传入客户id
    public ActionResult addElectricRoom(@RequestBody Map<String,String> map){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.addElectricRoom(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *修改电房
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("updateElectricRoom")//需传入客户id
    public ActionResult updateElectricRoom(@RequestBody ElectricRoomInfo roomInfo){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.updateElectricRoom(roomInfo);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *删除电房
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("deleteElectricRoom")//需传入客户id
    public ActionResult deleteElectricRoom(@RequestBody ElectricRoomStatus roomStatus){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.deleteElectricRoom(roomStatus);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询设备类型列表
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("equipmentTypeList")//需传入电房id
    public ActionResult findEquipmentTypeList(@RequestBody ElectricRoomStatus roomStatus){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> equipmentTypeList = operationMaintenanceService.findEquipmentTypeList(roomStatus);
            result.setContent(equipmentTypeList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询设备类型列表是否选择
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("equipmentTypeSelect")//需传入电房id,运维产品id： operProductsId
    public ActionResult findEquipmentTypeSelect(@RequestBody Map<String,Integer> map){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> equipmentTypeList = operationMaintenanceService.findEquipmentTypeSelect(map);
            result.setContent(equipmentTypeList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *新增设备类型
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("addEquipmentTypeByRoomId")//需传入电房id，设备类型id集合
    public ActionResult addEquipmentType(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
           operationMaintenanceService.addEquipmentType(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *删除设备类型
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("deleteEquipmentType")//需传入电房id，设备类型id集合
    public ActionResult deleteEquipmentType(@RequestBody RoomEquipmentType equipmentType){
        ActionResult result = new ActionResult();
        try {
           operationMaintenanceService.deleteEquipmentType(equipmentType);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *新增设备
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("addEquipment")//需传入设备类型id，设备类型id集合
    public ActionResult addEquipment(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
           operationMaintenanceService.addEquipment(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询设备列表
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("equipmentList")//需传入客户id
    public ActionResult queryEquipmentList(@RequestBody EquipmentTypeStatus typeStatus){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> mapList = operationMaintenanceService.queryEquipmentList(typeStatus);
            result.setContent(mapList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *删除设备
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("deleteEquipment")//需传入客户id
    public ActionResult deleteEquipment(@RequestBody EquipmentErrorMessage errorMessage){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.deleteEquipment(errorMessage);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *修改设备名称
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("updateEquipment")//需传入客户id
    public ActionResult updateEquipment(@RequestBody RoomEquipmentInfo equipmentInfo){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.updateEquipment(equipmentInfo);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询工作项列表
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("WorkProjectList")//需传入客户id
    public ActionResult queryWorkProjectList(@RequestBody EquipmentErrorMessage errorMessage){
        ActionResult result = new ActionResult();
        try {
            List<EquipmentWorkContent> workContentList = operationMaintenanceService.queryWorkProjectList(errorMessage);
            result.setContent(workContentList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *添加工作照片
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("addWorkPicture")//需传入客户id
    public ActionResult addWorkPicture(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.addWorkPicture(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询工作照片
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("photosList")//需传入客户id
    public ActionResult queryPhotosList(@RequestBody OrderVo orderVo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = operationMaintenanceService.queryPhotosList(orderVo);
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
     *添加异常信息
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("addErrorMessage")//
    public ActionResult addErrorMessage(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            operationMaintenanceService.addErrorMessage(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询异常信息
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("findErrorMessage")//
    public ActionResult findErrorMessage(@RequestBody EquipmentErrorMessage errorMessageParam){
        ActionResult result = new ActionResult();
        try {
            EquipmentErrorMessage errorMessage = operationMaintenanceService.findErrorMessage(errorMessageParam);
            if(errorMessage == null){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id",0);
                map.put("errorPhoto","");
                map.put("errorMessage","");
                map.put("adviceMessage","");
                result.setContent(map);
            }else {
                result.setContent(errorMessage);
            }
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询历史问题
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("historyProblemList")//需传入工作项id
    public ActionResult queryHistoryProblemList(@RequestBody EquipmentErrorMessage errorMessage){
        ActionResult result = new ActionResult();
        try {
            /*查询历史问题*/
            List<EquipmentErrorMessage> errorMessageList = operationMaintenanceService.queryHistoryProblemList(errorMessage);
            result.setContent(errorMessageList);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *完成设备类型
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("finishEquipmentType")//需传入客户id
    public ActionResult finishEquipmentType(@RequestBody EquipmentTypeStatus typeStatus){
        ActionResult result = new ActionResult();
        try {
            /*完成设备类型*/
            operationMaintenanceService.finishEquipmentType(typeStatus);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *完成此电房
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("finishElectricRoom")//需传入客户id
    public ActionResult finishElectricRoom(@RequestBody ElectricRoomStatus roomStatus){
        ActionResult result = new ActionResult();
        try {
            /*完成此电房*/
            operationMaintenanceService.finishElectricRoom(roomStatus);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *完成任务(用户签名)
     * @return ActionResult result
     */
    @ResponseBody
    @RequestMapping("customerSignature")//需传入客户id
    public ActionResult customerSignature(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            /*完成任务*/
            operationMaintenanceService.customerSignature(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

}

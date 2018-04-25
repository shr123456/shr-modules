package com.kexin.dkt.core.web.app.equipment;

import com.kexin.dkt.core.dao.equipment.EquipmentDao;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentGroup;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.service.equipment.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/12/5.
 */
@Controller
@RequestMapping("app/equipment")
@Slf4j
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    /**
     * 查询客户分组及用电量
     * @return
     */
    @RequestMapping("equipmentGroup")
    @ResponseBody
    public ActionResult queryEquipmentGroupList(@RequestBody SaleElectricityDo saleElectricityDo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = equipmentService.queryEquipmentGroupList(saleElectricityDo);
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
     * 查询客户某组设备用电排名
     * @return
     */
    @RequestMapping("equipmentList")
    @ResponseBody
    public ActionResult queryEquipmentList(@RequestBody SaleElectricityDo saleElectricityDo){
        ActionResult result = new ActionResult();
        try {
            Map<String,Object> map = new HashMap<String, Object>();
            EquipmentGroup group = equipmentService.findEquipmentGroupById(saleElectricityDo.getId());
            List<Map<String,Object>> equipmentList = equipmentService.queryEquipmentList(saleElectricityDo);
            map.put("title",group.getName());
            map.put("list",equipmentList);
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
     * 查询客户设备分组信息
     * @return
     */
    @RequestMapping("findEquipmentGroupList")
    @ResponseBody
    public ActionResult findEquipmentGroupList(@RequestBody SaleElectricityDo saleElectricityDo){
        ActionResult result = new ActionResult();
        try {
            List<EquipmentGroup> equipmentList = equipmentService.findEquipmentGroupList(saleElectricityDo);
            result.setContent(equipmentList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询客户设备分组信息带标识
     * @return
     */
    @RequestMapping("findEquipmentList")
    @ResponseBody
    public ActionResult findEquipmentListByGroupId(@RequestBody EquipmentGroup group){
        ActionResult result = new ActionResult();
        try {
            List<Map<String,Object>> equipmentList = equipmentService.findEquipmentListByGroupId(group);
            result.setContent(equipmentList);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改设备分组信息
     * @return
     */
    @RequestMapping("updateEquipmentGroup")
    @ResponseBody
    public ActionResult updateEquipmentGroup(@RequestBody Map<String,Object> map){

        ActionResult result = new ActionResult();
        try {
            equipmentService.updateEquipmentGroup(map);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新增设备分组
     * @return
     */
    @RequestMapping("addEquipmentGroup")
    @ResponseBody
    public ActionResult addEquipmentGroup(@RequestBody Map<String,Object> map){
        ActionResult result = new ActionResult();
        try {
            EquipmentGroup group = equipmentService.addEquipmentGroup(map);
            result.setContent(group.getId());
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除设备分组
     * @return
     */
    @RequestMapping("deleteEquipmentGroup")
    @ResponseBody
    public ActionResult deleteEquipmentGroup(@RequestBody EquipmentGroup group){
        ActionResult result = new ActionResult();
        try {
            equipmentService.deleteEquipmentGroup(group);
        }catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setMessage("操作失败");
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

}

package com.kexin.dkt.core.dao.equipment;

import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentGroup;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentToGroup;
import javafx.beans.binding.ObjectBinding;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/10/25.
 */
@MapperScan
public interface EquipmentDao {

    /*查询用户的设备配置*/
    public List<EquipmentCollectConfig> findEquipmentList(String userId) ;

    /**
     * 查询设备详情
     */
    Map<String,Object> loadEquipmentDetail(@Param("id") long id);

    /**
     * 根据设备id获取Dtu与地址
     * @param lpId
     * @return
     */
    Map<String,Object> getDtoMeterAddressByLpId(@Param("id")int lpId);

    /**
     * 查询设备分组及用电量
     * @param saleElectricityDo
     * @return
     */
    List<EquipmentGroup> findEquipmentGroupList(SaleElectricityDo saleElectricityDo);

    /**
     *查询某组设备
     * @param saleElectricityDo
     * @return
     */
    List<Map<String,Object>> queryEquipmentList(SaleElectricityDo saleElectricityDo);

    /**
     * 查询设备组的用电量
     * @param saleElectricityDo
     * @return
     */

    List<Map<String,Object>> getGroupEnergy(SaleElectricityDo saleElectricityDo);

    /**
     * 通过id查询分组
     * @param id
     * @return
     */
    EquipmentGroup findEquipmentGroupById(int id);

    /**
     *修改设备分组信息
     * @param group
     */
    void updateEquipmentGroup(EquipmentGroup group);

    /*新建设备分组*/
    void addEquipmentGroup(EquipmentGroup group);

    /*新增设备分组中间表*/
    void addEquipmentToGroup(EquipmentToGroup equipmentToGroup);

    /*删除设备分组中间表*/
    void deleteEquipmentToGroup(EquipmentGroup group);

    /*删除设备分组*/
    void deleteEquipmentGroup(EquipmentGroup group);

    /*通过分组id查询设备需要带上标识*/
    List<Map<String,Object>> findEquipmentListByGroupId(EquipmentGroup group);

    /*通过分组id查询设备id集合*/
    List<String> equipmentIdList(@Param("groupId")String groupId);
}

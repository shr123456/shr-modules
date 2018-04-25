package com.kexin.dkt.core.service.equipment;

import com.alibaba.fastjson.JSON;
import com.kexin.dkt.core.common.echarts.EchartsUtils;
import com.kexin.dkt.core.dao.equipment.EquipmentDao;
import com.kexin.dkt.core.dao.system.UserDao;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.custcustomer.CustUser;
import com.kexin.dkt.core.entity.echarts.EchartsSeries;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentGroup;
import com.kexin.dkt.core.entity.equipment.EquipmentToGroup;
import com.kexin.dkt.core.entity.security.SecUser;
import com.mysql.jdbc.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

/**
 * Created by kexin on 2017/10/25.
 */
@Slf4j
@Service
public class EquipmentService {

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private UserDao userDao;

    /*查询用户的设备配置*/
    @Transactional
    public List<EquipmentCollectConfig> findEquipmentList(String userId) {

        return equipmentDao.findEquipmentList(userId);
    }

    /**
     * 查询设备分组及用电量
     * @param saleElectricityDo
     * @return
     */
    public Map<String,Object> queryEquipmentGroupList(SaleElectricityDo saleElectricityDo) {
        List<Map<String,Object>> mapList = equipmentDao.getGroupEnergy(saleElectricityDo);
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        String[] dataTypeArray = new String[mapList.size()];
        if(mapList == null){
            mapList = new ArrayList<Map<String, Object>>();
        }else {
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", mapList.get(i).get("name").toString());
                map.put("value", mapList.get(i).get("electric").toString());
                dataTypeArray[i] = mapList.get(i).get("name").toString();
                dataList.add(map);
            }
        }
        String titleName = "";

        String option = "{" +
                "    title : {" +
                "        x:'center'" +
                "    }," +
                "    tooltip : {" +
                "        trigger: 'item'" +
                "    }," +
                "    legend: {" +
                "        orient: 'horizontal'," +
                "        left: 'center'," +
                "        data: " + JSON.toJSONString(dataTypeArray)+
                "    }," +
                "    yAxis:  {" +
                "        show: false," +
                "    }," +
                "    xAxis: {" +
                "        show: false," +
                "    }," +
                "    series : [" +
                "        {" +
                "            name: '"+titleName+"'," +
                "            type: 'pie'," +
                "            radius : '55%'," +
                "            center: ['50%', '60%']," +
                "            data: " + JSON.toJSONString(dataList)+"," +
                "            itemStyle: {" +
                "                emphasis: {" +
                "                    shadowBlur: 10," +
                "                    shadowOffsetX: 0" +
                "                }" +
                "            }" +
                "        }" +
                "    ]" +
                "}";
        Map<String,Object> allMap = new HashMap<String, Object>();
        allMap.put("option",JSON.parse(option));
        allMap.put("group",mapList);
        return allMap;
    }

    /**
     * 查询某组设备
     * @param saleElectricityDo
     * @return
     */
    public List<Map<String,Object>> queryEquipmentList(SaleElectricityDo saleElectricityDo){
        List<Map<String,Object>> equipmentList = equipmentDao.queryEquipmentList(saleElectricityDo);
        if(equipmentList == null){
            equipmentList = new ArrayList<Map<String,Object>>();
        }
        return equipmentList;
    }

    /**
     * 只查询设备分组信息
     * @param saleElectricityDo
     * @return
     */
    public List<EquipmentGroup> findEquipmentGroupList(SaleElectricityDo saleElectricityDo){
        return equipmentDao.findEquipmentGroupList(saleElectricityDo);
    }


    /*通过id查询分组*/
    public EquipmentGroup findEquipmentGroupById(int id) {
        return equipmentDao.findEquipmentGroupById(id);
    }

    /**
     * 修改设备分组信息
     * @param map
     */
    public void updateEquipmentGroup(Map<String,Object> map) {
        EquipmentGroup group = new EquipmentGroup();
        group.setId(Long.parseLong(map.get("id").toString()));
        List<Integer> list  = (List<Integer>)map.get("equipmentId");
        SecUser secUser = userDao.getUserById(map.get("userId").toString());
        group.setCreator(secUser.getUserName());
        group.setUpdateTime(new Date());
        equipmentDao.updateEquipmentGroup(group);
        //删除设备分组中间表
        equipmentDao.deleteEquipmentToGroup(group);
        EquipmentToGroup toGroup = new EquipmentToGroup();
        toGroup.setCreator(group.getCreator());
        toGroup.setCreateTime(new Date());
        toGroup.setChecked(1);
        toGroup.setGroupId(group.getId());
        group.setUpdateTime(new Date());
        if(list !=null && list.size() >0) {
            for (int i = 0; i < list.size(); i++) {
                toGroup.setEquipmentId(list.get(i));
                equipmentDao.addEquipmentToGroup(toGroup);
            }
        }
    }

    /**
     * 新增设备分组信息
     * @param map
     */
    public EquipmentGroup addEquipmentGroup(Map<String,Object> map) {
        EquipmentGroup group = new EquipmentGroup();
        SecUser secUser = userDao.getUserById(map.get("userId").toString());
        group.setCreator(secUser.getUserName());
        group.setName(map.get("name").toString());
        group.setCustomerId(map.get("customerId").toString());
        group.setCreateTime(new Date());
        group.setUpdateTime(new Date());
        equipmentDao.addEquipmentGroup(group);

        return group;
    }

    /**
     * 删除设备分组信息
     * @param group
     */
    public void deleteEquipmentGroup(EquipmentGroup group) {
        equipmentDao.deleteEquipmentGroup(group);
        //删除设备分组中间表
        equipmentDao.deleteEquipmentToGroup(group);
    }

    /**
     * 通过分组id查询设备需要带上标识
     * @param group
     * @return
     */
    public List<Map<String, Object>> findEquipmentListByGroupId(EquipmentGroup group) {
        return equipmentDao.findEquipmentListByGroupId(group);
    }

    /**
     *通过分组id查询设备id集合
     * @param groupId
     * @return
     */
    public List<String> equipmentIdList(String groupId){
        return equipmentDao.equipmentIdList(groupId);
    }
}

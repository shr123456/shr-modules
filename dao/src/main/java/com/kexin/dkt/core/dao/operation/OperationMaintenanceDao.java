package com.kexin.dkt.core.dao.operation;

import com.kexin.dkt.core.entity.file.SysAccessory;
import com.kexin.dkt.core.entity.operation.*;
import com.kexin.dkt.core.entity.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/11/7.
 */
@MapperScan
public interface OperationMaintenanceDao {

    /*查询客户项目中执行某种任务的执行次数*/
    int findTaskCount(OrderVo orderVo);

    /*查询某客户中的所有项目*/
    List<OperationMaintenanceProject> findAllProject(OrderVo orderVo);

    /*根据项目id查询某项目*/
    OperationMaintenanceProject findProjectById(@Param("projectId")int projectId);

    /*查询客户项目中维修执行总次数*/
    long findAllServiceCount(OrderVo orderVo);

    /*查询客户中服务类型的订单*/
    List<OperationMaintenanceOrder> findOrderByServiceType(OrderVo orderVo);

    /*根据类型查询订单列表*/
    List<OperationMaintenanceOrder> queryOrderByType(OrderVo orderVo);

    /*查询订单详情*/
    OperationMaintenanceOrder queryOrderDetail(OrderVo orderVo);

    /*保存工单评论*/
    void saveOrderComment(OperationMaintenanceOrder order);

    /*查询文件集合*/
    List<SysAccessory> findFileList(@Param("relationId") String relationId );

    /*修改阅读状态*/
    void updateReadStatus(OperationMaintenanceOrder order);

    /*查询某天的任务详情*/
    List<OperationMaintenanceOrder> findTaskByOneDay(Map<String,String> paramMap);

    /*查询本月的任务详情*/
    List<OperationMaintenanceOrder> findTaskByMonth(Map<String,String> paramMap);

    /*查询本周的任务详情*/
    List<OperationMaintenanceOrder> findTaskByWeek(Map<String,String> paramMap);

    /**
     * 查询本月超时任务数量
     * @param paramMap
     * @return
     */
    int findOverTimeTaskCount(Map<String,String> paramMap);

    /**
     * 查询本月超时任务列表
     * @param paramMap
     * @return
     */
    List<OperationMaintenanceOrder> findOverTimeTask(Map<String,String> paramMap);

    /**
     * 查询每月完成与未完成任务数量
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> findDoneTaskByMonth(Map<String,String> paramMap);

    /**
     * 查询每月当前日的时间列表
     * @param orderVo
     * @return
     */
    List<Map<String,Object>> getTimeList(OrderVo orderVo);

    /**
     * 查询客户某项目的工单列表
     * @param order
     * @return
     */
    List<OperationMaintenanceOrder> findOrderListByProjectId(OperationMaintenanceOrder order);

    /**
     * 查询班组成员带标识
     * @param receiveMap
     * @return
     */
    List<Map<String,String>> findConstructionGroupMember(Map<String,String> receiveMap);

    /**
     * 删除班组分组成员
     * @param groupName
     */
    void deleteConstructionGroup(@Param("groupName")String groupName);

    /**
     * 新增班组分组成员
     * @param map
     */
    void addConstructionGroupMember(@Param("map") Map<String, Object> map);

    /**
     * 查询工单中所服务的电房集合
     * @param orderVo
     * @return
     */
    List<Map<String,Object>> queryElectricRoomList(OrderVo orderVo);

    /**
     * 新增电房
     * @param roomInfo
     */
    void addElectricRoom(ElectricRoomInfo roomInfo);

    /**
     * 查询班组分组列表
     * @param
     * @return
     */
    List<String> findConstructionGroup();

    /**
     * 查询设备类型列表
     * @return
     */
    List<Map<String,Object>> findEquipmentTypeList(Map<String,Object> map);

    /**
     * 查询设备类型列表是否选择
     * @param map
     * @return
     */
    List<Map<String,Object>> findEquipmentTypeSelect(Map<String,Integer> map);

    /**
     * 查询设备类型数量
     * @param equipmentType
     * @return
     */
    int findEquipmentTypeCount(RoomEquipmentType equipmentType);

    /**
     * 新增设备类型
     * @param equipmentType
     * @return
     */
    void addEquipmentType(RoomEquipmentType equipmentType);

    /**
     *新增设备
     * @param equipmentInfo
     */
    void addEquipment(RoomEquipmentInfo equipmentInfo);

    /**
     *新增图片
     * @param sysAccessory
     */
    void addSysAccessory(SysAccessory sysAccessory);

    /**
     * 修改工单图片
     * @param order
     */
    void updateOrderPhoto(OperationMaintenanceOrder order);

    /**
     * 根据relationId删除SysAccessory
     * @param photosAfterWork
     */
    void deleteSysAccessory(@Param("relationId") String photosAfterWork);

    /**
     *添加工作项
     */
    void addEquipmentWorkContent(Map<String,Object> map);

    /**
     * 通过设备类型id查询设备名称
     * @param id
     * @return
     */
    RoomEquipmentType findEquipmentTypeById(@Param("id") int id);

    /**
     * 查询设备列表
     * @param equipmentType
     * @return
     */
    List<Map<String,Object>> queryEquipmentList(RoomEquipmentType equipmentType);

    /**
     * 修改设备类型
     * @param type
     */
    void updateEquipmentType(RoomEquipmentType type);

    /**
     * 删除设备
     * @param id
     */
    void deleteEquipment(@Param("id") long id);

    /**
     * 修改设备名称
     * @param equipmentInfo
     */
    void updateEquipment(RoomEquipmentInfo equipmentInfo);

    /**
     * 查询工作项列表
     * @param map
     * @return
     */
    List<EquipmentWorkContent> queryWorkProjectList(Map<String,Object> map);

    /**
     * 添加异常信息
     * @param errorMessage
     */
    void addErrorMessage( EquipmentErrorMessage errorMessage);

    /**
     * 删除设备类型
     * @param equipmentType
     */
    void deleteEquipmentType(RoomEquipmentType equipmentType);

    /**
     * 删除电房
     * @param roomInfo
     */
    void deleteElectricRoom(ElectricRoomInfo roomInfo);

    /**
     * 查询异常数量
     * @param errorMessage
     * @return
     */
    int findEquipmentErrorCount(EquipmentErrorMessage errorMessage);

    /**
     * 查询电房完成状态
     * @param roomStatus
     * @return
     */
    Integer findElectricRoomStatus(ElectricRoomStatus roomStatus);

    /**
     * 删除电房状态数据
     * @param roomStatus
     */
    void deleteElectricRoomStatus(ElectricRoomStatus roomStatus);

    /**
     * 查询设备类型状态
     * @param orderId
     * @param equipmentTypeId
     * @return
     */
    Integer findEquipmentTypeStatus(@Param("orderId") long orderId, @Param("equipmentTypeId") long equipmentTypeId);

    /**
     * 根据设备类型id设备类型状态
     * @param id
     */
    void deleteEquipmentTypeStatus(@Param("equipmentTypeId") long id);

    /**
     * 根据id查询设备
     * @param equipmentId
     * @return
     */
    RoomEquipmentInfo findEquipmentById(@Param("equipmentId") long equipmentId);

    /**
     * 删除异常信息
     * @param errorMessage
     */
    void deleteErrorMessage(EquipmentErrorMessage errorMessage);

    /**
     * 查询最新一次已完成工单
     * @param order
     * @return
     */
    OperationMaintenanceOrder findNewOrder(OperationMaintenanceOrder order);


    /**
     * 查询历史问题
     * @param errorMessage
     * @return
     */
    List<EquipmentErrorMessage> queryHistoryProblemList(EquipmentErrorMessage errorMessage);

    /**
     * 修改设备类型的状态
     * @param typeStatus
     */
    void finishEquipmentType(EquipmentTypeStatus typeStatus);

    /**
     * 完成此电房
     * @param roomStatus
     */
    void finishElectricRoom(ElectricRoomStatus roomStatus);

    /**
     * 完成任务
     * @param order
     */
    void customerSignature(OperationMaintenanceOrder order);

    /**
     * 查询异常信息
     * @param errorMessageParam
     * @return
     */
    EquipmentErrorMessage findErrorMessage(EquipmentErrorMessage errorMessageParam);

    /**
     * 修改异常信息
     * @param errorMessage
     */
    void updateErrorMessage(EquipmentErrorMessage errorMessage);

    /**
     * 根据id查询异常信息
     * @param errorMessage
     * @return
     */
    EquipmentErrorMessage findErrorMessageById(EquipmentErrorMessage errorMessage);

    /**
     * 查询设备类型状态数量
     * @param typeStatus
     * @return
     */
    int findEquipmentTypeStatusCount(EquipmentTypeStatus typeStatus);

    /**
     * 查询设备类型状态数量
     * @param roomStatus
     * @return
     */
    int findElectricRoomStatusCount(ElectricRoomStatus roomStatus);

    /**
     *
     * @param order
     * @return
     */
    OperationMaintenanceOrder findServiceTypeNewTime(OperationMaintenanceOrder order);

    /**
     * 查询分组成员名称
     * @param memberMap
     * @return
     */
    List<Map<String,String>> findGroupMemberName(Map<String, String> memberMap);

    /**
     * 查询工单中电房完成列表
     * @param order
     * @return
     */
    List<ElectricRoomStatus> findFinishRoomList(OperationMaintenanceOrder order);

    /**
     * 根据电房id查询电房查询
     * @param roomId
     * @return
     */
    Map<String,Object> findElectricRoomById(@Param("roomId") long roomId);

    /**
     * 根据工作项id查询工作项信息
     * @param workProjectId
     * @return
     */
    Map<String,Object> queryWorkProjectById(@Param("workProjectId") long workProjectId);

    /**
     * 查询时间段内工单列表
     * @param paramMap
     * @return
     */
    List<OperationMaintenanceOrder> findOrderByTime(Map<String,String> paramMap);

    /**
     * 修改SysAccessory
     * @param sysAccessory
     */
    void updateSysAccessory(SysAccessory sysAccessory);

    /**
     * 查询每月日期集合
     * @param monthTime
     * @return
     */
    List<String> findMonthTime(@Param("monthTime") String monthTime);

    /**
     * 新增工单
     * @param paramMap
     */
    void addOrder(Map<String, Object> paramMap);

    /**
     * 查询所有订单
     * @param paramMap
     * @return
     */
    List<OperationMaintenanceOrder> findAllTask(Map<String,String> paramMap);

    /**
     * 查询报告列表
     * @return
     */
    List<OperationMaintenanceOrder> findReportList(Map<String,String> reportMap);

    /**
     * 通过人员查询分组名
     * @param param
     * @return
     */
    Map<String,Object> findGroupByUserId(Map<String, String> param);

    /**
     * 按条件查询工单列表
     * @param paramMap
     * @return
     */
    List<OperationMaintenanceOrder> findOrderListLike(Map<String, Object> paramMap);

    /**
     * 条件查询客户中的所有项目
     * @param paramMap
     * @return
     */
    List<OperationMaintenanceProject> findProjectListLike(Map<String, Object> paramMap);

    /**
     * 添加工单记录
     * @param orderMap
     */
    void addOrderRecords(Map<String,Object> orderMap);

    /**
     * 修改工单状态
     * @param order
     */
    void updateOrder(OperationMaintenanceOrder order);

    /**
     * 修改分组
     * @param paramMap
     */
    void updateGroup(Map<String, Object> paramMap);

    /**
     * 主管评价
     * @param tempMap
     */
    void managerRemark(Map<String, Object> tempMap);

    /**
     * 运维轨迹
     */
    List<Map<String,Object>> orderLocus(Map<String, Object> paramMap);

    /**
     * 查询轨迹中某类型数量
     * @param paramMap
     * @return
     */
    int findRecordsTypeCount(Map<String, Object> paramMap);

    /**
     * 修改电房名称
     * @param roomInfo
     */
    void updateElectricRoom(ElectricRoomInfo roomInfo);

    /**
     * 查询所有超时未完成数量
     * @param paramMap
     * @return
     */
    int findAllOverTimeTaskCount(Map<String, String> paramMap);
}

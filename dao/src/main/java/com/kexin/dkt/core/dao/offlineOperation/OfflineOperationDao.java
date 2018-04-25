package com.kexin.dkt.core.dao.offlineOperation;

import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
public interface OfflineOperationDao {


    /**
     * 保存离线工单信息
     * @param paramMap
     */
    void addOffLineOrder(Map<String, Object> paramMap) ;

    /**
     * 保存离线工作项信息
     * @param paramMap
     */
    void addWorkMessage(Map<String, Object> paramMap) ;


    /**
     * 查询离线工单列表
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> findOffLineOrderList(Map<String, Object> paramMap);


    /**
     * 查询离线工单内容
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> findOffLineContent(Map<String, Object> paramMap);


    /**
     * 修改报告状态
     * @param orderMap
     */
    void updateOffLineOrder(Map<String, Object> orderMap);


    /*查询离线工单详情*/
    Map<String,Object> findOffLineOrder(Map<String, Object> temp1);
}

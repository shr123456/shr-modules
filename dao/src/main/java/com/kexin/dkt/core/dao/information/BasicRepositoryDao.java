package com.kexin.dkt.core.dao.information;

import com.kexin.dkt.core.entity.school.BasicRepository;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/11/27.
 */
@MapperScan
public interface BasicRepositoryDao {

    /*获取方案信息，如无人托管/电力建设等*/
    List<Map<String,Object>> loadSchemeinformation(@Param("repositoryType")String repositoryType) ;

    /*查询政策资讯/分页查询*/
    List<Map<String,Object>> findPolicyinformationByPage(@Param("repositoryType")String repositoryType);

    /*查询内容详情*/
    BasicRepository getInformationById(@Param("repositoryId")String repositoryId);
}

package com.kexin.dkt.core.dao.version;

import com.kexin.dkt.core.entity.version.VersionMessage;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

@MapperScan
public interface VersionDao {

    /*添加版本信息*/
    void addVersionMessage(VersionMessage versionMessage);

    /*查询版本信息*/
    List<VersionMessage> findVersionMessage(VersionMessage versionMessage);
}

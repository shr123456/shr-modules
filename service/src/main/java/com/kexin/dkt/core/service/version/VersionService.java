package com.kexin.dkt.core.service.version;

import com.kexin.dkt.core.dao.version.VersionDao;
import com.kexin.dkt.core.entity.version.VersionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VersionService {

    @Autowired
    private VersionDao versionDao;

    /**
     * 添加版本信息
     * @param versionMessage
     */
    public void addVersionMessage(VersionMessage versionMessage) {
            List<VersionMessage> versionList = versionDao.findVersionMessage(versionMessage);
            if(versionList ==null) {
                versionDao.addVersionMessage(versionMessage);
            }
    }


    public List<VersionMessage> findVersionMessageList(VersionMessage versionMessage) {
        return versionDao.findVersionMessage(versionMessage);
    }
}

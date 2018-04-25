package com.kexin.dkt.core.entity.version;

import lombok.Data;

import java.util.Date;

@Data
public class VersionMessage {

    private int id;
    private String versionNum;
    private int versionType;//1:app客户端,2:app运维端
    private String updateUrl;//更新地址
    private String versionContent;
    private Date createTime;
    private int deleted;
    private String remark;

}

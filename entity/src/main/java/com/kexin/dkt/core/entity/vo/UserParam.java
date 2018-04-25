package com.kexin.dkt.core.entity.vo;

import com.kexin.dkt.core.entity.security.SecUser;
import lombok.Data;

import java.util.Date;

/**
 * Created by kexin on 2017/10/30.
 */
@Data
public class UserParam {
    private String userId;
    private String userName;
    private String account;
    private String bossAccount;
    private String shuyeAcount;
    private String password;
    private String accType;
    private String deptId;
    private String channelId;
    private String email;
    private String mobile;
    private long enabled;
    private long deleted;
    private String branchId;
    private String dynaSalt;
    private String position;
    private String alias;
    private String creatorId;
    private Date createTime;
    private String modifierId;
    private Date modifyTime;
    private String headImage;
    private String newPassword;
}

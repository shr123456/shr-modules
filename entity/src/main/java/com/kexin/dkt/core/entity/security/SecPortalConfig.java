package com.kexin.dkt.core.entity.security;

import lombok.Data;
import java.util.Date;

@Data
public class SecPortalConfig {

  private String portalId;
  private String portalName;
  private long enabled;
  private String remark;
  private String createPerson;
  private Date createTime;
  private long deleted;

}

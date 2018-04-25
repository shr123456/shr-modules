package com.kexin.dkt.core.entity.file;

import lombok.Data;

import java.util.Date;

@Data
public class SysAccessory {

  private long id;
  private String relationId;
  private String filePath;
  private Date uploadTime;
  private String fileName;
  private String fileSuffix;
  private long fileType;
  private long busCode;
  private String busName;
  private String uploadPersonId;
  private String uploadPersonName;
  private long deleted;

}

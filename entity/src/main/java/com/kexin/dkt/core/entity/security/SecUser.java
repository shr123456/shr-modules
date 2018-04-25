package com.kexin.dkt.core.entity.security;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class SecUser implements RowMapper<SecUser>, Serializable {

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

  public SecUser mapRow(ResultSet resultSet, int i) throws SQLException {
    SecUser user = new SecUser();
    user.setUserId(resultSet.getString("user_id"));
    user.setUserName(resultSet.getString("user_name"));
    user.setAccount(resultSet.getString("account"));
    user.setBossAccount(resultSet.getString("boss_account"));
    user.setShuyeAcount(resultSet.getString("shuye_acount"));
    user.setPassword(resultSet.getString("password"));
    user.setAccType(resultSet.getString("acc_type"));
    user.setDeptId(resultSet.getString("dept_id"));
    user.setChannelId(resultSet.getString("channel_id"));
    user.setEmail(resultSet.getString("email"));
    user.setMobile(resultSet.getString("mobile"));
    user.setEnabled(resultSet.getLong("enabled"));
    user.setDeleted(resultSet.getLong("deleted"));
    user.setBranchId(resultSet.getString("branch_id"));
    user.setDynaSalt(resultSet.getString("dyna_salt"));
    user.setPosition(resultSet.getString("position"));
    user.setAlias(resultSet.getString("alias"));
    user.setCreatorId(resultSet.getString("creator_id"));
    user.setCreateTime(resultSet.getDate("create_time"));
    user.setModifierId(resultSet.getString("modifier_id"));
    user.setModifyTime(resultSet.getDate("modify_time"));
    return user;
  }
}

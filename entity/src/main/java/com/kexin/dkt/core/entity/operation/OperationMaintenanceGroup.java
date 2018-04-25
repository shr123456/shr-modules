package com.kexin.dkt.core.entity.operation;

import java.util.Date;

/**
 * 类名：班组人员分组
 * Created by yx on 2017/12/11.
 * .
 */
public class OperationMaintenanceGroup {

    private Integer operationGroupId;

    private String  operationGroupName;

    private String  operationGroupEngineer;

    private Date operationGroupTime;

    private Integer  deleted;

    public Integer getOperationGroupId() {
        return operationGroupId;
    }

    public void setOperationGroupId(Integer operationGroupId) {
        this.operationGroupId = operationGroupId;
    }

    public String getOperationGroupName() {
        return operationGroupName;
    }

    public void setOperationGroupName(String operationGroupName) {
        this.operationGroupName = operationGroupName;
    }

    public String getOperationGroupEngineer() {
        return operationGroupEngineer;
    }

    public void setOperationGroupEngineer(String operationGroupEngineer) {
        this.operationGroupEngineer = operationGroupEngineer;
    }

    public Date getOperationGroupTime() {
        return operationGroupTime;
    }

    public void setOperationGroupTime(Date operationGroupTime) {
        this.operationGroupTime = operationGroupTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}

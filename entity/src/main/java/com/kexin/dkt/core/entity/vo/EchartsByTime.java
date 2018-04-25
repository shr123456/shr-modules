package com.kexin.dkt.core.entity.vo;

/**
 * Created by kexin on 2017/11/1.
 */
public class EchartsByTime {
    private String userId;//用户id
    private int collectConfigId;//设备ID
    private String beginTime;//开始时间
    private String endTime;//结束时间
    private long  groupId;//设备分组id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCollectConfigId() {
        return collectConfigId;
    }

    public void setCollectConfigId(int collectConfigId) {
        this.collectConfigId = collectConfigId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}

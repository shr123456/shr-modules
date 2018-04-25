package com.kexin.dkt.core.entity.common;

/**
 * @auther shr
 * @date 2017/10/18
 * @time 14:25
 * @desc
 **/
public enum ResultCode {
    /**
     * 响应结果编码说明
     */

    /**
     * 业务操作成功返回编码200-299
     */
    SUCCESS("操作成功", "0"),
    /**
     * 业务操作失败返回编码400-499
     */
    ERROR("操作失败", "400"),
    /**
     * 业务操作其他结果返回编码300-399
     */
    EXTENTS("", "300");

    // 构造方法
    private ResultCode(String message, String code) {
        this.message = message;
        this.code = code;
    }

    private String message;
    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

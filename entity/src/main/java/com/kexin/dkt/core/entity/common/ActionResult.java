package com.kexin.dkt.core.entity.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @auther shr
 * @date 2017/10/18
 * @time 9:13
 * @desc
 **/
@Data
public class ActionResult implements Serializable {
    protected boolean isSuccess;
    protected Throwable error;
    protected String message;
    protected String timeStamp;
    protected String code;
    protected Object content;
    protected Object extend;
    protected Object custom;

    public ActionResult(){
        isSuccess = true;
        code = ResultCode.SUCCESS.getCode();
//        message = ResultCode.SUCCESS.getMessage();
        timeStamp = Long.toString(System.currentTimeMillis());
    }

}

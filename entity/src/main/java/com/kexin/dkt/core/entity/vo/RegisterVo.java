package com.kexin.dkt.core.entity.vo;

import lombok.Data;

/**
 * @auther shr
 * @date 2017/12/26
 * @time 17:12
 * @desc
 **/
@Data
public class RegisterVo {
    private String customerName;
    private String account;
    private String password;
    private String nickName ;
    private String linkTel;
}

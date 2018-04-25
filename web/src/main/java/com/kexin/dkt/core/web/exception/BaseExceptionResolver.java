package com.kexin.dkt.core.web.exception;

import com.kexin.dkt.core.entity.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther shr
 * @date 2017/10/26
 * @time 15:16
 * @desc : 系統統一异常处理类
 **/
@Slf4j
public class BaseExceptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error(e.getMessage(), e);
//        e.printStackTrace();
//        ModelAndView modelAndView=new ModelAndView();
//        //将错误信息传到页面
//        modelAndView.addObject("message","未知错误");
//        //指向到错误界面
//        modelAndView.setViewName("error");
//        return modelAndView;
//        modelAndView.addObject("success", false);
//        modelAndView.addObject("error", e.getMessage());
//        modelAndView.addObject("message", "请求失败，请重试");
//        modelAndView.addObject("code", ResultCode.ERROR.getCode());
//        modelAndView.addObject("content", "");
//        modelAndView.addObject("extend", "");
//
//        modelAndView.addObject("timeStamp", Long.toString(System.currentTimeMillis()));
        return null;
    }
}

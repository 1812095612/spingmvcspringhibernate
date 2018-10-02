package com.qiaosoftware.surveyproject.component.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    /**
     * form表单提交 Date类型数据绑定
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //不自动绑定对象中的roleSet属性，另行处理
        //binder.setDisallowedFields("roleSet");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
    
    /*@InitBinder("user")
    public void userBinder(WebDataBinder webDataBinder){
        //webDataBinder.setFieldDefaultPrefix(“user.”);来标明jsp中用user.id来传送参数
        webDataBinder.setFieldDefaultPrefix("user.");
    }

    @InitBinder("userDetail")
    public void userDetailBinder(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("userDetail.");
    }*/
    
    @ExceptionHandler(value = { java.lang.Exception.class })
    public ModelAndView handleException(Exception ex) {
        System.out.println("出现异常啦:" + ex);
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        return mv;
    }

}

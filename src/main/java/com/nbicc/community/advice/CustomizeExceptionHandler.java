package com.nbicc.community.advice;

import com.nbicc.community.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Throwable e, Model model){
        if (e instanceof MyException){
            model.addAttribute("message",e.getMessage());
        }else{
            model.addAttribute("message","服务崩溃了");
        }
        return new ModelAndView("error");

    }


}

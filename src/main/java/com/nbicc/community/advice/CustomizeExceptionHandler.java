package com.nbicc.community.advice;

import com.alibaba.fastjson.JSON;
import com.nbicc.community.dto.ResultDTO;
import com.nbicc.community.exception.CustomizeErrorCode;
import com.nbicc.community.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            if (e instanceof MyException){
                resultDTO = ResultDTO.errorOf((MyException)e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;
        }
        else{
            if (e instanceof MyException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message","服务崩溃了");
            }
            return new ModelAndView("error");
        }


    }


}

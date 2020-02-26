package com.nbicc.community.dto;

import com.nbicc.community.exception.CustomizeErrorCode;
import com.nbicc.community.exception.MyException;
import lombok.Data;

@Data
public class ResultDTO {
    private String message;
    private String code;

    public static ResultDTO errorOf(MyException e){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(e.getMessage());
        resultDTO.setCode(e.getCode());
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode customizeErrorCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(customizeErrorCode.getMessage());
        resultDTO.setCode(customizeErrorCode.getCode());
        return resultDTO;
    }

    public static ResultDTO successOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功");
        resultDTO.setCode("200");
        return resultDTO;
    }
}

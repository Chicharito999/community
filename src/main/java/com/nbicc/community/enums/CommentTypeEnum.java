package com.nbicc.community.enums;

import lombok.Data;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;
    CommentTypeEnum(Integer type){
        this.type = type;
    }
    public Integer getType() {
        return type;
    }
    public static boolean isExist(Integer type){
        for (CommentTypeEnum t:CommentTypeEnum.values()){
            if (t.getType()==type){
                return true;
            }
        }
        return false;
    }

}

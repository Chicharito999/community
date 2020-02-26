package com.nbicc.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String notifierName;
    private String typeName;
    private String outerTitle;
    private Integer status;
}

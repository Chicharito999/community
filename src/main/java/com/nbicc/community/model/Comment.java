package com.nbicc.community.model;

import lombok.Data;

@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
}

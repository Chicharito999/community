package com.nbicc.community.dto;

import com.nbicc.community.model.User;
import lombok.Data;

@Data
public class CommentResDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}

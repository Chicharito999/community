package com.nbicc.community.controller;

import com.nbicc.community.dto.CommentDTO;
import com.nbicc.community.dto.ResultDTO;
import com.nbicc.community.exception.CustomizeErrorCode;
import com.nbicc.community.exception.MyException;
import com.nbicc.community.mapper.CommentMapper;
import com.nbicc.community.model.Comment;
import com.nbicc.community.model.User;
import com.nbicc.community.service.CommentService;
import com.nbicc.community.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationService notificationService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            throw  new MyException(CustomizeErrorCode.NOTLOGIN);
        }
        if (commentDTO==null || StringUtils.isBlank(commentDTO.getContent())){
            throw new MyException(CustomizeErrorCode.NOTEXIST);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment);
        notificationService.insert(comment);
        return ResultDTO.successOf();
    }


}

package com.nbicc.community.service;

import com.nbicc.community.dto.CommentResDTO;
import com.nbicc.community.enums.CommentTypeEnum;
import com.nbicc.community.exception.CustomizeErrorCode;
import com.nbicc.community.exception.MyException;
import com.nbicc.community.mapper.CommentMapper;
import com.nbicc.community.mapper.QuestionMapper;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.Comment;
import com.nbicc.community.model.Question;
import com.nbicc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment){
        if (comment.getParentId()==null || comment.getParentId()==0){
            throw new MyException(CustomizeErrorCode.NOTEXIST);
        }
        if (comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            throw new MyException(CustomizeErrorCode.NOTEXIST);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            Comment comments = commentMapper.findById(comment.getParentId());
            if (comments==null){
                throw new MyException(CustomizeErrorCode.NOTEXIST);
            }
            commentMapper.insert(comment);
        }else{
            Question parentQuestion = questionMapper.getQuestionById((comment.getParentId().intValue()));
            if (parentQuestion==null){
                throw new MyException(CustomizeErrorCode.NOTEXIST);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(parentQuestion.getId());
        }
    }

    public List<CommentResDTO> getCommentsByQuestion(Integer id){
        List<Comment> comments = commentMapper.findByParentId(id.longValue());
        List<CommentResDTO> commentResDTOS = new ArrayList<>();
        for (Comment comment: comments){
            if (comment.getType() == 1){
                CommentResDTO commentResDTO = new CommentResDTO();
                User user = userMapper.findByCreator(comment.getCommentator());
                BeanUtils.copyProperties(comment,commentResDTO);
                commentResDTO.setUser(user);
                commentResDTOS.add(commentResDTO);
            }
        }
        return commentResDTOS;
    }




}

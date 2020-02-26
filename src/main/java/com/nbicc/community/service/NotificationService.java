package com.nbicc.community.service;

import com.nbicc.community.dto.NotificationDTO;
import com.nbicc.community.dto.PageinationDTO;
import com.nbicc.community.dto.QuestionDTO;
import com.nbicc.community.enums.NotificationTypeEnum;
import com.nbicc.community.mapper.CommentMapper;
import com.nbicc.community.mapper.NotificationMapper;
import com.nbicc.community.mapper.QuestionMapper;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.Comment;
import com.nbicc.community.model.Notification;
import com.nbicc.community.model.Question;
import com.nbicc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void insert(Comment comment){
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentator().longValue());
        notification.setOuterId(comment.getParentId());
        notification.setType(comment.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setStatus(0);
        Question question = questionMapper.getQuestionById(comment.getParentId().intValue());
        notification.setReceiver(question.getCreator().longValue());

        notificationMapper.insert(notification);
    }


    public PageinationDTO<NotificationDTO> getListById(Integer id, Integer page, Integer size) {
        PageinationDTO<NotificationDTO> pageinationDTO = new PageinationDTO<>();
        Integer count = notificationMapper.readCount(id.longValue());
        pageinationDTO.setTotalPage(count, size);
        Integer totalPage = pageinationDTO.getTotalPage();
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = (page - 1) * size;
        List<Notification> list = notificationMapper.getListById(id.longValue(), offset, size);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification q : list) {
            NotificationDTO notificationDTO = new NotificationDTO();
            //User user = userMapper.findByCreator(q.getCreator());
            notificationDTO.setId(q.getId());
            notificationDTO.setStatus(q.getStatus());
            notificationDTO.setTypeName(NotificationTypeEnum.REPLY_QUESTION.getName());
            User receiver = userMapper.findByCreator(q.getReceiver().intValue());
            notificationDTO.setNotifierName(receiver.getName());
            Question outer = questionMapper.getQuestionById(q.getOuterId().intValue());
            notificationDTO.setOuterTitle(outer.getTitle());
            notificationDTOS.add(notificationDTO);
        }
        pageinationDTO.setData(notificationDTOS);
        pageinationDTO.set(page);
        return pageinationDTO;
    }

    public int read(Long id){
        Notification notification = notificationMapper.getOneById(id);
        notification.setStatus(1);
        notificationMapper.updateById(id);
        return notification.getOuterId().intValue();
    }





}

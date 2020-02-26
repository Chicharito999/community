package com.nbicc.community.service;

import com.nbicc.community.dto.PageinationDTO;
import com.nbicc.community.dto.QuestionDTO;
import com.nbicc.community.exception.CustomizeErrorCode;
import com.nbicc.community.exception.MyException;
import com.nbicc.community.mapper.QuestionMapper;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.Question;
import com.nbicc.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageinationDTO<QuestionDTO> getList(Integer page, Integer size) {
        PageinationDTO<QuestionDTO> pageinationDTO = new PageinationDTO<>();
        Integer count = questionMapper.getCount();
        if (count == 0) {
            return null;
        }
        pageinationDTO.setTotalPage(count, size);
        Integer totalPage = pageinationDTO.getTotalPage();
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = (page - 1) * size;
        List<Question> list = questionMapper.getList(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question q : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findByCreator(q.getCreator());
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageinationDTO.setData(questionDTOS);
        pageinationDTO.set(page);
        return pageinationDTO;
    }

    public PageinationDTO<QuestionDTO> getListById(Integer id, Integer page, Integer size) {
        PageinationDTO<QuestionDTO> pageinationDTO = new PageinationDTO<>();
        Integer count = questionMapper.getCountById(id);
        pageinationDTO.setTotalPage(count, size);
        Integer totalPage = pageinationDTO.getTotalPage();
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = (page - 1) * size;
        List<Question> list = questionMapper.getListById(id, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question q : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findByCreator(q.getCreator());
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageinationDTO.setData(questionDTOS);
        pageinationDTO.set(page);
        return pageinationDTO;
    }

    public QuestionDTO getContentById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        if (question == null) {
            throw new MyException(CustomizeErrorCode.NOTEXIST);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findByCreator(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void incViewCount(Integer id) {
        questionMapper.incVeiwCount(id);
    }

    public List<Question> getRelated(QuestionDTO questionDTO) {
        Integer id = questionDTO.getId();
        String tag = questionDTO.getTag();
        String s = StringUtils.replaceChars(tag, ',', '|');
        List<Question> related = questionMapper.findRelated(id, s);
        return related;
    }

    public PageinationDTO<QuestionDTO> getSearchList(Integer page, Integer size, String search) {
        String s = StringUtils.replaceChars(search, ' ', '|');
        PageinationDTO<QuestionDTO> pageinationDTO = new PageinationDTO<>();
        Integer count = questionMapper.getSearchCount(s);
        if (count == 0) {
            return null;
        }
        pageinationDTO.setTotalPage(count, size);
        Integer totalPage = pageinationDTO.getTotalPage();
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = (page - 1) * size;
        List<Question> list = questionMapper.getSearchList(s,offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question q : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findByCreator(q.getCreator());
            BeanUtils.copyProperties(q, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageinationDTO.setData(questionDTOS);
        pageinationDTO.set(page);
        return pageinationDTO;
    }
}

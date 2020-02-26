package com.nbicc.community.controller;

import com.nbicc.community.dto.CommentResDTO;
import com.nbicc.community.dto.QuestionDTO;
import com.nbicc.community.model.Question;
import com.nbicc.community.service.CommentService;
import com.nbicc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model) {
        QuestionDTO questionDTO = questionService.getContentById(id);
        List<CommentResDTO> comments = commentService.getCommentsByQuestion(id);
        List<Question> related = questionService.getRelated(questionDTO);
        questionService.incViewCount(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",related);
        return "question";
    }

}

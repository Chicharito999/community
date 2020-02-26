package com.nbicc.community.controller;

import com.nbicc.community.dto.PageinationDTO;
import com.nbicc.community.dto.QuestionDTO;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.User;
import com.nbicc.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request, Model model, @RequestParam(name = "search",required = false)String search, @RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "6") Integer size) {
        PageinationDTO<QuestionDTO> pageinationDTO;
        if (StringUtils.isBlank(search)){
            pageinationDTO = questionService.getList(page, size);
        }else{
            pageinationDTO = questionService.getSearchList(page,size,search);
        }
        model.addAttribute("pageinationDTO", pageinationDTO);
        model.addAttribute("search",search);
        return "index";
    }
}


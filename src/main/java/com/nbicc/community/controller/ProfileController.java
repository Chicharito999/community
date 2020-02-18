package com.nbicc.community.controller;

import com.nbicc.community.dto.PageinationDTO;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.User;
import com.nbicc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model, HttpServletRequest request, @RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "1") Integer size) {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }


        String sectionName = null;
        String section = null;
        if ("questions".equals(action)) {
            sectionName = "我的问题";
            section = "questions";
        } else if ("replies".equals(action)) {
            sectionName = "最新回复";
            section = "replies";
        }

        PageinationDTO pageinationDTO = questionService.getListById(user.getId(), page, size);
        model.addAttribute("pageinationDTO", pageinationDTO);
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("section", section);
        return "profile";
    }


}

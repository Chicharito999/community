package com.nbicc.community.controller;

import com.nbicc.community.mapper.QuestionMapper;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.Question;
import com.nbicc.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    Question question;
    @GetMapping("/publish")
    public String publish(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie: cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    request.getSession().setAttribute("user",user);
                    break;
                }
            }
        }
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "description", required = false) String description,
                            @RequestParam(name = "tag", required = false) String tag,
                            HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie: cookies){
                if ("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    request.getSession().setAttribute("user",user);
                    break;
                }
            }
        }
        if (user == null){
            model.addAttribute("error","请先登录");
            return "publish";
        }
        if (title==null || "".equals(title)){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||"".equals(description)){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag==null||"".equals(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insertQuestion(question);
        return "index";
    }
}

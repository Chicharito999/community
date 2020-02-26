package com.nbicc.community.controller;

import com.nbicc.community.dto.AccessTokenDTO;
import com.nbicc.community.dto.GithubUser;
import com.nbicc.community.mapper.UserMapper;
import com.nbicc.community.model.User;
import com.nbicc.community.provider.GithubProvider;
import com.nbicc.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {
    @Autowired
    AccessTokenDTO accessTokenDTO;
    @Autowired
    GithubProvider githubProvider;
    @Autowired
    User user;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Value("${github.client.id}")
    String clientId;
    @Value("${github.redirect.uri}")
    String redirectUri;
    @Value("${github.client.secret}")
    String clientSecret;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletRequest request, HttpServletResponse response) {
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null) {
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            //user.setGmtCreate(System.currentTimeMillis());
            //user.setGmtModified(user.getGmtCreate());
            user.setToken(UUID.randomUUID().toString());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //userMapper.insertUser(user);
            userService.InsertOrUpate(user);
            response.addCookie(new Cookie("token", user.getToken()));
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            log.error("callback get github error {}",githubUser);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}

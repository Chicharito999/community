package com.nbicc.community.controller;

import com.nbicc.community.dto.AccessTokenDTO;
import com.nbicc.community.dto.GithubUser;
import com.nbicc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    AccessTokenDTO accessTokenDTO;
    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client.id}")
    String clientId;
    @Value("${github.redirect.uri}")
    String redirectUri;
    @Value("${github.client.secret}")
    String clientSecret;
    @GetMapping("/callback")
    public String callback(@RequestParam("code")String code,@RequestParam("state")String state){
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
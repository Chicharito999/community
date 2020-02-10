package com.nbicc.community.dto;

import lombok.Data;

import java.util.Locale;
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}

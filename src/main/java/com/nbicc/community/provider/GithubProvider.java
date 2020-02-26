package com.nbicc.community.provider;

import com.alibaba.fastjson.JSON;
import com.nbicc.community.dto.AccessTokenDTO;
import com.nbicc.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    @Value("${access.token.url}")
    String accessUrl;
    @Value("${user.url}")
    String userUrl;

    public String getAccessToken(AccessTokenDTO dto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url(accessUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);
            return string.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(userUrl + "=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

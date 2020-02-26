package com.nbicc.community.controller;

import com.nbicc.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String notification(@PathVariable("id") Long id){
        int questionId = notificationService.read(id);
        return "redirect:/question/"+String.valueOf(questionId);
    }




}

package com.cheer.springmvc.thymeleaf.web.controller;

import com.cheer.spring.mybatis.model.Question;
import com.cheer.spring.mybatis.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @ResponseBody
    @RequestMapping("questionList")
    public List questionList(){
        List<Question> questionList = questionService.getList();
        return questionList;
    }
}

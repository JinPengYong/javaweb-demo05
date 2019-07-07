package com.cheer.springmvc.thymeleaf.web.controller;

import com.cheer.spring.mybatis.model.Admin;
import com.cheer.spring.mybatis.model.Question;
import com.cheer.spring.mybatis.service.AdminService;
import com.cheer.spring.mybatis.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
public class AdminController{
    @Autowired
    private AdminService adminService;
    @Autowired
    private QuestionService questionService;
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("adminLogin")
    public String adminLogin(){
        return "adminLogin";
    }

    @PostMapping("adminLogin")
    public String adminLogin(String username, String password, HttpSession session,Model model) {
        Admin admin = adminService.find(username);
        if(admin !=null&&username.equals(admin.getUsername())&&password.equals(admin.getPassword())){
            session.setAttribute("admin",admin);
            List<Question> questionList = questionService.getList();
            /*session.setAttribute("questionList",questionList);*/
            model.addAttribute("questionList",questionList);
            return "pageAdmin";
        }else {
            return "redirect:/adminLogin";
        }
    }
}

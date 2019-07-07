package com.cheer.springmvc.thymeleaf.web.controller;

import com.cheer.spring.mybatis.model.Examinee;
import com.cheer.spring.mybatis.model.Question;
import com.cheer.spring.mybatis.service.ExamineeService;
import com.cheer.spring.mybatis.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
public class ExamineController {
    @Autowired
    private ExamineeService examineeService;
    @Autowired
    private QuestionService questionService;
    @GetMapping("examineLogin")
    public String examineLogin(){
        return "examineLogin";
    }
    @PostMapping("examineLogin")
    public String examineLogin(@Param("num") String num, @Param("password") String password, HttpSession session){
        Examinee examinee = examineeService.find(num);

        if (examinee != null&&num !=null&&password.equals(examinee.getPassword())) {
            session.setAttribute("num",num);
            session.setAttribute("examinee",examinee);
            return "pageExamine";
        }
        else{
            return "redirect:/examineLogin";
        }
    }

    @ResponseBody
    @GetMapping("answerResult")
    public Integer answerResult(@Param("answer") String[] answer,HttpSession session,Model model){
        List<String> resultList = questionService.getResultList();
        int x=0;
        int y=0;
        int score=0;
        String result=null;
        for (int i = 0; i <answer.length ; i++) {
            String s = resultList.get(i);
            String[] split = s.split("：");
            if(split[1].equals(answer[i])){
                x++;
            }else {
                y++;
            }
        }
        score=x*20;
        if(score>=100){
            result="合格";
        }else {
            result="不合格";
        }
        String num = (String)session.getAttribute("num");
        Examinee examinee=new Examinee(num,x,y,score,result);
        examineeService.update(examinee);

        return score;
    }
    @RequestMapping("result")
    public String result(Model model,HttpSession session){

        String num = (String)session.getAttribute("num");
        Examinee examinee = examineeService.find(num);
        model.addAttribute("examinee",examinee);
        return "result";
    }
}

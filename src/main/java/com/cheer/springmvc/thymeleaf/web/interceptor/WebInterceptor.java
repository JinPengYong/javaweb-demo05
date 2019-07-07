package com.cheer.springmvc.thymeleaf.web.interceptor;

import com.cheer.spring.mybatis.model.Admin;
import com.cheer.spring.mybatis.model.Examinee;
import com.cheer.spring.mybatis.model.Question;
import com.cheer.spring.mybatis.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class WebInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;
    private static final String[] IGNORE_URI = {"/login","/adminLogin","/examineLogin"};
    // 前置处理 返回值为false表示请求到此结束，不在往下执行，
    // 反之true，继续执行（交给下一个拦截器的前置方法处理或者目标处理方法）
    //先进后出，后进先出
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("111");
        String servletPath = request.getServletPath();
        for (String s : IGNORE_URI) {
            if (servletPath.contains(s)) {
                return true;
            }
        }

        Admin admin = (Admin)request.getSession().getAttribute("admin");
        Examinee examinee = (Examinee)request.getSession().getAttribute("examinee");
        if (admin == null&&examinee ==null) {
            return false;
        }
        return true;

    }

    @Override
    // 目标方法的后置处理
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("后置处理1");
    }

    @Override
    // 在完成之后执行 这个方法在 DispatcherServlet 完全处理完请 求后被调用，可以在该方法中进行一些资源清理的操作。
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("完成后执行1。。。");
    }
}

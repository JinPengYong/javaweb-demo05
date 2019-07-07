package com.cheer.springmvc.thymeleaf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.annotation.Resource;

@Configuration
@ComponentScan("com.cheer.springmvc.thymeleaf")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired // 注入spring容器
    private ApplicationContext applicationContext;

    @Resource(name="webInterceptor")
    private HandlerInterceptor interceptor;
    //注册拦截器
  /*  public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor).addPathPatterns("/*");
        registry.addInterceptor(interceptor2).addPathPatterns("/*");
    }*/

   /* // 处理静态资源css/js/image
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    *//* **************************************************************** *//*
    *//*  THYMELEAF-SPECIFIC ARTIFACTS                                    *//*
    *//*  TemplateResolver <- TemplateEngine <- ViewResolver              *//*
    *//* **************************************************************** *//*
*/
    // 模板解析器
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("utf-8");
        return templateResolver;
    }

    // 模板引擎
    @Bean
    public SpringTemplateEngine templateEngine(){
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    // 视图解析器
    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("utf-8");
        return viewResolver;
    }

    @Override
    // 简化静态资源处理方式，直接通过访问
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
     /*  registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/html").
                addResourceLocations("/resources").addResourceLocations("/WEB-INF/lib").addResourceLocations("/upload");*/


        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/templates/html");

    }

    @Override
    //注册拦截器
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor).addPathPatterns("/*");

    }


}

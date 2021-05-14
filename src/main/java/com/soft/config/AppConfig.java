
package com.soft.config;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/** Configuration class */

@Configuration
@ComponentScan(basePackages = "com.soft.controller") 
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{

    private static final String UTF8 = "UTF-8";

    private ApplicationContext applicationContext;

     @Override
     public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
       argumentResolvers.add(new AuthenticationPrincipalArgumentResolver());
     }    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewresolver = new ThymeleafViewResolver();
         viewresolver.setTemplateEngine((ISpringTemplateEngine) templateEngine());
         viewresolver.setOrder(1);
         viewresolver.setViewNames(new String[] {"*.html", "*.xhtml"});
         viewresolver.setCharacterEncoding(UTF8);
     return viewresolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
       SpringTemplateEngine templateEngine = new SpringTemplateEngine();
         templateEngine.setTemplateResolver(templateResolver());
         templateEngine.setEnableSpringELCompiler(true);
     return templateEngine;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
     return templateResolver;
    }
    
    /** Location of static resources(css, images, js). */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    }
}

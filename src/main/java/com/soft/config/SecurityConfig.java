package com.soft.config;

import com.soft.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Security configuration */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired UserService userService;

    @Bean
    public UserService getUserService() {
        return new UserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
         .authorizeRequests()
             .antMatchers("/register").permitAll()
             .antMatchers("/home.html", "/api/getalltopics", "/api/createanswer").hasAnyAuthority("USER","ADMIN")
             .antMatchers("/admin.html", "/questions.html", "/editquestion.html", "/api/createtopic", "/api/deletetopic", "/api/edittopic/", "/api/createquestion", "/api/deletequestion", "/api/editquestion", "/api/updatequestion").hasAuthority("ADMIN")
             .anyRequest().authenticated()
         .and().formLogin()
             .loginPage("/login.html")
             .failureUrl("/login-error")
             .defaultSuccessUrl("/home.html", true)
             .usernameParameter("username")
             .passwordParameter("password")
             .permitAll()
         .and().logout()
             .logoutUrl("/logout").logoutSuccessUrl("/logout-success")
             .invalidateHttpSession(true).deleteCookies("JSESSIONID")
             .permitAll()
         .and().csrf().disable();
    }
        
    @Override
    public void configure(WebSecurity security)
    {
      /** Permit requests for static resources. */
      security.ignoring().antMatchers("/css/*.css", "/img/*.gif", "/js/*.js");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
               .userDetailsService(this.userService)
               .passwordEncoder(new BCryptPasswordEncoder());
    }
}

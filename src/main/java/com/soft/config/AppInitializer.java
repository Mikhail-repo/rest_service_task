package com.soft.config;

import com.soft.database.DatabaseConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**  Initialization */

public class AppInitializer implements WebApplicationInitializer{
            
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        
        AnnotationConfigWebApplicationContext context;

        /** ApplicationContext */
        context = new AnnotationConfigWebApplicationContext();
        context.register(DatabaseConfig.class, AppConfig.class);
        context.setServletContext(sc);
        context.refresh();

        /** DispatcherServlet */
        DispatcherServlet dispServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = sc.addServlet("Dispatcher", dispServlet);
        registration.setLoadOnStartup(1);

        /** DispatcherServlet's URL mappings. */
        registration.addMapping("/");
    }            
}

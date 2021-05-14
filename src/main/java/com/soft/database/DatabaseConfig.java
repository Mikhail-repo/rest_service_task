package com.soft.database;

import java.io.IOException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class DatabaseConfig {

    @Bean(name="DataSourceBean")
    public DataSource getDataSource(){
       final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
          dsLookup.setResourceRef(true);
          DataSource dataSource = dsLookup.getDataSource("jdbc/UserAccountsDB");
     return dataSource;
    }

    @Bean(name="SessionFactory")
    @DependsOn("DataSourceBean")
    public SessionFactory getSessionFactory(DataSource dataSource) throws IOException {

        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);

        /* List of entities. */
        localSessionFactoryBean.setAnnotatedClasses(com.soft.security.MyUserDetails.class, com.soft.entity.Poll.class, com.soft.entity.Question.class);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        localSessionFactoryBean.setHibernateProperties(properties);
        localSessionFactoryBean.afterPropertiesSet();

     return  localSessionFactoryBean.getObject();
    }

    @Bean
    @DependsOn("SessionFactory")
    public UserAccountsDAOImpl getUserAccountsDAO(SessionFactory sessionFactory) {

         UserAccountsDAOImpl userAccountsDAO = new UserAccountsDAOImpl();
         userAccountsDAO.setSessionFactory(sessionFactory);
     return userAccountsDAO;
    }

    @Bean
    @DependsOn("SessionFactory")
    public PollsDAO getPollDAO(SessionFactory sessionFactory) {

        PollsDAO pollsDAO = new PollsDAO();
        pollsDAO.setSessionFactory(sessionFactory);
     return pollsDAO;
    }

    @Bean
    @DependsOn("SessionFactory")
    public QuestionsDAO getQuestionsDAO(SessionFactory sessionFactory) {

        QuestionsDAO questionsDAO = new QuestionsDAO();
        questionsDAO.setSessionFactory(sessionFactory);
     return questionsDAO;
    }
}


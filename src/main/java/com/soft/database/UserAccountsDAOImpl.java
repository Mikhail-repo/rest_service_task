package com.soft.database;

import com.soft.security.MyUserDetails;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserAccountsDAOImpl implements UserAccountsDAO{

    private SessionFactory sessionFactory;
  
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;   
    }
    
    /** Create a new user's account. */
    @Override
    public String createUserAccount(MyUserDetails myUserDetails) {

       Session session;
       String Resp="";
           try {
                 session = sessionFactory.openSession();
                 session.beginTransaction();
                 session.save(myUserDetails);
                 session.getTransaction().commit();
                 session.close();
                 Resp = "Data saved!";
            }
            catch(Exception e) {
               Resp="Exception occured: "+e.getMessage();   
            }            
     return Resp; 
    }

    @Override
    public MyUserDetails retrieveUserAccount(String email) {

        Session session;
        MyUserDetails userAccount = null;
          try {
               session = sessionFactory.openSession();
               session.beginTransaction();
               List result = session.createQuery("SELECT DISTINCT usr FROM MyUserDetails usr WHERE Email="+"'"+email+"'").list();

               for (MyUserDetails usr : (List<MyUserDetails>) result ) {
                 userAccount = usr;
               }
               session.getTransaction().commit();
               session.close();
           }
           catch(Exception e) {
           }
     return userAccount;
    }

    public boolean isAccountInDatabase(String email){
       if(retrieveUserAccount(email)!=null) return true;
        else return false;
    }
}
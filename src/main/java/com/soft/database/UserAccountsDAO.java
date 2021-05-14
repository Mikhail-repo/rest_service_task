package com.soft.database;

import com.soft.security.MyUserDetails;
import org.hibernate.SessionFactory;

public interface UserAccountsDAO {

    public void setSessionFactory(SessionFactory sessionFactory);

    public String createUserAccount(MyUserDetails myUserDetails);

    public MyUserDetails retrieveUserAccount(String email);

    public boolean isAccountInDatabase(String email);
}

package com.soft.security;

import com.soft.database.UserAccountsDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    
    /** Inject DAO */
    @Autowired UserAccountsDAOImpl userAccountsDAO;
    
    public UserService(){}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        MyUserDetails userAccount = null;
        try{
            userAccount = userAccountsDAO.retrieveUserAccount(email);                
        }
        catch(Exception exc){           
        }   
          
     return userAccount;
    }
}
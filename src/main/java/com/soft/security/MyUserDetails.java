package com.soft.security;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/** Сущность аккаунта пользователя. */
@Entity
@Table( name = "accounts" )
public class MyUserDetails implements UserDetails, Serializable{
    
  private static final long serialVersionUID = 1L;  
   
   @Id    
   @Column(name = "Email") 
   private String email;
   
   @Column(name = "FirstName") 
   private String firstName;
   
   @Column(name = "LastName") 
   private String lastName;  

   @Column(name = "Password") 
   private String userPassword;	

   @Column(name = "Admin") 
   private boolean admin;   
    
    public MyUserDetails() {}
     
    public MyUserDetails(String firstName, String lastName, String email, String userPassword, boolean admin) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.userPassword = userPassword;
    this.admin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
         
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      if(isAdmin())
        return AuthorityUtils.createAuthorityList("USER","ADMIN");
      else
        return AuthorityUtils.createAuthorityList("USER");
    }

    @Override
    public String getPassword() {
     return getUserPassword();
    }

    @Override
    public String getUsername() {
     return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
     return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
}
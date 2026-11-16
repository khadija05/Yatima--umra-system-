package com.example.jwtlogin.security.services;
import com.example.jwtlogin.model.User; import org.springframework.security.core.GrantedAuthority; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.userdetails.UserDetails; import java.util.*;
public class UserDetailsImpl implements UserDetails {
  private User u; public UserDetailsImpl(User u){this.u=u;} public static UserDetailsImpl build(User u){return new UserDetailsImpl(u);}
  public User getUser(){return u;} public Collection<? extends GrantedAuthority> getAuthorities(){ return List.of(new SimpleGrantedAuthority(u.getRole())); }
  public String getPassword(){return u.getPassword();} public String getUsername(){return u.getUsername();}
  public boolean isAccountNonExpired(){return true;} public boolean isAccountNonLocked(){return !u.isLocked();} public boolean isCredentialsNonExpired(){return true;} public boolean isEnabled(){return true;}
}

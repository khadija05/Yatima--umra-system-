
package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="users", uniqueConstraints={@UniqueConstraint(columnNames="username"), @UniqueConstraint(columnNames="email")})
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String username; private String email; private String password;
  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name="user_roles", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
  private Set<Role> roles = new HashSet<>();
  public User(){}
  public User(String u,String e,String p){username=u;email=e;password=p;}
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getUsername(){return username;} public void setUsername(String u){username=u;}
  public String getEmail(){return email;} public void setEmail(String e){email=e;}
  public String getPassword(){return password;} public void setPassword(String p){password=p;}
  public Set<Role> getRoles(){return roles;} public void setRoles(Set<Role> r){roles=r;}
}
  

package com.yatima.model;
import jakarta.persistence.*;
@Entity
@Table(name="users")
public class User {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String username;
private String password;
private String role;
private String tenantId;
private String phone;
private String name;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getUsername(){ return username; }
public void setUsername(String v){ this.username = v; }
public String getPassword(){ return password; }
public void setPassword(String v){ this.password = v; }
public String getRole(){ return role; }
public void setRole(String v){ this.role = v; }
public String getTenantId(){ return tenantId; }
public void setTenantId(String v){ this.tenantId = v; }
public String getPhone(){ return phone; }
public void setPhone(String v){ this.phone = v; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
}

package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Booking {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String phone;
private String username;
private String institution;
private String jinaPackage;
private String jinaKifurushi;
private String packageName;
private Double amount;
private String status;
private LocalDateTime createdAt;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public String getPhone(){ return phone; }
public void setPhone(String v){ this.phone = v; }
public String getUsername(){ return username; }
public void setUsername(String v){ this.username = v; }
public String getInstitution(){ return institution; }
public void setInstitution(String v){ this.institution = v; }
public String getJinaPackage(){ return jinaPackage; }
public void setJinaPackage(String v){ this.jinaPackage = v; }
public String getJinaKifurushi(){ return jinaKifurushi; }
public void setJinaKifurushi(String v){ this.jinaKifurushi = v; }
public String getPackageName(){ return packageName; }
public void setPackageName(String v){ this.packageName = v; }
public Double getAmount(){ return amount; }
public void setAmount(Double v){ this.amount = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public LocalDateTime getCreatedAt(){ return createdAt; }
public void setCreatedAt(LocalDateTime v){ this.createdAt = v; }
}

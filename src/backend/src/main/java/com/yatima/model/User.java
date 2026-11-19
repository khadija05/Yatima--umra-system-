package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
private String email;
private Boolean locked = false;
private String plan;
private String status;
private Boolean approvedByKhadija = false;
private LocalDateTime expiresAt;
private LocalDateTime createdAt;
private LocalDateTime trialEndsAt;
private String resetToken;
private LocalDateTime resetTokenExpiry;
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
public String getEmail(){ return email; }
public void setEmail(String v){ this.email = v; }
public Boolean getLocked(){ return locked; }
public void setLocked(Boolean v){ this.locked = v; }
public boolean isLocked(){ return locked != null && locked; }
public String getPlan(){ return plan; }
public void setPlan(String v){ this.plan = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public Boolean getApprovedByKhadija(){ return approvedByKhadija; }
public void setApprovedByKhadija(Boolean v){ this.approvedByKhadija = v; }
public boolean isApprovedByKhadija(){ return approvedByKhadija != null && approvedByKhadija; }
public LocalDateTime getExpiresAt(){ return expiresAt; }
public void setExpiresAt(LocalDateTime v){ this.expiresAt = v; }
public LocalDateTime getCreatedAt(){ return createdAt; }
public void setCreatedAt(LocalDateTime v){ this.createdAt = v; }
public LocalDateTime getTrialEndsAt(){ return trialEndsAt; }
public void setTrialEndsAt(LocalDateTime v){ this.trialEndsAt = v; }
public String getResetToken(){ return resetToken; }
public void setResetToken(String v){ this.resetToken = v; }
public LocalDateTime getResetTokenExpiry(){ return resetTokenExpiry; }
public void setResetTokenExpiry(LocalDateTime v){ this.resetTokenExpiry = v; }
}

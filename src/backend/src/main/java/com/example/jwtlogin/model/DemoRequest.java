package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="demo_requests")
public class DemoRequest {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String fullName; private String email; private String password; private String status="PENDING";
  private LocalDateTime createdAt=LocalDateTime.now();
  public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){this.fullName=v;}
  public String getEmail(){return email;} public void setEmail(String v){this.email=v;}
  public String getPassword(){return password;} public void setPassword(String v){this.password=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
  public LocalDateTime getCreatedAt(){return createdAt;}
}

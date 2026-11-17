package com.yatima.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  @Column(unique=true) public String username;
  @Column(unique=true) public String email;
  public String password; public String role; public String orgName;
  public String plan="DEMO"; public LocalDateTime subscriptionExpiry;
  public boolean locked=false; public LocalDateTime createdAt=LocalDateTime.now();
}

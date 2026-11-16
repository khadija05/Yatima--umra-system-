package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true, nullable=false) private String username;
  private String password; private String role="ROLE_USER";
  private String fullName; private String userType; private String ownerEmail;
  private String orgName;
  private String plan="KAWAIDA"; private Double planPrice=100000.0;
  private LocalDate subscriptionExpiry=LocalDate.now().plusDays(30);
  private boolean subscriptionActive=true; private boolean locked=false;
  private LocalDate demoExpiry; private int totalPaid=0;
  public User(){} public User(String u,String p){this.username=u; this.password=p;} public User(String u,String p,String r){this.username=u; this.password=p; this.role=r;}
  public Long getId(){return id;} public String getUsername(){return username;} public void setUsername(String v){this.username=v;}
  public String getPassword(){return password;} public void setPassword(String v){this.password=v;}
  public String getRole(){return role;} public void setRole(String v){this.role=v;}
  public String getFullName(){return fullName;} public void setFullName(String v){this.fullName=v;}
  public String getUserType(){return userType;} public void setUserType(String v){this.userType=v;}
  public String getOwnerEmail(){return ownerEmail;} public void setOwnerEmail(String v){this.ownerEmail=v;}
  public String getOrgName(){return orgName;} public void setOrgName(String v){this.orgName=v;}
  public String getPlan(){return plan;} public void setPlan(String v){this.plan=v;}
  public Double getPlanPrice(){return planPrice;} public void setPlanPrice(Double v){this.planPrice=v;}
  public LocalDate getSubscriptionExpiry(){return subscriptionExpiry;} public void setSubscriptionExpiry(LocalDate v){this.subscriptionExpiry=v;}
  public boolean isSubscriptionActive(){return subscriptionActive;} public void setSubscriptionActive(boolean v){this.subscriptionActive=v;}
  public boolean isLocked(){return locked;} public void setLocked(boolean v){this.locked=v;}
  public LocalDate getDemoExpiry(){return demoExpiry;} public void setDemoExpiry(LocalDate v){this.demoExpiry=v;}
  public int getTotalPaid(){return totalPaid;} public void setTotalPaid(int v){this.totalPaid=v;}
}

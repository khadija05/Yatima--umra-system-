package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true) private String username;
  private String email; private String phone; private String password;
  private String role="Mthamini"; private String tenantId=""; private String org="";
  private String plan="FREE-DEMO"; private Double amount=0.0; private String status="pending";
  private String paymentMethod="PBZ"; private String pbzAccount="0887387001"; private String pbzName="KHADIJA DENGE";
  private String myPbzAccount=""; private String myPbzName=""; private String requirements="";
  private String orgPhone=""; private String orgLocation="";
  private Boolean approvedByKhadija=false; private Boolean locked=false;
  private LocalDateTime expiresAt; private String muamala=""; private String date="";
  private String resetToken; private LocalDateTime resetTokenExpiry;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
  public String getPassword(){return password;} public void setPassword(String v){password=v;}
  public String getRole(){return role;} public void setRole(String v){role=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getOrg(){return org;} public void setOrg(String v){org=v;}
  public String getPlan(){return plan;} public void setPlan(String v){plan=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){amount=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
  public String getPbzAccount(){return pbzAccount;} public void setPbzAccount(String v){pbzAccount=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
  public String getMyPbzAccount(){return myPbzAccount;} public void setMyPbzAccount(String v){myPbzAccount=v;}
  public String getMyPbzName(){return myPbzName;} public void setMyPbzName(String v){myPbzName=v;}
  public String getRequirements(){return requirements;} public void setRequirements(String v){requirements=v;}
  public String getOrgPhone(){return orgPhone;} public void setOrgPhone(String v){orgPhone=v;}
  public String getOrgLocation(){return orgLocation;} public void setOrgLocation(String v){orgLocation=v;}
  public Boolean getApprovedByKhadija(){return approvedByKhadija;} public void setApprovedByKhadija(Boolean v){approvedByKhadija=v;}
  public Boolean getLocked(){return locked;} public void setLocked(Boolean v){locked=v;}
  public LocalDateTime getExpiresAt(){return expiresAt;} public void setExpiresAt(LocalDateTime v){expiresAt=v;}
  public String getMuamala(){return muamala;} public void setMuamala(String v){muamala=v;}
  public String getDate(){return date;} public void setDate(String v){date=v;}
  public String getResetToken(){return resetToken;} public void setResetToken(String v){resetToken=v;}
  public LocalDateTime getResetTokenExpiry(){return resetTokenExpiry;} public void setResetTokenExpiry(LocalDateTime v){resetTokenExpiry=v;}
}

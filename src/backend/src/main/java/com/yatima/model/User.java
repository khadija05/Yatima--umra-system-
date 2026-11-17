package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true) private String username;
  @Column(unique=true) private String email;
  private String phone; private String password;
  private String role="Mthamini";
  private String tenantId=""; private String org=""; private String orgLogo=""; private String orgPhone=""; private String orgLocation="Zanzibar";
  @Column(length=2000) private String requirements="";
  private String plan="FREE-0"; private Double amount=0.0; private String status="pending"; private String paymentMethod="FREE"; private String muamala="";
  private String pbzAccount="0887387001"; private String pbzName="KHADIJA DENGE";
  private String myPbzAccount=""; private String myPbzName="";
  private LocalDateTime expiresAt; private LocalDateTime createdAt=LocalDateTime.now(); private boolean approvedByKhadija=false;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
  public String getPassword(){return password;} public void setPassword(String v){password=v;}
  public String getRole(){return role;} public void setRole(String v){role=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getOrg(){return org;} public void setOrg(String v){org=v;}
  public String getOrgLogo(){return orgLogo;} public void setOrgLogo(String v){orgLogo=v;}
  public String getOrgPhone(){return orgPhone;} public void setOrgPhone(String v){orgPhone=v;}
  public String getOrgLocation(){return orgLocation;} public void setOrgLocation(String v){orgLocation=v;}
  public String getRequirements(){return requirements;} public void setRequirements(String v){requirements=v;}
  public String getPlan(){return plan;} public void setPlan(String v){plan=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){amount=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
  public String getMuamala(){return muamala;} public void setMuamala(String v){muamala=v;}
  public String getPbzAccount(){return pbzAccount;} public void setPbzAccount(String v){pbzAccount=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
  public String getMyPbzAccount(){return myPbzAccount;} public void setMyPbzAccount(String v){myPbzAccount=v;}
  public String getMyPbzName(){return myPbzName;} public void setMyPbzName(String v){myPbzName=v;}
  public LocalDateTime getExpiresAt(){return expiresAt;} public void setExpiresAt(LocalDateTime v){expiresAt=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
  public boolean isApprovedByKhadija(){return approvedByKhadija;} public void setApprovedByKhadija(boolean v){approvedByKhadija=v;}
}

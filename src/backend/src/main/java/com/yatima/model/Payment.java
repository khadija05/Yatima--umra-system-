package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String tenantId=""; private String username; private String plan; private Double amount=0.0; private String muamala=""; private String method="PBZ";
  private String pbzAccount="0887387001"; private String pbzName="KHADIJA DENGE"; private String status="pending"; private LocalDateTime createdAt=LocalDateTime.now();
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getPlan(){return plan;} public void setPlan(String v){plan=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){amount=v;}
  public String getMuamala(){return muamala;} public void setMuamala(String v){muamala=v;}
  public String getMethod(){return method;} public void setMethod(String v){method=v;}
  public String getPbzAccount(){return pbzAccount;} public void setPbzAccount(String v){pbzAccount=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

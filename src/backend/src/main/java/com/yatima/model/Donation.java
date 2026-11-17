package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Donation {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String tenantId=""; private String donorName; private Double amount=0.0; private String method="PBZ"; private String pbzName="KHADIJA DENGE";
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getDonorName(){return donorName;} public void setDonorName(String v){donorName=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){amount=v;}
  public String getMethod(){return method;} public void setMethod(String v){method=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
}

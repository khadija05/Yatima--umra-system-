package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="donations")
public class Donation {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String donorName=""; private String jinaMthamini=""; private String donorPhone="";
  private Double amount=0.0; private Double kiasi=0.0; private String method="PBZ"; private String paymentMethod="PBZ";
  private Long orphanId; private String tenantId=""; private String pbzName=""; private String status="pending";
  private LocalDateTime createdAt=LocalDateTime.now();
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getDonorName(){return donorName;} public void setDonorName(String v){donorName=v;}
  public String getJinaMthamini(){return jinaMthamini;} public void setJinaMthamini(String v){jinaMthamini=v;}
  public String getDonorPhone(){return donorPhone;} public void setDonorPhone(String v){donorPhone=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){amount=v;}
  public Double getKiasi(){return kiasi;} public void setKiasi(Double v){kiasi=v;}
  public String getMethod(){return method;} public void setMethod(String v){method=v;}
  public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
  public Long getOrphanId(){return orphanId;} public void setOrphanId(Long v){orphanId=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

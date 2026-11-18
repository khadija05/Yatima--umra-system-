package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="payments")
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String username=""; private String donorName=""; private String tenantId="";
  private String plan=""; private Double amount=0.0; private String muamala="";
  private String reference=""; private String paymentMethod="PBZ"; private String pbzName="";
  private String status="PENDING"; private String date=""; private String reason="";
  private String approvedBy=""; private LocalDateTime createdAt=LocalDateTime.now();
  private LocalDateTime approvedAt; private Long orphanId;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getDonorName(){return donorName;} public void setDonorName(String v){donorName=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getPlan(){return plan;} public void setPlan(String v){plan=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){amount=v;}
  public String getMuamala(){return muamala;} public void setMuamala(String v){muamala=v;}
  public String getReference(){return reference;} public void setReference(String v){reference=v;}
  public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getDate(){return date;} public void setDate(String v){date=v;}
  public String getReason(){return reason;} public void setReason(String v){reason=v;}
  public String getApprovedBy(){return approvedBy;} public void setApprovedBy(String v){approvedBy=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
  public LocalDateTime getApprovedAt(){return approvedAt;} public void setApprovedAt(LocalDateTime v){approvedAt=v;}
  public Long getOrphanId(){return orphanId;} public void setOrphanId(Long v){orphanId=v;}
  public boolean isApproved(){return "APPROVED".equalsIgnoreCase(status);}
  public boolean isPending(){return "PENDING".equalsIgnoreCase(status);}
  public void approve(String by, LocalDateTime exp){ status="APPROVED"; approvedBy=by; approvedAt=LocalDateTime.now(); }
  public void reject(String r){ status="REJECTED"; reason=r; }
}

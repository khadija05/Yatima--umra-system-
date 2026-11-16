package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="renew_requests")
public class RenewRequest {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String email; private String payerName; private String transactionRef; private Double amount;
  private String plan="KUBWA"; private String paymentMethod="PBZ"; private String status="PENDING";
  private LocalDateTime createdAt=LocalDateTime.now();
  public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){this.email=v;}
  public String getPayerName(){return payerName;} public void setPayerName(String v){this.payerName=v;}
  public String getTransactionRef(){return transactionRef;} public void setTransactionRef(String v){this.transactionRef=v;}
  public Double getAmount(){return amount;} public void setAmount(Double v){this.amount=v;}
  public String getPlan(){return plan;} public void setPlan(String v){this.plan=v;}
  public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){this.paymentMethod=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
  public LocalDateTime getCreatedAt(){return createdAt;}
}

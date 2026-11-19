package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Booking {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String username;
private String tenantId;
private String orphanName;
private Long orphanId;
private String status;
private Double amount;
private String plan;
private String paymentMethod;
private LocalDateTime createdAt;
private LocalDateTime bookingDate;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getUsername(){ return username; }
public void setUsername(String v){ this.username = v; }
public String getTenantId(){ return tenantId; }
public void setTenantId(String v){ this.tenantId = v; }
public String getOrphanName(){ return orphanName; }
public void setOrphanName(String v){ this.orphanName = v; }
public Long getOrphanId(){ return orphanId; }
public void setOrphanId(Long v){ this.orphanId = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public Double getAmount(){ return amount; }
public void setAmount(Double v){ this.amount = v; }
public void setAmount(Long v){ this.amount = v.doubleValue(); }
public void setAmount(long v){ this.amount = (double) v; }
public String getPlan(){ return plan; }
public void setPlan(String v){ this.plan = v; }
public String getPaymentMethod(){ return paymentMethod; }
public void setPaymentMethod(String v){ this.paymentMethod = v; }
public LocalDateTime getCreatedAt(){ return createdAt; }
public void setCreatedAt(LocalDateTime v){ this.createdAt = v; }
public LocalDateTime getBookingDate(){ return bookingDate; }
public void setBookingDate(LocalDateTime v){ this.bookingDate = v; }
}

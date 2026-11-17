package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Booking {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String tenantId=""; private Long userId; private String status="PENDING"; private String description;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
}

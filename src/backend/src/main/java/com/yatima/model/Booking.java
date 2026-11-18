package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="bookings")
public class Booking {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String username=""; private String phone=""; private String jinaPackage="";
  private String jinaKifurushi=""; private String description=""; private String maelezo="";
  private String status="PENDING"; private String tenantId=""; private LocalDateTime createdAt=LocalDateTime.now();
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
  public String getJinaPackage(){return jinaPackage;} public void setJinaPackage(String v){jinaPackage=v;}
  public String getJinaKifurushi(){return jinaKifurushi;} public void setJinaKifurushi(String v){jinaKifurushi=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public String getMaelezo(){return maelezo;} public void setMaelezo(String v){maelezo=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Tasisi {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String tenantId=""; private String name; private String description; private String location;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public String getLocation(){return location;} public void setLocation(String v){location=v;}
}

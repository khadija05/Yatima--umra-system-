package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Umra {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String tenantId=""; private String title; private String description; private Double price=0.0;
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getTitle(){return title;} public void setTitle(String v){title=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public Double getPrice(){return price;} public void setPrice(Double v){price=v;}
}

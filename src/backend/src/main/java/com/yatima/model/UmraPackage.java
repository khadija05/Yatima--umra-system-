package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class YatimaPackage {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Double price;
  private String description;
  private String duration;
  public Long getId(){ return id; }
  public void setId(Long id){ this.id = id; }
  public String getName(){ return name; }
  public void setName(String n){ this.name = n; }
  public Double getPrice(){ return price; }
  public void setPrice(Double p){ this.price = p; }
  public String getDescription(){ return description; }
  public void setDescription(String d){ this.description = d; }
  public String getDuration(){ return duration; }
  public void setDuration(String d){ this.duration = d; }
}

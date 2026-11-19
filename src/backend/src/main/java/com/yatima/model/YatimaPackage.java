package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class YatimaPackage {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private Double price;
private String description;
private String includes;
private String badge;
private String status;
private String duration;
private String features;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public Double getPrice(){ return price; }
public void setPrice(Double v){ this.price = v; }
public void setPrice(Long v){ this.price = v.doubleValue(); }
public void setPrice(long v){ this.price = (double) v; }
public String getDescription(){ return description; }
public void setDescription(String v){ this.description = v; }
public String getIncludes(){ return includes; }
public void setIncludes(String v){ this.includes = v; }
public String getBadge(){ return badge; }
public void setBadge(String v){ this.badge = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public String getDuration(){ return duration; }
public void setDuration(String v){ this.duration = v; }
public String getFeatures(){ return features; }
public void setFeatures(String v){ this.features = v; }
}

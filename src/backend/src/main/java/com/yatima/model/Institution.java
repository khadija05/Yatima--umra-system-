package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Institution {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String location;
private String phone;
private String description;
private String address;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public String getLocation(){ return location; }
public void setLocation(String v){ this.location = v; }
public String getPhone(){ return phone; }
public void setPhone(String v){ this.phone = v; }
public String getDescription(){ return description; }
public void setDescription(String v){ this.description = v; }
public String getAddress(){ return address; }
public void setAddress(String v){ this.address = v; }
}

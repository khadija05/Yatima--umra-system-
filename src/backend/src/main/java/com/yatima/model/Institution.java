package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Institution {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String location;
private String phone;
private String email;
private String pbzAccount;
private String pbzName;
private String airtelAccount;
private String airtelName;
private String mpesaAccount;
private String mpesaName;
private String tigoAccount;
private String tigoName;
private String halopesaAccount;
private String halopesaName;
private String plan;
private String status;
private String ownerName;
private String ownerPhone;
private String ownerEmail;
private String description;
private String address;
private Boolean approved;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public String getLocation(){ return location; }
public void setLocation(String v){ this.location = v; }
public String getPhone(){ return phone; }
public void setPhone(String v){ this.phone = v; }
public String getEmail(){ return email; }
public void setEmail(String v){ this.email = v; }
public String getPbzAccount(){ return pbzAccount; }
public void setPbzAccount(String v){ this.pbzAccount = v; }
public String getPbzName(){ return pbzName; }
public void setPbzName(String v){ this.pbzName = v; }
public String getAirtelAccount(){ return airtelAccount; }
public void setAirtelAccount(String v){ this.airtelAccount = v; }
public String getAirtelName(){ return airtelName; }
public void setAirtelName(String v){ this.airtelName = v; }
public String getMpesaAccount(){ return mpesaAccount; }
public void setMpesaAccount(String v){ this.mpesaAccount = v; }
public String getMpesaName(){ return mpesaName; }
public void setMpesaName(String v){ this.mpesaName = v; }
public String getTigoAccount(){ return tigoAccount; }
public void setTigoAccount(String v){ this.tigoAccount = v; }
public String getTigoName(){ return tigoName; }
public void setTigoName(String v){ this.tigoName = v; }
public String getHalopesaAccount(){ return halopesaAccount; }
public void setHalopesaAccount(String v){ this.halopesaAccount = v; }
public String getHalopesaName(){ return halopesaName; }
public void setHalopesaName(String v){ this.halopesaName = v; }
public String getPlan(){ return plan; }
public void setPlan(String v){ this.plan = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public String getOwnerName(){ return ownerName; }
public void setOwnerName(String v){ this.ownerName = v; }
public String getOwnerPhone(){ return ownerPhone; }
public void setOwnerPhone(String v){ this.ownerPhone = v; }
public String getOwnerEmail(){ return ownerEmail; }
public void setOwnerEmail(String v){ this.ownerEmail = v; }
public String getDescription(){ return description; }
public void setDescription(String v){ this.description = v; }
public String getAddress(){ return address; }
public void setAddress(String v){ this.address = v; }
public Boolean getApproved(){ return approved; }
public void setApproved(Boolean v){ this.approved = v; }
}

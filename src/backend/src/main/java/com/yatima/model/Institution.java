package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Institution {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name,location,pbzAccount,pbzName,phone,adminPhone,adminName,plan="TRIAL",status="ACTIVE";
  private String mpesaNumber="",mpesaName="",tigoNumber="",tigoName="",airtelNumber="",airtelName="",lipaNamba="";
  private String ownerName="",ownerPhone="",ownerWhatsapp="";
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public String getLocation(){return location;} public void setLocation(String l){this.location=l;}
  public String getPbzAccount(){return pbzAccount;} public void setPbzAccount(String a){this.pbzAccount=a;}
  public String getPbzName(){return pbzName;} public void setPbzName(String n){this.pbzName=n;}
  public String getPhone(){return phone;} public void setPhone(String p){this.phone=p;}
  public String getAdminPhone(){return adminPhone;} public void setAdminPhone(String p){this.adminPhone=p;}
  public String getAdminName(){return adminName;} public void setAdminName(String n){this.adminName=n;}
  public String getPlan(){return plan;} public void setPlan(String p){this.plan=p;}
  public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
  public String getMpesaNumber(){return mpesaNumber;} public void setMpesaNumber(String n){this.mpesaNumber=n;}
  public String getMpesaName(){return mpesaName;} public void setMpesaName(String n){this.mpesaName=n;}
  public String getTigoNumber(){return tigoNumber;} public void setTigoNumber(String n){this.tigoNumber=n;}
  public String getTigoName(){return tigoName;} public void setTigoName(String n){this.tigoName=n;}
  public String getAirtelNumber(){return airtelNumber;} public void setAirtelNumber(String n){this.airtelNumber=n;}
  public String getAirtelName(){return airtelName;} public void setAirtelName(String n){this.airtelName=n;}
  public String getLipaNamba(){return lipaNamba;} public void setLipaNamba(String n){this.lipaNamba=n;}
  public String getOwnerName(){return ownerName;} public void setOwnerName(String n){this.ownerName=n;}
  public String getOwnerPhone(){return ownerPhone;} public void setOwnerPhone(String p){this.ownerPhone=p;}
  public String getOwnerWhatsapp(){return ownerWhatsapp;} public void setOwnerWhatsapp(String w){this.ownerWhatsapp=w;}
}

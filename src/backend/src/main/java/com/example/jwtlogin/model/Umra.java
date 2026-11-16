package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="umra")
public class Umra {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name; private String passportNo; private String phone; private String gender; private String age;
  private String groupName; private String status="PENDING"; private String ownerEmail; private String orgName;
  private LocalDate passportExpiry; private LocalDate createdAt=LocalDate.now();
  public Long getId(){return id;} public String getName(){return name;} public void setName(String v){this.name=v;}
  public String getPassportNo(){return passportNo;} public void setPassportNo(String v){this.passportNo=v;}
  public String getPhone(){return phone;} public void setPhone(String v){this.phone=v;}
  public String getGender(){return gender;} public void setGender(String v){this.gender=v;}
  public String getAge(){return age;} public void setAge(String v){this.age=v;}
  public String getGroupName(){return groupName;} public void setGroupName(String v){this.groupName=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
  public String getOwnerEmail(){return ownerEmail;} public void setOwnerEmail(String v){this.ownerEmail=v;}
  public String getOrgName(){return orgName;} public void setOrgName(String v){this.orgName=v;}
  public LocalDate getPassportExpiry(){return passportExpiry;} public void setPassportExpiry(LocalDate v){this.passportExpiry=v;}
  public LocalDate getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDate v){this.createdAt=v;}
}

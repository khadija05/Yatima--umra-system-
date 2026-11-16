package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="orphans")
public class Orphan {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name; private String age; private String gender; private String status;
  private String school; private String shehia; private String parentName; private String guardianPhone;
  private String ownerEmail; private String orgName; private String photo; private String education;
  private LocalDate createdAt=LocalDate.now();
  public Long getId(){return id;} public String getName(){return name;} public void setName(String v){this.name=v;}
  public String getAge(){return age;} public void setAge(String v){this.age=v;}
  public String getGender(){return gender;} public void setGender(String v){this.gender=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
  public String getSchool(){return school;} public void setSchool(String v){this.school=v;}
  public String getShehia(){return shehia;} public void setShehia(String v){this.shehia=v;}
  public String getParentName(){return parentName;} public void setParentName(String v){this.parentName=v;}
  public String getGuardianPhone(){return guardianPhone;} public void setGuardianPhone(String v){this.guardianPhone=v;}
  public String getOwnerEmail(){return ownerEmail;} public void setOwnerEmail(String v){this.ownerEmail=v;}
  public String getOrgName(){return orgName;} public void setOrgName(String v){this.orgName=v;}
  public String getPhoto(){return photo;} public void setPhoto(String v){this.photo=v;}
  public String getEducation(){return education;} public void setEducation(String v){this.education=v;}
  public LocalDate getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDate v){this.createdAt=v;}
}

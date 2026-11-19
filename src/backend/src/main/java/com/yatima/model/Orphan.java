package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Orphan {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private Long age;
private String gender;
private String institution;
private Double amount;
private Long institutionId;
private String status;
private String guardianName;
private String guardianPhone;
private String photoUrl;
private Long yatimaPackageId;
private String yatimaPackageName;
private Double yatimaPackagePrice;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public Long getAge(){ return age; }
public void setAge(Long v){ this.age = v; }
public void setAge(Double v){ this.age = v.longValue(); }
public String getGender(){ return gender; }
public void setGender(String v){ this.gender = v; }
public String getInstitution(){ return institution; }
public void setInstitution(String v){ this.institution = v; }
public Double getAmount(){ return amount; }
public void setAmount(Double v){ this.amount = v; }
public void setAmount(Long v){ this.amount = v.doubleValue(); }
public Long getInstitutionId(){ return institutionId; }
public void setInstitutionId(Long v){ this.institutionId = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public String getGuardianName(){ return guardianName; }
public void setGuardianName(String v){ this.guardianName = v; }
public String getGuardianPhone(){ return guardianPhone; }
public void setGuardianPhone(String v){ this.guardianPhone = v; }
public String getPhotoUrl(){ return photoUrl; }
public void setPhotoUrl(String v){ this.photoUrl = v; }
public Long getYatimaPackageId(){ return yatimaPackageId; }
public void setYatimaPackageId(Long v){ this.yatimaPackageId = v; }
public String getYatimaPackageName(){ return yatimaPackageName; }
public void setYatimaPackageName(String v){ this.yatimaPackageName = v; }
public Double getYatimaPackagePrice(){ return yatimaPackagePrice; }
public void setYatimaPackagePrice(Double v){ this.yatimaPackagePrice = v; }
}

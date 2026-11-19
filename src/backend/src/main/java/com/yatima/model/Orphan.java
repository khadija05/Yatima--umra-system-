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
private String institutionName;
private String institutionPbzAccount;
private String institutionPbzName;
private Double amount;
private Long institutionId;
private String status;
private String guardianName;
private String guardianPhone;
private String guardianEmail;
private String guardianGender;
private String guardianDob;
private String guardianNationality;
private String guardianIdNumber;
private String guardianAddress;
private String guardianPhotoUrl;
private String guardianOccupation;
private String guardianNotes;
private String photoUrl;
private Long yatimaPackageId;
private String yatimaPackageName;
private Double yatimaPackagePrice;
private String yatimaPackageStatus;
private Double targetAmount;
private Double collectedAmount;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public Long getAge(){ return age; }
public void setAge(Long v){ this.age = v; }
public void setAge(Double v){ this.age = v.longValue(); }
public void setAge(long v){ this.age = v; }
public String getGender(){ return gender; }
public void setGender(String v){ this.gender = v; }
public String getInstitution(){ return institution; }
public void setInstitution(String v){ this.institution = v; }
public String getInstitutionName(){ return institutionName != null ? institutionName : institution; }
public void setInstitutionName(String v){ this.institutionName = v; }
public String getInstitutionPbzAccount(){ return institutionPbzAccount; }
public void setInstitutionPbzAccount(String v){ this.institutionPbzAccount = v; }
public String getInstitutionPbzName(){ return institutionPbzName; }
public void setInstitutionPbzName(String v){ this.institutionPbzName = v; }
public Double getAmount(){ return amount; }
public void setAmount(Double v){ this.amount = v; }
public void setAmount(Long v){ this.amount = v.doubleValue(); }
public void setAmount(long v){ this.amount = (double) v; }
public Long getInstitutionId(){ return institutionId; }
public void setInstitutionId(Long v){ this.institutionId = v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public String getGuardianName(){ return guardianName; }
public void setGuardianName(String v){ this.guardianName = v; }
public String getGuardianPhone(){ return guardianPhone; }
public void setGuardianPhone(String v){ this.guardianPhone = v; }
public String getGuardianEmail(){ return guardianEmail; }
public void setGuardianEmail(String v){ this.guardianEmail = v; }
public String getGuardianGender(){ return guardianGender; }
public void setGuardianGender(String v){ this.guardianGender = v; }
public String getGuardianDob(){ return guardianDob; }
public void setGuardianDob(String v){ this.guardianDob = v; }
public String getGuardianNationality(){ return guardianNationality; }
public void setGuardianNationality(String v){ this.guardianNationality = v; }
public String getGuardianIdNumber(){ return guardianIdNumber; }
public void setGuardianIdNumber(String v){ this.guardianIdNumber = v; }
public String getGuardianAddress(){ return guardianAddress; }
public void setGuardianAddress(String v){ this.guardianAddress = v; }
public String getGuardianPhotoUrl(){ return guardianPhotoUrl; }
public void setGuardianPhotoUrl(String v){ this.guardianPhotoUrl = v; }
public String getGuardianOccupation(){ return guardianOccupation; }
public void setGuardianOccupation(String v){ this.guardianOccupation = v; }
public String getGuardianNotes(){ return guardianNotes; }
public void setGuardianNotes(String v){ this.guardianNotes = v; }
public String getPhotoUrl(){ return photoUrl; }
public void setPhotoUrl(String v){ this.photoUrl = v; }
public Long getYatimaPackageId(){ return yatimaPackageId; }
public void setYatimaPackageId(Long v){ this.yatimaPackageId = v; }
public String getYatimaPackageName(){ return yatimaPackageName; }
public void setYatimaPackageName(String v){ this.yatimaPackageName = v; }
public Double getYatimaPackagePrice(){ return yatimaPackagePrice; }
public void setYatimaPackagePrice(Double v){ this.yatimaPackagePrice = v; }
public void setYatimaPackagePrice(Long v){ this.yatimaPackagePrice = v.doubleValue(); }
public void setYatimaPackagePrice(long v){ this.yatimaPackagePrice = (double) v; }
public String getYatimaPackageStatus(){ return yatimaPackageStatus; }
public void setYatimaPackageStatus(String v){ this.yatimaPackageStatus = v; }
public Double getTargetAmount(){ return targetAmount; }
public void setTargetAmount(Double v){ this.targetAmount = v; }
public void setTargetAmount(Long v){ this.targetAmount = v.doubleValue(); }
public void setTargetAmount(long v){ this.targetAmount = (double) v; }
public Double getCollectedAmount(){ return collectedAmount; }
public void setCollectedAmount(Double v){ this.collectedAmount = v; }
public void setCollectedAmount(Long v){ this.collectedAmount = v.doubleValue(); }
public void setCollectedAmount(long v){ this.collectedAmount = (double) v; }
}

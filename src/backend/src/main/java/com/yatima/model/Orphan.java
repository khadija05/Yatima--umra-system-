package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Orphan {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name,gender,location,story,status,photoUrl,birthCertificate,school,healthStatus;
  private String dateOfBirth, nationality="Tanzania", orphanIdNumber;
  private int age; private Long collectedAmount=0L,targetAmount=4500000L; private Long institutionId;
  private String institutionName,institutionPbzAccount,institutionPbzName;
  private String guardianName,guardianPhone,guardianRelation,guardianIdNumber,guardianAddress,guardianPhotoUrl,guardianOccupation,guardianNotes;
  private String guardianEmail,guardianGender,guardianDob,guardianNationality="Tanzania",guardianWhatsapp,guardianWorkplace,guardianIncome;
  private String guardianEmergencyName,guardianEmergencyPhone;
  private String yatimaPackageName=""; private Long yatimaPackageId; private Long yatimaPackagePrice=0L; private String yatimaPackageStatus="HAJACHAGULIWA";
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public int getAge(){return age;} public void setAge(int a){this.age=a;}
  public String getGender(){return gender;} public void setGender(String g){this.gender=g;}
  public String getLocation(){return location;} public void setLocation(String l){this.location=l;}
  public String getStory(){return story;} public void setStory(String s){this.story=s;}
  public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
  public Long getCollectedAmount(){return collectedAmount;} public void setCollectedAmount(Long c){this.collectedAmount=c;}
  public Long getTargetAmount(){return targetAmount;} public void setTargetAmount(Long t){this.targetAmount=t;}
  public String getPhotoUrl(){return photoUrl;} public void setPhotoUrl(String p){this.photoUrl=p;}
  public String getBirthCertificate(){return birthCertificate;} public void setBirthCertificate(String b){this.birthCertificate=b;}
  public String getSchool(){return school;} public void setSchool(String s){this.school=s;}
  public String getHealthStatus(){return healthStatus;} public void setHealthStatus(String h){this.healthStatus=h;}
  public String getDateOfBirth(){return dateOfBirth;} public void setDateOfBirth(String d){this.dateOfBirth=d;}
  public String getNationality(){return nationality;} public void setNationality(String n){this.nationality=n;}
  public String getOrphanIdNumber(){return orphanIdNumber;} public void setOrphanIdNumber(String n){this.orphanIdNumber=n;}
  public String getInstitutionName(){return institutionName;} public void setInstitutionName(String n){this.institutionName=n;}
  public Long getInstitutionId(){return institutionId;} public void setInstitutionId(Long id){this.institutionId=id;}
  public String getInstitutionPbzAccount(){return institutionPbzAccount;} public void setInstitutionPbzAccount(String a){this.institutionPbzAccount=a;}
  public String getInstitutionPbzName(){return institutionPbzName;} public void setInstitutionPbzName(String n){this.institutionPbzName=n;}
  public String getGuardianName(){return guardianName;} public void setGuardianName(String n){this.guardianName=n;}
  public String getGuardianPhone(){return guardianPhone;} public void setGuardianPhone(String p){this.guardianPhone=p;}
  public String getGuardianRelation(){return guardianRelation;} public void setGuardianRelation(String r){this.guardianRelation=r;}
  public String getGuardianIdNumber(){return guardianIdNumber;} public void setGuardianIdNumber(String n){this.guardianIdNumber=n;}
  public String getGuardianAddress(){return guardianAddress;} public void setGuardianAddress(String a){this.guardianAddress=a;}
  public String getGuardianPhotoUrl(){return guardianPhotoUrl;} public void setGuardianPhotoUrl(String p){this.guardianPhotoUrl=p;}
  public String getGuardianOccupation(){return guardianOccupation;} public void setGuardianOccupation(String o){this.guardianOccupation=o;}
  public String getGuardianNotes(){return guardianNotes;} public void setGuardianNotes(String n){this.guardianNotes=n;}
  public String getGuardianEmail(){return guardianEmail;} public void setGuardianEmail(String e){this.guardianEmail=e;}
  public String getGuardianGender(){return guardianGender;} public void setGuardianGender(String g){this.guardianGender=g;}
  public String getGuardianDob(){return guardianDob;} public void setGuardianDob(String d){this.guardianDob=d;}
  public String getGuardianNationality(){return guardianNationality;} public void setGuardianNationality(String n){this.guardianNationality=n;}
  public String getGuardianWhatsapp(){return guardianWhatsapp;} public void setGuardianWhatsapp(String w){this.guardianWhatsapp=w;}
  public String getGuardianWorkplace(){return guardianWorkplace;} public void setGuardianWorkplace(String w){this.guardianWorkplace=w;}
  public String getGuardianIncome(){return guardianIncome;} public void setGuardianIncome(String i){this.guardianIncome=i;}
  public String getGuardianEmergencyName(){return guardianEmergencyName;} public void setGuardianEmergencyName(String n){this.guardianEmergencyName=n;}
  public String getGuardianEmergencyPhone(){return guardianEmergencyPhone;} public void setGuardianEmergencyPhone(String p){this.guardianEmergencyPhone=p;}
  public String getYatimaPackageName(){return yatimaPackageName;} public void setYatimaPackageName(String n){this.yatimaPackageName=n;}
  public Long getYatimaPackageId(){return yatimaPackageId;} public void setYatimaPackageId(Long id){this.yatimaPackageId=id;}
  public Long getYatimaPackagePrice(){return yatimaPackagePrice;} public void setYatimaPackagePrice(Long p){this.yatimaPackagePrice=p;}
  public String getYatimaPackageStatus(){return yatimaPackageStatus;} public void setYatimaPackageStatus(String s){this.yatimaPackageStatus=s;}
}

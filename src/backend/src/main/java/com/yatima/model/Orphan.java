package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class Orphan {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String tenantId=""; private String name; private Integer age; private String gender="Mtoto"; private String location="Zanzibar";
  private String story=""; private String dream=""; private String needs="Elimu, Chakula, Afya";
  private String ownerUsername=""; private boolean featured=true; private String status="Anahitaji Msaada";
  private Integer progress=50; private Double remaining=2250000.0;
  @Column(length=5000000) private String photoBase64=""; private String photoName="";
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public Integer getAge(){return age;} public void setAge(Integer v){age=v;}
  public String getGender(){return gender;} public void setGender(String v){gender=v;}
  public String getLocation(){return location;} public void setLocation(String v){location=v;}
  public String getStory(){return story;} public void setStory(String v){story=v;}
  public String getDream(){return dream;} public void setDream(String v){dream=v;}
  public String getNeeds(){return needs;} public void setNeeds(String v){needs=v;}
  public String getOwnerUsername(){return ownerUsername;} public void setOwnerUsername(String v){ownerUsername=v;}
  public boolean isFeatured(){return featured;} public void setFeatured(boolean v){featured=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public Integer getProgress(){return progress;} public void setProgress(Integer v){progress=v;}
  public Double getRemaining(){return remaining;} public void setRemaining(Double v){remaining=v;}
  public String getPhotoBase64(){return photoBase64;} public void setPhotoBase64(String v){photoBase64=v;}
  public String getPhotoName(){return photoName;} public void setPhotoName(String v){photoName=v;}
}

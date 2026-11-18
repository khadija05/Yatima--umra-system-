package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="orphans")
public class Orphan {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name=""; private Integer age=7; private String gender="Msichana";
  private String location="Zanzibar"; private String story=""; private String dream="";
  private String needs="Umra, Elimu, Chakula"; private String status="Anahitaji Msaada";
  @Column(length=1000000) private String photoBase64=""; private String photoUrl="";
  private Boolean featured=true; private String tenantId=""; private Double target=4500000.0;
  private Double received=0.0; private Integer progress=0; private Double remaining=4500000.0;
  private LocalDateTime createdAt=LocalDateTime.now();
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public Integer getAge(){return age;} public void setAge(Integer v){age=v;}
  public String getGender(){return gender;} public void setGender(String v){gender=v;}
  public String getLocation(){return location;} public void setLocation(String v){location=v;}
  public String getStory(){return story;} public void setStory(String v){story=v;}
  public String getDream(){return dream;} public void setDream(String v){dream=v;}
  public String getNeeds(){return needs;} public void setNeeds(String v){needs=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getPhotoBase64(){return photoBase64;} public void setPhotoBase64(String v){photoBase64=v;}
  public String getPhotoUrl(){return photoUrl;} public void setPhotoUrl(String v){photoUrl=v;}
  public Boolean getFeatured(){return featured;} public void setFeatured(Boolean v){featured=v;}
  public String getTenantId(){return tenantId;} public void setTenantId(String v){tenantId=v;}
  public Double getTarget(){return target;} public void setTarget(Double v){target=v;}
  public Double getReceived(){return received;} public void setReceived(Double v){received=v;}
  public Integer getProgress(){return progress;} public void setProgress(Integer v){progress=v;}
  public Double getRemaining(){return remaining;} public void setRemaining(Double v){remaining=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
  public void recalc(){ if(target==null||target==0) target=4500000.0; if(received==null) received=0.0; progress=(int)Math.min(100, Math.round(received/target*100)); remaining=Math.max(0, target-received); }
}

package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="orphans")
public class Orphan {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String jinaKamili; private Integer umri; private String jinsia; private String mkoa; private String wilaya; private String shule; private String darasa;
  private String hali; private String mahitaji; private String mzaziMlezi; private String simuMzazi;
  private String ownerUsername; private String orgName; private LocalDateTime createdAt=LocalDateTime.now();
  public Orphan(){}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getJinaKamili(){return jinaKamili;} public void setJinaKamili(String v){jinaKamili=v;}
  public Integer getUmri(){return umri;} public void setUmri(Integer v){umri=v;}
  public String getJinsia(){return jinsia;} public void setJinsia(String v){jinsia=v;}
  public String getMkoa(){return mkoa;} public void setMkoa(String v){mkoa=v;}
  public String getWilaya(){return wilaya;} public void setWilaya(String v){wilaya=v;}
  public String getShule(){return shule;} public void setShule(String v){shule=v;}
  public String getDarasa(){return darasa;} public void setDarasa(String v){darasa=v;}
  public String getHali(){return hali;} public void setHali(String v){hali=v;}
  public String getMahitaji(){return mahitaji;} public void setMahitaji(String v){mahitaji=v;}
  public String getMzaziMlezi(){return mzaziMlezi;} public void setMzaziMlezi(String v){mzaziMlezi=v;}
  public String getSimuMzazi(){return simuMzazi;} public void setSimuMzazi(String v){simuMzazi=v;}
  public String getOwnerUsername(){return ownerUsername;} public void setOwnerUsername(String v){ownerUsername=v;}
  public String getOrgName(){return orgName;} public void setOrgName(String v){orgName=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

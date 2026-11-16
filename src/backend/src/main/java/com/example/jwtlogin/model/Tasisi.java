package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="tasisi")
public class Tasisi {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String jinaTasisi; private String aina; private String mkoa; private String wilaya; private String simu; private String email; private String mkurugenzi;
  private Integer idadiWatu; private Integer wafanyakazi; private String huduma; private String mahitaji; private Double bajeti; private Double wastaniRating=0.0; private Integer idadiWaliotathmini=0;
  private Boolean imehakikiwa=false; private String ownerUsername; private String orgName; private LocalDateTime createdAt=LocalDateTime.now();
  public Tasisi(){}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getJinaTasisi(){return jinaTasisi;} public void setJinaTasisi(String v){jinaTasisi=v;}
  public String getAina(){return aina;} public void setAina(String v){aina=v;}
  public String getMkoa(){return mkoa;} public void setMkoa(String v){mkoa=v;}
  public String getWilaya(){return wilaya;} public void setWilaya(String v){wilaya=v;}
  public String getSimu(){return simu;} public void setSimu(String v){simu=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getMkurugenzi(){return mkurugenzi;} public void setMkurugenzi(String v){mkurugenzi=v;}
  public Integer getIdadiWatu(){return idadiWatu;} public void setIdadiWatu(Integer v){idadiWatu=v;}
  public Integer getWafanyakazi(){return wafanyakazi;} public void setWafanyakazi(Integer v){wafanyakazi=v;}
  public String getHuduma(){return huduma;} public void setHuduma(String v){huduma=v;}
  public String getMahitaji(){return mahitaji;} public void setMahitaji(String v){mahitaji=v;}
  public Double getBajeti(){return bajeti;} public void setBajeti(Double v){bajeti=v;}
  public Double getWastaniRating(){return wastaniRating;} public void setWastaniRating(Double v){wastaniRating=v;}
  public Integer getIdadiWaliotathmini(){return idadiWaliotathmini;} public void setIdadiWaliotathmini(Integer v){idadiWaliotathmini=v;}
  public Boolean getImehakikiwa(){return imehakikiwa;} public void setImehakikiwa(Boolean v){imehakikiwa=v;}
  public String getOwnerUsername(){return ownerUsername;} public void setOwnerUsername(String v){ownerUsername=v;}
  public String getOrgName(){return orgName;} public void setOrgName(String v){orgName=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

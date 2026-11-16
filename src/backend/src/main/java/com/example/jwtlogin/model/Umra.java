package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="umra")
public class Umra {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String jinaTaasisi; private String leseniUmra; private String mkoa; private String wilaya; private String simu; private String email; private String mkurugenzi;
  private String jinaKifurushi; private Double bei; private Integer siku; private String tareheKuondoka; private String tareheKurudi; private String ndege; private String hoteliMakkah; private String hoteliMadinah; private String ainaChumba; private String chakula; private String visa; private String hudumaZilizopo;
  private Integer idadiWatu; private Integer nafasiZilizobaki; private Integer uwezoKuchukuaWatu; private Double wastaniRating=0.0; private Integer idadiWaliotathmini=0;
  private String akauntiBenki; private String jinaBenki; private Boolean imehakikiwa=false; private String ownerUsername; private String orgName; private LocalDateTime createdAt=LocalDateTime.now();
  public Umra(){}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getJinaTaasisi(){return jinaTaasisi;} public void setJinaTaasisi(String v){jinaTaasisi=v;}
  public String getLeseniUmra(){return leseniUmra;} public void setLeseniUmra(String v){leseniUmra=v;}
  public String getMkoa(){return mkoa;} public void setMkoa(String v){mkoa=v;}
  public String getWilaya(){return wilaya;} public void setWilaya(String v){wilaya=v;}
  public String getSimu(){return simu;} public void setSimu(String v){simu=v;}
  public String getEmail(){return email;} public void setEmail(String v){email=v;}
  public String getMkurugenzi(){return mkurugenzi;} public void setMkurugenzi(String v){mkurugenzi=v;}
  public String getJinaKifurushi(){return jinaKifurushi;} public void setJinaKifurushi(String v){jinaKifurushi=v;}
  public Double getBei(){return bei;} public void setBei(Double v){bei=v;}
  public Integer getSiku(){return siku;} public void setSiku(Integer v){siku=v;}
  public String getTareheKuondoka(){return tareheKuondoka;} public void setTareheKuondoka(String v){tareheKuondoka=v;}
  public String getTareheKurudi(){return tareheKurudi;} public void setTareheKurudi(String v){tareheKurudi=v;}
  public String getNdege(){return ndege;} public void setNdege(String v){ndege=v;}
  public String getHoteliMakkah(){return hoteliMakkah;} public void setHoteliMakkah(String v){hoteliMakkah=v;}
  public String getHoteliMadinah(){return hoteliMadinah;} public void setHoteliMadinah(String v){hoteliMadinah=v;}
  public String getAinaChumba(){return ainaChumba;} public void setAinaChumba(String v){ainaChumba=v;}
  public String getChakula(){return chakula;} public void setChakula(String v){chakula=v;}
  public String getVisa(){return visa;} public void setVisa(String v){visa=v;}
  public String getHudumaZilizopo(){return hudumaZilizopo;} public void setHudumaZilizopo(String v){hudumaZilizopo=v;}
  public Integer getIdadiWatu(){return idadiWatu;} public void setIdadiWatu(Integer v){idadiWatu=v;}
  public Integer getNafasiZilizobaki(){return nafasiZilizobaki;} public void setNafasiZilizobaki(Integer v){nafasiZilizobaki=v;}
  public Integer getUwezoKuchukuaWatu(){return uwezoKuchukuaWatu;} public void setUwezoKuchukuaWatu(Integer v){uwezoKuchukuaWatu=v;}
  public Double getWastaniRating(){return wastaniRating;} public void setWastaniRating(Double v){wastaniRating=v;}
  public Integer getIdadiWaliotathmini(){return idadiWaliotathmini;} public void setIdadiWaliotathmini(Integer v){idadiWaliotathmini=v;}
  public String getAkauntiBenki(){return akauntiBenki;} public void setAkauntiBenki(String v){akauntiBenki=v;}
  public String getJinaBenki(){return jinaBenki;} public void setJinaBenki(String v){jinaBenki=v;}
  public Boolean getImehakikiwa(){return imehakikiwa;} public void setImehakikiwa(Boolean v){imehakikiwa=v;}
  public String getOwnerUsername(){return ownerUsername;} public void setOwnerUsername(String v){ownerUsername=v;}
  public String getOrgName(){return orgName;} public void setOrgName(String v){orgName=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

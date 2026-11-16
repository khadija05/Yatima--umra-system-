package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="packages")
public class PackageKukodisha {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String jinaPackage; private String aina; private String maelezo; private Double bei; private String muda; private Integer idadiSiku; private String hudumaZilizomo; private String hoteli; private String ainaChumba; private String chakula; private String ndege; private Integer uwezoWatu; private Integer nafasiZilizobaki; private Boolean inapatikana=true; private Long tasisiId; private String jinaTasisi; private String ownerUsername; private LocalDateTime createdAt=LocalDateTime.now();
  public PackageKukodisha(){}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getJinaPackage(){return jinaPackage;} public void setJinaPackage(String v){jinaPackage=v;}
  public String getAina(){return aina;} public void setAina(String v){aina=v;}
  public String getMaelezo(){return maelezo;} public void setMaelezo(String v){maelezo=v;}
  public Double getBei(){return bei;} public void setBei(Double v){bei=v;}
  public String getMuda(){return muda;} public void setMuda(String v){muda=v;}
  public Integer getIdadiSiku(){return idadiSiku;} public void setIdadiSiku(Integer v){idadiSiku=v;}
  public String getHudumaZilizomo(){return hudumaZilizomo;} public void setHudumaZilizomo(String v){hudumaZilizomo=v;}
  public String getHoteli(){return hoteli;} public void setHoteli(String v){hoteli=v;}
  public String getAinaChumba(){return ainaChumba;} public void setAinaChumba(String v){ainaChumba=v;}
  public String getChakula(){return chakula;} public void setChakula(String v){chakula=v;}
  public String getNdege(){return ndege;} public void setNdege(String v){ndege=v;}
  public Integer getUwezoWatu(){return uwezoWatu;} public void setUwezoWatu(Integer v){uwezoWatu=v;}
  public Integer getNafasiZilizobaki(){return nafasiZilizobaki;} public void setNafasiZilizobaki(Integer v){nafasiZilizobaki=v;}
  public Boolean getInapatikana(){return inapatikana;} public void setInapatikana(Boolean v){inapatikana=v;}
  public Long getTasisiId(){return tasisiId;} public void setTasisiId(Long v){tasisiId=v;}
  public String getJinaTasisi(){return jinaTasisi;} public void setJinaTasisi(String v){jinaTasisi=v;}
  public String getOwnerUsername(){return ownerUsername;} public void setOwnerUsername(String v){ownerUsername=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

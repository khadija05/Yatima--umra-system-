package com.yatima.model;
import jakarta.persistence.*;
@Entity @Table(name="packages")
public class Package {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(unique=true) private String code=""; private String name=""; private String displayName="";
  private Double price=0.0; private Double bei=0.0; private Integer days=30;
  private String description=""; private String maelezo=""; private Integer maxOrphans=10;
  private Integer maxUmra=5; private Integer maxTasisi=1; private Integer maxBookings=9999;
  private Integer orderNo=1; private Boolean isPopular=false; private Boolean active=true;
  private String status="ACTIVE"; private String currency="TZS"; private String pbzAccount="0887387001"; private String pbzName="KHADIJA DENGE";
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getCode(){return code;} public void setCode(String v){code=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public String getDisplayName(){return displayName;} public void setDisplayName(String v){displayName=v;}
  public Double getPrice(){return price;} public void setPrice(Double v){price=v;}
  public Double getBei(){return bei;} public void setBei(Double v){bei=v;}
  public Integer getDays(){return days;} public void setDays(Integer v){days=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public String getMaelezo(){return maelezo;} public void setMaelezo(String v){maelezo=v;}
  public Integer getMaxOrphans(){return maxOrphans;} public void setMaxOrphans(Integer v){maxOrphans=v;}
  public Integer getMaxUmra(){return maxUmra;} public void setMaxUmra(Integer v){maxUmra=v;}
  public Integer getMaxTasisi(){return maxTasisi;} public void setMaxTasisi(Integer v){maxTasisi=v;}
  public Integer getMaxBookings(){return maxBookings;} public void setMaxBookings(Integer v){maxBookings=v;}
  public Integer getOrderNo(){return orderNo;} public void setOrderNo(Integer v){orderNo=v;}
  public Boolean getIsPopular(){return isPopular;} public void setIsPopular(Boolean v){isPopular=v;}
  public Boolean getActive(){return active;} public void setActive(Boolean v){active=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public String getCurrency(){return currency;} public void setCurrency(String v){currency=v;}
  public String getPbzAccount(){return pbzAccount;} public void setPbzAccount(String v){pbzAccount=v;}
  public String getPbzName(){return pbzName;} public void setPbzName(String v){pbzName=v;}
}

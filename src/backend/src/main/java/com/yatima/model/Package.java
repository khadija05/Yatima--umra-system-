package com.yatima.model;
import jakarta.persistence.*;
@Entity @Table(name="packages")
public class Package {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String code="KIDOGO-50000"; private String name="KIDOGO"; private Double price=50000.0; private Integer days=30;
  @Column(length=1000) private String description="Kwa kuanza. Yatima hadi 5, Ripoti ya mwezi, Msaada WhatsApp, Cheti.";
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getCode(){return code;} public void setCode(String v){code=v;}
  public String getName(){return name;} public void setName(String v){name=v;}
  public Double getPrice(){return price;} public void setPrice(Double v){price=v;}
  public Integer getDays(){return days;} public void setDays(Integer v){days=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
}

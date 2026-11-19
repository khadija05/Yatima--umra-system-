package com.yatima.model;
import jakarta.persistence.*;
@Entity public class UmraPackage {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name,description,badge; private Long price; private int collectedPercent;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
  public String getBadge(){return badge;} public void setBadge(String b){this.badge=b;}
  public Long getPrice(){return price;} public void setPrice(Long p){this.price=p;}
  public int getCollectedPercent(){return collectedPercent;} public void setCollectedPercent(int p){this.collectedPercent=p;}
}

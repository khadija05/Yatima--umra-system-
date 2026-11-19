YatimaPackage.java - Package za Yatima TU
package com.yatima.model; import jakarta.persistence.*;
@Entity public class YatimaPackage {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String name,description,badge,includes,duration; private Long price; private Boolean active=true;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
  public Long getPrice(){return price;} public void setPrice(Long p){this.price=p;}
  public String getBadge(){return badge;} public void setBadge(String b){this.badge=b;}
  public String getIncludes(){return includes;} public void setIncludes(String i){this.includes=i;}
  public String getDuration(){return duration;} public void setDuration(String d){this.duration=d;}
  public Boolean getActive(){return active;} public void setActive(Boolean a){this.active=a;}
}


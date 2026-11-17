package com.yatima.model;
import jakarta.persistence.*;
@Entity
public class YatimaPackage{ @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private String jinaPackage; private Double bei;
public Long getId(){return id;} public void setId(Long id){this.id=id;}
public String getJinaPackage(){return jinaPackage;} public void setJinaPackage(String v){this.jinaPackage=v;}
public Double getBei(){return bei;} public void setBei(Double v){this.bei=v;} }

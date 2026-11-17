package com.yatima.model;
import jakarta.persistence.*;
@Entity public class Package {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public String jinaPackage; public String aina; public Double bei; public String maelezo;
}

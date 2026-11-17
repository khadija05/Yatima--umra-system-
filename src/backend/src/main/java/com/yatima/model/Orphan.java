package com.yatima.model;
import jakarta.persistence.*;
@Entity public class Orphan {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public String jinaKamili; public Integer umri; public String jinsia;
  public String mkoa; public String wilaya; public String shule; public String mahitaji;
  public Long ownerId; public String ownerUsername;
}

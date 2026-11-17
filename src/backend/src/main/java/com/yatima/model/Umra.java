package com.yatima.model;
import jakarta.persistence.*;
@Entity public class Umra {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public String jinaTaasisi; public String jinaKifurushi; public Double bei;
  public Integer siku; public String ndege; public String hoteliMakkah; public String hoteliMadinah;
  public String ainaChumba; public Integer idadiWatu=0; public Integer uwezoKuchukuaWatu=40;
  public Double wastaniRating=0.0; public Long ownerId;
}

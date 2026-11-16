package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="subscriptions")
public class Subscription {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String username; private Long tasisiId; private Double kiasi; private Integer miezi; private Double jumla; private String status="ACTIVE"; private LocalDateTime createdAt=LocalDateTime.now();
  public Subscription(){}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getUsername(){return username;} public void setUsername(String v){username=v;}
  public Long getTasisiId(){return tasisiId;} public void setTasisiId(Long v){tasisiId=v;}
  public Double getKiasi(){return kiasi;} public void setKiasi(Double v){kiasi=v;}
  public Integer getMiezi(){return miezi;} public void setMiezi(Integer v){miezi=v;}
  public Double getJumla(){return jumla;} public void setJumla(Double v){jumla=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

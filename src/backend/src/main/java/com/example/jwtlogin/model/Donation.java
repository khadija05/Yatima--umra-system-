package com.example.jwtlogin.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="donations")
public class Donation {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String jinaMthamini; private String simu; private String ainaMsaada; private Double kiasi; private String maelezo; private Long tasisiId; private String status="PENDING"; private LocalDateTime createdAt=LocalDateTime.now();
  public Donation(){}
  public Long getId(){return id;} public void setId(Long v){id=v;}
  public String getJinaMthamini(){return jinaMthamini;} public void setJinaMthamini(String v){jinaMthamini=v;}
  public String getSimu(){return simu;} public void setSimu(String v){simu=v;}
  public String getAinaMsaada(){return ainaMsaada;} public void setAinaMsaada(String v){ainaMsaada=v;}
  public Double getKiasi(){return kiasi;} public void setKiasi(Double v){kiasi=v;}
  public String getMaelezo(){return maelezo;} public void setMaelezo(String v){maelezo=v;}
  public Long getTasisiId(){return tasisiId;} public void setTasisiId(Long v){tasisiId=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}

package com.yatima.model;
import jakarta.persistence.*;
@Entity public class Donation {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public String jinaMthamini; public String ainaMsaada; public Double kiasi; public Long tasisiId;
}

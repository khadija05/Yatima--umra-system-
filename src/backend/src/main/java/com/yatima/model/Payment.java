package com.yatima.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public Long userId; public String plan; public Double kiasi; public String muamala; public LocalDateTime createdAt=LocalDateTime.now();
}

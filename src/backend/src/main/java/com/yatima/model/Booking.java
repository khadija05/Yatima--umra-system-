package com.yatima.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity public class Booking {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public Long userId; public String username; public Long packageId; public String jinaPackage;
  public Double bei; public String status="PENDING"; public LocalDate tareheMwisho;
  public Boolean imelipa=false; public Integer miezi=1;
}

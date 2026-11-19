
package com.yatima.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
public class Umra {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String fullName;
private String title;
private String description;
private Double price;
private Double amount;
private String status;
private String phone;
private String email;
private String passportNumber;
private String packageName;
private String departureDate;
private String returnDate;
private LocalDate travelDate;
private String notes;
public Long getId(){ return id; }
public void setId(Long id){ this.id = id; }
public String getName(){ return name; }
public void setName(String v){ this.name = v; }
public String getFullName(){ return fullName; }
public void setFullName(String v){ this.fullName = v; }
public String getTitle(){ return title; }
public void setTitle(String v){ this.title = v; }
public String getDescription(){ return description; }
public void setDescription(String v){ this.description = v; }
public Double getPrice(){ return price; }
public void setPrice(Double v){ this.price = v; }
public void setPrice(Long v){ this.price = v.doubleValue(); }
public void setPrice(long v){ this.price = (double) v; }
public Double getAmount(){ return amount; }
public void setAmount(Double v){ this.amount = v; }
public void setAmount(Long v){ this.amount = v.doubleValue(); }
public void setAmount(long v){ this.amount = (double) v; }
public String getStatus(){ return status; }
public void setStatus(String v){ this.status = v; }
public String getPhone(){ return phone; }
public void setPhone(String v){ this.phone = v; }
public String getEmail(){ return email; }
public void setEmail(String v){ this.email = v; }
public String getPassportNumber(){ return passportNumber; }
public void setPassportNumber(String v){ this.passportNumber = v; }
public String getPackageName(){ return packageName; }
public void setPackageName(String v){ this.packageName = v; }
public String getDepartureDate(){ return departureDate; }
public void setDepartureDate(String v){ this.departureDate = v; }
public String getReturnDate(){ return returnDate; }
public void setReturnDate(String v){ this.returnDate = v; }
public LocalDate getTravelDate(){ return travelDate; }
public void setTravelDate(LocalDate v){ this.travelDate = v; }
public String getNotes(){ return notes; }
public void setNotes(String v){ this.notes = v; }
}

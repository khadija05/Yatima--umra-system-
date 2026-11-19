
// Booking.java
package com.yatima.model; import jakarta.persistence.*;
@Entity public class Booking { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; private String fullName,phone,packageName,notes,status="PENDING";
public Long getId(){return id;} public void setId(Long id){this.id=id;} public String getFullName(){return fullName;} public void setFullName(String n){this.fullName=n;} public String getPhone(){return phone;} public void setPhone(String p){this.phone=p;} public String getPackageName(){return packageName;} public void setPackageName(String p){this.packageName=p;} public String getNotes(){return notes;} public void setNotes(String n){this.notes=n;} public String getStatus(){return status;} public void setStatus(String s){this.status=s;} }


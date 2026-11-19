package com.yatima.model;
import jakarta.persistence.*;
@Entity public class Donation {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String donorName,donorPhone,orphanName,orphanPhotoUrl;
  private Long orphanId,amount;
  private String paymentMethod="PBZ",paymentStatus="PENDING",status="PENDING";
  private String transactionId,paymentPhone,paymentProofUrl,paymentDate,receiptNumber,notes;
  private String institutionName,institutionPbzAccount,institutionPbzName,institutionMpesa,institutionTigo,institutionAirtel;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getDonorName(){return donorName;} public void setDonorName(String n){this.donorName=n;}
  public String getDonorPhone(){return donorPhone;} public void setDonorPhone(String p){this.donorPhone=p;}
  public Long getOrphanId(){return orphanId;} public void setOrphanId(Long id){this.orphanId=id;}
  public String getOrphanName(){return orphanName;} public void setOrphanName(String n){this.orphanName=n;}
  public String getOrphanPhotoUrl(){return orphanPhotoUrl;} public void setOrphanPhotoUrl(String p){this.orphanPhotoUrl=p;}
  public Long getAmount(){return amount;} public void setAmount(Long a){this.amount=a;}
  public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String m){this.paymentMethod=m;}
  public String getPaymentStatus(){return paymentStatus;} public void setPaymentStatus(String s){this.paymentStatus=s;}
  public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
  public String getTransactionId(){return transactionId;} public void setTransactionId(String t){this.transactionId=t;}
  public String getPaymentPhone(){return paymentPhone;} public void setPaymentPhone(String p){this.paymentPhone=p;}
  public String getPaymentProofUrl(){return paymentProofUrl;} public void setPaymentProofUrl(String p){this.paymentProofUrl=p;}
  public String getPaymentDate(){return paymentDate;} public void setPaymentDate(String d){this.paymentDate=d;}
  public String getReceiptNumber(){return receiptNumber;} public void setReceiptNumber(String r){this.receiptNumber=r;}
  public String getNotes(){return notes;} public void setNotes(String n){this.notes=n;}
  public String getInstitutionName(){return institutionName;} public void setInstitutionName(String n){this.institutionName=n;}
  public String getInstitutionPbzAccount(){return institutionPbzAccount;} public void setInstitutionPbzAccount(String a){this.institutionPbzAccount=a;}
  public String getInstitutionPbzName(){return institutionPbzName;} public void setInstitutionPbzName(String n){this.institutionPbzName=n;}
  public String getInstitutionMpesa(){return institutionMpesa;} public void setInstitutionMpesa(String m){this.institutionMpesa=m;}
  public String getInstitutionTigo(){return institutionTigo;} public void setInstitutionTigo(String t){this.institutionTigo=t;}
  public String getInstitutionAirtel(){return institutionAirtel;} public void setInstitutionAirtel(String a){this.institutionAirtel=a;}
}

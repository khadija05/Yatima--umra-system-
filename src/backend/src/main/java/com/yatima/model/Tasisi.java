package com.yatima.model;
import jakarta.persistence.*;
@Entity public class Tasisi {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
  public String jinaTasisi; public String mkoa; public String wilaya; public String simu;
  public Integer idadiWatu; public String mahitaji; public Double bajeti; public String aina="MAYATIMA";
  public Double wastaniRating=0.0; public Integer jumlaRating=0; public Integer idadiRating=0;
  public Long ownerId;
}

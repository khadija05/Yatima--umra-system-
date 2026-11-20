package com.yatima.controller;
import com.yatima.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api") @CrossOrigin("*")
public class StatsController {
  private final OrphanRepository orphans;
  private final DonationRepository donations;
  private final SponsorshipRepository sponsorships;
  private final BookingRepository bookings;
  public StatsController(OrphanRepository o, DonationRepository d, SponsorshipRepository s, BookingRepository b){
    this.orphans=o; this.donations=d; this.sponsorships=s; this.bookings=b;
  }
  @GetMapping("/stats")
  public Map<String,Object> stats(){
    long totalDon = 0, totalSpon = 0;
    try{ totalDon = donations.findAll().stream().mapToLong(x -> x.getAmount()==null?0:x.getAmount().longValue()).sum(); }catch(Exception e){}
    try{ totalSpon = sponsorships.findAll().stream().mapToLong(x -> x.getTotalAmount()==null?0:x.getTotalAmount().longValue()).sum(); }catch(Exception e){}
    long umraCount = 0;
    try{ umraCount = bookings.findAll().stream().filter(x -> x.getPackageName()!=null && x.getPackageName().toLowerCase().contains("umra")).count(); }catch(Exception e){}
    return Map.of(
      "orphans", orphans.count(),
      "donations", donations.count(),
      "umra", umraCount,
      "totalDonations", totalDon,
      "totalSponsorship", totalSpon,
      "status", "OK"
    );
  }
  @GetMapping("/health")
  public Map<String,Object> health(){ return Map.of("status","OK","message","Backend iko hai"); }
  @GetMapping("/")
  public Map<String,Object> root(){ return Map.of("status","OK","message","Yatima Umra Backend iko hai"); }
}

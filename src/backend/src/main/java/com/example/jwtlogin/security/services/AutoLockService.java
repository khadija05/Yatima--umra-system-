package com.example.jwtlogin.service;
import com.example.jwtlogin.model.*; import com.example.jwtlogin.repository.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Service; import java.time.*;
import java.time.temporal.ChronoUnit; import java.util.List;
@Service
public class AutoLockService { @Autowired UserRepository userRepo; @Autowired BookingRepository bookingRepo;
@Scheduled(cron="0 0 0 * * *") public void angaliaWote(){
  for(User u: userRepo.findAll()){
    if(u.getSubscriptionExpiry()!=null && LocalDate.now().isAfter(u.getSubscriptionExpiry())){
      if(u.getPlan().equals("DEMO") && u.getTotalPaid()==0){ u.setLocked(true); u.setSubscriptionActive(false); userRepo.save(u); }
      else if(!u.getPlan().equals("DEMO") && u.getTotalPaid()==0){ u.setLocked(true); u.setSubscriptionActive(false); userRepo.save(u); }
    }
  }
  for(Booking b: bookingRepo.findAll()){
    if(b.getTareheMwisho()==null) continue; LocalDateTime sasa=LocalDateTime.now(); long siku=ChronoUnit.DAYS.between(sasa,b.getTareheMwisho()); b.setSikuZilizobaki((int)siku);
    if(sasa.isAfter(b.getTareheMwisho()) && !b.getImelipa()) b.setStatus("LOCKED");
    else if(sasa.isAfter(b.getTareheMwisho()) && b.getImelipa()) b.setStatus("EXPIRED");
    else if(b.getImelipa()) b.setStatus("ACTIVE");
    bookingRepo.save(b);
  }
}
@Scheduled(fixedRate=60000) public void kilaDakika(){ angaliaWote(); }
}

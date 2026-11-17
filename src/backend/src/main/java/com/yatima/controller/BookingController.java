package com.yatima.controller;
import com.yatima.model.Booking;
import com.yatima.repository.BookingRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/bookings") @CrossOrigin("*")
public class BookingController {
  private final BookingRepository repo; public BookingController(BookingRepository r){this.repo=r;}
  @GetMapping public List<Booking> all(){return repo.findAll();}
  @PostMapping public Booking create(@RequestBody Booking b){if(b.getStatus()==null||b.getStatus().isEmpty()) b.setStatus("PENDING"); return repo.save(b);}
  @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}

package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findByUsername(String username);
  List<Booking> findByStatus(String status);
}

package com.yatima.repository;
import com.yatima.model.Booking; import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*; public interface BookingRepository extends JpaRepository<Booking,Long>{
  List<Booking> findByTenantId(String t);
}

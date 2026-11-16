package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
  List<Donation> findByTasisiId(Long tasisiId);
}

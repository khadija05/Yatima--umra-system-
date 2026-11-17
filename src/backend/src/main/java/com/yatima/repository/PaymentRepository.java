package com.yatima.repository;
import com.yatima.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DonationRepository extends JpaRepository<Donation,Long> {}

package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.Tasisi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TasisiRepository extends JpaRepository<Tasisi, Long> {
  List<Tasisi> findByMkoa(String mkoa);
  List<Tasisi> findByOwnerUsername(String ownerUsername);

  @Query("SELECT t FROM Tasisi t ORDER BY (COALESCE(t.wastaniRating,0) * COALESCE(t.idadiWatu,0)) DESC")
  List<Tasisi> findTopBoraZaidi();
}

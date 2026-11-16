package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.Umra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UmraRepository extends JpaRepository<Umra, Long> {
  List<Umra> findByOwnerUsername(String ownerUsername);
  List<Umra> findByMkoa(String mkoa);

  @Query("SELECT u FROM Umra u ORDER BY u.bei ASC")
  List<Umra> findTopByBei();

  @Query("SELECT u FROM Umra u ORDER BY (COALESCE(u.wastaniRating,0) * COALESCE(u.idadiWatu,0)) DESC")
  List<Umra> findTopBoraZaidi();
}

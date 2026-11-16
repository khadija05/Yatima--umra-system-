package com.example.jwtlogin.repository;
import com.example.jwtlogin.model.Umra; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface UmraRepository extends JpaRepository<Umra,Long>{ List<Umra> findByOwnerEmail(String e); }

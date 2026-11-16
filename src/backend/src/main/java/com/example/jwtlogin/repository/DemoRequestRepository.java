package com.example.jwtlogin.repository;
import com.example.jwtlogin.model.DemoRequest; import org.springframework.data.jpa.repository.JpaRepository;
public interface DemoRequestRepository extends JpaRepository<DemoRequest,Long>{}

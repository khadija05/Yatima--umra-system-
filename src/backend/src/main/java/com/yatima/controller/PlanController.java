package com.yatima.controller;
import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/plans") @CrossOrigin("*")
public class PlanController {
  @GetMapping public List<Map<String,Object>> plans(){
    return List.of(
      Map.of("code","DEMO","displayName","DEMO - Bure Siku 3","price",0,"days",3,"maxOrphans",10,"maxUmra",5),
      Map.of("code","KIDOGO","displayName","KIDOGO","price",50000,"days",30,"maxOrphans",50,"maxUmra",20),
      Map.of("code","KAWAIDA","displayName","KAWAIDA","price",100000,"days",30,"maxOrphans",200,"maxUmra",100),
      Map.of("code","KUBWA","displayName","KUBWA","price",200000,"days",365,"maxOrphans",9999,"maxUmra",9999)
    );
  }
}

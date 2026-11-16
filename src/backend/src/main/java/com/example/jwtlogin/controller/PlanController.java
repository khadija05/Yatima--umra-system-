package com.example.jwtlogin.controllers;
import com.example.jwtlogin.model.Plan; import org.springframework.web.bind.annotation.*; import java.util.*;
@CrossOrigin(origins="*") @RestController @RequestMapping("/api/plans")
public class PlanController {
  @GetMapping public List<Map<String,Object>> all(){ List<Map<String,Object>> l=new ArrayList<>(); for(Plan p: Plan.values()){ Map<String,Object> m=new HashMap<>(); m.put("code",p.name()); m.put("displayName",p.getDisplayName()); m.put("price",p.getPrice()); m.put("maxOrphans",p.getMaxOrphans()); m.put("maxUmra",p.getMaxUmra()); m.put("days",p.getDays()); l.add(m); } return l; }
}

package com.yatima.controller;
import com.yatima.model.Orphan;
import com.yatima.repository.OrphanRepository;
import com.yatima.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
@RestController @RequestMapping("/api/orphans") @CrossOrigin("*")
public class OrphanController {
  private final OrphanRepository repo; private final JwtUtil jwt;
  public OrphanController(OrphanRepository r, JwtUtil j){repo=r;jwt=j;}
  @GetMapping("/featured") public List<Orphan> featured(){return repo.findByFeaturedTrue();}
  @GetMapping public List<Orphan> all(@RequestHeader(value="Authorization",required=false) String auth){
    try{String role=(String)jwt.parse(auth).get("role"); String user=jwt.parse(auth).getSubject(); if("SUPER_ADMIN".equals(role)) return repo.findAll(); return repo.findByTenantId(user);}catch(Exception e){return repo.findByFeaturedTrue();}
  }
  @GetMapping("/my") public List<Orphan> mine(@RequestHeader("Authorization") String auth){return repo.findByTenantId(jwt.parse(auth).getSubject());}
  @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable Long id){return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());}
  @PostMapping(consumes={"multipart/form-data"}) public ResponseEntity<?> create(@RequestParam("name") String name,@RequestParam(value="age",required=false) Integer age,@RequestParam(value="gender",required=false) String gender,@RequestParam(value="location",required=false) String location,@RequestParam(value="story",required=false) String story,@RequestParam(value="dream",required=false) String dream,@RequestParam(value="needs",required=false) String needs,@RequestParam(value="featured",required=false,defaultValue="true") boolean featured,@RequestParam(value="photo",required=false) MultipartFile photo,@RequestHeader(value="Authorization",required=false) String auth){
    try{String tenant=""; String user=""; if(auth!=null){user=jwt.parse(auth).getSubject(); String role=(String)jwt.parse(auth).get("role"); if(!"SUPER_ADMIN".equals(role)&&!"TENANT_ADMIN".equals(role)&&!"Admin".equals(role)&&!"OWNER".equals(role)) return ResponseEntity.status(403).body(Map.of("message","Hauna ruhusa! Ni Admin tu.")); tenant=user;}
      Orphan o=new Orphan(); o.setTenantId(tenant); o.setOwnerUsername(user); o.setName(name); o.setAge(age==null?7:age); o.setGender(gender==null?"Mtoto":gender); o.setLocation(location==null?"Zanzibar":location); o.setStory(story==null?"":story); o.setDream(dream==null?"":dream); o.setNeeds(needs==null?"Elimu, Chakula, Afya":needs); o.setFeatured(featured); o.setStatus("Anahitaji Msaada"); o.setProgress(50); o.setRemaining(2250000.0);
      if(photo!=null&&!photo.isEmpty()){String b64=Base64.getEncoder().encodeToString(photo.getBytes()); o.setPhotoBase64("data:"+(photo.getContentType()==null?"image/jpeg":photo.getContentType())+";base64,"+b64); o.setPhotoName(photo.getOriginalFilename());}else o.setPhotoBase64("https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?q=80&w=800&auto=format&fit=crop");
      repo.save(o); return ResponseEntity.ok(Map.of("message","Yatima amesajiliwa!","orphan",o));
    }catch(Exception e){return ResponseEntity.status(500).body(Map.of("message",e.getMessage()));}
  }
  @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id){repo.deleteById(id); return ResponseEntity.ok(Map.of("message","Amefutwa"));}
  @PutMapping("/{id}/featured") public ResponseEntity<?> toggle(@PathVariable Long id){Optional<Orphan> o=repo.findById(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Orphan x=o.get(); x.setFeatured(!x.isFeatured()); repo.save(x); return ResponseEntity.ok(x);}
}

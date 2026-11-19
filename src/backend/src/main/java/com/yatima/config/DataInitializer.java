
// DataInitializer.java
package com.yatima.config; import com.yatima.model.*; import com.yatima.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*;
@Configuration public class DataInitializer {
  @Bean CommandLineRunner init(OrphanRepository orphans, UmraPackageRepository packages, InstitutionRepository institutions){
    return args->{
      if(institutions.count()==0){
        Institution z=new Institution(); z.setName("Taasisi ya Mayatima Zanzibar"); z.setLocation("Zanzibar"); z.setPbzAccount("0887387001"); z.setPbzName("KHADIJA DENGE"); z.setPhone("0778709707"); z.setAdminPhone("0778709707"); z.setPlan("SPECIAL"); z.setStatus("ACTIVE"); institutions.save(z);
        Institution p=new Institution(); p.setName("Taasisi ya Mayatima Pemba"); p.setLocation("Pemba"); p.setPbzAccount("0655563593"); p.setPbzName("TAASISI PEMBA"); p.setPhone("0655563593"); p.setAdminPhone("0655563593"); p.setPlan("KAWAIDA"); p.setStatus("ACTIVE"); institutions.save(p);
      }
      if(orphans.count()==0){
        orphans.save(mk("Ahmed",10,"BOY","Zanzibar","Anapenda Qur'an","Anahitaji Msaada",2025000,"https://i.pravatar.cc/300?u=ahmed","Fatma Denge","0778709707","Mama","ZAN-123456","Michenzani, Zanzibar","Skuli ya Michenzani","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE"));
        orphans.save(mk("Fatma",9,"GIRL","Zanzibar","Yatima wa mama, anaishi na bibi","Anahitaji Msaada",1350000,"https://i.pravatar.cc/300?u=fatma","Bibi Zaituni","0776123456","Bibi","ZAN-654321","Mwanakwerekwe, Zanzibar","Skuli ya Mwanakwerekwe","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE"));
        orphans.save(mk("Hassan",11,"BOY","Zanzibar","Anapenda mpira na Qur'an","Anahitaji Msaada",2475000,"https://i.pravatar.cc/300?u=hassan","Amina Hassan","0773456789","Mama","ZAN-111222","Fuoni, Zanzibar","Skuli ya Fuoni","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE"));
        orphans.save(mk("Aisha",7,"GIRL","Zanzibar","Anapenda kuimba kaswida","Anahitaji Msaada",900000,"https://i.pravatar.cc/300?u=aisha","Khadija Aisha","0779988776","Mama","ZAN-333444","Kiembe Samaki, Zanzibar","Skuli ya Kiembe Samaki","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE"));
        orphans.save(mk("Yusuf",12,"BOY","Pemba","Amekusanya 80%","Karibu Kufikia Lengo",3600000,"https://i.pravatar.cc/300?u=yusuf","Ali Juma","0655563593","Mjomba","PEM-789012","Chakechake, Pemba","Skuli ya Chakechake","Taasisi ya Mayatima Pemba","0655563593","TAASISI PEMBA"));
        orphans.save(mk("Zainab",8,"GIRL","Pemba","Anapenda kuchora na kusoma","Anahitaji Msaada",1125000,"https://i.pravatar.cc/300?u=zainab","Mariam Zainab","0651122334","Mama","PEM-555666","Wete, Pemba","Skuli ya Wete","Taasisi ya Mayatima Pemba","0655563593","TAASISI PEMBA"));
      }
      if(packages.count()==0){
        packages.save(pk("Umra Standard - Siku 14","Makka 7 Siku + Madina 7 Siku",4500000,"MOST POPULAR",72));
        packages.save(pk("Umra VIP - Siku 14","Hoteli 5 Nyota • Usafiri wa VIP",7500000,"VIP",45));
        packages.save(pk("Umra Familia - Siku 14","Familia ya Watu 4",16000000,"FAMILIA",30));
      }
    };
  }
  private Orphan mk(String n,int age,String g,String loc,String story,String st,long col,String photo,String gName,String gPhone,String rel,String gId,String gAddr,String school,String inst,String acc,String accName){
    Orphan o=new Orphan(); o.setName(n); o.setAge(age); o.setGender(g); o.setLocation(loc); o.setStory(story); o.setStatus(st); o.setCollectedAmount(col); o.setTargetAmount(4500000L);
    o.setPhotoUrl(photo); o.setSchool(school); o.setHealthStatus("Mzima"); o.setBirthCertificate("ZAN-2026-"+n);
    o.setGuardianName(gName); o.setGuardianPhone(gPhone); o.setGuardianRelation(rel); o.setGuardianIdNumber(gId); o.setGuardianAddress(gAddr);
    o.setGuardianPhotoUrl("https://i.pravatar.cc/150?u="+gName); o.setGuardianOccupation("Mkulima / Mfanyabiashara"); o.setGuardianNotes("Mdhamini aliyethibitishwa na Taasisi");
    o.setInstitutionName(inst); o.setInstitutionPbzAccount(acc); o.setInstitutionPbzName(accName); return o;
  }
  private UmraPackage pk(String n,String d,long p,String b,int pct){ UmraPackage x=new UmraPackage(); x.setName(n); x.setDescription(d); x.setPrice(p); x.setBadge(b); x.setCollectedPercent(pct); return x; }
}

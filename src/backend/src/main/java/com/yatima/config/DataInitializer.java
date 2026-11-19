package com.yatima.config;
import com.yatima.model.*;
import com.yatima.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class DataInitializer {
  @Bean CommandLineRunner init(OrphanRepository orphans, InstitutionRepository institutions, YatimaPackageRepository packages){
    return args -> {
      if(institutions.count()==0){
        Institution z=new Institution(); z.setName("Taasisi ya Mayatima Zanzibar"); z.setLocation("Zanzibar"); z.setPbzAccount("0887387001"); z.setPbzName("KHADIJA DENGE"); z.setPhone("0778709707"); z.setAdminPhone("0778709707"); z.setAdminName("Khadija Denge"); z.setMpesaNumber("0778709707"); z.setMpesaName("KHADIJA DENGE"); z.setTigoNumber("0715709707"); z.setTigoName("KHADIJA DENGE"); z.setAirtelNumber("0788709707"); z.setAirtelName("KHADIJA DENGE"); z.setPlan("SPECIAL"); z.setStatus("ACTIVE"); z.setOwnerName("KHADIJA DENGE"); z.setOwnerPhone("0778709707"); institutions.save(z);
        Institution p=new Institution(); p.setName("Taasisi ya Mayatima Pemba"); p.setLocation("Pemba"); p.setPbzAccount("0655563593"); p.setPbzName("TAASISI PEMBA"); p.setPhone("0655563593"); p.setAdminPhone("0655563593"); p.setAdminName("Ali Juma"); p.setMpesaNumber("0655563593"); p.setMpesaName("TAASISI PEMBA"); p.setTigoNumber("0655563593"); p.setTigoName("TAASISI PEMBA"); p.setAirtelNumber("0655563593"); p.setAirtelName("TAASISI PEMBA"); p.setPlan("KAWAIDA"); p.setStatus("ACTIVE"); p.setOwnerName("KHADIJA DENGE"); p.setOwnerPhone("0778709707"); institutions.save(p);
      }
      if(packages.count()==0){
        YatimaPackage y1=new YatimaPackage(); y1.setName("YATIMA BRONZE"); y1.setDescription("Msaada wa Mwezi 1 - Chakula + Mavazi"); y1.setPrice(150000L); y1.setBadge("BRONZE"); y1.setIncludes("Chakula + Mavazi + Qur'an"); y1.setDuration("Mwezi 1"); packages.save(y1);
        YatimaPackage y2=new YatimaPackage(); y2.setName("YATIMA SILVER"); y2.setDescription("Msaada wa Miezi 6 - Elimu + Afya + Chakula"); y2.setPrice(900000L); y2.setBadge("SILVER"); y2.setIncludes("Elimu + Afya + Chakula + Mavazi"); y2.setDuration("Miezi 6"); packages.save(y2);
        YatimaPackage y3=new YatimaPackage(); y3.setName("YATIMA GOLD"); y3.setDescription("Msaada wa Mwaka 1 - Kila kitu"); y3.setPrice(1800000L); y3.setBadge("GOLD"); y3.setIncludes("Elimu + Afya + Chakula + Mavazi + Malazi"); y3.setDuration("Mwaka 1"); packages.save(y3);
        YatimaPackage y4=new YatimaPackage(); y4.setName("YATIMA FULL UMRA"); y4.setDescription("Umra Yote kwa Yatima - Visa + Tiketi + Hoteli"); y4.setPrice(4500000L); y4.setBadge("FULL UMRA ⭐"); y4.setIncludes("Visa + Tiketi + Hoteli + Chakula + Ziyara + Mavazi"); y4.setDuration("Umra Yote - Siku 14"); packages.save(y4);
      }
      if(orphans.count()==0){
        orphans.save(mk("Ahmed",10,"BOY","Zanzibar","Anapenda Qur'an na mpira","Anahitaji Msaada",2025000,"https://i.pravatar.cc/300?u=ahmed","Fatma Denge","0778709707","Mama","ZAN-123456","Michenzani, Zanzibar","Skuli ya Michenzani","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE","YATIMA FULL UMRA",4500000L));
        orphans.save(mk("Fatma",9,"GIRL","Zanzibar","Yatima wa mama, anaishi na bibi","Anahitaji Msaada",1350000,"https://i.pravatar.cc/300?u=fatma","Bibi Zaituni","0776123456","Bibi","ZAN-654321","Mwanakwerekwe, Zanzibar","Skuli ya Mwanakwerekwe","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE","YATIMA GOLD",1800000L));
        orphans.save(mk("Hassan",11,"BOY","Zanzibar","Anapenda mpira na Qur'an","Anahitaji Msaada",2475000,"https://i.pravatar.cc/300?u=hassan","Amina Hassan","0773456789","Mama","ZAN-111222","Fuoni, Zanzibar","Skuli ya Fuoni","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE","YATIMA SILVER",900000L));
        orphans.save(mk("Aisha",7,"GIRL","Zanzibar","Anapenda kuimba kaswida","Anahitaji Msaada",900000,"https://i.pravatar.cc/300?u=aisha","Khadija Aisha","0779988776","Mama","ZAN-333444","Kiembe Samaki, Zanzibar","Skuli ya Kiembe Samaki","Taasisi ya Mayatima Zanzibar","0887387001","KHADIJA DENGE","YATIMA BRONZE",150000L));
        orphans.save(mk("Yusuf",12,"BOY","Pemba","Amekusanya 80%","Karibu Kufikia Lengo",3600000,"https://i.pravatar.cc/300?u=yusuf","Ali Juma","0655563593","Mjomba","PEM-789012","Chakechake, Pemba","Skuli ya Chakechake","Taasisi ya Mayatima Pemba","0655563593","TAASISI PEMBA","YATIMA FULL UMRA",4500000L));
        orphans.save(mk("Zainab",8,"GIRL","Pemba","Anapenda kuchora na kusoma","Anahitaji Msaada",1125000,"https://i.pravatar.cc/300?u=zainab","Mariam Zainab","0651122334","Mama","PEM-555666","Wete, Pemba","Skuli ya Wete","Taasisi ya Mayatima Pemba","0655563593","TAASISI PEMBA","YATIMA GOLD",1800000L));
      }
    };
  }
  private Orphan mk(String n,int age,String g,String loc,String story,String st,long col,String photo,String gName,String gPhone,String rel,String gId,String gAddr,String school,String inst,String acc,String accName,String pkg,long pkgPrice){
    Orphan o=new Orphan(); o.setName(n); o.setAge(age); o.setGender(g); o.setLocation(loc); o.setStory(story); o.setStatus(st); o.setCollectedAmount(col); o.setTargetAmount(4500000L);
    o.setPhotoUrl(photo); o.setSchool(school); o.setHealthStatus("Mzima"); o.setBirthCertificate("ZAN-2026-"+n);
    o.setGuardianName(gName); o.setGuardianPhone(gPhone); o.setGuardianRelation(rel); o.setGuardianIdNumber(gId); o.setGuardianAddress(gAddr);
    o.setGuardianPhotoUrl("https://i.pravatar.cc/150?u="+gName); o.setGuardianOccupation("Mkulima / Mfanyabiashara"); o.setGuardianNotes("Mdhamini aliyethibitishwa na Taasisi");
    o.setGuardianEmail(""); o.setGuardianGender("KE"); o.setGuardianDob("1985-01-01"); o.setGuardianNationality("Tanzania");
    o.setInstitutionName(inst); o.setInstitutionPbzAccount(acc); o.setInstitutionPbzName(accName);
    o.setYatimaPackageName(pkg); o.setYatimaPackagePrice(pkgPrice); o.setYatimaPackageStatus("AMECHAGULIWA");
    return o;
  }
}

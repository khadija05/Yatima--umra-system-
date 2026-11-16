package com.example.jwtlogin.model;
public enum Plan {
  DEMO("DEMO - Siku 3 Bure", 0, 10, 5, 3),
  KIDOGO("KIDOGO - Madrasa / Shehia", 50000, 50, 20, 30),
  KAWAIDA("KAWAIDA - Zaidi Wanaotumia", 100000, 200, 100, 30),
  KUBWA("KUBWA - Taasisi / NGO", 200000, 1000000, 1000000, 30);
  private final String displayName; private final double price; private final int maxOrphans; private final int maxUmra; private final int days;
  Plan(String d, double p, int o, int u, int days){ this.displayName=d; this.price=p; this.maxOrphans=o; this.maxUmra=u; this.days=days; }
  public String getDisplayName(){return displayName;} public double getPrice(){return price;} public int getMaxOrphans(){return maxOrphans;} public int getMaxUmra(){return maxUmra;} public int getDays(){return days;}
  public static Plan fromCode(String c){ if(c==null) return KAWAIDA; for(Plan p: values()) if(p.name().equalsIgnoreCase(c)) return p; return KAWAIDA; }
}

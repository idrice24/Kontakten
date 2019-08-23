package com.auel.kontakten.model;

public enum Sex {
    MAENNLICH ('m'), WEIBLICH('w'), UNBEKANNT('u');
   
   private final char code;
   
   private Sex(char code)
   {
	   this.code = code;
   }
   
   public char getCode() {
       return code;
   }
   
   public static Sex getSexName(char code) {
       switch (code) {
       case 'm':
           return  MAENNLICH ;
       case 'w':
           return WEIBLICH;
       case 'u':
           return UNBEKANNT;
       default:
           return UNBEKANNT;
       }
   }
   
   
}

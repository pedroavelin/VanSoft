/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Gonga
 */
public class Tempo {
          
    public String Date(){
       String h; 
       Date data = new Date();
       SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
       h = formatar.format(data);
       return h;
    }
    
    public String Date(String formato){
       String h; 
       Date data = new Date();
       SimpleDateFormat formatar = new SimpleDateFormat(formato);
       h = formatar.format(data);
       return h;
    }
     
        
      public String Hours(){
       String h; 
       Date data = new Date();
       SimpleDateFormat formatar = new SimpleDateFormat("H:m:s");
       h = formatar.format(data);
       return h;
    }
      
      public String Hours(String formato){
       String h; 
       Date data = new Date();
       SimpleDateFormat formatar = new SimpleDateFormat(formato);
       h = formatar.format(data);
       return h;
    }
      
      
      
    public String Hours2(){
       String h; 
       Date data = new Date();
       SimpleDateFormat formatar = new SimpleDateFormat("H:m");
       h = formatar.format(data);
       return h;
    }
      
    public String DiaAnterior(){
        DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return df.format(cal.getTime());
    }
    
    public String NextDay(int num){
        DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num);
        return df.format(cal.getTime());
    }
    
}

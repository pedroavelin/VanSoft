/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. Chang
 */
package Controle;

/**
 *
 * @author Gonga
 */
public class Validação {
    
    public boolean ValidarTelef (String telefone) {
        if(telefone.length()==13 || telefone.length()==9){
            if(telefone.startsWith("+2449") || telefone.startsWith("9"))
            {   
                for(int i=4;i<telefone.length();i++)
                  if(!Character.isDigit(telefone.charAt(i)))
                      return false;
                return true;
            }                  
        }
        return false;
    }
//         Considerar todas as possíveis condições de início if (telefone.startsWith("+24492") ||telefone.startsWith("+24493") || telefone.startsWith("+24494") 
//                || telefone.startsWith("+24499")|| telefone.startsWith("91") || telefone.startsWith("92") ||telefone.startsWith("93") || telefone.startsWith("94") 
//                || telefone.startsWith("99")) 
        

   
        
        public boolean SoNumero(String cadeia){
            int tamanho = cadeia.length();
            int cont =0;
            for (int i = 0; i < tamanho; i++) {
                char x = cadeia.charAt(i);
                
                if(i==0 && x!='-' && x!='0' && x!='1' && x!='2' && x!='3' && x!='4' && x!='5' && x!='6' && x!='7' && x!='8' && x!='9')
                        return false;
                if(i!=0 && x!='.' &&  x!='0' && x!='1' && x!='2' && x!='3' && x!='4' && x!='5' && x!='6' && x!='7' && x!='8' && x!='9')
                        return false;
            }
            
            return true;
        }
        
        public boolean SoLetras(String cadeia){
            int tamanho = cadeia.length();
            int cont =0;
            for (int i = 0; i < tamanho; i++) {
                char x = cadeia.charAt(i);
                
                if(x=='-'|| x=='!' || x=='@' || x=='"' || x=='#' || x=='£' || x=='$' || x=='§' || x=='-' || x=='-'
                        || x=='{' || x=='}' || x=='[' || x==']' || x=='%' || x=='€' || x=='&' || x=='?' || x=='<'
                        || x=='<' || x=='>' || x=='»' || x=='«' || x=='#'
                        ||x==':' ||  x=='~' || x=='*' || x==',' || x=='+' || x=='/' || x=='´' || x=='=' || x==')'
                        || x=='0' || x=='1' || x=='2' || x=='3' || x=='4' || x=='5' || x=='6' || x=='7' 
                        || x=='8' || x=='9')
                        return false;
            }
            
            return true;
            
        }
        
    public boolean validarEmail (String email){
        if (email != null && email.contains("@")){
        String[] vectorEmail= email.split("@");
        if(vectorEmail.length!=0){
           String pos1=vectorEmail[1];
            if(pos1.contains(".")){
                return true;
            }
            else
                return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    
    
    public boolean validarNbi (String nbi){
        
        String v1;
        String v2;
        String v3;
        
        int c0=0;
        int c1=0;
        int c2=0;
        
        if(nbi.length()==14){
            v1=nbi.substring(0, 9);
            v2=nbi.substring(9, 11);
            v3=nbi.substring(11, 14);
            
            for(int i=0;i<v1.length();i++){
                if(Character.isDigit(v1.charAt(i))== true){
                    c0++;
                }
                else
                    return false;
                }
               
            for(int i=0;i<v2.length();i++){
                if(Character.isLetter(v2.charAt(i))== true){
                   c1++;
                }
                    else
                        return false;
                }
           
                for(int i=0;i<v3.length();i++){
                    if(Character.isDigit(v3.charAt(i))== true){
                      c2++;
                    }
                    else
                        return false;    
                }
                
                if(c0==9 && c1==2 && c2==3){
                    return true;
                }
                             
                }   
                        return false;
            }
       
        }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. Chang(
 */
package instalador;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Octávio Mfr
 */
public class ControloBD {

    public Statement stm;
    public ResultSet rs;
    public Connection con;
    private String driver = "org.postgresql.Driver";
    private String caminho = "jdbc:postgresql://localhost:5432/postgres";
    private String usuario = "postgres";
    private String senha = "zegonga";

    public boolean conexao() {// Metodo responsavel para realizar a conexão
        System.setProperty("jdbc.Drivers", driver);
        try {
            con = DriverManager.getConnection(caminho, usuario, senha);
            return true;
            //JOptionPane.showMessageDialog(null,"Conexão efetuada com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao se conectar com o banco de dados: \n" + ex.getMessage());
        }
        return false;

    }
//    public void conexao() {// Metodo responsavel para realizar a conexão
//        System.setProperty("jdbc.Drivers", driver);
//        try {
//            con = DriverManager.getConnection(caminho, usuario, senha);
//            //JOptionPane.showMessageDialog(null,"Conexão efetuada com sucesso");
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Erro ao se conectar com o banco de dados: \n" + ex.getMessage());
//        }
//
//    }
    
    public boolean conexao(String caminho) {// Metodo responsavel para realizar a conexão
        System.setProperty("jdbc.Drivers", driver);
        try {
            con = DriverManager.getConnection(caminho, usuario, senha);
//            JOptionPane.showMessageDialog(null,"Conexão efetuada com sucesso");
            return true;
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Erro ao se conectar com o banco de dados: \n" + ex.getMessage());
        }
        return false;

    }

    public void executeSql(String sql) {
        try {
            stm = con.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null,"Erro executaSQL \n"+ex.getMessage());
        }
    }

    public void deconecta() { // Metodo responsavel para realizar a desconexão
        try {
            con.close();
            //JOptionPane.showMessageDialog(null,"BD disconectada com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Disconectar com BD:\n" + ex.getMessage());
        }
    }

    public boolean eliminar(String tabela, String coluna, String id) {
        conexao();
        try {

            PreparedStatement pst = con.prepareStatement("delete from " + tabela + " WHERE " + coluna + " = " + id);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        deconecta();
        return false;
    }

    

//    public static void realizaBackup() throws IOException, InterruptedException {
//        Tempo tempo = new Tempo();
//        final List<String> comandos = new ArrayList<String>();
//
////­comandos.add("C:\\Pro­gram Files (x86)\\PostgreSQL\\8­.4\\bin\\pg_dump.exe­"); 
////­comandos.add("C:\\Pro­gram Files\\PostgresPlus\­\8.4SS\\bin\\pg_dump­.exe"); 
//        comandos.add("C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_dump.exe"); // esse é meu caminho 
//
//        comandos.add("-h");
//        comandos.add("localhost"); //ou comandos.add("192.168.0.1"); 
//        comandos.add("-p");
//        comandos.add("5432");
//        comandos.add("-U");
//        comandos.add("postgres");
//        comandos.add("-F");
//        comandos.add("c");
//        comandos.add("-b");
//        comandos.add("-v");
//        comandos.add("-f");
//        String data = tempo.Date().replace('-', '_');
//        comandos.add("C:\\Backup_vansoft " + data + ".backup"); // eu utilizei meu C:\ e D:\ para os testes e gravei o backup com sucesso. 
//        comandos.add("vansoft");
//        ProcessBuilder pb = new ProcessBuilder(comandos);
//
//        pb.environment().put("PGPASSWORD", "zegonga"); //Somente coloque sua senha 
//
//        try {
//            final Process process = pb.start();
//
//            final BufferedReader r = new BufferedReader(
//                    new InputStreamReader(process.getErrorStream()));
//            String line = r.readLine();
//            while (line != null) {
//                System.err.println(line);
//                line = r.readLine();
//            }
//            r.close();
//
//            process.waitFor();
//            process.destroy();
//            
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "backup não realizado. "+e);
//        } catch (InterruptedException ie) {
//            JOptionPane.showMessageDialog(null, "backup não realizado. "+ie);
//        }
//       
//
//    }

    public void Restore(String ficheiro) {
        final List<String> comandos = new ArrayList<String>();

//­comandos.add("C:\\Pro­gram Files (x86)\\PostgreSQL\\8­.4\\bin\\pg_dump.exe­"); 
//­comandos.add("C:\\Pro­gram Files\\PostgresPlus\­\8.4SS\\bin\\pg_dump­.exe"); 
        comandos.add("C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_restore.exe"); // esse é meu caminho 

        comandos.add("-d");
        comandos.add("postgres");
        comandos.add("--clean");
        comandos.add("--verbose");     
        comandos.add("-h");
        comandos.add("localhost"); //­ou comandos.add("192.168.0.1"); 
        comandos.add("-p");
        comandos.add("5432");
        comandos.add("-U");
        comandos.add("postgres");

        comandos.add(ficheiro);  

        ProcessBuilder pb = new ProcessBuilder(comandos);

        pb.environment().put("PGPASSWORD", "zegonga"); //Somente coloque sua senha 

        try {
            final Process process = pb.start();

            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();

            process.waitFor();
            process.destroy();
            JOptionPane.showMessageDialog(null, "Base de dados pronta.","Sucesso",JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Base de dados não criados.\n"+e,
                    "Erro",JOptionPane.ERROR_MESSAGE);
;
        } catch (InterruptedException ie) {
            
            JOptionPane.showMessageDialog(null, "Base de dados não criados.\n"+ie,
                    "Erro",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean Restaurar() {
        

        conexao();
        try {
            PreparedStatement pst = con.prepareStatement("drop schema public cascade;\n"
                    + "create schema public;");

            pst.execute();

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        deconecta();
        return false;
    }
    
      public String Chang(double V) {
//        BigDecimal valor = new BigDecimal(V);
//        NumberFormat nf = NumberFormat.getCurrencyInstance();
//        String formatado = nf.format(valor);
////        System.out.println(formatado);
//        return formatado.substring(0, formatado.length() - 2);
          
          BigDecimal valor = new BigDecimal(V);
        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
        String formatado = nf.format(valor);
//        System.out.println(nf.getCurrency());
       
        return formatado;

    }

    public String Chang(String V) {
//        BigDecimal valor = new BigDecimal(V);
//        NumberFormat nf = NumberFormat.getCurrencyInstance();
//        String formatado = nf.format(valor);
////        System.out.println(formatado);
//        return formatado.substring(0, formatado.length() - 2);
        
        BigDecimal valor = new BigDecimal(V);
        NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
        String formatado = nf.format(valor);
        System.out.println(nf.getCurrency());
       
        return formatado;

    }
    
    public int UltimoId(String TB, String campo,int idUser,int idTurno){
//        System.out.println("select "+campo+" from "+TB+" where idutilizaador = "+idUser+" AND idturno= "+idTurno+" ORDER BY "+campo);

        try {
            this.conexao();
            this.executeSql("select "+campo+" from "+TB+" where idutilizador = "+idUser+" AND idturno= "+idTurno+" ORDER BY "+campo);
            if (this.rs.last()) {
                int LastFID = this.rs.getInt(campo);
                System.out.println("NC "+LastFID);
                return LastFID;
                
            }
            this.deconecta();
            return  -1;
        } catch (SQLException ex) {
            Logger.getLogger(ControloBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    
    public void abrirDoc(String fileName){

        try {
            File file = new File(fileName);
            String nome = file.getAbsolutePath();
            JOptionPane.showMessageDialog(null, nome);
            System.out.println(file.getAbsolutePath());
            Desktop.getDesktop().open(file);
            
        } catch (IOException ex) {
            JOptionPane.showInputDialog(null, "Impossível instalar a base de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
            
    }
    
    
}

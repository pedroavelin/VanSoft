/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cramervan;

import Controle.EncriptaDecriptaRSA;
import Modelo.ModeloPDF;
import Vista.Add_saida;
import Vista.Entar;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Gonga
 */
public class CramerVan {

    /** [B@1556938
     *  [B@1556938
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Entar().setVisible(true);   
//        System.out.println("Hash:\n"+new EncriptaDecriptaRSA().GerarAssinatura("1111", "2222", "3333", 45.5, "asdasdasdasdadasdasdadas"));
        
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  
 */
package Vista;

import Controle.ControloBD;
import Controle.HardwareAddress;
import Controle.TextPrompt;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.awt.Toolkit;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Gonga
 */
public class Solicitar extends javax.swing.JFrame {

    ControloBD liga = new ControloBD();
    HardwareAddress hd = new HardwareAddress();

    /**
     * Creates new form Solicitar
     */
    public Solicitar() {
        initComponents();
//         criptografia();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
//        TextPrompt prompt1 = new TextPrompt("Insira a chave de ativação.", jTextField1);
        this.pack();

        try {
            liga.conexao();
            liga.executeSql("select * from empresa");
            if (liga.rs.first()) {
                this.jTextField2.setText(liga.rs.getString("designacao"));
                this.jTextField3.setText(liga.rs.getString("nif"));
//                this.jTextField_registoComercial.setText(liga.rs.getString("registo_comercial"));
//                this.jTextField_RazaoSocial.setText(liga.rs.getString("razao_social"));
//                this.jTextField_Telefone.setText(Integer.toString(liga.rs.getInt("telefone")));
                this.jTextField7.setText(liga.rs.getString("email"));
//                this.jTextField_WebSite.setText(liga.rs.getString("web_site"));
                this.jTextField6.setText(liga.rs.getString("pais"));
                this.jTextField6.setText(liga.rs.getString("provincia"));
//                this.jTextField_Rua.setText(liga.rs.getString("rua"));
//                this.jTextField_Edificio.setText(liga.rs.getString("edificio"));
//                this.jTextField_indicativo.setText(liga.rs.getString("indicativo"));
//                jComboBox1.setSelectedItem(liga.rs.getString("regime_de_iva"));
//                
//                this.revert(liga.rs.getBytes("foto"));
                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Incapaz de ler todos dados da empresa: ", "Erro de sistema.", JOptionPane.ERROR_MESSAGE);

        }

    }

    public Solicitar(String caminho) {
        initComponents();
//         criptografia();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);

        TextPrompt prompt2 = new TextPrompt("Nome do cliente.", jTextField2);
        TextPrompt prompt3 = new TextPrompt("Número de Identificação Fiscal.", jTextField3);
        TextPrompt prompt4 = new TextPrompt("Endereço de email", jTextField7);
        TextPrompt prompt5 = new TextPrompt("Província", jTextField5);
        TextPrompt prompt6 = new TextPrompt("País", jTextField6);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
//        System.out.println("hh"+nf.getCurrency().getDisplayName());
        String x = "" + nf.getCurrencyInstance().getCurrency().getDisplayName();
        jTextField6.setText(x);
        try {
            liga.conexao();
            liga.executeSql("select * from empresa");
            if (liga.rs.first()) {
                this.jTextField2.setText(liga.rs.getString("designacao"));
                this.jTextField3.setText(liga.rs.getString("nif"));
//                this.jTextField_registoComercial.setText(liga.rs.getString("registo_comercial"));
//                this.jTextField_RazaoSocial.setText(liga.rs.getString("razao_social"));
//                this.jTextField_Telefone.setText(Integer.toString(liga.rs.getInt("telefone")));
                this.jTextField7.setText(liga.rs.getString("email"));
//                this.jTextField_WebSite.setText(liga.rs.getString("web_site"));
                this.jTextField6.setText(liga.rs.getString("pais"));
                this.jTextField6.setText(liga.rs.getString("provincia"));
//                this.jTextField_Rua.setText(liga.rs.getString("rua"));
//                this.jTextField_Edificio.setText(liga.rs.getString("edificio"));
//                this.jTextField_indicativo.setText(liga.rs.getString("indicativo"));
//                jComboBox1.setSelectedItem(liga.rs.getString("regime_de_iva"));
//                
//                this.revert(liga.rs.getBytes("foto"));
                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Incapaz de ler todos dados da empresa: ", "Erro de sistema.", JOptionPane.ERROR_MESSAGE);

        }

        this.pack();
    }

    public void criptografia() {
        String dado = "1234567";
        try {
            // RC2, RC4, RC5, IDEA, Blowfish
            KeyGenerator keyGenerator = KeyGenerator.getInstance("RC4");
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher;
            cipher = Cipher.getInstance("RC4");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] textoCifrado = cipher.doFinal(dado.getBytes());
            System.out.println("Criptografado: " + textoCifrado.toString());

            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] textoDeCifrado = cipher.doFinal(textoCifrado);
            System.out.println("DeCriptografado: " + new String(textoDeCifrado));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        País = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Solicitação de licença");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Submeter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cliente");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(54, 70, 78));
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/faturacao_eletronica.png"))); // NOI18N
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 140, 200));

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Faturação");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 140, -1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(190, Short.MAX_VALUE)))
        );

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("NIF");

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Província");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        País.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        País.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        País.setText("País");

        jTextField6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jTextField7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("E-mail");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(jTextField5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(País, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(jTextField6)
                    .addComponent(jTextField7)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(156, 156, 156)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(País)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            if (hd.getMacAddress() != null) {
                String mac = hd.GetMac();
                int idpc = BucarPC(mac);
                if (idpc == -1) {
                    InsertPC(hd.GetMac(), hd.GetNAME(), hd.GetFULLNAME());
                    idpc = BucarPC(mac);
                }

                if (idpc != -1) {
                    try {
                        byte[] toSend = null;
//            System.out.println(Math.random());
                        boolean r = false;
                        do {
                            double random = Math.random();
                            String dado = mac + " " + random;
                            // AES, RC2,RC4, RC5, IDEA, Blowfish [B@146628a
                            KeyGenerator key = KeyGenerator.getInstance("RC2");
                            SecretKey secreckey = key.generateKey();
                            Cipher cipher;
                            cipher = Cipher.getInstance("RC2");
                            cipher.init(cipher.ENCRYPT_MODE, secreckey);
//            System.out.println("key: " + new String(secreckey.getEncoded()));
                            toSend = cipher.doFinal(dado.getBytes());
                            r = Insertkey(toSend, idpc);
                        } while (r);
                        try {
                            // open a connection to the site
                            URL url = new URL("https://vansoftware.000webhostapp.com/");
//                            URL url = new URL("http://localhost/Solicitacao_VansoFt/");
                            URLConnection con = url.openConnection();
                            // activate the output
                            con.setDoOutput(true);
                            PrintStream ps = new PrintStream(con.getOutputStream());
                            // send your parameters to your site
                            ps.print("&cliente=" + jTextField2.getText());
                            ps.print("&email=" + jTextField7.getText());
                            ps.print("&pais=" + jTextField6.getText());
                            ps.print("&provincia=" + jTextField5.getText());
                            ps.print("&nif=" + jTextField3.getText());
                            ps.print("&chave=" + toSend);
                            ps.print("&pc=" + mac + ", " + hd.GetFULLNAME());
                            ps.print("&sec=" + mac);
                            ps.print("&solicitar="+"solicitar");
//                System.out.println("Key: "+secreckey.toString());

                            // we have to get the input stream in order to actually send the request
                            con.getInputStream();
                            // print out to confirm
                            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String line = null;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
//                                 close the print stream
                                ps.close();
                            }

                            JOptionPane.showMessageDialog(rootPane, "Solicitação de licença enviada com sucesso.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                        } catch (MalformedURLException e1) {
                            JOptionPane.showMessageDialog(rootPane, e1, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

                        } catch (IOException e2) {
                            JOptionPane.showMessageDialog(rootPane, e2, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

                        }

                    } catch (NoSuchAlgorithmException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);
                    } catch (NoSuchPaddingException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

                    } catch (InvalidKeyException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

                    } catch (IllegalBlockSizeException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

                    } catch (BadPaddingException ex) {
                        JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sem ou má conexão a internet.", "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

            }
        } catch (UnknownHostException ex) {
//            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

        } catch (SocketException ex) {
//            Logger.getLogger(Solicitar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, ex, "Falha na solicitação de licença", JOptionPane.ERROR_MESSAGE);

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
//        new Principal3(this.id, this.perfil, liga.getCaminho()).setVisible(true);
//        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Solicitar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel País;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

    private boolean InsertPC(String GetMac, String GetNAME, String GetFULLNAME) {
        liga.conexao();
        try {
            PreparedStatement pst = liga.con.prepareStatement("insert into pc "
                    + "(mac, nome, nome_completo) "
                    + " values ('" + GetMac + "','" + GetNAME + "','" + GetFULLNAME + "')");
            boolean r = pst.execute();
            liga.deconecta();
            return r;
        } catch (SQLException ex) {
            System.out.println("11");
            return false;
        }

    }

    private int BucarPC(String MAC) {

        liga.conexao();
        liga.executeSql("Select idpc from pc where mac = '" + MAC + "'");

        try {
            if (liga.rs.first()) {
                return liga.rs.getInt("idpc");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }

    }

    public boolean Insertkey(byte[] toSend, int idpc) {
        liga.conexao();
        try {
            PreparedStatement pst = liga.con.prepareStatement("insert into chave "
                    + "(chave, estado, idpc) "
                    + " values ('" + toSend + "','não'," + idpc + ")");
            boolean r = pst.execute();
            liga.deconecta();
            return r;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private void InsertLicenca(String mac, String faturação) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

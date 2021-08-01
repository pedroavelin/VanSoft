/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. licenca(
 */
package Vista;

import Controle.ControloBD;
import Controle.HardwareAddress;
import Controle.Tempo;
import java.awt.Color;
import Controle.TextPrompt;
import cramervan.CramerVan;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Gonga
 */
public class Entar extends javax.swing.JFrame {

    ControloBD liga = new ControloBD();
    HardwareAddress hd = new HardwareAddress();

    /**
     * Creates new form Entar
     */
    public Entar() {
        try {
            initComponents();
            jTextField1.requestFocus();
            if (liga.conexao()) {
                liga.executeSql("select *  from configuracao");
                if (liga.rs.first() && liga.rs.getBoolean("backup")) {
                    try {
                        liga.realizaBackup();
                    } catch (IOException ex) {
                        Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                liga.deconecta();

                TextPrompt prompt1 = new TextPrompt("Nome de usuário", jTextField1);
                TextPrompt prompt2 = new TextPrompt("Senha", jPasswordField1);

                setSize(400, 410);
                jPasswordField1.setEnabled(true);
                jTextField1.setEnabled(true);

                this.LICENCA_STATE();

            } else {
                jLabel2.setText("Sem conexão com servidor.");
                jPasswordField1.setEnabled(false);
                jTextField1.setEnabled(false);
            }
        } catch (SQLException ex) {
            jLabel2.setText("Sem conexão com servidor.");
            jPasswordField1.setEnabled(false);
            jTextField1.setEnabled(false);
//            Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Ligado");
        } else {
            jLabel6.setText("Desligado");
        }

        this.pack();

    }

    public Entar(String caminho) {
        try {
            initComponents();
            jTextField1.requestFocus();
            liga.setCaminho(caminho);
            if (liga.conexao()) {
                liga.executeSql("select *  from configuracao");
                if (liga.rs.first() && liga.rs.getBoolean("backup")) {
                    try {
                        liga.realizaBackup();
                    } catch (IOException ex) {
                        Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                liga.deconecta();

                TextPrompt prompt1 = new TextPrompt("Nome de usuário", jTextField1);
                TextPrompt prompt2 = new TextPrompt("Senha", jPasswordField1);

                setSize(400, 410);
                jPasswordField1.setEnabled(true);
                jTextField1.setEnabled(true);

                this.LICENCA_STATE();
            } else {
                jLabel2.setText("Sem conexão com servidor.");
                jPasswordField1.setEnabled(false);
                jTextField1.setEnabled(false);
            }
        } catch (SQLException ex) {
            jLabel2.setText("Sem conexão com servidor.");
            jPasswordField1.setEnabled(false);
            jTextField1.setEnabled(false);
//            Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Ligado");
        } else {
            jLabel6.setText("Desligado");
        }

        this.pack();

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CramerVan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CramerVan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CramerVan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CramerVan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public boolean AbrirTurno(int idUtilizador){
//      double saldoAnterior, saldoDoDia;
//        saldoAnterior=saldoDoDia=0;
//        try {
//             liga.executeSql("SELECT saldo_p_dia_seguinte, saldoDoDia FROM public.turno WHERE data_saida= '"+tempo.Date()+"'; ");
//              if (liga.rs.last())
//                saldoAnterior = liga.rs.getDouble("saldo_p_dia_seguinte");
//              
//            
//            liga.conexao();
//            PreparedStatement pst = liga.con.prepareStatement("INSERT INTO public.turno(\n" +
//                    "            data_entrada, hora_entrada, data_saida, horasaida, idutilizador, \n" +
//                    "            entada, saida, saldo_anterior, saldo_atual, saldo_p_dia_seguinte)\n" +
//                      Tempo tempo = new Tempo();
//        "    VALUES (?, ?, ?, ?, ?, \n" +
//                    "            ?, ?, ?, ?, ?);");
//            pst.setString(1, tempo.Date());
//            pst.setString(2, tempo.Hours());
//            pst.setString(3, null);
//            pst.setString(4, null);
//            pst.setInt(5, idUtilizador);
//            pst.setString(6, null);
//            pst.setString(7, null);
//            pst.setDouble(8, saldoAnterior);
//            pst.setDouble(9, saldoDoDia);
//            pst.setString(10, null);
//            pst.execute();
//            liga.deconecta();
//        } catch (SQLException ex) {
//            Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
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
        jLabel2 = new javax.swing.JLabel();
        jbSair = new javax.swing.JButton();
        jbEntrar = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Entrar");
        setMinimumSize(new java.awt.Dimension(400, 410));
        setName("Entrar"); // NOI18N
        setUndecorated(true);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login de acesso");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(91, 95, 99));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Entra com suas credenciais");

        jbSair.setBackground(new java.awt.Color(217, 81, 51));
        jbSair.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbSair.setForeground(new java.awt.Color(255, 255, 255));
        jbSair.setText("Sair");
        jbSair.setBorderPainted(false);
        jbSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbSairMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbSairMouseExited(evt);
            }
        });
        jbSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSairActionPerformed(evt);
            }
        });
        jbSair.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbSairKeyPressed(evt);
            }
        });

        jbEntrar.setBackground(new java.awt.Color(56, 65, 84));
        jbEntrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbEntrar.setForeground(new java.awt.Color(255, 255, 255));
        jbEntrar.setText("Entrar");
        jbEntrar.setBorderPainted(false);
        jbEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbEntrarMouseExited(evt);
            }
        });
        jbEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEntrarActionPerformed(evt);
            }
        });
        jbEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbEntrarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbEntrarKeyTyped(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField1.setToolTipText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jPasswordField1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_user_male_circle_30px.png"))); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_password_26px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(91, 95, 99));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton1.setText("Licença");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Suporte");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("Servidor");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setForeground(new java.awt.Color(0, 0, 153));
        jButton4.setText("Esqueci a minha senha");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                                    .addComponent(jTextField1))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbSair, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(24, 24, 24))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbSair, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
        System.exit(0);

    }//GEN-LAST:event_jbSairActionPerformed

    private void jbEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEntrarMouseEntered
        this.jbEntrar.setBackground(new Color(235, 235, 235));
        this.jbEntrar.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jbEntrarMouseEntered

    private void jbSairMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbSairMouseEntered
        this.jbSair.setBackground(new Color(235, 235, 235));
        this.jbSair.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_jbSairMouseEntered

    private void jbEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEntrarMouseExited
        this.jbEntrar.setBackground(new Color(56, 65, 84));
        this.jbEntrar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbEntrarMouseExited

    private void jbSairMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbSairMouseExited
        this.jbSair.setBackground(new Color(217, 81, 51));
        this.jbSair.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbSairMouseExited

    private void jbEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEntrarActionPerformed
        //criar duas variaveis para validar as informações
        int a = 0;
        int b = 0;
        //pegar os valores dos campos
        String Y = jTextField1.getText();
        String O = jPasswordField1.getText();
        //Verificar se o jTcampo1 esta vazio
        if (jTextField1.equals("")) {

            JOptionPane.showMessageDialog(this, "Digite o nome");
            jTextField1.setBackground(Color.red);
            jTextField1.setBackground(Color.white);
            jTextField1.requestFocus(); //coloca o foco no campo
        } else {
            //se não estiver vazio atribui o valor 1 a variavel "a"
            a = 1;
            ////volta com a cor pardrão
            jTextField1.setBackground(Color.white);
            jTextField1.setForeground(Color.black);
        }
        // aqui verifica se as variaveis "a" e "b" estão com valores "1"
        if (a == 1 & b == 1) {
            //se estiver todos com valor "1" então o processo é concluido
            // aqui vai o código salvar os dados no banco
            JOptionPane.showMessageDialog(this, "salvo com sucesso");
        }
        if (jTextField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome do usuário inválido");
            jTextField1.requestFocus();
        } else if (jPasswordField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Senha inválida");
            jPasswordField1.requestFocus();
        } else {
            liga.conexao();
            try {
                liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
                liga.rs.first();
                if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {
                    int iduser = liga.rs.getInt("idutilizador");
                    Dashboard l = new Dashboard(iduser, liga.rs.getString("perfil"), liga.getCaminho());
                    l.setVisible(true);
//                    AbrirTurno(iduser, saldoAnterior, saldoDoDia);
                    dispose();
//               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
                } else {
                    jPasswordField1.requestFocus();
                    jPasswordField1.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(null, "Acesso Negado \n Senha inválida");
                }
            } catch (SQLException ex) {
                jTextField1.requestFocus();
                jTextField1.setBackground(Color.RED);
                JOptionPane.showMessageDialog(null, "Acesso Negado \n Nome do usuário inválido \n");
            }
        }
    }//GEN-LAST:event_jbEntrarActionPerformed

    private void jbEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbEntrarKeyPressed

        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Capslock ligado");
        } else {
            jLabel6.setText("Capslock desligado");
        }
        if (evt.getKeyCode() == evt.VK_ENTER) {
            int a = 0;
            int b = 0;
            //pegar os valores dos campos
            String Y = jTextField1.getText();
            String O = jPasswordField1.getText();
            //Verificar se o jTcampo1 esta vazio
            if (jTextField1.equals("")) {

                JOptionPane.showMessageDialog(this, "Digite o nome");
                jTextField1.setBackground(Color.red);
                jTextField1.setBackground(Color.white);
                jTextField1.requestFocus(); //coloca o foco no campo
            } else {
                //se não estiver vazio atribui o valor 1 a variavel "a"
                a = 1;
                ////volta com a cor pardrão
                jTextField1.setBackground(Color.white);
                jTextField1.setForeground(Color.black);
            }
            // aqui verifica se as variaveis "a" e "b" estão com valores "1"
            if (a == 1 & b == 1) {
                //se estiver todos com valor "1" então o processo é concluido
                // aqui vai o código salvar os dados no banco
                JOptionPane.showMessageDialog(this, "salvo com sucesso");
            }
            if (jTextField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do usuário inválido");
                jTextField1.requestFocus();
            } else if (jPasswordField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Senha inválida");
                jPasswordField1.requestFocus();
            } else {
                liga.conexao();
                try {
                    liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
                    liga.rs.first();
                    if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {
                        Dashboard l = new Dashboard(liga.rs.getInt("idutilizador"), liga.rs.getString("perfil"), liga.getCaminho());
                        l.setVisible(true);
                        dispose();
//               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
                    } else {
                        jPasswordField1.requestFocus();
                        jPasswordField1.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(null, "Acesso Negado \n Senha inválida");
                    }
                } catch (SQLException ex) {
                    jTextField1.requestFocus();
                    jTextField1.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(null, "Acesso Negado \n Nome do usuário inválido \n");
                }
            }
        }
    }//GEN-LAST:event_jbEntrarKeyPressed

    private void jbEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEntrarMouseClicked
        //criar duas variaveis para validar as informações

        //pegar os valores dos campos
        String Y = jTextField1.getText();
        String O = jPasswordField1.getText();
        //Verificar se o jTcampo1 esta vazio
        if (jTextField1.equals("")) {

            JOptionPane.showMessageDialog(this, "Digite o nome");
            jTextField1.setBackground(Color.red);
            jTextField1.setBackground(Color.white);
            jTextField1.requestFocus(); //coloca o foco no campo
        } else {
            //se não estiver vazio atribui o valor 1 a variavel "a"

            ////volta com a cor pardrão
            jTextField1.setBackground(Color.white);
            jTextField1.setForeground(Color.black);
        }
        // aqui verifica se as variaveis "a" e "b" estão com valores "1"

        if (jTextField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome do usuário inválido.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
            jTextField1.requestFocus();
        } else if (jPasswordField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Senha inválida.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
            jPasswordField1.requestFocus();
        } else {
            System.out.println(liga.getCaminho());
            liga.conexao();
            try {
                liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
                liga.rs.first();
                if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {
                    Dashboard l = new Dashboard(liga.rs.getInt("idutilizador"), liga.rs.getString("perfil"), liga.getCaminho());

                    l.setVisible(true);
                    dispose();
//               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
                } else {
                    jPasswordField1.requestFocus();
                    jPasswordField1.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(rootPane, "Senha inválida.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                jTextField1.requestFocus();
                jTextField1.setBackground(Color.RED);
                JOptionPane.showMessageDialog(null, "Nome do usuário inválido.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jbEntrarMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jbEntrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbEntrarKeyTyped
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Capslock ligado");
        } else {
            jLabel6.setText("Capslock desligado");
        }
    }//GEN-LAST:event_jbEntrarKeyTyped

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Capslock ligado");
        } else {
            jLabel6.setText("Capslock desligado");
        }

    }//GEN-LAST:event_formKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Capslock ligado");
        } else {
            jLabel6.setText("Capslock desligado");
        }

        if (evt.getKeyCode() == evt.VK_ENTER) {
            int a = 0;
            int b = 0;
            //pegar os valores dos campos
            String Y = jTextField1.getText();
            String O = jPasswordField1.getText();
            //Verificar se o jTcampo1 esta vazio
            if (jTextField1.equals("")) {

                JOptionPane.showMessageDialog(this, "Digite o nome");
                jTextField1.setBackground(Color.red);
                jTextField1.setBackground(Color.white);
                jTextField1.requestFocus(); //coloca o foco no campo
            } else {
                //se não estiver vazio atribui o valor 1 a variavel "a"
                a = 1;
                ////volta com a cor pardrão
                jTextField1.setBackground(Color.white);
                jTextField1.setForeground(Color.black);
            }
            // aqui verifica se as variaveis "a" e "b" estão com valores "1"
            if (a == 1 & b == 1) {
                //se estiver todos com valor "1" então o processo é concluido
                // aqui vai o código salvar os dados no banco
                JOptionPane.showMessageDialog(this, "salvo com sucesso");
            }
            if (jTextField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do usuário inválido");
                jTextField1.requestFocus();
            } else if (jPasswordField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Senha inválida");
                jPasswordField1.requestFocus();
            } else {
                liga.conexao();
                try {
                    liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
                    liga.rs.first();
                    if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {
                        Dashboard l = new Dashboard(liga.rs.getInt("idutilizador"), liga.rs.getString("perfil"), liga.getCaminho());

                        l.setVisible(true);
                        dispose();
//               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
                    } else {
                        jPasswordField1.requestFocus();
                        jPasswordField1.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(null, "Acesso Negado \n Senha inválida");
                    }
                } catch (SQLException ex) {
                    jTextField1.requestFocus();
                    jTextField1.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(null, "Acesso Negado \n Nome do usuário inválido \n");
                }
            }
        }

    }//GEN-LAST:event_jTextField1KeyPressed

    private void jPasswordField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyPressed
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Capslock ligado");
        } else {
            jLabel6.setText("Capslock desligado");
        }

        if (evt.getKeyCode() == evt.VK_ENTER) {
            int a = 0;
            int b = 0;
            //pegar os valores dos campos
            String Y = jTextField1.getText();
            String O = jPasswordField1.getText();
            //Verificar se o jTcampo1 esta vazio
            if (jTextField1.equals("")) {

                JOptionPane.showMessageDialog(this, "Digite o nome");
                jTextField1.setBackground(Color.red);
                jTextField1.setBackground(Color.white);
                jTextField1.requestFocus(); //coloca o foco no campo
            } else {
                //se não estiver vazio atribui o valor 1 a variavel "a"
                a = 1;
                ////volta com a cor pardrão
                jTextField1.setBackground(Color.white);
                jTextField1.setForeground(Color.black);
            }
            // aqui verifica se as variaveis "a" e "b" estão com valores "1"
            if (a == 1 & b == 1) {
                //se estiver todos com valor "1" então o processo é concluido
                // aqui vai o código salvar os dados no banco
                JOptionPane.showMessageDialog(this, "salvo com sucesso");
            }
            if (jTextField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do usuário inválido");
                jTextField1.requestFocus();
            } else if (jPasswordField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Senha inválida");
                jPasswordField1.requestFocus();
            } else {
                liga.conexao();
                try {
                    liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
                    liga.rs.first();
                    if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {

                        Dashboard l = new Dashboard(liga.rs.getInt("idutilizador"), liga.rs.getString("perfil"), liga.getCaminho());
                        l.setVisible(true);
                        dispose();
//               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
                    } else {
                        jPasswordField1.requestFocus();
                        jPasswordField1.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(null, "Acesso Negado \n Senha inválida");
                    }
                } catch (SQLException ex) {
                    jTextField1.requestFocus();
                    jTextField1.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(null, "Acesso Negado \n Nome do usuário inválido \n");
                }
            }
        }
    }//GEN-LAST:event_jPasswordField1KeyPressed

    private void jbSairKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbSairKeyPressed
        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
            jLabel6.setText("Capslock ligado");
        } else {
            jLabel6.setText("Capslock desligado");
        }
        if (evt.getKeyCode() == evt.VK_ENTER) {
            System.exit(0);
        }

    }//GEN-LAST:event_jbSairKeyPressed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        new licenca(liga.getCaminho()).setVisible(true);
        //      this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new configuracaoDaBD().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        new suporte().setVisible(true);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Esqueceu a sua senha e deseja recuperar?", "Recuperação de conta.", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION == resposta) {
            resposta = JOptionPane.showConfirmDialog(null, "A recuperação de conta inclui uma taxa de pagamento.\nDeseja continuar?", "Recuperação de conta.", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.YES_OPTION == resposta) {
                try {
                    liga.conexao();
                    liga.executeSql("select * from empresa");
                    if (liga.rs.first()) {
                        String empresa = liga.rs.getString("designacao");
                        String nif = liga.rs.getString("nif");
                        String telf = liga.rs.getString("telefone");
                        String email = liga.rs.getString("email");
                        if (email != null || email != "''") {
                            createNew(telf, email, nif, empresa);
                            JOptionPane.showConfirmDialog(null, "Receberá as credenciais de acesso ao sistema no email da empresa(" + email + ")", "Recuperação de conta.", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            createNew(telf, "Sem email", nif, empresa);
                            JOptionPane.showConfirmDialog(null, "Email da empresa não encontrado\nContacte o suporte técnico do aplicativo.", "Falha na Recuperação de conta.", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showConfirmDialog(null, "Email da empresa não encontrado\nContacte o suporte técnico do aplicativo.", "Falha na Recuperação de conta.", JOptionPane.ERROR_MESSAGE);
                    }

                    liga.deconecta();
                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "Email da empresa não encontrado\n" + ex + "\nContacte o suporte técnico do aplicativo", "Falha na Recuperação de conta.", JOptionPane.ERROR_MESSAGE);

//                    Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
//                    Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
                   JOptionPane.showConfirmDialog(null, "A recuperação de conta cancelada.", "Recuperação de conta.", JOptionPane.INFORMATION_MESSAGE);

                }
            } else {
                JOptionPane.showConfirmDialog(null, "A recuperação de conta cancelada.", "Recuperação de conta.", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    public void LICENCA_STATE() {
        // INICIO 
        ControloBD liga = new ControloBD();

        String SQL = "select m.modulo, e.estado, data_de_fim - current_date, data_de_ativacao, mac \n"
                + "from licenca l inner join modulo m \n"
                + "on l.idmodulo = m.idmodulo \n"
                + "inner join estado e\n"
                + "on l.idestado = e.idestado\n"
                + "inner join pc p \n"
                + "on l.idpc = p.idpc \n"
                + "WHERE p.nome = '" + this.hd.GetNAME() + "' AND data_de_ativacao <= current_date ";
        
        if (!liga.conexao()) {
            {
                SQL = "select m.modulo, e.estado, data_de_fim - current_date, data_de_ativacao, mac \n"
                        + "from licenca l inner join modulo m \n"
                        + "on l.idmodulo = m.idmodulo \n"
                        + "inner join estado e\n"
                        + "on l.idestado = e.idestado\n"
                        + "inner join pc p \n"
                        + "on l.idpc = p.idpc \n"
                        + "WHERE p.mac = '" + this.hd.GetMac() + "' AND data_de_ativacao <= current_date ";
            }
            liga.setCaminho(this.liga.getCaminho());
            liga.conexao();
        }

        liga.conexao();
        liga.executeSql(SQL);
        
        try {
            if (liga.rs!=null && liga.rs.first()) {
                int dias = liga.rs.getInt(3);
                System.out.println("Dias de acesso = " + dias);
                if (dias <= 0) {
                    jLabel2.setForeground(Color.RED);

                    jLabel2.setText("Aplicativo não licenciado.");
                    jPasswordField1.setEnabled(false);
                    jTextField1.setEnabled(false);
                    jbEntrar.setEnabled(false);
                     jbSair.setEnabled(true);
//                            UpdateLicenca(sqlUPDATE);
                } else {
                    jLabel2.setForeground(Color.BLACK);
                    jLabel2.setText("Entra com suas credenciais");
                    jPasswordField1.setEnabled(true);
                    jTextField1.setEnabled(true);
                    jbEntrar.setEnabled(true);
                    jbSair.setEnabled(true);
                }

            } else {
                jLabel2.setForeground(Color.RED);
                jLabel2.setText("Aplicativo não licenciado.");
                jPasswordField1.setEnabled(false);
                jTextField1.setEnabled(false);
                jbEntrar.setEnabled(false);
                 jbSair.setEnabled(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Reinicie a aplicação\nSe o erro continuar contacte o suporte sénior da aplicação\n", "Erro de licença", JOptionPane.ERROR_MESSAGE);
            jLabel2.setText("Aplicativo não licenciado.");
            jPasswordField1.setEnabled(true);
            jTextField1.setEnabled(true);
        }
        // FIM
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Entar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Entar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Entar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Entar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Entar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbEntrar;
    private javax.swing.JButton jbSair;
    // End of variables declaration//GEN-END:variables

    private void createNew(String telf, String email, String nif, String emp) throws IOException {
        try {
            PreparedStatement pst = liga.con.prepareStatement("Insert into utilizador (nome_utilizador, nome, nif, email, telefone, sexo, perfil, senha) values(?,?,?,?,?,?,?,?)");
            double random = Math.random();
            String user = "V" + random + "BV";
            pst.setString(1, user);
            pst.setString(2, user);
            pst.setString(3, user);
            pst.setString(4, user);
            pst.setInt(5, 999999999);
            pst.setString(6, "M");
            pst.setString(7, "Administrador");
            random = Math.random();
            String sen ="V" + random + "ED";
            pst.setString(8, sen);
            pst.execute();

            // open a connection to the site
            URL url = new URL("https://vansoftware.000webhostapp.com/");
            URLConnection con = url.openConnection();
            // activate the output
            con.setDoOutput(true);
            PrintStream ps = new PrintStream(con.getOutputStream());
            // send your parameters to your site
            ps.print("&cliente=" + emp);
            ps.print("&email=" + email);
            ps.print("&telf=" +telf);
            ps.print("&nif=" + nif);
            
            ps.print("&us=" + user);
            
            ps.print("&sn=" + sen);
            ps.print("&solicitar=" + "recup");
//                System.out.println("Key: "+secreckey.toString());

            // we have to get the input stream in order to actually send the request
            con.getInputStream();
            // print out to confirm
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                // close the print stream
                ps.close();
            }

        } catch (SQLException ex) {
//            Logger.getLogger(Entar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Sem conexão\n"+ex, "Falha na recuperaçao de senha", JOptionPane.ERROR_MESSAGE);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Sem conexão\n"+ex, "Falha na recuperaçao de senha", JOptionPane.ERROR_MESSAGE);
        }
    }
}

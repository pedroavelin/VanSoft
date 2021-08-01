/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import Controle.TextPrompt;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import Modelo.ModeloUtilizador;
import Modelo.ModeloEmpresa;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Controle.ControloBD;
import Controle.Validação;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonga
 */
public class RegistarEmpresa extends javax.swing.JFrame {
    
    int id;
    String perfil;
    private int Edmp;
    ControloBD liga = new ControloBD();
    Validação validacao = new Validação();

    /**
     * Creates new form AddUtilizador
     */
    public RegistarEmpresa() {
        try {
            initComponents();
            Toolkit T = Toolkit.getDefaultToolkit();
            this.setSize(T.getScreenSize());
            this.setExtendedState(this.MAXIMIZED_BOTH);
            this.jTextField_nomeDaFoto.setVisible(false);
            TextPrompt prompt1 = new TextPrompt("Nome da empresa", jTextField_nome_Empresa);
            TextPrompt prompt2 = new TextPrompt("Número de identificação fiscal", jTextField_nif);
            TextPrompt prompt5 = new TextPrompt("Registo comercial", jTextField_registoComercial);
            TextPrompt prompt6 = new TextPrompt("Telefone", jTextField_Telefone);
            TextPrompt prompt7 = new TextPrompt("País", jTextField_Pais);
            TextPrompt prompt8 = new TextPrompt("Razão social", jTextField_RazaoSocial);
            TextPrompt prompt9 = new TextPrompt("Província", jTextField_Provincia);
            TextPrompt prompt10 = new TextPrompt("endereço de email", jTextField_Email);
            TextPrompt prompt11 = new TextPrompt("Edifício", jTextField_Edificio);
            TextPrompt prompt12 = new TextPrompt("Codigo postal", jTextField_CodigoPostal);
            TextPrompt prompt13 = new TextPrompt("Endereço web", jTextField_WebSite);
            TextPrompt prompt14 = new TextPrompt("Rua", jTextField_Rua);
            liga.conexao();
            liga.executeSql("select * from empresa");
            if (liga.rs.first()) {
                this.jTextField_nome_Empresa.setText(liga.rs.getString("designacao"));
                this.jTextField_nif.setText(liga.rs.getString("nif"));
                this.jTextField_registoComercial.setText(liga.rs.getString("registo_comercial"));
                this.jTextField_RazaoSocial.setText(liga.rs.getString("razao_social"));
                this.jTextField_Telefone.setText(Integer.toString(liga.rs.getInt("telefone")));
                this.jTextField_Email.setText(liga.rs.getString("email"));
                this.jTextField_WebSite.setText(liga.rs.getString("web_site"));
                this.jTextField_Pais.setText(liga.rs.getString("pais"));
                this.jTextField_Provincia.setText(liga.rs.getString("provincia"));
                this.jTextField_Rua.setText(liga.rs.getString("rua"));
                this.jTextField_Edificio.setText(liga.rs.getString("edificio"));
                this.jTextField_indicativo.setText(liga.rs.getString("indicativo"));
                jComboBox1.setSelectedItem(liga.rs.getString("regime_de_iva"));
                
                this.revert(liga.rs.getBytes("foto"));
                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Incapaz de ler todos dados da empresa: ", "Erro de sistema.", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }
    
    public RegistarEmpresa(int id, String perfil, String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);
        try {
            this.jTextField_nomeDaFoto.setVisible(false);
            this.jLabel10_validacao.setVisible(false);
            this.jLabel11_validacao.setVisible(false);
            this.jLabel12_validacao.setVisible(false);
            this.jLabel13_validacao.setVisible(false);
            this.jLabel15_validacao.setVisible(false);
            this.jLabel15_validacao4.setVisible(false);
            this.jLabel15_validacao1.setVisible(false);
            TextPrompt prompt1 = new TextPrompt("Nome da empresa", jTextField_nome_Empresa);
            TextPrompt prompt2 = new TextPrompt("Número de identificação fiscal", jTextField_nif);
            TextPrompt prompt5 = new TextPrompt("Registo comercial", jTextField_registoComercial);
            TextPrompt prompt6 = new TextPrompt("Telefone", jTextField_Telefone);
            TextPrompt prompt7 = new TextPrompt("País", jTextField_Pais);
            TextPrompt prompt8 = new TextPrompt("Razão social", jTextField_RazaoSocial);
            TextPrompt prompt9 = new TextPrompt("Província", jTextField_Provincia);
            TextPrompt prompt10 = new TextPrompt("endereço de email", jTextField_Email);
            TextPrompt prompt11 = new TextPrompt("Edifício", jTextField_Edificio);
            TextPrompt prompt12 = new TextPrompt("Codigo postal", jTextField_CodigoPostal);
            TextPrompt prompt13 = new TextPrompt("Endereço web", jTextField_WebSite);
            TextPrompt prompt14 = new TextPrompt("Rua", jTextField_Rua);
            this.id = id;
            this.perfil = perfil;
            liga.conexao();
            liga.executeSql("select * from empresa");
            if (liga.rs.first()) {
                this.jTextField_nome_Empresa.setText(liga.rs.getString("designacao"));
                this.jTextField_nif.setText(liga.rs.getString("nif"));
                this.jTextField_registoComercial.setText(liga.rs.getString("registo_comercial"));
                this.jTextField_RazaoSocial.setText(liga.rs.getString("razao_social"));
                this.jTextField_Telefone.setText(Integer.toString(liga.rs.getInt("telefone")));
                this.jTextField_Email.setText(liga.rs.getString("email"));
                this.jTextField_WebSite.setText(liga.rs.getString("web_site"));
                this.jTextField_Pais.setText(liga.rs.getString("pais"));
                this.jTextField_Provincia.setText(liga.rs.getString("provincia"));
                this.jTextField_Rua.setText(liga.rs.getString("rua"));
                this.jTextField_Edificio.setText(liga.rs.getString("edificio"));
                this.jTextField_CodigoPostal.setText(liga.rs.getString("codigo_postal"));
                this.jTextField_indicativo.setText(liga.rs.getString("indicativo"));
                jComboBox1.setSelectedItem(liga.rs.getString("regime_de_iva"));
                
                this.revert(liga.rs.getBytes("foto"));
                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Incapaz de ler todos dados da empresa: " + ex, "Erro de sistema.", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    public void revert(byte[] B) {
        if (B != null) {
            File img = new File(Arrays.toString(B));
            this.jLabel_Foto.setIcon(new ImageIcon(B));
            this.photo = B;
            this.jTextField_nomeDaFoto.setText(img.getName());
        }
    }
    
    public RegistarEmpresa(ModeloEmpresa empresa) {
        initComponents();
    }
    
    public boolean UpdateEmpresa() {
        liga.conexao();
        try {
            
            if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
                String sexo = null;
                
                PreparedStatement pst = liga.con.prepareStatement("Update empresa set designacao =?, nif=?, registo_comercial=?, "
                        + "razao_social=?, telefone=?, email=?, web_site=?, pais=?,"
                        + "provincia=?, rua=?, edificio=?,codigo_postal=?, foto=?,regime_de_iva=?,indicativo=?");
                
                pst.setString(1, this.jTextField_nome_Empresa.getText());
                pst.setString(2, this.jTextField_nif.getText());
                pst.setString(3, this.jTextField_registoComercial.getText());
                pst.setString(4, this.jTextField_RazaoSocial.getText());
                pst.setInt(5, Integer.parseInt(this.jTextField_Telefone.getText()));
                pst.setString(6, this.jTextField_Email.getText());
                pst.setString(7, this.jTextField_WebSite.getText());
                pst.setString(8, this.jTextField_Pais.getText());
                pst.setString(9, this.jTextField_Provincia.getText());
                pst.setString(10, this.jTextField_Rua.getText());
                pst.setString(11, this.jTextField_Edificio.getText());
                pst.setString(12, this.jTextField_CodigoPostal.getText());
                pst.setBytes(13, this.photo);
                pst.setString(14, this.jComboBox1.getSelectedItem().toString());
                pst.setString(15, this.jTextField_indicativo.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não atualizados: " + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Sexo = new javax.swing.ButtonGroup();
        jPanel_Add_utilizador = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_nif = new javax.swing.JTextField();
        jTextField_nome_Empresa = new javax.swing.JTextField();
        jTextField_registoComercial = new javax.swing.JTextField();
        jTextField_RazaoSocial = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jTextField_nomeDaFoto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel_Foto = new javax.swing.JLabel();
        jLabel10_validacao = new javax.swing.JLabel();
        jLabel12_validacao = new javax.swing.JLabel();
        jLabel11_validacao = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_Telefone = new javax.swing.JTextField();
        jTextField_Email = new javax.swing.JTextField();
        jTextField_WebSite = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField_Telefone3 = new javax.swing.JTextField();
        jTextField_Pais = new javax.swing.JTextField();
        jTextField_Provincia = new javax.swing.JTextField();
        jTextField_Rua = new javax.swing.JTextField();
        jTextField_Edificio = new javax.swing.JTextField();
        jTextField_CodigoPostal = new javax.swing.JTextField();
        jLabel15_validacao = new javax.swing.JLabel();
        jLabel13_validacao = new javax.swing.JLabel();
        jLabel15_validacao1 = new javax.swing.JLabel();
        jLabel15_validacao4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField_indicativo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Add_utilizador.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Add_utilizador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel20.setBackground(new java.awt.Color(27, 167, 125));

        jLabel20.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Dados da empresa");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(217, 81, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Fechar");
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(56, 65, 84));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel1.setText("NIF *");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Nome *");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("Registo comercial *");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Razão social");

        jTextField_nif.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_nome_Empresa.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_registoComercial.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_RazaoSocial.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_upload_to_ftp_48px.png"))); // NOI18N
        jButton3.setText("Carregar");
        jButton3.setToolTipText("Carregar uma foto");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField_nomeDaFoto.setEditable(false);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(208, 212));
        jPanel1.setMinimumSize(new java.awt.Dimension(208, 212));
        jPanel1.setName(""); // NOI18N

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(208, 212));

        jLabel_Foto.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_Foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_user_120px.png"))); // NOI18N

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Foto, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_Foto, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jDesktopPane1.setLayer(jLabel_Foto, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel10_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao.setText("jLabel10");

        jLabel12_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel12_validacao.setText("jLabel10");

        jLabel11_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel11_validacao.setText("jLabel10");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 118, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(116, 116, 116))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10_validacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addComponent(jTextField_RazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_registoComercial)
                            .addComponent(jTextField_nome_Empresa)
                            .addComponent(jTextField_nif)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_nome_Empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10_validacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_nif, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12_validacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_registoComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11_validacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_RazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel8.setText("Web site");

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel7.setText("Telefone *");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Email");

        jTextField_Telefone.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Telefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_TelefoneActionPerformed(evt);
            }
        });

        jTextField_Email.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_EmailActionPerformed(evt);
            }
        });

        jTextField_WebSite.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_WebSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_WebSiteActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel6.setText("Fax");

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel9.setText("País *");

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel10.setText("Província *");

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel11.setText("Rua");

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel12.setText("Edifício");

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel13.setText("Código Postal");

        jTextField_Telefone3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Telefone3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Telefone3ActionPerformed(evt);
            }
        });

        jTextField_Pais.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Pais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_PaisActionPerformed(evt);
            }
        });

        jTextField_Provincia.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Provincia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_ProvinciaActionPerformed(evt);
            }
        });

        jTextField_Rua.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Rua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_RuaActionPerformed(evt);
            }
        });

        jTextField_Edificio.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Edificio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_EdificioActionPerformed(evt);
            }
        });

        jTextField_CodigoPostal.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_CodigoPostal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_CodigoPostalActionPerformed(evt);
            }
        });

        jLabel15_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel15_validacao.setText("jLabel10");

        jLabel13_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel13_validacao.setText("jLabel10");

        jLabel15_validacao1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel15_validacao1.setText("jLabel10");

        jLabel15_validacao4.setForeground(new java.awt.Color(153, 0, 0));
        jLabel15_validacao4.setText("jLabel10");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Regime geral", "Regime simplificado", "Regime de exclusão" }));

        jLabel14.setText("Regime de IVA");

        jLabel15.setText("Indicativo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField_Telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_WebSite, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_Telefone3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Pais, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13_validacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Rua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Provincia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField_Edificio, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15_validacao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15_validacao1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15_validacao4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_CodigoPostal, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jTextField_indicativo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_Telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(2, 2, 2)
                .addComponent(jLabel15_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField_WebSite)
                                    .addComponent(jLabel8))
                                .addGap(38, 38, 38))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField_Telefone3))))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Pais)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15_validacao1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Provincia)
                    .addComponent(jLabel10))
                .addGap(1, 1, 1)
                .addComponent(jLabel15_validacao4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_Rua, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Edificio)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_CodigoPostal)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_indicativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel_Add_utilizadorLayout = new javax.swing.GroupLayout(jPanel_Add_utilizador);
        jPanel_Add_utilizador.setLayout(jPanel_Add_utilizadorLayout);
        jPanel_Add_utilizadorLayout.setHorizontalGroup(
            jPanel_Add_utilizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Add_utilizadorLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel_Add_utilizadorLayout.setVerticalGroup(
            jPanel_Add_utilizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Add_utilizadorLayout.createSequentialGroup()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1008, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_utilizador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_utilizador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        this.jButton2.setBackground(new Color(235, 235, 235));
        this.jButton2.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        this.jButton2.setBackground(new Color(217, 81, 51));
        this.jButton2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        new Principal1(this.id, this.perfil, liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        this.jButton1.setBackground(new Color(235, 235, 235));
        this.jButton1.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        this.jButton1.setBackground(new Color(56, 65, 84));
        this.jButton1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if (f != null) {
            jLabel_Foto.setIcon(new ImageIcon(f.toString()));
            filename = f.getAbsolutePath();
            jTextField_nomeDaFoto.setText(filename);
            try {
                File img = new File(filename);
                FileInputStream fis = new FileInputStream(img);
                ByteArrayOutputStream bOs = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                for (int redNum; (redNum = fis.read(buffer)) != -1;) {
                    bOs.write(buffer, 0, redNum);
                    
                }
                this.photo = bOs.toByteArray();
                System.out.println(photo.length);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Não foi selecionado nenhuma imagem.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField_TelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_TelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_TelefoneActionPerformed

    private void jTextField_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_EmailActionPerformed

    private void jTextField_WebSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_WebSiteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_WebSiteActionPerformed

    private void jTextField_Telefone3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Telefone3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_Telefone3ActionPerformed

    private void jTextField_PaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_PaisActionPerformed

    private void jTextField_ProvinciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ProvinciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_ProvinciaActionPerformed

    private void jTextField_RuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_RuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_RuaActionPerformed

    private void jTextField_EdificioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_EdificioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_EdificioActionPerformed

    private void jTextField_CodigoPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_CodigoPostalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_CodigoPostalActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean r1, r2, r3, r4, r5, r6, r7, r8;
        if (!this.jTextField_nome_Empresa.getText().isEmpty()) {
            this.jLabel10_validacao.setVisible(false);
            r7 = true;
        } else {
            this.jLabel10_validacao.setText("Este campo não pode estar vazio");
            this.jLabel10_validacao.setVisible(true);
            r7 = false;
        }
        
        if (validacao.ValidarTelef(this.jTextField_Telefone.getText())) {
            r1 = true;
            this.jLabel15_validacao.setVisible(false);
        } else {
            this.jLabel15_validacao.setText("Entrada inválida. Ex: +244999111222333");
            this.jLabel15_validacao.setVisible(true);
            r1 = false;
        }
        
        if (validacao.validarEmail(this.jTextField_Email.getText()) || this.jTextField_Email.getText().isEmpty()) {
            this.jLabel13_validacao.setVisible(false);
            r2 = true;
            
        } else {
            this.jLabel13_validacao.setText("Entrada inválida. Ex: vvv@cramer.co");
            this.jLabel13_validacao.setVisible(true);
            r2 = false;
        }
        if (!this.jTextField_nif.getText().isEmpty()) {
            this.jLabel12_validacao.setVisible(false);
            r3 = true;
            
        } else {
            this.jLabel12_validacao.setText("Este campo não pode estar vazio");
            this.jLabel12_validacao.setVisible(true);
            r3 = false;
        }
        if (!this.jTextField_registoComercial.getText().isEmpty()) {
            this.jLabel11_validacao.setVisible(false);
            r4 = true;
        } else {
            this.jLabel11_validacao.setText("Este campo não pode estar vazio");
            this.jLabel11_validacao.setVisible(true);
            r4 = false;
        }
        if (!this.jTextField_Provincia.getText().isEmpty()) {
            this.jLabel15_validacao4.setVisible(false);
            r5 = true;
        } else {
            this.jLabel15_validacao4.setText("Este campo não pode estar vazio");
            this.jLabel15_validacao4.setVisible(true);
            r5 = false;
        }
        if (!this.jTextField_Pais.getText().isEmpty()) {
            this.jLabel15_validacao1.setVisible(false);
            r6 = true;
        } else {
            this.jLabel15_validacao1.setText("Este campo não pode estar vazio");
            this.jLabel15_validacao1.setVisible(true);
            r6 = false;
        }
        
        int indSize = jTextField_indicativo.getText().length();
        
        if (r1 && r2 && r3 && r4 && r5 && r6 && r7) {
            if (indSize > 0 && indSize <= 4) {
                
                boolean UpdateEmpresa = UpdateEmpresa();
                RegistarEmpresa EM = new RegistarEmpresa(this.id, this.perfil, liga.getCaminho());
                EM.setSize(this.getSize());
                EM.setVisible(true);
                this.dispose();
            } else {
                jTextField_indicativo.requestFocus();
                JOptionPane.showMessageDialog(null, "O campo 'Indicativo' de fatura não pode estar vazio e deve ter no máximo 4 catacteres.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            
        }

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(RegistarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistarEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistarEmpresa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel10_validacao;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel11_validacao;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel12_validacao;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel13_validacao;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel15_validacao;
    private javax.swing.JLabel jLabel15_validacao1;
    private javax.swing.JLabel jLabel15_validacao4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Foto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_Add_utilizador;
    private javax.swing.JTextField jTextField_CodigoPostal;
    private javax.swing.JTextField jTextField_Edificio;
    private javax.swing.JTextField jTextField_Email;
    private javax.swing.JTextField jTextField_Pais;
    private javax.swing.JTextField jTextField_Provincia;
    private javax.swing.JTextField jTextField_RazaoSocial;
    private javax.swing.JTextField jTextField_Rua;
    private javax.swing.JTextField jTextField_Telefone;
    private javax.swing.JTextField jTextField_Telefone3;
    private javax.swing.JTextField jTextField_WebSite;
    private javax.swing.JTextField jTextField_indicativo;
    private javax.swing.JTextField jTextField_nif;
    private javax.swing.JTextField jTextField_nomeDaFoto;
    private javax.swing.JTextField jTextField_nome_Empresa;
    private javax.swing.JTextField jTextField_registoComercial;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;
}

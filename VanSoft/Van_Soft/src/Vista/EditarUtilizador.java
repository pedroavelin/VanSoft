/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import java.awt.Color;
import Controle.TextPrompt;
import Controle.Validação;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import Modelo.ModeloUtilizador;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class EditarUtilizador extends javax.swing.JFrame {

    private int id;
    private String perfil;
    private int idUP;
    ControloBD liga = new ControloBD();
    Validação validacao = new Validação();

    /**
     * Creates new form Utilizador
     */
    public EditarUtilizador() {
        initComponents();
        TextPrompt prompt1 = new TextPrompt("Nome de utilizador", jTextField_nome_UTILIZADOR);
        TextPrompt prompt2 = new TextPrompt("Nome completo", jTextField_nome);
        TextPrompt prompt3 = new TextPrompt("Senha", jPasswordField_senha);
        TextPrompt prompt4 = new TextPrompt("Confirmar senha", jPasswordField_confirmarSenha);
        TextPrompt prompt5 = new TextPrompt("Número de Identificação Fiscal", jTextField_NIF);
        TextPrompt prompt6 = new TextPrompt("Número de telefone", jTextField_Telefone);
        TextPrompt prompt7 = new TextPrompt("Nome de utilizador", jTextField_nome_UTILIZADOR);
        TextPrompt prompt8 = new TextPrompt("endereço de email", jTextField_email);

    }

    /**
     * Creates new form Utilizador
     *
     * @param User
     * @param idutilizador
     * @param perfil
     */
    public EditarUtilizador(ModeloUtilizador User, int idutilizador, String perfil, String caminho) throws SQLException {
        initComponents();
        liga.setCaminho(caminho);
        this.jPanel3.setVisible(false);
        jLabel10_validacao.setVisible(false);
        jLabel11_validacao.setVisible(false);
        jLabel12_validacao.setVisible(false);
        jLabel13_validacao.setVisible(false);
        jLabel15_validacao.setVisible(false);
        TextPrompt prompt1 = new TextPrompt("Nome de utilizador", jTextField_nome_UTILIZADOR);
        TextPrompt prompt2 = new TextPrompt("Nome completo", jTextField_nome);
        TextPrompt prompt3 = new TextPrompt("Senha", jPasswordField_senha);
        TextPrompt prompt4 = new TextPrompt("Confirmar senha", jPasswordField_confirmarSenha);
        TextPrompt prompt5 = new TextPrompt("Número de Identificação Fiscal", jTextField_NIF);
        TextPrompt prompt6 = new TextPrompt("Número de telefone", jTextField_Telefone);
        TextPrompt prompt7 = new TextPrompt("Nome de utilizador", jTextField_nome_UTILIZADOR);
        TextPrompt prompt8 = new TextPrompt("endereço de email", jTextField_email);
        this.id = idutilizador;
        this.perfil = perfil;
        this.idUP = User.getIdUtilizador();
        liga.conexao();
//              
        liga.executeSql("select * from utilizador WHERE idutilizador=" + User.getIdUtilizador());
        if (liga.rs.first()) {
            this.jTextField_nome_UTILIZADOR.setText(liga.rs.getString("nome_utilizador"));
            this.jTextField_nome.setText(liga.rs.getString("nome"));
            this.jTextField_NIF.setText(liga.rs.getString("nif"));
            this.jTextField_email.setText(liga.rs.getString("email"));
            this.jTextField_Telefone.setText(Integer.toString(liga.rs.getInt("telefone")));

            String p = liga.rs.getString("perfil");
            String s = liga.rs.getString("sexo");
            if (s.equals("F")) {
                jRadioButton_SF.setSelected(true);
                jRadioButton_SM.setSelected(false);
            } else {
                jRadioButton_SM.setSelected(true);
                jRadioButton_SF.setSelected(false);
            }

            if (p == "Administrador") {
                jComboBox_Perfil.setSelectedIndex(0);
            } else if (p == "Secretário") {
                jComboBox_Perfil.setSelectedIndex(1);
            } else {
                jComboBox_Perfil.setSelectedIndex(2);
            }

            liga.deconecta();
        }
    }

    public boolean UpdateUtilizador() {
        liga.conexao();
        try {
            
            
                    String sexo = null;
                    if (jRadioButton_SF.isSelected()) {
                        sexo = "F";
                    } else {
                        sexo = "M";
                    }
                    int Tel = Integer.parseInt(jTextField_Telefone.getText());
                    System.out.println(Tel);
                    Modelo.ModeloUtilizador MU = new ModeloUtilizador(jTextField_nome_UTILIZADOR.getText(), jTextField_nome.getText(),
                    jTextField_NIF.getText(), jTextField_email.getText(), sexo, jComboBox_Perfil.getSelectedItem().toString(), jPasswordField_senha.getText(),
                    Tel);

                    PreparedStatement pst = liga.con.prepareStatement("Update utilizador set nome_utilizador =?, nome=?, nif=?, email=?, telefone=?, sexo=?, perfil=? WHERE idutilizador=?");
                    pst.setString(1, MU.getNome_utilizador());
                    pst.setString(2, MU.getNome());
                    pst.setString(3, MU.getNif());
                    pst.setString(4, MU.getEmail());
                    pst.setInt(5, MU.getTelefone());
                    pst.setString(6, MU.getSexo());
                    pst.setString(7, MU.getPerfil());
                    pst.setInt(8, this.idUP);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                    this.jTextField_nome_UTILIZADOR.setText(null);
                    jTextField_nome_UTILIZADOR.setText(null); 
                    jTextField_nome.setText(null);
                    jTextField_NIF.setText(null);
                    jTextField_email.setText(null);
                 
             
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não atualizados com sucesso." + ex);
        }
        liga.deconecta();

        return false;
    }
    
     public void revert(byte []B) {
        
        File img = new File(Arrays.toString(B));
        this.jLabel_Foto.setIcon(new ImageIcon(B));
        this.jTextField_nomeDaFoto.setText(img.getName());
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
        jPanel_Editar_utilizador = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_nome = new javax.swing.JTextField();
        jTextField_nome_UTILIZADOR = new javax.swing.JTextField();
        jTextField_NIF = new javax.swing.JTextField();
        jTextField_email = new javax.swing.JTextField();
        jTextField_Telefone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton_SM = new javax.swing.JRadioButton();
        jRadioButton_SF = new javax.swing.JRadioButton();
        jComboBox_Perfil = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10_validacao = new javax.swing.JLabel();
        jLabel11_validacao = new javax.swing.JLabel();
        jLabel12_validacao = new javax.swing.JLabel();
        jLabel13_validacao = new javax.swing.JLabel();
        jLabel15_validacao = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel_Foto = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField_nomeDaFoto = new javax.swing.JTextField();
        jPasswordField_confirmarSenha = new javax.swing.JPasswordField();
        jPasswordField_senha = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Editar_utilizador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Editar_utilizador.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(27, 167, 125));

        jLabel20.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Editar utilizador ");

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

        jPanel_Editar_utilizador.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 900, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(91, 95, 99));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Editar credenciais de entrada");

        jButton2.setBackground(new java.awt.Color(217, 81, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Fechar");
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
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
        jLabel1.setText("Nome");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Nome de usuário");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("NIF");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Telefone");

        jTextField_nome.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_nome_UTILIZADOR.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_NIF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_email.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_Telefone.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel6.setText("Sexo");

        Sexo.add(jRadioButton_SM);
        jRadioButton_SM.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_SM.setSelected(true);
        jRadioButton_SM.setText("M");
        jRadioButton_SM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_SMActionPerformed(evt);
            }
        });

        Sexo.add(jRadioButton_SF);
        jRadioButton_SF.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_SF.setText("F");

        jComboBox_Perfil.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jComboBox_Perfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Secretário", "Operador de caixa" }));

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel9.setText("Perfil de acesso");

        jLabel10_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao.setText("jLabel10");

        jLabel11_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel11_validacao.setText("jLabel10");

        jLabel12_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel12_validacao.setText("jLabel10");

        jLabel13_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel13_validacao.setText("jLabel10");

        jLabel15_validacao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel15_validacao.setText("jLabel10");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(59, 59, 59)
                        .addComponent(jRadioButton_SM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton_SF))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_Telefone, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jTextField_email, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jTextField_NIF, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(jTextField_nome_UTILIZADOR)
                            .addComponent(jTextField_nome)
                            .addComponent(jLabel12_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel10_validacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(118, Short.MAX_VALUE)
                        .addComponent(jLabel15_validacao, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_nome_UTILIZADOR, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel10_validacao)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel11_validacao)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_NIF, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12_validacao)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13_validacao)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel15_validacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_SM)
                    .addComponent(jRadioButton_SF)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox_Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(208, 212));
        jPanel1.setMinimumSize(new java.awt.Dimension(208, 212));
        jPanel1.setName(""); // NOI18N

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(208, 212));

        jLabel_Foto.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_Foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_user_120px.png"))); // NOI18N

        jDesktopPane1.setLayer(jLabel_Foto, javax.swing.JLayeredPane.DEFAULT_LAYER);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jButton3))
                    .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 68, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPasswordField_confirmarSenha.setEditable(false);
        jPasswordField_confirmarSenha.setBackground(new java.awt.Color(204, 204, 204));

        jPasswordField_senha.setEditable(false);
        jPasswordField_senha.setBackground(new java.awt.Color(204, 204, 204));

        jLabel7.setText("Senha");

        jLabel8.setText("Confirmar senha");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPasswordField_senha)
                                    .addComponent(jPasswordField_confirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 53, Short.MAX_VALUE))))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jPasswordField_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jPasswordField_confirmarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        jPanel_Editar_utilizador.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 900, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 903, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Editar_utilizador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Editar_utilizador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        new Principal(this.id, this.perfil,liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        this.jButton1.setBackground(new Color(235, 235, 235));
        this.jButton1.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        this.jButton1.setBackground(new Color(56, 65, 84));
        this.jButton1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton1MouseExited

    private void jRadioButton_SMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_SMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton_SMActionPerformed

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
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    
    
    
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean r1, r2, r3, r4, r5;
        if (!this.jTextField_nome_UTILIZADOR.getText().isEmpty()) {
            this.jLabel10_validacao.setVisible(false);
            r5 = true;
        } else {
            this.jLabel10_validacao.setText("Este campo não pode estar vazio");
            this.jLabel10_validacao.setVisible(true);
            r5 = false;
        }

        if (validacao.ValidarTelef(this.jTextField_Telefone.getText())) {
            r1 = true;
            this.jLabel15_validacao.setVisible(false);
        } else {
            this.jLabel15_validacao.setText("Entrada inválida. Ex: +244999111222");
            this.jLabel15_validacao.setVisible(true);
            r1 = false;
        }

        if (validacao.validarEmail(this.jTextField_email.getText())) {
            this.jLabel13_validacao.setVisible(false);
            r2 = true;

        } else {
            this.jLabel13_validacao.setText("Entrada inválida. Ex: vvv@cramer.co");
            this.jLabel13_validacao.setVisible(true);
            r2 = false;
        }
        if (!this.jTextField_NIF.getText().isEmpty()) {
            this.jLabel12_validacao.setVisible(false);
            r3 = true;

        } else {
            this.jLabel12_validacao.setText("Este campo não pode estar vazio");
            this.jLabel12_validacao.setVisible(true);
            r3 = false;
        }
        if (!this.jTextField_nome.getText().isEmpty()) {
            this.jLabel11_validacao.setVisible(false);
            r4 = true;
        } else {
            this.jLabel11_validacao.setText("Este campo não pode estar vazio");
            this.jLabel11_validacao.setVisible(true);
            r4 = false;
        }

       if (r1 && r2 && r3 && r4 && r5) {
                    UpdateUtilizador();
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
            java.util.logging.Logger.getLogger(EditarUtilizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarUtilizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarUtilizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarUtilizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarUtilizador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox_Perfil;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10_validacao;
    private javax.swing.JLabel jLabel11_validacao;
    private javax.swing.JLabel jLabel12_validacao;
    private javax.swing.JLabel jLabel13_validacao;
    private javax.swing.JLabel jLabel15_validacao;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JPanel jPanel_Editar_utilizador;
    private javax.swing.JPasswordField jPasswordField_confirmarSenha;
    private javax.swing.JPasswordField jPasswordField_senha;
    private javax.swing.JRadioButton jRadioButton_SF;
    private javax.swing.JRadioButton jRadioButton_SM;
    private javax.swing.JTextField jTextField_NIF;
    private javax.swing.JTextField jTextField_Telefone;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_nome;
    private javax.swing.JTextField jTextField_nomeDaFoto;
    private javax.swing.JTextField jTextField_nome_UTILIZADOR;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;
}

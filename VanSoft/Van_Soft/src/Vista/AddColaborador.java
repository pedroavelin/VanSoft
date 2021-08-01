/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import Controle.TextPrompt;
import Controle.ControloBD;
import Controle.Validação;
import Modelo.ModeloColaborador;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class AddColaborador extends javax.swing.JFrame {

    /**
     * Creates new form AddUtilizador
     */
    int id;
    String perfil;
    ControloBD liga = new ControloBD();
    Validação validacao = new Validação();

    public AddColaborador() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        TextPrompt prompt1 = new TextPrompt("Nome completo", jTextField_nome);
        TextPrompt prompt2 = new TextPrompt("Número do Bilhete de identidade", jTextField_BI);
        TextPrompt prompt3 = new TextPrompt("Função", jTextField_funcao);
        TextPrompt prompt4 = new TextPrompt("Número bancário internacional", jTextField_ibam);
        TextPrompt prompt5 = new TextPrompt("Número de Identificação Fiscal", jTextField_NIF);
        TextPrompt prompt6 = new TextPrompt("Telefone", jTextField_Telefone1);
        TextPrompt prompt7 = new TextPrompt("Telefone alternativo", jTextField_Telefone2);
        TextPrompt prompt8 = new TextPrompt("endereço de email", jTextField_email);
        this.jLabel10_validacao_bi.setVisible(false);
        this.jLabel10_validacao_funcao.setVisible(false);
        this.jLabel10_validacao_email.setVisible(false);
        this.jLabel10_validacao_telf1.setVisible(false);
        this.jLabel10_validacao_telf2.setVisible(false);
        this.jLabel10_validacao_nome.setVisible(false);
         this.jLabel10_validacao_nif.setVisible(false);

    }

    public AddColaborador(int id, String perfil, String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
         liga.setCaminho(caminho);
        TextPrompt prompt1 = new TextPrompt("Nome completo", jTextField_nome);
        TextPrompt prompt2 = new TextPrompt("Número do Bilhete de identidade", jTextField_BI);
        TextPrompt prompt3 = new TextPrompt("Função", jTextField_funcao);
        TextPrompt prompt4 = new TextPrompt("Número bancário internacional", jTextField_ibam);
        TextPrompt prompt5 = new TextPrompt("Número de Identificação Fiscal", jTextField_NIF);
        TextPrompt prompt6 = new TextPrompt("Telefone", jTextField_Telefone1);
        TextPrompt prompt7 = new TextPrompt("Telefone alternativo", jTextField_Telefone2);
        TextPrompt prompt8 = new TextPrompt("endereço de email", jTextField_email);
        this.id = id;
        this.perfil = perfil;
        this.jLabel10_validacao_bi.setVisible(false);
        this.jLabel10_validacao_funcao.setVisible(false);
        this.jLabel10_validacao_email.setVisible(false);
        this.jLabel10_validacao_telf1.setVisible(false);
        this.jLabel10_validacao_telf2.setVisible(false);
        this.jLabel10_validacao_nome.setVisible(false);
         this.jLabel10_validacao_nif.setVisible(false);
         
         
         
         
    }

    public boolean CreateColaborador() {
        liga.conexao();
        boolean RESULT = false;
        try {
            String sexo;
            if (jRadioButton_SF.isSelected()) {
                sexo = "F";
            } else {
                sexo = "M";
            }
            String tel1= jTextField_Telefone1.getText();
            String tel2= jTextField_Telefone2.getText();
            ModeloColaborador MC = new ModeloColaborador(tel1, tel2, this.jTextField_nome.getText(), jTextField_BI.getText(), jTextField_NIF.getText(),
                    jTextField_email.getText(), sexo, jTextField_nomeDaFoto.getText(), jTextField_ibam.getText(), jTextField_funcao.getText());
            PreparedStatement pst = liga.con.prepareStatement("Insert into colaborador "
                    + "(nome_completo,bi, nif, email, sexo, foto, ibam,telefone1,telefone2,funcao,nome_da_foto) values(?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, MC.getNome_completo());
            pst.setString(2, MC.getBi());
            pst.setString(3, MC.getNif());
            pst.setString(4, MC.getEmail());
            pst.setString(5, MC.getSexo());
            pst.setBytes(6, this.photo);
            pst.setString(7, MC.getIbam());
            pst.setString(8, MC.getTelefone1());
            pst.setString(9, MC.getTelefone2());
            pst.setString(10, MC.getFuncao());
            pst.setString(11, MC.getFoto());
            
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            RESULT = true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não inseridos \n" + ex, "Aviso ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        return RESULT;
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
        jPanel_Add_colaborador = new javax.swing.JPanel();
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
        jTextField_BI = new javax.swing.JTextField();
        jTextField_nome = new javax.swing.JTextField();
        jTextField_NIF = new javax.swing.JTextField();
        jTextField_email = new javax.swing.JTextField();
        jTextField_Telefone1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton_SM = new javax.swing.JRadioButton();
        jRadioButton_SF = new javax.swing.JRadioButton();
        jTextField_ibam = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField_Telefone2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_funcao = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10_validacao_nome = new javax.swing.JLabel();
        jLabel10_validacao_bi = new javax.swing.JLabel();
        jLabel10_validacao_nif = new javax.swing.JLabel();
        jLabel10_validacao_email = new javax.swing.JLabel();
        jLabel10_validacao_telf1 = new javax.swing.JLabel();
        jLabel10_validacao_funcao = new javax.swing.JLabel();
        jLabel10_validacao_telf2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel_Foto = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField_nomeDaFoto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Add_colaborador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Add_colaborador.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(27, 167, 125));

        jLabel20.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Adicionar Colaborador ");

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

        jPanel_Add_colaborador.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 900, -1));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(91, 95, 99));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Registo de um novo colaborador");

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
        jLabel1.setText("BI *");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Nome completo *");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("NIF *");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Telefone 1 *");

        jTextField_BI.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_nome.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_NIF.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_email.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_Telefone1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

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

        jTextField_ibam.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel10.setText("IBAM");

        jTextField_Telefone2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel7.setText("Telefone 2");

        jTextField_funcao.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel8.setText("Função *");

        jLabel10_validacao_nome.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_nome.setText("jLabel10");

        jLabel10_validacao_bi.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_bi.setText("jLabel10");

        jLabel10_validacao_nif.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_nif.setText("jLabel10");

        jLabel10_validacao_email.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_email.setText("jLabel10");

        jLabel10_validacao_telf1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_telf1.setText("jLabel10");

        jLabel10_validacao_funcao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_funcao.setText("jLabel10");

        jLabel10_validacao_telf2.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_telf2.setText("jLabel10");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel10)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField_Telefone2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                        .addGap(132, 132, 132))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel10_validacao_funcao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                        .addComponent(jLabel10_validacao_nif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_NIF)
                            .addComponent(jTextField_nome)
                            .addComponent(jTextField_BI)
                            .addComponent(jTextField_email)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField_Telefone1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton_SM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton_SF)
                                .addGap(18, 18, 18))
                            .addComponent(jTextField_funcao)
                            .addComponent(jLabel10_validacao_nome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10_validacao_bi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10_validacao_email, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_ibam)
                            .addComponent(jLabel10_validacao_telf2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel10_validacao_telf1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel10_validacao_nome)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_BI, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jLabel10_validacao_bi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_NIF, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel10_validacao_nif)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10_validacao_email)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Telefone1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_SM)
                    .addComponent(jRadioButton_SF)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10_validacao_telf1)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Telefone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10_validacao_telf2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_ibam, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_funcao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jLabel10_validacao_funcao)
                .addContainerGap())
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(255, 255, 255)
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
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );

        jPanel_Add_colaborador.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 900, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 903, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_colaborador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_colaborador, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE))
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
        new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
         boolean r1, r2, r3, r4, r5,r6,r7;
         if (!this.jTextField_funcao.getText().isEmpty()) {
            this.jLabel10_validacao_funcao.setVisible(false);
            r7 = true;

        } else {
            this.jLabel10_validacao_funcao.setText("Este campo não de estar vazio.");
            this.jLabel10_validacao_funcao.setVisible(true);
            r7 = false;
        }
        if (!this.jTextField_nome.getText().isEmpty()) {
            this.jLabel10_validacao_nome.setVisible(false);
            r5 = true;
        } else {
            this.jLabel10_validacao_nome.setText("Este campo não pode estar vazio");
            this.jLabel10_validacao_nome.setVisible(true);
            r5 = false;
        }

        if (validacao.ValidarTelef(this.jTextField_Telefone1.getText())) {
            r1 = true;
            this.jLabel10_validacao_telf1.setVisible(false);
        } else {
            this.jLabel10_validacao_telf1.setText("Entrada inválida. Ex: +244999111222333");
            this.jLabel10_validacao_telf1.setVisible(true);
            r1 = false;
        }
        if (this.jTextField_Telefone2.getText().isEmpty()|| validacao.ValidarTelef(this.jTextField_Telefone2.getText())) {
              r6 = true;
            this.jLabel10_validacao_telf2.setVisible(false);
        } else {
            this.jLabel10_validacao_telf2.setText("Entrada inválida. Ex: +244999111222333");
            this.jLabel10_validacao_telf2.setVisible(true);
              r6 = false;
        }

        if (this.jTextField_email.getText().isEmpty() || validacao.validarEmail(this.jTextField_email.getText())) {
            this.jLabel10_validacao_email.setVisible(false);
            r2 = true;

        } else {
            this.jLabel10_validacao_email.setText("Entrada inválida. Ex: vvv@cramer.co");
            this.jLabel10_validacao_email.setVisible(true);
            r2 = false;
        }
        if (validacao.validarNbi(this.jTextField_BI.getText())) {
            this.jLabel10_validacao_bi.setVisible(false);
            r3 = true;

        } else {
            this.jLabel10_validacao_bi.setText("Entrada inválida. ");
            this.jLabel10_validacao_bi.setVisible(true);
            r3 = false;
        }
        if (this.jTextField_NIF.getText().isEmpty() || validacao.validarNbi(this.jTextField_NIF.getText())) {
            this.jLabel10_validacao_nif.setVisible(false);
            r4 = true;
        } else {
            this.jLabel10_validacao_nif.setText("Entrada inválida. ");
            this.jLabel10_validacao_nif.setVisible(true);
            r4 = false;
        }
       

        
                if (r1 && r2 && r3 && r4 && r5&&r6) {
                    CreateColaborador();
                }
            
        
//        new AddColaborador(this.id, this.perfil).setVisible(true);
//        this.dispose();
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
            java.util.logging.Logger.getLogger(AddColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddColaborador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel10_validacao_bi;
    private javax.swing.JLabel jLabel10_validacao_email;
    private javax.swing.JLabel jLabel10_validacao_funcao;
    private javax.swing.JLabel jLabel10_validacao_nif;
    private javax.swing.JLabel jLabel10_validacao_nome;
    private javax.swing.JLabel jLabel10_validacao_telf1;
    private javax.swing.JLabel jLabel10_validacao_telf2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_Foto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_Add_colaborador;
    private javax.swing.JRadioButton jRadioButton_SF;
    private javax.swing.JRadioButton jRadioButton_SM;
    private javax.swing.JTextField jTextField_BI;
    private javax.swing.JTextField jTextField_NIF;
    private javax.swing.JTextField jTextField_Telefone1;
    private javax.swing.JTextField jTextField_Telefone2;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_funcao;
    private javax.swing.JTextField jTextField_ibam;
    private javax.swing.JTextField jTextField_nome;
    private javax.swing.JTextField jTextField_nomeDaFoto;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;
    
     public void revert(byte []B) {
        
        File img = new File(Arrays.toString(B));
        this.jLabel_Foto.setIcon(new ImageIcon(B));
        this.jTextField_nomeDaFoto.setText(img.getName());
    }

}

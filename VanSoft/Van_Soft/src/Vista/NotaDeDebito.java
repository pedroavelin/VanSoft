/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import Controle.Tempo;
import java.awt.Color;
import Controle.TextPrompt;
import Modelo.ModeloCliente;
import Modelo.ModeloEmpresa;
import Modelo.ModeloNota;
import Modelo.ModeloPDF;
import Modelo.ModeloUtilizador;
import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Gonga
 */
public class NotaDeDebito extends javax.swing.JFrame {

    Controle.ControloBD liga = new ControloBD();
    int id;
    int id_turno = -1;
    int numero = 0;
    String perfil;
    Tempo tem = new Tempo();
    DefaultListModel<String> model_List;
    DefaultListModel<String> model_List2;
    DefaultListModel<String> model_List3;
    DefaultListModel<String> model_List4;

    ModeloCliente MC = new ModeloCliente(-1);
    ModeloUtilizador MU = new ModeloUtilizador();
    ModeloEmpresa ME = new ModeloEmpresa();
    private double Total;

    /**
     * Creates new form AddUtilizador
     */
    public NotaDeDebito() {
        initComponents();
        model_List = new <String> DefaultListModel();
        model_List2 = new <String> DefaultListModel();
        model_List3 = new <String> DefaultListModel();
        model_List4 = new <String> DefaultListModel();
        jListDoc.setModel(model_List);
        jListCliente.setModel(model_List2);
        jListOrdem.setModel(model_List3);
        jList4.setModel(model_List4);
    }

    public NotaDeDebito(int id, String perfil, String caminho) {
        initComponents();
        liga.setCaminho(caminho);
        try {
//            initComponents();
            this.id = id;
            this.perfil = perfil;

            model_List = new <String> DefaultListModel();
            model_List2 = new <String> DefaultListModel();
            model_List3 = new <String> DefaultListModel();
            model_List4 = new <String> DefaultListModel();
            jListDoc.setModel(model_List);
            jListCliente.setModel(model_List2);
            jListOrdem.setModel(model_List3);
            jList4.setModel(model_List4);

            // INICIO biscar empresa.
            liga.conexao();
            liga.executeSql("select * from empresa limit 1");

            if (liga.rs.first()) {
                this.ME.setIdempresa(liga.rs.getInt(1));
                this.ME.setDesignacao(liga.rs.getString("designacao"));
                this.ME.setNif(liga.rs.getString("nif"));
                this.ME.setRegisto_comercial(liga.rs.getString("registo_comercial"));
                this.ME.setRazao_social(liga.rs.getString("razao_social"));
                this.ME.setTelefone((liga.rs.getInt("telefone")));
                this.ME.setEmail(liga.rs.getString("email"));
                this.ME.setWeb_site(liga.rs.getString("web_site"));
                this.ME.setPais(liga.rs.getString("pais"));
                this.ME.setProvincia(liga.rs.getString("provincia"));
                this.ME.setRua(liga.rs.getString("rua"));
                this.ME.setEdificio(liga.rs.getString("edificio"));
                this.ME.setCodigo_postal(liga.rs.getString("codigo_postal"));
                this.ME.setImagem_logotipo(liga.rs.getString("imagem_logotipo"));
                this.ME.setIndicativo(liga.rs.getString("indicativo"));
                this.ME.setSerie(liga.rs.getString("serie"));

            }
            liga.deconecta();

            // Fim buscar empresa.
            // inicio buscar Utilizador
            liga.conexao();
            liga.executeSql("select * from utilizador where idutilizador=" + id);

            if (liga.rs.first()) {

                this.MU.setIdUtilizador(id);
                this.MU.setNif(liga.rs.getString("nif"));
                this.MU.setNome_utilizador(liga.rs.getString("nome_utilizador"));
                this.MU.setNome(liga.rs.getString("nome"));
                this.MU.setTelefone((liga.rs.getInt("telefone")));
                this.MU.setEmail(liga.rs.getString("email"));
                this.MU.setPerfil(liga.rs.getString("perfil"));
                this.MU.setSexo(liga.rs.getString("sexo"));

            }
            liga.deconecta();
            // Fim buscar utilizador
        } catch (SQLException ex) {
            Logger.getLogger(NotaDeDebito.class.getName()).log(Level.SEVERE, null, ex);
        }

        liga.conexao();
        liga.executeSql("select *  from turno where idutilizador = " + this.id + " Order by idturno asc");
        try {
            if (liga.rs.last()) {
                boolean estado = liga.rs.getBoolean("estado");
                this.numero = liga.rs.getInt("numero");
                this.id_turno = liga.rs.getInt("idturno");
                if (!estado) {
                    int r = JOptionPane.showConfirmDialog(rootPane, "Deseja abrir o turno?", "Não podes fazer um documento sem antes abrir o turno.", JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        new Principal4(this.id, this.perfil, liga.getCaminho()).setVisible(true);
                        this.dispose();
                    } else {
                        this.dispose();
                    }
                }

            } else {
                int r = JOptionPane.showConfirmDialog(rootPane, "Deseja abrir o turno?", "Não podes fazer um documento sem antes abrir o turno.", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    new Principal4(this.id, this.perfil, liga.getCaminho()).setVisible(true);
                    this.dispose();
                } else {
                    this.dispose();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro: Consulte o suporte técnico do aplicativo", JOptionPane.ERROR_MESSAGE);
        }
        liga.deconecta();
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelTopMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_identificacao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListDoc = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jTextField_objectivo = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField_valor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_Designacao = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListCliente = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListOrdem = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanelTopMenu.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nota de débito");

        javax.swing.GroupLayout jPanelTopMenuLayout = new javax.swing.GroupLayout(jPanelTopMenu);
        jPanelTopMenu.setLayout(jPanelTopMenuLayout);
        jPanelTopMenuLayout.setHorizontalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopMenuLayout.createSequentialGroup()
                    .addContainerGap(424, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addContainerGap(424, Short.MAX_VALUE)))
        );
        jPanelTopMenuLayout.setVerticalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopMenuLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jInternalFrame1.setVisible(true);

        jList4.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jList4);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Facturas recibo");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Observações");

        jTextField_identificacao.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_identificacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_identificacaoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_identificacaoKeyTyped(evt);
            }
        });

        jListDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jListDoc.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListDoc.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListDoc.setVisibleRowCount(10);
        jListDoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListDocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListDoc);

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("Designação");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Motivo");

        jTextField_objectivo.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_objectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_objectivoActionPerformed(evt);
            }
        });

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

        jTextField_valor.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_valor.setText("00");
        jTextField_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_valorActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Valor");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Número de identificação fiscal", "Número do Bilhete" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });

        jListCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jListCliente.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListCliente.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListCliente.setEnabled(false);
        jListCliente.setVisibleRowCount(10);
        jScrollPane3.setViewportView(jListCliente);

        jListOrdem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jListOrdem.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListOrdem.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListOrdem.setEnabled(false);
        jScrollPane4.setViewportView(jListOrdem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 201, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_Designacao)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_identificacao, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                                    .addComponent(jTextField_objectivo)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_objectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jTextField_Designacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_identificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(130, 130, 130))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(43, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTopMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_objectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_objectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_objectivoActionPerformed

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
        new Principal2(this.id, this.perfil, liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        this.jButton1.setBackground(new Color(235, 235, 235));
        this.jButton1.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        this.jButton1.setBackground(new Color(56, 65, 84));
        this.jButton1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int selected = jList4.getSelectedIndex();
        if (selected != -1) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(rootPane, "Deseja emitir a nota de débito referente a fatura número " + jList4.getSelectedValue() + "?", null, JOptionPane.YES_NO_OPTION)) {
                CreateNota(jList4.getSelectedValue());
                int lastNota;
                lastNota = this.liga.UltimoId("nota_de_debito", "idnota_de_debito", this.id, this.id_turno);

                try {
                    //this.CreateLiquidacao();
                    liga.conexao();
                    PreparedStatement pst = liga.con.prepareStatement("UPDATE turno set nota_de_pagamento = nota_de_pagamento + " + jTextField_valor.getText() + ", "
                            + "saldo_atual = saldo_atual - " + jTextField_valor.getText()
                            + " WHERE idturno =" + this.id_turno);
                    pst.execute();

                    liga.deconecta();
                } catch (SQLException ex) {
                    Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(Level.SEVERE, null, ex);
                }
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
                chooser.setFileFilter(filter);
                chooser.setDialogTitle("Guardar Arquivo");

                ME.setIndicativo("ND " + ME.getIndicativo());
                String NUM = " 0";
                if (lastNota > 9) {
                    NUM = Integer.toString(lastNota);
                } else {
                    NUM = NUM + Integer.toString(lastNota);
                }
                String Aux = ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
                String nome_do_ficheiro = Aux;
                chooser.setSelectedFile(new File(nome_do_ficheiro.replaceAll("/", "_")));
                chooser.setAcceptAllFileFilterUsed(false);
                
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String file = chooser.getSelectedFile().toString().concat(".pdf");
                    try {
                        ModeloPDF e = new ModeloPDF(new File(file), null, "Documento", liga.getCaminho());
                        e.setME(ME);
                        // ModeloUtilizador MU = null;
                        e.setMU(MU);
                        e.setMC(MC);
                        e.exportA42_NotaDebito("Nota de débito", Aux, jList4.getSelectedValue(), Double.parseDouble(this.jTextField_valor.getText()), jTextField_objectivo.getText());
                        JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Ocorreu um erro " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "A nota fiscal é um documento rectificativo que serve para anular o valor de uma factura. \nTem que estar associado à uma factura obrigatoriamente.", "Nota não emitida", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_valorActionPerformed

    private void jTextField_identificacaoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_identificacaoKeyTyped


    }//GEN-LAST:event_jTextField_identificacaoKeyTyped

    private void jTextField_identificacaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_identificacaoKeyReleased
        if (this.jComboBox1.getSelectedIndex() == 0) {
            liga.conexao();
            liga.executeSql("select idcliente, designacao, nif,bi from cliente WHERE nif ilike '%" + this.jTextField_identificacao.getText() + "%'");
            model_List.removeAllElements();
            model_List2.removeAllElements();
            model_List3.removeAllElements();
            try {
                while (liga.rs.next()) {
                    model_List.addElement(liga.rs.getString("nif"));
                    model_List2.addElement(liga.rs.getString("designacao"));
                    model_List3.addElement(Integer.toString(liga.rs.getInt("idcliente")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(NotaDeDebito.class.getName()).log(Level.SEVERE, null, ex);
            }
            liga.deconecta();
        } else if (this.jComboBox1.getSelectedIndex() == 1) {
            liga.conexao();
            liga.executeSql("select idcliente, designacao, nif, bi from cliente WHERE bi ilike '%" + this.jTextField_identificacao.getText() + "%'");
            model_List.removeAllElements();
            model_List2.removeAllElements();
            model_List3.removeAllElements();
            try {
                while (liga.rs.next()) {
                    model_List.addElement(liga.rs.getString("bi"));
                    model_List2.addElement(liga.rs.getString("designacao"));
                    model_List3.addElement(Integer.toString(liga.rs.getInt("idcliente")));

                }
            } catch (SQLException ex) {
                Logger.getLogger(NotaDeDebito.class.getName()).log(Level.SEVERE, null, ex);
            }
            liga.deconecta();

        } else {
            JOptionPane.showMessageDialog(null, "Para realizar esta operação o cliente deve apresentar um documento de identificação válido (Bilhete ou NIF).", "Impossível realizar a pesquisa", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTextField_identificacaoKeyReleased

    private void jListDocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListDocMouseClicked
        int selected = jListDoc.getSelectedIndex();
        jListCliente.setSelectedIndex(selected);
        jListOrdem.setSelectedIndex(selected);
        String nome;
        String Ident;
        String idcliente;
        if (selected != -1) {
            Ident = jListDoc.getSelectedValue();
            nome = jListCliente.getSelectedValue();
            idcliente = jListOrdem.getSelectedValue();
            this.jTextField_Designacao.setText(nome);
            this.jTextField_identificacao.setText(Ident);
            BuscarFacturas(idcliente);
        }
    }//GEN-LAST:event_jListDocMouseClicked

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        this.jTextField_Designacao.setText("");
        this.jTextField_identificacao.setText("");
        model_List.removeAllElements();
        model_List2.removeAllElements();
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jList4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList4MouseClicked
        int selected = jList4.getSelectedIndex();
        if (selected != -1) {
            // Buscar a fatura antiga.
            String Ident = jList4.getSelectedValue();
            //System.out.println(Ident);
            liga.conexao();
            liga.executeSql("select idfatura_recibo, imposto_total, idcliente from recibo WHERE idfatura_recibo = " + Ident + " Order by idfatura_recibo");

            try {
                while (liga.rs.next()) {
                    Total = ((liga.rs.getDouble("imposto_total")));
                    MC.setIdCliente(liga.rs.getInt("idcliente"));
                    jTextField_valor.setText(Double.toString(Total));
                    JOptionPane.showMessageDialog(null, "O valor a ser anulado é de " + liga.Chang(Double.toString(Total)) + "Kz", "", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Dados não encontrados111", "", JOptionPane.WARNING_MESSAGE);

            }
            liga.deconecta();

            liga.conexao();
            liga.executeSql("select * from cliente WHRE idcliente  = " + MC.getIdCliente());

            try {
                while (liga.rs.next()) {
                    System.out.println(liga.rs.getString("designacao"));
                    MC.setDesignacao(liga.rs.getString("designacao"));
                    MC.setBi(liga.rs.getString("bi"));
                    MC.setNif(liga.rs.getString("nif"));
                    MC.setTipo_de_pessoa(liga.rs.getString("tipo_de_pessoa"));
                    MC.setSexo(liga.rs.getString("sexo"));
                    MC.setTelefone(liga.rs.getString("telefone"));
                    MC.setEndereco(liga.rs.getString("endereco"));
                    MC.setEmail(liga.rs.getString("email"));

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Dados não encontrados", "", JOptionPane.WARNING_MESSAGE);

            }

            liga.deconecta();
            // buscar a fatura antiga

            this.MC.setDesignacao(jListCliente.getSelectedValue());
            jComboBox1.getSelectedItem().toString();
            // this.MC.setNif(jListDoc.getSelectedValue());
            //this.jLabel_NomeDoCliente.setText(MC.getDesignacao());

        } else {
            System.out.println("erro");
        }
    }//GEN-LAST:event_jList4MouseClicked

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
            java.util.logging.Logger.getLogger(NotaDeDebito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotaDeDebito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotaDeDebito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotaDeDebito.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotaDeDebito().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList4;
    private javax.swing.JList<String> jListCliente;
    private javax.swing.JList<String> jListDoc;
    private javax.swing.JList<String> jListOrdem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelTopMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField_Designacao;
    private javax.swing.JTextField jTextField_identificacao;
    private javax.swing.JTextField jTextField_objectivo;
    private javax.swing.JTextField jTextField_valor;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;

    public boolean CreateNota(String idfactura) {
        liga.conexao();
        try {
            ModeloNota MN = new ModeloNota();
            MN.setIdcliente(Integer.parseInt(jListOrdem.getSelectedValue()));
            MN.setObjectivo(jTextField_objectivo.getText());
            MN.setObs("CLIENTE: " + jTextField_Designacao.getText() + "\n" + jComboBox1.getSelectedItem().toString() + "\n" + jTextArea1.getText());
            MN.setValor(Double.parseDouble(jTextField_valor.getText()));
            MN.setData(this.tem.Date());
            PreparedStatement pst = liga.con.prepareStatement("Insert into nota_de_debito (idcliente,data,valor,observacao,"
                    + "objectivo,idfactura,serie,indicativo,idutilizador, idturno)  values (?,?,?,?,?,?,?,'" + this.ME.getIndicativo() + "'," + this.id + "," + this.id_turno + ")");
            pst.setInt(1, MN.getIdcliente());
            pst.setString(2, MN.getData());
            pst.setDouble(3, MN.getValor());
            pst.setString(4, MN.getObs());
            pst.setString(5, MN.getObjectivo());
            pst.setInt(6, Integer.parseInt(idfactura));
            pst.setString(7, this.ME.getSerie());
            pst.execute();
            UpdateSaldo(MN.getValor());
            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não inseridos " + ex, " ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        return false;
    }

    /*public boolean CreateNota(String idfactura) {
     liga.conexao();
     try {
     ModeloNota MN = new ModeloNota();
     MN.setIdcliente(Integer.parseInt(jListOrdem.getSelectedValue()));
     MN.setObjectivo(jTextField_objectivo.getText());
     MN.setObs("CLIENTE: " + jTextField_Designacao.getText() + "\n" + jComboBox1.getSelectedItem().toString() + "\n" + jTextArea1.getText());
     MN.setValor(Double.parseDouble(jTextField_valor.getText()));
     MN.setData(this.tem.Date());
     PreparedStatement pst = liga.con.prepareStatement("Insert into nota_de_credito (idcliente,data,valor,observacao,objectivo,idfactura) values (?,?,?,?,?,?);");
     pst.setInt(1, MN.getIdcliente());
     pst.setString(2, MN.getData());
     pst.setDouble(3, MN.getValor());
     pst.setString(4, MN.getObs());
     pst.setString(5, MN.getObjectivo());
     pst.setInt(6, Integer.parseInt(idfactura));
     pst.execute();
     UpdateSaldo(MN.getValor());
     JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
     return true;
     } catch (SQLException ex) {
     JOptionPane.showMessageDialog(null, "Dados não inseridos", " ", JOptionPane.WARNING_MESSAGE);
     }
     liga.deconecta();
     return false;
     }*/
    public boolean UpdateSaldo(double valor) {
        try {
            liga.conexao();
            PreparedStatement pst = liga.con.prepareStatement("UPDATE caixa set saida = saida + " + valor + ", saldo = saldo - " + valor + ", idempresa =" + 1);
            pst.execute();
            liga.deconecta();

            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Caixa não atualizada.", "", JOptionPane.WARNING_MESSAGE);
            return false;
        }
//        return false;
    }

//    public String Chang(String V) {
//        BigDecimal valor = new BigDecimal(V);
//        NumberFormat nf = NumberFormat.getCurrencyInstance();
//        String formatado = nf.format(valor);
////        System.out.println(formatado);
//        return formatado.substring(0, formatado.length() - 2);
//    }
    private void BuscarFacturas(String Ident) {

        liga.conexao();
        liga.executeSql("select idfatura_recibo from recibo WHERE idcliente = " + Ident + " AND idfatura_recibo NOT IN (select idfactura from nota_de_dedito) Order by idfatura_recibo");
        model_List4.removeAllElements();
        try {
            while (liga.rs.next()) {
                model_List4.addElement(Integer.toString(liga.rs.getInt(1)));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados", "", JOptionPane.WARNING_MESSAGE);

        }
        liga.deconecta();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. export42_Liquidacao
 */
package Vista;

import Controle.ControloBD;
import Controle.Tempo;
import java.awt.Color;
import Controle.TextPrompt;
import Modelo.ModeloEmpresa;
import Modelo.ModeloNota;
import Modelo.ModeloPDF;
import Modelo.ModeloUtilizador;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Controle.EncriptaDecriptaRSA;
import Modelo.ModeloFatura;

/**
 *
 * @author Gonga
 */
public class LiquidacaoDeFatura extends javax.swing.JFrame {

    int id;
    String perfil;
    int id_turno = -1;
    int numero = 0;
    ControloBD liga = new ControloBD();
    Tempo tempo = new Tempo();
    DefaultListModel<String> model_List4;
    DefaultListModel<String> model_List5;
    ModeloUtilizador MU = new ModeloUtilizador();
    ModeloEmpresa ME = new ModeloEmpresa();
    EncriptaDecriptaRSA Assinatura = new EncriptaDecriptaRSA();
    /**
     * Creates new form AddUtilizador
     */
    public LiquidacaoDeFatura() {
        initComponents();
        model_List4 = new <String> DefaultListModel();
        jList4.setModel(model_List4);
        model_List5 = new <String> DefaultListModel();
        jList5.setModel(model_List5);
        BuscarFacturas();

    }

    public LiquidacaoDeFatura(String tipoDeFatura) {
        initComponents();

    }

    LiquidacaoDeFatura(int id, String perfil,String caminho) {
        initComponents();
        liga.setCaminho(caminho);
        try {
//            initComponents();
            this.id = id;
            this.perfil = perfil;
            model_List4 = new <String> DefaultListModel();
            jList4.setModel(model_List4);
            model_List5 = new <String> DefaultListModel();
            jList5.setModel(model_List5);
            BuscarFacturas();

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
        } catch (SQLException ex) {
            Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(Level.SEVERE, null, ex);
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
                        new Principal4(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                        this.dispose();
                    } else {
                        this.dispose();
                    }
                }

            } else {
                int r = JOptionPane.showConfirmDialog(rootPane, "Deseja abrir o turno?", "Não podes fazer um documento sem antes abrir o turno.", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    new Principal4(this.id, this.perfil,liga.getCaminho()).setVisible(true);
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

    private void BuscarFacturas() {
        liga.conexao();
        liga.executeSql("select idfactura, total, liquidacao from fatura WHERE liquidacao < 100 Order by idfactura");
        model_List4.removeAllElements();
        model_List5.removeAllElements();
        try {
            while (liga.rs.next()) {
                model_List4.addElement(Integer.toString(liga.rs.getInt("idfactura")));
                Double total = liga.rs.getDouble("total");
                Double x = (liga.rs.getDouble("liquidacao") / 100) * total;
                Double restante = total - x;
                model_List5.addElement(Double.toString(restante));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados", "", JOptionPane.WARNING_MESSAGE);

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
        jList4 = new javax.swing.JList<String>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList<String>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_Entidade = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Objectivo = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField_valor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanelTopMenu.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Liquidação de factura");

        javax.swing.GroupLayout jPanelTopMenuLayout = new javax.swing.GroupLayout(jPanelTopMenu);
        jPanelTopMenu.setLayout(jPanelTopMenuLayout);
        jPanelTopMenuLayout.setHorizontalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1117, Short.MAX_VALUE)
            .addGroup(jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1110, Short.MAX_VALUE))
        );
        jPanelTopMenuLayout.setVerticalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
            .addGroup(jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopMenuLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jInternalFrame1.setVisible(true);

        jList4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jList4.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList4.setToolTipText("Nº das faturas não liquidadas.");
        jList4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jList4);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Facturas");

        jList5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jList5.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList5.setToolTipText("Valor que resta a ser liquidado.");
        jList5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList5MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jList5);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Nº Facturas");
        jLabel8.setToolTipText("Nº das faturas não liquidadas.");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Valor (KZ)");
        jLabel9.setToolTipText("Valor que resta a ser liquidado.");

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5))))))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
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

        jTextField_Entidade.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("Entidade");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Objectivo");

        jTextField_Objectivo.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_Objectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_ObjectivoActionPerformed(evt);
            }
        });

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

        jTextField_valor.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_valor.setText("00");
        jTextField_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_valorActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Valor");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel6.setText("Documento");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Bilhete");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Passaport");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("Outro");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel6))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_Objectivo, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                    .addComponent(jTextField_Entidade)
                                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jTextField_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(50, 50, 50))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextField_Objectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Entidade, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1))
                .addGap(10, 10, 10)
                .addComponent(jRadioButton2)
                .addGap(10, 10, 10)
                .addComponent(jRadioButton3)
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jTextField_ObjectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ObjectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_ObjectivoActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        this.jButton2.setBackground(new Color(235, 235, 235));
        this.jButton2.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        this.jButton2.setBackground(new Color(217, 81, 51));
        this.jButton2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Principal2(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();

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

        this.CreateLiquidacao();


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_valorActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        new Principal2().setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton2MouseClicked

    private void jList4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList4MouseClicked
        int index = jList4.getSelectedIndex();
        jList5.setSelectedIndex(index);
    }//GEN-LAST:event_jList4MouseClicked

    private void jList5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList5MouseClicked
        int index = jList5.getSelectedIndex();
        jList4.setSelectedIndex(index);
    }//GEN-LAST:event_jList5MouseClicked

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
            java.util.logging.Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new LiquidacaoDeFatura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList4;
    private javax.swing.JList<String> jList5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelTopMenu;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField_Entidade;
    private javax.swing.JTextField jTextField_Objectivo;
    private javax.swing.JTextField jTextField_valor;
    // End of variables declaration//GEN-END:variables

    byte[] photo = null;
    String filename = null;

    public boolean CreateLiquidacao() {
        int index1 = jList4.getSelectedIndex();
        int index2 = jList5.getSelectedIndex();

        if (index1 != -1 && index2 != -1 && index1 == index2) {
            Double valorE = Double.parseDouble(this.jTextField_valor.getText());

            double valorP = Double.parseDouble(jList5.getSelectedValue().toString());
            int idP = Integer.parseInt(jList4.getSelectedValue());
            boolean totalidade = false;
            Double total = (valorP - valorE);
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Será feita uma liquidação da faura Nº" + idP + " no valor de " + valorE + "Kz" + " da dívida restante de " + valorP + " Kz." + "", "Liquidação.", JOptionPane.YES_NO_OPTION)) {
                try {
                    liga.conexao();
                    PreparedStatement pst = liga.con.prepareStatement("INSERT INTO public.liquidacao(data, objectivo, observacao, idfatura, total, documento,"
                            + "valor, serie,hora,indicativo,idutilizador, idturno) VALUES (?, ?, ?, ?, ?, ?, ?,?,current_time,'" + this.ME.getIndicativo() + "'," + this.id + "," + this.id_turno + ")");
                    if (total == 0) {
                        JOptionPane.showMessageDialog(null, "A fatura será liquidada na totalidade.", "Liquidação.", JOptionPane.INFORMATION_MESSAGE);
                        totalidade = true;
                    }
                    if (total > -1) {
                        String data_doc = tempo.Date();
                        String hora_doc = tempo.Hours();
                        pst.setString(1, data_doc);
                        pst.setString(2, this.jTextField_Objectivo.getText());
                        pst.setString(3, this.jTextArea1.getText());
                        pst.setInt(4, idP);
                        pst.setBoolean(5, totalidade);
                        if (this.jRadioButton1.isSelected()) {
                            pst.setString(6, this.jRadioButton1.getText());
//                            System.out.println(this.jRadioButton1.getText());
                        }
                        if (this.jRadioButton2.isSelected()) {
                            pst.setString(6, this.jRadioButton2.getText());
////                            System.out.println(this.jRadioButton2.getText());
                        }
                        if (this.jRadioButton3.isSelected()) {
                            pst.setString(6, this.jRadioButton3.getText());
//                            System.out.println(this.jRadioButton3.getText());
                        }
                        pst.setDouble(7, valorE);
                        pst.setString(8, this.ME.getSerie());
                        pst.execute();
                        if (UpdateSaldo(valorE) && UpdateFatura(idP, valorE)) {
                            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                        }
                        int NumLiqui;
                        NumLiqui = this.liga.UltimoId("liquidacao", "idliquidacao", this.id, this.id_turno);

                        try {

                            liga.conexao();
                            PreparedStatement pst1 = liga.con.prepareStatement("UPDATE turno set recibo = recibo + " + jTextField_valor.getText() + ", "
                                    + "saldo_atual = saldo_atual + " + jTextField_valor.getText() 
                                    + " WHERE idturno =" + this.id_turno);
                            pst1.execute();

//                            liga.deconecta();
                        } catch (SQLException ex) {
                            Logger.getLogger(LiquidacaoDeFatura.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        JFileChooser chooser = new JFileChooser();

                        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
                        chooser.setFileFilter(filter);
                        chooser.setDialogTitle("Guardar Arquivo");
                        ME.setIndicativo("RE " + ME.getIndicativo());
                        String NUM = " 0";
                        if (NumLiqui > 9) {
                            NUM = Integer.toString(NumLiqui);
                        } else {
                            NUM = NUM + Integer.toString(NumLiqui);
                        }
                        String Aux = ME.getIndicativo() + " "+ this.ME.getSerie() + NUM;
                        
                        String Last_asisinatura = LastHash(NumLiqui);
                        String hash = Assinatura.GerarAssinatura(data_doc, data_doc + " " +hora_doc, Aux, valorE, Last_asisinatura);

                        pst = liga.con.prepareStatement("UPDATE liquidacao "
                                + "SET hash= '" + hash + "' WHERE idliquidacao=" + NumLiqui);
                        pst.execute();
                        liga.deconecta();
                        
                        //System.out.println("file: "+Aux);
                        String nome_do_ficheiro = Aux;
                        chooser.setSelectedFile(new File(nome_do_ficheiro.replaceAll("/", "_")));

                        chooser.setAcceptAllFileFilterUsed(false);
                        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                            String file = chooser.getSelectedFile().toString().concat(".pdf");
                            try {
                                ModeloPDF e = new ModeloPDF(new File(file), null, "Documento",liga.getCaminho());
                                e.setME(ME);
                                e.setMU(MU);
                                e.setMF(new ModeloFatura(NumLiqui));
                                e.export42_Liquidacao(this.jTextField_Entidade.getText(), valorE, valorP, Integer.toString(idP), Aux);
                                JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);

                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "O valor pago pelo clinete é superior ao da dívida do cliente.", "Liquidação canceldada.", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Dados não inseridos " + ex, "Liquidação não realizada.", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Liquidação cancelada.", "Liquidação.", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Deves selecionar uma fatura para liquidar.", "Liquidação não realizada.", JOptionPane.WARNING_MESSAGE);
        }

        liga.deconecta();

        return false;
    }

    private boolean UpdateSaldo(Double valorE) {
        try {
            liga.conexao();
            PreparedStatement pst = liga.con.prepareStatement("UPDATE caixa set entrada = entrada + " + valorE + ", saldo = saldo + " + valorE);
            pst.execute();
            liga.deconecta();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "O valor não entrou na caixa.", "Liquidação", JOptionPane.WARNING_MESSAGE);
            return false;
        }

    }

    private boolean UpdateFatura(int idP, Double valorE) {
        liga.conexao();
        liga.executeSql("select total,liquidacao from fatura WHERE idfactura=" + idP);
        Double x = 0.0;
        Double total = 0.0;
        Double pago = 0.0;
        try {
            while (liga.rs.next()) {
                total = liga.rs.getDouble("total");
                x = liga.rs.getDouble("liquidacao");
                pago = (valorE * 100) / total;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fatura Nº " + idP + " não encontrada na base de daods.", "Impossível concluir a liquidação", JOptionPane.WARNING_MESSAGE);

        }
        liga.deconecta();

        double liqPerce = x + pago;

        try {
            liga.conexao();
            PreparedStatement pst = liga.con.prepareStatement("UPDATE fatura SET liquidacao = " + liqPerce + " WHERE  idfactura= " + idP);
            pst.execute();
            liga.deconecta();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "O valor não entrou na caixa.", "Liquidação", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    private String LastHash(int num) {
//        liga.conexao();
        if (num==1)
            return null;
        num--;
        liga.executeSql("select hash from liquidacao where idliquidacao= " + (num) + " ");
        try {
            if (liga.rs.first()) {
                String r = liga.rs.getString(1);
//                liga.deconecta();
                return r;
            } else {
//                liga.deconecta();
                return null;
            }
        } catch (SQLException ex) {
//            Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}

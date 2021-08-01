/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import Controle.Extenso;
import java.awt.Color;
import Controle.TextPrompt;
import Controle.Validação;
import Modelo.ModeloServico;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class AddServico extends javax.swing.JFrame {

    int id;
    String perfil;
    ControloBD liga = new ControloBD();
    DefaultListModel<String> model_List;
    Validação validacao = new Validação();
    String regime = "Regime geral";
    Extenso exte = new Extenso();

    /**
     * Creates new form AddUtilizador
     */
    public AddServico() {
        try {
            initComponents();
            Toolkit T = Toolkit.getDefaultToolkit();
            this.setSize(T.getScreenSize());
            this.setExtendedState(this.MAXIMIZED_BOTH);
            TextPrompt prompt1 = new TextPrompt("Nome de utilizador", jTextField_descricao);

            TextPrompt prompt7 = new TextPrompt("Nome de utilizador", jTextField_descricao);
            TextPrompt prompt8 = new TextPrompt("endereço de email", jTextField_iva);
            jLabel10_validacao_custo.setVisible(false);
            jLabel10_validacao_preco.setVisible(false);

            liga.conexao();
            liga.executeSql("select * from empresa");
            if (liga.rs.first()) {

                regime = liga.rs.getString("regime_de_iva");
                if (!"Regime geral".equals(regime)) {
                    this.jRadioButton_isento.setSelected(true);
                    jRadioButton_iva.setEnabled(false);
                    this.jComboBox_Iva.setVisible(false);
                } else {
                    this.jRadioButton_isento.setSelected(false);
                    // this.jRadioButton_isento.setSelected(true);
                    jRadioButton_iva.setEnabled(true);
                    jRadioButton_iva.setSelected(true);
                    //this.jComboBox_Iva.setVisible(true);
                }
                //this.revert(liga.rs.getBytes("foto"));
                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);

        }
    }

    public AddServico(int id, String perfil, String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);
        jLabel10_validacao_custo.setVisible(false);
        jLabel10_validacao_preco.setVisible(false);
        TextPrompt prompt1 = new TextPrompt("Descrição", jTextField_descricao);

        TextPrompt prompt2 = new TextPrompt("Preço", jTextField_preco);

        TextPrompt prompt3 = new TextPrompt("Custo", jTextField_custo);

        TextPrompt prompt4 = new TextPrompt("Observações do produto", jTextAreaOBS);
        this.id = id;
        this.perfil = perfil;
        model_List = new <String> DefaultListModel();
        JListTerceirizador.setModel(model_List);
        listaPesquisa();

        liga.conexao();
        liga.executeSql("select * from empresa");
        try {
            if (liga.rs.first()) {

                regime = liga.rs.getString("regime_de_iva");
                if (!"Regime geral".equals(regime)) {
                    this.jRadioButton_isento.setSelected(true);
                    jRadioButton_iva.setEnabled(false);
                    this.jComboBox_Iva.setVisible(false);
                } else {
                    this.jRadioButton_isento.setSelected(false);
                    // this.jRadioButton_isento.setSelected(true);
                    jRadioButton_iva.setEnabled(true);
                    jRadioButton_iva.setSelected(true);
                    //this.jComboBox_Iva.setVisible(true);
                }
                //this.revert(liga.rs.getBytes("foto"));
                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean CreateServico() {

        liga.conexao();
        try {

            String motivo = jComboBox_Iva.getSelectedItem().toString();
            double preco = 0.0;
            int exist = 0;
            double preco_unit = Double.parseDouble(this.Convert(this.jTextField_preco.getText()));
            preco = Double.parseDouble(this.Convert(this.jTextField_preco_venda.getText()));
            double custo = Double.parseDouble(this.Convert(this.jTextField_custo.getText()));
            double lucro = Double.parseDouble(this.Convert(this.jTextField_lucro1.getText()));
            double margem = Double.parseDouble(this.Convert(this.jTextField_margem_de_lucro.getText()));
            boolean iva = jRadioButton_iva.isSelected();
            double valor_iva = Double.parseDouble(this.Convert(this.jTextField_valor_iva.getText()));
            if (iva) {
                motivo = null;
//                preco = preco_unit + (preco_unit * 0.14);
            }

            ModeloServico MS = new ModeloServico(-1, iva, custo, preco, this.jTextField_descricao.getText(), motivo,
                    jTextAreaOBS.getText(), this.jTextField_nomeDaFoto.getText());
            PreparedStatement pst = liga.con.prepareStatement("Insert into servico"
                    + "(descricao, imposto_iva, motivo, observacao, custo, preco, nome_da_foto, foto,"
                    + "preco_unitario, lucro, margem_de_lucro, valor_iva,retencao) "
                    + "values(?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?)");
            pst.setString(1, MS.getDescricao());
            pst.setBoolean(2, MS.isImposto_iva());
            if (!"Regime geral".equals(this.regime)) {
                pst.setString(3, " ");
            } else {
                pst.setString(3, MS.getMotivo());
            }

            pst.setString(4, MS.getObservacao());
            pst.setDouble(5, MS.getCusto());
            pst.setDouble(6, MS.getPreco());
            pst.setString(7, MS.getFoto());
            pst.setBytes(8, this.photo);
            pst.setDouble(9, preco_unit);
            pst.setDouble(10, lucro);
            pst.setDouble(11, margem);
            pst.setDouble(12, valor_iva);
            pst.setBoolean(13, jCheckBox_retencao.isSelected());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não inseridos ", " ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();

        return false;
    }

    public boolean CreateServicoTerceirizado() {
        int idServ = -1;
        int idTerc = -1;

        liga.conexao();
        liga.executeSql("select idterceirizador from terceirizador WHERE designacao= '" + this.JListTerceirizador.getSelectedValue() + "'");
        try {
            while (liga.rs.next()) {
                idTerc = liga.rs.getInt("idterceirizador");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro em procurar dados" + ex);
        }

        liga.executeSql("select idservico from servico WHERE descricao= '" + this.jTextField_descricao.getText() + "'");
        try {
            while (liga.rs.next()) {
                idServ = liga.rs.getInt("idservico");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro em procurar dados" + ex);
        }

        liga.deconecta();

        liga.conexao();
        try {

            PreparedStatement pst = liga.con.prepareStatement("Insert into servicoterceirizado (idservico, idterceirizador) " + "values(?,?)");
            pst.setInt(1, idServ);
            pst.setInt(2, idTerc);

            pst.execute();

            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não inseridos " + ex, " ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();

        return false;
    }

    public void listaPesquisa() {

        liga.conexao();
        liga.executeSql("select designacao from terceirizador Order by designacao;");
        model_List.removeAllElements();

        try {
            while (liga.rs.next()) {
                model_List.addElement(liga.rs.getString("designacao"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro em procurar dados" + ex);
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

        IVA = new javax.swing.ButtonGroup();
        Terceirizado = new javax.swing.ButtonGroup();
        jPanel_Add_servico = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_descricao = new javax.swing.JTextField();
        jTextField_iva = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton_isento = new javax.swing.JRadioButton();
        jRadioButton_iva = new javax.swing.JRadioButton();
        jComboBox_Iva = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        jTextField_custo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_preco = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaOBS = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel10_validacao_preco = new javax.swing.JLabel();
        jLabel10_validacao_custo = new javax.swing.JLabel();
        jTextField_preco_venda = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField_valor_iva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField_margem_de_lucro = new javax.swing.JTextField();
        jTextField_lucro1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField_nomeDaFoto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel_Foto = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton_terceirizado = new javax.swing.JRadioButton();
        jRadioButton_não_terceirizado = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JListTerceirizador = new javax.swing.JList<String>();
        jCheckBox_retencao = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Add_servico.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Add_servico.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Add_servico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(27, 167, 125));

        jLabel20.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Adicionar Servico");

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

        jPanel_Add_servico.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 900, -1));

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

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("Descrição");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Custo");

        jTextField_descricao.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_iva.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_iva.setText("14");

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel6.setText("IVA");

        IVA.add(jRadioButton_isento);
        jRadioButton_isento.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_isento.setSelected(true);
        jRadioButton_isento.setText("Isento");
        jRadioButton_isento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_isentoMouseClicked(evt);
            }
        });
        jRadioButton_isento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_isentoActionPerformed(evt);
            }
        });

        IVA.add(jRadioButton_iva);
        jRadioButton_iva.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_iva.setText("Taxa");
        jRadioButton_iva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_ivaMouseClicked(evt);
            }
        });

        jComboBox_Iva.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jComboBox_Iva.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Isento nos termos da alínea a) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea b) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea c) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea d) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea e) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea f) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea g) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea h) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea i) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea j) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea k) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea l) nº1 do artigo 12º do CIVA", "Isento nos termos da alínea m) do artigo 12º do CIVA", "Isento nos termos da alínea n) do artigo 12º do CIVA", "Isento nos termos da alínea o) do artigo 12º do CIVA", "Isento nos termos da alínea a) do artigo 15º do CIVA", "Isento nos termos da alínea b) do artigo 15º do CIVA", "Isento nos termos da alínea c) do artigo 15º do CIVA", "Isento nos termos da alínea d) do artigo 15º do CIVA", "Isento nos termos da alínea e) do artigo 15º do CIVA", "Isento nos termos da alínea f) do artigo 15º do CIVA", "Isento nos termos da alínea g) do artigo 15º do CIVA", "Isento nos termos da alínea h) do artigo 15º do CIVA", "Isento nos termos da alínea i) do artigo 15º do CIVA", "Isento nos termos da alínea a) do artigo 14º", "Isento nos termos da alínea b) do artigo 14º", "Isento nos termos da alínea c) do artigo 14º", "Isento nos termos da alínea d) do artigo 14º", "Isento nos termos da alínea e) do artigo 14º", "Isento nos termos da alínea a) nº2 do artigo 14º", "Isento nos termos da alínea b) nº2 do artigo 14º", "Isento nos termos da alínea a) nº1 do artigo 16º", "Isento nos termos da alínea b) nº1 do artigo 16º", "Isento nos termos da alínea c) nº1 do artigo 16º", "Isento nos termos da alínea d) nº1 do artigo 16º", "Isento nos termos da alínea e) nº1 do artigo 16º" }));

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel9.setText("Motivo de isenção");

        jTextField_custo.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_custo.setText("00.00");
        jTextField_custo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_custoActionPerformed(evt);
            }
        });

        jLabel7.setText("%");

        jTextField_preco.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_preco.setText("00.00");
        jTextField_preco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField_precoMouseExited(evt);
            }
        });
        jTextField_preco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_precoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel11.setText("Preço");

        jTextAreaOBS.setColumns(20);
        jTextAreaOBS.setRows(5);
        jScrollPane2.setViewportView(jTextAreaOBS);

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Observação");

        jLabel10_validacao_preco.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_preco.setText("Entrada inválida. Só é permitido valor numérico  (Ex: 55.5) ");

        jLabel10_validacao_custo.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_custo.setText("Entrada inválida. Só é permitido valor numérico (Ex: 55.5) ");

        jTextField_preco_venda.setEditable(false);
        jTextField_preco_venda.setText("0,00");

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel13.setText("P. Venda (Kwanza)");

        jTextField_valor_iva.setEditable(false);
        jTextField_valor_iva.setText("0,00");

        jLabel15.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel15.setText("Valor IVA (Kwanza)");

        jLabel16.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel16.setText("Margem de Lucro (%)");

        jTextField_margem_de_lucro.setEditable(false);
        jTextField_margem_de_lucro.setText("0,00");
        jTextField_margem_de_lucro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_margem_de_lucroActionPerformed(evt);
            }
        });

        jTextField_lucro1.setEditable(false);
        jTextField_lucro1.setText("0,00");
        jTextField_lucro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_lucro1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel14.setText("Lucro (Kwanza)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jRadioButton_iva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton_isento))
                            .addComponent(jComboBox_Iva, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(13, 13, 13)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField_margem_de_lucro)
                                        .addComponent(jTextField_valor_iva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                        .addComponent(jTextField_lucro1))
                                    .addComponent(jTextField_preco_venda)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_preco, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10_validacao_preco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_descricao)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_custo, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10_validacao_custo, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 3, Short.MAX_VALUE)))
                        .addGap(67, 67, 67))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_descricao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jRadioButton_isento)
                    .addComponent(jRadioButton_iva)
                    .addComponent(jTextField_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox_Iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_custo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jLabel10_validacao_custo)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10_validacao_preco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jTextField_preco_venda))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_valor_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_margem_de_lucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_lucro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_upload_to_ftp_48px.png"))); // NOI18N
        jButton3.setText("Carregar");
        jButton3.setToolTipText("Carregar uma foto");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Outros dados"));

        Terceirizado.add(jRadioButton_terceirizado);
        jRadioButton_terceirizado.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_terceirizado.setText("Terceirizado");
        jRadioButton_terceirizado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_terceirizadoMouseClicked(evt);
            }
        });

        Terceirizado.add(jRadioButton_não_terceirizado);
        jRadioButton_não_terceirizado.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_não_terceirizado.setText("Não terceirizado");
        jRadioButton_não_terceirizado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_não_terceirizadoMouseClicked(evt);
            }
        });

        JListTerceirizador.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        JListTerceirizador.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(JListTerceirizador);

        jCheckBox_retencao.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jCheckBox_retencao.setText("Usar rtenção na fonte");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jRadioButton_terceirizado)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton_não_terceirizado))
                    .addComponent(jCheckBox_retencao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jCheckBox_retencao)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_não_terceirizado)
                    .addComponent(jRadioButton_terceirizado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(45, 45, 45)
                                    .addComponent(jButton3))
                                .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(251, 251, 251)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap(18, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel_Add_servico.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 900, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 901, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_servico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel_Add_servico, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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
        new Principal3(this.id, this.perfil, liga.getCaminho()).setVisible(true);
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

    private void jRadioButton_isentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_isentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton_isentoActionPerformed

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

    private void jRadioButton_isentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_isentoMouseClicked
        this.jLabel9.setVisible(true);
        this.jComboBox_Iva.setVisible(true);
    }//GEN-LAST:event_jRadioButton_isentoMouseClicked

    private void jRadioButton_ivaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_ivaMouseClicked
        this.jLabel9.setVisible(false);
        this.jComboBox_Iva.setVisible(false);
    }//GEN-LAST:event_jRadioButton_ivaMouseClicked

    private void jRadioButton_não_terceirizadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_não_terceirizadoMouseClicked
        this.JListTerceirizador.setEnabled(false);

    }//GEN-LAST:event_jRadioButton_não_terceirizadoMouseClicked

    private void jRadioButton_terceirizadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_terceirizadoMouseClicked
        this.JListTerceirizador.setEnabled(true);
    }//GEN-LAST:event_jRadioButton_terceirizadoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean r1, r2, r3;
        if (!this.jTextField_preco.getText().isEmpty() && validacao.SoNumero(Convert(this.jTextField_preco.getText()))) {
            r1 = true;
            jLabel10_validacao_preco.setVisible(false);
        } else {
            jLabel10_validacao_preco.setVisible(true);
            r1 = false;
        }

        if (!this.jTextField_custo.getText().isEmpty() && validacao.SoNumero(Convert(this.jTextField_custo.getText()))) {
            r2 = true;
            jLabel10_validacao_custo.setVisible(false);
        } else {
            r2 = false;
            jLabel10_validacao_custo.setVisible(true);
        }

        if (!jTextField_descricao.getText().isEmpty()) {
            r3 = true;

        } else {
            r3 = false;
            JOptionPane.showMessageDialog(null, "O campos Descrição não pode estar vazio.", " ", JOptionPane.WARNING_MESSAGE);
        }

        if (r1 && r2 && r3) {
            if (this.CreateServico() && this.jRadioButton_terceirizado.isSelected()) {
                this.CreateServicoTerceirizado();
                new AddServico(id, perfil, liga.getCaminho()).setVisible(true);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_precoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_precoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_precoActionPerformed

    private void jTextField_custoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_custoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_custoActionPerformed

    private void jTextField_margem_de_lucroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_margem_de_lucroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_margem_de_lucroActionPerformed

    private void jTextField_lucro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_lucro1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_lucro1ActionPerformed

    private void jTextField_precoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_precoMouseExited
        calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());
    }//GEN-LAST:event_jTextField_precoMouseExited

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
            java.util.logging.Logger.getLogger(AddServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddServico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddServico().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup IVA;
    private javax.swing.JList<String> JListTerceirizador;
    private javax.swing.ButtonGroup Terceirizado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox_retencao;
    private javax.swing.JComboBox<String> jComboBox_Iva;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel10_validacao_custo;
    private javax.swing.JLabel jLabel10_validacao_preco;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Foto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_Add_servico;
    private javax.swing.JRadioButton jRadioButton_isento;
    private javax.swing.JRadioButton jRadioButton_iva;
    private javax.swing.JRadioButton jRadioButton_não_terceirizado;
    private javax.swing.JRadioButton jRadioButton_terceirizado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaOBS;
    private javax.swing.JTextField jTextField_custo;
    private javax.swing.JTextField jTextField_descricao;
    private javax.swing.JTextField jTextField_iva;
    private javax.swing.JTextField jTextField_lucro1;
    private javax.swing.JTextField jTextField_margem_de_lucro;
    private javax.swing.JTextField jTextField_nomeDaFoto;
    private javax.swing.JTextField jTextField_preco;
    private javax.swing.JTextField jTextField_preco_venda;
    private javax.swing.JTextField jTextField_valor_iva;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;

    public void revert(byte[] B) {
        if (B != null) {
            File img = new File(Arrays.toString(B));
            this.jLabel_Foto.setIcon(new ImageIcon(B));
            this.photo = B;
            this.jTextField_nomeDaFoto.setText(img.getName());
        }

    }

    private void calcularIVA_lucro(String preco_param, String cust_param) {

        preco_param = preco_param.replace('.', ' ');
        String[] split = preco_param.split(" ");
        preco_param = "0";
        for (String split1 : split) {
            preco_param += split1;
        }
        preco_param = preco_param.replace(',', '.');

        cust_param = cust_param.replace('.', ' ');
        String[] split2 = cust_param.split(" ");
        cust_param = "0";
        for (String split12 : split2) {
            cust_param += split12;
        }
        cust_param = cust_param.replace(',', '.');
        System.out.println(cust_param + "\n" + preco_param);

        boolean SoNumero1 = validacao.SoNumero(preco_param);
        boolean SoNumero2 = validacao.SoNumero(cust_param);

        if (!SoNumero1) {
            this.jTextField_preco.setText("0,00");
            preco_param = "0.00";
        }
        if (!SoNumero2) {
            this.jTextField_custo.setText("0,00");
            cust_param = "0.00";
        }

        double preco_unit = Double.parseDouble(preco_param);
        double preco;
        double valor_iva = 0;
        boolean iva = jRadioButton_iva.isSelected();
        if (iva) {
            //motivo = null;
            valor_iva = preco_unit * 0.14;

        }

        preco = preco_unit + valor_iva;
        double custo = Double.parseDouble(cust_param);
        // System.out.println(custo);
        double lucro = preco_unit - custo;
        double margem = 100;
        if (custo != 0) {
            margem = (lucro * 100) / custo;
        }
//        System.out.println(margem);
        jTextField_lucro1.setText(exte.Chang(lucro));
        jTextField_preco_venda.setText(exte.Chang(preco));
        jTextField_preco.setText(exte.Chang(preco_unit));
        jTextField_custo.setText(exte.Chang(custo));
        this.jTextField_valor_iva.setText(exte.Chang(valor_iva));
        this.jTextField_margem_de_lucro.setText(exte.Chang(margem));
    }

    String Convert(String param) {
        param = param.replace('.', ' ');
        String[] split = param.split(" ");
        param = "0";
        for (String split1 : split) {
            param += split1;
        }
        param = param.replace(',', '.');
        return param;
    }

}

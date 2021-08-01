/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import Controle.TextPrompt;
import Controle.ControloBD;
import Controle.Extenso;
import Controle.Validação;
import Modelo.ModeloColaborador;
import Modelo.ModeloProduto;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class EditarProduto extends javax.swing.JFrame {

    /**
     * Creates new form EditarProduto
     */
    Validação validacao = new Validação();

    int id;
    String perfil;
    ControloBD liga = new ControloBD();
    String regime = "Regime geral";
    Extenso exte = new Extenso();
    private int idProduto;

    public EditarProduto() {
        try {
            initComponents();
            exte.setMeoda(" Kwanza(s)");
            TextPrompt prompt1 = new TextPrompt("Código", jTextField_codigo);
            TextPrompt prompt2 = new TextPrompt("Código de barra", jTextField_codigo_de_barra);
            TextPrompt prompt4 = new TextPrompt("Descrição", jTextField_descricao);
            TextPrompt prompt5 = new TextPrompt("Origem", jTextField_origem);
            TextPrompt prompt3 = new TextPrompt("Modelo", jTextField_modelo);
            TextPrompt prompt6 = new TextPrompt("Quantidade em stoque", jTextField_existencia);
            TextPrompt prompt7 = new TextPrompt("Preço", jTextField_preco);
            TextPrompt prompt8 = new TextPrompt("Marca", jTextField_marca);
            this.jLabel10_validacao_codigo.setVisible(false);
            this.jLabel10_validacao_descricao.setVisible(false);
            this.jLabel10_validacao_preco.setVisible(false);
            this.jLabel10_validacao_marca.setVisible(false);
            this.jLabel10_validacao_existemcia.setVisible(false);

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
        this.jLabel4.setVisible(false);
        this.jTextField_existencia.setVisible(false);

        boolean confec = jCheckBox_confecionavel.isSelected();
        jTextField_confec.setVisible(confec);
        jLabel_tempo.setVisible(confec);
        if (confec) {
            jTextField_confec.setText("1");
        } else {
            jTextField_confec.setText("-1");
        }

    }

    public EditarProduto(int id, String perfil, ModeloProduto modeP, String caminho) {
        try {
            initComponents();
            liga.setCaminho(caminho);
            TextPrompt prompt1 = new TextPrompt("Código", jTextField_codigo);
            TextPrompt prompt2 = new TextPrompt("Código de barra", jTextField_codigo_de_barra);
            TextPrompt prompt4 = new TextPrompt("Descrição", jTextField_descricao);
            TextPrompt prompt5 = new TextPrompt("Origem", jTextField_origem);
            TextPrompt prompt3 = new TextPrompt("Modelo", jTextField_modelo);
            TextPrompt prompt6 = new TextPrompt("Quantidade em stoque", jTextField_existencia);
            TextPrompt prompt7 = new TextPrompt("Preço", jTextField_preco);
            TextPrompt prompt8 = new TextPrompt("Marca", jTextField_marca);
            this.jLabel10_validacao_codigo.setVisible(false);
            this.jLabel10_validacao_descricao.setVisible(false);
            this.jLabel10_validacao_preco.setVisible(false);
            this.jLabel10_validacao_marca.setVisible(false);
            this.jLabel10_validacao_existemcia.setVisible(false);

            this.id = id;
            this.perfil = perfil;
            this.idProduto = modeP.getIdproduto();
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
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
        }
        this.jLabel4.setVisible(false);
        this.jTextField_existencia.setVisible(false);

        liga.conexao();
        liga.executeSql("select * from produto WHERE idproduto= "+this.idProduto);
        try {

            if (liga.rs.first()) {
                this.jTextField_codigo.setText(liga.rs.getString("codigo"));
                this.jTextField_codigo_de_barra.setText(liga.rs.getString("codigo_de_barra"));
                this.jTextField_descricao.setText(liga.rs.getString("descricao"));
                this.jTextField_existencia.setText(liga.rs.getString("existencia"));
                this.jTextField_iva.setText("14");

                if (liga.rs.getBoolean("imposto_iva")) {
                    this.jRadioButton_iva.setSelected(true);
                    jComboBox_Iva.setVisible(false);
                    jLabel9.setVisible(false);
                } else {
                    this.jRadioButton_isento.setSelected(true);
                    jComboBox_Iva.setVisible(true);
                    jLabel9.setVisible(true);
                }

                this.jTextField_marca.setText(liga.rs.getString("marca"));
                this.jTextField_modelo.setText(liga.rs.getString("modelo"));
                this.jTextField_origem.setText(liga.rs.getString("origem"));
        
                this.jTextField_nomeDaFoto.setText(liga.rs.getString("nome_da_foto"));
                
                if (liga.rs.getString("custo")!=null)
                    this.jTextField_custo.setText(exte.Chang(liga.rs.getString("custo")));
                if (liga.rs.getString("preco_unitario")!=null)
                    this.jTextField_preco.setText(exte.Chang(liga.rs.getString("preco_unitario")));
                if (liga.rs.getString("preco")!=null)
                    this.jTextField_preco_venda.setText(exte.Chang(liga.rs.getString("preco")));
                if (liga.rs.getString("valor_iva")!=null)
                    this.jTextField_valor_iva.setText(exte.Chang(liga.rs.getString("valor_iva")));
                if (liga.rs.getString("margem_de_lucro")!=null)
                this.jTextField_margem_de_lucro.setText(exte.Chang(liga.rs.getString("margem_de_lucro")));
                if (liga.rs.getString("lucro")!=null)
                    this.jTextField_lucro1.setText(exte.Chang(liga.rs.getString("lucro")));
                
                this.revert(liga.rs.getBytes("foto"));

                //
                jCheckBox_medido_em_balanca.setSelected(liga.rs.getBoolean("medido_em_balanca"));
                jCheckBox_expira.setSelected(liga.rs.getBoolean("expira"));
                jCheckBox_medido_em_m_2.setSelected(liga.rs.getBoolean("metros"));
                jCheckBox_preco_apartir_do_atendimento.setSelected(liga.rs.getBoolean("preco_no_atendimento"));
                jCheckBox_confecionavel.setSelected(liga.rs.getBoolean("confecionavel"));
                boolean confec = jCheckBox_confecionavel.isSelected();
                //boolean confec = jCheckBox_confecionavel.isSelected();
                jTextField_confec.setVisible(confec);
                jLabel_tempo.setVisible(confec);
                if (confec) {
                    jTextField_confec.setText(liga.rs.getString("tempo"));
                } else {
                    jTextField_confec.setText("-1");
                }

                liga.deconecta();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
        }

    }

    public boolean UpdateProduto() {

        liga.conexao();
        try {
            String sexo;

            String motivo = jComboBox_Iva.getSelectedItem().toString();

            int exist = 0;
            double preco_unit = Double.parseDouble(this.Convert(this.jTextField_preco.getText()));
            double preco = Double.parseDouble(this.Convert(this.jTextField_preco_venda.getText()));
            double custo = Double.parseDouble(this.Convert(this.jTextField_custo.getText()));
            double lucro = Double.parseDouble(this.Convert(this.jTextField_lucro1.getText()));
            double margem = Double.parseDouble(this.Convert(this.jTextField_margem_de_lucro.getText()));
            boolean iva = jRadioButton_iva.isSelected();
            double valor_iva = Double.parseDouble(this.Convert(this.jTextField_valor_iva.getText()));
            if (iva) {
                motivo = null;
//                preco = preco_unit + (preco_unit * 0.14);
            }

            ModeloProduto MC = new ModeloProduto(-1, exist, preco, iva, this.jTextField_codigo.getText(), this.jTextField_codigo_de_barra.getText(),
                    jTextField_descricao.getText(), jTextField_marca.getText(),
                    jTextField_modelo.getText(), this.jTextField_origem.getText(), this.jTextField_nomeDaFoto.getText(), motivo);
            PreparedStatement pst = liga.con.prepareStatement("UPDATE produto SET "
                    + " codigo=?, codigo_de_barra=?, descricao=?, existencia=?, imposto_iva=?, "
                    + " motivo=?, preco=?,marca=?,modelo=?,origem=?, "
                    + " nome_da_foto=?, foto=?, preco_unitario=?, qnt=?,custo=?, lucro=?,"
                    + " margem_de_lucro=?, valor_iva=?,"
                    + " medido_em_balanca=?, expira=?,  metros=?, preco_no_atendimento=?, confecionavel=?, tempo=? "
                    + " WHERE idproduto = " + this.idProduto);
            pst.setString(1, MC.getCodigo());
            pst.setString(2, MC.getCodigo_de_barra());
            pst.setString(3, MC.getDescricao());
            pst.setInt(4, MC.getExistencia());
            pst.setBoolean(5, MC.isImposto_iva());
            if (!"Regime geral".equals(this.regime)) {
                pst.setString(6, " ");
            } else {
                pst.setString(6, MC.getMotivo());
            }
            pst.setDouble(7, MC.getPreco());
            pst.setString(8, MC.getMarca());
            pst.setString(9, MC.getModelo());
            pst.setString(10, MC.getOrigem());
            pst.setString(11, MC.getNome_foto());
            pst.setBytes(12, this.photo);
            pst.setDouble(13, preco_unit);
            pst.setDouble(14, 0);
            pst.setDouble(15, custo);
            pst.setDouble(16, lucro);
            pst.setDouble(17, margem);
            pst.setDouble(18, valor_iva);
            //+ " expira,  metros, preco_no_atendimento, confecionavel, tempo) "
            pst.setBoolean(19, jCheckBox_medido_em_balanca.isSelected());
            pst.setBoolean(20, jCheckBox_expira.isSelected());
            pst.setBoolean(21, jCheckBox_medido_em_m_2.isSelected());
            pst.setBoolean(22, jCheckBox_preco_apartir_do_atendimento.isSelected());
            boolean confec = jCheckBox_confecionavel.isSelected();
            pst.setBoolean(23, confec);
            if (confec) {
                pst.setInt(24, Integer.parseInt(jTextField_confec.getText()));
            } else {
                pst.setDouble(24, -1);
            }

            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Dados não inseridos", JOptionPane.WARNING_MESSAGE);
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

        IVA = new javax.swing.ButtonGroup();
        jPanel_Add_produto = new javax.swing.JPanel();
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
        jLabel5 = new javax.swing.JLabel();
        jTextField_codigo_de_barra = new javax.swing.JTextField();
        jTextField_codigo = new javax.swing.JTextField();
        jTextField_descricao = new javax.swing.JTextField();
        jTextField_iva = new javax.swing.JTextField();
        jTextField_existencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton_isento = new javax.swing.JRadioButton();
        jRadioButton_iva = new javax.swing.JRadioButton();
        jComboBox_Iva = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        jTextField_origem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_preco = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10_validacao_codigo = new javax.swing.JLabel();
        jLabel10_validacao_descricao = new javax.swing.JLabel();
        jLabel10_validacao_existemcia = new javax.swing.JLabel();
        jLabel10_validacao_preco = new javax.swing.JLabel();
        jTextField_custo = new javax.swing.JTextField();
        jTextField_margem_de_lucro = new javax.swing.JTextField();
        jTextField_preco_venda = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_valor_iva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField_lucro1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField_nomeDaFoto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel_Foto = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField_marca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField_modelo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel10_validacao_marca = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox_medido_em_balanca = new javax.swing.JCheckBox();
        jCheckBox_expira = new javax.swing.JCheckBox();
        jCheckBox_entrada_com_serie = new javax.swing.JCheckBox();
        jCheckBox_medido_em_m_2 = new javax.swing.JCheckBox();
        jCheckBox_preco_apartir_do_atendimento = new javax.swing.JCheckBox();
        jCheckBox_confecionavel = new javax.swing.JCheckBox();
        jLabel_tempo = new javax.swing.JLabel();
        jTextField_confec = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Add_produto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Add_produto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(27, 167, 125));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Editar Produto");
        jPanel20.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 19, 1230, 36));

        jPanel_Add_produto.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 1230, -1));

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
        jLabel1.setText("Código de barra");

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Código *");

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel3.setText("Descrição *");

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel4.setText("Existência *");

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Origem");

        jTextField_codigo_de_barra.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_codigo.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_descricao.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jTextField_iva.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField_iva.setText("14");

        jTextField_existencia.setEditable(false);
        jTextField_existencia.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_existencia.setText("0");

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

        jTextField_origem.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel7.setText("%");

        jTextField_preco.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jTextField_preco.setText("0,00");
        jTextField_preco.setToolTipText("");
        jTextField_preco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField_precoMouseExited(evt);
            }
        });
        jTextField_preco.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField_precoPropertyChange(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel11.setText("Preço(Kwanza)");

        jLabel10_validacao_codigo.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_codigo.setText("Este campo não pode estar vazio.");

        jLabel10_validacao_descricao.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_descricao.setText("Este campo não pode estar vazio.");

        jLabel10_validacao_existemcia.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_existemcia.setText("Entrada inválida. Só é permitido valor numérico.");

        jLabel10_validacao_preco.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_preco.setText("Entrada inválida. Só é permitido valor numérico.");

        jTextField_custo.setText("0,00");
        jTextField_custo.setToolTipText("");

        jTextField_margem_de_lucro.setEditable(false);
        jTextField_margem_de_lucro.setText("0,00");
        jTextField_margem_de_lucro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_margem_de_lucroActionPerformed(evt);
            }
        });

        jTextField_preco_venda.setEditable(false);
        jTextField_preco_venda.setText("0,00");

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel12.setText("Custo (Kwanza)");

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel13.setText("P. Venda (Kwanza)");

        jLabel14.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel14.setText("Lucro (Kwanza)");

        jTextField_valor_iva.setEditable(false);
        jTextField_valor_iva.setText("0,00");

        jLabel15.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel15.setText("Valor IVA (Kwanza)");

        jTextField_lucro1.setEditable(false);
        jTextField_lucro1.setText("0,00");
        jTextField_lucro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_lucro1ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel16.setText("Margem de Lucro (%)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jComboBox_Iva, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_valor_iva)
                            .addComponent(jTextField_margem_de_lucro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_preco_venda, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_lucro1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jLabel9)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel10_validacao_preco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTextField_preco)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_custo)
                            .addComponent(jTextField_codigo_de_barra)
                            .addComponent(jTextField_descricao)
                            .addComponent(jTextField_codigo)
                            .addComponent(jTextField_origem)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jRadioButton_iva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton_isento)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel10_validacao_codigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10_validacao_descricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10_validacao_existemcia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_existencia))))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(1, 1, 1)
                .addComponent(jLabel10_validacao_codigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_codigo_de_barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_descricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1)
                .addComponent(jLabel10_validacao_descricao)
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_existencia)
                    .addComponent(jLabel4))
                .addGap(1, 1, 1)
                .addComponent(jLabel10_validacao_existemcia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextField_origem))
                    .addComponent(jLabel5))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jRadioButton_isento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jRadioButton_iva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextField_iva))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox_Iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_custo, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel10_validacao_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap())
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

        jTextField_marca.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel8.setText("Marca");

        jTextField_modelo.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel10.setText("Modelo");

        jLabel10_validacao_marca.setForeground(new java.awt.Color(153, 0, 0));
        jLabel10_validacao_marca.setText("Este campo não pde estar vazio.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jButton3))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField_modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10_validacao_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10_validacao_marca, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_nomeDaFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(304, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        jPanel_Add_produto.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 900, 620));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Outras opções"));

        jCheckBox_medido_em_balanca.setText("Medido em balança");

        jCheckBox_expira.setText("Contém lote (Expira)");

        jCheckBox_entrada_com_serie.setText("Entrada com número de série");

        jCheckBox_medido_em_m_2.setText("Medido metros quadrado");

        jCheckBox_preco_apartir_do_atendimento.setText("Preço de venda apartir do atendimento");

        jCheckBox_confecionavel.setText("Confecionável");
        jCheckBox_confecionavel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox_confecionavelMouseClicked(evt);
            }
        });
        jCheckBox_confecionavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_confecionavelActionPerformed(evt);
            }
        });

        jLabel_tempo.setText("Tempo (Minutos):");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox_medido_em_balanca)
                    .addComponent(jCheckBox_expira)
                    .addComponent(jCheckBox_entrada_com_serie)
                    .addComponent(jCheckBox_medido_em_m_2)
                    .addComponent(jCheckBox_preco_apartir_do_atendimento)
                    .addComponent(jCheckBox_confecionavel))
                .addGap(0, 95, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_tempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_confec))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jCheckBox_medido_em_balanca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_expira)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_entrada_com_serie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_medido_em_m_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_preco_apartir_do_atendimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_confecionavel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_tempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_confec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel_Add_produto.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 60, 320, 270));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1231, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_produto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Add_produto, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))
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
//        new Principal3(this.id, this.perfil).setVisible(true);
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

    private void jRadioButton_ivaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_ivaMouseClicked
        this.jLabel9.setVisible(false);
        this.jComboBox_Iva.setVisible(false);
        calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());

    }//GEN-LAST:event_jRadioButton_ivaMouseClicked

    private void jRadioButton_isentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_isentoMouseClicked
        this.jLabel9.setVisible(true);
        this.jComboBox_Iva.setVisible(true);
        calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());

    }//GEN-LAST:event_jRadioButton_isentoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean r1, r2, r3, r4, r5;

        if (!this.jTextField_codigo.getText().isEmpty()) {
            this.jLabel10_validacao_codigo.setVisible(false);
            r5 = true;
        } else {
            this.jLabel10_validacao_codigo.setVisible(true);
            r5 = false;
        }

        if (validacao.SoNumero(this.jTextField_existencia.getText()) && !this.jTextField_existencia.getText().isEmpty()) {
            r1 = true;
            this.jLabel10_validacao_existemcia.setVisible(false);
        } else {
            this.jLabel10_validacao_existemcia.setVisible(true);
            r1 = false;
        }
        if (!this.jTextField_descricao.getText().isEmpty()) {
            r4 = true;
            this.jLabel10_validacao_descricao.setVisible(false);
        } else {
            this.jLabel10_validacao_descricao.setVisible(true);
            r4 = false;
        }

        if (!this.jTextField_marca.getText().isEmpty()) {
            this.jLabel10_validacao_marca.setVisible(false);
            r2 = true;

        } else {
            this.jLabel10_validacao_marca.setVisible(true);
            r2 = false;
        }
        if (!this.jTextField_preco.getText().isEmpty() && validacao.SoNumero(Convert(this.jTextField_preco.getText()))) {
            r3 = true;
            jLabel10_validacao_preco.setVisible(false);
        } else {
            jLabel10_validacao_preco.setVisible(true);
            r3 = false;
        }

        if (r1 && r2 && r3 && r4 && r5) {
            boolean confec = jCheckBox_confecionavel.isSelected();
            if (confec) {
                if (Integer.parseInt(jTextField_confec.getText()) >= 1) {
                    if (UpdateProduto()) {
//                        System.out.println("1");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "O tempo de confecção do produto deve ser um número inteiro e maior que 0 minuto", "Aviso", JOptionPane.WARNING_MESSAGE);
                    jTextField_confec.requestFocus();
                }
            } else {
                if (UpdateProduto()) {
//                   System.out.println("1");
                }
                jTextField_confec.setText("-1");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox_confecionavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_confecionavelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_confecionavelActionPerformed

    private void jTextField_precoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_precoMouseExited
        calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());

    }//GEN-LAST:event_jTextField_precoMouseExited

    private void jTextField_precoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField_precoPropertyChange
        // calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());

    }//GEN-LAST:event_jTextField_precoPropertyChange

    private void jTextField_margem_de_lucroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_margem_de_lucroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_margem_de_lucroActionPerformed

    private void jTextField_lucro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_lucro1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_lucro1ActionPerformed

    private void jCheckBox_confecionavelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox_confecionavelMouseClicked
        boolean confec = jCheckBox_confecionavel.isSelected();
        jTextField_confec.setVisible(confec);
        jLabel_tempo.setVisible(confec);
        if (confec) {
            jTextField_confec.setText("1");
        } else {
            jTextField_confec.setText("-1");
        }
    }//GEN-LAST:event_jCheckBox_confecionavelMouseClicked

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
            java.util.logging.Logger.getLogger(EditarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new EditarProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup IVA;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox_confecionavel;
    private javax.swing.JCheckBox jCheckBox_entrada_com_serie;
    private javax.swing.JCheckBox jCheckBox_expira;
    private javax.swing.JCheckBox jCheckBox_medido_em_balanca;
    private javax.swing.JCheckBox jCheckBox_medido_em_m_2;
    private javax.swing.JCheckBox jCheckBox_preco_apartir_do_atendimento;
    private javax.swing.JComboBox<String> jComboBox_Iva;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel10_validacao_codigo;
    private javax.swing.JLabel jLabel10_validacao_descricao;
    private javax.swing.JLabel jLabel10_validacao_existemcia;
    private javax.swing.JLabel jLabel10_validacao_marca;
    private javax.swing.JLabel jLabel10_validacao_preco;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel jLabel_tempo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_Add_produto;
    private javax.swing.JRadioButton jRadioButton_isento;
    private javax.swing.JRadioButton jRadioButton_iva;
    private javax.swing.JTextField jTextField_codigo;
    private javax.swing.JTextField jTextField_codigo_de_barra;
    private javax.swing.JTextField jTextField_confec;
    private javax.swing.JTextField jTextField_custo;
    private javax.swing.JTextField jTextField_descricao;
    private javax.swing.JTextField jTextField_existencia;
    private javax.swing.JTextField jTextField_iva;
    private javax.swing.JTextField jTextField_lucro1;
    private javax.swing.JTextField jTextField_marca;
    private javax.swing.JTextField jTextField_margem_de_lucro;
    private javax.swing.JTextField jTextField_modelo;
    private javax.swing.JTextField jTextField_nomeDaFoto;
    private javax.swing.JTextField jTextField_origem;
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

//    private void calcularPreco(String lucro_param, String cust_param) {
//
//    }
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

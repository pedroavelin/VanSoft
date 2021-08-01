/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. origem
 */
package Vista;

import Controle.ControloBD;
import Controle.Extenso;
import Controle.Tempo;
import java.awt.Color;
import Controle.TextPrompt;
import Controle.Validação;
import Modelo.ModeloCliente;
import Modelo.ModeloEmpresa;
import Modelo.ModeloNota;
import Modelo.ModeloPDF;
import Modelo.ModeloTabela;
import Modelo.ModeloTarefa;
import Modelo.ModeloUtilizador;
import java.awt.Toolkit;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class Add_entrada extends javax.swing.JFrame {

    int id_turno = -1;
    int numero = 0;
    Controle.ControloBD liga = new ControloBD();
    int id;
    String perfil;
    Tempo tem = new Tempo();
    DefaultListModel<String> model_List;
    String select;
    int id_pesquisa = -1;
    Extenso exte = new Extenso();
    ArrayList<Integer> Id_armazens = new ArrayList();
    ArrayList dados_entrada = new ArrayList();
    String[] colunas_entrada = new String[]{"ID", "Descricao", "Custo\n(Kwanza)", "Quantidade", "Total\n(Kwanza)", "Lote", "Data de\nFabrico", "Data de\nExpiração"};
    Validação validacao = new Validação();
    private int linha;
    private int coluna;
    private boolean estado;
    private int origem = -1;
    private int registo;

    /**
     * Creates new form AddUtilizador
     */
    public Add_entrada() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        jLabel_id_produto.setVisible(false);
        TextPrompt prompt1 = new TextPrompt("Pesquisar", jTextField_Filtrar);
//        TextPrompt prompt = new TextPrompt("Pesquisar", jTextField_Filtrar);
        TextPrompt promp2 = new TextPrompt("Descrição ou o Cod. de barra do produto", jTextField_pesquisa);
        TextPrompt prompt3 = new TextPrompt("Quantidade", jTextField_qnt_entrada);
        TextPrompt prompt4 = new TextPrompt("Lote", jTextField_lote);
        model_List = new <String> DefaultListModel();
        this.select = "select * from produto";
        PreencherTableProduto("select * from  produto order by descricao");
        jComboBox_armazem.removeAllItems();
        liga.conexao();
        liga.executeSql("Select * from armazem order by id_armazem asc");

        try {
            liga.rs.first();
            do {
                jComboBox_armazem.addItem(liga.rs.getString("descricao"));
                this.Id_armazens.add(liga.rs.getInt("id_armazem"));
                System.out.println(liga.rs.getInt("id_armazem"));
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex + "\nSem armazens cadatastrado.\nNão é possível realizar a entrada em stock.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

        liga.deconecta();

        jPanel_fornecedores.setVisible(false);
        jPanel_origem.setVisible(false);
        PreencherTableForncedores();
        PreencherTableOrigem();

        jPanel_export.setEnabled(false);
        jPanel_imprimir.setEnabled(false);
    }

    public Add_entrada(int id, String perfil, String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);
        jLabel_id_produto.setVisible(false);
        this.id = id;
        this.perfil = perfil;
        jPanel_export.setEnabled(false);
        jPanel_imprimir.setEnabled(false);
        TextPrompt prompt1 = new TextPrompt("Pesquisar", jTextField_Filtrar);
        TextPrompt promp2 = new TextPrompt("Descrição ou o Cod. de barra do produto", jTextField_pesquisa);
        TextPrompt prompt3 = new TextPrompt("Quantidade", jTextField_qnt_entrada);
        TextPrompt prompt4 = new TextPrompt("Lote", jTextField_lote);
        model_List = new <String> DefaultListModel();
        
        jTextField_fornecedor_id.setText("0");
        jTextField_fornecedor_nif.setText("indiferenciado");
        jTextField_fornecedor_nome.setText("Fornecedor indiferenciado.");

        this.select = "select * from produto";
        PreencherTableProduto("select * from  produto order by descricao");

        this.jInternalFrame_buscar_produto.setVisible(false);
        this.jInternalFrame_entrada.setVisible(true);
        this.jInternalFrame_lista_de_produto.setVisible(false);
        jComboBox_armazem.removeAllItems();
        liga.conexao();
        liga.executeSql("Select * from armazem order by id_armazem asc");
        try {
            liga.rs.first();
            do {
                jComboBox_armazem.addItem(liga.rs.getString("descricao"));
                this.Id_armazens.add(liga.rs.getInt("id_armazem"));
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Sem armazens cadatastrado.\nNão é possível realizar a entrada em stock.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

        liga.deconecta();

        jPanel_fornecedores.setVisible(false);
        jPanel_origem.setVisible(false);

        PreencherTableForncedores();
        PreencherTableOrigem();

        liga.conexao();
        liga.executeSql("select *  from turno where idutilizador = " + this.id + " Order by idturno asc");
        try {
            if (liga.rs.last()) {
                estado = liga.rs.getBoolean("estado");
                this.numero = liga.rs.getInt("numero");
                this.id_turno = liga.rs.getInt("idturno");
                if (!estado) {
                    int r = JOptionPane.showConfirmDialog(rootPane, "Deseja abrir o turno?", "Não podes fazer um documento sem antes abrir o turno.", JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        this.dispose();
                        new Principal4(this.id, this.perfil,liga.getCaminho()).setVisible(true);

                    } else {
                        this.dispose();
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jInternalFrame_buscar_produto = new javax.swing.JInternalFrame();
        jPanelTopMenu1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextField_qnt_entrada = new javax.swing.JTextField();
        jLabel_qnt_entrada = new javax.swing.JLabel();
        jCalendar_data_expiracao = new com.toedter.calendar.JCalendar();
        jCalendar_data_fabrico = new com.toedter.calendar.JCalendar();
        jLabel_data_fabrico = new javax.swing.JLabel();
        jLabel_data_expiracao = new javax.swing.JLabel();
        jTextField_lote = new javax.swing.JTextField();
        jLabel_qnt_entrada1 = new javax.swing.JLabel();
        jCheckBox_expira = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jTextField_custo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_preco = new javax.swing.JTextField();
        jTextField_preco_venda = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField_valor_iva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField_margem_de_lucro = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_lucro1 = new javax.swing.JTextField();
        jLabel_desc = new javax.swing.JLabel();
        jLabel_id_produto = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jTextField_pesquisa = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton_guardar = new javax.swing.JButton();
        jButton_fechar = new javax.swing.JButton();
        jInternalFrame_lista_de_produto = new javax.swing.JInternalFrame();
        jPanel_Utilizadores = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jComboBox_Filtrar = new javax.swing.JComboBox<String>();
        jTextField_Filtrar = new javax.swing.JTextField();
        jButton_Filtrar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jComboBox_OrdenarServico = new javax.swing.JComboBox<String>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProduto = new javax.swing.JTable();
        jInternalFrame_entrada = new javax.swing.JInternalFrame();
        jPanelTopMenu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        T_Tabela_entrada = new javax.swing.JTable();
        jPanel_imprimir = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel_export = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jButton_guardar_entrada = new javax.swing.JButton();
        jButton_fechar_entrada = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_pagamento = new javax.swing.JTextField();
        jTextField_total = new javax.swing.JTextField();
        jTextField_falta = new javax.swing.JTextField();
        jButton_call_buscar_form_entrada = new javax.swing.JButton();
        jComboBox_armazem = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton_pagar = new javax.swing.JRadioButton();
        jRadioButton_nao_pagar = new javax.swing.JRadioButton();
        jTextField_fornecedor_nome = new javax.swing.JTextField();
        jTextField_fornecedor_nif = new javax.swing.JTextField();
        jTextField_fornecedor_id = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel_origem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableOrigem = new javax.swing.JTable();
        jRadioButton_caixa = new javax.swing.JRadioButton();
        jRadioButton_banco = new javax.swing.JRadioButton();
        jPanel_fornecedores = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableFornecedores = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jInternalFrame_buscar_produto.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrame_buscar_produto.setVisible(true);

        jPanelTopMenu1.setBackground(new java.awt.Color(27, 167, 125));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Busca de produtos");

        javax.swing.GroupLayout jPanelTopMenu1Layout = new javax.swing.GroupLayout(jPanelTopMenu1);
        jPanelTopMenu1.setLayout(jPanelTopMenu1Layout);
        jPanelTopMenu1Layout.setHorizontalGroup(
            jPanelTopMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanelTopMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE))
        );
        jPanelTopMenu1Layout.setVerticalGroup(
            jPanelTopMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(jPanelTopMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopMenu1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)))
        );

        jLabel_qnt_entrada.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel_qnt_entrada.setText("Quantidade");

        jCalendar_data_expiracao.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jCalendar_data_fabrico.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel_data_fabrico.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel_data_fabrico.setText("D. Fabrico");

        jLabel_data_expiracao.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel_data_expiracao.setText("D. Expiração");

        jLabel_qnt_entrada1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel_qnt_entrada1.setText("Nº do Lote");

        jCheckBox_expira.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jCheckBox_expira.setText("Expira");
        jCheckBox_expira.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jCalendar_data_fabrico, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                        .addGap(54, 54, 54)
                        .addComponent(jCalendar_data_expiracao, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel_data_fabrico, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(285, 285, 285)
                                .addComponent(jLabel_data_expiracao, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel_qnt_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_qnt_entrada1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_lote, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                                    .addComponent(jTextField_qnt_entrada)))
                            .addComponent(jCheckBox_expira))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_qnt_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_qnt_entrada))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_lote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_qnt_entrada1))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox_expira)
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_data_expiracao)
                    .addComponent(jLabel_data_fabrico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCalendar_data_fabrico, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCalendar_data_expiracao, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTextField_custo.setText("0,00");
        jTextField_custo.setToolTipText("");
        jTextField_custo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField_custoMouseExited(evt);
            }
        });
        jTextField_custo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_custoActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel12.setText("Custo (Kwanza)");

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel11.setText("Preço(Kwanza)");

        jTextField_preco.setEditable(false);
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

        jTextField_preco_venda.setEditable(false);
        jTextField_preco_venda.setText("0,00");

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel13.setText("P. Venda (Kwanza)");

        jTextField_valor_iva.setEditable(false);
        jTextField_valor_iva.setText("0,00");

        jLabel15.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel15.setText("Valor IVA (Kwanza)");

        jTextField_margem_de_lucro.setEditable(false);
        jTextField_margem_de_lucro.setText("0,00");
        jTextField_margem_de_lucro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_margem_de_lucroActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel16.setText("Margem de Lucro (%)");

        jLabel14.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel14.setText("Lucro (Kwanza)");

        jTextField_lucro1.setEditable(false);
        jTextField_lucro1.setText("0,00");
        jTextField_lucro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_lucro1ActionPerformed(evt);
            }
        });

        jLabel_desc.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N

        jLabel_id_produto.setText("jLabel10");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_custo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_preco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField_valor_iva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                            .addComponent(jTextField_margem_de_lucro)
                            .addComponent(jTextField_lucro1)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(17, 17, 17)
                        .addComponent(jTextField_preco_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel_id_produto)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_desc)
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_custo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jTextField_preco_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_valor_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_margem_de_lucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_lucro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel_id_produto)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jButton3.setText("Pesquisar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField_pesquisa.setToolTipText("Coloca a descrição ou o código de barra do produto");

        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton_guardar.setBackground(new java.awt.Color(56, 65, 84));
        jButton_guardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_guardar.setForeground(new java.awt.Color(255, 255, 255));
        jButton_guardar.setText("Guardar");
        jButton_guardar.setBorderPainted(false);
        jButton_guardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_guardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_guardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_guardarMouseExited(evt);
            }
        });
        jButton_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guardarActionPerformed(evt);
            }
        });

        jButton_fechar.setBackground(new java.awt.Color(217, 81, 51));
        jButton_fechar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_fechar.setForeground(new java.awt.Color(255, 255, 255));
        jButton_fechar.setText("Fechar");
        jButton_fechar.setBorderPainted(false);
        jButton_fechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_fechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_fecharMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_fecharMouseExited(evt);
            }
        });
        jButton_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_fecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame_buscar_produtoLayout = new javax.swing.GroupLayout(jInternalFrame_buscar_produto.getContentPane());
        jInternalFrame_buscar_produto.getContentPane().setLayout(jInternalFrame_buscar_produtoLayout);
        jInternalFrame_buscar_produtoLayout.setHorizontalGroup(
            jInternalFrame_buscar_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopMenu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jInternalFrame_buscar_produtoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jInternalFrame_buscar_produtoLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jButton_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame_buscar_produtoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
        );
        jInternalFrame_buscar_produtoLayout.setVerticalGroup(
            jInternalFrame_buscar_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame_buscar_produtoLayout.createSequentialGroup()
                .addComponent(jPanelTopMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame_buscar_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jTextField_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame_buscar_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame_buscar_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(741, Short.MAX_VALUE))
        );

        jInternalFrame_lista_de_produto.setVisible(true);

        jPanel_Utilizadores.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Utilizadores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Utilizadores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(27, 167, 125));

        jLabel22.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Produtos");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 1330, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_Utilizadores.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 1330, -1));

        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel22.setToolTipText("Ver os dados completos dos utilizadores");
        jPanel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel22.setOpaque(false);
        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel22MouseClicked(evt);
            }
        });

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N
        jLabel35.setText("jLabel28");

        jLabel36.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Actualizar");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_Utilizadores.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel27.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Filtrar por");

        jComboBox_Filtrar.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_Filtrar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "descricao", "codigo_de_barra", "origem" }));
        jComboBox_Filtrar.setToolTipText("");
        jComboBox_Filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_FiltrarActionPerformed(evt);
            }
        });

        jTextField_Filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_FiltrarActionPerformed(evt);
            }
        });

        jButton_Filtrar.setText("Filtar");
        jButton_Filtrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_FiltrarMouseClicked(evt);
            }
        });
        jButton_Filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_Filtrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_Filtrar, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Filtrar, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jComboBox_Filtrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Filtrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Filtrar))
                .addContainerGap())
        );

        jPanel_Utilizadores.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 510, -1));

        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Ordenar por");

        jButton7.setText("Ordenar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jComboBox_OrdenarServico.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_OrdenarServico.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "descricao asc", "descricao desc", "motivo asc", "motivo desc", "preco asc", "preco desc", "codigo asc", "codigo desc", "codigo_de_barra asc", "codigo_de_barra desc", "existencia asc", "existencia desc", "imposto_iva asc", "imposto_iva desc", "marca asc", "marca desc", "modelo asc", "modelo desc", "origem asc", "origem desc" }));
        jComboBox_OrdenarServico.setToolTipText("");
        jComboBox_OrdenarServico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_OrdenarServicoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox_OrdenarServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_OrdenarServico)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel_Utilizadores.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 370, -1));

        jTableProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Nome de utilizador", "Telefone", "Último login"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableProduto);

        javax.swing.GroupLayout jInternalFrame_lista_de_produtoLayout = new javax.swing.GroupLayout(jInternalFrame_lista_de_produto.getContentPane());
        jInternalFrame_lista_de_produto.getContentPane().setLayout(jInternalFrame_lista_de_produtoLayout);
        jInternalFrame_lista_de_produtoLayout.setHorizontalGroup(
            jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jPanel_Utilizadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame_lista_de_produtoLayout.setVerticalGroup(
            jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Utilizadores, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 955, Short.MAX_VALUE))
        );

        jInternalFrame_entrada.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrame_entrada.setVisible(true);
        jInternalFrame_entrada.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelTopMenu.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Entrada em stock");

        javax.swing.GroupLayout jPanelTopMenuLayout = new javax.swing.GroupLayout(jPanelTopMenu);
        jPanelTopMenu.setLayout(jPanelTopMenuLayout);
        jPanelTopMenuLayout.setHorizontalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1370, Short.MAX_VALUE))
        );
        jPanelTopMenuLayout.setVerticalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopMenuLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jInternalFrame_entrada.getContentPane().add(jPanelTopMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame_entrada.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 554, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 155, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrame_entrada.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1166, 554, -1, -1));

        T_Tabela_entrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Produto", "Qnt", "Custo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        T_Tabela_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                T_Tabela_entradaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(T_Tabela_entrada);

        jInternalFrame_entrada.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 1360, 90));

        jPanel_imprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_imprimir.setToolTipText("Emitir um relatório dos dados presentes na tabela em PDF e Imprimir");
        jPanel_imprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_imprimir.setOpaque(false);
        jPanel_imprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_imprimirMouseClicked(evt);
            }
        });

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_print_90px.png"))); // NOI18N
        jLabel39.setText("jLabel28");

        jLabel40.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Exportar e Imprimir");

        javax.swing.GroupLayout jPanel_imprimirLayout = new javax.swing.GroupLayout(jPanel_imprimir);
        jPanel_imprimir.setLayout(jPanel_imprimirLayout);
        jPanel_imprimirLayout.setHorizontalGroup(
            jPanel_imprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_imprimirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_imprimirLayout.setVerticalGroup(
            jPanel_imprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_imprimirLayout.createSequentialGroup()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame_entrada.getContentPane().add(jPanel_imprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(416, 84, -1, 120));

        jPanel_export.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_export.setToolTipText("Emitir um relatório dos dados presentes na tabela em  em PDF ");
        jPanel_export.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_export.setOpaque(false);
        jPanel_export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_exportMouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_pdf_2_90px.png"))); // NOI18N
        jLabel37.setText("jLabel28");

        jLabel38.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Exportar PDF");

        javax.swing.GroupLayout jPanel_exportLayout = new javax.swing.GroupLayout(jPanel_export);
        jPanel_export.setLayout(jPanel_exportLayout);
        jPanel_exportLayout.setHorizontalGroup(
            jPanel_exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_exportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_exportLayout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_exportLayout.setVerticalGroup(
            jPanel_exportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_exportLayout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addContainerGap())
        );

        jInternalFrame_entrada.getContentPane().add(jPanel_export, new org.netbeans.lib.awtextra.AbsoluteConstraints(278, 84, -1, 120));

        jButton_guardar_entrada.setBackground(new java.awt.Color(56, 65, 84));
        jButton_guardar_entrada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_guardar_entrada.setForeground(new java.awt.Color(255, 255, 255));
        jButton_guardar_entrada.setText("Guardar");
        jButton_guardar_entrada.setBorderPainted(false);
        jButton_guardar_entrada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_guardar_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_guardar_entradaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_guardar_entradaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_guardar_entradaMouseExited(evt);
            }
        });
        jButton_guardar_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guardar_entradaActionPerformed(evt);
            }
        });
        jInternalFrame_entrada.getContentPane().add(jButton_guardar_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 84, 98, 120));

        jButton_fechar_entrada.setBackground(new java.awt.Color(217, 81, 51));
        jButton_fechar_entrada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_fechar_entrada.setForeground(new java.awt.Color(255, 255, 255));
        jButton_fechar_entrada.setText("Anular");
        jButton_fechar_entrada.setBorderPainted(false);
        jButton_fechar_entrada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_fechar_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_fechar_entradaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_fechar_entradaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_fechar_entradaMouseExited(evt);
            }
        });
        jButton_fechar_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_fechar_entradaActionPerformed(evt);
            }
        });
        jInternalFrame_entrada.getContentPane().add(jButton_fechar_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 84, 97, 120));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Total"));

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel3.setText("Total");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel4.setText("Pagamento");

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel5.setText("Valor em falta");

        jTextField_pagamento.setEditable(false);
        jTextField_pagamento.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jTextField_pagamento.setText("0,00");

        jTextField_total.setEditable(false);
        jTextField_total.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jTextField_total.setText("0,00");

        jTextField_falta.setEditable(false);
        jTextField_falta.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jTextField_falta.setText("0,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_falta)
                    .addComponent(jTextField_total, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(jTextField_pagamento))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_pagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_falta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );

        jInternalFrame_entrada.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 490, 160));

        jButton_call_buscar_form_entrada.setBackground(new java.awt.Color(217, 81, 51));
        jButton_call_buscar_form_entrada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_call_buscar_form_entrada.setForeground(new java.awt.Color(255, 255, 255));
        jButton_call_buscar_form_entrada.setText("Buscar");
        jButton_call_buscar_form_entrada.setBorderPainted(false);
        jButton_call_buscar_form_entrada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_call_buscar_form_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_call_buscar_form_entradaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_call_buscar_form_entradaMouseExited(evt);
            }
        });
        jButton_call_buscar_form_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_call_buscar_form_entradaActionPerformed(evt);
            }
        });
        jInternalFrame_entrada.getContentPane().add(jButton_call_buscar_form_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 84, 97, 120));

        jComboBox_armazem.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jComboBox_armazem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jInternalFrame_entrada.getContentPane().add(jComboBox_armazem, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 240, 30));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel6.setText("Armazem");
        jInternalFrame_entrada.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 100, -1));

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 51)), "Pagamento"));

        buttonGroup2.add(jRadioButton_pagar);
        jRadioButton_pagar.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_pagar.setText("Pagar");
        jRadioButton_pagar.setToolTipText("Realizar o pagamento da entrada em stock");
        jRadioButton_pagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_pagarMouseClicked(evt);
            }
        });

        buttonGroup2.add(jRadioButton_nao_pagar);
        jRadioButton_nao_pagar.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jRadioButton_nao_pagar.setSelected(true);
        jRadioButton_nao_pagar.setText("Não pagar");
        jRadioButton_nao_pagar.setToolTipText("Não realizar o pagamento da entrada em stock");
        jRadioButton_nao_pagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_nao_pagarMouseClicked(evt);
            }
        });

        jTextField_fornecedor_nome.setEditable(false);
        jTextField_fornecedor_nome.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jTextField_fornecedor_nif.setEditable(false);
        jTextField_fornecedor_nif.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jTextField_fornecedor_id.setEditable(false);
        jTextField_fornecedor_id.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel7.setText("Nome");

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel8.setText("NIF");

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel9.setText("ID");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jRadioButton_nao_pagar)
                        .addGap(26, 26, 26)
                        .addComponent(jRadioButton_pagar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_fornecedor_nome)
                            .addComponent(jTextField_fornecedor_nif)
                            .addComponent(jTextField_fornecedor_id, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_nao_pagar)
                    .addComponent(jRadioButton_pagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_fornecedor_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_fornecedor_nif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_fornecedor_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)))
        );

        jPanel_origem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)), "Origem do pagamento"));
        jPanel_origem.setToolTipText("Origem do montante para o pagamento da entrada em stock");

        jTableOrigem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableOrigem.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableOrigem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableOrigemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableOrigem);

        buttonGroup3.add(jRadioButton_caixa);
        jRadioButton_caixa.setSelected(true);
        jRadioButton_caixa.setText("Caixa");
        jRadioButton_caixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_caixaMouseClicked(evt);
            }
        });

        buttonGroup3.add(jRadioButton_banco);
        jRadioButton_banco.setText("Banco");
        jRadioButton_banco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_bancoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel_origemLayout = new javax.swing.GroupLayout(jPanel_origem);
        jPanel_origem.setLayout(jPanel_origemLayout);
        jPanel_origemLayout.setHorizontalGroup(
            jPanel_origemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_origemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_origemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_origemLayout.createSequentialGroup()
                        .addComponent(jRadioButton_caixa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton_banco)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_origemLayout.setVerticalGroup(
            jPanel_origemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_origemLayout.createSequentialGroup()
                .addGroup(jPanel_origemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_caixa)
                    .addComponent(jRadioButton_banco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_fornecedores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)), "Lista de fornecedores"));

        jTableFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableFornecedores.setToolTipText("Lista de fornecedores");
        jTableFornecedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableFornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableFornecedoresMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableFornecedores);

        javax.swing.GroupLayout jPanel_fornecedoresLayout = new javax.swing.GroupLayout(jPanel_fornecedores);
        jPanel_fornecedores.setLayout(jPanel_fornecedoresLayout);
        jPanel_fornecedoresLayout.setHorizontalGroup(
            jPanel_fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_fornecedoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_fornecedoresLayout.setVerticalGroup(
            jPanel_fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_fornecedoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_fornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_origem, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_origem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_fornecedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        jInternalFrame_entrada.getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, 830, 160));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame_lista_de_produto)
            .addComponent(jInternalFrame_buscar_produto)
            .addComponent(jInternalFrame_entrada)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame_entrada, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jInternalFrame_lista_de_produto)
                .addGap(0, 0, 0)
                .addComponent(jInternalFrame_buscar_produto)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_precoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_precoMouseExited
        //calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());
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

    private void jTextField_custoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_custoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_custoActionPerformed

    private void jButton_guardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardarMouseEntered
        this.jButton_guardar.setBackground(new Color(235, 235, 235));
        this.jButton_guardar.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButton_guardarMouseEntered

    private void jButton_guardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardarMouseExited
        this.jButton_guardar.setBackground(new Color(56, 65, 84));
        this.jButton_guardar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton_guardarMouseExited

    private void jButton_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guardarActionPerformed
        if (!jLabel_desc.getText().isEmpty()) {
            if (!jTextField_qnt_entrada.getText().isEmpty() && Double.parseDouble(Convert(jTextField_qnt_entrada.getText())) > 0.0) {
                if (jCheckBox_expira.isSelected()) {
                    if (!jTextField_lote.getText().isEmpty()) {
                        if (jCalendar_data_fabrico.getDate() != jCalendar_data_expiracao.getDate()) {
                            this.jInternalFrame_buscar_produto.setVisible(false);
                            this.jInternalFrame_entrada.setVisible(true);
                            this.jInternalFrame_lista_de_produto.setVisible(false);
                            PreencherTableProduto_entrada();

                            jTextField_pesquisa.setText("0,00");
                            jTextField_qnt_entrada.setText("");
                            jTextField_lote.setText("");
                            jLabel_desc.setText("");
//                            jCalendar_data_expiracao.setDate(null);
//                            jCalendar_data_fabrico.setDate(null);
                            this.jTextField_preco.setText("0,00");
                            this.jTextField_preco_venda.setText("0,00");
                            this.jTextField_custo.setText("0,00");
                            this.jTextField_lucro1.setText("0,00");
                            jTextField_margem_de_lucro.setText("0,00");
                            this.jTextField_valor_iva.setText("0,00");

                        } else {
                            JOptionPane.showMessageDialog(null, "Verifique a data de expiração.\nA data de expiração deve ser superior a data de fabrico", "Aviso", JOptionPane.WARNING_MESSAGE);
                            jCalendar_data_expiracao.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Informa o lote do produto.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        jTextField_lote.requestFocus();
                    }
                } else {
                    this.jInternalFrame_buscar_produto.setVisible(false);
                    this.jInternalFrame_entrada.setVisible(true);
                    this.jInternalFrame_lista_de_produto.setVisible(false);
                    PreencherTableProduto_entrada();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Informa a quantidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
                jTextField_qnt_entrada.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum poduto selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            jTextField_pesquisa.requestFocus();
        }


    }//GEN-LAST:event_jButton_guardarActionPerformed

    private void jButton_fecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_fecharMouseEntered
        this.jButton_fechar.setBackground(new Color(235, 235, 235));
        this.jButton_fechar.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_jButton_fecharMouseEntered

    private void jButton_fecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_fecharMouseExited
        this.jButton_fechar.setBackground(new Color(217, 81, 51));
        this.jButton_fechar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton_fecharMouseExited

    private void jButton_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_fecharActionPerformed
        this.jInternalFrame_buscar_produto.setVisible(false);
        this.jInternalFrame_entrada.setVisible(true);
        this.jInternalFrame_lista_de_produto.setVisible(false);

    }//GEN-LAST:event_jButton_fecharActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
//        System.out.println(this.jComboBox_OrdenarServico.getSelectedItem().toString());
//        System.out.println(this.select + " order by " + this.jComboBox_OrdenarServico.getSelectedItem().toString());
        PreencherTableProduto(this.select + " order by " + this.jComboBox_OrdenarServico.getSelectedItem().toString());
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox_OrdenarServicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarServicoMouseClicked

    }//GEN-LAST:event_jComboBox_OrdenarServicoMouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        this.select = "select * from produto";
        PreencherTableProduto("select * from  produto order by descricao");
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jComboBox_FiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_FiltrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_FiltrarActionPerformed

    private void jTextField_FiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_FiltrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_FiltrarActionPerformed

    private void jButton_FiltrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_FiltrarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_FiltrarMouseClicked

    private void jButton_FiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FiltrarActionPerformed
        String colum = jComboBox_Filtrar.getSelectedItem().toString();
        this.select = "select * from produto where " + colum + " ilike '%" + this.jTextField_Filtrar.getText() + "%'";
        PreencherTableProduto(select);
    }//GEN-LAST:event_jButton_FiltrarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.jInternalFrame_buscar_produto.setVisible(false);
        this.jInternalFrame_entrada.setVisible(false);
        this.jInternalFrame_lista_de_produto.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutoMouseClicked
        int linha = jTableProduto.getSelectedRow();
        if (linha != -1) {
            id_pesquisa = Integer.parseInt(this.jTableProduto.getValueAt(linha, 0).toString());
            //jTextField_pesquisa.setText(this.jTableProduto.getValueAt(linha, 2).toString());
            pesquisa(id_pesquisa);
            this.jInternalFrame_buscar_produto.setVisible(true);
            this.jInternalFrame_entrada.setVisible(false);
            this.jInternalFrame_lista_de_produto.setVisible(false);

        }
    }//GEN-LAST:event_jTableProdutoMouseClicked

    private void T_Tabela_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_T_Tabela_entradaMouseClicked
        //
    }//GEN-LAST:event_T_Tabela_entradaMouseClicked

    private void jPanel_imprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_imprimirMouseClicked
//        T_Tabela_entrada
        if (T_Tabela_entrada.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String file = chooser.getSelectedFile().toString().concat(".pdf");
                try {
                    ModeloPDF e = new ModeloPDF(new File(file), T_Tabela_entrada, "Relatório",liga.getCaminho());

                    e.ExportarVendas("Entrada em stock", true);

                    JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a exportação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jPanel_imprimirMouseClicked

    private void jPanel_exportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_exportMouseClicked
        
            Tempo t = new Tempo();
            ModeloEmpresa ME = new ModeloEmpresa();
            ModeloUtilizador MU = new ModeloUtilizador();
            liga.conexao();
            liga.executeSql("select * from empresa limit 1");
            try {
                if (liga.rs.first()) {
                    ME.setIdempresa(liga.rs.getInt(1));
                    ME.setDesignacao(liga.rs.getString("designacao"));
                    ME.setNif(liga.rs.getString("nif"));
                    ME.setRegisto_comercial(liga.rs.getString("registo_comercial"));
                    ME.setRazao_social(liga.rs.getString("razao_social"));
                    ME.setTelefone((liga.rs.getInt("telefone")));
                    ME.setEmail(liga.rs.getString("email"));
                    ME.setWeb_site(liga.rs.getString("web_site"));
                    ME.setPais(liga.rs.getString("pais"));
                    ME.setProvincia(liga.rs.getString("provincia"));
                    ME.setRua(liga.rs.getString("rua"));
                    ME.setEdificio(liga.rs.getString("edificio"));
                    ME.setCodigo_postal(liga.rs.getString("codigo_postal"));
                    ME.setImagem_logotipo(liga.rs.getString("imagem_logotipo"));
                    
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex,"Erro",JOptionPane.ERROR_MESSAGE);
//                Logger.getLogger(Add_saida.class.getName()).log(Level.SEVERE, null, ex);
            }
            liga.deconecta();
            
            
            liga.conexao();
            liga.executeSql("select * from utilizador where idutilizador=" + id);
            try {
                if (liga.rs.first()) {
                    
                    MU.setIdUtilizador(id);
                    MU.setNif(liga.rs.getString("nif"));
                    MU.setNome_utilizador(liga.rs.getString("nome_utilizador"));
                    MU.setNome(liga.rs.getString("nome"));
                    MU.setTelefone((liga.rs.getInt("telefone")));
                    MU.setEmail(liga.rs.getString("email"));
                    MU.setPerfil(liga.rs.getString("perfil"));
                    MU.setSexo(liga.rs.getString("sexo"));
                    
                }
                
                if (T_Tabela_entrada.getRowCount() > 0) {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
                    chooser.setFileFilter(filter);
                    chooser.setDialogTitle("Guardar Arquivo");
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        String file = chooser.getSelectedFile().toString().concat(".pdf");
                        try {
                            int latnum =this.registo;
                            ModeloPDF e = new ModeloPDF(new File(file), T_Tabela_entrada, "Entrada "+latnum+1,liga.getCaminho());
                           
                            e.setME(ME);
//                                e.setMC(MC);
                            e.setMU(MU);
//                                e.setMF(MF);
                            e.setMC(new ModeloCliente(Integer.parseInt(this.jTextField_fornecedor_id.getText())));
                            e.getMC().setDesignacao(jTextField_fornecedor_nome.getText());
                            e.getMC().setNif(jTextField_fornecedor_nif.getText());
                            
                            new Add_entrada(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                            this.dispose();
                            e.exportA4Entrada(latnum, tem.Date(),tem.Hours2(),jComboBox_armazem.getSelectedItem().toString(),jTextField_pagamento.getText());
                            JOptionPane.showMessageDialog(null, "Os Dados foram gravados no directório selecionado", "Informação", JOptionPane.INFORMATION_MESSAGE);
                            
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Ocorreu um erro " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Add_saida.class.getName()).log(Level.SEVERE, null, ex);
            }
            liga.deconecta();
            
            
                  
        
    }//GEN-LAST:event_jPanel_exportMouseClicked

    private void jButton_guardar_entradaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardar_entradaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_guardar_entradaMouseEntered

    private void jButton_guardar_entradaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardar_entradaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_guardar_entradaMouseExited

    private void jButton_guardar_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guardar_entradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_guardar_entradaActionPerformed

    private void jButton_fechar_entradaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_fechar_entradaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_fechar_entradaMouseEntered

    private void jButton_fechar_entradaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_fechar_entradaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_fechar_entradaMouseExited

    private void jButton_fechar_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_fechar_entradaActionPerformed

    }//GEN-LAST:event_jButton_fechar_entradaActionPerformed

    private void jButton_call_buscar_form_entradaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_call_buscar_form_entradaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_call_buscar_form_entradaMouseEntered

    private void jButton_call_buscar_form_entradaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_call_buscar_form_entradaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_call_buscar_form_entradaMouseExited

    private void jButton_call_buscar_form_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_call_buscar_form_entradaActionPerformed
        if (id_turno != -1 && numero != 0 && this.estado) {
            this.jInternalFrame_buscar_produto.setVisible(true);
            this.jInternalFrame_entrada.setVisible(false);
            this.jInternalFrame_lista_de_produto.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Não se pode realizar entrada em stock com o turno fechado.", "Aviso", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jButton_call_buscar_form_entradaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String pesquisa = this.jTextField_pesquisa.getText();
        if (!pesquisa.isEmpty()) {
            pesquisa(pesquisa);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField_custoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_custoMouseExited
        if (!jTextField_custo.getText().isEmpty() && !jTextField_preco.getText().isEmpty()) {
            calcularIVA_lucro(this.jTextField_preco.getText(), this.jTextField_custo.getText());
        }

    }//GEN-LAST:event_jTextField_custoMouseExited

    private void jRadioButton_nao_pagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_nao_pagarMouseClicked
        jPanel_fornecedores.setVisible(false);
        jPanel_origem.setVisible(false);
        jTextField_fornecedor_id.setText("0");
        jTextField_fornecedor_nif.setText("indiferenciado");
        jTextField_fornecedor_nome.setText("Fornecedor indiferenciado.");
        jTextField_pagamento.setText("0,00");

        double total = Double.parseDouble(Convert(jTextField_total.getText()));
        double pagamento = 0.0;
        double falta = total - pagamento;
        this.jTextField_falta.setText(exte.Chang(falta));
    }//GEN-LAST:event_jRadioButton_nao_pagarMouseClicked

    private void jRadioButton_pagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_pagarMouseClicked
        jPanel_fornecedores.setVisible(true);
        jPanel_origem.setVisible(true);
        JOptionPane.showMessageDialog(null, "Para efectuar a compra a fornecedor deves selecionar um fornecedor \ne a origem do montante nas tabelas que se seguem.  ", "Pagamento da entrada em stock", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jRadioButton_pagarMouseClicked

    private void jRadioButton_caixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_caixaMouseClicked
        PreencherTableOrigem();
    }//GEN-LAST:event_jRadioButton_caixaMouseClicked

    private void jRadioButton_bancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_bancoMouseClicked
        PreencherTableOrigem();
    }//GEN-LAST:event_jRadioButton_bancoMouseClicked

    private void jTableFornecedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableFornecedoresMouseClicked
        int line = jTableFornecedores.getSelectedRow();

        if (line != -1 && jRadioButton_pagar.isSelected()) {
            this.jTextField_fornecedor_id.setText(jTableFornecedores.getValueAt(line, 0).toString());
            this.jTextField_fornecedor_nome.setText(jTableFornecedores.getValueAt(line, 1).toString());
            this.jTextField_fornecedor_nif.setText(jTableFornecedores.getValueAt(line, 2).toString());
        }

    }//GEN-LAST:event_jTableFornecedoresMouseClicked

    private void jTableOrigemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableOrigemMouseClicked

        double pagamento = 0.0;
        double saldo = 0.0;
        int line = jTableOrigem.getSelectedRow();
        double total = Double.parseDouble(Convert(jTextField_total.getText()));

        if (line != -1 && jRadioButton_pagar.isSelected()) {
            saldo = Double.parseDouble(Convert(jTableOrigem.getValueAt(line, 2).toString()));
            if (saldo >= total) {
//                linha = line;
//                coluna = 2;
                pagamento = total;
                saldo = saldo - total;
                double falta = total - pagamento;
                this.jTextField_falta.setText(exte.Chang(falta));
                this.jTextField_pagamento.setText(exte.Chang(pagamento));
                //jTableOrigem.setValueAt(exte.Chang(saldo), line, 2);
                origem = Integer.parseInt(Convert(jTableOrigem.getValueAt(line, 0).toString()));
            } else {

                if (jRadioButton_banco.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Ímpossivel realizar o pagamento com esta conta bancária: " + jTableOrigem.getValueAt(line, 1).toString() + "\n"
                            + "Saldo disponivel: " + (jTableOrigem.getValueAt(line, 2).toString()) + "kz\n"
                            + "Total: " + jTextField_total.getText() + "kz ", "Pagamento da entrada em stock", JOptionPane.INFORMATION_MESSAGE);
                    origem = -1;
                    this.jTextField_falta.setText(exte.Chang(total));
                    this.jTextField_pagamento.setText(exte.Chang(0));
                } else {
                    JOptionPane.showMessageDialog(null, "Ímpossivel realizar o pagamento com esta caixa: " + jTableOrigem.getValueAt(line, 1).toString() + "\n"
                            + "Saldo disponivel: " + (jTableOrigem.getValueAt(line, 2).toString()) + "kz\n"
                            + "Total: " + jTextField_total.getText() + "kz ", "Pagamento da entrada em stock", JOptionPane.INFORMATION_MESSAGE);
                    this.jTextField_falta.setText(exte.Chang(total));
                    this.jTextField_pagamento.setText(exte.Chang(0));
                    origem = -1;
                }

            }
        }

    }//GEN-LAST:event_jTableOrigemMouseClicked

    private void jButton_fechar_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_fechar_entradaMouseClicked
        int result = JOptionPane.showConfirmDialog(rootPane, "Deseja realizar esta acção?", "Anular entrada em stock", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Entrada em stock anulada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new Add_entrada(id, perfil,liga.getCaminho()).setVisible(false);
            this.dispose();
        } else {

        }

    }//GEN-LAST:event_jButton_fechar_entradaMouseClicked

    private void jButton_guardar_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardar_entradaMouseClicked
        double pagamento_total = Double.parseDouble(Convert(jTextField_pagamento.getText()));
        double valor_em_falta = Double.parseDouble(Convert(jTextField_falta.getText()));
        double total = Double.parseDouble(Convert(jTextField_total.getText()));
        int result;
        String Sql = "";
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("H:m");
        String hora_registo = formatar.format(data);
        if (jRadioButton_nao_pagar.isSelected()) {
            result = JOptionPane.showConfirmDialog(rootPane, "Deseja realizar esta acção?", "Entrada em stock", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
                if (id_turno != -1 && numero != 0 && this.estado) {
                    int linhas = T_Tabela_entrada.getRowCount();
                    int id_registo = -1;
                    // Buscar o último registo. 
                    liga.conexao();
                    liga.executeSql("Select * from registo_entrada order by id_registo");
                    try {
                        if (liga.rs.last()) {
                            id_registo = liga.rs.getInt("id_registo") + 1;
                            this.registo = id_registo;
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(rootPane, "\nNão foi possível fazer o registo da entrada.", "Erro", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }

//                    liga.deconecta();
                    boolean resposta = true;

                    if (linhas > 0) {
                        resposta = CreateEntrada("Insert into registo_entrada (id_registo,data_r,hora,idturno,"
                                + "custo, pagamento, resto) "
                                + " VALUES (" + id_registo + ",CURRENT_DATE,'" + hora_registo + "'," + id_turno + ", "
                                + total + ", " + pagamento_total + "," + valor_em_falta + ")");
                    }

                    //
                    if (!resposta) {
                        for (int i = 0; i < linhas; i++) {
                            String id_produto = T_Tabela_entrada.getValueAt(i, 0).toString();
                            String qnt = T_Tabela_entrada.getValueAt(i, 3).toString();
                            String lote = T_Tabela_entrada.getValueAt(i, 5).toString();
                            String data_expiracao = T_Tabela_entrada.getValueAt(i, 6).toString();
                            if (data_expiracao.equals("-")) {
                                data_expiracao = null;
                            } else {
                                data_expiracao = "'" + data_expiracao + "'";
                            }

                            String data_fabrico = T_Tabela_entrada.getValueAt(i, 7).toString();
                            if (data_fabrico.equals("-")) {
                                data_fabrico = null;
                            } else {
                                data_fabrico = "'" + data_fabrico + "'";
                            }
                            String custo = Convert(T_Tabela_entrada.getValueAt(i, 4).toString());
                            String pagamento = "0.0";
                            int id_armazem = this.Id_armazens.get(jComboBox_armazem.getSelectedIndex());
                            CreateEntrada("Insert into entrada (entrada,data_registo, hora_registo,\n"
                                    + "  qnt, lote,data_expiracao, data_fabrico, "
                                    + "custo, pagamento, idproduto, "
                                    + "id_armazem, origem, id_origem,idturno,id_registo) "
                                    + "VALUES "
                                    + "('',CURRENT_DATE,'" + hora_registo + "',\n"
                                    + qnt + ", '" + lote + "'," + data_expiracao + "," + data_fabrico + ", "
                                    + custo + ", " + pagamento + ", " + id_produto + "," + id_armazem + ",'indiferenciado',-1," + id_turno + "," + id_registo + ")");
                        }
                        

                        jPanel_export.setEnabled(true);
                        jPanel_imprimir.setEnabled(true);
                        this.jButton_guardar_entrada.setEnabled(false);
                        this.jButton_fechar_entrada.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Entrada em stock realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível realizar a entrada em stock.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Não se pode realizar entrada em stock com o turno fechado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                jPanel_export.setEnabled(false);
                jPanel_imprimir.setEnabled(false);
                this.jButton_guardar_entrada.setEnabled(true);
                this.jButton_fechar_entrada.setEnabled(true);
            }

        } else if (jRadioButton_pagar.isSelected()) {
            result = JOptionPane.showConfirmDialog(rootPane, "Deseja realizar esta acção?", "Compra a fornecedor.", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
                if (id_turno != -1 && numero != 0 && this.estado) {
                    if (!this.jTextField_fornecedor_id.getText().isEmpty()) {
                        if (this.jTableOrigem.getSelectedRow() != -1 && origem != -1) {
                            int linhas = T_Tabela_entrada.getRowCount();
                            int id_registo = -1;
                            // Buscar o último registo. 
                            liga.conexao();
                            liga.executeSql("Select * from registo_entrada order by id_registo");
                            try {
                                if (liga.rs.last()) {
                                    id_registo = liga.rs.getInt("id_registo") + 1;
                                }

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(rootPane, "\nNão foi possível fazer o registo da entrada.", "Erro", JOptionPane.INFORMATION_MESSAGE);
                                this.dispose();
                            }

//                    liga.deconecta();
                            boolean resposta = true;

                            if (linhas > 0) {
                                resposta = CreateEntrada("Insert into registo_entrada (id_registo,data_r,hora,idturno,"
                                        + "custo, pagamento, resto) "
                                        + " VALUES (" + id_registo + ",CURRENT_DATE,'" + hora_registo + "'," + id_turno + ", "
                                        + total + ", " + pagamento_total + "," + valor_em_falta + ")");
                            }

                            //
                            if (!resposta) {
                                for (int i = 0; i < linhas; i++) {
                                    String id_produto = T_Tabela_entrada.getValueAt(i, 0).toString();
                                    String qnt = T_Tabela_entrada.getValueAt(i, 3).toString();
                                    String lote = T_Tabela_entrada.getValueAt(i, 5).toString();
                                    String data_expiracao = T_Tabela_entrada.getValueAt(i, 6).toString();
                                    if (data_expiracao.equals("-")) {
                                        data_expiracao = null;
                                    } else {
                                        data_expiracao = "'" + data_expiracao + "'";
                                    }

                                    String data_fabrico = T_Tabela_entrada.getValueAt(i, 7).toString();
                                    if (data_fabrico.equals("-")) {
                                        data_fabrico = null;
                                    } else {
                                        data_fabrico = "'" + data_fabrico + "'";
                                    }
                                    String custo = Convert(T_Tabela_entrada.getValueAt(i, 4).toString());
                                    String pagamento = "0.0";
                                    int id_armazem = this.Id_armazens.get(jComboBox_armazem.getSelectedIndex());
                                    CreateEntrada("Insert into entrada (entrada,data_registo, hora_registo,\n"
                                            + "  qnt, lote,data_expiracao, data_fabrico, "
                                            + "custo, pagamento, idproduto, "
                                            + "id_armazem, origem, id_origem,idturno,id_registo) "
                                            + "VALUES "
                                            + "('',CURRENT_DATE,'" + hora_registo + "',\n"
                                            + qnt + ", '" + lote + "'," + data_expiracao + "," + data_fabrico + ", "
                                            + custo + ", " + pagamento + ", " + id_produto + "," + id_armazem + ",'indiferenciado',-1," + id_turno + "," + id_registo + ")");
                                }
                                jPanel_export.setEnabled(true);
                                jPanel_imprimir.setEnabled(true);
                                this.jButton_guardar_entrada.setEnabled(false);
                                this.jButton_fechar_entrada.setEnabled(false);

                                if (jRadioButton_caixa.isSelected()) {
                                    Update_Caixa_saldo(pagamento_total, origem);
                                } else {
                                    Update_Banco_saldo(pagamento_total, origem);
                                }
                                JOptionPane.showMessageDialog(null, "Entrada em stock realizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Não foi possível realizar a entrada em stock.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Deves selecionar uma origem de pagamento com saldo suficiente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Deves selecionar um fornecedor ou usar o fornecedor indiferenciado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Não se pode realizar entrada em stock com o turno fechado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                jPanel_export.setEnabled(false);
                jPanel_imprimir.setEnabled(false);
                this.jButton_guardar_entrada.setEnabled(true);
                this.jButton_fechar_entrada.setEnabled(true);
            }

        }
    }//GEN-LAST:event_jButton_guardar_entradaMouseClicked

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
            java.util.logging.Logger.getLogger(Add_entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add_entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add_entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add_entrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        
        
        
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Add_entrada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.JTable T_Tabela_entrada;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton_Filtrar;
    private javax.swing.JButton jButton_call_buscar_form_entrada;
    private javax.swing.JButton jButton_fechar;
    private javax.swing.JButton jButton_fechar_entrada;
    private javax.swing.JButton jButton_guardar;
    private javax.swing.JButton jButton_guardar_entrada;
    private com.toedter.calendar.JCalendar jCalendar_data_expiracao;
    private com.toedter.calendar.JCalendar jCalendar_data_fabrico;
    private javax.swing.JCheckBox jCheckBox_expira;
    private javax.swing.JComboBox<String> jComboBox_Filtrar;
    private javax.swing.JComboBox<String> jComboBox_OrdenarServico;
    private javax.swing.JComboBox jComboBox_armazem;
    private javax.swing.JInternalFrame jInternalFrame_buscar_produto;
    private javax.swing.JInternalFrame jInternalFrame_entrada;
    private javax.swing.JInternalFrame jInternalFrame_lista_de_produto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_data_expiracao;
    private javax.swing.JLabel jLabel_data_fabrico;
    private javax.swing.JLabel jLabel_desc;
    private javax.swing.JLabel jLabel_id_produto;
    private javax.swing.JLabel jLabel_qnt_entrada;
    private javax.swing.JLabel jLabel_qnt_entrada1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelTopMenu;
    private javax.swing.JPanel jPanelTopMenu1;
    private javax.swing.JPanel jPanel_Utilizadores;
    private javax.swing.JPanel jPanel_export;
    private javax.swing.JPanel jPanel_fornecedores;
    private javax.swing.JPanel jPanel_imprimir;
    private javax.swing.JPanel jPanel_origem;
    private javax.swing.JRadioButton jRadioButton_banco;
    private javax.swing.JRadioButton jRadioButton_caixa;
    private javax.swing.JRadioButton jRadioButton_nao_pagar;
    private javax.swing.JRadioButton jRadioButton_pagar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableFornecedores;
    private javax.swing.JTable jTableOrigem;
    private javax.swing.JTable jTableProduto;
    private javax.swing.JTextField jTextField_Filtrar;
    private javax.swing.JTextField jTextField_custo;
    private javax.swing.JTextField jTextField_falta;
    private javax.swing.JTextField jTextField_fornecedor_id;
    private javax.swing.JTextField jTextField_fornecedor_nif;
    private javax.swing.JTextField jTextField_fornecedor_nome;
    private javax.swing.JTextField jTextField_lote;
    private javax.swing.JTextField jTextField_lucro1;
    private javax.swing.JTextField jTextField_margem_de_lucro;
    private javax.swing.JTextField jTextField_pagamento;
    private javax.swing.JTextField jTextField_pesquisa;
    private javax.swing.JTextField jTextField_preco;
    private javax.swing.JTextField jTextField_preco_venda;
    private javax.swing.JTextField jTextField_qnt_entrada;
    private javax.swing.JTextField jTextField_total;
    private javax.swing.JTextField jTextField_valor_iva;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;

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

    public void PreencherTableForncedores() {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "Nome", "NIF"};
        dados.add(new Object[]{"0", "Indiferenciado", "Indiferenciado"});

        liga.conexao();
        liga.executeSql("select idfornecedor ,designacao , nif from fornecedor  ");

        try {
            if(liga.rs.first())
            do {

                dados.add(new Object[]{liga.rs.getString("idfornecedor"), liga.rs.getString("designacao"), liga.rs.getString("nif")});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };

        jTableFornecedores.setModel(modelo);

//        
        jTableFornecedores.getTableHeader().setReorderingAllowed(false);
        jTableFornecedores.setAutoResizeMode(jTableProduto.AUTO_RESIZE_ALL_COLUMNS);
        jTableFornecedores.setSelectionMode(1);

        liga.deconecta();
    }

    public void PreencherTableOrigem() {
        liga.conexao();
        ModeloTabela modelo;
        if (jRadioButton_caixa.isSelected()) {
            ArrayList dados = new ArrayList();

            String[] colunas = new String[]{"ID", "Caixa", "Saldo"};

            liga.executeSql("select idcaixa ,saldo from caixa  ");

            try {
                if(liga.rs.first())
                do {
                    String saldo = "0";
                    if (liga.rs.getString("saldo") != null) {
                        saldo = exte.Chang(liga.rs.getString("saldo"));
                    } else {
                        saldo = exte.Chang("0");
                    }
                    dados.add(new Object[]{liga.rs.getString("idcaixa"), "Caixa", saldo});
                } while (liga.rs.next());
            } catch (SQLException ex) {
                jRadioButton_banco.setSelected(true);
                jRadioButton_caixa.setSelected(false);
                this.PreencherTableOrigem();
                JOptionPane.showMessageDialog(rootPane, ex + "\nSem dados de caixa.\n Realiza o pagamento com o saldo da conta bancária cadastrada.", "Dados não encntrados", JOptionPane.INFORMATION_MESSAGE);

            }

            modelo = new ModeloTabela(dados, colunas) {
            };
        } else {
            ArrayList dados = new ArrayList();

            String[] colunas = new String[]{"ID", "Nº Conta", "Saldo"};

            liga.executeSql("select idconta , numero_da_conta , saldo from conta  ");

            try {
                if(liga.rs.first())
                do {
                    String saldo = "0";
                    if (liga.rs.getString("saldo") != null) {
                        saldo = exte.Chang(liga.rs.getString("saldo"));
                    } else {
                        saldo = exte.Chang("0");
                    }
                    dados.add(new Object[]{liga.rs.getString("idconta"), liga.rs.getString("numero_da_conta"), saldo});
                } while (liga.rs.next());
            } catch (SQLException ex) {
                jRadioButton_banco.setSelected(false);
                jRadioButton_caixa.setSelected(true);
                this.PreencherTableOrigem();
                JOptionPane.showMessageDialog(rootPane, ex + "\nSem conta bancária cadastrada.\n Realiza o pagamento com o saldo da caixa ou cadatra uma conta.", "Dados não encntrados", JOptionPane.INFORMATION_MESSAGE);

            }

            modelo = new ModeloTabela(dados, colunas) {
            };
        }
        jTableOrigem.setModel(modelo);

//        
        jTableOrigem.getTableHeader().setReorderingAllowed(false);
        jTableOrigem.setAutoResizeMode(jTableProduto.AUTO_RESIZE_ALL_COLUMNS);
        jTableOrigem.setSelectionMode(1);

        liga.deconecta();
    }

    public void PreencherTableProduto(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "cód. de barra", "Descricao"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if(liga.rs.first())
            do {

                dados.add(new Object[]{liga.rs.getString(1), liga.rs.getString("codigo_de_barra"), liga.rs.getString("descricao")});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };

        jTableProduto.setModel(modelo);

//        
        jTableProduto.getTableHeader()
                .setReorderingAllowed(false);
        jTableProduto.setAutoResizeMode(jTableProduto.AUTO_RESIZE_ALL_COLUMNS);

        liga.deconecta();
    }

    public void PreencherTableProduto_entrada() {

        String data_fabrico, data_expiracao;

        if (!jCheckBox_expira.isSelected()) {
            data_fabrico = data_expiracao = "-";
        } else {

            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data_fabrico = formatar.format(jCalendar_data_fabrico.getDate());
            data_expiracao = formatar.format(jCalendar_data_expiracao.getDate());
        }

        Double total_local = Double.parseDouble(Convert(jTextField_custo.getText())) * Double.parseDouble(jTextField_qnt_entrada.getText());
        this.dados_entrada.add(new Object[]{this.jLabel_id_produto.getText(), this.jLabel_desc.getText(),
            exte.Chang(Convert(jTextField_custo.getText())),
            jTextField_qnt_entrada.getText(),
            exte.Chang(total_local.toString()), jTextField_lote.getText(), data_fabrico, data_expiracao});

        ModeloTabela modelo = new ModeloTabela(dados_entrada, colunas_entrada) {
        };

        T_Tabela_entrada.setModel(modelo);

//        
        T_Tabela_entrada.getTableHeader()
                .setReorderingAllowed(false);
        T_Tabela_entrada.setAutoResizeMode(T_Tabela_entrada.AUTO_RESIZE_ALL_COLUMNS);
        T_Tabela_entrada.setSelectionMode(1);

        double total = Double.parseDouble(Convert(jTextField_total.getText()));
        total = total + total_local;
        jTextField_total.setText(exte.Chang(total));
        double pagamento = Double.parseDouble(Convert(jTextField_pagamento.getText()));
        double falta = total - pagamento;
        this.jTextField_falta.setText(exte.Chang(falta));
    }

    private void pesquisa(String pesquisa) {

        liga.conexao();

        try {
            if (liga.rs.first()) {

                liga.executeSql("select * from produto WHERE descricao ilike '" + pesquisa + "' or codigo_de_barra =  '" + pesquisa + "'");

                if (liga.rs.first()) {
                    jLabel_id_produto.setText(liga.rs.getString("idproduto"));
                    jLabel_desc.setText(liga.rs.getString("descricao"));
                    boolean expira = liga.rs.getBoolean("expira");
                    jCheckBox_expira.setSelected(expira);
                    if (!expira) {
                        this.jTextField_lote.setText("Sem lote");
                        this.jTextField_lote.setEditable(false);
                        this.jCalendar_data_fabrico.setEnabled(false);
                        this.jCalendar_data_expiracao.setEnabled(false);

                    } else {
                        this.jTextField_lote.setEditable(true);
                        this.jTextField_lote.setText("");
                        this.jCalendar_data_fabrico.setEnabled(true);
                        this.jCalendar_data_expiracao.setEnabled(true);
                    }

                    if (liga.rs.getString("custo") != null) {
                        this.jTextField_custo.setText(exte.Chang(liga.rs.getString("custo")));
                    } else {
                        this.jTextField_custo.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("preco_unitario") != null) {
                        this.jTextField_preco.setText(exte.Chang(liga.rs.getString("preco_unitario")));
                    } else {
                        this.jTextField_preco.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("preco") != null) {
                        this.jTextField_preco_venda.setText(exte.Chang(liga.rs.getString("preco")));
                    } else {
                        this.jTextField_preco_venda.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("valor_iva") != null) {
                        this.jTextField_valor_iva.setText(exte.Chang(liga.rs.getString("valor_iva")));
                    } else {
                        this.jTextField_valor_iva.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("margem_de_lucro") != null) {
                        this.jTextField_margem_de_lucro.setText(exte.Chang(liga.rs.getString("margem_de_lucro")));
                    } else {
                        this.jTextField_margem_de_lucro.setText((exte.Chang("0")));
                    }
                    if (liga.rs.getString("lucro") != null) {
                        this.jTextField_lucro1.setText(exte.Chang(liga.rs.getString("lucro")));
                    } else {
                        this.jTextField_lucro1.setText(exte.Chang("0"));
                    }
                    this.jTextField_pesquisa.setText("");

                }
            }
            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void pesquisa(int id_pesquisa) {
        int idProduto = id_pesquisa;
        //System.out.println(id_pesquisa);
        liga.conexao();

        try {
            if (liga.rs.first()) {

                liga.executeSql("select * from produto WHERE idproduto= " + idProduto);

                if (liga.rs.first()) {
                    jLabel_id_produto.setText(liga.rs.getString("idproduto"));
                    jLabel_desc.setText(liga.rs.getString("descricao"));
                    boolean expira = liga.rs.getBoolean("expira");
                    jCheckBox_expira.setSelected(expira);
                    if (!expira) {
                        this.jTextField_lote.setText("Sem lote");
                        this.jTextField_lote.setEditable(false);
                        this.jCalendar_data_fabrico.setEnabled(false);
                        this.jCalendar_data_expiracao.setEnabled(false);

                    } else {
                        this.jTextField_lote.setEditable(true);
                        this.jTextField_lote.setText("");
                        this.jCalendar_data_fabrico.setEnabled(true);
                        this.jCalendar_data_expiracao.setEnabled(true);
                    }

                    if (liga.rs.getString("custo") != null) {
                        this.jTextField_custo.setText(exte.Chang(liga.rs.getString("custo")));
                    } else {
                        this.jTextField_custo.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("preco_unitario") != null) {
                        this.jTextField_preco.setText(exte.Chang(liga.rs.getString("preco_unitario")));
                    } else {
                        this.jTextField_preco.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("preco") != null) {
                        this.jTextField_preco_venda.setText(exte.Chang(liga.rs.getString("preco")));
                    } else {
                        this.jTextField_preco_venda.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("valor_iva") != null) {
                        this.jTextField_valor_iva.setText(exte.Chang(liga.rs.getString("valor_iva")));
                    } else {
                        this.jTextField_valor_iva.setText(exte.Chang("0"));
                    }
                    if (liga.rs.getString("margem_de_lucro") != null) {
                        this.jTextField_margem_de_lucro.setText(exte.Chang(liga.rs.getString("margem_de_lucro")));
                    } else {
                        this.jTextField_margem_de_lucro.setText((exte.Chang("0")));
                    }
                    if (liga.rs.getString("lucro") != null) {
                        this.jTextField_lucro1.setText(exte.Chang(liga.rs.getString("lucro")));
                    } else {
                        this.jTextField_lucro1.setText(exte.Chang("0"));
                    }

                }
            }
            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
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

        double custo = Double.parseDouble(cust_param);
        // System.out.println(custo);
        double lucro = preco_unit - custo;
        double margem = 100;
        if (custo != 0) {
            margem = (lucro * 100) / custo;
        }
//        System.out.println(margem);
        jTextField_lucro1.setText(exte.Chang(lucro));
//        jTextField_preco_venda.setText(exte.Chang(preco));
//        jTextField_preco.setText(exte.Chang(preco_unit));
        jTextField_custo.setText(exte.Chang(custo));
//        this.jTextField_valor_iva.setText(exte.Chang(valor_iva));
        this.jTextField_margem_de_lucro.setText(exte.Chang(margem));
    }

    private boolean CreateEntrada(String Sql) {
        liga.conexao();
        try {

            PreparedStatement pst = liga.con.prepareStatement(Sql);
            boolean r = pst.execute();

            liga.deconecta();
            return r;
        } catch (SQLException ex) {
            System.out.println(ex + "\n");
        }

        return false;
    }

    private void Update_Caixa_saldo(double pagamento_total, int idcaixa) {
        liga.conexao();
        try {
//                        System.out.println("UPDATE caixa set saida = saida + " + pagamento_total + ", saldo = saldo - " + pagamento_total + " where idcaixa= '" + idcaixa+"'");
            PreparedStatement pst = liga.con.prepareStatement("UPDATE caixa set saida = saida + " + pagamento_total + ", saldo = saldo - " + pagamento_total + " where idcaixa= '" + idcaixa+"'");
            pst.execute();
           
//            pst = liga.con.prepareStatement("UPDATE turno set saldo_atual = saldo_atual - " + 0 + " WHERE idturno = " + this.id_turno);
//            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o saldo da caixa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        liga.deconecta();
    }

    private void Update_Banco_saldo(double pagamento_total, int idconta) {
         liga.conexao();
        try {
            PreparedStatement pst = liga.con.prepareStatement("UPDATE conta set saldo = saldo - " + pagamento_total + " where idconta= " + idconta);
            pst.execute();
//            System.out.println("UPDATE conta set saldo = saldo - " + pagamento_total + " where idconta= " + idconta);
            
//            pst = liga.con.prepareStatement("UPDATE turno set saldo_atual = saldo_atual - " + 0 + " WHERE idturno = " + this.id_turno);
//            pst.execute();
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Não foi possível atualizar o saldo da conta bancária.", "Erro", JOptionPane.ERROR_MESSAGE);
                       
                    
        }
        liga.deconecta();
    }

}

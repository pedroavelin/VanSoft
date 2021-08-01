/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package Vista;

import Controle.ControloBD;
import Controle.Extenso;
import Controle.Tempo;
import java.awt.Color;
import Controle.TextPrompt;
import Controle.Validação;
import Modelo.ModeloEmpresa;
import Modelo.ModeloNota;
import Modelo.ModeloPDF;
import Modelo.ModeloTabela;
import Modelo.ModeloTarefa;
import Modelo.ModeloUtilizador;
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
public class Inventario extends javax.swing.JFrame {

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
    String[] colunas_entrada = new String[]{"ID", "Descricao", "´Stock", "Físico", "Diferença", "Info"};
    Validação validacao = new Validação();
    private int linha;
    private int coluna;
    private boolean estado;
    private int origem = -1;

    /**
     * Creates new form AddUtilizador
     */
    public Inventario() {
        initComponents();

//        jLabel_id_produto.setVisible(false);
        TextPrompt prompt1 = new TextPrompt("Pesquisar", jTextField_Filtrar);
//        TextPrompt prompt = new TextPrompt("Pesquisar", jTextField_Filtrar);
        TextPrompt promp2 = new TextPrompt("Descrição ou o Cod. de barra do produto", jTextField_pesquisa);
        TextPrompt prompt3 = new TextPrompt("Quantidade", jTextField_qnt_entrada);
        model_List = new <String> DefaultListModel();

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

        this.select = "select * from produto";
        PreencherTableProduto("select produto.*,entrada.* from  produto order by descricao");

    }

    public Inventario(int id, String perfil, String caminho) {
        initComponents();
        liga.setCaminho(caminho);
//        jLabel_id_produto.setVisible(false);
        this.id = id;
        this.perfil = perfil;

        TextPrompt prompt1 = new TextPrompt("Pesquisar", jTextField_Filtrar);
        TextPrompt promp2 = new TextPrompt("Descrição ou o Cod. de barra do produto", jTextField_pesquisa);
        TextPrompt prompt3 = new TextPrompt("Quantidade", jTextField_qnt_entrada);
        model_List = new <String> DefaultListModel();

        this.jInternalFrame_buscar_produto.setVisible(false);
//        this.jInternalFrame_entrada.setVisible(true);
        this.jInternalFrame_lista_de_produto.setVisible(true);
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

//        jPanel_fornecedores.setVisible(false);
//        jPanel_origem.setVisible(false);
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

        this.select = "select * from produto";
        PreencherTableProduto("select * from produto");
        jLabel_idproduto.setVisible(false);
        jLabel_stock.setVisible(false);

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
        jButton_fechar = new javax.swing.JButton();
        jButton_guardar = new javax.swing.JButton();
        jLabel_desc = new javax.swing.JLabel();
        jLabel_idproduto = new javax.swing.JLabel();
        jLabel_stock = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jTextField_pesquisa = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jInternalFrame_lista_de_produto = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProduto = new javax.swing.JTable();
        jPanelTopMenu2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox_armazem = new javax.swing.JComboBox();
        jButton_guardar_entrada = new javax.swing.JButton();
        jButton_fechar_entrada = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jComboBox_Filtrar = new javax.swing.JComboBox<String>();
        jTextField_Filtrar = new javax.swing.JTextField();
        jButton_Filtrar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jComboBox_OrdenarServico = new javax.swing.JComboBox<String>();
        jScrollPane3 = new javax.swing.JScrollPane();
        T_Tabela_entrada = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

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

        jLabel_desc.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_desc.setText("jLabel1");

        jLabel_idproduto.setText("jLabel1");

        jLabel_stock.setText("jLabel1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel_qnt_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_qnt_entrada))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                                .addComponent(jButton_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(331, 331, 331))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel_idproduto)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel_stock))
                            .addComponent(jLabel_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_qnt_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_qnt_entrada))
                .addGap(56, 56, 56)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_idproduto)
                    .addComponent(jLabel_stock))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jInternalFrame_buscar_produtoLayout = new javax.swing.GroupLayout(jInternalFrame_buscar_produto.getContentPane());
        jInternalFrame_buscar_produto.getContentPane().setLayout(jInternalFrame_buscar_produtoLayout);
        jInternalFrame_buscar_produtoLayout.setHorizontalGroup(
            jInternalFrame_buscar_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopMenu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame_buscar_produtoLayout.createSequentialGroup()
                .addContainerGap(1028, Short.MAX_VALUE)
                .addComponent(jTextField_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(jInternalFrame_buscar_produtoLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(800, Short.MAX_VALUE))
        );

        jInternalFrame_lista_de_produto.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame_lista_de_produto.setVisible(true);

        jTableProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Cod. de Barra", "Descrição", "QNT"
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

        jPanelTopMenu2.setBackground(new java.awt.Color(27, 167, 125));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Inventário");

        javax.swing.GroupLayout jPanelTopMenu2Layout = new javax.swing.GroupLayout(jPanelTopMenu2);
        jPanelTopMenu2.setLayout(jPanelTopMenu2Layout);
        jPanelTopMenu2Layout.setHorizontalGroup(
            jPanelTopMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelTopMenu2Layout.setVerticalGroup(
            jPanelTopMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopMenu2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel6.setText("Armazem");

        jComboBox_armazem.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jComboBox_armazem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_armazem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_armazemMouseClicked(evt);
            }
        });

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

        T_Tabela_entrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descrição", "Stock", "Físico", "Diferença", "Info."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
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

        jPanel1.setBackground(new java.awt.Color(51, 255, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.setText("Concluir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jInternalFrame_lista_de_produtoLayout = new javax.swing.GroupLayout(jInternalFrame_lista_de_produto.getContentPane());
        jInternalFrame_lista_de_produto.getContentPane().setLayout(jInternalFrame_lista_de_produtoLayout);
        jInternalFrame_lista_de_produtoLayout.setHorizontalGroup(
            jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopMenu2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1081, Short.MAX_VALUE))
                    .addGroup(jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                        .addGroup(jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_armazem, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                                .addComponent(jButton_guardar_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jButton_fechar_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jInternalFrame_lista_de_produtoLayout.setVerticalGroup(
            jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame_lista_de_produtoLayout.createSequentialGroup()
                .addComponent(jPanelTopMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_guardar_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_fechar_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox_armazem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame_lista_de_produtoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame_buscar_produto)
            .addComponent(jInternalFrame_lista_de_produto)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame_lista_de_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jInternalFrame_buscar_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_guardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardarMouseEntered
        this.jButton_guardar.setBackground(new Color(235, 235, 235));
        this.jButton_guardar.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButton_guardarMouseEntered

    private void jButton_guardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardarMouseExited
        this.jButton_guardar.setBackground(new Color(56, 65, 84));
        this.jButton_guardar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton_guardarMouseExited

    private void jButton_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guardarActionPerformed

        boolean v = true;

        if (!jTextField_qnt_entrada.getText().isEmpty()) {
            String qnt = jTextField_qnt_entrada.getText();
            String stock = jLabel_stock.getText();

            v = !Double.isNaN(Double.parseDouble(qnt));
            if (v) {
                double stockv = Double.parseDouble(Convert(stock));
                double qntv = Double.parseDouble(Convert(qnt));
                double dif = stockv - qntv;
                String inf;
                if (dif < 0) {
                    inf = "Excesso";
                } else if (dif == 0) {
                    inf = "OK";
                } else {
                    inf = "Ruptura";
                }

                this.jInternalFrame_buscar_produto.setVisible(false);
                this.jInternalFrame_lista_de_produto.setVisible(true);
                PreencherTableProduto(jLabel_idproduto.getText(), jLabel_desc.getText(), stock, qnt, dif, inf);
//                jLabel_desc.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "A quantidade deve ser um valor numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
                jTextField_qnt_entrada.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Insira uma quantidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
            jTextField_qnt_entrada.requestFocus();
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
//        this.jInternalFrame_entrada.setVisible(true);
        this.jInternalFrame_lista_de_produto.setVisible(true);

    }//GEN-LAST:event_jButton_fecharActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
//        System.out.println(this.jComboBox_OrdenarServico.getSelectedItem().toString());
//        System.out.println(this.select + " order by " + this.jComboBox_OrdenarServico.getSelectedItem().toString());
        PreencherTableProduto(this.select + " order by " + this.jComboBox_OrdenarServico.getSelectedItem().toString());
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox_OrdenarServicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarServicoMouseClicked

    }//GEN-LAST:event_jComboBox_OrdenarServicoMouseClicked

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
//        this.jInternalFrame_entrada.setVisible(false);
        this.jInternalFrame_lista_de_produto.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutoMouseClicked
        int linha = jTableProduto.getSelectedRow();
        if (linha != -1) {
            jLabel_desc.setText(this.jTableProduto.getValueAt(linha, 2).toString());
            jLabel_stock.setText(this.jTableProduto.getValueAt(linha, 3).toString());
            id_pesquisa = Integer.parseInt(this.jTableProduto.getValueAt(linha, 0).toString());
            jLabel_idproduto.setText(Integer.toString(id_pesquisa));
//            PreencherTableProdutolote(id_pesquisa);

            this.jInternalFrame_buscar_produto.setVisible(true);
//        this.jInternalFrame_entrada.setVisible(false);
            this.jInternalFrame_lista_de_produto.setVisible(false);

        }
    }//GEN-LAST:event_jTableProdutoMouseClicked

    private void T_Tabela_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_T_Tabela_entradaMouseClicked
        
        int line = T_Tabela_entrada.getSelectedRow();

        if (line >= 0) {
            int result = JOptionPane.showConfirmDialog(rootPane, "Deseja remover a linha selecionada?", "Inventário.", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_NO_OPTION) {
                dados_entrada.remove(line);
                ModeloTabela modelo = new ModeloTabela(dados_entrada, colunas_entrada) {
                };

                T_Tabela_entrada.setModel(modelo);

//        
                T_Tabela_entrada.getTableHeader()
                        .setReorderingAllowed(false);
                T_Tabela_entrada.setAutoResizeMode(T_Tabela_entrada.AUTO_RESIZE_ALL_COLUMNS);
            }
        }
    }//GEN-LAST:event_T_Tabela_entradaMouseClicked

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String pesquisa = this.jTextField_pesquisa.getText();
        if (!pesquisa.isEmpty()) {
//            pesquisa(pesquisa);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton_fechar_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_fechar_entradaMouseClicked
        int result = JOptionPane.showConfirmDialog(rootPane, "Deseja realizar esta acção?", "Anular entrada em stock", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Entrada em stock anulada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            new Inventario(id, perfil,liga.getCaminho()).setVisible(false);
            this.dispose();
        } else {

        }

    }//GEN-LAST:event_jButton_fechar_entradaMouseClicked

    private void jButton_guardar_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guardar_entradaMouseClicked
        int result = JOptionPane.showConfirmDialog(rootPane, "Deseja concluir este invetário", "Inventário.", JOptionPane.YES_NO_OPTION);
        int qnt = 0;
        if (result == JOptionPane.YES_NO_OPTION) {
            int lines = T_Tabela_entrada.getRowCount();
            for (int i = 0; i < lines; i++) {
                int latnum;
                latnum = SelectLast("select numero from inventario ");
                if (insert(i, latnum)) {
                    qnt++;
                }
            }
            if (qnt == 1) {
                JOptionPane.showConfirmDialog(rootPane, "Uma linha não foi inserida.\nVerifica o seu Inventário.", "Inventário.", JOptionPane.WARNING_MESSAGE);
            } else if (qnt >= 2) {
                JOptionPane.showConfirmDialog(rootPane, qnt + " linhas não foram inseridas.\nVerifica o seu Inventário.", "Inventário.", JOptionPane.WARNING_MESSAGE);
            }

            new Inventario(id, perfil,liga.getCaminho()).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showConfirmDialog(rootPane, "Inventário não foi concluído.", "Inventário.", JOptionPane.INFORMATION_MESSAGE);
        }


    }//GEN-LAST:event_jButton_guardar_entradaMouseClicked

    private void jComboBox_armazemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_armazemMouseClicked
        
        PreencherTableProduto("select * from produto");
        this.dados_entrada.clear();
        
        ModeloTabela modelo = new ModeloTabela(this.dados_entrada, this.colunas_entrada) {};

        T_Tabela_entrada.setModel(modelo);        
        T_Tabela_entrada.getTableHeader().setReorderingAllowed(false);
        T_Tabela_entrada.setAutoResizeMode(T_Tabela_entrada.AUTO_RESIZE_ALL_COLUMNS);
    }//GEN-LAST:event_jComboBox_armazemMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int result = JOptionPane.showConfirmDialog(rootPane, "Deseja concluir este invetário", "Inventário.", JOptionPane.YES_NO_OPTION);
        int qnt = 0;
        if (result == JOptionPane.YES_NO_OPTION) {
            int latnum;
            latnum = SelectLast("select numero from inventario ");
            int lines = T_Tabela_entrada.getRowCount();
            for (int i = 0; i < lines; i++) {                
                if (insert(i, latnum)) {
                    qnt++;
                }
            }
            if (qnt == 1) {
                JOptionPane.showConfirmDialog(rootPane, "Uma linha não foi inserida.\nVerifica o seu Inventário.", "Inventário.", JOptionPane.WARNING_MESSAGE);
            } else if (qnt >= 2) {
                JOptionPane.showConfirmDialog(rootPane, qnt + " linhas não foram inseridas.\nVerifica o seu Inventário.", "Inventário.", JOptionPane.WARNING_MESSAGE);
            }
            
            
            // inicio
            
            
            
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
                           
                            ModeloPDF e = new ModeloPDF(new File(file), T_Tabela_entrada, "Entrada "+latnum+1,liga.getCaminho());
                           
                            e.setME(ME);
//                                e.setMC(MC);
                            e.setMU(MU);
//                                e.setMF(MF);
//                            e.setMC(new ModeloCliente(Integer.parseInt(this.jTextField_fornecedor_id.getText())));
//                            e.getMC().setDesignacao(jTextField_fornecedor_nome.getText());
//                            e.getMC().setNif(jTextField_fornecedor_nif.getText());
                            
                            new Add_entrada(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                            this.dispose();
                            e.exportA4Inventario(latnum, tem.Date(),tem.Hours2(),jComboBox_armazem.getSelectedItem().toString());
                            JOptionPane.showMessageDialog(null, "Os Dados foram gravados no directório selecionado", "Informação", JOptionPane.INFORMATION_MESSAGE);
                            
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null,e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Add_saida.class.getName()).log(Level.SEVERE, null, ex);
            }
            liga.deconecta();
            
            
            
            // fim
            
            
            
            
            

            new Inventario(id, perfil,liga.getCaminho()).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showConfirmDialog(rootPane, "Inventário não foi concluído.", "Inventário.", JOptionPane.INFORMATION_MESSAGE);
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
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.JTable T_Tabela_entrada;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton_Filtrar;
    private javax.swing.JButton jButton_fechar;
    private javax.swing.JButton jButton_fechar_entrada;
    private javax.swing.JButton jButton_guardar;
    private javax.swing.JButton jButton_guardar_entrada;
    private javax.swing.JComboBox<String> jComboBox_Filtrar;
    private javax.swing.JComboBox<String> jComboBox_OrdenarServico;
    private javax.swing.JComboBox jComboBox_armazem;
    private javax.swing.JInternalFrame jInternalFrame_buscar_produto;
    private javax.swing.JInternalFrame jInternalFrame_lista_de_produto;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel_desc;
    private javax.swing.JLabel jLabel_idproduto;
    private javax.swing.JLabel jLabel_qnt_entrada;
    private javax.swing.JLabel jLabel_stock;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelTopMenu1;
    private javax.swing.JPanel jPanelTopMenu2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableProduto;
    private javax.swing.JTextField jTextField_Filtrar;
    private javax.swing.JTextField jTextField_pesquisa;
    private javax.swing.JTextField jTextField_qnt_entrada;
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

//    public void PreencherTableForncedores() {
//        ArrayList dados = new ArrayList();
//        String[] colunas = new String[]{"ID", "Nome", "NIF"};
//        dados.add(new Object[]{"0", "Indiferenciado", "Indiferenciado"});
//
//        liga.conexao();
//        liga.executeSql("select idfornecedor ,designacao , nif from fornecedor  ");
//
//        try {
//            liga.rs.first();
//            do {
//
//                dados.add(new Object[]{liga.rs.getString("idfornecedor"), liga.rs.getString("designacao"), liga.rs.getString("nif")});
//            } while (liga.rs.next());
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
//        }
//
//        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
//        };
//
//        jTableFornecedores.setModel(modelo);
//
////        
//        jTableFornecedores.getTableHeader().setReorderingAllowed(false);
//        jTableFornecedores.setAutoResizeMode(jTableProduto.AUTO_RESIZE_ALL_COLUMNS);
//        jTableFornecedores.setSelectionMode(1);
//
//        liga.deconecta();
//    }
//    public void PreencherTableOrigem() {
//        liga.conexao();
//        ModeloTabela modelo;
//        if (jRadioButton_caixa.isSelected()) {
//            ArrayList dados = new ArrayList();
//
//            String[] colunas = new String[]{"ID", "Caixa", "Saldo"};
//
//            liga.executeSql("select idcaixa ,saldo from caixa  ");
//
//            try {
//                liga.rs.first();
//                do {
//                    String saldo = "0";
//                    if (liga.rs.getString("saldo") != null) {
//                        saldo = exte.Chang(liga.rs.getString("saldo"));
//                    } else {
//                        saldo = exte.Chang("0");
//                    }
//                    dados.add(new Object[]{liga.rs.getString("idcaixa"), "Caixa", saldo});
//                } while (liga.rs.next());
//            } catch (SQLException ex) {
//                jRadioButton_banco.setSelected(true);
//                jRadioButton_caixa.setSelected(false);
//                this.PreencherTableOrigem();
//                JOptionPane.showMessageDialog(rootPane, ex + "\nSem dados de caixa.\n Realiza o pagamento com o saldo da conta bancária cadastrada.", "Dados não encntrados", JOptionPane.INFORMATION_MESSAGE);
//
//            }
//
//            modelo = new ModeloTabela(dados, colunas) {
//            };
//        } else {
//            ArrayList dados = new ArrayList();
//
//            String[] colunas = new String[]{"ID", "Nº Conta", "Saldo"};
//
//            liga.executeSql("select idconta , numero_da_conta , saldo from conta  ");
//
//            try {
//                liga.rs.first();
//                do {
//                    String saldo = "0";
//                    if (liga.rs.getString("saldo") != null) {
//                        saldo = exte.Chang(liga.rs.getString("saldo"));
//                    } else {
//                        saldo = exte.Chang("0");
//                    }
//                    dados.add(new Object[]{liga.rs.getString("idconta"), liga.rs.getString("numero_da_conta"), saldo});
//                } while (liga.rs.next());
//            } catch (SQLException ex) {
//                jRadioButton_banco.setSelected(false);
//                jRadioButton_caixa.setSelected(true);
//                this.PreencherTableOrigem();
//                JOptionPane.showMessageDialog(rootPane, ex + "\nSem conta bancária cadastrada.\n Realiza o pagamento com o saldo da caixa ou cadatra uma conta.", "Dados não encntrados", JOptionPane.INFORMATION_MESSAGE);
//
//            }
//
//            modelo = new ModeloTabela(dados, colunas) {
//            };
//        }
//        jTableOrigem.setModel(modelo);
//
////        
//        jTableOrigem.getTableHeader().setReorderingAllowed(false);
//        jTableOrigem.setAutoResizeMode(jTableProduto.AUTO_RESIZE_ALL_COLUMNS);
//        jTableOrigem.setSelectionMode(1);
//
//        liga.deconecta();
//    }
    public void PreencherTableProduto(String id, String produto, String qnt, String fisico, double dif, String inf) {
//        System.out.println(dif);
        dados_entrada.add(new Object[]{id, produto, qnt, exte.Chang(fisico), exte.Chang(dif), inf});

        ModeloTabela modelo = new ModeloTabela(dados_entrada, colunas_entrada) {
        };

        T_Tabela_entrada.setModel(modelo);

//        
        T_Tabela_entrada.getTableHeader()
                .setReorderingAllowed(false);
        T_Tabela_entrada.setAutoResizeMode(T_Tabela_entrada.AUTO_RESIZE_ALL_COLUMNS);

    }

    public void PreencherTableProduto(String Sql) {
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "cód. de barra", "Descricao", "Qnt"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            liga.rs.first();
            do {
                String qnt = Double.toString(TakeQNT(liga.rs.getInt(1)));
                dados.add(new Object[]{liga.rs.getString(1), liga.rs.getString("codigo_de_barra"), liga.rs.getString("descricao"), exte.Chang(qnt)});
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

    public double TakeQNT(int idproduto) {
        ControloBD cne = new ControloBD();
        double saida = 0, entrada = 0, qnt = 0;
        cne.conexao();
        cne.executeSql("select SUM (qnt) from entrada where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()));

        try {
            cne.rs.first();
            do {
                entrada = cne.rs.getDouble(1);

            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        System.out.println(entrada);

        cne.executeSql("select SUM (qnt) from saida where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()));

        try {
            cne.rs.first();
            do {
                saida = cne.rs.getDouble(1);

            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println(saida);

        return entrada - saida;

    }

//    public void PreencherTableProdutolote(int idproduto) {
//        int line = jTableProduto.getSelectedRow();
//        if (line > -1) {
//            String desc = jTableProduto.getValueAt(line, 2).toString();
//            jLabel_desc.setText(desc);
//            ArrayList dados = new ArrayList();
//            String[] colunas = new String[]{"lote", "Qnt"};
//            liga.conexao();
//            liga.executeSql("select lote, SUM (qnt) from entrada where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()) + " group by lote");
//
//            try {
//                liga.rs.first();
//                do {
//                    Double val = TakeQNT(idproduto, liga.rs.getDouble(2), liga.rs.getString(1));
//                    if (val > 0) {
//                        String qnt = Double.toString(val);
//                        dados.add(new Object[]{liga.rs.getString(1), exte.Chang(qnt)});
//                    }
//                } while (liga.rs.next());
//                this.jInternalFrame_buscar_produto.setVisible(true);
////            this.jInternalFrame_entrada.setVisible(false);
//                this.jInternalFrame_lista_de_produto.setVisible(false);
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(rootPane, "Não é possível realizar baixa desse produto.", "Sem stock", JOptionPane.INFORMATION_MESSAGE);
//                this.jInternalFrame_buscar_produto.setVisible(false);
////            this.jInternalFrame_entrada.setVisible(false);
//                this.jInternalFrame_lista_de_produto.setVisible(true);
//            }
//
//            ModeloTabela modelo = new ModeloTabela(dados, colunas) {
//            };
//
//            jTableProdutoLote.setModel(modelo);
//            jTableProdutoLote.getTableHeader().setReorderingAllowed(false);
//            jTableProdutoLote.setAutoResizeMode(jTableProduto.AUTO_RESIZE_ALL_COLUMNS);
//
//            liga.deconecta();
//        }
//    }
    private double TakeQNT(int idproduto, double entrada, String lote) {

        ControloBD cne = new ControloBD();
        double saida = 0, qnt = 0;
        cne.conexao();
        cne.executeSql("select SUM (qnt) from saida where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()) + " and lote = '" + lote + "' ");

        try {
            cne.rs.first();
            do {
                saida = cne.rs.getDouble(2);

            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        return entrada - saida;
        //To change body of generated methods, choose Tools | Templates.
    }

    private boolean insert(int i, int latnum) {
        String Sql = "";
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("H:m");
        String hora_registo = formatar.format(data);
        String stock = T_Tabela_entrada.getValueAt(i, 2).toString();
        String fisico = T_Tabela_entrada.getValueAt(i, 3).toString();
        String dif = T_Tabela_entrada.getValueAt(i, 4).toString();
        String inf = T_Tabela_entrada.getValueAt(i, 5).toString();
        String idprod = T_Tabela_entrada.getValueAt(i, 0).toString();

        return CreateEntrada("INSERT INTO inventario (data_registo, hora_registo , stock, fisico,inf ,diferenca,\n"
                + "  idproduto,\n"
                + "  id_armazem,\n"
                + "  idturmo,\n"
                + "  numero) "
                + "VALUES (current_date,'" + hora_registo + "'," + Convert(stock) + "," + Convert(fisico) + ","
                + "'" + inf + "'," + Convert(dif) + "," + idprod + ",\n"
                + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()) + ",\n"
                + this.id_turno + ", " + latnum + "  )");

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

    private int SelectLast(String sql) {
        ControloBD cne = new ControloBD();
        int num = 1;
        cne.conexao();
        cne.executeSql(sql);
        try {
            cne.rs.last();
            do {
                num = cne.rs.getInt("numero");
                num++;
            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        return num;
    }

}

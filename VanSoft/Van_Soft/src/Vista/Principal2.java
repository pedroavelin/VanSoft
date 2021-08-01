/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import java.awt.Color;
import Controle.TextPrompt;
import Modelo.ModeloUtilizador;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.ImageIcon;
import Modelo.ModeloColaborador;
import Modelo.ModeloEmpresa;
import Modelo.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;

/**
 *
 * @author Gonga
 */
public class Principal2 extends javax.swing.JFrame {

    int id;
    String perfil;
    ControloBD liga = new ControloBD();
    String select, selectF;
    private String selectND;
    private String selectNC;
    private String selectLic;

    /**
     * Creates new form Principal2
     */
    public Principal2() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);

//        PreencherTabela("select * from fatura");
        this.selectF = "Select fatura.idfactura, fatura.titulo_da_fatura, cliente.designacao, data from fatura, cliente WHERE fatura.idcliente = cliente.idcliente";
        this.selectND = "Select idnota_de_debito, observacao, valor, data, designacao, idfactura from nota_de_debito, cliente WHERE nota_de_debito.idcliente = cliente.idcliente ";
        this.selectNC = "Select idnota_de_credito, valor, data, designacao, idfactura from nota_de_dedito, cliente WHERE nota_de_credito.idcliente = cliente.idcliente ";
        this.jPanel_RelatórioFactura.setVisible(true);
        this.jPanel_RelatórioNotas.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        this.PreencherTabelaF(selectF + " Order by data desc");
        this.selectLic = "SELECT * FROM public.liquidacao ORDER BY idfatura;";
//         jLabel2.setVisible(false);
    }

    Principal2(int id, String perfil, String caminho) {
        initComponents();
        liga.setCaminho(caminho);
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.id = id;
//        jLabel2.setVisible(false);
        this.perfil = perfil;
        this.select = "select * from fatura";
//        PreencherTabela("select * from fatura");
        this.selectF = "Select fatura.idfactura, fatura.titulo_da_fatura, cliente.designacao, data from fatura, cliente WHERE fatura.idcliente = cliente.idcliente";
        this.selectND = "Select nota_de_debito.*, designacao from nota_de_debito, cliente WHERE nota_de_debito.idcliente = cliente.idcliente ";
        this.selectNC = "Select nota_de_credito.*, designacao from nota_de_dedito, cliente WHERE nota_de_credito.idcliente = cliente.idcliente ";
        this.selectLic = "SELECT * FROM public.liquidacao ORDER BY idfatura;";
        this.jPanel_RelatórioFactura.setVisible(true);
        this.jPanel_RelatórioNotas.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        this.PreencherTabelaF(selectF + " Order by data desc");
        jLabel_nome_utilizador.setText(this.getNomeUser(this.id, this.perfil));

    }

    public String getNomeUser(int idutilizador, String perfil) {
        try {
            String nome_user = "";
            liga.conexao();
            liga.executeSql("select * from utilizador where idutilizador ='" + idutilizador + "' AND perfil='" + perfil + "'");
            if (liga.rs.first()) {
                return liga.rs.getString("nome_utilizador");
            }
            liga.deconecta();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Este usuário não tem registo.\nPor questões de segurança, a aplicação será imediatamente encerrada\nFaça um novo login ou contacte o suporte técnico do Aplicativo.", "Aviso", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return "Sem nome de utilizador";
    }

    public void PreencherTabelaND(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Objectivo", "Obs.", "Valor", "Data", "Cliente", "Nº Da Fatura"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString("idnota_de_debito"), liga.rs.getString("objectivo"), liga.rs.getString("observacao"), liga.rs.getString("valor"), liga.rs.getString("data"), liga.rs.getString("designacao"), liga.rs.getString("idfactura")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados.\n" + ex, "", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTableNotas.setModel(modelo);
        jTableNotas.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTableNotas.getColumnModel().getColumn(0).setResizable(true);
        jTableNotas.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableNotas.getColumnModel().getColumn(1).setResizable(true);
        jTableNotas.getColumnModel().getColumn(2).setPreferredWidth(300);
        jTableNotas.getColumnModel().getColumn(2).setResizable(true);
        jTableNotas.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTableNotas.getColumnModel().getColumn(3).setResizable(true);
        jTableNotas.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableNotas.getColumnModel().getColumn(4).setResizable(true);
        jTableNotas.getColumnModel().getColumn(5).setPreferredWidth(200);
        jTableNotas.getColumnModel().getColumn(5).setResizable(true);
        jTableNotas.getColumnModel().getColumn(6).setPreferredWidth(40);
        jTableNotas.getColumnModel().getColumn(6).setResizable(true);
        jTableNotas.getTableHeader().setReorderingAllowed(false);
        jTableNotas.setAutoResizeMode(jTableNotas.AUTO_RESIZE_ALL_COLUMNS);
        jTableNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public void PreencherTabelaNC(String Sql) {
        this.selectNC = "Select nota_de_credito.*, designacao,recibo.indicativo from nota_de_credito, cliente, recibo \n"
                + "WHERE nota_de_credito.idcliente = cliente.idcliente \n"
                + "AND recibo.idfatura_recibo = nota_de_credito.idfactura \n"
                + "AND origem = 1";

        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID","Nº", "Motivo",  "Valor", "Data", "Cliente", "Doc"};
        liga.conexao();
        liga.executeSql(selectNC + " order by nota_de_credito.data desc");

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString("idnota_de_credito")
                            ,"NC "+liga.rs.getString("indicativo")+" "+liga.rs.getString("idnota_de_credito"), 
                            liga.rs.getString("observacao"), 
                            liga.rs.getString("valor"), 
                            liga.rs.getString("data"), 
                            liga.rs.getString("designacao"), 
                            "FR "+liga.rs.getString(13)+" "+liga.rs.getString("idfactura")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados.\n" + ex, "", JOptionPane.WARNING_MESSAGE);
        }

        liga.executeSql("Select nota_de_credito.*, designacao,fatura.indicativo from nota_de_credito, cliente, fatura \n"
                + "WHERE nota_de_credito.idcliente = cliente.idcliente \n"
                + "AND fatura.idfactura = nota_de_credito.idfactura \n"
                + "AND origem = 2 order by nota_de_credito.data desc");

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString("idnota_de_credito"),
                        "NC "+liga.rs.getString("indicativo")+" "+liga.rs.getString("idnota_de_credito"), 
                        liga.rs.getString("observacao"), 
                        liga.rs.getString("valor"), 
                        liga.rs.getString("data"), 
                        liga.rs.getString("designacao"), 
                        "FT "+liga.rs.getString(13)+" "+liga.rs.getString("idfactura")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados.\n" + ex, "", JOptionPane.WARNING_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTableNotas.setModel(modelo);
        jTableNotas.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTableNotas.getColumnModel().getColumn(0).setResizable(true);
        jTableNotas.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableNotas.getColumnModel().getColumn(1).setResizable(true);
        jTableNotas.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTableNotas.getColumnModel().getColumn(2).setResizable(true);
        jTableNotas.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTableNotas.getColumnModel().getColumn(3).setResizable(true);
        jTableNotas.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableNotas.getColumnModel().getColumn(4).setResizable(true);
        jTableNotas.getColumnModel().getColumn(5).setPreferredWidth(200);
        jTableNotas.getColumnModel().getColumn(5).setResizable(true);
        jTableNotas.getColumnModel().getColumn(6).setPreferredWidth(40);
        jTableNotas.getColumnModel().getColumn(6).setResizable(true);
        jTableNotas.getTableHeader().setReorderingAllowed(false);
        jTableNotas.setAutoResizeMode(jTableNotas.AUTO_RESIZE_ALL_COLUMNS);
        jTableNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public void PreencherTabelaLiquidacao(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Objectivo", "Total", "Valor recebido", "Data e hora", "Nº Da Fatura"};

        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString("idliquidacao"), liga.rs.getString("objectivo"), YesOrNot(liga.rs.getBoolean("total")), liga.rs.getString("valor"), liga.rs.getString("data"), liga.rs.getString("idfatura")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados" + ex,
                    "Aviso", JOptionPane.WARNING_MESSAGE);

        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTableLiquidacao.setModel(modelo);
        jTableLiquidacao.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTableLiquidacao.getColumnModel().getColumn(0).setResizable(false);
        jTableLiquidacao.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTableLiquidacao.getColumnModel().getColumn(1).setResizable(false);
        jTableLiquidacao.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTableLiquidacao.getColumnModel().getColumn(2).setResizable(false);
        jTableLiquidacao.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTableLiquidacao.getColumnModel().getColumn(3).setResizable(false);
        jTableLiquidacao.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTableLiquidacao.getColumnModel().getColumn(4).setResizable(false);
        jTableLiquidacao.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTableLiquidacao.getColumnModel().getColumn(5).setResizable(false);
//        jTableLiquidacao.getColumnModel().getColumn(6).setPreferredWidth(100);
//        jTableLiquidacao.getColumnModel().getColumn(6).setResizable(true);
        jTableLiquidacao.getTableHeader().setReorderingAllowed(false);
//        jTableLiquidacao.setAutoResizeMode(jTableLiquidacao.AUTO_RESIZE_ALL_COLUMNS);
        jTableLiquidacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public void PreencherTabelaF(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Tipo de movimento", "Data", "Cliente"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString(1), liga.rs.getString("titulo_da_fatura"), liga.rs.getString("data"), liga.rs.getString("designacao")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados",
                    "Aviso", JOptionPane.WARNING_MESSAGE);

        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTableF.setModel(modelo);
        jTableF.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTableF.getColumnModel().getColumn(0).setResizable(false);
        jTableF.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableF.getColumnModel().getColumn(1).setResizable(false);
        jTableF.getColumnModel().getColumn(2).setPreferredWidth(160);
        jTableF.getColumnModel().getColumn(2).setResizable(false);
        jTableF.getColumnModel().getColumn(3).setPreferredWidth(300);
        jTableF.getColumnModel().getColumn(3).setResizable(false);

        jTableF.getTableHeader().setReorderingAllowed(false);
        jTableF.setAutoResizeMode(jTableF.AUTO_RESIZE_ALL_COLUMNS);
        jTableF.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroupCorDoSistema = new javax.swing.ButtonGroup();
        buttonGroupTipoDeLetra = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel_nome_utilizador = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Tesouraria = new javax.swing.JInternalFrame();
        jPanel_RelatórioNotas = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNotas = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jComboBox_OrdenarNota = new javax.swing.JComboBox<String>();
        jButton6 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jComboBox_FiltrarNota = new javax.swing.JComboBox<String>();
        jTextField_FiltrarNotas = new javax.swing.JTextField();
        jButton_Filtrar = new javax.swing.JButton();
        jPanel_RelatórioFactura = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableF = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jComboBox_OrdenarFactura = new javax.swing.JComboBox<String>();
        jButton7 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jComboBox_FiltrarFactura = new javax.swing.JComboBox<String>();
        jTextField_FiltrarFactura = new javax.swing.JTextField();
        jButton_Filtrar1 = new javax.swing.JButton();
        jPanel_RelatorioLiquidacao = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableLiquidacao = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jComboBox_OrdenarNota1 = new javax.swing.JComboBox<String>();
        jButton8 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jComboBox_FiltrarNota1 = new javax.swing.JComboBox<String>();
        jTextField_FiltrarNotas1 = new javax.swing.JTextField();
        jButton_Filtrar2 = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jLabel_Logotipo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 625));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(54, 70, 78));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(54, 70, 78));
        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_treatment_list_40px.png"))); // NOI18N
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Operações");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 140, -1));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 220, 40));

        jPanel5.setBackground(new java.awt.Color(54, 70, 78));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_stutdown_40px.png"))); // NOI18N
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sair");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel9.setBackground(new java.awt.Color(54, 70, 78));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Suporte");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel5.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 220, 40));

        jPanel6.setBackground(new java.awt.Color(54, 70, 78));
        jPanel6.setToolTipText("Configurações do software");
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_settings_3_40px.png"))); // NOI18N
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Configurações");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 220, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_general_ledger_40px_2.png"))); // NOI18N
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(54, 70, 78));
        jLabel10.setText("Tesouraria");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 220, 40));

        jPanel8.setBackground(new java.awt.Color(54, 70, 78));
        jPanel8.setToolTipText("Dados gerais da empresa");
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_commercial_development_management_40px.png"))); // NOI18N
        jPanel8.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Empresa");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 220, 40));

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Copyright©VanSoft");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 220, -1));

        jPanel10.setBackground(new java.awt.Color(54, 70, 78));
        jPanel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel10MouseClicked(evt);
            }
        });
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_overtime_40px.png"))); // NOI18N
        jPanel10.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Turnos");
        jPanel10.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel11.setBackground(new java.awt.Color(54, 70, 78));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel11.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Suporte");
        jPanel11.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 220, 40));

        jLabel_nome_utilizador.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel_nome_utilizador.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_nome_utilizador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_nome_utilizador.setText("Nome de utilizador");
        jPanel2.add(jLabel_nome_utilizador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 220, -1));

        jPanel29.setBackground(new java.awt.Color(54, 70, 78));
        jPanel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel29MouseClicked(evt);
            }
        });
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel29.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Suporte");
        jPanel29.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel30.setBackground(new java.awt.Color(54, 70, 78));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel30.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Suporte");
        jPanel30.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel29.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 220, 40));

        jPanel37.setBackground(new java.awt.Color(54, 70, 78));
        jPanel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel37MouseClicked(evt);
            }
        });
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_product_40px.png"))); // NOI18N
        jPanel37.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Stock");
        jPanel37.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel53.setBackground(new java.awt.Color(54, 70, 78));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel53.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Suporte");
        jPanel53.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel37.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 220, 40));

        jPanel31.setBackground(new java.awt.Color(54, 70, 78));
        jPanel31.setForeground(new java.awt.Color(255, 255, 255));
        jPanel31.setToolTipText("Dashboard");
        jPanel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel31MouseClicked(evt);
            }
        });
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Dashboard");
        jPanel31.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_graph_report_script_filled_40px.png"))); // NOI18N
        jPanel31.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 220, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 105, 220, 560));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tesouraria.setVisible(true);

        jPanel_RelatórioNotas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_RelatórioNotas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(27, 167, 125));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Notas fiscais");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_RelatórioNotas.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, -1));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jTableNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Descrição", "Tipo de movimento", "Imposto", "Data", "Entidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableNotasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableNotas);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setToolTipText("Actualizar");
        jPanel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel22.setOpaque(false);
        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel22MouseClicked(evt);
            }
        });

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N

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

        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox_OrdenarNota.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_OrdenarNota.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cliente asc", "Cliente desc", "data asc", "data desc", "Nº da nota asc", "Nº da nota desc" }));
        jComboBox_OrdenarNota.setToolTipText("");
        jComboBox_OrdenarNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_OrdenarNotaMouseClicked(evt);
            }
        });

        jButton6.setText("Ordenar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jComboBox_OrdenarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_OrdenarNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel27.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Filtrar por");

        jComboBox_FiltrarNota.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_FiltrarNota.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Data", "Cliente", "Nº da nota" }));
        jComboBox_FiltrarNota.setToolTipText("");
        jComboBox_FiltrarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_FiltrarNotaActionPerformed(evt);
            }
        });

        jTextField_FiltrarNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_FiltrarNotasActionPerformed(evt);
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
                .addComponent(jComboBox_FiltrarNota, 0, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_FiltrarNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Filtrar)
                .addGap(4, 4, 4))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jComboBox_FiltrarNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_FiltrarNotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Filtrar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        jPanel_RelatórioNotas.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 960, 290));

        jPanel_RelatórioFactura.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_RelatórioFactura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(27, 167, 125));

        jLabel23.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Fatura");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_RelatórioFactura.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, -1));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jTableF.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Descrição", "Tipo de movimento", "Imposto", "Data", "Entidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableF);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setToolTipText("Actualizar");
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.setOpaque(false);
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N

        jLabel40.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Actualizar");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setToolTipText("Ver relatórios completo");
        jPanel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel26.setOpaque(false);
        jPanel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel26MouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Listar");

        jLabel46.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_list_90px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel42))
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE)))
        );

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox_OrdenarFactura.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_OrdenarFactura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nº asc", "Nº desc", "cliente asc", "cliente desc", "data asc", "data desc", "Tipo asc", "Tipo desc" }));
        jComboBox_OrdenarFactura.setToolTipText("");
        jComboBox_OrdenarFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_OrdenarFacturaMouseClicked(evt);
            }
        });
        jComboBox_OrdenarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_OrdenarFacturaActionPerformed(evt);
            }
        });

        jButton7.setText("Ordenar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox_OrdenarFactura, 0, 156, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_OrdenarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel28.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Filtrar por");

        jComboBox_FiltrarFactura.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_FiltrarFactura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nº Fatura", "Cliente", "Data" }));
        jComboBox_FiltrarFactura.setToolTipText("");
        jComboBox_FiltrarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_FiltrarFacturaActionPerformed(evt);
            }
        });

        jTextField_FiltrarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_FiltrarFacturaActionPerformed(evt);
            }
        });

        jButton_Filtrar1.setText("Filtar");
        jButton_Filtrar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_Filtrar1MouseClicked(evt);
            }
        });
        jButton_Filtrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Filtrar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_FiltrarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_FiltrarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Filtrar1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jComboBox_FiltrarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_FiltrarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Filtrar1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jPanel_RelatórioFactura.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1010, 290));

        jPanel_RelatorioLiquidacao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_RelatorioLiquidacao.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(27, 167, 125));

        jLabel24.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Liquidação");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_RelatorioLiquidacao.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jTableLiquidacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Descrição", "Tipo de movimento", "Imposto", "Data", "Entidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableLiquidacao);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setToolTipText("Actualizar");
        jPanel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.setOpaque(false);
        jPanel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel27MouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Actualizar");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox_OrdenarNota1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_OrdenarNota1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cliente asc", "Cliente desc", "data asc", "data desc", "Nº da nota asc", "Nº da nota desc" }));
        jComboBox_OrdenarNota1.setToolTipText("");
        jComboBox_OrdenarNota1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_OrdenarNota1MouseClicked(evt);
            }
        });

        jButton8.setText("Ordenar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jComboBox_OrdenarNota1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_OrdenarNota1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel29.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Filtrar por");

        jComboBox_FiltrarNota1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_FiltrarNota1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Data", "Cliente", "Nº da nota" }));
        jComboBox_FiltrarNota1.setToolTipText("");
        jComboBox_FiltrarNota1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_FiltrarNota1ActionPerformed(evt);
            }
        });

        jTextField_FiltrarNotas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_FiltrarNotas1ActionPerformed(evt);
            }
        });

        jButton_Filtrar2.setText("Filtar");
        jButton_Filtrar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_Filtrar2MouseClicked(evt);
            }
        });
        jButton_Filtrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Filtrar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_FiltrarNota1, 0, 132, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_FiltrarNotas1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Filtrar2)
                .addGap(4, 4, 4))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jComboBox_FiltrarNota1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_FiltrarNotas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Filtrar2))
                .addContainerGap())
        );

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setToolTipText("Ver relatórios completo");
        jPanel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.setOpaque(false);
        jPanel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel28MouseClicked(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Listar");

        jLabel45.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_list_90px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel44)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 41, Short.MAX_VALUE)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        jPanel_RelatorioLiquidacao.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1150, 290));

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_report_card_90px.png"))); // NOI18N
        jMenu4.setText("Relatórios");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Faturas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);
        jMenu4.add(jSeparator2);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Proformas");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);
        jMenu4.add(jSeparator3);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Fatuas Recibo");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);
        jMenu4.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Recibos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);
        jMenu4.add(jSeparator4);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Orçamentos");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);
        jMenu4.add(jSeparator5);

        jMenu1.setText("Notas");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Crédito");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator7);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Débito");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenu4.add(jMenu1);

        jMenu6.setText("Caixa");

        jMenuItem9.setText("Saídas");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem9);

        jMenuItem8.setText("Entrdas");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

        jMenuItem7.setText("Saldo");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem7);

        jMenu4.add(jMenu6);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_receive_cash_90px.png"))); // NOI18N
        jMenu5.setText("Nota de pagamento");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_cash_in_hand_90px.png"))); // NOI18N
        jMenu3.setText("Anulação de fatura");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_payment_history_90px.png"))); // NOI18N
        jMenu2.setText("Liquidação de factura");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        Tesouraria.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout TesourariaLayout = new javax.swing.GroupLayout(Tesouraria.getContentPane());
        Tesouraria.getContentPane().setLayout(TesourariaLayout);
        TesourariaLayout.setHorizontalGroup(
            TesourariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TesourariaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_RelatórioNotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(TesourariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TesourariaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_RelatórioFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(TesourariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TesourariaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_RelatorioLiquidacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        TesourariaLayout.setVerticalGroup(
            TesourariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TesourariaLayout.createSequentialGroup()
                .addComponent(jPanel_RelatórioNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 130, Short.MAX_VALUE))
            .addGroup(TesourariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TesourariaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_RelatórioFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(119, Short.MAX_VALUE)))
            .addGroup(TesourariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TesourariaLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel_RelatorioLiquidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(120, Short.MAX_VALUE)))
        );

        jPanel3.add(Tesouraria, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 1000, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1060, 550));

        jLabel_Logotipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Logotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/LogoApp.png"))); // NOI18N
        jPanel1.add(jLabel_Logotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        new Principal(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // System.exit(0);
        new Entar().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        if (jLabel22.getText().equals("Notas de débito")) {

            PreencherTabelaND(selectND);

        }

        if (jLabel22.getText().equals("Notas de crédito")) {

            PreencherTabelaNC(selectNC);
        }
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        new Principal1(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        new Principal2(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        new Principal3(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
//        new NotaDeDebito55(this.id, this.perfil,liga.getCaminho()).setVisible(true);
//        this.dispose();
        JOptionPane.showConfirmDialog(null, "Função temporáriamente inativa.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        new NotaDeCredito(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        new LiquidacaoDeFatura(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
//        new Principal2().setVisible(true);
//        this.dispose();
//        this.jPanel_Relatório.setVisible(true);

    }//GEN-LAST:event_jMenu4MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        new Principal4(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jComboBox_OrdenarNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarNotaMouseClicked

    }//GEN-LAST:event_jComboBox_OrdenarNotaMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (jLabel22.getText().equals("Notas de crédito")) {

            String Ordem = "data desc";
            if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Nº da nota asc")) {
                Ordem = "idnota_de_credito asc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Nº da nota desc")) {
                Ordem = "idnota_de_credito desc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Cliente asc")) {
                Ordem = "idcliente asc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Cliente desc")) {
                Ordem = "idcliente desc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("data asc")) {
                Ordem = "data asc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("data desc")) {
                Ordem = "data desc";
            }

            PreencherTabelaNC(this.selectNC + " order by " + Ordem);

        }

        if (jLabel22.getText().equals("Notas de débito")) {
            String Ordem = "data desc";
            if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Nº da nota asc")) {
                Ordem = "idnota_de_debito asc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Nº da nota desc")) {
                Ordem = "idnota_de_debito desc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Cliente asc")) {
                Ordem = "cliente.idcliente asc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("Cliente desc")) {
                Ordem = "cliente.idcliente desc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("data asc")) {
                Ordem = "data asc";
            } else if (this.jComboBox_OrdenarNota.getSelectedItem().equals("data desc")) {
                Ordem = "data desc";
            }

            PreencherTabelaND(this.selectND + " order by " + Ordem);
        }
//        PreencherTabela("select idutilizador, nome, telefone, perfil ultimo_login from utilizador order by " + this.jComboBox_OrdenarUtilizador.getSelectedItem().toString() + "");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox_FiltrarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_FiltrarNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_FiltrarNotaActionPerformed

    private void jTextField_FiltrarNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_FiltrarNotasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_FiltrarNotasActionPerformed

    private void jButton_FiltrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_FiltrarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_FiltrarMouseClicked

    private void jButton_FiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FiltrarActionPerformed
        String Coluna = " ";
        if (jLabel22.getText().equals("Notas de débito")) {

            if (!this.jTextField_FiltrarNotas.getText().isEmpty()) {
                if (this.jComboBox_FiltrarNota.getSelectedItem().equals("Nº da nota")) {
                    Coluna = "AND nota_de_debito.idnota_de_debito = " + this.jTextField_FiltrarNotas.getText();
                } else if (this.jComboBox_FiltrarNota.getSelectedItem().equals("Cliente")) {
                    Coluna = "AND cliente.designacao ilike '%" + this.jTextField_FiltrarNotas.getText() + "%'";
                } else if (this.jComboBox_FiltrarNota.getSelectedItem().equals("Data")) {
                    Coluna = "AND nota_de_debito.data ilike '%" + this.jTextField_FiltrarNotas.getText() + "%'";
                }

            }
            PreencherTabelaND("Select nota_de_debito.*, cliente.designacao from nota_de_debito, cliente WHERE nota_de_debito.idcliente = cliente.idcliente " + Coluna);
            selectND = "Select nota_de_debito.*, cliente.designacao from nota_de_debito, cliente WHERE nota_de_debito.idcliente = cliente.idcliente " + Coluna;

        }

        if (jLabel22.getText().equals("Notas de crédito")) {

            if (!this.jTextField_FiltrarNotas.getText().isEmpty()) {
                if (this.jComboBox_FiltrarNota.getSelectedItem().equals("Nº da nota")) {
                    Coluna = "AND nota_de_credito.idnota_de_credito = " + this.jTextField_FiltrarNotas.getText();
                } else if (this.jComboBox_FiltrarNota.getSelectedItem().equals("Cliente")) {
                    Coluna = "AND cliente.designacao ilike '%" + this.jTextField_FiltrarNotas.getText() + "%'";
                } else if (this.jComboBox_FiltrarNota.getSelectedItem().equals("Data")) {
                    Coluna = "AND nota_de_credito.data ilike '%" + this.jTextField_FiltrarNotas.getText() + "%'";
                }

            }
            PreencherTabelaNC("Select nota_de_credito.*, cliente.designacao from nota_de_credito, cliente WHERE nota_de_credito.idcliente = cliente.idcliente " + Coluna);
            selectNC = "Select nota_de_credito.*, cliente.designacao from nota_de_credito, cliente WHERE nota_de_credito.idcliente = cliente.idcliente " + Coluna;
        }


    }//GEN-LAST:event_jButton_FiltrarActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // this.jComboBox_OrdenarUtilizador.setModel();
        this.jPanel_RelatórioFactura.setVisible(true);
        this.jPanel_RelatórioNotas.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        jLabel23.setText("Fatura");
        this.selectF = "Select fatura.idfactura, fatura.titulo_da_fatura, cliente.designacao, data from fatura, cliente WHERE fatura.idcliente = cliente.idcliente AND fatura.liquidacao<100 ";
        this.PreencherTabelaF(selectF + " order by data desc");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        new Principal2(id, perfil, liga.getCaminho()).setVisible(true);
        dispose();
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel26MouseClicked
        new ListarFatura(jLabel23.getText(), liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel26MouseClicked

    private void jComboBox_OrdenarFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarFacturaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_OrdenarFacturaMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        System.out.println(this.jComboBox_OrdenarFactura.getSelectedItem());
        String Ordem = "data desc";
        if (jLabel23.getText().equals("Fatura")) {
            if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Nº asc")) {
                Ordem = "fatura.idfactura asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Nº desc")) {
                Ordem = "fatura.idfactura desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("cliente asc")) {
                Ordem = "idcliente asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("cliente desc")) {
                Ordem = "idcliente desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("data asc")) {
                Ordem = "data asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("data desc")) {
                Ordem = "data desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Tipo asc")) {
                Ordem = "titulo_da_fatura asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Tipo desc")) {
                Ordem = "titulo_da_fatura desc";
            }
        } else if (jLabel23.getText().equals("Proforma")) {
            if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Nº asc")) {
                Ordem = "proforma.idproforma asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Nº desc")) {
                Ordem = "proforma.idproforma desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("cliente asc")) {
                Ordem = "idcliente asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("cliente desc")) {
                Ordem = "idcliente desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("data asc")) {
                Ordem = "data asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("data desc")) {
                Ordem = "data desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Tipo asc")) {
                Ordem = "titulo_da_fatura asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Tipo desc")) {
                Ordem = "titulo_da_fatura desc";
            }
        } else if (jLabel23.getText().equals("Fatura recibo")) {
            if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Nº asc")) {
                Ordem = "fatura_recibo.idfatura_recibo asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Nº desc")) {
                Ordem = "fatura_recibo.idfatura_recibo desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("cliente asc")) {
                Ordem = "idcliente asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("cliente desc")) {
                Ordem = "idcliente desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("data asc")) {
                Ordem = "data asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("data desc")) {
                Ordem = "data desc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Tipo asc")) {
                Ordem = "titulo_da_fatura asc";
            } else if (this.jComboBox_OrdenarFactura.getSelectedItem().equals("Tipo desc")) {
                Ordem = "titulo_da_fatura desc";
            }
        }
        PreencherTabelaF(this.selectF + " order by " + Ordem);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox_FiltrarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_FiltrarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_FiltrarFacturaActionPerformed

    private void jTextField_FiltrarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_FiltrarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_FiltrarFacturaActionPerformed

    private void jButton_Filtrar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_Filtrar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Filtrar1MouseClicked

    private void jButton_Filtrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Filtrar1ActionPerformed
        System.out.println(this.jComboBox_FiltrarFactura.getSelectedItem());
        String Coluna = "  ";
        if (jLabel23.getText().equals("Fatura")) {
            if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Nº Fatura")) {
                Coluna = "AND fatura.idfactura = " + this.jTextField_FiltrarFactura.getText();
            } else if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Cliente")) {
                Coluna = "AND cliente.designacao ilike '%" + this.jTextField_FiltrarFactura.getText() + "%'";
            } else if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Data")) {
                Coluna = "AND fatura.data ilike '%" + this.jTextField_FiltrarFactura.getText() + "%'";
            }
            PreencherTabelaF("Select fatura.idfactura, fatura.titulo_da_fatura, cliente.designacao, data from fatura, cliente WHERE fatura.idcliente = cliente.idcliente " + Coluna);
            selectF = "Select fatura.idfactura, fatura.titulo_da_fatura, cliente.designacao, data from fatura, cliente WHERE fatura.idcliente = cliente.idcliente " + Coluna;

        }
        if (jLabel23.getText().equals("Proforma")) {
            if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Nº Fatura")) {
                Coluna = "AND proforma.proforma = " + this.jTextField_FiltrarFactura.getText();
            } else if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Cliente")) {
                Coluna = "AND cliente.designacao ilike '%" + this.jTextField_FiltrarFactura.getText() + "%'";
            } else if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Data")) {
                Coluna = "AND proforma.data ilike '%" + this.jTextField_FiltrarFactura.getText() + "%'";
            }
            PreencherTabelaF("Select proforma.idproforma, proforma.titulo_da_fatura, cliente.designacao, proforma.data from proforma, cliente WHERE proforma.idcliente = cliente.idcliente " + Coluna);
            selectF = "Select proforma.idproforma, proforma.titulo_da_fatura, cliente.designacao, proforma.data from proforma, cliente WHERE proforma.idcliente = cliente.idcliente " + Coluna;

        }
        if (jLabel23.getText().equals("Fatura recibo")) {
            if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Nº Fatura")) {

                if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Nº Fatura")) {
                    Coluna = "AND fatura_recibo.idfatura_recibo = " + this.jTextField_FiltrarFactura.getText();
                } else if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Cliente")) {
                    Coluna = "AND cliente.designacao ilike '%" + this.jTextField_FiltrarFactura.getText() + "%'";
                } else if (this.jComboBox_FiltrarFactura.getSelectedItem().equals("Data")) {
                    Coluna = "AND fatura_recibo.data ilike '%" + this.jTextField_FiltrarFactura.getText() + "%'";
                }
                PreencherTabelaF("Select fatura_recibo.idfatura_recibo, fatura_recibo.titulo_da_fatura, cliente.designacao, fatura_recibo.data from fatura_recibo, cliente WHERE fatura_recibo.idcliente = cliente.idcliente " + Coluna);
                selectF = "Select fatura_recibo.idfatura_recibo, fatura_recibo.titulo_da_fatura, cliente.designacao, fatura_recibo.data from fatura_recibo, cliente WHERE fatura_recibo.idcliente = cliente.idcliente " + Coluna;

            }
        }

    }//GEN-LAST:event_jButton_Filtrar1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.jPanel_RelatórioFactura.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        this.jPanel_RelatórioNotas.setVisible(true);
        this.jLabel22.setText("Notas de débito");
        this.selectND = "Select nota_de_debito.*, designacao from nota_de_debito, cliente WHERE nota_de_debito.idcliente = cliente.idcliente ";
        this.PreencherTabelaND(selectND + " order by nota_de_debito.data desc");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.jPanel_RelatórioFactura.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        this.jPanel_RelatórioNotas.setVisible(true);
        this.jLabel22.setText("Notas de crédito");
        this.selectNC = "Select nota_de_credito.*, designacao from nota_de_credito, cliente WHERE nota_de_credito.idcliente = cliente.idcliente ";
        this.PreencherTabelaNC(selectNC + " order by nota_de_credito.data desc");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jComboBox_OrdenarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_OrdenarFacturaActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        this.jPanel_RelatórioFactura.setVisible(true);
        this.jPanel_RelatórioNotas.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        jLabel23.setText("Proforma");
        this.selectF = "Select proforma.idproforma, proforma.titulo_da_fatura, cliente.designacao, proforma.data from proforma, cliente WHERE proforma.idcliente = cliente.idcliente ";
        this.PreencherTabelaF(selectF + " order by data desc");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        this.jPanel_RelatórioFactura.setVisible(true);
        this.jPanel_RelatórioNotas.setVisible(false);
        this.jPanel_RelatorioLiquidacao.setVisible(false);
        jLabel23.setText("Fatura recibo");
        this.selectF = "Select recibo.idfatura_recibo, recibo.titulo_da_fatura, cliente.designacao, recibo.data from recibo, cliente WHERE recibo.idcliente = cliente.idcliente ";
        this.PreencherTabelaF(selectF + " order by data desc");
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jPanel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel27MouseClicked
        new Principal2(id, perfil, liga.getCaminho()).setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jPanel27MouseClicked

    private void jComboBox_OrdenarNota1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarNota1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_OrdenarNota1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jComboBox_FiltrarNota1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_FiltrarNota1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_FiltrarNota1ActionPerformed

    private void jTextField_FiltrarNotas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_FiltrarNotas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_FiltrarNotas1ActionPerformed

    private void jButton_Filtrar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_Filtrar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Filtrar2MouseClicked

    private void jButton_Filtrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Filtrar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Filtrar2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.jPanel_RelatorioLiquidacao.setVisible(true);
        this.jPanel_RelatórioFactura.setVisible(false);
        this.jPanel_RelatórioNotas.setVisible(false);
        this.PreencherTabelaLiquidacao(selectLic);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jPanel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel28MouseClicked
        new ListarFatura("Liquidação", liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel28MouseClicked

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        new caixa().setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        //new caixa("Saídas de caixa").setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        //new caixa("Entrada de caixa").setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jPanel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel29MouseClicked
        new suporte().setVisible(true);       // TODO add your handling code here:
    }//GEN-LAST:event_jPanel29MouseClicked

    private void jPanel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel37MouseClicked
        new Principal_stock(id, perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel37MouseClicked

    private void jPanel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel31MouseClicked
        new Dashboard(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel31MouseClicked

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
         new ListarFatura("Orçamentos", liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jTableNotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableNotasMouseClicked
        int linha = jTableNotas.getSelectedRow();
        String idFat;
        if (linha != -1) {
            idFat = jTableNotas.getValueAt(linha, 0).toString();
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(rootPane, "Deseja rever o documento " + idFat + "?", "", JOptionPane.YES_NO_OPTION)) {
                if (jLabel22.getText().equals("Notas de crédito")) 
                    this.liga.Abrir_Doc("nota_de_credito", "idnota_de_credito", idFat);
                else
                    this.liga.Abrir_Doc("nota_de_credito", "idnota_de_credito", idFat);
                
               
            }
        }
    }//GEN-LAST:event_jTableNotasMouseClicked

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
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Principal2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame Tesouraria;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupCorDoSistema;
    private javax.swing.ButtonGroup buttonGroupTipoDeLetra;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton_Filtrar;
    private javax.swing.JButton jButton_Filtrar1;
    private javax.swing.JButton jButton_Filtrar2;
    private javax.swing.JComboBox<String> jComboBox_FiltrarFactura;
    private javax.swing.JComboBox<String> jComboBox_FiltrarNota;
    private javax.swing.JComboBox<String> jComboBox_FiltrarNota1;
    private javax.swing.JComboBox<String> jComboBox_OrdenarFactura;
    private javax.swing.JComboBox<String> jComboBox_OrdenarNota;
    private javax.swing.JComboBox<String> jComboBox_OrdenarNota1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Logotipo;
    private javax.swing.JLabel jLabel_nome_utilizador;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_RelatorioLiquidacao;
    private javax.swing.JPanel jPanel_RelatórioFactura;
    private javax.swing.JPanel jPanel_RelatórioNotas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JTable jTableF;
    private javax.swing.JTable jTableLiquidacao;
    private javax.swing.JTable jTableNotas;
    private javax.swing.JTextField jTextField_FiltrarFactura;
    private javax.swing.JTextField jTextField_FiltrarNotas;
    private javax.swing.JTextField jTextField_FiltrarNotas1;
    // End of variables declaration//GEN-END:variables

    byte[] photo = null;
    String filename = null;

    private String YesOrNot(boolean vaL) {
        if (vaL) {
            return "SIM";
        } else {
            return "NÃO";
        }
    }
}

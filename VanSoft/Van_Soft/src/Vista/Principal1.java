/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;



import Modelo.Exporter;
import Modelo.ModeloPDF;
import java.io.File;
import java.util.List;
import javax.swing.JTable;

import javax.swing.JOptionPane;
import Modelo.ModeloColaborador;
import Modelo.ModeloContaForma;
import Modelo.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;
import Controle.ControloBD;
import Modelo.ModeloForma;
import Modelo.ModeloTarefa;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Toolkit;

/**
 *
 * @author Gonga
 */
public class Principal1 extends javax.swing.JFrame {

    int id;
    String perfil;
    ControloBD liga = new ControloBD();
    String selectConta;
    

    /**
     * Creates new form Principal2
     */
    public Principal1() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.jPanel_Agenda.setVisible(false);
        this.jPanel_Contas.setVisible(false);
        this.jPanel_FormasDePagamento.setVisible(false);
        this.jPanel_Colaboradores.setVisible(true);
//        jLabel2.setVisible(false);

    }

    public Principal1(int id, String perfil,String caminho) {
        initComponents();
        
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);
        this.jPanel_Agenda.setVisible(false);
        this.jPanel_Contas.setVisible(false);
        this.jPanel_FormasDePagamento.setVisible(false);
        this.jPanel_Colaboradores.setVisible(true);
        this.id = id;
        this.perfil = perfil;
//        jLabel2.setVisible(false);
        jLabel_nome_utilizador.setText(this.getNomeUser(this.id, this.perfil));
        
        PreencherTabelaColaborador("Select idcolaborador, nome_completo, bi, sexo,email,telefone1,funcao from colaborador order by idcolaborador;");
    }

    public String getNomeUser(int idutilizador, String perfil) {
        try {
            String nome_user="";
            liga.conexao();
            liga.executeSql("select * from utilizador where idutilizador ='" + idutilizador + "' AND perfil='"+perfil+ "'");
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
    public void PreencherTabelaColaborador(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "Nome", "Sexo", "BI", "Email", "Telefone", "Função"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first())
            do {
                dados.add(new Object[]{liga.rs.getString("idcolaborador"), liga.rs.getString("nome_completo"), liga.rs.getString("sexo"), liga.rs.getString("bi"), liga.rs.getString("email"), liga.rs.getString("telefone1"), liga.rs.getString("funcao")});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao Inserir os Dados na Tabela\n"+ex);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTableColaboradores.setModel(modelo);
        jTableColaboradores.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTableColaboradores.getColumnModel().getColumn(0).setResizable(false);
        jTableColaboradores.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableColaboradores.getColumnModel().getColumn(1).setResizable(true);
        jTableColaboradores.getColumnModel().getColumn(2).setPreferredWidth(40);
        jTableColaboradores.getColumnModel().getColumn(2).setResizable(true);
        jTableColaboradores.getColumnModel().getColumn(3).setPreferredWidth(160);
        jTableColaboradores.getColumnModel().getColumn(3).setResizable(true);
        jTableColaboradores.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTableColaboradores.getColumnModel().getColumn(4).setResizable(false);
        jTableColaboradores.getColumnModel().getColumn(5).setPreferredWidth(150);
        jTableColaboradores.getColumnModel().getColumn(5).setResizable(true);
        jTableColaboradores.getColumnModel().getColumn(6).setPreferredWidth(150);
        jTableColaboradores.getColumnModel().getColumn(6).setResizable(true);
        jTableColaboradores.getTableHeader().setReorderingAllowed(false);
        jTableColaboradores.setAutoResizeMode(jTableColaboradores.AUTO_RESIZE_ALL_COLUMNS);
//        jTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }
    private void PreencherTabelaAgenda(String Sql) {
       ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Codigo", "Tarefa", "Data", "Local", "Responsável"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first())
            do {
                dados.add(new Object[]{liga.rs.getString("idagenda"), liga.rs.getString("tarefa"), liga.rs.getString("data_tarefa"),liga.rs.getString("local"), liga.rs.getString("responsavel")});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao Inserir os Dados na Tabela\n"+ex);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTable_Agenda.setModel(modelo);
        jTable_Agenda.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable_Agenda.getColumnModel().getColumn(0).setResizable(false);
        jTable_Agenda.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTable_Agenda.getColumnModel().getColumn(1).setResizable(true);
        jTable_Agenda.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable_Agenda.getColumnModel().getColumn(2).setResizable(false);
        jTable_Agenda.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable_Agenda.getColumnModel().getColumn(3).setResizable(true);
        jTable_Agenda.getColumnModel().getColumn(4).setPreferredWidth(300);
        jTable_Agenda.getColumnModel().getColumn(4).setResizable(true);
       
        jTable_Agenda.getTableHeader().setReorderingAllowed(false);
        jTable_Agenda.setAutoResizeMode(jTable_Agenda.AUTO_RESIZE_ALL_COLUMNS);
//        jTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public void PreencherTabelaFormaPagamento(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "Descrição"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first())
            do {

                dados.add(new Object[]{liga.rs.getString("idformadepagamento"), liga.rs.getString("descricao")});

            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Dados não encntrados\n"+ex, JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTable_FormaPagamento.setModel(modelo);
        jTable_FormaPagamento.getColumnModel().getColumn(0).setPreferredWidth(70);
        jTable_FormaPagamento.getColumnModel().getColumn(0).setResizable(false);
        jTable_FormaPagamento.getColumnModel().getColumn(1).setPreferredWidth(500);
        jTable_FormaPagamento.getColumnModel().getColumn(1).setResizable(false);
        jTable_FormaPagamento.getTableHeader().setReorderingAllowed(false);
        jTable_FormaPagamento.setAutoResizeMode(jTable_FormaPagamento.AUTO_RESIZE_ALL_COLUMNS);
//        jTable_FormaPagamento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();

    }

    public void PreencherTabelaContas(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "Banco", "abreviacao", "numero de conta", "IBAM","Saldo"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first())
            do {

                dados.add(new Object[]{liga.rs.getString("idconta"), liga.rs.getString("nome_banco"), liga.rs.getString("abreviacao"), liga.rs.getString("numero_da_conta"), liga.rs.getString("ibam"),liga.rs.getString("saldo")});

            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex, "Dados não encntrados", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTable_contas.setModel(modelo);
        jTable_contas.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable_contas.getColumnModel().getColumn(0).setResizable(true);
        jTable_contas.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable_contas.getColumnModel().getColumn(1).setResizable(true);
        jTable_contas.getColumnModel().getColumn(2).setPreferredWidth(40);
        jTable_contas.getColumnModel().getColumn(2).setResizable(true);
        jTable_contas.getColumnModel().getColumn(3).setPreferredWidth(140);
        jTable_contas.getColumnModel().getColumn(3).setResizable(true);
        jTable_contas.getColumnModel().getColumn(4).setPreferredWidth(200);
        jTable_contas.getColumnModel().getColumn(4).setResizable(true);
        jTable_contas.getColumnModel().getColumn(5).setPreferredWidth(200);
        jTable_contas.getColumnModel().getColumn(5).setResizable(true);

        jTable_contas.setAutoResizeMode(jTable_FormaPagamento.AUTO_RESIZE_ALL_COLUMNS);
//        jTable_contas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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
        jPanel36 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Empresa = new javax.swing.JInternalFrame();
        jPanel_Colaboradores = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableColaboradores = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel_Agenda = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Agenda = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel_Contas = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_contas = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jComboBox_OrdenarConta = new javax.swing.JComboBox<String>();
        jButton11 = new javax.swing.JButton();
        jPanel50 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jComboBox_Filtrar7 = new javax.swing.JComboBox<String>();
        jTextField_Filtrar2 = new javax.swing.JTextField();
        jButton_Filtrar2 = new javax.swing.JButton();
        jPanel_FormasDePagamento = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_FormaPagamento = new javax.swing.JTable();
        jPanel41 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jComboBox_Filtrar8 = new javax.swing.JComboBox<String>();
        jTextField_FiltrarForma = new javax.swing.JTextField();
        jButton_Filtrar3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
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

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sair");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 142, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 220, 40));

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

        jPanel7.setBackground(new java.awt.Color(54, 70, 78));
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
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tesouraria");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 220, 40));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
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
        jLabel12.setForeground(new java.awt.Color(54, 70, 78));
        jLabel12.setText("Empresa");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 220, 40));

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Copyright©VanSoft");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 220, -1));

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
        jPanel2.add(jLabel_nome_utilizador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 220, -1));

        jPanel36.setBackground(new java.awt.Color(54, 70, 78));
        jPanel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel36MouseClicked(evt);
            }
        });
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel36.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Suporte");
        jPanel36.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel37.setBackground(new java.awt.Color(54, 70, 78));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel37.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel68.setBackground(new java.awt.Color(255, 255, 255));
        jLabel68.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Suporte");
        jPanel37.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel36.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 220, 40));

        jPanel38.setBackground(new java.awt.Color(54, 70, 78));
        jPanel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel38MouseClicked(evt);
            }
        });
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel38.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Stock");
        jPanel38.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

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

        jPanel38.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_product_40px.png"))); // NOI18N
        jPanel38.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 220, 40));

        jPanel47.setBackground(new java.awt.Color(54, 70, 78));
        jPanel47.setForeground(new java.awt.Color(255, 255, 255));
        jPanel47.setToolTipText("Dashboard");
        jPanel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel47MouseClicked(evt);
            }
        });
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Dashboard");
        jPanel47.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_graph_report_script_filled_40px.png"))); // NOI18N
        jPanel47.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 220, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 105, 220, 550));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Empresa.setVisible(true);

        jPanel_Colaboradores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Colaboradores.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(27, 167, 125));

        jLabel22.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Colaborador");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_Colaboradores.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 920, -1));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jTableColaboradores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableColaboradores);

        jPanel13.setToolTipText("Alterar os dados de um colaborador");
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.setOpaque(false);
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_edit_user_male_90px.png"))); // NOI18N
        jLabel27.setText("jLabel27");

        jLabel30.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Editar");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(2, 2, 2)
                .addComponent(jLabel30)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel17.setToolTipText("Eliminar colaborador");
        jPanel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel17.setOpaque(false);
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel17MouseClicked(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_remove_user_male_90px.png"))); // NOI18N
        jLabel28.setText("jLabel28");

        jLabel31.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Eliminar");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel18.setToolTipText("Adicionar um novo colaborador");
        jPanel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel18.setOpaque(false);
        jPanel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel18MouseClicked(evt);
            }
        });
        jPanel18.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel18ComponentResized(evt);
            }
        });

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_add_user_male_90px_1.png"))); // NOI18N
        jLabel32.setText("jLabel27");

        jLabel33.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Adicionar");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel32)
                .addGap(2, 2, 2)
                .addComponent(jLabel33)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel19.setToolTipText("Ver os dados completos dos colaboradores");
        jPanel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel19.setOpaque(false);
        jPanel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel19MouseClicked(evt);
            }
        });

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_list_90px.png"))); // NOI18N
        jLabel29.setText("jLabel28");

        jLabel34.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Lista");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel22.setToolTipText("Actualizar");
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
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
        );

        jPanel_Colaboradores.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 920, 310));

        jPanel_Agenda.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Agenda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Agenda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel26.setBackground(new java.awt.Color(27, 167, 125));

        jLabel43.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Agenda");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_Agenda.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 880, -1));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        jTable_Agenda.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable_Agenda);

        jPanel31.setToolTipText("Adicionar uma conta bancária");
        jPanel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel31.setOpaque(false);
        jPanel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel31MouseClicked(evt);
            }
        });
        jPanel31.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel31ComponentResized(evt);
            }
        });

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_plus_90px_1.png"))); // NOI18N
        jLabel49.setText("jLabel27");

        jLabel50.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Adicionar");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addGap(2, 2, 2)
                .addComponent(jLabel50)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel15.setEnabled(false);
        jPanel15.setOpaque(false);
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_edit_90px.png"))); // NOI18N
        jLabel51.setText("jLabel27");

        jLabel52.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Editar");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jLabel51)
                .addGap(2, 2, 2)
                .addComponent(jLabel52)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel32.setToolTipText("Eliminar contabancária");
        jPanel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel32.setOpaque(false);
        jPanel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel32MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel32MouseEntered(evt);
            }
        });

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_cancel_90px.png"))); // NOI18N
        jLabel53.setText("jLabel28");

        jLabel54.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Eliminar");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel29.setToolTipText("Emitir um relatório dos dados presentes na tabela em PDF e Imprimir");
        jPanel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel29.setOpaque(false);
        jPanel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel29MouseClicked(evt);
            }
        });

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_print_90px.png"))); // NOI18N
        jLabel46.setText("jLabel28");

        jLabel55.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Exportar e Imprimir");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel33.setToolTipText("Emitir um relatório dos dados presentes na tabela em EXEL");
        jPanel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel33.setOpaque(false);
        jPanel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel33MouseClicked(evt);
            }
        });

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_exe_90px.png"))); // NOI18N
        jLabel56.setText("jLabel28");
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Exportar EXEL");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel57)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel35.setToolTipText("Emitir um relatório dos dados presentes na tabela em  em PDF ");
        jPanel35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel35.setOpaque(false);
        jPanel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel35MouseClicked(evt);
            }
        });

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_pdf_2_90px.png"))); // NOI18N
        jLabel58.setText("jLabel28");

        jLabel59.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Exportar PDF");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel_Agenda.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 880, 330));

        jPanel_Contas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_Contas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(27, 167, 125));

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Conta bancária");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_Contas.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 950, -1));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });

        jTable_contas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable_contas);

        jPanel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.setEnabled(false);
        jPanel14.setOpaque(false);
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_edit_90px.png"))); // NOI18N
        jLabel37.setText("jLabel27");

        jLabel38.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Editar");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addGap(2, 2, 2)
                .addComponent(jLabel38)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel24.setToolTipText("Eliminar contabancária");
        jPanel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel24.setOpaque(false);
        jPanel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel24MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel24MouseEntered(evt);
            }
        });

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_cancel_90px.png"))); // NOI18N
        jLabel39.setText("jLabel28");

        jLabel40.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Eliminar");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jPanel25.setToolTipText("Adicionar uma conta bancária");
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.setOpaque(false);
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });
        jPanel25.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel25ComponentResized(evt);
            }
        });

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_plus_90px_1.png"))); // NOI18N
        jLabel41.setText("jLabel27");

        jLabel42.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Adicionar");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel41)
                .addGap(2, 2, 2)
                .addComponent(jLabel42)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel28.setToolTipText("Ver od dados completos das contas");
        jPanel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.setEnabled(false);
        jPanel28.setOpaque(false);
        jPanel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel28MouseClicked(evt);
            }
        });

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_list_90px.png"))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Lista");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45))
        );

        jPanel30.setToolTipText("Actualizar");
        jPanel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel30.setOpaque(false);
        jPanel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel30MouseClicked(evt);
            }
        });

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N
        jLabel47.setText("jLabel28");

        jLabel48.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Actualizar");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48))
        );

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox_OrdenarConta.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_OrdenarConta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "nome asc", "nome desc", "abrev asc", "abrev desc" }));
        jComboBox_OrdenarConta.setToolTipText("");
        jComboBox_OrdenarConta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_OrdenarContaMouseClicked(evt);
            }
        });

        jButton11.setText("Ordenar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jComboBox_OrdenarConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_OrdenarConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel72.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setText("Filtrar por");

        jComboBox_Filtrar7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_Filtrar7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "nome", "abrev", "conta", "ibam" }));
        jComboBox_Filtrar7.setToolTipText("");
        jComboBox_Filtrar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_Filtrar7ActionPerformed(evt);
            }
        });

        jTextField_Filtrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_Filtrar2ActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_Filtrar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_Filtrar2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Filtrar2)
                .addContainerGap())
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72)
                    .addComponent(jComboBox_Filtrar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Filtrar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Filtrar2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
        );

        jPanel_Contas.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 950, 310));

        jPanel_FormasDePagamento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_FormasDePagamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel39.setBackground(new java.awt.Color(27, 167, 125));

        jLabel25.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Forma de pagamento");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_FormasDePagamento.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 920, -1));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));

        jTable_FormaPagamento.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable_FormaPagamento);

        jPanel41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel41.setEnabled(false);
        jPanel41.setOpaque(false);
        jPanel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel41MouseClicked(evt);
            }
        });

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_edit_90px.png"))); // NOI18N
        jLabel60.setText("jLabel27");

        jLabel61.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("Editar");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(jLabel60)
                .addGap(2, 2, 2)
                .addComponent(jLabel61)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel42.setToolTipText("Eliminar forma de pagamento");
        jPanel42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel42.setOpaque(false);
        jPanel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel42MouseClicked(evt);
            }
        });

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_cancel_90px.png"))); // NOI18N
        jLabel62.setText("jLabel28");

        jLabel63.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Eliminar");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel63)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel43.setToolTipText("Adicionar uma forma de pagamento");
        jPanel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel43.setOpaque(false);
        jPanel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel43MouseClicked(evt);
            }
        });
        jPanel43.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel43ComponentResized(evt);
            }
        });

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_plus_90px_1.png"))); // NOI18N
        jLabel64.setText("jLabel27");

        jLabel65.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("Adicionar");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jLabel64)
                .addGap(2, 2, 2)
                .addComponent(jLabel65)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel44.setToolTipText("Ver od dados completos das formas de pagamento");
        jPanel44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel44.setEnabled(false);
        jPanel44.setOpaque(false);
        jPanel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel44MouseClicked(evt);
            }
        });

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_list_90px.png"))); // NOI18N
        jLabel66.setText("jLabel28");

        jLabel67.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("Lista");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel67))
        );

        jPanel46.setToolTipText("Actualizar");
        jPanel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel46.setOpaque(false);
        jPanel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel46MouseClicked(evt);
            }
        });

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N
        jLabel69.setText("jLabel28");

        jLabel70.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("Actualizar");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel70))
        );

        jPanel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel73.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("Filtrar por");

        jComboBox_Filtrar8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox_Filtrar8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Descrição" }));
        jComboBox_Filtrar8.setToolTipText("");
        jComboBox_Filtrar8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_Filtrar8ActionPerformed(evt);
            }
        });

        jTextField_FiltrarForma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_FiltrarFormaActionPerformed(evt);
            }
        });

        jButton_Filtrar3.setText("Filtar");
        jButton_Filtrar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_Filtrar3MouseClicked(evt);
            }
        });
        jButton_Filtrar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Filtrar3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_Filtrar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_FiltrarForma))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Filtrar3)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel51Layout.createSequentialGroup()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73)
                    .addComponent(jComboBox_Filtrar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_FiltrarForma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Filtrar3))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
        );

        jPanel_FormasDePagamento.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 56, 920, 310));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_permanent_job_90px.png"))); // NOI18N
        jMenu1.setText("Colaborador");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_company_90px.png"))); // NOI18N
        jMenu3.setText("Empresa");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_property_time_90px.png"))); // NOI18N
        jMenu2.setText("Agenda");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_money_box_90px.png"))); // NOI18N
        jMenu4.setText("Banco");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Conta bancária");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);
        jMenu4.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Formas de pagamento");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_money_bag_90px.png"))); // NOI18N
        jMenu5.setText("Caixa");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenu5.add(jSeparator2);

        jMenuItem2.setText("Caixa");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);
        jMenu5.add(jSeparator3);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Efetuar entrada");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem6);
        jMenu5.add(jSeparator4);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Efetuar saída");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);
        jMenu5.add(jSeparator5);

        jMenuBar1.add(jMenu5);

        Empresa.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout EmpresaLayout = new javax.swing.GroupLayout(Empresa.getContentPane());
        Empresa.getContentPane().setLayout(EmpresaLayout);
        EmpresaLayout.setHorizontalGroup(
            EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 951, Short.MAX_VALUE)
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_Colaboradores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_Agenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_Contas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jPanel_FormasDePagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        EmpresaLayout.setVerticalGroup(
            EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 423, Short.MAX_VALUE)
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel_Colaboradores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel_Agenda, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_Contas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(21, 21, 21)))
            .addGroup(EmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(EmpresaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel_FormasDePagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(11, 11, 11)))
        );

        jPanel3.add(Empresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 900, 540));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 900, 550));

        jLabel_Logotipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Logotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/LogoApp.png"))); // NOI18N
        jPanel1.add(jLabel_Logotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        new Principal(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
       // System.exit(0);
        new Entar().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        int X = jTableColaboradores.getSelectedRow();
        int idSelected = -1;
        if (X != -1) {
            idSelected = Integer.parseInt(jTableColaboradores.getValueAt(X, 0).toString());
            ModeloColaborador mode = new ModeloColaborador(idSelected);
            if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
                this.dispose();
                new EditarColaborador(mode, this.id, this.perfil,liga.getCaminho()).setVisible(true);
            }
            if (this.perfil.equals("Operador de caixa")) {
                JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona um colaborador.", "", JOptionPane.WARNING_MESSAGE);
        }
       

    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int result;
            int rows;
            ArrayList<String> Aldone = new ArrayList<>();
            ArrayList<String> bad = new ArrayList<>();
            rows = this.jTableColaboradores.getSelectedRowCount();

            if (rows > 0) {
                if (rows > 1) {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar os colaboradores selecionados?", null, JOptionPane.YES_NO_OPTION);
                } else {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar o colaborador selecionado?", null, JOptionPane.YES_NO_OPTION);
                }

                if (JOptionPane.YES_OPTION == result) {
                    String row;
                    for (int i = 0; i < rows; i++) {
                        row = this.jTableColaboradores.getValueAt(this.jTableColaboradores.getSelectedRows()[i], 0).toString();
                        if (liga.eliminar("colaborador", "idcolaborador", row)) {
                            Aldone.add(this.jTableColaboradores.getValueAt(this.jTableColaboradores.getSelectedRows()[i], 0).toString());
                        } else {
                            bad.add(this.jTable_FormaPagamento.getValueAt(this.jTableColaboradores.getSelectedRows()[i], 0).toString());
                        }

                    }
                    if (bad.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Eliminado com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao eliminar " + bad.size() + " conta(s).", "Erro de sistema", JOptionPane.ERROR_MESSAGE);
                    }

                    new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado.", "Não eliminado.", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jPanel17MouseClicked

    private void jPanel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel18MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            new AddColaborador(this.id, this.perfil, liga.getCaminho()).setVisible(true);
            this.dispose();
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jPanel18MouseClicked

    private void jPanel18ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel18ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel18ComponentResized

    private void jPanel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel19MouseClicked
        
        new ListarColaborador(liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel19MouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        new Principal1(this.id,this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            this.dispose();
            new RegistarEmpresa(this.id, perfil,liga.getCaminho()).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", "", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jMenu3MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        new Principal1(this.id,this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        this.jPanel_FormasDePagamento.setVisible(false);
        this.jPanel_Contas.setVisible(false);
        this.jPanel_Colaboradores.setVisible(false);
        this.jPanel_Agenda.setVisible(true);
        this.PreencherTabelaAgenda("SELECT * FROM agenda ");
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            this.dispose();
            new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);

            this.jPanel_FormasDePagamento.setVisible(false);
            this.jPanel_Contas.setVisible(false);
            this.jPanel_Agenda.setVisible(false);
            this.jPanel_Colaboradores.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", "", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jMenu1MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        new Principal2(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        new Principal3(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel24MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int result;
            int rows;
            ArrayList<String> Aldone = new ArrayList<>();
            ArrayList<String> bad = new ArrayList<>();
            rows = this.jTable_contas.getSelectedRowCount();

            if (rows > 0) {
                if (rows > 1) {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar as contas de pagamento selecionadas?", null, JOptionPane.YES_NO_OPTION);
                } else {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar a conta de pagamento selecionada?", null, JOptionPane.YES_NO_OPTION);
                }

                if (JOptionPane.YES_OPTION == result) {
                    String row;
                    for (int i = 0; i < rows; i++) {
                        row = this.jTable_contas.getValueAt(this.jTable_contas.getSelectedRows()[i], 0).toString();
                        if (liga.eliminar("forma_de_pagamento", "idconta", row) && liga.eliminar("conta", "idconta", row)) {

                            Aldone.add(this.jTable_contas.getValueAt(this.jTable_contas.getSelectedRows()[i], 0).toString());
                        } else {
                            bad.add(this.jTable_FormaPagamento.getValueAt(this.jTable_FormaPagamento.getSelectedRows()[i], 0).toString());
                        }

                    }
                    if (bad.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Eliminado com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao eliminar " + bad.size() + " conta(s).", "Erro de sistema", JOptionPane.ERROR_MESSAGE);
                    }

                    new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma conta selecionada.", "Não eliminado.", JOptionPane.WARNING_MESSAGE);
            }

        }

        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", null, JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jPanel24MouseClicked

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        this.dispose();
        new AddContaBancaria(this.id, this.perfil, liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel25ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel25ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel25ComponentResized

    private void jPanel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel28MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel28MouseClicked

    private void jPanel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel30MouseClicked
        this.dispose();
        new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel30MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int X = jTable_contas.getSelectedRow();
            int idSelected;
            if (X != -1) {
                idSelected = Integer.parseInt(jTable_contas.getValueAt(X, 0).toString());
                ModeloContaForma mode = new ModeloContaForma(idSelected);
                this.dispose();
                new EditarContaBancaria(mode, this.id, this.perfil,liga.getCaminho()).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona um utilizador.", "", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel41MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int X = jTable_FormaPagamento.getSelectedRow();
            int idSelected;
            if (X != -1) {
                idSelected = Integer.parseInt(jTable_FormaPagamento.getValueAt(X, 0).toString());
                System.out.println(idSelected);
                String descSelected = jTable_FormaPagamento.getValueAt(X, 1).toString();
                System.out.println(descSelected);
                ModeloForma mode = new ModeloForma(idSelected, descSelected);
                new EditarFormaDePagamento(mode, this.id, this.perfil,liga.getCaminho()).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona uma forma de pagamento selecionada.", "", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);

        }

    }//GEN-LAST:event_jPanel41MouseClicked

    private void jPanel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel42MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int result;
            int rows;
            ArrayList<String> Aldone = new ArrayList<>();
            ArrayList<String> bad = new ArrayList<>();
            rows = this.jTable_FormaPagamento.getSelectedRowCount();
            if (rows > 0) {
                if (rows > 1) {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar as formas de pagamento selecionadas?", null, JOptionPane.YES_NO_OPTION);
                } else {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar a forma de pagamento selecionada?", null, JOptionPane.YES_NO_OPTION);
                }

                if (JOptionPane.YES_OPTION == result) {
                    String row;
                    for (int i = 0; i < rows; i++) {
                        row = this.jTable_FormaPagamento.getValueAt(this.jTable_FormaPagamento.getSelectedRows()[i], 0).toString();
                        if (liga.eliminar("forma_de_pagamento", "idformadepagamento", row)) {
                            Aldone.add(this.jTable_FormaPagamento.getValueAt(this.jTable_FormaPagamento.getSelectedRows()[i], 0).toString());
                        } else {
                            bad.add(this.jTable_FormaPagamento.getValueAt(this.jTable_FormaPagamento.getSelectedRows()[i], 0).toString());
                        }

                    }
                    if (bad.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Eliminado com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao eliminar " + bad.size() + " forma(s) de pagamnto.", "Erro de sistema", JOptionPane.ERROR_MESSAGE);
                    }

                    new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma forma de pagamento selecionada.", "Não eliminado.", JOptionPane.WARNING_MESSAGE);
            }

        }

        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", null, JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jPanel42MouseClicked

    private void jPanel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel43MouseClicked
        this.dispose();
        new AddFormaDePagamento(this.id, this.perfil).setVisible(true);
    }//GEN-LAST:event_jPanel43MouseClicked

    private void jPanel43ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel43ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel43ComponentResized

    private void jPanel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel44MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel44MouseClicked

    private void jPanel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel46MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel46MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.jPanel_Agenda.setVisible(false);
        this.jPanel_FormasDePagamento.setVisible(false);
        this.jPanel_Colaboradores.setVisible(false);
        this.jPanel_Contas.setVisible(true);
        PreencherTabelaContas("select * from conta order by idconta");
        this.selectConta = "select * from conta ";

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.jPanel_Contas.setVisible(false);
        this.jPanel_Colaboradores.setVisible(false);
        this.jPanel_Agenda.setVisible(false);
        this.jPanel_FormasDePagamento.setVisible(true);
        PreencherTabelaFormaPagamento("Select idformadepagamento, descricao from forma_de_pagamento order by idformadepagamento");

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
       new caixa ().setVisible(false);

    }//GEN-LAST:event_jMenu5MouseClicked

    private void jPanel24MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel24MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel24MouseEntered

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
       new Principal4(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel31MouseClicked
        this.dispose();
        new AddTarefa(this.id, this.perfil,liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel31MouseClicked

    private void jPanel31ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel31ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel31ComponentResized

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int X = jTable_Agenda.getSelectedRow();
            int idSelected;
            if (X != -1) {
                idSelected = Integer.parseInt(jTable_Agenda.getValueAt(X, 0).toString());
                ModeloTarefa mode = new ModeloTarefa(id,jTable_Agenda.getValueAt(X, 1).toString(), jTable_Agenda.getValueAt(X, 2).toString(), jTable_Agenda.getValueAt(X, 3).toString(), jTable_Agenda.getValueAt(X, 4).toString());
                this.dispose();
                new EditarTarefa(this.id, this.perfil, mode).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona uma tarefa.", "", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel32MouseClicked
//        System.out.println("aaaaa");   
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            int result;
            int rows;
            ArrayList<String> Aldone = new ArrayList<>();
            ArrayList<String> bad = new ArrayList<>();
            rows = this.jTable_Agenda.getSelectedRowCount();

            if (rows > 0) {
                if (rows > 1) {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar as tarefas selecionadas?", null, JOptionPane.YES_NO_OPTION);
                } else {
                    result = JOptionPane.showConfirmDialog(rootPane, "Deseja eliminar a tarefa selecionada?", null, JOptionPane.YES_NO_OPTION);
                }

                if (JOptionPane.YES_OPTION == result) {
                    String row;
                    for (int i = 0; i < rows; i++) {
                        row = this.jTable_Agenda.getValueAt(this.jTable_Agenda.getSelectedRows()[i], 0).toString();
                        if (liga.eliminar("agenda", "idagenda", row)) {
                            Aldone.add(this.jTable_Agenda.getValueAt(this.jTable_Agenda.getSelectedRows()[i], 0).toString());
                        } else {
                            bad.add(this.jTable_Agenda.getValueAt(this.jTable_Agenda.getSelectedRows()[i], 0).toString());
                        }

                    }
                    if (bad.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Eliminado com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao eliminar " + bad.size() + " conta(s).", "Erro de sistema", JOptionPane.ERROR_MESSAGE);
                    }

                    new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma tarefa selecionada.", "Não eliminado.", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jPanel32MouseClicked

    private void jPanel32MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel32MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel32MouseEntered

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            new saida_de_caixa(this.id, this.perfil,liga.getCaminho()).setVisible(true);
            this.dispose();
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
       if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            
           new entrada_de_caixa(this.id, this.perfil,liga.getCaminho()).setVisible(true);
           this.dispose();
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new caixa().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jComboBox_OrdenarContaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_OrdenarContaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_OrdenarContaMouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String ordem = this.jComboBox_OrdenarConta.getSelectedItem().toString();
         if (ordem.equals("nome asc"))
            ordem = "nome_banco asc";
        if (ordem.equals("nome desc")) 
            ordem = "nome_banco desc";
        if (ordem.equals("abrev asc")) 
            ordem = "abreviacao asc";
        if (ordem.equals("abrev desc")) 
            ordem = "abreviacao desc";
        PreencherTabelaContas(this.selectConta+" order by "+ordem);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jComboBox_Filtrar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Filtrar7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_Filtrar7ActionPerformed

    private void jTextField_Filtrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_Filtrar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_Filtrar2ActionPerformed

    private void jButton_Filtrar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_Filtrar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Filtrar2MouseClicked

    private void jButton_Filtrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Filtrar2ActionPerformed

        String colum = jComboBox_Filtrar7.getSelectedItem().toString();

        if (colum.equals("nome"))
        colum = "nome_bamco";
        if (colum.equals("abrev"))
        colum = "abreviacao";
        if (colum.equals("conta"))
        colum = "numero_da_conta";
        if (colum.equals("ibam"))
        colum = "ibam";

        this.selectConta = "select * from colaborador where "+colum+" ilike '%"+this.jTextField_Filtrar2.getText()+"%'";
        PreencherTabelaContas(selectConta);

    }//GEN-LAST:event_jButton_Filtrar2ActionPerformed

    private void jComboBox_Filtrar8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Filtrar8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_Filtrar8ActionPerformed

    private void jTextField_FiltrarFormaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_FiltrarFormaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_FiltrarFormaActionPerformed

    private void jButton_Filtrar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_Filtrar3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Filtrar3MouseClicked

    private void jButton_Filtrar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Filtrar3ActionPerformed
        PreencherTabelaFormaPagamento("Select idformadepagamento, descricao from forma_de_pagamento WHERE descricao ilike '%"+this.jTextField_FiltrarForma.getText()+"%' order by descricao"); 
    }//GEN-LAST:event_jButton_Filtrar3ActionPerformed

    private void jPanel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel29MouseClicked
        if (jTable_Agenda.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String file = chooser.getSelectedFile().toString().concat(".pdf");
                try {
                    ModeloPDF e = new ModeloPDF(new File(file), jTable_Agenda, "Relatório",liga.getCaminho());
                                      
                    e.ExportarAgenda("Agenda de trabalho.",true);

                    JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a exportação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jPanel29MouseClicked

    private void jPanel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel33MouseClicked
        if (jTable_Agenda.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (.xls)", "xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                List<JTable> tb = new ArrayList<JTable>();
                List<String> nom = new ArrayList<String>();
                tb.add(jTable_Agenda);
                
                nom.add("Agenda de trabalho.");

                String file = chooser.getSelectedFile().toString().concat(".xls");
                try {
                    Exporter e = new Exporter(new File(file), tb, nom);
                    if (e.export()) {
                        JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro na exportação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jPanel33MouseClicked

    private void jPanel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel35MouseClicked
        if (jTable_Agenda.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
            
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String file = chooser.getSelectedFile().toString().concat(".pdf");
                try {
                    ModeloPDF e = new ModeloPDF(new File(file), jTable_Agenda, "Relatório",liga.getCaminho());
                    e.ExportarAgenda("Agenda de trabalho",false);

                    JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a exportação.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jPanel35MouseClicked

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
            // TODO add your handling code here:
    }//GEN-LAST:event_jLabel56MouseClicked

    private void jPanel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel36MouseClicked
        new suporte().setVisible(true);       // TODO add your handling code here:
    }//GEN-LAST:event_jPanel36MouseClicked

    private void jPanel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel38MouseClicked
       new Principal_stock(id, perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel38MouseClicked

    private void jPanel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel47MouseClicked
        new Dashboard(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel47MouseClicked

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
            java.util.logging.Logger.getLogger(Principal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame Empresa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupCorDoSistema;
    private javax.swing.ButtonGroup buttonGroupTipoDeLetra;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton_Filtrar2;
    private javax.swing.JButton jButton_Filtrar3;
    private javax.swing.JComboBox<String> jComboBox_Filtrar7;
    private javax.swing.JComboBox<String> jComboBox_Filtrar8;
    private javax.swing.JComboBox<String> jComboBox_OrdenarConta;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel78;
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
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
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
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_Agenda;
    private javax.swing.JPanel jPanel_Colaboradores;
    private javax.swing.JPanel jPanel_Contas;
    private javax.swing.JPanel jPanel_FormasDePagamento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTable jTableColaboradores;
    private javax.swing.JTable jTable_Agenda;
    private javax.swing.JTable jTable_FormaPagamento;
    private javax.swing.JTable jTable_contas;
    private javax.swing.JTextField jTextField_Filtrar2;
    private javax.swing.JTextField jTextField_FiltrarForma;
    // End of variables declaration//GEN-END:variables

    byte[] photo = null;
    String filename = null;

    
}

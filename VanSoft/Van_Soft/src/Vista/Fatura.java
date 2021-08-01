/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. print(
 */
package Vista;

import Controle.ControloBD;
import Controle.EncriptaDecriptaRSA;
import Controle.Extenso;
import Controle.Tempo;
import java.awt.Color;
import Modelo.ModeloCliente;
import Modelo.ModeloEmpresa;
import Modelo.ModeloFatura;
import Modelo.ModeloPDF;
import Modelo.ModeloProduto;
import Modelo.ModeloServico;
import Modelo.ModeloTabela;
import Modelo.ModeloUtilizador;
import Modelo.ModeloVenda;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Gonga
 */
public class Fatura extends javax.swing.JFrame {

    int id_turno = -1;
    int numero = 0;
    int id, QuantPontos = 0;
    String perfil;
    String serie = "";
    double percentImpostoIVA, imposto_Total, subTotal;
    Controle.ControloBD liga = new ControloBD();
    ModeloCliente MC = new ModeloCliente(-1);
    ModeloUtilizador MU = new ModeloUtilizador();
    ModeloEmpresa ME = new ModeloEmpresa();
    String[] colunas = new String[]{"Cód.", "Tipo", "Descrição", "P. Unit.", "IVA", "Motivo", "R. Font", "QNT", "Desc", "Total"};
    ModeloFatura MF = new ModeloFatura();
//    MF.setTotal_doIVA(0);
    ArrayList dados = new ArrayList();
    Tempo tempo = new Tempo();
    private double ValorPago = -1;
    ArrayList<String> SelectedForma;
    ArrayList<Integer> Id_armazens = new ArrayList();
    ArrayList<String> Localizacao_armazens = new ArrayList();
    ArrayList<Integer> Id_armazensAux = new ArrayList();
    private String select;
    Extenso exte = new Extenso();
    int Dias_de_validade = 7;
    EncriptaDecriptaRSA Assinatura = new EncriptaDecriptaRSA();

    /**
     * Creates new form AddUtilizador TakeQNT
     */
    public Fatura() {

        initComponents();
        this.jLabel_TítuloDaTabela.setText("Serviços");
        PreencherTable("select idservico, descricao, preco_unitario, imposto_iva, motivo,preco  from servico order by idservico");
        this.jInternalFrame1.setVisible(false);
        jTableContentFact.getTableHeader().setReorderingAllowed(false);
        jTableContentFact.setAutoResizeMode(jTableContentFact.AUTO_RESIZE_ALL_COLUMNS);
        jTableContentFact.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        DEFINIR PERCENTAGEM
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
                        this.dispose();
                        new Principal4(this.id, this.perfil, liga.getCaminho()).setVisible(true);

                    } else {
                        this.dispose();
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
//        this.jLabel2.setText("Tamanho do documento");

        MC.setIdCliente(1);
        this.jLabel_NomeDoCliente.setText("CONSUMIDOR FINAL");
        this.MC.setDesignacao("CONSUMIDOR FINAL");
        this.MC.setTipo_de_pessoa("");
        this.MC.setBi("CONSUMIDOR FINAL");
        this.MC.setNif("CONSUMIDOR FINAL");
        this.MC.setSexo("");

        this.MC.setTelefone("DESCONHECIDO");
        this.MC.setEmail("DESCONHECIDO");
        this.MC.setEndereco("DESCONHECIDO");
        this.MC.setCidade("DESCONHECIDO");
        this.jLabel_NomeDoCliente.setText(MC.getDesignacao());

        jTextField1.requestFocus();

    }

    public Fatura(String tipoDeFatura, int id, String perfil, String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        liga.setCaminho(caminho);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        try {

//            this.jLabel2.setText("");
            this.jLabel_tipo_de_factura.setText(tipoDeFatura);
            MF.setTitulo(tipoDeFatura);
            MF.setTotal_doIVA(0.0);
            MF.setModeda("Kwanza");
            this.jLabel_TítuloDaTabela.setText("Serviços");
            this.imposto_Total = this.subTotal = 0;
            liga.conexao();
            liga.executeSql("select * from empresa limit 1");
            String tamanho = "A4";
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
                jLabel_NomeDaEmp.setText(ME.getDesignacao());
                jLabel_nif.setText(ME.getNif());
                this.percentImpostoIVA = liga.rs.getDouble("valor_imposto_iva");
                this.ME.setRegime_de_iva(liga.rs.getString("regime_de_iva"));
                tamanho = liga.rs.getString("tamanho_doc");
                this.ME.setTamanho_do_doc(tamanho);
                this.Dias_de_validade = 7;
                this.ME.setIndicativo(liga.rs.getString("indicativo"));
                this.ME.setTaxa_de_retencao(liga.rs.getDouble("retencao_na_fonte") / 100);
                this.ME.setPagador(liga.rs.getString("pagador_retencao"));
                this.ME.setSerie(liga.rs.getString("serie"));
//                System.out.println("Serie" + this.ME.getSerie());
//                this.jTextFieldTRETENCAO.setText(Double.toString(liga.rs.getDouble("retencao_na_fonte")));
//                if (liga.rs.getString("pagador_retencao").equals("cliente")){
//                    this.jRadioButton2.setSelected(true);
//                    nome_do
//                }else{
//                   this.jRadioButton1.setSelected(true);
//                }
                switch (tamanho) {
                    case "ticket":
                        this.jRadioButton_ticket.setSelected(true);
                        break;
                    case "ticket pequeno":
                        this.jRadioButton_ticket_pequeno.setSelected(true);
                        break;
                    case "A5":
                        this.jRadioButton_A5.setSelected(true);
                        break;
                    default:
                        this.jRadioButton_A4.setSelected(true);
                        break;
                }
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
            JOptionPane.showMessageDialog(rootPane, "Dados do utilizador não encntrados ", null, JOptionPane.WARNING_MESSAGE);
        }

        PreencherTable("select idservico, descricao, preco_unitario, imposto_iva, motivo,preco from servico order by idservico");
        this.jInternalFrame1.setVisible(false);
        this.id = id;
        this.perfil = perfil;

//        jTableContentFact.getTableHeader().setReorderingAllowed(false);
//        jTableContentFact.setAutoResizeMode(jTableContentFact.AUTO_RESIZE_ALL_COLUMNS);
//        jTableContentFact.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        jComboBox_armazem.removeAllItems();
        liga.conexao();
        liga.executeSql("Select * from armazem order by id_armazem asc");
        try {
            liga.rs.first();
            do {
                jComboBox_armazem.addItem(liga.rs.getString("descricao"));
                this.Id_armazens.add(liga.rs.getInt("id_armazem"));
                this.Localizacao_armazens.add(liga.rs.getString("localizacao"));
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Não é possível realizar esta ação sem pelo menos um armazem cadatastrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

        liga.deconecta();

        if (tipoDeFatura.equals("Proforma")) {
            this.jLabel_TítuloDaTabela.setText("Produtos");
//       IMPOSTO(     PreencherTable("select codigo, descricao, preco,imposto_iva, motivo from produto where existencia > 0 order by idproduto");
            jRadioButton1.setSelected(false);
            jRadioButton2.setSelected(true);
            jRadioButton1.setEnabled(false);
//            jRadioButton1.setd(false);

            this.select = "select * from produto";
            if (jComboBox_armazem.getItemCount() > 0 && this.Id_armazens.size() > 0) {
                PreencherTableProduto("select produto.*,entrada.* from  produto order by descricao");
            }

        }

        MC.setIdCliente(1);
        this.jLabel_NomeDoCliente.setText("CONSUMIDOR FINAL");
        this.MC.setDesignacao("CONSUMIDOR FINAL");
        this.MC.setTipo_de_pessoa("");
        this.MC.setBi("CONSUMIDOR FINAL");
        this.MC.setNif("CONSUMIDOR FINAL");
        this.MC.setSexo("");

        this.MC.setTelefone("DESCONHECIDO");
        this.MC.setEmail("DESCONHECIDO");
        this.MC.setEndereco("DESCONHECIDO");
        this.MC.setCidade("DESCONHECIDO");
        this.jLabel_NomeDoCliente.setText(MC.getDesignacao());

        jTextField1.requestFocus();

    }

//    public void PreencherTableProduto(String id, String produto, String qnt, String lote, String custo) {
//
//        Double total = Double.parseDouble(Convert(custo)) * Double.parseDouble(qnt);
//        String arm = jComboBox_armazem.getSelectedItem().toString();
//        this.Id_armazensAux.add(this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()));
//        dados_entrada.add(new Object[]{id, produto, exte.Chang(qnt), lote, custo, exte.Chang(total), arm});
//
//        ModeloTabela modelo = new ModeloTabela(dados_entrada, colunas_entrada) {
//        };
//
//        T_Tabela_entrada.setModel(modelo);
//
////        
//        T_Tabela_entrada.getTableHeader()
//                .setReorderingAllowed(false);
//        T_Tabela_entrada.setAutoResizeMode(T_Tabela_entrada.AUTO_RESIZE_ALL_COLUMNS);
//
//    }
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

        cne.executeSql("select SUM (qnt) from saida where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()));

        try {
            cne.rs.first();
            do {
                saida = cne.rs.getDouble(1);

            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        return entrada - saida;

    }
//addvenda(

    public double TakeQNT2(int idproduto) {
        ControloBD cne = new ControloBD();
        double saida = 0;
        cne.conexao();

        cne.executeSql("select lote, SUM (qnt) from saida where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()) + " group by lote");
        try {
            cne.rs.first();
            do {
                saida = cne.rs.getDouble(1);

            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        return saida;

    }

    public void PreencherTableProduto(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Codigo", "Descrição", "P. unit", "IVA", "Motivo", "QNT", "P. venda"};
        liga.conexao();
        liga.executeSql(Sql);
        try {
            liga.rs.first();
            do {
                String qnt = Double.toString(TakeQNT(liga.rs.getInt(1)));
                String custo = liga.rs.getString("custo");
                if (custo != null) {
                    custo = exte.Chang(custo);
                } else {
                    custo = "0,00";
                }
                String preco = liga.rs.getString("preco_unitario");
                if (preco == null) {
                    preco = "0.00";
                }
                String precoV = liga.rs.getString("preco");
                if (precoV == null) {
                    precoV = "0.00";
                }
                dados.add(new Object[]{liga.rs.getString("codigo"), liga.rs.getString("descricao"), exte.Chang(preco),
                    IMPOSTO(liga.rs.getBoolean("imposto_iva")), liga.rs.getString("motivo"), qnt, exte.Chang(precoV)});
            } while (liga.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };

        jTableMAteria.setModel(modelo);
        jTableMAteria.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTableMAteria.getColumnModel().getColumn(0).setResizable(true);
        jTableMAteria.getColumnModel().getColumn(1).setPreferredWidth(300);
        jTableMAteria.getColumnModel().getColumn(1).setResizable(true);
        jTableMAteria.getColumnModel().getColumn(3).setPreferredWidth(40);
        jTableMAteria.getColumnModel().getColumn(3).setResizable(true);
        jTableMAteria.getColumnModel().getColumn(4).setPreferredWidth(5);
        jTableMAteria.getColumnModel().getColumn(4).setResizable(true);
//        jTableMAteria.getColumnModel().getColumn(5).setPreferredWidth(40);
//        jTableMAteria.getColumnModel().getColumn(5).setResizable(true);
//        
        jTableMAteria.getTableHeader()
                .setReorderingAllowed(false);
        jTableMAteria.setAutoResizeMode(jTableMAteria.AUTO_RESIZE_ALL_COLUMNS);

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
        buttonGroupTipo = new javax.swing.ButtonGroup();
        buttonGrouptamanho_doc = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelTopMenu = new javax.swing.JPanel();
        jLabel_tipo_de_factura = new javax.swing.JLabel();
        jPanelCenter = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton_ticket_pequeno = new javax.swing.JRadioButton();
        jRadioButton_ticket = new javax.swing.JRadioButton();
        jRadioButton_A5 = new javax.swing.JRadioButton();
        jRadioButton_A4 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanelcenter2 = new javax.swing.JPanel();
        jLabel_NomeDaEmp = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel_nif = new javax.swing.JLabel();
        jLabel_Desconto = new javax.swing.JLabel();
        jLabel_RetencaoNaFonte = new javax.swing.JLabel();
        jLabel_Total = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel_NomeDoCliente = new javax.swing.JLabel();
        jPanelEsc = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelPRODUTO_SERVICO = new javax.swing.JPanel();
        jTextField_Desconto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMAteria = new javax.swing.JTable();
        jLabel_TítuloDaTabela = new javax.swing.JLabel();
        jComboBox_armazem = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_QNT = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jTextFieldPagamento = new javax.swing.JTextField();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jTextFieldPagamento2 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_FormaPagamento = new javax.swing.JTable();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel_Utilizadores = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_cliente = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jPanel21 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableContentFact = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jButtonGerarFatura = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("POS");
        setName("POS"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1000, 600));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanelTopMenu.setBackground(new java.awt.Color(27, 167, 125));

        jLabel_tipo_de_factura.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel_tipo_de_factura.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_tipo_de_factura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_tipo_de_factura.setText("Tipo da factura");

        javax.swing.GroupLayout jPanelTopMenuLayout = new javax.swing.GroupLayout(jPanelTopMenu);
        jPanelTopMenu.setLayout(jPanelTopMenuLayout);
        jPanelTopMenuLayout.setHorizontalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_tipo_de_factura)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelTopMenuLayout.setVerticalGroup(
            jPanelTopMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel_tipo_de_factura)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanelCenter.setBackground(new java.awt.Color(54, 70, 78));
        jPanelCenter.setBorder(new javax.swing.border.MatteBorder(null));
        jPanelCenter.setForeground(new java.awt.Color(255, 255, 51));

        jPanel12.setBackground(new java.awt.Color(54, 70, 78));
        jPanel12.setToolTipText("Eliminar elemento da fatura");
        jPanel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_delete_sign_40px_1.png"))); // NOI18N
        jPanel12.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Eliminar");
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, -1));

        jPanel13.setBackground(new java.awt.Color(54, 70, 78));
        jPanel13.setToolTipText("Diminuir a quantidade");
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_minus_40px_1.png"))); // NOI18N
        jPanel13.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Diminuir");
        jPanel13.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 60, -1));

        jPanel14.setBackground(new java.awt.Color(54, 70, 78));
        jPanel14.setToolTipText("Aumentar a quantidade");
        jPanel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_plus_40px.png"))); // NOI18N
        jPanel14.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Aumentar");
        jLabel6.setToolTipText("Aumentar a quantidade");
        jPanel14.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 70, -1));

        jPanel15.setBackground(new java.awt.Color(54, 70, 78));
        jPanel15.setToolTipText("Configurações do software");
        jPanel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_client_company_40px.png"))); // NOI18N
        jPanel15.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Cliente");
        jPanel15.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 40, -1));

        jPanel4.setBackground(new java.awt.Color(54, 70, 78));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255))));

        buttonGroup1.add(jRadioButton_ticket_pequeno);
        jRadioButton_ticket_pequeno.setText("Ticket P");
        jRadioButton_ticket_pequeno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_ticket_pequenoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton_ticket);
        jRadioButton_ticket.setText("Ticket");

        buttonGroup1.add(jRadioButton_A5);
        jRadioButton_A5.setText("A5");

        buttonGroup1.add(jRadioButton_A4);
        jRadioButton_A4.setSelected(true);
        jRadioButton_A4.setText("A4");

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tamanho do");

        jLabel20.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("documento");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_ticket)
                    .addComponent(jRadioButton_A5)
                    .addComponent(jRadioButton_A4)))
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton_ticket_pequeno))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton_A4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_A5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_ticket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton_ticket_pequeno)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCenterLayout = new javax.swing.GroupLayout(jPanelCenter);
        jPanelCenter.setLayout(jPanelCenterLayout);
        jPanelCenterLayout.setHorizontalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelCenterLayout.setVerticalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelcenter2.setBackground(new java.awt.Color(0, 0, 153));
        jPanelcenter2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel_NomeDaEmp.setFont(new java.awt.Font("Calibri", 1, 30)); // NOI18N
        jLabel_NomeDaEmp.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NomeDaEmp.setText("Nome da empresa");

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NIF");

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Desconto:");

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Retenção na fonte:");

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total:");

        jLabel_nif.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel_nif.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_nif.setText("00.0");

        jLabel_Desconto.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel_Desconto.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Desconto.setText("00.0");

        jLabel_RetencaoNaFonte.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel_RetencaoNaFonte.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_RetencaoNaFonte.setText("00.0");

        jLabel_Total.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        jLabel_Total.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Total.setText("00.0");

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Cliente:");

        jLabel_NomeDoCliente.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        jLabel_NomeDoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_NomeDoCliente.setText("Não definido");

        javax.swing.GroupLayout jPanelcenter2Layout = new javax.swing.GroupLayout(jPanelcenter2);
        jPanelcenter2.setLayout(jPanelcenter2Layout);
        jPanelcenter2Layout.setHorizontalGroup(
            jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelcenter2Layout.createSequentialGroup()
                .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanelcenter2Layout.createSequentialGroup()
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel_NomeDoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanelcenter2Layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel_Total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanelcenter2Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel_RetencaoNaFonte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelcenter2Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel_Desconto, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel_nif, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel_NomeDaEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelcenter2Layout.setVerticalGroup(
            jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelcenter2Layout.createSequentialGroup()
                .addComponent(jLabel_NomeDaEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_nif, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel_Desconto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel_RetencaoNaFonte))
                .addGap(53, 53, 53)
                .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel_Total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelcenter2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel_NomeDoCliente))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanelEsc.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEsc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroupTipo.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Serviços");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroupTipo.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jRadioButton2.setText("Produtos");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(54, 70, 78));
        jSeparator1.setForeground(new java.awt.Color(54, 70, 78));

        jPanelPRODUTO_SERVICO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField_Desconto.setText("00.0");
        jTextField_Desconto.setToolTipText("Valor total do desconto");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel3.setText("Desconto fixo (KZ)");
        jLabel3.setToolTipText("Valor total do desconto");

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jTableMAteria.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jTableMAteria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Descrição", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMAteria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMAteriaMouseClicked(evt);
            }
        });
        jTableMAteria.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTableMAteriaPropertyChange(evt);
            }
        });
        jTableMAteria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableMAteriaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTableMAteria);

        jLabel_TítuloDaTabela.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel_TítuloDaTabela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_TítuloDaTabela.setText("Serviços");

        jComboBox_armazem.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jComboBox_armazem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_armazem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_armazemItemStateChanged(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel19.setText("Armazem");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_TítuloDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_armazem, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_armazem))
                .addGap(13, 13, 13)
                .addComponent(jLabel_TítuloDaTabela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTextField1.setToolTipText("");
        jTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField1PropertyChange(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel18.setText("Pesquisa");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel4.setText("Quantidade");

        jTextField_QNT.setText("00.0");
        jTextField_QNT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_QNTKeyPressed(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton16.setText("Buscar");
        jButton16.setToolTipText("Anexar o produto a fatura");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jButton16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton16KeyPressed(evt);
            }
        });

        jButton17.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton17.setText("Descontar");
        jButton17.setToolTipText("Anexar o produto a fatura");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jButton17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton17KeyPressed(evt);
            }
        });

        jButton18.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton18.setText("Aplicar");
        jButton18.setToolTipText("Anexar o produto a fatura");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelPRODUTO_SERVICOLayout = new javax.swing.GroupLayout(jPanelPRODUTO_SERVICO);
        jPanelPRODUTO_SERVICO.setLayout(jPanelPRODUTO_SERVICOLayout);
        jPanelPRODUTO_SERVICOLayout.setHorizontalGroup(
            jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                        .addGroup(jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                                .addComponent(jTextField_Desconto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel18)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                                    .addComponent(jTextField_QNT, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 60, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelPRODUTO_SERVICOLayout.setVerticalGroup(
            jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextField_Desconto)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPRODUTO_SERVICOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelPRODUTO_SERVICOLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextField_QNT)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanelEscLayout = new javax.swing.GroupLayout(jPanelEsc);
        jPanelEsc.setLayout(jPanelEscLayout);
        jPanelEscLayout.setHorizontalGroup(
            jPanelEscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEscLayout.createSequentialGroup()
                .addGroup(jPanelEscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEscLayout.createSequentialGroup()
                        .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(162, 162, 162)
                        .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelEscLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelEscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelPRODUTO_SERVICO, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanelEscLayout.setVerticalGroup(
            jPanelEscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEscLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelPRODUTO_SERVICO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jInternalFrame2.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrame2.setClosable(true);
        jInternalFrame2.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame2.setPreferredSize(new java.awt.Dimension(776, 458));
        jInternalFrame2.setVisible(false);
        jInternalFrame2.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrame2InternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("2");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("3");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("1");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setText("6");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("7");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("8");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("9");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("0");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("OK");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_left_arrow_32px.png"))); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton15.setText(".");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setBorder(new javax.swing.border.MatteBorder(null));

        jTextFieldPagamento.setEditable(false);
        jTextFieldPagamento.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPagamento.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldPagamento.setText("0.00");

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPagamento)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane1.setLayer(jTextFieldPagamento, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane3.setBorder(new javax.swing.border.MatteBorder(null));

        jTextFieldPagamento2.setEditable(false);
        jTextFieldPagamento2.setBackground(new java.awt.Color(255, 255, 0));
        jTextFieldPagamento2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextFieldPagamento2.setText("0.00");
        jTextFieldPagamento2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPagamento2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPagamento2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldPagamento2, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane3.setLayer(jTextFieldPagamento2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Forma de pagamento"));
        jPanel2.setToolTipText("Seleciona uma forma de pagamento.");

        jTable_FormaPagamento.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable_FormaPagamento);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1))
                .addGap(27, 27, 27)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane3)))
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame1.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrame1.setClosable(true);
        jInternalFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setVisible(false);
        jInternalFrame1.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrame1InternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel_Utilizadores.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Utilizadores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        jTable_cliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Nome do cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_clienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_cliente);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addGap(0, 0, 0))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel22.setToolTipText("Ver os dados completos dos utilizadores");
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

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel27.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Filtrar por");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel1.setText("Nome:");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel26.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Ordenar por");

        jComboBox1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nome", "Idade", "ID", "Perfil" }));
        jComboBox1.setToolTipText("");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_UtilizadoresLayout = new javax.swing.GroupLayout(jPanel_Utilizadores);
        jPanel_Utilizadores.setLayout(jPanel_UtilizadoresLayout);
        jPanel_UtilizadoresLayout.setHorizontalGroup(
            jPanel_UtilizadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_UtilizadoresLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_UtilizadoresLayout.setVerticalGroup(
            jPanel_UtilizadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_UtilizadoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_UtilizadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel21.setBackground(new java.awt.Color(27, 167, 125));

        jLabel22.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Clientes");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel_Utilizadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jPanel_Utilizadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTableContentFact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableContentFact.setColumnSelectionAllowed(true);
        jTableContentFact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableContentFactMouseClicked(evt);
            }
        });
        jTableContentFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableContentFactKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableContentFact);
        jTableContentFact.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jButton19.setBackground(new java.awt.Color(41, 70, 46));
        jButton19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Cancelar");
        jButton19.setBorderPainted(false);
        jButton19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton19MouseExited(evt);
            }
        });
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButtonGerarFatura.setBackground(new java.awt.Color(56, 65, 84));
        jButtonGerarFatura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonGerarFatura.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGerarFatura.setText("Concluir");
        jButtonGerarFatura.setBorderPainted(false);
        jButtonGerarFatura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonGerarFatura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonGerarFaturaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonGerarFaturaMouseExited(evt);
            }
        });
        jButtonGerarFatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarFaturaActionPerformed(evt);
            }
        });
        jButtonGerarFatura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonGerarFaturaKeyPressed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(217, 81, 51));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Fechar");
        jButton7.setBorderPainted(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton7MouseExited(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelcenter2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButtonGerarFatura, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(3, 3, 3)))
                .addGap(0, 0, 0)
                .addComponent(jPanelEsc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addComponent(jInternalFrame2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(924, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(167, 167, 167)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGap(168, 168, 168)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTopMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelcenter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonGerarFatura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton19)
                        .addGap(400, 400, 400))
                    .addComponent(jPanelEsc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(62, 62, 62)
                    .addComponent(jInternalFrame2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(998, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(156, 156, 156)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGap(156, 156, 156)))
        );

        try {
            jInternalFrame2.setMaximum(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGerarFaturaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGerarFaturaMouseEntered
        this.jButtonGerarFatura.setBackground(new Color(235, 235, 235));
        this.jButtonGerarFatura.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButtonGerarFaturaMouseEntered

    private void jButtonGerarFaturaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGerarFaturaMouseExited
        this.jButtonGerarFatura.setBackground(new Color(56, 65, 84));
        this.jButtonGerarFatura.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButtonGerarFaturaMouseExited

    private void jButton7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseEntered
        this.jButton7.setBackground(new Color(235, 235, 235));
        this.jButton7.setForeground(new Color(217, 81, 51));

    }//GEN-LAST:event_jButton7MouseEntered

    private void jButton7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseExited
        this.jButton7.setBackground(new Color(217, 81, 51));
        this.jButton7.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton7MouseExited

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        int lin;
        lin = jTableContentFact.getSelectedRow();
        if (lin != -1) {
            String cod = this.jTableContentFact.getValueAt(lin, 0).toString();
            String tipo = this.jTableContentFact.getValueAt(lin, 1).toString();
            String descricao = this.jTableContentFact.getValueAt(lin, 2).toString();
            int result = JOptionPane.showConfirmDialog(rootPane, "Deseja excluir do documento o " + tipo.toLowerCase() + " com descrição " + descricao + "?\n", null, JOptionPane.YES_NO_OPTION);
            if (JOptionPane.YES_OPTION == result) {
                AgregarItem(0);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado na tabela.", null, JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jPanel12MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked

        int lin = jTableContentFact.getSelectedRow();
        if (lin > -1) {
            double qnt = Double.parseDouble(this.jTableContentFact.getValueAt(lin, 7).toString());

            if (qnt > 1) {
                AgregarItem(qnt--);
            } else {
                JOptionPane.showMessageDialog(null, "Impossível reduzir a quantidade. \nQuantidade = 1", null, JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado na tabela.", null, JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        int lin = jTableContentFact.getSelectedRow();
        if (lin > -1) {
            double qnt = Double.parseDouble(this.jTableContentFact.getValueAt(lin, 7).toString());
            if (qnt > 0) {
                AgregarItem(qnt++);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado na tabela.", null, JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        jPanelTopMenu.setVisible(false);
        this.jInternalFrame2.setVisible(false);
        this.jPanelCenter.setVisible(false);
        jPanelcenter2.setVisible(false);
//        jPanelDir.setVisible(false);
        jTableContentFact.setVisible(false);
        jScrollPane1.setVisible(false);
        jButtonGerarFatura.setVisible(false);
        jButton19.setVisible(false);
        jButton7.setVisible(false);
        jPanelEsc.setVisible(false);
//        jTableContentFact.setVisible(false);
        PreencherTableCliente("select * from cliente order by idcliente");
        this.jInternalFrame1.setVisible(true);
        jTableContentFact.setVisible(false);
        jScrollPane1.setVisible(false);

    }//GEN-LAST:event_jPanel15MouseClicked

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        if (!jLabel_tipo_de_factura.getText().equals("Proforma")) {
            this.jLabel_TítuloDaTabela.setText("Serviços");
            PreencherTable("select idservico, descricao, preco_unitario, imposto_iva, motivo,preco from servico order by idservico");
        }
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        this.jLabel_TítuloDaTabela.setText("Produtos");
        PreencherTableProduto("select * from produto");
        jTextField1.requestFocus();
//        PreencherTable("select codigo, descricao, preco,imposto_iva, motivo from produto where existencia > 0 order by idproduto");
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jTableMAteriaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTableMAteriaPropertyChange

    }//GEN-LAST:event_jTableMAteriaPropertyChange

    private void jTableMAteriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMAteriaMouseClicked
        AgregarItem();

    }//GEN-LAST:event_jTableMAteriaMouseClicked

    private void jTableContentFactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableContentFactMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableContentFactMouseClicked

    private void jButtonGerarFaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarFaturaActionPerformed
        if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
            PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
            MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
            MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
            MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
            MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
            MF.setTotal_doIVA(imposto_Total);
            MF.setSubTotal(subTotal + MF.getDesconto());
            MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
            MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
            {
                if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                    Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                    this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                    this.jInternalFrame2.setVisible(true);
                    this.jInternalFrame2.setVisible(false);
                    this.jInternalFrame2.setVisible(true);

                    this.jInternalFrame1.setVisible(false);
                    this.jPanelCenter.setVisible(false);
                    jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                    jButtonGerarFatura.setVisible(false);
                    jButton19.setVisible(false);
                    jButton7.setVisible(false);
                    jPanelEsc.setVisible(false);
                    jPanelEsc.setVisible(false);
                    jPanelTopMenu.setVisible(false);
                    jTableContentFact.setVisible(false);
                    jScrollPane1.setVisible(false);
//                    jTableContentFact
                    this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                    this.jTextFieldPagamento.setText(this.jLabel_Total.getText());

                }
                if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                    JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                    this.createFatura(MU, ME, MC);
                    MF.setEntrada(0.00);
                    insertVenda();
                    Print();
                    restart();

                } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                    this.createFaturaProforma(MU, ME, MC);
                    JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                    MF.setEntrada(0.00);
                    Print();
                    restart();

                } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                    int cont = 0;
                    for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                        if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                            cont++;
                            break;
                        }
                    }

                    if (cont > 0) {
                        this.createFaturaOcamento(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jButtonGerarFaturaActionPerformed

    private void jInternalFrame2InternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrame2InternalFrameClosing
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);

        this.jInternalFrame2.setVisible(false);
        this.jInternalFrame1.setVisible(false);
        this.jPanelCenter.setVisible(true);
        jPanelcenter2.setVisible(true);
//        jPanelDir.setVisible(true);
        jTableContentFact.setVisible(true);
        jScrollPane1.setVisible(true);
        jButtonGerarFatura.setVisible(true);
        jButton19.setVisible(true);
        jButton7.setVisible(true);

        jPanelEsc.setVisible(true);
    }//GEN-LAST:event_jInternalFrame2InternalFrameClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("2");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "2");
        }
//        jTextFieldPagamento.setText(this.Chang(this.jTextField3.getText()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("3");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "3");
        }
//        jTextFieldPagamento.setText(this.Chang(this.jTextField3.getText()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("1");

        } else {

            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "1");

        }
//        jTextFieldPagamento.setText(this.Chang(this.jTextField3.getText()));
//        jTextFieldPagamento.setText(this.Chang(this.jTextField3.getText()));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("4");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "4");
        }
//        jTextFieldPagamento.setText(this.Chang(this.jTextField3.getText()));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("5");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "5");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("6");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "6");
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("7");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "7");
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("8");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "8");
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("9");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "9");
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("0.00");
        } else {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + "0");
        }

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        this.ValorPago = Double.parseDouble(jTextFieldPagamento.getText());
        MF.setPagamento(ValorPago);
        SelectedForma = new ArrayList<>();
        MF.setTroco(ValorPago - Double.parseDouble(this.jLabel_Total.getText()));
        int selected = jTable_FormaPagamento.getSelectedRow();

        if (MF.getTroco() < 0) {
            JOptionPane.showMessageDialog(rootPane, "O valor recebebido é inferior ao Total.\n" + liga.Chang(ValorPago) + "<" + liga.Chang(this.jLabel_Total.getText()), "Valor inválido.", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                this.createFaturaRecibo(MU, ME, MC);
                liga.conexao();

                Double entrada = ValorPago - MF.getTroco();
                String forma = "numerario";
                PreparedStatement pst = liga.con.prepareStatement("UPDATE caixa set entrada = entrada + " + entrada + ", saldo = saldo + " + entrada + ", idempresa =" + ME.getIdempresa());

                if (selected > -1) {
                    forma = "bancos";
                    String ibam = jTable_FormaPagamento.getValueAt(selected, 3).toString();
                    String banco = jTable_FormaPagamento.getValueAt(selected, 0).toString();
                    String conta = jTable_FormaPagamento.getValueAt(selected, 2).toString();
                    SelectedForma.add(banco);
                    SelectedForma.add(" IBAN: " + ibam);
                    SelectedForma.add(jTable_FormaPagamento.getValueAt(selected, 1).toString());
                    SelectedForma.add(conta);
                    pst = liga.con.prepareStatement("UPDATE conta set saldo = saldo + " + entrada + " "
                            + " where "
                            + " nome_banco = '" + banco + "' AND "
                            + " ibam = '" + ibam + "' AND "
                            + " numero_da_conta = '" + conta + "' ");
//                    System.out.println("UPDATE conta set saldo = saldo + " + entrada + " "
//                            + " where "+" (abreviacao = '" +banco+"' OR "
//                            + " nome_banco = '" +banco+"') AND "
//                            + " ibam = '"+ibam+"' AND "
//                            + " numero_da_conta = '"+ conta+"' ");

                } else {
                    SelectedForma.add(" ");
                    SelectedForma.add(" ");
                    SelectedForma.add(" ");
                    SelectedForma.add(" ");
                    forma = "numerario";
                    pst = liga.con.prepareStatement("UPDATE caixa set entrada = entrada + " + entrada + ", saldo = saldo + " + entrada + " where idempresa =" + ME.getIdempresa());
                }

                boolean execute = pst.execute();

                pst = liga.con.prepareStatement("UPDATE turno set " + forma + " = " + forma + " + " + entrada + ", saldo_atual = saldo_atual + " + entrada + ", fatura_recibo = fatura_recibo + " + entrada + " WHERE idturno =" + this.id_turno);
                pst.execute();

                liga.deconecta();

                Print();
                insertVenda();
                JOptionPane.showMessageDialog(rootPane, "Factura recibo elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);

                this.jInternalFrame1.setVisible(false);
                this.jPanelCenter.setVisible(true);
                jPanelcenter2.setVisible(true);
//                jPanelDir.setVisible(true);
//                jTableContentFact.setVisible(false);
                jTableContentFact.setVisible(true);
                jScrollPane1.setVisible(true);
                jButtonGerarFatura.setVisible(true);
                jButton19.setVisible(true);
                jButton7.setVisible(true);
                jPanelEsc.setVisible(true);
                jPanelTopMenu.setVisible(true);

                Fatura fatura = new Fatura(this.jLabel_tipo_de_factura.getText(), this.id, this.perfil, this.liga.getCaminho());
                fatura.setVisible(true);
                this.dispose();
            } catch (SQLException ex) {
                Logger.getLogger(Fatura.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        jTextFieldPagamento.setText(jTextFieldPagamento.getText().substring(0, jTextFieldPagamento.getText().length() - 1));
        if (jTextFieldPagamento.getText().isEmpty()) {
            jTextFieldPagamento.setText("0.00");
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTable_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_clienteMouseClicked

        int result = JOptionPane.showConfirmDialog(rootPane, "Gerar fatura para o cliente " + this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 1) + "?", null, JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION == result) {
            this.MC.setIdCliente(Integer.parseUnsignedInt(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 0).toString()));
            this.MC.setDesignacao(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 1).toString());
            String tipo = "";
            if (this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 4).toString() != null) {
                this.MC.setTipo_de_pessoa(tipo);
            }
            if (this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 4).toString().equals("Física")) {
                this.MC.setBi(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 2).toString());
            } else {
                this.MC.setBi("");
            }

            this.MC.setNif(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 3).toString());
            if (this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 4).toString().equals("Física")) {
                this.MC.setSexo(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 5).toString());
            } else {
                this.MC.setSexo("");
            }
            this.MC.setTelefone(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 6).toString());
            this.MC.setEmail(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 7).toString());
            this.MC.setEndereco(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 8).toString());
            this.MC.setCidade(this.jTable_cliente.getValueAt(this.jTable_cliente.getSelectedRow(), 9).toString());
            this.jLabel_NomeDoCliente.setText(MC.getDesignacao());

            this.jInternalFrame1.setVisible(false);
            this.jPanelCenter.setVisible(true);
            jPanelcenter2.setVisible(true);
//            jPanelDir.setVisible(true);
            jTableContentFact.setVisible(true);
            jScrollPane1.setVisible(true);
            jButtonGerarFatura.setVisible(true);
            jButton19.setVisible(true);
            jButton7.setVisible(true);
            jPanelEsc.setVisible(true);

        } else {
            MC.setIdCliente(1);
            this.jLabel_NomeDoCliente.setText("CONSUMIDOR FINAL");
            this.MC.setDesignacao("CONSUMIDOR FINAL");
            this.MC.setTipo_de_pessoa("");
            this.MC.setBi("CONSUMIDOR FINAL");
            this.MC.setNif("CONSUMIDOR FINAL");
            this.MC.setSexo("");

            this.MC.setTelefone("DESCONHECIDO");
            this.MC.setEmail("DESCONHECIDO");
            this.MC.setEndereco("DESCONHECIDO");
            this.MC.setCidade("DESCONHECIDO");
            this.jLabel_NomeDoCliente.setText(MC.getDesignacao());
        }
    }//GEN-LAST:event_jTable_clienteMouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        JOptionPane.showMessageDialog(null, "Actualizar");
        try {
            this.jTable_cliente.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("sdada"), new MessageFormat(" sdasdas"));

        } catch (java.awt.print.PrinterException ex) {
            System.out.println("" + ex);
        }
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed

    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jInternalFrame1InternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrame1InternalFrameClosing
        this.jInternalFrame1.setVisible(false);
        this.jPanelCenter.setVisible(true);
        jPanelcenter2.setVisible(true);
//        jPanelDir.setVisible(true);
        jTableContentFact.setVisible(true);
        jScrollPane1.setVisible(true);
        jButtonGerarFatura.setVisible(true);
        jButton19.setVisible(true);
        jButton7.setVisible(true);
        jPanelEsc.setVisible(true);
    }//GEN-LAST:event_jInternalFrame1InternalFrameClosing

    private void jTextFieldPagamento2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPagamento2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPagamento2ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        Double Valor = Double.parseDouble(this.jTextFieldPagamento.getText());
        if (Valor == 0) {
            jTextFieldPagamento.setText("0.00");
        } else if (!this.jTextFieldPagamento.getText().contains(".")) {
            jTextFieldPagamento.setText(jTextFieldPagamento.getText() + ".");
            QuantPontos++;
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            this.jRadioButton2.setSelected(true);
            this.jLabel_TítuloDaTabela.setText("Produtos");
            PreencherTableProduto("select * from produto WHERE "
                    + "descricao ILIKE '%" + this.jTextField1.getText() + "%' \n"
                    + "OR codigo_de_barra ILIKE '%" + this.jTextField1.getText() + "%' \n"
                    + "OR codigo ILIKE '%" + this.jTextField1.getText() + "%' \n"
                    + "OR marca ILIKE '%" + this.jTextField1.getText() + "%'  \n"
                    + "OR origem ILIKE '%" + this.jTextField1.getText() + "%' ; ");
            jTableMAteria.requestFocus();
        }
//        System.out.println(("select codigo, descricao, preco,imposto_iva, motivo from produto WHERE codigo_de_barra = '" + this.jTextField1.getText() + "'"));

    }//GEN-LAST:event_jButton16ActionPerformed

    private void jTextField1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField1PropertyChange
//        if (!jTextField1.getText().isEmpty()) {
//            TakeCliente("select codigo, descricao, preco,imposto_iva, motivo from produto WHERE codigo_de_barra ILIKE '%---%' \n"
//                    + "OR codigo ILIKE '%" + this.jTextField1.getText() + "%' \n"
//                    + "OR marca ILIKE '%" + this.jTextField1.getText() + "%'  \n"
//                    + "OR origem ILIKE '%" + this.jTextField1.getText() + "%' \n"
//                    + "OR preco = " + this.jTextField1.getText() + "; ");
//        }
//        System.out.println("fff");
    }//GEN-LAST:event_jTextField1PropertyChange

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        this.jLabel_Total.setText(Double.toString(Double.parseDouble(jLabel_Total.getText()) - Double.parseDouble(jTextField_Desconto.getText())));
        this.jLabel_Desconto.setText(jTextField_Desconto.getText());
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
//        Double x = Double.parseDouble(jLabel_Total.getText());
//        double y = Double.parseDouble(jTextField_QNT.getText());
//        this.jLabel_Total.setText(Double.toString(x + y));
//        this.jLabel_RetencaoNaFonte.setText(jTextField_QNT.getText());
//        double qnt = Double.parseDouble(jTextField_QNT.getText());
//        if (qnt > 0) {
//            AgregarItem(qnt);
//        }

        double qnt = Double.parseDouble(jTextField_QNT.getText());
        if (qnt > 0) {
            AgregarItem(qnt);
            jTableContentFact.requestFocus();
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton19MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19MouseEntered

    private void jButton19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton19MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19MouseExited

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        int result = JOptionPane.showConfirmDialog(rootPane, "Deseja Cancelar a emissão desse documento?", null, JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION == result) {
            JOptionPane.showMessageDialog(null, "Documento cancelado com sucesso.", "Cancelado.", JOptionPane.INFORMATION_MESSAGE);
            restart();

        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jComboBox_armazemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_armazemItemStateChanged
        if (jComboBox_armazem.getItemCount() > 0 && this.Id_armazens.size() > 0) {
            PreencherTableProduto("select * from produto");
            this.jRadioButton2.setSelected(true);
            this.jLabel_TítuloDaTabela.setText("Produtos");
        }
    }//GEN-LAST:event_jComboBox_armazemItemStateChanged

    private void jRadioButton_ticket_pequenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_ticket_pequenoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton_ticket_pequenoActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == evt.VK_CONTROL) {
            // inicioco

            if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
                PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
                MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
                MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
                MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
                MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
                MF.setTotal_doIVA(imposto_Total);
                MF.setSubTotal(subTotal + MF.getDesconto());
                MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
                MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
                {
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                        Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                        this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                        this.jInternalFrame2.setVisible(true);
                        this.jInternalFrame2.setVisible(false);
                        this.jInternalFrame2.setVisible(true);

                        this.jInternalFrame1.setVisible(false);
                        this.jPanelCenter.setVisible(false);
                        jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                        jButtonGerarFatura.setVisible(false);
                        jButton19.setVisible(false);
                        jButton7.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelTopMenu.setVisible(false);
                        jTableContentFact.setVisible(false);
                        jScrollPane1.setVisible(false);
//                    jTableContentFact
                        this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                        this.jTextFieldPagamento.setText(this.jLabel_Total.getText());
//                    liga.conexao();
//                    liga.executeSql("select idfatura_recibo from recibo");
//                    if (liga.rs.last()) {
//                        this.MF.setIdfactra(liga.rs.getInt(1)+1);
//                    }
//                    liga.deconecta();
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
//                }
                    }
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                        JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        this.createFatura(MU, ME, MC);
                        MF.setEntrada(0.00);
                        insertVenda();
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                        this.createFaturaProforma(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                        int cont = 0;
                        for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                            if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                                cont++;
                                break;
                            }
                        }

                        if (cont > 0) {
                            this.createFaturaOcamento(MU, ME, MC);
                            JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                            MF.setEntrada(0.00);
                            Print();
                            restart();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            //FIM
        } else if (evt.getKeyCode() == evt.VK_ENTER) {

            this.jRadioButton2.setSelected(true);
            this.jLabel_TítuloDaTabela.setText("Produtos");
            PreencherTableProduto("select * from produto WHERE "
                    + "descricao ILIKE '%" + this.jTextField1.getText() + "%' \n"
                    + "OR codigo_de_barra ILIKE '%" + this.jTextField1.getText() + "%' \n"
                    + "OR codigo ILIKE '%" + this.jTextField1.getText() + "%' \n"
                    + "OR marca ILIKE '%" + this.jTextField1.getText() + "%'  \n"
                    + "OR origem ILIKE '%" + this.jTextField1.getText() + "%' ; ");
            jTableMAteria.requestFocus();
        } else {
            jTextField1.requestFocus();
        }


    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTableMAteriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableMAteriaKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            AgregarItem();
//            int line = this.dados.size()-1;
            jTableMAteria.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_SHIFT) {
            jTextField1.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_TAB) {
            jTableContentFact.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_CONTROL) {
            // inicio
            if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
                PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
                MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
                MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
                MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
                MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
                MF.setTotal_doIVA(imposto_Total);
                MF.setSubTotal(subTotal + MF.getDesconto());
                MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
                MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
                {
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                        Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                        this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                        this.jInternalFrame2.setVisible(true);
                        this.jInternalFrame2.setVisible(false);
                        this.jInternalFrame2.setVisible(true);

                        this.jInternalFrame1.setVisible(false);
                        this.jPanelCenter.setVisible(false);
                        jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                        jButtonGerarFatura.setVisible(false);
                        jButton19.setVisible(false);
                        jButton7.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelTopMenu.setVisible(false);
                        jTableContentFact.setVisible(false);
                        jScrollPane1.setVisible(false);
//                    jTableContentFact
                        this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                        this.jTextFieldPagamento.setText(this.jLabel_Total.getText());
//                    liga.conexao();
//                    liga.executeSql("select idfatura_recibo from recibo");
//                    if (liga.rs.last()) {
//                        this.MF.setIdfactra(liga.rs.getInt(1)+1);
//                    }
//                    liga.deconecta();
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
//                }
                    }
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                        JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        this.createFatura(MU, ME, MC);
                        MF.setEntrada(0.00);
                        insertVenda();
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                        this.createFaturaProforma(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                        int cont = 0;
                        for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                            if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                                cont++;
                                break;
                            }
                        }

                        if (cont > 0) {
                            this.createFaturaOcamento(MU, ME, MC);
                            JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                            MF.setEntrada(0.00);
                            Print();
                            restart();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            // fim
        }
    }//GEN-LAST:event_jTableMAteriaKeyPressed

    private void jTextField_QNTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_QNTKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            double qnt = Double.parseDouble(jTextField_QNT.getText());
            if (qnt > 0) {
                AgregarItem(qnt);
                jTableContentFact.requestFocus();
            }
        } else if (evt.getKeyCode() == evt.VK_SHIFT) {
            jTextField1.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_TAB) {
            jTableMAteria.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_CONTROL) {
            // inicio
            if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
                PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
                MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
                MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
                MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
                MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
                MF.setTotal_doIVA(imposto_Total);
                MF.setSubTotal(subTotal + MF.getDesconto());
                MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
                MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
                {
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                        Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                        this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                        this.jInternalFrame2.setVisible(true);
                        this.jInternalFrame2.setVisible(false);
                        this.jInternalFrame2.setVisible(true);

                        this.jInternalFrame1.setVisible(false);
                        this.jPanelCenter.setVisible(false);
                        jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                        jButtonGerarFatura.setVisible(false);
                        jButton19.setVisible(false);
                        jButton7.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelTopMenu.setVisible(false);
                        jTableContentFact.setVisible(false);
                        jScrollPane1.setVisible(false);
//                    jTableContentFact
                        this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                        this.jTextFieldPagamento.setText(this.jLabel_Total.getText());
//                    liga.conexao();
//                    liga.executeSql("select idfatura_recibo from recibo");
//                    if (liga.rs.last()) {
//                        this.MF.setIdfactra(liga.rs.getInt(1)+1);
//                    }
//                    liga.deconecta();
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
//                }
                    }
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                        JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        this.createFatura(MU, ME, MC);
                        MF.setEntrada(0.00);
                        insertVenda();
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                        this.createFaturaProforma(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                        int cont = 0;
                        for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                            if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                                cont++;
                                break;
                            }
                        }

                        if (cont > 0) {
                            this.createFaturaOcamento(MU, ME, MC);
                            JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                            MF.setEntrada(0.00);
                            Print();
                            restart();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            // fim
        }
    }//GEN-LAST:event_jTextField_QNTKeyPressed

    private void jTableContentFactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableContentFactKeyPressed
        if (evt.getKeyCode() == evt.VK_TAB) {
            jTableMAteria.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_ENTER) {
            jTextField_QNT.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_SHIFT) {
            jTextField1.requestFocus();
        } else if (evt.getKeyCode() == evt.VK_CONTROL) {
            //inicio
            if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
                PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
                MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
                MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
                MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
                MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
                MF.setTotal_doIVA(imposto_Total);
                MF.setSubTotal(subTotal + MF.getDesconto());
                MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
                MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
                {
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                        Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                        this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                        this.jInternalFrame2.setVisible(true);
                        this.jInternalFrame2.setVisible(false);
                        this.jInternalFrame2.setVisible(true);

                        this.jInternalFrame1.setVisible(false);
                        this.jPanelCenter.setVisible(false);
                        jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                        jButtonGerarFatura.setVisible(false);
                        jButton19.setVisible(false);
                        jButton7.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelTopMenu.setVisible(false);
                        jTableContentFact.setVisible(false);
                        jScrollPane1.setVisible(false);
//                    jTableContentFact
                        this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                        this.jTextFieldPagamento.setText(this.jLabel_Total.getText());
//                    liga.conexao();
//                    liga.executeSql("select idfatura_recibo from recibo");
//                    if (liga.rs.last()) {
//                        this.MF.setIdfactra(liga.rs.getInt(1)+1);
//                    }
//                    liga.deconecta();
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
//                }
                    }
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                        JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        this.createFatura(MU, ME, MC);
                        MF.setEntrada(0.00);
                        insertVenda();
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                        this.createFaturaProforma(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                        int cont = 0;
                        for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                            if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                                cont++;
                                break;
                            }
                        }

                        if (cont > 0) {
                            this.createFaturaOcamento(MU, ME, MC);
                            JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                            MF.setEntrada(0.00);
                            Print();
                            restart();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            //fim
        }
    }//GEN-LAST:event_jTableContentFactKeyPressed

    private void jButtonGerarFaturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonGerarFaturaKeyPressed
        if (evt.getKeyCode() == evt.VK_CONTROL) {
            if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
                PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
                MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
                MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
                MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
                MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
                MF.setTotal_doIVA(imposto_Total);
                MF.setSubTotal(subTotal + MF.getDesconto());
                MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
                MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
                {
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                        Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                        this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                        this.jInternalFrame2.setVisible(true);
                        this.jInternalFrame2.setVisible(false);
                        this.jInternalFrame2.setVisible(true);

                        this.jInternalFrame1.setVisible(false);
                        this.jPanelCenter.setVisible(false);
                        jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                        jButtonGerarFatura.setVisible(false);
                        jButton19.setVisible(false);
                        jButton7.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelEsc.setVisible(false);
                        jPanelTopMenu.setVisible(false);
                        jTableContentFact.setVisible(false);
                        jScrollPane1.setVisible(false);
//                    jTableContentFact
                        this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                        this.jTextFieldPagamento.setText(this.jLabel_Total.getText());
//                    liga.conexao();
//                    liga.executeSql("select idfatura_recibo from recibo");
//                    if (liga.rs.last()) {
//                        this.MF.setIdfactra(liga.rs.getInt(1)+1);
//                    }
//                    liga.deconecta();
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
//                }
                    }
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                        JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        this.createFatura(MU, ME, MC);
                        MF.setEntrada(0.00);
                        insertVenda();
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                        this.createFaturaProforma(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();

                    } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                        int cont = 0;
                        for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                            if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                                cont++;
                                break;
                            }
                        }

                        if (cont > 0) {
                            this.createFaturaOcamento(MU, ME, MC);
                            JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                            MF.setEntrada(0.00);
                            Print();
                            restart();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                        }
                    }

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonGerarFaturaKeyPressed

    private void jButton16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton16KeyPressed
        if (this.id_turno != -1 && this.jTableContentFact.getRowCount() > 0) {
            PreencherTabelaFormaPagamento("Select conta.abreviacao, conta.numero_da_conta, conta.ibam, forma_de_pagamento.descricao from conta, forma_de_pagamento WHERE conta.idconta=forma_de_pagamento.idconta order by conta.abreviacao, forma_de_pagamento.descricao");

//        if (this.createFatura(this.MU, this.ME, this.MC))
            MF.setDesconto(Double.parseDouble(this.jLabel_Desconto.getText()));
            MF.setTotal(Double.parseDouble(this.jLabel_Total.getText()));
            MF.setRetencao_na_fonte(Double.parseDouble(jLabel_RetencaoNaFonte.getText()));
            MF.setTaxa(this.percentImpostoIVA);
//            System.out.println("IVA TOTAL: "+MF.setTotal_doIVA());
            MF.setTotal_doIVA(imposto_Total);
            MF.setSubTotal(subTotal + MF.getDesconto());
            MF.setArmazem(this.jComboBox_armazem.getSelectedItem().toString());
            MF.setLocalizacao_armazem(this.Localizacao_armazens.get(jComboBox_armazem.getSelectedIndex()));
            {
                if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
//                try {

                    Toolkit T = Toolkit.getDefaultToolkit();
//                    this.setSize(T.getScreenSize());
                    this.setSize((int) (T.getScreenSize().width * 0.68), (int) (T.getScreenSize().height * 0.50));

                    this.jInternalFrame2.setVisible(true);
                    this.jInternalFrame2.setVisible(false);
                    this.jInternalFrame2.setVisible(true);

                    this.jInternalFrame1.setVisible(false);
                    this.jPanelCenter.setVisible(false);
                    jPanelcenter2.setVisible(false);
//                    jPanelDir.setVisible(false);
//                    jTableContentFact.setVisible(false);
                    jButtonGerarFatura.setVisible(false);
                    jButton19.setVisible(false);
                    jButton7.setVisible(false);
                    jPanelEsc.setVisible(false);
                    jPanelEsc.setVisible(false);
                    jPanelTopMenu.setVisible(false);
                    jTableContentFact.setVisible(false);
                    jScrollPane1.setVisible(false);
//                    jTableContentFact
                    this.jTextFieldPagamento2.setText("Total: " + this.jLabel_Total.getText());
                    this.jTextFieldPagamento.setText(this.jLabel_Total.getText());
//                    liga.conexao();
//                    liga.executeSql("select idfatura_recibo from recibo");
//                    if (liga.rs.last()) {
//                        this.MF.setIdfactra(liga.rs.getInt(1)+1);
//                    }
//                    liga.deconecta();
//                    
//                } catch (SQLException ex) {
//                    Logger.getLogger(Fatura.class.getName()).log(Level.SEVERE, null, ex);
//                }
                }
                if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                    JOptionPane.showMessageDialog(rootPane, "Factura elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                    this.createFatura(MU, ME, MC);
                    MF.setEntrada(0.00);
                    insertVenda();
                    Print();
                    restart();

                } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
//                try {
                    this.createFaturaProforma(MU, ME, MC);
                    JOptionPane.showMessageDialog(rootPane, "Proforma elaborada com sucesso.", " ", JOptionPane.INFORMATION_MESSAGE);
                    MF.setEntrada(0.00);
                    Print();
                    restart();

                } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
//                try {
                    int cont = 0;
                    for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
                        if (jTableContentFact.getValueAt(i, 1).toString().equals("Serviço")) {
                            cont++;
                            break;
                        }
                    }

                    if (cont > 0) {
                        this.createFaturaOcamento(MU, ME, MC);
                        JOptionPane.showMessageDialog(rootPane, "Orçamento elaborado com sucesso.", "Sucesso. ", JOptionPane.INFORMATION_MESSAGE);
                        MF.setEntrada(0.00);
                        Print();
                        restart();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "O orçamento deve conter pelo menos um serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);

                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Sem artigos para concluir a fatura", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton16KeyPressed

    private void jButton17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17KeyPressed

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
            java.util.logging.Logger.getLogger(Fatura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fatura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fatura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fatura.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fatura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Sexo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupTipo;
    private javax.swing.ButtonGroup buttonGrouptamanho_doc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonGerarFatura;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox jComboBox_armazem;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Desconto;
    private javax.swing.JLabel jLabel_NomeDaEmp;
    private javax.swing.JLabel jLabel_NomeDoCliente;
    private javax.swing.JLabel jLabel_RetencaoNaFonte;
    private javax.swing.JLabel jLabel_Total;
    private javax.swing.JLabel jLabel_TítuloDaTabela;
    private javax.swing.JLabel jLabel_nif;
    private javax.swing.JLabel jLabel_tipo_de_factura;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelEsc;
    private javax.swing.JPanel jPanelPRODUTO_SERVICO;
    private javax.swing.JPanel jPanelTopMenu;
    private javax.swing.JPanel jPanel_Utilizadores;
    private javax.swing.JPanel jPanelcenter2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton_A4;
    private javax.swing.JRadioButton jRadioButton_A5;
    private javax.swing.JRadioButton jRadioButton_ticket;
    private javax.swing.JRadioButton jRadioButton_ticket_pequeno;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTableContentFact;
    private javax.swing.JTable jTableMAteria;
    private javax.swing.JTable jTable_FormaPagamento;
    private javax.swing.JTable jTable_cliente;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextFieldPagamento;
    private javax.swing.JTextField jTextFieldPagamento2;
    private javax.swing.JTextField jTextField_Desconto;
    private javax.swing.JTextField jTextField_QNT;
    // End of variables declaration//GEN-END:variables
    byte[] photo = null;
    String filename = null;

    public void PreencherTable(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Codigo", "Descrição", "P. unit", "IVA", "Motivo", "P.Venda "};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString(1), liga.rs.getString(2), exte.Chang(liga.rs.getString(3)), IMPOSTO(liga.rs.getBoolean(4)), liga.rs.getString(5), exte.Chang(liga.rs.getString(6))});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados\n" + ex, "Sem serviços cadastrados.", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };

        jTableMAteria.setModel(modelo);
        jTableMAteria.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTableMAteria.getColumnModel().getColumn(0).setResizable(true);
        jTableMAteria.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTableMAteria.getColumnModel().getColumn(1).setResizable(true);
        jTableMAteria.getColumnModel().getColumn(3).setPreferredWidth(10);
        jTableMAteria.getColumnModel().getColumn(3).setResizable(true);
        jTableMAteria.getColumnModel().getColumn(4).setPreferredWidth(10);
        jTableMAteria.getColumnModel().getColumn(4).setResizable(true);
//        jTableMAteria.getColumnModel().getColumn(4).setPreferredWidth(200);
//        jTableMAteria.getColumnModel().getColumn(4).setResizable(true);

        jTableMAteria.getTableHeader().setReorderingAllowed(false);
        jTableMAteria.setAutoResizeMode(jTableMAteria.AUTO_RESIZE_ALL_COLUMNS);
        jTableMAteria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public void TakeCliente(String Sql) {
        liga.conexao();
        liga.executeSql(Sql);
        try {
            if (liga.rs.first()) {
                if (MC.getIdCliente() != -1) {
                    Double precoUnit = Double.parseDouble(liga.rs.getString("preco"));
//                System.out.println("prec "+precoUnit);
                    String descri = (liga.rs.getString("descricao"));
                    String IVA = liga.rs.getString("imposto_iva");
                    String Motivo = "  ";
                    String mot = liga.rs.getString("motivo");
                    Double taxa;
                    if (mot == null) {
                        Motivo = mot;
                        taxa = 0.0;
//                    System.out.println("Isento");

                    } else {
                        taxa = this.percentImpostoIVA / 100;
//                    System.out.println("IVA");
                    }

                    int quant = 1;
                    Double desconto = Double.parseDouble(jTextField_Desconto.getText());
                    if (desconto > 0) {
                        int resultQuest = JOptionPane.NO_OPTION;
                        if (jLabel_TítuloDaTabela.getText().equals("Serviços")) {
                            resultQuest = JOptionPane.showConfirmDialog(rootPane, "Relalizar um desconto de " + desconto + " Referente ao serviço: " + descri + "?", null, JOptionPane.YES_NO_OPTION);
                        } else if (jLabel_TítuloDaTabela.getText().equals("Produtos")) {
                            resultQuest = JOptionPane.showConfirmDialog(rootPane, "Relalizar um desconto de " + desconto + " Referente ao produto: " + descri + "?", null, JOptionPane.YES_NO_OPTION);
                        }

                        if (JOptionPane.NO_OPTION == resultQuest) {
                            desconto = 00.0;
                        }
                    }

                    Double totalInicial = ((precoUnit) * quant) - desconto;
                    Double impostoinicial = taxa * precoUnit * quant;
//                totalInicial+=impostoinicial;
                    this.imposto_Total += impostoinicial;
                    {
                        ModeloProduto modeloP = new ModeloProduto();
                        modeloP.setCodigo(liga.rs.getString("codigo"));
                        this.dados.add(new Object[]{liga.rs.getString("codigo"), "Produto", descri, precoUnit, IVA, Motivo, jTextField_QNT.getText(), quant, desconto, totalInicial + (taxa * precoUnit * quant)});
                    }
                    Double Tdesconto = Double.parseDouble(jLabel_Desconto.getText());

                    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                    };
                    jTableContentFact.setModel(modelo);

                    Tdesconto += desconto;
                    Double Total = Double.parseDouble(jLabel_Total.getText());
                    subTotal += totalInicial;
                    Total += totalInicial + impostoinicial;
                    System.out.println(Total);
                    jLabel_Total.setText(Total.toString());
                    jLabel_Desconto.setText(Tdesconto.toString());
                    Double rent = Double.parseDouble(this.jLabel_RetencaoNaFonte.getText());
                    rent += Double.parseDouble(this.jTextField_QNT.getText());
                    this.jLabel_RetencaoNaFonte.setText(Double.toString(rent));
                    jTextField_QNT.setText("00.0");
                    jTextField_Desconto.setText("00.0");
                } else {
                    JOptionPane.showMessageDialog(null, "Não podes adicionar um item na fatura sem antes selecionar um cliente.", "Nenhum cliente selecionado", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Produto não encntrado. ", null, JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();

    }

    public void PreencherTableCliente(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "Designação", "Nº do BI", "NIF", "Tipo de pessoa", "Sexo", "Telefone", "Email", "Endereço", "Cidade"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            liga.rs.first();
            do {

                dados.add(new Object[]{liga.rs.getString("idcliente"), liga.rs.getString("designacao"), liga.rs.getString("bi"), liga.rs.getString("nif"), liga.rs.getString("tipo_de_pessoa"), liga.rs.getString("sexo"),
                    liga.rs.getString("telefone"), liga.rs.getString("email"), liga.rs.getString("endereco"), liga.rs.getString("cidade")});

            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.WARNING_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTable_cliente.setModel(modelo);
        jTable_cliente.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable_cliente.getColumnModel().getColumn(0).setResizable(false);
        jTable_cliente.getColumnModel().getColumn(1).setPreferredWidth(235);
        jTable_cliente.getColumnModel().getColumn(1).setResizable(true);
        jTable_cliente.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable_cliente.getColumnModel().getColumn(2).setResizable(true);
        jTable_cliente.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable_cliente.getColumnModel().getColumn(3).setResizable(true);
        jTable_cliente.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable_cliente.getColumnModel().getColumn(4).setResizable(true);
        jTable_cliente.getColumnModel().getColumn(5).setPreferredWidth(40);
        jTable_cliente.getColumnModel().getColumn(5).setResizable(false);
        jTable_cliente.getColumnModel().getColumn(6).setPreferredWidth(70);
        jTable_cliente.getColumnModel().getColumn(6).setResizable(true);
        jTable_cliente.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable_cliente.getColumnModel().getColumn(7).setResizable(true);
        jTable_cliente.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable_cliente.getColumnModel().getColumn(8).setResizable(false);
        jTable_cliente.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTable_cliente.getColumnModel().getColumn(9).setResizable(true);

        jTable_cliente.getTableHeader().setReorderingAllowed(false);
        jTable_cliente.setAutoResizeMode(jTable_cliente.AUTO_RESIZE_ALL_COLUMNS);
//        jTable_terceirizador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public boolean AddVenda(int IDFACTURA) {
//        System.out.print(ln("ID VENDA"+IDFACTURA);

        ArrayList<ModeloVenda> Lista = new ArrayList<ModeloVenda>();

        for (int i = 0; i < jTableContentFact.getRowCount(); i++) {
            ModeloVenda MV = new ModeloVenda();
            MV.setIdFatura(IDFACTURA);
            MV.setCodigo((jTableContentFact.getValueAt(i, 0).toString()));
            MV.setTipo(jTableContentFact.getValueAt(i, 1).toString());
            MV.setDescricao(jTableContentFact.getValueAt(i, 2).toString());
            MV.setPreco_unit(Double.parseDouble(jTableContentFact.getValueAt(i, 3).toString()));
            MV.setRentcao(Double.parseDouble(jTableContentFact.getValueAt(i, 6).toString()));
            MV.setQuant((int) Double.parseDouble(jTableContentFact.getValueAt(i, 7).toString()));
            MV.setDesconto(Double.parseDouble(jTableContentFact.getValueAt(i, 8).toString()));
            MV.setTotal(Double.parseDouble(jTableContentFact.getValueAt(i, 9).toString()));

            MV.setIva(jTableContentFact.getValueAt(i, 4).toString());
            MV.setMotivo(jTableContentFact.getValueAt(i, 5).toString());
            if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
                MV.setOrigem(1);
            } else if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                MV.setOrigem(2);
            } else {
                MV.setOrigem(-1);
            }

            Lista.add(MV);
        }
        liga.conexao();
        int venda = 0;
        for (ModeloVenda modeloVenda : Lista) {
            try {

                PreparedStatement pst = liga.con.prepareStatement("Insert into venda "
                        + "(idfactura, codigo, descricao, preco_unitario, quantidade, desconto, total, data, hora, tipo,iva,motivo,retencao,origem) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, modeloVenda.getIdFatura());
                pst.setString(2, modeloVenda.getCodigo());
                pst.setString(3, modeloVenda.getDescricao());
                pst.setDouble(4, modeloVenda.getPreco_unit());
                pst.setInt(5, modeloVenda.getQuant());
                pst.setDouble(6, modeloVenda.getDesconto());
                pst.setDouble(7, modeloVenda.getTotal());
                pst.setString(8, tempo.Date());
                pst.setString(9, tempo.Hours());
                pst.setString(10, modeloVenda.getTipo());
                pst.setString(11, modeloVenda.getIva());
                pst.setString(12, modeloVenda.getMotivo());
                pst.setDouble(13, modeloVenda.getRentcao());
                pst.setDouble(14, modeloVenda.getOrigem());
                pst.execute();
//                System.out.println("VERDADE " + venda++);
                return true;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Venda não realizada.", "Erro", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }
        liga.deconecta();
        return true;
    }

    private boolean createFaturaRecibo(ModeloUtilizador MU, ModeloEmpresa ME, ModeloCliente MC) {
        int LastFID = 0;
        if (MU.getIdUtilizador() > 0) {

            try {
                liga.conexao();
                Double desconto = Double.parseDouble(jLabel_Desconto.getText());
                Double retencao = Double.parseDouble(jLabel_RetencaoNaFonte.getText());;
                Double total = Double.parseDouble(jLabel_Total.getText());
                String data = tempo.Date();
                String hora = tempo.Hours();

                ModeloFatura MF = new ModeloFatura(-1);
                MF.setData(data);
                MF.setHora(hora);
                MF.setDesconto(desconto);
                MF.setMc(MC);
                MF.setMe(ME);
                MF.setMu(MU);
                MF.setRetencao_na_fonte(retencao);
                MF.setTitulo(this.jLabel_tipo_de_factura.getText());
                MF.setTotal(total);
                MF.setPagamento(this.MF.getPagamento());
                MF.setTroco(this.MF.getTroco());
                PreparedStatement pst = liga.con.prepareStatement("Insert into recibo"
                        + "(titulo_da_fatura, desconto,retencao_na_fonte, "
                        + "total,data, hora, idempresa,idutilizador,idcliente,valor_pago,troco,moeda,serie,hash, idturno,indicativo) "
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?," + id_turno + ",'" + this.ME.getIndicativo() + "')");
                pst.setString(1, MF.getTitulo());
                pst.setDouble(2, MF.getDesconto());
                pst.setDouble(3, MF.getRetencao_na_fonte());
                pst.setDouble(4, MF.getTotal());
                String data_doc = MF.getData();
                pst.setString(5, data_doc);
                String hora_doc = MF.getHora();
                pst.setString(6, hora_doc);
                pst.setInt(7, MF.getMe().getIdempresa());
                pst.setInt(8, MF.getMu().getIdUtilizador());
                pst.setInt(9, MF.getMc().getIdCliente());
                pst.setDouble(10, this.MF.getPagamento());
                pst.setDouble(11, MF.getTroco());
                pst.setString(12, MF.getModeda());
                pst.setString(13, this.ME.getSerie());

                pst.setString(14, "");

                pst.execute();
                liga.deconecta();

                liga.conexao();
                liga.executeSql("select idfatura_recibo from recibo WHERE idutilizador = " + MF.getMu().getIdUtilizador() + " ORDER BY idfatura_recibo");
                if (liga.rs.last()) {
                    LastFID = liga.rs.getInt("idfatura_recibo");
                    this.MF.setIdfactra(LastFID);
                    String Last_asisinatura = LastHash("recibo", "idfatura_recibo", LastFID);
                    if (Last_asisinatura == null) {
                        Last_asisinatura = "";
                    }
                    boolean result = AddVenda(LastFID);
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
                        //
                        String NUM = " 0";
                        if (this.MF.getIdfactra() > 9) {
                            NUM = Integer.toString(LastFID);
                        } else {
                            NUM = NUM + Integer.toString(LastFID);
                        }

                        NUM = ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
                        String hash = Assinatura.GerarAssinatura(data_doc, data_doc + " " + hora, NUM, MF.getTotal(), Last_asisinatura);

                        pst = liga.con.prepareStatement("UPDATE recibo "
                                + "SET hash= '" + hash + "' WHERE idfatura_recibo=" + LastFID);
                        pst.execute();
                        liga.deconecta();

                        //                       
                        return result;

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Fatura.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Volta entrar no sistema com credenciais válidas!", "Não és um utilizador autorizador.", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    private boolean createFaturaProforma(ModeloUtilizador MU, ModeloEmpresa ME, ModeloCliente MC) {
        int LastFID = 0;
        if (MU.getIdUtilizador() > 0) {
            try {
                liga.conexao();
                Double desconto = Double.parseDouble(jLabel_Desconto.getText());
                Double retencao = Double.parseDouble(jLabel_RetencaoNaFonte.getText());;
                Double total = Double.parseDouble(jLabel_Total.getText());
                String data = tempo.Date();
                String hora = tempo.Hours();

                ModeloFatura MF = new ModeloFatura(-1);
                MF.setData(data);
                MF.setHora(hora);
                MF.setDesconto(desconto);
                MF.setMc(MC);
                MF.setMe(ME);
                MF.setMu(MU);
                MF.setRetencao_na_fonte(retencao);
                MF.setTitulo(this.jLabel_tipo_de_factura.getText());
                MF.setTotal(total);
//                MF.setPagamento(this.ValorPago); 

                PreparedStatement pst = liga.con.prepareStatement("Insert into proforma"
                        + "(titulo_da_fatura, desconto,retencao_na_fonte, "
                        + "total, data, hora, idempresa,idutilizador,idcliente,moeda,serie,hash,idturno,indicativo) "
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?," + this.id_turno + ",'" + this.ME.getIndicativo() + "')");
                System.out.println(this.ME.getIndicativo());
                pst.setString(1, MF.getTitulo());
                pst.setDouble(2, MF.getDesconto());
                pst.setDouble(3, MF.getRetencao_na_fonte());
                pst.setDouble(4, MF.getTotal());
                pst.setString(5, MF.getData());
                pst.setString(6, MF.getHora());
                pst.setInt(7, MF.getMe().getIdempresa());
                pst.setInt(8, MF.getMu().getIdUtilizador());
                pst.setInt(9, MF.getMc().getIdCliente());
                pst.setString(10, this.MF.getModeda());
                pst.setString(11, this.ME.getSerie());
                pst.setString(12, "");
//                String Last_asisinatura = LastHash("proforma");

                pst.execute();
                liga.deconecta();

                liga.conexao();
                liga.executeSql("select idproforma from proforma WHERE idutilizador = " + MF.getMu().getIdUtilizador() + " ORDER BY idproforma");
                if (liga.rs.last()) {
                    LastFID = liga.rs.getInt("idproforma");
                    this.MF.setIdfactra(LastFID);
                    String Last_asisinatura = LastHash("proforma", "idproforma", LastFID);
                    if (Last_asisinatura == null) {
                        Last_asisinatura = "";
                    }
                    
                    String NUM = " 0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(LastFID);
                    } else {
                        NUM = NUM + Integer.toString(LastFID);
                    }

                    if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {

                        NUM = ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
                        String hash = Assinatura.GerarAssinatura(MF.getData(), MF.getData() + " " + MF.getHora(), NUM, MF.getTotal(), Last_asisinatura);

                        pst = liga.con.prepareStatement("UPDATE Proforma "
                                + "SET hash= '" + hash + "' WHERE idproforma=" + LastFID);
                        pst.execute();
                        return true;

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Fatura.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Volta entrar no sistema com credenciais válidas!", "Não és um utilizador autorizador.", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    private boolean createFaturaOcamento(ModeloUtilizador MU, ModeloEmpresa ME, ModeloCliente MC) {
        int LastFID = 0;
        if (MU.getIdUtilizador() > 0) {
            try {
                liga.conexao();
                Double desconto = Double.parseDouble(jLabel_Desconto.getText());
                Double retencao = Double.parseDouble(jLabel_RetencaoNaFonte.getText());;
                Double total = Double.parseDouble(jLabel_Total.getText());
                String data = tempo.Date();
                String hora = tempo.Hours();

                ModeloFatura MF = new ModeloFatura(-1);
                MF.setData(data);
                MF.setHora(hora);
                MF.setDesconto(desconto);
                MF.setMc(MC);
                MF.setMe(ME);
                MF.setMu(MU);
                MF.setRetencao_na_fonte(retencao);
                MF.setTitulo(this.jLabel_tipo_de_factura.getText());
                MF.setTotal(total);
//                MF.setPagamento(this.ValorPago); 

                PreparedStatement pst = liga.con.prepareStatement("Insert into orcamento"
                        + "(titulo_da_fatura, desconto,retencao_na_fonte, "
                        + "total, data, hora, idempresa,idutilizador,idcliente,moeda,serie,hash,idturno,indicativo) "
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?," + this.id_turno + ",'" + this.ME.getIndicativo() + "')");
//                        System.out.println(this.ME.getIndicativo()); Titulo(
                pst.setString(1, MF.getTitulo());
                pst.setDouble(2, MF.getDesconto());
                pst.setDouble(3, MF.getRetencao_na_fonte());
                pst.setDouble(4, MF.getTotal());
                pst.setString(5, MF.getData());
                pst.setString(6, MF.getHora());
                pst.setInt(7, MF.getMe().getIdempresa());
                pst.setInt(8, MF.getMu().getIdUtilizador());
                pst.setInt(9, MF.getMc().getIdCliente());
                pst.setString(10, this.MF.getModeda());
                pst.setString(11, this.ME.getSerie());
                pst.setString(12, "");
//                String Last_asisinatura = LastHash("orcamento");

                pst.execute();
                liga.deconecta();

                liga.conexao();
                liga.executeSql("select idorcamento from orcamento ORDER BY idorcamento");
                if (liga.rs.last()) {
                    LastFID = liga.rs.getInt("idorcamento");
                    this.MF.setIdfactra(LastFID);
                    String Last_asisinatura = LastHash("orcamento", "idorcamento", LastFID);
                    if (Last_asisinatura == null) {
                        Last_asisinatura = "";
                    }
                    
                    String NUM = " 0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(LastFID);
                    } else {
                        NUM = NUM + Integer.toString(LastFID);
                    }

                    if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {

                        NUM = ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
                        String hash = Assinatura.GerarAssinatura(MF.getData(), MF.getData() + " " + MF.getHora(), NUM, MF.getTotal(), Last_asisinatura);

                        pst = liga.con.prepareStatement("UPDATE orcamento "
                                + "SET hash= '" + hash + "' WHERE idorcamento=" + LastFID);
                        pst.execute();
                        liga.deconecta();
                        return true;

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Fatura.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Volta entrar no sistema com credenciais válidas!", "Não és um utilizador autorizador.", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    private boolean createFatura(ModeloUtilizador MU, ModeloEmpresa ME, ModeloCliente MC) {
        int LastFID = 0;
        if (MU.getIdUtilizador() > 0) {
            try {
                liga.conexao();
                Double desconto = Double.parseDouble(jLabel_Desconto.getText());
                Double retencao = Double.parseDouble(jLabel_RetencaoNaFonte.getText());;
                Double total = Double.parseDouble(jLabel_Total.getText());
                String data = tempo.Date();
                String hora = tempo.Hours();

                ModeloFatura MF = new ModeloFatura(-1);
                MF.setData(data);
                MF.setHora(hora);
                MF.setDesconto(desconto);
                MF.setMc(MC);
                MF.setMe(ME);
                MF.setMu(MU);
                MF.setRetencao_na_fonte(retencao);
                MF.setTitulo(this.jLabel_tipo_de_factura.getText());
                MF.setTotal(total);
                MF.setPagamento(this.ValorPago);

                PreparedStatement pst = liga.con.prepareStatement("Insert into fatura"
                        + "(titulo_da_fatura, desconto,retencao_na_fonte, "
                        + "total, data, hora, idempresa,idutilizador,idcliente,moeda,imposto_total,serie,hash,idturno,indicativo) "
                        + "values(?,?,?,?,?,?,?,?,?,?,?,?,?," + this.id_turno + ",'" + this.ME.getIndicativo() + "')");
                pst.setString(1, MF.getTitulo());
                pst.setDouble(2, MF.getDesconto());
                pst.setDouble(3, MF.getRetencao_na_fonte());
                pst.setDouble(4, MF.getTotal());
                String data_doc = MF.getData();
                pst.setString(5, data_doc);
                String hora_doc = MF.getHora();
                pst.setString(6, MF.getHora());
                pst.setInt(7, MF.getMe().getIdempresa());
                pst.setInt(8, MF.getMu().getIdUtilizador());
                pst.setInt(9, MF.getMc().getIdCliente());
                pst.setString(10, this.MF.getModeda());
                pst.setDouble(11, this.MF.getTotal_doIVA());

                pst.setString(12, this.ME.getSerie());

//                String Last_asisinatura = LastHash("fatura");
                pst.setString(13, "");
               
                pst.execute();

                pst = liga.con.prepareStatement("UPDATE turno set fatura = fatura + " + MF.getTotal() + " WHERE idturno =" + this.id_turno);
                pst.execute();

                liga.deconecta();

                //
                //
                liga.conexao();
                liga.executeSql("select idfactura from fatura WHERE idutilizador = " + MF.getMu().getIdUtilizador() + " ORDER BY idfactura");
                if (liga.rs.last()) {
                    LastFID = liga.rs.getInt(1);
                    this.MF.setIdfactra(LastFID);
                    String Last_asisinatura = LastHash("fatura", "idfactura", LastFID);
                    if (Last_asisinatura == null) {
                        Last_asisinatura = "";
                    }
                    boolean result = AddVenda(LastFID);
                    String NUM = " 0";
                    if (this.MF.getIdfactra() > 9) {
                        NUM = Integer.toString(LastFID);
                    } else {
                        NUM = NUM + Integer.toString(LastFID);
                    }
                    if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                        NUM = ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
                        String hash = Assinatura.GerarAssinatura(data_doc, data_doc + " " + hora_doc, NUM, MF.getTotal(), Last_asisinatura);

                        pst = liga.con.prepareStatement("UPDATE fatura "
                                + "SET hash= '" + hash + "' WHERE idfactura=" + LastFID);
                        pst.execute();
                        liga.deconecta();
                        return result;
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(Fatura.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Volta entrar no sistema com credenciais válidas!", "Não és um utilizador autorizador.", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    public void Print() {
        if (jTableContentFact.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            String desc = "";
            if (this.jLabel_tipo_de_factura.getText().equals("Fatura")) {
                desc = "FT ";
            } else if (this.jLabel_tipo_de_factura.getText().equals("Fatura recibo")) {
                desc = "FR ";
            } else if (this.jLabel_tipo_de_factura.getText().equals("Proforma")) {
                desc = "PP ";
            } else if (this.jLabel_tipo_de_factura.getText().equals("Orçamento")) {
                desc = "OR ";
            }
            ME.setIndicativo(desc + ME.getIndicativo());
            String NUM = " 0";
            if (this.MF.getIdfactra() > 9) {
                NUM = Integer.toString(this.MF.getIdfactra());
            } else {
                NUM = NUM + Integer.toString(this.MF.getIdfactra());
            }

            String nome_do_ficheiro  = ME.getIndicativo() + " " + this.ME.getSerie() + NUM;
            
            chooser.setSelectedFile(new File(nome_do_ficheiro .replaceAll("/", "_")));
//            System.out.println(ME.getIndicativo() + "" + this.ME.getSerie() + NUM);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String file = chooser.getSelectedFile().toString().concat(".pdf");
                try {
                    ModeloPDF e = new ModeloPDF(new File(file), jTableContentFact, "Documento", liga.getCaminho());
                    e.setME(ME);
                    e.setMC(MC);
                    e.setMU(MU);
                    e.setMF(MF);

                    e.setFormasDePagamento(SelectedForma);
                    String tamanho = null;
                    if (jRadioButton_A5.isSelected()) {
//                        tamanho = "A5";
                        tamanho = "A4";
                    } else if (jRadioButton_ticket_pequeno.isSelected()) {
                        tamanho = "A8";
                    } else if (jRadioButton_ticket.isSelected()) {
                        tamanho = "A7";
                    } else {
                        tamanho = "A4";
                    }
                    e.exportA42(tamanho, this.Dias_de_validade);
                    JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    private String IMPOSTO(boolean aBoolean) {
        if (aBoolean) {
            return Double.toString(this.percentImpostoIVA) + "%";
        } else {
            return "ISENTO";
        }
    }

    public void PreencherTabelaFormaPagamento(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Banco", "Forma de pagamento", "Conta", "IBAN"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {

                    dados.add(new Object[]{liga.rs.getString("abreviacao"), liga.rs.getString("descricao"), liga.rs.getString("numero_da_conta"), liga.rs.getString("ibam")});

                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Formas de pagameno não encontradas.\n" + ex, "Dados não encntrados", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        jTable_FormaPagamento.setModel(modelo);
//        jTable_FormaPagamento.getColumnModel().getColumn(0).setPreferredWidth(70); hash
//        jTable_FormaPagamento.getColumnModel().getColumn(0).setResizable(false);
//        jTable_FormaPagamento.getColumnModel().getColumn(1).setPreferredWidth(500);
//        jTable_FormaPagamento.getColumnModel().getColumn(1).setResizable(false);
//        jTable_FormaPagamento.getTableHeader().setReorderingAllowed(false);
        jTable_FormaPagamento.setAutoResizeMode(jTable_FormaPagamento.AUTO_RESIZE_ALL_COLUMNS);
        jTable_FormaPagamento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();

    }

    private boolean insert(int i, int latnum, int armazem) {
        String Sql = "";
        java.util.Date data = new java.util.Date();
        SimpleDateFormat formatar = new SimpleDateFormat("H:m");
        String hora_registo = formatar.format(data);
        String qnt = jTableContentFact.getValueAt(i, 7).toString();
        // PEGAR DA BD O LOTE MAIS ANTIGO
        ModeloProduto MP = Takeprod(i, jTableContentFact.getValueAt(i, 2).toString());
        String lote = MP.getLote();
        // PEGAR DA BD 
        String idprod = Integer.toString(MP.getIdproduto());
        // PEGAR DA BD
        String custo = Double.toString(MP.getCusto());

        String total = Double.toString(MP.getCusto() * Double.parseDouble(qnt));
        int arm = this.Id_armazens.get(jComboBox_armazem.getSelectedIndex());
        int tipoSaida = 1;
        System.out.println("QNT: " + qnt);
        return CreateSaida("INSERT INTO saida (saida,data_registo,hora_registo,qnt,lote,"
                + "  idproduto,\n"
                + "  id_armazem,\n"
                + "  idturno, numero, custo, total,id_tipo_saida) "
                + "VALUES ('Baixa',current_date,'" + hora_registo + "'," + qnt + ",'" + lote + "',"
                + "  " + idprod + ",\n"
                + "  " + arm + ",\n"
                + this.id_turno + "," + latnum + "," + exte.Convert(custo) + "," + exte.Convert(total) + ", " + tipoSaida + ")");

    }

    public ModeloProduto Takeprod(int i, String desc) {
        ModeloProduto MP = new ModeloProduto();
        //INICIO IMPOSTO
        //System.out.println(liga.getCaminho());
        liga.conexao();
        liga.executeSql("select custo,* from produto WHERE descricao = '" + desc + "'");
        try {

            if (liga.rs.first()) {
                MP.setIdproduto(liga.rs.getInt("idproduto"));
                MP.setCodigo(liga.rs.getString("codigo"));
                MP.setCodigo_de_barra(liga.rs.getString("codigo_de_barra"));
                MP.setDescricao(liga.rs.getString("descricao"));
                MP.setImposto_iva(liga.rs.getBoolean("imposto_iva"));
                MP.setLote(Produtolote(i, MP.getIdproduto()));
//                if (liga.rs.getString(1)!=null)
//                    MP.setCusto(liga.rs.getDouble(1));
//               else
                MP.setCusto(0.0);

            }
            liga.deconecta();
        } catch (SQLException ex) {
            //System.out.println("select custo,* from produto WHERE descricao = '" + desc + "'");
            JOptionPane.showMessageDialog(null, "Falha na busca do produto: \n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }

        //FIM        
        return MP;

    }

    private boolean CreateSaida(String Sql) {
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

    private double TakeQNT(int idproduto, double entrada, String lote) {

        ControloBD cne = new ControloBD();
        double saida = 0, qnt = 0;
        cne.conexao();

        cne.executeSql("select SUM (qnt) from saida where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()) + " and lote = '" + lote + "' ");

        try {
            cne.rs.first();
            do {
                saida = cne.rs.getDouble(1);
            } while (cne.rs.next());
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }
        // System.out.println(saida);

        return entrada - saida;
        //To change body of generated methods, choose Tools | Templates.
    }

    public String Produtolote(int line, int idproduto) {
        if (line > -1) {
            double x = -1;
            String aux = jTableContentFact.getValueAt(line, 7).toString();
            if (aux != null) {
                x = Double.parseDouble(aux);
            }
            liga.conexao();
            liga.executeSql("select lote, SUM (qnt) from entrada where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()) + " group by lote");
            try {
                liga.rs.first();
                do {
                    Double val = TakeQNT(idproduto, liga.rs.getDouble(2), liga.rs.getString("lote"));
                    if (val >= x) {
                        return liga.rs.getString("lote");
                    }
                } while (liga.rs.next());

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Não é possível realizar baixa desse produto.", "Sem stock", JOptionPane.INFORMATION_MESSAGE);

            }
            liga.deconecta();
        }
        return null;
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
            // JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.INFORMATION_MESSAGE);
        }

        return num;
    }

    void insertVenda() {
        int lines = jTableContentFact.getRowCount();
        int latnum;
        latnum = SelectLast("select numero from saida where id_tipo_saida =1 ");
        for (int i = 0; i < lines; i++) {
            if (jTableContentFact.getValueAt(i, 1).toString().equals("Produto")) {
                insert(i, latnum, 222);
            }
        }
    }

    public void AgregarItem() {
        if (MC.getIdCliente() != -1) {
            int X = jTableMAteria.getSelectedRow();
            String idSelected;
            double DescontoTotal = Double.parseDouble(jLabel_Desconto.getText());
            double Total = Double.parseDouble(jLabel_Total.getText());
            double Retencao_Total = Double.parseDouble(jLabel_RetencaoNaFonte.getText());

            if (X != -1) {
                if (jRadioButton2.isSelected() && jLabel_TítuloDaTabela.getText().equals("Produtos")) {
                    if (jLabel_tipo_de_factura.getText().equals("Proforma") || jLabel_tipo_de_factura.getText().equals("Orçamento") || Double.parseDouble(jTableMAteria.getValueAt(X, 5).toString()) > 0) {
                        ModeloProduto MP = BuscarProduto(jTableMAteria.getValueAt(X, 1).toString());
                        String IVA = jTableMAteria.getValueAt(X, 3).toString();
                        String Motivo = "";
                        Object mot = jTableMAteria.getValueAt(X, 4);
                        Double taxa;
                        Double quant = 1.0;
                        Double desconto = Double.parseDouble(jTextField_Desconto.getText());
                        jTextField_Desconto.setText("0.0");
                        MP.setPreco(MP.getPreco());
                        MP.setPreco_venda(MP.getPreco_venda() - desconto);
                        DescontoTotal += desconto;
                        jLabel_Desconto.setText(Double.toString(DescontoTotal));
                        if (mot != null) {
                            Motivo = mot.toString();
                            taxa = 0.0;
//                    System.out.println("Isento");

                        } else {
                            taxa = this.percentImpostoIVA / 100;
                            MF.setTotal_doIVA(MF.getTotal_doIVA() + MP.getValor_iva());
//                    System.out.println("IVA");
                        }
                        this.dados.add(new Object[]{MP.getCodigo(), "Produto", MP.getDescricao(),
                            MP.getPreco(), IVA, Motivo, "0.0", quant, desconto, MP.getPreco_venda()});

                        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                        };
                        jTableContentFact.setModel(modelo);

//                Tdesconto += desconto;
                        Total = Double.parseDouble(jLabel_Total.getText());
//                    subTotal += totalInicial;
                        Total += MP.getPreco_venda();

                        jLabel_Total.setText(Double.toString(Total));
                    } else {
                        JOptionPane.showMessageDialog(null, "Stock insuficiente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (jRadioButton1.isSelected() && jLabel_TítuloDaTabela.getText().equals("Serviços")) {
                    ModeloServico MP = BuscarServico(jTableMAteria.getValueAt(X, 1).toString());
                    String IVA = jTableMAteria.getValueAt(X, 3).toString();
                    String Motivo = "";
                    Object mot = jTableMAteria.getValueAt(X, 4);
                    Double taxa;
                    Double quant = 1.0;
                    Double desconto = Double.parseDouble(jTextField_Desconto.getText());
                    jTextField_Desconto.setText("0.0");
                    MP.setPreco(MP.getPreco());
                    Double RETENCAO = 0.0;
                    boolean tem = MP.isRetencao_na_fonte();
                    if (!this.MC.getTipo_de_pessoa().equals("Física") && tem && MP.getPreco() >= 20000) {
                        RETENCAO = MP.getPreco() * ME.getTaxa_de_retencao();

                        if (this.ME.getPagador().equals("cliente")) {
                            MP.setPreco_venda(MP.getPreco_venda() - RETENCAO - desconto);

                        } else {
                            MP.setPreco_venda(MP.getPreco_venda() - desconto);
                        }
                    } else {
                        MP.setPreco_venda(MP.getPreco_venda() - desconto);

                    }

                    DescontoTotal += desconto;
                    jLabel_Desconto.setText(Double.toString(DescontoTotal));
                    Retencao_Total += RETENCAO;
                    jLabel_RetencaoNaFonte.setText(Double.toString(Retencao_Total));
                    if (mot != null) {
                        Motivo = mot.toString();
                        taxa = 0.0;
//                    System.out.println("Isento");

                    } else {
                        taxa = this.percentImpostoIVA / 100;
                        MF.setTotal_doIVA(MF.getTotal_doIVA() + MP.getValor_iva());
//                    System.out.println("IVA");
                    }
                    this.dados.add(new Object[]{MP.getCodigo(), "Serviço", MP.getDescricao(),
                        MP.getPreco(), IVA, Motivo, RETENCAO, quant, desconto, MP.getPreco_venda()});

                    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                    };
                    jTableContentFact.setModel(modelo);

//                Tdesconto += desconto;
                    Total = Double.parseDouble(jLabel_Total.getText());
//                    subTotal += totalInicial;
                    Total += MP.getPreco_venda();

                    jLabel_Total.setText(Double.toString(Total));
                } else {
                    JOptionPane.showMessageDialog(null, "Reinicie a aplicação.", "Erro.", JOptionPane.ERROR_MESSAGE);
//                    System.out.println("Erro. Reinicie a aplicação.");
                }

                jTableContentFact.getColumnModel().getColumn(0).setPreferredWidth(40);
                jTableContentFact.getColumnModel().getColumn(0).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(1).setPreferredWidth(5);
                jTableContentFact.getColumnModel().getColumn(1).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(2).setPreferredWidth(200);
                jTableContentFact.getColumnModel().getColumn(2).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(3).setPreferredWidth(5);
                jTableContentFact.getColumnModel().getColumn(3).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(4).setPreferredWidth(20);
                jTableContentFact.getColumnModel().getColumn(4).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(5).setPreferredWidth(5);
                jTableContentFact.getColumnModel().getColumn(5).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(6).setPreferredWidth(50);
                jTableContentFact.getColumnModel().getColumn(6).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(7).setPreferredWidth(20);
                jTableContentFact.getColumnModel().getColumn(7).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(8).setPreferredWidth(70);
                jTableContentFact.getColumnModel().getColumn(8).setResizable(true);
                jTableContentFact.getColumnModel().getColumn(9).setPreferredWidth(70);
                jTableContentFact.getColumnModel().getColumn(9).setResizable(true);

                jTableContentFact.getTableHeader().setReorderingAllowed(false);
                jTableContentFact.setAutoResizeMode(jTableMAteria.AUTO_RESIZE_ALL_COLUMNS);
                jTableContentFact.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            }

        } else {
            JOptionPane.showMessageDialog(null, "Não podes adicionar um item na fatura sem antes selecionar um cliente.", "Nenhum cliente selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void AgregarItem(double qnt) {
        if (MC.getIdCliente() != -1) {
            double DescontoTotal = Double.parseDouble(jLabel_Desconto.getText());
            double Total = Double.parseDouble(jLabel_Total.getText());
            double Retencao_Total = Double.parseDouble(jLabel_RetencaoNaFonte.getText());
            int X = jTableContentFact.getSelectedRow();
            String idSelected;
            if (X != -1) {
                if (jRadioButton2.isSelected() && jLabel_TítuloDaTabela.getText().equals("Produtos")) {

                    // Descontar o que já existe.
                    double quant = Double.parseDouble(jTableContentFact.getValueAt(X, 7).toString());
                    double desconto = Double.parseDouble(jTableContentFact.getValueAt(X, 8).toString());
                    DescontoTotal = DescontoTotal - desconto;
                    double auxTotal = Double.parseDouble(jTableContentFact.getValueAt(X, 9).toString());
                    Total = Total - auxTotal;
                    System.out.println("TOTAL: " + Total);

                    // inserir novo
                    ModeloProduto MP = BuscarProduto(jTableContentFact.getValueAt(X, 2).toString());
                    String IVA = jTableContentFact.getValueAt(X, 4).toString();
                    String Motivo = "";
                    Object mot = jTableContentFact.getValueAt(X, 5);
                    Double taxa;
                    quant = qnt;
                    desconto = quant * Double.parseDouble(jTextField_Desconto.getText());
                    jTextField_Desconto.setText("0.0");
                    MP.setPreco(MP.getPreco());
                    MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);
                    DescontoTotal += desconto;
                    jLabel_Desconto.setText(Double.toString(DescontoTotal));
                    if (mot != null) {
                        Motivo = mot.toString();
                        taxa = 0.0;
//                    System.out.println("Isento");

                    } else {
                        taxa = this.percentImpostoIVA / 100;
                        MF.setTotal_doIVA(MF.getTotal_doIVA() + quant * MP.getValor_iva());

//                    System.out.println("IVA");
                    }

                    // remover a linha
                    this.dados.remove(X);
                    if (qnt > 0) {
                        this.dados.add(X, new Object[]{MP.getCodigo(), "Produto", MP.getDescricao(),
                            MP.getPreco(), IVA, Motivo, "0.0", quant, desconto, MP.getPreco_venda()});
                    }

                    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                    };
                    jTableContentFact.setModel(modelo);

//                Tdesconto += desconto;
//                     Total = Double.parseDouble(jLabel_Total.getText());
//                    subTotal += totalInicial;
                    Total += MP.getPreco_venda();

                    jLabel_Total.setText(Double.toString(Total));
                } else if (jRadioButton1.isSelected() && jLabel_TítuloDaTabela.getText().equals("Serviços")) {

                    // Descontar o que já existe.
                    double quant = Double.parseDouble(jTableContentFact.getValueAt(X, 7).toString());
                    double desconto = Double.parseDouble(jTableContentFact.getValueAt(X, 8).toString());
                    double retencao = Double.parseDouble(jTableContentFact.getValueAt(X, 6).toString());
                    Retencao_Total = Retencao_Total - retencao;
                    DescontoTotal = DescontoTotal - desconto;
                    double auxTotal = Double.parseDouble(jTableContentFact.getValueAt(X, 9).toString());
                    Total = Total - auxTotal;
//                    System.out.println("TOTAL: " + Total);

                    // inserir novo
                    ModeloServico MP = BuscarServico(jTableContentFact.getValueAt(X, 2).toString());
                    String IVA = jTableContentFact.getValueAt(X, 4).toString();
                    String Motivo = "";
                    Object mot = jTableContentFact.getValueAt(X, 5);
                    Double taxa;
                    quant = qnt;
                    desconto = quant * Double.parseDouble(jTextField_Desconto.getText());
                    jTextField_Desconto.setText("0.0");
                    MP.setPreco(MP.getPreco());
                    Double RETENCAO = 0.0;
                    boolean tem = MP.isRetencao_na_fonte();
                    if (!this.MC.getTipo_de_pessoa().equals("Física") && tem && MP.getPreco() >= 20000) {
                        RETENCAO = MP.getPreco() * ME.getTaxa_de_retencao();
                        RETENCAO *= quant;
                        if (this.ME.getPagador().equals("cliente")) {
                            MP.setPreco_venda(quant * MP.getPreco_venda() - RETENCAO - desconto);

                        } else {
                            MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);
                        }
                    } else {
                        MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);

                    }
//                    MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);
                    DescontoTotal += desconto;
                    jLabel_Desconto.setText(Double.toString(DescontoTotal));
                    Retencao_Total = Retencao_Total + RETENCAO;
                    jLabel_RetencaoNaFonte.setText(Double.toString(Retencao_Total));
                    if (mot != null) {
                        Motivo = mot.toString();
                        taxa = 0.0;
//                    System.out.println("Isento");

                    } else {
                        taxa = this.percentImpostoIVA / 100;
                        MF.setTotal_doIVA(MF.getTotal_doIVA() + quant * MP.getValor_iva());

//                    System.out.println("IVA");
                    }

                    // remover a linha
                    this.dados.remove(X);
                    if (qnt > 0) {
                        this.dados.add(X, new Object[]{MP.getCodigo(), "Serviço", MP.getDescricao(),
                            MP.getPreco(), IVA, Motivo, RETENCAO, quant, desconto, MP.getPreco_venda()});
                    }

                    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                    };
                    jTableContentFact.setModel(modelo);

//                Tdesconto += desconto;
//                     Total = Double.parseDouble(jLabel_Total.getText());
//                    subTotal += totalInicial;
                    Total += MP.getPreco_venda();

                    jLabel_Total.setText(Double.toString(Total));
                } else {
                    JOptionPane.showMessageDialog(null, "Reinicie a aplicação.", "Erro.", JOptionPane.ERROR_MESSAGE);
//                    System.out.println("Erro. Reinicie a aplicação.");
                }

            }

            jTableContentFact.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTableContentFact.getColumnModel().getColumn(0).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(1).setPreferredWidth(5);
            jTableContentFact.getColumnModel().getColumn(1).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableContentFact.getColumnModel().getColumn(2).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(3).setPreferredWidth(5);
            jTableContentFact.getColumnModel().getColumn(3).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(4).setPreferredWidth(20);
            jTableContentFact.getColumnModel().getColumn(4).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(5).setPreferredWidth(5);
            jTableContentFact.getColumnModel().getColumn(5).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTableContentFact.getColumnModel().getColumn(6).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(7).setPreferredWidth(20);
            jTableContentFact.getColumnModel().getColumn(7).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(8).setPreferredWidth(70);
            jTableContentFact.getColumnModel().getColumn(8).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(9).setPreferredWidth(70);
            jTableContentFact.getColumnModel().getColumn(9).setResizable(true);

            jTableContentFact.getTableHeader().setReorderingAllowed(false);
            jTableContentFact.setAutoResizeMode(jTableMAteria.AUTO_RESIZE_ALL_COLUMNS);
            jTableContentFact.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else {
            JOptionPane.showMessageDialog(null, "Não podes adicionar um item na fatura sem antes selecionar um cliente.", "Nenhum cliente selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void AgregarItem(double qnt, int X) {
        if (MC.getIdCliente() != -1) {
            double DescontoTotal = Double.parseDouble(jLabel_Desconto.getText());
            double Total = Double.parseDouble(jLabel_Total.getText());
            double Retencao_Total = Double.parseDouble(jLabel_RetencaoNaFonte.getText());
//            int X = jTableContentFact.getSelectedRow();
            String idSelected;
            if (X != -1) {
                if (jRadioButton2.isSelected() && jLabel_TítuloDaTabela.getText().equals("Produtos")) {

                    // Descontar o que já existe.
                    double quant = Double.parseDouble(jTableContentFact.getValueAt(X, 7).toString());
                    double desconto = Double.parseDouble(jTableContentFact.getValueAt(X, 8).toString());
                    DescontoTotal = DescontoTotal - desconto;
                    double auxTotal = Double.parseDouble(jTableContentFact.getValueAt(X, 9).toString());
                    Total = Total - auxTotal;
                    System.out.println("TOTAL: " + Total);

                    // inserir novo
                    ModeloProduto MP = BuscarProduto(jTableContentFact.getValueAt(X, 2).toString());
                    String IVA = jTableContentFact.getValueAt(X, 4).toString();
                    String Motivo = "";
                    Object mot = jTableContentFact.getValueAt(X, 5);
                    Double taxa;
                    quant = qnt;
                    desconto = quant * Double.parseDouble(jTextField_Desconto.getText());
                    jTextField_Desconto.setText("0.0");
                    MP.setPreco(MP.getPreco());
                    MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);
                    DescontoTotal += desconto;
                    jLabel_Desconto.setText(Double.toString(DescontoTotal));
                    if (mot != null) {
                        Motivo = mot.toString();
                        taxa = 0.0;
//                    System.out.println("Isento");

                    } else {
                        taxa = this.percentImpostoIVA / 100;
                        MF.setTotal_doIVA(MF.getTotal_doIVA() + quant * MP.getValor_iva());

//                    System.out.println("IVA");
                    }

                    // remover a linha
                    this.dados.remove(X);
                    if (qnt > 0) {
                        this.dados.add(X, new Object[]{MP.getCodigo(), "Produto", MP.getDescricao(),
                            MP.getPreco(), IVA, Motivo, "0.0", quant, desconto, MP.getPreco_venda()});
                    }

                    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                    };
                    jTableContentFact.setModel(modelo);

//                Tdesconto += desconto;
//                     Total = Double.parseDouble(jLabel_Total.getText());
//                    subTotal += totalInicial;
                    Total += MP.getPreco_venda();

                    jLabel_Total.setText(Double.toString(Total));
                } else if (jRadioButton1.isSelected() && jLabel_TítuloDaTabela.getText().equals("Serviços")) {

                    // Descontar o que já existe.
                    double quant = Double.parseDouble(jTableContentFact.getValueAt(X, 7).toString());
                    double desconto = Double.parseDouble(jTableContentFact.getValueAt(X, 8).toString());
                    double retencao = Double.parseDouble(jTableContentFact.getValueAt(X, 6).toString());
                    Retencao_Total = Retencao_Total - retencao;
                    DescontoTotal = DescontoTotal - desconto;
                    double auxTotal = Double.parseDouble(jTableContentFact.getValueAt(X, 9).toString());
                    Total = Total - auxTotal;
//                    System.out.println("TOTAL: " + Total);

                    // inserir novo
                    ModeloServico MP = BuscarServico(jTableContentFact.getValueAt(X, 2).toString());
                    String IVA = jTableContentFact.getValueAt(X, 4).toString();
                    String Motivo = "";
                    Object mot = jTableContentFact.getValueAt(X, 5);
                    Double taxa;
                    quant = qnt;
                    desconto = quant * Double.parseDouble(jTextField_Desconto.getText());
                    jTextField_Desconto.setText("0.0");
                    MP.setPreco(MP.getPreco());
                    Double RETENCAO = 0.0;
                    boolean tem = MP.isRetencao_na_fonte();
                    if (!this.MC.getTipo_de_pessoa().equals("Física") && tem && MP.getPreco() >= 20000) {
                        RETENCAO = MP.getPreco() * ME.getTaxa_de_retencao();
                        RETENCAO *= quant;
                        if (this.ME.getPagador().equals("cliente")) {
                            MP.setPreco_venda(quant * MP.getPreco_venda() - RETENCAO - desconto);

                        } else {
                            MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);
                        }
                    } else {
                        MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);

                    }
//                    MP.setPreco_venda(quant * MP.getPreco_venda() - desconto);
                    DescontoTotal += desconto;
                    jLabel_Desconto.setText(Double.toString(DescontoTotal));
                    Retencao_Total = Retencao_Total + RETENCAO;
                    jLabel_RetencaoNaFonte.setText(Double.toString(Retencao_Total));
                    if (mot != null) {
                        Motivo = mot.toString();
                        taxa = 0.0;
//                    System.out.println("Isento");

                    } else {
                        taxa = this.percentImpostoIVA / 100;
                        MF.setTotal_doIVA(MF.getTotal_doIVA() + quant * MP.getValor_iva());

//                    System.out.println("IVA");
                    }

                    // remover a linha
                    this.dados.remove(X);
                    if (qnt > 0) {
                        this.dados.add(X, new Object[]{MP.getCodigo(), "Serviço", MP.getDescricao(),
                            MP.getPreco(), IVA, Motivo, RETENCAO, quant, desconto, MP.getPreco_venda()});
                    }

                    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
                    };
                    jTableContentFact.setModel(modelo);

//                Tdesconto += desconto;
//                     Total = Double.parseDouble(jLabel_Total.getText());
//                    subTotal += totalInicial;
                    Total += MP.getPreco_venda();

                    jLabel_Total.setText(Double.toString(Total));
                } else {
                    JOptionPane.showMessageDialog(null, "Reinicie a aplicação.", "Erro.", JOptionPane.ERROR_MESSAGE);
//                    System.out.println("Erro. Reinicie a aplicação.");
                }

            }

            jTableContentFact.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTableContentFact.getColumnModel().getColumn(0).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(1).setPreferredWidth(5);
            jTableContentFact.getColumnModel().getColumn(1).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableContentFact.getColumnModel().getColumn(2).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(3).setPreferredWidth(5);
            jTableContentFact.getColumnModel().getColumn(3).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(4).setPreferredWidth(20);
            jTableContentFact.getColumnModel().getColumn(4).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(5).setPreferredWidth(5);
            jTableContentFact.getColumnModel().getColumn(5).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTableContentFact.getColumnModel().getColumn(6).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(7).setPreferredWidth(20);
            jTableContentFact.getColumnModel().getColumn(7).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(8).setPreferredWidth(70);
            jTableContentFact.getColumnModel().getColumn(8).setResizable(true);
            jTableContentFact.getColumnModel().getColumn(9).setPreferredWidth(70);
            jTableContentFact.getColumnModel().getColumn(9).setResizable(true);

            jTableContentFact.getTableHeader().setReorderingAllowed(false);
            jTableContentFact.setAutoResizeMode(jTableMAteria.AUTO_RESIZE_ALL_COLUMNS);
            jTableContentFact.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else {
            JOptionPane.showMessageDialog(null, "Não podes adicionar um item na fatura sem antes selecionar um cliente.", "Nenhum cliente selecionado", JOptionPane.WARNING_MESSAGE);
        }
    }

    ModeloProduto BuscarProduto(String descricao) {
        ModeloProduto MP;
        liga.conexao();
        liga.executeSql("select * from produto WHERE descricao= '" + descricao + "'");
        try {

            if (liga.rs.first()) {
                MP = new ModeloProduto(liga.rs.getInt("idproduto"));
                MP.setCodigo(liga.rs.getString("codigo"));
                MP.setCodigo_de_barra(liga.rs.getString("codigo_de_barra"));
                MP.setDescricao(liga.rs.getString("descricao"));
//                this.jTextField_existencia.setText(liga.rs.getString("existencia"));
//                this.jTextField_iva.setText("14");

                if (liga.rs.getBoolean("imposto_iva")) {
                    MP.setImposto_iva(true);
                    MP.setMotivo("");
                } else {
                    MP.setImposto_iva(false);
                    MP.setValor_iva(0.0);
                    MP.setMotivo(liga.rs.getString("motivo"));
                }

                String punitario = liga.rs.getString("preco_unitario");
                if (punitario != null) {
                    MP.setPreco(Double.parseDouble(punitario));
                } else {
                    MP.setPreco(0.0);
                }

                String pvenda = liga.rs.getString("preco");
                if (pvenda != null) {
                    MP.setPreco_venda(Double.parseDouble(pvenda));
                } else {
                    MP.setPreco_venda(0.0);
                }
                if (liga.rs.getString("valor_iva") != null) {
                    MP.setValor_iva(liga.rs.getDouble("valor_iva"));
                } else {
                    MP.setValor_iva(0.0);
                }

                return MP;
//                
            }
            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return null;
    }

    ModeloServico BuscarServico(String descricao) {
        ModeloServico MP;
        liga.conexao();
        liga.executeSql("select * from servico WHERE descricao= '" + descricao + "'");
        try {

            if (liga.rs.first()) {
                MP = new ModeloServico(liga.rs.getInt("idservico"));
                MP.setCodigo("s" + liga.rs.getString("idservico"));
//                MP.setCodigo_de_barra(liga.rs.getString("codigo_de_barra"));
                MP.setDescricao(liga.rs.getString("descricao"));
//                this.jTextField_existencia.setText(liga.rs.getString("existencia"));
//                this.jTextField_iva.setText("14");

                if (liga.rs.getBoolean("imposto_iva")) {
                    MP.setImposto_iva(true);
                    MP.setMotivo("");
                } else {
                    MP.setImposto_iva(false);
                    MP.setValor_iva(0.0);
                    MP.setMotivo(liga.rs.getString("motivo"));
                }

                String punitario = liga.rs.getString("preco_unitario");
                if (punitario != null) {
                    MP.setPreco(Double.parseDouble(punitario));
                } else {
                    MP.setPreco(0.0);
                }

                String pvenda = liga.rs.getString("preco");
                if (pvenda != null) {
                    MP.setPreco_venda(Double.parseDouble(pvenda));
                } else {
                    MP.setPreco_venda(0.0);
                }
                if (liga.rs.getString("valor_iva") != null) {
                    MP.setValor_iva(liga.rs.getDouble("valor_iva"));
                } else {
                    MP.setValor_iva(0.0);
                }

                if (liga.rs.getString("retencao") != null) {
                    MP.setRetencao_na_fonte(liga.rs.getBoolean("retencao"));
                } else {
                    MP.setRetencao_na_fonte(false);
                }

                return MP;
//                
            }
            liga.deconecta();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return null;
    }

    public void restart() {
        Fatura fatura = new Fatura(this.jLabel_tipo_de_factura.getText(), this.id, this.perfil, this.liga.getCaminho());
        fatura.setVisible(true);
        this.dispose();
    }

    private String LastHash(String tabela, String campo, int num) {
        if (num == 1) {
            return null;
        }
        liga.conexao();
        num--;
        liga.executeSql("select hash from " + tabela + " WHERE " + campo + "=" + num);
        try {
            if (liga.rs.last()) {
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
//Assinatura

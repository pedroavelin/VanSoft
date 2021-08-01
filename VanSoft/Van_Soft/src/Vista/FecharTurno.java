/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import Controle.TextPrompt;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Modelo.ModeloEmpresa;
import Modelo.ModeloUtilizador;
import Modelo.ModeloPDF;
import Modelo.ModeloTabela;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Gonga
 */
public class FecharTurno extends javax.swing.JFrame {

    int id_turno = -1;
    ControloBD liga = new ControloBD();
    int numero, id_utilizador;
    String perfil;
    ModeloEmpresa ME = new ModeloEmpresa();
    ModeloUtilizador MU = new ModeloUtilizador(-1);
    String tamanho = "A4";

    ArrayList dadosFT = new ArrayList();
    String[] colunasFT;
    ArrayList dadosFR = new ArrayList();
    String[] colunasFR;
    ArrayList dadosNC = new ArrayList();
    String[] colunasNC;
    ArrayList dadosRC = new ArrayList();
    String[] colunasRC;
    ArrayList dadosSD = new ArrayList();
    String[] colunasSD;
    ArrayList dadosPR = new ArrayList();
    String[] colunasPR;

    /**
     * Creates new form abrir_turno
     */
    public FecharTurno() {
        initComponents();
        TextPrompt prompt1 = new TextPrompt("Saldo inicial do turno.", jTextField1);

    }

    public FecharTurno(int id_turno, int numero, int id_utilizador, String perfil, String caminho) {
        try {
            initComponents();
            Toolkit T = Toolkit.getDefaultToolkit();
            this.setSize(T.getScreenSize());
            this.setExtendedState(this.MAXIMIZED_BOTH);
            liga.setCaminho(caminho);
            this.id_turno = id_turno;
            this.numero = numero;
            this.id_utilizador = id_utilizador;
            this.perfil = perfil;
            PreencherTabelaFatura("select * from fatura where idturno = " + this.id_turno + " order by idfactura");
            TextPrompt prompt1 = new TextPrompt("Saldo final do turno.", jTextField1);

            liga.conexao();
            liga.executeSql("select * from utilizador where idutilizador=" + id_utilizador);

            if (liga.rs.first()) {

                this.MU.setIdUtilizador(id_utilizador);
                this.MU.setNif(liga.rs.getString("nif"));
                this.MU.setNome_utilizador(liga.rs.getString("nome_utilizador"));
                this.MU.setNome(liga.rs.getString("nome"));
                this.MU.setTelefone((liga.rs.getInt("telefone")));
                this.MU.setEmail(liga.rs.getString("email"));
                this.MU.setPerfil(liga.rs.getString("perfil"));
                this.MU.setSexo(liga.rs.getString("sexo"));

            }
            liga.deconecta();

            liga.conexao();
            liga.executeSql("select * from empresa limit 1");
//            tamanho = "A4";
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

                this.ME.setRegime_de_iva(liga.rs.getString("regime_de_iva"));
                tamanho = liga.rs.getString("tamanho_doc");
                this.ME.setTamanho_do_doc(tamanho);
//                this.Dias_de_validade= 7;
                this.ME.setIndicativo(liga.rs.getString("indicativo"));
                this.ME.setTaxa_de_retencao(liga.rs.getDouble("retencao_na_fonte") / 100);
                this.ME.setPagador(liga.rs.getString("pagador_retencao"));
//                this.jTextFieldTRETENCAO.setText(Double.toString(liga.rs.getDouble("retencao_na_fonte")));
//                if (liga.rs.getString("pagador_retencao").equals("cliente")){
//                    this.jRadioButton2.setSelected(true);
//                    
//                }else{
//                   this.jRadioButton1.setSelected(true);
//                }

            }
            liga.deconecta();
        } catch (SQLException ex) {
            Logger.getLogger(FecharTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreencherTableVenda("SELECT venda.*, indicativo FROM venda inner join recibo on "
                + "venda.idfactura = recibo.idfatura_recibo WHERE "
                + "venda.origem=1 AND recibo.idturno = " + this.id_turno + " "
                + "order by venda.descricao, venda.codigo ",
                "SELECT venda.*, indicativo FROM venda inner join fatura \n"
                + "on venda.idfactura = fatura.idfactura WHERE venda.origem=2 AND fatura.idturno =  " + this.id_turno + " "
                + "order by venda.descricao, venda.codigo ");
    }

    public FecharTurno(int id_turno, double saldo_final, int numero, int id_utilizador, String perfil, String caminho) {
        try {
            initComponents();

            Toolkit T = Toolkit.getDefaultToolkit();
            this.setSize(T.getScreenSize());
            this.setExtendedState(this.MAXIMIZED_BOTH);

            jLabel1.setText("Registro do turno Nº " + numero);
            jTextField1.setText("" + saldo_final);
            jTextField1.setEditable(false);
            jbEntrar.setText("Imprimir");
            liga.setCaminho(caminho);
            this.id_turno = id_turno;
            this.numero = numero;
            this.id_utilizador = id_utilizador;
            this.perfil = perfil;
            PreencherTabelaFatura("select * from fatura where idturno = " + this.id_turno + " order by idfactura");
            TextPrompt prompt1 = new TextPrompt("Saldo final do turno.", jTextField1);

            liga.conexao();
            liga.executeSql("select * from utilizador inner join turno "
                    + "on turno.idutilizador= utilizador.idutilizador "
                    + "where idturno =" + this.id_turno);

            if (liga.rs.first()) {

                this.MU.setIdUtilizador(liga.rs.getInt("idutilizador"));
                this.MU.setNif(liga.rs.getString("nif"));
                this.MU.setNome_utilizador(liga.rs.getString("nome_utilizador"));
                this.MU.setNome(liga.rs.getString("nome"));
                this.MU.setTelefone((liga.rs.getInt("telefone")));
                this.MU.setEmail(liga.rs.getString("email"));
                this.MU.setPerfil(liga.rs.getString("perfil"));
                this.MU.setSexo(liga.rs.getString("sexo"));

            }
            liga.deconecta();

            liga.conexao();
            liga.executeSql("select * from empresa limit 1");
//            tamanho = "A4";
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

                this.ME.setRegime_de_iva(liga.rs.getString("regime_de_iva"));
                tamanho = liga.rs.getString("tamanho_doc");
                this.ME.setTamanho_do_doc(tamanho);
//                this.Dias_de_validade= 7;
                this.ME.setIndicativo(liga.rs.getString("indicativo"));
                this.ME.setTaxa_de_retencao(liga.rs.getDouble("retencao_na_fonte") / 100);
                this.ME.setPagador(liga.rs.getString("pagador_retencao"));
//                this.jTextFieldTRETENCAO.setText(Double.toString(liga.rs.getDouble("retencao_na_fonte")));
//                if (liga.rs.getString("pagador_retencao").equals("cliente")){
//                    this.jRadioButton2.setSelected(true);
//                    
//                }else{
//                   this.jRadioButton1.setSelected(true);
//                }

            }
            liga.deconecta();
        } catch (SQLException ex) {
            Logger.getLogger(FecharTurno.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreencherTableVenda("SELECT venda.*, indicativo FROM venda inner join recibo on "
                + "venda.idfactura = recibo.idfatura_recibo WHERE "
                + "venda.origem=1 AND recibo.idturno = " + this.id_turno + " "
                + "order by venda.descricao, venda.codigo ",
                "SELECT venda.*, indicativo FROM venda inner join fatura \n"
                + "on venda.idfactura = fatura.idfactura WHERE venda.origem=2 AND fatura.idturno =  " + this.id_turno + " "
                + "order by venda.descricao, venda.codigo ");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroupDocumentos = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbEntrar = new javax.swing.JButton();
        jbSair = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        T_Tabela = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        T_Tabela1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Fecho de turno");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setText("00");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jbEntrar.setBackground(new java.awt.Color(56, 65, 84));
        jbEntrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbEntrar.setForeground(new java.awt.Color(255, 255, 255));
        jbEntrar.setText("Fechar");
        jbEntrar.setBorderPainted(false);
        jbEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbEntrarMouseExited(evt);
            }
        });
        jbEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEntrarActionPerformed(evt);
            }
        });
        jbEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbEntrarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbEntrarKeyTyped(evt);
            }
        });

        jbSair.setBackground(new java.awt.Color(217, 81, 51));
        jbSair.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbSair.setForeground(new java.awt.Color(255, 255, 255));
        jbSair.setText("Cancelar");
        jbSair.setBorderPainted(false);
        jbSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jbSairMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jbSairMouseExited(evt);
            }
        });
        jbSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSairActionPerformed(evt);
            }
        });
        jbSair.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbSairKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Saldo final");

        T_Tabela.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(T_Tabela);

        buttonGroupDocumentos.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Fatura");
        jRadioButton1.setToolTipText("");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroupDocumentos.add(jRadioButton2);
        jRadioButton2.setText("Fatura Recito");
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        buttonGroupDocumentos.add(jRadioButton3);
        jRadioButton3.setText("Nota de crédito (Devoluções)");
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseClicked(evt);
            }
        });

        buttonGroupDocumentos.add(jRadioButton4);
        jRadioButton4.setText("Recibos");
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton4MouseClicked(evt);
            }
        });

        buttonGroupDocumentos.add(jRadioButton5);
        jRadioButton5.setText("Despesas");
        jRadioButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton5MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Histórico de documentos");

        T_Tabela1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(T_Tabela1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Histórico de vendas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(301, 301, 301)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton3)
                        .addGap(10, 10, 10)
                        .addComponent(jRadioButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jScrollPane3)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(80, 80, 80))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jbSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbSair, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEntrarMouseClicked
//        //criar duas variaveis para validar as informações
//
//        //pegar os valores dos campos
//        String Y = jTextField1.getText();
//        String O = jPasswordField1.getText();
//        //Verificar se o jTcampo1 esta vazio
//        if (jTextField1.equals("")) {
//
//            JOptionPane.showMessageDialog(this, "Digite o nome");
//            jTextField1.setBackground(Color.red);
//            jTextField1.setBackground(Color.white);
//            jTextField1.requestFocus(); //coloca o foco no campo
//        } else {
//            //se não estiver vazio atribui o valor 1 a variavel "a"
//
//            ////volta com a cor pardrão
//            jTextField1.setBackground(Color.white);
//            jTextField1.setForeground(Color.black);
//        }
//        // aqui verifica se as variaveis "a" e "b" estão com valores "1"
//
//        if (jTextField1.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Nome do usuário inválido.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
//            jTextField1.requestFocus();
//        } else if (jPasswordField1.getText().isEmpty()) {
//            JOptionPane.showMessageDialog(rootPane, "Senha inválida.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
//            jPasswordField1.requestFocus();
//        } else {
//            liga.conexao();
//            try {
//                liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
//                liga.rs.first();
//                if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {
//                    Principal l = new Principal();
//                    l.setVisible(true);
//                    dispose();
//                    //               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
//                } else {
//                    jPasswordField1.requestFocus();
//                    jPasswordField1.setBackground(Color.RED);
//                    JOptionPane.showMessageDialog(rootPane, "Senha inválida.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
//                }
//            } catch (SQLException ex) {
//                jTextField1.requestFocus();
//                jTextField1.setBackground(Color.RED);
//                JOptionPane.showMessageDialog(null, "Nome do usuário inválido.", "Acesso Negado.", JOptionPane.WARNING_MESSAGE);
//            }
//        }
    }//GEN-LAST:event_jbEntrarMouseClicked

    private void jbEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEntrarMouseEntered
        this.jbEntrar.setBackground(new Color(235, 235, 235));
        this.jbEntrar.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jbEntrarMouseEntered

    private void jbEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbEntrarMouseExited
        this.jbEntrar.setBackground(new Color(56, 65, 84));
        this.jbEntrar.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbEntrarMouseExited

    private void jbEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEntrarActionPerformed
        if (!jTextField1.getText().isEmpty()) {
            double valorInicial = Double.parseDouble(jTextField1.getText());
            if (Fechar()) {
                PreencherTableVenda("SELECT venda.*, indicativo FROM venda inner join recibo on "
                        + "venda.idfactura = recibo.idfatura_recibo WHERE "
                        + "venda.origem=1 AND recibo.idturno = " + this.id_turno + " "
                        + "order by venda.descricao, venda.codigo ",
                        "SELECT venda.*, indicativo FROM venda inner join fatura \n"
                        + "on venda.idfactura = fatura.idfactura WHERE venda.origem=2 AND fatura.idturno =  " + this.id_turno + " "
                        + "order by venda.descricao, venda.codigo ");

                PreencherTabelaFatura("select * from fatura where idturno = " + this.id_turno + " order by idfactura");
                this.PreencherTabelaNC("Select nota_de_credito.*, designacao from nota_de_credito, cliente "
                        + "WHERE nota_de_credito.idcliente = cliente.idcliente AND idturno = " + this.id_turno + " "
                        + " order by nota_de_credito.data desc");

                PreencherTableCaixaSaida("SELECT idsaida, saida_de_caixa.valor, saida_de_caixa.saldo_restante, saida_de_caixa.obs, saida_de_caixa.objectivo, nome, saida_de_caixa.data\n"
                        + "  FROM saida_de_caixa, utilizador, turno WHERE saida_de_caixa.idutilizador= utilizador.idutilizador\n"
                        + "  AND turno.idutilizador = utilizador.idutilizador \n"
                        + "   AND idturno = " + this.id_turno);

                PreencherTabelaLiquidacao("select liquidacao.*, fatura.indicativo from liquidacao, fatura \n"
                        + "where liquidacao.idfatura = fatura.idfactura AND liquidacao.idturno = " + this.id_turno + " ;");

                PreencherTabela("select * from recibo where idturno = " + this.id_turno + " order by idfatura_recibo");

                JOptionPane.showMessageDialog(null, "Turno fechado com sucesso.", "Novo turno", JOptionPane.INFORMATION_MESSAGE);
                this.BusacrTurno();
                if (!jbEntrar.getText().equals("Imprimir")) {
                    new Principal4(id_utilizador, perfil, liga.getCaminho()).setVisible(true);
                    this.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível fechar o turno.", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Insira o valor final.", "Não foi possível abrir o turno.", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jbEntrarActionPerformed

    private void jbEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbEntrarKeyPressed
//
//        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
//            jLabel6.setText("Capslock ligado");
//        }else{
//            jLabel6.setText("Capslock desligado");
//        }
//        if (evt.getKeyCode() == evt.VK_ENTER) {
//            int a = 0;
//            int b = 0;
//            //pegar os valores dos campos
//            String Y = jTextField1.getText();
//            String O = jPasswordField1.getText();
//            //Verificar se o jTcampo1 esta vazio
//            if (jTextField1.equals("")) {
//
//                JOptionPane.showMessageDialog(this, "Digite o nome");
//                jTextField1.setBackground(Color.red);
//                jTextField1.setBackground(Color.white);
//                jTextField1.requestFocus(); //coloca o foco no campo
//            } else {
//                //se não estiver vazio atribui o valor 1 a variavel "a"
//                a = 1;
//                ////volta com a cor pardrão
//                jTextField1.setBackground(Color.white);
//                jTextField1.setForeground(Color.black);
//            }
//            // aqui verifica se as variaveis "a" e "b" estão com valores "1"
//            if (a == 1 & b == 1) {
//                //se estiver todos com valor "1" então o processo é concluido
//                // aqui vai o código salvar os dados no banco
//                JOptionPane.showMessageDialog(this, "salvo com sucesso");
//            }
//            if (jTextField1.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Nome do usuário inválido");
//                jTextField1.requestFocus();
//            } else if (jPasswordField1.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Senha inválida");
//                jPasswordField1.requestFocus();
//            } else {
//                liga.conexao();
//                try {
//                    liga.executeSql("select * from utilizador where nome_utilizador ='" + jTextField1.getText() + "'");
//                    liga.rs.first();
//                    if (liga.rs.getString("senha").equals(jPasswordField1.getText())) {
//                        Principal l = new Principal(liga.rs.getInt("idutilizador"), liga.rs.getString("perfil"));
//                        l.setVisible(true);
//                        dispose();
//                        //               //                JOptionPane.showMessageDialog(null, "Bem Vindo "+jTextField1_Usuario.getText());
//                    } else {
//                        jPasswordField1.requestFocus();
//                        jPasswordField1.setBackground(Color.RED);
//                        JOptionPane.showMessageDialog(null, "Acesso Negado \n Senha inválida");
//                    }
//                } catch (SQLException ex) {
//                    jTextField1.requestFocus();
//                    jTextField1.setBackground(Color.RED);
//                    JOptionPane.showMessageDialog(null, "Acesso Negado \n Nome do usuário inválido \n");
//                }
//            }
//        }
    }//GEN-LAST:event_jbEntrarKeyPressed

    private void jbEntrarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbEntrarKeyTyped
//        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
//            jLabel6.setText("Capslock ligado");
//        }else{
//            jLabel6.setText("Capslock desligado");
//        }
    }//GEN-LAST:event_jbEntrarKeyTyped

    private void jbSairMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbSairMouseEntered
        this.jbSair.setBackground(new Color(235, 235, 235));
        this.jbSair.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_jbSairMouseEntered

    private void jbSairMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbSairMouseExited
        this.jbSair.setBackground(new Color(217, 81, 51));
        this.jbSair.setForeground(Color.WHITE);
    }//GEN-LAST:event_jbSairMouseExited

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
        if (this.id_utilizador == -1) {
            new Registo_de_turno(liga.getCaminho()).setVisible(true);
            this.dispose();
        } else {
            new Principal4(id_utilizador, perfil, liga.getCaminho()).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jbSairActionPerformed

    private void jbSairKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbSairKeyPressed
//        if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)){
//            jLabel6.setText("Capslock ligado");
//        }else{
//            jLabel6.setText("Capslock desligado");
//        }
//        if (evt.getKeyCode() == evt.VK_ENTER) {
//            System.exit(0);
//        }
    }//GEN-LAST:event_jbSairKeyPressed

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        PreencherTabela("select * from recibo where idturno = " + this.id_turno + " order by idfatura_recibo");
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jRadioButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton4MouseClicked
        PreencherTabelaLiquidacao("select liquidacao.*, fatura.indicativo from liquidacao, fatura \n"
                + "where liquidacao.idfatura = fatura.idfactura AND liquidacao.idturno = " + this.id_turno + " ;");
    }//GEN-LAST:event_jRadioButton4MouseClicked

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        PreencherTabelaFatura("select * from fatura where idturno = " + this.id_turno + " order by idfactura");

    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
        this.PreencherTabelaNC("Select nota_de_credito.*, designacao from nota_de_credito, cliente "
                + "WHERE nota_de_credito.idcliente = cliente.idcliente AND idturno = " + this.id_turno + " "
                + " order by nota_de_credito.data desc");
    }//GEN-LAST:event_jRadioButton3MouseClicked

    private void jRadioButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton5MouseClicked
        PreencherTableCaixaSaida("SELECT idsaida, saida_de_caixa.valor, saida_de_caixa.saldo_restante, saida_de_caixa.obs, saida_de_caixa.objectivo, nome, saida_de_caixa.data\n"
                + "  FROM saida_de_caixa, utilizador, turno WHERE saida_de_caixa.idutilizador= utilizador.idutilizador\n"
                + "  AND turno.idutilizador = utilizador.idutilizador \n"
                + "   AND idturno = " + this.id_turno);
    }//GEN-LAST:event_jRadioButton5MouseClicked

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
            java.util.logging.Logger.getLogger(FecharTurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FecharTurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FecharTurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FecharTurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FecharTurno().setVisible(true);
            }
        });
    }

    private boolean Fechar() {

        if (jTextField1.getText().equals("Imprimir")) {
            return true;
        }

        int value = this.numero;
//        System.out.println("numero: "+value+" iduser: "+this.id_utilizador);

        liga.conexao();
        try {

            PreparedStatement pst = liga.con.prepareStatement(""
                    + "Update turno set saldo_final = " + Double.parseDouble(this.jTextField1.getText())
                    + ",  estado= false,"
                    + " data_saida = current_date, horasaida = current_time"
                    + " where idutilizador =" + this.id_utilizador);

            pst.execute();
//            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            liga.deconecta();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados do turni não Inseridos: \n" + ex, "Erro ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        return false;
    }

    public void BusacrTurno() {
        liga.conexao();
        liga.executeSql("select *  from turno where idturno = " + this.id_turno + " ;");
        try {
            if (liga.rs.first()) {
                try {
                    if (this.id_utilizador == -1) {

                    }
                    boolean estado = liga.rs.getBoolean("estado");
                    String Sestado = "Fechado";
                    if (estado) {
                        Sestado = "Aberto";
                    }
                    String data_i, data_f;
                    data_i = liga.rs.getString("data_entrada") + " " + liga.rs.getString("hora_entrada");
                    data_f = liga.rs.getString("data_saida") + " " + liga.rs.getString("horasaida");
                    double fatura = liga.rs.getDouble("fatura");
                    double recibo = liga.rs.getDouble("recibo");
                    double fatura_recibo = liga.rs.getDouble("fatura_recibo");

                    double nota_de_credito = liga.rs.getDouble("nota_de_credito");
                    double valor_inicial = liga.rs.getDouble("saldo_inicial");
                    double saida_em_caixa = liga.rs.getDouble("saida_de_caixa");
                    double banco = liga.rs.getDouble("bancos");
                    double numerario = liga.rs.getDouble("numerario");
                    double saldo = liga.rs.getDouble("saldo_atual");
                    double saldo_informado = liga.rs.getDouble("saldo_final");
                    double direrenca = saldo_informado - saldo;
                    String dif = direrenca + "";
                    if (direrenca > 0) {
                        dif = direrenca + " (Excesso)";
                    }
                    if (direrenca < 0) {
                        dif = direrenca + " (Quebra)";
                    }

//                    liga.rs.getString("entrada_em_caixa"),
//                    liga.rs.getString("nota_de_pagamento"),
//                      liga.rs.getString("saldo_atual"),
//                    liga.rs.getString("idturno")
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
                    chooser.setFileFilter(filter);
                    chooser.setDialogTitle("Guardar Arquivo");
                    chooser.setSelectedFile(new File("Fecho do turno Nº " + this.id_turno));
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        String file = chooser.getSelectedFile().toString().concat(".pdf");
                        try {
                            ModeloPDF e = new ModeloPDF(new File(file), null, "Documento", liga.getCaminho());
                            e.setME(ME);
                            e.setMU(MU);

                            e.Fecho("A4", numero, "ORIGINAL", Sestado, data_i, data_f,
                                    fatura, recibo, fatura_recibo,
                                    nota_de_credito, valor_inicial, saida_em_caixa,
                                    banco, numerario, saldo, saldo_informado, dif,
                                    dadosFT, colunasFT,
                                    dadosFR, colunasFR,
                                    dadosNC, colunasNC,
                                    dadosRC, colunasRC,
                                    dadosSD, colunasSD,
                                    dadosPR, colunasPR);
                            JOptionPane.showMessageDialog(null, "Os Dados Foram Gravados no Directorio Selecionado", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e) {
                            Logger.getLogger(FecharTurno.class.getName()).log(Level.SEVERE, null, e);

                            JOptionPane.showMessageDialog(null, "Ocorreu um erro: \n" + e, " Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro: \n" + ex, " Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro: \n" + ex, " Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void PreencherTabela(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Título", "Desc.(Kz)", "Data", "Hora", "Ret. Fonte", "Total(Kz)", "Pagamento(Kz)", "troco"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {

                    dados.add(new Object[]{"FR " + liga.rs.getString("indicativo") + " " + liga.rs.getString("idfatura_recibo"), liga.rs.getString("titulo_da_fatura"), liga.rs.getString("desconto"), liga.rs.getString("data"), liga.rs.getString("hora"), liga.rs.getString("retencao_na_fonte"), liga.rs.getString("total"), liga.rs.getString("valor_pago"), liga.rs.getString("Troco")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela.setModel(modelo);
        T_Tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        T_Tabela.getColumnModel().getColumn(0).setResizable(true);
        T_Tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(1).setResizable(true);
        T_Tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
        T_Tabela.getColumnModel().getColumn(2).setResizable(true);
        T_Tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(4).setResizable(true);
        T_Tabela.getColumnModel().getColumn(5).setPreferredWidth(160);
        T_Tabela.getColumnModel().getColumn(5).setResizable(true);
        T_Tabela.getColumnModel().getColumn(6).setPreferredWidth(160);
        T_Tabela.getColumnModel().getColumn(6).setResizable(true);
        T_Tabela.getColumnModel().getColumn(7).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(7).setResizable(true);
        T_Tabela.getColumnModel().getColumn(8).setPreferredWidth(160);
        T_Tabela.getColumnModel().getColumn(8).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(9).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(9).setResizable(false);

        T_Tabela.getTableHeader().setReorderingAllowed(false);
        T_Tabela.setAutoResizeMode(T_Tabela.AUTO_RESIZE_ALL_COLUMNS);
//        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.dadosFR = dados;
        this.colunasFR = colunas;

        liga.deconecta();
    }

    public void PreencherTabelaFatura(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Título", "Desc.(Kz)", "Data", "Hora", "Ret. Fonte", "Dívida(Kz)", "Liquidação(%)"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {

                    dados.add(new Object[]{"FT " + liga.rs.getString("indicativo") + " " + liga.rs.getString("idfactura"), liga.rs.getString("titulo_da_fatura"), liga.rs.getString("desconto"), liga.rs.getString("data"), liga.rs.getString("hora"), liga.rs.getString("retencao_na_fonte"), liga.rs.getString("total"), liga.rs.getString("liquidacao")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados \n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela.setModel(modelo);
        T_Tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        T_Tabela.getColumnModel().getColumn(0).setResizable(true);
        T_Tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(1).setResizable(true);
        T_Tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
        T_Tabela.getColumnModel().getColumn(2).setResizable(true);
        T_Tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(4).setResizable(true);
        T_Tabela.getColumnModel().getColumn(5).setPreferredWidth(160);
        T_Tabela.getColumnModel().getColumn(5).setResizable(true);
        T_Tabela.getColumnModel().getColumn(6).setPreferredWidth(160);
        T_Tabela.getColumnModel().getColumn(6).setResizable(true);
        T_Tabela.getColumnModel().getColumn(7).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(7).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(8).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(8).setResizable(false);
//        JTableColaboradores.getColumnModel().getColumn(9).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(9).setResizable(false);

        T_Tabela.getTableHeader().setReorderingAllowed(false);
        T_Tabela.setAutoResizeMode(T_Tabela.AUTO_RESIZE_ALL_COLUMNS);
//        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dadosFT = dados;
        this.colunasFT = colunas;
        liga.deconecta();
    }

    public void PreencherTabelaProforma(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Título", "Desc.(Kz)", "Data", "Hora", "Ret. Fonte", "Total(Kz)"};;
        liga.conexao();
        liga.executeSql(Sql);

        try {
            liga.rs.first();
            do {

                dados.add(new Object[]{liga.rs.getString("idproforma"), liga.rs.getString("titulo_da_fatura"), liga.rs.getString("desconto"), liga.rs.getString("data"), liga.rs.getString("hora"), liga.rs.getString("retencao_na_fonte"), liga.rs.getString("total")});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela.setModel(modelo);
        T_Tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        T_Tabela.getColumnModel().getColumn(0).setResizable(true);
        T_Tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(1).setResizable(true);
        T_Tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
        T_Tabela.getColumnModel().getColumn(2).setResizable(true);
        T_Tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela.getColumnModel().getColumn(4).setPreferredWidth(140);
        T_Tabela.getColumnModel().getColumn(4).setResizable(true);
        T_Tabela.getColumnModel().getColumn(5).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(5).setResizable(true);
        T_Tabela.getColumnModel().getColumn(6).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(6).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(7).setPreferredWidth(100);
//        JTableColaboradores.getColumnModel().getColumn(7).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(8).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(8).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(9).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(9).setResizable(false);

        T_Tabela.getTableHeader().setReorderingAllowed(false);
        T_Tabela.setAutoResizeMode(T_Tabela.AUTO_RESIZE_ALL_COLUMNS);
//        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    public void PreencherTabelaLiquidacao(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Título", "Obectivo", "Observacao", "Data e Hora", "Valor da liquidação(Kz)", "Referente a fatura Nº", "Total a liquidar(Kz)"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{"RC " + liga.rs.getString("indicativo") + " " + liga.rs.getString("idliquidacao"), "Liquidação", liga.rs.getString("objectivo"), liga.rs.getString("observacao"), liga.rs.getString("data"), liga.rs.getString("valor"), "FT " + liga.rs.getString(13) + " " + liga.rs.getString("idfatura"), liga.rs.getString("total")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados \n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela.setModel(modelo);
        T_Tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        T_Tabela.getColumnModel().getColumn(0).setResizable(true);
        T_Tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(1).setResizable(true);
        T_Tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
        T_Tabela.getColumnModel().getColumn(2).setResizable(true);
        T_Tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela.getColumnModel().getColumn(4).setPreferredWidth(140);
        T_Tabela.getColumnModel().getColumn(4).setResizable(true);
        T_Tabela.getColumnModel().getColumn(5).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(5).setResizable(true);
        T_Tabela.getColumnModel().getColumn(6).setPreferredWidth(180);
        T_Tabela.getColumnModel().getColumn(6).setResizable(true);
        T_Tabela.getColumnModel().getColumn(7).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(7).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(8).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(8).setResizable(true);
//        JTableColaboradores.getColumnModel().getColumn(9).setPreferredWidth(160);
//        JTableColaboradores.getColumnModel().getColumn(9).setResizable(false);

        T_Tabela.getTableHeader().setReorderingAllowed(false);
        T_Tabela.setAutoResizeMode(T_Tabela.AUTO_RESIZE_ALL_COLUMNS);
//        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dadosRC = dados;
        this.colunasRC = colunas;

        liga.deconecta();
    }

//    public void PreencherTabelaVendas(String Sql) {
//
//        ArrayList dados = new ArrayList();
//        String[] colunas = new String[]{"Código", "Tipo", "Descrição", "Preco unitário(Kz)", "Quant.", "Total(Kz)", "IVA", "motivo", "Pagamento(Kz)", "troco"};
//        liga.conexao();
//        liga.executeSql(Sql);
//
//        try {
//            liga.rs.first();
//            do {
//
//                dados.add(new Object[]{liga.rs.getString("codigo"), liga.rs.getString("tipo"), liga.rs.getString("descricao"), liga.rs.getString("preco_unitario"), liga.rs.getString("quantidade"), liga.rs.getString("total"), liga.rs.getString("iva"), liga.rs.getString("motivo"), liga.rs.getString("recibo.valor_pago"), liga.rs.getString("recibo.troco")});
//            } while (liga.rs.next());
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados", null, JOptionPane.WARNING_MESSAGE);
//        }
//        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
//        };
//        T_Tabela1.setModel(modelo);
//        T_Tabela1.getColumnModel().getColumn(0).setPreferredWidth(100);
//        T_Tabela1.getColumnModel().getColumn(0).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(1).setPreferredWidth(100);
//        T_Tabela1.getColumnModel().getColumn(1).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(2).setPreferredWidth(200);
//        T_Tabela1.getColumnModel().getColumn(2).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(3).setPreferredWidth(120);
//        T_Tabela1.getColumnModel().getColumn(3).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(4).setPreferredWidth(50);
//        T_Tabela1.getColumnModel().getColumn(4).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(5).setPreferredWidth(120);
//        T_Tabela1.getColumnModel().getColumn(5).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(6).setPreferredWidth(100);
//        T_Tabela1.getColumnModel().getColumn(6).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(7).setPreferredWidth(160);
//        T_Tabela1.getColumnModel().getColumn(7).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(8).setPreferredWidth(160);
//        T_Tabela1.getColumnModel().getColumn(8).setResizable(true);
//        T_Tabela1.getColumnModel().getColumn(9).setPreferredWidth(160);
//        T_Tabela1.getColumnModel().getColumn(9).setResizable(false);
//
//        T_Tabela1.getTableHeader().setReorderingAllowed(false);
//        T_Tabela1.setAutoResizeMode(T_Tabela1.AUTO_RESIZE_ALL_COLUMNS);
////        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//
//        liga.deconecta();
//    }
    public void PreencherTabelaNC(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Motivo", "Obs.", "Valor", "Data", "Cliente", "Doc"};
        
        liga.conexao();
        liga.executeSql("Select nota_de_credito.*, designacao,recibo.indicativo from nota_de_credito, cliente, recibo \n"
                + "WHERE nota_de_credito.idcliente = cliente.idcliente \n"
                + "AND recibo.idfatura_recibo = nota_de_credito.idfactura \n"
                + "AND origem = 1 order by nota_de_credito.data desc");

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{"NC "+liga.rs.getString("indicativo")+" "+liga.rs.getString("idnota_de_credito"), liga.rs.getString("objectivo"), liga.rs.getString("observacao"), liga.rs.getString("valor"), liga.rs.getString("data"), liga.rs.getString("designacao"), "FR "+liga.rs.getString(13)+" "+liga.rs.getString("idfactura")});
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
                    dados.add(new Object[]{"NC "+liga.rs.getString("indicativo")+" "+liga.rs.getString("idnota_de_credito"), liga.rs.getString("objectivo"), liga.rs.getString("observacao"), liga.rs.getString("valor"), liga.rs.getString("data"), liga.rs.getString("designacao"), "FT "+liga.rs.getString(13)+" "+liga.rs.getString("idfactura")});
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados.\n" + ex, "", JOptionPane.WARNING_MESSAGE);
        }
        
        // fim
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela.setModel(modelo);
        T_Tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        T_Tabela.getColumnModel().getColumn(0).setResizable(true);
        T_Tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        T_Tabela.getColumnModel().getColumn(1).setResizable(true);
        T_Tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        T_Tabela.getColumnModel().getColumn(2).setResizable(true);
        T_Tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
        T_Tabela.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
        T_Tabela.getColumnModel().getColumn(4).setResizable(true);
        T_Tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
        T_Tabela.getColumnModel().getColumn(5).setResizable(true);
        T_Tabela.getColumnModel().getColumn(6).setPreferredWidth(40);
        T_Tabela.getColumnModel().getColumn(6).setResizable(true);
        T_Tabela.getTableHeader().setReorderingAllowed(false);
        T_Tabela.setAutoResizeMode(T_Tabela.AUTO_RESIZE_ALL_COLUMNS);
        T_Tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dadosNC = dados;
        this.colunasNC = colunas;
        liga.deconecta();
    }

    public void PreencherTableCaixaSaida(String Sql) {
        ArrayList dados = new ArrayList();

        String[] colunas = new String[]{"Nº", "Valor (KZ)", "Saldo restante (Kz)", "OBS.", "Objectivo", "data", "Utilizador"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
            if (liga.rs.first()) {
                do {

                    dados.add(new Object[]{liga.rs.getString("idsaida"), liga.Chang(liga.rs.getString("valor")), liga.Chang(liga.rs.getString("saldo_restante")), liga.rs.getString("obs"), liga.rs.getString("objectivo"), liga.rs.getString("data"), liga.rs.getString("nome")});

                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados\n " + ex, null, JOptionPane.WARNING_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela.setModel(modelo);

        T_Tabela.getTableHeader().setReorderingAllowed(false);
        T_Tabela.setAutoResizeMode(T_Tabela.AUTO_RESIZE_ALL_COLUMNS);
        T_Tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dadosSD = dados;
        this.colunasSD = colunas;

        liga.deconecta();
    }

    public void PreencherTableVenda(String Sql1, String Sql2) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Nº", "Tipo", "Descrição", "Codigo", "P. Unitário", "Qnt. vendida", "Nº Doc", "Data", "Hora", "IVA",};
        liga.conexao();
        liga.executeSql(Sql1);

        try {
            if (liga.rs.first()) {
                do {
                    dados.add(new Object[]{liga.rs.getString("idvenda"), liga.rs.getString("tipo"),
                        liga.rs.getString("descricao"), liga.rs.getString("codigo"), liga.Chang(liga.rs.getDouble("preco_unitario")),
                        liga.rs.getInt("quantidade"), "FR " + liga.rs.getString("indicativo") + " " + liga.rs.getInt("idfactura"), liga.rs.getString("data"), liga.rs.getString("hora"), liga.rs.getString("iva")});

                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados \n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        ControloBD liga2 = new ControloBD();
        liga2.setCaminho(liga.getCaminho());
        liga2.conexao();
        liga2.executeSql(Sql2);

        try {
            if (liga2.rs.first()) {
                do {
                    dados.add(new Object[]{liga2.rs.getString("idvenda"), liga2.rs.getString("tipo"),
                        liga2.rs.getString("descricao"), liga2.rs.getString("codigo"),
                        liga2.Chang(liga2.rs.getDouble("preco_unitario")),
                        liga2.rs.getInt("quantidade"), "FT " + liga2.rs.getString("indicativo") + " " + liga2.rs.getInt("idfactura"),
                        liga2.rs.getString("data"), liga2.rs.getString("hora"), liga2.rs.getString("iva")});

                } while (liga2.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados \n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        T_Tabela1.setModel(modelo);
        T_Tabela1.getColumnModel().getColumn(0).setPreferredWidth(40);
        T_Tabela1.getColumnModel().getColumn(0).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(1).setPreferredWidth(70);
        T_Tabela1.getColumnModel().getColumn(1).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(2).setPreferredWidth(100);
        T_Tabela1.getColumnModel().getColumn(2).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(3).setPreferredWidth(60);
        T_Tabela1.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(3).setPreferredWidth(70);
        T_Tabela1.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(3).setPreferredWidth(70);
        T_Tabela1.getColumnModel().getColumn(3).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(4).setPreferredWidth(70);
        T_Tabela1.getColumnModel().getColumn(4).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(5).setPreferredWidth(70);
        T_Tabela1.getColumnModel().getColumn(5).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(6).setPreferredWidth(70);
        T_Tabela1.getColumnModel().getColumn(6).setResizable(true);
        T_Tabela1.getColumnModel().getColumn(7).setPreferredWidth(40);
        T_Tabela1.getColumnModel().getColumn(7).setResizable(true);
        T_Tabela1.getTableHeader().setReorderingAllowed(false);
        T_Tabela1.setAutoResizeMode(T_Tabela1.AUTO_RESIZE_ALL_COLUMNS);
//        jTable_terceirizador.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.dadosPR = dados;
        this.colunasPR = colunas;

        liga.deconecta();
    }
//    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable T_Tabela;
    private javax.swing.JTable T_Tabela1;
    private javax.swing.ButtonGroup buttonGroupDocumentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jbEntrar;
    private javax.swing.JButton jbSair;
    // End of variables declaration//GEN-END:variables

}

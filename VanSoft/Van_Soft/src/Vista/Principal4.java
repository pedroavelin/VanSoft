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
import Controle.Tempo;
import Controle.xmldoc;
import Modelo.ModeloUtilizador;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import Modelo.ModeloTabela;
import Modelo.SendMailUsingAuthentication;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.ListSelectionModel;
import org.xml.sax.SAXException;

/**
 *
 * @author Gonga
 */
public class Principal4 extends javax.swing.JFrame {

    private int id;
    private String perfil;
    ControloBD liga = new ControloBD();
    Tempo tempo = new Tempo();
    Extenso exte = new Extenso();

    /**
     * Creates new form Principal2
     */
    public Principal4() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);

    }

    public Principal4(int idutilizador, String perfil, String caminho) {
        initComponents();
        liga.setCaminho(caminho);
//        this.jPanel_registo_de_turnos.setVisible(false);
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.jPanel_meu_turno.setVisible(true);
        this.id = idutilizador;
        this.perfil = perfil;
        this.exte.setMeoda(" Kwanza(s)");

        jLabel_nome_utilizador.setText(this.getNomeUser(this.id, this.perfil));

        liga.conexao();
        int numero = 0;
        int id_turno = -1;
        this.jLabel_turno.setText("Turno Nº " + numero);
        this.jLabel_utlilizador.setText("Utilizador: " + this.jLabel_nome_utilizador.getText());

        liga.executeSql("select *  from turno where idutilizador = " + this.id+" Order by idturno asc");
        try {

            if (liga.rs.last()) {

                boolean estado = liga.rs.getBoolean("estado");
                numero = liga.rs.getInt("numero");
                this.jLabel_turno.setText("Turno Nº " + numero);

                this.fatura.setText("Factura: " + this.exte.Chang(liga.rs.getString("fatura"))+this.exte.getMeoda());
                this.recibo.setText("Recibo: " + this.exte.Chang(liga.rs.getString("recibo"))+this.exte.getMeoda());
                this.fatura_recibo.setText("Factura recibo: " + this.exte.Chang(liga.rs.getString("fatura_recibo"))+this.exte.getMeoda());
                this.saida_de_caixa.setText("Saida de caixa: " + this.exte.Chang(liga.rs.getString("saida_de_caixa"))+this.exte.getMeoda());
                this.entrada_em_caixa.setText("Entrada em caixa: " + this.exte.Chang(liga.rs.getString("entrada_em_caixa"))+this.exte.getMeoda());
                this.nota_de_credito.setText("Nota de credito: " + this.exte.Chang(liga.rs.getString("nota_de_credito"))+this.exte.getMeoda());
                this.nota_de_pagamento.setText("Nota de pagamento: " + this.exte.Chang(liga.rs.getString("nota_de_pagamento"))+this.exte.getMeoda());
                this.numerario.setText("Numerário: " + this.exte.Chang(liga.rs.getString("numerario"))+this.exte.getMeoda());
                this.bancos.setText("Banco: " + this.exte.Chang(liga.rs.getString("bancos"))+this.exte.getMeoda());
                this.saldo_inicial.setText("Saldo inicial: " + this.exte.Chang(liga.rs.getString("saldo_inicial"))+this.exte.getMeoda());
                double vaIfo, valAtu;
                vaIfo = liga.rs.getDouble("saldo_final");
                valAtu = liga.rs.getDouble("saldo_atual");
                
//                this.exten.setMeoda(" Kwanza");
//                this.exten.setNumber(valor);
//                String ValorPago = this.exten.show();
//                this.exten.setNumber(valorP);
                
                this.saldo_informado.setText("Valor informado: " + this.exte.Chang(liga.rs.getString("saldo_final"))+this.exte.getMeoda());
                this.saldo_atual.setText("Saldo: " + this.exte.Chang(liga.rs.getString("saldo_atual"))+this.exte.getMeoda());
                
                id_turno = liga.rs.getInt("idturno");
                if (estado) {
                    this.jLabel_estado.setText("Estado: Aberto");
                    this.jLabel_abertura.setText("Data de abertura: " + liga.rs.getString("data_entrada") + " " + liga.rs.getString("hora_entrada"));
                    this.jLabel_fecho.setText("_");
//                    JOptionPane.showMessageDialog(null, "O turno Nº " + numero + " já se encontra aberto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    this.jButton1.setEnabled(false);
                    this.jButton2.setEnabled(true);
                    this.saldo_informado.setVisible(false);
                    this.jLabel_info.setVisible(false);
                } else {
//                    new abrir_turno(id_turno,numero,this.id);
                    this.saldo_informado.setVisible(true);
                    this.jLabel_info.setVisible(true);
                    double result = vaIfo-valAtu;
                    if (vaIfo>valAtu){
                        
                        this.jLabel_info.setText("Resultado: "+this.exte.Chang(result)+this.exte.getMeoda()+" Valor em Excesso");
                    }else if (vaIfo<valAtu){
                         
                         this.jLabel_info.setText("Resultado: "+this.exte.Chang(result)+this.exte.getMeoda()+" Valor em Falta");
                    }else{
                        this.jLabel_info.setText("Resultado: "+this.exte.Chang(result)+this.exte.getMeoda()+" ");
                    }
                    this.jButton1.setEnabled(true);
                    this.jButton2.setEnabled(false);
                    this.jLabel_estado.setText("Estado: Fechado");
                    this.jLabel_abertura.setText("Data de abertura: " + liga.rs.getString("data_entrada") + " " + liga.rs.getString("hora_entrada"));
                    this.jLabel_fecho.setText("Data de fecho: " + liga.rs.getString("data_saida") + " " + liga.rs.getString("horasaida"));

                }

            } else {
                this.saldo_informado.setVisible(false);
                 this.jLabel_info.setVisible(false);
                this.jLabel_estado.setText("Estado: Fechado");
                this.jLabel_abertura.setText("_");
                this.jLabel_fecho.setText("_");
                this.fatura.setText("_");
            this.recibo.setText("_");
            this.fatura_recibo.setText("_");
            this.saida_de_caixa.setText("_");
            this.entrada_em_caixa.setText("_");
            this.nota_de_credito.setText("_");
            this.nota_de_pagamento.setText("_");
            this.numerario.setText("Numerário: 0");
            this.bancos.setText("Banco: 0");
            this.saldo_inicial.setText("Saldo inicial: 0" );
            this.saldo_atual.setText("Saldo: 0");
                this.jButton1.setEnabled(true);
                this.jButton2.setEnabled(false);
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro: Consulte o suporte técnico do aplicativo", JOptionPane.ERROR_MESSAGE);
        }
        liga.deconecta();
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
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jIF_turnos = new javax.swing.JInternalFrame();
        jPanel_meu_turno = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jLabel_turno = new javax.swing.JLabel();
        jLabel_utlilizador = new javax.swing.JLabel();
        jLabel_estado = new javax.swing.JLabel();
        jLabel_abertura = new javax.swing.JLabel();
        jLabel_fecho = new javax.swing.JLabel();
        fatura = new javax.swing.JLabel();
        recibo = new javax.swing.JLabel();
        fatura_recibo = new javax.swing.JLabel();
        saida_de_caixa = new javax.swing.JLabel();
        saldo_inicial = new javax.swing.JLabel();
        bancos = new javax.swing.JLabel();
        numerario = new javax.swing.JLabel();
        nota_de_pagamento = new javax.swing.JLabel();
        nota_de_credito = new javax.swing.JLabel();
        entrada_em_caixa = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        saldo_atual = new javax.swing.JLabel();
        saldo_informado = new javax.swing.JLabel();
        jLabel_info = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jPanel15 = new javax.swing.JPanel();
        jLabel_Logotipo = new javax.swing.JLabel();
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
        jLabel_nome_utilizador = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 625));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jIF_turnos.setBackground(new java.awt.Color(255, 255, 255));
        jIF_turnos.setVisible(true);

        jPanel_meu_turno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel_meu_turno.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(27, 167, 125));

        jLabel37.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Meu turno");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 1090, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel_meu_turno.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, -1));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Descrição");

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Itens:");

        jLabel_turno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_turno.setText("Turno nº");

        jLabel_utlilizador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_utlilizador.setText("Utlilizador");

        jLabel_estado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_estado.setText("jLabel21");

        jLabel_abertura.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_abertura.setText("jLabel22");

        jLabel_fecho.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_fecho.setText("jLabel23");

        fatura.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fatura.setText("jLabel24");

        recibo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        recibo.setText("jLabel25");

        fatura_recibo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fatura_recibo.setText("jLabel27");

        saida_de_caixa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saida_de_caixa.setText("jLabel28");

        saldo_inicial.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        saldo_inicial.setText("jLabel27");

        bancos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bancos.setText("jLabel25");

        numerario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        numerario.setText("jLabel24");

        nota_de_pagamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nota_de_pagamento.setText("jLabel23");

        nota_de_credito.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nota_de_credito.setText("jLabel22");

        entrada_em_caixa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        entrada_em_caixa.setText("jLabel21");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_turno)
                    .addComponent(jLabel_utlilizador)
                    .addComponent(jLabel_estado)
                    .addComponent(jLabel_abertura)
                    .addComponent(jLabel_fecho)
                    .addComponent(fatura)
                    .addComponent(recibo)
                    .addComponent(fatura_recibo)
                    .addComponent(saida_de_caixa)
                    .addComponent(entrada_em_caixa)
                    .addComponent(nota_de_credito)
                    .addComponent(nota_de_pagamento)
                    .addComponent(numerario)
                    .addComponent(bancos)
                    .addComponent(saldo_inicial)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(203, Short.MAX_VALUE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel_turno)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_utlilizador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_estado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_abertura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_fecho)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fatura)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recibo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fatura_recibo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saida_de_caixa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entrada_em_caixa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nota_de_credito)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nota_de_pagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numerario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bancos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saldo_inicial)
                .addGap(21, 21, 21))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Abrir turno");
        jButton1.setToolTipText("Abrir um novo turno");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setText("Fechar turno");
        jButton2.setToolTipText("Fechar o turno atualmente aberto.");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        saldo_atual.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        saldo_atual.setText("jLabel28");

        saldo_informado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        saldo_informado.setText("jLabel28");

        jLabel_info.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_info.setText("jLabel15");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(saldo_atual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saldo_informado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(316, 316, 316))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(saldo_informado, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saldo_atual, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jLabel_info, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(269, 269, 269))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_meu_turno.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1090, 380));

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_property_time_90px.png"))); // NOI18N
        jMenu5.setText("Turno");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenu5.add(jSeparator3);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Meu turno");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);
        jMenu5.add(jSeparator4);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Confirmar turno");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);
        jMenu5.add(jSeparator5);
        jMenu5.add(jSeparator6);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Registo de turnos");
        jMenuItem9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jMenuItem9ItemStateChanged(evt);
            }
        });
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuBar1.add(jMenu5);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_saftAO_90px.png"))); // NOI18N
        jMenu1.setText("SAFT-AO");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jIF_turnos.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jIF_turnosLayout = new javax.swing.GroupLayout(jIF_turnos.getContentPane());
        jIF_turnos.getContentPane().setLayout(jIF_turnosLayout);
        jIF_turnosLayout.setHorizontalGroup(
            jIF_turnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jIF_turnosLayout.createSequentialGroup()
                .addComponent(jPanel_meu_turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jIF_turnosLayout.setVerticalGroup(
            jIF_turnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jIF_turnosLayout.createSequentialGroup()
                .addComponent(jPanel_meu_turno, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.add(jIF_turnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 550));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 900, 20));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1130, 550));

        jLabel_Logotipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Logotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/LogoApp.png"))); // NOI18N
        jPanel1.add(jLabel_Logotipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 100));

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

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 220, 40));

        jPanel6.setBackground(new java.awt.Color(54, 70, 78));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));
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

        jLabel_nome_utilizador.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel_nome_utilizador.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_nome_utilizador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_nome_utilizador.setText("Nome de utilizador");
        jPanel2.add(jLabel_nome_utilizador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 220, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setForeground(new java.awt.Color(54, 70, 78));
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
        jLabel17.setForeground(new java.awt.Color(54, 70, 78));
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

        jLabel26.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Copyright©VanSoft");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 220, -1));

        jPanel25.setBackground(new java.awt.Color(54, 70, 78));
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel25.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Suporte");
        jPanel25.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel26.setBackground(new java.awt.Color(54, 70, 78));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel26.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Suporte");
        jPanel26.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel25.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 220, 40));

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

        jLabel42.setBackground(new java.awt.Color(255, 255, 255));
        jLabel42.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Dashboard");
        jPanel31.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_graph_report_script_filled_40px.png"))); // NOI18N
        jPanel31.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 220, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 105, 220, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1352, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if (this.perfil.equals("Administrador") || this.perfil.equals("Secretário")) {
            JOptionPane.showMessageDialog(null, "Confirmar turno", " ", JOptionPane.INFORMATION_MESSAGE);
        }
        if (this.perfil.equals("Operador de caixa")) {
            JOptionPane.showMessageDialog(null, "Esta função não é permitida ao operador de caixa.", " ", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jMenuItem9ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ItemStateChanged

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
//       this.jPanel_registo_de_turnos.setVisible(true);
//       this.jPanel_meu_turno.setVisible(false);
       new Registo_de_turno(liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
//        this.jPanel_Agenda.setVisible(false);
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
//       this.jPanel_registo_de_turnos.setVisible(false);
       this.jPanel_meu_turno.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
                int result = JOptionPane.showConfirmDialog(rootPane, "Essa operação pode levar muito tempo. \nMelhor realizar no final do dia.\nDeseja continuar?", "SAFT-AO", JOptionPane.YES_NO_OPTION);
                
                if (JOptionPane.YES_OPTION == result)
                {
                    new SAFTAO(liga.getCaminho()).setVisible(true);
                }
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        new Principal3(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
       // System.exit(0);
        new Entar().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        new Principal(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        new Principal2(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        new Principal1(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        this.dispose();
        new Principal4(this.id, this.perfil,liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        new suporte().setVisible(true);       // TODO add your handling code here:
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel37MouseClicked
        new Principal_stock(id, perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel37MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        liga.conexao();
        int numero = 0;
        int id_turno = -1;

        liga.executeSql("select *  from turno where idutilizador = " + this.id+" Order by idturno asc");
        try {
            if (liga.rs.last()) {
                boolean estado = liga.rs.getBoolean("estado");
                numero = liga.rs.getInt("numero");
                id_turno = liga.rs.getInt("idturno");
                if (estado) {
                    JOptionPane.showMessageDialog(null, "O turno Nº " + numero + " já se encontra aberto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    this.jButton1.setEnabled(true);
                    this.jButton2.setEnabled(false);
                } else {
                    int r = JOptionPane.showConfirmDialog(rootPane, "Deseja abrir o turno?", null, JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        new abrir_turno(id_turno, numero, this.id, this.perfil,liga.getCaminho()).setVisible(true);
                        this.dispose();
                    }
                }

            } else {
                int r = JOptionPane.showConfirmDialog(rootPane, "Deseja abrir o turno?", null, JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    new abrir_turno(id_turno, numero, this.id, this.perfil,liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
                this.jButton1.setEnabled(true);
                this.jButton2.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro: Consulte o suporte técnico do aplicativo", JOptionPane.ERROR_MESSAGE);
        }
        liga.deconecta();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        liga.conexao();
        int numero = 0;
        int id_turno = -1;

        liga.executeSql("select *  from turno where idutilizador = " + this.id+" Order by idturno asc");
        try {
            if (liga.rs.last()) {
                boolean estado = liga.rs.getBoolean("estado");
                numero = liga.rs.getInt("numero");
                id_turno = liga.rs.getInt("idturno");
                if (estado) {
                    int r = JOptionPane.showConfirmDialog(rootPane, "Deseja fechar o turno?", null, JOptionPane.YES_NO_OPTION);
                    if (r == JOptionPane.YES_OPTION) {
                        new FecharTurno(id_turno, numero, this.id, this.perfil,liga.getCaminho()).setVisible(true);
                        this.dispose();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "O turno Nº " + numero + " já se encontra fechado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    this.jButton1.setEnabled(false);
                    this.jButton2.setEnabled(true);

                }

            } else {

                this.jButton1.setEnabled(true);
                this.jButton2.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Erro: Consulte o suporte técnico do aplicativo", JOptionPane.ERROR_MESSAGE);
        }
        liga.deconecta();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel31MouseClicked
        new Dashboard(this.id, this.perfil,liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel31MouseClicked

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
            java.util.logging.Logger.getLogger(Principal4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal4().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bancos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupCorDoSistema;
    private javax.swing.ButtonGroup buttonGroupTipoDeLetra;
    private javax.swing.JLabel entrada_em_caixa;
    private javax.swing.JLabel fatura;
    private javax.swing.JLabel fatura_recibo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JInternalFrame jIF_turnos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
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
    private javax.swing.JLabel jLabel_abertura;
    private javax.swing.JLabel jLabel_estado;
    private javax.swing.JLabel jLabel_fecho;
    private javax.swing.JLabel jLabel_info;
    private javax.swing.JLabel jLabel_nome_utilizador;
    private javax.swing.JLabel jLabel_turno;
    private javax.swing.JLabel jLabel_utlilizador;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_meu_turno;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JLabel nota_de_credito;
    private javax.swing.JLabel nota_de_pagamento;
    private javax.swing.JLabel numerario;
    private javax.swing.JLabel recibo;
    private javax.swing.JLabel saida_de_caixa;
    private javax.swing.JLabel saldo_atual;
    private javax.swing.JLabel saldo_informado;
    private javax.swing.JLabel saldo_inicial;
    // End of variables declaration//GEN-END:variables

    byte[] photo = null;
    String filename = null;
}

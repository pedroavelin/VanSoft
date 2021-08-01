/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import Controle.TextPrompt;
import Controle.ControloBD;
import Controle.Tempo;
import Modelo.Exporter;
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
import com.sun.javafx.charts.Legend;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Gonga
 */
public class Dashboard extends javax.swing.JFrame {

    private int id;
    private String perfil;
    ControloBD liga = new ControloBD();
    Tempo tempo = new Tempo();

    /**
     * Creates new form Principal2
     */
    public Dashboard() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        chamar("select SUM(total), p.descricao from venda v \n"
                + "inner join produto p\n"
                + "on v.descricao = p.descricao where to_date(v.data,'dd-MM-yy') = current_date \n"
                + "group by p.descricao \n"
                + "order by \n"
                + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        this.pack();
        jMenu4.setBackground(new Color(51, 51, 255));
        jMenu1.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));
//        this.jPanel_Dashboard.setVisible(true);
//        this.jPanel14_dash_clientes.setVisible(true);
//        this.jPanel14_dash_PRODUTOS.setVisible(false);

//        jInternalFrame1.setSize(200, 200);
//        jInternalFrame2.setSize(200, 200);
//        jInternalFrame3.setSize(200, 200);
    }

//    public Dashboard(int idutilizador, String perfil) {
//
//        initComponents();
//        //        produtos mais solicitados
//        chamar("select SUM(total), p.descricao from venda v \n"
//                + "inner join produto p\n"
//                + "on v.descricao = p.descricao\n"
//                + "group by p.descricao \n"
//                + "order by \n"
//                + "SUM(total) desc;", "Prdodutos mais vendidos", "Produtos", "Venda (Kz)");
//
//        this.pack();
//
//        jMenu4.setBackground(new Color(51, 51, 255));
//        jMenu1.setBackground(new Color(240, 240, 240));
//        jMenu3.setBackground(new Color(240, 240, 240));
//        jLabel2.setVisible(false);
////            this.jPanel_Dashboard.setVisible(true);
//        this.id = idutilizador;
//        this.perfil = perfil;
//
//        jLabel_nome_utilizador.setText(this.getNomeUser(this.id, this.perfil));
////            PreencherTabela("select idutilizador, nome, telefone, ultimo_login from utilizador order by idutilizador");
//
////            liga.conexao();
////            liga.executeSql("select *  from configuracao");
////            if (liga.rs.first()) {
////                this.jCheckBox1.setSelected((liga.rs.getBoolean("backup")));
////
////                liga.deconecta();
////            }
////        } catch (SQLException ex) {
////            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        
//    }
//
//    
    public Dashboard(int idutilizador, String perfil, String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);

        this.setExtendedState(this.MAXIMIZED_BOTH);
        //        produtos mais solicitados
        chamar("select SUM(total), descricao from venda v \n"
                + "where to_date(v.data,'dd-MM-yy') = current_date \n"
                + "group by descricao \n"
                + "order by \n"
                + "SUM(total) desc;", "Vendas", "Artigo", "Venda (Kz)");

        this.pack();

        jMenu4.setBackground(new Color(51, 51, 255));
        jMenu1.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));
        jLabel2.setVisible(false);
//            this.jPanel_Dashboard.setVisible(true);
        this.id = idutilizador;
        this.perfil = perfil;

        jLabel_nome_utilizador.setText(this.getNomeUser(this.id, this.perfil));
//            PreencherTabela("select idutilizador, nome, telefone, ultimo_login from utilizador order by idutilizador");

//            liga.conexao();
//            liga.executeSql("select *  from configuracao");
//            if (liga.rs.first()) {
//                this.jCheckBox1.setSelected((liga.rs.getBoolean("backup")));
//
//                liga.deconecta();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
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
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
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
        buttonGroupMoeda = new javax.swing.ButtonGroup();
        buttonGroup_pagador_da_retencao = new javax.swing.ButtonGroup();
        buttonGroup_tamanho_do_doc = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
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
        jPanel23 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel_Logotipo = new javax.swing.JLabel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel_Produtos_mais_vendidos = new javax.swing.JPanel();
        jInternalFrameProdutos_mais_vendidos = new javax.swing.JInternalFrame();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1000, 625));

        jPanel2.setBackground(new java.awt.Color(54, 70, 78));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("jLabel2");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 50, 41));

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

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 220, 40));

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

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 220, 40));

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

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 220, 40));

        jLabel_nome_utilizador.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel_nome_utilizador.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_nome_utilizador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_nome_utilizador.setText("Nome de utilizador");
        jPanel2.add(jLabel_nome_utilizador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 220, 20));

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

        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 220, 40));

        jLabel26.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Copyright©VanSoft");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 220, 20));

        jPanel23.setBackground(new java.awt.Color(54, 70, 78));
        jPanel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel23MouseClicked(evt);
            }
        });
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel23.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel38.setBackground(new java.awt.Color(255, 255, 255));
        jLabel38.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Suporte");
        jPanel23.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel24.setBackground(new java.awt.Color(54, 70, 78));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_help_40px.png"))); // NOI18N
        jPanel24.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel40.setBackground(new java.awt.Color(255, 255, 255));
        jLabel40.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Suporte");
        jPanel24.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel23.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 220, 40));

        jPanel2.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 220, 40));

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

        jPanel2.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 220, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setToolTipText("Dashboard");
        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(54, 70, 78));
        jLabel8.setText("Dashboard");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_graph_report_script_filled_40px.png"))); // NOI18N
        jPanel6.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 220, 40));

        jPanel12.setBackground(new java.awt.Color(54, 70, 78));
        jPanel12.setToolTipText("Configurações do software");
        jPanel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel12MouseClicked(evt);
            }
        });
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_settings_3_40px.png"))); // NOI18N
        jPanel12.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Configurações");
        jPanel12.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 11, 142, -1));

        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 220, -1));

        jLabel_Logotipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Logotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/LogoApp.png"))); // NOI18N

        jInternalFrame1.setVisible(true);

        jInternalFrameProdutos_mais_vendidos.setVisible(true);

        javax.swing.GroupLayout jInternalFrameProdutos_mais_vendidosLayout = new javax.swing.GroupLayout(jInternalFrameProdutos_mais_vendidos.getContentPane());
        jInternalFrameProdutos_mais_vendidos.getContentPane().setLayout(jInternalFrameProdutos_mais_vendidosLayout);
        jInternalFrameProdutos_mais_vendidosLayout.setHorizontalGroup(
            jInternalFrameProdutos_mais_vendidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 351, Short.MAX_VALUE)
        );
        jInternalFrameProdutos_mais_vendidosLayout.setVerticalGroup(
            jInternalFrameProdutos_mais_vendidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 862, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_Produtos_mais_vendidosLayout = new javax.swing.GroupLayout(jPanel_Produtos_mais_vendidos);
        jPanel_Produtos_mais_vendidos.setLayout(jPanel_Produtos_mais_vendidosLayout);
        jPanel_Produtos_mais_vendidosLayout.setHorizontalGroup(
            jPanel_Produtos_mais_vendidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Produtos_mais_vendidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrameProdutos_mais_vendidos)
                .addContainerGap())
        );
        jPanel_Produtos_mais_vendidosLayout.setVerticalGroup(
            jPanel_Produtos_mais_vendidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Produtos_mais_vendidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrameProdutos_mais_vendidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(288, Short.MAX_VALUE))
        );

        jMenu3.setText("Vendas");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu1.setText("Clientes Devedores");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu4.setText("Mais vendidos");

        jMenuItem1.setBackground(new java.awt.Color(51, 51, 255));
        jMenuItem1.setText("Produtos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuItem2.setText("Serviços");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        jInternalFrame1.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Produtos_mais_vendidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Produtos_mais_vendidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame2.setVisible(true);

        jButton1.setText("Pesquisar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("De");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("à");

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(7, 7, 7)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame2Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel_Logotipo, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jInternalFrame2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jInternalFrame1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel_Logotipo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jInternalFrame2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 682, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        new Dashboard(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // System.exit(0);
        new Entar().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel5MouseClicked

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

    private void jPanel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseClicked
        new Principal4(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel10MouseClicked

    private void jPanel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel23MouseClicked
        new suporte().setVisible(true);       // TODO add your handling code here:
    }//GEN-LAST:event_jPanel23MouseClicked

    private void jPanel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel37MouseClicked
        new Principal_stock(id, perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel37MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Dimension size = this.getSize();
        chamar("select SUM(total), p.descricao from venda v \n"
                + "inner join produto p\n"
                + "on v.descricao = p.descricao\n"
                + "group by p.descricao \n"
                + "order by \n"
                + "SUM(total) desc limit 10;", "Prdodutos mais vendidos", "Produtos", "Venda (Kz)");

        this.pack();
        jMenu4.setBackground(new Color(51, 51, 255));
        jMenu1.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));
        this.setSize(size);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Dimension size = this.getSize();
        chamar("select SUM(total), p.descricao from venda v \n"
                + "inner join servico p\n"
                + "on v.descricao = p.descricao\n"
                + "group by p.descricao \n"
                + "order by \n"
                + "SUM(total) desc limit 10;", "Serviços mais solicitados", "Serviços", "Receitas (Kz)");
        this.pack();
        jMenu4.setBackground(new Color(51, 51, 255));
        jMenu1.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));

        this.setSize(size);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        Dimension size = this.getSize();

        chamar("select SUM(f.total-(f.total*liquidacao)/100), c.designacao "
                + "from fatura f inner join cliente c \n"
                + "on f.idcliente = c.idcliente "
                + "group by c.designacao", "Clientes devedores", "Clientes", "Dívida (Kz)");
        this.pack();
        jMenu1.setBackground(new Color(51, 51, 255));
        jMenu4.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));
        this.setSize(size);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jPanel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseClicked
        new Principal(this.id, this.perfil, liga.getCaminho()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel12MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
//        System.out.println(jDateChooser1.getDate());
//        int extendedState = this.getExtendedState();
        Dimension size = this.getSize();
        if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
            String data1;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data1 = formatar.format(jDateChooser1.getDate());
            System.out.println(data1);
            chamar("select SUM(total), p.descricao from venda v \n"
                    + "inner join produto p\n"
                    + "on v.descricao = p.descricao where v.data = '" + data1 + "' \n"
                    + "group by p.descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        } else if (jDateChooser2.getDate() != null && jDateChooser1.getDate() == null) {
            String data1;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data1 = formatar.format(jDateChooser2.getDate());
            System.out.println(data1);
            chamar("select SUM(total), p.descricao from venda v \n"
                    + "inner join produto p\n"
                    + "on v.descricao = p.descricao where v.data = '" + data1 + "' \n"
                    + "group by p.descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
            String data1, data2;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data1 = formatar.format(jDateChooser1.getDate());
            data2 = formatar.format(jDateChooser1.getDate());
            System.out.println(data1);
            System.out.println(data2);
            chamar("select SUM(total), p.descricao from venda v \n"
                    + "inner join produto p\n"
                    + "on v.descricao = p.descricao "
                    + " where to_date(v.data,'dd-MM-yy') BETWEEN '" + data1 + "' AND"
                    + " '" + data2 + "' \n"
                    + "group by p.descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        } else {

            chamar("select SUM(total), p.descricao from venda v \n"
                    + "inner join produto p\n"
                    + "on v.descricao = p.descricao where to_date(v.data,'dd-MM-yy') = current_date \n"
                    + "group by p.descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        }

        this.pack();
        jMenu4.setBackground(new Color(51, 51, 255));
        jMenu1.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));
        this.setSize(size);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Dimension size = this.getSize();
        if (jDateChooser1.getDate() != null && jDateChooser2.getDate() == null) {
            String data1;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data1 = formatar.format(jDateChooser1.getDate());
            System.out.println(data1);
            chamar("select SUM(total), descricao from venda v \n"
                    + " where v.data = '" + data1 + "' \n"
                    + "group by descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        } else if (jDateChooser2.getDate() != null && jDateChooser1.getDate() == null) {
            String data1;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data1 = formatar.format(jDateChooser2.getDate());
            System.out.println(data1);
            chamar("select SUM(total), descricao from venda v \n"
                    + "where v.data = '" + data1 + "' \n"
                    + "group by descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        } else if (jDateChooser1.getDate() != null && jDateChooser2.getDate() != null) {
            String data1, data2;
            SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-YYYY");
            data1 = formatar.format(jDateChooser1.getDate());
            data2 = formatar.format(jDateChooser1.getDate());
            System.out.println(data1);
            System.out.println(data2);
            chamar("select SUM(total), descricao from venda v \n"
                    + " where to_date(v.data,'dd-MM-yy') BETWEEN '" + data1 + "' AND"
                    + " '" + data2 + "' \n"
                    + "group by descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        } else {

            chamar("select SUM(total), p.descricao from venda v \n"
                    + " where to_date(v.data,'dd-MM-yy') = current_date \n"
                    + "group by descricao \n"
                    + "order by \n"
                    + "SUM(total) desc;", "Prdodutos vendidos", "Produtos", "Venda (Kz)");
        }

        this.pack();
        jMenu4.setBackground(new Color(51, 51, 255));
        jMenu1.setBackground(new Color(240, 240, 240));
        jMenu3.setBackground(new Color(240, 240, 240));

        this.setSize(size);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupCorDoSistema;
    private javax.swing.ButtonGroup buttonGroupMoeda;
    private javax.swing.ButtonGroup buttonGroupTipoDeLetra;
    private javax.swing.ButtonGroup buttonGroup_pagador_da_retencao;
    private javax.swing.ButtonGroup buttonGroup_tamanho_do_doc;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrameProdutos_mais_vendidos;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
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
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_Produtos_mais_vendidos;
    // End of variables declaration//GEN-END:variables

    byte[] photo = null;
    String filename = null;

    private static final long serialVersionUID = 1L;

    public void chamar(String Sql, String titulo, String Eixo_X, String Eixo_Y) {

        final CategoryDataset dataset1 = DadosDoGrafico(Sql);

        final JFreeChart chart;
        chart = ChartFactory.createBarChart3D(
                titulo, // este é título do gráfico
                Eixo_X, // Título do eixo x
                Eixo_Y, // Título do eixo y
                dataset1, // são as informações inerentes a cada barra que vai ser criada nafunção abaixo
                PlotOrientation.VERTICAL, //coloca as barras na posição vertical
                true, // include legend
                true,
                false);
// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
// set the background color for the chart...
        chart.setBackgroundPaint(new Color(0xCC, 0xFF, 0xCC));
// chart.getLegend().setAnchor(Legend.);

// get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
//        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
//        plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
        final CategoryItemRenderer renderer1 = plot.getRenderer();

        renderer1.setSeriesPaint(0, Color.blue);
        renderer1.setSeriesPaint(1, Color.yellow);
        renderer1.setSeriesPaint(2, Color.green);

        final CategoryDataset dataset2 = createDataset2();
        final ValueAxis axis2 = new NumberAxis3D("Secondary");
        plot.setRangeAxis(1, axis2);
//        plot.setDataset(1, dataset2);
        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setSeriesPaint(0, Color.blue);
//        plot.setRenderer(1, renderer2);

        plot.mapDatasetToRangeAxis(1, 1);
//        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
// OPTIONAL CUSTOMISATION COMPLETED.
// add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);

        jInternalFrameProdutos_mais_vendidos.setContentPane(chartPanel);
        jInternalFrame1.setResizable(false);

    }

    private CategoryDataset createDataset1() {
// esta função serve para formar o dataset, que é a arrumação dos dados no gráfico
//series é cada barra de cor diferente no gráfico
// definindo o nome das barras abaixofinal 

//        select SUM(valor_pago), p.descricao from recibo r inner join venda v 
//on v.idfactura = r.idfatura_recibo inner join produto p 
//on v.descricao = p.descricao where v.origem =1 group by p.descricao order by SUM(valor_pago) desc;
//        
        final String series1 = "1º ano";
        final String series2 = "2º ano";
        final String series3 = "3º ano";
        final String series4 = "4º ano";
        final String series5 = "5º ano";
        final String series6 = "6º ano";
        final String series7 = "7º ano";
        final String series8 = "8º ano";
// definindo a categoria (o nome do eixo x do gráfico) a que pertencem estas barras
        final String category1 = "Categoria turmas";
// definindo o valor correspondente a cada barra e a cada categoria.
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(8.0, series1, category1);
        dataset.addValue(10.0, series2, category1);
        dataset.addValue(12.0, series3, category1);
        dataset.addValue(13.0, series4, category1);
        dataset.addValue(16.0, series5, category1);
        dataset.addValue(17.0, series6, category1);
        dataset.addValue(18.0, series7, category1);
        dataset.addValue(20.0, series8, category1);
        return dataset;
    }

    /* Esta parte comentada é para fazer um gráfico de linhas mas segue o mesmo 
     princípiodo de barras*/
    private CategoryDataset createDataset2() {
        // row keys...
        final String series1 = "Fourth";
// column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";
// create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15.0, series1, category1);
        dataset.addValue(24.0, series1, category2);
        dataset.addValue(31.0, series1, category3);
        dataset.addValue(25.0, series1, category4);
        dataset.addValue(56.0, series1, category5);
        return dataset;

    }

    private CategoryDataset DadosDoGrafico(String Sql) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        liga.conexao();
        int i = 1;
        liga.executeSql(Sql);
        try {
            if (liga.rs.first()) {
                do {

                    dataset.addValue(liga.rs.getDouble(1), liga.rs.getString(2), liga.rs.getString(2));
                    i++;
//                if (1 == 10) {
//                    break;
//                }
                } while (liga.rs.next());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados \n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }

        return dataset;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import Controle.Extenso;
import Modelo.Exporter;
import Modelo.ModeloPDF;
import Modelo.ModeloTabela;
import java.awt.Toolkit;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Gonga
 */
public class Produtos_por_armazem extends javax.swing.JFrame {
    
    Extenso exte = new Extenso();
    ControloBD liga = new ControloBD();
    ArrayList<Integer> Id_armazens = new ArrayList();
    ArrayList dadosAux = new ArrayList();
    private double percentImpostoIVA;

    public Produtos_por_armazem() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }
    /**
     * Creates new form Produtos_por_armazem
     */
    
    public Produtos_por_armazem(String caminho) {
        liga.setCaminho(caminho);
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        try {
            initComponents();
            liga.conexao();
            liga.executeSql("select * from empresa limit 1");
//            this.percentImpostoIVA = "0";
            if (liga.rs.first()) {
                this.percentImpostoIVA = liga.rs.getDouble("valor_imposto_iva");
            }
            liga.deconecta();
            
            jComboBox_armazem.removeAllItems();
            liga.conexao();
            liga.executeSql("Select * from armazem order by id_armazem");
            try {
                liga.rs.first();
                do {
                    jComboBox_armazem.addItem(liga.rs.getString("descricao"));
                    this.Id_armazens.add(liga.rs.getInt("id_armazem"));
                    //this.Localizacao_armazens.add(liga.rs.getString("localizacao"));
                } while (liga.rs.next());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "N??o ?? poss??vel realizar esta a????o sem pelo menos um armazem cadatastrado.", "Aviso", JOptionPane.ERROR_MESSAGE);
                this.dispose();
            }
            liga.deconecta();
        } catch (SQLException ex) {
            Logger.getLogger(Produtos_por_armazem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PreencherTableProduto("select * from produto");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor. PreencherTableProduto
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel19 = new javax.swing.JLabel();
        jComboBox_armazem = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMAteria = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel19.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel19.setText("Armazem");

        jComboBox_armazem.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jComboBox_armazem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_armazem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_armazemItemStateChanged(evt);
            }
        });

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
                "Descri????o", "Pre??o"
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
        jScrollPane2.setViewportView(jTableMAteria);

        jPanel25.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel25.setToolTipText("Emitir um relat??rio dos dados presentes na tabela em  em PDF ");
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.setOpaque(false);
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_pdf_2_90px.png"))); // NOI18N
        jLabel41.setText("jLabel28");

        jLabel42.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Exportar PDF");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel28.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel28.setToolTipText("Emitir um relat??rio dos dados presentes na tabela em EXEL");
        jPanel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.setOpaque(false);
        jPanel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel28MouseClicked(evt);
            }
        });

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_exe_90px.png"))); // NOI18N
        jLabel47.setText("jLabel28");

        jLabel48.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Exportar EXEL");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel40.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel40.setToolTipText("Actualizar");
        jPanel40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel40.setOpaque(false);
        jPanel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel40MouseClicked(evt);
            }
        });

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_repeat_90px.png"))); // NOI18N
        jLabel57.setText("jLabel28");

        jLabel58.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("Actualizar");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel58)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox_armazem, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_armazem))
                .addGap(417, 417, 417))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(355, 355, 355))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(144, 144, 144)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox_armazemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_armazemItemStateChanged
        if (jComboBox_armazem.getItemCount() > 0 && this.Id_armazens.size() > 0) {
            PreencherTableProduto("select * from produto");
//            this.jRadioButton2.setSelected(true);
////            this.jLabel_T??tuloDaTabela.setText("Produtos");
        }
    }//GEN-LAST:event_jComboBox_armazemItemStateChanged

    private void jTableMAteriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMAteriaMouseClicked
        //AgregarItem();
    }//GEN-LAST:event_jTableMAteriaMouseClicked

    private void jTableMAteriaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTableMAteriaPropertyChange

    }//GEN-LAST:event_jTableMAteriaPropertyChange

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        if (jTableMAteria.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf (.pdf)", "pdf");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String file = chooser.getSelectedFile().toString().concat(".pdf");
                try {
                    ModeloPDF e = new ModeloPDF(new File(file), jTableMAteria, "Relat??rio",liga.getCaminho());
                        e.ExportarRel("Listagem de produtos no armaz??m "+jComboBox_armazem.getSelectedItem().toString(),false,dadosAux );
                    
                    JOptionPane.showMessageDialog(null, "Os Dados foram gravados no directorio selecionado", "Mensagem de Informa????o", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a exporta????o.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel28MouseClicked
        if (jTableMAteria.getRowCount() > 0) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (.xls)", "xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Arquivo");
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                List<JTable> tb = new ArrayList<JTable>();
                List<String> nom = new ArrayList<String>();
                tb.add(jTableMAteria);
                
                nom.add("Listagem de produtos no armaz??m "+jComboBox_armazem.getSelectedItem().toString());
                
                String file = chooser.getSelectedFile().toString().concat(".xls");
                try {
                    Exporter e = new Exporter(new File(file), tb, nom);
                    if (e.export()) {
                        JOptionPane.showMessageDialog(null, "Os dados foram gravados no directorio selecionado", "Mensagem de Informa????o", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro na exporta????o.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jPanel28MouseClicked

    private void jPanel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel40MouseClicked
        new Relatorio_stock_inventario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPanel40MouseClicked

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
            java.util.logging.Logger.getLogger(Produtos_por_armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Produtos_por_armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Produtos_por_armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Produtos_por_armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Produtos_por_armazem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox_armazem;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableMAteria;
    // End of variables declaration//GEN-END:variables



     public void PreencherTableProduto(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Cod. de barra", "Descri????o", "Pre??o unit.", "IVA", "Motivo", "QNT", "Pre??o venda."};
        for (String val: colunas)
        {
            dadosAux.add(val);
        }

        liga.conexao();
        liga.executeSql(Sql);
        try {
            if(liga.rs.first())
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
                dados.add(new Object[]{liga.rs.getString("codigo_de_barra"), liga.rs.getString("descricao"), exte.Chang(preco),
                    IMPOSTO(liga.rs.getBoolean("imposto_iva")), liga.rs.getString("motivo"), " "+qnt, exte.Chang(precoV)});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(rootPane, "Dados n??o encntrados\n"+ex, "Aviso", JOptionPane.ERROR_MESSAGE);
        }

        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };

        jTableMAteria.setModel(modelo);

//        
        jTableMAteria.getTableHeader()
                .setReorderingAllowed(false);
        jTableMAteria.setAutoResizeMode(jTableMAteria.AUTO_RESIZE_ALL_COLUMNS);

        liga.deconecta();
    }
     
     public double TakeQNT(int idproduto) {
        ControloBD cne = new ControloBD();
        cne.setCaminho(liga.getCaminho());
        double saida = 0, entrada = 0, qnt = 0;
        cne.conexao();
        cne.executeSql("select SUM (qnt) from entrada where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()));

        try {
            if(cne.rs.first())
            do {
                entrada = cne.rs.getDouble(1);

            } while (cne.rs.next());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(rootPane, "Dados n??o encntrados\n"+ex, "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }

        cne.executeSql("select SUM (qnt) from saida where idproduto = " + idproduto + " and id_armazem = " + this.Id_armazens.get(jComboBox_armazem.getSelectedIndex()));

        try {
            if(cne.rs.first())
            do {
                saida = cne.rs.getDouble(1);

            } while (cne.rs.next());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(rootPane, "Dados n??o encntrados\n"+ex, "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }

        return entrada - saida;

    }
     
     
     private String IMPOSTO(boolean aBoolean) {
        if (aBoolean) {
            return Double.toString(this.percentImpostoIVA) + "%";
        } else {
            return "ISENTO";
        }
    }
}

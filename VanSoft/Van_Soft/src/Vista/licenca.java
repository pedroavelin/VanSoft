/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. chave já
 */
package Vista;

import Controle.ControloBD;
import Controle.HardwareAddress;
import java.awt.Color;
import Controle.TextPrompt;
import Modelo.ModeloTabela;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class licenca extends javax.swing.JFrame {

    ControloBD liga = new ControloBD();
    HardwareAddress hd = new HardwareAddress();

    /**
     * Creates new form Entar
     */
    public licenca() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        String mac = this.hd.GetMac();
        System.out.println(mac);
//        PreencherTabela("select m.modulo, e.estado, data_de_fim - current_date, data_de_ativacao, mac \n"
//                + "from licenca l inner join modulo m \n"
//                + "on l.idmodulo = m.idmodulo \n"
//                + "inner join estado e\n"
//                + "on l.idestado = e.idestado\n"
//                + "inner join pc p \n"
//                + "on l.idpc = p.idpc \n"
//                + "WHERE p.mac = '" + mac + "'");
    }

    public licenca(String caminho) {
        initComponents();
        liga.setCaminho(caminho);
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);

//        String mac = this.hd.GetMac();
//        System.out.println(mac);
        PreencherTabela();

    }

    public void PreencherTabela() {
        ControloBD liga = new ControloBD();

        String SQL = "select m.modulo, e.estado, data_de_fim - current_date, data_de_ativacao, mac \n"
                + "from licenca l inner join modulo m \n"
                + "on l.idmodulo = m.idmodulo \n"
                + "inner join estado e\n"
                + "on l.idestado = e.idestado\n"
                + "inner join pc p \n"
                + "on l.idpc = p.idpc \n"
                + "WHERE p.nome = '" + this.hd.GetNAME() + "'";

        if (!liga.conexao()) {
            {
                SQL = "select m.modulo, e.estado, data_de_fim - current_date, data_de_ativacao, mac \n"
                        + "from licenca l inner join modulo m \n"
                        + "on l.idmodulo = m.idmodulo \n"
                        + "inner join estado e\n"
                        + "on l.idestado = e.idestado\n"
                        + "inner join pc p \n"
                        + "on l.idpc = p.idpc \n"
                        + "WHERE p.mac = '" + this.hd.GetMac() + "'";
            }
            liga.setCaminho(this.liga.getCaminho());
            liga.conexao();
        }

        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Modulo", "Estado", "Acesso", "Data de ativação"};
        liga.conexao();
        liga.executeSql(SQL);
        try {
            if (liga.rs!=null && liga.rs.first()) {
                do {

                    dados.add(new Object[]{liga.rs.getString(1), liga.rs.getString(2), liga.rs.getString(3), liga.rs.getString(4)});
                } while (liga.rs.next());
            } else {
                dados.add(new Object[]{"Faturação", "Inativo", "0", "  "});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados não encntrados\n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        JTableLicenca.setModel(modelo);
        JTableLicenca.getColumnModel().getColumn(0).setPreferredWidth(40);
        JTableLicenca.getColumnModel().getColumn(0).setResizable(false);
        JTableLicenca.getColumnModel().getColumn(1).setPreferredWidth(200);
        JTableLicenca.getColumnModel().getColumn(1).setResizable(true);
        JTableLicenca.getColumnModel().getColumn(2).setPreferredWidth(180);
        JTableLicenca.getColumnModel().getColumn(2).setResizable(true);
        JTableLicenca.getColumnModel().getColumn(3).setPreferredWidth(180);
        JTableLicenca.getColumnModel().getColumn(3).setResizable(true);

        JTableLicenca.getTableHeader().setReorderingAllowed(false);
        JTableLicenca.setAutoResizeMode(JTableLicenca.AUTO_RESIZE_ALL_COLUMNS);
//        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableLicenca = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 410));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(27, 167, 125));

        jLabel1.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Licença");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        JTableLicenca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        JTableLicenca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Modulo", "Estado", "Acesso", "Data de ativação"
            }
        ));
        jScrollPane1.setViewportView(JTableLicenca);

        jButton1.setText("Solicitar chave de ativação");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Testar o Aplicativo");

        jButton3.setText("Ativar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ativação da licença");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 438, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(71, 71, 71)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Solicitar(liga.getCaminho()).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int x = BucarPC();

        if (x != -1) {
            InsertLicenca(x, 1);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Chave inválida. eeee", "Aviso", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(licenca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(licenca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(licenca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(licenca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new licenca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableLicenca;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void InsertLicenca(int pc, int modulo) {
        if (!jTextField1.getText().isEmpty()) {
            liga.conexao();
            liga.executeSql("Select * from chave where chave = '" + jTextField1.getText() + "' AND idpc=" + pc);
            try {
                if (liga.rs.first()) {
                    if (liga.rs.getString("estado").equals("não")) {
                        liga.executeSql("Select descricao from licenca where idmodulo=" + modulo + " AND idpc=" + pc);
                        PreparedStatement pst;
                        if (liga.rs.first()) {
                            pst = liga.con.prepareStatement("UPDATE licenca SET "
                                    + "descricao = ?, data_de_ativacao = current_date, "
                                    + "data_de_fim=current_date + 365, dias_de_acesso=?,"
                                    + "chave=?,idestado=? "
                                    + "where idmodulo=? AND idpc=? ");
                            System.out.println("UPDATE licenca SET "
                                    + "descricao = ?, data_de_ativacao = current_date, "
                                    + "data_de_fim=current_date + 365, dias_de_acesso=?,"
                                    + "chave=?,idestado=? "
                                    + "where idmodulo=? AND idpc=? ");
                        } else {
                            pst = liga.con.prepareStatement("Insert into licenca "
                                    + "(descricao,data_de_ativacao,data_de_fim,dias_de_acesso,"
                                    + "chave,idestado,idmodulo,idpc)  "
                                    + "values (?,current_date,current_date + 365,?,?,?,?,?)");
                            System.out.println("Insert into licenca "
                                    + "(descricao,data_de_ativacao,data_de_fim,dias_de_acesso,"
                                    + "chave,idestado,idmodulo,idpc)  "
                                    + "values (?,current_date,current_date + 365,?,?,?,?,?)");
                        }
                        pst.setString(1, "" + modulo);
//                    pst.setString(2, "current_date");
//                    pst.setString(3, "current_date + 365");
                        pst.setInt(2, 365);
                        pst.setString(3, jTextField1.getText());
                        pst.setInt(4, 1);
                        pst.setInt(5, modulo);
                        pst.setInt(6, pc);

                        if (!pst.execute()) {
                            pst = liga.con.prepareStatement("UPDATE chave SET estado='sim' where "
                                    + "chave = '" + jTextField1.getText() + "' and estado='não' AND idpc=" + pc);
                            System.out.println("UPDATE chave SET estado='sim' where "
                                    + "chave = '" + jTextField1.getText() + "' and estado='não' AND idpc=" + pc);
                            pst.execute();
                            JOptionPane.showMessageDialog(rootPane, "Ativado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            new licenca(this.liga.getCaminho()).setVisible(true);
                            this.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Chave já utilizada.", "Falha na ativação", JOptionPane.WARNING_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Chave inválida.", "Falha na ativação", JOptionPane.WARNING_MESSAGE);

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Chave inválida.\n" + ex, "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Conecta-se a uma rede de internet", "Falha na ativação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int BucarPC(String MAC) {

        liga.conexao();
        liga.executeSql("Select idpc from pc where mac = '" + MAC + "'");

        try {
            if (liga.rs.first()) {
                return liga.rs.getInt("idpc");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }

    }

    private int BucarPC() {
        ControloBD liga = new ControloBD();
        String MAC = this.hd.GetNAME();

        if (!liga.conexao()) {
            MAC = this.hd.GetMac();
            liga.setCaminho(this.liga.getCaminho());
            liga.conexao();
            liga.executeSql("Select idpc from pc where mac = '" + MAC + "'");
        } else {
            liga.executeSql("Select idpc from pc where nome = '" + MAC + "'");
        }

        try {
            if (liga.rs.first()) {
                return liga.rs.getInt("idpc");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }

    }
}

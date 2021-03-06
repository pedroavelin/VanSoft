/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controle.ControloBD;
import Modelo.ModeloTabela;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonga
 */
public class armazem extends javax.swing.JFrame {

    ControloBD liga = new ControloBD();
    int idUpdate = -1;

    /**
     * Creates new form armazem
     */
    public armazem() {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        PreencherTabela("Select * from armazem order by id_armazem;");
        this.jPanel1.setVisible(false);
    }
    public armazem(String caminho) {
        initComponents();
        Toolkit T = Toolkit.getDefaultToolkit();
        this.setSize(T.getScreenSize());
        this.setExtendedState(this.MAXIMIZED_BOTH);
        liga.setCaminho(caminho);
        PreencherTabela("Select * from armazem order by id_armazem;");
        this.jPanel1.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel25 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableAmazens = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField_descricao = new javax.swing.JTextField();
        jTextField_localizacao = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField_tamanho = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel25.setBackground(new java.awt.Color(27, 167, 125));

        jLabel38.setFont(new java.awt.Font("David", 1, 36)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Armazens");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        JTableAmazens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Descri????o", "Localiza????o", "Tamanho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JTableAmazens.setToolTipText("Lista dos armazens");
        JTableAmazens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableAmazensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTableAmazens);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel22.setToolTipText("Adicionar um novo armazem");
        jPanel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel22.setOpaque(false);
        jPanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel22MouseClicked(evt);
            }
        });

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_plus_90px_1.png"))); // NOI18N

        jLabel36.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Novo");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addContainerGap())
        );

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel24.setToolTipText("Editar armazem");
        jPanel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel24.setOpaque(false);
        jPanel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel24MouseClicked(evt);
            }
        });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_edit_90px_1.png"))); // NOI18N

        jLabel43.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Editar");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel27.setToolTipText("Eliminar um armazem");
        jPanel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.setOpaque(false);
        jPanel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel27MouseClicked(evt);
            }
        });

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cramervan/Vista/img/icons8_cancel_90px.png"))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Eliminar");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Novo armazem"));

        jTextField_localizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_localizacaoActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(56, 65, 84));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Descri????o  *");

        jLabel2.setText("Localiza????o *");

        jLabel3.setText("Tamanho");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(217, 81, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cancelar");
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_descricao)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField_localizacao))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_tamanho)
                            .addComponent(jLabel3))
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_descricao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_tamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_localizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 304, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JTableAmazensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableAmazensMouseClicked

    }//GEN-LAST:event_JTableAmazensMouseClicked

    private void jPanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel22MouseClicked
        this.jPanel1.setVisible(true);
        this.idUpdate = -1;
        jTextField_descricao.setText("");
        jTextField_localizacao.setText("");
        jTextField_tamanho.setText("");
    }//GEN-LAST:event_jPanel22MouseClicked

    private void jPanel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel24MouseClicked
        int linha = JTableAmazens.getSelectedRow();
        if (linha > -1) {
            if (JTableAmazens.getValueAt(linha, 0) != null) {
                idUpdate = Integer.parseInt(JTableAmazens.getValueAt(linha, 0).toString());
                this.jPanel1.setVisible(true);
            } else {
                idUpdate = -1;
            }
            if (JTableAmazens.getValueAt(linha, 1) != null);
            jTextField_descricao.setText(JTableAmazens.getValueAt(linha, 1).toString());

            liga.conexao();
            liga.executeSql("select * from armazem where id_armazem = " + idUpdate);

            try {
                if(liga.rs.first())
                do {
                    jTextField_localizacao.setText(liga.rs.getString("localizacao"));
                    jTextField_descricao.setText(liga.rs.getString("descricao"));
                    jTextField_tamanho.setText(liga.rs.getString("tamanho"));
                } while (liga.rs.next());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Dados n??o encntrados\n"+ex, "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleciona um dos armazens na tabela.", "Erro", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jPanel24MouseClicked

    private void jPanel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel27MouseClicked
        int linha = JTableAmazens.getSelectedRow();
        if (linha > -1) {
            int id = Integer.parseInt(JTableAmazens.getValueAt(linha, 0).toString());
            if (id != 1) {
                if (DeletarArmazem(id)) {
                    new armazem(liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "N??o podes eliminar o armazem predefinido.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleciona um dos armazens na tabela.", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jPanel27MouseClicked

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        this.jButton1.setBackground(new Color(235, 235, 235));
        this.jButton1.setForeground(new Color(56, 65, 84));
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        this.jButton1.setBackground(new Color(56, 65, 84));
        this.jButton1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean r1, r2;

        if (!this.jTextField_descricao.getText().isEmpty()) {
            r1 = true;

        } else {

            r1 = false;
        }

        if (!this.jTextField_localizacao.getText().isEmpty()) {
            r2 = true;

        } else {
            r2 = false;
        }

        if (r1 && r2) {
            if (idUpdate == -1) {
                if (this.CriarArmazem()) {
                    new armazem(liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            } else {
                if (this.updateArmazem()) {
                    new armazem(liga.getCaminho()).setVisible(true);
                    this.dispose();
                }
            }
        } else if (!r1 && r2) {

            JOptionPane.showMessageDialog(null, "Coloca uma descri????o para o armazem.", "Armazem n??o cadastrado,", JOptionPane.WARNING_MESSAGE);
            this.jTextField_descricao.requestFocus();
        } else if (r1 && !r2) {

            JOptionPane.showMessageDialog(null, "Coloca uma localiza????o para o armazem.", "Armazem n??o cadastrado,", JOptionPane.WARNING_MESSAGE);
        } else if (r1 && !r2) {

            JOptionPane.showMessageDialog(null, "Coloca uma descri????o para o armazem.", "Armazem n??o cadastrado,", JOptionPane.WARNING_MESSAGE);
            this.jTextField_localizacao.requestFocus();
        } else {
            this.jTextField_descricao.requestFocus();
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigat??rios indicados com asterisco (*) .", "Armazem n??o cadastrado,", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_localizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_localizacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_localizacaoActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        this.jButton2.setBackground(new Color(235, 235, 235));
        this.jButton2.setForeground(new Color(217, 81, 51));
    }//GEN-LAST:event_jButton2MouseEntered

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        this.jButton2.setBackground(new Color(217, 81, 51));
        this.jButton2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.jPanel1.setVisible(false);
        jTextField_descricao.setText("");
        jTextField_localizacao.setText("");
        jTextField_tamanho.setText("");
        this.idUpdate = -1;
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(armazem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new armazem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableAmazens;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField_descricao;
    private javax.swing.JTextField jTextField_localizacao;
    private javax.swing.JTextField jTextField_tamanho;
    // End of variables declaration//GEN-END:variables

    private boolean CriarArmazem() {
        liga.conexao();
        try {
            PreparedStatement pst = liga.con.prepareStatement("INSERT into armazem "
                    + " (descricao, localizacao, tamanho) VALUES ('" + jTextField_descricao.getText() + "','" + jTextField_localizacao.getText() + "','" + jTextField_tamanho.getText() + "')");

            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);

            pst.execute();
            liga.deconecta();
            this.idUpdate = -1;
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados n??o gravados " + ex, "Erro ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        return false;
    }

    public void PreencherTabela(String Sql) {
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"ID", "Descri????o", "Tamanho", "Localiza????o"};
        liga.conexao();
        liga.executeSql(Sql);

        try {
           if(liga.rs.first())
            do {

                dados.add(new Object[]{liga.rs.getString("id_armazem"), liga.rs.getString("descricao"), liga.rs.getString("tamanho"), liga.rs.getString("localizacao")});
            } while (liga.rs.next());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dados n??o encntrados\n"+ex, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        };
        JTableAmazens.setModel(modelo);
        JTableAmazens.getColumnModel().getColumn(0).setPreferredWidth(40);
        JTableAmazens.getColumnModel().getColumn(0).setResizable(false);
        JTableAmazens.getColumnModel().getColumn(1).setPreferredWidth(200);
        JTableAmazens.getColumnModel().getColumn(1).setResizable(true);
        JTableAmazens.getColumnModel().getColumn(2).setPreferredWidth(180);
        JTableAmazens.getColumnModel().getColumn(2).setResizable(true);
        JTableAmazens.getColumnModel().getColumn(3).setPreferredWidth(180);
        JTableAmazens.getColumnModel().getColumn(3).setResizable(true);

        JTableAmazens.getTableHeader().setReorderingAllowed(false);
        JTableAmazens.setAutoResizeMode(JTableAmazens.AUTO_RESIZE_ALL_COLUMNS);
//        JTableColaboradores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        liga.deconecta();
    }

    private boolean updateArmazem() {
       
        liga.conexao();
        try {
            PreparedStatement pst = liga.con.prepareStatement("UPDATE armazem "
                    + " set descricao=?, localizacao=?, tamanho=? WHERE id_armazem = " + this.idUpdate);

            pst.setString(1, jTextField_descricao.getText());
            pst.setString(2, jTextField_localizacao.getText());
            pst.setString(3, jTextField_tamanho.getText());

            this.idUpdate = -1;
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            liga.deconecta();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Dados n??o atualizados.", "Aviso ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        return false;
    }

    private boolean DeletarArmazem(int parseInt) {
        liga.conexao();
        try {
            PreparedStatement pst = liga.con.prepareStatement("DELETE FROM ARMAZEM"
                    + " WHERE id_armazem = " + parseInt);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Dados Inseridos com sucesso.", "Guardado.", JOptionPane.INFORMATION_MESSAGE);
            liga.deconecta();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Armazem n??o eliminado ", "Erro ", JOptionPane.WARNING_MESSAGE);
        }
        liga.deconecta();
        return false;
    }

}

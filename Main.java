
package Form;

import java.awt.Dimension;


public class Main extends javax.swing.JFrame {

    DataNasabah ds =new DataNasabah();
    DataSurvey dg =new DataSurvey();
    DataJenis da =new DataJenis();
    info inf =new info();
    Trans sp =new Trans();
    JurnalForm fj = new JurnalForm();
    
    
    public Main() {
        initComponents();
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        baground1 = new Komponen.baground();
        Cetak = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuAkun = new javax.swing.JMenuItem();
        jMenuFormJurnal = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SPK Pemberian Pembiayaan Nasabah Metode KNN");
        setResizable(false);

        Cetak.setBorder(new javax.swing.border.MatteBorder(null));
        Cetak.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu2.setText("Data Master ||");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Users-Student-icon.png"))); // NOI18N
        jMenuItem1.setText("Data Siswa");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Teacher-icon.png"))); // NOI18N
        jMenuItem2.setText("Data Barang");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuAkun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Teacher-icon.png"))); // NOI18N
        jMenuAkun.setText("Data Account");
        jMenuAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAkunActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuAkun);

        jMenuFormJurnal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Teacher-icon.png"))); // NOI18N
        jMenuFormJurnal.setText("Form Jurnal");
        jMenuFormJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFormJurnalActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuFormJurnal);

        Cetak.add(jMenu2);

        jMenu3.setText("Penjualan  ||");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Actions-view-list-details-icon.png"))); // NOI18N
        jMenuItem4.setText("Transaksi");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        Cetak.add(jMenu3);

        jMenu5.setText("Informasi ||");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Science-School-icon.png"))); // NOI18N
        jMenuItem6.setText("SMKN 1 Randudongkal");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem6);

        Cetak.add(jMenu5);

        setJMenuBar(Cetak);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(baground1, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(baground1, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        
        baground1.add(sp);
        Dimension parentSize = baground1.getSize();
        Dimension childSize = sp.getSize();
        sp.setLocation((parentSize.width - childSize.width)/2, (parentSize.height - childSize.height)/2);
        ds.dispose();
        inf.dispose();
        dg.dispose();
        da.dispose();
        sp.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
       
        baground1.add(inf);
        Dimension parentSize = baground1.getSize();
        Dimension childSize = inf.getSize();
        inf.setLocation((parentSize.width - childSize.width)/2, (parentSize.height - childSize.height)/2);
        ds.dispose();
        sp.dispose();
        da.dispose();
        dg.dispose();
        inf.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:

        baground1.add(dg);
        Dimension parentSize = baground1.getSize();
        Dimension childSize = dg.getSize();
        dg.setLocation((parentSize.width - childSize.width)/2, (parentSize.height - childSize.height)/2);
        ds.dispose();
        inf.dispose();
        sp.dispose();
        da.dispose();
        fj.dispose();
        fj.dispose();
        dg.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:

        baground1.add(ds);
        Dimension parentSize = baground1.getSize();
        Dimension childSize = ds.getSize();
        ds.setLocation((parentSize.width - childSize.width)/2, (parentSize.height - childSize.height)/2);
        dg.dispose();
        inf.dispose();
        da.dispose();
        sp.dispose();
        fj.dispose();
        ds.setVisible(true);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAkunActionPerformed
        // TODO add your handling code here:
         baground1.add(da);
        Dimension parentSize = baground1.getSize();
        Dimension childSize = da.getSize();
        da.setLocation((parentSize.width - childSize.width)/2, (parentSize.height - childSize.height)/2);
        dg.dispose();
        inf.dispose();
        sp.dispose();
        fj.dispose();
        da.setVisible(true);
    }//GEN-LAST:event_jMenuAkunActionPerformed

    private void jMenuFormJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFormJurnalActionPerformed
        // TODO add your handling code here:
        baground1.add(fj);
        Dimension parentSize = baground1.getSize();
        Dimension childSize = fj.getSize();
        fj.setLocation((parentSize.width - childSize.width)/2, (parentSize.height - childSize.height)/2);
        dg.dispose();
        inf.dispose();
        da.dispose();
        sp.dispose();
        fj.setVisible(true);
    }//GEN-LAST:event_jMenuFormJurnalActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Cetak;
    private Komponen.baground baground1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuItem jMenuAkun;
    private javax.swing.JMenuItem jMenuFormJurnal;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    // End of variables declaration//GEN-END:variables
}

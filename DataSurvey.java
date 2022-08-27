/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Aries
 */
public class DataSurvey extends javax.swing.JInternalFrame {
    ResultSet r;
    Statement s;
    Connection c;
    private Object[][]databarang=null;
    private String[]label={"ID Barang","Nama Barang", "Harga", "Stok","Keterangan"};
    
    private static void cetak(String srcrespon2laporanjasper) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

     koneksi con = new koneksi();
     Date tgl=new Date();
     SimpleDateFormat noformat=new SimpleDateFormat("yyMM");
     JasperReport jasrep;
     JasperPrint jaspri;
     Map param = new HashMap();
     JasperDesign jasdes;
    
    public DataSurvey() {
        initComponents(); 
        bukakoneksi();
        bacaTable();
    }
    public void bacaTable(){
        try 
       {
           s=c.createStatement();
           String sql = "select * from barang";
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           databarang= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               databarang[x][0]=r.getString("id_barang");
               databarang[x][1]=r.getString("nama");
               databarang[x][2]=r.getString("harga");
               databarang[x][3]=r.getString("stok");
               databarang[x][4]=r.getString("keterangan");
               x++;
           }
           jtBarang.setModel(new DefaultTableModel(databarang, label));
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    public void pencarian(){
        try 
       {
           s=c.createStatement();
           String sql = "select * from barang where id_barang like '%"+jtCari.getText()+"%' or nama like '%"+jtCari.getText()+"%'";
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           databarang= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               databarang[x][0]=r.getString("id_barang");
               databarang[x][1]=r.getString("nama");
               databarang[x][2]=r.getString("harga");
               databarang[x][3]=r.getString("stok");
               databarang[x][4]=r.getString("keterangan");
               x++;
           }
           jtBarang.setModel(new DefaultTableModel(databarang, label));
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    public void reset(){
        jtKet.setText("");
        jtNama.setText("");
        jtHarga.setText("");
        jtID.setText("");
        jtStok.setText("");
        jtID.requestFocus();
    }
    private void bukakoneksi()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://localhost/kospinmu","root","");
            System.out.println("Koneksi Sukses");
        } 
        catch (Exception e) 
        {
            System.out.println("Koneksi Sukses");
        }
    }
    private void simpan()
    {
       try 
       {
           String sql = "INSERT INTO barang VALUES('"+jtID.getText()+"','"+jtNama.getText()+"','"
                   + ""+jtHarga.getText()+"','"+jtKet.getText()+"','"+jtStok.getText()+"')";
           s.executeUpdate(sql);
           s.close();
           JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
           bacaTable();
           reset();
       }
       catch (SQLException e) 
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    private void editdata()
    {
      try 
      {
          String sql= "UPDATE barang SET nama='"+jtNama.getText()+"', harga='"+jtHarga.getText()+"', keterangan='"+jtKet.getText()+"', stok='"+jtStok.getText()+"' WHERE id_barang='"+jtID.getText()+"'";
                    
          s.executeUpdate(sql);
          s.close();
          JOptionPane.showMessageDialog(null, "Data berhasil di Edit");
          bacaTable();
          reset();
      }
      catch (SQLException e) 
      {
          JOptionPane.showMessageDialog(null, e);
      }
    }
     private void settable()
   {
       int row = jtBarang.getSelectedRow();
       jtID.setText((String)jtBarang.getValueAt(row, 0));
       jtNama.setText((String)jtBarang.getValueAt(row, 1));
       jtStok.setText((String)jtBarang.getValueAt(row, 3));
       jtHarga.setText((String)jtBarang.getValueAt(row, 2));
       jtKet.setText((String)jtBarang.getValueAt(row, 4));
       jtBarang.requestFocus();
   }
    private void hapusdata()
  {
      try 
      {
          String sql = "DELETE FROM barang WHERE id_barang='"+jtID.getText()+"'";
          s.executeUpdate(sql);
          s.close();
          JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
          bacaTable();
          reset();
      } 
      catch (SQLException e)
      {
          JOptionPane.showMessageDialog(null, e);
      }
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtID = new javax.swing.JTextField();
        jtStok = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtKet = new javax.swing.JTextArea();
        jtNama = new javax.swing.JTextField();
        jtHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtBarang = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jbTambah = new javax.swing.JButton();
        jbEdit = new javax.swing.JButton();
        jBsimpan = new javax.swing.JButton();
        jbHapus = new javax.swing.JButton();
        jbReset = new javax.swing.JButton();
        jbKeluar = new javax.swing.JButton();
        cetak = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtCari = new javax.swing.JTextField();
        jbSearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Master :: Data Hasil Survey");

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID Barang");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nama Barang");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Stok");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Ket.");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jtID.setEnabled(false);
        jPanel3.add(jtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 160, -1));

        jtStok.setEnabled(false);
        jPanel3.add(jtStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 160, -1));

        jtKet.setColumns(20);
        jtKet.setRows(5);
        jtKet.setEnabled(false);
        jScrollPane1.setViewportView(jtKet);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 160, -1));

        jtNama.setEnabled(false);
        jPanel3.add(jtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 160, -1));

        jtHarga.setEnabled(false);
        jPanel3.add(jtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 160, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Harga");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 81, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, 250));

        jtBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        jtBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtBarang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 550, 190));

        jbTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Button-Add-icon.png"))); // NOI18N
        jbTambah.setText("Tambah");
        jbTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTambahActionPerformed(evt);
            }
        });

        jbEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Actions-document-edit-icon.png"))); // NOI18N
        jbEdit.setText("Edit");
        jbEdit.setEnabled(false);
        jbEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditActionPerformed(evt);
            }
        });

        jBsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Floppy-Small-icon.png"))); // NOI18N
        jBsimpan.setText("Simpan");
        jBsimpan.setEnabled(false);
        jBsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBsimpanActionPerformed(evt);
            }
        });

        jbHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Actions-edit-delete-icon.png"))); // NOI18N
        jbHapus.setText("Hapus");
        jbHapus.setEnabled(false);
        jbHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHapusActionPerformed(evt);
            }
        });

        jbReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Rotate-view-tool-icon.png"))); // NOI18N
        jbReset.setText("Reset");
        jbReset.setEnabled(false);
        jbReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbResetActionPerformed(evt);
            }
        });

        jbKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/logout-icon.png"))); // NOI18N
        jbKeluar.setText("Keluar");
        jbKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbKeluarActionPerformed(evt);
            }
        });

        cetak.setText("Cetak Barang");
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbTambah)
                .addGap(18, 18, 18)
                .addComponent(jbReset, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cetak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(jbKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                        .addComponent(jBsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                    .addComponent(jbReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 840, 70));

        jLabel1.setText("Search By ID Barang / Nama");

        jbSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Zoom-icon.png"))); // NOI18N
        jbSearch.setText("Search");
        jbSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbSearch)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSearch))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 550, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbKeluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbKeluarActionPerformed

    private void jbTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambahActionPerformed
        // TODO add your handling code here:
        jBsimpan.setEnabled(true);
        jbEdit.setEnabled(true);
        jbHapus.setEnabled(true);
        jbReset.setEnabled(true);
        jtID.setEnabled(true);
        jtNama.setEnabled(true);
        jtStok.setEnabled(true);
        jtHarga.setEnabled(true);
        jtKet.setEnabled(true);
        jtID.requestFocus();
    }//GEN-LAST:event_jbTambahActionPerformed

    private void jbResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jbResetActionPerformed

    private void jBsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBsimpanActionPerformed
        // TODO add your handling code here:
        if("".equals(jtID.getText()) || "".equals(jtNama.getText()) || "".equals(jtStok.getText())){
            JOptionPane.showMessageDialog(null, "Lengkapi dahulu data");
            jtID.requestFocus();
        }else{
            try {
                s=c.createStatement();
                String a="SELECT * FROM barang WHERE id_barang='"+jtID.getText()+"'";
                r=s.executeQuery(a);
                if(r.next()){
                    JOptionPane.showMessageDialog(null, "ID Barang sudah ada");
                    jtID.requestFocus();
                }else{
                    simpan();
                }
            } catch (Exception e) {
            }
            
        }
    }//GEN-LAST:event_jBsimpanActionPerformed

    private void jbEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditActionPerformed
        // TODO add your handling code here:
        if("".equals(jtID.getText())){
            JOptionPane.showMessageDialog(null, "Data Tidak ditemukan");
            jtID.requestFocus();
        }
        else{
            if(JOptionPane.showConfirmDialog(null, "apakah data ini ingin di Edit ?", "peringatan",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION)
            {
            editdata();
            }
        }
    }//GEN-LAST:event_jbEditActionPerformed

    private void jbHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHapusActionPerformed
        // TODO add your handling code here:
        if("".equals(jtID.getText())){
            JOptionPane.showMessageDialog(null, "Data Tidak ditemukan");
            jtID.requestFocus();
        }
        else{
            if(JOptionPane.showConfirmDialog(null, "apakah data ini ingin di Edit ?", "peringatan",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION)
            {
            hapusdata();
            }
        }
    }//GEN-LAST:event_jbHapusActionPerformed

    private void jbSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSearchActionPerformed
        // TODO add your handling code here:
        if("".equals(jtCari.getText())){
            bacaTable();
        }else{
        pencarian();
        }
    }//GEN-LAST:event_jbSearchActionPerformed

    private void jtBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtBarangMouseClicked
        // TODO add your handling code here:
        settable();
    }//GEN-LAST:event_jtBarangMouseClicked


    
    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        // TODO add your handling code here:\
        
            java.sql.Connection con = null;
        
         try{
             
             String jdbcDriver = "com.mysql.jdbc.Driver";
             Class.forName(jdbcDriver);
    
             String url = "jdbc:mysql://localhost/penjualan";
             String user = "root";
             String pass = "";
             
             con = DriverManager.getConnection(url, user, pass);
             Statement stm = con.createStatement();
             
             try {
                 String report = ("E:\\Semester 7 Agustus 2020\\Skripsi\\PROGRAM\\penjualan\\kospinmu\\src\\Form\\LaporanBarang.jrxml");
                 HashMap hash = new HashMap();
//                 hash.put("kode", cetak.getText());
                 JasperReport JRpt = JasperCompileManager.compileReport(report);
                 JasperPrint JPrint = JasperFillManager.fillReport(JRpt, hash, con);
                 JasperViewer.viewReport(JPrint, false);
             
             }catch(Exception e){
             JOptionPane.showMessageDialog(null,e);
                 
             }

    }catch(Exception e){
        Logger.getLogger (DataSurvey.class.getName()).log(Level.SEVERE,null,e);
        JOptionPane.showMessageDialog(null,e);
    }
    }//GEN-LAST:event_cetakActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cetak;
    private javax.swing.JButton jBsimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbEdit;
    private javax.swing.JButton jbHapus;
    private javax.swing.JButton jbKeluar;
    private javax.swing.JButton jbReset;
    private javax.swing.JButton jbSearch;
    private javax.swing.JButton jbTambah;
    private javax.swing.JTable jtBarang;
    private javax.swing.JTextField jtCari;
    private javax.swing.JTextField jtHarga;
    private javax.swing.JTextField jtID;
    private javax.swing.JTextArea jtKet;
    private javax.swing.JTextField jtNama;
    private javax.swing.JTextField jtStok;
    // End of variables declaration//GEN-END:variables
}


package Form;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Aries
 */
public final class JurnalForm extends javax.swing.JInternalFrame {
    ResultSet r;
    Statement s;
    Connection c;
    private Object[][]dataTransaksi=null;
    private String[]label={"ID Jurnal","Nama Akun", "Tanggal","Debit","Kredit"};

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
    
    public JurnalForm() {
        initComponents();
        bukakoneksi();
        muat_tabel_transaksi();
        off();
        jbTambah1.setEnabled(true);
        jurnalLama();
        setComboAkun();
        
    }
    
    public void reset(){
       jtNominal.setText("");
       jtTanggalAwal.requestFocus();
       
    }
    public void on(){
       jtJurnal.setEnabled(true);
       jtNominal.setEnabled(true);
       jtTanggalAkhir.setEnabled(true);
       jbSimpan.setEnabled(true);
       jBBaru.setEnabled(true);
       jtTanggalAwal.setEnabled(true);
       jComboBox1.setEnabled(true);
       jComboBoxTipe.setEnabled(true);
    }
    public void off(){
       jtJurnal.setEnabled(false);
       jtNominal.setEnabled(false);
       jtTanggalAwal.setEnabled(false);
       jtTanggalAkhir.setEnabled(false);
       jComboBox1.setEnabled(false);
       jComboBoxTipe.setEnabled(false);
       jbSimpan.setEnabled(false);
       jbTambah1.setEnabled(false);
       jtTotalTrans.setEnabled(false);
    }
    
    public void jurnalLama(){
        try {
            s=c.createStatement();
            String sql="SELECT id_jurnal FROM jurnal ORDER BY id_jurnal DESC";
            r = s.executeQuery(sql);
            if(r.next()){
                jtJurnal.setText(r.getString("id_jurnal"));
            }
            else{
                jtJurnal.setText("Jurnal-0001");
            }
        } catch (Exception e) {
        }
    }
    
    public void jurnal(){
        try {
            s=c.createStatement();
            String sql="SELECT id_jurnal FROM jurnal ORDER BY id_jurnal DESC";
            r = s.executeQuery(sql);
            if(r.next()){
            String id = r.getString("id_jurnal");
            int nomor= Integer.valueOf(id.substring(8,11));
                System.out.println(""+nomor);
                if(nomor<9){
                    
                jtJurnal.setText("Jurnal-000"+String.valueOf(nomor+1));
                }else if(nomor<99){
                jtJurnal.setText("Jurnal-00"+String.valueOf(nomor+1));
                }else if(nomor<999){
                jtJurnal.setText("Jurnal-0"+String.valueOf(nomor+1));
                }else{
                jtJurnal.setText("Jurnal-"+String.valueOf(nomor+1));
                }
            }
            else{
                jtJurnal.setText("Jurnal-0001");
            }
        } catch (Exception e) {
        }
    }
    
      
    public void pencarian(){
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID Jurnal");
        model1.addColumn("Nama Akun");
        model1.addColumn("Tanggal");
        model1.addColumn("Debit");
        model1.addColumn("Kredit");
        
       try{
           s=c.createStatement();
            String sql = "SELECT id_jurnal, nama, tanggal, debit, credit FROM jurnal WHERE id_jurnal LIKE '%"+jcari.getText()+"%' ORDER BY id_jurnal";
           
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           
           while(r.next()){
               model1.addRow(new Object[]{
                    r.getString("id_jurnal"),
                    r.getString("nama"),
                    r.getString("tanggal"),
                    r.getString("debit"),
                    r.getString("credit")
                });
           }
           jtbTrans.setModel(model1);
           r.close();
       }
       catch (Exception e){
           JOptionPane.showMessageDialog(null, e);
       }       
    }
    
    private void cekOut(){
        String tanggal ="YYYY-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tanggal);
        String tampil = String.valueOf(format.format(jtTanggalAkhir.getDate()));
        String sql = "";
        try{
            if (String.valueOf(jComboBoxTipe.getSelectedItem()).equalsIgnoreCase("Debit")) {
                sql = "INSERT INTO jurnal VALUES('"+jtJurnal.getText()+"',"
                 + " '"+jComboBox1.getSelectedItem()+"',"
                 + " '"+String.valueOf(format.format(jtTanggalAwal.getDate()))+"',"
                 + " '"+jtNominal.getText()+"',"
                 + " '0')";
            }else if(String.valueOf(jComboBoxTipe.getSelectedItem()).equalsIgnoreCase("Kredit")){
                sql = "INSERT INTO jurnal VALUES('"+jtJurnal.getText()+"',"
                 + " '"+jComboBox1.getSelectedItem()+"',"
                 + " '"+String.valueOf(format.format(jtTanggalAwal.getDate()))+"',"
                 + " '0',"
                 + " '"+jtNominal.getText()+"')";
            }
         
        s.executeUpdate(sql);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    private void muat_tabel_transaksi(){
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID Jurnal");
        model1.addColumn("Nama Akun");
        model1.addColumn("Tanggal");
        model1.addColumn("Debit");
        model1.addColumn("Kredit");
        
        try{
            String sql = "SELECT id_jurnal, nama, tanggal, debit, credit FROM jurnal ORDER BY id_jurnal";
            s=c.createStatement();
            r=s.executeQuery(sql);
            ResultSetMetaData m = r.getMetaData();
            
            while(r.next()){
                model1.addRow(new Object[]{
                    r.getString("id_jurnal"),
                    r.getString("nama"),
                    r.getString("tanggal"),
                    r.getString("debit"),
                    r.getString("credit")
                });
            }
            jtbTrans.setModel(model1);
            r.close();
        }catch(Exception e){
            
        }
    }
    
    private void setComboAkun(){
        try {
            String abg = "select nama from akun";
            s =c.createStatement();
            r = s.executeQuery(abg);
            while(r.next()){
                jComboBox1.addItem(r.getString("nama"));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }   
    }    
    private void ambilDetailTrans(){
        String tanggal ="YYYY-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tanggal);
        String awal = String.valueOf(format.format(jtTanggalAwal.getDate()));
        String akhir = String.valueOf(format.format(jtTanggalAkhir.getDate()));
        
        try{
            String sql = "SELECT SUM(bayar) FROM transaksi WHERE date(tanggal) BETWEEN '"+awal+"' AND '"+akhir+"'";
            s=c.createStatement();
            r=s.executeQuery(sql);
            
            while(r.next()){
                jtTotalTrans.setText(r.getString("SUM(bayar)"));
                
            }
           
            r.close();
        }catch(Exception e){
            
        }
    }
    
     private void settable()
       {
           int row = jtbTrans.getSelectedRow();
           jtT.setText((String)jtbTrans.getValueAt(row,0));
       }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jtTanggalAkhir = new com.toedter.calendar.JDateChooser();
        jbSimpan = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jBBaru = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jtJurnal = new javax.swing.JTextField();
        jtTanggalAwal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jtNominal = new javax.swing.JTextField();
        jtTotalTrans = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxTipe = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jBJurnalBaru = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbTrans = new javax.swing.JTable();
        jcari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jbCetak = new javax.swing.JButton();
        jtT = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        Jbkeluar = new javax.swing.JButton();
        jbTambah1 = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("APP :: Penjualan");

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtTanggalAkhir.setDateFormatString("yyyy-MM-dd");
        jtTanggalAkhir.setEnabled(false);
        jPanel2.add(jtTanggalAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 120, -1));

        jbSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Floppy-Small-icon.png"))); // NOI18N
        jbSimpan.setText("Simpan");
        jbSimpan.setEnabled(false);
        jbSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(jbSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 220, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tanggal");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jBBaru.setText("Tampil");
        jBBaru.setEnabled(false);
        jBBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBaruActionPerformed(evt);
            }
        });
        jPanel2.add(jBBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 70, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Jurnal");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jtJurnal.setEnabled(false);
        jPanel2.add(jtJurnal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 160, -1));

        jtTanggalAwal.setDateFormatString("yyyy-MM-dd");
        jtTanggalAwal.setEnabled(false);
        jPanel2.add(jtTanggalAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 120, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("-");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 20, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "==Pilih Akun==" }));
        jComboBox1.setEnabled(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Debit");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jtNominal.setEnabled(false);
        jPanel2.add(jtNominal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 150, -1));

        jtTotalTrans.setEnabled(false);
        jPanel2.add(jtTotalTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 280, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Total Transaksi");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jComboBoxTipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Debit", "Kredit" }));
        jComboBoxTipe.setEnabled(false);
        jPanel2.add(jComboBoxTipe, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 100, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tipe Akun");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jBJurnalBaru.setText("Jurnal Baru");
        jBJurnalBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBJurnalBaruActionPerformed(evt);
            }
        });
        jPanel2.add(jBJurnalBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 420, 480));

        jtbTrans.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbTrans.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbTransMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbTrans);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Cari Transaksi");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Zoom-icon.png"))); // NOI18N
        jButton2.setText("Cari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jbCetak.setText("Cetak Jurnal");
        jbCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jcari, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbCetak, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jtT))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jbCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 540, 480));

        Jbkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/logout-icon.png"))); // NOI18N
        Jbkeluar.setText("Keluar");
        Jbkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbkeluarActionPerformed(evt);
            }
        });

        jbTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Button-Add-icon.png"))); // NOI18N
        jbTambah1.setText("Tambah");
        jbTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbTambah1ActionPerformed(evt);
            }
        });

        jbRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Rotate-view-tool-icon.png"))); // NOI18N
        jbRefresh.setText("Refresh");
        jbRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Jbkeluar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbTambah1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(jbRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbTambah1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jbkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addGap(302, 302, 302))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JbkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbkeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_JbkeluarActionPerformed
    
    private void bukakoneksi(){
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
    
    private void jtbTransMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbTransMouseClicked
        // TODO add your handling code here:
        settable();
        
    }//GEN-LAST:event_jtbTransMouseClicked

    private void jbSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSimpanActionPerformed
        // TODO add your handling code here:
        if("".equals(jtJurnal.getText())||"".equals(jtTanggalAwal.getDate())||"".equals(jtTanggalAkhir.getDate())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        else{
            
            cekOut();
            muat_tabel_transaksi();
            off();
            jbTambah1.setEnabled(true);
            
        }
    }//GEN-LAST:event_jbSimpanActionPerformed

    private void jbTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambah1ActionPerformed
        // TODO add your handling code here:
        on();
        reset();
    }//GEN-LAST:event_jbTambah1ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if("".equals(jcari.getText())){
            muat_tabel_transaksi();
        }else{
            pencarian();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    
    private void jBBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBaruActionPerformed
        // TODO add your handling code here:
        if("".equals(jtJurnal.getText())||"".equals(jtTanggalAwal.getDate())||"".equals(jtTanggalAkhir.getDate())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        else{
            ambilDetailTrans();
        }
        
    }//GEN-LAST:event_jBBaruActionPerformed

    
    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        // TODO add your handling code here:
        muat_tabel_transaksi();
        setComboAkun();

    }//GEN-LAST:event_jbRefreshActionPerformed

    private void jbCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCetakActionPerformed
        // TODO add your handling code here:
        
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
                 String report = ("E:\\Semester 7 Agustus 2020\\Skripsi\\PROGRAM\\penjualan\\kospinmu\\src\\Form\\LaporanJurnal.jrxml");
                 HashMap hash = new HashMap();
                 hash.put("kode",jtT.getText());
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
    }//GEN-LAST:event_jbCetakActionPerformed
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
//        if(String.valueOf(jComboBox1.getSelectedItem()).equalsIgnoreCase("==Pilih Barang==")){
//
//            jtDebit.setText("");
//        }else{
//            try {
//                int total = 0;
//
//                String abg = "SELECT id_akun, nama FROM akun WHERE nama = '"+jComboBox1.getSelectedItem()+"'";
//                s =c.createStatement();
//                r = s.executeQuery(abg);
//                r.next();
//                jtDebit.setText(r.getString("id_akun"));
//                r.close();
//
//            } catch (Exception e) {
//            }
//        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jBJurnalBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBJurnalBaruActionPerformed
        // TODO add your handling code here:
        jurnal();
    }//GEN-LAST:event_jBJurnalBaruActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jbkeluar;
    private javax.swing.JButton jBBaru;
    private javax.swing.JButton jBJurnalBaru;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBoxTipe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCetak;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JButton jbSimpan;
    private javax.swing.JButton jbTambah1;
    private javax.swing.JTextField jcari;
    private javax.swing.JTextField jtJurnal;
    private javax.swing.JTextField jtNominal;
    private javax.swing.JTextField jtT;
    private com.toedter.calendar.JDateChooser jtTanggalAkhir;
    private com.toedter.calendar.JDateChooser jtTanggalAwal;
    private javax.swing.JTextField jtTotalTrans;
    private javax.swing.JTable jtbTrans;
    // End of variables declaration//GEN-END:variables
}

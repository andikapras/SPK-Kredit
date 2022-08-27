
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
public final class Trans extends javax.swing.JInternalFrame {
    ResultSet r;
    Statement s;
    Connection c;
    private Object[][]dataTransaksi=null;
    private String[]label={"Tanggal","ID Transaksi","Nama","Total Bayar"};

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
    
    public Trans() {
        initComponents();
        bukakoneksi();
        transk();
        setComboBarang();
        muat_tabel_transaksi();
        off();
       jbTambah1.setEnabled(true);
       perkalian();
       keuntungan();
       //jurnalLama();
        
        
    }
    public JTextField GETextField(){
        return jtNIS;
    }
    public JTextField GETextField1(){
        return jtNama;
    }
    public JTextField GETextField2(){
        return jtkelas;
    }
    
    
    public void reset(){
       jtNIS.setText("");
       jtNama.setText("");
       //jtTanggal.setText("");
       jtkelas.setText("");
       jtNIS.requestFocus();
       
    }
    public void on(){
       jtNIS.setEnabled(true);
       jtNama.setEnabled(true);
       jtTanggal.setEnabled(true);
       jtkelas.setEnabled(true);
       jbSimpan.setEnabled(true);
       jButton1.setEnabled(true);
       jBBaru.setEnabled(true);
       jtQty.setEnabled(true);
       jtTotalBayar.setEnabled(true);
    }
    public void off(){
       jtNIS.setEnabled(false);
       jtNama.setEnabled(false);
       jtTanggal.setEnabled(false);
       jtkelas.setEnabled(false);
       jbSimpan.setEnabled(false);
       jButton1.setEnabled(false);
       jbTambah1.setEnabled(false);
    }
    public void transk(){
        try {
            s=c.createStatement();
            String sql="SELECT id_transaksi FROM transaksi ORDER BY id_transaksi DESC";
            r = s.executeQuery(sql);
            if(r.next()){
            String id = r.getString("id_transaksi");
            int nomor= Integer.valueOf(id.substring(9,12));
                if(nomor<9){
                    
                jLabel2.setText("TRX-PNJ-000"+String.valueOf(nomor+1));
                }else if(nomor<99){
                jLabel2.setText("TRX-PNJ-00"+String.valueOf(nomor+1));
                }else if(nomor<999){
                jLabel2.setText("TRX-PNJ-0"+String.valueOf(nomor+1));
                }else{
                jLabel2.setText("TRX-PNJ-"+String.valueOf(nomor+1));
                }
            }
            else{
                jLabel2.setText("TRX-PNJ-0001");
            }
        } catch (Exception e) {
        }
    }
    
    public int totalB = 0;
    
    private void muat_tabel_detail(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("barang");
        model.addColumn("qty");
        model.addColumn("harga");
        model.addColumn("total");
        
        try{
           s=c.createStatement();
           String sql = "SELECT b.nama, d.jumlah, b.harga, d.total FROM barang b, detail_transaksi d WHERE d.id_barang = b.id_barang AND d.id_transaksi = '"+jLabel2.getText()+"'";
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
            
            while(r.next()){
                
                model.addRow(new Object[]{
                    r.getString("nama"),
                    r.getString("jumlah"),
                    r.getString("harga"),
                    r.getString("total"),
                });
                
            }
            totalB = totalB+Integer.valueOf(jlTotal.getText());
            jtTotalBayar.setText(String.valueOf(totalB));
            jTableBarang.setModel(model);
            r.close();
        }catch(Exception e){
            
        }
    }
    
    private void keuntungan(){
        
        int kNow = 0;
        try{
           String sql = "SELECT total FROM detail_transaksi";
           s=c.createStatement();
           r=s.executeQuery(sql);
            
            while (r.next()) {
                int k = Integer.valueOf(r.getString("total"));
                try{
//              System.out.println(stok);
                kNow = kNow + k;
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            jLabel16.setText(String.valueOf(kNow));
            r.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void pencarian(){
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID Transaksi");
        model1.addColumn("Tanggal");
        model1.addColumn("Nama");
        model1.addColumn("Total Bayar");
        
       try{
           s=c.createStatement();
            String sql = "SELECT t.id_transaksi, t.tanggal, s.nama, t.bayar FROM siswa s, transaksi t WHERE s.nis = t.nis AND t.id_transaksi LIKE '%"+jcari.getText()+"%' OR s.nis = t.nis AND s.nama LIKE '%"+jcari.getText()+"%' ORDER BY t.id_transaksi";
           
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           
           while(r.next()){
               model1.addRow(new Object[]{
                    r.getString("id_transaksi"),
                    r.getString("tanggal"),
                    r.getString("nama"),
                    r.getString("bayar")
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
        String tampil = String.valueOf(format.format(jtTanggal.getDate()));
        try{
         String sql = "INSERT INTO transaksi VALUES('"+jLabel2.getText()+"',"
                 + " '"+jtTotalBayar.getText()+"',"
                 + " '"+tampil+"',"
                 + " '"+jtNIS.getText()+"')";
         
         s.executeUpdate(sql);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    private void kwitansi(){
        try {
            s=c.createStatement();
            String sql="SELECT id_transaksi FROM kwitansi ORDER BY id_transaksi DESC";
            r = s.executeQuery(sql);
            while(r.next()){
                if (jLabel2.getText().equalsIgnoreCase(r.getString("id_transaksi"))) {
                    try{
                        sql = "UPDATE kwitansi SET id_transaksi = '"+jLabel2.getText()+"',"
                            + " nama_siswa = '"+jtNama.getText()+"',"
                            + " kelas = '"+jtkelas.getText()+"',"
                            + " nama_barang = '"+jcBarang.getSelectedItem()+"',"
                            + " bayar = '"+jtTotalBayar.getText()+"'";
                         s.executeUpdate(sql);
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
                    }else{
                        try{
                            sql = "INSERT INTO kwitansi VALUES('"+jLabel2.getText()+"',"
                                + " '"+jtNama.getText()+"',"
                                + " '"+jtkelas.getText()+"',"
                                + " '"+jcBarang.getSelectedItem()+"',"
                                + " '"+jtTotalBayar.getText()+"')";

                                s.executeUpdate(sql);
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }  
            }
        }catch(Exception ex){
            
        }
    }
            
    
    
    
//    private void updateKas(){
//        try{
//         String sql = ("UPDATE akun SET nominal = '"+jLabel12.getText()+"' WHERE id_akun = 'AKUN01'");
//         
//         s.executeUpdate(sql);
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
//    
//    private void updateJualan(){
//        try{
//         String sql = ("UPDATE akun SET nominal = '"+jLabel16.getText()+"' WHERE id_akun = 'AKUN02'");
//         
//         s.executeUpdate(sql);
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
//        }
//    }
    
    private void insertDetail(){
        
        try{
         String sql = "INSERT INTO detail_transaksi VALUES('"+jLabel2.getText()+"',"
                 + " '"+jlIdBarang.getText()+"',"
                 + " '"+jtNIS.getText()+"',"
                 + " '"+jtQty.getText()+"',"
                 + " '"+jlTotal.getText()+"')";
         
         s.executeUpdate(sql);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
        
    private void setComboBarang(){
        try {
            String abg = "select nama from barang";
            s =c.createStatement();
            r = s.executeQuery(abg);
            while(r.next()){
                jcBarang.addItem(r.getString("nama"));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }   
    }
    
    private void muat_tabel_transaksi(){
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID Barang");
        model1.addColumn("Tanggal");
        model1.addColumn("Nama");
        model1.addColumn("Total Bayar");
        
        try{
            String sql = "SELECT t.id_transaksi, t.tanggal, s.nama, t.bayar FROM siswa s, transaksi t WHERE s.nis = t.nis ORDER BY t.id_transaksi";
            s=c.createStatement();
            r=s.executeQuery(sql);
            ResultSetMetaData m = r.getMetaData();
            
            while(r.next()){
                model1.addRow(new Object[]{
                    r.getString("id_transaksi"),
                    r.getString("tanggal"),
                    r.getString("nama"),
                    r.getString("bayar")
                });
            }
            jtbTrans.setModel(model1);
            r.close();
        }catch(Exception e){
            
        }
    }
    
     private void settable()
       {
       
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtNIS = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jtTanggal = new com.toedter.calendar.JDateChooser();
        jbSimpan = new javax.swing.JButton();
        jtNama = new javax.swing.JTextField();
        jtkelas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jBBaru = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jtTotalBayar = new javax.swing.JTextField();
        jtQty = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableBarang = new javax.swing.JTable();
        jcBarang = new javax.swing.JComboBox<>();
        jBHitung = new javax.swing.JButton();
        jlTotal = new javax.swing.JLabel();
        jlIdBarang = new javax.swing.JLabel();
        jbCetak = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbTrans = new javax.swing.JTable();
        jcari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Jbkeluar = new javax.swing.JButton();
        jbTambah1 = new javax.swing.JButton();
        cetak1 = new javax.swing.JButton();
        jbRefresh = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("APP :: Penjualan");

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("TRX-PNJ-");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ID Nasabah");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jtNIS.setEnabled(false);
        jPanel2.add(jtNIS, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 210, -1));

        jButton1.setText("< >");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 60, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Kelas");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jtTanggal.setDateFormatString("yyyy-MM-dd");
        jtTanggal.setEnabled(false);
        jPanel2.add(jtTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 150, -1));

        jbSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Floppy-Small-icon.png"))); // NOI18N
        jbSimpan.setText("Simpan");
        jbSimpan.setEnabled(false);
        jbSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSimpanActionPerformed(evt);
            }
        });
        jPanel2.add(jbSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 220, 40));

        jtNama.setEnabled(false);
        jPanel2.add(jtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 280, -1));

        jtkelas.setEnabled(false);
        jPanel2.add(jtkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 280, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tanggal");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nama Nasabah");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("ID Transaksi");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Barang");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        jBBaru.setText("Beli");
        jBBaru.setEnabled(false);
        jBBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBaruActionPerformed(evt);
            }
        });
        jPanel2.add(jBBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 70, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Qty");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Total Bayar");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        jtTotalBayar.setEnabled(false);
        jPanel2.add(jtTotalBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, 220, -1));

        jtQty.setEnabled(false);
        jPanel2.add(jtQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 120, -1));

        jTableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Barang", "Qty", "Harga", "Total"
            }
        ));
        jScrollPane2.setViewportView(jTableBarang);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 400, 120));

        jcBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "==Pilih Barang==" }));
        jcBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcBarangItemStateChanged(evt);
            }
        });
        jcBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcBarangActionPerformed(evt);
            }
        });
        jPanel2.add(jcBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 120, -1));

        jBHitung.setText("Hitung");
        jBHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHitungActionPerformed(evt);
            }
        });
        jPanel2.add(jBHitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 70, -1));

        jlTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlTotal.setText("...");
        jPanel2.add(jlTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, -1, -1));

        jlIdBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlIdBarang.setText("...");
        jPanel2.add(jlIdBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, -1, -1));

        jbCetak.setText("Cetak Kwitansi");
        jbCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCetakActionPerformed(evt);
            }
        });
        jPanel2.add(jbCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 120, 40));

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

        jLabel12.setText("...");

        jLabel13.setText("Sisa Kas");

        jLabel14.setText("Total Transaksi");

        jLabel16.setText("...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jcari, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(110, 110, 110)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16))
                        .addGap(62, 62, 62))))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14))
                .addContainerGap(63, Short.MAX_VALUE))
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

        cetak1.setText("Cetak Laporan");
        cetak1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetak1ActionPerformed(evt);
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
                    .addComponent(jbTambah1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cetak1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
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
                .addComponent(jbRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(220, 220, 220)
                .addComponent(cetak1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    
    private void jtbTransMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbTransMouseClicked
        // TODO add your handling code here:
        settable();
    }//GEN-LAST:event_jtbTransMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cSiswa cs = new cSiswa();
        this.getParent().add(cs);
        cs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSimpanActionPerformed
        // TODO add your handling code here:
        if("".equals(jtNIS.getText())||"".equals(jtNama.getText())||"".equals(jtkelas.getText())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        else{
            
            cekOut();
            muat_tabel_transaksi();
            off();
            jbTambah1.setEnabled(true);
            
            totalB = 0;
        }
    }//GEN-LAST:event_jbSimpanActionPerformed

    private void jbTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbTambah1ActionPerformed
        // TODO add your handling code here:
        transk();
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

    private void perkalian(){
        int sxhNow = 0;
        try{
            String sql = "SELECT stok*harga as sxh FROM barang";
            s=c.createStatement();
            r=s.executeQuery(sql);
            
            while (r.next()) {
                int sxh = Integer.valueOf(r.getString("sxh"));
                try{
//              System.out.println(stok);
                sxhNow = sxhNow + sxh;
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            jLabel12.setText(String.valueOf(sxhNow));
            r.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void stok_kurang(){
        int stok = 0;
        try{
            String sql = "SELECT stok FROM barang WHERE id_barang = '"+jlIdBarang.getText()+"' ";
            s=c.createStatement();
            r=s.executeQuery(sql);
            
            while (r.next()) {
                stok = Integer.valueOf(r.getString("stok"));
            }
            r.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
//            System.out.println(stok);
            int stokNow = stok - Integer.valueOf(jtQty.getText());
            String sql = "UPDATE barang SET stok='"+stokNow+"' WHERE id_barang = '"+jlIdBarang.getText()+"'";
            s.executeUpdate(sql);
//            System.out.println(stokNow);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    
    }
    
    private void jBBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBaruActionPerformed
        // TODO add your handling code here:
        if("".equals(jtNIS.getText())||"".equals(jcBarang.getSelectedItem())||"".equals(jtQty.getText())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        else{
            insertDetail();
            muat_tabel_detail();
            stok_kurang();
            perkalian();
            //updateKas();
            keuntungan();
            //updateJualan();
       
        }
        
    }//GEN-LAST:event_jBBaruActionPerformed

    private void jBHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHitungActionPerformed
        // TODO add your handling code here:
        
        if("".equals(jcBarang.getSelectedItem())||"".equals(jtQty.getText())){
            JOptionPane.showMessageDialog(null, "Data Masih Kosong");
        }
        
        if(String.valueOf(jcBarang.getSelectedItem()).equalsIgnoreCase("==Pilih Barang==")){
            jlIdBarang.setText("...");
            jlTotal.setText("...");
        }else{
            try {
                int total = 0;

                String abg = "SELECT harga FROM barang WHERE nama = '"+jcBarang.getSelectedItem()+"'";
                s =c.createStatement();
                r = s.executeQuery(abg);
                r.next();
                int harga = r.getInt("harga");

                total = Integer.parseInt(jtQty.getText())*harga;
                jlTotal.setText(String.valueOf(total));
                r.close();
            } catch (Exception e) {
            }
        }
        
    }//GEN-LAST:event_jBHitungActionPerformed

    private void jcBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcBarangItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jcBarangItemStateChanged

    private void jcBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcBarangActionPerformed
        // TODO add your handling code here:
        if(String.valueOf(jcBarang.getSelectedItem()).equalsIgnoreCase("==Pilih Barang==")){

        jlIdBarang.setText("...");
            jlTotal.setText("...");
        }else{
            try {
                int total = 0;

                String abg = "SELECT id_barang, harga FROM barang WHERE nama = '"+jcBarang.getSelectedItem()+"'";
                s =c.createStatement();
                r = s.executeQuery(abg);
                r.next();

                jlIdBarang.setText(r.getString("id_barang"));
                int harga = r.getInt("harga");

                if (jtQty.getText().equalsIgnoreCase("")) {
                    jlTotal.setText(String.valueOf(total));
                }else{
                    total = Integer.parseInt(jtQty.getText())*harga;
                    jlTotal.setText(String.valueOf(total));
                }
                r.close();

            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jcBarangActionPerformed

    
    private void cetak1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetak1ActionPerformed
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
                 String report = ("E:\\Semester 7 Agustus 2020\\Skripsi\\PROGRAM\\penjualan\\kospinmu\\src\\Form\\LaporanTransaksi.jrxml");
                 
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
  
    }//GEN-LAST:event_cetak1ActionPerformed

    private void jbRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRefreshActionPerformed
        // TODO add your handling code here:
        muat_tabel_transaksi();

    }//GEN-LAST:event_jbRefreshActionPerformed

    private void jbCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCetakActionPerformed
        // TODO add your handling code here:
        
        
        kwitansi();
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
                 String report = ("E:\\Semester 7 Agustus 2020\\Skripsi\\PROGRAM\\penjualan\\kospinmu\\src\\Form\\LaporanKwitansi.jrxml");
                 HashMap hash = new HashMap();
                 hash.put("kode",jLabel2.getText());
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Jbkeluar;
    private javax.swing.JButton cetak1;
    private javax.swing.JButton jBBaru;
    private javax.swing.JButton jBHitung;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableBarang;
    private javax.swing.JButton jbCetak;
    private javax.swing.JButton jbRefresh;
    private javax.swing.JButton jbSimpan;
    private javax.swing.JButton jbTambah1;
    private javax.swing.JComboBox<String> jcBarang;
    private javax.swing.JTextField jcari;
    private javax.swing.JLabel jlIdBarang;
    private javax.swing.JLabel jlTotal;
    private javax.swing.JTextField jtNIS;
    private javax.swing.JTextField jtNama;
    private javax.swing.JTextField jtQty;
    private com.toedter.calendar.JDateChooser jtTanggal;
    private javax.swing.JTextField jtTotalBayar;
    private javax.swing.JTable jtbTrans;
    private javax.swing.JTextField jtkelas;
    // End of variables declaration//GEN-END:variables
}

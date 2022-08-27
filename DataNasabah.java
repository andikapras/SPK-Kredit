package Form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class DataNasabah extends javax.swing.JInternalFrame {
    ResultSet r;
    Statement s;
    Connection c;
    private Object[][]datasiswa=null;
    private String[]label={"ID Nasabah","Nama Nasabah", "Pekerjaan", "Jenis Kelamin"};
    /**
     * Creates new form DataNasabah
     */
    public DataNasabah() {
        initComponents();
        bukakoneksi();
        bacaTable();
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
    public void reset(){
        jtNama.setText("");
        
        jtNis.setText("");
        
        jtkelas.setText("");
       
        jtNis.requestFocus();
    }
    
    public void bacaTable(){
        try 
       {
           s=c.createStatement();
           String sql = "SELECT * from nasabah";
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           datasiswa= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               datasiswa[x][0]=r.getString("id");
               datasiswa[x][1]=r.getString("nama");
               datasiswa[x][2]=r.getString("pekerjaan");
               datasiswa[x][3]=r.getString("jns_kelamin");
                             
               x++;
           }
           jtbsiswa.setModel(new DefaultTableModel(datasiswa, label));
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
           String sql = "SELECT id, nama, jns_kelamin, kelas FROM siswa WHERE id like '%"+jtsearcj.getText()+"%' or nama like '%"+jtsearcj.getText()+"%'";
           r=s.executeQuery(sql);
           ResultSetMetaData m=r.getMetaData();
           int kolom=m.getColumnCount();
           int baris=0;
           while(r.next())
           {
               baris = r.getRow();
           }
           datasiswa= new Object[baris][kolom];
           int x=0;
           r.beforeFirst();
           while(r.next())
           {
               datasiswa[x][0]=r.getString("id");
               datasiswa[x][1]=r.getString("nama");
               datasiswa[x][2]=r.getString("pekerjaan");
               datasiswa[x][3]=r.getString("jns_kelamin");
               x++;
           }
           jtbsiswa.setModel(new DefaultTableModel(datasiswa, label));
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, e);
       }
    }
    private void simpan()
    {
        String tanggal ="YYYY-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(tanggal);
       try 
       {
           String sql = "INSERT INTO nasabah VALUES('"+jtNis.getText()+"',"
                   + "'"+jtNama.getText()+"','"+jtkelas.getText()+"',"
                   + "'"+jcJk.getSelectedItem()+"')";
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
    {   String tanggal ="YYYY-MM-dd";
      try 
      {
          String sql= "UPDATE nasabah SET nama='"+jtNama.getText()+"', jns_kelamin='"+jcJk.getSelectedItem()+"', kelas='"+jtkelas.getText()+"' WHERE id='"+jtNis.getText()+"'";
          
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
       int row = jtbsiswa.getSelectedRow();
       jtNis.setText((String)jtbsiswa.getValueAt(row, 0));
       jtNama.setText((String)jtbsiswa.getValueAt(row, 1));
       jtkelas.setText((String)jtbsiswa.getValueAt(row, 2));
       jcJk.setSelectedItem((String)jtbsiswa.getValueAt(row, 3));
       
       jtNis.requestFocus();
       }
     private void hapusdata()
    {
      try 
      {
          String sql = "DELETE FROM nasabah WHERE id='"+jtNis.getText()+"'";
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtNis = new javax.swing.JTextField();
        jtNama = new javax.swing.JTextField();
        jcJk = new javax.swing.JComboBox();
        jtkelas = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbsiswa = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jbreset = new javax.swing.JButton();
        jbtambah = new javax.swing.JButton();
        jbsimpan = new javax.swing.JButton();
        jbedit = new javax.swing.JButton();
        jbhapus = new javax.swing.JButton();
        jbkeluar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtsearcj = new javax.swing.JTextField();
        jbsearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Master :: Data Nasabah");

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID Nasabah");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nama");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Jenis Kelamin");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Kelas");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jtNis.setEnabled(false);
        jPanel3.add(jtNis, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 174, -1));

        jtNama.setEnabled(false);
        jtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNamaActionPerformed(evt);
            }
        });
        jPanel3.add(jtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 174, -1));

        jcJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laki - laki", "Perempuan" }));
        jcJk.setEnabled(false);
        jPanel3.add(jcJk, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 110, 20));

        jtkelas.setEnabled(false);
        jtkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtkelasActionPerformed(evt);
            }
        });
        jPanel3.add(jtkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 174, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 311, 250));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtbsiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbsiswaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtbsiswa);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 450, 230));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 470, 250));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Rotate-view-tool-icon.png"))); // NOI18N
        jbreset.setText("Reset");
        jbreset.setEnabled(false);
        jbreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbresetActionPerformed(evt);
            }
        });
        jPanel2.add(jbreset, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 20, 87, 45));

        jbtambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Button-Add-icon.png"))); // NOI18N
        jbtambah.setText("Tambah");
        jbtambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtambahActionPerformed(evt);
            }
        });
        jPanel2.add(jbtambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 20, -1, 45));

        jbsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Floppy-Small-icon.png"))); // NOI18N
        jbsimpan.setText("Simpan");
        jbsimpan.setEnabled(false);
        jbsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsimpanActionPerformed(evt);
            }
        });
        jPanel2.add(jbsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 20, -1, 45));

        jbedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Actions-document-edit-icon.png"))); // NOI18N
        jbedit.setText("Edit");
        jbedit.setEnabled(false);
        jbedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbeditActionPerformed(evt);
            }
        });
        jPanel2.add(jbedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 87, 45));

        jbhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Actions-edit-delete-icon.png"))); // NOI18N
        jbhapus.setText("Hapus");
        jbhapus.setEnabled(false);
        jbhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbhapusActionPerformed(evt);
            }
        });
        jPanel2.add(jbhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 20, 87, 45));

        jbkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/logout-icon.png"))); // NOI18N
        jbkeluar.setText("Keluar");
        jbkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbkeluarActionPerformed(evt);
            }
        });
        jPanel2.add(jbkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, 45));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 790, 80));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Search by ID / Nama");

        jbsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Zoom-icon.png"))); // NOI18N
        jbsearch.setText("Search");
        jbsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jtsearcj, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbsearch)
                .addGap(22, 22, 22))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtsearcj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jbsearch))
                .addGap(14, 14, 14))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 790, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbkeluarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbkeluarActionPerformed

    private void jbtambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtambahActionPerformed
        // TODO add your handling code here:
        jbedit.setEnabled(true);
     
        jbhapus.setEnabled(true);
        jbreset.setEnabled(true);
        jbsimpan.setEnabled(true);
        jtNis.setEnabled(true);
        jtNama.setEnabled(true);
        jtkelas.setEnabled(true);
        jcJk.setEnabled(true);
        jtNis.requestFocus();
    }//GEN-LAST:event_jbtambahActionPerformed

    private void jtbsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbsiswaMouseClicked
        // TODO add your handling code here:
        settable();
    }//GEN-LAST:event_jtbsiswaMouseClicked

    private void jbresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbresetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_jbresetActionPerformed

    private void jbsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsimpanActionPerformed
        // TODO add your handling code here:
        if("".equals(jtNis.getText()) || "".equals(jtNama.getText()) || "".equals(jtkelas.getText())){
            JOptionPane.showMessageDialog(null, "Lengkapi dahulu data");
            jtNis.requestFocus();
        }else{
            try {
                s=c.createStatement();
                String a="SELECT * FROM nasabah WHERE nis='"+jtNis.getText()+"'";
                r=s.executeQuery(a);
                if(r.next()){
                    JOptionPane.showMessageDialog(null, "ID Sudah ada");
                    jtNis.requestFocus();
                }else{
                    simpan();
                }
            } catch (Exception e) {
            }
            
        }
    }//GEN-LAST:event_jbsimpanActionPerformed

    private void jbeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbeditActionPerformed
        // TODO add your handling code here:
        if("".equals(jtNis.getText()) || "".equals(jtNama.getText()) || "".equals(jtkelas.getText())){
            JOptionPane.showMessageDialog(null, "Data Tidak ditemukan");
            jtNis.requestFocus();
        }
        else{
            if(JOptionPane.showConfirmDialog(null, "apakah data ini ingin di Edit ?", "peringatan",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION)
            {
            editdata();
            }
        }
    }//GEN-LAST:event_jbeditActionPerformed

    private void jbhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbhapusActionPerformed
        // TODO add your handling code here:
        if("".equals(jtNis.getText()) || "".equals(jtNama.getText()) || "".equals(jtkelas.getText())){
            JOptionPane.showMessageDialog(null, "Data Tidak ditemukan");
            jtNis.requestFocus();
        }
        else{
            if(JOptionPane.showConfirmDialog(null, "apakah data ini ingin di Edit ?", "peringatan",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION)
            {
            hapusdata();
            }
        }
    }//GEN-LAST:event_jbhapusActionPerformed

    private void jbsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsearchActionPerformed
        // TODO add your handling code here:
        if("".equals(jtsearcj.getText())){
            bacaTable();
        }else{
            pencarian();
        }
        
    }//GEN-LAST:event_jbsearchActionPerformed

    private void jtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtNamaActionPerformed

    private void jtkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtkelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtkelasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbedit;
    private javax.swing.JButton jbhapus;
    private javax.swing.JButton jbkeluar;
    private javax.swing.JButton jbreset;
    private javax.swing.JButton jbsearch;
    private javax.swing.JButton jbsimpan;
    private javax.swing.JButton jbtambah;
    private javax.swing.JComboBox jcJk;
    private javax.swing.JTextField jtNama;
    private javax.swing.JTextField jtNis;
    private javax.swing.JTable jtbsiswa;
    private javax.swing.JTextField jtkelas;
    private javax.swing.JTextField jtsearcj;
    // End of variables declaration//GEN-END:variables
}

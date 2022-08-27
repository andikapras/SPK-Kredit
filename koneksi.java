
package Form;

import com.mysql.jdbc.Driver;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class koneksi {
   
com.mysql.jdbc.Connection conn;
Statement st;
ResultSet rs; 
Properties Properti;
public void setkoneksi()
{
    try
    {
        Class.forName("com.mysql.jdbc.Driver");
        conn=(com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost/kospinmu","root","");
        st=conn.createStatement();
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,"<Error> Koneksikan Xampp Terlebih Dahulu : "+e,"Koneksi Gagal",JOptionPane.WARNING_MESSAGE);
    }}

}
//public class koneksi {
//private static Connection koneksi;
//Connection conn;
//Statement st;
//ResultSet rs; 
//Properties Properti;
//   
//public static Connection GetConnection()throws SQLException{
//       try {
//           if (koneksi==null){
//           new Driver();
//           koneksi=DriverManager.getConnection("jdbc:mysql://localhost/penjualan","root","");
//           conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/elektronik","root","root");
//           st=conn.createStatement();
//       }
//    } catch (Exception e) {
//           System.out.printf("no data base selected");
//    }
//       return koneksi;
//   }
//   public void cetak(String File){
//       try {
//	      Connection koneksi = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/penjualan","root","");
//	      HashMap parameter = new HashMap();
//	      InputStream file = getClass().getResourceAsStream(File);
//	      JasperPrint anggota = JasperFillManager.fillReport(file, parameter, koneksi);
//	      JasperViewer.viewReport(anggota, false);
//	 } catch (Exception e) {
//	      Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, e);
//	 }
//    }
//}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mulia_tani;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TreeMap;
import koneksi.db_koneksi;
import com.sun.javafx.image.impl.IntArgb;
import com.sun.prism.PresentableState;
import javax.swing.JComboBox;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Statement;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;



/**
 *
 * @author ACER
 */
public final class form_penjualan extends javax.swing.JFrame {
private DefaultTableModel model;
   
    /**
     * Creates new form form_penjualan
     */
    public form_penjualan() {
        initComponents();
        combo_pegawai();
        cmb_pelanggan();
        autoKey();
        
        txtnota.disable();
        txtkodepegawai.hide();
        txtstok.hide();
        txtdatetime.hide();
        txtpelanggan.hide();
        
        model = new DefaultTableModel ();
        tbljual.setModel(model);
        model.addColumn("Kode_barang");
        model.addColumn("Nama_barang");
        model.addColumn("Kode_pegawai");
        model.addColumn("Kode_pelanggan");
        model.addColumn("Harga_Jual");
        model.addColumn("Qty");
        model.addColumn("Subtotal");
        model.addColumn("Tanggal");
        
        
       
       
       loadData();
       Date date = new Date();
       jdate.setDate(date);
       
    }
    public void Batal(){
    int x,y,z ;
            x = Integer.parseInt(txtstok.getText());
            y = Integer.parseInt(txtqty.getText());
            z = x+y;
    String Kode_barang = this.txtkode.getText();
    
    try{
    String sql = "UPDATE data_barang set jumlah=? WHERE Kode_barang=?";
    PreparedStatement p = (PreparedStatement) db_koneksi.getkoneksi().prepareStatement(sql);
    p.setInt(1, z);
    p.setString(2, Kode_barang);
    p.executeUpdate();
    p.close();
    }catch(SQLException er){
    System.out.println("Terjadi Kesalahan");
    }finally{
        //JOptionPane.showMessageDialog(this, "Stok Barang Telah Diupdate Diubah");
}
    try{
    String sql = "DELETE from data_detailjual WHERE kode_nota='"+this.txtnota.getText()+"' AND tanggal='"+this.txtdatetime.getText()+"'";
    PreparedStatement p = (PreparedStatement) db_koneksi.getkoneksi().prepareStatement(sql); 
    p.executeUpdate();
    p.close();
    }catch(SQLException er){
        System.out.println("Terjadi Kesalahan");
    }finally{
        loadData();
        JOptionPane.showMessageDialog(this,"Sukses Hapus Data...");
    }
        
    }
    public final void loadData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    try{
        Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
        String sql = "Select * from data_detailjual, data_barang WHERE data_detailjual.Kode_barang = data_barang.Kode_barang AND data_detailjual.Kode_nota='"+this.txtnota.getText()+"'";
        ResultSet res = stat.executeQuery(sql);
        
    while(res.next()){
        Object[]o=new Object [8];
        o[0]= res.getString("Kode_barang");
        o[1]= res.getString("Nama_barang");
        o[2]= res.getString("Kode_pegawai");
        o[3]= res.getString("Kode_pelanggan");
        o[4]= res.getString("Harga_jual");
        o[5]= res.getString("Qty");
        o[6]= res.getString("Subtotal");
        o[7]= res.getString("Tanggal");
        
        model.addRow(o);
    }
        res.close();
        stat.close();
        showData();
    }catch(SQLException er){
        System.out.println("Terjadi Kesalahan");
    }
    
   int total = 0;
   for (int i =0; i< tbljual.getRowCount(); i++){
       int amount = Integer.parseInt((String)tbljual.getValueAt(i, 6));
       total += amount;
   }
   txttotal.setText(""+total);
 }


    
   public void cari_kode(){
   int i= tbljual.getSelectedRow();
   if(i==-1){ 
   return;
   }
   String ID=(String)model.getValueAt(i, 0); 
   txtkode.setText(ID);
   }
   
  public void showData(){
  try{
  Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
  String sql = "Select * from data_detailjual, data_barang WHERE data_detailjual.Kode_barang=data_barang.Kode_barang AND data_detailjual.kode_barang='"+this.txtkode.getText()+"'";
  ResultSet res = stat.executeQuery(sql);
  while(res.next()){
      this.txtkodepegawai.setText(res.getString("Kode_pegawai"));
      this.txtkode.setText(res.getString("Kode_barang"));
      this.txtqty.setText(res.getString("Qty"));
      this.txtnama.setText(res.getString("Nama_barang"));
      this.txtharga.setText(res.getString("Harga_jual"));
      this.txtsub.setText(res.getString("Subtotal"));
      this.txtdatetime.setText("Tanggal");
      this.txtpelanggan.setText("Kode_pelanggan");
  }
  res.close();
  stat.close();
  }catch(SQLException er){
    System.out.println(er.getMessage());
    }
}
  
public void showSisa(){
    try{
    Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
    String sql = "Select * from data_barang WHERE Kode_barang='"+txtkode.getText()+"'";
    ResultSet res = stat.executeQuery(sql);
    while(res.next()){
        this.txtstok.setText(res.getString("Jumlah"));
    }res.close(); stat.close();
    }
        catch (Exception er){
                System.out.println(er.getMessage());
                }
    }

public void updateStock(){
 int x,y,z;
 x = Integer.parseInt(txtstok.getText());
 y = Integer.parseInt(txtqty.getText());
 z = x-y;
 String kode_barang = this.txtkode.getText();
 try{
   Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
   String sql = "UPDATE data_barang set jumlah=? WHERE Kode_barang=?";
   PreparedStatement p = (PreparedStatement) db_koneksi.getkoneksi().prepareStatement(sql);
   p.setInt(1, z);
   p.setString(2, kode_barang);
   p.executeUpdate();
   p.close();
 }catch(SQLException er){
     System.out.println("Terjadi Kesalahan");
}finally{
     
 }
}

 public void autoSum(){
     int a, b, c;
     a = Integer.parseInt(txtharga.getText());
     b = Integer.parseInt(txtqty.getText());
     c = a*b;
     txtsub.setText(""+c);
 }
  public void hitungKembali(){
      int d, e, f;
      d = Integer.parseInt(txttotal.getText());
      e = Integer.parseInt(txtcash.getText());
      f = e-d;
      txtkembali.setText(""+f);
  }  
  
  public void autoKey(){
      try{
          java.util.Date tgl = new java.util.Date();
          java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyMMdd");
          java.text.SimpleDateFormat tanggal = new java.text.SimpleDateFormat("yyyyMMdd");
          Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
          String sql = "select max(kode_nota) FROM data_jual WHERE tanggal="+tanggal.format(tgl);
          ResultSet res = stat.executeQuery(sql);
          while (res.next()){
          Long a = res.getLong(1);
          if(a == 0){
              this.txtnota.setText(kal.format(tgl)+"0000"+(a+1));
          }else{
              this.txtnota.setText(""+(a+1));
          }
      }
          res.close();stat.close();}
      catch (Exception er){
          JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
      }
      
  }
  
  public void selesai(){
      String Kode_nota =this.txtnota.getText();
      String Kode_pelanggan =this.txtpelanggan.getText();
      String Kode_pegawai = this.txtkodepegawai.getText();
      String Total = this.txttotal.getText();
      String Cash = this.txtcash.getText();
      String Kembali =this.txtkembali.getText();
      
      //Date date = new Date();
      //jdate.setDate(date);
      
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date tanggal = new Date();
      tanggal = jdate.getDate();
      String Tanggal = dateFormat.format(tanggal);
      
    try{
     Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
     String sql = "Insert into data_jual (Kode_nota, Kode_pegawai, Tanggal, Total, Cash, Kembali) Values (?,?,?,?,?)";
     PreparedStatement p=(PreparedStatement)db_koneksi.getkoneksi().prepareStatement(sql);
     p.setString(1, Kode_nota);
     p.setString(2, Kode_pegawai);
     p.setString(3, Total);
     p.setString(4, Cash);
     p.setString(5, Kembali);
     p.executeUpdate();
     p.close();
    }catch (SQLException er){
        System.out.println(er);
    }finally{
        //loadData();
        JOptionPane.showMessageDialog(this, "Data Telah Tersimpan");
    }
    autoKey();
    loadData();
  }
  public void tambahDetail(){
      Date HariSekarang = new Date();
      SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
      
    
      String Kode_barang = this.txtkode.getText();
      String Nama_barang = this.txtnama.getText();
      String Kode_nota = this.txtnota.getText();
      String Kode_pegawai = this.txtkodepegawai.getText();
      String Harga_jual = this.txtharga.getText();
      String Qty = this.txtqty.getText();
      String Subtotal = this.txtsub.getText();
      String Tanggal = ft.format(HariSekarang);
      String Kode_pelanggan = this.txtpelanggan.getText();
      
    try{
      Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
      String sql ="Insert into data_detailjual (Kode_barang, Nama_barang, Kode_nota, Kode_pegawai,Kode_pelanggan,  Harga_jual, Qty, Subtotal, Tanggal) values (?,?,?,?,?,?,?,?,?)";
      PreparedStatement p=(PreparedStatement)db_koneksi.getkoneksi().prepareStatement(sql);
      
      
      p.setString(1, Kode_barang);
      p.setString(2, Nama_barang);
      p.setString(3, Kode_nota);
      p.setString(4, Kode_pegawai);
      p.setString(5, Kode_pelanggan);
      p.setString(6, Harga_jual);
      p.setString(7, Qty);
      p.setString(8, Subtotal);
      p.setString(9, Tanggal);
      p.executeUpdate();
      p.close();
    }catch(SQLException er){
        System.out.println(er);
    }finally{
        //loadData();
        //JOptionPane.showMessageDialog(this, "Data Telah Tersimpan");
    }
    
  }

public void cari_id(){
try{
 Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
 String sql = "Select * from data_barang WHERE data_barang.Kode_barang='"+this.txtkode.getText()+"'";
 ResultSet res = stat.executeQuery(sql);

 while (res.next()){
 this.txtnama.setText(res.getString("Nama_barang"));
 this.txtharga.setText(res.getString("Harga_jual"));
 this.txtstok.setText(res.getString("Jumlah"));
}res.close(); stat.close();
}catch(Exception er){
    System.out.println(er.getMessage());
}
}
public void cari_nama(){
    try{
     Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
     String sql_data = "select Kode_pegawai from data_pegawai where Nama_pegawai='"+cmbopegawai.getSelectedItem()+"'";
     ResultSet res  = stat.executeQuery(sql_data);
    while(res.next()){
        this.txtkodepegawai.setText(res.getString("Kode_pegawai"));
    }res.close(); stat.close();
    }catch (Exception er){
        System.out.println(er.getMessage());
    }  
 }
public void cari_pelanggan(){
    try{
     Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
     String sql_data = "select Kode_pelanggan from data_pelanggan where Nama_pelanggan='"+cmbpelanggan.getSelectedItem()+"'";
     ResultSet res  = stat.executeQuery(sql_data);
    while(res.next()){
        this.txtpelanggan.setText(res.getString("Kode_pelanggan"));
    }res.close(); stat.close();
    }catch (Exception er){
        System.out.println(er.getMessage());
    }  
 }
public void bersihkan(){
    txtkode.setText("");
    txtnama.setText("");
    txtharga.setText("");
    txtqty.setText("");
    txtcash.setText("");
    txtsub.setText("");
    txtkembali.setText("");
    txtpelanggan.setText("");
}
public void combo_pegawai(){
    try{
     Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
     String sql_tc = "select Kode_pegawai, Nama_pegawai FROM data_pegawai order by Kode_pegawai asc";
     ResultSet res = stat.executeQuery(sql_tc);
    while (res.next()){
        String nama = res.getString("Nama_pegawai");
        cmbopegawai.addItem(nama);
    }res.close(); stat.close();
    }catch (Exception er){
        System.out.println(er.getMessage());
    }
}
public void cmb_pelanggan(){
    try{
     Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
     String sql_tc = "select Kode_pelanggan, Nama_pelanggan FROM data_pelanggan order by Kode_pelanggan asc";
     ResultSet res = stat.executeQuery(sql_tc);
    while (res.next()){
        String nama = res.getString("Nama_pelanggan");
        cmbpelanggan.addItem(nama);
    }res.close(); stat.close();
    }catch (Exception er){
        System.out.println(er.getMessage());
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtnota = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jdate = new com.toedter.calendar.JCalendar();
        jLabel11 = new javax.swing.JLabel();
        cmbopegawai = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cmbpelanggan = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtkode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtharga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtqty = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtsub = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbljual = new javax.swing.JTable();
        txttotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnadd = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtcash = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtkembali = new javax.swing.JTextField();
        btnsimpan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtstok = new javax.swing.JTextField();
        txtkodepegawai = new javax.swing.JTextField();
        txtdatetime = new javax.swing.JTextField();
        txtpelanggan = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setBackground(new java.awt.Color(0, 0, 51));
        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORM PENJUALAN MULIA TANI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("No Faktur");

        txtnota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnotaActionPerformed(evt);
            }
        });
        txtnota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnotaKeyPressed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nama Pegawai");

        cmbopegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbopegawaiMouseClicked(evt);
            }
        });
        cmbopegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbopegawaiActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nama Pelanggan");

        cmbpelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbpelangganMouseClicked(evt);
            }
        });
        cmbpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbpelangganActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kode Barang");

        txtkode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodeKeyPressed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nama Barang");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Harga Jual");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Qty");

        txtqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtqtyKeyPressed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Subtotal");

        tbljual.setModel(new javax.swing.table.DefaultTableModel(
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
        tbljual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbljualMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbljual);

        txttotal.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        txttotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txttotalMouseClicked(evt);
            }
        });
        txttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalActionPerformed(evt);
            }
        });
        txttotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txttotalKeyPressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Pembelian (Rp)");

        btnadd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnadd.setText("ADD");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btnbatal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnbatal.setText("Batal");
        btnbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbatalActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("CASH    Rp.");

        txtcash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcashKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("KEMBALI  Rp.");

        btnsimpan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Keluar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel12))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnota, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbopegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txttotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtsub, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnbatal, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(btnadd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtcash, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(606, 606, 606)
                                .addComponent(txtstok))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnsimpan)
                                        .addGap(26, 26, 26)
                                        .addComponent(jButton1)))
                                .addGap(608, 608, 608)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtkodepegawai)
                                    .addComponent(txtdatetime))))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtnota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel2))
                            .addComponent(jdate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbopegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtcash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(txtstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtkembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnsimpan)
                            .addComponent(jButton1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(txtkodepegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtdatetime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(txtpelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
Batal();
bersihkan();
// TODO add your handling code here:
    }//GEN-LAST:event_btnbatalActionPerformed

    private void tbljualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbljualMouseClicked
this.cari_kode();
this.showData();
this.showSisa();       // TODO add your handling code here:
    }//GEN-LAST:event_tbljualMouseClicked

    private void txtkodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodeKeyPressed
 if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
            cari_id();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodeKeyPressed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
selesai();
bersihkan();
// TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
tambahDetail();
updateStock();
loadData();
bersihkan();        // TODO add your handling code here:
    }//GEN-LAST:event_btnaddActionPerformed

    private void cmbopegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbopegawaiActionPerformed
cari_nama();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbopegawaiActionPerformed

    private void txtqtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyPressed
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
    autoSum();
    }        // TODO add your handling code here:
    }//GEN-LAST:event_txtqtyKeyPressed

    private void txtcashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcashKeyPressed
 if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
     hitungKembali();
     }        // TODO add your handling code here:
    }//GEN-LAST:event_txtcashKeyPressed

    private void cmbopegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbopegawaiMouseClicked
cari_nama();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbopegawaiMouseClicked

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

    private void txttotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txttotalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalMouseClicked

    private void txtnotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotaActionPerformed

    private void txtnotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotaKeyPressed

    private void txttotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbpelangganActionPerformed
cari_pelanggan();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbpelangganActionPerformed

    private void cmbpelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbpelangganMouseClicked
cari_pelanggan();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbpelangganMouseClicked

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
            java.util.logging.Logger.getLogger(form_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btnbatal;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JComboBox<String> cmbopegawai;
    private javax.swing.JComboBox<String> cmbpelanggan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JCalendar jdate;
    private javax.swing.JTable tbljual;
    private javax.swing.JTextField txtcash;
    private javax.swing.JTextField txtdatetime;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtkembali;
    private javax.swing.JTextField txtkode;
    private javax.swing.JTextField txtkodepegawai;
    private javax.swing.JTextField txtnama;
    private javax.swing.JTextField txtnota;
    private javax.swing.JTextField txtpelanggan;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtstok;
    private javax.swing.JTextField txtsub;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables

  
}

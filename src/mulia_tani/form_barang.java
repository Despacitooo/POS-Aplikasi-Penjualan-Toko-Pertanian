/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mulia_tani;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import koneksi.db_koneksi;
import java.sql.ResultSet;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author ACER
 */
public final class form_barang extends javax.swing.JFrame {
private final DefaultTableModel model;
String Kode_barang,Nama_barang,Jenis_barang,Harga_beli,Harga_jual,Jumlah;
    /**
     * Creates new form form_barang
     */
public void loadData(){
        Kode_barang = txtkode.getText();
        Nama_barang = txtnama.getText();
        Jenis_barang = (String)cmboxjenis.getSelectedItem();
        Harga_beli = txtbeli.getText();
        Harga_jual = txtjual.getText();
        Jumlah = txtjumlah.getText();
        
    }
public void saveData(){
        loadData();
    try{
        Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
        String sql = "insert into data_barang(Kode_barang, Nama_barang, Jenis_barang, Harga_beli, Harga_jual, Jumlah)"
                + "values ('"+ Kode_barang +"', '"+ Nama_barang +"', '"+ Jenis_barang +"', '"+ Harga_beli +"','"+ Harga_jual +"', '"+ Jumlah +"')";
      PreparedStatement p =(PreparedStatement) db_koneksi.getkoneksi().prepareStatement(sql);
        p.executeUpdate();
        getData();
    }catch (SQLException err){
        JOptionPane.showMessageDialog(null, err.getMessage());             
    }
    }
    public void Reset (){
    Kode_barang = "";
    Nama_barang = "";
    Jenis_barang = "";
    Harga_beli = "";
    Harga_jual = "";
    Jumlah = "";
    txtkode.setText(Kode_barang);
    txtnama.setText(Nama_barang);
    txtbeli.setText("");
    txtjual.setText("");
    txtjumlah.setText("");
    }
    public void dataSelect(){
    int i = tblbarang.getSelectedRow();
    if (i == -1){
    return;
    }
    txtkode.setText(""+model.getValueAt(i, 0));
    txtnama.setText(""+model.getValueAt(i, 1));
    cmboxjenis.setSelectedItem(""+model.getValueAt(i, 2));
    txtbeli.setText(""+model.getValueAt(i, 3));
    txtjual.setText(""+model.getValueAt(i, 4));
    txtjumlah.setText(""+model.getValueAt(i, 5));
    }
    public void updateData(){
    loadData();
    try{
        Statement stat = (Statement)db_koneksi.getkoneksi().createStatement();
        String sql = "UPDATE Data_barang SET Kode_barang = '"+ Kode_barang +"',"
                                      +"Nama_barang = '"+ Nama_barang +"',"
                                      +"Jenis_barang  = '"+ Jenis_barang +"',"
                                      +"Harga_beli  = '"+ Harga_beli +"',"
                                      +"Harga_jual  = '"+ Harga_jual +"',"
                                      +"Jumlah = '"+ Jumlah +"' WHERE Kode_barang = '"+ Kode_barang + "'";                        
        PreparedStatement p = (PreparedStatement)db_koneksi.getkoneksi().prepareStatement(sql);
        p.executeUpdate();
        
        getData();
        
        Reset ();
        JOptionPane.showMessageDialog(null, "Update Berhasil....");
    }catch(SQLException er){
        JOptionPane.showMessageDialog(null, er.getMessage());
    }
    }
    public void deleteData(){
    loadData();
    int pesan = JOptionPane.showConfirmDialog(null, "Anda Yakin Menghapus Data"+ Kode_barang +"?", "Konfirmasi",
    JOptionPane.OK_CANCEL_OPTION);
    
    if (pesan == JOptionPane.OK_OPTION){
        try{
            Statement stat = (Statement)db_koneksi.getkoneksi().createStatement();
            String sql = "DELETE FROM Data_barang WHERE Kode_barang = '"+ Kode_barang +"'";
            PreparedStatement p = (PreparedStatement)db_koneksi.getkoneksi().prepareStatement(sql);
            p.executeUpdate();
            getData();
            Reset();
            JOptionPane.showMessageDialog(null, "Delete Berhasil");
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, er.getMessage());
        
        
        }
    }
    
}
    public form_barang() {
        initComponents();
        model = new DefaultTableModel ();
        tblbarang.setModel(model);
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jenis Barang");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Jumlah(Stok)");
    getData();
    }
   
 public void getData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
    try{
        Statement stat = (Statement) db_koneksi.getkoneksi().createStatement();
        String sql = "select * from Data_barang";
        ResultSet res = stat.executeQuery(sql);
    while(res.next()){
        Object[]obj = new Object [6];
        obj[0] = res.getString("Kode_barang");
        obj[1] = res.getString("Nama_barang");
        obj[2] = res.getString("Jenis_barang");
        obj[3] = res.getString("Harga_beli");
        obj[4] = res.getString("Harga_jual");
        obj[5] = res.getString("Jumlah");
        
    model.addRow(obj);
    }
                
   }catch (SQLException err){
       JOptionPane.showMessageDialog(null, err.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtkode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtnama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmboxjenis = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtbeli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtjual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        btnsave = new javax.swing.JButton();
        btnreset = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbarang = new javax.swing.JTable();
        btnupdate = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORM BARANG MULIA TANI");

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kode Barang");

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Barang");

        jLabel4.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jenis Barang");

        cmboxjenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pupuk", "Obat", "Benih", "Alat Pertanian" }));
        cmboxjenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmboxjenisActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Harga Beli");

        txtbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbeliActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Harga Jual");

        jLabel7.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Jumlah (Stok)");

        btnsave.setText("Save");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnreset.setText("Reset");
        btnreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetActionPerformed(evt);
            }
        });

        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        tblbarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tblbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblbarang);

        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(cmboxjenis, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtbeli, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtjual, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtkode)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(txtbeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtjual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmboxjenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(53, 53, 53)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnreset, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
saveData();        // TODO add your handling code here:
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetActionPerformed
Reset();        // TODO add your handling code here:
    }//GEN-LAST:event_btnresetActionPerformed

    private void cmboxjenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmboxjenisActionPerformed
       // TODO add your handling code here:
    }//GEN-LAST:event_cmboxjenisActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
updateData();        // TODO add your handling code here:
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
deleteData();        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
this.dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void tblbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbarangMouseClicked
dataSelect();        // TODO add your handling code here:
    }//GEN-LAST:event_tblbarangMouseClicked

    private void txtbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbeliActionPerformed

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
            java.util.logging.Logger.getLogger(form_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnreset;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cmboxjenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblbarang;
    private javax.swing.JTextField txtbeli;
    private javax.swing.JTextField txtjual;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkode;
    private javax.swing.JTextField txtnama;
    // End of variables declaration//GEN-END:variables
}

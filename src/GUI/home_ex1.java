
package GUI;

import com.sun.glass.events.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Code.*;
import java.awt.print.PrinterException;
import java.sql.DriverManager;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class home_ex1 extends javax.swing.JFrame implements Runnable {
    int hour,second,minutes;
    int day,month,year;
    String timestr,yearstr;
    
    Connection Con;
    PreparedStatement pst;
    PreparedStatement pstup;
  //  PreparedStatement pstadd;
    ResultSet rs;
    Statement std;
     Statement stt;
    

   
    public home_ex1() {
        Thread t = new Thread(this);
        t.start();
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //SetUndercorated(true);
      //  passvalTable();
      FindUser();
      
       
    }
    //==================================================================ADD-Details==============================================================================
    public void addDetails()
    {
     String p_code = txtproduct_code.getText();
            txtqty.requestFocus();
          
          
            txtpay.setText("");
            txtbalance.setText("");
          
          // txttotal.setText("");
            
            printbill_txtarea.setText("");
            
            
            try {
               // Class.forName("com.mysql.jdbc.Driver");   //  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/perera_shop","root","");

               DBConnection connect = new DBConnection();
               Connection con = connect.getConnection();
               pst = con.prepareStatement("select * from product_sales where item_code = ? ");
                
                
                pst.setString(1, p_code);
                rs = pst.executeQuery();
                
                if(rs.next()==false)
                {
                    JOptionPane.showMessageDialog(this,"Product code not found");
                    txtproduct_code.setText("");
                    txtproduct_code.requestFocus();
                }
                else
                {
                    String product_name = rs.getString("item_name");
                    String price = rs.getString("price");
                    
                    txtproduct_name.setText(product_name.trim());
                    txtprice.setText(price.trim());
                }
                
            } catch (SQLException | NullPointerException ex) {
                Logger.getLogger(home_ex1.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
//=================================================================set sum to 0==================================================================
    public void setsumzero(){
         DefaultTableModel tMOdel = (DefaultTableModel) jTable1.getModel();
            tMOdel.setRowCount(0);
           
    }
    //==============================================================================ADDTOTAL====================================================================
    public void addTot()
    {
        try{
            
        
        int qty = Integer.parseInt(txtqty.getText()); //.toString()
        Float price = Float.parseFloat(txtprice.getText());
       
       
       Float tot = qty * price;
        
        txtamount.setText(Float.toString(tot));
        
        
        }
        catch(NumberFormatException ex){}
   
    }
    //==============================================================================GETBALANCE====================================================================
     public void Balance()  //create balance class
    {
         float total = Float.parseFloat(txttotal.getText());
        float pay = Float.parseFloat(txtpay.getText());
        
        if(pay<total)
        {
            JOptionPane.showMessageDialog(this,"The payment is incorrect");
            txtpay.setText("");
           txtpay.requestFocus();
           
        }
        else{
            
        float bal = pay - total;
        txtbalance.setText(String.valueOf(bal));
        bill();
        setsumzero();
        dailysaleDatabase();
        txtproduct_code.requestFocus();
        
        }
    }
     //==============================================================================PRINTBILL====================================================================
     public void bill()
    {
        String total = txttotal.getText();
        String pay = txtpay.getText();
        String bal = txtbalance.getText();
        
        DefaultTableModel model = new DefaultTableModel();
       
       model = (DefaultTableModel)jTable1.getModel();
       
      // printbill_txtarea.setText(printbill_txtarea.getText() + "------------------------------------------------------------------------\n");
       
       printbill_txtarea.setText(printbill_txtarea.getText() + "                             PERERA WINE STORES             \n"); 
       printbill_txtarea.setText(printbill_txtarea.getText() + "                                   PUDALUOYA                   \n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "                                 TEL : 0774592528             \n"); 
       printbill_txtarea.setText(printbill_txtarea.getText() + "--------------------------------------------------------------------------------\n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "PRODUCT" + "\t" + "PRICE" + "\t " + "QTY" + "\t"+ "AMOUNT" + "\n" ); 
       printbill_txtarea.setText(printbill_txtarea.getText() + "--------------------------------------------------------------------------------\n");
       for(int i =0 ;i< model.getRowCount();i++)
       {
           String pno = (String)model.getValueAt(i, 0);
           String pname = (String)model.getValueAt(i, 1);
           String price = (String)model.getValueAt(i, 3);
           String quantity = (String)model.getValueAt(i, 2);
           String amount = (String)model.getValueAt(i, 4);
           
           printbill_txtarea.setText(printbill_txtarea.getText()+pno+"\n" + pname + "\t" + price + "\t " + quantity + "\t" + amount + "\n" );
       }
        printbill_txtarea.setText(printbill_txtarea.getText() + "--------------------------------------------------------------------------------\n");
      // printbill_txtarea.setText(printbill_txtarea.getText() + "\n");// blank spaces
       
       printbill_txtarea.setText(printbill_txtarea.getText() + "SUBTOTAL"+"\t"+"\t"+"\t"+ total + "\n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "CASH"+"\t"+"\t"+"\t"+ pay + "\n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "--------------------------------------------------------------------------------\n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "BALANCE"+"\t"+"\t"+"\t"+ bal + "\n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "DATE :"+yearstr+"\n");
        printbill_txtarea.setText(printbill_txtarea.getText() + "TIME :"+timestr+"\n");
        printbill_txtarea.setText(printbill_txtarea.getText() + "--------------------------------------------------------------------------------\n");
        printbill_txtarea.setText(printbill_txtarea.getText() + "                                       THANKYOU              \n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "--------------------------------------------------------------------------------\n");
       printbill_txtarea.setText(printbill_txtarea.getText() + "                           'Your feedback is welcome'"+ "\n");
       printbill_txtarea.setText(printbill_txtarea.getText() +"                   (c) 2020 Team HillCity  771234567"+ "\n");
       
    }
     //==============================================================================PASS VALUES TO THE MAIN TABLE=============================================================
     public void passTable()
     {
          
        
        
       DefaultTableModel model = new DefaultTableModel();
       
       model = (DefaultTableModel)jTable1.getModel();
       
       
       String datee = date.getText();
       String timee = time.getText();

       String datetime = datee+"_"+timee;
       
       model.addRow(new Object[]
       {
           
           datetime,
           txtproduct_code.getText(),
           txtproduct_name.getText(),
           txtqty.getText(),//.toString(),
           txtprice.getText(),
           txtamount.getText(),
       
       });
       
       float sum = 0;
       
       
       for(int i = 0;i<jTable1.getRowCount();i++)
       {
           sum = sum + Float.parseFloat(jTable1.getValueAt(i,5).toString());
          
       }
       txttotal.setText(Float.toString(sum));
      
       txtproduct_code.setText("");
       txtqty.setText("");
       txtproduct_name.setText("");
       txtprice.setText("");
       txtamount.setText("");
       txtproduct_code.requestFocus();
      //  txttotal.setText(Float.toString(sum = 0.0f));
    
     }
   
     //==============================================================================TABLE ROW REMOVE====================================================================
     public void btnremoveRow()
     {
      DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        //get selected row index
        
        int selectedrowindex = jTable1.getSelectedRow();
        model.removeRow(selectedrowindex);
     }
     //==============================================================================PASS PRODUCT DETAILS TO SUB TABLE===================================================
//     public void passvalTable()
//     {
//            // TODO add your handling code here:
//            
//            Class.forName("com.mysql.jdbc.Driver");
//            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/perera_shop","root","")) {
//                Statement st = con.createStatement();
//                String sql = "select * from product_sales ";
//                ResultSet ps = st.executeQuery(sql);
//        try {
//                
//                while(ps.next())
//                {
//                    String pcode = String.valueOf(ps.getString("item_code"));
//                    String pname = String.valueOf(ps.getString("item_name"));
//                    String price = String.valueOf(ps.getString("price"));
//                    
//                    //string array to store data in jtable
//                    
//                    String tbData[] = {pcode,pname,price};
//                    DefaultTableModel tblModel = (DefaultTableModel)jTable223.getModel();
//                    
//                    // add string array to table
//                    tblModel.addRow(tbData);
//                }
//            }
//            
//        } catch (SQLException | ClassNotFoundException ex){}
//     
//     }

    // ==============================================PASS PRODUCT DETAILS TO SUB TABLE AND SEARCH ITEMS===================================================
     public ArrayList<SearchClass> ListProduct(String ValToSearch)
     {
         ArrayList<SearchClass> ProductList = new ArrayList<SearchClass>();
         
        try{
            DBConnection connecttable = new DBConnection();
               Connection con = connecttable.getConnection();
             Statement st = con.createStatement();
             String searchQuery = "SELECT * FROM `product_sales` WHERE CONCAT(`item_code`, `item_name`, `price`) LIKE '%"+ValToSearch+"%'";
             rs = st.executeQuery(searchQuery);
             
             
             while(rs.next())
             {
               SearchClass searchclass = new  SearchClass(
               
                       rs.getString("item_code"),
                       rs.getString("item_name"),
                       rs.getString("price")
                       
               
               
               );
               ProductList.add(searchclass);
             }
        }
        catch(SQLException ex){
            
        }
        return ProductList;
         
     }
     public void FindUser(){
         
         ArrayList<SearchClass> searchclass = ListProduct(jTextField1.getText());
         DefaultTableModel model = new DefaultTableModel();
         model.setColumnIdentifiers(new Object[]{"item_code","item_name","price",});
         Object[] row = new Object[3];
         
         for(int i =0;i<searchclass.size();i++){
             row[0] = searchclass.get(i).getPcode();
             row[1] = searchclass.get(i).getPname();
             row[2] = searchclass.get(i).getPrice();
             model.addRow(row);
             jTable223.setModel(model);
         }
     }
     //=================================================update quantity===========================================================================
     public void UpdateQty(){
         
         try{
             
             DBConnection updateconnect = new DBConnection();
               Connection con = updateconnect.getConnection();
              pstup = con.prepareStatement("UPDATE `product_sales` SET `qty` = `qty` - quantity where `item_code` =  productID");
             
             
          
             
             for(int i=0;i<jTable1.getRowCount();i++) 
             {
               String productID = (String)jTable1.getValueAt(i, 1);
               
               int quantity =Integer.parseInt((String)jTable1.getValueAt(i, 3)) ;
              
                
                pstup.setInt(1, quantity);
                pstup.setString(2, productID);
                pstup.executeUpdate();
                 
             }
             
         }
         catch(SQLException e){}
         
        
       
     }
     
     //========================================================add dailty sales========================================================================================
     
     public void add(){
         
         try{
             
             DBConnection addconnection = new DBConnection();
               Connection con = addconnection.getConnection();
              String datee = date.getText();
       String timee = time.getText();

       String datetime = datee+"_"+timee;
               
               
           String sql = "INSERT INTO `all_sales`(date_time,`Procuctid`, `ProductName`, `Quantity`, `unitprice`,`amount`) VALUES ('"+datetime+"','"+txtproduct_code.getText()+"','"+txtproduct_name.getText()+"','"+txtqty.getText()+"','"+txtprice.getText()+"','"+txtamount.getText()+"')";
        
           PreparedStatement pstadd = con.prepareStatement(sql);
            pstadd.execute();
         
           
           
         }
         catch(SQLException e){
              JOptionPane.showMessageDialog(rootPane,e);
         } 
     }
     //====================================================================remove/delete data from table and database=============================================================================
     
     public void btnremobeDatabase(){
         
         
         int row = jTable1.getSelectedRow();
         String cell = jTable1.getModel().getValueAt(row,0).toString();
         String sqll = "DELETE FROM `all_sales` WHERE date_time = " + cell;
         try{
              DBConnection deleteRow = new DBConnection();
               Connection con = deleteRow.getConnection();
             
             pst = con.prepareStatement(sqll);
             pst.execute();
             JOptionPane.showMessageDialog(null, "Deleted Succesfully");
         }
         catch(Exception e){
               JOptionPane.showMessageDialog(null, e);
         }
         
         
     }
     //===================================================================================add daily sales to database==================================================
     public void dailysaleDatabase()
     {
         try
         {
             
         
         DBConnection dailysale = new DBConnection();
               Connection con = dailysale.getConnection();
         
       String datee = date.getText();
       String timee = time.getText();
       String datetime = datee+"_"+timee;
       
        String query = "INSERT INTO `daily_sales`(time_date,`total_price`, `cus_pay`, `balance`) VALUES ('"+datetime+"','"+txttotal.getText()+"','"+txtpay.getText()+"','"+txtbalance.getText()+"')";
        
         PreparedStatement pstadd = con.prepareStatement(query);
            pstadd.execute();
         
         }
         catch(SQLException e){
             
         }
       
         
     }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btn_remove_rows = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtproduct_code = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtqty = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        addbtn = new javax.swing.JButton();
        txtproduct_name = new javax.swing.JLabel();
        txtprice = new javax.swing.JLabel();
        txtamount = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtpay = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtbalance = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        printbill_txtarea = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable223 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel5.setBackground(new java.awt.Color(51, 153, 0));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setText("Copyright © 2020 by Team HillCity ");

        btn_remove_rows.setBackground(new java.awt.Color(255, 0, 0));
        btn_remove_rows.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_remove_rows.setText("REMOVE SELECTED ROW");
        btn_remove_rows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_remove_rowsActionPerformed(evt);
            }
        });
        btn_remove_rows.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_remove_rowsKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_remove_rows, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(359, 359, 359))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_remove_rows, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel21))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        time.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 255));

        date.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        date.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("PRODUCT CODE :");

        txtproduct_code.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtproduct_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproduct_codeActionPerformed(evt);
            }
        });
        txtproduct_code.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtproduct_codeKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setText("PRODUCT NAME :");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel17.setText("QUANTITY :");

        txtqty.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtqtyKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel18.setText("UNIT PRICE :");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel19.setText("AMOUNT :");

        addbtn.setBackground(new java.awt.Color(0, 153, 0));
        addbtn.setText("ADD");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });
        addbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                addbtnKeyPressed(evt);
            }
        });

        txtproduct_name.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtproduct_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtprice.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtprice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtamount.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        txtamount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        txtamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtamountKeyPressed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 0, 51));
        jButton2.setText("PAY");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton2KeyPressed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "PRODUCT CODE", "PRODUCT NAME", "QUANTITY", "PRICE", "AMOUNT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(5);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel23.setText("TOTAL");

        txttotal.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel24.setText("PAY");

        txtpay.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txtpay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpayActionPerformed(evt);
            }
        });
        txtpay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpayKeyPressed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel25.setText("BALANCE");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        txtbalance.setBackground(new java.awt.Color(255, 255, 255));
        txtbalance.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        txtbalance.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtbalance, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(txtbalance, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        printbill_txtarea.setColumns(20);
        printbill_txtarea.setRows(5);
        jScrollPane2.setViewportView(printbill_txtarea);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("[End] to pay");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel25))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtpay)
                            .addComponent(txttotal)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel22)))
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTable223.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Code", "Product Name", "Unit Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable223);
        if (jTable223.getColumnModel().getColumnCount() > 0) {
            jTable223.getColumnModel().getColumn(0).setResizable(false);
            jTable223.getColumnModel().getColumn(0).setHeaderValue("Product Code");
            jTable223.getColumnModel().getColumn(1).setResizable(false);
            jTable223.getColumnModel().getColumn(1).setHeaderValue("Product Name");
            jTable223.getColumnModel().getColumn(2).setResizable(false);
            jTable223.getColumnModel().getColumn(2).setHeaderValue("Unit Price");
        }

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton3.setText("Search Product");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtproduct_code, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                            .addComponent(txtproduct_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtqty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19)
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtamount, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtamount, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                            .addComponent(addbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtproduct_name, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7))
                                        .addGap(11, 11, 11))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(5, 5, 5)))))
                                .addGap(44, 44, 44))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19)
                                        .addComponent(txtproduct_code, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(35, 35, 35)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtproduct_codeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproduct_codeKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                addDetails();
                break;
            case KeyEvent.VK_END:
                txtpay.requestFocus();
                break;
            case KeyEvent.VK_ESCAPE:
        //  Quit cExit = new Quit();
        //  cExit.quit();
        //    Main_tab tbs = new Main_tab();
        //    tbs.setVisible(true);
        //   this.dispose();
        JFrame frame = new JFrame("Exit");
        if(JOptionPane.showConfirmDialog(frame,"Confirm if you want to exit","Sales management system", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION)
        {
               Main_tab tbs = new Main_tab();
           tbs.setVisible(true);
          this.dispose();
        }
                
                
                
                break;
            default:
                break;
        }
     
    }//GEN-LAST:event_txtproduct_codeKeyPressed

    private void txtqtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyPressed
        // TODO add your handling code here:
        addTot();
     
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            addbtn.requestFocus();
        }
         else if(evt.getKeyCode() == KeyEvent.VK_END)
        {
            txtpay.requestFocus();
        }
   
    }//GEN-LAST:event_txtqtyKeyPressed
 
    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        // TODO add your handling code here:
        add();
        
    }//GEN-LAST:event_addbtnActionPerformed

    private void addbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addbtnKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            add();
       passTable();
       
       
        }
     
    }//GEN-LAST:event_addbtnKeyPressed

    private void jButton2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton2KeyPressed
        // TODO add your handling code here:
        txtpay.requestFocus();
    }//GEN-LAST:event_jButton2KeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtpayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpayKeyPressed
        // TODO add your handling code here:
        
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
       
            Balance();
           // bill();
          //  setsumzero();
         //   dailysaleDatabase();
       
            try {
                printbill_txtarea.print();
            } catch (PrinterException ex) {
                Logger.getLogger(home_ex1.class.getName()).log(Level.SEVERE, null, ex);
            }

        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
        {

         //   txtproduct_code.requestFocus();
             
        }
        }
        
        
    }//GEN-LAST:event_txtpayKeyPressed

    private void txtproduct_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproduct_codeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproduct_codeActionPerformed

    private void txtpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpayActionPerformed

    private void btn_remove_rowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_remove_rowsActionPerformed
        // TODO add your handling code here:
      
      btnremobeDatabase();
      btnremoveRow();
    }//GEN-LAST:event_btn_remove_rowsActionPerformed

    private void btn_remove_rowsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_remove_rowsKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_remove_rowsKeyPressed

    private void txtamountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtamountKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtamountKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        FindUser();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

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
            java.util.logging.Logger.getLogger(home_ex1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home_ex1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home_ex1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home_ex1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new home_ex1().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addbtn;
    private javax.swing.JButton btn_remove_rows;
    private javax.swing.JLabel date;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable223;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea printbill_txtarea;
    private javax.swing.JLabel time;
    private javax.swing.JLabel txtamount;
    private javax.swing.JLabel txtbalance;
    private javax.swing.JTextField txtpay;
    private javax.swing.JLabel txtprice;
    private javax.swing.JTextField txtproduct_code;
    private javax.swing.JLabel txtproduct_name;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        while(true)
        {
            try{
                Calendar c = Calendar.getInstance();
                hour=c.get(Calendar.HOUR_OF_DAY);
                if(hour>12){
                    hour = hour - 12;
                }
                minutes=c.get(Calendar.MINUTE);
                second=c.get(Calendar.SECOND);
                year=c.get(Calendar.YEAR);
                month=c.get(Calendar.MONTH);
                day =c.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
                Date dat = c.getTime();
                timestr=sdf.format(dat);
                yearstr=df.format(dat);
                
               try
               {
                  time.setText(timestr);
                date.setText(yearstr);  
               }
               catch(NullPointerException e)
               {
               }
                
                
                
                
                
            }catch(Exception e){e.printStackTrace(); }
        }
    }
}

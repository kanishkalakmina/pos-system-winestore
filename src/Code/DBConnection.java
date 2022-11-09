 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lakmina
 */
public class DBConnection {
    
   public Connection getConnection()
     {
         Connection con = null;
         try{
           
             
             
              con = DriverManager.getConnection("jdbc:mysql://localhost/perera_shop","root","");
         }
         catch(SQLException ex){
             
         }
         return con;
     }
    
}

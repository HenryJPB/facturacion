/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import bean.interfase.IControladorBDOracle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author henrypb
 */
public class ControladorBDOracle implements IControladorBDOracle {

       private final String driver = "oracle.jdbc.driver.OracleDriver";
       private final String oracleSID = "DES102";
       private final String host = "193.168.0.3";
       private final String usuario = "ops$desInv02";
       private final String contrasena = "ops$desInv02";
       private final String puerto = "1521";
       private Connection con;  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void connect() {
      try {
          Class.forName(driver);
          String url = "jdbc:oracle:thin:@" + host +":"+puerto+":" + oracleSID;
          //System.out.println(url+","+usuario+","+contrasena);
          con = DriverManager.getConnection(url, usuario, contrasena);
      } catch ( Exception excepcion ) { System.err.println( excepcion ); }
       //catch (Exception e) { System.out.println("ERROR: fallo la coneccion a MySQL");};
    } // connect.
      
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Connection getConeccionOracle() {
              if ( con == null ) {
           connect();
      }
      else
              try {
                  if ( con.isClosed() ) {
                      connect();
                  }   } catch (SQLException ex) {
                  Logger.getLogger(ControladorBDOracle.class.getName()).log(Level.SEVERE, null, ex);
              }
      return con;
    }
    
}

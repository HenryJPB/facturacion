/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.libreria;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author henrypb
 */
public class LibreriaHP {
    
    public SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public DecimalFormat formatoDecimal = new DecimalFormat("###,###,###,##0.00"); 
    
    public DecimalFormat formatoFactura = new DecimalFormat("000000");
    
    public DecimalFormat formatoNcf = new DecimalFormat("0000000000"); 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public boolean esNumerico( String valorStr ) {
        try {
           double d = Double.parseDouble(valorStr);
        }  // try.
        catch(NumberFormatException nfe)  {
              return false;
        }  // catch.  
        return true;  
    }  //
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void error( JPanel panel, JTextField campo, String mensaje ) {
        campo.setBackground(Color.YELLOW);
        JOptionPane.showMessageDialog(panel,mensaje,"ATENCION",JOptionPane.ERROR_MESSAGE);
        campo.setBackground(Color.WHITE);
        campo.requestFocus();
    }  // errror(). 
    
    //--------------------------------------------------------------------------
    // convertir un String a un String en formato numerico convencional. 
    // Ejm. 10.213,45 -> 10213.45 para convertir en <>.Parse... / new BigDecimal().  
    //--------------------------------------------------------------------------
    public String convFormatoNumerico( String valorS1 ) {
        String valorS2 = null; 
        valorS2 = valorS1.replace(".","");  
        valorS2 = valorS2.replace(",",".");   
        valorS2 = valorS2.replace(" ", "");  
        return (valorS2);
    }  //  convFormatoNumerico(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void notificarFocus(JTextField campo) {
        campo.setBackground(Color.YELLOW);
        campo.requestFocus();
    }  // notificarFocus().  
    
}  // MyLibreryHP.  

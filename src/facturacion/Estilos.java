/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facturacion;

import java.awt.Color;
import java.text.SimpleDateFormat;

/**
 *
 * @author henrypb
 */
public class Estilos {
      private final int r = 184;
      private final int g = 191;
      private final int b = 195; 
      // setBackground());
      public java.awt.Color backgroundColor =  new java.awt.Color(184, 191, 195); 
      public static final Integer xWindow = 940,
                                  yWindow = 750;  
      public static Color colorCelda = Color.LIGHT_GRAY;   // color by default.  
      private static final String patronFecha = "dd/MM/yyyy";  
      public static SimpleDateFormat formatoFecha = new SimpleDateFormat(patronFecha);
}  // public class Estilos.  

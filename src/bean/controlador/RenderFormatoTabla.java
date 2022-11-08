/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author henrypb
 */
public class RenderFormatoTabla extends JFormattedTextField implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
            //Si values es nulo dara problemas de renderizado, por lo tanto se pone como vacio
            JFormattedTextField celda = new JFormattedTextField();
            //
            if (value == null) {
                celda.setText("");
            }
            else if (value instanceof String ) {
                celda.setText(value.toString());
            }
            else if(value instanceof Integer ){
                celda.setValue(value);
                celda.setHorizontalAlignment(SwingConstants.CENTER); 
               //valorCelda.setHorizontalAlignment(SwingConstants.CENTER);
            }
            else if ( value instanceof BigDecimal || value instanceof Double || value instanceof Float ) {
                celda.setValue(value);
                celda.setHorizontalAlignment(SwingConstants.RIGHT);
                celda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###,###,##0.00")))); 
            }
            /*
            if (row == 1) {
                lbl.setHorizontalAlignment(SwingConstants.RIGHT); //alina a la izquierda
            }
            if (row == 2) {
                lbl.setForeground(Color.BLUE); //fuente azul
            }
            if (column == 1 & row != 2) { //color de fondo
                lbl.setOpaque(true);
                lbl.setBackground(Color.YELLOW);
            }
            if (isSelected ) {
                celda.setOpaque(true);
                celda.setBackground(Color.YELLOW);
            }
            */
            return celda;
        }   // getTableCellRendererComponent().  

}     // clase RenderFormatoTabla().  

// ----------------------------------------------------------------------------------------------------------
/*    Ejemplo tomado Originalmente asi (el dia 01/08/2013):  
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            //Si values es nulo dara problemas de renderizado, por lo tanto se pone como vacio
            JLabel lbl = new JLabel(value == null ? "" : value.toString());
            if (row == 1) {
                lbl.setHorizontalAlignment(SwingConstants.RIGHT); //alina a la izquierda
            }
            if (row == 2) {
                lbl.setForeground(Color.BLUE); //fuente azul
            }
            if (column == 1 & row != 2) { //color de fondo
                lbl.setOpaque(true);
                lbl.setBackground(Color.YELLOW);
            }
            return lbl;
        }   // getTableCellRendererComponent().  
*/
// -------------------------------------------------------------------------------------------------------------

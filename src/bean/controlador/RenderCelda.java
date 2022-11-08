/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import facturacion.Estilos;  
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author henrypb
 * NO funciona correctamente.
 * Sujeto a Revision (Brqto: 10/10/2013).  
 * 
 */
public class RenderCelda extends JFormattedTextField implements TableCellRenderer {

    private final Integer ordColumnCodProd = 2;  
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            //Si values es nulo dara problemas de renderizado, por lo tanto se pone como vacio
            //JLabel celda = new JLabel(value == null ? "" : value.toString());
            //JFormattedTextField celda = new JFormattedTextField(value == null ? "" : value.toString());
            JFormattedTextField celda = new JFormattedTextField();
            if (value instanceof String ) {
                celda.setText(value.toString());
            }
            else if (value instanceof Integer ) {
                celda.setValue(value);
                celda.setHorizontalAlignment(SwingConstants.CENTER);
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
            */
            if ( column == ordColumnCodProd ) { // Style de las celdas de la columna. 
                celda.setOpaque(true);
                celda.setBackground(Color.cyan);
                celda.setFont(new Font("Sanserif", Font.BOLD, 13));
            }
            if (isSelected ) {
                celda.setOpaque(true);
                celda.setBackground(Color.YELLOW);
                //celda.setBackground(Estilos.colorCelda);
            }
            return celda;
    }
}

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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author henrypb
 */
public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
 
  public CheckBoxRenderer() {
    setOpaque(true);
  }
  
    @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else{
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("CheckBox.background"));   // Brqto: 09/10/2012. 
    }
    setText( (value ==null) ? "" : value.toString() );
    return this;
  }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

/**
 *
 * @author henrypb
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
 
/**
 * @version 1.0 11/09/98
 */
public class BotonRenderer extends JButton implements TableCellRenderer {
 
  public BotonRenderer() {
    setOpaque(true);
    setBackground(Color.green); 
  }
  
    @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
    boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else{
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    // Las siguientes instrucciones aplican al objeto; Hablamos de Button.  
    setFont(new Font("Sanserif", Font.BOLD, 12)); 
    //setBackground(Color.LIGHT_GRAY);   
    setText( (value ==null) ? "" : value.toString() );
    return this;
  }
  
}

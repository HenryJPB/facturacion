/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author henrypb
 */
public class CheckBoxEditor extends DefaultCellEditor {
  protected JCheckBox checkBox;
  private String      label;
  private boolean     isPushed;
 
  public CheckBoxEditor(JCheckBox checkBox) {
    super(checkBox);
    checkBox = new JCheckBox();
    checkBox.setOpaque(true);
    checkBox.addActionListener(new ActionListener() {
            @Override
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }
 
    @Override
  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
    if (isSelected) {
      checkBox.setForeground(table.getSelectionForeground());
      checkBox.setBackground(table.getSelectionBackground());
    } else{
      checkBox.setForeground(table.getForeground());
      checkBox.setBackground(table.getBackground());
    }
    label = (value ==null) ? "" : value.toString();
    checkBox.setText( label );
    isPushed = true;
    return checkBox;
  }
 
    @Override
  public Object getCellEditorValue() {
    if (isPushed)  {
      // 
      // 
      JOptionPane.showMessageDialog(checkBox ,label + ": Ouch!");
      // System.out.println(label + ": Ouch!");
    }
    isPushed = false;
    return label ;
  }
   
    @Override
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }
 
    @Override
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}


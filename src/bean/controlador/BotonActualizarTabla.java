/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

/**
 *
 * @author henrypb
 */
import bean.entidad.Inv08Dat;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @version 1.0 11/09/98
 */
public class BotonActualizarTabla extends DefaultCellEditor {

    protected JButton button;
    private String botonLabel;
    private String idRow;
    private Double cantidad;
    //private enum COLUMNA {ITEM_NO, CODIGO, TIPO, DESCRIPCION, PESO, NO_PEDIDO, UNIDADES,ATADOS,ITEMS, PRECIO,ALICUOTA, PESO_GUIA, CONFORME, ELIMINAR, ID_ROW };
    ControladorInv08.COLUMNA RENGLON;
    private boolean isPushed;
    //private Guia02 itemsGuia02;

    public BotonActualizarTabla(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        botonLabel = (value == null) ? "" : value.toString();
        button.setText(botonLabel);
        isPushed = true;
        // ---------------------------------------------------------------------
        // EJEMPLO:  ejecucion correcta al 01/08/2013.   
        /* ---------------------------------------------------------------------
         itemsGuia02 = getItemsLista(table, row);                            // Var de ambito Global.  
         idRow = (String) table.getValueAt(row, RENGLON.ID_ROW.ordinal());   // Var de ambito Global. 
         RenderCelda renderCelda = new RenderCelda();  
         Estilos.colorCelda=Color.RED;     // Color que utilizaremos para reenderizar la celda de la tabla.
         table.getColumnModel().getColumn(RENGLON.TOTAL_PESO.ordinal()).setCellRenderer(renderCelda.render);
         // Despues de ejecutar alguna accion de chequeo o validacion: Retornar al color by default.  
         ------------------------------------------------------------------------*/
        //
        //cantidad = (Double) table.getValueAt(row, RENGLON.CANTIDAD.ordinal());
        //
        return button;
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String facXpeso(Boolean facPeso) {
        if (facPeso) {
            return ("X");
        } else {
            return null;
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String facXunidad(Boolean facUnidad) {
        if (facUnidad) {
            return ("X");
        }
        return null;
    }

    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    private Inv08Dat getItemsLista(JTable tabla, int fila) {
        Inv08Dat inv08Dat = null; 
        //int indiceColumna = COLUMNA.DESCRIPCION.ordinal(); 
        //valor = (  tabla.getValueAt(fila,indiceColumna )==null?"<<nulo>>":tabla.getValueAt(fila, indiceColumna).toString() ); 
        /*
        Guia02 guia02 = null;
        String nroGuia = PanelGuiaDespacho.txtDisplayNroGuia.getText();
        if (nroGuia != null) {
            Integer itemNo = (Integer) tabla.getValueAt(fila, RENGLON.ITEM_NO.ordinal());
            String codigo = (String) tabla.getValueAt(fila, RENGLON.CODIGO.ordinal());
            String tipo = (String) tabla.getValueAt(fila, RENGLON.TIPO.ordinal());
            String descripcion = (String) tabla.getValueAt(fila, RENGLON.DESCRIPCION.ordinal());
            Double peso = (Double) tabla.getValueAt(fila, RENGLON.PESO_UNI.ordinal());
            String nroPedido = (String) tabla.getValueAt(fila, RENGLON.NO_PEDIDO.ordinal());
            Double cantidad = (Double) tabla.getValueAt(fila, RENGLON.CANTIDAD.ordinal());
            Double items = (Double) tabla.getValueAt(fila, RENGLON.ITEMS.ordinal());
            String atados = (String) tabla.getValueAt(fila, RENGLON.ATADOS.ordinal());
            Double precio = (Double) tabla.getValueAt(fila, RENGLON.PRECIO.ordinal());
            Double alicuota = (Double) tabla.getValueAt(fila, RENGLON.ALICUOTA.ordinal());
            Double pesoGuia = (Double) tabla.getValueAt(fila, RENGLON.TOTAL_PESO.ordinal());
            String facPeso = facXpeso((Boolean) tabla.getValueAt(fila, RENGLON.FAC_PESO.ordinal()));
            String facUnidad = facXunidad((Boolean) tabla.getValueAt(fila, RENGLON.FAC_UNIDAD.ordinal()));
            guia02 = new Guia02(itemNo, codigo, tipo, descripcion, peso, nroPedido, cantidad, items, atados, precio, alicuota, pesoGuia, facPeso, facUnidad);
        }   // if ...
        return (guia02);
           */ 
        return (inv08Dat); 
    }   // getItemsGuia().  

    //----------------------------------------------------------------------------
    // Ejecutar la Accion al Presionar el Boton Conforme:  
    //----------------------------------------------------------------------------
    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Ejemplo:  
            //JOptionPane.showMessageDialog(button, "valor de la Tabla( filaX," + RENGLON.valueOf(RENGLON.ID_ROW.toString()) + ")=" + idRow);   // Donde 'idRow' en una var de Ambito General-  
            //JOptionPane.showMessageDialog(button, "valor de la Tabla ( cantidad ) ( filaX," + RENGLON.valueOf(RENGLON.CANTIDAD.toString()) + ")=" + cantidad);
            //PanelGuiaDespacho.chbActualizarGridProductos.setSelected(Boolean.TRUE);
            //
            // ControladorDetalleGuia controladorDetalleGuia = new ControladorDetalleGuia();  
            // controladorDetalleGuia.actualizarRegProductos(itemsGuia02, idRow);   // Donde itemsGuia02 y idRow son Vars de ambito Global.  
        }
        isPushed = false;
        return botonLabel;
    }

    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    //----------------------------------------------------------------------------
    //----------------------------------------------------------------------------
    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
    
}  // BotonActualizarTablaGuia02().

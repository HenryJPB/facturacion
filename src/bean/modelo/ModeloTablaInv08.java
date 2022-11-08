/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo;

import bean.controlador.ControladorInv08;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author henrypb Modelos Tabla Tipo Transporte / Unidad Tipo Transporte <-->
 * Tipo Camion.
 *
 */
public abstract class ModeloTablaInv08 extends AbstractTableModel {

    ControladorInv08.COLUMNA RENGLON;  
    //private enum RENGLON {
    // ITEM_NO, CODIGO, TIPO, DESCRIPCION, PESO_UNI, NO_PEDIDO, CANTIDAD, ATADOS, ITEMS, PRECIO,ALICUOTA,TOTAL_PESO, BOTON_ACTUALIZAR, BOTON_ELIMINAR, ID_ROW }
    //   1        2        3          4            5       6         7      8         9     10      11        12              13           14            15
    //                              1        2        3        4            5           6         7         8          9               10              11         12    13         14           15            16   
    private String[] columnas = {"Item", "Codigo", "Tipo","Descripcion", "Nro.Guia", "Peso", "Cantidad","Precio"," Monto Flete","Cargo x Serv","Peso Guia"," FxU" ,"FxP", "Total Monto","Actualizar", "Accion"}; 
    public List<Object[]> listaDetalleProductos = new ArrayList<Object[]>();   // Fue sustituida por arregloDetalleProductosGuia para incorporar los botones [Actualizar] y [Eliminar].   
    public Object[][] arregloDetalleProductos = {};
    public Object[] longValues = {1234, "123456789012345", "1234","123456789-123456789-123456789-123456789","1234567890",9999999.999, 99999999 , 9999999.999, 999999999.999, 9999999999.99, 9999999999.99, Boolean.FALSE, Boolean.FALSE,  9999999999.99,"12345678901234","123456789012" };  // :definir precision y clase de la columna. (String/Value). 
    //                           (ItemNo),  (Codigo)       (Tipo)          (Descripcion)                     (Guia)        (Peso)   ( Cantidad )   (Precio)   (Monto Flet)   (Cargo x Serv)  (Peso Guia)    (FxP)          (FxU)           ( Total M)    (btnActualizar) (btnEliminar)                                
    //                               1         2             3                  4                              5              6           7            8            9             10              11         12              13               14             15              16                
    @Override
    public int getRowCount() {
        return listaDetalleProductos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Class getColumnClass(int c) {
        //return ( getValueAt(0, c).getClass() );  // fila 0 ????.  << ERROR >>.  Ajustado en la sig. Linea el 03/10/13.  
        return ( longValues[c].getClass() ); 
    } // getColumnClass. 

    @Override
    public boolean isCellEditable(int row, int col) {
        if ( col == RENGLON.CODIGO.ordinal() || col==RENGLON.TIPO.ordinal() || col== RENGLON.NRO_GUIA.ordinal() ||col == RENGLON.PESO_GUIA.ordinal() || col==RENGLON.FAC_PESO.ordinal() || col==RENGLON.FAC_UNIDAD.ordinal() ||col== RENGLON.TOTAL_MONTO.ordinal() ) {
                   return (false);
        } else {
                   return (true);
        }  // if-else. 
    }  // isCellEditable.  

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (listaDetalleProductos.size() > 0) {
            Object[] detalle = listaDetalleProductos.get(rowIndex);
            return (detalle[columnIndex]);
        } else {
            return (null);
        }
    }  // getValueAt. 

    //--------------------------------------------------------------------------
    //  ??: El siguiente metodo NO es neceario. Ver metodo anterior e 
    //      implementalo como getValue( rowIndex, -1 ). Donde -1 = un valor
    //      fuera de rango que hace que se active la ultima isntruccion de este
    //      metodo.  
    //--------------------------------------------------------------------------
    public Object getRecordAt(int rowIndex) {
        return (listaDetalleProductos.get(rowIndex));
    }

    /**
     * Don't need to implement this method unless your table's* data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        // Someter 'value' a algun proceso de validacion antes de alterar el valor en la tabla:  
        if (col == 3) {
            String descripcion = (String) value;  // col de Descripcion.    
            if (descripcion.length() > 0) {
                listaDetalleProductos.get(row)[col] = value;
                fireTableCellUpdated(row, col);   //  forzar Actualizar el nuevo valor sobra la tabla.  
            }
        }
        else {
            listaDetalleProductos.get(row)[col] = value;
            fireTableCellUpdated(row, col);   //  forzar Actualizar el nuevo valor sobra la tabla.  
        }
    }  // setValueAt().

    //---------------------------------------------------------------------------
    // NOTA al 31/07/2013: funcionó con Errores ???.  
    //---------------------------------------------------------------------------
    public void limpiarLista() {
        listaDetalleProductos.clear();
        fireTableDataChanged();       // ???.  
    }

    //--------------------------------------------------------------------------
    // Esto NO esta funcionando,.........(01/08/2013)..............
    //--------------------------------------------------------------------------
    /*
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        //String columnName = model.getColumnName(column);
        //model = 
        //Object data = model.getValueAt(row, column);
        Object[] detalleGuia = listaDetalleProductosGuia.get(row);
        //System.out.println("dentro del TableChanged valor=" +detalleGuia[column]);
        // *****Do something with the data <<Aqui>>..****
    }
    */
    
}   // ModeloTablaDetalleGuia.  
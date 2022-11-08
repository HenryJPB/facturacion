/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

//import static bean.controlador.ControladorDetalleGuia.COLUMNA.CANTIDAD;
import bean.servicio.ServicioAdmonInv07;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.AbstractCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import vistas.PanelPostearFactura;

/**
 *
 * @author henrypb Creado : 10-10-2013.
 *
 */
public class CellEditorNumerico extends AbstractCellEditor implements TableCellEditor {

    private Number oldValor = null;
    private Number nuevoValor = null;
    private int fila;
    private String formatoNumerico;
    private JFormattedTextField celda = new JFormattedTextField();
    private ControladorInv08.COLUMNA columna;  // atributo NO considerada 
    LibreriaHP libHP = new LibreriaHP(); 
    
    //--------------------------------------------------------------------------
    // Constructor de la clase
    // Fecha: 10/10/2013.   
    // NOTA: ejemplo de pase de valor a una clase. 
    //--------------------------------------------------------------------------
    public CellEditorNumerico(ControladorInv08.COLUMNA columna, String fn) {
        this.columna = columna;
        this.formatoNumerico = fn;
        celda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Object getCellEditorValue() {
        //return ( (JFormattedTextField) editor ).getValue() ;  
        //return new Integer(getValue());
        //return Double.parseDouble(editor.getText()); // .getValue(); 
        return ((JFormattedTextField) celda).getValue();
    }  // getCellEditorValue.  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        oldValor = (Number) value;
        fila = row;
        //Si values es nulo dara problemas de renderizado, por lo tanto se pone vacio
        //JLabel celda = new JLabel(value == null ? "" : value.toString());
        //JFormattedTextField celda = new JFormattedTextField(value == null ? "" : value.toString());
        //JFormattedTextField celda = new JFormattedTextField();
        if (value instanceof String) {
            celda.setText(value.toString());
        } else if (value instanceof Integer) {
            celda.setValue(value);
            celda.setHorizontalAlignment(SwingConstants.CENTER);
        } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
            celda.setValue(value);
            celda.setHorizontalAlignment(SwingConstants.RIGHT);
            //celda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###,###,##0.00"))));
            celda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(formatoNumerico))));
        }
        if (isSelected) {
            //celda.setOpaque(true);
            //celda.setBackground(Color.YELLOW);
            //celda.setBackground(Estilos.colorCelda);
        }
        return celda;
    }  // getTableCellEditorComponent.

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void actualizarPeso(BigDecimal peso, Integer cantidad) {
        BigDecimal old_totalPesoColumna = (BigDecimal) PanelPostearFactura.tablaInv08.getValueAt(fila, ControladorInv08.COLUMNA.PESO_GUIA.ordinal());
        BigDecimal totalPeso =  peso.multiply(new BigDecimal(cantidad));
        PanelPostearFactura.tablaInv08.setValueAt(totalPeso, fila, ControladorInv08.COLUMNA.PESO_GUIA.ordinal());
        BigDecimal totalPesoGuia = BigDecimal.ZERO; 
        if ( PanelPostearFactura.lblPesoGuia.getText()!=null && !PanelPostearFactura.lblPesoGuia.getText().isEmpty() && libHP.esNumerico(PanelPostearFactura.lblPesoGuia.getText()) ) {
             totalPesoGuia = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblPesoGuia.getText()));
        }
        totalPesoGuia = totalPesoGuia.subtract(old_totalPesoColumna);
        totalPesoGuia = totalPesoGuia.add(totalPeso);
        PanelPostearFactura.lblPesoGuia.setText(libHP.formatoDecimal.format(totalPesoGuia));
        //PanelGuiaDespacho.txtDisplayPesoNominal.setValue(totalPesoGuia);
    }  // actualizarPeso().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void actualizarMonto(BigDecimal peso, Integer cantidad, BigDecimal precio, BigDecimal montoFlete, BigDecimal otrosCargos ) {
        ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07(); 
        BigDecimal old_totalMontoColumna = (BigDecimal) PanelPostearFactura.tablaInv08.getValueAt(fila, ControladorInv08.COLUMNA.TOTAL_MONTO.ordinal());
        BigDecimal totalMontoFactura = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblBaseImponible.getText())); 
        //totalMontoGuia = totalMontoGuia - old_totalMontoColumna;
        BigDecimal totalMontoColumna = precio;
        Boolean facXpeso = (Boolean) PanelPostearFactura.tablaInv08.getValueAt(fila, ControladorInv08.COLUMNA.FAC_PESO.ordinal());
        if (facXpeso) {
            totalMontoColumna = totalMontoColumna.multiply(peso);
        }
        Boolean facXunidad = (Boolean) PanelPostearFactura.tablaInv08.getValueAt(fila, ControladorInv08.COLUMNA.FAC_UNIDAD.ordinal());
        if (facXunidad) {
            totalMontoColumna = totalMontoColumna.multiply(new BigDecimal(cantidad));
        }
        totalMontoColumna = totalMontoColumna.add(montoFlete); 
        totalMontoColumna = totalMontoColumna.add(otrosCargos); 
        PanelPostearFactura.tablaInv08.setValueAt(totalMontoColumna, fila, ControladorInv08.COLUMNA.TOTAL_MONTO.ordinal());
        // ** SHOW TOTALES DE LA FACTURA **  ( en Stand by *).  
        //String factura = PanelPostearFactura.txtNroFactura.getText(); 
        //String ncf = PanelPostearFactura.txtNroCtrlFiscal.getText(); 
        //Inv07Dat inv07Dat = servAdmonInv07.getInv07(ncf, factura, null); 
        ControladorInv08 ctldrInv08 = new ControladorInv08(); 
        //ctldrInv08.showTotales(inv07Dat);
        //ctldrInv08.actualizarMontoFactura(totalMontoColumna);  // *Aqui esta el piquete*
        ctldrInv08.actualizarMontoFactura();
        // **  
    }  // actualizarMonto(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void validarDatos() {
        nuevoValor = (Number) getCellEditorValue();
        String nuevoValorS = "0"; 
        if (nuevoValor == null) {
            nuevoValor = 0.00;
        } else if (nuevoValor instanceof Integer) {
           nuevoValor = Math.abs(nuevoValor.intValue());  
        } else { 
           nuevoValor = Math.abs(nuevoValor.doubleValue());
        }  // if-else. 
        ((JFormattedTextField) celda).setValue(nuevoValor);  // **
        //nuevoValorS = libHP.convFormatoNumerico(nuevoValor.toString());
        nuevoValorS = nuevoValor.toString();
        ControladorInv08.COLUMNA colPeso = ControladorInv08.COLUMNA.PESO_UNI;
        ControladorInv08.COLUMNA colCantidad = ControladorInv08.COLUMNA.CANTIDAD;
        ControladorInv08.COLUMNA colPrecio = ControladorInv08.COLUMNA.PRECIO;
        ControladorInv08.COLUMNA colFlete = ControladorInv08.COLUMNA.MONTO_FLETE; 
        ControladorInv08.COLUMNA colCargoServ = ControladorInv08.COLUMNA.CARGO_SERV_ASOCIADO;  
        if (columna == colPeso || columna == colCantidad || columna == colPrecio || columna==colFlete || columna == colCargoServ ) {
            // iniciar atributos de las variables:
            BigDecimal peso = new BigDecimal(PanelPostearFactura.tablaInv08.getValueAt(fila, colPeso.ordinal()).toString());
            Integer cantidad = (Integer) PanelPostearFactura.tablaInv08.getValueAt(fila, colCantidad.ordinal());  
            BigDecimal precio = new BigDecimal(PanelPostearFactura.tablaInv08.getValueAt(fila, colPrecio.ordinal()).toString());  
            //
            BigDecimal flete = BigDecimal.ZERO; 
            if ( PanelPostearFactura.tablaInv08.getValueAt(fila, colFlete.ordinal())!=null ) {
                flete = new BigDecimal(PanelPostearFactura.tablaInv08.getValueAt(fila, colFlete.ordinal()).toString()); 
            }
            //
            BigDecimal otroCargo = BigDecimal.ZERO; 
            if (PanelPostearFactura.tablaInv08.getValueAt(fila, colCargoServ.ordinal())!=null ) {
                otroCargo = new BigDecimal(PanelPostearFactura.tablaInv08.getValueAt(fila, colCargoServ.ordinal()).toString());  
            }
            //Integer cantidad = (Integer) PanelPostearFactura.tablaInv08.getValueAt(fila, colCantidad.ordinal());
            //BigDecimal precio = new BigDecimal(PanelPostearFactura.tablaInv08.getValueAt(fila, colPrecio.ordinal()).toString());
            switch (columna) {
                case PESO_UNI: {
                    //String nuevoValorS = nuevoValor.toString();  
                    //peso = new BigDecimal((double) nuevoValor);
                    peso = new BigDecimal(nuevoValorS);
                    break;
                }  // PESO_UNI.
                case CANTIDAD: {
                    //cantidad = Integer.parseInt(nuevoValor.toString());
                    if ( nuevoValorS.indexOf(".")>0 ) {
                         nuevoValorS = nuevoValorS.substring(0,nuevoValorS.indexOf("."));
                    }
                    if ( nuevoValorS.indexOf(",")>0 ) {
                         nuevoValorS = nuevoValorS.substring(0,nuevoValorS.indexOf(","));
                    }
                    cantidad = Integer.parseInt(nuevoValorS);
                    break;
                }  // case CANTIDAD. 
                case PRECIO: {
                    //precio = new BigDecimal((double) nuevoValor);
                    precio = new BigDecimal(nuevoValorS); 
                    break;
                }
                case MONTO_FLETE : {
                    flete = new BigDecimal(nuevoValorS); 
                    break; 
                }
                case CARGO_SERV_ASOCIADO : {
                    otroCargo = new BigDecimal(nuevoValorS); 
                    break; 
                }
            }  // swicth.
            actualizarPeso(peso, cantidad);
            actualizarMonto(peso,cantidad,precio,flete,otroCargo);
        }  // if-else.
    }  // validar().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    protected void fireEditingStopped() {
        validarDatos();
        super.fireEditingStopped();
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public boolean stopCellEditing() {
        //System.out.println("Valor despues de cambiar="+getCellEditorValue());  
        //validarDatos();
        return super.stopCellEditing();
    }
    
} // CellEditorNumerico.  

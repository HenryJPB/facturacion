/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import bean.entidad.Inv07Dat;
import bean.entidad.Inv08Dat;
import bean.entidad.Inv08DatId;
import bean.interfase.IControladorInv08;
import bean.modelo.ModeloTablaInv08;
import bean.servicio.ServicioAdmonInv07;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import vistas.PanelPostearFactura;
import static vistas.PanelPostearFactura.lblBaseImponible;
import static vistas.PanelPostearFactura.lblPesoGuia;
import static vistas.PanelPostearFactura.lblSubTotal;
import static vistas.PanelPostearFactura.lblTotalFactura;
import static vistas.PanelPostearFactura.txtDepGarantia;
import static vistas.PanelPostearFactura.txtDescuento;
import static vistas.PanelPostearFactura.txtIva;
import static vistas.PanelPostearFactura.txtMontoAjuste;
import static vistas.PanelPostearFactura.txtMontoFlete;
import static vistas.PanelPostearFactura.txtReconociFlete;

/**
 *
 * @author henrypb
 */
public class ControladorInv08 implements IControladorInv08 {

    public enum COLUMNA {

        ITEM_NO, CODIGO, TIPO, DESCRIPCION, NRO_GUIA, PESO_UNI, CANTIDAD, PRECIO, MONTO_FLETE, CARGO_SERV_ASOCIADO, PESO_GUIA, FAC_UNIDAD, FAC_PESO, TOTAL_MONTO, BOTON_ACTUALIZAR, BOTON_ELIMINAR, ID_ROW
    }   //  0      1       2      3            4          5          6        7         8                 9                10        11        12          13               14             15             16                 

    private enum RENGLON {

        CODIGO, TIPO_PROD, DESCRIPCION
    };  // Colunnas de la LOV.  

    ModeloTablaInv08 modeloTablaInv08;
    LibreriaHP libHP = new LibreriaHP();

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void iniciarTablaProductos(Inv07Dat inv07Dat) {
        //configurarTablaProductos();
        iniciarTabla(inv07Dat);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarTabla(Inv07Dat inv07Dat) {
        JTableHeader header = PanelPostearFactura.tablaInv08.getTableHeader();
        header.setBackground(Color.YELLOW);
        header.setForeground(Color.DARK_GRAY);
        header.setFont(new Font("Monospaced", Font.BOLD, 13));
        modeloTablaInv08 = new ModeloTablaInv08() {
        };
        modeloTablaInv08.listaDetalleProductos.clear();  // Asegurar q la tabla este vacia. Revisar metodo refresh el Grid. ???.   
        //cargarDatosTabla(modeloTablaInv08.listaDetalleProductos, inv07Dat);       // <: utilizando un Lista. 
        //modeloTablaInv08.listaDetalleProductos = cargarDatos(inv07Dat);       // <: utilizando un Lista. 
        modeloTablaInv08.listaDetalleProductos = cargarDatosV2(inv07Dat);       // <: utilizando un Lista. 
        //cargarDatosTablaGuia02(modeloTablaGuia02.arregloDetalleProductosGuia);   // <: utilizando un Arreglo bidimensional.  
        PanelPostearFactura.tablaInv08.setModel(modeloTablaInv08);
        ordenarTabla();   // <-- Rutina SUJETA A REVISION  (Brqto: 02/08/2014).   
        //
        //SortedListModel model = new SortedListModel();
        // fill model
        //JList list = new JList(model)
        //
        //Collections.sort(modeloTablaInv08.listaDetalleProductos);  // Error: No hay un metodo to sort a List(<Object[]>)
        configurarCeldasTabla();
        PanelPostearFactura.tablaInv08.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnSizes(PanelPostearFactura.tablaInv08, modeloTablaInv08);
        ocultarColumna(COLUMNA.ID_ROW.ordinal());
    }  // iniciarTablaGuia02

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean facXunidad(String facUnidad) {
        if (facUnidad == null || facUnidad.isEmpty()) {
            return (Boolean.FALSE);
        } else {
            return (Boolean.TRUE);
        }
    }  // facXunidad()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean facXpeso(String facPeso) {
        if (facPeso == null || facPeso.isEmpty()) {
            return (Boolean.FALSE);
        } else {
            return (Boolean.TRUE);
        }
    }  // facXpeso()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public String facXunidad(Boolean ok) {
        if (ok) {
            return ("X");
        } else {
            return ("");
        } // if-else-  
    }  // facXpeso().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public String facXpeso(Boolean ok) {
        if (ok) {
            return ("X");
        } else {
            return ("");
        } // if-else-  
    }  // facXpeso(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void configurarCeldasTabla() {
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.CODIGO.ordinal()).setCellRenderer(new RenderCelda());
        //
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.DESCRIPCION.ordinal()).setCellEditor(new CellEditorAlfaNumerico(COLUMNA.DESCRIPCION));
        //PanelGuiaDespacho.tablaDetalleGuia.getColumnModel().getColumn(COLUMNA.NO_PEDIDO.ordinal()).setCellEditor(new CellEditorAlfaNumerico(COLUMNA.NO_PEDIDO));
        // *
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.PESO_UNI.ordinal()).setCellRenderer(new RenderCeldaNumerica("###,###,##0.000"));
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.PESO_UNI.ordinal()).setCellEditor(new CellEditorNumerico(COLUMNA.PESO_UNI, "###,###,##0.000"));
        // configurar .addListener: actionPerformed para desplazar la columna Peso/Uni. 
        //
        //PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.TOTAL_MONTO.ordinal()).setCellRenderer(new RenderCeldaNumerica("###,###,###,##0.000"));
        PanelPostearFactura.tablaInv08.getColumn("Cantidad").setCellRenderer(new RenderCeldaNumerica("###,###,##0"));
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.CANTIDAD.ordinal()).setCellEditor(new CellEditorNumerico(COLUMNA.CANTIDAD, "###,###,##0"));
        // configurar .addListener. actionPerformed para desplazar la colunna cantidad.
        PanelPostearFactura.tablaInv08.getColumn("Precio").setCellRenderer(new RenderCeldaNumerica("###,###,##0.00"));
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.PRECIO.ordinal()).setCellEditor(new CellEditorNumerico(COLUMNA.PRECIO, "###,###,##0.00"));
        //
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.MONTO_FLETE.ordinal()).setCellRenderer(new RenderCeldaNumerica("###,###,##0.00"));
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.MONTO_FLETE.ordinal()).setCellEditor(new CellEditorNumerico(COLUMNA.MONTO_FLETE, "###,###,##0.00"));
        //
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.CARGO_SERV_ASOCIADO.ordinal()).setCellRenderer(new RenderCeldaNumerica("###,###,##0.00"));
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.CARGO_SERV_ASOCIADO.ordinal()).setCellEditor(new CellEditorNumerico(COLUMNA.CARGO_SERV_ASOCIADO, "###,###,##0.00"));
        //PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.TOTAL_MONTO.ordinal()).setCellEditor( new CellEditorNumerico(COLUMNA.TOTAL_MONTO,"###,###,##0.00"));
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.PESO_GUIA.ordinal()).setCellRenderer(new RenderCeldaNumerica("###,###,##0.00"));
        //
        PanelPostearFactura.tablaInv08.getColumnModel().getColumn(COLUMNA.TOTAL_MONTO.ordinal()).setCellRenderer(new RenderCeldaNumerica("###,###,##0.00"));
        // Configurar Boton Actualizar.  
        PanelPostearFactura.tablaInv08.getColumn("Actualizar").setCellRenderer(new BotonRenderer());
        PanelPostearFactura.tablaInv08.getColumn("Actualizar").setCellEditor(new BotonActualizarTabla(new JCheckBox()));
        // Remind: el identificador "Actualizar" es nombre del header q identifica la columna. 
        PanelPostearFactura.tablaInv08.getColumn("Accion").setCellRenderer(new BotonRenderer());
        //
        String nroFactura = PanelPostearFactura.txtNroFactura.getText();
        PanelPostearFactura.tablaInv08.getColumn("Accion").setCellEditor(new BotonEliminarTabla(new JCheckBox()));
    }  // configurarCeldasTabla(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ocultarColumna(int indiceColumna) {   // segun Internet link: http://elfragmx.wordpress.com/2011/03/12/ocultar-columna-de-jtable-en-java/
        /*
         PanelGuiaDespacho.tablaDetalleGuia.getColumnModel().getColumn(indiceColumna).setMaxWidth(0);
         PanelGuiaDespacho.tablaDetalleGuia.getColumnModel().getColumn(indiceColumna).setMinWidth(0);
         PanelGuiaDespacho.tablaDetalleGuia.getColumnModel().getColumn(indiceColumna).setPreferredWidth(0);
         */
    }  // ocultarColumna().   // funciono correctamente el 23-07-2013.  

    //-------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void setColumnSizes(JTable tabla, ModeloTablaInv08 modeloTabla) {
        ModeloTablaInv08 model = (ModeloTablaInv08) tabla.getModel();
        TableColumn column;
        Component comp;
        int headerWidth;
        int cellWidth;
        Object[] longValues = model.longValues;    // <! de esta menera accedes a los miembros de la clase ModeloTablaGuia02. 
        TableCellRenderer headerRenderer = tabla.getTableHeader().getDefaultRenderer();
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {   // (**)  
            column = tabla.getColumnModel().getColumn(i);
            comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
            comp = tabla.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(tabla, longValues[i], false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
        // ** Valores constantes para definir el ancho de algunas colunnas:  *** 
        //tabla.getColumn("Atados").setPreferredWidth(60);   //  <<  where n=valor en px.  
        //tabla.getColumnModel().getColumn(COLUMNA.ALICUOTA.ordinal()).setPreferredWidth(100);   // valor expresado en pixels. 
        tabla.getColumnModel().getColumn(COLUMNA.FAC_PESO.ordinal()).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(COLUMNA.FAC_UNIDAD.ordinal()).setPreferredWidth(50);
    } //  setColumnSizes().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Object[]> cargarDatos(Inv07Dat inv07Dat) {
        List<Object[]> lista = new ArrayList<Object[]>();
        Object[] fila;
        Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
        for (Inv08Dat inv08Dat : inv08Dats) {
            //System.out.println("Cod Prod="+inv08Dat.getId().getC8Codigo()+"***Descripcion="+inv08Dat.getC8Descripcion()+"****"); 
            fila = new Object[]{
                inv08Dat.getId().getC8ItemNo(),
                inv08Dat.getId().getC8Codigo(),
                inv08Dat.getC8Tipo(),
                inv08Dat.getC8Descripcion(),
                inv08Dat.getC8Guia(),
                inv08Dat.getC8Peso(),
                inv08Dat.getC8Unidades(),
                inv08Dat.getC8Precio(),
                inv08Dat.getC8Flete(), // Monto Flete. 
                inv08Dat.getC8Ajuste(), // Cargo x Servicios. 
                inv08Dat.getC8PesoGuia(),
                facXunidad(inv08Dat.getC8Fxunidad()),
                facXpeso(inv08Dat.getC8Fxpeso()),
                calcularMonto(inv08Dat), // TOTAL MONTO del RENGLON. 
                "Conforme",
                "Eliminar"
            }; // fila. 
            lista.add(fila);
        }  // for.  
        return (lista);
    }  //  cargarDatosTabla().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Object[]> cargarDatosV2(Inv07Dat inv07Dat) {
        List<Object[]> lista = new ArrayList<Object[]>();
        Object[] fila;
        Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
        for (Inv08Dat inv08Dat : inv08Dats) {
            //System.out.println("Cod Prod="+inv08Dat.getId().getC8Codigo()+"***Descripcion="+inv08Dat.getC8Descripcion()+"****"); 
            fila = getFila(inv08Dat);
            lista.add(fila);
        }  // for.  
        //Collections.sort(lista);   ????
        return (lista);
    }  //  cargarDatosTabla().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public Object[] getFila(Inv08Dat inv08Dat) {
        Object[] fila;
        fila = new Object[]{
            inv08Dat.getId().getC8ItemNo(),
            inv08Dat.getId().getC8Codigo(),
            inv08Dat.getC8Tipo(),
            inv08Dat.getC8Descripcion(),
            inv08Dat.getC8Guia(),
            inv08Dat.getC8Peso(),
            inv08Dat.getC8Unidades(),
            inv08Dat.getC8Precio(),
            inv08Dat.getC8Flete(), // Monto Flete. 
            inv08Dat.getC8Ajuste(), // Cargo x Servicios. 
            inv08Dat.getC8PesoGuia(),
            facXunidad(inv08Dat.getC8Fxunidad()),
            facXpeso(inv08Dat.getC8Fxpeso()),
            calcularMonto(inv08Dat), // TOTAL MONTO del RENGLON. 
            "Conforme",
            "Eliminar"
        }; // fila. 
        //lista.add(fila); 
        return (fila);
    }  //  nuevaFila().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public BigDecimal calcularMonto(Inv08Dat inv08Dat) {
        BigDecimal precio = BigDecimal.ZERO; 
        if ( inv08Dat.getC8Precio()!=null ) {
             precio = inv08Dat.getC8Precio();  
        }
        BigDecimal monto =  precio; 
        if (facXpeso(inv08Dat.getC8Fxpeso()) && inv08Dat.getC8Peso()!=null ) {
            monto = monto.multiply(inv08Dat.getC8Peso());
        }
        if (facXunidad(inv08Dat.getC8Fxunidad()) && inv08Dat.getC8Unidades()!=null ) {
            monto = monto.multiply(new BigDecimal(inv08Dat.getC8Unidades()));
        }
        if (inv08Dat.getC8Flete() != null) {
            monto = monto.add(inv08Dat.getC8Flete());
        }
        if (inv08Dat.getC8Ajuste() != null) {
            monto = monto.add(inv08Dat.getC8Ajuste());
        }
        return (monto);
    }   // calcularMonto().

    //--------------------------------------------------------------------------
    // SUJETO A REVISION (Brqto: 10/10/2013).>> Produce un efecto indeseable 
    //--------------------------------------------------------------------------
    private void ordenarTabla() {
        ModeloTablaInv08 modeloTablaOrdenada = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        RowSorter<TableModel> tablaOrdenada = new TableRowSorter<TableModel>(modeloTablaOrdenada);
        PanelPostearFactura.tablaInv08.setRowSorter(tablaOrdenada);
    }

    //--------------------------------------------------------------------------
    // Show totales desde los registros de la B.D. 
    //--------------------------------------------------------------------------
    public void showTotales(Inv07Dat inv07Dat) {
        final BigDecimal alicuota = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblFactorIVA.getText()));
        LibreriaHP libHP = new LibreriaHP();
        ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
        //BigDecimal pesoGuia = servAdmonInv07.sumPesoGuia(inv07Dat.getC7Factura()); 
        Inv07Dat totales = servAdmonInv07.getTotales(inv07Dat.getC7Factura());
        lblPesoGuia.setText(libHP.formatoDecimal.format(totales.getSumPesoGuia()));
        BigDecimal montoFlete = totales.getSumFlete();
        txtMontoFlete.setText(libHP.formatoDecimal.format(montoFlete));      // Sumatoria de m.Fletes del grid. 
        BigDecimal montoAjuste = totales.getSumAjuste();
        txtMontoAjuste.setText(libHP.formatoDecimal.format(montoAjuste));    // Sumatoria de m.Ajustes del grid.  
        // * Monto Bruto de la Factura *  
        BigDecimal montoFactura = totales.getMontoFactura();
        PanelPostearFactura.lblMontoFactura.setText(libHP.formatoDecimal.format(montoFactura));
        // *****************************
        BigDecimal flete = BigDecimal.ZERO;  
        if (inv07Dat.getC7MontoFlete() != null) {
            flete = inv07Dat.getC7MontoFlete();
        } 
        PanelPostearFactura.txtFlete.setText(libHP.formatoDecimal.format(flete));
        montoFactura = montoFactura.add(flete);  
        //
        BigDecimal reconociFlete = BigDecimal.ZERO;
        if (inv07Dat.getC7ReconociFlete() != null) {
            reconociFlete = inv07Dat.getC7ReconociFlete();
        } 
        txtReconociFlete.setText(libHP.formatoDecimal.format(reconociFlete));
        montoFactura = montoFactura.subtract(reconociFlete);  
        //
        BigDecimal descuento = BigDecimal.ZERO;
        if (inv07Dat.getC7Descuento() != null) {
            descuento = inv07Dat.getC7Descuento();
        } 
        txtDescuento.setText(libHP.formatoDecimal.format(descuento));
        montoFactura = montoFactura.subtract(descuento); 
        //
        lblBaseImponible.setText(libHP.formatoDecimal.format(montoFactura));
        // * recalcular el Iva aqui. *
        /*
        montoFactura = montoFactura.subtract(montoFlete);
        montoFactura = montoFactura.subtract(montoAjuste);
        BigDecimal iva = montoFactura.multiply(alicuota.divide(new BigDecimal(100.00)));
        */  
        BigDecimal iva = BigDecimal.ZERO; 
        if (inv07Dat.getC7Ivm() != null) {
            iva = inv07Dat.getC7Ivm(); 
        }
        txtIva.setText(libHP.formatoDecimal.format(iva));
        // **
        BigDecimal subTotal = montoFactura.add(iva);
        //BigDecimal subTotal = totales.getSubTotal();  
        lblSubTotal.setText(libHP.formatoDecimal.format(subTotal));
        BigDecimal depGarantia = totales.getDepGarantia();
        txtDepGarantia.setText(libHP.formatoDecimal.format(depGarantia));
        //BigDecimal total = totales.getTotalFactura();  
        BigDecimal total = subTotal.subtract(depGarantia);  
        lblTotalFactura.setText(libHP.formatoDecimal.format(total));
    }  // showDatosProducto(). 

    //--------------------------------------------------------------------------
    // actualizar totales de la factura desde la Variables de Memoria <-> el
    // valor de los campos de la  pantalla de captacion de datos.  
    //--------------------------------------------------------------------------
    public void actualizarMontoFactura() {
        BigDecimal factorIva = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblFactorIVA.getText()));
        factorIva = factorIva.divide(new BigDecimal(100.00));
        // ** ?Es esto correcto???:   
        //BigDecimal montoFactura = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblMontoFactura.getText()));  // *Aqui esta el piquete*
        Inv07Dat inv07Aux = new Inv07Dat(); 
        inv07Aux = getSumGrid();          
        //BigDecimal montoFactura = getMontoGrid();
        BigDecimal montoFactura = inv07Aux.getMontoFactura(); 
        BigDecimal sumPesoGuia = inv07Aux.getSumPesoGuia();  
        // **
        PanelPostearFactura.lblPesoGuia.setText(libHP.formatoDecimal.format(sumPesoGuia));
        PanelPostearFactura.lblMontoFactura.setText(libHP.formatoDecimal.format(montoFactura));
        //
        BigDecimal flete = BigDecimal.ZERO;  
        if (PanelPostearFactura.txtFlete.getText() != null && !PanelPostearFactura.txtFlete.getText().isEmpty() && PanelPostearFactura.txtFlete.getText().trim().length()>0 ) {
            flete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtFlete.getText()));
        }
        montoFactura = montoFactura.add(flete);  
        //
        BigDecimal descuento = BigDecimal.ZERO;
        if (PanelPostearFactura.txtDescuento.getText() != null && !PanelPostearFactura.txtDescuento.getText().isEmpty() && PanelPostearFactura.txtDescuento.getText().trim().length()>0 ) {
            descuento = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDescuento.getText()));
        }
        montoFactura = montoFactura.subtract(descuento);
        //
        BigDecimal reconociFlete = BigDecimal.ZERO;
        if (PanelPostearFactura.txtReconociFlete.getText() != null && !PanelPostearFactura.txtReconociFlete.getText().isEmpty() && PanelPostearFactura.txtReconociFlete.getText().trim().length()>0 ) {
            reconociFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtReconociFlete.getText()));
        }
        montoFactura = montoFactura.subtract(reconociFlete);
        //
        PanelPostearFactura.lblBaseImponible.setText(libHP.formatoDecimal.format(montoFactura));
        //
        BigDecimal iva = BigDecimal.ZERO; 
        if ( PanelPostearFactura.chbContribuyente.isSelected() ) {
            iva = montoFactura.multiply(factorIva);
        }
        PanelPostearFactura.txtIva.setText(libHP.formatoDecimal.format(iva));
        montoFactura = montoFactura.add(iva);
        PanelPostearFactura.lblSubTotal.setText(libHP.formatoDecimal.format(montoFactura));
        BigDecimal anticipo = BigDecimal.ZERO;
        if (PanelPostearFactura.txtDepGarantia.getText() != null && !PanelPostearFactura.txtDepGarantia.getText().isEmpty() && libHP.esNumerico(PanelPostearFactura.txtDepGarantia.getText()) ) {
            anticipo = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDepGarantia.getText()));
        }
        montoFactura = montoFactura.subtract(anticipo);
        PanelPostearFactura.lblTotalFactura.setText(libHP.formatoDecimal.format(montoFactura));
    }  // actuallizarMontoFactura(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private BigDecimal getMontoGrid() {
        ModeloTablaInv08 modelo = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        List<Object[]> lista = new ArrayList<Object[]>();
        BigDecimal monto = BigDecimal.ZERO;
        for (Object[] registro : modelo.listaDetalleProductos) {
            monto = monto.add((BigDecimal) registro[COLUMNA.TOTAL_MONTO.ordinal()]);
        }  // for.  
        return (monto);
    }  // getMontoGrid(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat getSumGrid() {
        Inv07Dat inv07Aux = new Inv07Dat(); 
        ModeloTablaInv08 modelo = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        List<Object[]> lista = new ArrayList<Object[]>();
        BigDecimal sumPesoGuia = BigDecimal.ZERO; 
        BigDecimal sumMonto = BigDecimal.ZERO;
        for (Object[] registro : modelo.listaDetalleProductos) {
            if ( registro[COLUMNA.PESO_GUIA.ordinal()]!=null ) {
                sumPesoGuia = sumPesoGuia.add( (BigDecimal) registro[COLUMNA.PESO_GUIA.ordinal()]);  
            }
            if ( registro[COLUMNA.TOTAL_MONTO.ordinal()]!=null ) {
                sumMonto = sumMonto.add( (BigDecimal) registro[COLUMNA.TOTAL_MONTO.ordinal()]);
            }
        }  // for.  
        inv07Aux.setSumPesoGuia(sumPesoGuia);
        inv07Aux.setMontoFactura(sumMonto);
        return (inv07Aux);
    }  // getSumGrid().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void eliminarRegListaProductos(Byte nroItem, String codigo) {
        ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
        ModeloTablaInv08 modeloTabla = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        ArrayList<Object[]> listaProductos;
        listaProductos = (ArrayList<Object[]>) modeloTabla.listaDetalleProductos;
        if (listaProductos.size() > 0) {
            // Probar...
            // NOTA (30/07/2013): Existen 2 metodos para eliminar un elemento de un ArrayList:
            // (1). Por su indice o posicion dentro del Arreglo. 
            // (2). Segun el objeto como argumento; pero de igual forma se requieres saber cual Objeto es; por lo tanto hay que realizar un busqueda en el ArrayList.  ?.  
            // Procedere por el 2do Metodo:
            Inv08Dat inv08Dat = null;
            Object[] fila = null;
            boolean encontrado = false;
            int i = -1;
            while (!encontrado && i < listaProductos.size()) {    // <! realizar busqueda. 
                i++;
                fila = listaProductos.get(i);
                if (fila[COLUMNA.ITEM_NO.ordinal()] == nroItem && fila[COLUMNA.CODIGO.ordinal()].equals(codigo)) {
                    encontrado = Boolean.TRUE;
                    inv08Dat = getInv08(fila);
                    servAdmonInv07.eliminar(inv08Dat);
                    modeloTabla.listaDetalleProductos.remove(fila);
                    //modeloTabla.fireTableRowsDeleted(i, i);  
                    modeloTabla.fireTableDataChanged();
                    actualizarTotales("-", inv08Dat);
                }
            }  // while-  
        }  // if listaProductos posee + de 1 registro.  
    }  // eliminarRegListProductos().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv08Dat getInv08(Object[] fila) {
        Inv08Dat inv08Dat = null;
        // (*)
        String factura = PanelPostearFactura.txtNroFactura.getText();
        Byte noItem = (Byte) fila[0];
        String codigo = (String) fila[1];
        String tipoProd = (String) fila[2];
        String descripcion = (String) fila[3];
        String guia = (String) fila[4];
        String pesoS = (fila[5] == null ? "0.00" : fila[5].toString());
        BigDecimal peso = new BigDecimal(pesoS);
        Integer cantidad = (Integer) fila[6];
        String precioS = (fila[7] == null ? "0.00" : fila[7].toString());
        BigDecimal precio = new BigDecimal(precioS);
        String fleteS = (fila[8] == null ? "0.00" : fila[8].toString());
        BigDecimal flete = new BigDecimal(fleteS);
        String ajusteS = (fila[9] == null ? "0.00" : fila[9].toString());
        BigDecimal ajuste = new BigDecimal(ajusteS);
        String pesoGuiaS = (fila[10] == null ? "0.00" : fila[10].toString());
        BigDecimal pesoGuia = new BigDecimal(pesoGuiaS);
        Boolean b = (Boolean) fila[11];
        String facXunidad = facXunidad(b);
        b = (Boolean) fila[12];
        String facXpeso = facXpeso(b);
        //BigDecimal monto = precio;
        //monto = monto.multiply(new BigDecimal(cantidad));
        // (*)
        inv08Dat = new Inv08Dat(new Inv08DatId(factura, codigo, noItem), new Inv07Dat(factura), tipoProd, descripcion, guia, peso, cantidad, precio, flete, facXunidad, facXpeso, pesoGuia, ajuste);
        return (inv08Dat);
    }  // getInv08(); 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void actualizarTotales(String tipoOper, Inv08Dat inv08Dat) {
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        BigDecimal pesoGuia = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblPesoGuia.getText()));
        BigDecimal factorIVA = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblFactorIVA.getText()));
        factorIVA = factorIVA.divide(new BigDecimal(100.00));
        BigDecimal montoFactura = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblBaseImponible.getText()));
        BigDecimal montoFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtMontoFlete.getText()));
        BigDecimal otrosCargos = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtMontoAjuste.getText()));
        //BigDecimal baseImp = montoFactura; 
        BigDecimal baseImp = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        BigDecimal anticipo = BigDecimal.ZERO;
        BigDecimal reconociFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtReconociFlete.getText()));
        BigDecimal descuento = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDescuento.getText()));
        switch (tipoOper) {
            case "+": {
                pesoGuia = pesoGuia.add(inv08Dat.getC8PesoGuia()); 
                otrosCargos = otrosCargos.add(inv08Dat.getC8Ajuste());
                montoFlete = montoFlete.add(inv08Dat.getC8Flete());
                montoFactura = montoFactura.add(ctldrInv08.calcularMonto(inv08Dat));
                break;
            }  // case "+".
            case "-": {
                pesoGuia = pesoGuia.subtract(inv08Dat.getC8PesoGuia()); 
                otrosCargos = otrosCargos.subtract(inv08Dat.getC8Ajuste());
                montoFlete = montoFlete.subtract(inv08Dat.getC8Flete());
                montoFactura = montoFactura.subtract(ctldrInv08.calcularMonto(inv08Dat));
                break;
            }
        }  // switch 
        PanelPostearFactura.lblPesoGuia.setText(libHP.formatoDecimal.format(pesoGuia));
        PanelPostearFactura.txtMontoFlete.setText(libHP.formatoDecimal.format(montoFlete));
        PanelPostearFactura.txtMontoAjuste.setText(libHP.formatoDecimal.format(otrosCargos));
        PanelPostearFactura.lblMontoFactura.setText(libHP.formatoDecimal.format(montoFactura));
        montoFactura = montoFactura.subtract(descuento);
        montoFactura = montoFactura.subtract(reconociFlete);
        baseImp = montoFactura;
        PanelPostearFactura.lblBaseImponible.setText(libHP.formatoDecimal.format(baseImp));
        if ( PanelPostearFactura.chbContribuyente.isSelected() ) {
           iva = baseImp.multiply(factorIVA);
        }
        PanelPostearFactura.txtIva.setText(libHP.formatoDecimal.format(iva));
        montoFactura = montoFactura.add(iva);
        PanelPostearFactura.lblSubTotal.setText(libHP.formatoDecimal.format(montoFactura));
        if (PanelPostearFactura.txtDepGarantia.getText() != null && !PanelPostearFactura.txtDepGarantia.getText().isEmpty()) {
            anticipo = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDepGarantia.getText()));
        }
        montoFactura = montoFactura.subtract(anticipo);
        PanelPostearFactura.lblTotalFactura.setText(libHP.formatoDecimal.format(montoFactura));
    }  // actualizarTotales(). 

}  // ControladorInv08().  

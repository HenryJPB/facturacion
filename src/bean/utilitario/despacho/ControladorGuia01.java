/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.despacho;

import bean.controlador.ControladorInv08;
import bean.controlador.ControladorPostearFactura;
import bean.entidad.Inv07Dat;
import bean.entidad.Inv08Dat;
import bean.entidad.Inv08DatId;
import bean.servicio.ServicioAdmonGuia01;
import bean.entidad.Guias01Dat;
import entidad.Guias02Dat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Set;
import javax.swing.JDialog;
import vistas.PanelPostearFactura;

/**
 *
 * @author henrypb
 */
public class ControladorGuia01 implements IControladorGuia01 {

    final Integer sizeX = 525, // valores constantes.
                  sizeY = 380;
    final Integer posX = 550,
                  posY = 50;

    private enum Columna { NRO_GUIA, FECHA_GUIA, CLIENTE }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void ejecutarDialogGuiasFacturar() {
        final JDialog dialogFrame = new JDialog(); 
        final PanelLovGuiasFacturar panelLovGuiasFacturar = new PanelLovGuiasFacturar(); 
        panelLovGuiasFacturar.btnConforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = panelLovGuiasFacturar.tablaGuiasFacturar.getSelectedRow(); 
                if ( fila>=0 ) {
                    String nroGuia = (String) panelLovGuiasFacturar.tablaGuiasFacturar.getValueAt(fila, Columna.NRO_GUIA.ordinal()); 
                    //System.out.println("*****Nro Guia selected="+nroGuia+"****");
                    //ServicioAdmonGuia01 servAdmonGuia01 = new ServicioAdmonGuia01(); 
                    //Guias01Dat guia01 = servAdmonGuia01.getGuiaDespacho(nroGuia); 
                    //ControladorPostearFactura ctldrPostearFact = new ControladorPostearFactura(); 
                    //ctldrPostearFact.setDatosInv07(guia01);
                    ServicioAdmonGuia01 servAdmonGuia01 = new ServicioAdmonGuia01(); 
                    Guias01Dat guia01 = servAdmonGuia01.getGuiaDespacho(nroGuia); 
                    //inv07Dat = (Inv07Dat) listaFacturas.get(0);
                    //Inv07Dat inv07 = new Inv07Dat();  
                    Inv07Dat inv07 = getDatosGuia( guia01 ); // get los datos de la guia y cargalos a la var inv07 para ser mostrados.  
                    ControladorPostearFactura ctldrPostearFactura = new ControladorPostearFactura(); 
                    ctldrPostearFactura.showDatosMaestro(inv07);
                    ctldrPostearFactura.showDatosDetalleProducto(inv07);
                    ControladorInv08 ctldrInv08 = new ControladorInv08(); 
                    //ctldrInv08.showTotales(inv07);  // *NOTA*: Esta instruccion <NO APLICA> se debe a la información registrada en la B.D.
                                                      //  por lo tanto, recuerda, q en este punto aun el usario no ha registrado la informacion. 
                    ctldrInv08.actualizarMontoFactura();
                }  // if.  
                dialogFrame.dispose();
            }  // actionPerfomed().  
        } );
        panelLovGuiasFacturar.btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFrame.dispose();
            }
        } );
        dialogFrame.setContentPane(panelLovGuiasFacturar);
        dialogFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogFrame.setModal(true);
        dialogFrame.setLocation(posX, posY);
        dialogFrame.setSize(sizeX, sizeY);
        dialogFrame.setVisible(true); 
    }  // ejecutarDialogFrame(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------.
    @Override
    public Inv07Dat getDatosGuia(Guias01Dat guia01) {
        Inv07Dat resultanteInv07 = new Inv07Dat();
        getDatosCliente(resultanteInv07,guia01);
        getDatosVenta(resultanteInv07,guia01);
        getDatosProducto(resultanteInv07,guia01);
        return (resultanteInv07);
    }  // getDatosGuia(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void getDatosCliente(Inv07Dat inv07,Guias01Dat guia01) {
        // ** set Nro Factura
        inv07.setC7Fecha(guia01.getC1FechaGuia());
        // ** set Ncf. 
        if (guia01.getC1ClienteEspecial() != null && !guia01.getC1ClienteEspecial().isEmpty()) {
            inv07.setC7ClienteEspecial("X");
        }
        inv07.setC7CodigoCliente(guia01.getC1CodigoCliente());
        if (guia01.getC1RetiradoPlanta() != null && !guia01.getC1RetiradoPlanta().isEmpty()) {
            inv07.setC7RetiradoPlanta("X");
        }
        inv07.setC7Orden1(guia01.getC1Orden1());
        String dirEntrega = (guia01.getC1Entrega1() != null ? guia01.getC1Entrega1() : "");
        inv07.setC7Entrega1(dirEntrega);
        if (guia01.getC1Entrega2() != null && !guia01.getC1Entrega2().isEmpty()) {
            inv07.setC7Entrega2(guia01.getC1Entrega2());
        }
        if (guia01.getC1Entrega3() != null && !guia01.getC1Entrega3().isEmpty()) {
            inv07.setC7Entrega3(guia01.getC1Entrega3());
        }
    }  // getDatosCliente(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void getDatosVenta(Inv07Dat inv07,Guias01Dat guia01) {
        if ( guia01.getC1Orden1()!=null && !guia01.getC1Orden1().isEmpty() ) {
            inv07.setC7Orden1(guia01.getC1Orden1());
        }
        if ( guia01.getC1Orden2()!=null && !guia01.getC1Orden2().isEmpty() ) {
           //listModel.addElement();
           inv07.setC7Orden2(guia01.getC1Orden2());
        }
        inv07.setC7Guia1(guia01.getC1Guia());
        inv07.setC7FechaGuia(guia01.getC1FechaGuia());
        inv07.setC7Pedido1(guia01.getC1Pedido1());
        //DefaultComboBoxModel cbbModel1 = new DefaultComboBoxModel();
        //cbbModel1.removeAllElements();
        //cbbModel1.addElement(guia01.getC1Vendedor());
        inv07.setC7Vendedor(guia01.getC1Vendedor());
        //DefaultComboBoxModel cbbModel2 = new DefaultComboBoxModel();
        //cbbModel2.removeAllElements();
        //cbbModel2.addElement(guia01.getC1Zona());
        inv07.setC7Zona(guia01.getC1Zona());
        //DefaultComboBoxModel cbbModel3 = new DefaultComboBoxModel();
        //cbbModel3.removeAllElements();
        if ( guia01.getC1FormaPago()!=null && !guia01.getC1FormaPago().isEmpty() ) {
            //cbbModel3.addElement(guia01.getC1FormaPago().equals("C")?"CONTADO":"CREDITO");
            //inv07.setC7CondicionesPago(guia01.getC1FormaPago().equals("C")?"CONTADO":"CREDITO");
            //cbbModel3.addElement(guia01.getC1FormaPago().equals("C")?"CONTADO":"CREDITO");            
            inv07.setC7CondicionesPago(guia01.getC1FormaPago());
        }
    }  // getDatosVenta().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void getDatosProducto(Inv07Dat inv07,Guias01Dat guia01) {
        //private List<Object[]> cargarDatos(Inv07Dat inv07Dat) {
        //List<Object[]> lista = new ArrayList<Object[]>();
        //Object[] fila;
        inv07.setC7Ivm(guia01.getC1Alicuota()); 
        ControladorInv08 ctldrInv08 = new ControladorInv08(); 
        Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07.getInv08Dats();
        //inv08Dats.clear();  // ¿?.  
        Inv08Dat inv08Dat = null;
        Set<Guias02Dat> guias02Dats = (Set<Guias02Dat>) guia01.getGuias02Dats(); 
        for (Guias02Dat guia02 : guias02Dats) {
            String factura = PanelPostearFactura.txtNroFactura.getText();
            Byte noItem = (Byte) guia02.getId().getC2ItemNo();
            String codigo = guia02.getId().getC2Codigo();
            String tipoProd = guia02.getC2Tipo();
            String descripcion = guia02.getC2Descripcion();
            String guia = guia02.getId().getC2Guia();
            //String pesoS = (fila[5] == null ? "0.00" : fila[5].toString());
            //BigDecimal peso = new BigDecimal(pesoS);
            BigDecimal peso = guia02.getC2Peso(); 
            Integer cantidad = 0;
            if ( guia02.getC2Items()!=null ) {
                cantidad = new Integer(guia02.getC2Items());
            }
            //BigDecimal precio = (BigDecimal) (fila[7]); 
            //String precioS = (fila[7] == null ? "0.00" : fila[7].toString());
            //BigDecimal precio = new BigDecimal(precioS);
            BigDecimal precio = guia02.getC2Precio(); 
            //String fleteS = (fila[8] == null ? "0.00" : fila[8].toString());
            //BigDecimal flete = new BigDecimal(fleteS);
            BigDecimal flete = BigDecimal.ZERO; 
            //String ajusteS = (fila[9] == null ? "0.00" : fila[9].toString());
            //BigDecimal ajuste = new BigDecimal(ajusteS);
            BigDecimal ajuste = BigDecimal.ZERO;  
            //String pesoGuiaS = (fila[10] == null ? "0.00" : fila[10].toString());
            // BigDecimal pesoGuia = new BigDecimal(pesoGuiaS);
            BigDecimal pesoGuia = guia02.getC2PesoGuia();  
            String facXunidad = guia02.getC2Fxunidad(); 
            String facXpeso = guia02.getC2Fxpeso();  
            inv08Dat = new Inv08Dat(new Inv08DatId(factura, codigo, noItem), new Inv07Dat(factura), tipoProd, descripcion, guia, peso, cantidad, precio, flete, facXunidad, facXpeso, pesoGuia, ajuste);
            inv08Dats.add(inv08Dat); 
        }  // for.  
        inv07.setInv08Dats(inv08Dats);
    }  //  getDatosProducto().  
    
}  // ControladorGuia01(). 

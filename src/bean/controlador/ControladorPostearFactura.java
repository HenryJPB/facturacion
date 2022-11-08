/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import bean.entidad.CxcdDat;
import bean.entidad.Inv07Dat;
import bean.entidad.Inv08Dat;
import bean.entidad.Inv08DatId;
import bean.interfase.IControladorPostearFactura;
import bean.modelo.ModeloTablaInv08;
import bean.utilitario.cliente.ServicioAdmonCxcd;
import bean.servicio.ServicioAdmonInv07;
import bean.servicio.ServicioAdmonVend01;
import bean.utilitario.despacho.ControladorGuia01;
import bean.utilitario.libreria.LibreriaHP;
import bean.entidad.Guias01Dat;
import bean.servicio.ServicioAdmonGuia01;
import bean.utilitario.fleteDestino.ServicioAdmonFlete04;
import entidad.Flete04Dat;
import entidad.Guias02Dat;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import vistas.PanelPostearFactura;
import static vistas.PanelPostearFactura.cbbCondicionesPago;
import static vistas.PanelPostearFactura.cbbVendedor;
import static vistas.PanelPostearFactura.cbbZonaVenta;
import static vistas.PanelPostearFactura.chbClienteEspecial;
import static vistas.PanelPostearFactura.chbRetiradoPlanta;
import static vistas.PanelPostearFactura.lblVendedor;
import static vistas.PanelPostearFactura.lblZonaVenta;
import static vistas.PanelPostearFactura.listOrdenFab;
import static vistas.PanelPostearFactura.spnPlazo;
import static vistas.PanelPostearFactura.txtCodCliente;
import static vistas.PanelPostearFactura.chbContribuyente;
import static vistas.PanelPostearFactura.txtDirEntrega;
import static vistas.PanelPostearFactura.txtFechaFactura;
import static vistas.PanelPostearFactura.txtFechaGuia;
import static vistas.PanelPostearFactura.txtMonExtranjera;
import static vistas.PanelPostearFactura.txtNombreCliente;
import static vistas.PanelPostearFactura.txtNroCtrlFiscal;
import static vistas.PanelPostearFactura.txtNroFactura;
import static vistas.PanelPostearFactura.txtNroGuia;
import static vistas.PanelPostearFactura.txtNroPedido;
import static vistas.PanelPostearFactura.txtPerCentDescuento;
import static vistas.PanelPostearFactura.txtPerCentFlete;

/**
 *
 * @author henrypb
 */
public class ControladorPostearFactura implements IControladorPostearFactura {

    LibreriaHP libHP = new LibreriaHP();
    private ControladorInv08.COLUMNA COLUMNA;
    static final int SI = 0;
    static final int NO = 1;
    //PanelPostearFactura panelPostearFactura = new PanelPostearFactura(); 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void limpiarCampos() {
        limpiarCamposBuscar();
        limpiarCamposCliente();
        limpiarCamposVenta();
        limpiarCamposProducto();
    }  // limpiarCampos(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void editarRegistro() {
        setCamposEditar(Boolean.TRUE);
        setBotoneraBusqueda(Boolean.TRUE);
        PanelPostearFactura.tablaInv08.setEnabled(Boolean.TRUE);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean checkCamposClaves() {
        Boolean ok = Boolean.FALSE;
        String factura = null;
        if (PanelPostearFactura.txtNroFactura.getText() != null) {
            factura = PanelPostearFactura.txtNroFactura.getText();
        }
        String ncf = null;
        if (PanelPostearFactura.txtNroCtrlFiscal.getText() != null) {
            ncf = PanelPostearFactura.txtNroCtrlFiscal.getText();
        }
        java.util.Date fecha = new java.util.Date();
        if (PanelPostearFactura.txtFechaFactura.getDate() != null) {
            fecha = PanelPostearFactura.txtFechaFactura.getDate();
        }
        ok = (factura != null) || (ncf != null) || (fecha != null);
        return (ok);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void setCamposEditar(Boolean activar) {
        setCamposBusqueda(activar);
        setCamposCliente(activar);
        setCamposVenta(activar);
        setCamposProducto(activar);
    }  // iniciarCampos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void setCamposBusqueda(Boolean activar) {
        PanelPostearFactura.txtNroFactura.setEditable(activar);
        PanelPostearFactura.txtFechaFactura.setEnabled(activar);
        PanelPostearFactura.txtNroCtrlFiscal.setEditable(activar);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void setBotoneraBusqueda(Boolean activar) {
        Boolean modoInsercion = PanelPostearFactura.chbModoInsercion.isSelected();
        Boolean desactivarBotones = !activar;
        if (modoInsercion) {
            PanelPostearFactura.btnBuscar.setEnabled(Boolean.FALSE);
            PanelPostearFactura.btnEjecutarBuscar.setEnabled(Boolean.FALSE);
            PanelPostearFactura.btnCancelarBusqueda.setEnabled(Boolean.FALSE);
        } else {
            PanelPostearFactura.btnBuscar.setEnabled(!activar);
            PanelPostearFactura.btnEjecutarBuscar.setEnabled(activar);
            PanelPostearFactura.btnCancelarBusqueda.setEnabled(activar);
            //this.cbbOperadorBusquedaFecha.setEnabled(activar);
        }  // if-else.  
    }  // setBotoneraBusqueda(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void setCamposCliente(Boolean activar) {
        PanelPostearFactura.chbClienteEspecial.setEnabled(activar);
        PanelPostearFactura.btnLovClientes.setEnabled(activar);
        PanelPostearFactura.txtCodCliente.setEditable(activar);
        PanelPostearFactura.txtNombreCliente.setEditable(activar);
        //PanelPostearFactura.chbContribuyente.setEnabled(activar);  // Campo NO editable.
        PanelPostearFactura.chbRetiradoPlanta.setEnabled(activar);
        PanelPostearFactura.txtOrdenCompra1.setEditable(activar);
        PanelPostearFactura.txtDirEntrega.setEditable(activar);
    }  // setCamposCliente().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void setCamposVenta(Boolean activar) {
        PanelPostearFactura.txtOrdenFab.setEditable(activar);
        PanelPostearFactura.listOrdenFab.setEnabled(activar);
        PanelPostearFactura.txtNroGuia.setEditable(activar);
        PanelPostearFactura.txtFechaGuia.setEnabled(activar);
        PanelPostearFactura.txtNroPedido.setEditable(activar);
        //PanelPostearFactura.cbbVendedor.setEditable(activar);
        PanelPostearFactura.cbbVendedor.setEnabled(activar);
        //PanelPostearFactura.cbbZonaVenta.setEditable(activar);
        PanelPostearFactura.cbbZonaVenta.setEnabled(activar);
        //PanelPostearFactura.cbbCondicionesPago.setEditable(activar);
        PanelPostearFactura.cbbCondicionesPago.setEnabled(activar);
        PanelPostearFactura.spnPlazo.setEnabled(activar);
        PanelPostearFactura.txtPerCentDescuento.setEditable(activar);
        PanelPostearFactura.txtPerCentFlete.setEditable(activar);
        PanelPostearFactura.txtMonExtranjera.setEditable(activar);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void setCamposProducto(Boolean activar) {
        PanelPostearFactura.btnAddItems.setEnabled(activar);
        PanelPostearFactura.btnGetFlete.setEnabled(activar);
        PanelPostearFactura.btnHelpFlete.setEnabled(activar);
        PanelPostearFactura.tablaInv08.setEnabled(activar);
        PanelPostearFactura.txtDepGarantia.setEditable(activar);
        PanelPostearFactura.txtFlete.setEditable(activar);
        PanelPostearFactura.txtDescuento.setEditable(activar);
        PanelPostearFactura.txtReconociFlete.setEditable(activar);
        PanelPostearFactura.chbNC.setEnabled(activar);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void iniciarCampos() {
        //iniciarCamposBuscar(); 
        iniciarCamposCliente();
        iniciarCamposVenta();
        iniciarCamposProducto();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void iniciarCamposBuscar() {

    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void iniciarCamposCliente() {
        PanelPostearFactura.chbClienteEspecial.setSelected(Boolean.FALSE);
        PanelPostearFactura.txtCodCliente.setText("");
        PanelPostearFactura.txtNombreCliente.setText("");
        PanelPostearFactura.chbContribuyente.setSelected(Boolean.FALSE);  // Campo Status <-> NO editable.  
        PanelPostearFactura.chbRetiradoPlanta.setSelected(Boolean.FALSE);
        PanelPostearFactura.txtOrdenCompra1.setText("");
        PanelPostearFactura.txtDirEntrega.setText("");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void iniciarCamposVenta() {
        PanelPostearFactura.listOrdenFab.setModel(new DefaultListModel());
        PanelPostearFactura.txtNroGuia.setText("");
        PanelPostearFactura.txtFechaGuia.setDate(null);
        PanelPostearFactura.txtNroPedido.setText("");
        // VENDEDORES: 
        String vendedores[] = {"00", "70"};  // (Brqto. 27/06/2014). sustituir esto por arrglo getted from D.B.  
        ServicioAdmonVend01 servAdmonVend01 = new ServicioAdmonVend01();
        //Vector<String> vendedores = servAdmonVend01.getCodVendedores();
        PanelPostearFactura.cbbVendedor.setModel(new DefaultComboBoxModel(vendedores));
        PanelPostearFactura.lblVendedor.setText("");
        // ZONAS DE VENTA:  
        String zonas[] = {"00"};
        PanelPostearFactura.cbbZonaVenta.setModel(new DefaultComboBoxModel(zonas));
        PanelPostearFactura.lblZonaVenta.setText("");
        String condiciones[] = {"CONTADO", "CREDITO"};
        PanelPostearFactura.cbbCondicionesPago.setModel(new DefaultComboBoxModel(condiciones));
        PanelPostearFactura.spnPlazo.setValue(0);
        PanelPostearFactura.txtPerCentDescuento.setText("");
        PanelPostearFactura.txtPerCentFlete.setText("");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void iniciarCamposProducto() {
        PanelPostearFactura.lblDestinoFlete.setText("");
        PanelPostearFactura.lblTipoTransp.setText("");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void limpiarCamposBuscar() {
        PanelPostearFactura.txtNroFactura.setText("");
        PanelPostearFactura.txtFechaFactura.setDate(null);
        PanelPostearFactura.txtFechaGuia.setDate(null);
        PanelPostearFactura.txtNroCtrlFiscal.setText("");
        PanelPostearFactura.chbModoConsulta.setSelected(Boolean.FALSE);
        PanelPostearFactura.chbModoInsercion.setSelected(Boolean.FALSE);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void limpiarCamposCliente() {
        PanelPostearFactura.chbClienteEspecial.setSelected(Boolean.FALSE);
        PanelPostearFactura.txtCodCliente.setText("");
        PanelPostearFactura.txtNombreCliente.setText("");
        PanelPostearFactura.chbContribuyente.setSelected(Boolean.FALSE);
        PanelPostearFactura.chbRetiradoPlanta.setSelected(Boolean.FALSE);
        PanelPostearFactura.txtOrdenCompra1.setText("");
        PanelPostearFactura.txtDirEntrega.setText("");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void limpiarCamposVenta() {
        PanelPostearFactura.txtOrdenFab.setText("");
        PanelPostearFactura.listOrdenFab.setModel(new DefaultListModel());
        PanelPostearFactura.txtNroGuia.setText("");
        PanelPostearFactura.txtFechaGuia.setDate(null);
        PanelPostearFactura.txtNroPedido.setText("");
        //PanelPostearFactura.cbbVendedor.setModel(new DefaultComboBoxModel());
        PanelPostearFactura.cbbVendedor.setSelectedItem("00");
        //PanelPostearFactura.cbbZonaVenta.setModel(new DefaultComboBoxModel());
        PanelPostearFactura.cbbZonaVenta.setSelectedItem("00");
        //PanelPostearFactura.cbbCondicionesPago.setModel(new DefaultComboBoxModel());
        PanelPostearFactura.cbbCondicionesPago.setSelectedItem("CONTADO");
        PanelPostearFactura.spnPlazo.setValue(0);
        PanelPostearFactura.txtPerCentDescuento.setText("");
        PanelPostearFactura.txtPerCentFlete.setText("");
        PanelPostearFactura.txtMonExtranjera.setText("");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void limpiarCamposProducto() {
        PanelPostearFactura.lblPesoGuia.setText(" ");
        PanelPostearFactura.tablaInv08.setModel(new ModeloTablaInv08() {
        });
        PanelPostearFactura.lblMontoFactura.setText(" ");
        PanelPostearFactura.lblBaseImponible.setText(" ");
        PanelPostearFactura.lblSubTotal.setText(" ");
        PanelPostearFactura.lblDestinoFlete.setText("");
        PanelPostearFactura.lblTipoTransp.setText("");
        PanelPostearFactura.txtMontoFlete.setText(" ");
        PanelPostearFactura.txtMontoAjuste.setText(" ");
        PanelPostearFactura.txtFlete.setText(" ");
        PanelPostearFactura.txtReconociFlete.setText(" ");
        PanelPostearFactura.txtDescuento.setText(" ");
        PanelPostearFactura.txtIva.setText(" ");
        PanelPostearFactura.lblSubTotal.setText(" ");
        PanelPostearFactura.txtDepGarantia.setText(" ");
        PanelPostearFactura.lblTotalFactura.setText(" ");
    }  // limpiarCamposProducto().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void actualizarRegistro() {
        String nroFactura = PanelPostearFactura.txtNroFactura.getText();
        ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
        Inv07Dat inv07Dat = loadDatosInv07();
        if (PanelPostearFactura.chbModoConsulta.isSelected()) {
            servAdmonInv07.actualizar(inv07Dat);
        } else if (PanelPostearFactura.chbModoInsercion.isSelected()) {
            if (servAdmonInv07.detalleRegistrado(nroFactura)) {  // asegurarce d q NO hay inconsistencia de informacion.  
                JOptionPane.showMessageDialog(null, "Se intenta repetir el NRO-FACTURA y/o Nro. Control Fiscal.", "ATENCION:", JOptionPane.WARNING_MESSAGE);
            } else {
                Set<Inv08Dat> inv08DatsBck = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
                //Inv07Dat inv07Aux = new Inv07Dat();  // (*). 
                Inv07Dat inv07Aux = inv07Dat;
                inv07Aux.setInv08Dats(new HashSet<Inv08Dat>());  // >> Anular/recrear los nodos hijos para registrar el nodo Cabeza. 
                //  ** Registrar NODO Master.        ** 
                servAdmonInv07.agregar(inv07Aux);
                //------------------------------------------------------------------
                //  ** Registrar elementos DETALLE.  **
                inv07Dat.setInv08Dats(inv08DatsBck);
                servAdmonInv07.actualizar(inv07Dat);
                // => actualizar Guias01Dat <- c1Status = 'F')acturada. // *IMPORTANTE* 
            }
        }  // if-else interno. 
        JOptionPane.showMessageDialog(null, "FACTURA actualizada exitosamente.", "ATENCION:", JOptionPane.OK_OPTION);
        cancelar();
    }  // actualizarRegistro().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat loadDatosInv07() {
        Inv07Dat inv07Dat = new Inv07Dat();
        loadDatosCliente(inv07Dat);
        loadDatosVenta(inv07Dat);
        loadDatosProducto(inv07Dat);
        /* ** Ejemplo visualiza los datos de un Set (Conjunto) **. 
         System.out.println("*****REVISAR******");
         Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
         for ( Inv08Dat i : inv08Dats ) {
         System.out.println("****cantidad="+i.getC8Unidades()+"******");
         } 
         */
        return (inv07Dat);
    }  // loadDatosInv07().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void loadDatosCliente(Inv07Dat inv07Dat) {
        final int precisionLineasDirEntrega = 4;
        // *claves*
        inv07Dat.setC7Factura(PanelPostearFactura.txtNroFactura.getText());
        inv07Dat.setC7Fecha(PanelPostearFactura.txtFechaFactura.getDate());
        inv07Dat.setC7Ncf(PanelPostearFactura.txtNroCtrlFiscal.getText());
        //
        inv07Dat.setC7ClienteEspecial("");
        if (PanelPostearFactura.chbClienteEspecial.isSelected()) {
            inv07Dat.setC7ClienteEspecial("X");
        }
        inv07Dat.setC7CodigoCliente(PanelPostearFactura.txtCodCliente.getText());
        inv07Dat.setC7RazonSocial(PanelPostearFactura.txtNombreCliente.getText());
        inv07Dat.setC7RetiradoPlanta("");
        if (PanelPostearFactura.chbRetiradoPlanta.isSelected()) {
            inv07Dat.setC7RetiradoPlanta("X");
        }
        inv07Dat.setC7OrdenCompra1(PanelPostearFactura.txtOrdenCompra1.getText());
        String dirEntrega = PanelPostearFactura.txtDirEntrega.getText();
        Integer c, l, i = 0;
        String a[] = new String[precisionLineasDirEntrega];
        Boolean continua = Boolean.TRUE;
        while (continua && i < precisionLineasDirEntrega) {  // enumerados desde zero (0).  
            l = dirEntrega.length();
            c = dirEntrega.indexOf("\n");
            if (c == -1) {
                if (l >= 0) {
                    continua = Boolean.FALSE;
                    a[i] = dirEntrega;
                }
            } else {
                if (c != -1 && c < l) {
                    a[i] = dirEntrega.substring(0, c);
                    dirEntrega = dirEntrega.substring(c + 1, l);
                }
            }  // if-else. 
            //System.out.println("*****dirEntrega[i]="+a[i].toString()+"***");  
            i++;
        }  // while().  
        if (i >= 1) {
            inv07Dat.setC7Entrega1(a[0]);
        }
        if (i >= 2) {
            inv07Dat.setC7Entrega2(a[1]);
        }
        if (i >= 3) {
            inv07Dat.setC7Entrega3(a[2]);
        }
        if (i >= 4) {
            inv07Dat.setC7Entrega4(a[3]);
        }
    }  // loadDatosCliente().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void loadDatosVenta(Inv07Dat inv07Dat) {
        final int PRECISION_GUIA = 6;
        ListModel dlm = PanelPostearFactura.listOrdenFab.getModel();
        if (dlm.getSize() > 0) {
            //System.out.println("Lista de Ord. Fab contiene DATOS,.........."); 
            if (dlm.getSize() >= 1) {
                inv07Dat.setC7Orden1((String) dlm.getElementAt(0));
            }
            if (dlm.getSize() >= 2) {
                inv07Dat.setC7Orden2((String) dlm.getElementAt(1));
            }
        }
        String guia = PanelPostearFactura.txtNroGuia.getText().trim();
        if (guia.length() > PRECISION_GUIA) {
            guia = guia.substring(0, PRECISION_GUIA);
        }
        inv07Dat.setC7Guia1(guia);
        inv07Dat.setC7FechaGuia(PanelPostearFactura.txtFechaGuia.getDate());
        inv07Dat.setC7Pedido1(PanelPostearFactura.txtNroPedido.getText());
        inv07Dat.setC7Vendedor((String) PanelPostearFactura.cbbVendedor.getSelectedItem());
        inv07Dat.setC7Nombre(PanelPostearFactura.lblVendedor.getText());
        inv07Dat.setC7Zona((String) PanelPostearFactura.cbbZonaVenta.getSelectedItem());
        inv07Dat.setC7CondicionesPago((String) PanelPostearFactura.cbbCondicionesPago.getSelectedItem());
        int plazo = PanelPostearFactura.spnPlazo.getValue();
        inv07Dat.setC7Plazo((short) plazo);
        String valorS = (String) ((PanelPostearFactura.txtPerCentDescuento.getText() != null && !PanelPostearFactura.txtPerCentDescuento.getText().isEmpty()) ? libHP.convFormatoNumerico(PanelPostearFactura.txtPerCentDescuento.getText()) : "0");
        inv07Dat.setC7PerCentFlete(new BigDecimal(valorS));
        valorS = (String) ((PanelPostearFactura.txtPerCentFlete.getText() != null && !PanelPostearFactura.txtPerCentFlete.getText().isEmpty()) ? libHP.convFormatoNumerico(PanelPostearFactura.txtPerCentFlete.getText()) : "0");
        inv07Dat.setC7PerCentFlete(new BigDecimal(valorS));
        valorS = (String) ((PanelPostearFactura.txtMonExtranjera.getText() != null && !PanelPostearFactura.txtMonExtranjera.getText().isEmpty()) ? libHP.convFormatoNumerico(PanelPostearFactura.txtMonExtranjera.getText()) : "0");
        inv07Dat.setC7CambioMoneda(new BigDecimal(valorS));
    }  // loadDatosVenta().  

    //--------------------------------------------------------------------------
    // * Eliminame  ( old (way) load Datos Producto ).  * 
    //--------------------------------------------------------------------------
    private void loadDatosProducto0(Inv07Dat inv07Dat) {
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        //inv07Dat.setC7MontoFlete(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtMontoFlete.getText())));
        inv07Dat.setC7MontoFlete(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtFlete.getText())));
        inv07Dat.setC7MontoAjuste(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtMontoAjuste.getText())));
        inv07Dat.setC7ReconociFlete(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtReconociFlete.getText())));
        inv07Dat.setC7Descuento(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDescuento.getText())));
        inv07Dat.setC7Ivm(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtIva.getText())));
        inv07Dat.setC7Anticipo(new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDepGarantia.getText())));
        /*
         Set<Inv08Dat> inv08Dats = new Set<Inv08Dat>() { 

         @Override
         public int size() {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean isEmpty() {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean contains(Object o) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public Iterator<Inv08Dat> iterator() {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public Object[] toArray() {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public <T> T[] toArray(T[] a) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean add(Inv08Dat e) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean remove(Object o) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean containsAll(Collection<?> c) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean addAll(Collection<? extends Inv08Dat> c) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean retainAll(Collection<?> c) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public boolean removeAll(Collection<?> c) {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public void clear() {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }
         };
         */
        Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
        //Inv08Dat inv08Dat = null;  
        ModeloTablaInv08 m = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        Object[] fila;
        Integer i = 0;
        System.out.println("aquica1.......................");
        for (Inv08Dat inv08Dat : inv08Dats) {
            System.out.println("aquica2.......................");
            //inv08Dats.removeAll(inv08Dats); 
            fila = m.listaDetalleProductos.get(i);
            System.out.println("**fila[0]=" + fila[0] + "***fila[1]=" + fila[1] + "**fila[2]=" + fila[2] + "***fila[3]=" + fila[3] + "***fila[4]=" + fila[4] + "***fila[5]=" + fila[5] + "*fila[6]=**" + fila[6] + "***");
            String factura = PanelPostearFactura.txtNroFactura.getText();
            Byte noItem = (Byte) fila[0];
            String codigo = (String) fila[1];
            inv08Dat.setId(new Inv08DatId(factura, codigo, noItem));
            String tipoProd = (String) fila[2];
            inv08Dat.setC8Tipo(tipoProd);
            String descripcion = (String) fila[3];
            inv08Dat.setC8Descripcion(descripcion);
            String guia = (String) fila[4];
            inv08Dat.setC8Guia(guia);
            BigDecimal peso = (BigDecimal) fila[5];
            inv08Dat.setC8Peso(peso);
            Integer cantidad = (Integer) fila[6];
            inv08Dat.setC8Unidades(cantidad);
            BigDecimal precio = (BigDecimal) fila[7];
            inv08Dat.setC8Precio(precio);
            BigDecimal flete = (BigDecimal) fila[8];
            inv08Dat.setC8Flete(flete);
            BigDecimal ajuste = (BigDecimal) fila[9];
            inv08Dat.setC8Ajuste(ajuste);
            BigDecimal pesoGuia = (BigDecimal) fila[10];
            inv08Dat.setC8PesoGuia(pesoGuia);
            Boolean b = (Boolean) fila[11];
            String facXunidad = ctldrInv08.facXunidad(b);
            inv08Dat.setC8Fxunidad(facXunidad);
            b = (Boolean) fila[12];
            String facXpeso = ctldrInv08.facXpeso(b);
            inv08Dat.setC8Fxpeso(facXpeso);
            // ** total Monto **
            BigDecimal monto = precio;
            monto = monto.multiply(new BigDecimal(cantidad));
            //inv08Dat = new Inv08Dat(new Inv08DatId(factura,codigo,noItem), new Inv07Dat(factura), tipoProd, descripcion,guia,peso,cantidad,precio,flete,facXunidad,facXpeso,pesoGuia,ajuste ); 
            //System.out.println("pesoGuia="+inv08Dat.getC8PesoGuia()+"facXpeso="+inv08Dat.getC8Fxpeso()+"*******");
            //inv08Dats.add(inv08Dat);     
            System.out.println("****cantidad=" + inv08Dat.getC8Unidades() + "****");
        }
        inv07Dat.setInv08Dats(inv08Dats);
        /*
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
         */
    }  // loadDatosProducto0()------------------Old-----------------------------

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void loadDatosProducto(Inv07Dat inv07Dat) {
        // Nota (1): el siguiente flete se refiere al registrado al flete de la Grilla.  
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        BigDecimal montoFlete = BigDecimal.ZERO;
        if (PanelPostearFactura.txtMontoFlete.getText() != null && !PanelPostearFactura.txtMontoFlete.getText().isEmpty() && PanelPostearFactura.txtMontoFlete.getText().trim().length() > 0) {
            montoFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtMontoFlete.getText()));
        }
        inv07Dat.setC7MontoFlete(montoFlete);
        //
        if ( PanelPostearFactura.chbNC.isSelected() ) {
            inv07Dat.setC7Nc("X");
        } else {
            inv07Dat.setC7Nc(null);
        }
        // Nota (2): el siguiente flete se refiere al flete calculado por <-> % flete.  
        // *
        // Nota ( Brqto: 31/10/14 ) --> implantar metodo de calculo de flete dada la matriz de calculo de Ventas.  
        // **
        BigDecimal cobroFlete = BigDecimal.ZERO;
        if (PanelPostearFactura.txtFlete.getText() != null && !PanelPostearFactura.txtFlete.getText().isEmpty() && PanelPostearFactura.txtFlete.getText().trim().length() > 0) {
            cobroFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtFlete.getText()));
        }
        inv07Dat.setC7MontoFlete(cobroFlete);
        //
        BigDecimal montoAjuste = BigDecimal.ZERO;
        if (PanelPostearFactura.txtMontoAjuste.getText() != null && PanelPostearFactura.txtMontoAjuste.getText().isEmpty() && PanelPostearFactura.txtMontoAjuste.getText().trim().length() > 0) {
            montoAjuste = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtMontoAjuste.getText()));
        }
        inv07Dat.setC7MontoAjuste(montoAjuste);
        BigDecimal reconociFlete = BigDecimal.ZERO;
        if (PanelPostearFactura.txtReconociFlete.getText() != null && !PanelPostearFactura.txtReconociFlete.getText().isEmpty() && PanelPostearFactura.txtReconociFlete.getText().trim().length() > 0) {
            reconociFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtReconociFlete.getText()));
        }
        inv07Dat.setC7ReconociFlete(reconociFlete);
        BigDecimal descuento = BigDecimal.ZERO;
        if (PanelPostearFactura.txtDescuento.getText() != null && !PanelPostearFactura.txtDescuento.getText().isEmpty() && PanelPostearFactura.txtDescuento.getText().trim().length() > 0) {
            descuento = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDescuento.getText()));
        }
        inv07Dat.setC7Descuento(descuento);
        BigDecimal iva = BigDecimal.ZERO;
        if (PanelPostearFactura.txtIva.getText() != null && !PanelPostearFactura.txtIva.getText().isEmpty() && PanelPostearFactura.txtIva.getText().trim().length() > 0) {
            iva = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtIva.getText()));
        }
        inv07Dat.setC7Ivm(iva);
        BigDecimal anticipo = BigDecimal.ZERO;
        if (PanelPostearFactura.txtDepGarantia.getText() != null && !PanelPostearFactura.txtDepGarantia.getText().isEmpty() && PanelPostearFactura.txtDepGarantia.getText().trim().length() > 0) {
            anticipo = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDepGarantia.getText()));
        }
        inv07Dat.setC7Anticipo(anticipo);
        Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
        Inv08Dat inv08Dat = null;
        inv08Dats.clear();
        ModeloTablaInv08 m = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        for (Object[] fila : m.listaDetalleProductos) {
            //System.out.println("**fila[0]="+fila[0]+"***fila[1]="+fila[1]+"**fila[2]="+fila[2]+"***fila[3]="+fila[3]+"***fila[4]="+fila[4]+"***fila[5]="+fila[5]+"*fila[6]=**"+fila[6]+"**fila[7]=*"+fila[7]+"******");
            String factura = PanelPostearFactura.txtNroFactura.getText();
            Byte noItem = (Byte) fila[0];
            String codigo = (String) fila[1];
            String tipoProd = (String) fila[2];
            String descripcion = (String) fila[3];
            String guia = (String) fila[4];
            String pesoS = (fila[5] == null ? "0.00" : fila[5].toString());
            BigDecimal peso = new BigDecimal(pesoS);
            Integer cantidad = (Integer) fila[6];
            //BigDecimal precio = (BigDecimal) (fila[7]); 
            String precioS = (fila[7] == null ? "0.00" : fila[7].toString());
            BigDecimal precio = new BigDecimal(precioS);
            //
            String fleteS = (fila[8] == null ? "0.00" : fila[8].toString());
            BigDecimal flete = new BigDecimal(fleteS);
            //
            String ajusteS = (fila[9] == null ? "0.00" : fila[9].toString());
            BigDecimal ajuste = new BigDecimal(ajusteS);
            //
            String pesoGuiaS = (fila[10] == null ? "0.00" : fila[10].toString());
            BigDecimal pesoGuia = new BigDecimal(pesoGuiaS);
            //
            Boolean b = (Boolean) fila[11];
            String facXunidad = ctldrInv08.facXunidad(b);
            b = (Boolean) fila[12];
            String facXpeso = ctldrInv08.facXpeso(b);
            BigDecimal monto = precio;
            monto = monto.multiply(new BigDecimal(cantidad));
            inv08Dat = new Inv08Dat(new Inv08DatId(factura, codigo, noItem), new Inv07Dat(factura), tipoProd, descripcion, guia, peso, cantidad, precio, flete, facXunidad, facXpeso, pesoGuia, ajuste);
            //System.out.println("**fila[0]="+fila[0]+"***fila[1]="+fila[1]+"**fila[2]="+fila[2]+"***fila[3]="+fila[3]+"***fila[4]="+fila[4]+"***fila[5]="+fila[5]+"*fila[6]=**"+fila[6]+"**fila[7]=*"+fila[7]+"******");
            //System.out.println("pesoGuia="+inv08Dat.getC8PesoGuia()+"facXpeso="+inv08Dat.getC8Fxpeso()+"*******");
            inv08Dats.add(inv08Dat);
        }
        inv07Dat.setInv08Dats(inv08Dats);
    }  // loadDatosProducto()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void EliminarsetDatosProducto(Guias01Dat guia01) {
        Set<Guias02Dat> guia02Dats = (Set<Guias02Dat>) guia01.getGuias02Dats();
        for (Guias02Dat guia02 : guia02Dats) {
            System.out.println("**itemNro=**" + guia02.getId().getC2ItemNo() + "*cod Prod=**" + guia02.getId().getC2Codigo() + "**tipoProd" + guia02.getC2Tipo() + "*descrip=" + guia02.getC2Descripcion() + "****");
        }
    }  // setDatosProducto(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void showDatosMaestro(Inv07Dat inv07Dat) {
        showDatosClaves(inv07Dat);
        showDatosCliente(inv07Dat);
        showDatosVenta(inv07Dat);
    }  // showDatosMaestro(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void showDatosClaves(Inv07Dat inv07Dat) {
        PanelPostearFactura.txtNroFactura.setText(inv07Dat.getC7Factura());
        PanelPostearFactura.txtNroCtrlFiscal.setText(inv07Dat.getC7Ncf());
        PanelPostearFactura.txtFechaFactura.setDate(inv07Dat.getC7Fecha());
    }  // showDatosClaves(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void showDatosCliente(Inv07Dat inv07Dat) {
        ServicioAdmonCxcd servicioAdmonCxcd = new ServicioAdmonCxcd();
        txtNroFactura.setText(inv07Dat.getC7Factura());
        txtNroCtrlFiscal.setText(inv07Dat.getC7Ncf());
        txtFechaFactura.setDate(inv07Dat.getC7Fecha());
        if (inv07Dat.getC7ClienteEspecial() != null && !inv07Dat.getC7ClienteEspecial().isEmpty()) {
            chbClienteEspecial.setSelected(true);
        } else {
            chbClienteEspecial.setSelected(false);
        }
        String codCliente = inv07Dat.getC7CodigoCliente();
        txtCodCliente.setText(codCliente);
        CxcdDat cliente = servicioAdmonCxcd.getCliente(codCliente);
        txtNombreCliente.setText(cliente.getNombreCliProv());
        chbContribuyente.setSelected(((cliente.getContribuyente() != null && !cliente.getContribuyente().isEmpty()) == Boolean.TRUE && cliente.getContribuyente().equals("S") ? Boolean.TRUE : Boolean.FALSE));
        if (inv07Dat.getC7RetiradoPlanta() != null && !inv07Dat.getC7RetiradoPlanta().isEmpty()) {
            chbRetiradoPlanta.setSelected(true);
        } else {
            chbRetiradoPlanta.setSelected(false);
        }
        PanelPostearFactura.txtOrdenCompra1.setText(inv07Dat.getC7OrdenCompra1());
        String dirEntrega = null;
        dirEntrega = (inv07Dat.getC7Entrega1() != null ? inv07Dat.getC7Entrega1().trim() + "\n" : " ")
                + (inv07Dat.getC7Entrega2() != null ? inv07Dat.getC7Entrega2().trim() + "\n" : " ")
                + (inv07Dat.getC7Entrega3() != null ? inv07Dat.getC7Entrega3().trim() + "\n" : " ")
                + (inv07Dat.getC7Entrega4() != null ? inv07Dat.getC7Entrega4().trim() + "\n" : " ");
        txtDirEntrega.setText(dirEntrega);
    }  // showDatosCliente().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void showDatosVenta(Inv07Dat inv07Dat) {
        DefaultListModel modeloListaOrdenFab = new DefaultListModel();
        //DefaultListModel modeloListaCodVendedor = new DefaultListModel();
        //DefaultListModel modeloListaZonaVenta = new DefaultListModel();
        LibreriaHP libHP = new LibreriaHP();
        modeloListaOrdenFab.clear();
        String orden = inv07Dat.getC7Orden1();
        if (orden != null && !orden.isEmpty()) {
            modeloListaOrdenFab.addElement(orden);
        }
        orden = inv07Dat.getC7Orden2();
        if (orden != null && !orden.isEmpty()) {
            modeloListaOrdenFab.addElement(orden);
        }
        listOrdenFab.setModel(modeloListaOrdenFab);
        txtNroGuia.setText(inv07Dat.getC7Guia1() + " " + (inv07Dat.getC7Guia2() != null ? inv07Dat.getC7Guia2() : ""));
        txtFechaGuia.setDate(inv07Dat.getC7FechaGuia());
        txtNroPedido.setText(inv07Dat.getC7Pedido1());
        String codVendedor = inv07Dat.getC7Vendedor();
        if (codVendedor == null) {
            codVendedor = "00";
        }
        cbbVendedor.setSelectedItem(codVendedor);
        lblVendedor.setText(vendedor(codVendedor));
        String zonaVenta = inv07Dat.getC7Zona();
        if (zonaVenta == null) {
            zonaVenta = "00";
        }
        cbbZonaVenta.setSelectedItem(zonaVenta);
        lblZonaVenta.setText("<ubic geograf. zona venta>");
        String condVenta = inv07Dat.getC7CondicionesPago();
        if (condVenta == null) {
            condVenta = "CONTADO";
        }
        cbbCondicionesPago.setSelectedItem(condVenta);
        spnPlazo.setValue(0);
        if (inv07Dat.getC7Plazo() != null) {
            spnPlazo.setValue(inv07Dat.getC7Plazo());
        }
        if (inv07Dat.getC7DescPerCent() != null) {
            txtPerCentDescuento.setText(libHP.formatoDecimal.format(inv07Dat.getC7DescPerCent()));
        }
        if (inv07Dat.getC7PerCentFlete() != null) {
            txtPerCentFlete.setText(libHP.formatoDecimal.format(inv07Dat.getC7PerCentFlete()));
        }
        txtMonExtranjera.setText(libHP.formatoDecimal.format(1.00));
        if (inv07Dat.getC7CambioMoneda() != null) {
            txtMonExtranjera.setText(libHP.formatoDecimal.format(inv07Dat.getC7CambioMoneda()));
        }
    }  // showDatosVenta().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void showDatosDetalleProducto(Inv07Dat inv07Dat) {
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        ctldrInv08.iniciarTablaProductos(inv07Dat);
        if (inv07Dat.getC7MontoFlete() != null) {
            PanelPostearFactura.txtFlete.setText(libHP.formatoDecimal.format(inv07Dat.getC7MontoFlete()));
        }
        if ( inv07Dat.getC7Nc() != null && !inv07Dat.getC7Nc().isEmpty()) {
            PanelPostearFactura.chbNC.setSelected(Boolean.TRUE);
        } else {
            PanelPostearFactura.chbNC.setSelected(Boolean.FALSE);
        }
    }  // showDatosDetalleProducto().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public String vendedor(String codVendedor) {
        ServicioAdmonVend01 servAdmonVend01 = new ServicioAdmonVend01();
        return (servAdmonVend01.nombreVendedor(codVendedor));
    }  // vendedor().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void agregarRegistro() {
        limpiarCampos();
        ControladorGuia01 ctldrGuia01 = new ControladorGuia01();
        ctldrGuia01.ejecutarDialogGuiasFacturar();
        //
        ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
        Inv07Dat inv07Dat = servAdmonInv07.getNextFactura();
        PanelPostearFactura.txtNroFactura.setText(inv07Dat.getC7Factura());
        PanelPostearFactura.txtFechaFactura.setDate(inv07Dat.getC7Fecha());
        PanelPostearFactura.txtNroCtrlFiscal.setText(inv07Dat.getC7Ncf());
        PanelPostearFactura.txtFechaFactura.setDate(new java.util.Date());
        PanelPostearFactura.chbModoInsercion.setSelected(Boolean.TRUE);
        //editarRegistro();
        setBotoneraBusqueda(Boolean.FALSE);
        //
    } // agregarRegistro().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void eliminarRegistro() {
        /*
         if ((nroGuia != null && !nroGuia.isEmpty()) && modoConsulta() && (JOptionPane.showConfirmDialog(panelGuiaDespacho, "Eliminar Registro", "Conforme", JOptionPane.YES_NO_OPTION) == SI)) {
         // si exiteGuia01( nroGuia )
         //    -> eliminarGuia01 
         if (servicioAdministracionGuia01.guiaExiste(nroGuia)) {
         servicioAdministracionGuia01.eliminar(nroGuia);
         }
         */
        if (JOptionPane.showConfirmDialog(null, "Eliminar Registro", "Conforme", JOptionPane.YES_NO_OPTION) == SI) {
            //System.out.println("ELIMINAR REGISTRO:::::>");
            if (PanelPostearFactura.chbModoConsulta.isSelected() && checkCamposClaves()) {
                Inv07Dat inv07Dat = loadDatosInv07();  // Vaciar la inf de la pantalla al registro. <-> recuerda que getInv07 lo hace desde el reg de la B.D. 
                ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
                servAdmonInv07.eliminar(inv07Dat);
                limpiarCampos();
            }  // if. 
        }
    }  // eliminarRegistro.          

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void cancelar() {
        PanelPostearFactura.chbModoConsulta.setSelected(Boolean.FALSE);
        PanelPostearFactura.chbModoInsercion.setSelected(Boolean.FALSE);
        setCamposEditar(Boolean.FALSE);
        setCamposBusqueda(Boolean.FALSE);
        setBotoneraBusqueda(Boolean.FALSE);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean pedidoOrdFab() {
        Boolean ok = Boolean.FALSE;
        DefaultListModel dlm = (DefaultListModel) listOrdenFab.getModel();
        if (!dlm.isEmpty()) {
            ok = Boolean.TRUE;
        }
        return (ok);
    }  // pedidoOrdFab(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void actualizarFlete(java.util.Date alFecha, String codDestino, String codSector, String tipoTransporte) {
        /* ====================================================================  
         Informacion ilustrativa  ( no eliminar ): ==========================
         Brqto ( 05-11-2014 ): Revisar el sig ejemplo de lanzar query desde hibernate
         config --> Run Query0: from Flete04Dat where  id.c4FechaRelacion = '15-04-2014
         ==================================================================== */
        ServicioAdmonFlete04 servAdmFlete04 = new ServicioAdmonFlete04();
        java.util.Date fecha = PanelPostearFactura.txtFechaGuia.getDate();
        if (fecha != null) {
            Flete04Dat flete04 = servAdmFlete04.getTarifaFlete(fecha, codDestino, codSector);
            if (flete04 != null) {
                BigDecimal montoFlete = BigDecimal.ZERO;
                if (pedidoOrdFab()) {
                    montoFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.lblPesoGuia.getText()));
                    montoFlete = montoFlete.multiply(flete04.getC4PrecioTonEspecial() == null ? BigDecimal.ZERO : flete04.getC4PrecioTonEspecial());
                    montoFlete = montoFlete.divide(new BigDecimal(1000));  // expresar kgs -> ton. 
                } else {
                    switch (tipoTransporte) {
                        case "C":
                            montoFlete = flete04.getC4PrecioTonCamion();
                            break;
                        case "G":
                            montoFlete = flete04.getC4PrecioTonGandola();
                            break;
                        case "T":
                            montoFlete = flete04.getC4PrecioTonToronto();
                            break;
                    }   // swicth.
                }  // if pedido de 1 ord de fabric. 
                PanelPostearFactura.txtFlete.setText(libHP.formatoDecimal.format(montoFlete));
                PanelPostearFactura.lblDestinoFlete.setText(codDestino + "-" + codSector);
                PanelPostearFactura.lblTipoTransp.setText("(" + tipoTransporte + ")");
            }
        }  // if flete04 != null.  
    }  // actualizarMontoFlete(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public JPanel panelShowInfoFlete() {
        JPanel panel = new JPanel(null);
        String guia = PanelPostearFactura.txtNroGuia.getText().trim();
        ServicioAdmonGuia01 servAdmGuia01 = new ServicioAdmonGuia01();
        Guias01Dat guia01 = servAdmGuia01.getGuiaDespacho(guia);
        java.util.Date fechaGuia = guia01.getC1FechaGuia();
        txtFechaGuia.setDate(fechaGuia);
        String codDestino = guia01.getC1CodDestino();
        if (fechaGuia != null && codDestino != null && guia01.getC1TipoTransporte() != null) {
            String codSector = ((guia01.getC1CodSector() == null || guia01.getC1CodSector().isEmpty()) ? "000" : guia01.getC1CodSector());
            //
            // * linea de msg 1* ( Destino )====================================
            JLabel lbl01 = new JLabel();
            lbl01.setSize(200, 16);
            lbl01.setLocation(10, 10);
            lbl01.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl01.setText("(*) Destino : ");
            panel.add(lbl01);
            //
            ServicioAdmonFlete04 servAdmFlete04 = new ServicioAdmonFlete04();
            Flete04Dat flete04 = servAdmFlete04.getTarifaFlete(fechaGuia, codDestino, codSector);
            JLabel lbl11 = new JLabel();
            lbl11.setSize(300, 16);
            lbl11.setLocation(150, 10);
            lbl11.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl11.setForeground(Color.BLUE);
            lbl11.setText(codDestino + "-" + codSector + "  " + flete04.getC4NombreDestino());
            panel.add(lbl11);
            //
            // * linea de msg 2* (Fecha tarifaria)==============================
            //JLabel lbl02 = new JLabel("Prueba1",SwingConstants.LEFT);
            JLabel lbl02 = new JLabel();
            lbl02.setSize(200, 16);
            lbl02.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl02.setLocation(10, 30);
            lbl02.setText("(*) Fecha tarifa : ");
            panel.add(lbl02);
            JLabel lbl12 = new JLabel();
            lbl12.setSize(100, 16);
            lbl12.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl12.setLocation(150, 30);
            lbl12.setForeground(Color.BLUE);
            lbl12.setText(libHP.formatoFecha.format(flete04.getId().getC4FechaRelacion()));
            panel.add(lbl12);
            //
            // * linea de msg 3* (Tipo transporte)==============================
            JLabel lbl03 = new JLabel();
            lbl03.setSize(200, 16);
            lbl03.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl03.setLocation(10, 50);
            lbl03.setText("(*) Tipo Transporte : ");
            panel.add(lbl03);
            JLabel lbl13 = new JLabel();
            lbl13.setSize(100, 16);
            lbl13.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl13.setLocation(150, 50);
            lbl13.setForeground(Color.BLUE);
            String tipoTransp = null;
            BigDecimal montoFlete = BigDecimal.ZERO; 
            switch (guia01.getC1TipoTransporte().trim()) {
                case "C": 
                    tipoTransp = "CAMION";
                    montoFlete = flete04.getC4PrecioTonCamion();  
                    break;  
                case "G":
                    tipoTransp = "GANDOLA"; 
                    montoFlete = flete04.getC4PrecioTonGandola();  
                    break; 
                case "T":
                    tipoTransp = "TORONTO"; 
                    montoFlete = flete04.getC4PrecioTonToronto();  
                    break; 
                default :
                    tipoTransp = "* No definido?* "; 
                    montoFlete = BigDecimal.ZERO; 
            }  // switch().   
            lbl13.setText(tipoTransp);
            panel.add(lbl13);
            // 
            // * linea de msg 4* ( Monto de la tarifa)==========================
            JLabel lbl04 = new JLabel();
            lbl04.setSize(200, 16);
            lbl04.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl04.setLocation(10, 70);
            lbl04.setText("(*) Monto : ");
            panel.add(lbl04);
            JLabel lbl14 = new JLabel();
            lbl14.setSize(100, 16);
            lbl14.setFont(new Font("Dialog", Font.BOLD, 11));
            lbl14.setLocation(150, 70);
            lbl14.setForeground(Color.BLUE);
            String txtMontoFlete = libHP.formatoDecimal.format(montoFlete); 
            if (pedidoOrdFab()) {
                //txtMonto = txtMonto + " " + "Bs/ton.";  
                montoFlete = BigDecimal.ZERO;
                if ( flete04.getC4PrecioTonEspecial()!=null ) {
                    montoFlete = flete04.getC4PrecioTonEspecial();                      
                }
                txtMontoFlete = libHP.formatoDecimal.format(montoFlete)+" Bs./ton."; 
            }
            lbl14.setText(txtMontoFlete);
            panel.add(lbl14);
        }  // if .  
        return (panel);
    }  // showInfoFlete().   

}  // ControladorPostearFactura(); 

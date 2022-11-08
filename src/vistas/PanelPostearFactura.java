/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import bean.controlador.ControladorAddItemsInv08;
import bean.controlador.ControladorInv08;
import bean.controlador.ControladorPostearFactura;
import bean.entidad.CxcdDat;
import bean.entidad.Guias01Dat;
import bean.entidad.Inv07Dat;
import bean.modelo.ModeloTablaInv08;
import bean.servicio.ServicioAdmonGuia01;
import bean.utilitario.cliente.ServicioAdmonCxcd;
import bean.servicio.ServicioAdmonInv07;
import bean.servicio.ServicioAdmonVend01;
import bean.utilitario.cliente.ControladorLovCliente;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author henrypb
 */
public final class PanelPostearFactura extends javax.swing.JPanel {

    Double factorIVA = 12.00; // al 24-09-2014.  
    ControladorPostearFactura ctldrPostearFactura = new ControladorPostearFactura();
    ServicioAdmonCxcd servicioAdmonCxcd;
    DefaultListModel modeloListaOrdenFab = new DefaultListModel();
    DefaultListModel modeloListaCodVendedor = new DefaultListModel();
    DefaultListModel modeloListaZonaVenta = new DefaultListModel();
    //DecimalFormat formatoDecimal = new DecimalFormat("###,###,###,##0.00");
    LibreriaHP libHP = new LibreriaHP();

    public PanelPostearFactura() {
        initComponents();
        iniciar();
        ctldrPostearFactura.setCamposEditar(Boolean.FALSE);
        ctldrPostearFactura.iniciarCampos();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        PanelPostearFactura.lblFactorIVA.setText(libHP.formatoDecimal.format(factorIVA));
        PanelPostearFactura.txtIva.setEditable(Boolean.FALSE);
        PanelPostearFactura.txtMontoAjuste.setEditable(Boolean.FALSE);
        PanelPostearFactura.txtMontoFlete.setEditable(Boolean.FALSE);
        PanelPostearFactura.chbModoConsulta.setSelected(Boolean.FALSE);
        PanelPostearFactura.chbModoInsercion.setSelected(Boolean.FALSE);
        //iniciarCbbVendedor();  // -> ver ctldrPostearFactura.iniciarCampos,... 
    }  // iniciar().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarCbbVendedor() {
        //DefaultComboBoxModel dm = (DefaultComboBoxModel) cbbVendedor.getModel(); 
        //dm.removeAllElements();
        //dm.
        cbbVendedor.removeAllItems();
        ServicioAdmonVend01 servAdmonVend01 = new ServicioAdmonVend01();
        DefaultComboBoxModel dm = new DefaultComboBoxModel(servAdmonVend01.getCodVendedores());
        cbbVendedor.setModel(dm);
    }  // iniciarCbbVendedor()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void notificarFocus(JTextField campo) {
        campo.setBackground(Color.YELLOW);
        campo.requestFocus();
    }  // notificarFocus().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean nroOrdenRegistrada(String elemento) {
        Boolean registrado = Boolean.FALSE;
        if (!modeloListaOrdenFab.isEmpty() && modeloListaOrdenFab.indexOf(elemento) >= 0) {
            registrado = Boolean.TRUE;
        }
        return (registrado);
    } // nroOrdenRegistrada().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void agregarOrdenFab() {
        final int MAX_ORDENES = 4;
        DefaultListModel modelo = (DefaultListModel) listOrdenFab.getModel();
        String elemento = txtOrdenFab.getText();
        if ((elemento != null || !elemento.isEmpty()) && !nroOrdenRegistrada(elemento) && modeloListaOrdenFab.getSize() < MAX_ORDENES) {
            modelo.addElement(elemento);
            //modeloListaOrdenFab.addElement(elemento);
        }
        //listOrdenFab.setModel(modeloListaOrdenFab);
        listOrdenFab.setModel(modelo);
    }  // agtegarOrdenFab().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void eliminarOrdenFab() {
        DefaultListModel modelo = (DefaultListModel) listOrdenFab.getModel();
        if (listOrdenFab.getSelectedValue() != null) {
            String elemento = listOrdenFab.getSelectedValue().toString();
            //modeloListaOrdenFab.removeElement(elemento);
            modelo.removeElement(elemento);
            //listOrdenFab.setModel(modeloListaOrdenFab);
            listOrdenFab.setModel(modelo);
        }
        //listOrdenFab.updateUI();  // ¿¿¿¿Necesario????. 
    }  // eliminarOrdenFab().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ejecutarBuscar() {
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        List<Inv07Dat> listaFacturas = new ArrayList<Inv07Dat>();
        Inv07Dat inv07Dat = null;
        String nroFactura = PanelPostearFactura.txtNroFactura.getText();
        String ncf = PanelPostearFactura.txtNroCtrlFiscal.getText();
        java.sql.Date sqlFechaFactura = null;
        java.util.Date utilFechaFactura = PanelPostearFactura.txtFechaFactura.getDate();
        if (utilFechaFactura != null) {
            sqlFechaFactura = new java.sql.Date(utilFechaFactura.getTime());
            //System.out.println("....Fecha factura="+sqlFechaFactura+"*******");
        }
        if ((nroFactura != null && !nroFactura.isEmpty()) || (ncf != null && !ncf.isEmpty() || (sqlFechaFactura != null))) {
            ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
            if (servAdmonInv07.facturaRegistrada(ncf, nroFactura, sqlFechaFactura)) {
                //JOptionPane.showMessageDialog(this, "EXITO=FACTURA registrada-", "ATENCION:", JOptionPane.WARNING_MESSAGE);
                listaFacturas = servAdmonInv07.getlistaFacturas(ncf, nroFactura, sqlFechaFactura);
                inv07Dat = (Inv07Dat) listaFacturas.get(0);
                //System.out.println("EXITOOOOOO;> nro Factura=" + inv07Dat.getC7Factura() + "fecha=" + inv07Dat.getC7Fecha() + "*Paridad cambiaria=" + inv07Dat.getC7CambioMoneda() + "**");
                ctldrPostearFactura.showDatosMaestro(inv07Dat);
                ctldrPostearFactura.showDatosDetalleProducto(inv07Dat);
                ctldrInv08.showTotales(inv07Dat);
                /*
                 System.out.println("DETALLE DE PRODUCTO:");
                 System.out.println("====================");  
                 Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();  
                 for (Inv08Dat inv08Dat : inv08Dats ) {
                 System.out.println("("+inv08Dat.getId().getC8ItemNo()+")-Cod Prod: "+inv08Dat.getId().getC8Codigo()+"-Descripcion: "+inv08Dat.getC8Descripcion()+" ");
                 }  // for
                 */
                PanelPostearFactura.chbModoConsulta.setSelected(Boolean.TRUE);
                PanelPostearFactura.chbModoInsercion.setSelected(Boolean.FALSE);
            } else {
                JOptionPane.showMessageDialog(this, "FACTURA NO registrada. Intente nuevamente.", "ATENCION:", JOptionPane.WARNING_MESSAGE);
            } // if-else.   
        }
        cancelarBuscar();
    }  // ejecutarBusqueda().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void cancelarBuscar() {
        ctldrPostearFactura.setCamposBusqueda(false);
        ctldrPostearFactura.setBotoneraBusqueda(false);
    }  // cancelarBUscar().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String zonaVenta(String codVenta) {
        return null;
    }  // zonaVenta().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void actualizarDatosVenta() {
        lblVendedor.setText(ctldrPostearFactura.vendedor((String) cbbVendedor.getSelectedItem()));
        ServicioAdmonVend01 servAdmonVend01 = new ServicioAdmonVend01();
        DefaultComboBoxModel dm = new DefaultComboBoxModel(servAdmonVend01.getZonasVenta((String) cbbVendedor.getSelectedItem()));
        cbbZonaVenta.setModel(dm);
    }  // actualizarDatosVenta(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void validarCliente() {
        //System.out.println("Action Performed: cod Cliente Post changed.............");
        servicioAdmonCxcd = new ServicioAdmonCxcd();
        String codCliente = txtCodCliente.getText();
        txtCodCliente.setText(codCliente.toUpperCase());
        txtCodCliente.setBackground(Color.WHITE);
        if (servicioAdmonCxcd.clienteRegistrado(codCliente)) {
            CxcdDat cliente = servicioAdmonCxcd.getCliente(codCliente);
            //String nombreCliente = servicioAdmonCxcd.nombreCliente(codCliente);
            txtNombreCliente.setText(cliente.getNombreCliProv());
            chbRetiradoPlanta.requestFocus();
            //chbContribuyente.setSelected( (cliente.getContribuyente()!=null && !cliente.getContribuyente().isEmpty()==Boolean.TRUE?Boolean.TRUE:Boolean.FALSE) );
            chbContribuyente.setSelected(((cliente.getContribuyente() != null && !cliente.getContribuyente().isEmpty()) == Boolean.TRUE && cliente.getContribuyente().equals("S") ? Boolean.TRUE : Boolean.FALSE));
        } else {
            notificarFocus(txtCodCliente);
            JOptionPane.showMessageDialog(this, "Codigo del Cliente NO registrado.", "ATENCION:", JOptionPane.ERROR_MESSAGE);
        }
    }  // validarCliente(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void refrescarGridProductos() {
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        ServicioAdmonInv07 servAdmonInv07 = new ServicioAdmonInv07();
        String nroFactura = PanelPostearFactura.txtNroFactura.getText();
        if (nroFactura != null && !nroFactura.isEmpty()) {
            String ncf = PanelPostearFactura.txtNroCtrlFiscal.getText();
            java.util.Date utilFechaFactura = PanelPostearFactura.txtFechaFactura.getDate();
            java.sql.Date sqlFechaFactura = null;
            if (utilFechaFactura != null) {
                sqlFechaFactura = new java.sql.Date(utilFechaFactura.getTime());
            }
            List<Inv07Dat> listaFacturas = servAdmonInv07.getlistaFacturas(ncf, nroFactura, sqlFechaFactura);
            Inv07Dat inv07Dat = (Inv07Dat) listaFacturas.get(0);
            //System.out.println("EXITOOOOOO;> nro Factura=" + inv07Dat.getC7Factura() + "fecha=" + inv07Dat.getC7Fecha() + "*Paridad cambiaria=" + inv07Dat.getC7CambioMoneda() + "**");
            //showDatosDetalleProducto(inv07Dat);
            ctldrInv08.iniciarTablaProductos(inv07Dat);
            ctldrInv08.showTotales(inv07Dat);
        }
    }  // refrescarGridProductos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void actualizarFlete(String guia) {
        ServicioAdmonGuia01 servAdmGuia01 = new ServicioAdmonGuia01();
        Guias01Dat guia01 = servAdmGuia01.getGuiaDespacho(guia);
        java.util.Date fechaGuia = guia01.getC1FechaGuia();
        txtFechaGuia.setDate(fechaGuia);
        String codDestino = guia01.getC1CodDestino();
        if (fechaGuia != null && codDestino != null && guia01.getC1TipoTransporte() != null) {
            String codSector = ((guia01.getC1CodSector() == null || guia01.getC1CodSector().isEmpty()) ? "000" : guia01.getC1CodSector());
            String tipoTransporte = guia01.getC1TipoTransporte();
            ctldrPostearFactura.actualizarFlete(fechaGuia, codDestino, codSector, tipoTransporte);
            //System.out.println("**f.Guia="+fechaGuia+"***cod Dest="+codDestino+"***cod Sector="+codSector+"***tipo Transp="+tipoTransporte+"***");
            ControladorInv08 ctldrInv08 = new ControladorInv08();
            ctldrInv08.actualizarMontoFactura();
        }  // if 
    } // actualizarFlete().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void validarGuia() {
        if (txtNroGuia.getText() == null && !txtNroGuia.getText().isEmpty()) {
            libHP.notificarFocus(txtNroGuia);
        } else {
            ServicioAdmonGuia01 servAdmGuia01 = new ServicioAdmonGuia01();
            String guia = txtNroGuia.getText().trim();
            if (!servAdmGuia01.guiaRegistrada(guia)) {
                libHP.error(null, txtNroGuia, "Guia NO registrada.");
                //libHP.notificarFocus(txtNroGuia);
            } else {
                Boolean ok = Boolean.FALSE;
                Guias01Dat guia01 = servAdmGuia01.getGuiaDespacho(guia); 
                txtFechaGuia.setDate(guia01.getC1FechaGuia());
                if (txtFlete.getText() == null || txtFlete.getText().isEmpty() || txtFlete.getText().trim().length() == 0) {
                    ok = Boolean.TRUE;
                } else {
                    BigDecimal valor = new BigDecimal(libHP.convFormatoNumerico(txtFlete.getText().trim()));
                    //if ( valor.equals( BigDecimal.ZERO ) ) {
                    if (valor.compareTo(BigDecimal.ZERO) == 0) {
                        ok = Boolean.TRUE;
                    }   // if txtFlete es vacion || igual 0.  
                }
                if (ok) {
                    actualizarFlete(guia);
                }
            }  // if-else interno. 
        }  // if-else externo. 
    }  // validarGuia().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void showInfoFlete() {
        JFrame frame = new JFrame("INFORMACION TARIFA FLETE");
        //JPanel panel = new JPanel(null);
        ServicioAdmonGuia01 servAdmGuia01 = new ServicioAdmonGuia01();
        String guia = PanelPostearFactura.txtNroGuia.getText().trim();
        if (servAdmGuia01.guiaRegistrada(guia)) {
            JPanel panel = ctldrPostearFactura.panelShowInfoFlete();
            frame.setContentPane(panel);
        } else {
            JLabel msg = new JLabel("Atencion!: Guia NO registrada.", SwingConstants.CENTER);
            msg.setLocation(130, 70);
            msg.setSize(220, 40);
            msg.setFont(new Font("Dialog", 1, 14));
            msg.setForeground(Color.RED);
            frame.add(msg);  
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(Boolean.FALSE);
        frame.setSize(440, 140);
        frame.setLocation(250, 380);
        frame.setVisible(true);
    }  // showInfoFlete().  

    /**
     * ========================================================================
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * =======================================================================
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        chbClienteEspecial = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();
        btnLovClientes = new javax.swing.JButton();
        chbRetiradoPlanta = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtOrdenCompra1 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNroCtrlFiscal = new javax.swing.JTextField();
        txtNroFactura = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnEjecutarBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnCancelarBusqueda = new javax.swing.JButton();
        txtFechaFactura = new com.toedter.calendar.JDateChooser();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDirEntrega = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        chbModoConsulta = new javax.swing.JCheckBox();
        chbModoInsercion = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        chbContribuyente = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listOrdenFab = new javax.swing.JList();
        txtOrdenFab = new javax.swing.JTextField();
        btnAddOrdenFab = new javax.swing.JButton();
        btnEliminaOrdenFab = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNroGuia = new javax.swing.JTextField();
        txtFechaGuia = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        txtNroPedido = new javax.swing.JTextField();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jLabel16 = new javax.swing.JLabel();
        cbbZonaVenta = new javax.swing.JComboBox();
        cbbVendedor = new javax.swing.JComboBox();
        lblVendedor = new javax.swing.JLabel();
        lblZonaVenta = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        spnPlazo = new com.toedter.components.JSpinField();
        jLabel34 = new javax.swing.JLabel();
        cbbCondicionesPago = new javax.swing.JComboBox();
        txtPerCentDescuento = new javax.swing.JTextField();
        txtPerCentFlete = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtMonExtranjera = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel23 = new javax.swing.JLabel();
        lblPesoGuia = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaInv08 = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        txtMontoFlete = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtMontoAjuste = new javax.swing.JTextField();
        txtReconociFlete = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtDepGarantia = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JTextField();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jTextField10 = new javax.swing.JTextField();
        lblTotalFactura = new javax.swing.JLabel();
        btnAddItems = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        lblBaseImponible = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        btnRefrescar = new javax.swing.JButton();
        lblFactorIVA = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtFlete = new javax.swing.JTextField();
        btnGetFlete = new javax.swing.JButton();
        lblMontoFactura = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        lblDestinoFlete = new javax.swing.JLabel();
        lblTipoTransp = new javax.swing.JLabel();
        btnHelpFlete = new javax.swing.JButton();
        chbNC = new javax.swing.JCheckBox();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        jPanel1.setBorder(null);

        chbClienteEspecial.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        chbClienteEspecial.setText("Cliente especial");

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel7.setText("Cod Cliente        :");

        txtCodCliente.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        txtCodCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClienteActionPerformed(evt);
            }
        });
        txtCodCliente.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtCodClienteInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        btnLovClientes.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        btnLovClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/buscar16px.png"))); // NOI18N
        btnLovClientes.setText("...");
        btnLovClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLovClientesActionPerformed(evt);
            }
        });

        chbRetiradoPlanta.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        chbRetiradoPlanta.setText("Retirado en planta");

        jLabel9.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel9.setText("Orden de Compra Nro.: ");

        jTextField5.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField5.setText("jTextField5");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel4.setText("Cod Vendedor   : ");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel6.setText("Zona Vendedor : ");

        txtOrdenCompra1.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtOrdenCompra1.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jTextField7.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField7.setText("jTextField5");

        jTextField8.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jTextField8.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField8.setText("jTextField5");

        txtNombreCliente.setBackground(new java.awt.Color(162, 232, 214));
        txtNombreCliente.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N

        jLayeredPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLayeredPane2.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel2.setText("Factura Nro: ");

        jLabel5.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel5.setText("Nro. Control Fiscal: ");

        txtNroCtrlFiscal.setBackground(new java.awt.Color(87, 234, 163));
        txtNroCtrlFiscal.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        txtNroFactura.setBackground(new java.awt.Color(87, 234, 163));
        txtNroFactura.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        btnBuscar.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/buscar16px.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setToolTipText("Procesar Buscar.");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnEjecutarBuscar.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        btnEjecutarBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/carritoEntrega16px.png"))); // NOI18N
        btnEjecutarBuscar.setText("Ejecutar Buscar");
        btnEjecutarBuscar.setToolTipText("Ejecutar Busqueda segun valores del campo clave.");
        btnEjecutarBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel3.setText("Fecha factura: ");

        btnCancelarBusqueda.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        btnCancelarBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/undo16px.png"))); // NOI18N
        btnCancelarBusqueda.setText("Cancelar");
        btnCancelarBusqueda.setToolTipText("Cancelar Buscar.");
        btnCancelarBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarBusquedaActionPerformed(evt);
            }
        });

        txtFechaFactura.setDateFormatString("dd-MM-yyyy");
        txtFechaFactura.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEjecutarBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jLayeredPane2Layout.createSequentialGroup()
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNroCtrlFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(txtFechaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNroCtrlFiscal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEjecutarBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jLayeredPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(txtNroCtrlFiscal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(txtNroFactura, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(btnBuscar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(btnEjecutarBuscar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(btnCancelarBusqueda, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(txtFechaFactura, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.SystemColor.activeCaptionText, java.awt.Color.lightGray));

        txtDirEntrega.setColumns(20);
        txtDirEntrega.setRows(5);
        jScrollPane1.setViewportView(txtDirEntrega);

        jLabel11.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        jLabel11.setText("Direccion de entrega: ");

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane3.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane6.setBorder(new javax.swing.border.MatteBorder(null));

        chbModoConsulta.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        chbModoConsulta.setText("Consulta");
        chbModoConsulta.setEnabled(false);

        chbModoInsercion.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        chbModoInsercion.setText("Insercion");
        chbModoInsercion.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(122, 66, 44));
        jLabel15.setText("Modo");

        javax.swing.GroupLayout jLayeredPane6Layout = new javax.swing.GroupLayout(jLayeredPane6);
        jLayeredPane6.setLayout(jLayeredPane6Layout);
        jLayeredPane6Layout.setHorizontalGroup(
            jLayeredPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chbModoConsulta)
                    .addComponent(chbModoInsercion)
                    .addComponent(jLabel15))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jLayeredPane6Layout.setVerticalGroup(
            jLayeredPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbModoConsulta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbModoInsercion)
                .addGap(19, 19, 19))
        );
        jLayeredPane6.setLayer(chbModoConsulta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane6.setLayer(chbModoInsercion, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane6.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);

        chbContribuyente.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        chbContribuyente.setText("Contribuyente");
        chbContribuyente.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212)
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chbRetiradoPlanta)
                                .addGap(147, 147, 147)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOrdenCompra1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chbClienteEspecial)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLovClientes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chbContribuyente)))))
                .addGap(0, 70, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jLabel4)
                        .addGap(6, 6, 6)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbClienteEspecial)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLovClientes)
                    .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbContribuyente))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtOrdenCompra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(chbRetiradoPlanta)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane6)
                    .addComponent(jLayeredPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1578, 1578, 1578)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(270, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("(1).Datos del Cliente", jPanel1);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel1.setText("Orden de Fabricacion: ");

        listOrdenFab.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        listOrdenFab.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listOrdenFabValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listOrdenFab);

        txtOrdenFab.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        btnAddOrdenFab.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        btnAddOrdenFab.setText("+");
        btnAddOrdenFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOrdenFabActionPerformed(evt);
            }
        });

        btnEliminaOrdenFab.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        btnEliminaOrdenFab.setText("-");
        btnEliminaOrdenFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaOrdenFabActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel10.setText("Nro. Guia    : ");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel12.setText("Fecha Guia :");

        txtNroGuia.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtNroGuia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroGuiaActionPerformed(evt);
            }
        });

        txtFechaGuia.setDateFormatString("dd-MM-yyyy");
        txtFechaGuia.setEnabled(false);
        txtFechaGuia.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel13.setText("Nro.Pedido   : ");

        txtNroPedido.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        jLayeredPane4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLayeredPane4.setOpaque(true);

        jLabel16.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel16.setText("Zona de Venta : ");

        cbbVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbVendedorActionPerformed(evt);
            }
        });

        lblVendedor.setBackground(new java.awt.Color(115, 235, 222));
        lblVendedor.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblVendedor.setText(" ");
        lblVendedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        lblVendedor.setOpaque(true);

        lblZonaVenta.setBackground(new java.awt.Color(115, 235, 222));
        lblZonaVenta.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblZonaVenta.setText(" ");
        lblZonaVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        lblZonaVenta.setOpaque(true);

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel14.setText("Cod Vendedor : ");

        javax.swing.GroupLayout jLayeredPane4Layout = new javax.swing.GroupLayout(jLayeredPane4);
        jLayeredPane4.setLayout(jLayeredPane4Layout);
        jLayeredPane4Layout.setHorizontalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbZonaVenta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblZonaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        jLayeredPane4Layout.setVerticalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cbbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVendedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbZonaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(lblZonaVenta))
                .addGap(28, 28, 28))
        );
        jLayeredPane4.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(cbbZonaVenta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(cbbVendedor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(lblVendedor, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(lblZonaVenta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane4.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel32.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel32.setText("Condiciones de Pago : ");

        jLabel33.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel33.setText("Plazo                                   : ");

        jLabel34.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel34.setText("dias");

        cbbCondicionesPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtPerCentDescuento.setText("0,00");

        txtPerCentFlete.setText("0,00");

        jLabel35.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel35.setText("Flete   :");

        jLabel36.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel36.setText("Descuento : ");

        jLabel37.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel37.setText("%");

        jLabel38.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel38.setText("%");

        jLabel45.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel45.setText("Paridad Mon Extranjera=");

        txtMonExtranjera.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonExtranjera.setText("1,00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtOrdenFab, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddOrdenFab, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminaOrdenFab, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(txtNroGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(246, 246, 246))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel32))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbCondicionesPago, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(spnPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel34)))
                                .addGap(110, 110, 110)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel36)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel35)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPerCentDescuento)
                                    .addComponent(txtPerCentFlete, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel38)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addGap(18, 18, 18)
                                .addComponent(txtMonExtranjera, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtOrdenFab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAddOrdenFab)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnEliminaOrdenFab)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtNroGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtFechaGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(txtNroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel32)
                                    .addComponent(cbbCondicionesPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(spnPlazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPerCentDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel36))
                                    .addComponent(jLabel37))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(txtPerCentFlete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38))))
                        .addGap(2, 2, 2)
                        .addComponent(txtMonExtranjera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2220, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("(2).Condiciones de Venta", jPanel2);

        jPanel3.setForeground(java.awt.Color.red);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLayeredPane1.setOpaque(true);

        jLabel23.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel23.setText("Total kgs.: ");

        lblPesoGuia.setBackground(new java.awt.Color(190, 218, 235));
        lblPesoGuia.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblPesoGuia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPesoGuia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblPesoGuia.setOpaque(true);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(lblPesoGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(lblPesoGuia, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(jLabel23, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lblPesoGuia, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel3.add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, 40));

        tablaInv08.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tablaInv08.setModel(new ModeloTablaInv08() {});
        tablaInv08.setEnabled(false);
        tablaInv08.setRowHeight(22);
        jScrollPane3.setViewportView(tablaInv08);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 889, 120));

        jLabel25.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel25.setForeground(java.awt.Color.black);
        jLabel25.setText("Base imponible     =");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 282, 130, -1));

        txtMontoFlete.setBackground(new java.awt.Color(87, 244, 231));
        txtMontoFlete.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(txtMontoFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 110, -1));

        jLabel26.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(19, 83, 228));
        jLabel26.setText("(+) Flete              =");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        jLabel28.setText(")");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 431, -1, 0));

        jLabel30.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(19, 83, 228));
        jLabel30.setText("∑ Servicios Asociados = ");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, -1, 30));

        jLabel31.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(186, 87, 51));
        jLabel31.setText("(-) Reconoc. Flete = ");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, -1, -1));

        txtMontoAjuste.setBackground(new java.awt.Color(87, 244, 231));
        txtMontoAjuste.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(txtMontoAjuste, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 110, -1));

        txtReconociFlete.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtReconociFlete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReconociFleteActionPerformed(evt);
            }
        });
        jPanel3.add(txtReconociFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 110, -1));

        jLabel8.setText(")");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1131, 336, 21, -1));

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel19.setForeground(java.awt.Color.black);
        jLabel19.setText("SUB-TOTAL =");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, -1, -1));

        jLabel39.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(186, 87, 51));
        jLabel39.setText("(-) Descuento  = ");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, -1, 29));

        jLabel40.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel40.setForeground(java.awt.Color.black);
        jLabel40.setText("(-) Deposito en Garantia  = ");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, -1, 24));

        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });
        jPanel3.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 250, 110, -1));

        txtDepGarantia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(txtDepGarantia, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 136, -1));

        jLabel41.setText(")");
        jPanel3.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(1195, 498, -1, -1));

        lblSubTotal.setBackground(new java.awt.Color(104, 230, 141));
        lblSubTotal.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        lblSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lblSubTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(lblSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, 180, -1));

        jLayeredPane5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLayeredPane5.setOpaque(true);

        jTextField10.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(87, 17, 17));
        jTextField10.setText("TOTAL FACTURA = ");

        lblTotalFactura.setBackground(new java.awt.Color(11, 230, 243));
        lblTotalFactura.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        lblTotalFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalFactura.setText(" ");
        lblTotalFactura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblTotalFactura.setOpaque(true);

        javax.swing.GroupLayout jLayeredPane5Layout = new javax.swing.GroupLayout(jLayeredPane5);
        jLayeredPane5.setLayout(jLayeredPane5Layout);
        jLayeredPane5Layout.setHorizontalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane5Layout.setVerticalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane5Layout.createSequentialGroup()
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblTotalFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jLayeredPane5.setLayer(jTextField10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(lblTotalFactura, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel3.add(jLayeredPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, -1, -1));

        btnAddItems.setBackground(new java.awt.Color(8, 69, 171));
        btnAddItems.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btnAddItems.setForeground(java.awt.Color.white);
        btnAddItems.setText("Agregar Items");
        btnAddItems.setOpaque(true);
        btnAddItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemsActionPerformed(evt);
            }
        });
        jPanel3.add(btnAddItems, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 889, -1));

        jLabel42.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(19, 83, 228));
        jLabel42.setText("(+) I.V.A.  ");
        jPanel3.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 282, 60, -1));

        jLabel43.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel43.setText("%");
        jPanel3.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 282, 30, 20));

        jLabel44.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(19, 83, 228));
        jLabel44.setText("=");
        jPanel3.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 282, 10, -1));

        txtIva.setBackground(new java.awt.Color(224, 213, 41));
        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.add(txtIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, 120, 25));

        lblBaseImponible.setBackground(new java.awt.Color(224, 213, 41));
        lblBaseImponible.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        lblBaseImponible.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBaseImponible.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblBaseImponible.setOpaque(true);
        jPanel3.add(lblBaseImponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 132, 25));
        jPanel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 1020, 10));
        jPanel3.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 1020, 10));

        btnRefrescar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/refresh02_16px.png"))); // NOI18N
        btnRefrescar.setText("<html>Recuperar<br>valores almacenados</html>");
        btnRefrescar.setToolTipText("Refrescar datos de la grilla y monto de la factura.");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        jPanel3.add(btnRefrescar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 380, 130, 70));

        lblFactorIVA.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblFactorIVA.setForeground(java.awt.Color.darkGray);
        lblFactorIVA.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFactorIVA.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblFactorIVA.setOpaque(true);
        jPanel3.add(lblFactorIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 282, 50, 20));

        jLabel17.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel17.setForeground(java.awt.Color.red);
        jLabel17.setText("(");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 250, -1, -1));

        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel18.setForeground(java.awt.Color.red);
        jLabel18.setText(")");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 10, -1));

        jLabel20.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel20.setForeground(java.awt.Color.red);
        jLabel20.setText("(");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 250, 10, 20));

        jLabel21.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel21.setForeground(java.awt.Color.red);
        jLabel21.setText(")");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 250, 10, -1));

        jLabel27.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(19, 83, 228));
        jLabel27.setText("∑ Monto Flete   = ");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        txtFlete.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtFlete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFleteActionPerformed(evt);
            }
        });
        jPanel3.add(txtFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 100, -1));

        btnGetFlete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/refresh16px.png"))); // NOI18N
        btnGetFlete.setToolTipText("Extraer valor de la matriz de Flete.");
        btnGetFlete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetFleteActionPerformed(evt);
            }
        });
        jPanel3.add(btnGetFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 30, -1));

        lblMontoFactura.setBackground(new java.awt.Color(104, 230, 141));
        lblMontoFactura.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        lblMontoFactura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMontoFactura.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblMontoFactura.setOpaque(true);
        jPanel3.add(lblMontoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 220, 132, 25));

        jLabel29.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel29.setForeground(java.awt.Color.black);
        jLabel29.setText("  MONTO FACTURA = ");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 220, -1, -1));

        lblDestinoFlete.setFont(new java.awt.Font("Ubuntu", 1, 10)); // NOI18N
        lblDestinoFlete.setText("...");
        jPanel3.add(lblDestinoFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 50, 20));

        lblTipoTransp.setText("()");
        jPanel3.add(lblTipoTransp, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 20, 20));

        btnHelpFlete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/help-icon16px.png"))); // NOI18N
        btnHelpFlete.setToolTipText("");
        btnHelpFlete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpFleteActionPerformed(evt);
            }
        });
        jPanel3.add(btnHelpFlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 30, -1));

        chbNC.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        chbNC.setText("Productos No Conforme");
        jPanel3.add(chbNC, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 350, 200, -1));

        jTabbedPane1.addTab("(3).Detalles del Producto", jPanel3);

        add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1028, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtNroGuiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroGuiaActionPerformed
        validarGuia();
        txtNroPedido.requestFocus();
    }//GEN-LAST:event_txtNroGuiaActionPerformed

    private void btnEliminaOrdenFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaOrdenFabActionPerformed
        eliminarOrdenFab();
    }//GEN-LAST:event_btnEliminaOrdenFabActionPerformed

    private void btnAddOrdenFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOrdenFabActionPerformed
        agregarOrdenFab();
    }//GEN-LAST:event_btnAddOrdenFabActionPerformed

    private void listOrdenFabValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listOrdenFabValueChanged
        //label.setText(jListOrdenFab.getSelectedValue().toString());
    }//GEN-LAST:event_listOrdenFabValueChanged

    private void btnCancelarBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarBusquedaActionPerformed
        cancelarBuscar();
    }//GEN-LAST:event_btnCancelarBusquedaActionPerformed

    private void btnEjecutarBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarBuscarActionPerformed
        ejecutarBuscar();
    }//GEN-LAST:event_btnEjecutarBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        ctldrPostearFactura.setCamposBusqueda(true);
        ctldrPostearFactura.setBotoneraBusqueda(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLovClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLovClientesActionPerformed
        ControladorLovCliente ctldrLovCliente = new ControladorLovCliente();
        ctldrLovCliente.ejecutarDialogLovCliente();
    }//GEN-LAST:event_btnLovClientesActionPerformed

    private void txtCodClienteInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtCodClienteInputMethodTextChanged
        //System.out.println("I,Text Changed: cod Cliente Post changed.............");  ?????.
    }//GEN-LAST:event_txtCodClienteInputMethodTextChanged

    private void txtCodClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClienteActionPerformed
        validarCliente();
    }//GEN-LAST:event_txtCodClienteActionPerformed

    private void btnAddItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemsActionPerformed
        ControladorAddItemsInv08 ctldrAddItemsInv08 = new ControladorAddItemsInv08();
        ctldrAddItemsInv08.ejecutarDialogAddItemsInv08();
    }//GEN-LAST:event_btnAddItemsActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        if (PanelPostearFactura.chbModoConsulta.isSelected()) {
            refrescarGridProductos();
        }
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void txtReconociFleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReconociFleteActionPerformed
        BigDecimal reconociFlete = BigDecimal.ZERO;
        if (PanelPostearFactura.txtReconociFlete.getText() != null && !PanelPostearFactura.txtReconociFlete.getText().isEmpty()) {
            reconociFlete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtReconociFlete.getText()));
        }
        PanelPostearFactura.txtReconociFlete.setText(libHP.formatoDecimal.format(reconociFlete));
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        ctldrInv08.actualizarMontoFactura();
        txtDescuento.requestFocus();
    }//GEN-LAST:event_txtReconociFleteActionPerformed

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        BigDecimal descuento = BigDecimal.ZERO;
        if (PanelPostearFactura.txtDescuento.getText() != null && !PanelPostearFactura.txtDescuento.getText().isEmpty()) {
            descuento = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtDescuento.getText()));
        }
        PanelPostearFactura.txtDescuento.setText(libHP.formatoDecimal.format(descuento));
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        ctldrInv08.actualizarMontoFactura();
        lblTotalFactura.requestFocus();
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void cbbVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbVendedorActionPerformed
        actualizarDatosVenta();
    }//GEN-LAST:event_cbbVendedorActionPerformed

    private void txtFleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFleteActionPerformed
        BigDecimal flete = BigDecimal.ZERO;
        if (PanelPostearFactura.txtFlete.getText() != null && !PanelPostearFactura.txtFlete.getText().isEmpty()) {
            flete = new BigDecimal(libHP.convFormatoNumerico(PanelPostearFactura.txtFlete.getText()));
        }
        PanelPostearFactura.txtFlete.setText(libHP.formatoDecimal.format(flete));
        ControladorInv08 ctldrInv08 = new ControladorInv08();
        ctldrInv08.actualizarMontoFactura();
        txtReconociFlete.requestFocus();
    }//GEN-LAST:event_txtFleteActionPerformed

    private void btnGetFleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetFleteActionPerformed
        String nroGuia = PanelPostearFactura.txtNroGuia.getText().trim();
        if (nroGuia != null && !nroGuia.isEmpty() && nroGuia.length() > 0) {
            ServicioAdmonGuia01 servAdmGuia01 = new ServicioAdmonGuia01();
            if (servAdmGuia01.guiaRegistrada(nroGuia)) {
                actualizarFlete(nroGuia);
            }
        }  // if.  
    }//GEN-LAST:event_btnGetFleteActionPerformed

    private void btnHelpFleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpFleteActionPerformed
        String nroGuia = PanelPostearFactura.txtNroGuia.getText().trim();
        String flete = PanelPostearFactura.txtFlete.getText().trim();
        if ((nroGuia != null && nroGuia.length() > 0) && (flete != null && flete.length() > 0)) {
            showInfoFlete();
        }  // if.  
    }//GEN-LAST:event_btnHelpFleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAddItems;
    public javax.swing.JButton btnAddOrdenFab;
    public static javax.swing.JButton btnBuscar;
    public static javax.swing.JButton btnCancelarBusqueda;
    public static javax.swing.JButton btnEjecutarBuscar;
    public javax.swing.JButton btnEliminaOrdenFab;
    public static javax.swing.JButton btnGetFlete;
    public static javax.swing.JButton btnHelpFlete;
    public static javax.swing.JButton btnLovClientes;
    public static javax.swing.JButton btnRefrescar;
    public static javax.swing.JComboBox cbbCondicionesPago;
    public static javax.swing.JComboBox cbbVendedor;
    public static javax.swing.JComboBox cbbZonaVenta;
    public static javax.swing.JCheckBox chbClienteEspecial;
    public static javax.swing.JCheckBox chbContribuyente;
    public static javax.swing.JCheckBox chbModoConsulta;
    public static javax.swing.JCheckBox chbModoInsercion;
    public static javax.swing.JCheckBox chbNC;
    public static javax.swing.JCheckBox chbRetiradoPlanta;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    public static javax.swing.JLabel lblBaseImponible;
    public static javax.swing.JLabel lblDestinoFlete;
    public static javax.swing.JLabel lblFactorIVA;
    public static javax.swing.JLabel lblMontoFactura;
    public static javax.swing.JLabel lblPesoGuia;
    public static javax.swing.JTextField lblSubTotal;
    public static javax.swing.JLabel lblTipoTransp;
    public static javax.swing.JLabel lblTotalFactura;
    public static javax.swing.JLabel lblVendedor;
    public static javax.swing.JLabel lblZonaVenta;
    public static javax.swing.JList listOrdenFab;
    public static com.toedter.components.JSpinField spnPlazo;
    public static javax.swing.JTable tablaInv08;
    public static javax.swing.JTextField txtCodCliente;
    public static javax.swing.JTextField txtDepGarantia;
    public static javax.swing.JTextField txtDescuento;
    public static javax.swing.JTextArea txtDirEntrega;
    public static com.toedter.calendar.JDateChooser txtFechaFactura;
    public static com.toedter.calendar.JDateChooser txtFechaGuia;
    public static javax.swing.JTextField txtFlete;
    public static javax.swing.JTextField txtIva;
    public static javax.swing.JTextField txtMonExtranjera;
    public static javax.swing.JTextField txtMontoAjuste;
    public static javax.swing.JTextField txtMontoFlete;
    public static javax.swing.JTextField txtNombreCliente;
    public static javax.swing.JTextField txtNroCtrlFiscal;
    public static javax.swing.JTextField txtNroFactura;
    public static javax.swing.JTextField txtNroGuia;
    public static javax.swing.JTextField txtNroPedido;
    public static javax.swing.JTextField txtOrdenCompra1;
    public static javax.swing.JTextField txtOrdenFab;
    public static javax.swing.JTextField txtPerCentDescuento;
    public static javax.swing.JTextField txtPerCentFlete;
    public static javax.swing.JTextField txtReconociFlete;
    // End of variables declaration//GEN-END:variables

}

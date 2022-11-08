/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import bean.controlador.ControladorInv03;
import bean.controlador.ControladorInv08;
import bean.controlador.ControladorLovProductos;
import bean.entidad.Inv01Dat;
import bean.entidad.Inv04Dat;
import bean.entidad.Inv07Dat;
import bean.entidad.Inv08Dat;
import bean.entidad.Inv08DatId;
import bean.modelo.ModeloTablaInv08;
import bean.servicio.ServicioAdmonInv01;
import bean.servicio.ServicioAdmonInv02;
import bean.servicio.ServicioAdmonInv03;
import bean.servicio.ServicioAdmonInv06;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author henrypb
 */
public class PanelAddItemsInv08 extends javax.swing.JPanel {

    public JButton btnConforme;
    public JButton btnCancelar;
    String ordenFab1 = null,
            ordenFab2 = null;
    LibreriaHP libHP = new LibreriaHP();

    /**
     * Creates new form PanelAddItemsInv08
     */
    public PanelAddItemsInv08() {
        initComponents();
        iniciar();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        chbFxUnidad.setSelected(Boolean.FALSE);
        chbFxPeso.setSelected(Boolean.FALSE);
        iniciarSppBotonera();
        txtItemNro.setEditable(Boolean.FALSE);
        txtItemNro.setText(nextItemNro().toString());
        txtCodProd.setText("");
        txtTipoProd.setEditable(Boolean.FALSE);
        txtTipoProd.setText("");
        chbFxUnidad.setSelected(Boolean.FALSE);
        chbFxPeso.setSelected(Boolean.FALSE);
        /* 
         .
         .  --> iniciar campos.  
         . 
         */
    }  // inciar().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarSppBotonera() {
        btnConforme = new JButton("Conforme");
        btnConforme.setFont(new Font("sansserif", Font.BOLD, 12));
        btnConforme.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( validarCampos() ) {
                     agregarRegTabla();  
                } // if.  
            }  // actionPerformmed(). 
        } );
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("sansserif", Font.BOLD, 12));
        sppBotonera.setLeftComponent(btnConforme);
        sppBotonera.setRightComponent(btnCancelar);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean validarCampos() {
        Boolean valido = Boolean.TRUE; 
        if ( valido && ( txtCodProd.getText()==null || txtCodProd.getText().isEmpty() ) ) {
            valido = Boolean.FALSE; 
        }
        if ( valido && ( txtPesoUnidad.getText()==null || txtPesoUnidad.getText().isEmpty() ) ) {
            valido = Boolean.FALSE;           
        }
        if ( valido && ( txtPrecio.getText()==null || txtPrecio.getText().isEmpty() )) {
            valido = Boolean.FALSE;   
        }
        if ( valido && ( txtCantidad.getText()==null || txtCantidad.getText().isEmpty() )) {
            valido = Boolean.FALSE;        
        }           
        if ( !valido ) {
            JOptionPane.showMessageDialog(this, "Campos claves vacios o nulos.", "ATENCION:", JOptionPane.ERROR_MESSAGE); 
        }
        return (valido);
    }  // validarCampos(). 
    
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String facXunidad( Boolean ok) {
       if (ok) {
           return("X");
       } else {
           return("");  
       } // if-else-  
    }  // facXpeso().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String facXpeso( Boolean ok) {
       if (ok) {
           return("X");
       } else {
           return("");  
       } // if-else-  
    }  // facXpeso(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv08Dat loadDatosInv08() {
        final int PRECISION_GUIA = 6; 
        Inv08Dat inv08Dat = null; 
        String tipoProd = PanelAddItemsInv08.txtTipoProd.getText();
        String descripcion = PanelAddItemsInv08.lblDescripcion.getText();  
        String facXpeso  = facXpeso(PanelAddItemsInv08.chbFxPeso.isSelected());
        String facXunidad = facXunidad(PanelAddItemsInv08.chbFxUnidad.isSelected());
        String guia = PanelAddItemsInv08.txtGuia.getText().substring(0, PRECISION_GUIA);
        BigDecimal peso = (new BigDecimal(libHP.convFormatoNumerico(PanelAddItemsInv08.txtPesoUnidad.getText())));
        BigDecimal pesoGuia = BigDecimal.ZERO; 
        if ( peso!=null ) {
             pesoGuia = peso;  
        }
        Integer cantidad = (Integer.parseInt(PanelAddItemsInv08.txtCantidad.getText()));
        if ( peso!=null && cantidad!=null ) {
             pesoGuia = pesoGuia.multiply(new BigDecimal(cantidad)); 
        }
        BigDecimal precio = (new BigDecimal(libHP.convFormatoNumerico(PanelAddItemsInv08.txtPrecio.getText())));
        BigDecimal flete = BigDecimal.ZERO; 
        if ( PanelAddItemsInv08.txtMontoFlete.getText()!=null && !PanelAddItemsInv08.txtMontoFlete.getText().isEmpty() ) {
             flete = (new BigDecimal(libHP.convFormatoNumerico(PanelAddItemsInv08.txtMontoFlete.getText())) );
        }
        BigDecimal otrosCargos = BigDecimal.ZERO; 
        if ( PanelAddItemsInv08.txtOtrosCargos.getText()!=null && !PanelAddItemsInv08.txtOtrosCargos.getText().isEmpty()) {
             otrosCargos = (new BigDecimal(libHP.convFormatoNumerico(PanelAddItemsInv08.txtOtrosCargos.getText())));
        }
        Byte   itemNo = Byte.parseByte(PanelAddItemsInv08.txtItemNro.getText()); 
        String factura = PanelPostearFactura.txtNroFactura.getText(); 
        String codProd = PanelAddItemsInv08.txtCodProd.getText();  
        //inv08Dat.setId(new Inv08DatId(factura,codProd,itemNo));
        inv08Dat = new Inv08Dat(new Inv08DatId(factura,codProd,itemNo), new Inv07Dat(factura), tipoProd, descripcion,guia,peso,cantidad,precio,flete,facXunidad,facXpeso,pesoGuia,otrosCargos ); 
        return (inv08Dat);
    }  // loadDatosInv08(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void agregarRegTabla() {
       ControladorInv08 ctldrInv08 = new ControladorInv08();  
       ModeloTablaInv08 modelo = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
       Inv08Dat inv08Dat = new Inv08Dat();  
       inv08Dat = loadDatosInv08();
       modelo.listaDetalleProductos.add(ctldrInv08.getFila(inv08Dat)); 
       modelo.fireTableDataChanged();
       ctldrInv08.actualizarTotales("+",inv08Dat); 
    }  // agregarRegTabla(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Integer nextItemNro() {
        ModeloTablaInv08 m = (ModeloTablaInv08) PanelPostearFactura.tablaInv08.getModel();
        Byte ultimo = 0;
        Byte nroItem = 0;
        //System.out.println("****filas="+m.getRowCount()+"*****");
        for (Integer i = 0; i < m.getRowCount(); i++) {
            //System.out.println("Valor del="+m.getValueAt(i,0));
            nroItem = (Byte) m.getValueAt(i, 0);
            if (nroItem > ultimo) {
                ultimo = nroItem;
            }
        }
        return (ultimo + 1);
    }  // nextItemNro();  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void validarProducto() {
        ListModel dlm = PanelPostearFactura.listOrdenFab.getModel();
        if (dlm.getSize() > 0) {
            //System.out.println("Lista de Ord. Fab contiene DATOS,.........."); 
            if (dlm.getSize() >= 1) {
                ordenFab1 = (String) dlm.getElementAt(0);
            }
            if (dlm.getSize() >= 2) {
                ordenFab2 = (String) dlm.getElementAt(1);
            }
        }
        if ( ordenFab1 == null && ordenFab2 == null ) {  // Prod Standard. 
            String codProd = PanelAddItemsInv08.txtCodProd.getText();
            ServicioAdmonInv01 servAdmonInv01 = new ServicioAdmonInv01();
            if (servAdmonInv01.prodRegistrado(codProd)) {
                //System.out.println("EXITO PROD STANDARD registrado.");
                Inv01Dat inv01Dat = servAdmonInv01.getDatosProducto(codProd);
                setDatosProductoStandard(inv01Dat);
            } else {
                //System.out.println("NO EXITO PROD STANDARD NO registrado.");
                JOptionPane.showMessageDialog(this, "Codigo del Producto NO registrado.", "ATENCION:", JOptionPane.ERROR_MESSAGE);
            } // if-else. 
        } else { // validar Prod Especial.   
           String codProd = PanelAddItemsInv08.txtCodProd.getText();  
           ServicioAdmonInv03 servAdmonInv03 = new ServicioAdmonInv03(); 
           if ( servAdmonInv03.prodRegistrado(codProd, ordenFab1) ) {
                Inv04Dat inv04Dat = servAdmonInv03.getDatosProducto(codProd, ordenFab1); 
                setDatosProdEspecial(inv04Dat); 
           } else {
              JOptionPane.showMessageDialog(this, "Codigo del Producto NO registrado.(revisar O.Fab.)", "ATENCION:", JOptionPane.ERROR_MESSAGE);
           }
        }  // 
    }  // validarProducto(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void setDatosProductoStandard(Inv01Dat inv01Dat) {
        String tipoProd = inv01Dat.getC1Tipo();
        PanelAddItemsInv08.txtTipoProd.setText(tipoProd);
        ServicioAdmonInv06 servAdmonInv06 = new ServicioAdmonInv06();
        if (!servAdmonInv06.regEncontrado(tipoProd)) {
            JOptionPane.showMessageDialog(this, "Tipo de Producto NO registrado. Revisar por inconsistencia de información.", "ATENCION:", JOptionPane.ERROR_MESSAGE);
        } else {
            chbFxUnidad.setSelected(servAdmonInv06.facXunidad(tipoProd));
            chbFxPeso.setSelected(servAdmonInv06.facXpeso(tipoProd));
        }  // if-else.
        procesarPrecioProdStandard(inv01Dat);
        lblDescripcion.setText(inv01Dat.getC1Descripcion());
        String nroGuia = PanelPostearFactura.txtNroGuia.getText();  
        txtGuia.setText(nroGuia);
        txtPesoUnidad.setText(libHP.formatoDecimal.format(inv01Dat.getC1Peso()));
        txtCantidad.requestFocus();
        BigDecimal pesoGuia = BigDecimal.ZERO;
        if ( inv01Dat.getC1Peso()!=null && inv01Dat.getC1Unidades()!=null ) {
             pesoGuia = inv01Dat.getC1Peso().multiply(new BigDecimal(inv01Dat.getC1Unidades()));
        }
        lblPesoGuia.setText(libHP.formatoDecimal.format(pesoGuia));
        lblTotal.setText(libHP.formatoDecimal.format(calcularTotal()));
    }  // setDatosProducto(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void procesarPrecioProdStandard(Inv01Dat inv01Dat) {
        String codProd = inv01Dat.getC1Codigo();
        ServicioAdmonInv02 servAdmonInv02 = new ServicioAdmonInv02();
        if (!servAdmonInv02.precioRegistrado(codProd)) {
            JOptionPane.showMessageDialog(this, "Precio del Producto NO registrado.", "ATENCION:", JOptionPane.ERROR_MESSAGE);
        } else {
            if (chbFxUnidad.isSelected()) {
                txtPrecio.setText(libHP.formatoDecimal.format(servAdmonInv02.precioUnidad(codProd)));
            }
            if (chbFxPeso.isSelected()) {
                txtPrecio.setText(libHP.formatoDecimal.format(servAdmonInv02.precioKgs(codProd)));
            }
        }
    }  // procesarPrecioProdStandard().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void setDatosProdEspecial(Inv04Dat inv04Dat) {
        ControladorInv03 ctldrInv03 = new ControladorInv03(); 
        //System.out.println("***cod Prod="+inv04Dat.getId().getC4Codigo()+"***tipo Prod="+inv04Dat.getC4TipoProd()+"****."); 
        PanelAddItemsInv08.txtTipoProd.setText(inv04Dat.getC4TipoProd());
        PanelAddItemsInv08.chbFxPeso.setSelected(Boolean.TRUE);
        PanelAddItemsInv08.chbFxUnidad.setSelected(Boolean.TRUE);
        PanelAddItemsInv08.lblDescripcion.setText(ctldrInv03.descripProdEspecial(inv04Dat));
        String nroGuia = PanelPostearFactura.txtNroGuia.getText();  
        txtGuia.setText(nroGuia);
        txtPesoUnidad.setText(libHP.formatoDecimal.format(inv04Dat.getC4PesoItem()));
        txtCantidad.setText(inv04Dat.getC4Lam().toString());
        txtCantidad.requestFocus();
        txtPrecio.setText(libHP.formatoDecimal.format(inv04Dat.getC4Bsxton()));
        lblPesoGuia.setText(libHP.formatoDecimal.format(calcularPesoGuia()));
        lblTotal.setText(libHP.formatoDecimal.format(calcularTotal()));
    }  // setDatosProdEspecial(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private BigDecimal calcularPesoGuia() {
        BigDecimal peso = BigDecimal.ZERO; 
        if (txtPesoUnidad.getText()!=null && !txtPesoUnidad.getText().isEmpty()) {
            String pesoS = txtPesoUnidad.getText(); 
            peso = new BigDecimal(libHP.convFormatoNumerico(pesoS)); 
        }
        Short cantidad = 0; 
        if ( txtCantidad.getText()!=null && !txtCantidad.getText().isEmpty() ) {
            cantidad = Short.parseShort(txtCantidad.getText()); 
        }
        return (peso.multiply(new BigDecimal(cantidad)));
    }  // calcularPesoGuia(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO; 
        if (txtPrecio.getText()!=null && !txtPrecio.getText().isEmpty()) {
            total = new BigDecimal(libHP.convFormatoNumerico(txtPrecio.getText()));             
        }
        if ( chbFxUnidad.isSelected() && txtCantidad!=null && !txtCantidad.getText().isEmpty() ) {
             Short cantidad = Short.parseShort(txtCantidad.getText());  
             total = total.multiply(new BigDecimal(cantidad)); 
        }
        if ( chbFxPeso.isSelected() && txtPesoUnidad.getText()!=null && !txtPesoUnidad.getText().isEmpty() ) {
             BigDecimal peso = new BigDecimal(libHP.convFormatoNumerico(txtPesoUnidad.getText())); 
             total = total.multiply(peso); 
        }
        if (txtMontoFlete.getText()!=null && !txtMontoFlete.getText().isEmpty()) {
             total = total.add(new BigDecimal(libHP.convFormatoNumerico(txtMontoFlete.getText()))); 
        }
        if (txtOtrosCargos.getText()!=null && !txtOtrosCargos.getText().isEmpty() ) {
            total = total.add(new BigDecimal(libHP.convFormatoNumerico(txtOtrosCargos.getText()))); 
        }
        return (total);
    }  // calcularTotal(). 
    
    /**
     * ========================================================================
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        sppBotonera = new javax.swing.JSplitPane();
        txtItemNro = new javax.swing.JTextField();
        txtCodProd = new javax.swing.JTextField();
        btnLovProductos = new javax.swing.JButton();
        txtTipoProd = new javax.swing.JTextField();
        txtGuia = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPesoUnidad = new javax.swing.JTextField();
        txtMontoFlete = new javax.swing.JTextField();
        txtOtrosCargos = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        chbFxUnidad = new javax.swing.JCheckBox();
        chbFxPeso = new javax.swing.JCheckBox();
        lblTotal = new javax.swing.JLabel();
        lblPesoGuia = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();

        jLabel1.setBackground(new java.awt.Color(235, 120, 96));
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel1.setText("                                                        Agregar Items a la Factura");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel2.setText("Item Nro.: ");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel4.setText("Cod. Produto : ");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel5.setText("Tipo: ");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel6.setText("Guia Nro.: ");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel7.setText("Peso :");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel8.setText("Cantidad :");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel9.setText("Precio:");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel10.setText("Monto Flete :");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel11.setText("Cargo x Servicios :");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel12.setText("Peso Guia:");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel13.setText("Total:");

        txtItemNro.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtItemNro.setText(" ");

        txtCodProd.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtCodProd.setText(" ");
        txtCodProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProdActionPerformed(evt);
            }
        });

        btnLovProductos.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        btnLovProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/buscar16px.png"))); // NOI18N
        btnLovProductos.setText("...");
        btnLovProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLovProductosActionPerformed(evt);
            }
        });

        txtTipoProd.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtTipoProd.setText(" ");

        txtGuia.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtGuia.setText(" ");

        txtPrecio.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        txtCantidad.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        txtPesoUnidad.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtPesoUnidad.setText(" ");
        txtPesoUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoUnidadActionPerformed(evt);
            }
        });

        txtMontoFlete.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtMontoFlete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoFleteActionPerformed(evt);
            }
        });

        txtOtrosCargos.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        txtOtrosCargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOtrosCargosActionPerformed(evt);
            }
        });

        chbFxUnidad.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        chbFxUnidad.setText("F x Unidad");
        chbFxUnidad.setEnabled(false);
        chbFxUnidad.setOpaque(true);

        chbFxPeso.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        chbFxPeso.setText("F x Peso");
        chbFxPeso.setEnabled(false);
        chbFxPeso.setOpaque(true);

        lblTotal.setBackground(new java.awt.Color(11, 238, 215));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText(" ");
        lblTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblTotal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblTotal.setOpaque(true);

        lblPesoGuia.setBackground(java.awt.Color.orange);
        lblPesoGuia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPesoGuia.setText(" ");
        lblPesoGuia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblPesoGuia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblPesoGuia.setOpaque(true);

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel14.setText("Descripcion: ");

        lblDescripcion.setBackground(new java.awt.Color(26, 236, 174));
        lblDescripcion.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        lblDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblDescripcion.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sppBotonera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel13)
                        .addGap(68, 68, 68)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLovProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMontoFlete, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOtrosCargos, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPesoGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPesoUnidad, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtGuia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)))
                        .addContainerGap(211, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chbFxUnidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chbFxPeso)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(txtItemNro, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtItemNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLovProductos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTipoProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbFxUnidad)
                    .addComponent(chbFxPeso))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGuia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPesoUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMontoFlete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtOtrosCargos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPesoGuia, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(sppBotonera, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLovProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLovProductosActionPerformed
        ControladorLovProductos ctldrLovProd = new ControladorLovProductos();
        ctldrLovProd.ejecutarDialogProductos();
    }//GEN-LAST:event_btnLovProductosActionPerformed

    private void txtCodProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProdActionPerformed
        validarProducto();
    }//GEN-LAST:event_txtCodProdActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        lblPesoGuia.setText(libHP.formatoDecimal.format(calcularPesoGuia())); 
        lblTotal.setText(libHP.formatoDecimal.format(calcularTotal()) );
        txtMontoFlete.requestFocus();
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPesoUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoUnidadActionPerformed
        lblTotal.setText(libHP.formatoDecimal.format(calcularPesoGuia())); 
        txtMontoFlete.requestFocus();
    }//GEN-LAST:event_txtPesoUnidadActionPerformed

    private void txtOtrosCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOtrosCargosActionPerformed
        lblTotal.setText(libHP.formatoDecimal.format(calcularTotal()));
        if (txtOtrosCargos.getText()!=null && !txtOtrosCargos.getText().isEmpty() ) {
           txtOtrosCargos.setText(libHP.formatoDecimal.format(new BigDecimal(libHP.convFormatoNumerico(txtOtrosCargos.getText()))));
        }
        btnConforme.requestFocus();
    }//GEN-LAST:event_txtOtrosCargosActionPerformed

    private void txtMontoFleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoFleteActionPerformed
        lblTotal.setText(libHP.formatoDecimal.format(calcularTotal()));
        if ( txtMontoFlete.getText()!=null && !txtMontoFlete.getText().isEmpty() ) {
           txtMontoFlete.setText(libHP.formatoDecimal.format(new BigDecimal(libHP.convFormatoNumerico(txtMontoFlete.getText()))));
        }
        txtOtrosCargos.requestFocus();
    }//GEN-LAST:event_txtMontoFleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnLovProductos;
    public static javax.swing.JCheckBox chbFxPeso;
    public static javax.swing.JCheckBox chbFxUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lblDescripcion;
    public static javax.swing.JLabel lblPesoGuia;
    public static javax.swing.JLabel lblTotal;
    public static javax.swing.JSplitPane sppBotonera;
    public static javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCodProd;
    public static javax.swing.JTextField txtGuia;
    public static javax.swing.JTextField txtItemNro;
    public static javax.swing.JTextField txtMontoFlete;
    public static javax.swing.JTextField txtOtrosCargos;
    public static javax.swing.JTextField txtPesoUnidad;
    public static javax.swing.JTextField txtPrecio;
    public static javax.swing.JTextField txtTipoProd;
    // End of variables declaration//GEN-END:variables

}

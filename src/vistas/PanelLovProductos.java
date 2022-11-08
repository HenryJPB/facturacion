/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import bean.controlador.ControladorInv03;
import bean.entidad.Inv01Dat;
import bean.entidad.Inv03Dat;
import bean.entidad.Inv04Dat;
import bean.servicio.ServicioAdmonInv01;
import bean.servicio.ServicioAdmonInv03;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author henrypb
 */
public class PanelLovProductos extends javax.swing.JPanel {

    public JButton btnConforme;
    public JButton btnCancelar;
    DefaultTableModel modeloTablaProductos;
    String ordenFab1 = null;
    String ordenFab2 = null;

    /**
     * Creates new form PanelLovProductos
     */
    public PanelLovProductos() {
        initComponents();
        iniciar();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        btnConforme = new JButton("Conforme");
        btnConforme.setFont(new Font("sansserif", Font.BOLD, 12));
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("sansserif", Font.BOLD, 12));
        sppBotonera.setLeftComponent(btnConforme);
        sppBotonera.setRightComponent(btnCancelar);
        ListModel dlm = PanelPostearFactura.listOrdenFab.getModel();
        if (dlm.getSize() > 0) {
            //System.out.println("Lista de Ord. Fab contiene DATOS,.........."); 
            chbProdEspecial.setSelected(Boolean.TRUE);
            if (dlm.getSize() >= 1) {
                ordenFab1 = (String) dlm.getElementAt(0);
            }
            if (dlm.getSize() >= 2) {
                ordenFab2 = (String) dlm.getElementAt(1);
            }
        } else {
            //System.out.println("Lista de Ord. Fab NO contiene DATOS,.........."); 
            chbProdEspecial.setSelected(Boolean.FALSE);
        }
        iniciarTabla();
    }  // iniciar().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarTabla() {
        String[] columnNames = {"Codigo", "Tipo", "Descripcion"};
        Object[][] datos = {};
        modeloTablaProductos = new DefaultTableModel(datos, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        JTableHeader header = this.tablaProductos.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.yellow);
        header.setFont(new Font("Monospaced", Font.BOLD, 13));
        //datos = cargarDatosTablaProductos();   // Intento = ????.  
        actualizarTablaProductos();
        this.tablaProductos.setModel(modeloTablaProductos);
        this.tablaProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnSizes(this.tablaProductos, modeloTablaProductos);
        ordenarTabla();
    }  // iniciarTablaFleteDestino(); 

    //--------------------------------------------------------------------------
    // (Brqto (05-09-2014). Funciona??->SUJETO a REVISION.   
    //--------------------------------------------------------------------------
    private Object[][] cargarDatosTablaProductos() {
        Object[][] lista = null;  // new ArrayList<Object[]>(); 
        //final int noColunnas = 3;
        //Object dato[]; // = new Object[noColunnas];   // Donde n=Nro de Objetos columnas
        //Object dato[]; // 
        //String nroOrden = PanelGuiaDespacho.txtOrden1.getText();
        if (!chbProdEspecial.isSelected()) {   // Prod Estandard.  
            ServicioAdmonInv01 servicioAdmonInv01 = new ServicioAdmonInv01();
            Integer cont = 0;
            for (Inv01Dat producto : servicioAdmonInv01.getProdStandard("","")) {
                //lista = (Object[][]) new Object[cont]{producto.getC1Codigo(), producto.getC1Tipo(), producto.getC1Descripcion()};
                lista = new Object[][]{{cont}, {producto.getC1Codigo(), producto.getC1Tipo(), producto.getC1Descripcion()}};
                cont++;
                //modeloTablaProductos.addRow(dato);
            }  // for.  
        } else {    // Prod Especial. 
            //ServicioAdmonInv04 servicioAdmonInv04 = new ServicioAdmonInv04();
            //for ( Inv04Dat producto : servicioAdmonInv04.getProdEspecial(ordenFab1, ordenFab2) ) {
            //dato = new Object[]{producto.getId().getC4Codigo(), producto.getC4TipoProd(), ( producto.getC4Diaml()+"x"+producto.getC4Diamt() ) )};
            //dato = null; 
            //modeloTablaProductos.addRow(dato);
            //}  // for.
        }      // if-else- 
        return (lista);
    }  // cargarDatosTablaProductos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void actualizarTablaProductos() {
        Object dato[]; // 
        Inv03Dat inv03Dat;
        if (!chbProdEspecial.isSelected()) {   // Prod Estandard.  
            String codigoLike = txtCodProd.getText(); 
            String nombreLike = txtDescripcion.getText(); 
            ServicioAdmonInv01 servicioAdmonInv01 = new ServicioAdmonInv01();
            for (Inv01Dat producto : servicioAdmonInv01.getProdStandard(codigoLike,nombreLike) ) {
                //lista = (Object[][]) new Object[cont]{producto.getC1Codigo(), producto.getC1Tipo(), producto.getC1Descripcion()};
                dato = new Object[]{producto.getC1Codigo(), producto.getC1Tipo(), producto.getC1Descripcion()};
                modeloTablaProductos.addRow(dato);
            }  // for.  
        } else {    // Prod Especiales. ** Vaciar una orden Fab1 ***
            ServicioAdmonInv03 servAdmonInv03 = new ServicioAdmonInv03();
            ControladorInv03 ctldrInv03 = new ControladorInv03(); 
            if (servAdmonInv03.ordenRegistrada(ordenFab1)) {
                inv03Dat = servAdmonInv03.getOrdenFab(ordenFab1);
                //System.out.println("*************Orden de Fab=" + inv03Dat.getC3Orden() + "********");
                Set<Inv04Dat> inv04Dats = inv03Dat.getInv04Dats(); 
                for ( Inv04Dat inv04Dat : inv04Dats ) {
                    dato = new Object[]{inv04Dat.getId().getC4Codigo(),inv04Dat.getC4TipoProd(),ctldrInv03.descripProdEspecial(inv04Dat)}; 
                    modeloTablaProductos.addRow(dato);
                }  // for.  
            }  // Vaciar una orden Fab2 **
            if (servAdmonInv03.ordenRegistrada(ordenFab2)) {
                inv03Dat = servAdmonInv03.getOrdenFab(ordenFab2);
                //System.out.println("*************Orden de Fab=" + inv03Dat.getC3Orden() + "********");
                Set<Inv04Dat> inv04Dats = inv03Dat.getInv04Dats(); 
                for ( Inv04Dat inv04Dat : inv04Dats ) {
                    dato = new Object[]{inv04Dat.getId().getC4Codigo(),inv04Dat.getC4TipoProd(),ctldrInv03.descripProdEspecial(inv04Dat)}; 
                    modeloTablaProductos.addRow(dato);
                }  // for.  
            }
            //for ( Inv04Dat producto : servicioAdmonInv04.getProdEspecial(ordenFab1, ordenFab2) ) {
            //    descripcion = producto.getC4TipoProd()+" "+producto.getC4Diaml()+" x "+producto.getC4Diamt()+""; 
            //    dato = new Object[]{producto.getCodProducto(),producto.getC4TipoProd() ,descripcion };
            //    modeloTablaProductos.addRow(dato);
            //}  // for.  
        }      // if-else- 
    }  // actualizarDatosTablaProductos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String descripProdEspecialOLD(Inv04Dat inv04Dat) {
        String cadena = null;
        LibreriaHP libHP = new LibreriaHP(); 
        cadena = libHP.formatoDecimal.format(inv04Dat.getC4Taml())+"/"+libHP.formatoDecimal.format(inv04Dat.getC4Tamt())+" "; 
        cadena = cadena+libHP.formatoDecimal.format(inv04Dat.getC4Sepl())+" x" + libHP.formatoDecimal.format(inv04Dat.getC4Sept())+" "; 
        cadena = cadena+libHP.formatoDecimal.format(inv04Dat.getC4Diaml())+"/"+libHP.formatoDecimal.format(inv04Dat.getC4Diamt()); 
        /*
                         "to_char(C4_DIAML,'99.99')||'/'||to_char(C4_DIAMT,'99.99')||' ') as descripcion," +  
                         "C4_TAML as tamL," + 
                         "C4_TAMT as tamT," +  
                         "C4_SEPL as sepL," +  
                         "C4_SEPT as sepT," +  
                         "C4_DIAML as diamL," +  
                         "C4_DIAMT as diamT," + 
                         "C4_NUML as numL," +  
                         "C4_NUMT as numT," + 
                         "C4_SOBRL1 as sobrL1," +  
                         "C4_SOBRL2 as sobrL2," +  
                         "C4_SOBRT1 as sobrT1," +  
                         "C4_SOBRT2 as sobrT2," +  
                         "C4_PESO_ITEM as pesoItem," +  
                         "C4_LAM as lam," +  
                         "C4_BSXTON as bsTon " +  
                         "from   INV04_DAT " + 
                         "where  C4_ORDEN = '"+nroOrdenFab+"' " + 
                         "and    C4_CODIGO 
        */  
        return(cadena); 
    }  // descripProdEspecial(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void setColumnSizes(JTable tabla, DefaultTableModel modelo) {
        //ModeloTablaUnidadTransporte model = (ModeloTablaUnidadTransporte) tabla.getModel();
        TableModel model = tabla.getModel();
        TableColumn column;
        Component comp;
        int headerWidth;
        int cellWidth;
        //Object[] longValues = model.longValues;
        Object[] longValues = {"1234567890123", "1234", "1234567890-1234567890-1234567890-1234567890-123"};
        TableCellRenderer headerRenderer = tabla.getTableHeader().getDefaultRenderer();
        for (int i = 0; i < modelo.getColumnCount(); i++) {   // (**)  
            column = tabla.getColumnModel().getColumn(i);
            comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
            comp = tabla.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(tabla, longValues[i], false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    } //  setColumnSizes().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ordenarTabla() {
        TableModel modeloTablaOrdenada = this.tablaProductos.getModel();
        RowSorter<TableModel> tablaOrdenada = new TableRowSorter<TableModel>(modeloTablaOrdenada);
        this.tablaProductos.setRowSorter(tablaOrdenada);
    }

    //--------------------------------------------------------------------------
    //-------------------------------------------------------------------------- 
    private void refrescarDatosProducto() {
        iniciarTabla(); 
    }  // refrescarDatosProducto(); 
    
    /**
     * ========================================================================
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * =========================================================================
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        chbProdEspecial = new javax.swing.JCheckBox();
        btnRefrescar = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        txtCodProd = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        sppBotonera = new javax.swing.JSplitPane();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(27, 154, 247));
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/logoDesica16px.png"))); // NOI18N
        jLabel1.setText("                  L.O.V. PRODUCTOS");
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 55));

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel2.setText("Cod. Prod     :");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel3.setText("Decripcion   :");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        chbProdEspecial.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        chbProdEspecial.setText("Prod. Especial (MP)");
        chbProdEspecial.setEnabled(false);
        add(chbProdEspecial, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, -1, -1));

        btnRefrescar.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/filter-icon16px.png"))); // NOI18N
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        add(btnRefrescar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, -1, -1));

        txtDescripcion.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 85, 260, -1));

        txtCodProd.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        add(txtCodProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 110, -1));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaProductos);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 550, 210));
        add(sppBotonera, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        refrescarDatosProducto(); 
    }//GEN-LAST:event_btnRefrescarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnRefrescar;
    public static javax.swing.JCheckBox chbProdEspecial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JSplitPane sppBotonera;
    public javax.swing.JTable tablaProductos;
    public static javax.swing.JTextField txtCodProd;
    public static javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.despacho;

import bean.servicio.ServicioAdmonGuia01;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
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
public class PanelLovGuiasFacturar extends javax.swing.JPanel {

    public JButton btnConforme;
    public JButton btnCancelar;
    DefaultTableModel modeloTabla;

    /**
     * Creates new form PanelLovGuiasDespachar
     */
    public PanelLovGuiasFacturar() {
        initComponents();
        iniciar();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        iniciarSppBotonera();
        iniciarTabla();
    }  // iniciar(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarSppBotonera() {
        btnConforme = new JButton("Seleccionar");
        btnCancelar = new JButton("Cancelar");
        sppBotonera.setLeftComponent(btnConforme);
        sppBotonera.setRightComponent(btnCancelar);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarTabla() {
        String[] columnNames = {"Guia", "Fecha", "Cliente"};
        Object[][] datos = {};
        modeloTabla = new DefaultTableModel(datos, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells are false
                return false;
            }
        };
        JTableHeader header = tablaGuiasFacturar.getTableHeader();
        header.setBackground(Color.black);
        header.setForeground(Color.yellow);
        header.setFont(new Font("Monospaced", Font.BOLD, 13));
        //datos = cargarDatosTablaProductos();   // Intento = ????.  
        //modeloTabla = actualizarTablaProductos0();
        actualizarTablaProductos();
        tablaGuiasFacturar.setModel(modeloTabla);
        tablaGuiasFacturar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setColumnSizes(tablaGuiasFacturar, modeloTabla);
        ordenarTabla();
    }  // iniciarTablaFleteDestino(); 

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
        Object[] longValues = {"123456", "1234567890", "1234567890-1234567890-1234567890-1234567890-123"};
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
    // ** No funcionó. (Brqto: 07/10/2014). 
    //--------------------------------------------------------------------------
    private DefaultTableModel actualizarTablaProductos0() {
        Object dato[]; // 
        //Guias01Dat guia01;
        DefaultTableModel modelo = new DefaultTableModel();
        ServicioAdmonGuia01 servicioAdmonGuia01 = new ServicioAdmonGuia01();
        for (String[] guia01 : servicioAdmonGuia01.getGuiasPorFacturar()) {
            //lista = (Object[][]) new Object[cont]{producto.getC1Codigo(), producto.getC1Tipo(), producto.getC1Descripcion()};
            //dato = new Object[]{guia01.getC1Guia(), guia01.getC1FechaGuia(), guia01.getNombreCliente()};
            //modelo.addRow(dato);
        }  // for. 
        return (modelo);
    } // actualizarDatosTablaProductos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void actualizarTablaProductos() {
        Object dato[]; // 
        //Guias01Dat guia01;
        //DefaultTableModel modelo = new DefaultTableModel();
        ServicioAdmonGuia01 servicioAdmonGuia01 = new ServicioAdmonGuia01();
        for (String[] guia01 : servicioAdmonGuia01.getGuiasPorFacturar()) {
            //lista = (Object[][]) new Object[cont]{producto.getC1Codigo(), producto.getC1Tipo(), producto.getC1Descripcion()};
            dato = new Object[]{guia01[0], guia01[1],guia01[2]};
            modeloTabla.addRow(dato);
        }  // for. 
    } // actualizarDatosTablaProductos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ordenarTabla() {
        TableModel modeloTablaOrdenada = tablaGuiasFacturar.getModel();
        RowSorter<TableModel> tablaOrdenada = new TableRowSorter<TableModel>(modeloTablaOrdenada);
        tablaGuiasFacturar.setRowSorter(tablaOrdenada);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        sppBotonera = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGuiasFacturar = new javax.swing.JTable();

        jLabel1.setBackground(new java.awt.Color(27, 154, 247));
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/logoDesica16px.png"))); // NOI18N
        jLabel1.setText(" Guias x Facturar");
        jLabel1.setOpaque(true);

        tablaGuiasFacturar.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaGuiasFacturar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sppBotonera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sppBotonera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JSplitPane sppBotonera;
    public javax.swing.JTable tablaGuiasFacturar;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.cliente;

import bean.entidad.CxcdDat;
import bean.utilitario.hibernate.HibernateUtil;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class PanelLovCliente extends javax.swing.JPanel {

    CxcdDat cxcdDat;
    public JButton btnConforme;
    public JButton btnCancelar;
    DefaultTableModel modeloTablaClientes;
    //private static String QUERY_CLIENTES_ACTIVO = "from CxcdDat where subStr( codigo,1,1 ) = 'C' and tipoDeCliente ='A' and codigo!='C-9999'  order by nombreCliProv asc";

    /**
     * Creates new form PanelLovCliente
     */
    public PanelLovCliente() {
        initComponents();
        iniciarSppBotonera();
        iniciarTablaClientes(); 
        //ajustarColumnSizes(this.tablaClientes); 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public enum columnas {
        COD_CLIENTE, NOMBRE_CLIENTE
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarSppBotonera() {
        btnConforme = new JButton("Conforme");
        btnConforme.setFont(new Font("sansserif", Font.BOLD, 12));
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("sansserif", Font.BOLD, 12));
        sppBotonera.setLeftComponent(btnConforme);
        sppBotonera.setRightComponent(btnCancelar);
    }  // iniciarSppBotonera().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Cliente> runQueryBasedOnClientesActivo() {
        String codClienteLike = "";
        if ( this.txtCodCliente.getText()!=null && !this.txtCodCliente.getText().isEmpty() ) {
            codClienteLike = this.txtCodCliente.getText().toUpperCase();  
        } 
        String nombreClienteLike = ""; 
        if ( this.txtNombreCliente.getText()!=null && !this.txtNombreCliente.getText().isEmpty() ) {
            nombreClienteLike = this.txtNombreCliente.getText().toUpperCase();  
        }
        String QUERY_CLIENTES_ACTIVO = "from CxcdDat where subStr( codigo,1,1 ) = 'C' and tipoDeCliente ='A' and codigo!='C-9999' and codigo like '"+codClienteLike + "%' and nombreCliProv like '" + nombreClienteLike +  "%' order by nombreCliProv asc";
        return ( executeHQLQuery(QUERY_CLIENTES_ACTIVO) );
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Cliente> executeHQLQuery(String hql) {
        List<Cliente> resultList = new ArrayList<Cliente>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            resultList = getClientesActivo(q.list());
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (resultList);
    } // executeHQLQuery(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Cliente> getClientesActivo(List lista) {
        Cliente cliente;
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        if (lista == null || lista.isEmpty()) {
            System.err.println("***ATENCION: Lista de datos VACIA.****");
        } else {
            //limpiar();
            for (Object o : lista) {
                //Inv01Dat  registro = (Inv01Dat) o;
                cxcdDat = (CxcdDat) o;
                cliente = new Cliente(cxcdDat.getCodigo(), cxcdDat.getNombreCliProv());
                //System.out.println("***cliente1="+cxcdDat.getNombreCliProv()+"****");
                listaCliente.add(cliente);
            }  // for.  
        }  // if-else.  
        return (listaCliente);
    }  // List<Cliente>.  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ajustarColumnSizes(JTable tabla ) {
        TableColumn column;
        for (int i = 0; i < modeloTablaClientes.getColumnCount(); i++) {
            column = tabla.getColumnModel().getColumn(i);
            switch (i) {
                case 0: {
                    column.setPreferredWidth(100);
                    break; 
                }
                case 1: {
                    column.setPreferredWidth(400);
                    break; 
                }
            }  // switch. 
        }  // for.
    }  // ajustarColumnSizes. 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarTableModel() {
        String[] columnas = {"Cod Cliente","Nombre Cliente"} ; 
        Object[][] datos = {}; 
        modeloTablaClientes = new DefaultTableModel( datos, columnas ) {
        //modeloTablaClientes = new DefaultTableModel(runQueryBasedOnClientesActivo() , columnas ) {
           @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
    }  // setTableModel().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciarTablaClientes() {
        Object[] dato; 
        iniciarTableModel(); 
        // Actualizar datos del Table Model. 
        for ( Cliente cliente : runQueryBasedOnClientesActivo() ) {
             dato = new Object[] {cliente.getCodCliente(),cliente.getNombreCliente()}; 
             modeloTablaClientes.addRow(dato); 
        }  // for.  
        this.tablaClientes.setModel(modeloTablaClientes);
        ajustarColumnSizes(this.tablaClientes);
    }  // iniciarTablaClientes().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void setTablaClientes() {
        Object[] dato;  
        //this.tablaClientes.removeAll();
        iniciarTableModel();  
        for ( Cliente cliente : runQueryBasedOnClientesActivo() ) {
             dato = new Object[] {cliente.getCodCliente(),cliente.getNombreCliente()}; 
             modeloTablaClientes.addRow(dato); 
        }  // for.  
        this.tablaClientes.setModel(modeloTablaClientes);
        ajustarColumnSizes(this.tablaClientes); 
    }  // iniciarTablaClientes().  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRefrescar = new javax.swing.JButton();
        txtCodCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        sppBotonera = new javax.swing.JSplitPane();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/logoDesica16px.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jLabel2.setText("L.O.V. CLIENTES ACTIVOS");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 12, -1, -1));

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel3.setText("Codigo: ");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, -1));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLabel4.setText("Nombre cliente: ");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        btnRefrescar.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        add(btnRefrescar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, -1, -1));

        txtCodCliente.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        add(txtCodCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 70, -1));

        txtNombreCliente.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 190, -1));

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(tablaClientes);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 500, 220));
        add(sppBotonera, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        setTablaClientes(); 
    }//GEN-LAST:event_btnRefrescarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnRefrescar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane sppBotonera;
    public javax.swing.JTable tablaClientes;
    public javax.swing.JTextField txtCodCliente;
    public javax.swing.JTextField txtNombreCliente;
    // End of variables declaration//GEN-END:variables
}

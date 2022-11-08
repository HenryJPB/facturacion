/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.cliente;
/*  Brqto (12-08-2014). "...jktoolkit..NO SOPORTADO por Java FX. 
import com.jktoolkit.table.JKColumn;
import com.jktoolkit.table.JKPopupMenuItem;
import com.jktoolkit.table.JKTable;
import com.jktoolkit.table.datasource.impl.JKDataSourceList;
*/
import bean.entidad.CxcdDat;
import javax.swing.JDialog;
import vistas.PanelPostearFactura;


/**
 *
 * @author henrypb
 */
public class ControladorLovCliente implements IControladorLovCliente {

    final Integer sizeX = 500, // valores constantes.
            sizeY = 400;
    final Integer posX = 350,
            posY = 200;

    @Override
    public void ejecutarDialogLovCliente() {
        final JDialog dialogFrame = new JDialog();
        final PanelLovCliente panelLovCliente = new PanelLovCliente();
        panelLovCliente.btnConforme.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int fila = panelLovCliente.tablaClientes.getSelectedRow();
                if (fila >= 0) {
                    Integer ordinalCodCliente = PanelLovCliente.columnas.COD_CLIENTE.ordinal();
                    Integer ordinalNombreCliente = PanelLovCliente.columnas.NOMBRE_CLIENTE.ordinal();
                    String codCliente = (String) panelLovCliente.tablaClientes.getValueAt(fila, ordinalCodCliente);
                    //String nombreCliente = (String) panelLovCliente.tablaClientes.getValueAt(fila, ordinalNombreCliente);
                    PanelPostearFactura.txtCodCliente.setText(codCliente);
                    ServicioAdmonCxcd servAdmCxcd = new ServicioAdmonCxcd(); 
                    CxcdDat cliente = servAdmCxcd.getCliente(codCliente); 
                    PanelPostearFactura.txtNombreCliente.setText(cliente.getNombreCliProv());
                    PanelPostearFactura.chbContribuyente.setSelected( ( ((cliente.getContribuyente()!=null && !cliente.getContribuyente().isEmpty())==Boolean.TRUE && cliente.getContribuyente().equals("S")?Boolean.TRUE:Boolean.FALSE)) );
                }
                dialogFrame.dispose();
            }
        });
        //        
        panelLovCliente.btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dialogFrame.dispose();
            }
        });
        dialogFrame.setContentPane(panelLovCliente);
        dialogFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogFrame.setModal(true);
        dialogFrame.setLocation(posX, posY);
        dialogFrame.setSize(sizeX, sizeY);
        dialogFrame.setVisible(true);
    }

    
}  // ControladoLovCliente.  
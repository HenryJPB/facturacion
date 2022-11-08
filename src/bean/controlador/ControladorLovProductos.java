/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador;

import bean.interfase.IControladorLovProductos;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import vistas.PanelAddItemsInv08;
import vistas.PanelLovProductos;

/**
 *
 * @author henrypb
 */
public class ControladorLovProductos implements IControladorLovProductos {
    
    final Integer sizeX = 545, // valores constantes.
                  sizeY = 400;
    final Integer posX = 450,
                  posY = 100;
   
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void ejecutarDialogProductos() {
        final JDialog dialogAddProd = new JDialog(); 
        final PanelLovProductos panelLovProductos = new PanelLovProductos();  
        // ** add funcionalidad a btnConforme. **
        panelLovProductos.btnConforme.addActionListener(new java.awt.event.ActionListener() {
             //-------------------------------------------------
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer filaSeleccionada = panelLovProductos.tablaProductos.getSelectedRow();  
                if ( filaSeleccionada>=0 ) {
                    String codProd = (String) panelLovProductos.tablaProductos.getValueAt(filaSeleccionada, 0); 
                    String tipoProd = (String) panelLovProductos.tablaProductos.getValueAt(filaSeleccionada, 1); 
                    String descripcion = (String) panelLovProductos.tablaProductos.getValueAt(filaSeleccionada, 2); 
                    PanelAddItemsInv08.txtCodProd.setText(codProd);
                    PanelAddItemsInv08.txtTipoProd.setText(tipoProd);
                    PanelAddItemsInv08.txtCodProd.requestFocus();
                }
                dialogAddProd.dispose();             
            }
        } );
        // ** add funcionalidad a btnCancelar. **
        panelLovProductos.btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            //--------------------------------------------------
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogAddProd.dispose();
            }
        } );
        dialogAddProd.setContentPane(panelLovProductos);
        dialogAddProd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogAddProd.setModal(true);
        dialogAddProd.setLocation(posX, posY);
        dialogAddProd.setSize(sizeX, sizeY);
        dialogAddProd.setVisible(true);
    }  // ejecutarDialogProd().  
    
}  // ControladorLovProductos.  

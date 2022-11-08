/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador;

import bean.interfase.IControladorAddItemsInv08;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import vistas.PanelAddItemsInv08;

/**
 *
 * @author henrypb
 */
public class ControladorAddItemsInv08 implements IControladorAddItemsInv08 {

    final Integer sizeX = 525, // valores constantes.
                  sizeY = 580;
    final Integer posX = 350,
                  posY = 120;
    final Integer despX = 50, 
                  despY = -40; 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void ejecutarDialogAddItemsInv08() {
        final JDialog dialogFrame = new JDialog(); 
        PanelAddItemsInv08 panelAddItemsInv08 = new PanelAddItemsInv08();  
        // **
        panelAddItemsInv08.btnConforme.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFrame.dispose();
            }
        });
        panelAddItemsInv08.btnCancelar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFrame.dispose();
            }
        });
        dialogFrame.setContentPane(panelAddItemsInv08);
        dialogFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogFrame.setModal(true);
        dialogFrame.setLocation(posX, posY);
        dialogFrame.setSize(sizeX, sizeY);
        dialogFrame.setResizable(Boolean.FALSE);
        dialogFrame.setVisible(true);
    }


}   // Controlador.  

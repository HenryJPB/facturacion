/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import bean.interfase.IControladorReporteFactura;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JRViewer;
import vistas.PanelPostearFactura;
import vistas.PanelReporteFactura;

/**
 *
 * @author henrypb
 */
public class ControladorReporteFactura implements IControladorReporteFactura {
    
    final Integer sizeX = 350, // valores constantes.
                  sizeY = 320;
    final Integer posX = 350,
                  posY = 120;
    final Integer despX = 50, 
                  despY = -40; 
    
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
       // ** iniciar vars y atributos. **
    }  // iniciar();  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void ejecutarDialogReporteFactura() {
        final JDialog dialogFrame = new JDialog(); 
        PanelReporteFactura panel = new PanelReporteFactura();  
        // **
        panel.btnConforme.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( PanelPostearFactura.txtNroFactura.getText()!=null && !PanelPostearFactura.txtNroFactura.getText().isEmpty() ) {
                   String factura1 = PanelPostearFactura.txtNroFactura.getText();  
                   String factura2 = factura1; 
                   imprimirFactura(factura1,factura2);  
                }
                dialogFrame.dispose();
            }
        });
        panel.btnCancelar.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialogFrame.dispose();
            }
        });
        dialogFrame.setContentPane(panel);
        dialogFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogFrame.setModal(true);
        dialogFrame.setLocation(posX, posY);
        dialogFrame.setSize(sizeX, sizeY);
        dialogFrame.setResizable(Boolean.FALSE);
        dialogFrame.setVisible(true);
    }  // ejecutarDialogReporteFactura(); b 
 
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void imprimirFactura(String factura1, String factura2) {
        ControladorBDOracle conectarBD = new ControladorBDOracle();
        JasperReport jasperReporte;
        JasperPrint jasperPrint;
        // Conectar al Data Source: 
        Connection coneccion = null;
        coneccion = conectarBD.getConeccionOracle();
        // jasperParameter is a Hashmap contains the parameters passed from application to the jrxml layout
        HashMap parametros = new HashMap();
        parametros.put("factura1", factura1);
        parametros.put("factura2", factura2);
        //String nombreReporte = guia1;
        try {
            //jasperReporte = JasperCompileManager.compileReport("./reportes/reporteFactura_v2.jrxml");
            jasperReporte = JasperCompileManager.compileReport("./reportes/reporteFactura_v2.jrxml");
            jasperPrint = JasperFillManager.fillReport(jasperReporte, parametros, coneccion);
            JDialog dialogJViewer = new JDialog();
            dialogJViewer.setSize( 1200,780 );
            //dialogJViewer.pack();   //  << este comando genera una salida indeseable. 
            dialogJViewer.getContentPane().add(new JRViewer(jasperPrint));   
            dialogJViewer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogJViewer.setModal(true);
            dialogJViewer.setTitle("** INFORME FACTURA **");
            dialogJViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println( ex );  
        }  // try-catch.  // try-catch.
    }  // imprimirFactura().
}

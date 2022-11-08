/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import bean.interfase.IControladorReportes;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author henrypb
 */
public class ControladorReportes implements IControladorReportes {

    final Integer sizeX = 400;
    final Integer sizeY = 330;
    final Integer posX = 400;
    final Integer posY = 200;

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void ejecutarReporteFactura() {
        ControladorReporteFactura ctldr = new ControladorReporteFactura();
        ctldr.ejecutarDialogReporteFactura();
    }  // ejecutarReporteFactura().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void exportarExcel(JasperPrint jp, String nombreReporte) throws JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "./salida/" + nombreReporte);
        exporter.exportReport();
    }  // imprimirPDF().  

    //--------------------------------------------------------------------------
    // imprimir type style I. 
    //--------------------------------------------------------------------------
    private void imprimirGuiaOld(String guia1, String guia2) {
        ControladorBDOracle conectarBD = new ControladorBDOracle();
        JasperReport jasperReporte;
        JasperPrint jasperPrint;
        // Conectar al Data Source: 
        Connection coneccion = null;
        coneccion = conectarBD.getConeccionOracle();
        // jasperParameter is a Hashmap contains the parameters passed from application to the jrxml layout
        HashMap parametros = new HashMap();
        parametros.put("guia1", guia1);
        parametros.put("guia2", guia2);
        String nombreReporte = guia1;
        try {
            jasperReporte = JasperCompileManager.compileReport("./reportes/reporteGuiaDespacho.jrxml");
            jasperPrint = JasperFillManager.fillReport(jasperReporte, parametros, coneccion);
            String destino = "";   // PanelReporteFactura.cbbDestinoReporte.getSelectedItem().toString();
            switch (destino) {
                case "html": {
                    nombreReporte = nombreReporte + ".html";
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, "./salida/" + nombreReporte);
                    break;
                }  // case html.  
                case "pdf": {
                    nombreReporte = nombreReporte + ".pdf";
                    JasperExportManager.exportReportToPdfFile(jasperPrint, "./salida/" + nombreReporte);
                    break;
                }  // case pdf.  
                case "excel": {
                    nombreReporte = nombreReporte + ".xls";
                    exportarExcel(jasperPrint, nombreReporte);
                    break;
                }  // case excel.  
            }  // switch.  
        } catch (JRException ex) {
            Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println( ex );  
        }  // try-catch.  // try-catch.
        //-------------------------------------------------
        // **** Ejecutar Reporte on the DeskTop:      ****
        //-------------------------------------------------
        try {
            File path = new File("salida/" + nombreReporte);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            System.err.println( ex );
        }  // try-catch. 
    }  // imprimirFactura().  
    
}  // ControladorReportes.  

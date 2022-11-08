/* *****************************************************************************
 * DERIVADOS SIDERURGICOS, C.A. 
 * Sistema de Factuaracion. 
 * Creado el 17 de Octubre 2013. 
 * Autor: Henry J. Pulgar Blanco. 
 * Plataforma tecnologica: JavaFX-in-Swing. 
 * IDE: NetBeans 7.3.1. 
 ***************************************************************************  */
package facturacion;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import vistas.FrameAccesoPrincipal;

/**
 *
 * @author henrypb Fecha creacion: Barquisimeto: 29/11/2012.
 *
 */
public class Facturacion {
    /*
     public static final Integer xWindow = 950,
     yWindow = 750;
     */

    static JFrame miFrame;
    static JProgressBar miProgressBar;

    /**
     * @param args the command line arguments
     *
     */
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private static void mostrarFrameBienvenida() {
        JLabel comentarioBienvenida = new JLabel("BIENVENIDO!!", SwingConstants.CENTER);
        comentarioBienvenida.setLocation(130, 70);
        comentarioBienvenida.setSize(220, 40);
        comentarioBienvenida.setFont(new Font("Dialog", 1, 20));
        miProgressBar = new JProgressBar(0, 100);
        miProgressBar.setSize(220, 20);
        miProgressBar.setLocation(130, 100);
        miProgressBar.setBorderPainted(true);
        miProgressBar.setBorder(BorderFactory.createRaisedBevelBorder());
        JPanel contenido = new JPanel(null);
        miFrame = new JFrame("FACTURACION");
        miFrame.setContentPane(contenido);
        miFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Container contentPane = miReportFrame.getContentPane();
        miFrame.setSize(500, 250);
        miFrame.setLocation(250, 200);
        miFrame.add(comentarioBienvenida);
        miFrame.add(miProgressBar);
        miFrame.setVisible(true);
        try {
            for (Integer i = 0; i <= 200; i += 20) {
                Thread.sleep(i);
                miProgressBar.setValue(i / 2);
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Facturacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  // metodo mostrarFrameBienvenida(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        try {  // El siguiente comando define la apariencia de la aplicacion/se puede suprimir.  
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        // *  INTRUCCIONES DEL METODO main().  * //
        mostrarFrameBienvenida();   // --> miFrame.  
        // *  
        FrameAccesoPrincipal frameAccesoPrincipal = new FrameAccesoPrincipal();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frameAccesoPrincipal.setState(Frame.NORMAL);
        //frameAccesoPrincipal.setSize(dimension);  // Funcionó correctamente el 29/11/2012. 15:55- 
        frameAccesoPrincipal.setSize(Estilos.xWindow, Estilos.yWindow);
        miFrame.dispose();
        frameAccesoPrincipal.setVisible(true);
        // */
        // ---------------------------------------------------------------------
        // * EXTENSION AREA DE PRUEBA *  
        //PruebaBindTableContent.ejecutarPruebaBindTableContent();  // funcionó correctamente el 31/03/2014 a las 14:00 h.  
        // ---------------------------------------------------------------------
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

/**
 *
 * @author henrypb
 */
public class PanelPortada extends javax.swing.JPanel {

    /**
     * Creates new form PanelPortada
     */
    public PanelPortada() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPortada = new javax.swing.JLabel();

        lblPortada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bean/utilitario/imagenes/contabilidad100x100.jpg"))); // NOI18N
        lblPortada.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPortada, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPortada, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblPortada;
    // End of variables declaration//GEN-END:variables
}

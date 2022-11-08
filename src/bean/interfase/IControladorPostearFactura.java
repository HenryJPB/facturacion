/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfase;

/**
 *
 * @author henrypb
 */
public interface IControladorPostearFactura {

    public abstract void setCamposEditar( Boolean editar );  
    
    public abstract void iniciarCampos();  
    
    public abstract void limpiarCampos();
    
    public abstract void actualizarFlete(java.util.Date alFecha, String codDestino, String codSector, String tipoTransporte );  
    
    public abstract void editarRegistro(); 
    
    public abstract void agregarRegistro(); 
    
    public abstract void actualizarRegistro(); 
    
    public abstract void eliminarRegistro(); 
    
    public abstract void cancelar();    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.despacho;

import bean.entidad.Inv07Dat;
import bean.entidad.Guias01Dat;

/**
 *
 * @author henrypb
 */
public interface IControladorGuia01 {
    
    public abstract void ejecutarDialogGuiasFacturar(); 

    // * Datos Guia01 => (vaciar en la estructura) Inv07. *  
    public abstract Inv07Dat getDatosGuia( Guias01Dat guia01 ); 
    
}

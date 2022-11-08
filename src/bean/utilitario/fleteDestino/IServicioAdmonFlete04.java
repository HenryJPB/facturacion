/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.fleteDestino;

import entidad.Flete04Dat;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonFlete04 {
    
    public abstract Flete04Dat getTarifaFlete(java.util.Date alFecha, String codDestino, String codSector);  
    
}

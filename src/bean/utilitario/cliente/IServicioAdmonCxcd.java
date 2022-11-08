/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.utilitario.cliente;

import bean.entidad.CxcdDat;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonCxcd {
 
    public abstract Boolean clienteRegistrado( String codCliente ); 
    
    public abstract String nombreCliente( String codCliente );  
    
    public abstract CxcdDat getCliente( String codCliente ); 
}

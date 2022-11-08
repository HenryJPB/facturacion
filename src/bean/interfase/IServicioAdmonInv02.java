/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.interfase;

import java.math.BigDecimal;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonInv02 {
    
    public abstract Boolean precioRegistrado(String codProducto); 
    
    public abstract BigDecimal precioUnidad(String codProducto); 
    
    public abstract BigDecimal precioKgs(String codProducto); 
    
}

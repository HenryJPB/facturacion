/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.interfase;

import java.util.Vector;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonVend01 {
    
    public abstract Vector<String> getCodVendedores();  
    
    public abstract String nombreVendedor( String codVendedor ); 
    
    public abstract Vector<String> getZonasVenta( String codVendedor ); 
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfase;

import bean.entidad.Inv03Dat;
import bean.entidad.Inv04Dat;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonInv03 {

    public abstract Boolean ordenRegistrada(String ordenFab);

    //public abstract List<Inv03Dat> getItemsOrden(String ordenFab1,String ordenFab2); 
    
    public abstract Inv03Dat getOrdenFab(String ordenFab);
    
    public abstract Boolean prodRegistrado( String codProd, String ordenFab ); 

    public abstract Inv04Dat getDatosProducto( String codProd, String ordenFab ); 
}

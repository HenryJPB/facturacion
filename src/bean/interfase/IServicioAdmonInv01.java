/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.interfase;

import bean.entidad.Inv01Dat;
import java.util.List;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonInv01 {
    
    public abstract List<Inv01Dat> getProdStandard(String codigoLike, String nombreLike );  

    public abstract Boolean prodRegistrado( String codProd ); 

    public abstract Inv01Dat getDatosProducto( String codProd ); 

}


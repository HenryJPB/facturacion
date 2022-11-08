/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfase;

import bean.entidad.Guias01Dat;
import java.util.List;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonGuia01 {
    
    public abstract List<String[]> getGuiasPorFacturar(); 
    
    public abstract Boolean guiaRegistrada( String nroGuia ); 
    
    public abstract Guias01Dat getGuiaDespacho( String nroGuia ); 
    
}

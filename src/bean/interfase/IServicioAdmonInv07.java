/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.interfase;

import bean.entidad.Inv07Dat;
import bean.entidad.Inv08Dat;
import java.util.List;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonInv07 {
    
    public abstract Boolean facturaRegistrada(String ncf, String nroFactura, java.sql.Date fechaFactura ); 

    public abstract Boolean detalleRegistrado( String nroFactura );  // validar la inserción de los items detalle.  
    
    public abstract List<Inv07Dat> getlistaFacturas(String ncf, String nroFactura, java.sql.Date fechaFactura );

    public abstract Inv07Dat getInv07(String ncf, String nroFactura, java.sql.Date fechaFactura ); 
    
    public abstract Inv07Dat getTotales( String nroFactura ); 
    
    public abstract Inv07Dat getNextFactura(); 
    
    public abstract void agregar( Inv07Dat inv07Dat );  
    
    public abstract void actualizar( Inv07Dat inv07Dat ); 
    
    public abstract void eliminar(Inv07Dat inv07Dat); 
    
    public abstract void eliminar(Inv08Dat inv08Dat); 
    
    }

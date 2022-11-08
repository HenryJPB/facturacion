/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.fleteDestino;

import bean.utilitario.hibernate.HibernateUtil;
import bean.utilitario.libreria.LibreriaHP;
import entidad.Flete04Dat;
import entidad.Flete04DatId;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonFlete04 implements IServicioAdmonFlete04 {
    
    LibreriaHP libHP = new LibreriaHP(); 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Flete04Dat getTarifaFlete( java.util.Date alFecha, String codDestino, String codSector ) {
        // *from Flete04Dat where id.c4FechaRelacion <= (select MAX( id.c4FechaRelacion ) as f from Flete04Dat where id.c4FechaRelacion <= '15-04-2014' ) *: EXITO!!! al 05/11/14 - a las 14:00 
        //final String Q =  "from Flete04Dat where id.c4FechaRelacion <= '"+alFecha+"'";  
        java.sql.Date alFechaSql = new java.sql.Date(alFecha.getTime());  
        //final String Q = "from Flete04Dat where id.c4FechaRelacion = (select MAX( id.c4FechaRelacion ) from Flete04Dat where id.c4FechaRelacion <= '" + libHP.formatoFecha.format(alFecha) + "' ) and id.c4CodDestino = '" + codDestino + "' and id.c4CodSector = '" + codSector + "'";    // <==Correcto. 
        final String Q = "from Flete04Dat where id.c4FechaRelacion = (select MAX( c3FechaRelacion ) from Flete03Dat where c3FechaRelacion <= '" + libHP.formatoFecha.format(alFecha) + "' ) and id.c4CodDestino = '" + codDestino + "' and id.c4CodSector = '" + codSector + "'";    // <==Correcto. 
        return (executeHQLQueryFlete04(Q));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Flete04Dat executeHQLQueryFlete04(String hql) {
        Flete04Dat flete04 = new Flete04Dat();
        //resultList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (q.list() != null && !q.list().isEmpty()) {
                flete04 = procesarFlete04(q.list());
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (flete04);
    }  // executeHQLQueryFlete04(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Flete04Dat procesarFlete04(List lista) {
        Flete04Dat flete04 = null;
        if (lista == null && lista.isEmpty()) {
            System.err.println("ATENCION: Lista TARIFA de FLETES esta vacia o posee inconsistencia de datos.");
        } else {
            for (Object o : lista) {
                flete04 = (Flete04Dat) o;
                //private Flete04DatId id;    // ...:  
                java.util.Date c4FechaRelacion = flete04.getId().getC4FechaRelacion();
                String c4CodDestino = flete04.getId().getC4CodDestino();
                String c4CodSector = flete04.getId().getC4CodSector();
                // private Flete03Dat flete03Dat;   // aqui puede ser referenciado como null.  
                String c4CodPais = flete04.getC4CodPais();
                String c4CodEstado = flete04.getC4CodEstado();
                String c4NombreDestino = flete04.getC4NombreDestino();
                String c4CodPostal = flete04.getC4CodPostal();
                BigDecimal c4PrecioTonCamion = flete04.getC4PrecioTonCamion();
                BigDecimal c4PrecioTonGandola = flete04.getC4PrecioTonGandola();
                BigDecimal c4PrecioTonToronto = flete04.getC4PrecioTonToronto();
                BigDecimal c4PrecioTonEspecial = flete04.getC4PrecioTonEspecial();
                String c4CodMonExtCamion = flete04.getC4CodMonExtCamion();
                BigDecimal c4PrecioTonCamionMe = flete04.getC4PrecioTonCamionMe();
                String c4CodMonExtGandola = flete04.getC4CodMonExtGandola();
                BigDecimal c4PrecioTonGandolaMe = flete04.getC4PrecioTonGandolaMe();
                String c4CodMonExtToronto = flete04.getC4CodMonExtToronto();
                BigDecimal c4PrecioTonTorontoMe = flete04.getC4PrecioTonTorontoMe();
                String c4CodMonExtPrecioEsp = flete04.getC4CodMonExtPrecioEsp();
                BigDecimal c4PrecioTonEspecialMe = flete04.getC4PrecioTonEspecial();
                flete04 = new Flete04Dat(new Flete04DatId(c4FechaRelacion, c4CodDestino, c4CodSector), null, c4CodPais, c4CodEstado, c4NombreDestino, c4CodPostal, c4PrecioTonCamion, c4PrecioTonGandola, c4PrecioTonToronto, c4PrecioTonEspecial, c4CodMonExtCamion, c4PrecioTonCamionMe, c4CodMonExtGandola, c4PrecioTonGandolaMe, c4CodMonExtToronto, c4PrecioTonTorontoMe, c4CodMonExtPrecioEsp, c4PrecioTonEspecialMe);
            }  // for.
        }  // if-else.  
        return (flete04);
    }  // procesarFlete04().  

}  // ServicioAdmonFlete04(). 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.servicio;

import bean.entidad.Inv02Dat;
import bean.interfase.IServicioAdmonInv02;
import bean.utilitario.hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonInv02 implements IServicioAdmonInv02 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean precioRegistrado(String codProducto) {
        String QUERY="from Inv02Dat where id.c2Codigo='"+codProducto+"' and ROWNUM=1"; 
        return (executeHQLQueryFind(QUERY));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public BigDecimal precioUnidad(String codProducto) {
        String QUERY="from Inv02Dat where ( id.c2Codigo='"+codProducto+"' and id.c2Fecha = (select MAX(id.c2Fecha) from Inv02Dat where id.c2Codigo='"+codProducto+"' ) )"; 
        //select id.c2Codigo as c from Inv02Dat where ( id.c2Codigo='98' and id.c2Fecha = (select MAX(id.c2Fecha) from Inv02Dat where id.c2Codigo='98' ) ) 
        Inv02Dat inv02Dat = executeHQLQueryInv02(QUERY); 
        return (inv02Dat.getC2PrecioUnidad());
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public BigDecimal precioKgs(String codProducto) {
        String QUERY="from Inv02Dat where ( id.c2Codigo='"+codProducto+"' and id.c2Fecha = (select MAX(id.c2Fecha) from Inv02Dat where id.c2Codigo='"+codProducto+"' ) )"; 
        // select id.c2Codigo as c from Inv02Dat where ( id.c2Codigo='98' and id.c2Fecha = (select MAX(id.c2Fecha) from Inv02Dat where id.c2Codigo='98' ) ) 
        Inv02Dat inv02Dat = executeHQLQueryInv02(QUERY); 
        return (inv02Dat.getC2PrecioKgs());
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean executeHQLQueryFind(String hql) {
        Boolean encontrado = Boolean.FALSE;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (!q.list().isEmpty()) {
                encontrado = Boolean.TRUE;
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (encontrado);
    } // executeHQLQueryFind(
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv02Dat executeHQLQueryInv02(String hql) {
        Inv02Dat inv02Dat=null;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (!q.list().isEmpty() && q.list() != null) {
                //displayResult(resultList);
                inv02Dat = procesarInv02(q.list());
            }  // if. 
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv02Dat);
    } // executeHQLQueryInv02()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv02Dat procesarInv02(List q) {
        Inv02Dat inv02Dat = null; 
        for ( Object o : q ) {
            inv02Dat = (Inv02Dat) o; 
            BigDecimal c2PrecioUnidad = inv02Dat.getC2PrecioUnidad();  
            BigDecimal c2PrecioKgs = inv02Dat.getC2PrecioKgs(); 
            inv02Dat = new Inv02Dat(null, c2PrecioUnidad, c2PrecioKgs); 
        }  // for.  
        return (inv02Dat);
    } // procesarInv02(). 
}

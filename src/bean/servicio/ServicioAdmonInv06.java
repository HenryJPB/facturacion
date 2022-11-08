/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.entidad.Inv06Dat;
import bean.interfase.IServicioAdmonInv06;
import bean.utilitario.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonInv06 implements IServicioAdmonInv06 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean regEncontrado(String tipoProd) {
        String QUERY = "from Inv06Dat where c6Tipo='" + tipoProd + "'";
        return (executeHQLQueryFind(QUERY));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean facXunidad(String tipoProd) {
        Inv06Dat inv06Dat=null;
        Boolean ok = Boolean.FALSE; 
        String QUERY = "from Inv06Dat where c6Tipo='" + tipoProd + "'";
        inv06Dat = executeHQLQueryInv06(QUERY); 
        if ( inv06Dat.getC6Fxunidad()!=null && !inv06Dat.getC6Fxunidad().isEmpty() ) {
            ok = Boolean.TRUE; 
        }
        return (ok);
    }  // facXunidad(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean facXpeso(String tipoProd) {
        Inv06Dat inv06Dat=null; 
        Boolean ok = Boolean.FALSE; 
        String QUERY = "from Inv06Dat where c6Tipo='" + tipoProd + "'";
        inv06Dat = executeHQLQueryInv06(QUERY); 
        if ( inv06Dat.getC6Fxpeso()!=null && !inv06Dat.getC6Fxpeso().isEmpty() ) {
            ok = Boolean.TRUE; 
        }
        return (ok);
    }  // facXpeso(). 

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
    } // executeHQLQueryFind()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv06Dat executeHQLQueryInv06(String hql) {
        Inv06Dat inv06Dat=null;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (!q.list().isEmpty() && q.list() != null) {
                //displayResult(resultList);
                inv06Dat = procesarInv06(q.list());
            }  // if. 
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv06Dat);
    } // executeHQLQueryFind()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv06Dat procesarInv06(List q) {
        Inv06Dat inv06Dat=null;
        for ( Object o : q  ) {
            inv06Dat = (Inv06Dat) o;
            String c6Tipo = inv06Dat.getC6Tipo(); 
            String c6Descripcion = inv06Dat.getC6Descripcion(); 
            String c6Ubicacion = inv06Dat.getC6Ubicacion(); 
            String c6Fxunidad = inv06Dat.getC6Fxunidad(); 
            String c6Fxpeso = inv06Dat.getC6Fxpeso(); 
            String c6Lxunidad = inv06Dat.getC6Lxunidad(); 
            String c6Lxpeso = inv06Dat.getC6Lxpeso(); 
            inv06Dat = new Inv06Dat(c6Tipo,c6Descripcion,c6Ubicacion,c6Fxunidad,c6Fxpeso,c6Lxunidad,c6Lxpeso);
        }  // for.
        return (inv06Dat); 
    }  // procesarInv06(). 

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.servicio;

import bean.entidad.Vend01Dat;
import bean.interfase.IServicioAdmonVend01;
import bean.utilitario.hibernate.HibernateUtil;
import java.util.List;
import java.util.Vector;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonVend01 implements IServicioAdmonVend01 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public String nombreVendedor(String codVendedor) {
        String QUERY = "from Vend01Dat where c1CodVendedor ='"+codVendedor+"'"; 
        return (executeHQLQueryNombreVendedor(QUERY));
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String executeHQLQueryNombreVendedor(String hql) {
        String nombre = null;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            nombre = procesarNombreVendedor(q.list()); 
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (nombre);
    } // executeHQLQuery().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String procesarNombreVendedor(List q) {
        String nombre = null;  
        if (q==null || q.isEmpty()  ) {
            System.err.println("**ATENCION: Revisar por inconsistencia de informacion en la tabla VEND01_DAT.**.");
        } else {
            Vend01Dat vend01Dat; 
            for ( Object o : q ) {
                  vend01Dat = (Vend01Dat) o; 
                  nombre = vend01Dat.getC1NombreVend(); 
            }  // for.  
        }  // if-else.  
        return (nombre);
    }  // procesarNombreVendedor(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Vector<String> getCodVendedores() {
        final String QUERY="from Vend01Dat order by c1CodVendedor"; 
        return (executeHQLQueryCodVendedores(QUERY));
        //return (null);
    } // getCodVendedores().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Vector<String> executeHQLQueryCodVendedores(String hql) {
        Vector<String> lista = new Vector<String>();  
        lista = null; 
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            if ( !q.list().isEmpty()) {
                lista = procesarListaCodVendedores(q.list()); 
            }
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (lista);
    } // executeHQLQuery().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Vector<String> procesarListaCodVendedores(List q) {
        Vector<String> lista = new Vector<String>(); 
        if (q==null || q.isEmpty()  ) {
            System.err.println("**ATENCION: Revisar por inconsistencia de informacion en la tabla VEND01_DAT (Cod Vendedores).**.");
        } else {
            Vend01Dat vend01Dat; 
            for ( Object o : q ) {
                  vend01Dat = (Vend01Dat) o; 
                  if ( vend01Dat.getC1CodVendedor()!=null ) {
                     lista.add(vend01Dat.getC1CodVendedor()); 
                  }
            }  // for.  
        }  // if-else.  
        return (lista);
    }  // procesarListaVendedor(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Vector<String> getZonasVenta(String codVendedor) {
        final String QUERY = "from Vend01Dat where c1CodVendedor='"+codVendedor+"'"; 
        return (executeHQLQueryZonasVenta(QUERY));
    } // getZonasVenta()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Vector<String> executeHQLQueryZonasVenta(String hql) {
        Vector<String> lista = new Vector<String>();  
        lista = null;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            if ( !q.list().isEmpty()) {
                 lista = procesarZonasVenta(q.list()); 
            }
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (lista);
    } // executeHQLQuery().    

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Vector<String> procesarZonasVenta(List q) {
        Vector<String> lista = new Vector<String>(); 
        if (q==null || q.isEmpty()  ) {
            System.err.println("**ATENCION: Revisar por inconsistencia de informacion en la tabla VEND01_DAT (Zonas).**.");
        } else {
            Vend01Dat vend01Dat; 
            for ( Object o : q ) {
                  vend01Dat = (Vend01Dat) o; 
                  if ( vend01Dat.getC1Zona()!=null ) {
                     lista.add(vend01Dat.getC1Zona()); 
                  }
            }  // for.  
        }  // if-else.  
        return (lista);
    }  // procesarListaVendedor().
    
}  // ServicioQAdmonVend01(). 
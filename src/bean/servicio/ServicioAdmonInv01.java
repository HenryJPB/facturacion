/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.servicio;

import bean.entidad.Inv01Dat;
import bean.interfase.IServicioAdmonInv01;
import bean.utilitario.hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonInv01 implements IServicioAdmonInv01 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<Inv01Dat> getProdStandard(String codigoLike, String nombreLike ) {
        String QUERY = "from Inv01Dat where c1Codigo like '"+codigoLike+"%' and c1Descripcion like '"+nombreLike+"%' order by c1Tipo, c1Codigo";  
        return (executeHQLQueryAllInv01(QUERY));
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Inv01Dat> executeHQLQueryAllInv01(String hql) {
        List<Inv01Dat> resultList = new ArrayList<Inv01Dat>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            resultList = procesarListaProductos(q.list());
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (resultList);
    } // executeHQLQueryInv01().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Inv01Dat> procesarListaProductos(List q) {
        List<Inv01Dat> lista = new ArrayList<Inv01Dat>();
        Inv01Dat inv01Dat;  
        if ( q==null || q.isEmpty() ) {
            System.err.println("** ATENCION: lista de Productos VACIA o inconsistencia de informacion..??***");
            System.out.println("**Pongase en contacto con el DBA de tu instalación de B.D.****");
        } else {
            for ( Object o : q ) {
                inv01Dat = (Inv01Dat) o; 
                String c1Codigo = inv01Dat.getC1Codigo();
                String c1Tipo= inv01Dat.getC1Tipo();
                String c1Descripcion = inv01Dat.getC1Descripcion(); 
                BigDecimal c1Espesor = inv01Dat.getC1Espesor(); 
                BigDecimal c1Longitud = inv01Dat.getC1Longitud(); 
                BigDecimal c1Ancho = inv01Dat.getC1Ancho(); 
                Integer c1Area = inv01Dat.getC1Area(); 
                String c1Separacion = inv01Dat.getC1Separacion(); 
                Integer c1ItemsAtado = inv01Dat.getC1ItemsAtado();
                BigDecimal c1Peso = inv01Dat.getC1Peso(); 
                BigDecimal c1PrecioActual = inv01Dat.getC1PrecioActual(); 
                String c1UnidadMedida = inv01Dat.getC1UnidadMedida();
                String c1CodMaquina = inv01Dat.getC1CodMaquina();
                String c1Observacion = inv01Dat.getC1Observacion(); 
                Short c1Unidades = inv01Dat.getC1Unidades();
                String c1CodContable = inv01Dat.getC1CodContable(); 
                lista.add(new Inv01Dat(c1Codigo, c1Tipo ,c1Descripcion, c1Espesor, c1Longitud, c1Ancho, c1Area, c1Separacion, c1ItemsAtado, c1Peso, c1PrecioActual, c1UnidadMedida, c1CodMaquina, c1Observacion, c1Unidades, c1CodContable) ); 
            }  // for. 
        }  // if-else.  
        return (lista);   
    }  // procesarListProductos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean prodRegistrado(String codProd) {
        String QUERY = "from Inv01Dat where c1Codigo = '"+codProd+"'"; 
        return(executeHQLQueryFind(QUERY)); 
    }  // prodRegistrado(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean executeHQLQueryFind(String hql) {
        //List<Inv01Dat> resultList = new ArrayList<Inv01Dat>();
        Boolean valido = Boolean.FALSE; 
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            //resultList = procesarListaProductos(q.list());
            if ( q.list()!=null && !q.list().isEmpty() ) {
                 valido = Boolean.TRUE; 
            }  // if. 
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (valido);
    } // executeHQLQueryFind().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Inv01Dat getDatosProducto(String codProd) {
        String QUERY="from Inv01Dat where c1Codigo='"+codProd+"'"; 
        return(executeHQLQueryInv01(QUERY));
    }  // getDatosProducto().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv01Dat executeHQLQueryInv01(String hql) {
        Inv01Dat inv01Dat = null; 
        Boolean valido = Boolean.FALSE; 
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            //resultList = procesarListaProductos(q.list());
            if ( q.list()!=null && !q.list().isEmpty() ) {
                 inv01Dat = procesarProducto(q.list()); 
            }  // if. 
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv01Dat);
    }  // executeHQLQueryInv01(). 
 
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv01Dat procesarProducto(List q) {
        Inv01Dat inv01Dat = null;  
        if ( q==null || q.isEmpty() ) {
            System.err.println("** ATENCION: lista de Productos VACIA o inconsistencia de informacion..??***");
            System.out.println("**Pongase en contacto con el DBA de tu instalación de B.D.****");
        } else {
            for ( Object o : q ) {
                inv01Dat = (Inv01Dat) o; 
                String c1Codigo = inv01Dat.getC1Codigo();
                String c1Tipo= inv01Dat.getC1Tipo();
                String c1Descripcion = inv01Dat.getC1Descripcion(); 
                BigDecimal c1Espesor = inv01Dat.getC1Espesor(); 
                BigDecimal c1Longitud = inv01Dat.getC1Longitud(); 
                BigDecimal c1Ancho = inv01Dat.getC1Ancho(); 
                Integer c1Area = inv01Dat.getC1Area(); 
                String c1Separacion = inv01Dat.getC1Separacion(); 
                Integer c1ItemsAtado = inv01Dat.getC1ItemsAtado();
                BigDecimal c1Peso = inv01Dat.getC1Peso(); 
                BigDecimal c1PrecioActual = inv01Dat.getC1PrecioActual(); 
                String c1UnidadMedida = inv01Dat.getC1UnidadMedida();
                String c1CodMaquina = inv01Dat.getC1CodMaquina();
                String c1Observacion = inv01Dat.getC1Observacion(); 
                Short c1Unidades = inv01Dat.getC1Unidades();
                String c1CodContable = inv01Dat.getC1CodContable(); 
                //lista.add(new Inv01Dat(c1Codigo, c1Tipo ,c1Descripcion, c1Espesor, c1Longitud, c1Ancho, c1Area, c1Separacion, c1ItemsAtado, c1Peso, c1PrecioActual, c1UnidadMedida, c1CodMaquina, c1Observacion, c1Unidades, c1CodContable) ); 
                inv01Dat = new Inv01Dat(c1Codigo, c1Tipo ,c1Descripcion, c1Espesor, c1Longitud, c1Ancho, c1Area, c1Separacion, c1ItemsAtado, c1Peso, c1PrecioActual, c1UnidadMedida, c1CodMaquina, c1Observacion, c1Unidades, c1CodContable); 
            }  // for. 
        }  // if-else.  
        return (inv01Dat);   
    }  // procesarListProductos().
    
}

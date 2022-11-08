/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.entidad.Inv03Dat;
import bean.entidad.Inv04Dat;
import bean.entidad.Inv04DatId;
import bean.interfase.IServicioAdmonInv03;
import bean.utilitario.hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonInv03 implements IServicioAdmonInv03 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------  
    @Override
    public Boolean ordenRegistrada(String ordenFab) {
        String QUERY = "from Inv03Dat where c3Orden ='" + ordenFab + "'";
        return (executeHQLQueryFind(QUERY));
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
    } // executeHQLQueryFind().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Inv03Dat getOrdenFab(String ordenFab) {
        Inv03Dat inv03Dat = null;
        String QUERY = "from Inv03Dat where c3Orden ='" + ordenFab + "'";
        return (executeHQLQueryInv03(QUERY));
    }  // getItemsOrden().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv03Dat executeHQLQueryInv03(String hql) {
        Inv03Dat inv03Dat = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (!q.list().isEmpty()) {
                inv03Dat = procesarListaOrdenFab(q.list());
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv03Dat);
    } // executeHQLQueryOrdenFab().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv03Dat procesarListaOrdenFab(List q) {
        Inv03Dat resultanteInv03Dat = null;
        if (q == null || q.isEmpty()) {
            System.err.println("**ATENCION: Lista de Orden de Fab esta vacia o inconsistencia de Informacion.***");
        } else {
            Inv03Dat inv03Dat;
            for (Object o : q) {
                inv03Dat = (Inv03Dat) o;
                String c3Orden = inv03Dat.getC3Orden();
                String c3Cotizacion = inv03Dat.getC3Cotizacion();
                String c3CodigoCliente = inv03Dat.getC3CodigoCliente();
                String c3Atencion = inv03Dat.getC3Atencion();
                String c3Telefono = inv03Dat.getC3Telefono();
                Date c3FechaEntrega = inv03Dat.getC3FechaEntrega();
                String c3DirEntrega = inv03Dat.getC3DirEntrega();
                String c3FormaPago = inv03Dat.getC3FormaPago();
                String c3Observacion1 = inv03Dat.getC3Observacion1();
                String c3Observacion2 = inv03Dat.getC3Observacion2();
                Set<Inv04Dat> inv04Dats = inv03Dat.getInv04Dats();
                resultanteInv03Dat = new Inv03Dat(c3Orden, c3Cotizacion, c3CodigoCliente, c3Atencion, c3Telefono, c3FechaEntrega, c3DirEntrega, c3FormaPago, c3Observacion1, c3Observacion2, inv04Dats);
            }  // for.  
        }  // if-else.  
        return (resultanteInv03Dat);
    }  // procesarListaOrdenFab(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean prodRegistrado(String codProd, String ordenFab) {
        String QUERY = "from Inv04Dat where id.c4Codigo='" + codProd + "' and id.c4Orden='" + ordenFab + "'";
        return (executeHQLQueryFind(QUERY));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Inv04Dat getDatosProducto(String codProd, String ordenFab) {
        String QUERY = "from Inv04Dat where id.c4Codigo='" + codProd + "' and id.c4Orden='" + ordenFab + "'";
        return (executeHQLQueryInv04(QUERY));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv04Dat executeHQLQueryInv04(String hql) {
        Inv04Dat inv04Dat = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (!q.list().isEmpty()) {
                inv04Dat = procesarListaProdOrden(q.list());
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv04Dat);
    } // executeHQLQueryOrdenFab().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv04Dat procesarListaProdOrden(List q) {
        Inv04Dat inv04Dat = null;
        if (q == null || q.isEmpty()) {
            System.err.println("****ATENCION: Lista de productos de la O.F. esta vacia / revisar por inconsistencia de informacio.****");
        } else {
            for (Object o : q) {
                inv04Dat = (Inv04Dat) o;
                String orden = inv04Dat.getId().getC4Orden();
                String codigo = inv04Dat.getId().getC4Codigo(); 
                // private Inv03Dat inv03Dat;
                String c4TipoProd = inv04Dat.getC4TipoProd();
                BigDecimal c4Taml = inv04Dat.getC4Taml();
                BigDecimal c4Tamt = inv04Dat.getC4Tamt();
                BigDecimal c4Sepl = inv04Dat.getC4Sepl();
                BigDecimal c4Sept = inv04Dat.getC4Sept();
                BigDecimal c4Diaml = inv04Dat.getC4Diaml();
                BigDecimal c4Diamt = inv04Dat.getC4Diamt();
                Short c4Numl = inv04Dat.getC4Numl();
                Short c4Numt = inv04Dat.getC4Numt();
                Short c4Sobrl1 = inv04Dat.getC4Sobrl1();
                Short c4Sobrl2 = inv04Dat.getC4Sobrl2();
                Short c4Sobrt1 = inv04Dat.getC4Sobrt1();
                Short c4Sobrt2 = inv04Dat.getC4Sobrt2();
                BigDecimal c4PesoItem = inv04Dat.getC4PesoItem();
                Integer c4Lam = inv04Dat.getC4Lam();
                BigDecimal c4Bsxton = inv04Dat.getC4Bsxton();
                String c4CodContable = inv04Dat.getC4CodContable();
                inv04Dat = new Inv04Dat( new Inv04DatId(orden,codigo), new Inv03Dat(orden), c4TipoProd, c4Taml, c4Tamt, c4Sepl, c4Sept, c4Diaml, c4Diamt, c4Numl, c4Numt, c4Sobrl1, c4Sobrl2, c4Sobrt1, c4Sobrt2, c4PesoItem, c4Lam, c4Bsxton, c4CodContable ); 
            } // for 
        }  // if.  
        return (inv04Dat);
    }  // procesarListaProdOrden(); 

}

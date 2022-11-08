/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.utilitario.cliente;

import bean.entidad.CxcdDat;
import bean.utilitario.hibernate.HibernateUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonCxcd implements IServicioAdmonCxcd {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean clienteRegistrado(String codCliente) {
        String QUERY = "from CxcdDat where codigo='"+codCliente+"'";  
        return (executeHQLQueryClienteRegistrado(QUERY)); 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public String nombreCliente(String codCliente) {
        String QUERY="from CxcdDat where codigo = '"+codCliente+"'";   
        return( executeHQLQueryNombreCliente(QUERY) ); 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String executeHQLQueryNombreCliente(String hql) {
        String nombre = null;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            nombre = procesarNombreCliente(q.list()); 
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (nombre);
    } // executeHQLQueryNombreCliente().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private String procesarNombreCliente( List q ) {
        CxcdDat cxcdDat;  
        String nombre = null; 
        if ( q==null || q.isEmpty() ) {
            System.out.println("**ATENCION: inconsistencia de Información en el Nombre Cliente.***");
        } else {
            for ( Object o : q ) {
                cxcdDat = (CxcdDat) o; 
                nombre = cxcdDat.getNombreCliProv(); 
            }  //  for.  
        }  // if-else.  
        return (nombre);  
    }  // procesarNombreCliente().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean executeHQLQueryClienteRegistrado(String hql) {
        Boolean valido = Boolean.FALSE;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            if ( !q.list().isEmpty() ) valido = Boolean.TRUE;  
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (valido);
    } // executeHQLQueryClienteRegistrado().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public CxcdDat getCliente(String codCliente) {
        final String QUERY="from CxcdDat where codigo='"+codCliente+"'"; 
        return(executeHQLQueryGetCxcd(QUERY)); 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private CxcdDat executeHQLQueryGetCxcd(String hql) {
        CxcdDat cxcd = null;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            if ( q.list()!=null && !q.list().isEmpty() ) {
                cxcd = procesarCxcd(q.list()); 
            }
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (cxcd);
    } // executeHQLQueryGetCxcd().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private CxcdDat procesarCxcd(List qList) {
        CxcdDat cxcd = null;  
        for ( Object o: qList ) {
            cxcd = (CxcdDat) o;
            String codigo = cxcd.getCodigo();
            String codigoContable = cxcd.getCodigoContable(); 
            String nombreCliProv = cxcd.getNombreCliProv(); 
            String nombreCliProv01 = cxcd.getNombreCliProv01(); 
            String razonSocial = cxcd.getRazonSocial(); 
            String direccion1 = cxcd.getDireccion1();
            String direccion2 = cxcd.getDireccion2();
            String direccion3 = cxcd.getDireccion3();
            String telefono = cxcd.getTelefono();
            String cedula = cxcd.getCedula();
            String fechaEmisionRif = cxcd.getFechaEmisionRif();
            String fechaVencRif = cxcd.getFechaVencRif();
            BigDecimal montoCompras = cxcd.getMontoCompras();
            BigDecimal limiteCredito = cxcd.getLimiteCredito();
            String tipoDeCliente = cxcd.getTipoDeCliente();
            BigDecimal totalDebitos = cxcd.getTotalDebitos();
            BigDecimal totalCreditos = cxcd.getTotalCreditos();
            BigDecimal debitosPeriodo = cxcd.getDebitosPeriodo();
            BigDecimal creditosPeriodo = cxcd.getCreditosPeriodo();
            String tipoProveedor = cxcd.getTipoProveedor();
            String personaContacto = cxcd.getPersonaContacto();
            String tiempoEntrega = cxcd.getTiempoEntrega();
            String origen = cxcd.getOrigen();
            String ubicGeograf = cxcd.getUbicGeograf();
            String pais = cxcd.getPais();
            String estado = cxcd.getEstado();
            String ciudad = cxcd.getCiudad();
            String municipio = cxcd.getMunicipio();
            String codPostal= cxcd.getCodPostal();
            String contribuyente = cxcd.getContribuyente();
            String nit = cxcd.getNit();
            String nroPatente = cxcd.getNroPatente();
            String agenteRetencion = cxcd.getAgenteRetencion();
            Date fechaActExpediente = cxcd.getFechaActExpediente();
            cxcd = new CxcdDat(codigo,codigoContable,nombreCliProv,nombreCliProv01,razonSocial,direccion1,direccion2,direccion3,telefono,cedula,fechaEmisionRif,fechaVencRif,montoCompras,limiteCredito,tipoDeCliente,totalDebitos,totalCreditos,debitosPeriodo,creditosPeriodo,tipoProveedor,personaContacto,tiempoEntrega,origen,ubicGeograf,pais,estado,ciudad,municipio,codPostal,contribuyente,nit,nroPatente,agenteRetencion,fechaActExpediente);  
        }  // for. 
        return(cxcd);
    }  // procesarCxcd(). 
    
}

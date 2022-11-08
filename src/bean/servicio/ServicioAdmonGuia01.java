/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.interfase.IServicioAdmonGuia01;
import bean.utilitario.hibernate.HibernateUtil;
import bean.utilitario.libreria.LibreriaHP;
import bean.entidad.Guias01Dat;
import entidad.Guias02Dat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonGuia01 implements IServicioAdmonGuia01 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    //public List<Guias01Dat> getGuiasPorFacturar() {
    public List<String[]> getGuiasPorFacturar() {
        final String QUERY = "select tabla1.c1Guia as nroGuia, tabla1.c1FechaGuia as fecha, tabla2.nombreCliProv as nombreCliente from Guias01Dat tabla1, CxcdDat tabla2 where ( tabla1.c1CodigoCliente  = tabla2.codigo and tabla1.c1CodigoCliente!='C-9999' and tabla1.c1Status='D' )";
        //final String QUERY = "from Guias01Dat tabla1, CxcdDat tabla2 where ( tabla1.c1CodigoCliente  = tabla2.codigo and tabla1.c1Status='D' )"; 
        //final String QUERY = "from Guias01Dat where c1Status='D'"; 
        return (executeHQLQueryGuia01(QUERY));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<String[]> executeHQLQueryGuia01(String hql) {
        //List<Guias01Dat> resultList = new ArrayList<Guias01Dat>();
        List<String[]> resultList = new ArrayList<String[]>();
        //String[] resultList; 
        resultList = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (q.list() != null && !q.list().isEmpty()) {
                resultList = procesarGuiasFacturar(q.list());
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (resultList);
    } // executeHQLQueryGuia01(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<String[]> procesarGuiasFacturar(List lista) {
    //private <Guias01Dat> procesarGuiasFacturar(List lista) {
        LibreriaHP libHP = new LibreriaHP();
        Guias01Dat guia01 = null;
        String renglon[];
        List<String[]> listaResultante = new ArrayList<>();
        if (lista == null || lista.isEmpty()) {
            System.err.println("***ATENCION: Lista de datos VACIA.****");
        } else {
            /* Ier Intento: Error. (Brqto: 07/10/2014 ). 
             for (Object o : lista) {
             guia01 = (Guias01Dat) o;
             String nroGuia = guia01.getC1Guia();  
             java.util.Date fecha = guia01.getC1FechaGuia();  
             //guia01 = new Guias01Dat(guia01.getC1Guia(), guia01.getC1FechaGuia(), guia01.getNombreCliente());
             guia01 = new Guias01Dat(nroGuia,fecha); 
             System.out.println("***guia="+nroGuia+"**Cliente=**"+guia01.getNombreCliente()+"**fecha Guia=*"+fecha+"****");
             listaGuias01.add(guia01);
             }  // for.  
             */
            /*  2do. Intento: Error. (Brqto: 07/10/2014 ). 
             for ( int i=0; i<=4; i++ ) {
             //Guias01Dat g = new Guias01Dat(); 
             Object[] o; 
             //g = (Guias01Dat) lista.get(i);  
             //System.out.println("****?="+g.getC1Guia()+"*****");
             o = (Object[]) lista.get(i); 
             System.out.println("****?="+lista.get(i)+"****o?=*"+o[1]+"****");
             }
             */
            List<Object[]> l2 = lista;
            for (Object[] g : l2) {
                //System.out.println("**l2.gNro=**" + g[0] + "**l2.Fecha=***" + g[1] + "**Nombre Cliente=***" + g[2] + "****");
                String guia = (String) g[0];
                java.util.Date fechaAux = (java.util.Date) g[1];
                String fechaS = libHP.formatoFecha.format(fechaAux);
                String cliente = (String) g[2];
                renglon = new String[] {guia,fechaS,cliente}; 
                listaResultante.add((String[]) renglon);
            }
        }  // if-else.
        return (listaResultante);
    }  // procesarGuiasFacturar().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean guiaRegistrada(String nroGuia) {
        final String QUERY = "from Guias01Dat where c1Guia='"+nroGuia+"'"; 
        return (executeHQLQueryFindGuia01(QUERY));
    }  // guiaRegistrada().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean executeHQLQueryFindGuia01(String hql) {
        //String[] resultList; 
        Boolean ok = Boolean.FALSE;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (q.list() != null && !q.list().isEmpty()) {
                ok = Boolean.TRUE;  
            }
            //displayResult(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (ok);
    } // executeHQLQueryGuia01().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Guias01Dat getGuiaDespacho(String nroGuia) {
        final String QUERY = "from Guias01Dat where c1Guia='"+nroGuia+"'"; 
        return (executeHQLQueryGetGuia01(QUERY));
    } // getGuiaDespacho().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Guias01Dat executeHQLQueryGetGuia01(String hql) {
        Guias01Dat resultante = null;  
        Boolean ok = Boolean.FALSE;  
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (q.list() != null && !q.list().isEmpty()) {
                resultante = procesarGuia01(q.list());  
            }
            //displayResult(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (resultante);
    } // executeHQLQueryGetGuia01().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Guias01Dat procesarGuia01(List lista) {
        Guias01Dat guia01=null;
        if ( lista==null || lista.isEmpty() ) {
            System.err.println("***ATENCION: Lista -GUIA01- vacia o revisar por inconsistencia de informacion*****");
        } else {
            for ( Object o : lista ) {
                guia01 = (Guias01Dat) o; 
                String c1Guia = guia01.getC1Guia();  
                java.util.Date c1FechaGuia = guia01.getC1FechaGuia(); 
                String c1ClienteEspecial = guia01.getC1ClienteEspecial();
                String c1CodigoCliente = guia01.getC1CodigoCliente();
                String c1RazonSocial = guia01.getC1RazonSocial();
                String c1Rif = guia01.getC1Rif();
                String c1Nit = guia01.getC1Nit();
                String c1Ncf = guia01.getC1Ncf();
                String c1DirFiscal1 = guia01.getC1DirFiscal1();
                String c1DirFiscal2 = guia01.getC1DirFiscal2();
                String c1DirFiscal3 = guia01.getC1DirFiscal3();
                String c1RetiradoPlanta = guia01.getC1RetiradoPlanta();
                String c1Entrega1 = guia01.getC1Entrega1();
                String c1Entrega2 = guia01.getC1Entrega2(); 
                String c1Entrega3 = guia01.getC1Entrega3();
                String c1Entrega4 = guia01.getC1Entrega4();
                String c1OrdenCompra = guia01.getC1OrdenCompra();
                String c1Pedido1 = guia01.getC1Pedido1();
                String c1Pedido2 = guia01.getC1Pedido2();
                String c1Pedido3 = guia01.getC1Pedido3();
                String c1Pedido4 = guia01.getC1Pedido4();
                String c1Pedido5 = guia01.getC1Pedido5();
                String c1Pedido6 = guia01.getC1Pedido6();
                String c1Orden1 = guia01.getC1Orden1();
                String c1Orden2 = guia01.getC1Orden2();
                String c1FormaPago = guia01.getC1FormaPago();
                Short c1Plazo = guia01.getC1Plazo();
                String c1Vendedor = guia01.getC1Vendedor();
                String c1NombreVendedor = guia01.getC1NombreVendedor();
                String c1Zona = guia01.getC1Zona();
                BigDecimal c1Alicuota = guia01.getC1Alicuota();
                String c1Factura = guia01.getC1Factura();
                java.util.Date c1FechaFactura = guia01.getC1FechaFactura();
                String c1NombreChofer = guia01.getC1NombreChofer();
                String c1CiChofer = guia01.getC1CiChofer();
                String c1CodTransp = guia01.getC1CodTransp();
                String c1NombreTransp = guia01.getC1NombreTransp();
                String c1TipoTransporte = guia01.getC1TipoTransporte();
                String c1CodCamion = guia01.getC1CodCamion();
                String c1TipoCamion = guia01.getC1TipoCamion();
                String c1NoEjes = guia01.getC1NoEjes();
                BigDecimal c1Capacidad = guia01.getC1Capacidad();
                String c1PlacaChuto = guia01.getC1PlacaChuto();
                String c1PlacaBatea = guia01.getC1PlacaBatea();
                java.util.Date c1FechaRelacion = guia01.getC1FechaRelacion();
                String c1CodDestino = guia01.getC1CodDestino();
                String c1CodSector = guia01.getC1CodSector();  
                String c1NombreDestino = guia01.getC1NombreDestino();
                String c1Estado = guia01.getC1Estado();
                String c1GuiaReparto = guia01.getC1GuiaReparto();
                String c1GuiaOrigenReparto = guia01.getC1GuiaOrigenReparto();
                String c1SerialTicket1 = guia01.getC1SerialTicket1();
                BigDecimal c1PesoTara = guia01.getC1PesoTara();
                String c1SerialTicket2 = guia01.getC1SerialTicket2();
                BigDecimal c1PesoBruto = guia01.getC1PesoBruto();
                String c1Status = guia01.getC1Status();
                String c1Observacion = guia01.getC1Observacion();
                String c1ObservacionRomana1 = guia01.getC1ObservacionRomana1();
                String c1FleteProcesado = guia01.getC1FleteProcesado();
                //System.out.println("*****Guia="+c1Guia+"***fecha="+c1FechaGuia+"****");
                //  private Set guias02Dats = new HashSet(0);
                Set<Guias02Dat> guia02Dats = (Set<Guias02Dat>) guia01.getGuias02Dats(); 
            } // for.           
        }  // if-else. 
        return (guia01);
    }  // procesarGuia01(). 
    
}

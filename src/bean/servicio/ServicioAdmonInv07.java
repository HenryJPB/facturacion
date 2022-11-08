/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.entidad.Inv07Dat;
import bean.entidad.Inv08Dat;
import bean.interfase.IServicioAdmonInv07;
import bean.utilitario.hibernate.HibernateUtil;
import bean.utilitario.libreria.LibreriaHP;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
public class ServicioAdmonInv07 implements IServicioAdmonInv07 {

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    LibreriaHP libHP = new LibreriaHP(); 
    /*
     //--------------------------------------------------------------------------
     //--------------------------------------------------------------------------
     private List<Cliente> runQueryBasedOnClientesActivo() {
     String codClienteLike = "";
     if ( this.txtCodCliente.getText()!=null && !this.txtCodCliente.getText().isEmpty() ) {
     codClienteLike = this.txtCodCliente.getText().toUpperCase();  
     } 
     String nombreClienteLike = ""; 
     if ( this.txtNombreCliente.getText()!=null && !this.txtNombreCliente.getText().isEmpty() ) {
     nombreClienteLike = this.txtNombreCliente.getText().toUpperCase();  
     }
     String QUERY_CLIENTES_ACTIVO = "from CxcdDat where subStr( codigo,1,1 ) = 'C' and tipoDeCliente ='A' and codigo!='C-9999' and codigo like '"+codClienteLike + "%' and nombreCliProv like '" + nombreClienteLike +  "%' order by nombreCliProv asc";
     return ( executeHQLQuery(QUERY_CLIENTES_ACTIVO) );
     }
     */

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean facturaRegistrada(String ncf, String nroFactura, Date fechaFactura) {
        String QUERY_FIND_FACTURA = null;
        if ( ( nroFactura!=null || !nroFactura.isEmpty() ) && fechaFactura==null && ( ncf==null || ncf.isEmpty() ) ) {
            QUERY_FIND_FACTURA = "from Inv07Dat where c7Factura like '" + nroFactura + "%' ";
        } else if ( ( nroFactura==null || nroFactura.isEmpty() ) && fechaFactura!=null && ( ncf==null || ncf.isEmpty() ) ) {
            QUERY_FIND_FACTURA = "from Inv07Dat where c7Fecha = '" + formatoFecha.format(fechaFactura) + "' ";
        } else if ( ( nroFactura==null || nroFactura.isEmpty() ) && fechaFactura==null && ( ncf!=null || !ncf.isEmpty() ) ) {
            QUERY_FIND_FACTURA = "from Inv07Dat where c7Ncf like '" + ncf + "%' ";
        } // if-else.  
        return (executeHQLQueryFind(QUERY_FIND_FACTURA));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean detalleRegistrado(String nroFactura) {
        final String QUERY = "from Inv08Dat where id.c8Factura= '"+nroFactura+"' and ROWNUM=1";
        return (executeHQLQueryFind(QUERY)); 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<Inv07Dat> getlistaFacturas(String ncf, String nroFactura, Date fechaFactura) {
        //List<Inv07Dat> lista = new ArrayList<Inv07Dat>();  
        String QUERY_FACTURA = null;
        if ( ( nroFactura!=null || !nroFactura.isEmpty() ) && fechaFactura==null && ( ncf==null || ncf.isEmpty() ) ) {
            QUERY_FACTURA = "from Inv07Dat where c7Factura like '" + nroFactura + "%' ";
        } else if ( ( nroFactura==null || nroFactura.isEmpty() ) && fechaFactura!=null && ( ncf==null || ncf.isEmpty() ) ) {
            QUERY_FACTURA = "from Inv07Dat where c7Fecha = '" + formatoFecha.format(fechaFactura) + "' ";
        } else if ( ( nroFactura==null || nroFactura.isEmpty() ) && fechaFactura==null && ( ncf!=null || !ncf.isEmpty() ) ) {
            QUERY_FACTURA = "from Inv07Dat where c7Ncf like '" + ncf + "%' ";
        } // if-else.  
        return (executeHQLQueryListaInv07(QUERY_FACTURA));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Inv07Dat getInv07(String ncf, String nroFactura, Date fechaFactura) {
        String QUERY_FACTURA;
        if (fechaFactura != null) {
            QUERY_FACTURA = "from Inv07Dat where c7Factura='" + nroFactura + "' and c7Ncf='" + ncf + "' and c7Fecha = '" + formatoFecha.format(fechaFactura) + "'";
        } else {
            QUERY_FACTURA = "from Inv07Dat where c7Factura='" + nroFactura + "' and c7Ncf='" + ncf + "'";
        } // if-else. 
        return (executeHQLQueryInv07(QUERY_FACTURA));
    }  // getInv07().  

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
    private List<Inv07Dat> executeHQLQueryListaInv07(String hql) {
        List<Inv07Dat> resultList = new ArrayList<Inv07Dat>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            resultList = procesarListaFacturas(q.list());
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (resultList);
    } // executeHQLQueryFind().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat executeHQLQueryInv07(String hql) {
        Inv07Dat resultInv07 = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            //displayResult(resultList);
            resultInv07 = procesarInv07(q.list());
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (resultInv07);
    } // executeHQLQueryListaInv07().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private List<Inv07Dat> procesarListaFacturas(List q) {
        Inv07Dat inv07Dat = null;
        //Inv08Dat inv08Dats = null;
        //Set<Inv08Dat> inv08Dats = (Set<Inv08Dat> ) inv07Dat.getInv08Dats();    // (*).  
        List<Inv07Dat> lista = new ArrayList<Inv07Dat>();
        if (q == null || q.isEmpty()) {
            System.err.println("** ATENCION: lista de facturas VACIA o inconsistencia de informacion. **");
        } else {
            for (Object o : q) {
                inv07Dat = (Inv07Dat) o;
                String c7Factura = inv07Dat.getC7Factura();
                java.sql.Date c7Fecha = (java.sql.Date) inv07Dat.getC7Fecha();
                String c7ClienteEspecial = inv07Dat.getC7ClienteEspecial();
                String c7CodigoCliente = inv07Dat.getC7CodigoCliente();
                String c7RazonSocial = inv07Dat.getC7RazonSocial();
                String c7Rif = inv07Dat.getC7Rif();
                String c7Nit = inv07Dat.getC7Nit();
                String c7Ncf = inv07Dat.getC7Ncf();
                String c7RetiradoPlanta = inv07Dat.getC7RetiradoPlanta();
                String c7DirFiscal1 = inv07Dat.getC7DirFiscal1();
                String c7DirFiscal2 = inv07Dat.getC7DirFiscal2();
                String c7DirFiscal3 = inv07Dat.getC7DirFiscal3();
                String c7Entrega1 = inv07Dat.getC7Entrega1();
                String c7Entrega2 = inv07Dat.getC7Entrega2();
                String c7Entrega3 = inv07Dat.getC7Entrega3();
                String c7Entrega4 = inv07Dat.getC7Entrega4();
                String c7Orden1 = inv07Dat.getC7Orden1();
                String c7Orden2 = inv07Dat.getC7Orden2();
                String c7Financiar = inv07Dat.getC7Financiar();
                String c7CondicionesPago = inv07Dat.getC7CondicionesPago();
                String c7Vendedor = inv07Dat.getC7Vendedor();
                String c7Nombre = inv07Dat.getC7Nombre();
                String c7Zona = inv07Dat.getC7Zona();
                String c7OrdenCompra1 = inv07Dat.getC7OrdenCompra1();
                String c7OrdenCompra2 = inv07Dat.getC7OrdenCompra2();
                String c7Pedido1 = inv07Dat.getC7Pedido1();
                String c7Pedido2 = inv07Dat.getC7Pedido2();
                BigDecimal c7DescPerCent = inv07Dat.getC7DescPerCent();
                BigDecimal c7PerCentFlete = inv07Dat.getC7PerCentFlete();
                Short c7Plazo = inv07Dat.getC7Plazo();
                String c7Guia1 = inv07Dat.getC7Guia1();
                String c7Guia2 = inv07Dat.getC7Guia2();
                java.sql.Date c7FechaGuia = (java.sql.Date) inv07Dat.getC7FechaGuia();
                BigDecimal c7ReconociFlete = inv07Dat.getC7ReconociFlete();
                BigDecimal c7MontoFlete = inv07Dat.getC7MontoFlete();
                BigDecimal c7MontoAjuste = inv07Dat.getC7MontoAjuste();
                BigDecimal c7Descuento = inv07Dat.getC7Descuento();
                BigDecimal c7Ivm = inv07Dat.getC7Ivm();
                BigDecimal c7Anticipo = inv07Dat.getC7Anticipo();
                BigDecimal c7IvmAnt = inv07Dat.getC7IvmAnt();
                BigDecimal c7CambioMoneda = inv07Dat.getC7CambioMoneda();
                String c7Nc = inv07Dat.getC7Nc(); 
                String c7Status = inv07Dat.getC7Status();
                Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
                /* La siguiente instruccion for, funciono correctamente en Brqto: 26/08/2014:  
                 for ( Inv08Dat inv08Dat : inv08Dats ) {
                 System.out.println("Cod Prod: "+inv08Dat.getId().getC8Codigo()+"****Descrip. Prod="+inv08Dat.getC8Descripcion()+"******");   // Eeeeeeeeeeeexitoooooooooooo. en Brqto, 26-08-2014. Cooorrreccctoooo !!!.  
                 }
                 */
                //
                //lista.add( new Inv07Dat(c7Factura, c7Fecha, c7ClienteEspecial, c7CodigoCliente, c7RazonSocial, c7Rif, c7Nit, c7Ncf, c7RetiradoPlanta, c7DirFiscal1, c7DirFiscal2, c7DirFiscal3, c7Entrega1, c7Entrega2, c7Entrega3, c7Entrega4, c7Orden1, c7Orden2, c7Financiar, c7CondicionesPago, c7Vendedor, c7Nombre, c7Zona, c7OrdenCompra1, c7OrdenCompra2, c7Pedido1, c7Pedido2, c7DescPerCent, c7Plazo, c7Guia1, c7Guia2, c7FechaGuia, c7ReconociFlete, c7Descuento, c7Ivm, c7Anticipo, c7IvmAnt, c7CambioMoneda, c7Status, (Set) inv08Dats) );    
                lista.add(new Inv07Dat(c7Factura, c7Fecha, c7ClienteEspecial, c7CodigoCliente, c7RazonSocial, c7Rif, c7Nit, c7Ncf, c7RetiradoPlanta, c7DirFiscal1, c7DirFiscal2, c7DirFiscal3, c7Entrega1, c7Entrega2, c7Entrega3, c7Entrega4, c7Orden1, c7Orden2, c7Financiar, c7CondicionesPago, c7Vendedor, c7Nombre, c7Zona, c7OrdenCompra1, c7OrdenCompra2, c7Pedido1, c7Pedido2, c7DescPerCent, c7PerCentFlete, c7Plazo, c7Guia1, c7Guia2, c7FechaGuia, c7MontoFlete, c7ReconociFlete, c7Descuento, c7MontoAjuste, c7Ivm, c7Anticipo, c7IvmAnt, c7CambioMoneda, c7Nc, c7Status, inv08Dats));
            }  // for.  
        } // if-else.  
        return (lista);
    }  // procesarListaFacturas().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat procesarInv07(List q) {
        Inv07Dat inv07Dat = null;
        //Inv08Dat inv08Dats = null;
        //Set<Inv08Dat> inv08Dats = (Set<Inv08Dat> ) inv07Dat.getInv08Dats();    // (*).  
        List<Inv07Dat> lista = new ArrayList<Inv07Dat>();
        if (q == null || q.isEmpty()) {
            System.err.println("** ATENCION: Factura NO encontrado o inconsistencia de informacion. **");
        } else {
            for (Object o : q) {
                inv07Dat = (Inv07Dat) o;
                String c7Factura = inv07Dat.getC7Factura();
                java.sql.Date c7Fecha = (java.sql.Date) inv07Dat.getC7Fecha();
                String c7ClienteEspecial = inv07Dat.getC7ClienteEspecial();
                String c7CodigoCliente = inv07Dat.getC7CodigoCliente();
                String c7RazonSocial = inv07Dat.getC7RazonSocial();
                String c7Rif = inv07Dat.getC7Rif();
                String c7Nit = inv07Dat.getC7Nit();
                String c7Ncf = inv07Dat.getC7Ncf();
                String c7RetiradoPlanta = inv07Dat.getC7RetiradoPlanta();
                String c7DirFiscal1 = inv07Dat.getC7DirFiscal1();
                String c7DirFiscal2 = inv07Dat.getC7DirFiscal2();
                String c7DirFiscal3 = inv07Dat.getC7DirFiscal3();
                String c7Entrega1 = inv07Dat.getC7Entrega1();
                String c7Entrega2 = inv07Dat.getC7Entrega2();
                String c7Entrega3 = inv07Dat.getC7Entrega3();
                String c7Entrega4 = inv07Dat.getC7Entrega4();
                String c7Orden1 = inv07Dat.getC7Orden1();
                String c7Orden2 = inv07Dat.getC7Orden2();
                String c7Financiar = inv07Dat.getC7Financiar();
                String c7CondicionesPago = inv07Dat.getC7CondicionesPago();
                String c7Vendedor = inv07Dat.getC7Vendedor();
                String c7Nombre = inv07Dat.getC7Nombre();
                String c7Zona = inv07Dat.getC7Zona();
                String c7OrdenCompra1 = inv07Dat.getC7OrdenCompra1();
                String c7OrdenCompra2 = inv07Dat.getC7OrdenCompra2();
                String c7Pedido1 = inv07Dat.getC7Pedido1();
                String c7Pedido2 = inv07Dat.getC7Pedido2();
                BigDecimal c7DescPerCent = inv07Dat.getC7DescPerCent();
                BigDecimal c7PerCentFlete = inv07Dat.getC7PerCentFlete();
                Short c7Plazo = inv07Dat.getC7Plazo();
                String c7Guia1 = inv07Dat.getC7Guia1();
                String c7Guia2 = inv07Dat.getC7Guia2();
                java.sql.Date c7FechaGuia = (java.sql.Date) inv07Dat.getC7FechaGuia();
                BigDecimal c7ReconociFlete = inv07Dat.getC7ReconociFlete();
                BigDecimal c7MontoFlete = inv07Dat.getC7MontoFlete();
                BigDecimal c7MontoAjuste = inv07Dat.getC7MontoAjuste();
                BigDecimal c7Descuento = inv07Dat.getC7Descuento();
                BigDecimal c7Ivm = inv07Dat.getC7Ivm();
                BigDecimal c7Anticipo = inv07Dat.getC7Anticipo();
                BigDecimal c7IvmAnt = inv07Dat.getC7IvmAnt();
                BigDecimal c7CambioMoneda = inv07Dat.getC7CambioMoneda();
                String c7Nc = inv07Dat.getC7Nc(); 
                String c7Status = inv07Dat.getC7Status();
                Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
                /* La siguiente instruccion for, funciono correctamente en Brqto: 26/08/2014:  
                 for ( Inv08Dat inv08Dat : inv08Dats ) {
                 System.out.println("Cod Prod: "+inv08Dat.getId().getC8Codigo()+"****Descrip. Prod="+inv08Dat.getC8Descripcion()+"******");   // Eeeeeeeeeeeexitoooooooooooo. en Brqto, 26-08-2014. Cooorrreccctoooo !!!.  
                 }
                 */
                //
                inv07Dat = new Inv07Dat(c7Factura, c7Fecha, c7ClienteEspecial, c7CodigoCliente, c7RazonSocial, c7Rif, c7Nit, c7Ncf, c7RetiradoPlanta, c7DirFiscal1, c7DirFiscal2, c7DirFiscal3, c7Entrega1, c7Entrega2, c7Entrega3, c7Entrega4, c7Orden1, c7Orden2, c7Financiar, c7CondicionesPago, c7Vendedor, c7Nombre, c7Zona, c7OrdenCompra1, c7OrdenCompra2, c7Pedido1, c7Pedido2, c7DescPerCent, c7PerCentFlete, c7Plazo, c7Guia1, c7Guia2, c7FechaGuia, c7MontoFlete, c7ReconociFlete, c7Descuento, c7MontoAjuste, c7Ivm, c7Anticipo, c7IvmAnt, c7CambioMoneda, c7Nc, c7Status, inv08Dats);
            }  // for.  
        }  // if-else. 
        return (inv07Dat);
    } // procesarInv07().  

    //--------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    @Override
    public Inv07Dat getNextFactura() {
        final String QUERY = "from Inv07Dat where c7Fecha= ( select MAX(c7Fecha) from Inv07Dat )";
        return (executeHQLQueryNextFactura(QUERY));
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat executeHQLQueryNextFactura(String hql) {
        //BigDecimal suma = BigDecimal.ZERO;
        Inv07Dat inv07Dat = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (q.list() != null || !q.list().isEmpty()) {
                inv07Dat = procesarNextFactura(q.list());
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv07Dat);
    } // executeHQLQueryNextFactura().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat procesarNextFactura(List lista) {
        Inv07Dat inv07Dat = null;
        Long nextFacturaN = new Long(0);
        Long nextNcfN = new Long(0); 
        if (lista == null || lista.isEmpty()) {
            System.err.println("** ATENCION: Factura NO encontrado o inconsistencia de informacion. **");
        } else {
            for ( Object o : lista ) {
                 inv07Dat = ( Inv07Dat ) o;
                 if ( libHP.esNumerico(inv07Dat.getC7Factura()) ) {
                        //System.out.println("****Next Factura="+inv07Dat.getC7Factura()+"*****");
                        nextFacturaN = Long.parseLong(inv07Dat.getC7Factura())+1; 
                        //System.out.println("****next Factura Numerica="+nextFacturaN+"*****"); 
                 }
                 if ( libHP.esNumerico(inv07Dat.getC7Ncf()) ) {
                        nextNcfN = Long.parseLong(inv07Dat.getC7Ncf())+1; 
                 }
            }  // for
        } // if-else
        String nextFactura = libHP.formatoFactura.format(nextFacturaN);
        String nextNcf = libHP.formatoNcf.format(nextNcfN); 
        inv07Dat = new Inv07Dat(nextFactura,nextNcf); 
        return (inv07Dat);
    } // procesarNextFactura().  

        //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Inv07Dat getTotales(String nroFactura
    ) {
        String QUERY = "from Inv07Dat where c7Factura ='" + nroFactura + "'";
        return (executeHQLQueryTotales(QUERY));
    }
        //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    private Inv07Dat executeHQLQueryTotales(String hql) {
        //BigDecimal suma = BigDecimal.ZERO;
        Inv07Dat inv07Dat = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            if (q.list() != null || !q.list().isEmpty()) {
                inv07Dat = procesarTotales(q.list());
            }
            //displayResult(resultList);
            //desplegarResultado(resultList);
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return (inv07Dat);
    } // executeHQLQuerySum().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Inv07Dat procesarTotales(List q) {
        BigDecimal sumPesoGuia = BigDecimal.ZERO;
        BigDecimal flete;
        BigDecimal sumFlete = BigDecimal.ZERO;
        BigDecimal ajuste;
        BigDecimal sumAjuste = BigDecimal.ZERO;
        BigDecimal montoFactura = BigDecimal.ZERO;
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal depGarantia = BigDecimal.ZERO;
        BigDecimal totalFactura = BigDecimal.ZERO;
        Integer cantidad = 0;
        BigDecimal precio = BigDecimal.ZERO;
        BigDecimal peso = BigDecimal.ZERO;
        BigDecimal acumulador = BigDecimal.ZERO;
        Inv07Dat inv07Dat = null;
        for (Object o : q) {
            inv07Dat = (Inv07Dat) o;
            Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
            for (Inv08Dat inv08Dat : inv08Dats) {
                if (inv08Dat.getC8PesoGuia() != null) {
                    //suma = suma + inv08Dat.getC8PesoGuia();
                    sumPesoGuia = sumPesoGuia.add(inv08Dat.getC8PesoGuia());
                }
                flete = BigDecimal.ZERO;
                if (inv08Dat.getC8Flete() != null) {
                    flete = inv08Dat.getC8Flete();
                    sumFlete = sumFlete.add(inv08Dat.getC8Flete());
                }
                ajuste = BigDecimal.ZERO;
                if (inv08Dat.getC8Ajuste() != null) {
                    ajuste = inv08Dat.getC8Ajuste();
                    sumAjuste = sumAjuste.add(inv08Dat.getC8Ajuste());
                }
                cantidad = inv08Dat.getC8Unidades();
                peso = inv08Dat.getC8Peso();
                precio = inv08Dat.getC8Precio();
                montoFactura = precio;
                if (inv08Dat.getC8Fxunidad() != null && !inv08Dat.getC8Fxunidad().isEmpty() && inv08Dat.getC8Fxunidad().equals("X")) {
                    montoFactura = montoFactura.multiply(new BigDecimal(cantidad));
                }
                if (inv08Dat.getC8Fxpeso() != null && !inv08Dat.getC8Fxpeso().isEmpty() && inv08Dat.getC8Fxpeso().equals("X")) {
                    montoFactura = montoFactura.multiply(peso);
                }
                montoFactura = montoFactura.add(flete);
                montoFactura = montoFactura.add(ajuste);
                acumulador = acumulador.add(montoFactura);
            }  // for interno. 
        }  // for externo.  
        inv07Dat.setSumPesoGuia(sumPesoGuia);
        inv07Dat.setSumFlete(sumFlete);
        inv07Dat.setSumAjuste(sumAjuste);
        montoFactura = acumulador;
        subTotal = acumulador;
        //subTotal = subTotal.add(sumFlete); 
        //subTotal = subTotal.add(sumAjuste); 
        //if ( inv07Dat.getC7MontoFlete()!=null ) subTotal = subTotal.add(inv07Dat.getC7MontoFlete()); 
        if (inv07Dat.getC7Descuento() != null) {
            subTotal = subTotal.subtract(inv07Dat.getC7Descuento());
        }
        if (inv07Dat.getC7ReconociFlete() != null) {
            subTotal = subTotal.subtract(inv07Dat.getC7ReconociFlete());
        }
        if (inv07Dat.getC7Ivm() != null) {
            subTotal = subTotal.add(inv07Dat.getC7Ivm());
        }
        if (inv07Dat.getC7Anticipo() != null) {
            depGarantia = inv07Dat.getC7Anticipo();
        }
        if (inv07Dat.getC7IvmAnt() != null) {
            depGarantia = depGarantia.add(inv07Dat.getC7IvmAnt());
        }
        totalFactura = subTotal;
        totalFactura = totalFactura.subtract(depGarantia);
        // ** set TOTALES: **
        inv07Dat.setMontoFactura(montoFactura);
        inv07Dat.setSubTotal(subTotal);
        inv07Dat.setDepGarantia(depGarantia);
        inv07Dat.setTotalFactura(totalFactura);
        return (inv07Dat);
    }  // procesarTotales().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void agregarOld(Inv07Dat inv07Dat) {
        System.out.println("ESTOY dentro del METODO master");
        System.out.println("*****inv07.Fact="+inv07Dat.getC7Factura()+"**fecha="+inv07Dat.getC7Fecha()+"**ncf="+inv07Dat.getC7Ncf()+"***");
        System.out.println("ESTOY dentro del METODO detalle");
        System.out.println("*******************************");
        Set<Inv08Dat> inv08Dats = (Set<Inv08Dat>) inv07Dat.getInv08Dats();
        System.out.println("***hijos count="+inv08Dats.size()+"***");
        for ( Inv08Dat inv08 : inv08Dats ) {  
              System.out.println("***factura="+inv08.getId().getC8Factura()+"****no.Item="+inv08.getId().getC8ItemNo()+"**codigo="+inv08.getId().getC8Codigo()+"*****");
        }  // for.
        // 1ra  PARTE: (Registrar el dato MASTER)*******************************
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Inv07Dat inv07Aux = inv07Dat; 
        inv07Aux.setInv08Dats(null);
        session.save(inv07Aux);   // Almacenar el nodo Padre/Cabeza. >> Exito!!!.
        session.saveOrUpdate(inv07Dat);
        //session.persist(inv07Dat);
        //session.saveOrUpdate(inv07Dat);
        //session.save(inv07Dat.getInv08Dats());  // NO funcionó. 
        session.getTransaction().commit();
        session.close();
        //
        // 2da PARTE: (Registrar los datos DETAILS) ****************************
        /*
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //session.save(inv07Dat.getInv08Dats());  // NO funcionó. ??????
        session.saveOrUpdate(inv07Dat);
        //
        session.getTransaction().commit();
        session.close();
        */
    } // agregarOLd().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void agregar(Inv07Dat inv07Dat) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(inv07Dat);
        //session.persist(inv07Dat);
        //session.saveOrUpdate(inv07Dat);
        //session.save(inv07Dat.getInv08Dats());  // NO funcionó. 
        session.getTransaction().commit();
        session.close();
    } // agregar().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void actualizar(Inv07Dat inv07Dat) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(inv07Dat);
        session.getTransaction().commit();
        session.close();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void eliminar(Inv07Dat inv07Dat) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(inv07Dat);
        session.getTransaction().commit();
        session.close();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void eliminar(Inv08Dat inv08Dat) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(inv08Dat);
        session.getTransaction().commit();
        session.close();
    }
 
}  // servicioAdmonInv07(). 

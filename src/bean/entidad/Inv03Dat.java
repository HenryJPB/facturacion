package bean.entidad;
// Generated 05-sep-2014 14:23:58 by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Inv03Dat generated by hbm2java
 */
public class Inv03Dat  implements java.io.Serializable {


     private String c3Orden;
     private String c3Cotizacion;
     private String c3CodigoCliente;
     private String c3Atencion;
     private String c3Telefono;
     private Date c3FechaEntrega;
     private String c3DirEntrega;
     private String c3FormaPago;
     private String c3Observacion1;
     private String c3Observacion2;
     private Set inv04Dats = new HashSet(0);

    public Inv03Dat() {
    }

	
    public Inv03Dat(String c3Orden) {
        this.c3Orden = c3Orden;
    }
    public Inv03Dat(String c3Orden, String c3Cotizacion, String c3CodigoCliente, String c3Atencion, String c3Telefono, Date c3FechaEntrega, String c3DirEntrega, String c3FormaPago, String c3Observacion1, String c3Observacion2, Set inv04Dats) {
       this.c3Orden = c3Orden;
       this.c3Cotizacion = c3Cotizacion;
       this.c3CodigoCliente = c3CodigoCliente;
       this.c3Atencion = c3Atencion;
       this.c3Telefono = c3Telefono;
       this.c3FechaEntrega = c3FechaEntrega;
       this.c3DirEntrega = c3DirEntrega;
       this.c3FormaPago = c3FormaPago;
       this.c3Observacion1 = c3Observacion1;
       this.c3Observacion2 = c3Observacion2;
       this.inv04Dats = inv04Dats;
    }
   
    public String getC3Orden() {
        return this.c3Orden;
    }
    
    public void setC3Orden(String c3Orden) {
        this.c3Orden = c3Orden;
    }
    public String getC3Cotizacion() {
        return this.c3Cotizacion;
    }
    
    public void setC3Cotizacion(String c3Cotizacion) {
        this.c3Cotizacion = c3Cotizacion;
    }
    public String getC3CodigoCliente() {
        return this.c3CodigoCliente;
    }
    
    public void setC3CodigoCliente(String c3CodigoCliente) {
        this.c3CodigoCliente = c3CodigoCliente;
    }
    public String getC3Atencion() {
        return this.c3Atencion;
    }
    
    public void setC3Atencion(String c3Atencion) {
        this.c3Atencion = c3Atencion;
    }
    public String getC3Telefono() {
        return this.c3Telefono;
    }
    
    public void setC3Telefono(String c3Telefono) {
        this.c3Telefono = c3Telefono;
    }
    public Date getC3FechaEntrega() {
        return this.c3FechaEntrega;
    }
    
    public void setC3FechaEntrega(Date c3FechaEntrega) {
        this.c3FechaEntrega = c3FechaEntrega;
    }
    public String getC3DirEntrega() {
        return this.c3DirEntrega;
    }
    
    public void setC3DirEntrega(String c3DirEntrega) {
        this.c3DirEntrega = c3DirEntrega;
    }
    public String getC3FormaPago() {
        return this.c3FormaPago;
    }
    
    public void setC3FormaPago(String c3FormaPago) {
        this.c3FormaPago = c3FormaPago;
    }
    public String getC3Observacion1() {
        return this.c3Observacion1;
    }
    
    public void setC3Observacion1(String c3Observacion1) {
        this.c3Observacion1 = c3Observacion1;
    }
    public String getC3Observacion2() {
        return this.c3Observacion2;
    }
    
    public void setC3Observacion2(String c3Observacion2) {
        this.c3Observacion2 = c3Observacion2;
    }
    public Set getInv04Dats() {
        return this.inv04Dats;
    }
    
    public void setInv04Dats(Set inv04Dats) {
        this.inv04Dats = inv04Dats;
    }




}


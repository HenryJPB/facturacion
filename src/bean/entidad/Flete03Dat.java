package entidad;
// Generated 05-nov-2014 11:34:04 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Flete03Dat generated by hbm2java
 */
public class Flete03Dat  implements java.io.Serializable {


     private Date c3FechaRelacion;
     private String c3Observacion1;
     private Short c3RepartoTonCamion1;
     private BigDecimal c3RepartoPrecioTonCamion1;
     private Short c3RepartoTonGandola1;
     private BigDecimal c3RepartoPrecioTonGandola1;
     private Short c3RepartoTonToronto1;
     private BigDecimal c3RepartoPrecioTonToronto1;
     private Set flete04Dats = new HashSet(0);

    public Flete03Dat() {
    }

	
    public Flete03Dat(Date c3FechaRelacion) {
        this.c3FechaRelacion = c3FechaRelacion;
    }
    public Flete03Dat(Date c3FechaRelacion, String c3Observacion1, Short c3RepartoTonCamion1, BigDecimal c3RepartoPrecioTonCamion1, Short c3RepartoTonGandola1, BigDecimal c3RepartoPrecioTonGandola1, Short c3RepartoTonToronto1, BigDecimal c3RepartoPrecioTonToronto1, Set flete04Dats) {
       this.c3FechaRelacion = c3FechaRelacion;
       this.c3Observacion1 = c3Observacion1;
       this.c3RepartoTonCamion1 = c3RepartoTonCamion1;
       this.c3RepartoPrecioTonCamion1 = c3RepartoPrecioTonCamion1;
       this.c3RepartoTonGandola1 = c3RepartoTonGandola1;
       this.c3RepartoPrecioTonGandola1 = c3RepartoPrecioTonGandola1;
       this.c3RepartoTonToronto1 = c3RepartoTonToronto1;
       this.c3RepartoPrecioTonToronto1 = c3RepartoPrecioTonToronto1;
       this.flete04Dats = flete04Dats;
    }
   
    public Date getC3FechaRelacion() {
        return this.c3FechaRelacion;
    }
    
    public void setC3FechaRelacion(Date c3FechaRelacion) {
        this.c3FechaRelacion = c3FechaRelacion;
    }
    public String getC3Observacion1() {
        return this.c3Observacion1;
    }
    
    public void setC3Observacion1(String c3Observacion1) {
        this.c3Observacion1 = c3Observacion1;
    }
    public Short getC3RepartoTonCamion1() {
        return this.c3RepartoTonCamion1;
    }
    
    public void setC3RepartoTonCamion1(Short c3RepartoTonCamion1) {
        this.c3RepartoTonCamion1 = c3RepartoTonCamion1;
    }
    public BigDecimal getC3RepartoPrecioTonCamion1() {
        return this.c3RepartoPrecioTonCamion1;
    }
    
    public void setC3RepartoPrecioTonCamion1(BigDecimal c3RepartoPrecioTonCamion1) {
        this.c3RepartoPrecioTonCamion1 = c3RepartoPrecioTonCamion1;
    }
    public Short getC3RepartoTonGandola1() {
        return this.c3RepartoTonGandola1;
    }
    
    public void setC3RepartoTonGandola1(Short c3RepartoTonGandola1) {
        this.c3RepartoTonGandola1 = c3RepartoTonGandola1;
    }
    public BigDecimal getC3RepartoPrecioTonGandola1() {
        return this.c3RepartoPrecioTonGandola1;
    }
    
    public void setC3RepartoPrecioTonGandola1(BigDecimal c3RepartoPrecioTonGandola1) {
        this.c3RepartoPrecioTonGandola1 = c3RepartoPrecioTonGandola1;
    }
    public Short getC3RepartoTonToronto1() {
        return this.c3RepartoTonToronto1;
    }
    
    public void setC3RepartoTonToronto1(Short c3RepartoTonToronto1) {
        this.c3RepartoTonToronto1 = c3RepartoTonToronto1;
    }
    public BigDecimal getC3RepartoPrecioTonToronto1() {
        return this.c3RepartoPrecioTonToronto1;
    }
    
    public void setC3RepartoPrecioTonToronto1(BigDecimal c3RepartoPrecioTonToronto1) {
        this.c3RepartoPrecioTonToronto1 = c3RepartoPrecioTonToronto1;
    }
    public Set getFlete04Dats() {
        return this.flete04Dats;
    }
    
    public void setFlete04Dats(Set flete04Dats) {
        this.flete04Dats = flete04Dats;
    }




}



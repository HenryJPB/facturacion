package bean.entidad;
// Generated 04-sep-2014 15:29:48 by Hibernate Tools 3.6.0


import java.math.BigDecimal;

/**
 * Inv02Dat generated by hbm2java
 */
public class Inv02Dat  implements java.io.Serializable {


     private Inv02DatId id;
     private BigDecimal c2PrecioUnidad;
     private BigDecimal c2PrecioKgs;

    public Inv02Dat() {
    }

	
    public Inv02Dat(Inv02DatId id) {
        this.id = id;
    }
    public Inv02Dat(Inv02DatId id, BigDecimal c2PrecioUnidad, BigDecimal c2PrecioKgs) {
       this.id = id;
       this.c2PrecioUnidad = c2PrecioUnidad;
       this.c2PrecioKgs = c2PrecioKgs;
    }
   
    public Inv02DatId getId() {
        return this.id;
    }
    
    public void setId(Inv02DatId id) {
        this.id = id;
    }
    public BigDecimal getC2PrecioUnidad() {
        return this.c2PrecioUnidad;
    }
    
    public void setC2PrecioUnidad(BigDecimal c2PrecioUnidad) {
        this.c2PrecioUnidad = c2PrecioUnidad;
    }
    public BigDecimal getC2PrecioKgs() {
        return this.c2PrecioKgs;
    }
    
    public void setC2PrecioKgs(BigDecimal c2PrecioKgs) {
        this.c2PrecioKgs = c2PrecioKgs;
    }




}



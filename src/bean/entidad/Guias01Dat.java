package bean.entidad;
// Generated 06-oct-2014 12:18:56 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Guias01Dat generated by hbm2java
 */
public class Guias01Dat  implements java.io.Serializable {

     private String c1Guia;
     private Date c1FechaGuia;
     private String c1ClienteEspecial;
     private String c1CodigoCliente;
     private String c1RazonSocial;
     private String c1Rif;
     private String c1Nit;
     private String c1Ncf;
     private String c1DirFiscal1;
     private String c1DirFiscal2;
     private String c1DirFiscal3;
     private String c1RetiradoPlanta;
     private String c1Entrega1;
     private String c1Entrega2;
     private String c1Entrega3;
     private String c1Entrega4;
     private String c1OrdenCompra;
     private String c1Pedido1;
     private String c1Pedido2;
     private String c1Pedido3;
     private String c1Pedido4;
     private String c1Pedido5;
     private String c1Pedido6;
     private String c1Orden1;
     private String c1Orden2;
     private String c1FormaPago;
     private Short c1Plazo;
     private String c1Vendedor;
     private String c1NombreVendedor;
     private String c1Zona;
     private BigDecimal c1Alicuota;
     private String c1Factura;
     private Date c1FechaFactura;
     private String c1NombreChofer;
     private String c1CiChofer;
     private String c1CodTransp;
     private String c1NombreTransp;
     private String c1TipoTransporte;
     private String c1CodCamion;
     private String c1TipoCamion;
     private String c1NoEjes;
     private BigDecimal c1Capacidad;
     private String c1PlacaChuto;
     private String c1PlacaBatea;
     private Date c1FechaRelacion;
     private String c1CodDestino;
     private String c1CodSector;
     private String c1NombreDestino;
     private String c1Estado;
     private String c1GuiaReparto;
     private String c1GuiaOrigenReparto;
     private String c1SerialTicket1;
     private BigDecimal c1PesoTara;
     private String c1SerialTicket2;
     private BigDecimal c1PesoBruto;
     private String c1Status;
     private String c1Observacion;
     private String c1ObservacionRomana1;
     private String c1FleteProcesado;
     private Set guias02Dats = new HashSet(0);
     // **
     private String nombreCliente; 

    public Guias01Dat() {
    }

	
    public Guias01Dat(String c1Guia) {
        this.c1Guia = c1Guia;
    }

    public Guias01Dat(String c1Guia, Date c1FechaGuia) {
        this.c1Guia = c1Guia;
        this.c1FechaGuia = c1FechaGuia;
    }

    
    public Guias01Dat(String c1Guia, Date c1FechaGuia, String nombreCliente) {
        this.c1Guia = c1Guia;
        this.c1FechaGuia = c1FechaGuia;
        this.nombreCliente = nombreCliente;
    }

    public Guias01Dat(String c1Guia, Date c1FechaGuia, String c1ClienteEspecial, String c1CodigoCliente, String c1RazonSocial, String c1Rif, String c1Nit, String c1Ncf, String c1DirFiscal1, String c1DirFiscal2, String c1DirFiscal3, String c1RetiradoPlanta, String c1Entrega1, String c1Entrega2, String c1Entrega3, String c1Entrega4, String c1OrdenCompra, String c1Pedido1, String c1Pedido2, String c1Pedido3, String c1Pedido4, String c1Pedido5, String c1Pedido6, String c1Orden1, String c1Orden2, String c1FormaPago, Short c1Plazo, String c1Vendedor, String c1NombreVendedor, String c1Zona, BigDecimal c1Alicuota, String c1Factura, Date c1FechaFactura, String c1NombreChofer, String c1CiChofer, String c1CodTransp, String c1NombreTransp, String c1TipoTransporte, String c1CodCamion, String c1TipoCamion, String c1NoEjes, BigDecimal c1Capacidad, String c1PlacaChuto, String c1PlacaBatea, Date c1FechaRelacion, String c1CodDestino, String c1CodSector, String c1NombreDestino, String c1Estado, String c1GuiaReparto, String c1GuiaOrigenReparto, String c1SerialTicket1, BigDecimal c1PesoTara, String c1SerialTicket2, BigDecimal c1PesoBruto, String c1Status, String c1Observacion, String c1ObservacionRomana1, String c1FleteProcesado) {
        this.c1Guia = c1Guia;
        this.c1FechaGuia = c1FechaGuia;
        this.c1ClienteEspecial = c1ClienteEspecial;
        this.c1CodigoCliente = c1CodigoCliente;
        this.c1RazonSocial = c1RazonSocial;
        this.c1Rif = c1Rif;
        this.c1Nit = c1Nit;
        this.c1Ncf = c1Ncf;
        this.c1DirFiscal1 = c1DirFiscal1;
        this.c1DirFiscal2 = c1DirFiscal2;
        this.c1DirFiscal3 = c1DirFiscal3;
        this.c1RetiradoPlanta = c1RetiradoPlanta;
        this.c1Entrega1 = c1Entrega1;
        this.c1Entrega2 = c1Entrega2;
        this.c1Entrega3 = c1Entrega3;
        this.c1Entrega4 = c1Entrega4;
        this.c1OrdenCompra = c1OrdenCompra;
        this.c1Pedido1 = c1Pedido1;
        this.c1Pedido2 = c1Pedido2;
        this.c1Pedido3 = c1Pedido3;
        this.c1Pedido4 = c1Pedido4;
        this.c1Pedido5 = c1Pedido5;
        this.c1Pedido6 = c1Pedido6;
        this.c1Orden1 = c1Orden1;
        this.c1Orden2 = c1Orden2;
        this.c1FormaPago = c1FormaPago;
        this.c1Plazo = c1Plazo;
        this.c1Vendedor = c1Vendedor;
        this.c1NombreVendedor = c1NombreVendedor;
        this.c1Zona = c1Zona;
        this.c1Alicuota = c1Alicuota;
        this.c1Factura = c1Factura;
        this.c1FechaFactura = c1FechaFactura;
        this.c1NombreChofer = c1NombreChofer;
        this.c1CiChofer = c1CiChofer;
        this.c1CodTransp = c1CodTransp;
        this.c1NombreTransp = c1NombreTransp;
        this.c1TipoTransporte = c1TipoTransporte;
        this.c1CodCamion = c1CodCamion;
        this.c1TipoCamion = c1TipoCamion;
        this.c1NoEjes = c1NoEjes;
        this.c1Capacidad = c1Capacidad;
        this.c1PlacaChuto = c1PlacaChuto;
        this.c1PlacaBatea = c1PlacaBatea;
        this.c1FechaRelacion = c1FechaRelacion;
        this.c1CodDestino = c1CodDestino;
        this.c1CodSector = c1CodSector;
        this.c1NombreDestino = c1NombreDestino;
        this.c1Estado = c1Estado;
        this.c1GuiaReparto = c1GuiaReparto;
        this.c1GuiaOrigenReparto = c1GuiaOrigenReparto;
        this.c1SerialTicket1 = c1SerialTicket1;
        this.c1PesoTara = c1PesoTara;
        this.c1SerialTicket2 = c1SerialTicket2;
        this.c1PesoBruto = c1PesoBruto;
        this.c1Status = c1Status;
        this.c1Observacion = c1Observacion;
        this.c1ObservacionRomana1 = c1ObservacionRomana1;
        this.c1FleteProcesado = c1FleteProcesado;
    }

    public Guias01Dat(String c1Guia, Date c1FechaGuia, String c1ClienteEspecial, String c1CodigoCliente, String c1RazonSocial, String c1Rif, String c1Nit, String c1Ncf, String c1DirFiscal1, String c1DirFiscal2, String c1DirFiscal3, String c1RetiradoPlanta, String c1Entrega1, String c1Entrega2, String c1Entrega3, String c1Entrega4, String c1OrdenCompra, String c1Pedido1, String c1Pedido2, String c1Pedido3, String c1Pedido4, String c1Pedido5, String c1Pedido6, String c1Orden1, String c1Orden2, String c1FormaPago, Short c1Plazo, String c1Vendedor, String c1NombreVendedor, String c1Zona, BigDecimal c1Alicuota, String c1Factura, Date c1FechaFactura, String c1NombreChofer, String c1CiChofer, String c1CodTransp, String c1NombreTransp, String c1TipoTransporte, String c1CodCamion, String c1TipoCamion, String c1NoEjes, BigDecimal c1Capacidad, String c1PlacaChuto, String c1PlacaBatea, Date c1FechaRelacion, String c1CodDestino, String c1CodSector, String c1NombreDestino, String c1Estado, String c1GuiaReparto, String c1GuiaOrigenReparto, String c1SerialTicket1, BigDecimal c1PesoTara, String c1SerialTicket2, BigDecimal c1PesoBruto, String c1Status, String c1Observacion, String c1ObservacionRomana1, String c1FleteProcesado, String nombreCliente) {
        this.c1Guia = c1Guia;
        this.c1FechaGuia = c1FechaGuia;
        this.c1ClienteEspecial = c1ClienteEspecial;
        this.c1CodigoCliente = c1CodigoCliente;
        this.c1RazonSocial = c1RazonSocial;
        this.c1Rif = c1Rif;
        this.c1Nit = c1Nit;
        this.c1Ncf = c1Ncf;
        this.c1DirFiscal1 = c1DirFiscal1;
        this.c1DirFiscal2 = c1DirFiscal2;
        this.c1DirFiscal3 = c1DirFiscal3;
        this.c1RetiradoPlanta = c1RetiradoPlanta;
        this.c1Entrega1 = c1Entrega1;
        this.c1Entrega2 = c1Entrega2;
        this.c1Entrega3 = c1Entrega3;
        this.c1Entrega4 = c1Entrega4;
        this.c1OrdenCompra = c1OrdenCompra;
        this.c1Pedido1 = c1Pedido1;
        this.c1Pedido2 = c1Pedido2;
        this.c1Pedido3 = c1Pedido3;
        this.c1Pedido4 = c1Pedido4;
        this.c1Pedido5 = c1Pedido5;
        this.c1Pedido6 = c1Pedido6;
        this.c1Orden1 = c1Orden1;
        this.c1Orden2 = c1Orden2;
        this.c1FormaPago = c1FormaPago;
        this.c1Plazo = c1Plazo;
        this.c1Vendedor = c1Vendedor;
        this.c1NombreVendedor = c1NombreVendedor;
        this.c1Zona = c1Zona;
        this.c1Alicuota = c1Alicuota;
        this.c1Factura = c1Factura;
        this.c1FechaFactura = c1FechaFactura;
        this.c1NombreChofer = c1NombreChofer;
        this.c1CiChofer = c1CiChofer;
        this.c1CodTransp = c1CodTransp;
        this.c1NombreTransp = c1NombreTransp;
        this.c1TipoTransporte = c1TipoTransporte;
        this.c1CodCamion = c1CodCamion;
        this.c1TipoCamion = c1TipoCamion;
        this.c1NoEjes = c1NoEjes;
        this.c1Capacidad = c1Capacidad;
        this.c1PlacaChuto = c1PlacaChuto;
        this.c1PlacaBatea = c1PlacaBatea;
        this.c1FechaRelacion = c1FechaRelacion;
        this.c1CodDestino = c1CodDestino;
        this.c1CodSector = c1CodSector;
        this.c1NombreDestino = c1NombreDestino;
        this.c1Estado = c1Estado;
        this.c1GuiaReparto = c1GuiaReparto;
        this.c1GuiaOrigenReparto = c1GuiaOrigenReparto;
        this.c1SerialTicket1 = c1SerialTicket1;
        this.c1PesoTara = c1PesoTara;
        this.c1SerialTicket2 = c1SerialTicket2;
        this.c1PesoBruto = c1PesoBruto;
        this.c1Status = c1Status;
        this.c1Observacion = c1Observacion;
        this.c1ObservacionRomana1 = c1ObservacionRomana1;
        this.c1FleteProcesado = c1FleteProcesado;
        this.nombreCliente = nombreCliente;
    }
    
    public String getC1Guia() {
        return this.c1Guia;
    }
    
    public void setC1Guia(String c1Guia) {
        this.c1Guia = c1Guia;
    }
    public Date getC1FechaGuia() {
        return this.c1FechaGuia;
    }
    
    public void setC1FechaGuia(Date c1FechaGuia) {
        this.c1FechaGuia = c1FechaGuia;
    }
    public String getC1ClienteEspecial() {
        return this.c1ClienteEspecial;
    }
    
    public void setC1ClienteEspecial(String c1ClienteEspecial) {
        this.c1ClienteEspecial = c1ClienteEspecial;
    }
    public String getC1CodigoCliente() {
        return this.c1CodigoCliente;
    }
    
    public void setC1CodigoCliente(String c1CodigoCliente) {
        this.c1CodigoCliente = c1CodigoCliente;
    }
    public String getC1RazonSocial() {
        return this.c1RazonSocial;
    }
    
    public void setC1RazonSocial(String c1RazonSocial) {
        this.c1RazonSocial = c1RazonSocial;
    }
    public String getC1Rif() {
        return this.c1Rif;
    }
    
    public void setC1Rif(String c1Rif) {
        this.c1Rif = c1Rif;
    }
    public String getC1Nit() {
        return this.c1Nit;
    }
    
    public void setC1Nit(String c1Nit) {
        this.c1Nit = c1Nit;
    }
    public String getC1Ncf() {
        return this.c1Ncf;
    }
    
    public void setC1Ncf(String c1Ncf) {
        this.c1Ncf = c1Ncf;
    }
    public String getC1DirFiscal1() {
        return this.c1DirFiscal1;
    }
    
    public void setC1DirFiscal1(String c1DirFiscal1) {
        this.c1DirFiscal1 = c1DirFiscal1;
    }
    public String getC1DirFiscal2() {
        return this.c1DirFiscal2;
    }
    
    public void setC1DirFiscal2(String c1DirFiscal2) {
        this.c1DirFiscal2 = c1DirFiscal2;
    }
    public String getC1DirFiscal3() {
        return this.c1DirFiscal3;
    }
    
    public void setC1DirFiscal3(String c1DirFiscal3) {
        this.c1DirFiscal3 = c1DirFiscal3;
    }
    public String getC1RetiradoPlanta() {
        return this.c1RetiradoPlanta;
    }
    
    public void setC1RetiradoPlanta(String c1RetiradoPlanta) {
        this.c1RetiradoPlanta = c1RetiradoPlanta;
    }
    public String getC1Entrega1() {
        return this.c1Entrega1;
    }
    
    public void setC1Entrega1(String c1Entrega1) {
        this.c1Entrega1 = c1Entrega1;
    }
    public String getC1Entrega2() {
        return this.c1Entrega2;
    }
    
    public void setC1Entrega2(String c1Entrega2) {
        this.c1Entrega2 = c1Entrega2;
    }
    public String getC1Entrega3() {
        return this.c1Entrega3;
    }
    
    public void setC1Entrega3(String c1Entrega3) {
        this.c1Entrega3 = c1Entrega3;
    }
    public String getC1Entrega4() {
        return this.c1Entrega4;
    }
    
    public void setC1Entrega4(String c1Entrega4) {
        this.c1Entrega4 = c1Entrega4;
    }
    public String getC1OrdenCompra() {
        return this.c1OrdenCompra;
    }
    
    public void setC1OrdenCompra(String c1OrdenCompra) {
        this.c1OrdenCompra = c1OrdenCompra;
    }
    public String getC1Pedido1() {
        return this.c1Pedido1;
    }
    
    public void setC1Pedido1(String c1Pedido1) {
        this.c1Pedido1 = c1Pedido1;
    }
    public String getC1Pedido2() {
        return this.c1Pedido2;
    }
    
    public void setC1Pedido2(String c1Pedido2) {
        this.c1Pedido2 = c1Pedido2;
    }
    public String getC1Pedido3() {
        return this.c1Pedido3;
    }
    
    public void setC1Pedido3(String c1Pedido3) {
        this.c1Pedido3 = c1Pedido3;
    }
    public String getC1Pedido4() {
        return this.c1Pedido4;
    }
    
    public void setC1Pedido4(String c1Pedido4) {
        this.c1Pedido4 = c1Pedido4;
    }
    public String getC1Pedido5() {
        return this.c1Pedido5;
    }
    
    public void setC1Pedido5(String c1Pedido5) {
        this.c1Pedido5 = c1Pedido5;
    }
    public String getC1Pedido6() {
        return this.c1Pedido6;
    }
    
    public void setC1Pedido6(String c1Pedido6) {
        this.c1Pedido6 = c1Pedido6;
    }
    public String getC1Orden1() {
        return this.c1Orden1;
    }
    
    public void setC1Orden1(String c1Orden1) {
        this.c1Orden1 = c1Orden1;
    }
    public String getC1Orden2() {
        return this.c1Orden2;
    }
    
    public void setC1Orden2(String c1Orden2) {
        this.c1Orden2 = c1Orden2;
    }
    public String getC1FormaPago() {
        return this.c1FormaPago;
    }
    
    public void setC1FormaPago(String c1FormaPago) {
        this.c1FormaPago = c1FormaPago;
    }
    public Short getC1Plazo() {
        return this.c1Plazo;
    }
    
    public void setC1Plazo(Short c1Plazo) {
        this.c1Plazo = c1Plazo;
    }
    public String getC1Vendedor() {
        return this.c1Vendedor;
    }
    
    public void setC1Vendedor(String c1Vendedor) {
        this.c1Vendedor = c1Vendedor;
    }
    public String getC1NombreVendedor() {
        return this.c1NombreVendedor;
    }
    
    public void setC1NombreVendedor(String c1NombreVendedor) {
        this.c1NombreVendedor = c1NombreVendedor;
    }
    public String getC1Zona() {
        return this.c1Zona;
    }
    
    public void setC1Zona(String c1Zona) {
        this.c1Zona = c1Zona;
    }
    public BigDecimal getC1Alicuota() {
        return this.c1Alicuota;
    }
    
    public void setC1Alicuota(BigDecimal c1Alicuota) {
        this.c1Alicuota = c1Alicuota;
    }
    public String getC1Factura() {
        return this.c1Factura;
    }
    
    public void setC1Factura(String c1Factura) {
        this.c1Factura = c1Factura;
    }
    public Date getC1FechaFactura() {
        return this.c1FechaFactura;
    }
    
    public void setC1FechaFactura(Date c1FechaFactura) {
        this.c1FechaFactura = c1FechaFactura;
    }
    public String getC1NombreChofer() {
        return this.c1NombreChofer;
    }
    
    public void setC1NombreChofer(String c1NombreChofer) {
        this.c1NombreChofer = c1NombreChofer;
    }
    public String getC1CiChofer() {
        return this.c1CiChofer;
    }
    
    public void setC1CiChofer(String c1CiChofer) {
        this.c1CiChofer = c1CiChofer;
    }
    public String getC1CodTransp() {
        return this.c1CodTransp;
    }
    
    public void setC1CodTransp(String c1CodTransp) {
        this.c1CodTransp = c1CodTransp;
    }
    public String getC1NombreTransp() {
        return this.c1NombreTransp;
    }
    
    public void setC1NombreTransp(String c1NombreTransp) {
        this.c1NombreTransp = c1NombreTransp;
    }
    public String getC1TipoTransporte() {
        return this.c1TipoTransporte;
    }
    
    public void setC1TipoTransporte(String c1TipoTransporte) {
        this.c1TipoTransporte = c1TipoTransporte;
    }
    public String getC1CodCamion() {
        return this.c1CodCamion;
    }
    
    public void setC1CodCamion(String c1CodCamion) {
        this.c1CodCamion = c1CodCamion;
    }
    public String getC1TipoCamion() {
        return this.c1TipoCamion;
    }
    
    public void setC1TipoCamion(String c1TipoCamion) {
        this.c1TipoCamion = c1TipoCamion;
    }
    public String getC1NoEjes() {
        return this.c1NoEjes;
    }
    
    public void setC1NoEjes(String c1NoEjes) {
        this.c1NoEjes = c1NoEjes;
    }
    public BigDecimal getC1Capacidad() {
        return this.c1Capacidad;
    }
    
    public void setC1Capacidad(BigDecimal c1Capacidad) {
        this.c1Capacidad = c1Capacidad;
    }
    public String getC1PlacaChuto() {
        return this.c1PlacaChuto;
    }
    
    public void setC1PlacaChuto(String c1PlacaChuto) {
        this.c1PlacaChuto = c1PlacaChuto;
    }
    public String getC1PlacaBatea() {
        return this.c1PlacaBatea;
    }
    
    public void setC1PlacaBatea(String c1PlacaBatea) {
        this.c1PlacaBatea = c1PlacaBatea;
    }
    public Date getC1FechaRelacion() {
        return this.c1FechaRelacion;
    }
    
    public void setC1FechaRelacion(Date c1FechaRelacion) {
        this.c1FechaRelacion = c1FechaRelacion;
    }

    public String getC1CodSector() {
        return c1CodSector;
    }

    public void setC1CodSector(String c1CodSector) {
        this.c1CodSector = c1CodSector;
    }
    
    public String getC1CodDestino() {
        return this.c1CodDestino;
    }
    
    public void setC1CodDestino(String c1CodDestino) {
        this.c1CodDestino = c1CodDestino;
    }
    public String getC1NombreDestino() {
        return this.c1NombreDestino;
    }
    
    public void setC1NombreDestino(String c1NombreDestino) {
        this.c1NombreDestino = c1NombreDestino;
    }
    public String getC1Estado() {
        return this.c1Estado;
    }
    
    public void setC1Estado(String c1Estado) {
        this.c1Estado = c1Estado;
    }
    public String getC1GuiaReparto() {
        return this.c1GuiaReparto;
    }
    
    public void setC1GuiaReparto(String c1GuiaReparto) {
        this.c1GuiaReparto = c1GuiaReparto;
    }
    public String getC1GuiaOrigenReparto() {
        return this.c1GuiaOrigenReparto;
    }
    
    public void setC1GuiaOrigenReparto(String c1GuiaOrigenReparto) {
        this.c1GuiaOrigenReparto = c1GuiaOrigenReparto;
    }
    public String getC1SerialTicket1() {
        return this.c1SerialTicket1;
    }
    
    public void setC1SerialTicket1(String c1SerialTicket1) {
        this.c1SerialTicket1 = c1SerialTicket1;
    }
    public BigDecimal getC1PesoTara() {
        return this.c1PesoTara;
    }
    
    public void setC1PesoTara(BigDecimal c1PesoTara) {
        this.c1PesoTara = c1PesoTara;
    }
    public String getC1SerialTicket2() {
        return this.c1SerialTicket2;
    }
    
    public void setC1SerialTicket2(String c1SerialTicket2) {
        this.c1SerialTicket2 = c1SerialTicket2;
    }
    public BigDecimal getC1PesoBruto() {
        return this.c1PesoBruto;
    }
    
    public void setC1PesoBruto(BigDecimal c1PesoBruto) {
        this.c1PesoBruto = c1PesoBruto;
    }
    public String getC1Status() {
        return this.c1Status;
    }
    
    public void setC1Status(String c1Status) {
        this.c1Status = c1Status;
    }
    public String getC1Observacion() {
        return this.c1Observacion;
    }
    
    public void setC1Observacion(String c1Observacion) {
        this.c1Observacion = c1Observacion;
    }
    public String getC1ObservacionRomana1() {
        return this.c1ObservacionRomana1;
    }
    
    public void setC1ObservacionRomana1(String c1ObservacionRomana1) {
        this.c1ObservacionRomana1 = c1ObservacionRomana1;
    }
    public String getC1FleteProcesado() {
        return this.c1FleteProcesado;
    }
    
    public void setC1FleteProcesado(String c1FleteProcesado) {
        this.c1FleteProcesado = c1FleteProcesado;
    }
    public Set getGuias02Dats() {
        return this.guias02Dats;
    }
    
    public void setGuias02Dats(Set guias02Dats) {
        this.guias02Dats = guias02Dats;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

}



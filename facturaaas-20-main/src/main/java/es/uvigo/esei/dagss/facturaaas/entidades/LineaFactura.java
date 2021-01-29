package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LINEA_FACTURA")
public class LineaFactura implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_FACTURA")
    private Factura factura;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_IVA")
    private TipoIVA tipo_IVA;
    
    
    @Column(name = "CONCEPTO")
    private String concepto;
    
    @Column(name = "CANTIDAD")
    private Double cantidad;
    
    @Column(name = "PRECIO_UNITARIO")
    private Double precio_unidad;
    
    @Column(name = "PORCENTAJE_DESCUENTO")
    private Double descuento;
    
    @Column(name = "IMPORTE_TOTAL")
    private Double importe_total;   
    
    public LineaFactura(){
        
    }

    public LineaFactura(String concepto, Double cantidad, Double precio_unidad, Double descuento, Double importe_total, Factura factura, TipoIVA tipo_IVA) {
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.precio_unidad = precio_unidad;
        this.descuento = descuento;
        this.importe_total = importe_total;
        this.factura = factura;
        this.tipo_IVA = tipo_IVA;
    }

    public Long getId() {
        return id;
    }

    public String getConcepto() {
        return concepto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public Double getPrecioUnitario() {
        return precio_unidad;
    }

    public Double getPorcentajeDescuento() {
        return descuento;
    }

    public Double getImporteTotal() {
        return importe_total;
    }

    public TipoIVA getTipoIVA() {
        return tipo_IVA;
    }

    public Factura getFactura() {
        return factura;
    }
    

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(Double precio_unidad) {
        this.precio_unidad = precio_unidad;
    }

    public void setPorcentajeDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public void setImporteTotal(Double importe_total) {
        this.importe_total = importe_total;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public void setTipoIVA(TipoIVA tipo_IVA) {
        this.tipo_IVA = tipo_IVA;
    }

   
    
}

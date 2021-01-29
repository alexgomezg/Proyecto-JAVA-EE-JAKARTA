/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.facturaaas.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PAGO")
public class Pago implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_FACTURA")
    private Factura factura;
   
    @Column(name = "ESTADO_PAGO")
    private EstadoPago estado_pago;
    
    public Pago(){
        
    }

    public Pago(EstadoPago estado_pago, Factura factura) {
        this.estado_pago = estado_pago;
        this.factura = factura;
    }

    public Long getId() {
        return id;
    }

    public EstadoPago getEstadoPago() {
        return estado_pago;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setEstadoPago(EstadoPago estado_pago) {
        this.estado_pago = estado_pago;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}

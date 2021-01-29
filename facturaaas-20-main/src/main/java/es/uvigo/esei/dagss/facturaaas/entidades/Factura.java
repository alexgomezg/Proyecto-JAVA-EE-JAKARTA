package es.uvigo.esei.dagss.facturaaas.entidades;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "FACTURA")
public class Factura implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "ID_FORMA_PAGO")
    private FormaPago forma_pago;
    
    @Column(name = "EJERCICIO_FACTURA")
    private String ejercicio;
    
    @Column(name = "FECHA_EMISION")
    private Date fecha_emision;
    
    @Column(name = "COMENTARIO_FACTURA")
    private String comentario;
   
    @Column(name = "ESTADO_FACTURA")
    private EstadoFactura estado_factura;
    
    public Factura (){
        
    }

    public Factura(String ejercicio, Date fecha_emision, String comentario, Cliente cliente, FormaPago forma_pago, EstadoFactura estado_factura) {
        this.ejercicio = ejercicio;
        this.fecha_emision = fecha_emision;
        this.comentario = comentario;
        this.cliente = cliente;
        this.forma_pago = forma_pago;
        this.estado_factura = estado_factura;
    }

    public Long getId() {
        return id;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public Date getEmision() {
        return fecha_emision;
    }

    public String getComentario() {
        return comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public FormaPago getFormaDePago() {
        return forma_pago;
    }

    public EstadoFactura getEstadoDeFactura() {
        return estado_factura;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public void setEmision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFormaDePago(FormaPago forma_pago) {
        this.forma_pago = forma_pago;
    }

    public void setEstadoDeFactura(EstadoFactura estado_factura) {
        this.estado_factura = estado_factura;
    }

}

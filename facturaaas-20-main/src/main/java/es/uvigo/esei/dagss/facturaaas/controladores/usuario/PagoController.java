package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.LineaFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.PagoDAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "pagoController")
@ViewScoped
public class PagoController implements Serializable{
    
    @Inject
    private PagoDAO dao;
    @Inject
    private AutenticacionController autenticacionController;
    @Inject
    private LineaFacturaDAO linea_facturaDao;
    
    private List<Pago> pagos;
    private Cliente clienteBusqueda;
    private Pago pagoActual;

    public Pago getPagoActual() {
        return pagoActual;
    }

    public void setPagoActual(Pago pagoActual) {
        this.pagoActual = pagoActual;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public Cliente getClienteBusqueda() {
        return clienteBusqueda;
    }

    public void setClienteBusqueda(Cliente clienteBusqueda) {
        this.clienteBusqueda = clienteBusqueda;
    }
    
    @PostConstruct
    public void cargaInicial(){
        this.pagos = refrescarLista();
    }
    
    public List<Pago> refrescarLista(){
        return dao.buscarPorUsuario(autenticacionController.getUsuarioLogueado());
    }
    
    public List<Cliente> getClientesDistintos(){
        List<Cliente> clientes = new ArrayList<>();
        List<Pago> aux = refrescarLista();
        
        for(Pago pago : aux){
            if(!clientes.contains(pago.getFactura().getCliente())){
                clientes.add(pago.getFactura().getCliente());
            }
        }
        
        return clientes;
    }
    
    public void getPagosPorCliente(){
        if(this.clienteBusqueda == null){
            this.pagos = this.refrescarLista();
        } else {
            this.pagos = dao.buscarPorCliente(clienteBusqueda);
        }
    }
    
    public double calcularImporte(Pago pago){
        List<LineaFactura> lineasFactura = linea_facturaDao.buscarPorFactura(pago.getFactura());
        double importeTotal = 0;
        
        for(LineaFactura linea : lineasFactura){
            importeTotal += linea.getImporteTotal();
        }
        
        return importeTotal / pago.getFactura().getFormaDePago().getNumeroPagos();  
    }
    
    public void doEditar(Pago pago){
        this.pagoActual = pago;
    }
    
    public void doGuardar(){
        dao.actualizar(pagoActual);
        
        this.pagos = this.refrescarLista();
        this.pagoActual = null;
    }
    
    public void doCancelarEditada(){
        this.pagoActual = null;
    }
    
    public EstadoPago[] getEstadosPagoPosibles(){
        return EstadoPago.values();
    }
}

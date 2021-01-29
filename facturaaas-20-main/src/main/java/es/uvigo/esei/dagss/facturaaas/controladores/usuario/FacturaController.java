package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.controladores.AutenticacionController;
import es.uvigo.esei.dagss.facturaaas.daos.ClienteDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.FormaPagoDAO;
import es.uvigo.esei.dagss.facturaaas.daos.LineaFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.PagoDAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoPago;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.FormaPago;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named(value = "facturaController")
@ViewScoped
public class FacturaController implements Serializable{
    
    @Inject
    private FacturaDAO dao;
    @Inject
    private ClienteDAO clienteDao;
    @Inject
    private FormaPagoDAO forma_pagoDao;
    @Inject
    private PagoDAO pagoDao;
    @Inject
    private AutenticacionController autenticacionController;
    @Inject
    private LineaFacturaDAO linea_facturaDao;
    
    private List<Factura> facturas;
    private Cliente clienteBusqueda;
    private boolean esNueva;
    private EstadoFactura[] estadosFactura = EstadoFactura.values();

    public EstadoFactura[] getEstadosFactura() {
        return estadosFactura;
    }
    
    public boolean isEsNueva() {
        return esNueva;
    }

    public void setEsNueva(boolean esNueva) {
        this.esNueva = esNueva;
    }
    private Factura facturaActual;

    public Factura getFacturaActual() {
        return facturaActual;
    }
    
    public void setFacturaActual(Factura facturaActual) {
        this.facturaActual = facturaActual;
    }
    
    public List<Factura> getFacturas (){
        return facturas;
    }
    
    public Cliente getClienteBusqueda(){
        return this.clienteBusqueda;
    }
    
    public void setClienteBusqueda(Cliente clienteBusqueda){
        this.clienteBusqueda = clienteBusqueda;
    }
    
    @PostConstruct
    public void cargaInicial() {
        this.facturas = refrescarLista();
    }
    
    private List<Factura> refrescarLista() {
        return dao.buscarPorUsuario(autenticacionController.getUsuarioLogueado());
    }
    
    public List<Cliente> getClientesDistintos(){
        List<Cliente> clientes = new ArrayList<>();
        List<Factura> aux = dao.buscarPorUsuario(autenticacionController.getUsuarioLogueado());
        
        for(Factura factura : aux){
            if(!clientes.contains(factura.getCliente())){
                clientes.add(factura.getCliente());
            }
        }
        
        return clientes;
    }
    
    public List<Cliente> getClientesPosibles(){
        return clienteDao.buscarTodosConPropietario(autenticacionController.getUsuarioLogueado());
    }
    
    public List<FormaPago> getFormasPagoPosibles(){
        return forma_pagoDao.buscarActivas();
    }
    
    public void getFacturasPorCliente(){
        if(this.clienteBusqueda == null){
            this.facturas = refrescarLista();
        }
        else{
            this.facturas = dao.buscarPorCliente(clienteBusqueda);
        }
    }
    
    public void doNueva(){
        this.esNueva = true;
        this.facturaActual = new Factura();
        this.facturaActual.setEmision(new Date());
        this.facturaActual.setCliente(new Cliente());
        this.facturaActual.setEstadoDeFactura(EstadoFactura.EMITIDA);
    }
    
    public void doEditar (Factura factura){
        this.esNueva = false;
        this.facturaActual = factura;
        this.facturaActual.setEmision(new Date());
    }
    
    public double calcularImporte(Factura factura){
        List<LineaFactura> lineasFactura = linea_facturaDao.buscarPorFactura(factura);
        double importeTotal = 0;
        for(LineaFactura linea : lineasFactura){
            importeTotal += linea.getImporteTotal();
        }
        return importeTotal;
    }
    
    public void doNuevaFactura(){
        if (this.esNueva) {
            dao.crear(facturaActual);
            this.actualizarPagos(facturaActual);
        } else {
            if(!dao.buscarPorClave(facturaActual.getId()).getFormaDePago().equals(facturaActual.getFormaDePago())){
                this.actualizarPagos(facturaActual);
            }
            dao.actualizar(facturaActual);
        }
        this.actualizarPagos(facturaActual);
        this.facturas = refrescarLista();
        this.facturaActual = null;
        this.esNueva = false;
    }
    
    private void actualizarPagos(Factura factura){
        for(Pago pago : pagoDao.buscarPorFactura(factura)){
            pago.setEstadoPago(EstadoPago.ANULADO);
            pagoDao.actualizar(pago);
        }
        for(int i = 0; i < factura.getFormaDePago().getNumeroPagos(); i++){
            pagoDao.crear(new Pago(EstadoPago.PENDIENTE, factura));
        }
    }
    
    public void doCancelarEditada() {
        this.facturaActual = null;
    }
}

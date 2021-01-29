package es.uvigo.esei.dagss.facturaaas.controladores.usuario;

import es.uvigo.esei.dagss.facturaaas.daos.LineaFacturaDAO;
import es.uvigo.esei.dagss.facturaaas.daos.TipoIVADAO;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.TipoIVA;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "lineaFacturaController")
@ViewScoped
public class LineaFacturaController implements Serializable {

    @Inject
    private LineaFacturaDAO dao;

    @Inject
    private TipoIVADAO tipo_ivaDao;

    private List<LineaFactura> lineas;
    private Factura facturaActual;
    private boolean esNueva;
    private LineaFactura lineaActual;

    public void cargaInicial(Factura factura) {
        this.lineas = this.dao.buscarPorFactura(factura);
        this.facturaActual = factura;
    }

    public List<LineaFactura> getLineas() {
        return this.lineas;
    }

    public LineaFactura getLineaActual() {
        return this.lineaActual;
    }

    public void doEditar(LineaFactura linea) {
        this.esNueva = false;
        this.lineaActual = linea;
    }

    public void doNueva() {
        this.esNueva = true;
        this.lineaActual = new LineaFactura();
        this.lineaActual.setFactura(this.facturaActual);
    }

    public void doNuevaLinea() {
        this.lineaActual.setImporteTotal(this.lineaActual.getCantidad() * this.lineaActual.getPrecioUnitario());

        if (this.lineaActual.getTipoIVA() != null) {
            this.lineaActual.setImporteTotal(this.lineaActual.getImporteTotal() * (1 + (this.lineaActual.getTipoIVA().getPorcentaje() / 100)));
        }

        this.lineaActual.setImporteTotal(this.lineaActual.getImporteTotal() * (1 - (this.lineaActual.getPorcentajeDescuento() / 100)));

        if (this.esNueva) {
            dao.crear(lineaActual);
        } else {
            dao.actualizar(lineaActual);
        }
        this.cargaInicial(facturaActual);
        this.lineaActual = null;
        this.esNueva = false;
    }

    public void doCancelarEditada() {
        this.lineaActual = null;
    }

    public List<TipoIVA> cargarTiposIVA() {
        return this.tipo_ivaDao.buscarActivos();
    }
}

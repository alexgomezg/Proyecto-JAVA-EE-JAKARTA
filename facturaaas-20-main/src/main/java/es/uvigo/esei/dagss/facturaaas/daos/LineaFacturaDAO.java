package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import java.util.List;

public interface LineaFacturaDAO extends GenericoDAO<LineaFactura, Long> {
    public List<LineaFactura> buscarPorFactura (Factura factura);
}

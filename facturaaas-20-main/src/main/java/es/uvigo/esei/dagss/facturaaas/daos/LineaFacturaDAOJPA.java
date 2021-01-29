
package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.LineaFactura;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;


@Stateless
public class LineaFacturaDAOJPA extends GenericoDAOJPA<LineaFactura, Long> implements LineaFacturaDAO{

    @Override
    public List<LineaFactura> buscarPorFactura(Factura factura) {
        TypedQuery<LineaFactura> query = em.createQuery("SELECT lf FROM LineaFactura AS lf "
                + "WHERE lf.factura.id = :idFactura", LineaFactura.class);
        
        query.setParameter("idFactura", factura.getId());
        
        return query.getResultList();
    }

    
}

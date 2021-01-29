package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.EstadoPago;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
public class PagoDAOJPA extends GenericoDAOJPA<Pago, Long> implements PagoDAO{

    @Override
    public List<Pago> buscarPorUsuario(Usuario usuario) {
        TypedQuery<Pago> query = em.createQuery("SELECT p FROM Pago AS p "
                + "WHERE p.factura.cliente.propietario.id = :idUsuario "
                + "AND p.estado_pago != :estado", Pago.class);
        
        query.setParameter("idUsuario", usuario.getId());
        query.setParameter("estado", EstadoPago.ANULADO);
        
        return query.getResultList();
    }

    @Override
    public List<Pago> buscarPorCliente(Cliente cliente) {
        TypedQuery<Pago> query = em.createQuery("SELECT p FROM Pago AS p "
                + "WHERE p.factura.cliente.id = :idCliente "
                + "AND p.estado_pago != :estado", Pago.class);
        
        query.setParameter("idCliente", cliente.getId());
        query.setParameter("estado", EstadoPago.ANULADO);

        
        return query.getResultList();
    }
    
    @Override
    public List<Pago> buscarPorFactura(Factura factura){
        TypedQuery<Pago> query = em.createQuery("SELECT p FROM Pago as p "
                + "WHERE p.factura.id = :idFactura", Pago.class);
        
        query.setParameter("idFactura", factura.getId());
        
        return query.getResultList();
    }
    
}

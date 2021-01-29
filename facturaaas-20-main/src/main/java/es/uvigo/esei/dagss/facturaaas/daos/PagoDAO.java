
package es.uvigo.esei.dagss.facturaaas.daos;
import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Pago;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;

public interface PagoDAO extends GenericoDAO<Pago, Long> {
    
    public List<Pago> buscarPorUsuario(Usuario usuario);
    
    public List<Pago> buscarPorCliente (Cliente cliente);
    
    public List<Pago> buscarPorFactura(Factura factura);
}



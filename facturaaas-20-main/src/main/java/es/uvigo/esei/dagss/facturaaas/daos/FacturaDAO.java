package es.uvigo.esei.dagss.facturaaas.daos;

import es.uvigo.esei.dagss.facturaaas.entidades.Cliente;
import es.uvigo.esei.dagss.facturaaas.entidades.Factura;
import es.uvigo.esei.dagss.facturaaas.entidades.Usuario;
import java.util.List;


public interface FacturaDAO extends GenericoDAO<Factura, Long> {
    
    public List<Factura> buscarPorUsuario (Usuario usuario);
    
    public List<Factura> buscarPorCliente (Cliente cliente);
    
    
}

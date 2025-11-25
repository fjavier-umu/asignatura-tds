package tds.tpv.negocio.modelo;

import java.util.List;

public interface TPV {
	
	List<? extends Producto> getProductos();
	
	List<? extends CarritoCompra> getCompras();

}

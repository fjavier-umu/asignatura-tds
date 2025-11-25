package tds.tpv.negocio.modelo;

public interface Item {

	Producto getProducto();

	int getCantidad();

	String getDescripcion();

	double getPrecio();

	double getPrecioTotal();
}

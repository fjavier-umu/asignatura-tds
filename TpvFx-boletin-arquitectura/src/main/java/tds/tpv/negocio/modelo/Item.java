package tds.tpv.negocio.modelo;

public class Item {
	
	private final Producto producto;
	private int cantidad;

	public Item(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	/**
	 * Devuelve el producto asociado al item 
	 * @return Producto
	 */
	public Producto getProducto() {
		return producto;
	}
	
	/**
	 * Devuelve la cantidad de productos que se han aniadido a este item concreto
	 * @return int
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Devuelve la descripcion del producto
	 * @return String
	 */
	public String getDescripcion() {
		return producto.getDescripcion();
	}
	
	
	/**
	 * Incrementa la cantidad de productos de este tiem.
	 * @param cantidad
	 */
	public void incrementarCantidad(int cantidad) {
		this.cantidad += cantidad;
	}

	/**
	 * Devuelve el precio total del producto que es el importe por la cantidad 
	 * @return double
	 */
	public double getPrecioTotal() {
		return this.producto.getPrecio() * this.cantidad;
	}
	
}

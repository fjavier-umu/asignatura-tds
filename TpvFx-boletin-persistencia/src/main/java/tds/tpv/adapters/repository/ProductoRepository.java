package tds.tpv.adapters.repository;

import java.util.List;

import tds.tpv.negocio.modelo.impl.ProductoImpl;
import tds.tpv.adapters.repository.exceptions.ElementoExistenteException;
import tds.tpv.adapters.repository.exceptions.ElementoNoEncontradoException;
import tds.tpv.adapters.repository.exceptions.ErrorPersistenciaException;

public interface ProductoRepository {

	/**
	 * Devuelve una lista con todos los productos cargados
	 * 
	 * @return Lista de productos
	 */
	List<? extends ProductoImpl> getProductos();

	/**
	 * Busca un producto por su codigo y lo devuelve
	 * 
	 * @param codigo
	 * @throws ElementoNoEncontradoException si no encuentra el producto buscado
	 * @return Producto
	 */
	ProductoImpl findById(String codigo) throws ElementoNoEncontradoException;

	/**
	 * Aniade un producto al inventario de productos
	 * 
	 * @param producto
	 * @throws ElementoExistenteException si existe un producto igual en el
	 *                                    repositorio
	 * @return void
	 */
	void addProducto(ProductoImpl producto) throws ElementoExistenteException,ErrorPersistenciaException;

	/**
	 * Elimina un producto del inventario de productos, si ya estaba eliminado no
	 * hace nada
	 * 
	 * @param producto
	 * @return void
	 */
	void removeProducto(ProductoImpl producto) throws ErrorPersistenciaException;

	/**
	 * Actualiza un producto en el almacen de productos, devolviendo la referencia al producto que
	 * se ha actualizado
	 * 
	 * @param producto
	 * @return Producto
	 */
	ProductoImpl updateProducto(ProductoImpl producto) throws ErrorPersistenciaException;
}

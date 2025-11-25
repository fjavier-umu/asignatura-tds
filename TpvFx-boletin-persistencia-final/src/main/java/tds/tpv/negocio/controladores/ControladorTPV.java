package tds.tpv.negocio.controladores;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Preconditions;

import tds.tpv.adapters.repository.CompraRepository;
import tds.tpv.adapters.repository.ProductoRepository;
import tds.tpv.negocio.modelo.CarritoCompra;
import tds.tpv.negocio.modelo.Item;
import tds.tpv.negocio.modelo.Producto;
import tds.tpv.negocio.modelo.TPV;
import tds.tpv.negocio.modelo.impl.CarritoCompraImpl;
import tds.tpv.negocio.modelo.impl.ProductoImpl;
import tds.tpv.negocio.modelo.impl.TPVImpl;
import tds.tpv.adapters.repository.exceptions.ElementoExistenteException;
import tds.tpv.adapters.repository.exceptions.ElementoNoEncontradoException;
import tds.tpv.adapters.repository.exceptions.ErrorPersistenciaException;
import tds.tpv.negocio.modelo.impl.CarritoCompraImpl;
import tds.tpv.negocio.modelo.impl.ProductoImpl;
import tds.tpv.negocio.modelo.CarritoCompra;
import tds.tpv.negocio.modelo.Item;
import tds.tpv.negocio.modelo.Producto;

public class ControladorTPV {

	private static final Logger log = LogManager.getLogger();

	private final ProductoRepository repositorioProductos;
	private final CompraRepository repositorioCompras;
	private CarritoCompraImpl carrito;
	
	public ControladorTPV(ProductoRepository repPro, CompraRepository repCom) {
		this.repositorioProductos = repPro;
		this.repositorioCompras = repCom;
		nuevoCarrito();
	}

	// Como podemos hacer para que la vista no pueda aniadir al carrito?
	// => Devolver una interfaz (o hacer que Carrito sea una interfaz y tener un
	// CarritoImpl para el controlador)
	public CarritoCompra nuevoCarrito() {
		this.carrito = new CarritoCompraImpl();
		return carrito;
	}

	public List<? extends Producto> getProductos() {
		return repositorioProductos.getProductos();
	}

	public List<? extends Producto> getProductosPorFiltro(String filtro) {
		return repositorioProductos.getProductos().stream()
				.filter(producto -> producto.getDescripcion().toLowerCase().contains(filtro.toLowerCase()))
				.collect(Collectors.toList());
	}

	/**
	 * Añade un producto al carrito.
	 * 
	 * @return El item añadido o modificado.
	 */
	public Item addToCarrito(Producto producto, int cantidad) {
		Preconditions.checkNotNull(carrito);
		log.info("Añadido producto {} al carrito con cantidad {}", producto.getCodigo(), cantidad);
		return this.carrito.addItem(producto, cantidad);
	}
	
	
	public void quitarFromCarrito(Producto producto) {
		Preconditions.checkNotNull(carrito);
		Preconditions.checkNotNull(producto);
		log.info("Quitamos producto ({}) {} del carrito ", producto.getCodigo(), producto.getDescripcion());
		 this.carrito.removeItem(producto);
	}

	public CarritoCompra getCarrito() {
		Preconditions.checkNotNull(carrito);
		return carrito;
	}

	// La parte de stock podria estar en otro controlador

	/**
	 * Modifica la cantidad de stock. Si se quiere reducir la cantidad, se pasa un
	 * número negativo
	 * 
	 * @param producto El producto que se quiere modificar
	 * @param cantidad La cantidad que se suma (positivo) o resta (negativo) al
	 *                 stock actual
	 */
	private void modificarStock(Producto producto, int cantidad) {
		// Asegurar que el producto efectivamente existe
		ProductoImpl productoImpl;
		try {
			productoImpl = repositorioProductos.findById(producto.getCodigo());
			Preconditions.checkState(productoImpl == producto);

			// Asegurar que el stock nunca es negativo
			// Siempre sumamos porque el viewcontroller mandara un numero positivo o
			// negativo segun convenga
			Preconditions.checkState(productoImpl.getCantidad() + cantidad >= 0);

			productoImpl.setCantidad(productoImpl.getCantidad() + cantidad);
			repositorioProductos.updateProducto(productoImpl);
		} catch (ElementoNoEncontradoException e) {
			log.error("No se encuentra el producto {} para modifiarlo", producto.getCodigo(), e);

		} catch (ErrorPersistenciaException e) {
			log.error("Error en la actualizacion del producto {}", producto.getCodigo(), e);
		}
	}

	public void aumentarStock(Producto producto, int cantidad) {
		//Aseguramos que el valor de cantidad es siempre positivo
		Preconditions.checkState(cantidad >= 0);
		log.info("Aumentamos el stock del producto {} en {} unidades",producto.getCodigo(),cantidad);
		this.modificarStock(producto, cantidad);
	}

	public void guardarCarrito() {
		int id = repositorioCompras.getCompras().size();
		// por simplicidad se pone como limite 99999 carritos
		carrito.setIdCompra("C" + String.format("%05d", id));

		AtomicInteger index = new AtomicInteger(0);
		// por simplicidad se pone como limite 99999 items por carrito
		carrito.getItems().stream()
						.forEach(i -> {
								int ind = index.getAndIncrement();
								i.setIdItem( carrito.getIdCompra() + "I" + String.format("%05d", ind) );
							}
						);
		
		try {
			repositorioCompras.addCompra(carrito);
		} catch (ElementoExistenteException e) {
			log.error("El carrito de la compra ya existe", carrito.getIdCompra(), e);
		} catch (ErrorPersistenciaException e) {
			log.error("Error en la actualizacion de la compra {}", carrito.getIdCompra(), e);
		}
	}

	public List<? extends CarritoCompra> getCompras() {
		return repositorioCompras.getCompras();
	}	
	
	public void reducirStock(Producto producto, int cantidad) {
		//Aseguramos que el valor de cantidad es siempre positivo
		Preconditions.checkState(cantidad >= 0);
		log.info("Reducimos el stock del producto {} en {} unidades",producto.getCodigo(),cantidad);
		this.modificarStock(producto, -cantidad);
	}

	public void nuevoProductoStock(ProductoImpl productoImpl) {
		try {
			repositorioProductos.addProducto(productoImpl);
			log.info("Creado nuevo producto ({}) {}", productoImpl.getCodigo(),productoImpl.getDescripcion());
		} catch (ElementoExistenteException e) {
			log.error("El producto ya existe {}", productoImpl.getCodigo(), e);

		} catch (ErrorPersistenciaException e) {
			log.error("Error en la persistencia del producto {}", productoImpl.getCodigo(), e);
		}

	}
}

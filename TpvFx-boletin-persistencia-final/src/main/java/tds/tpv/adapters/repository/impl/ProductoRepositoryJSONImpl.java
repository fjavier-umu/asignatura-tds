package tds.tpv.adapters.repository.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tds.tpv.Configuracion;
import tds.tpv.adapters.repository.ProductoRepository;
import tds.tpv.negocio.modelo.TPV;
import tds.tpv.negocio.modelo.impl.CarritoCompraImpl;
import tds.tpv.negocio.modelo.impl.ProductoImpl;
import tds.tpv.negocio.modelo.impl.TPVImpl;
import tds.tpv.adapters.repository.exceptions.ElementoExistenteException;
import tds.tpv.adapters.repository.exceptions.ElementoNoEncontradoException;
import tds.tpv.adapters.repository.exceptions.ErrorPersistenciaException;

public class ProductoRepositoryJSONImpl implements ProductoRepository {
	private static final Logger log = LogManager.getLogger();

	private List<ProductoImpl> productos = null;
	private String rutaFichero;

	private void cargaProductos() throws ErrorPersistenciaException {
		try {
			rutaFichero = Configuracion.getInstancia().getRutaTPV();
			this.productos = cargarProductos(rutaFichero);
		} catch (Exception e) {
			log.error("Error cargando los productos ", e);
			throw new ErrorPersistenciaException(e);
		}
	}

	@Override
	public List<? extends ProductoImpl> getProductos() {
		if (productos == null) {
			try {
				cargaProductos();
			} catch (ErrorPersistenciaException e) {
				// Manejo la excepcion pero no la propago porque en este sitio es donde se puede
				// gestionar mejor
				log.error("No se han podido cargar los productos ", e);
			}
		}
		return productos;
	}

	@Override
	public ProductoImpl findById(String codigo) throws ElementoNoEncontradoException {
		Optional<ProductoImpl> producto = productos.stream()
					.filter(prod -> prod.getCodigo().equals(codigo))
					.findFirst();
		
		if (producto.isEmpty())
				throw new ElementoNoEncontradoException("El producto " + codigo + " no existe");
		return producto.orElse(null);
	}

	@Override
	public void addProducto(ProductoImpl producto) throws ElementoExistenteException, ErrorPersistenciaException {
		// Si el producto ya existe no puedo insertarlo
		if (productos.contains(producto)) {
			throw new ElementoExistenteException("El producto " + producto.getCodigo() + " ya existe en el inventario");
		}
		productos.add(producto);
		try {
			guardarProductos(productos, rutaFichero);
		} catch (Exception e) {
			log.error("Error persistiendo el producto {}", producto.getCodigo(), e);
			// Capturo las excepciones genericas lanzadas al persistir y lanzo una propia
			// encapsulando la excepcion real
			throw new ErrorPersistenciaException(e);
		}

	}

	@Override
	public void removeProducto(ProductoImpl producto) throws ErrorPersistenciaException {

		if (!productos.contains(producto)) {
			return;
		}

		productos.remove(producto);
		try {
			guardarProductos(productos, rutaFichero);
		} catch (Exception e) {
			log.error("Error eliminando el producto {}", producto.getCodigo(), e);
			throw new ErrorPersistenciaException(e);
		}
	}

	@Override
	public ProductoImpl updateProducto(ProductoImpl producto) throws ErrorPersistenciaException {
		// No borramos los productos con stock 0
		try {
			guardarProductos(productos, rutaFichero);
			return producto;
		} catch (Exception e) {
			log.error("Error eliminando el producto {}", producto.getCodigo(), e);
			throw new ErrorPersistenciaException(e);
		}
	}

	// Metodo para cargar los productos incialmente desde fichero json
	private List<ProductoImpl> cargarProductos(String rutaFichero)
			throws StreamReadException, DatabindException, IOException {

		InputStream ficheroStream = getClass().getResourceAsStream(rutaFichero);
		
		ObjectMapper mapper = new ObjectMapper();
		
		TPVImpl tpv = (TPVImpl) Configuracion.getInstancia().getTPV();
		
		TPVImpl tpvCargado = mapper.readValue(ficheroStream, new TypeReference<TPVImpl>() {});
		tpv.setProductos((List<ProductoImpl>) tpvCargado.getProductos());		
		tpv.setCompras((List<CarritoCompraImpl>) tpvCargado.getCompras());

		this.productos = (List<ProductoImpl>) tpv.getProductos();
		return productos;
	}

	// Metodo para guardar por completo en el fichero json
	private void guardarProductos(List<ProductoImpl> productos, String rutaFichero)
			throws StreamWriteException, DatabindException, IOException {

		// Se carga mediante URL para prevenir problemas con rutas con espacios en
		// blanco o caracteres no estandar
		URL url = getClass().getResource(rutaFichero);
		try {
			// Cargo el fichero a partir de la URL local
			File ficheroJSon = Paths.get(url.toURI()).toFile();
	
			TPVImpl tpv = (TPVImpl) Configuracion.getInstancia().getTPV();
			tpv.setProductos(productos);
			this.productos = productos;
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(ficheroJSon, tpv);

		} catch (IOException | URISyntaxException e) {
			log.error("Error persistiendo en fichero", e);
		}
	}

}

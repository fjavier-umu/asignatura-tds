package tds.tpv.adapters.repository.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tds.tpv.Configuracion;
import tds.tpv.adapters.repository.CompraRepository;
import tds.tpv.adapters.repository.exceptions.ElementoExistenteException;
import tds.tpv.adapters.repository.exceptions.ElementoNoEncontradoException;
import tds.tpv.adapters.repository.exceptions.ErrorPersistenciaException;
import tds.tpv.negocio.modelo.impl.CarritoCompraImpl;
import tds.tpv.negocio.modelo.impl.ProductoImpl;
import tds.tpv.negocio.modelo.impl.TPVImpl;

public class CompraRepositoryJSONImpl implements CompraRepository {
	private static final Logger log = LogManager.getLogger();

	private List<CarritoCompraImpl> compras = null;
	private String rutaFichero;
	
	private void cargaCompras() throws ErrorPersistenciaException {
		try {
			rutaFichero = Configuracion.getInstancia().getRutaTPV(); //Configuracion.getInstancia().getRutaCompras();
			this.compras = cargarCompras(rutaFichero);
			if (compras == null) compras = new ArrayList<>();
		} catch (Exception e) {
			log.error("Error cargando las compras ", e);
			throw new ErrorPersistenciaException(e);
		}
	}

	@Override
	public List<? extends CarritoCompraImpl> getCompras() {
		if (compras == null) {
			try {
				cargaCompras();
			} catch (ErrorPersistenciaException e) {
				// Manejo la excepcion pero no la propago porque en este sitio es donde se puede
				// gestionar mejor
				log.error("No se han podido cargar las compras ", e);
			}
		}
		return compras;
	}

	@Override
	public CarritoCompraImpl findById(int id) throws ElementoNoEncontradoException { 
		Optional<CarritoCompraImpl> compra = compras.stream()
				.filter( c -> c.getIdCompra().equals(id))
				.findFirst();
	
		if (compra.isEmpty())
			throw new ElementoNoEncontradoException("La compra " + id + " no existe");
		return compra.orElse(null);

	}

	@Override
	public void addCompra(CarritoCompraImpl compra) throws ElementoExistenteException,ErrorPersistenciaException {
		// Si el producto ya existe no puedo insertarlo
		if (compras.contains(compra)) {
			throw new ElementoExistenteException("La compra " + compra.getIdCompra() + " ya existe");
		}
		
		compras.add(compra);
		try {
			guardarCompras(compras, rutaFichero);
		} catch (Exception e) {
			log.error("Error persistiendo la compra{}", compra.getIdCompra(), e);
			// Capturo las excepciones genericas lanzadas al persistir y lanzo una propia
			// encapsulando la excepcion real
			throw new ErrorPersistenciaException(e);
		}
	}

	@Override
	public void removeCompra(CarritoCompraImpl compra) throws ErrorPersistenciaException {
		if (!compras.contains(compra)) 
			return ;
		
		compras.remove(compra);
		try {
			guardarCompras(compras, rutaFichero);
		} catch (Exception e) {
			log.error("Error eliminando la compra {}", compra.getIdCompra(), e);
			throw new ErrorPersistenciaException(e);
		}
	}

	@Override
	public CarritoCompraImpl updateCompra(CarritoCompraImpl compra) throws ErrorPersistenciaException {
		try {
			guardarCompras(compras, rutaFichero);
			return compra;
		} catch (Exception e) {
			log.error("Error eliminando la compra {}", compra.getIdCompra(), e);
			throw new ErrorPersistenciaException(e);
		}
	}

	private List<CarritoCompraImpl> cargarCompras(String rutaFichero) throws Exception {
		
		InputStream ficheroStream = getClass().getResourceAsStream(rutaFichero);
		
		ObjectMapper mapper = new ObjectMapper();
		TPVImpl tpv = (TPVImpl) Configuracion.getInstancia().getTPV();
		
		TPVImpl tpvCargado = mapper.readValue(ficheroStream, new TypeReference<TPVImpl>() {});
		tpv.setProductos((List<ProductoImpl>) tpvCargado.getProductos());		
		tpv.setCompras((List<CarritoCompraImpl>) tpvCargado.getCompras());

		this.compras = (List<CarritoCompraImpl>) tpv.getCompras();
		return compras;
	}

	private void guardarCompras(List<CarritoCompraImpl> compras, String rutaFichero) throws Exception {
		String rutaAbsoluta = getClass().getResource(rutaFichero).getPath();

		TPVImpl tpv = (TPVImpl) Configuracion.getInstancia().getTPV();

		tpv.setCompras(compras);
		this.compras = compras;
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(rutaAbsoluta), tpv);
	}

}

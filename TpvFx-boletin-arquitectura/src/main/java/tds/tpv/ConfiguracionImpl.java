package tds.tpv;

import tds.tpv.adapters.repository.impl.ProductoRepositoryJSONImpl;
import tds.tpv.negocio.controladores.ControladorTPV;

public class ConfiguracionImpl extends Configuracion {

	private ControladorTPV controlador;

	public ConfiguracionImpl() {
		this.controlador = new ControladorTPV(new ProductoRepositoryJSONImpl());
	}
	
	@Override
	public ControladorTPV getControladorTPV() {
		return controlador;
	}
	
	@Override
	public String getRutaAlmacen() {
		return "/data/almacen.json";
	}
	
}
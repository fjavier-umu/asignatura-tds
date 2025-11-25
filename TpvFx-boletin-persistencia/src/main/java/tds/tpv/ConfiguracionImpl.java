package tds.tpv;

import tds.tpv.adapters.repository.impl.CompraRepositoryJSONImpl;
import tds.tpv.adapters.repository.impl.ProductoRepositoryJSONImpl;
import tds.tpv.negocio.controladores.ControladorTPV;
import tds.tpv.negocio.modelo.TPV;
import tds.tpv.negocio.modelo.impl.TPVImpl;

public class ConfiguracionImpl extends Configuracion {

	private ControladorTPV controlador;
	private TPV tpv;

	public ConfiguracionImpl() {
		this.controlador = new ControladorTPV(new ProductoRepositoryJSONImpl(), new CompraRepositoryJSONImpl());
		this.tpv = new TPVImpl();
	}
	
	@Override
	public ControladorTPV getControladorTPV() {
		return controlador;
	}
		
	@Override
	public String getRutaTPV() {
		return "/data/tpv.json";
	}

	@Override
	public TPV getTPV() {
		return tpv;	
	}


}
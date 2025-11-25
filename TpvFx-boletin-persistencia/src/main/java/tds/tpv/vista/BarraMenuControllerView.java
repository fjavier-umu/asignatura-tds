package tds.tpv.vista;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import tds.tpv.Configuracion;

public class BarraMenuControllerView {

	@FXML
	private void irAStock() throws IOException {
		Configuracion.getInstancia().getSceneManager().showTiendaStock();
	}

	@FXML
	public void salir(Event e) {
		Platform.exit();
	}
	
	@FXML
	void abrirAcerca(Event e) {
		Configuracion.getInstancia().getSceneManager().showAcerca();
	}

}

package tds.tpv.adapters.repository.exceptions;

public class ErrorPersistenciaException extends Exception {

	public ErrorPersistenciaException(String mensaje) {
		super(mensaje);
	}
	
	public ErrorPersistenciaException(Exception excepcion) {
		super(excepcion);
	}

}

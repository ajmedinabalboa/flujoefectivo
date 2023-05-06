package com.apiflujoefectivo.flujoefectivo.exception;

public class MensajeError {	
	
	private String exception;	
	private String mensaje;
	private String ruta;
	
	public MensajeError(String exception, String mensaje, String ruta) {
		super();
		this.exception = exception;
		this.mensaje = mensaje;
		this.ruta = ruta;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public String toString() {
		return "MensajeError [exception=" + exception + ", mensaje=" + mensaje + ", ruta=" + ruta + "]";
	}
	
}

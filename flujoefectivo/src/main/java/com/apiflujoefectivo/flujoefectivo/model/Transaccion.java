package com.apiflujoefectivo.flujoefectivo.model;

import java.util.Date;

public class Transaccion {	
	
	private String tipoTransaccion;
	private String numeroCuenta;	
	private double montoTransaccion;
	private String moneda;
	private Date fechaTransaccion;
	
	public Transaccion(String tipoTransaccion, String numeroCuenta, double montoTransaccion, Date fechaTransaccion, String moneda) {
		super();		
		this.tipoTransaccion = tipoTransaccion;
		this.numeroCuenta = numeroCuenta;		
		this.montoTransaccion = montoTransaccion;
		this.fechaTransaccion = fechaTransaccion;
		this.moneda = moneda;
	}	

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}	

	public double getMontoTransaccion() {
		return montoTransaccion;
	}

	public void setMontoTransaccion(double montoTransaccion) {
		this.montoTransaccion = montoTransaccion;
	}

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}
	
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	@Override
	public String toString() {
		return "Transaccion [tipoTransaccion=" + tipoTransaccion + ", numeroCuenta=" + numeroCuenta
				+ ", montoTransaccion=" + montoTransaccion + ", moneda=" + moneda + ", fechaTransaccion="
				+ fechaTransaccion + "]";
	}
}

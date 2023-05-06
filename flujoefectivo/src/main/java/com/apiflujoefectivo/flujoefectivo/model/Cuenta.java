package com.apiflujoefectivo.flujoefectivo.model;

import java.util.Date;

public class Cuenta {

	private String numeroCuenta;
	private String cliente;
	private String estadoCuenta;
	private double saldoCuenta;
	private String moneda;	
	private boolean retiroMontoMayor;
	private Date fechaCreacion;
	
	public Cuenta(String numeroCuenta, String cliente,String estadoCuenta, double saldoCuenta, Date fechaCreacion,String moneda,boolean retiroMontoMayor) {
		super();		
		this.numeroCuenta = numeroCuenta;
		this.cliente = cliente;
		this.estadoCuenta = estadoCuenta;
		this.saldoCuenta = saldoCuenta;
		this.fechaCreacion = fechaCreacion;
		this.moneda = moneda;
		this.retiroMontoMayor = retiroMontoMayor;
	}	

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public double getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(double saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}
	
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public boolean isRetiroMontoMayor() {
		return retiroMontoMayor;
	}

	public void setRetiroMontoMayor(boolean retiroMontoMayor) {
		this.retiroMontoMayor = retiroMontoMayor;
	}
	
	@Override
	public String toString() {
		return "Cuenta [numeroCuenta=" + numeroCuenta + ", cliente=" + cliente + ", estadoCuenta=" + estadoCuenta
				+ ", saldoCuenta=" + saldoCuenta + ", moneda=" + moneda + ", fechaCreacion=" + fechaCreacion + "]";
	}

}

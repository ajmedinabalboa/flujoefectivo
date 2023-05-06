package com.apiflujoefectivo.flujoefectivo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apiflujoefectivo.flujoefectivo.exception.MensajeError;
import com.apiflujoefectivo.flujoefectivo.model.*;

@Service
public class FlujoEfectivoService {	

	/* Para emular la persistencia se crean estas 2 instacias de Listas
	 * cuentas: representa las listas de cuentas de clientes
	 * transacciones: representa las listas de transacciones de cuentas de clientes */
	private List<Cuenta> cuentas;
	private List<Transaccion> transacciones;
	
	/**
	 * Constructor del servicio 
	 * @author alvaro.medina.balboa	 
	*/
	public FlujoEfectivoService() {
		cuentas = new ArrayList<>();
		transacciones = new ArrayList<>();
	}
	
	/**
	 * Metodo que permite crear la cuenta de un cliente
	 * @author alvaro.medina.balboa
	 * @param cliente, numCuenta, montoApertura, moneda
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	public HashMap<String,Object> crearCuenta(String cliente, String numeroCuenta, double montoApertura,String moneda) {
		HashMap<String,Object> respuesta = new HashMap<String,Object>();
		// Verificamos que el numero de cuenta exista dentro de nuestra coleccion de cuentas
		if(cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta) && p.getMoneda().equals(moneda)).count() == 0) {
			try {
				// creamos el objeto transaccion con la informacion necesaria
				Transaccion transaccion = new Transaccion("APERTURA", numeroCuenta, montoApertura, new Date(),moneda);
				this.transacciones.add(transaccion);
				//creamos el objeto cuenta con la informacion necesaria
				Cuenta cuenta = new Cuenta(numeroCuenta, cliente,"ACTIVE", montoApertura, new Date(),moneda,false);
				this.cuentas.add(cuenta);
				// si todo esta correcto devolver el mensaje de exito
				respuesta.put("Mensaje", "La cuenta fue creada satisfactoriamente");
			}catch (Exception e) {
				// en caso de error devolvemos una instancia de MensajeError con las descripciones necesarias
				MensajeError mensaje = new MensajeError("Error al Aperturar", e.getMessage(),"");
				respuesta.put("Error",mensaje);
			}
		}
		else
		{
			// en caso de que no exista el numero de cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
			MensajeError mensaje = new MensajeError("Error al Aperturar", "El numero de Cuenta y Moneda ya se encuentra registrado.", "");
			respuesta.put("Error",mensaje);
		}
		return respuesta;
	}
	
	/**
	 * Metodo que permite depositar en la cuenta del cliente
	 * @author alvaro.medina.balboa
	 * @param numeroCuenta,monto,moneda
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	public HashMap<String,Object> depositarCuenta(String numeroCuenta, double monto,String moneda) {
		HashMap<String,Object> respuesta = new HashMap<String,Object>();
		// Verificamos que el numero de cuenta exista dentro de nuestra coleccion de cuentas
		if(cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).count() > 0) {
			Optional<Cuenta> cuenta = cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).findFirst();
			// verificamos la moneda de la cuenta sea igual a la moneda de la transaccion a realizar
			if(cuenta.get().getMoneda().equals(moneda)) {
				// verificamos si la cuenta se encuentra en estado HOLD
				if(cuenta.get().getEstadoCuenta().equals("HOLD")) {
					// verificamos si el deposito es mayor o igual al saldo negativo
					if(cuenta.get().getSaldoCuenta()+monto >= 0)
						cuenta.get().setEstadoCuenta("ACTIVE");
				}
				try {
					// creamos el objeto transaccion con la informacion necesaria
					Transaccion transaccion = new Transaccion("DEPOSITO", numeroCuenta, monto, new Date(),moneda);
					this.transacciones.add(transaccion);
					// actualizamos el saldo en la cuenta
					cuenta.get().setSaldoCuenta(cuenta.get().getSaldoCuenta()+monto);
					respuesta.put("Mensaje", "el deposito se realizo satisfactoriamente");
				}catch (Exception e) {	
					// en caso de error devolvemos una instancia de MensajeError con las descripciones necesarias
					MensajeError mensaje = new MensajeError("Error al depositar", e.getMessage(),"");
					respuesta.put("Error",mensaje);
				}
			}
			else
			{
				// en caso de que no exista el numero de cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
				MensajeError mensaje = new MensajeError("Error al Depositar", "La Moneda no corresponde a la cuenta registrada.", "");
				respuesta.put("Error",mensaje);
			}
		}
		else
		{
			// en caso de que no exista el numero de cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
			MensajeError mensaje = new MensajeError("Error al Depositar", "El numero de Cuenta no se encuentra registrado.", "");
			respuesta.put("Error",mensaje);
		}
		return respuesta;
	}
	
	/**
	 * - Metodo que permite retirar de la cuenta del cliente
	 * @author alvaro.medina.balboa
	 * @param numeroCuenta, monto, moneda
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	public HashMap<String,Object> retirarCuenta(String numeroCuenta, double monto, String moneda) {
		HashMap<String,Object> respuesta = new HashMap<String,Object>();
		// Verificamos que el numero de cuenta exista dentro de nuestra coleccion de cuentas
		if(cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).count() > 0) {
			Optional<Cuenta> cuenta = cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).findFirst();
			// verificamos si la cuenta se encuentra en estado HOLD
			if(!cuenta.get().getEstadoCuenta().equals("HOLD")) {
				// verificamos la moneda de la cuenta sea igual a la moneda de la transaccion a realizar
				if(cuenta.get().getMoneda().equals(moneda)) {
					boolean retiroAprobado = true;
					// logica de negocio para el retiro de monto superior a saldo por única vez
					if(cuenta.get().getSaldoCuenta()-monto <= 0) {
						if(cuenta.get().isRetiroMontoMayor())
							retiroAprobado = false;
						else {
							cuenta.get().setEstadoCuenta("HOLD");
							cuenta.get().setRetiroMontoMayor(true);
						}
					}	
					// si la variable retiroAprobado no se ha modificado se procede con el retiro
					if(retiroAprobado) {
						try {
							// creamos el objeto transaccion con la informacion necesaria
							Transaccion transaccion = new Transaccion("RETIRO", numeroCuenta, (-1)*monto, new Date(),moneda);
							this.transacciones.add(transaccion);
							// actualizamos el saldo en la cuenta
							cuenta.get().setSaldoCuenta(cuenta.get().getSaldoCuenta()-monto);	
							respuesta.put("Mensaje", "el retiro se realizo satisfactoriamente");
						}catch (Exception e) {	
							// en caso de error devolvemos una instancia de MensajeError con las descripciones necesarias							
							MensajeError mensaje = new MensajeError("Error al Retirar", e.getMessage(),"");
							respuesta.put("Error",mensaje);
						}
					}
					else
					{
						// en caso de que ya se haya realizado un retiro con monto superior al saldo devolvemos una instancia de MensajeError con las descripciones necesarias
						MensajeError mensaje = new MensajeError("Error al Retirar", "Se retiró un monto superior al saldo con anterioridad.", "");
						respuesta.put("Error",mensaje);
					}
				}
				else
				{
					// en caso de que la moneda no corresponda a la misma de la cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
					MensajeError mensaje = new MensajeError("Error al Retirar", "La Moneda no corresponde a la cuenta registrada.", "");
					respuesta.put("Error",mensaje);
				}
			}
			else
			{
				// en caso de que la cuenta se encuentre en estado HOLD devolvemos una instancia de MensajeError con las descripciones necesarias
				MensajeError mensaje = new MensajeError("Error al Retirar", "La cuenta se encuentra en estado HOLD y no se puede retirar.", "");
				respuesta.put("Error",mensaje);
			}	
		}
		else
		{
			// en caso de que no exista el numero de cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
			MensajeError mensaje = new MensajeError("Error al Retirar", "El numero de Cuenta no se encuentra registrado.", "");
			respuesta.put("Error",mensaje);
		}	
		return respuesta;
	}
	
	/**
	 * - Metodo que devuelve el saldo de la cuenta del cliente
	 * @author alvaro.medina.balboa
	 * @param numeroCuenta
	 * @return HashMap<String,Object> que representa el mensaje de éxito con el saldo o mensaje de error
	*/
	public HashMap<String,Object> consultarSaldoCuenta(String numeroCuenta) {		
		HashMap<String,Object> respuesta = new HashMap<String,Object>();	
		// Verificamos que el numero de cuenta exista dentro de nuestra coleccion de cuentas
		if(cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).count() > 0) {
			try {
				// filtramos la cuenta por el numero de cuenta
				Optional<Cuenta> cuenta = cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).findFirst();
				// enviamos el mensaje con el saldo total
				respuesta.put("Informacion", "Saldo Total: "+cuenta.get().getSaldoCuenta());
			}catch (Exception e) {
				// en caso de error devolvemos una instancia de MensajeError con las descripciones necesarias
				MensajeError mensaje = new MensajeError("Error al Consultar", e.getMessage(),"");
				respuesta.put("Error",mensaje);
			}
		}
		else
		{
			// en caso de que no exista el numero de cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
			MensajeError mensaje = new MensajeError("Error al Consultar", "El numero de Cuenta no se encuentra registrado.", "");
			respuesta.put("Error",mensaje);
		}	
		return respuesta;
	}
	
	/**
	 * - Metodo que permite consultar el historial de transacciones del cliente
	 * @author alvaro.medina.balboa
	 * @param numeroCuenta
	 * @return HashMap<String,Object> que representa el listado de transacciones o mensaje de error
	*/
	public HashMap<String,Object> obtenerHistoricoTransacciones(String numeroCuenta) {
		HashMap<String,Object> respuesta = new HashMap<String,Object>();
		// Verificamos que el numero de cuenta exista dentro de nuestra coleccion de cuentas
		if(cuentas.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).count() > 0) {
			try {
				// filtramos la informacion por el numero de cuenta
				List<Transaccion> historialTransacciones = transacciones.stream().filter(p -> p.getNumeroCuenta().equals(numeroCuenta)).toList();
				// enviamos el listado de las transacciones
				respuesta.put("Historial de Transacciones", historialTransacciones);
			}catch (Exception e) {	
				// en caso de error devolvemos una instancia de MensajeError con las descripciones necesarias
				MensajeError mensaje = new MensajeError("Error al Consultar", e.getMessage(),"");
				respuesta.put("Error",mensaje);
			}
		}
		else
		{
			// en caso de que no exista el numero de cuenta devolvemos una instancia de MensajeError con las descripciones necesarias
			MensajeError mensaje = new MensajeError("Error al Consultar", "El numero de Cuenta no se encuentra registrado.", "");
			respuesta.put("Error",mensaje);
		}
		return respuesta;
	}
	
}

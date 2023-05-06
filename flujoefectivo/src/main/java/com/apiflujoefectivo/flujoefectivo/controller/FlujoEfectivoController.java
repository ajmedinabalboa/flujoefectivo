package com.apiflujoefectivo.flujoefectivo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apiflujoefectivo.flujoefectivo.dto.AperturaDTO;
import com.apiflujoefectivo.flujoefectivo.dto.OperacionDTO;
import com.apiflujoefectivo.flujoefectivo.service.FlujoEfectivoService;

@RestController
@RequestMapping("/api")
public class FlujoEfectivoController {
	
	private FlujoEfectivoService flujoEfectivoService;

	/**
	 * - Constructor para el servicio FlujoEfectivoService
	 * - Se instancia la logica de Servicio flujoEfectivoService
	 * @author alvaro.medina.balboa
	 * @param flujoEfectivoService	 
	*/
	@Autowired
	public FlujoEfectivoController(FlujoEfectivoService flujoEfectivoService) {
		this.flujoEfectivoService = flujoEfectivoService;
	}
	
	/**
	 * Metodo que llama a la instancia GET y consultar el saldo del cliente
	 * @author alvaro.medina.balboa
	 * @param numeroCuenta
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	@GetMapping("/saldo/{numeroCuenta}")
	@ResponseStatus(HttpStatus.OK)
	public HashMap<String,Object> getConsultarSaldo(@PathVariable String numeroCuenta) {
		return flujoEfectivoService.consultarSaldoCuenta(numeroCuenta);
	}
	
	/**
	 * Metodo que llama a la instancia GET y consultar el historial de transacciones del cliente
	 * @author alvaro.medina.balboa
	 * @param numeroCuenta
	 * @return HashMap<String,Object> que representa el listado de transacciones o mensaje de error
	*/
	@GetMapping("/historial/{numeroCuenta}")
	@ResponseStatus(HttpStatus.OK)
	public HashMap<String,Object> getHistorialTransacciones(@PathVariable String numeroCuenta) {
		return flujoEfectivoService.obtenerHistoricoTransacciones(numeroCuenta);
	}
	
	/**
	 * Metodo que llama a la instancia POST y realiza el deposito en la cuenta del cliente
	 * @author alvaro.medina.balboa
	 * @param operacionDTO objeto DTO para ingresar el numeroCuenta,monto,moneda
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	@PostMapping("/depositar")
	@ResponseStatus(HttpStatus.CREATED)
	public HashMap<String,Object> getDepositar(@RequestBody OperacionDTO operacionDTO) {
		return flujoEfectivoService.depositarCuenta(operacionDTO.getNumeroCuenta(), operacionDTO.getMonto(), operacionDTO.getMoneda());
		
	}
	
	/**
	 * Metodo que llama a la instancia POST y realiza el retiro en la cuenta del cliente
	 * @author alvaro.medina.balboa
	 * @param operacionDTO objeto DTO para ingresar el numeroCuenta,monto,moneda
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	@PostMapping("/retirar")
	@ResponseStatus(HttpStatus.CREATED)
	public HashMap<String,Object> getRetirar(@RequestBody OperacionDTO operacionDTO) {
		return flujoEfectivoService.retirarCuenta(operacionDTO.getNumeroCuenta(), operacionDTO.getMonto(), operacionDTO.getMoneda());		
	}
	
	/**
	 * Metodo que llama a la instancia POST y realiza la apertura de cuenta del cliente
	 * @author alvaro.medina.balboa
	 * @param aperturaDTO objeto DTO para ingresar el cliente,numeroCuenta,monto,moneda
	 * @return HashMap<String,Object> que representa el mensaje de éxito o mensaje de error
	*/
	@PostMapping("/aperturar")
	@ResponseStatus(HttpStatus.CREATED)
	public HashMap<String,Object> aperturarCuenta(@RequestBody AperturaDTO aperturaDTO) {
		return this.flujoEfectivoService.crearCuenta(aperturaDTO.getCliente(), aperturaDTO.getNumeroCuenta(), aperturaDTO.getMonto(),aperturaDTO.getMoneda());		
	}
	
}

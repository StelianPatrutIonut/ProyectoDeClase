package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;

	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	// Constructor de la clase que recibe la instancia de la web de compra y el número del fan
	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;

	}
	/*	@Override
	public void run() {
		while (entradasCompradas < EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
			synchronized (webCompra) {
				if (webCompra.hayEntradas()) {
					webCompra.comprarEntrada();
					entradasCompradas++;
					dimeEntradasCompradas();
				} else {
					try {
						webCompra.wait(); // Espera a que el promotor reponga las entradas
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						return;
					}
				}
			}
			try {
				Thread.sleep((int) (Math.random() * 2000) + 1000); // Dormir entre 1 y 3 segundos
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}

		synchronized (webCompra) {
			if (--EjemploTicketMaster.NUM_FANS == 0) {
				WebCompraConciertos.todosFansCompletos = true;
				webCompra.notifyAll(); // Notificar que todos los fans han completado sus compras
			}
		}
	}
*/

	// Método run que se ejecutará cuando se inicie el hilo
	@Override
	public void run() {

				while (entradasCompradas <= EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
					synchronized (webCompra) {
				if (webCompra.hayEntradas()) {
					mensajeFan("Voy a intentar comprar una entrada, veremos qué pasa...");
					webCompra.comprarEntrada();
					entradasCompradas++;
					//mensajeFan("¡Entrada comprada! El fan " + this.numeroFan + " ha comprado un total de: " + entradasCompradas);
					dimeEntradasCompradas();
				} else {
					try {
						//mensajeFan("Vaya no hay entradas actualmente, voy a esperar a que el promotor saque entradas para poder comprar entradas");
						webCompra.wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
			try{
				Thread.sleep((int) (Math.random() * 2000) + 1000); // Dormir entre 1 y 3 segundos
			}catch (InterruptedException e){
				Thread.currentThread().interrupt();
				return;
			}

		}


	}

	// Método para imprimir la cantidad de entradas compradas por el fan
	public void dimeEntradasCompradas() {
		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}




	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
			System.out.println(System.currentTimeMillis() + "|" + tabuladores + " Fan " + this.numeroFan + ": " + mensaje);

	}
}

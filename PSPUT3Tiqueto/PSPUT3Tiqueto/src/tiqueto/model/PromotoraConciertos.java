package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.Random;

public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	// Constructor que recibe la instancia de la web de compra
	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	// Método run que se ejecutará cuandsso se inicie el hilo
	@Override
	public void run() {

		int entrdasRepuestas = 0;
		Random random = new Random();
		while (entrdasRepuestas < EjemploTicketMaster.TOTAL_ENTRADAS) {
			synchronized (webCompra) {
				if (!webCompra.hayEntradas()) {
					//int entradasReponer = EjemploTicketMaster.REPOSICION_ENTRADAS;
					int entradasRepuestasNuevas = webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);
					entrdasRepuestas+=entradasRepuestasNuevas;
					mensajePromotor("Se ha repuesto "+entradasRepuestasNuevas+" y total repuestas "+entrdasRepuestas);
					/*mensajePromotor("El promotor acaba de reponer entradas. Quedan " +
							webCompra.entradasRestantes() + " entradas disponibles. Se repusieron " +
							entradasRepuestasNuevas + " entradas.");*/
					webCompra.notifyAll();
				}
			}

			try {
				int tiempo = random.nextInt(5000) + 3000;
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();//saber que hilo le ha despertado
				break;
			}
		}

		// Al finalizar la venta, cerramos la venta
		synchronized (webCompra) {
			webCompra.cerrarVenta();
			webCompra.notifyAll();
		}
	}


		/**
		 }
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);
	}
}

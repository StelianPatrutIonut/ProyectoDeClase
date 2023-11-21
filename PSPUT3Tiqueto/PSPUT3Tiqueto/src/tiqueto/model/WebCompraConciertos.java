package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb {

	private int numeroDeEntradasDisponibles;//aqui es private lo he cambiado a publico para probar una cosa
	private int entradas;

	// Constructor de la clase
	public WebCompraConciertos() {
		super();
		this.numeroDeEntradasDisponibles= EjemploTicketMaster.TOTAL_ENTRADAS;
		this.entradas=0;
	}

	// Implementación del método para comprar una entrada
	@Override
	public synchronized boolean comprarEntrada() {
		if (entradas > 0){
			entradas--;
			mensajeWeb("Entrada comprada, quedan: "+entradas);
			return true;
		}else {
			try{
				mensajeWeb("No quedan entradas disponibles ");
				wait();
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
			return false;
        }
	}

	// Implementación del método para reponer entradas
	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {
		if (numeroDeEntradasDisponibles > 0 || hayEntradas()==false){
			entradas += numeroEntradas;
		/*	//numeroDeEntradasDisponibles-=numeroEntradas;
			mensajeWeb("Se han repuesto las entradas, el promotor ha repuesto: " + numeroEntradas + " entradas");
			mensajeWeb("Actualmente hay: " + entradas);*/
			int entradasReponer = numeroEntradas;
			numeroDeEntradasDisponibles -= entradasReponer;
			notifyAll();
			return entradasReponer;
		}
		return  0;

	}

	// Implementación del método para cerrar la venta
	@Override
	public void cerrarVenta() {
		// TODO Auto-generated method stub
		mensajeWeb("Se ha cerrado la venta de entradas ya no queda nada");
		mensajeWeb("Ventana cerrada");
		notifyAll();

	}

	// Implementación del método para verificar si hay entradas disponibles
	@Override
	public boolean hayEntradas() {
		/*if (entradas>0){
			mensajeWeb("Hay entradas disponibles");
			return true;
		}else{
			mensajeWeb("No hay entradas disponibles");
		return false;
		}*/
		return entradas > 0;
	}

	// Implementación del método para obtener el número de entradas restantes
	@Override
	public int entradasRestantes() {
		// TODO Auto-generated method stub
		//mensajeWeb("Actualmente quedan "+numeroDeEntradasDisponibles+" entradas disponibles. Corre que se agotan");
		return entradas;
	}

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
	}

}

package tiqueto;

import java.util.ArrayList;
import java.util.List;

import tiqueto.model.FanGrupo;
import tiqueto.model.PromotoraConciertos;
import tiqueto.model.WebCompraConciertos;

public class EjemploTicketMaster {

	// Total de entradas que se venderán
	public static int TOTAL_ENTRADAS = 10 ;

	// El número de entradas que repondrá cada vez el promotor
	public static int REPOSICION_ENTRADAS = 2;

	// El número máximo de entradas por fan
	public static int MAX_ENTRADAS_POR_FAN = 5;

	// El número total de fans
	public static int NUM_FANS = 4;

	public static void main(String[] args) throws InterruptedException {

		// Mensaje inicial que indica el inicio de la venta de entradas
		String mensajeInicial = "[ Empieza la venta de tickets. Se esperan %d fans, y un total de %d entradas ]";
		System.out.println(String.format(mensajeInicial, NUM_FANS, TOTAL_ENTRADAS));

		// Se crea una instancia de la clase WebCompraConciertos que representa la web de compra de entradas
		WebCompraConciertos webCompra = new WebCompraConciertos();

		// Se crea una instancia de la clase PromotoraConciertos que representa al promotor del concierto
		// La promotora se inicializa con la web de compra de entradas
		PromotoraConciertos liveNacion = new PromotoraConciertos(webCompra);

		// Se crea una lista para almacenar a los fans
		List<FanGrupo> fans = new ArrayList<>();

		// Creamos y lanzamos hilos para cada fan
		for (int numFan = 1; numFan <= NUM_FANS; numFan++) {
			FanGrupo fan = new FanGrupo(webCompra, numFan);
			fans.add(fan);
			fan.start();
		}

		// Lanzamos al promotor para que empiece a reponer entradas
		liveNacion.start();
		Thread.sleep(5000);

		// Esperamos a que el promotor termine, para preguntar a los fans cuántas entradas tienen compradas
		liveNacion.join();

		// Imprimimos información sobre la fase de venta
		System.out.println("\n [ Terminada la fase de venta - Sondeamos a pie de calle a los compradores ] \n");
		System.out.println("Total entradas ofertadas: " + TOTAL_ENTRADAS);
		System.out.println("Total entradas disponibles en la web: " + webCompra.entradasRestantes());

		// Preguntamos a cada fan cuántas entradas han comprado
		for (FanGrupo fan : fans) {
			fan.dimeEntradasCompradas();
		}

	}
}
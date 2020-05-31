package HorseRacing;
/**
 * Classe che implementa e gestisce la produzione e il rilascio di una risorsa. Permette la comunicazione fra il processo che realizza un movimento e i thread che concorrono per utilizzarlo
 * @author Patrissi Mathilde
 */
public class movement {

private boolean step = false;
/**costruttore di default
 */
public movement() {
	
}
/**Metodo per l'acquisizione di un movimento
 */
public synchronized void get() { 
	while (step == false) { //rimango in attesa finche' non viene fornita la possibilita' di effettuare uno spostamento
		try { wait(); } catch(InterruptedException e){}
	}
	step = false;
	notifyAll();
}
/**Metodo per la produzione di un movimento
 */
public synchronized void put() { 
	while (step == true) { //resto in attesa finche' un cavallo non ha effettuato uno spostamento
		try { wait(); } catch(InterruptedException e){}
	}
	step = true;
	notifyAll();
	
}
}
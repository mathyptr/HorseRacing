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
	while (step == false) {
		try { wait(); } catch(InterruptedException e){}
	}
//	System.out.println("-Horse: mi sposto"+x);
	step = false;
	notifyAll();
}
/**Metodo per la produzione di un movimento
 */
public synchronized void put() {
	while (step == true) {
		try { wait(); } catch(InterruptedException e){}
	}
//	System.out.println("+Race: spostamento "+value);
	step = true;
	notifyAll();
	
}
}
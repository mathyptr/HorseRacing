/**
 * descrizione
 * @author Patrissi Mathilde
 */
public class movement {
private int x;
private boolean step = false;
/**Metodo per l'acquisizione di un movimento
 * @return il numero di pixel di cui il movimento in questione permette di spostarsi
 */
public synchronized int get() {
	while (step == false) {
		try { wait(); } catch(InterruptedException e){}
	}
	System.out.println("-Horse: mi sposto"+x);
	step = false;
	int v=x;//mathy
	notifyAll();
	return v;
//prof	return valore;
}
/**Metodo per la produzione di un movimento
 * @param value int
 */
public synchronized void put(int value) {
	while (step == true) {
		try { wait(); } catch(InterruptedException e){}
	}
	System.out.println("+Race: spostamento "+value);
	x = value;
	step = true;
	notifyAll();
	
}
}
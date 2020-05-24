public class movement {
private int x;
private boolean step = false;
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
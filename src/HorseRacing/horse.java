package HorseRacing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Classe per l'implementazione del cavallo: gestisce il suo spostamento e il caricamento delle sue immagini da file per la gestione dell'animazione
 * @author Patrissi Mathilde
 */

public class horse extends Thread{
	private java.util.Vector <BufferedImage> imgb =new java.util.Vector(1,1);
	private java.util.Vector <Integer> speedhorse =new java.util.Vector(1,1);

	private movement step;
	private int end, num,posx, posy,indeximg,speed,finalpos;
	private String name;
	/**
	 * Costruttore della classe horse
	 * @param num int
	 * @param end int
	 * @param step movement
	 */
	public horse(int num,int end, movement step) {
		this.num=num;
		this.end=end;
		this.step=step;
		this.setName("Cavallo "+this.num);
		posx=0;
		posy=0;
		finalpos=0;		
		indeximg=0;
		loadImage();
		initDefaultSpeed();
		speed=speedhorse.get((int)(Math.random()*10));
		MessagesBundle msgB=new MessagesBundle();
		name=msgB.GetResourceValue("name_horse"+num);
	   
	}
	/**metodo per l'inizializzazione di tutte le possibili velocità associabili ad un cavallo
	 *
	*/
	private void initDefaultSpeed(){
		speedhorse.add(14);
		speedhorse.add(15);
		speedhorse.add(18);
		speedhorse.add(10);
		speedhorse.add(12);
		speedhorse.add(14);
		speedhorse.add(16);
		speedhorse.add(18);
		speedhorse.add(19);
		speedhorse.add(20);		
	  }	
	/**metodo per il caricamento delle immagini di un cavallo: queste permetteranno la sua animazione
	 *@exception e
	*/
	private void loadImage() {
		try {
			
			InputStream is=getClass().getResourceAsStream("/img/horse"+num+"/horse.png");
			imgb.addElement( ImageIO.read(is));
			//imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse.png")));    
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse1.png")));
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse2.png")));
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse3.png")));
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse4.png")));
			
			}
	     catch (Exception e){
			   System.out.println("errore input");
		 }
		 indeximg=(int)(Math.random()*5);
	}	
	/**metodo per ottenere il nome del cavallo
	 *@return il nome del cavallo
	*/
	public String getHorseName() {
		return name;
	}
	/**metodo per ottenere il valore della coordinata x attuale 
	 *@return il valore della coordinata x
	*/
	public Integer getPosX() {
		return posx;
	}
	/**metodo per ottenere il valore della coordinata y attuale 
	 *@return il valore della coordinata y
	*/
	public Integer getPosY() {
		return posy;
	}
	/**metodo per ottenere l'immagine del cavallo da disegnare
	 *@return la successiva immagine del cavallo per gestire l'animazione
	*/
	public BufferedImage getImage() {
	
		if(posx<end)
			indeximg=(indeximg+1)%imgb.size();
		return imgb.get(indeximg);		
	}	
	/**metodo per impostare la posizione della coordinata x
	 * @param posx Integer
	 */
	public void setPosX(Integer posx) {
		
		if(posx<end)
			this.posx=posx+speed;
	}
	/**metodo per impostare la posizione della coordinata y
	 * @param posy Integer
	 */
	public void  setPosY(Integer posy) {
		 this.posy=posy;
	}
	/**metodo per impostare la velocita'
	 * @param speed Integer
	 */
	public void setspeed(Integer speed) {
		this.speed=speed;
	}
	/**metodo per impostare la posizione di arrivo al traguardo
	 * @param finalpos int
	 */
	public void SetFinalPosition(int finalpos){
		this.finalpos=finalpos;
	}
	/**metodo per ottenere la posizione finale
	 * @return la posizione finale
	 */
	public int GetFinalPosition()	{
		return  finalpos;
	}
	/**metodo per spostare il cavallo lungo l'asse x data la sua velocita'
	 */
	public void move() {
		int n;
		n=posx+1+speed;
		if(posx<end)
			if(n>end)
				posx=end;
			else
				posx=n;
	}	
	/**metodo che viene richiamato per l'esecuzione del thread
	 */
	public void run() {
		while(posx<end) {
			step.get();
			 move();
			 try {
				sleep(500);
			} catch (InterruptedException e) {
				
			}
		}	
	}
}

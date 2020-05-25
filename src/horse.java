
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class horse extends Thread{
	private java.util.Vector <BufferedImage> imgb =new java.util.Vector(1,1);
	private java.util.Vector <Integer> speedhorse =new java.util.Vector(1,1);

	private movement step;
	private int end, num,posx, posy,indeximg,speed,finalpos;
	
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
	   
	}
	private void initDefaultSpeed(){
		speedhorse.add(4);
		speedhorse.add(5);
		speedhorse.add(8);
		speedhorse.add(10);
		speedhorse.add(12);
		speedhorse.add(14);
		speedhorse.add(16);
		speedhorse.add(18);
		speedhorse.add(19);
		speedhorse.add(20);		
	  }	

	private void loadImage() {
		try {
			
			InputStream is=getClass().getResourceAsStream("/img/horse"+num+"/horse.png");
			imgb.addElement( ImageIO.read(is));
			//imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse.png")));    
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse1.png")));
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse2.png")));
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse3.png")));
		    imgb.addElement( ImageIO.read(getClass().getResourceAsStream("/img/horse"+num+"/horse4.png")));
			
	/*   imgb.addElement( ImageIO.read(new File("resources/horse"+num+"/horse.png")));    
	     imgb.addElement( ImageIO.read(new File("resources/horse"+num+"/horse1.png")));
	     imgb.addElement( ImageIO.read(new File("resources/horse"+num+"/horse2.png")));
	     imgb.addElement( ImageIO.read(new File("resources/horse"+num+"/horse3.png")));
	     imgb.addElement( ImageIO.read(new File("resources/horse"+num+"/horse4.png")));
*/
			}
	     catch (Exception e){
			   System.out.println("errore input");
		 }
		 indeximg=(int)(Math.random()*5);
	}		
	public Integer getPosX() {
		return posx;
	}
	public Integer getPosY() {
		return posy;
	}
	public BufferedImage getImage() {
	
		if(posx<end)
			indeximg=(indeximg+1)%imgb.size();
		return imgb.get(indeximg);		
			
	//		indeximg=indeximg%imgb.size()+1;
	//		return imgb.get(indeximg-1);		
	}	
	
	public void setPosX(Integer posx) {
		
		if(posx<end)
			this.posx=posx+speed;
	}
	public void  setPosY(Integer posy) {
		 this.posy=posy;
	}
	public void setspeed(Integer speed) {
		this.speed=speed;
	}
	public void SetFinalPosition(int finalpos){
		this.finalpos=finalpos;
	}
	public int GetFinalPosition()	{
		return  finalpos;
	}
	public void move() {
		int n;
		n=posx+1+speed;
		if(posx<end)
			if(n>end)
				posx=end;
			else
				posx=n;
	}	
	public void run() {
		int valore;
		while(posx<end) {
			 valore = step.get();
			 move();
			 try {
				sleep(500);
			} catch (InterruptedException e) {
				
			}
//			 System.out.println("-Consumatore #"+this.number+"get:"+valore);
//			System.out.println(Thread.currentThread().getName()+" giro "+i);
		}	
		System.out.println(Thread.currentThread().getName()+" ARRIVATO");
	}
}

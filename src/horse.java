
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class horse extends Thread{
	private java.util.Vector <BufferedImage> imgb =new java.util.Vector(1,1);
	private movement step;
	private int end, num,posx, posy,indeximg,speed;
	
	public horse(int num,int end, movement step) {
		this.num=num;
		this.end=end;
		this.step=step;
		this.setName("Cavallo "+this.num);
		posx=0;
		posy=0;
		speed=0;		
		indeximg=0;
		loadImage();
	   
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
		indeximg=indeximg%imgb.size()+1;
		return imgb.get(indeximg-1);		
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
	
	public void move() {
		
		if(posx<end)
			this.posx=posx+1+speed;
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

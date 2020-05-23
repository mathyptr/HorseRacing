
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class horse extends Thread{
	private java.util.Vector <BufferedImage> imgb =new java.util.Vector(1,1);

	private int num,posx, posy,indeximg,speed;
	
	public horse(int num) {
		this.num=num;
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
		
		if(posx>=0)
			this.posx=posx-speed;
	}
	public void  setPosY(Integer posy) {
		 this.posy=posy;
	}
	public void setspeed(Integer speed) {
		this.speed=speed;
	}
	
	
	public void run() {
		for(int i=1;i<=100;i++) {
			System.out.println(Thread.currentThread().getName()+" giro "+i);
		}	
		System.out.println(Thread.currentThread().getName()+" ARRIVATO");
	}
}

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class weather {
	 
	 private int width,height,tilewidth,tileheight;
	 private Image rain,thunder;
	 private BufferedImage bkg;
	 
	 public weather(int width, int height,int tilewidth, int tileheight) {
		 this.width=width;
		 this.height =height;
		 this.tilewidth=tilewidth;
		 this.tileheight =tileheight;
		 Init();
     }
	 
	 private void Init()  {
		 int i,j;
	    	try {
//		   		rain=ImageIO.read(getClass().getResourceAsStream("/img/atmo/acqua.png")).getScaledInstance(tilewidth/5, tileheight/5,Image.SCALE_SMOOTH);
		   		rain=ImageIO.read(getClass().getResourceAsStream("/img/atmo/snow.png")).getScaledInstance(tilewidth/5, tileheight/5,Image.SCALE_SMOOTH);
		   		
				thunder= ImageIO.read(getClass().getResourceAsStream("/img/atmo/lampo.png")).getScaledInstance(tilewidth/2, tileheight/2,Image.SCALE_SMOOTH);
				bkg =(BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/atmo/trasparente.png"));//.getScaledInstance(width/2, height/2,Image.SCALE_SMOOTH);
	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   
	    }
	 
	  public void buildWeather()  {
	    	int px,py;
	    	int i,j;
	    	BufferedImage bkg1=null;
			try {
				bkg = ImageIO.read(getClass().getResourceAsStream("/img/atmo/trasparente.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		    Graphics2D gg = bkg.createGraphics();			  
	    	
//		   		Image im=ImageIO.read(getClass().getResourceAsStream("/img/atmo/acqua.png")).getScaledInstance(width/5, height/5,Image.SCALE_SMOOTH);
		  		for(i=0;i<width;i+=30)
	    			for(j=0;j<height;j+=30)
	    			{
	    				px=(int)(Math.random()*30);
	    				py=(int)(Math.random()*30);
	    				gg.drawImage(rain, i+px, j+py, null);
	    			}
					py=(int)(Math.random()*height);    		
			
//	   		Image imL;
//				imL = ImageIO.read(getClass().getResourceAsStream("/img/atmo/lampo.png")).getScaledInstance(width/2, height/2,Image.SCALE_SMOOTH);
//				BufferedImage image=(BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/atmo/trasparente.png"));//.getScaledInstance(width/2, height/2,Image.SCALE_SMOOTH);
				for(i=0;i<width;i+=50) {    		
					py=(int)(Math.random()*height);
		    		gg.drawImage(thunder, i,py, null);	
			 	}
				
//				g.drawImage(image, 0,0, this);	
	   
	    }
		public Image getImage() {
			
			return bkg;//bkg.getScaledInstance(-1, -1,Image.SCALE_SMOOTH);		
		}	
	 
}

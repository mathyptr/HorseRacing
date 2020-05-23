import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class race extends JPanel  {

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 600;
    private final int INITIAL_Y = 10;  
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 100;
    private int width =50;
    private int height =50;
    private int corsia =60;
    private int nmaxkorse=10;
	private java.util.Vector <horse> h =new java.util.Vector(1,1);
	private java.util.Vector <Integer> speedhorse =new java.util.Vector(1,1);
	
    private int numhorse=0;
    private int indeximage=0;
   
    private Timer timer;
    private int x, y;
   
 
    
    public race(int n) {
 	  	
       	  numhorse=n;
    	 //       setBackground(Color.BLACK);
         setBackground(Color.WHITE);    	
         setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
         initDefaultSpeed();
         initHorses();
         
       		try {     
  //  		    InputStream is=getClass().getResourceAsStream("/music/horse.wav");
  	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/galoppata.wav")));
  	         Clip clip = AudioSystem.getClip();
  	         clip.open(audioIn);
  	         //clip.start();
  	         clip.loop(10);
   		}
  	    catch (Exception e){
  		   System.out.println("errore on sound: "+e.toString());
  	    }
  		
       
         x = INITIAL_X;
         y = INITIAL_Y;
         timer = new Timer();
         timer.scheduleAtFixedRate(new ScheduleTask(),INITIAL_DELAY, PERIOD_INTERVAL);        
    }

	private void initDefaultSpeed(){
		speedhorse.add(0);
		speedhorse.add(3);
		speedhorse.add(4);
		speedhorse.add(8);
		speedhorse.add(10);
		speedhorse.add(12);
		speedhorse.add(14);
		speedhorse.add(16);
		speedhorse.add(18);
		speedhorse.add(20);		
	  }		
       
    private void initHorses(){
    	for (int i=0;i<numhorse;i++) {
    		h.addElement(new horse(i+1));
    		h.get(i).setPosX(INITIAL_X);
    		h.get(i).setPosY(INITIAL_Y+i*corsia);
    		h.get(i).setspeed(speedhorse.get((int)(Math.random()*10)));
    	}
	}


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCorsie(g);
        drawHorse(g);
    }
    
    private void drawHorse(Graphics g) {
    	Image hh;
    	BufferedImage hh1 = null,hh2=null;
    	Graphics2D ig;
    	for (int i=0;i<numhorse;i++) {  
    		hh=h.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);
  //  		hh=h.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);
  		 //  		hh2=((ToolkitImage)hh).getBufferedImage();
/*		ig=h.get(i).getImage().createGraphics();
   		Color from = Color.white;
		Color to = Color.green;
		ig.setColor(to);
		ig.fillRect(0, 0,width,height);	
		*/
// 		hh=hh1.getScaledInstance(width, height,Image.SCALE_SMOOTH);
 //		ig.drawImage((BufferedImage) hh, null, 0, 0);
 	    			
   
            g.drawImage(hh, h.get(i).getPosX(), h.get(i).getPosY(), this);
    	 }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawCorsie(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        float[] dash1 = { 2f, 0f, 2f };

        BasicStroke bs1 = new BasicStroke(1, 
            BasicStroke.CAP_BUTT, 
            BasicStroke.JOIN_ROUND, 
            1.0f, 
            dash1,
            2f);
        g2d.setStroke(bs1);   	
    	
    	
    	for (int i=1;i<=numhorse;i++) {
    		g.drawLine(0,i*corsia, INITIAL_X,i*corsia);   	 
    	}
	}
    private void moveHorse() {

    	int i;
   /* 	
    	for ( i=0;i<numhorse;i++) { 
    		x=h.get(i).getPosX();
      		y=h.get(i).getPosY();
    	}
        indeximage++;
        x -= 1;
        if (x <0 ) {            
        	x = INITIAL_X;              
        }
      	for ( i=0;i<numhorse;i++) 
      		h.get(i).setPosX(x);
      	*/
    	for ( i=0;i<numhorse;i++) { 
    		x=h.get(i).getPosX();
      		y=h.get(i).getPosY();
            x -= 1;
      /*      if (x <0 ) {            
        	 x = INITIAL_X;              
            }*/
      		h.get(i).setPosX(x);
    	}
      	indeximage++;
        Toolkit.getDefaultToolkit().sync();
    }
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            moveHorse();        
            repaint();
        }
    }
}
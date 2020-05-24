import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class race extends JPanel  {

    private final int B_WIDTH = 600;
    private final int B_HEIGHT = 600;
    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 10;  
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 50;
    private int end;
    private int width =50;
    private int height =50;
    private int corsia =60;
    private int nmaxkorse=10;
	private java.util.Vector <horse> h =new java.util.Vector(1,1);
	private java.util.Vector <Integer> speedhorse =new java.util.Vector(1,1);
	private movement step;	
	private weather weat;
    private int numhorse=0;
    private int indeximage=0;
   
    private Timer timer;
    private int x, y;
   
 
    
    public race(int n) {
 	  	  step=new movement();
       	  numhorse=n;
       	  end=B_WIDTH;
    	 //       setBackground(Color.BLACK);
       	  Color co= new Color(170,218,24);
         setBackground(co);    	
         setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
         MenuBar();
         initDefaultSpeed();
         initHorses();
         weat = new weather(B_WIDTH,B_HEIGHT,width,height);
         
       		try {     
  //  		    InputStream is=getClass().getResourceAsStream("/music/horse.wav");
  	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/galoppata.wav")));
  	         Clip clip = AudioSystem.getClip();
  	         clip.open(audioIn);
  	         //clip.start();
//  	         clip.loop(10);
   		}
  	    catch (Exception e){
  		   System.out.println("errore on sound: "+e.toString());
  	    }
  		
       
         x = INITIAL_X;
         y = INITIAL_Y;
         
         timer = new Timer();
         timer.scheduleAtFixedRate(new ScheduleTask(),INITIAL_DELAY, PERIOD_INTERVAL);        
    }

    

    private void MenuBar() {
    	JPopupMenu popup = new JPopupMenu();
    	  
    	JMenuBar menuBar = new JMenuBar();
    	ImageIcon exitIcon = new ImageIcon("src/resources/exit.png");

    	JMenu fileMenu = new JMenu("Opzioni");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuItem1 = new JMenuItem("Exit", exitIcon);
        eMenuItem1.setMnemonic(KeyEvent.VK_E);
        eMenuItem1.setToolTipText("Exit application");
        eMenuItem1.addActionListener((event) -> System.exit(0));

        JMenuItem eMenuItem2 = new JMenuItem("Cavallo", exitIcon);
        eMenuItem2.setMnemonic(KeyEvent.VK_C);
        eMenuItem2.setToolTipText("Cavallo");
        eMenuItem2.addActionListener((event) -> System.exit(0));

        JMenuItem eMenuItem3 = new JMenuItem("Fantino", exitIcon);
        eMenuItem3.setMnemonic(KeyEvent.VK_F);
        eMenuItem3.setToolTipText("Fantino");
        eMenuItem3.addActionListener((event) -> System.exit(0));

        JMenuItem eMenuItem4 = new JMenuItem("Percorso", exitIcon);
        eMenuItem4.setMnemonic(KeyEvent.VK_P);
        eMenuItem4.setToolTipText("Percorso");
        eMenuItem4.addActionListener((event) -> System.exit(0));

        JMenuItem eMenuItem5 = new JMenuItem("Cond atmosferiche", exitIcon);
        eMenuItem5.setMnemonic(KeyEvent.VK_A);
        eMenuItem5.setToolTipText("Cond atmo");
        eMenuItem5.addActionListener((event) -> System.exit(0));

        JMenuItem eMenuItem6 = new JMenuItem("Sound off", exitIcon);
        eMenuItem6.setMnemonic(KeyEvent.VK_O);
        eMenuItem6.setToolTipText("Sound off");
        eMenuItem6.addActionListener((event) -> System.exit(0));

        
        fileMenu.add(eMenuItem1);
        fileMenu.add(eMenuItem2);
        fileMenu.add(eMenuItem3);
        fileMenu.add(eMenuItem4);
        fileMenu.add(eMenuItem5);
        menuBar.add(fileMenu);
//        popup.add(eMenuItem);
//        this.setJMenuBar(menuBar);
        this.add(menuBar);
//        this.add(popup);
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
       
    private void initHorses(){
    	for (int i=0;i<numhorse;i++) {
    		h.addElement(new horse(i+1,end,step));
    		h.get(i).setPosX(INITIAL_X);
    		h.get(i).setPosY(INITIAL_Y+i*corsia);
    		h.get(i).setspeed(speedhorse.get((int)(Math.random()*10)));
    		h.get(i).start();
    	}
	}


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCorsie(g);
        drawHorse(g);
        drawWater(g);
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
    		g.drawLine(0,i*corsia, end,i*corsia);   	 
    	}
	}
    
    private void drawWater(Graphics g)  {
    	weat.buildWeather();
    	g.drawImage(weat.getImage(), 0, 0, this);
    }    
    
    private void drawWater1(Graphics g)  {
    	int px,py;
    	int i,j;
    	try {
	   		Image im=ImageIO.read(getClass().getResourceAsStream("/img/atmo/acqua.png")).getScaledInstance(width/5, height/5,Image.SCALE_SMOOTH);
	  		for(i=0;i<B_WIDTH;i+=30)
    			for(j=0;j<B_HEIGHT;j+=30)
    			{
    				px=(int)(Math.random()*30);
    				py=(int)(Math.random()*30);
    				g.drawImage(im, i+px, j+py, this);
    			}
				py=(int)(Math.random()*B_HEIGHT);    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		Image imL;
		try {
			imL = ImageIO.read(getClass().getResourceAsStream("/img/atmo/lampo.png")).getScaledInstance(width/2, height/2,Image.SCALE_SMOOTH);
			BufferedImage image=(BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/atmo/trasparente.png"));//.getScaledInstance(width/2, height/2,Image.SCALE_SMOOTH);
   		    Graphics2D gg = image.createGraphics();			  
			for(i=0;i<B_WIDTH;i+=50) {    		
				py=(int)(Math.random()*B_HEIGHT);
	    		gg.drawImage(imL, i,py, this);	
		 	}
			
			g.drawImage(image, 0,0, this);	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   
    }
    
    private void moveHorse() {

 /*   	int i;
      	for ( i=0;i<numhorse;i++) 
      		h.get(i).move();
   */   	
    	step.put(indeximage);
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
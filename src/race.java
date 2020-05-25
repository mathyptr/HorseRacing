import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class race extends JPanel  implements ActionListener{

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
    private boolean start=false;
    private Timer timer;
    private int x, y;
    private String atmo;
   
 	JPopupMenu popup = new JPopupMenu();
	JMenuBar menuBar = new JMenuBar();
	ImageIcon exitIcon = new ImageIcon("src/resources/exit.png");
	BufferedImage imagebkg=null;
	
	JMenu fileMenu = new JMenu("Opzioni");
	private JPanel panHorse= new JPanel();
	private JButton btnCambia = new JButton("Scegli");
	private JTextField numhorseField = new JTextField("10",2);
	private JComboBox atmoCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();

    public race() {
     	numhorse=10;	  	        
    	
        Menu();
   //     startRace();
    }

    private void startRace() {
  		start=true;
  		 
    	  step=new movement();
       	  end=B_WIDTH;
    	 //       setBackground(Color.BLACK);
       	  Color co= new Color(170,218,24);
         setBackground(co);    	
         setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
         initDefaultSpeed();
         initHorses();
         weat = new weather(B_WIDTH,B_HEIGHT,width,height,atmo);
         
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
    
    private void Menu() {

   // 	  Color co= new Color(170,218,24);
    //   setBackground(co);    	
 //      panHorse.setLayout(null);
       setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
//       numhorseField.setBounds(50,50,10,10);
//       btnCambia.setBounds(100,100,10,10);
       atmoCombo.addItem("sole");
       atmoCombo.addItem("pioggia");
       atmoCombo.addItem("tempesta");
       atmoCombo.addItem("neve");
       panHorse.add(atmoCombo);
       bkgCombo.addItem("sabbia");
       bkgCombo.addItem("terra");
       bkgCombo.addItem("erba");
       bkgCombo.addItem("roccia");
       bkgCombo.addItem("ghiaccio");
       bkgCombo.addItem("asfalto");
       panHorse.add(bkgCombo);
       panHorse.add(numhorseField);
        panHorse.add(btnCambia);
        panHorse.setVisible(true);
        this.add(panHorse);
        btnCambia.addActionListener(this);
    }

  
    
    public void actionPerformed(ActionEvent e)
    { 
    	String pulsante=e.getActionCommand();
    	
    	if(pulsante.contentEquals("Scegli"))
    	{  	
    		loadBKGimg();
    	    numhorse=Integer.valueOf(numhorseField.getText());
    	    atmo=atmoCombo.getSelectedItem().toString();
       	    if(numhorse<2)
        	    JOptionPane.showMessageDialog(this,"Al minimo cavalli sono 2","Attenzione",JOptionPane.WARNING_MESSAGE);  
       	    else if(numhorse>10)
       	    	JOptionPane.showMessageDialog(this,"Al massimo i cavalli sono 10","Attenzione",JOptionPane.WARNING_MESSAGE);	
    	    else
    		{	    	
 //   	    btnCambia.setVisible(false);
 //   	    numhorseField.setVisible(false);
    	    	panHorse.setVisible(false);    	    	
    			startRace();
    	//	System.exit(0);
    		}	
    	}		
    }  
    

    private void loadBKGimg()
   	{  	
    	String bkgstr,filebkg=null;
    	bkgstr=bkgCombo.getSelectedItem().toString();
		
		if(bkgstr.contentEquals("sabbia"))
			filebkg="/img/bkg/sand.jpg";		
		else if(bkgstr.contentEquals("terra"))
			filebkg="/img/bkg/field.jpg";
		else if(bkgstr.contentEquals("erba"))
			filebkg="/img/bkg/green.jpg";
		else if(bkgstr.contentEquals("asfalto"))
			filebkg="/img/bkg/railroad.jpg";
		else if(bkgstr.contentEquals("roccia"))
			filebkg="/img/bkg/rock.jpg";
		else if(bkgstr.contentEquals("ghiaccio"))
			filebkg="/img/bkg/ice.jpg";
	
		try {
			if(filebkg!=null)
				imagebkg=(BufferedImage) ImageIO.read(getClass().getResourceAsStream(filebkg));    					
		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
        if(start) {
        	drawBackground(g);
        	drawCorsie(g);
        	drawHorse(g);
        	drawWater(g);
        	drawNumber(g);
        }
    }
    
    private void drawNumber(Graphics g) {
		try {
			
	        g.drawImage((BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/num/1.png")), 560, 240, this);

		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//        g.drawImage(imagebkg, 100, 100, this);

	}
    private void drawBackground(Graphics g) {
        g.drawImage(imagebkg, 0, 0, this);
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

        float[] dash1 = { 4f, 0f, 4f };

        BasicStroke bs1 = new BasicStroke((float) 2.5, 
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
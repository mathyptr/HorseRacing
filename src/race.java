import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import java.util.Locale;
import java.util.ResourceBundle;
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
import javax.swing.JLabel;
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
	private java.util.Vector <Integer> winner =new java.util.Vector(1,1);
	private java.util.Vector <BufferedImage> imgNum =new java.util.Vector(1,1);
	
	private movement step;	
	private weather weat;
    private int numhorse=0;
    private int indeximage=0;
    private boolean start=false;
    private Timer timer;
    private int x, y;
    private String atmo;
    private Locale currentLocale;
    private ResourceBundle messages;
    MessagesBundle msgB= new MessagesBundle();
 	JPopupMenu popup = new JPopupMenu();
	JMenuBar menuBar = new JMenuBar();
	ImageIcon exitIcon = new ImageIcon("src/resources/exit.png");
	BufferedImage imagebkg=null;
	BufferedImage imagefinish=null;
	JMenu fileMenu = new JMenu("Opzioni");
	private JPanel panHorse= new JPanel();
	private JPanel panHorseImg= new JPanel();
	private JTextField numhorseField = new JTextField("10",2);
	private JComboBox atmoCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();
	private JComboBox 	languageCombo = new JComboBox();
	private JButton btnStart =null;
	private JLabel numehorseLabel =null;
	private JLabel atmoLabel = null;
	private JLabel percorsoLabel = null;
	private JLabel languageLabel = null;

    public race() {
     	numhorse=10;	  	        
        
        String language,country;
        language="it";
        country="IT";
        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        msgB.SetLanguage("en", "US");
        loadNumberimg();
        Menu();
        step=new movement();
   	  	end=B_WIDTH-width;
  
   //     startRace();
    }

    private void startRace() {
  		start=true;
        weat = new weather(B_WIDTH,B_HEIGHT,width,height,atmo);
  		 
     	 //       setBackground(Color.BLACK);
       	  Color co= new Color(170,218,24);
         setBackground(co);    	
         setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
//         initDefaultSpeed();
         initHorses();
  //       weat = new weather(B_WIDTH,B_HEIGHT,width,height,atmo);
         
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
    	btnStart = new JButton("Partenza");
    	numehorseLabel = new JLabel("Numero di cavalli",JLabel.RIGHT);
    	atmoLabel = new JLabel("Condizioni atmosferiche",JLabel.RIGHT);
    	percorsoLabel = new JLabel("Percorso",JLabel.RIGHT);
    	languageLabel = new JLabel("Linguaggio",JLabel.RIGHT);


   // 	  Color co= new Color(170,218,24);
    //   setBackground(co);    	
 //      panHorse.setLayout(null);
       setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
//       numhorseField.setBounds(50,50,10,10);
//       btnCambia.setBounds(100,100,10,10);
 //      setLayout(new GridLayout(2,2,10,10));
       setLayout(new BorderLayout());
    
       //       panHorse.setPreferredSize(new Dimension(B_WIDTH/2, B_HEIGHT/4));
       panHorse.setLayout(new GridLayout(3,4,10,10));
//       panHorse.setLayout(new GridLayout(5,1));

       atmoCombo.addItem("sole");
       atmoCombo.addItem("pioggia");
       atmoCombo.addItem("tempesta");
       atmoCombo.addItem("neve");
       panHorse.add(atmoLabel);       
       panHorse.add(atmoCombo);
       

       bkgCombo.addItem("sabbia");
       bkgCombo.addItem("terra");
       bkgCombo.addItem("erba");
       bkgCombo.addItem("roccia");
       bkgCombo.addItem("ghiaccio");
       bkgCombo.addItem("asfalto");
       panHorse.add(percorsoLabel);       
       panHorse.add(bkgCombo);
       
       
       languageCombo.addItem("IT");
       languageCombo.addItem("EN");
       panHorse.add(languageLabel);
       panHorse.add(languageCombo);
       
       panHorse.add(numehorseLabel);
       panHorse.add(numhorseField);
       
       panHorse.add(new JLabel(""));                
       panHorse.add(btnStart);     
     
        panHorse.setVisible(true);
        this.add(panHorse,"North");
        
        panHorseImg.setLayout(new GridLayout(5,2,10,10));
        Graphics g=this.getGraphics();
       	
        Image hh;
    	java.util.Vector <horse> horseshow =new java.util.Vector(1,1);
       	for (int i=0;i<numhorse;i++) {
      		horseshow.addElement(new horse(i+1,end,step));
        		hh=horseshow.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);      
        	    panHorseImg.add(new JLabel(new ImageIcon(hh)));
        	    		            
      	}    
       	panHorseImg.setVisible(true);
        this.add(panHorseImg,"Center");
//        this.add(btnStart,"South");     
             
    	/*
    	for (int i=0;i<numhorse/2;i++) {
      		horseshow.addElement(new horse(i+1,end,step));
      		horseshow.get(i).setPosX(INITIAL_X);
      		horseshow.get(i).setPosY(INITIAL_Y+(i+B_HEIGHT/2)*corsia);
       		hh=horseshow.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);      
            g.drawImage(hh, horseshow.get(i).getPosX(), horseshow.get(i).getPosY(), this);
            
      	}    
     	for (int i=numhorse/2;i<numhorse;i++) {
      		horseshow.addElement(new horse(i+1,end,step));
      		horseshow.get(i).setPosX(INITIAL_X+B_WIDTH/2);
      		horseshow.get(i).setPosY(INITIAL_Y+(i+B_HEIGHT/2)*corsia);
      		hh=horseshow.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);           		 
            g.drawImage(hh, horseshow.get(i).getPosX(), horseshow.get(i).getPosY(), this);
                 	}     
    
    */
        btnStart.addActionListener(this);
    }

  
    
    public void actionPerformed(ActionEvent e)
    { 
    	String pulsante=e.getActionCommand();
    	
    	if(pulsante.contentEquals("Partenza"))
    	{  	
    		loadBKGimg();
    	    atmo=atmoCombo.getSelectedItem().toString();
    	    numhorse=Integer.valueOf(numhorseField.getText());
    	    if(languageCombo.getSelectedItem().toString().equals("IT"))
    	    	 msgB.SetLanguage("it", "IT");   	     
    	    else
    	   	     msgB.SetLanguage("en", "US");
    	     
       	    if(numhorse<2)
        	    JOptionPane.showMessageDialog(this,"Al minimo cavalli sono 2","Attenzione",JOptionPane.WARNING_MESSAGE);  
       	    else if(numhorse>10)
       	    	JOptionPane.showMessageDialog(this,"Al massimo i cavalli sono 10","Attenzione",JOptionPane.WARNING_MESSAGE);	
    	    else
    		{	    	
 //   	    btnCambia.setVisible(false);
 //   	    numhorseField.setVisible(false);
    	    	panHorse.setVisible(false);    	    	
    	    	panHorseImg.setVisible(false); 
    			startRace();
    	//	System.exit(0);
    		}	
    	}		
    }  
    
    private void loadNumberimg()
   	{   
    	try {
		
        	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/1.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/2.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/3.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/4.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/5.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/6.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/7.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/8.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/9.png")));
           	imgNum.addElement( ImageIO.read(getClass().getResourceAsStream("/img/num/10.png")));
 
		} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
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
			imagefinish=(BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/bkg/finish.png"));
			if(filebkg!=null)
				imagebkg=(BufferedImage) ImageIO.read(getClass().getResourceAsStream(filebkg));    					
		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
}
         
    private void initHorses(){
    	for (int i=0;i<numhorse;i++) {
    		h.addElement(new horse(i+1,end,step));
    		h.get(i).setPosX(INITIAL_X);
    		h.get(i).setPosY(INITIAL_Y+i*corsia);
//    		h.get(i).setspeed(speedhorse.get((int)(Math.random()*10)));
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
			
			int i,horsenum;
	      	for ( i=0;i<winner.size();i++) { 
	      		horsenum=winner.get(i);
	    	    g.drawImage(imgNum.get(i), end-50, h.get(horsenum).getPosY(), this);
	      	}

			
//	        g.drawImage((BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/num/1.png")), 560, 240, this);


//        g.drawImage(imagebkg, 100, 100, this);

	}
    private void drawBackground(Graphics g) {
        g.drawImage(imagebkg, 0, 0, this);
        for (int i=0;i<numhorse;i++) {
    		g.drawImage(imagefinish,end, i*corsia,this);   	 
    	}   
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
    
     
    private void checkWin() {

    	    	int i;
    	      	for ( i=0;i<numhorse;i++) 
    	      	{
    	      		if(h.get(i).getPosX()>=end && h.get(i).GetFinalPosition()==0)
    	      		{
    	      			h.get(i).SetFinalPosition(winner.size()+1);
    	      			winner.addElement(i);
    	      		}
    	      	}    
    	      	if(winner.size()==numhorse)
    	      	{
    	      		start=false;
        	    	panHorse.setVisible(true);    	    	
   //     	    	panHorseImg.setVisible(false);
    	      	} 
    	    }
    
    private void moveHorse() {

 /*   	int i;
      	for ( i=0;i<numhorse;i++) 
      		h.get(i).move();
   */   
    	checkWin();
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
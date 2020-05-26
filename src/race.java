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
import java.awt.LayoutManager;
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
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
/**
 * descrizione
 * @author Patrissi Mathilde
 */
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
    private String atmo="atmo_snow";
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
	private JPanel panHorseOpt= new JPanel();
	private JPanel panHorseImg= new JPanel();
	private JPanel panHorseImgRight= new JPanel();
	private JPanel panHorseImgLeft= new JPanel();
	private JTextField numhorseField = new JTextField("10",2);
	private JComboBox atmoCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();
	private JComboBox 	languageCombo = new JComboBox();
	private Box boxUpper;
	private Box boxCenter;
	private Box boxBottom;

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
//        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        msgB.SetLanguage("it", "IT");
        loadNumberimg();
        Menu();
        step=new movement();
   	  	end=B_WIDTH-width;
  
   //     startRace();
    }

    private void startRace() {
  		start=true;
        weat = new weather(B_WIDTH,B_HEIGHT,width,height,atmo);
  		 
        if(winner.size()!=0)
        	winner.clear();
         setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

         initHorses();
         
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
    	btnStart = new JButton(MessagesBundle.GetResourceValue("btn_start"));
    	Border edge=BorderFactory.createRaisedBevelBorder();
    	Dimension size = new Dimension(80,20);
    	btnStart.setBorder(edge);
    	btnStart.setPreferredSize(size);
    	
    /*	numehorseLabel = new JLabel("Numero di cavalli",JLabel.RIGHT);
    	atmoLabel = new JLabel("Condizioni atmosferiche",JLabel.RIGHT);
    	percorsoLabel = new JLabel("Percorso",JLabel.RIGHT);
    	languageLabel = new JLabel("Linguaggio",JLabel.RIGHT);
*/
    	numehorseLabel = new JLabel("Numero di cavalli");
    	atmoLabel = new JLabel("Condizioni atmosferiche");
    	percorsoLabel = new JLabel("Percorso");
    	languageLabel = new JLabel("Linguaggio");

    	
    /*	LayoutManager panHorseLayout = new BoxLayout(panHorse, BoxLayout.X_AXIS);
    	panHorse.setLayout(panHorseLayout);
    	LayoutManager panHorseOptLayout = new BoxLayout(panHorseOpt, BoxLayout.X_AXIS);       
    	panHorseOpt.setLayout(panHorseOptLayout);
    	LayoutManager panHorseImgLayout = new BoxLayout(panHorseImg, BoxLayout.X_AXIS);
    	panHorseImg.setLayout(panHorseImgLayout);
    	*/
    	 

       setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
    

  //     panHorse.setLayout(new GridLayout(3,4,10,10));

   	   btnStart.setText(msgB.GetResourceValue("btn_start"));
   	   numehorseLabel.setText(msgB.GetResourceValue("label_numehorse"));
   	   atmoLabel.setText(msgB.GetResourceValue("label_atmo"));
       percorsoLabel.setText(msgB.GetResourceValue("label_place"));
       languageLabel.setText(msgB.GetResourceValue("label_language"));
       
       atmoCombo.addItem(msgB.GetResourceValue("atmo_sun"));
       atmoCombo.addItem(msgB.GetResourceValue("atmo_rain"));
       atmoCombo.addItem(msgB.GetResourceValue("atmo_stormwind"));
       atmoCombo.addItem(msgB.GetResourceValue("atmo_snow"));
                   
       bkgCombo.addItem(msgB.GetResourceValue("bkg_sand"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_field"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_green"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_rock")); 
       bkgCombo.addItem(msgB.GetResourceValue("bkg_ice"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_railroad")); 

       languageCombo.addItem("IT");
       languageCombo.addItem("EN");
       
       /*
       panHorse.add(atmoLabel);       
       panHorse.add(atmoCombo);
       panHorse.add(percorsoLabel);       
       panHorse.add(bkgCombo);             
       panHorseOpt.add(languageLabel);
       panHorseOpt.add(languageCombo);       
       panHorseOpt.add(numehorseLabel);
       panHorseOpt.add(numhorseField);       
       panHorseOpt.add(new JLabel(""));                
       panHorseOpt.add(btnStart);     
     
        panHorse.setVisible(true);
        panHorseOpt.setVisible(true);
        this.add(panHorse);
        this.add(panHorseOpt);*/

       boxUpper = Box.createHorizontalBox();
   	   Box boxUpper1 = Box.createVerticalBox();
   	   Box boxUpper2 = Box.createVerticalBox();
  
       
       boxUpper1.add(atmoLabel);       
       boxUpper1.add(atmoCombo);
       //boxUpper1.add(Box.createVerticalStrut(10));
       boxUpper1.add(percorsoLabel);       
       boxUpper1.add(bkgCombo);          
//       boxUpper1.add(Box.createHorizontalStrut(20));  
       
       boxUpper2.add(languageLabel);
       boxUpper2.add(languageCombo);
       //boxUpper2.add(Box.createVerticalStrut(10));
       boxUpper2.add(numehorseLabel);
       boxUpper2.add(numhorseField);  
//       boxUpper2.add(Box.createHorizontalStrut(20));
       
 /*      boxUpper.add(Box.createHorizontalStrut(100));
       boxUpper.add(boxUpper1);
       boxUpper.add(Box.createHorizontalStrut(100));
       boxUpper.add(boxUpper2);
       boxUpper.add(Box.createHorizontalStrut(150));
   */    
      	panHorse.setBorder(new TitledBorder(new EtchedBorder(),""));
      	panHorse.add(boxUpper1,BorderLayout.CENTER);
      	panHorseOpt.setBorder(new TitledBorder(new EtchedBorder(),""));
      	panHorseOpt.add(boxUpper2,BorderLayout.CENTER);

      	boxUpper.add(panHorse);
//      	boxUpper.add(Box.createHorizontalStrut(5));        		
    	boxUpper.add(panHorseOpt); 
       
       
       
       
       Box boxVerticalLeft = Box.createVerticalBox();
             
//        panHorseImg.setLayout(new GridLayout(5,2,10,10));
        Graphics g=this.getGraphics();

        Image hh;
    	java.util.Vector <horse> horseshow =new java.util.Vector(1,1);
       	for (int i=0;i<numhorse/2;i++) {
      		horseshow.addElement(new horse(i+1,end,step));
        		hh=horseshow.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);      
                boxVerticalLeft.add(Box.createHorizontalStrut(50));
        		boxVerticalLeft.add(new JLabel(new ImageIcon(hh)));
        		boxVerticalLeft.add(new JLabel(horseshow.get(i).getHorseName()));
      	}    
        Box boxVerticalRight = Box.createVerticalBox();
       	for (int i=numhorse/2;i<numhorse;i++) {
      		horseshow.addElement(new horse(i+1,end,step));
        		hh=horseshow.get(i).getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH);
        		boxVerticalRight.add(Box.createHorizontalStrut(50));        		
        		boxVerticalRight.add(new JLabel(new ImageIcon(hh)));
        		boxVerticalRight.add(new JLabel(horseshow.get(i).getHorseName()));        		
      	} 
       	panHorseImgRight.add(boxVerticalRight);
       	panHorseImgLeft.add(boxVerticalLeft);
 //      	panHorseImg.setVisible(true);
 //       this.add(panHorseImg,"Center");
//        this.add(boxUpper);    
   
     	
     	boxCenter = Box.createHorizontalBox();
     	boxCenter.add(panHorseImgRight);
     	boxCenter.add(panHorseImgLeft);
     	
     	boxBottom = Box.createHorizontalBox();
        boxBottom.add(btnStart);     
        BoxLayout box=new BoxLayout(this,BoxLayout.Y_AXIS);
        this.setLayout(box);
        this.add(boxUpper);
        this.add(Box.createVerticalStrut(100));
        this.add(boxCenter);
        this.add(boxBottom);
//        this.add(boxUpper,BorderLayout.NORTH);
    	
 //       this.add(panHorse);
 //      	this.add(panHorseOpt);
//        this.add(boxVerticalLeft,BorderLayout.WEST);
 //       this.add(boxVerticalRight,BorderLayout.EAST);
    
        languageCombo.addActionListener(this);
        btnStart.addActionListener(this);
    }

    private void MenuOLD() {
    	btnStart = new JButton("Partenza");
    	numehorseLabel = new JLabel("Numero di cavalli",JLabel.RIGHT);
    	atmoLabel = new JLabel("Condizioni atmosferiche",JLabel.RIGHT);
    	percorsoLabel = new JLabel("Percorso",JLabel.RIGHT);
    	languageLabel = new JLabel("Linguaggio",JLabel.RIGHT);

    	btnStart.setText(msgB.GetResourceValue("btn_start"));
    	numehorseLabel.setText(msgB.GetResourceValue("label_numehorse"));
    	atmoLabel.setText(msgB.GetResourceValue("label_atmo"));
    	percorsoLabel.setText(msgB.GetResourceValue("label_place"));
    	languageLabel.setText(msgB.GetResourceValue("label_language"));
 

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
/*
       atmoCombo.addItem("sole");
       atmoCombo.addItem("pioggia");
       atmoCombo.addItem("tempesta");
       atmoCombo.addItem("neve");
  */  
                     
      
       atmoCombo.addItem(msgB.GetResourceValue("atmo_sun"));
       atmoCombo.addItem(msgB.GetResourceValue("atmo_rain"));
       atmoCombo.addItem(msgB.GetResourceValue("atmo_stormwind"));
       atmoCombo.addItem(msgB.GetResourceValue("atmo_snow"));
 
       
       
       panHorse.add(atmoLabel);       
       panHorse.add(atmoCombo);
       

/*       bkgCombo.addItem("sabbia");
       bkgCombo.addItem("terra");
       bkgCombo.addItem("erba");
       bkgCombo.addItem("roccia");
       bkgCombo.addItem("ghiaccio");
       bkgCombo.addItem("asfalto");
   */    
       bkgCombo.addItem(msgB.GetResourceValue("bkg_sand"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_field"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_green"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_rock")); 
       bkgCombo.addItem(msgB.GetResourceValue("bkg_ice"));
       bkgCombo.addItem(msgB.GetResourceValue("bkg_railroad")); 
       
       
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
        languageCombo.addActionListener(this);
        btnStart.addActionListener(this);
    }
  
    
    public void actionPerformed(ActionEvent e)
    { 
    	String pulsante=e.getActionCommand();
  
       	if(pulsante.contentEquals("comboBoxChanged"))
       	{
       		chgLanguage();
        	buildComponent();
       	}
       	else if(pulsante.contentEquals(MessagesBundle.GetResourceValue("btn_start")))
    	{  	
    		loadBKGimg();
    	    numhorse=Integer.valueOf(numhorseField.getText());
    	   
    	    SetAtmo();
  	    
       	    if(numhorse<2)
        	    JOptionPane.showMessageDialog(this,MessagesBundle.GetResourceValue("warn_nmin_horse"),"warn_msg",JOptionPane.WARNING_MESSAGE);  
       	    else if(numhorse>10)
       	    	JOptionPane.showMessageDialog(this,MessagesBundle.GetResourceValue("warn_nmax_horse"),"warn_msg",JOptionPane.WARNING_MESSAGE);	
    	    else
    		{	    	

//    	    	panHorse.setVisible(false);    	    	
//    	    	panHorseImg.setVisible(false);
    	    	
    	    	panelMenuSetStatus(false);    	    	
    	    	
    			startRace();
    	//	System.exit(0);
    		}	
    	}		
    }  
    
    private void panelMenuSetStatus(boolean status)
    {
    	boxUpper.setVisible(status);
    	boxCenter.setVisible(status);
    	boxBottom.setVisible(status);
	}
    
    private void chgLanguage()
    {
    	if(languageCombo.getSelectedItem().toString().equals("IT"))
    		msgB.SetLanguage("it", "IT");   	     
    	else
    		msgB.SetLanguage("en", "US");
    } 
    private void buildComponent()
    {
    	int i;
    	 int itemCount = bkgCombo.getItemCount();
        for( i=0;i<itemCount;i++){
        	bkgCombo.removeItemAt(0);
         }
    
  //      remove(panHorse);        
        
        bkgCombo.addItem(msgB.GetResourceValue("bkg_sand"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_field"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_green"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_rock")); 
        bkgCombo.addItem(msgB.GetResourceValue("bkg_ice"));
        bkgCombo.addItem(msgB.GetResourceValue("bkg_railroad")); 
          
                
     	  itemCount = atmoCombo.getItemCount();
         for( i=0;i<itemCount;i++){
        	 atmoCombo.removeItemAt(0);
          }   
        
        atmoCombo.addItem(msgB.GetResourceValue("atmo_sun"));
        atmoCombo.addItem(msgB.GetResourceValue("atmo_rain"));
        atmoCombo.addItem(msgB.GetResourceValue("atmo_stormwind"));
        atmoCombo.addItem(msgB.GetResourceValue("atmo_snow"));
//        panHorse.add(atmoLabel);       
//       panHorse.add(atmoCombo);
      
  
//        panHorse.add(percorsoLabel);       
//        panHorse.add(bkgCombo)
    	btnStart.setText(msgB.GetResourceValue("btn_start"));
    	numehorseLabel.setText(msgB.GetResourceValue("label_numehorse"));
    	atmoLabel.setText(msgB.GetResourceValue("label_atmo"));
    	percorsoLabel.setText(msgB.GetResourceValue("label_place"));
    	languageLabel.setText(msgB.GetResourceValue("label_language"));
    	
   /*   	btnStart = new JButton("Partenza");
    	numehorseLabel = new JLabel("Numero di cavalli",JLabel.RIGHT);
    	atmoLabel = new JLabel("Condizioni atmosferiche",JLabel.RIGHT);
    	percorsoLabel = new JLabel("Percorso",JLabel.RIGHT);
    	languageLabel = new JLabel("Linguaggio",JLabel.RIGHT);
*/
    	
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
    
    private void SetAtmo()
    {
    	atmo=MessagesBundle.GetResourceKey(atmoCombo.getSelectedItem().toString());
	}
    private void loadBKGimg()
   	{  	
    	String bkgstr,filebkg=null;
//    	bkgstr=bkgCombo.getSelectedItem().toString();
    	bkgstr=MessagesBundle.GetResourceKey(bkgCombo.getSelectedItem().toString());
  	  
		if(bkgstr.contentEquals("bkg_sand"))
			filebkg="/img/bkg/sand.jpg";		
		else if(bkgstr.contentEquals("bkg_field"))
			filebkg="/img/bkg/field.jpg";
		else if(bkgstr.contentEquals("bkg_green"))
			filebkg="/img/bkg/green.jpg";
		else if(bkgstr.contentEquals("bkg_railroad"))
			filebkg="/img/bkg/railroad.jpg";
		else if(bkgstr.contentEquals("bkg_rock"))
			filebkg="/img/bkg/rock.jpg";
		else if(bkgstr.contentEquals("bkg_ice"))
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
    	if(h.size()!=0)
    		h.clear();
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
	    	    g.drawImage(imgNum.get(i), end-50, h.get(horsenum).getPosY()-height/3, this);
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
    	      		panelMenuSetStatus(true);
//        	    	panHorse.setVisible(true);    	    	
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
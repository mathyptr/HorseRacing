
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel  {

    private final int B_WIDTH = 650;
    private final int B_HEIGHT = 650;
    private final int INITIAL_X = 650;
    private final int INITIAL_Y = 150;    
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 100;
    
    private int indeximage=0;
    private Image horse;
    private Image horse1;
    private Image horse2;
    private Image horse3;
    private Image horse4;
    private Timer timer;
    private int x, y;
    
    
    public Board() {
    
        initBoard();        
    }
    
    private void loadImage() {
        
        ImageIcon ii = new ImageIcon("src/resources/horse.png");
        horse = ii.getImage();  
        ii = new ImageIcon("src/resources/horse1.png");
        horse1 = ii.getImage(); 
        ii = new ImageIcon("src/resources/horse2.png");
        horse2 = ii.getImage(); 
        ii = new ImageIcon("src/resources/horse3.png");
        horse3 = ii.getImage(); 
        ii = new ImageIcon("src/resources/horse4.png");
        horse4 = ii.getImage(); 
    }
    
    private void initBoard() {
        
 //       setBackground(Color.BLACK);
        setBackground(Color.WHITE);    	
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 
                INITIAL_DELAY, PERIOD_INTERVAL);        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawHorse(g);
    }
    
    private void drawHorse(Graphics g) {
    	Image h;
    	int i=indeximage%5;
        if(i==0)
        	h=horse;
        else if(i==1)
        	h=horse1;
        else if(i==2)
        	h=horse2;
        else if(i==3)
        	h=horse3;
        else 
        	h=horse4;
        indeximage++;
        g.drawImage(h, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            
            x -= 1;
           // y -= 1;

   //         if (y > B_HEIGHT) {
            if (x <0 ) {
                            
                y = INITIAL_Y;
                x = INITIAL_X;
            }
            
            repaint();
        }
    }
}
import java.applet.AudioClip;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class HorseRacing extends JFrame {

    public HorseRacing(int n) {
   	
    	
        add(new race(n));
                        
        setResizable(false);
        pack();
        
        setTitle("Horse");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    public static void main(String[] args) {
        
    	int  n=10;
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader tastiera = new BufferedReader(input);
		System.out.println("Quanti cavalli sono presenti alla gara?");
		try {
			n=(Integer.valueOf(tastiera.readLine()).intValue());
		}
	    catch (Exception e){
		   System.out.println("errore input 1");
	    }
		final Integer numehorse = new Integer(n);
		
		
        EventQueue.invokeLater(() -> {
            JFrame ex = new HorseRacing(numehorse);
            ex.setVisible(true);
        });
    }
}
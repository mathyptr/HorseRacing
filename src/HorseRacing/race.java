package HorseRacing;

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
import javax.swing.JCheckBox;
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
 * Classe che gestisce la gara di cavalli visualizzando background, corsie,
 * cavalli, musica e condizioni atmosferiche
 * 
 * @author Patrissi Mathilde
 */

public class race extends JPanel implements ActionListener {

	private final int B_WIDTH = 600;
	private final int B_HEIGHT = 600;
	private final int INITIAL_X = 0;
	private final int INITIAL_Y = 10;
	private final int INITIAL_DELAY = 100;
	private final int PERIOD_INTERVAL = 50;
	private int end;
	private int priceposition;
	private int width = 50;
	private int height = 50;
	private int corsia = 60;
	private int nmaxhorse = 10;
	private java.util.Vector<horse> h = new java.util.Vector(1, 1);
	private java.util.Vector<horse> horsewinner = new java.util.Vector(1, 1);

	private java.util.Vector<Integer> speedhorse = new java.util.Vector(1, 1);
	private java.util.Vector<Integer> winner = new java.util.Vector(1, 1);
	private java.util.Vector<BufferedImage> imgNum = new java.util.Vector(1, 1);
	private java.util.Vector<BufferedImage> imgCup = new java.util.Vector(1, 1);

	private movement step;
	private weather weat;
	private int numhorse = 0;
	private int numhorseWinner = 3;
	private int indeximage = 0;
	private boolean start = false;
	private boolean firstrace = true;
	private boolean pricegiving = false;
	private boolean soundON = true;
	private Timer timer;
	private int x, y;
	private String atmo = "atmo_snow";
	MessagesBundle msgB = new MessagesBundle();

	Clip clipMenu;
	Clip clipRace;
	Clip clipWin;
	BufferedImage imagebkg = null;
	BufferedImage imagefinish = null;

	private JPanel panHorse = new JPanel();
	private JPanel panHorseOpt = new JPanel();
	private JPanel panOpt = new JPanel();
	private JPanel panHorseImg = new JPanel();
	private JPanel panHorseImgRight = new JPanel();
	private JPanel panHorseImgLeft = new JPanel();
	private JPanel panButtonStart = new JPanel();

	private JTextField numhorseField = new JTextField("10", 2);
	private JComboBox atmoCombo = new JComboBox();
	private JComboBox bkgCombo = new JComboBox();
	private JComboBox languageCombo = new JComboBox();
	private JCheckBox soundCheckBox = new JCheckBox();

	private Box boxUpper;
	private Box boxCenter;
	private Box boxBottom;

	private JLabel numehorseLabel = new JLabel();
	private JLabel atmoLabel = new JLabel();
	private JLabel percorsoLabel = new JLabel();
	private JLabel languageLabel = new JLabel();
	private JLabel soundLabel = new JLabel();

	private JButton btnStart = null;

	/**
	 * Costruttore della classe race
	 */
	public race() {
		numhorse = 10;

		msgB.SetLanguage("it", "IT"); //setto come lingua di default italiano
		loadNumberimg(); //carico le immagini dei numeri
		loadCup();  //carico le immagini delle coppe
		step = new movement();
		priceposition = B_WIDTH / 2;
		try { //apertura file audio relativi al menu', alla corsa e alla premiazione
			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/music/Menu.wav")));
			clipMenu = AudioSystem.getClip();
			clipMenu.open(audioIn);

			audioIn = AudioSystem.getAudioInputStream(
					new BufferedInputStream(getClass().getResourceAsStream("/music/galoppata.wav")));
			clipRace = AudioSystem.getClip();
			clipRace.open(audioIn);

			audioIn = AudioSystem.getAudioInputStream(
					new BufferedInputStream(getClass().getResourceAsStream("/music/applausi.wav")));
			clipWin = AudioSystem.getClip();
			clipWin.open(audioIn);
		} catch (Exception e) {
			System.out.println("errore on sound: " + e.toString());
		}
		Menu();

	}

	/**
	 * Metodo che permette l'inizio della gara inizializzando i thread che
	 * gestiscono i cavalli, il meteo e la musica
	 */
	private void startRace() {
		end = B_WIDTH - width; //end è la coordinata dopo la quale inizia il tile del traguardo

		weat = new weather(B_WIDTH, B_HEIGHT, width, height, atmo); //inizializzo la classe che gestisce il layer per le condizioni atmosferiche

		if (winner.size() != 0)
			winner.clear();

		initHorses();
		ClipSound(clipMenu, false);
		ClipSound(clipWin, false);
		ClipSound(clipRace, true);

		x = INITIAL_X;
		y = INITIAL_Y;
		start = true;
		timer = new Timer(); //imposto il timer che darà la cadenza temporale per il rilascio di un movimento
		timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
	}

	/**
	 * Metodo per l'implementazione dell'interfaccia menu' iniziale
	 */

	private void Menu() { //menu principale per gestire condizioni atmosferiche, percorso, numero di cavalli, lingua e suono

		btnStart = new JButton();
		Border edge = BorderFactory.createRaisedBevelBorder();
		Dimension size = new Dimension(100, 60);
		soundCheckBox.setSelected(true);

		size = new Dimension(80, 20);
		atmoLabel.setPreferredSize(size); // 3005
		atmoCombo.setPreferredSize(size); // 3005
		percorsoLabel.setPreferredSize(size); // 3005
		bkgCombo.setPreferredSize(size); // 3005
		languageLabel.setPreferredSize(size); // 3005
		languageCombo.setPreferredSize(size); // 3005
		numehorseLabel.setPreferredSize(size); // 3005;
		numhorseField.setPreferredSize(size); // 3005
		soundCheckBox.setPreferredSize(size); // 3005

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));


		boxUpper = Box.createHorizontalBox();
		Box boxUpper1 = Box.createVerticalBox();
		Box boxUpper2 = Box.createVerticalBox();
		Box boxUpper3 = Box.createVerticalBox();

		boxUpper1.add(atmoLabel);
		boxUpper1.add(atmoCombo);

		boxUpper1.add(percorsoLabel);
		boxUpper1.add(bkgCombo);

		boxUpper2.add(languageLabel);
		boxUpper2.add(languageCombo);
		
		boxUpper2.add(numehorseLabel);
		boxUpper2.add(numhorseField);

		boxUpper3.add(soundCheckBox);
		
		panHorse.setBorder(new TitledBorder(new EtchedBorder(), ""));
		panHorse.add(boxUpper1, BorderLayout.CENTER);
		panHorseOpt.setBorder(new TitledBorder(new EtchedBorder(), ""));
		panHorseOpt.add(boxUpper2, BorderLayout.CENTER);
		panOpt.setBorder(new TitledBorder(new EtchedBorder(), ""));
		panOpt.add(boxUpper3, BorderLayout.CENTER);

		boxUpper.add(panHorse);	
		boxUpper.add(panHorseOpt);
		boxUpper.add(panOpt);

		Box boxVerticalLeft = Box.createVerticalBox();


		Graphics g = this.getGraphics();

		Image hh;
		java.util.Vector<horse> horseshow = new java.util.Vector(1, 1);
		for (int i = 0; i < numhorse / 2; i++) {
			horseshow.addElement(new horse(i + 1, end, step));
			hh = horseshow.get(i).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			boxVerticalLeft.add(Box.createHorizontalStrut(50));
			boxVerticalLeft.add(new JLabel(new ImageIcon(hh)));
			boxVerticalLeft.add(new JLabel(horseshow.get(i).getHorseName()));
		}
		Box boxVerticalRight = Box.createVerticalBox();
		for (int i = numhorse / 2; i < numhorse; i++) {
			horseshow.addElement(new horse(i + 1, end, step));
			hh = horseshow.get(i).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			boxVerticalRight.add(Box.createHorizontalStrut(50));
			boxVerticalRight.add(new JLabel(new ImageIcon(hh)));
			boxVerticalRight.add(new JLabel(horseshow.get(i).getHorseName()));
		}
		panHorseImgRight.add(boxVerticalRight);
		panHorseImgLeft.add(boxVerticalLeft);
	

		boxCenter = Box.createHorizontalBox();
		boxCenter.add(panHorseImgRight);
		boxCenter.add(panHorseImgLeft);

		
		panButtonStart.add(btnStart);

		boxBottom = Box.createHorizontalBox();
		
		boxBottom.add(Box.createHorizontalStrut(B_WIDTH / 2 - 50));
		boxBottom.add(panButtonStart);
		boxBottom.add(Box.createHorizontalStrut(B_WIDTH / 2 - 50)); 
		
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);

		boxBottom.setOpaque(false);

		this.add(boxUpper);
		this.add(boxBottom);
		this.add(Box.createVerticalStrut(100));
		this.add(boxCenter);
		
		this.add(Box.createVerticalStrut(500));

		this.setOpaque(false);
		

		buildComponent();

		languageCombo.addActionListener(this);
		btnStart.addActionListener(this);
		soundCheckBox.addActionListener(this);

		ClipSound(clipRace, false);
		ClipSound(clipWin, false);
		ClipSound(clipMenu, true);

	}

	/**
	 * Metodo per attivare o disattivare il suono
	 * 
	 * @param clipsound Clip
	 * @param state     boolean
	 */

	private void ClipSound(Clip clipsound, boolean state) { 
		if (soundON)
			if (state) {
				try {
					clipsound.loop(100);
				} catch (Exception e) {
					System.out.println("errore on sound: " + e.toString());
				}
			} else
				clipsound.stop();

	}

	/**
	 * Metodo per la gestione dei possibili eventi di interazione con l'utente
	 * attraverso il menu'
	 * 
	 * @param e ActionEvent
	 */

	public void actionPerformed(ActionEvent e) {
		String pulsante = e.getActionCommand();

		if (pulsante.contentEquals("comboBoxChanged")) { //quando viene selezionata la lingua vengono richiamati i metodi seguenti
			chgLanguage();
			buildComponent();
		} else if (MessagesBundle.GetResourceKey(pulsante).contentEquals("label_sound")) { //se viene spuntata la casella del suono viene attivato l'audio
			boolean s = soundCheckBox.isSelected();
			if (s) {
				soundON = s;
				ClipSound(clipMenu, true);
			} else {
				ClipSound(clipMenu, false);
				soundON = s;
			}

		} else if (pulsante.contentEquals(MessagesBundle.GetResourceValue("btn_start"))) { //se viene premuto il tasto in questione vengono lette le opzioni settate dall'utente ed avviata la corsa
			loadBKGimg();
			try {
				numhorse = Integer.parseInt(numhorseField.getText());

			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(this, MessagesBundle.GetResourceValue("error_on_num_horse"),
						MessagesBundle.GetResourceValue("err_msg"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			numhorse = Integer.valueOf(numhorseField.getText());
			SetAtmo();
			if (numhorse < 2)
				JOptionPane.showMessageDialog(this, MessagesBundle.GetResourceValue("warn_nmin_horse"),
						MessagesBundle.GetResourceValue("warn_msg"), JOptionPane.WARNING_MESSAGE);
			else if (numhorse > nmaxhorse)
				JOptionPane.showMessageDialog(this, MessagesBundle.GetResourceValue("warn_nmax_horse"),
						MessagesBundle.GetResourceValue("warn_msg"), JOptionPane.WARNING_MESSAGE);
			else {
				panelMenuSetStatus(false);
				startRace();
				ClipSound(clipMenu, false);
				ClipSound(clipWin, false);
				ClipSound(clipRace, true);

			}
		} else if (pulsante.contentEquals(MessagesBundle.GetResourceValue("btn_restart"))) {
			pricegiving = false;
			firstrace = true;
			panelMenuSetStatus(true);
			ClipSound(clipWin, false);
			ClipSound(clipRace, false);
			ClipSound(clipMenu, true);
		}

	}

	/**
	 * Metodo che visualizza o nasconde il menu in base allo stato attuale del
	 * gioco. (Esempio: durante la gara il menu' iniziale viene nascosto)
	 */
	private void panelMenuSetStatus(boolean status) {
		btnStart.setText(msgB.GetResourceValue("btn_start"));
		boxUpper.setVisible(status);
		boxCenter.setVisible(status);
		boxBottom.setVisible(status);
	}

	/**
	 * Metodo che visualizza o nasconde il pulsante presente durante la premiazione
	 */
	private void panelMenu1SetStatus(boolean status) {
		btnStart.setText(msgB.GetResourceValue("btn_restart"));
		boxBottom.setVisible(status);
	}

	/**
	 * Metodo che permette il cambio della lingua
	 */
	private void chgLanguage() {
		if (languageCombo.getSelectedItem().toString().equals("IT"))
			msgB.SetLanguage("it", "IT");
		else
			msgB.SetLanguage("en", "US");
	}

	/**
	 * Metodo che imposta i componenti del menu' in base alla lingua scelta
	 */
	private void buildComponent() {
		int i;
		int itemCount = bkgCombo.getItemCount();

		for (i = 0; i < itemCount; i++) {
			bkgCombo.removeItemAt(0);
		}
		String lan;
		if (languageCombo.getSelectedItem() != null)
			lan = languageCombo.getSelectedItem().toString();
		else
			lan = null;

		itemCount = languageCombo.getItemCount();
		for (i = 0; i < itemCount; i++) {
			languageCombo.removeItemAt(0);
		}

		bkgCombo.addItem(msgB.GetResourceValue("bkg_sand"));
		bkgCombo.addItem(msgB.GetResourceValue("bkg_field"));
		bkgCombo.addItem(msgB.GetResourceValue("bkg_green"));
		bkgCombo.addItem(msgB.GetResourceValue("bkg_rock"));
		bkgCombo.addItem(msgB.GetResourceValue("bkg_ice"));
		bkgCombo.addItem(msgB.GetResourceValue("bkg_railroad"));
		languageCombo.addItem("IT");
		languageCombo.addItem("EN");
		if (lan != null) {
			if (lan.equals("IT"))
				languageCombo.setSelectedIndex(0);
			else
				languageCombo.setSelectedIndex(1);
		} else
			languageCombo.setSelectedIndex(0);

		itemCount = atmoCombo.getItemCount();
		for (i = 0; i < itemCount; i++) {
			atmoCombo.removeItemAt(0);
		}

		atmoCombo.addItem(msgB.GetResourceValue("atmo_sun"));
		atmoCombo.addItem(msgB.GetResourceValue("atmo_rain"));
		atmoCombo.addItem(msgB.GetResourceValue("atmo_stormwind"));
		atmoCombo.addItem(msgB.GetResourceValue("atmo_snow"));

		if (firstrace)
			btnStart.setText(msgB.GetResourceValue("btn_start"));
		else
			btnStart.setText(msgB.GetResourceValue("btn_restart"));
		numehorseLabel.setText(msgB.GetResourceValue("label_numehorse"));
		atmoLabel.setText(msgB.GetResourceValue("label_atmo"));
		percorsoLabel.setText(msgB.GetResourceValue("label_place"));
		languageLabel.setText(msgB.GetResourceValue("label_language"));
		soundCheckBox.setText(msgB.GetResourceValue("label_sound"));

	}

	/**
	 * Metodo che permette il caricamento delle immagini dei numeri per la
	 * classifica
	 */
	private void loadNumberimg() {
		try {

			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/1.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/2.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/3.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/4.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/5.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/6.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/7.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/8.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/9.png")));
			imgNum.addElement(ImageIO.read(getClass().getResourceAsStream("/img/num/10.png")));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Metodo che permette il caricamento delle immagini delle coppe per la
	 * premiazione
	 */
	private void loadCup() {
		try {

			imgCup.addElement(ImageIO.read(getClass().getResourceAsStream("/img/cup/coppaOro.png")));
			imgCup.addElement(ImageIO.read(getClass().getResourceAsStream("/img/cup/coppaArgento.png")));
			imgCup.addElement(ImageIO.read(getClass().getResourceAsStream("/img/cup/coppaBronzo.png")));

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Metodo che permette l'impostazione delle condizioni atmosferiche scelte
	 */
	private void SetAtmo() {
		atmo = MessagesBundle.GetResourceKey(atmoCombo.getSelectedItem().toString());
	}

	/**
	 * Metodo che permette il caricamento del background scelto
	 */
	private void loadBKGimg() {
		String bkgstr, filebkg = null;
		bkgstr = MessagesBundle.GetResourceKey(bkgCombo.getSelectedItem().toString());

		if (bkgstr.contentEquals("bkg_sand"))
			filebkg = "/img/bkg/sand.jpg";
		else if (bkgstr.contentEquals("bkg_field"))
			filebkg = "/img/bkg/field.jpg";
		else if (bkgstr.contentEquals("bkg_green"))
			filebkg = "/img/bkg/green.jpg";
		else if (bkgstr.contentEquals("bkg_railroad"))
			filebkg = "/img/bkg/railroad.jpg";
		else if (bkgstr.contentEquals("bkg_rock"))
			filebkg = "/img/bkg/rock.jpg";
		else if (bkgstr.contentEquals("bkg_ice"))
			filebkg = "/img/bkg/ice.jpg";

		try {
			imagefinish = (BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/bkg/finish.png"));
			if (filebkg != null)
				imagebkg = (BufferedImage) ImageIO.read(getClass().getResourceAsStream(filebkg));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Metodo che permette l'inizializzazione della posizione dei cavalli e il loro
	 * avvio
	 */
	private void initHorses() {
		if (h.size() != 0)
			h.clear();
		for (int i = 0; i < numhorse; i++) {
			h.addElement(new horse(i + 1, end, step));
			h.get(i).setPosX(INITIAL_X);
			h.get(i).setPosY(INITIAL_Y + i * corsia);
			h.get(i).start();
		}
	}

	/**
	 * Metodo che permette l'inizializzazione della posizione dei cavalli vincitori
	 * e il loro avvio
	 */
	private void initHorsesWinner() {
		if (horsewinner.size() != 0)
			horsewinner.clear();
		for (int i = 0; i < numhorseWinner; i++) {
			horsewinner.addElement(new horse(winner.get(i) + 1, end, step));
			horsewinner.get(i).setPosX(INITIAL_X);
			horsewinner.get(i).setPosY(INITIAL_Y + (i + 3) * corsia);
			horsewinner.get(i).start();
		}
	}

	/**
	 * Override del metodo paintComponent per disegnare background, corsie, cavalli,
	 * meteo e numeri
	 * 
	 * @param g Graphics
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (start) { //se siamo nella fase di corsa vengono visualizzati tutti i layer
			drawBackground(g);
			drawCorsie(g);
			drawHorse(g);
			drawWater(g);
			drawNumber(g);
		} else if (pricegiving) {//se siamo nella fase di premiazione vengono visualizzati il layer per il background e per i cavalli vincitori
			drawBackground(g);
			drawHorseWinner(g);

		}
	}

	/**
	 * Metodo che disegna i numeri della classifica
	 * 
	 * @param g Graphics
	 */
	private void drawNumber(Graphics g) {

		int i, horsenum;
		for (i = 0; i < winner.size(); i++) {
			horsenum = winner.get(i);
			g.drawImage(imgNum.get(i), end - 50, h.get(horsenum).getPosY() - height / 3, this);
		}
	}

	/**
	 * Metodo che disegna il background
	 * 
	 * @param g Graphics
	 */
	private void drawBackground(Graphics g) {
		g.drawImage(imagebkg, 0, 0, this);

	}

	/**
	 * Metodo che disegna i cavalli
	 * 
	 * @param g Graphics
	 */
	private void drawHorse(Graphics g) {
		Image hh;
		BufferedImage hh1 = null, hh2 = null;
		Graphics2D ig;
		for (int i = 0; i < numhorse; i++) {
			hh = h.get(i).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			g.drawImage(hh, h.get(i).getPosX(), h.get(i).getPosY(), this);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Metodo che disegna i cavalli vincitori
	 * 
	 * @param g Graphics
	 */
	private void drawHorseWinner(Graphics g) {
		Image hh;
		BufferedImage hh1 = null, hh2 = null;
		Graphics2D ig;
		for (int i = 0; i < numhorseWinner; i++) {
			hh = horsewinner.get(i).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			g.drawImage(hh, horsewinner.get(i).getPosX(), horsewinner.get(i).getPosY(), this);
			g.drawImage(imgCup.get(i), priceposition + 50, horsewinner.get(i).getPosY(), this);
			g.setFont(g.getFont().deriveFont(20f));
			g.setColor(Color.WHITE);
			g.drawString(horsewinner.get(i).getHorseName(), priceposition + 100, horsewinner.get(i).getPosY() + 30);

		}
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Metodo che disegna le coppe
	 * 
	 * @param g Graphics
	 */
	private void drawPrizeGiving(Graphics g) {
		int horsenum;
		Image hh;

		horsenum = winner.get(0);
		g.drawImage(imgNum.get(0), end / 2, INITIAL_Y / 2 - height, this);
		hh = h.get(horsenum).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		horsenum = winner.get(1);
		g.drawImage(imgNum.get(1), end / 2, INITIAL_Y / 2 - height / 2, this);
		hh = h.get(horsenum).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		horsenum = winner.get(2);
		g.drawImage(imgNum.get(2), end / 2, INITIAL_Y / 2 + height, this);
		hh = h.get(horsenum).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

	}

	/**
	 * Metodo che disegna le corsie
	 * 
	 * @param g Graphics
	 */
	private void drawCorsie(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		float[] dash1 = { 4f, 0f, 4f };

		BasicStroke bs1 = new BasicStroke((float) 2.5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f);
		g2d.setStroke(bs1);

		int i;
		for (i = 0; i < nmaxhorse; i++) {
			g.drawLine(0, i * corsia, end, i * corsia);
			g.drawImage(imagefinish, end, i * corsia, this);
		}
		g.drawLine(0, i * corsia, end, i * corsia);

	}

	/**
	 * Metodo che disegna il meteo
	 * 
	 * @param g Graphics
	 */
	private void drawWater(Graphics g) {
		weat.buildWeather();
		g.drawImage(weat.getImage(), 0, 0, this);
	}

	/**
	 * Metodo che controlla l'arrivo dei cavalli per la classifica
	 */
	private void checkWin() {

		int i;
		for (i = 0; i < numhorse; i++) {
			if (h.get(i).getPosX() >= end && h.get(i).GetFinalPosition() == 0) {
				h.get(i).SetFinalPosition(winner.size() + 1);
				winner.addElement(i);
			}
		}
		if (winner.size() == numhorse) {
			firstrace = false;
			start = false;

			end = priceposition;
			if (numhorse > 2)
				numhorseWinner = 3;
			else
				numhorseWinner = 2;
			initHorsesWinner();
			pricegiving = true;
			panelMenu1SetStatus(true);
			ClipSound(clipMenu, false);
			ClipSound(clipRace, false);
			ClipSound(clipWin, true);

		}
	}

	/**
	 * Metodo che controlla l'arrivo dei cavalli vincitori in prossimita' delle
	 * coppe
	 */
	private void checkEndPrizeGiving() {

		int i, allprice;
		allprice = 0;
		for (i = 0; i < numhorseWinner; i++) {
			if (horsewinner.get(i).getPosX() >= end) {
				horsewinner.get(i).SetFinalPosition(i + 1);
				allprice++;
			}
		}

	}

	/**
	 * Metodo che permette ai cavalli di muoversi
	 */
	private void moveHorse() {

		if (start)
			checkWin();
		else if (pricegiving)
			checkEndPrizeGiving();

		step.put();

		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Classe che gestisce la schedulazione del movimento dei cavalli attraverso un
	 * timer
	 */
	private class ScheduleTask extends TimerTask {

		@Override
		public void run() {
			moveHorse();
			repaint();
		}
	}
}
package HorseRacing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javax.imageio.ImageIO;

/**
 * Classe che implementa le condizioni atmosferiche
 * 
 * @author Patrissi Mathilde
 *
 */
public class weather {

	private int width, height, tilewidth, tileheight;
	private Image imgBase, thunder = null;
	private BufferedImage bkg;
	private String type = "atmo_sun";

	/**
	 * Costruttore della classe weather
	 * 
	 * @param width      int
	 * @param height     int
	 * @param tilewidth  int
	 * @param tileheight int
	 * @param type       String
	 */
	public weather(int width, int height, int tilewidth, int tileheight, String type) {
		this.width = width;
		this.height = height;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.type = type;
		Init();
	}

	/**
	 * Metodo per l'inizializzazione del meteo
	 * 
	 * @exception eccezione di I/O
	 */
	private void Init() {
		int i, j;
		try {
			String fileAtmo = null;
			if (type.equals("atmo_rain") || type.equals("atmo_stormwind"))
				fileAtmo = "/img/atmo/rain.png"; // nel caso in cui la condizione metereologica scelta sia "pioggia" o
													// "tempesta" viene caricata l'immagine della goccia d'acqua
			else if (type.equals("atmo_snow"))
				fileAtmo = "/img/atmo/snow.png"; // nel caso in cui la condizione metereologica scelta sia "neve" viene
													// caricata l'immagine del fiocco di neve

			if (fileAtmo != null)
				imgBase = ImageIO.read(getClass().getResourceAsStream(fileAtmo)).getScaledInstance(tilewidth / 5,
						tileheight / 5, Image.SCALE_SMOOTH);

			if (type.equals("atmo_stormwind"))// nel caso in cui la condizione metereologica scelta sia "tempesta" viene
												// caricata l'immagine del fulmine
				thunder = ImageIO.read(getClass().getResourceAsStream("/img/atmo/lampo.png"))
						.getScaledInstance(tilewidth / 2, tileheight / 2, Image.SCALE_SMOOTH);
			bkg = (BufferedImage) ImageIO.read(getClass().getResourceAsStream("/img/atmo/trasparente.png")); // carico
																												// un'immagine
																												// trasparente
																												// su
																												// cui
																												// verranno
																												// applicati
																												// i
																												// tile
																												// relativi
																												// alla
																												// pioggia,
																												// alla
																												// neve
																												// e al
																												// fulmine
																												// in
																												// modo
																												// random

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo per la realizzazione del layer meteo
	 */
	public void buildWeather() {
		int px, py;
		int i, j;
		BufferedImage bkg1 = null;
		try {
			bkg = ImageIO.read(getClass().getResourceAsStream("/img/atmo/trasparente.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D gg = bkg.createGraphics();
		if (imgBase != null) {

			for (i = 0; i < width; i += 30)
				for (j = 0; j < height; j += 30) // l'immagine dello sfondo viene suddivisa in tile dalla dimensione
													// 30*30 e in ciascunno di questi vengono applicati in modo random
													// le immagini della goccia d'acqua o del fiocco di neve
				{
					px = (int) (Math.random() * 30);
					py = (int) (Math.random() * 30);
					gg.drawImage(imgBase, i + px, j + py, null);
				}
			py = (int) (Math.random() * height);
		}
		if (thunder != null) {
			for (i = 0; i < width; i += 50) {// l'immagine dello sfondo viene suddivisa in colonne di larghezza 50 e in
												// ciascuna di queste vengono applicati in modo random le immagini del
												// fulmine
				py = (int) (Math.random() * height);
				gg.drawImage(thunder, i, py, null);
			}
		}

	}

	/**
	 * Metodo che restituisce il layer meteo
	 * 
	 * @return il layer meteo
	 */
	public Image getImage() {

		return bkg;
	}

}

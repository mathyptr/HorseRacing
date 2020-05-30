package HorseRacing;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Classe utilizzata per l'internazionalizzazione e la localizzazione della lingua
 * @author Patrissi Mathilde
 */

public class MessagesBundle {
    private String language="it";
    private String country="IT";
    private Locale currentLocale;
    private static  ResourceBundle messages;
    /*Costruttore della classe MessagesBundle
     */
    public void MessagesBundle() {
        }
    /**Metodo per il settaggio della lingua: La localizzazione del file avviene tramite la coppia language country
     * @param language String
     * @param country String
     */
    public void SetLanguage(String language, String country) {
    this.language=language;
    this.country=country;
    currentLocale = new Locale(language, country);
    messages = ResourceBundle.getBundle("language.MessagesBundle", currentLocale);
    
    }
    /**Metodo per prelevare la chiave associata alla label value
     * @param value String
     * @return la chiave
     */
    public static String GetResourceKey(String value) {
    	String key="";
	    Enumeration  bundleKeys= messages.getKeys();
	    while (bundleKeys.hasMoreElements()) {
	         key = (String)bundleKeys.nextElement();
	         if(messages.getString(key).equals(value))
	        	 break;
	    }
    	return key;
	 
    }
    /**Metodo per prelevare la label associato alla chiave key
     * @param key String
     * @return la label
     */
    public static String GetResourceValue(String key) {
    	return messages.getString(key);
	 
    }

}

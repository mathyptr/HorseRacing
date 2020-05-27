

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * descrizione
 * @author Patrissi Mathilde
 */

public class MessagesBundle {
    private String language="it";
    private String country="IT";
    private Locale currentLocale;
    private static  ResourceBundle messages;
    
    public void MessagesBundle() {
//        currentLocale = new Locale(language, country);
//        messages = ResourceBundle.getBundle("resources.language.MessagesBundle", currentLocale);
        }
    /**Metodo per il settaggio della lingua
     * @param language String
     * @param country String
     */
    public void SetLanguage(String language, String country) {
    this.language=language;
    this.country=country;
    currentLocale = new Locale(language, country);
    messages = ResourceBundle.getBundle("language.MessagesBundle", currentLocale);
   
   /* mapResource = new HashMap<>();
    Enumeration<String> keys = messages.getKeys();
    while (keys.hasMoreElements()) {
        String key = keys.nextElement();
        mapResource.put(key, messages.getString(key));
    }
*/
    
    }
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
    public static String GetResourceValue(String key) {
    	return messages.getString(key);
	 
    }

}

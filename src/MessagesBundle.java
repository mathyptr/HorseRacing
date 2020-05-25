import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesBundle {
    private String language="it";
    private String country="IT";
    private Locale currentLocale;
    private static  ResourceBundle messages;
    
    public void MessagesBundle() {
//        currentLocale = new Locale(language, country);
//        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
        }
    public void SetLanguage(String language, String country) {
    this.language=language;
    this.country=country;
    currentLocale = new Locale(language, country);
    messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }
    public static ResourceBundle GetResourceBundle() {
    	return messages;
    }
}

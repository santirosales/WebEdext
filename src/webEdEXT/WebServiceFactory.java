package webEdEXT;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class WebServiceFactory {
	private static WebServiceFactory instance = null;
	private edEXT.server.ServidorService service;
	
    //singleton
    //constructor privado para que no se instancie directamente
	private WebServiceFactory() {
		
	}
	
	public edEXT.server.Servidor getWS() {
        if(service == null) {
        	Properties properties = cargarProperties();
        	String wsdl = (String)properties.get("WSDL");
            System.out.println(String.format("***Usando url: %s", wsdl));
        	try {
				URL url = new URL(wsdl);
				try {
					service = new edEXT.server.ServidorService(url);
				} catch (Exception e) {
		            System.out.println(String.format("***ERROR: no se puede iniciar el servicio web en : %s", wsdl));
				}
			} catch (MalformedURLException e) {
	            System.out.println(String.format("***ERROR: no se puede usar la url: %s", wsdl));
				e.printStackTrace();
			}
        }
        return service.getServidorPort();
    }
	
	public static WebServiceFactory getInstance() {
		if(instance == null)
			instance = new WebServiceFactory();
		return instance;
	}
	
	private Properties cargarProperties() {
        Properties prop = new Properties();
        //String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("../config/webEdEXT.properties")) {
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            //System.out.println(prop.getProperty("WSDL"));
        } catch (IOException ex) {
            System.out.println(String.format("***ERROR: no se puede leer el archivo de propiedades."));
            ex.printStackTrace();
        }
        return prop;
	}
}

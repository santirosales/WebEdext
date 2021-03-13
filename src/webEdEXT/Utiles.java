package webEdEXT;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import edEXT.server.DtInfoUsuario;

public class Utiles {
	public static boolean puedeAcceder(HttpSession sesion, String tipoPuede) {
		if(sesion.getAttribute("usuarioLogueado") != null) {
			DtInfoUsuario usuario = (DtInfoUsuario)sesion.getAttribute("usuarioLogueado");
			if(usuario.getTipo().equals(tipoPuede))
				return true;
		}
		return false;
	}
	
	@SuppressWarnings("serial")
	public static XMLGregorianCalendar fechaXML(Date fecha) {
		XMLGregorianCalendar cal;
		if(fecha == null)
			return null;
		try {
			cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar() {{ setTime(fecha); }});
		} catch (DatatypeConfigurationException e) {
			return null;
		}
		return cal;
	}
}

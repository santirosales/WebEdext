
package edEXT.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para dtInfoEstudiante complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dtInfoEstudiante"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://server.edEXT/}dtInfoUsuario"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="inscripEdiciones" type="{http://server.edEXT/}dtInscripcionEdicion" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="inscripProgramas" type="{http://server.edEXT/}dtInscripcionPrograma" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="puntajes" type="{http://server.edEXT/}dtCursoPuntaje" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtInfoEstudiante", propOrder = {
    "inscripEdiciones",
    "inscripProgramas",
    "puntajes"
})
public class DtInfoEstudiante
    extends DtInfoUsuario
{

    @XmlElement(nillable = true)
    protected List<DtInscripcionEdicion> inscripEdiciones;
    @XmlElement(nillable = true)
    protected List<DtInscripcionPrograma> inscripProgramas;
    @XmlElement(nillable = true)
    protected List<DtCursoPuntaje> puntajes;

    /**
     * Gets the value of the inscripEdiciones property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the inscripEdiciones property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInscripEdiciones().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtInscripcionEdicion }
     * 
     * 
     */
    public List<DtInscripcionEdicion> getInscripEdiciones() {
        if (inscripEdiciones == null) {
            inscripEdiciones = new ArrayList<DtInscripcionEdicion>();
        }
        return this.inscripEdiciones;
    }

    /**
     * Gets the value of the inscripProgramas property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the inscripProgramas property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInscripProgramas().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtInscripcionPrograma }
     * 
     * 
     */
    public List<DtInscripcionPrograma> getInscripProgramas() {
        if (inscripProgramas == null) {
            inscripProgramas = new ArrayList<DtInscripcionPrograma>();
        }
        return this.inscripProgramas;
    }

    /**
     * Gets the value of the puntajes property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the puntajes property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPuntajes().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtCursoPuntaje }
     * 
     * 
     */
    public List<DtCursoPuntaje> getPuntajes() {
        if (puntajes == null) {
            puntajes = new ArrayList<DtCursoPuntaje>();
        }
        return this.puntajes;
    }

}

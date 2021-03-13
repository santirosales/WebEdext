
package edEXT.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para dtCertificadoProgramaFormacion complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dtCertificadoProgramaFormacion"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="estudiante" type="{http://server.edEXT/}dtInfoEstudiante" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="programa" type="{http://server.edEXT/}dtProgramaFormacion" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="inscripcion" type="{http://server.edEXT/}dtInscripcionPrograma" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="inscripcionesEdiciones" type="{http://server.edEXT/}dtInscripcionEdicion" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCertificadoProgramaFormacion", propOrder = {
    "estudiante",
    "programa",
    "inscripcion",
    "inscripcionesEdiciones",
    "error"
})
public class DtCertificadoProgramaFormacion {

    protected DtInfoEstudiante estudiante;
    protected DtProgramaFormacion programa;
    protected DtInscripcionPrograma inscripcion;
    @XmlElement(nillable = true)
    protected List<DtInscripcionEdicion> inscripcionesEdiciones;
    protected String error;

    /**
     * Obtiene el valor de la propiedad estudiante.
     * 
     * @return
     *     possible object is
     *     {@link DtInfoEstudiante }
     *     
     */
    public DtInfoEstudiante getEstudiante() {
        return estudiante;
    }

    /**
     * Define el valor de la propiedad estudiante.
     * 
     * @param value
     *     allowed object is
     *     {@link DtInfoEstudiante }
     *     
     */
    public void setEstudiante(DtInfoEstudiante value) {
        this.estudiante = value;
    }

    /**
     * Obtiene el valor de la propiedad programa.
     * 
     * @return
     *     possible object is
     *     {@link DtProgramaFormacion }
     *     
     */
    public DtProgramaFormacion getPrograma() {
        return programa;
    }

    /**
     * Define el valor de la propiedad programa.
     * 
     * @param value
     *     allowed object is
     *     {@link DtProgramaFormacion }
     *     
     */
    public void setPrograma(DtProgramaFormacion value) {
        this.programa = value;
    }

    /**
     * Obtiene el valor de la propiedad inscripcion.
     * 
     * @return
     *     possible object is
     *     {@link DtInscripcionPrograma }
     *     
     */
    public DtInscripcionPrograma getInscripcion() {
        return inscripcion;
    }

    /**
     * Define el valor de la propiedad inscripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link DtInscripcionPrograma }
     *     
     */
    public void setInscripcion(DtInscripcionPrograma value) {
        this.inscripcion = value;
    }

    /**
     * Gets the value of the inscripcionesEdiciones property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the inscripcionesEdiciones property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getInscripcionesEdiciones().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtInscripcionEdicion }
     * 
     * 
     */
    public List<DtInscripcionEdicion> getInscripcionesEdiciones() {
        if (inscripcionesEdiciones == null) {
            inscripcionesEdiciones = new ArrayList<DtInscripcionEdicion>();
        }
        return this.inscripcionesEdiciones;
    }

    /**
     * Obtiene el valor de la propiedad error.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Define el valor de la propiedad error.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

}

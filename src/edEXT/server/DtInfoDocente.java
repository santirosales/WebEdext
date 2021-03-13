
package edEXT.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Clase Java para dtInfoDocente complex type.
 * 
 * &lt;p&gt;El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dtInfoDocente"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://server.edEXT/}dtInfoUsuario"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="instituto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="cursos" type="{http://server.edEXT/}dtInfoCurso" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ediciones" type="{http://server.edEXT/}dtCursoEdicion" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="programas" type="{http://server.edEXT/}dtProgramaFormacion" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtInfoDocente", propOrder = {
    "instituto",
    "cursos",
    "ediciones",
    "programas"
})
public class DtInfoDocente
    extends DtInfoUsuario
{

    protected String instituto;
    @XmlElement(nillable = true)
    protected List<DtInfoCurso> cursos;
    @XmlElement(nillable = true)
    protected List<DtCursoEdicion> ediciones;
    @XmlElement(nillable = true)
    protected List<DtProgramaFormacion> programas;

    /**
     * Obtiene el valor de la propiedad instituto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstituto() {
        return instituto;
    }

    /**
     * Define el valor de la propiedad instituto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstituto(String value) {
        this.instituto = value;
    }

    /**
     * Gets the value of the cursos property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the cursos property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCursos().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtInfoCurso }
     * 
     * 
     */
    public List<DtInfoCurso> getCursos() {
        if (cursos == null) {
            cursos = new ArrayList<DtInfoCurso>();
        }
        return this.cursos;
    }

    /**
     * Gets the value of the ediciones property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the ediciones property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getEdiciones().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtCursoEdicion }
     * 
     * 
     */
    public List<DtCursoEdicion> getEdiciones() {
        if (ediciones == null) {
            ediciones = new ArrayList<DtCursoEdicion>();
        }
        return this.ediciones;
    }

    /**
     * Gets the value of the programas property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the programas property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getProgramas().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DtProgramaFormacion }
     * 
     * 
     */
    public List<DtProgramaFormacion> getProgramas() {
        if (programas == null) {
            programas = new ArrayList<DtProgramaFormacion>();
        }
        return this.programas;
    }

}

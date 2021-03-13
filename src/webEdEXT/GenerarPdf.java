package webEdEXT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import edEXT.server.DtCertificadoProgramaFormacion;
import edEXT.server.DtInfoUsuario;
import edEXT.server.DtInscripcionEdicion;

/**
 * Servlet implementation class GenerarPdf
 */
@WebServlet("/GenerarPdf")
public class GenerarPdf extends HttpServlet {
    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		String error = "";
		//tomo el programa y el usuario
		String nombrePrograma = request.getParameter("programa");
		DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
		if(nombrePrograma == null || nombrePrograma.isEmpty())
			error = "Debe proporcionar el nombre de un programa de formación.";
		if(error.isEmpty()) {
			if(usuarioLogueado == null || !usuarioLogueado.getTipo().equals("Estudiante")) 
				error = "Debe estar logueado como Estudiante.";
		}
		if(!error.isEmpty()) {
			request.setAttribute("error", error);
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return;
		}
		
		DtCertificadoProgramaFormacion certificado = ws.obtenerCertificadoProgramaFormacion(usuarioLogueado.getNickname(), nombrePrograma);
		if(!certificado.getError().isEmpty()) {
			request.setAttribute("error", certificado.getError());
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return;
		}
		//el pdf, ya viene chequeado el programa y el usuario
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        pdfDoc.setDefaultPageSize(PageSize.A4.rotate());
        
        //fondo
        String fondoPath = getServletContext().getRealPath("WEB-INF/images/borde_certificado.png");  
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.addImage(ImageDataFactory.create(fondoPath), PageSize.A4.rotate(), false);
        
        String filePath = getServletContext().getRealPath("WEB-INF/images/EdEXT.png");
        ImageData imageData = ImageDataFactory.create(filePath);
        Image logo = new Image(imageData);
        
        //agregar titulo
        Text titulo = new Text("Certificado de aprovación");
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        titulo.setFont(font);
        titulo.setFontSize(24);
        Paragraph parrafoTitulo = new Paragraph(titulo);
        parrafoTitulo.setTextAlignment(TextAlignment.LEFT);
        
        Table table = new Table(3);
        table.setWidth(UnitValue.createPercentValue(100));
        table.addCell(getCell(logo));
        table.addCell(getCell(parrafoTitulo, TextAlignment.CENTER, VerticalAlignment.BOTTOM));
        table.addCell(getCell(new Paragraph(""), TextAlignment.RIGHT, VerticalAlignment.BOTTOM));

        doc.add(table);
        
        //separador
        SolidLine line = new SolidLine(1f);
        LineSeparator ls = new LineSeparator(line);
        doc.add(ls);

        //agregar titulo programa
        Text tituloPrograma = new Text(certificado.getPrograma().getNombre());
        tituloPrograma.setFont(font);
        tituloPrograma.setFontSize(18);
        Paragraph parrafoPrograma = new Paragraph(tituloPrograma);
        parrafoPrograma.setTextAlignment(TextAlignment.CENTER);
        doc.add(parrafoPrograma);

        doc.add(new Paragraph(new Text("\n")));  
        
        Text tituloEstudiante = new Text(String.format("El estudiante %s %s, ha aprobado los cursos:", certificado.getEstudiante().getNombre(), certificado.getEstudiante().getApellido()));
        tituloEstudiante.setFont(font);
        tituloEstudiante.setFontSize(14);
        Paragraph parrafoEstudiante = new Paragraph(tituloEstudiante);
        doc.add(parrafoEstudiante);

        doc.add(new Paragraph(new Text("\n")));  
        
        Table tableCursos = new Table(3);
        tableCursos.setWidth(UnitValue.createPercentValue(100));
    	tableCursos.addCell(getCellBold(new Paragraph("Nombre del curso"), TextAlignment.LEFT, VerticalAlignment.MIDDLE));
    	tableCursos.addCell(getCellBold(new Paragraph("Fecha aprovación"), TextAlignment.RIGHT, VerticalAlignment.MIDDLE));
    	tableCursos.addCell(getCellBold(new Paragraph("Nota"), TextAlignment.RIGHT, VerticalAlignment.MIDDLE));
        for(DtInscripcionEdicion i : certificado.getInscripcionesEdiciones()) {
        	if(i != null) {
	        	tableCursos.addCell(getCell(new Paragraph(i.getCurso()), TextAlignment.LEFT, VerticalAlignment.MIDDLE));
	        	tableCursos.addCell(getCell(new Paragraph(formato.format(i.getFechaNota().toGregorianCalendar().getTime())), TextAlignment.RIGHT, VerticalAlignment.MIDDLE));
	        	tableCursos.addCell(getCell(new Paragraph(i.getNota().toString()), TextAlignment.RIGHT, VerticalAlignment.MIDDLE));
        	}
        }
        doc.add(tableCursos);
        
        //para separar
        doc.add(new Paragraph(new Text("\n")));  
        doc.add(new Paragraph(new Text("\n")));  

        //agregar fin
        Text tituloFin = new Text(String.format("Ha cumplido todos los cursos del programa en la fecha: %s", formato.format(certificado.getInscripcion().getFechaAprobada().toGregorianCalendar().getTime())));
        tituloFin.setFont(font);
        tituloFin.setFontSize(14);
        Paragraph parrafoFin = new Paragraph(tituloFin);
        parrafoFin .setTextAlignment(TextAlignment.LEFT);
        doc.add(parrafoFin);

        doc.close();

        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the contentlength
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }
    
    public Cell getCell(Image imagen) {
        Cell cell = new Cell().add(imagen);
        cell.setPadding(0);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public Cell getCell(Paragraph text, TextAlignment alignment, VerticalAlignment verticalAlignment) {
        Cell cell = new Cell().add(text);
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public Cell getCellBold(Paragraph text, TextAlignment alignment, VerticalAlignment verticalAlignment) throws IOException {
        Cell cell = new Cell().add(text);
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setBorder(Border.NO_BORDER);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        cell.setFontSize(14);
        cell.setFont(font);
        return cell;
    }

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 6067021675155015602L;
}

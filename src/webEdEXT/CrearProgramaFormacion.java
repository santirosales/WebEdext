package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edEXT.server.DtProgramaFormacion;

/**
 * Servlet implementation class CrearProgramaFormacion
 */
@WebServlet("/CrearProgramaFormacion")
public class CrearProgramaFormacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrearProgramaFormacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			
			request.getRequestDispatcher("/WEB-INF/crearProgramaFormacion.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			String error = "";
			String nombre = request.getParameter("nombre");		
			String descripcion = request.getParameter("descripcion");
			String fechaInicioStr = request.getParameter("fechaInicio");
			String fechaFinStr = request.getParameter("fechaFin");
			String urlImagen = request.getParameter("urlImagen");
			Date fechaInicio = null;
			try {
				fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
			} catch (ParseException e) {
				error = String.format("No se puede convertir la fecha de inicio %s.", fechaInicioStr);
			}		
			Date fechaFin = null;
			try {
				fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinStr);
			} catch (ParseException e) {
				error = String.format("No se puede convertir la fecha de inicio %s.", fechaFinStr);
			}
			final Date fechaInicioDev = fechaInicio;
			final Date fechaFinDev = fechaFin;			
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//no hay error
			if(error.isEmpty()) {
				error = ws.crearProgramaFormacion(nombre, descripcion, Utiles.fechaXML(fechaInicio), Utiles.fechaXML(fechaFin), urlImagen);
			}
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);					
				//relleno todo para que se cargue al volver
				DtProgramaFormacion dt = new DtProgramaFormacion();
				dt.setNombre(nombre);
				dt.setDescripcion(descripcion);
				dt.setFechaInicio(Utiles.fechaXML(fechaInicioDev));
				dt.setFechaFin(Utiles.fechaXML(fechaFinDev));
				dt.setUrlImagen(urlImagen);
				request.setAttribute("programa", dt);
				request.getRequestDispatcher("/WEB-INF/crearProgramaFormacion.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}
}

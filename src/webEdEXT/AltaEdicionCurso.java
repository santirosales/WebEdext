package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtListaString;

/**
 * Servlet implementation class AltaEdicionCurso
 */
@WebServlet("/AltaEdicionCurso")
public class AltaEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtListaString dt = ws.listarInstitutos();
			List<String> institutos = dt.getLista();
			request.setAttribute("institutos", institutos);		
			request.getRequestDispatcher("/WEB-INF/altaEdicionCurso.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			String error = "";
			String nombre = request.getParameter("nombre");
			String urlImagen = request.getParameter("urlImagen");
			String instituto = request.getParameter("instituto");
			String curso = request.getParameter("curso");
			String cupoStr = request.getParameter("cupo");
			String fechaInicioStr = request.getParameter("fechaInicio");
			String fechaFinStr = request.getParameter("fechaFin");
			List<String> listaDocentes = null;
			if(request.getParameter("listaDocentes") != null)
				listaDocentes = Arrays.asList(request.getParameterValues("listaDocentes"));
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
			Integer cupo = -1;
			if(cupoStr == null || cupoStr.isEmpty())
				cupoStr = "-1";
			try {
				cupo = Integer.parseInt(cupoStr);
			} catch (NumberFormatException e) {
				error = String.format("No se puede convertir el valor %s.", cupoStr);
			}		
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//no hay error
			if(error.isEmpty()) {
				error = ws.crearEdicionCurso(nombre, curso, Utiles.fechaXML(fechaInicio), Utiles.fechaXML(fechaFin), 
										cupo, Utiles.fechaXML(new Date()), urlImagen, 
										listaDocentes);
			}
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);		
				//relleno todo para que se cargue al volver
				DtCursoEdicion dt = new DtCursoEdicion();
				dt.setNombre(nombre);
				dt.setCurso(curso);
				dt.setInstituto(instituto);
				dt.setFechaInicio(Utiles.fechaXML(fechaInicioDev));
				dt.setFechaFin(Utiles.fechaXML(fechaFinDev)); 
				dt.setCupo(cupo);
				dt.setFechaRegistro(Utiles.fechaXML(new Date()));
				dt.setUrlImagen(urlImagen);
				dt.setVigente(false);
				request.setAttribute("edicion", dt);		
				List<String> institutos = ws.listarInstitutos().getLista();
				request.setAttribute("institutos", institutos);
				List<String> cursos = ws.listarCursosInstitutoString(instituto).getLista();
				request.setAttribute("cursoSeleccionado", curso);		
				request.setAttribute("cursos", cursos);		
				List<String> docentes = ws.listarDocentesInstituto(instituto).getLista();
				request.setAttribute("docentes", docentes);
				request.setAttribute("listaDocentes", listaDocentes);			
				request.getRequestDispatcher("/WEB-INF/altaEdicionCurso.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

}

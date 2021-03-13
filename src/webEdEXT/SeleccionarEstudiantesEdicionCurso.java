package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtInfoDocente;

/**
 * Servlet implementation class SeleccionarEstudiantesEdicionCurso
 */
@WebServlet("/SeleccionarEstudiantesEdicionCurso")
public class SeleccionarEstudiantesEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionarEstudiantesEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		String edicion = request.getParameter("edicion");
		String instituto = "";
		String curso = "";
		DtCursoEdicion edicionExiste = null;
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			//si estoy aca se que es docente y no es nulo
			DtInfoDocente usuario = (DtInfoDocente)request.getSession().getAttribute("usuarioLogueado");
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//no todos los cursos del instituto del docente para que seleccione
			instituto = usuario.getInstituto();
			List<String> cursos = ws.listarCursosInstitutoString(instituto).getLista();
			//si viene una edicion cargo los datos acorde a ella, seteo instituto y curso para que se muestren seleccionados
			if(edicion != null && !edicion.isEmpty()) {
				edicionExiste = ws.getDtInfoCursoEdicion(edicion);
				if(edicionExiste != null) {
					//verifico que la edicion sea del instituto del docente
					if(usuario.getInstituto().equals(edicionExiste.getInstituto())) {
						curso = edicionExiste.getCurso();
					} else {
						request.setAttribute("error", "El docente no pertenece al instituto del curso de la edición.");
						request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);											
					}
				}
			} else {
			}
			request.setAttribute("cursos", cursos);			
			request.setAttribute("curso", curso);			
			request.setAttribute("instituto", instituto);			
			request.setAttribute("edicion", edicionExiste);
			request.getRequestDispatcher("/WEB-INF/seleccionarEstudiantesEdicionCurso.jsp").forward(request, response);
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
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			String error = "";
			String curso = request.getParameter("curso");
			String instituto = request.getParameter("instituto");
			String edicion = request.getParameter("edicion");
			DtCursoEdicion edicionExiste = null;
			DtInfoDocente usuario = (DtInfoDocente)request.getSession().getAttribute("usuarioLogueado");
			if(usuario == null )
				error = "Debe loguearse como docente para seleccionar los estudiantes.";
			else {
				if(edicion == null || edicion.isEmpty())
					error = "Debe seleccionar una edición vigente.";
				else {
					edicionExiste = ws.getDtInfoCursoEdicion(edicion);
					if(edicionExiste != null) {
						//verifico que la edicion sea del instituto del docente
						if(usuario.getInstituto().equals(edicionExiste.getInstituto())) {
							curso = edicionExiste.getCurso();
						} else
							error = "El docente no pertenece al instituto del curso de la edición.";
					} else
						error = "No existe la edición.";					
				}
			}
			String[] listaInscripciones = null;
			if(request.getParameter("listaInscripciones") != null)
				listaInscripciones = request.getParameterValues("listaInscripciones");
			else
				error = "La lista de inscriptos está vacía.";
			String[] listaEstados = null;
			if(request.getParameter("listaEstados") != null) {
				listaEstados = request.getParameterValues("listaEstados");
				if (listaInscripciones.length != listaEstados.length)
					error = "El tamaño de la lista de inscriptos y la de estados debe coincidir.";
			}
			else
				error = "La lista de estados está vacía.";
			//por ultimo verifico el cupo
			if(error.isEmpty()) {
				Integer cantidad = 0;
				for(int i = 0; i < listaEstados.length; i++)
					if (listaEstados[i].equals("Aceptada"))
						cantidad++;
				if(edicionExiste.getCupo() != -1 && cantidad > edicionExiste.getCupo())
					error = "La cantidad de aceptados a la edición es mayor al cupo.";				
			}			
			//no hay error y se que las listas tienen el mismo tamaño
			if(error.isEmpty()) {
				for(int i = 0; i < listaInscripciones.length; i++) {
					if (listaEstados[i].equals("Aceptada"))
						error = ws.aceptarInscripcionEdicion(edicion, listaInscripciones[i]);
					else if (listaEstados[i].equals("Rechazada"))
						error = ws.rechazarInscripcionEdicion(edicion, listaInscripciones[i]);
				}
			}
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);		
				//relleno todo para que se cargue al volver
				List<String> institutos = ws.listarInstitutos().getLista();
				request.setAttribute("institutos", institutos);
				List<String> cursos = null;
				if(instituto != null && !instituto.isEmpty()) 
					cursos = ws.listarCursosInstitutoString(instituto).getLista();
				request.setAttribute("cursos", cursos);	
				request.setAttribute("instituto", instituto);
				if(edicionExiste != null) {
					instituto = edicionExiste.getInstituto();
					curso = edicionExiste.getCurso();
				}
				request.setAttribute("curso", curso);
				request.setAttribute("edicion", edicionExiste);
				request.setAttribute("listaInscripciones", listaInscripciones);
				request.setAttribute("listaEstados", listaEstados);
				request.getRequestDispatcher("/WEB-INF/seleccionarEstudiantesEdicionCurso.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}
}

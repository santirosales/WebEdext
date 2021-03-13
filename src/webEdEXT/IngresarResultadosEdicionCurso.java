package webEdEXT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtInfoCurso;
import edEXT.server.DtInfoDocente;

/**
 * Servlet implementation class IngresarResultadosEdicionCurso
 */
@WebServlet("/IngresarResultadosEdicionCurso")
public class IngresarResultadosEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IngresarResultadosEdicionCurso() {
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
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			String nickname = ((DtInfoDocente)request.getSession().getAttribute("usuarioLogueado")).getNickname();
			DtInfoDocente usuario = (DtInfoDocente)ws.seleccionarUsuario(nickname);
			//no todos los cursos del instituto del docente para que seleccione
			instituto = usuario.getInstituto();
			List<String> cursos = new ArrayList<String>();
			for(DtInfoCurso dt : usuario.getCursos()) {
				if( dt != null)
					cursos.add(dt.getNombre());
			}
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
			}
			request.setAttribute("cursos", cursos);			
			request.setAttribute("curso", curso);			
			request.setAttribute("instituto", instituto);			
			request.setAttribute("edicion", edicionExiste);
			request.getRequestDispatcher("/WEB-INF/ingresarResultadosEdicionCurso.jsp").forward(request, response);
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
			//el check si esta seleccionado viene, sino es nulo
			boolean finalizar = request.getParameter("cerrarEdicion") != null;
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
			String[] listaNotas = null;
			if(request.getParameter("listaNotas") != null) {
				listaNotas = request.getParameterValues("listaNotas");
				if (listaInscripciones.length != listaNotas.length)
					error = "El tamaño de la lista de inscriptos y la de notas debe coincidir.";
			}
			else
				error = "La lista de notas está vacía.";
			//no hay error y se que las listas tienen el mismo tamaño
			if(error.isEmpty()) {
				for(int i = 0; i < listaInscripciones.length; i++) {
					Integer nota = null;
					Date fecha = null;
					if(listaNotas[i] != null && !listaNotas[i].isEmpty()) {
						//asignamos las notas en la fecha actual, siempre y cuando no sea nulo
						try {
							nota = Integer.parseInt(listaNotas[i]);
						} catch (NumberFormatException e) {
							error = String.format("No se puede convertir el valor %s para %s y %s.", listaNotas[i], edicion, listaInscripciones[i]);
						}
						fecha = new Date();
					}
					//calificamos cada una de las inscripciones
					if (error.isEmpty())
						error = ws.calificarInscripcionEdicion(edicion, listaInscripciones[i], nota, Utiles.fechaXML(fecha));
				}
				//si viene el parametro finalizar la finalizamos
				if (error.isEmpty() && finalizar)
					error = ws.finalizarEdicion(edicion);
					if(!error.isEmpty())
						System.out.println(String.format("ERROR: %s", error));
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
					//le dejo solo las inscripciones que me interesan
					edicionExiste.getInscripciones().removeIf(i -> !i.getEstado().equals("Aceptada"));
				}
				request.setAttribute("curso", curso);
				request.setAttribute("edicion", edicionExiste);
				request.setAttribute("listaInscripciones", listaInscripciones);
				request.setAttribute("listaNotas", listaNotas);
				request.getRequestDispatcher("/WEB-INF/ingresarResultadosEdicionCurso.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}
}

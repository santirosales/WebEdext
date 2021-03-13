package webEdEXT;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;
import edEXT.server.DtInscripcionEdicion;

/**
 * Servlet implementation class AgregarComentarioEdicionCurso
 */
@WebServlet("/AgregarComentarioEdicionCurso")
public class AgregarComentarioEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarComentarioEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		String error = "";
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			String nombreEdicion = request.getParameter("edicion");
			if(nombreEdicion != null && !nombreEdicion.isEmpty()) {
				edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
				DtCursoEdicion edicion = ws.getDtInfoCursoEdicion(nombreEdicion);
				if(edicion != null) {
					//ahora el estudiante que comenta tiene que tener la edicion de curso
					DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
					DtInfoEstudiante estudiante = (DtInfoEstudiante)ws.seleccionarUsuario(usuario.getNickname());
					if(estudiante != null) {
						//buscamos que tenga al menos una inscripcion en la edicion con estado distinto de rechazada
						DtInscripcionEdicion ins = estudiante.getInscripEdiciones().stream()
														.filter(i -> i.getEdicion().equals(nombreEdicion) && !("Rechazada".equals(i.getEstado())))
														.findAny()
														.orElse(null);
						if(ins != null) {
							//cumple con todo
							request.setAttribute("edicion", edicion);
						} else 
							error = String.format("El estudiante %s debe tener al menos una inscripcion en la edición del curso.", usuario.getNickname());
					} else 
						error = String.format("No se puede encontrar el estudiante %s.", usuario.getNickname());
				} else 
					error = String.format("La edición de curso %s no existe.", nombreEdicion);				
			} else  //no viene una edicion
				error = "Debe especificar una edición de curso.";
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return;
		}
		if(!error.isEmpty()) 
			request.setAttribute("error", error);
		request.getRequestDispatcher("/WEB-INF/agregarComentarioEdicionCurso.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		String error = "";
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			String nombreEdicion = request.getParameter("edicion");
			String texto = request.getParameter("texto");
			if(texto != null && !texto.isEmpty()) {
				if(nombreEdicion != null && !nombreEdicion.isEmpty()) {
					edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
					DtCursoEdicion edicion = ws.getDtInfoCursoEdicion(nombreEdicion);
					if(edicion != null) {
						//ahora el estudiante que comenta tiene que tener la edicion de curso
						DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
						DtInfoEstudiante estudiante = (DtInfoEstudiante)ws.seleccionarUsuario(usuario.getNickname());
						if(estudiante != null) {
							//buscamos que tenga al menos una inscripcion en la edicion con estado distinto de rechazada
							DtInscripcionEdicion ins = estudiante.getInscripEdiciones().stream()
															.filter(i -> i.getEdicion().equals(nombreEdicion) && !("Rechazada".equals(i.getEstado())))
															.findAny()
															.orElse(null);
							if(ins != null) {
								//cumple con todo agregamos el comentario
								error = ws.agregarCursoEdicionComentario(estudiante.getNickname(), nombreEdicion, texto);
							} else 
								error = String.format("El estudiante %s debe tener al menos una inscripcion en la edición del curso.", usuario.getNickname());
						} else 
							error = String.format("No se puede encontrar el estudiante %s.", usuario.getNickname());
					} else 
						error = String.format("La edición de curso %s no existe.", nombreEdicion);				
				} else  //no viene una edicion
					error = "Debe especificar una edición de curso.";
			} else  //no viene un texo
				error = "Debe especificar un texto para el comentario.";
			//si esta todo ok lo mandamos a la pagina de comentarios sino seteamos el error
			if(error.isEmpty()) 
				response.sendRedirect(String.format("ListarComentariosEdicionCurso?nombre=%s", URLEncoder.encode(nombreEdicion, StandardCharsets.UTF_8.toString())));
			else {
				request.setAttribute("error", error);
				request.getRequestDispatcher("/WEB-INF/agregarComentarioEdicionCurso.jsp").forward(request, response);
			} 								
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}
}

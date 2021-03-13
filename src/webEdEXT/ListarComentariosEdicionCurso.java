package webEdEXT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtCursoEdicionComentario;
import edEXT.server.DtCursoPuntaje;
import edEXT.server.DtInfoCurso;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;
import edEXT.server.DtInscripcionEdicion;

/**
 * Servlet implementation class ListarComentariosEdicionCurso
 */
@WebServlet("/ListarComentariosEdicionCurso")
public class ListarComentariosEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarComentariosEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		String nombreEdicion = request.getParameter("nombre");
		String error = "";
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			if (nombreEdicion != null && !nombreEdicion.isEmpty()) {
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
							//cumple con todo tengo que tomar la valoracion del estudiante para el curso
							DtInfoCurso curso = ws.getDtInfoCurso(edicion.getCurso());
							List<DtEdicionComentarioMostrar> lista = new ArrayList<DtEdicionComentarioMostrar>();
							for(DtCursoEdicionComentario c : edicion.getComentarios()) {
								DtEdicionComentarioMostrar ele = new DtEdicionComentarioMostrar();
								ele.setComentario(c);
								//el puntaje del cuso para el usuario desde la edicion
								DtCursoPuntaje puntaje = curso.getPuntajes().stream().filter(p -> p.getEstudiante()
																			.equals(c.getEstudiante()))
																			.findFirst()
																			.orElse(null);
								ele.setPuntaje(puntaje);
								//agrego las respuestas
								for(DtCursoEdicionComentario r : c.getRespuestas()) {
									DtEdicionComentarioMostrar res = new DtEdicionComentarioMostrar();
									res.setComentario(r);
									//el puntaje del cuso para el usuario desde la edicion
									DtCursoPuntaje puntaje1 = curso.getPuntajes().stream().filter(p -> p.getEstudiante()
																				.equals(r.getEstudiante()))
																				.findFirst()
																				.orElse(null);
									res.setPuntaje(puntaje1);									
									ele.getRespuestas().add(res);
								}
								lista.add(ele);
							}
							request.setAttribute("comentarios", lista);
							request.setAttribute("edicion", edicion);
						} else 
							error = String.format("El estudiante %s debe tener al menos una inscripcion en la edición del curso.", usuario.getNickname());
					} else 
						error = String.format("No se puede encontrar el estudiante %s.", usuario.getNickname());
				} else 
					error = String.format("La edición de curso %s no existe.", nombreEdicion);				
				if(!error.isEmpty()) 
					request.setAttribute("error", error);
				request.getRequestDispatcher("/WEB-INF/listarComentariosEdicionCurso.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "Debe especificar un nombre de la edición del curso");
				request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);			
			} 
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
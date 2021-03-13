package webEdEXT;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edEXT.server.DtCursoPuntaje;
import edEXT.server.DtInfoCurso;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class ValorarCurso
 */
@WebServlet("/ValorarCurso")
public class ValorarCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValorarCurso() {
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
			String nombreCurso = request.getParameter("curso");
			if(nombreCurso != null && !nombreCurso.isEmpty()) {
				edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
				DtInfoCurso curso = ws.getDtInfoCurso(nombreCurso);
				if(curso != null) {
					//ahora el estudiante que comenta tiene que tener la edicion de curso
					DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
					DtInfoEstudiante estudiante = (DtInfoEstudiante)ws.seleccionarUsuario(usuario.getNickname());
					if(estudiante != null) {
						//buscamos que tenga
						List<DtInfoCurso> cursos = ws.listarCursosEstudiante(usuario.getNickname()).getLista();
						DtInfoCurso cursoExiste = cursos.stream()
												.filter(c -> c.getNombre().equals(nombreCurso))
													.findAny()
													.orElse(null);
						if(cursoExiste != null) {
							//tomo la valoracion del usuario si existe
							Integer valoracion = cursoExiste.getPuntajes().stream()
																			.filter(p -> estudiante.getNickname().equals(p.getEstudiante()))
																			.findAny()
																			.map(DtCursoPuntaje::getValor)
																			.orElse(0);
							//cumple con todo
							if(valoracion > 0 && valoracion < 6)
								request.setAttribute("existeValoracion", true);
							request.setAttribute("valoracion", valoracion);
							request.setAttribute("curso", curso);
						} else 
							error = String.format("El estudiante %s debe tener al menos una inscripcion alguna edición del curso.", usuario.getNickname());
					} else 
						error = String.format("No se puede encontrar el estudiante %s.", usuario.getNickname());
				} else 
					error = String.format("El curso %s no existe.", nombreCurso);				
			} else  //no viene un curso
				error = "Debe especificar un curso.";
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return;
		}
		if(!error.isEmpty()) 
			request.setAttribute("error", error);
		request.getRequestDispatcher("/WEB-INF/valorarCurso.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		String error = "";
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			String nombreCurso = request.getParameter("curso");
			String valoracion = request.getParameter("valoracion");
			if(valoracion != null && !valoracion.isEmpty()) {
				if(nombreCurso != null && !nombreCurso.isEmpty()) {
					edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
					DtInfoCurso curso = ws.getDtInfoCurso(nombreCurso);
					if(curso != null) {
						//ahora el estudiante que comenta tiene que tener la edicion de curso
						DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
						DtInfoEstudiante estudiante = (DtInfoEstudiante)ws.seleccionarUsuario(usuario.getNickname());
						if(estudiante != null) {
							//buscamos que tenga
							List<DtInfoCurso> cursos = ws.listarCursosEstudiante(usuario.getNickname()).getLista();
							DtInfoCurso cursoExiste = cursos.stream()
													.filter(c -> c.getNombre().equals(nombreCurso))
														.findAny()
														.orElse(null);
							if(cursoExiste != null) {
								//tomo la valoracion del usuario si existe
								Integer valoracionAnterior = cursoExiste.getPuntajes().stream()
																				.filter(p -> estudiante.getNickname().equals(p.getEstudiante()))
																				.findAny()
																				.map(DtCursoPuntaje::getValor)
																				.orElse(0);
								//cumple con todo tomamos la valoracion
								Integer valor = 0;
							    try {
							        valor = Integer.parseInt(valoracion);
							    } catch (NumberFormatException e) {
							        error = "El puntaje del curso debe ser un entero.";
							    }
							    //todo bien, puntuamos
							    if(error.isEmpty())							    
							    	error = ws.puntuarCurso(estudiante.getNickname(), nombreCurso, valor);
							    //si hay algun error
							    if(!error.isEmpty()) {
							    	if(valoracionAnterior > 0 && valoracionAnterior < 6)
							    		request.setAttribute("existeValoracion", true);
									request.setAttribute("valoracion", valoracionAnterior);							    	
							    }
							} else 
								error = String.format("El estudiante %s debe tener al menos una inscripcion en el curso.", usuario.getNickname());
						} else 
							error = String.format("No se puede encontrar el estudiante %s.", usuario.getNickname());
						//seteamos el curso
						request.setAttribute("curso", curso);
					} else 
						error = String.format("El curso %s no existe.", nombreCurso);				
				} else  //no viene un curso
					error = "Debe especificar un curso.";
			} else  //no viene un texo
				error = "Debe especificar un valor para la valoración.";
			//si esta todo ok lo mandamos a la pagina de comentarios sino seteamos el error
			if(error.isEmpty()) 
				response.sendRedirect(String.format("ListarValorarCurso?nombre=%s", URLEncoder.encode(nombreCurso, StandardCharsets.UTF_8.toString())));
			else {
				request.setAttribute("error", error);
				request.getRequestDispatcher("/WEB-INF/valorarCurso.jsp").forward(request, response);
			} 								
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}
}

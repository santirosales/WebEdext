package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;
import edEXT.server.DtInscripcionEdicion;

/**
 * Servlet implementation class DesistirInscripcionEdicionCurso
 */
@WebServlet("/DesistirInscripcionEdicionCurso")
public class DesistirInscripcionEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DesistirInscripcionEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		String edicion = request.getParameter("edicion");
		String nickname = request.getParameter("nickname");
		if (edicion != null && !edicion.isEmpty() && nickname != null && !nickname.isEmpty() ) {
			DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			if(usuarioLogueado != null && usuarioLogueado.getTipo().equals("Estudiante")) {
				edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
				DtInfoEstudiante usuario = (DtInfoEstudiante)ws.seleccionarUsuario(nickname);
				//tiene que ser el mismo usuario que está logueado
				if(usuarioLogueado.getNickname().equals(usuario.getNickname())) {
					//tomamos la primera -tiene una por edicion-
					DtInscripcionEdicion inscripcion = usuario.getInscripEdiciones().stream()
															.filter(i -> i.getEdicion().equals(edicion) 
																	&& i.getEstudiante().getNickname().equals(nickname))
															.findAny()
															.orElse(null);
					request.setAttribute("inscripcion", inscripcion);
					request.getRequestDispatcher("/WEB-INF/desistirInscripcionEdicionCurso.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("error", "Debe loguearse como estudiante.");
				request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);							
			}
		} else {
			request.setAttribute("error", "Debe especificar un nombre de edición y un nickname.");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		String edicion = request.getParameter("edicion");
		String nickname = request.getParameter("nickname");
		if (edicion != null && !edicion.isEmpty() && nickname != null && !nickname.isEmpty() ) {
			DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			if(usuarioLogueado != null && usuarioLogueado.getTipo().equals("Estudiante")) {
				edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
				DtInfoEstudiante usuario = (DtInfoEstudiante)ws.seleccionarUsuario(nickname);
				//tiene que ser el mismo usuario que está logueado
				if(usuarioLogueado.getNickname().equals(usuario.getNickname())) {
					//todo bien, la quitamos
					String error = ws.desitirInscripcionEdicion(edicion, nickname);
					if(error.isEmpty())
						response.sendRedirect("Index");
					else {
						//seteo el error
						request.setAttribute("error", error);
						//tomamos la primera -tiene una por edicion-
						DtInscripcionEdicion inscripcion = usuario.getInscripEdiciones().stream()
															.filter(i -> i.getEdicion().equals(edicion) 
																	&& i.getEstudiante().getNickname().equals(nickname))
															.findAny()
															.orElse(null);									
						request.setAttribute("inscripcion", inscripcion);
						request.getRequestDispatcher("/WEB-INF/desistirInscripcionEdicionCurso.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("error", "Solo puede desistir de inscripciones suyas.");
					request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);							
				}
			}
			else {
				request.setAttribute("error", "Debe loguearse como estudiante.");
				request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);							
			}
		} else {
			request.setAttribute("error", "Debe especificar un nombre de edición y un nickname.");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);			
		}
	}
}

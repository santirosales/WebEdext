package webEdEXT;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class InscripcionProgramaFormacion
 */
@WebServlet("/InscripcionProgramaFormacion")
public class InscripcionProgramaFormacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscripcionProgramaFormacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			List<String> programas = ws.listarProgramasFormacionString().getLista();
			request.setAttribute("programas", programas);
			request.setAttribute("programa", "");
			request.getRequestDispatcher("/WEB-INF/inscripcionProgramaFormacion.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			String nickname = usuario.getNickname();
			String error = "";
			String programa = request.getParameter("programa");
			if(programa == null || programa.isEmpty())
				error = "Debe seleccionar un programa de formación.";
			//tengo programa seleccionado, intento inscribirlo 
			error = ws.inscribirProgramaFormacion(programa, nickname, Utiles.fechaXML(new Date()));
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);		
				//relleno todo para que se cargue al volver
				List<String> programas = ws.listarProgramasFormacionString().getLista();
				request.setAttribute("programas", programas);
				request.setAttribute("programa", programa);
				request.getRequestDispatcher("/WEB-INF/inscripcionProgramaFormacion.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

}

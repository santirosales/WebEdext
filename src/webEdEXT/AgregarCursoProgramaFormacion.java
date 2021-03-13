package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtListaString;

/**
 * Servlet implementation class AgregarCursoProgramaFormacion
 */
@WebServlet("/AgregarCursoProgramaFormacion")
public class AgregarCursoProgramaFormacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarCursoProgramaFormacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtListaString dt = ws.listarProgramasFormacionString();
			List<String> programas = dt.getLista();
			request.setAttribute("programas", programas);
			request.setAttribute("programa", "");
			request.setAttribute("curso", "");
			request.getRequestDispatcher("/WEB-INF/agregarCursoProgramaFormacion.jsp").forward(request, response);
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
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			String error = "";
			String curso = request.getParameter("curso");
			String programa = request.getParameter("programa");
			if(curso == null || curso.isEmpty())
				error = "Debe seleccionar un curso.";
			if(programa == null || programa.isEmpty())
				error = "Debe seleccionar un programa de formación.";
			//tengo las dos cosas todo ok			
			error = ws.agregarCursoProgramaFormacion(programa,  curso);
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);		
				//relleno todo para que se cargue al volver
				DtListaString dt = ws.listarProgramasFormacionString();
				List<String> programas = dt.getLista();
				request.setAttribute("programas", programas);
				List<String> cursos = null;
				if(curso != null && !curso.isEmpty()) {
					DtListaString dtCursos = ws.listarCursosString();
					cursos = dtCursos.getLista();
				}
				request.setAttribute("cursos", cursos);	
				request.setAttribute("curso", curso);
				request.setAttribute("programa", programa);
				request.getRequestDispatcher("/WEB-INF/agregarCursoProgramaFormacion.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

}

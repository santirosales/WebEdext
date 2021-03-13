package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtCursoEdicion;

/**
 * Servlet implementation class ConsultaEdicionCurso
 */
@WebServlet("/ConsultaEdicionCurso")
public class ConsultaEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultaEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		if (nombre != null && !nombre.isEmpty()) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtCursoEdicion edicion = ws.getDtInfoCursoEdicion(nombre);
			request.setAttribute("edicion", edicion);
	        request.getRequestDispatcher("/WEB-INF/consultaEdicionCurso.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Debe especificar un nombre de la edición del curso");
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

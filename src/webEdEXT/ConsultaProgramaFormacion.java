package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtProgramaFormacion;

/**
 * Servlet implementation class ConsultaProgramaFormacion
 */
@WebServlet("/ConsultaProgramaFormacion")
public class ConsultaProgramaFormacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultaProgramaFormacion() {
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
			DtProgramaFormacion programa = ws.seleccionarProgramaFormacion(nombre);
			request.setAttribute("programa", programa);
	        request.getRequestDispatcher("/WEB-INF/consultaProgramaFormacion.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Debe especificar un nombre de programa de formación");
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

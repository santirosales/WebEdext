package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoInstituto;

/**
 * Servlet implementation class SeleccionarCursoInstituto
 */
@WebServlet("/SeleccionarCursoInstituto")
public class SeleccionarCursoInstituto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionarCursoInstituto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "institutos");
		String nombre = request.getParameter("nombre");
		if (nombre != null && !nombre.isEmpty()) {
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoInstituto instituto = ws.seleccionarInstituto(nombre);
			request.setAttribute("instituto", instituto);
			request.getRequestDispatcher("/WEB-INF/seleccionarCursoInstituto.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Debe especificar un nombre");
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

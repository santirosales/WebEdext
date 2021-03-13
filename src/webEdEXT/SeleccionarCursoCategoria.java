package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoCategoria;

/**
 * Servlet implementation class SeleccionarCursoCategoria
 */
@WebServlet("/SeleccionarCursoCategoria")
public class SeleccionarCursoCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionarCursoCategoria() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "categorias");
		String nombre = request.getParameter("nombre");
		if (nombre != null && !nombre.isEmpty()) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoCategoria categoria = ws.seleccionarCategoria(nombre);
			request.setAttribute("categoria", categoria);
			request.getRequestDispatcher("/WEB-INF/seleccionarCursoCategoria.jsp").forward(request, response);
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

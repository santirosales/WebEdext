package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListarInstitutosSidebar
 */
@WebServlet("/ListarInstitutosSidebar")
public class ListarInstitutosSidebar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarInstitutosSidebar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		List<String> institutos = ws.listarInstitutos().getLista();
		request.setAttribute("institutos", institutos);
        //para que se inserte el codigo en la pagina directo
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/listarInstitutosSidebar.jsp");      
        view.include(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

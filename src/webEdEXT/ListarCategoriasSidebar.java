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
 * Servlet implementation class ListarCategoriasSidebar
 */
@WebServlet("/ListarCategoriasSidebar")
public class ListarCategoriasSidebar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarCategoriasSidebar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		List<String> categorias = ws.listarCategorias().getLista();
		request.setAttribute("categorias", categorias);
        //para que se inserte el codigo en la pagina directo
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/listarCategoriasSidebar.jsp");      
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

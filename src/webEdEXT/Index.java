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
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		DtListaString dt = ws.listarInstitutos();
		List<String> institutos = dt.getLista();
		request.getSession().setAttribute("institutosSesion", institutos);
		//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
		DtListaString dtCategorias = ws.listarCategorias();
		List<String> categorias = dtCategorias.getLista();
		request.getSession().setAttribute("categoriasSesion", categorias);
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

}

package webEdEXT;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtListaCursos;

/**
 * Servlet implementation class ListarCursosTendencias
 */
@WebServlet("/ListarCursosTendencias")
public class ListarCursosTendencias extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarCursosTendencias() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		DtListaCursos cursos = ws.listarCursosVisitas();
		request.setAttribute("cursos", cursos.getLista());
        //para que se inserte el codigo en la pagina directo
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/listarCursosTendencias.jsp");      
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

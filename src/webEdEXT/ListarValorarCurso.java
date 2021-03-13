package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoCurso;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class ListarValorarCurso
 */
@WebServlet("/ListarValorarCurso")
public class ListarValorarCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarValorarCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			//ya se que hay usuario logueado
			DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			List<DtInfoCurso> cursos = ws.listarCursosEstudiante(usuario.getNickname()).getLista();
			request.setAttribute("cursos", cursos);
			request.getRequestDispatcher("/WEB-INF/listarValorarCurso.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
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

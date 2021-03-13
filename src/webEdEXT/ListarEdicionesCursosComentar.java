package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class Listar Resultados
 */
@WebServlet("/ListarEdicionesCursosComentar")
public class ListarEdicionesCursosComentar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarEdicionesCursosComentar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
		if(usuarioLogueado != null && usuarioLogueado.getTipo().equals("Estudiante")) {
			//tomamos de persistencia al estudiante para que este actualizado y poder modificarlo sin alterar el de la sesion
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoEstudiante dt = (DtInfoEstudiante)ws.seleccionarUsuario(usuarioLogueado.getNickname());
			//solo nos interesan las inscripciones que no fueron rechazadas
			if (dt != null)
				dt.getInscripEdiciones().removeIf(i -> i.getEstado().equals("Rechazada"));
			request.setAttribute("usuario", dt);
        	request.getRequestDispatcher("/WEB-INF/listarEdicionesCursosComentar.jsp").forward(request, response);
		} else {
				request.setAttribute("error", "Debe estar logueado como Estudiante");
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
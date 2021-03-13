package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class SeleccionarUsuario
 */
@WebServlet("/SeleccionarUsuario")
public class SeleccionarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "perfil");
		//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		List<DtInfoUsuario> usuarios = ws.listarUsuarios().getLista();
		request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/seleccionarUsuario.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

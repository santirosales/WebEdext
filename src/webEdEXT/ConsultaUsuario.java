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
 * Servlet implementation class ConsultaDocente
 */
@WebServlet("/ConsultaUsuario")
public class ConsultaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultaUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "perfil");
		String nick = request.getParameter("nickname");
		if (nick != null && !nick.isEmpty()) {
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoUsuario usuario = ws.seleccionarUsuario(nick);
			DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			//si no es el estudiante el que consulta le tengo que quitar las rechazadas 
			//par que no sean visibles por otros
			if(usuarioLogueado == null || !usuarioLogueado.getNickname().equals(nick)) {
				if(usuario.getTipo() == "Estudiante") {
					DtInfoEstudiante dt = (DtInfoEstudiante)usuario;
					if(dt.getInscripEdiciones() != null)
						dt.getInscripEdiciones().removeIf(i -> i.getEstado().equals("Rechazada"));
				}
			}
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("/WEB-INF/consultaUsuario.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Debe especificar un nickname");
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

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
 * Servlet implementation class ListarDesistirInscripcionCurso
 */
@WebServlet("/ListarDesistirInscripcionEdicionCurso")
public class ListarDesistirInscripcionEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarDesistirInscripcionEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
		if(usuarioLogueado != null && usuarioLogueado.getTipo().equals("Estudiante")) {
			//tomamos de persistencia al estudiante para que este actualizado
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//ya vienen ordenadas en orden descendente por fecha desde el servicio
			DtInfoEstudiante dt = (DtInfoEstudiante)ws.seleccionarUsuario(usuarioLogueado.getNickname());
			//quitamos las que no queremos, solo inscripto
			dt.getInscripEdiciones().removeIf(i -> i.getEstado().equals("Rechazada"));
			dt.getInscripEdiciones().removeIf(i -> i.getEstado().equals("Aceptada"));
			request.setAttribute("usuario", dt);
        	request.getRequestDispatcher("/WEB-INF/listarDesistirInscripcionEdicionCurso.jsp").forward(request, response);
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
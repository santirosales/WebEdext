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
 * Servlet implementation class ListarTramitarCertificadoProgramaFormacion
 */
@WebServlet("/ListarTramitarCertificadoProgramaFormacion")
public class ListarTramitarCertificadoProgramaFormacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarTramitarCertificadoProgramaFormacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		String error = "";
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			DtInfoEstudiante estudiante = (DtInfoEstudiante)ws.seleccionarUsuario(usuario.getNickname());
			if(estudiante == null)
				error = String.format("No se puede encontrar el estudiante %s.", usuario.getNickname());

			if(error.isEmpty()) {
				request.setAttribute("inscripciones", estudiante.getInscripProgramas());
			} else
				request.setAttribute("error", error);
			request.getRequestDispatcher("/WEB-INF/listarTramitarCertificadoProgramaFormacion.jsp").forward(request, response);
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

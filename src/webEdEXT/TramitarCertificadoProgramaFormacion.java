package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class TramitarCertificadoProgramaFormacion
 */
@WebServlet("/TramitarCertificadoProgramaFormacion")
public class TramitarCertificadoProgramaFormacion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TramitarCertificadoProgramaFormacion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		String error = "";
		String nombrePrograma = request.getParameter("programa");
		if(nombrePrograma != null && !nombrePrograma.isEmpty()) {
			DtInfoUsuario usuarioLogueado = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			if(usuarioLogueado != null && usuarioLogueado.getTipo().equals("Estudiante")) {
				//intento tramitarlo, si todo ok devuelve vacio, sino el error
				error = ws.tramitarCertificadoProgramaFormacion(usuarioLogueado.getNickname(), nombrePrograma);
			} else {
					request.setAttribute("error", "Debe estar logueado como Estudiante.");
					request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);			
			}
		} else {
			request.setAttribute("error", "Debe Proporcionar el nombre de un programa de formación.");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);			
		}
		request.setAttribute("programa", nombrePrograma);
		request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/tramitarCertificadoProgramaFormacion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
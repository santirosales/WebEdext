package webEdEXT;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;
import edEXT.server.DtInscripcionEdicion;
import edEXT.server.DtCursoEdicion;

/**
 * Servlet implementation class ListarEvaluaciones
 */
@WebServlet("/ListarEvaluaciones")
public class ListarEvaluaciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarEvaluaciones() {
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
			DtInfoEstudiante dt = (DtInfoEstudiante)ws.seleccionarUsuario(usuarioLogueado.getNickname());
			List<DtInscripcionEdicion> inscripciones = dt.getInscripEdiciones();
			//solo nos interesan las inscripciones aceptadas
			inscripciones.removeIf(i -> !i.getEstado().equals("Aceptada"));
			//le dejamos solo lo que puede ver que son las inscripciones a ediciones finalizadas
			Iterator<DtInscripcionEdicion> i = inscripciones.iterator();
			while (i.hasNext()) {
				DtInscripcionEdicion inscripcion = i.next();
				DtCursoEdicion edicion = ws.getDtInfoCursoEdicion(inscripcion.getEdicion());
				if(edicion != null && !edicion.isFinalizada())
					i.remove();
			}
			request.setAttribute("usuario", dt);
        	request.getRequestDispatcher("/WEB-INF/listarEvaluaciones.jsp").forward(request, response);
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
package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtInfoDocente;

/**
 * Servlet implementation class ListarAceptadosEdicionCurso
 */
@WebServlet("/ListarAceptadosEdicionCurso")
public class ListarAceptadosEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarAceptadosEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		String edicion = request.getParameter("edicion");
		String instituto = "";
		String curso = "";
		DtCursoEdicion edicionExiste = null;
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			//si estoy aca se que es docente y no es nulo
			DtInfoDocente usuario = (DtInfoDocente)request.getSession().getAttribute("usuarioLogueado");
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			instituto = usuario.getInstituto();
			List<DtCursoEdicion> ediciones = null;
			List<String> cursos = ws.listarCursosInstitutoString(instituto).getLista();
			//si viene una edicion cargo los datos acorde a ella, seteo instituto y curso para que se muestren seleccionados
			if(edicion != null && !edicion.isEmpty()) {
				edicionExiste = ws.getDtInfoCursoEdicion(edicion);
				if(edicionExiste != null) {
					//le quito los que son distintos de aceptada
					edicionExiste.getInscripciones().removeIf(i -> i.getEstado().equals("Rechazada"));
					edicionExiste.getInscripciones().removeIf(i -> i.getEstado().equals("Inscripto"));
					//verifico que la edicion sea del instituto del docente
					if(usuario.getInstituto().equals(edicionExiste.getInstituto())) {
						curso = edicionExiste.getCurso();
						ediciones = ws.seleccionarCurso(curso).getLista();
					} else {
						request.setAttribute("error", "El docente no pertenece al instituto del curso de la edición.");
						request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);											
					}
				}
			} else {
			}
			request.setAttribute("cursos", cursos);			
			request.setAttribute("curso", curso);			
			request.setAttribute("instituto", instituto);
			request.setAttribute("edicion", edicionExiste);
			request.setAttribute("ediciones", ediciones);			
			request.getRequestDispatcher("/WEB-INF/listarAceptadosEdicionCurso.jsp").forward(request, response);
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

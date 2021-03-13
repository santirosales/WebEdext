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
 * Servlet implementation class VerResultadosEdicionCurso
 */
@WebServlet("/VerResultadosEdicionCurso")
public class VerResultadosEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerResultadosEdicionCurso() {
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
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			String nickname = ((DtInfoDocente)request.getSession().getAttribute("usuarioLogueado")).getNickname();
			DtInfoDocente usuario = (DtInfoDocente)ws.seleccionarUsuario(nickname);
			//no todos los cursos del instituto del docente para que seleccione
			instituto = usuario.getInstituto();
			//dice los cursos asociados al instituto del docente
			List<String> cursos = ws.listarCursosInstitutoString(instituto).getLista();
			//si viene una edicion cargo los datos acorde a ella, seteo instituto y curso para que se muestren seleccionados
			if(edicion != null && !edicion.isEmpty()) {
				edicionExiste = ws.getDtInfoCursoEdicion(edicion);
				if(edicionExiste != null) {
					//verifico que la edicion sea del instituto del docente
					if(usuario.getInstituto().equals(edicionExiste.getInstituto())) {
						curso = edicionExiste.getCurso();
					} else {
						request.setAttribute("error", "El docente no pertenece al instituto del curso de la edición.");
						request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);											
					}
				}
			}
			request.setAttribute("cursos", cursos);			
			request.setAttribute("curso", curso);			
			request.setAttribute("instituto", instituto);			
			request.setAttribute("edicion", edicionExiste);
			request.getRequestDispatcher("/WEB-INF/verResultadosEdicionCurso.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

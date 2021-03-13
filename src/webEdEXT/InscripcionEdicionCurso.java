package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import edEXT.server.DtCursoEdicion;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class InscripcionEdicionCurso
 */
@WebServlet("/InscripcionEdicionCurso")
public class InscripcionEdicionCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscripcionEdicionCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		String edicion = request.getParameter("edicion");
		String instituto = "";
		String curso = "";
		DtCursoEdicion edicionExiste = null;
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			List<String> cursos = null;
			//si viene una edicion cargo los datos acorde a ella, seteo instituto y curso para que se muestren seleccionados
			if(edicion != null && !edicion.isEmpty()) {
				edicionExiste = ws.getDtInfoCursoEdicion(edicion);
				if(edicionExiste != null) {
					instituto = edicionExiste.getInstituto();
					curso = edicionExiste.getCurso();
					cursos = ws.listarCursosInstitutoString(instituto).getLista();
				}
			}
			List<String> institutos = ws.listarInstitutos().getLista();
			request.setAttribute("institutos", institutos);		
			List<String> categorias = ws.listarCategorias().getLista();
			request.setAttribute("categorias", categorias);
			request.setAttribute("cursos", cursos);			
			request.setAttribute("curso", curso);			
			request.setAttribute("instituto", instituto);			
			request.setAttribute("edicion", edicionExiste);
			request.getRequestDispatcher("/WEB-INF/inscripcionEdicionCurso.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "inscripciones");
		if(Utiles.puedeAcceder(request.getSession(), "Estudiante")) {
			String error = "";
			String curso = request.getParameter("curso");
			String instituto = request.getParameter("instituto");
			String categoria = request.getParameter("categoria");
			String edicion = request.getParameter("edicion");
			String urlVideo = request.getParameter("urlVideo");
			if(edicion == null || edicion.isEmpty())
				error = "Debe seleccionar una edición vigente.";
			DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
			if(usuario == null )
				error = "Debe loguearse para inscribirse.";
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//no hay error
			if(error.isEmpty()) {
				error = ws.inscribirEdicionCurso(edicion, usuario.getNickname(), Utiles.fechaXML(new Date()), urlVideo);
			}
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);		
				//relleno todo para que se cargue al volver
				List<String> institutos = ws.listarInstitutos().getLista();
				request.setAttribute("institutos", institutos);		
				List<String> categorias = ws.listarCategorias().getLista();
				request.setAttribute("categorias", categorias);
				List<String> cursos = null;
				if(instituto != null && !instituto.isEmpty()) 
					cursos = ws.listarCursosInstitutoString(instituto).getLista();
				else if(categoria != null && !categoria.isEmpty()) 
					cursos = ws.listarCursosCategoriaString(categoria).getLista();
				request.setAttribute("cursos", cursos);	
				request.setAttribute("instituto", instituto);
				request.setAttribute("categoria", categoria);
				DtCursoEdicion edicionExiste = ws.getDtInfoCursoEdicion(edicion);
				if(edicionExiste != null) {
					instituto = edicionExiste.getInstituto();
					curso = edicionExiste.getCurso();
					cursos = ws.listarCursosInstitutoString(instituto).getLista();
				}
				request.setAttribute("curso", curso);
				request.setAttribute("edicion", edicionExiste);
				request.setAttribute("urlVideo", urlVideo);
				request.getRequestDispatcher("/WEB-INF/inscripcionEdicionCurso.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

}

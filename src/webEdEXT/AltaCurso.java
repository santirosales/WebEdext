package webEdEXT;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import edEXT.server.DtInfoCurso;
import edEXT.server.DtListaString;


/**
 * Servlet implementation class AltaCurso
 */
@WebServlet("/AltaCurso")
public class AltaCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			request.setAttribute("seccion", "cursos");
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtListaString dt = ws.listarInstitutos();
			List<String> institutos = dt.getLista();
			request.setAttribute("institutos", institutos);		
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			DtListaString dtCategorias = ws.listarCategorias();	
			List<String> categorias = dtCategorias.getLista();
			request.setAttribute("categorias", categorias);		
			request.getRequestDispatcher("/WEB-INF/altaCurso.jsp").forward(request, response);
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		if(Utiles.puedeAcceder(request.getSession(), "Docente")) {
			String error = "";
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			String duracion = request.getParameter("duracion");
			String url = request.getParameter("url");
			String urlImagen = request.getParameter("urlImagen");
			String instituto = request.getParameter("instituto");
			String creditosStr = request.getParameter("creditos");
			String cantHorasStr = request.getParameter("cantidadHoras");
			String fechaStr = request.getParameter("fechaAlta");
			List<String> listaPrevias = null;
			if(request.getParameter("listaPrevias") != null)
				listaPrevias = Arrays.asList(request.getParameterValues("listaPrevias"));
			List<String> listaCategorias = null;
			if(request.getParameter("listaCategorias") != null)
				listaCategorias = Arrays.asList(request.getParameterValues("listaCategorias"));
			Date fecha = null;
			try {
				fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
			} catch (ParseException e) {
				error = String.format("No se puede convertir la fecha %s.", fechaStr);
			}
			final Date fechaDev = fecha;
			Integer creditos = 0;
			try {
				creditos = Integer.parseInt(creditosStr);
			} catch (NumberFormatException e) {
				error = String.format("No se puede convertir el valor %s.", creditosStr);
			}		
			Integer cantidadHoras = 0;
			try {
				cantidadHoras = Integer.parseInt(cantHorasStr);
			} catch (NumberFormatException e) {
				error = String.format("No se puede convertir el valor %s.", cantHorasStr);
			}		
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//no hay error
			if(error.isEmpty()) {
				error = ws.ingresarCurso(instituto, nombre, descripcion, duracion, 
										cantidadHoras, creditos, url, Utiles.fechaXML(fecha), urlImagen, 
										listaPrevias, listaCategorias);
			}
			//si no hay error lo mando al index
			if(error.isEmpty()) {
				response.sendRedirect("Index");
			} else {
				//seteo el error
				request.setAttribute("error", error);		
				DtInfoCurso dt = null;
				//relleno todo para que se cargue al volver
				dt = new DtInfoCurso();
				dt.setNombre(nombre);
				dt.setInstituto(instituto);
				dt.setDescripcion(descripcion);
				dt.setDuracion(duracion);
				dt.setCantidadHoras(cantidadHoras); 
				dt.setCreditos(creditos);
				dt.setUrl(url);
				dt.setFechaAlta(Utiles.fechaXML(fechaDev));
				dt.setUrlImagen(urlImagen);
				//agregar previas 
				//for(String s: listaPrevias) 
					//dt.getPrevias().add(s);
				//agregar previas 
				//for(String s: listaCategorias)
					//dt.getCategorias().add(s);
				request.setAttribute("curso", dt);
				DtListaString dtInstitutos = ws.listarInstitutos();
				List<String> institutos = dtInstitutos.getLista();
				request.setAttribute("institutos", institutos);
				DtListaString dtPrevias = ws.listarCursosInstitutoString(instituto);
				List<String> previas = dtPrevias.getLista();
				request.setAttribute("previas", previas);		
				DtListaString dtCategorias = ws.listarCategorias();
				List<String> categorias = dtCategorias.getLista();
				request.setAttribute("categorias", categorias);
				request.getRequestDispatcher("/WEB-INF/altaCurso.jsp").forward(request, response);
			}
		} else { //no puede acceder
			request.setAttribute("error", "Acceso denegado");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);					
		}
	}

}

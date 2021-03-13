package webEdEXT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class ModificarUsuario
 */
@WebServlet("/ModificarUsuario")
public class ModificarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "perfil");
		DtInfoUsuario usuario = (DtInfoUsuario)request.getSession().getAttribute("usuarioLogueado");
		//se modifica el usuario guardado en la sesion, si no esta mostramos error
		if(usuario == null) {
			request.setAttribute("error", "Debe iniciar sesión para modificar el usuario.");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);				
		}
		else {
			//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			//actualizamos el usuario en la sesion
			DtInfoUsuario usuario1 = ws.seleccionarUsuario(usuario.getNickname());
			request.getSession().setAttribute("usuarioLogueado", usuario1); 
			request.setAttribute("usuario", usuario1);	
			List<String> institutos = ws.listarInstitutos().getLista();
			request.setAttribute("institutos", institutos);		
	        request.getRequestDispatcher("/WEB-INF/modificarUsuario.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "perfil");
		String error = "";
		String nickname = request.getParameter("nickname");
		String urlFoto = request.getParameter("urlFoto");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String fechaStr = request.getParameter("fechaNacimiento");
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
		} catch (ParseException e) {
			error = String.format("No se puede convertir la fecha %s.", fechaStr);
		}
		String tipo = request.getParameter("tipo");
		if(tipo == null)
			tipo = "";
		String instituto = request.getParameter("instituto");
		
		//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		//no hay error
		if(error.isEmpty()) {
			switch (tipo) {
			case "Estudiante":
				error = ws.modificarEstudiante(nickname, nombre, apellido, Utiles.fechaXML(fecha), urlFoto);
				break;
			case "Docente":
				error = ws.modificarDocente(nickname, nombre, apellido, Utiles.fechaXML(fecha), urlFoto, instituto);
				break;
			default:
				error = "Debe especificar un tipo para el usuario.";
			}
		}
		//si no hay error lo mando al index
		if(error.isEmpty()) {
			//reseteo el usuario en la sesion
			DtInfoUsuario usuario = ws.seleccionarUsuario(nickname);
			request.getSession().setAttribute("usuarioLogueado", usuario);
			response.sendRedirect("Index");
		} else {
			//seteo el error
			request.setAttribute("error", error);		
			request.setAttribute("usuario", request.getSession().getAttribute("usuarioLogueado"));		
			List<String> institutos = ws.listarInstitutos().getLista();
			request.setAttribute("institutos", institutos);		
	        request.getRequestDispatcher("/WEB-INF/modificarUsuario.jsp").forward(request, response);
		}		
	}
}

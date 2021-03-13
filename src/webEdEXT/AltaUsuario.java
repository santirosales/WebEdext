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
import java.util.Date;
import edEXT.server.DtInfoDocente;
import edEXT.server.DtInfoEstudiante;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class AltaUsuario
 */
@WebServlet("/AltaUsuario")
public class AltaUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltaUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		List<String> institutos = ws.listarInstitutos().getLista();
		request.setAttribute("institutos", institutos);		
		request.getRequestDispatcher("/WEB-INF/altaUsuario.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = "";
		//request.setCharacterEncoding("UTF-8");
		String nickname = request.getParameter("nickname");
		String urlFoto = request.getParameter("urlFoto");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String email = request.getParameter("email");
		String clave = request.getParameter("clave");
		String claveConfirmar = request.getParameter("claveConfirmar");
		if(!clave.equals(claveConfirmar))
			error = "La clave ingresada y la confirmación no son iguales.";
		String fechaStr = request.getParameter("fechaNacimiento");
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
		} catch (ParseException e) {
			error = String.format("No se puede convertir la fecha %s.", fechaStr);
		}
		final Date fechaDevuelvo = fecha;
		String tipo = request.getParameter("tipo");
		String instituto = request.getParameter("instituto");
		
		//IUsuarioController uc = UsuarioControllerFactory.getInstance().getIUsuarioController();
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		//no hay error
		if(error.isEmpty()) {
			switch (tipo) {
			case "Estudiante":
				error = ws.altaEstudiante(nickname, nombre, apellido, email, Utiles.fechaXML(fecha), clave, urlFoto);
				break;
			case "Docente":
				error = ws.altaDocente(nickname, nombre, apellido, email, Utiles.fechaXML(fecha), clave, urlFoto, instituto);
				break;
			default:
				error = "Debe especificar un tipo para el usuario.";
			}
		}
		//si no hay error lo mando al index
		if(error.isEmpty()) {
			response.sendRedirect("Index");
		} else {
			//seteo el error
			request.setAttribute("error", error);		
			DtInfoUsuario u = null;
			//relleno todo para que se cargue al volver
			switch (tipo) {
			case "Estudiante":
				u = new DtInfoEstudiante();  
				u.setNickname(nickname);
				u.setNombre(nombre);
				u.setApellido(apellido);
				u.setCorreoElectronico(email);
				u.setFechaNacimiento(Utiles.fechaXML(fechaDevuelvo));
				u.setClave(clave);
				u.setUrlFoto(urlFoto);
				break;
			case "Docente":
				u = new DtInfoDocente();
				u.setNickname(nickname);
				u.setNombre(nombre);
				u.setApellido(apellido);
				u.setCorreoElectronico(email);
				u.setFechaNacimiento(Utiles.fechaXML(fechaDevuelvo));
				u.setClave(clave);
				u.setUrlFoto(urlFoto);
				((DtInfoDocente)u).setInstituto(instituto);
				break;
			default:
				error = "Debe especificar un tipo para el usuario.";
			}
			request.setAttribute("usuario", u);		
			List<String> institutos = ws.listarInstitutos().getLista();
			request.setAttribute("institutos", institutos);		
			request.getRequestDispatcher("/WEB-INF/altaUsuario.jsp").forward(request, response);
		}
	}

}

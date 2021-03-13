package webEdEXT;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import edEXT.controller.IUsuarioController;
//import edEXT.controller.UsuarioControllerFactory;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//para recordar el usuario en una cookie
		Cookie[] cookies = request.getCookies();
	    String usuarioRecordado = "";
		if (cookies != null) {		    
		    for (Cookie aCookie : cookies) {
		        if (aCookie.getName().equals("usuarioRecordado")) {
		            usuarioRecordado = aCookie.getValue();
		        }
		    }
		}
		request.setAttribute("nickname", usuarioRecordado);
		request.setAttribute("recordarme", !"".equals(usuarioRecordado));
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nick = request.getParameter("nickname");
		String password = request.getParameter("password");
		boolean recordarme = request.getParameter("recordarme") != null;
		edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
		//buscamos por nombre y sino por correo
		DtInfoUsuario usuario = ws.seleccionarUsuario(nick);
		//si no existe por nombre de usuario lo buscamos por correo
		if (usuario == null)
			usuario = ws.seleccionarUsuarioCorreo(nick);		
		if (usuario == null) {
			request.setAttribute("error", "Nombre de usuario o contraseña incorrectos.");
	        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);			
		} else {
			if (usuario.getClave().equals(password)) {
				//desde los jsp se puede acceder con ${usuarioLoguado}
				request.getSession().setAttribute("usuarioLogueado", usuario);
                
				//actualizamos la cookie de recordar el usuario
				if(recordarme) {
					Cookie cookieUsuario = new Cookie("usuarioRecordado", nick);
					cookieUsuario.setMaxAge(604800);
					response.addCookie(cookieUsuario);  
				} else {
					Cookie cookieUsuario = new Cookie("usuarioRecordado", "");
					cookieUsuario.setMaxAge(604800);
					response.addCookie(cookieUsuario);  					
				}
				//redirigimos al indice
				response.sendRedirect("Index");
			} else { //la contraseña es incorrecta
				request.setAttribute("error", "Nombre de usuario o contraseña incorrectos.");
		        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);							
			}
		}
	}
}

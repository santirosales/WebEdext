package webEdEXT;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import edEXT.server.DtInfoUsuario;

/**
 * Servlet implementation class JsonNicknameLibre
 */
@WebServlet("/JsonNicknameLibre")
public class JsonNicknameLibre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonNicknameLibre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname");
		Gson gson = new Gson();
		String salida = gson.toJson(false);
		if (nickname != null && !nickname.isEmpty()) {
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();		
			DtInfoUsuario usuario = ws.seleccionarUsuario(nickname);
			if(usuario == null)
				salida = gson.toJson(true);
		} else {
			salida = gson.toJson(false);			
		}
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(salida);
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

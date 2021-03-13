package webEdEXT;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edEXT.server.DtInfoCurso;

/**
 * Servlet implementation class JsonCursosCategoria
 */
@WebServlet("/JsonCursosCategoria")
public class JsonCursosCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonCursosCategoria() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoria = request.getParameter("categoria");
		if (categoria != null && !categoria.isEmpty()) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			List<DtInfoCurso> cursos = ws.listarCursosCategoria(categoria).getLista();
			//Gson gson = new Gson();
			//Gson gson = new Gson();
            GsonBuilder gson_builder = new GsonBuilder();
            gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                    new XMLGregorianCalendarConverter.Serializer());
            gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                    new XMLGregorianCalendarConverter.Deserializer());
            Gson gson = gson_builder.create();
			String salida = gson.toJson(cursos);
	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(salida);
	        out.flush();
		} else {
	        PrintWriter out = response.getWriter();
	        out.print("Debe especificar un instituto.");
	        out.flush();
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

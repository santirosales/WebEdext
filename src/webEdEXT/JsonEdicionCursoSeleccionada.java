package webEdEXT;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edEXT.server.DtCursoEdicion;

/**
 * Servlet implementation class JsonEdicionCursoSeleccionada
 */
@WebServlet("/JsonEdicionCursoSeleccionada")
public class JsonEdicionCursoSeleccionada extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonEdicionCursoSeleccionada() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String edicion = request.getParameter("edicion");
		if (edicion != null && !edicion.isEmpty()) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtCursoEdicion edicionSeleccionada = ws.getDtInfoCursoEdicion(edicion);
			if(edicionSeleccionada != null){
				edicionSeleccionada.getInscripciones().removeIf(i -> i.getEstado().equals("Rechazada"));
				edicionSeleccionada.getInscripciones().removeIf(i -> i.getEstado().equals("Inscripto"));
			}
			//Gson gson = new Gson();
            GsonBuilder gson_builder = new GsonBuilder();
            gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                    new XMLGregorianCalendarConverter.Serializer());
            gson_builder.registerTypeAdapter(XMLGregorianCalendar.class,
                    new XMLGregorianCalendarConverter.Deserializer());
            Gson gson = gson_builder.create();
			String salida = gson.toJson(edicionSeleccionada);
	        PrintWriter out = response.getWriter();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        out.print(salida);
	        out.flush();
		} else {
	        PrintWriter out = response.getWriter();
	        out.print("Debe especificar un curso.");
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

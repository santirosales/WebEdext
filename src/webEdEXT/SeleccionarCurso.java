package webEdEXT;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edEXT.server.DtInfoCurso;

/**
 * Servlet implementation class SeleccionarCurso
 */
@WebServlet("/SeleccionarCurso")
public class SeleccionarCurso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionarCurso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("seccion", "cursos");
		String nombre = request.getParameter("nombre");
		if (nombre != null && !nombre.isEmpty()) {
			//ICursoController cc = CursoControllerFactory.getInstance().getICursoController();
			edEXT.server.Servidor ws = WebServiceFactory.getInstance().getWS();
			DtInfoCurso curso = ws.getDtInfoCurso(nombre);
			long una = curso.getPuntajes().stream().filter(p -> p.getValor() == 1).count();
			long dos = curso.getPuntajes().stream().filter(p -> p.getValor() == 2).count();
			long tres = curso.getPuntajes().stream().filter(p -> p.getValor() == 3).count();
			long cuatro = curso.getPuntajes().stream().filter(p -> p.getValor() == 4).count();
			long cinco = curso.getPuntajes().stream().filter(p -> p.getValor() == 5).count();
			//obtengo el maximo, va a ser el 100%
			long arr[] = {una, dos, tres, cuatro, cinco};
			long max = Arrays.stream(arr).max().orElse(0);
			//por si todos son ceros
			if(max == 0)
				max = 1;
			long unaPorc = una*100/max;
			long dosPorc = dos*100/max;
			long tresPorc = tres*100/max;
			long cuatroPorc = cuatro*100/max;
			long cincoPorc = cinco*100/max;
			request.setAttribute("una", una);
			request.setAttribute("dos", dos);
			request.setAttribute("tres", tres);
			request.setAttribute("cuatro", cuatro);
			request.setAttribute("cinco", cinco);
			request.setAttribute("curso", curso);
			request.setAttribute("unaPorc", unaPorc);
			request.setAttribute("dosPorc", dosPorc);
			request.setAttribute("tresPorc", tresPorc);
			request.setAttribute("cuatroPorc", cuatroPorc);
			request.setAttribute("cincoPorc", cincoPorc);
			request.setAttribute("curso", curso);
			request.getRequestDispatcher("/WEB-INF/seleccionarCurso.jsp").forward(request, response);
		} else {
			request.setAttribute("error", "Debe especificar un nombre");
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

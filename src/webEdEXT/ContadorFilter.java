package webEdEXT;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public class ContadorFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
    	//tomamos el nombre del servlet
    	String nombreServlet = ((HttpServletRequest)request).getServletPath();
    	if("/SeleccionarCurso".equals(nombreServlet)) {
        	String nombreCurso = ((HttpServletRequest)request).getParameter("nombre");
        	if(nombreCurso != null && !nombreCurso.isEmpty()) {
            	System.out.println(String.format("INFORMACIÓN: servlet: %s actualizando visitas curso: %s", nombreServlet, nombreCurso));
        		WebServiceFactory.getInstance().getWS().incrementarVisitaCurso(nombreCurso);
        	}
    	}
        next.doFilter(request, response);
    }

    public void destroy() {
    }
}
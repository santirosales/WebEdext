<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:plantilla>
    <jsp:body>
         <h2>Página principal</h2>
         <p> 
         	<c:if test="${not empty usuarioLogueado}">
        		Bienvenid@: ${usuarioLogueado.getNombre()}
     		</c:if>
         </p>                   
    </jsp:body>
</t:plantilla>
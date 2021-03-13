<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:plantilla>
    <jsp:body>
        <h4>Desistir de una inscripción a edición de curso</h4>
        <hr />
        <br />
        <div class="form-group table-responsive">
            <table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th>Nombre</th>
	                  <th>Fecha</th>
	                  <th>Estado</th>
	                  <th></th>
	                </tr>
	              </thead>
	              <tbody>
					<c:if test="${not empty usuario.getInscripEdiciones()}">
						<c:forEach items="${usuario.getInscripEdiciones()}" var="ed" >
		               		<tr>
				                <td><a class="link" href="ConsultaEdicionCurso?nombre=${fn:escapeXml(ed.getEdicion())}">${ed.getEdicion()}</a></td>
			                  	<td> <fmt:formatDate value="${ed.getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy"/> </td>
			                  	<td> ${ed.getEstado()} </td>
			                  	<td><a class="btn btn-info btn-sm float-right btnQuitar" role="button" href="DesistirInscripcionEdicionCurso?edicion=${fn:escapeXml(ed.getEdicion())}&nickname=${usuario.getNickname()}"><i class="fas fa-trash"></i></a></td>
			                </tr>
						</c:forEach>
            		</c:if>
					<c:if test="${empty usuario.getInscripEdiciones()}">
			                <tr>
			                  <td>No tiene inscripciones en estado inscripto...</td>
			                  <td></td>
			                  <td></td>
			                  <td></td>
			                </tr>
            		</c:if>
	              </tbody>
	            </table> 
        </div>
		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-10">
		  </div>
		</div>
    </jsp:body>
</t:plantilla>

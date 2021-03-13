<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
         <h4>Seleccionar Programa de Formación</h4>
         <hr />
         <br />
        <div class="form-group">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Nombre</th>
                </tr>
              </thead>
              <tbody>
				<c:if test="${not empty programas}">
					<c:forEach items="${programas}" var="prog" >                       
		              <tr>
		                <td><a class="link" href="ConsultaProgramaFormacion?nombre=${fn:escapeXml(prog)}">${prog}</a></td>
		              </tr>
					</c:forEach>
	       		</c:if>
				<c:if test="${empty programas}">
		              <tr>
		                <td>No hay programas de formación...</td>
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
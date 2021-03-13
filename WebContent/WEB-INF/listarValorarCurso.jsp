<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
         <h4>Valorar Curso de ${usuarioLogueado.getNickname()}</h4>
         <hr />
         <br />
        <div class="form-group">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Curso</th>
                  <th>Valoración promedio</th>
                </tr>
              </thead>
              <tbody>
	            <fmt:setLocale value="en_US"/>
				<c:if test="${not empty cursos}">
					<c:forEach items="${cursos}" var="curso" >                       
		              <tr>
		                <td><a class="link" href="ValorarCurso?curso=${fn:escapeXml(curso.getNombre())}">${curso.getNombre()}</a></td>
		 	            <c:set var="promedio"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${curso.getPuntaje()}" /></c:set>
		                <td><div class="rate" data-rate-value="${promedio}" title="${promedio}"></div></td>
		              </tr>
					</c:forEach>
	       		</c:if>
				<c:if test="${empty cursos}">
		              <tr>
		                <td>El usuario no participa en ningún curso...</td>
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
    <script>
        $(document).ready(function(){
            var options = {
                readonly: true,
                max_value: 5,
                step_size: 0.1
            }
            $(".rate").rate(options);
        });       
    </script>      
    </jsp:body>
</t:plantilla>
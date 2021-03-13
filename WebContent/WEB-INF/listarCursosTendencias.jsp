<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:plantilla>
    <jsp:body>
        <h4>Tendencias</h4>
        <hr />
        <br />
        <div class="form-group table-responsive">
            <table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th>Imagen</th>
	                  <th>Nombre</th>
	                  <th>Valoración</th>
	                  <th>Visitas</th>
	                </tr>
	              </thead>
	              <tbody>
	                <fmt:setLocale value="en_US"/>
					<c:forEach items="${cursos}" var="curso" >
	               		<tr>
		                  	<td>
		                  	<c:if test="${curso.getUrlImagen() != ''}"> 
		                  		<img src="${curso.getUrlImagen() != '' ? curso.getUrlImagen() : nophoto}" style="height: 64px;" class="img-thumbnail"class="img-fluid" alt="Imagen del curso">
            				</c:if>
		                  	</td>
			                <td><a class="link" href="SeleccionarCurso?nombre=${fn:escapeXml(curso.getNombre())}">${curso.getNombre()}</a></td>
			 	            <c:set var="promedio"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${curso.getPuntaje()}" /></c:set>			                
		                  	<td><div class="rate" data-rate-value="${promedio}" title="${promedio}"></div></td>	                  	
		                  	<td>${curso.getVisitas()}</td>
		                </tr>
					</c:forEach>
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

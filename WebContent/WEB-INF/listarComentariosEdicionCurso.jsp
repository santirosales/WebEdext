<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:plantilla>
    <jsp:body>
        <h4>Comentarios de ${edicion.getNombre()}</h4>
        <hr />
        <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<div class="form-group row">
          	<div class="col-sm-offset-2 col-sm-10">
				<a class="btn btn-info" role="button" href="AgregarComentarioEdicionCurso?edicion=${fn:escapeXml(edicion.getNombre())}">Agregar comentario</a>
			</div>
        </div>		   
        <div class="form-group table-responsive">
            <table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th>Tipo</th>
	                  <th>Nickname</th>
	                  <th>Comentario</th>
	                  <th>Fecha</th>
	                  <th>Valoración</th>
	                  <th></th>
	                </tr>
	              </thead>
	              <tbody>
					<c:if test="${not empty comentarios}">
						<c:forEach items="${comentarios}" var="comentario" >
		               		<tr>
		               			<td><div class="text-center">${comentario.getComentario().getIdPadre() == -1 ? "<i class='fas fa-comment'></i>" : ""}</div></td>
				                <td>${comentario.getComentario().getEstudiante()}</td>
				                <td>${comentario.getComentario().getTexto()}</td>
			                  	<td><fmt:formatDate value="${comentario.getComentario().getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
		 	            		<td><div class="rate" data-rate-value="${comentario.getPuntaje().getValor()}"></div></td>
			                  	<td><a class="btn btn-info" role="button" href="ResponderComentarioEdicionCurso?padreId=${comentario.getComentario().getId()}&edicion=${fn:escapeXml(comentario.getComentario().getEdicion())}">Responder</a></td>			                  	
			                </tr>
							<c:forEach items="${comentario.getRespuestas()}" var="repuesta" >
			               		<tr>
			               			<td><div class="float-right">${repuesta.getComentario().getIdPadre() == -1 ? "" : "<i class='fas fa-forward'></i>"}</div></td>
					                <td>${repuesta.getComentario().getEstudiante()}</td>
					                <td>${repuesta.getComentario().getTexto()}</td>
				                  	<td><fmt:formatDate value="${repuesta.getComentario().getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
			 	            		<td><div class="rate" data-rate-value="${repuesta.getPuntaje().getValor()}"></div></td>
		                			<td></td>			                  	
				                </tr>
							</c:forEach>
						</c:forEach>
            		</c:if>
					<c:if test="${empty edicion.getComentarios()}">
			                <tr>
			                  <td>No hay comentarios...</td>
			                  <td></td>
			                  <td></td>
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

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>${curso.getNombre()}</h4>
    	 <div class="form-group row">
            <div class="col-md-8 row">
               <div class="col-md-3">
	 	            <c:set var="nophoto" value="${pageContext.request.contextPath}/imagenes/no-image.png" />
	                <img src="${curso.getUrlImagen() != '' ? curso.getUrlImagen() : nophoto}" class="img-fluid" alt="Imagen del curso">
               </div>
               <div class="col-md-9">
					<fmt:setLocale value="en_US"/>				
					<div class="row ">
						<div class="col-sm-8">
			               <table class="table table-sm table-borderless table-condensed">
			                  <thead>
			                   <tr>
			                     <th></th>
			                     <th></th>
			                   </tr>
			                  </thead>
			                  <tbody>
			                     <tr>
			                        <td style="width: 60px;"><div class="rate rate-barra" data-rate-value="5"></div></td>
			                        <td><div class="barra" style="width: ${cincoPorc}%;">${cinco}</div></td>
			                     </tr>
			                     <tr>
			                        <td><div class="rate rate-barra" data-rate-value="4"></div></td>
			                        <td><div class="barra" style="width: ${cuatroPorc}%;">${cuatro}</div></td>
			                     </tr>
			                     <tr>
			                        <td><div class="rate rate-barra" data-rate-value="3"></div></td>
			                        <td><div class="barra" style="width: ${tresPorc}%;">${tres}</div></td>
			                     </tr>
			                     <tr>
			                        <td><div class="rate rate-barra" data-rate-value="2"></div></td>
			                        <td><div class="barra" style="width: ${dosPorc}%;">${dos}</div></td>
			                     </tr>
			                     <tr>
			                        <td><div class="rate rate-barra" data-rate-value="1"></div></td>
			                        <td><div class="barra" style="width: ${unaPorc}%;">${una}</div></td>
			                     </tr>
			                  </tbody>
			               </table>
						</div>
						<div class="col-sm-4" style="display: inline-block!important;">
							<div class=" justify-content-center align-items-center">
						    <div class="d-flex justify-content-center text-center">
						       		<span>Valoración promedio</span>
							</div>
						  	<c:set var="promedio"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${curso.getPuntaje()}" /></c:set>
					   		<div class="d-flex justify-content-center" style="font-size: 2rem;">${promedio}</div>
					   		<div class="d-flex justify-content-center"><div class="rate" data-rate-value="${promedio}"></div></div>
					   		<div class="d-flex justify-content-center"><span>${curso.getPuntajes().size()}</span></div></div>
						</div>	
					</div>               
               </div>
               <div class="col-md-12">
                  <!--textarea class="form-control" id="nombre" name="nombre" readonly rows="2">${fn:escapeXml(curso.getNombre())}</textarea-->
                  <input type="text" class="form-control" name="instituto" id="instituto" required maxlength="45" readonly value="${curso.getInstituto()}">
               </div>
               <div class="col-md-12" style="overflow: hidden;">
                  <a class="" href="${curso.getUrl()}">${fn:substring(curso.getUrl(), 0, 128)}</a>
                  <p>
					${curso.getDescripcion()}
                  </p>               
               </div>
               <div class="col-md-12">
               		<button id="btnValoraciones" type="button" class="btn btn-info">Mostrar valoraciones</button>
               </div>               
               <div id="valoraciones" class="col-md-12" style="display:none;">
                  <div class="card">
                    <div class="card-body table-responsive">                  
                      <div class=""><strong>Valoraciones</strong></div>
					   <table class="table table-bordered table-striped">
		                  <thead>
		                   <tr>
		                     <th>Estudiante</th>
		                     <th>Puntaje</th>
		                   </tr>
		                  </thead>
		                  <tbody>
							<c:if test="${not empty curso.getPuntajes()}">
								<c:forEach items="${curso.getPuntajes()}" var="puntaje" >
						                <tr>
						                  <td>${puntaje.getEstudiante()}</td>
										  <c:set var="puntos"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${puntaje.getValor()}" /></c:set>
						                  <td title="${puntos}"><div class="rate" data-rate-value="${puntaje.getValor()}"></div></td>
						                </tr>
								</c:forEach>
		            		</c:if>
							<c:if test="${empty curso.getPuntajes()}">
					                <tr>
					                  <td>No tiene...</td>
					                  <td></td>
					                </tr>
		            		</c:if>
		                  </tbody>
		               </table>
                    </div>
                  </div>
                </div>
            </div>
            <div class="col-md-4 row table-responsive">
               <table class="table table-bordered table-striped">
                  <thead>
                   <tr>
                     <th>Ediciones del curso</th>
                   </tr>
                  </thead>
                  <tbody>
					<c:if test="${not empty curso.getEdiciones()}">
						<c:forEach items="${curso.getEdiciones()}" var="edicion" >
				                <tr>
				                  <td><a class="link" href="ConsultaEdicionCurso?nombre=${fn:escapeXml(edicion.getNombre())}">${edicion.getNombre()}</a></td>
				                </tr>
						</c:forEach>
            		</c:if>
					<c:if test="${empty curso.getEdiciones()}">
			                <tr>
			                  <td>No tiene...</td>
			                </tr>
            		</c:if>
                  </tbody>
               </table>
			   <table class="table table-bordered table-striped">
                  <thead>
                   <tr>
                     <th>Programas de formación</th>
                   </tr>
                  </thead>
                  <tbody>
					<c:if test="${not empty curso.getProgramas()}">
						<c:forEach items="${curso.getProgramas()}" var="programa" >
				                <tr>
				                  <td><a class="link" href="ConsultaProgramaFormacion?nombre=${fn:escapeXml(programa.getNombre())}">${programa.getNombre()}</a></td>
				                </tr>
						</c:forEach>
            		</c:if>
					<c:if test="${empty curso.getProgramas()}">
			                <tr>
			                  <td>No tiene...</td>
			                </tr>
            		</c:if>
                  </tbody>
               </table>
            </div>
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
            
            $("#btnValoraciones").click(function() {
	   			 if($("#valoraciones").is(":visible")) {
		       		$("#valoraciones").hide();
		       		$("#btnValoraciones").html('Mostrar valoraciones');
	   			 } else {
				    $("#valoraciones").show();
		       		$("#btnValoraciones").html('Ocultar valoraciones');
	   			 }
   			});
        });       
    </script>
    <style>
   	.barra 
   	{
   		font: 10px sans-serif; 
   		text-align: right;
   		background: steelblue; 
   		padding-right: 6px; 
   		padding-top: 3px; 
   		padding-bottom: 3px; 
   		margin: 1px; 
   		color: white; 
   	}
   	.leyenda-barra 
   	{
   		font: 10px sans-serif; 
   		text-align: right;
   		padding-right: 6px; 
   		padding-top: 3px; 
   		padding-bottom: 3px; 
   		margin: 1px; 
   	}
   	.rate-barra 
   	{
   		font: 14px sans-serif; 
   		text-align: right;
   	}
    </style>
    </jsp:body>
</t:plantilla>
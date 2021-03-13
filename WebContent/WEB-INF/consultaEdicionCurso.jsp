<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>${edicion.getNombre()}</h4>    
		<div class="">
            <div class="row">
               <div class="col-md-2">
	 	            <c:set var="nophoto" value="${pageContext.request.contextPath}/imagenes/no-image.png" />
	                <img src="${edicion.getUrlImagen() != '' ? edicion.getUrlImagen() : nophoto}" class="img-fluid" alt="Imagen de la edición">
               </div>
               <div class="col-md-10">
                  <!--input type="text" class="form-control" name="nombre" id="nombre" maxlength="45" readonly value="${fn:escapeXml(edicion.getNombre())}"-->
                  <input type="text" class="form-control" name="instituto" id="instituto" maxlength="45" readonly value="${edicion.getInstituto()}">
                  <div class="">
					<c:if test="${not empty usuarioLogueado}">
						<c:if test="${usuarioLogueado.getTipo() =='Estudiante' && edicion.isVigente()}">
	                   	 <a class="link" href="InscripcionEdicionCurso?edicion=${fn:escapeXml(edicion.getNombre())}">Inscribirme</a>
	            		</c:if>
            		</c:if>
					<c:if test="${!edicion.isVigente()}">
                   	 <span>La edición del curso no se encuentra vigente</span>
            		</c:if>
                  </div>
                  <div class="">
                     <a class="link" href="SeleccionarCurso?nombre=${fn:escapeXml(edicion.getCurso())}">Ver información del curso</a>
                  </div>
               </div>
            </div>
            <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaInicio">Fecha de inicio:</label>
             <div class="col-sm-10">
				<fmt:formatDate value="${edicion.getFechaInicio().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaInicio"/>
               	<input type="date" class="form-control" name="fechaInicio" id="fechaInicio" readonly value="${fechaInicio}">
             </div>
           </div>
            <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaFin">Fecha de fin:</label>
             <div class="col-sm-10">
				<fmt:formatDate value="${edicion.getFechaFin().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaFin"/>
               	<input type="date" class="form-control" name="fechaFin" id="fechaFin" readonly value="${fechaFin}">
             </div>
           </div>
            <div class="form-group row">
             <label class="control-label col-sm-2" for="cupo">Cupo definido:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="cupo" id="cupo" maxlength="5" readonly value="${edicion.getCupo() == -1 ? 'Sin definir' : edicion.getCupo()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaAlta">Fecha publicación:</label>
             <div class="col-sm-10">
				<fmt:formatDate value="${edicion.getFechaRegistro().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaAlta"/>
               	<input type="date" class="form-control" name="fechaAlta" id="fechaAlta" readonly value="${fechaAlta}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="finalizada">Finalizada:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="finalizada" id="cupo" readonly value="${edicion.isFinalizada() ? 'La edición ha sido finalizada' : ''}">
	         </div>
           </div>           
            <div class="form-group">
              <div class="row">
                <div class="col-md-12">
                  <div class="card">
                    <div class="card-body table-responsive">                  
                      <div class=""><strong>Docentes</strong></div>
                      <table class="table table-bordered table-striped">
                        <thead>
                          <tr>
							<th>Nickname</th>
							<th>Nombre</th>
							<th>Correo</th>
                          </tr>
                        </thead>
                        <tbody>
						<c:if test="${not empty edicion.getDocentes()}">
							<c:forEach items="${edicion.getDocentes()}" var="doc" >
					                <tr>
					                  <td><a class="link" href="ConsultaUsuario?nickname=${doc.getNickname()}">${doc.getNickname()}</a></td>
					                  <td>${doc.getNombre()} ${doc.getApellido()}</td>
					                  <td>${doc.getCorreoElectronico()}</td>
					                </tr>
							</c:forEach>
	            		</c:if>
						<c:if test="${empty edicion.getDictadoPor()}">
				                <tr>
				                  <td>No tiene...</td>
				                </tr>
	            		</c:if>
                        </tbody>
                      </table>                  
                    </div>
                  </div>
                </div>
              </div>
            <div class="form-group">
    	    	<div class="row">
	               <div class="col-md-12">
	               		<button id="btnComentarios" type="button" class="btn btn-info">Mostrar comentarios</button>
	               </div>               
	               <div id="comentarios" class="col-md-12" style="display:none;">
	                  <div class="card">
	                    <div class="card-body table-responsive">                  
	                      <div class=""><strong>Comentarios</strong></div>
						   <table class="table table-bordered table-striped">
			                  <thead>
			                   <tr>
			                     <th>Tipo</th>
			                     <th>Estudiante</th>
			                     <th>Comentario</th>
			                     <th>Fecha</th>
			                   </tr>
			                  </thead>
			                  <tbody>
								<c:if test="${not empty edicion.getComentarios()}">
									<c:forEach items="${edicion.getComentarios()}" var="comentario" >
							                <tr>
							                  <td><div class="text-center"><i class="fas fa-comment"></i></div></td>
							                  <td>${comentario.getEstudiante()}</td>
							                  <td>${comentario.getTexto()}</td>
							                  <td><fmt:formatDate value="${comentario.getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
							                </tr>
										<c:forEach items="${comentario.getRespuestas()}" var="respuesta" >
								                <tr>
							                  	  <td><div class="float-right"><i class="fas fa-forward"></i></div></td>
								                  <td>${respuesta.getEstudiante()}</td>
								                  <td>${respuesta.getTexto()}</td>
							                  	  <td><fmt:formatDate value="${respuesta.getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
								                </tr>
										</c:forEach>
									</c:forEach>
			            		</c:if>
								<c:if test="${empty edicion.getComentarios()}">
						                <tr>
						                  <td>No tiene...</td>
						                  <td></td>
						                  <td></td>
						                  <td></td>
						                </tr>
			            		</c:if>
			                  </tbody>
			               </table>
	                    </div>
	                  </div>
	                </div>    	    	
	            </div>
            </div>
              
            </div>
        </div>
    <script>
        $(document).ready(function(){
            $("#btnComentarios").click(function() {
	   			 if($("#comentarios").is(":visible")) {
		       		$("#comentarios").hide();
		       		$("#btnComentarios").html('Mostrar comentarios');
	   			 } else {
				    $("#comentarios").show();
		       		$("#btnComentarios").html('Ocultar comentarios');
	   			 }
   			});
        });       
    </script>        
    </jsp:body>
</t:plantilla>	

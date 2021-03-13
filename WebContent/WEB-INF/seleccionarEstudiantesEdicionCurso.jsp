<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Seleccionar Estudiantes Edición de Curso</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="SeleccionarEstudiantesEdicionCurso" method="post" autocomplete="off">
           <div class="form-group row">
             <label class="control-label col-sm-2" for="instituto">Instituto:</label>
             <div class="col-sm-10">
				<input type="text" class="form-control" name="institutoVer" id="institutoVer" maxlength="45" readonly value="${instituto}">
				<input type="hidden" name="instituto" id="instituto" value="${instituto}">
             </div>
           </div>
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="curso">Curso:</label>
             <div class="col-sm-10">
                <select class="form-control" name="curso" id="curso">
            		<option value="" ${curso == '' ? 'selected' : ''}>(Seleccione un curso)</option>
				<c:forEach items="${cursos}" var="cur" >
	            	<option value="${fn:escapeXml(cur)}" ${curso == cur ? 'selected' : ''}>${cur}</option>
				</c:forEach>
                </select>
             </div>
           </div>           
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nombreEdicion">Nombre:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nombreEdicion" id="nombreEdicion" readonly maxlength="80" value="${fn:escapeXml(edicion.getNombre())}">
               <input type="hidden" name="edicion" id="edicion" value="${fn:escapeXml(edicion.getNombre())}">
             </div>
           </div>
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaInicio">Fecha de inicio:</label>
             <div class="col-sm-10">
	  	   	   <fmt:formatDate value="${edicion.getFechaInicio().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaInicio" />         
               <input type="date" class="form-control" name="fechaInicio" id="fechaInicio" readonly value="${fechaInicio}">
             </div>
           </div>
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaFin">Fecha de fin:</label>
             <div class="col-sm-10">
	  	   	   <fmt:formatDate value="${edicion.getFechaFin().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaFin" />         
               <input type="date" class="form-control" name="fechaFin" id="fechaFin" readonly value="${fechaFin}">
             </div>
           </div>
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="cupo">Cupos definidos:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="cupo" id="cupo" readonly maxlength="5" value="${edicion.getCupo() == -1 ? 'Sin definir' : edicion.getCupo()}">
             </div>
           </div>
            <div class="form-group">
              <div class="row">
                <div class="col-md-6">
                  <div class="card">
                    <div class="card-body">                  
                      <div class=""><strong>Seleccione los estudiantes</strong></div>
                      <table id="tablaInscripciones" class="table table-bordered table-striped">
                        <thead>
                          <tr>
							<th>Estudiante</th>
							<th>Estado</th>
							<th>Video</th>
                          </tr>
                        </thead>
                        <tbody>
						<c:if test="${not empty edicion.inscripciones}">
							<c:forEach items="${edicion.inscripciones}" var="ins" >
					            <tr>
					                  <td><input name="listaInscripciones" type="hidden" value="${ins.getEstudiante().getNickname()}">${ins.getEstudiante().getNombre()} ${ins.getEstudiante().getApellido()}</td>
									  <td><select class='form-control form-control-sm inscripcionEstado' name='listaEstados' ${edicion.finalizada ? 'disabled' : ''}>
						  			  <option ${ins.estado == 'Aceptada' ? 'selected' : ''}>Aceptada</option>
						  			  <option ${ins.estado == 'Rechazada' ? 'selected' : ''}>Rechazada</option>						  			  
									  </select></td>
					                  <td>
										<c:if test="${ins.getUrlVideo() != ''}">
						                  	<button type="button" id="btnVideo" class="btn btn-sm btn-info btnVideo" data-nickname="${ins.getEstudiante().getNickname()}" data-video="${ins.getUrlVideo()}"><i class="fas fa-video"></i></button>
					            		</c:if>
					                  </td>									  
								</tr>
							</c:forEach>
	            		</c:if>
						<c:if test="${empty edicion.inscripciones}">
				                <tr>
				                  <td>No hay inscripciones...</td>
				                  <td></td>
				                  <td></td>				                  
				                </tr>
	            		</c:if>
                        </tbody>
                      </table>                  
                    </div>
                  </div>
		           <div class="form-group row">
		             <div class="col-sm-offset-4 col-sm-8">
		               <button type="submit" class="btn btn-info">Confirmar inscripción</button>
		             </div>
		           </div>		                     
                </div>
                <div class="col-md-6">
                  <div class="card">
                    <div id="tituloVideo" class="card-header">
                    </div>
                    <div class="card-body">                  
						<div id="videoPlayer" class="embed-responsive embed-responsive-16by9">
							<iframe id="iframeVideo" class="embed-responsive-item" src=""></iframe>						  
						</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
         </form>
	<script type="text/javascript">	
	$(function() {
		$('select[name="curso"]').on('change', function() {
		    var curso = $(this).val();
		    if(curso) {
				$.getJSON('JsonCursoEdicionVigente', { 'curso': curso }, function(data) {
					//alert(data.nombre);
					if(data) {
						$("#nombreEdicion").val(data.nombre);
						$("#edicion").val(data.nombre);
						var fIni = new Date(data.fechaInicio);
						$("#fechaInicio").val(fIni.getFullYear() + '-' + ("0" + (fIni.getMonth() + 1)).slice(-2) + "-" +  ("0" + fIni.getDate()).slice(-2));
						var fFin = new Date(data.fechaFin);
						$("#fechaFin").val(fFin.getFullYear() + '-' + ("0" + (fFin.getMonth() + 1)).slice(-2) + "-" +  ("0" + fFin.getDate()).slice(-2));
						$("#cupo").val(data.cupo == -1 ? 'Sin definir' : data.cupo);
	
						//antes de agregar la limpio
						$("#tablaInscripciones > tbody").html("");
						$("#tituloVideo").html("");
						$("#iframeVideo").attr('src', "")					
						$.each(data.inscripciones, function(indice, inscripcion) {
					    	//alert(indice + ": " + inscripcion.estudiante.nombre);
					    	var strBoton = "";
					    	if(inscripcion.urlVideo)
						    	strBoton = "<button type='button' id='btnVideo' class='btn btn-sm btn-info btnVideo' data-nickname='" + inscripcion.estudiante.nickname + "' data-video='" + inscripcion.urlVideo + "' ><i class='fas fa-video'></i></button>";
					    	var $row = "<tr><td><input name='listaInscripciones' type='hidden' value='" + inscripcion.estudiante.nickname + "'/>" + 
					    	inscripcion.estudiante.nombre + " " + inscripcion.estudiante.apellido + "</td>" +
							"<td><select class='form-control form-control-sm inscripcionEstado' name='listaEstados' " + (data.finalizada ? "disabled" : "") + " data-nick=" + inscripcion.estudiante.nickname + ">" +
							"  <option " + (inscripcion.estado == 'Aceptada' ? 'selected' : '') + ">Aceptada</option>" + 
							"  <option " + (inscripcion.estado == 'Rechazada' ? 'selected' : '') + ">Rechazada</option>" +
							"</select></td>" +
							"<td>" + strBoton + "</td>";
			    			$("#tablaInscripciones tbody").append($row);				    	
					    });
						//la edicion esta como data
						if(data.finalizada)
							$("#error").html("La edición del curso se encuentra finalizada.");
						else
							$("#error").html("");						
					} else {
						$("#error").html("No hay una edición vigente para el curso.");
						limpiarEdicion();
					}
				});
		    } else {
		    	limpiarEdicion();
		    }
		});
/*		$("#tablaInscripciones").on('change', '.inscripcionEstado', function(e){
			e.preventDefault();
		    var nick = $(this).data("nick");
		    var estado = $(this).val();
		    alert(nick + ":" + estado);
		});
*/
	});
	function limpiarEdicion() {
		$("#nombreEdicion").val('');
		$("#edicion").val('');
		//en firefox no se borra si es readonly
		$("#fechaInicio").attr("readonly", false).val('').attr("readonly", true);
		$("#fechaFin").attr("readonly", false).val('').attr("readonly", true);;
		$("#cupo").val('');
		$("#tablaInscripciones > tbody").html("");
		$("#tituloVideo").html("");
		$("#iframeVideo").attr('src', "")
	};
	//cargar el video con el click en el boton
	$("#tablaInscripciones").on('click','.btnVideo',function(){
		//alert($(this).data('video'));
		var nickname = $(this).data('nickname');
		$("#tituloVideo").html("Video de: " + nickname);
		var url = $(this).data('video');
		$("#iframeVideo").attr('src', url)
	});
		</script>          
    </jsp:body>
</t:plantilla>
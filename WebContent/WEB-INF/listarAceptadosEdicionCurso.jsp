<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Listar Estudiantes Aceptados Edición de Curso</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="ListarAceptadosEdicionCurso" method="post" autocomplete="off">
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
             <label class="control-label col-sm-2" for="edicion">Edición:</label>
             <div class="col-sm-10">
                <select class="form-control" name="edicion" id="edicion">
            		<option value="" ${edicion == '' ? 'selected' : ''}>(Seleccione una edicion)</option>
				<c:forEach items="${ediciones}" var="ed" >
	            	<option value="${fn:escapeXml(ed.getNombre())}" ${edicion.getNombre() == ed.getNombre() ? 'selected' : ''}>${ed.getNombre()}</option>
				</c:forEach>
                </select>
             </div>
           </div>              
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nombreEdicion">Nombre:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nombreEdicion" id="nombreEdicion" readonly maxlength="80" value="${edicion.getNombre()}">
               <input type="hidden" name="nombreEdicion" id="nombreEdicion" value="${edicion.getNombre()}">
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
                      <div class=""><strong>Aceptados a esta edición</strong></div>
                      <table id="tablaInscripciones" class="table table-bordered table-striped">
                        <thead>
                          <tr>
							 <th>Nombre</th>
	                 		 <th>Estado</th>
                          </tr>
                        </thead>
                        <tbody>
						<c:if test="${not empty edicion.inscripciones}">
							<c:forEach items="${edicion.inscripciones}" var="ins" >
					            <tr>
					                <td><input name="listaInscripciones" type="hidden" value="${ins.getEstudiante().getNickname()}">${ins.getEstudiante().getNombre()} ${ins.getEstudiante().getApellido()}</td>
			                  		<td> ${ins.getEstado()} </td>
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
                </div>
              </div>
            </div>	   
         </form>
	<script type="text/javascript">	
	$(function() {
		$('select[name="instituto"]').on('change', function() {
			//quitar la seleccion de categoria
			$('select[name="categoria"]').val('');
			//limpiar los datos de edicion
			limpiarEdicion();
		    var instituto = $(this).val();
			//alert(instituto);
			//vaciar la select
	    	$('#curso').find('option').remove();
			//agregar opcion vacia
	        $('#curso').append($('<option>', { 
	            value: '',
	            text : '(Seleccione un curso)' 
	        }));
			$.getJSON('JsonCursosInstituto', { 'instituto': instituto }, function(data) {
			    $.each(data, function(indice, curso) {
			    	//alert(indice + ": " + curso.nombre);
			        $('#curso').append($('<option>', { 
			            value: curso.nombre,
			            text : curso.nombre 
			        }));
			    });
			});
		});
		$('select[name="curso"]').on('change', function() {
			//vaciar la select
	    	$('#edicion').find('option').remove();
			//agregar opcion vacia
	        $('#edicion').append($('<option>', { 
	            value: '',
	            text : '(Seleccione una edicion)' 
	        }));
		    var curso = $(this).val();
				//alert(data.nombre);
				
			$.getJSON('JsonEdicionesCurso', {'curso' : curso}, function(data) {
				$.each(data, function(indice, edicion) {
					$('#edicion').append($('<option>', {
						value : edicion.nombre,
						text : edicion.nombre
					}));
				});
			});
			limpiarEdicion();
		});
		$('select[name="edicion"]').on('change', function() {
		    var edicion = $(this).val();
			//alert(data.nombre);
			if(edicion) {
				$.getJSON('JsonEdicionCursoSeleccionada', {'edicion' : edicion}, function(data) {
					if(data) {
						$("#nombreEdicion").val(data.nombre);
						var fIni = new Date(data.fechaInicio);
						$("#fechaInicio").val(fIni.getFullYear() + '-' + ("0" + (fIni.getMonth() + 1)).slice(-2) + "-" +  ("0" + fIni.getDate()).slice(-2));
						var fFin = new Date(data.fechaFin);
						$("#fechaFin").val(fFin.getFullYear() + '-' + ("0" + (fFin.getMonth() + 1)).slice(-2) + "-" +  ("0" + fFin.getDate()).slice(-2));
						$("#cupo").val(data.cupo == -1 ? 'Sin definir' : data.cupo);
						
						//antes de agregar la limpio
						$("#tablaInscripciones > tbody").html("");
						$.each(data.inscripciones, function(indice, inscripcion) {
					    	//alert(indice + ": " + inscripcion.estudiante.nombre);
					    	var $row = "<tr><td><input name='listaInscripciones' type='hidden' value='" + inscripcion.estudiante.nickname + "'/>" + 
					    	inscripcion.estudiante.nombre + " " + inscripcion.estudiante.apellido +
							"<td> " + inscripcion.estado + "</td>";
			    			$("#tablaInscripciones tbody").append($row);				    	
					    });
						$("#error").html("");
					} else {
						$("#error").html("No hay una edición vigente para el curso.");
						limpiarEdicion();
					}
				});
			} else 
				limpiarEdicion();
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
			$("#fechaInicio").attr("readonly", false).val('').attr("readonly",
					true);
			$("#fechaFin").attr("readonly", false).val('').attr("readonly",
					true);
			;
			$("#cupo").val('');
			$("#tablaInscripciones > tbody").html("");
		};
	</script>          
    </jsp:body>
</t:plantilla>
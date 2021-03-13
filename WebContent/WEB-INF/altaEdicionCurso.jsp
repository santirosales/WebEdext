<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
         <h4>Alta de Edición de Curso</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>  
         <form id="formAltaCurso" class="form-horizontal" action="AltaEdicionCurso" method="post">
		   <div id="divInstituto" class="form-group row">
             <label class="control-label col-sm-2" for="tipo">Instituto:</label>
             <div class="col-sm-10">
                <select class="form-control" name="instituto" id="instituto">       
	            	<option value="" ${edicion.getInstituto() == '' ? 'selected' : ''}>(Seleccione un instituto)</option>
				<c:forEach items="${institutos}" var="instituto" >
	            	<option value="${instituto}" ${edicion.getInstituto() == instituto ? 'selected' : ''}>${instituto}</option>
				</c:forEach>
                </select>
             </div>
           </div>
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="curso">Curso:</label>
             <div class="col-sm-10">
                <select class="form-control" name="curso" id="curso">
				<c:forEach items="${cursos}" var="curso" >
	            	<option value="${fn:escapeXml(curso)}" ${curso == cursoSeleccionado ? 'selected' : ''}>${curso}</option>
				</c:forEach>
                </select>
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nombre">Nombre:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nombre" id="nombre" required maxlength="200" value="${edicion.getNombre()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaInicio">Fecha de inicio:</label>
             <div class="col-sm-10">
             	<fmt:formatDate value="${edicion.getFechaInicio().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaInicio" />         
             	<input type="date" class="form-control" name="fechaInicio" id="fechaInicio" required value="${fechaInicio}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaFin">Fecha de fin:</label>
             <div class="col-sm-10">
             	<fmt:formatDate value="${edicion.getFechaFin().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaFin" />         
             	<input type="date" class="form-control" name="fechaFin" id="fechaFin" required value="${fechaFin}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="cupo">Cupo:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="cupo" id="cupo" maxlength="50" value="${edicion.getCupo()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="urlImagen">URL imagen:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="urlImagen" id="urlImagen" value="${edicion.getUrlImagen()}">
             </div>
           </div>
           
	        <ul class="nav nav-tabs">
	          <li class="nav-item">
	            <a class="nav-link active" data-toggle="tab" href="#docentes"><strong>Docentes</strong></a>
	          </li>
	        </ul>
	
	        <div class="tab-content">
	          <div id="docentes" class="tab-pane container active">
                  <div class="card">
                    <div class="card-body">                  
                      <div class=""><strong>Docentes</strong></div>
                      <div class="input-group">
                        <div class="input-group-prepend">
                          <label class="input-group-text" for="selectDocentes">Agregar:</label>
                        </div>                       
                        <select class="form-control" id="selectDocentes">
						<c:forEach items="${docentes}" var="docente" >
			            	<option value="${docente}">${docente}</option>
						</c:forEach>                        
                        </select>
                        <div class="input-group-append">
                          <button id="btnAgregarDocente" class="btn btn-info" type="button"><i class="fa fa-plus" aria-hidden="true"></i></button>
                        </div>                       
                      </div>
                      <table id="tablaDocentes" class="table table-bordered table-striped">
                        <thead>
                          <tr>
                            <th>Nickname</th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
							<c:forEach items="${listaDocentes}" var="docente" >
				                <tr>
				                  <td><input name="listaDocentes" type="hidden" value="${docente}">${docente}</td><td><button class="btn btn-info btn-sm float-right btnQuitar"><i class="fas fa-trash"></i></button></td>
				                </tr>
							</c:forEach>
						</tbody>
                      </table>                  
                    </div>
                  </div>
                </div>
	        </div>
           <div class="form-group row">
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Guardar</button>
             </div>
           </div>
         </form>
	<script type="text/javascript">	
	$(function() {
		$('select[name="instituto"]').on('change', function() {
		    var instituto = $(this).val();
			//alert(instituto);
			//vaciar la select
	    	$('#curso').find('option').remove();
			$.getJSON('JsonCursosInstituto', { 'instituto': instituto }, function(data) {
			    $.each(data, function(indice, curso) {
			    	//alert(indice + ": " + curso.nombre);
			        $('#curso').append($('<option>', { 
			            value: curso.nombre,
			            text : curso.nombre 
			        }));
			    });
			});
	    	$('#selectDocentes').find('option').remove();
			$.getJSON('JsonDocentesInstituto', { 'instituto': instituto }, function(data) {
			    $.each(data, function(indice, docente) {
			    	//alert(indice + ": " + curso.nombre);
			        $('#selectDocentes').append($('<option>', { 
			            value: docente,
			            text : docente
			        }));
			    });
			});
		});
		$("#btnAgregarDocente").on("click",function() {
			var docente = $("#selectDocentes").children("option:selected").html();
	    	var $row = "<tr><td><input name='listaDocentes' type='hidden' value='" + docente + "'/>" + 
	    				docente + 
	    				"<td><button class='btn btn-info btn-sm float-right btnQuitar'><i class='fas fa-trash'></i></button></td>" +
	    				"</td></tr>";
	    	//antes de agregar verifico que no este
	    	var existeDocente = false;
			$("#tablaDocentes tr").each(function() {
			    var texto = $(this).find("input").val()
			    if(texto == docente)
			    	existeDocente = true;
			});
			if(!existeDocente)
		    	$("#tablaDocentes tbody").append($row);
	  	});
		//borrar la fila con el click en el boton
		$("#tablaDocentes").on('click','.btnQuitar',function(){
			$(this).closest('tr').remove();
		});		
	});
	</script>  
    </jsp:body>
</t:plantilla>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Inscripción a Edición de Curso</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="InscripcionEdicionCurso" method="post" autocomplete="off">
           <div class="form-group row">
             <label class="control-label col-sm-2" for="instituto">Instituto:</label>
             <div class="col-sm-4">
             <select class="form-control" id="instituto" name="instituto">
            		<option value="" ${instituto == '' ? 'selected' : ''}>(Seleccione un instituto)</option>
				<c:forEach items="${institutos}" var="ins" >
	            	<option value="${fn:escapeXml(ins)}" ${instituto == ins ? 'selected' : ''}>${ins}</option>
				</c:forEach>
             </select>
             </div>
			 <label class="control-label col-sm-2" for="curso">Categorías:</label>
			 <div class="col-sm-4">
             <select class="form-control" id="categoria" name="categoria">
            		<option value="" ${categoria == '' ? 'selected' : ''}>(Seleccione una categoría)</option>
				<c:forEach items="${categorias}" var="cat" >
	            	<option value="${fn:escapeXml(cat)}" ${categoria == cat ? 'selected' : ''}>${cat}</option>
				</c:forEach>
             </select>
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
             <label class="control-label col-sm-2" for="nombreEdicion">Edición:</label>
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
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="urlVideo">Video:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="urlVideo" id="video" maxlength="255" value="${urlVideo}">
             </div>
           </div>
           <div class="form-group row">
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Confirmar inscripción</button>
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
		$('select[name="categoria"]').on('change', function() {
			//quitar la seleccion de instituto
			$('select[name="instituto"]').val('');
			//limpiar los datos de edicion
			limpiarEdicion();
		    var categoria = $(this).val();
			//vaciar la select
	    	$('#curso').find('option').remove();
			//agregar opcion vacia
	        $('#curso').append($('<option>', { 
	            value: '',
	            text : '(Seleccione un curso)' 
	        }));
			$.getJSON('JsonCursosCategoria', { 'categoria': categoria }, function(data) {
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
		    var curso = $(this).val();
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
					//la edicion esta como data
					if(data && data.finalizada)
						$("#error").html("La edición del curso se encuentra finalizada.");
					else
						$("#error").html("");
				} else {
					$("#error").html("No hay una edición vigente para el curso.");
					$("#nombreEdicion").val('');
					$("#edicion").val('');
					$("#fechaInicio").val('');
					$("#fechaFin").val('');
					$("#cupo").val('');
				}
			});
		});		
	});
	function limpiarEdicion() {
		$("#nombreEdicion").val('');
		$("#edicion").val('');
		//en firefox no se borra si es readonly
		$("#fechaInicio").attr("readonly", false).val('').attr("readonly", true);
		$("#fechaFin").attr("readonly", false).val('').attr("readonly", true);;
		$("#cupo").val('');
		$("#error").html("");
	};

	</script>          
    </jsp:body>
</t:plantilla>
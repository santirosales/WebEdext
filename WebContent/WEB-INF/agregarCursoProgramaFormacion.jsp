<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Agregar Curso a Programa de Formación</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="AgregarCursoProgramaFormacion" method="post" autocomplete="off">
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="curso">Programa:</label>
             <div class="col-sm-10">
                <select class="form-control" name="programa" id="programa">
            		<option value="" ${programa == '' ? 'selected' : ''}>(Seleccione un programa)</option>
				<c:forEach items="${programas}" var="prog" >
	            	<option value="${fn:escapeXml(prog)}" ${programa == prog ? 'selected' : ''}>${prog}</option>
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
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Guardar</button>
             </div>
           </div>		   
         </form>
	<script type="text/javascript">	
	$(function() {
		$('select[name="programa"]').on('change', function() {
		    var programa = $(this).val();
			//alert(programa);
			//vaciar la select
	    	$('#curso').find('option').remove();
			//agregar opcion vacia
	        $('#curso').append($('<option>', { 
	            value: '',
	            text : '(Seleccione un curso)' 
	        }));
			$.getJSON('JsonCursos', function(data) {
			    $.each(data, function(indice, curso) {
			    	//alert(indice + ": " + curso.nombre);
			        $('#curso').append($('<option>', { 
			            value: curso.nombre,
			            text : curso.nombre 
			        }));
			    });
			});
		});
	});
	</script>          
    </jsp:body>
</t:plantilla>
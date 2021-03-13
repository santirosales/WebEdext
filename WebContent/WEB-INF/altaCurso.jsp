<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:plantilla>
    <jsp:body>
         <h4>Alta de Curso</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>  
         <form id="formAltaCurso" class="form-horizontal" action="AltaCurso" method="post">
		   <div id="divInstituto" class="form-group row">
             <label class="control-label col-sm-2" for="tipo">Instituto:</label>
             <div class="col-sm-10">
                <select class="form-control" name="instituto" id="instituto">       
	            	<option value="" ${curso.getInstituto() == '' ? 'selected' : ''}>(Seleccione un instituto)</option>
				<c:forEach items="${institutos}" var="instituto" >
	            	<option value="${instituto}" ${curso.getInstituto() == instituto ? 'selected' : ''}>${instituto}</option>
				</c:forEach>
                </select>
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nombre">Nombre:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nombre" id="nombre" required maxlength="200" value="${curso.getNombre()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="descripcion">Descripción:</label>
             <div class="col-sm-10">
                <textarea class="form-control" id="descripcion" name="descripcion" required rows="4">${curso.getDescripcion()}</textarea>
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="duracion">Duración:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="duracion" id="duracion" required maxlength="50" value="${curso.getDuracion()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="cantidadHoras">Cant. horas:</label>
             <div class="col-sm-10">
               <input type="number" class="form-control" name="cantidadHoras" id="cantidadHoras" required maxlength="6" value="${curso.getCantidadHoras()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="creditos">Créditos:</label>
             <div class="col-sm-10">
               <input type="number" class="form-control" name="creditos" id="creditos" required maxlength="4" value="${curso.getCreditos()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="url">URL:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="url" id="url" required value="${curso.getUrl()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="urlImagen">URL imagen:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="urlImagen" id="urlImagen" value="${curso.getUrlImagen()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaAlta">Fecha de alta:</label>
             <div class="col-sm-10">
             	<fmt:formatDate value="${curso.getFechaAlta().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fecha" />         
             	<input type="date" class="form-control" name="fechaAlta" id="fechaAlta" required value="${fecha}">
             </div>
           </div>
           
	        <ul class="nav nav-tabs">
	          <li class="nav-item">
	            <a class="nav-link active" data-toggle="tab" href="#previas"><strong>Previas</strong></a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" data-toggle="tab" href="#categorias"><strong>Categorías</strong></a>
	          </li>
	        </ul>
	
	        <div class="tab-content">
	          <div id="previas" class="tab-pane container active">
                  <div class="card">
                    <div class="card-body">                  
                      <div class=""><strong>Previas</strong></div>
                      <div class="input-group">
                        <div class="input-group-prepend">
                          <label class="input-group-text" for="selectPrevias">Agregar:</label>
                        </div>                       
                        <select class="form-control" id="selectPrevias">
						<c:forEach items="${previas}" var="curso" >
			            	<option value="${curso}">${curso}</option>
						</c:forEach> 
						</select>
                        <div class="input-group-append">
                          <button id="btnAgregarPrevia" class="btn btn-info" type="button"><i class="fa fa-plus" aria-hidden="true"></i></button>
                        </div>                       
                      </div>
                      <table id="tablaPrevias" class="table table-bordered table-striped">
                        <thead>
                          <tr>
                            <th>Nombre de la previa</th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
							<c:forEach items="${curso.getPrevias()}" var="curso" >
				                <tr>
				                  <td><input name="listaPrevias" type="hidden" value="${curso}">${curso}</td><td><button class="btn btn-info btn-sm float-right btnQuitar"><i class="fas fa-trash"></i></button></td>
				                </tr>
							</c:forEach>
						</tbody>
                      </table>                  
                    </div>
                  </div>
                </div>
	          <div id="categorias" class="tab-pane container">
                  <div class="card">
                    <div class="card-body">                  
                      <div class=""><strong>Categorías</strong></div>
                      <div class="input-group">
                        <div class="input-group-prepend">
                          <label class="input-group-text" for="selectCategorias">Agregar:</label>
                        </div>                       
                        <select class="form-control" id="selectCategorias">
			            	<!-- option value=""></option -->
						<c:forEach items="${categorias}" var="categoria" >
			            	<option value="${categoria}">${categoria}</option>
						</c:forEach>
                        </select>
                        <div class="input-group-append">
                          <button id="btnAgregarCategoria" class="btn btn-info" type="button"><i class="fa fa-plus" aria-hidden="true"></i></button>
                        </div>                       
                      </div>
                      <table id="tablaCategorias" class="table table-bordered table-striped">
                        <thead>
                          <tr>
                            <th>Nombre de la categoría</th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
							<c:forEach items="${curso.getCategorias()}" var="categoria" >
				                <tr>
				                  <td><input name="listaCategorias" type="hidden" value="${categoria}">${categoria}</td><td><button class="btn btn-info btn-sm float-right btnQuitar"><i class="fas fa-trash"></i></button></td>
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
	    	$('#selectPrevias').find('option').remove();
			$.getJSON('JsonCursosInstituto', { 'instituto': instituto }, function(data) {
			    $.each(data, function(indice, curso) {
			    	//alert(indice + ": " + curso.nombre);
			        $('#selectPrevias').append($('<option>', { 
			            value: curso.nombre,
			            text : curso.nombre 
			        }));
			    });
			});
		});		
		//agregar previas y categorias
		$("#btnAgregarPrevia").on("click",function() {
			var previa = $("#selectPrevias").children("option:selected").html();
	    	var $row = "<tr><td><input name='listaPrevias' type='hidden' value='" + previa + "'/>" + 
	    				previa + 
	    				"<td><button class='btn btn-info btn-sm float-right btnQuitar'><i class='fas fa-trash'></i></button></td>" +
	    				"</td></tr>";
	    	//antes de agregar verifico que no este
	    	var existePrevia = false;
			$("#tablaPrevias tr").each(function() {
			    var texto = $(this).find("input").val()
			    if(texto == previa)
			    	existePrevia = true;
			});
			if(!existePrevia)
		    	$("#tablaPrevias tbody").append($row);
	  	});
		
		$("#btnAgregarCategoria").on("click",function() {
			var categoria = $("#selectCategorias").children("option:selected").html();
	    	var $row = "<tr><td><input name='listaCategorias' type='hidden' value='" + categoria + "'/>" + 
	    				categoria + 
	    				"<td><button class='btn btn-info btn-sm float-right btnQuitar'><i class='fas fa-trash'></i></button></td>" +
	    				"</td></tr>";
	    	//antes de agregar verifico que no este
	    	var existeCategoria = false;
			$("#tablaCategorias tr").each(function() {
			    var texto = $(this).find("input").val()
			    if(texto == categoria)
			    	existeCategoria = true;
			});
			if(!existeCategoria)
		    	$("#tablaCategorias tbody").append($row);
	  	});

		//borrar la fila con el click en el boton
		$("#tablaPrevias").on('click','.btnQuitar',function(){
			$(this).closest('tr').remove();
		});
		//borrar la fila con el click en el boton
		$("#tablaCategorias").on('click','.btnQuitar',function(){
			$(this).closest('tr').remove();
		});
	});
	</script>  
    </jsp:body>
</t:plantilla>
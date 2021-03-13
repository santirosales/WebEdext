<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:plantilla>
    <jsp:body>
         <h4>Alta de Usuario</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>           
         <form id="formAltaUsuario" class="form-horizontal" action="AltaUsuario" method="post">
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nickname">Nickname:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nickname" id="nickname" required maxlength="45" value="${usuario.getNickname()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="urlFoto">URL Foto:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="urlFoto" id="urlFoto" value="${usuario.getUrlFoto()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nombre">Nombre:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nombre" id="nombre" required maxlength="45" value="${usuario.getNombre()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="apellido">Apellido:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="apellido" id="apellido" required maxlength="45" value="${usuario.getApellido()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="clave">Contraseña:</label>
             <div class="col-sm-10">
               <input type="password" class="form-control" name="clave" id="clave" required maxlength="45">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="claveConfirmar">Confirmar contraseña:</label>
             <div class="col-sm-10">
               <input type="password" class="form-control" name="claveConfirmar" id="claveConfirmar" required maxlength="45">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="email">Correo:</label>
             <div class="col-sm-10">
               <input type="email" class="form-control" name="email" id="email" required maxlength="45" value="${usuario.getCorreoElectronico()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaNacimiento">Fecha de nac.:</label>
             <div class="col-sm-10">
               <fmt:formatDate value="${usuario.getFechaNacimiento().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fecha" />         
               <input type="date" class="form-control" name="fechaNacimiento" id="fechaNacimiento" required 
               		value="${fecha}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="tipo">Tipo:</label>
             <div class="col-sm-10">
              <select class="form-control" name="tipo" id="tipo" onchange="getTipo(this);">
                 <option value="Estudiante" ${usuario.getTipo() == 'Estudiante' ? 'selected' : ''}>Estudiante</option>
                 <option value="Docente" ${usuario.getTipo() == 'Docente' ? 'selected' : ''}>Docente</option>
              </select>
             </div>
           </div>
           <div id="divInstituto" class="form-group row" style="${usuario.getTipo() != 'Docente' ? 'display:none;' : ''}">
             <label class="control-label col-sm-2" for="tipo">Instituto:</label>
             <div class="col-sm-10">
                <select class="form-control" name="instituto" id="instituto">       
				<c:forEach items="${institutos}" var="instituto" >
					<c:if test="${usuario.getTipo() == 'Docente'}">
	            		<option value="${instituto}" ${usuario.getInstituto() == instituto ? 'selected' : ''}>${instituto}</option>
	            	</c:if>
					<c:if test="${usuario.getTipo() != 'Docente'}">
	            		<option value="${instituto}">${instituto}</option>
	            	</c:if>
				</c:forEach>
                </select>
             </div>
           </div>
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-info">Guardar</button>
            </div>
         </form>
		<script type="text/javascript">	
			function getTipo(sel) {
			 var select = $(sel).find(':selected').val();
			 if(select == "Docente")
			       $("#divInstituto").show();
			 else
			       $("#divInstituto").hide();
			};
		</script>
		<script type='text/javascript'>
		$( "#formAltaUsuario" ).validate( {
			rules: {
				nickname: {
					required: true,
				    remote: {
				        url: "JsonNicknameLibre",
				        type: "POST",
				        cache: false,
				        dataType: "json",
				        data: {
				            email: function() { return $("#nickname").val(); }
				        },
				        dataFilter: function(response) {
				            return response;
				        }
				    }					
				},
				nombre: "required",
				apellido: "required",
				clave: {
					required: true
				},
				claveConfirmar: {
					required: true,
					equalTo: "#clave"
				},
				fechaNacimiento: "required",
				email: {
					required: true,
					email: true,
				    remote: {
				        url: "JsonEmailLibre",
				        type: "POST",
				        cache: false,
				        dataType: "json",
				        data: {
				            email: function() { return $("#email").val(); }
				        },
				        dataFilter: function(response) {
				            return response;
				        }
				    }	
				}
			},
			messages: {
				nickname: {
					required: "Debe ingresar un nickname",
					remote: "El nickname ya está en uso"
				},
				nombre: "Debe ingresar un nombre",
				apellido: "Debe ingresar un apellido",
				clave: {
					required: "Debe ingresar una clave"
				},
				claveConfirmar: {
					required: "Debe repetir la contraseña ingresada",
					equalTo: "Las contrseñas deben coincidir"
				},
				fechaNacimiento: "Debe ingresar una fecha de nacimiento",
				email: {
					required: "Por favor ingrese un email",
					email: "Por favor ingrese un email válido",
					remote: "El correo electrónico ya está en uso"
				}
			},
			errorClass: 'text-danger',
		    errorElement: 'em',
		    highlight: function (element, errorClass, validClass) {
		        //$(element).removeClass('is-valid');
		        $(element).addClass('is-invalid');
		    },
		    unhighlight: function (element, errorClass, validClass) {
		        $(element).removeClass('is-invalid');
		        //$(element).addClass('is-valid');
		    }
	    });
		</script>
    </jsp:body>
</t:plantilla>

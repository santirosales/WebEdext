<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:plantilla>
    <jsp:body>
		<h4>Modificar ${usuario.getTipo()}</h4>
      	<hr />
      	<br />
		<div class="form-group row">
         	<div class="mx-auto">
         		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>          
         <form id="formAltaUsuario" class="form-horizontal" action="ModificarUsuario" method="post">
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nickname">Nickname:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nickname" id="nickname" required maxlength="45" readonly value="${usuario.getNickname()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nickname">URL Foto:</label>
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
             <label class="control-label col-sm-2" for="email">Correo:</label>
             <div class="col-sm-10">
               <input type="email" class="form-control" name="email" id="email" required maxlength="45" readonly value="${usuario.getCorreoElectronico()}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaNacimiento">Fecha de nac.:</label>
             <div class="col-sm-10">               
			   <fmt:formatDate value="${usuario.getFechaNacimiento().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fecha"/>
               <input type="date" class="form-control" name="fechaNacimiento" id="fechaNacimiento" required value="${fecha}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="tipo">Tipo:</label>
             <div class="col-sm-10">
               <input type="hidden" name="tipo" id="tipo" value="${usuario.getTipo()}">
               <input type="text" class="form-control" required maxlength="45" readonly value="${usuario.getTipo()}">
             </div>
           </div>
		   <c:if test="${usuario.getTipo() == 'Docente'}">
           <div id="divInstituto" class="form-group row">
             <label class="control-label col-sm-2" for="tipo">Instituto:</label>
             <div class="col-sm-10">
                <select class="form-control" name="instituto" id="instituto">       
				<c:forEach items="${institutos}" var="instituto" >
	            	<option value="${instituto}" ${usuario.getInstituto() == instituto ? 'selected' : ''}>${instituto}</option>
				</c:forEach>
                </select>
             </div>
           </div>
		   </c:if>
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-info">Guardar</button>
            </div>
         </form>
    </jsp:body>
</t:plantilla>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
         <h4>Crear Programa de Formación</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>  
         <form id="formCrearProgramaFormacion" class="form-horizontal" action="CrearProgramaFormacion" method="post">		   
           <div class="form-group row">
             <label class="control-label col-sm-2" for="nombre">Nombre:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="nombre" id="nombre" required maxlength="200" value="${programa.getNombre()}">
             </div>
           </div>           
           <div class="form-group row">
             <label class="control-label col-sm-2" for="descripcion">Descripción:</label>
             <div class="col-sm-10">
                <textarea class="form-control" id="descripcion" name="descripcion" required rows="4">${programa.getDescripcion()}</textarea>
             </div>
           </div>           
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaInicio">Fecha de inicio:</label>
             <div class="col-sm-10">
             	<fmt:formatDate value="${programa.getFechaInicio().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaInicio" />         
             	<input type="date" class="form-control" name="fechaInicio" id="fechaInicio" value="${fechaInicio}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaFin">Fecha de fin:</label>
             <div class="col-sm-10">
             	<fmt:formatDate value="${programa.getFechaFin().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaFin" />         
             	<input type="date" class="form-control" name="fechaFin" id="fechaFin" value="${fechaFin}">
             </div>
           </div>                 
           <div class="form-group row">
             <label class="control-label col-sm-2" for="urlImagen">URL imagen:</label>
             <div class="col-sm-10">
               <input type="text" class="form-control" name="urlImagen" id="urlImagen" value="${programa.getUrlImagen()}">
             </div>
           </div>   
           <div class="form-group row">
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Guardar</button>
             </div>
           </div>
         </form>
	 
    </jsp:body>
</t:plantilla>
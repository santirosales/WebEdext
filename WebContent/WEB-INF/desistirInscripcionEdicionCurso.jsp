<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
         <h4>Desistir de inscripción a edición de curso</h4>
         <hr />
         <br />
		<div class="form-group row">
         	<div class="mx-auto">
         		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>                   
        <form class="form-horizontal" action="DesistirInscripcionEdicionCurso" method="post" autocomplete="off">
         <div class="form-group row">
           <label class="control-label col-sm-2" for="nickname">Nickname:</label>
           <div class="col-sm-10">
           		<input type="hidden" name="nickname" id="nickname" value="${inscripcion.getEstudiante().getNickname()}">
           		<input type="text" class="form-control" name="nickname" id="nickname" readonly value="${inscripcion.getEstudiante().getNickname()}">
           </div>
         </div>           
         <div class="form-group row">
           <label class="control-label col-sm-2" for="edicion">Edición:</label>
           <div class="col-sm-10">
           		<input type="hidden" name="edicion" id="edicion" value="${fn:escapeXml(inscripcion.getEdicion())}">
           		<input type="text" class="form-control" name="edicion" id="edicion" readonly value="${fn:escapeXml(inscripcion.getEdicion())}">
           </div>
         </div>           
         <div class="form-group row">
           <label class="control-label col-sm-2" for="fechaInicio">Fecha de inscripcion:</label>
           <div class="col-sm-10">
				<fmt:formatDate value="${inscripcion.getFecha().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fecha"/>
             	<input type="date" class="form-control" name="fecha" id="fecha" readonly value="${fecha}">
           </div>
         </div>           
         <div class="form-group row">
           <label class="control-label col-sm-2" for="urlVideo">Video:</label>
           <div class="col-sm-10">
           		<input type="text" class="form-control" name="urlVideo" id="urlVideo" readonly value="${inscripcion.getUrlVideo()}">
           </div>
         </div>
           <div class="form-group row">
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Confirmar</button>
             </div>
           </div>		   
        </form>
    </jsp:body>
</t:plantilla>